package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// mailRecord builds the client's opcode-539 mail record. The server ignores the
// client's own id/sender fields (it trusts the session), so only the recipient and
// the extra TLV blob matter.
func mailRecord(receiverID int64, receiverName, title, body string, cards []int32) []byte {
	extra := testclient.NewW()
	if title != "" {
		extra.U16(1).I32(int32(len(title))).Raw([]byte(title))
	}
	if body != "" {
		extra.U16(2).I32(int32(len(body))).Raw([]byte(body))
	}
	if len(cards) > 0 {
		extra.U16(3).U16(uint16(len(cards)))
		for _, id := range cards {
			extra.I32(id)
		}
	}
	eb := extra.Bytes()

	w := testclient.NewW()
	w.I64(0)             // mailId — the server assigns it
	w.I64(0)             // senderId — taken from the session
	w.Str8("")           // senderName — idem
	w.I32(2)             // senderGame
	w.I64(receiverID)    //
	w.Str8(receiverName) //
	w.I32(int32(len(eb)))
	w.Raw(eb)
	w.I64(time.Now().UnixMilli())
	w.U8(0).U8(0).U8(0) // read / deletedBySender / deletedByReceiver
	w.I32(0)            // state
	return w.Bytes()
}

// readMailList decodes MAIL_LIST (15001) far enough to return, per mail, its id,
// title, body and attached card ids.
type mailView struct {
	id         int64
	senderName string
	title      string
	body       string
	cards      []int32
}

func readMailList(t *testing.T, payload []byte) []mailView {
	t.Helper()
	r := testclient.NewR(payload)
	n := int(r.U16())
	out := make([]mailView, 0, n)
	for i := 0; i < n; i++ {
		var m mailView
		m.id = r.I64()
		_ = r.I64() // senderId
		m.senderName = r.Str8()
		_ = r.I32() // senderGame
		_ = r.I64() // receiverId
		_ = r.Str8()
		extraLen := int(r.I32())
		if extraLen < 0 || extraLen > r.Remaining() {
			t.Fatalf("mail %d: bad extraLen %d", i, extraLen)
		}
		er := testclient.NewR(r.RawN(extraLen))
		for er.Remaining() > 0 {
			switch tag := er.U16(); tag {
			case 1:
				m.title = string(er.RawN(int(er.I32())))
			case 2:
				m.body = string(er.RawN(int(er.I32())))
			case 3:
				c := int(er.U16())
				for j := 0; j < c; j++ {
					m.cards = append(m.cards, er.I32())
				}
			case 4:
				_ = er.I32()
			default:
				t.Fatalf("mail %d: unknown extra tag %d", i, tag)
			}
		}
		_ = r.I64() // date
		_ = r.U8()  // read
		_ = r.U8()  // deletedBySender
		_ = r.U8()  // deletedByReceiver
		_ = r.I32() // state
		out = append(out, m)
	}
	if r.Remaining() != 0 {
		t.Errorf("%d trailing bytes after %d mails", r.Remaining(), n)
	}
	return out
}

// TestMailboxOpensEvenWhenEmpty: clicking the mailbox sends 15000, and the server
// MUST answer 15001 — that reply is what opens the client's mailbox dialog, so
// silence would make the mailbox look inert.
func TestMailboxOpensEvenWhenEmpty(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "mail_empty", "MailEmpty")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, testclient.OpMailListRequest, nil)
	f, _, err := a.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no MailList(15001) — the mailbox dialog would never open: %v", err)
	}
	if got := readMailList(t, f.Payload); len(got) != 0 {
		t.Errorf("new coach has %d mails, want 0", len(got))
	}
}

// TestMailSendReceiveAndDelete drives the full coach-to-coach round trip:
// recipient lookup, send, the recipient reading it, and deletion.
func TestMailSendReceiveAndDelete(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "mail_from", "Sender")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "mail_to", "Receiver")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// 1. The compose form validates the recipient name -> its coach id.
	_ = a.Send(2, testclient.OpMailCheckName, testclient.NewW().Str8("Receiver").Bytes())
	f, _, err := a.WaitFor(testclient.OpMailNameResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no MailNameResult(15507): %v", err)
	}
	if got := testclient.NewR(f.Payload).I64(); got != bID {
		t.Fatalf("name lookup = %d, want the receiver's coach id %d", got, bID)
	}

	// An unknown name must resolve to 0 so the client refuses to send.
	_ = a.Send(2, testclient.OpMailCheckName, testclient.NewW().Str8("Nobody").Bytes())
	f, _, err = a.WaitFor(testclient.OpMailNameResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no MailNameResult for unknown name: %v", err)
	}
	if got := testclient.NewR(f.Payload).I64(); got != 0 {
		t.Errorf("unknown-name lookup = %d, want 0", got)
	}

	// 2. Send the mail.
	_ = a.Send(3, testclient.OpMailSend, mailRecord(bID, "Receiver", "Hello", "Body text", nil))
	f, _, err = a.WaitFor(testclient.OpMailSendResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no MailSendResult(15003): %v", err)
	}
	if result := testclient.NewR(f.Payload).I64(); result <= 0 {
		t.Fatalf("send result = %d, want > 0 (accepted)", result)
	}

	// 3. The recipient opens their mailbox and sees it.
	_ = b.Send(3, testclient.OpMailListRequest, nil)
	f, _, err = b.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("receiver got no MailList: %v", err)
	}
	got := readMailList(t, f.Payload)
	if len(got) != 1 {
		t.Fatalf("receiver has %d mails, want 1", len(got))
	}
	if got[0].title != "Hello" || got[0].body != "Body text" {
		t.Errorf("mail = %q/%q, want \"Hello\"/\"Body text\"", got[0].title, got[0].body)
	}
	if got[0].senderName != "Sender" {
		t.Errorf("sender = %q, want \"Sender\" (taken from the session)", got[0].senderName)
	}

	// The sender sees it in their "Sent" tab.
	_ = a.Send(3, testclient.OpMailListRequest, nil)
	f, _, err = a.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("sender got no MailList: %v", err)
	}
	if sent := readMailList(t, f.Payload); len(sent) != 1 {
		t.Errorf("sender sees %d mails, want 1 (its sent copy)", len(sent))
	}

	// 4. The recipient deletes it; it disappears for them but not for the sender.
	_ = b.Send(3, testclient.OpMailDelete, testclient.NewW().U8(1).I64(got[0].id).Bytes())
	b.DrainReceived(150 * time.Millisecond)

	_ = b.Send(3, testclient.OpMailListRequest, nil)
	f, _, err = b.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("receiver got no MailList after delete: %v", err)
	}
	if after := readMailList(t, f.Payload); len(after) != 0 {
		t.Errorf("receiver still has %d mails after deleting", len(after))
	}

	_ = a.Send(3, testclient.OpMailListRequest, nil)
	f, _, err = a.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("sender got no MailList after receiver deleted: %v", err)
	}
	if after := readMailList(t, f.Payload); len(after) != 1 {
		t.Errorf("sender's sent copy vanished when the receiver deleted (got %d)", len(after))
	}
}

// TestMailAttachmentsMoveWithTheMail: attached cards leave the sender's inventory
// and are handed to the receiver only when they collect them.
func TestMailAttachmentsMoveWithTheMail(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "mail_att_a", "AttSender")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "mail_att_b", "AttReceiver")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// Zaap cards are granted at login regardless of game data, so card 202 is
	// guaranteed to be in the sender's inventory here.
	const attached int32 = 202
	_ = a.Send(3, testclient.OpMailSend,
		mailRecord(bID, "AttReceiver", "Gift", "for you", []int32{attached}))
	if _, _, err := a.WaitFor(testclient.OpMailSendResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no MailSendResult: %v", err)
	}

	_ = b.Send(3, testclient.OpMailListRequest, nil)
	f, _, err := b.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("receiver got no MailList: %v", err)
	}
	got := readMailList(t, f.Payload)
	if len(got) != 1 {
		t.Fatalf("receiver has %d mails, want 1", len(got))
	}
	if len(got[0].cards) != 1 || got[0].cards[0] != attached {
		t.Fatalf("mail attachments = %v, want [%d]", got[0].cards, attached)
	}

	// Collect them.
	_ = b.Send(3, testclient.OpMailTakeCards, testclient.NewW().I64(got[0].id).U8(0).Bytes())
	f, _, err = b.WaitFor(testclient.OpMailCardsTaken, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no MailCardsTaken(15007): %v", err)
	}
	r := testclient.NewR(f.Payload)
	if mailID := r.I64(); mailID != got[0].id {
		t.Errorf("cards-taken mailId = %d, want %d", mailID, got[0].id)
	}
	if coachID := r.I64(); coachID != bID {
		t.Errorf("cards-taken coachId = %d, want %d", coachID, bID)
	}
	if n := int(r.U8()); n != 1 || r.I32() != attached {
		t.Errorf("collected %d cards, want 1 (card %d)", n, attached)
	}

	// The mail keeps existing but no longer carries the attachment.
	_ = b.Send(3, testclient.OpMailListRequest, nil)
	f, _, err = b.WaitFor(testclient.OpMailList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("receiver got no MailList after collecting: %v", err)
	}
	after := readMailList(t, f.Payload)
	if len(after) != 1 {
		t.Fatalf("receiver has %d mails after collecting, want 1", len(after))
	}
	if len(after[0].cards) != 0 {
		t.Errorf("attachments still present after collecting: %v", after[0].cards)
	}
}
