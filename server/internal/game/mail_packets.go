package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Mail wire format.
//
// A mail record (used by MAIL_LIST 15001, MAIL_SEND 539 and MAIL_SEND_RESULT
// 15003) is a fixed header followed by a variable "extra" TLV blob:
//
//	[i64 mailId][i64 senderId][u8 len][utf8 senderName][i32 senderGame]
//	[i64 receiverId][u8 len][utf8 receiverName]
//	[i32 extraLen][extra…]
//	[i64 dateMillis][u8 read][u8 deletedBySender][u8 deletedByReceiver][i32 state]
//
// The extra blob is order-agnostic tagged fields; absent ones are simply omitted:
//
//	tag 1 title  : [u16 1][i32 byteLen][utf8]
//	tag 2 body   : [u16 2][i32 byteLen][utf8]
//	tag 3 cards  : [u16 3][u16 count]{[i32 cardTemplateId]}
//	tag 4 system : [u16 4][i32 systemMessageId]   (senderId < 0 mails only)
//
// A senderId < 0 marks a system mail, for which the client pulls its title/body/
// sender name from i18n tables 32/33/34 keyed by the tag-4 id instead of the
// strings above.

// writeStringU8 appends [u8 byteLen][bytes], clipping to what the length prefix
// can express so an over-long name can never corrupt the frame.
func writeStringU8(w *protocol.Writer, s string) {
	b := []byte(s)
	if len(b) > 255 {
		b = b[:255]
	}
	w.U8(uint8(len(b)))
	w.Raw(b)
}

// buildMailExtra builds the TLV blob of a mail record.
func buildMailExtra(title, body string, cards []int32) []byte {
	w := protocol.NewWriter()
	if title != "" {
		b := []byte(title)
		w.U16(mailTagTitle).I32(int32(len(b))).Raw(b)
	}
	if body != "" {
		b := []byte(body)
		w.U16(mailTagBody).I32(int32(len(b))).Raw(b)
	}
	if len(cards) > 0 {
		w.U16(mailTagCards).U16(uint16(len(cards)))
		for _, id := range cards {
			w.I32(id)
		}
	}
	return w.Bytes()
}

// writeMailRecord appends one mail record. cards overrides the mail's stored
// attachments (used when echoing a just-sent mail).
func writeMailRecord(w *protocol.Writer, m *domain.Mail, cards []int32) {
	if cards == nil {
		cards = make([]int32, 0, len(m.Cards))
		for _, c := range m.Cards {
			cards = append(cards, c.TemplateID)
		}
	}
	extra := buildMailExtra(m.Title, m.Body, cards)

	w.I64(int64(m.ID))
	w.I64(int64(m.SenderID))
	writeStringU8(w, m.SenderName)
	w.I32(mailSenderGame)
	w.I64(int64(m.ReceiverID))
	writeStringU8(w, m.ReceiverName)
	w.I32(int32(len(extra)))
	w.Raw(extra)
	w.I64(m.SentAtMillis)
	w.U8(boolU8(m.Read))
	w.U8(boolU8(m.DeletedBySender))
	w.U8(boolU8(m.DeletedByReceiver))
	w.I32(0) // state: never read by the client
}

// buildMailList builds MAIL_LIST (15001): [i16 count]{mail record}. Receiving it
// is what OPENS the client's mailbox dialog, so it must be sent even when empty.
func buildMailList(mails []domain.Mail) ([]byte, error) {
	w := protocol.NewWriter().U16(uint16(len(mails)))
	for i := range mails {
		writeMailRecord(w, &mails[i], nil)
	}
	return protocol.EncodeS2C(protocol.OpMailList, w.Bytes())
}

// buildMailCardsTaken builds MAIL_CARDS_TAKEN (15007):
// [i64 mailId][i64 coachId][u8 count]{[i32 cardTemplateId]}. The client removes
// each returned card from the mail and adds it to its inventory.
func buildMailCardsTaken(mailID, coachID int64, cards []int32) ([]byte, error) {
	if len(cards) > 255 {
		cards = cards[:255]
	}
	w := protocol.NewWriter().I64(mailID).I64(coachID).U8(uint8(len(cards)))
	for _, id := range cards {
		w.I32(id)
	}
	return protocol.EncodeS2C(protocol.OpMailCardsTaken, w.Bytes())
}

// buildMailNewNotice builds MAIL_NEW_NOTICE (15005): [u8 count] — the "you have
// [n] new mail" toast. Purely cosmetic.
func buildMailNewNotice(count uint8) ([]byte, error) {
	return protocol.EncodeS2C(protocol.OpMailNewNotice, protocol.NewWriter().U8(count).Bytes())
}

// inboundMail is a decoded client-sent mail record (opcode 539). Only the fields
// the server trusts are kept — the sender is taken from the session instead.
type inboundMail struct {
	receiverID   int64
	receiverName string
	title        string
	body         string
	cards        []int32
}

// decodeMailRecord parses a mail record sent by the client.
func decodeMailRecord(r *protocol.Reader) (*inboundMail, error) {
	if _, err := r.I64(); err != nil { // mailId: always 0, the server assigns it
		return nil, err
	}
	if _, err := r.I64(); err != nil { // senderId: taken from the session instead
		return nil, err
	}
	if _, err := r.StringU8(); err != nil { // senderName: idem
		return nil, err
	}
	if _, err := r.I32(); err != nil { // senderGame
		return nil, err
	}
	receiverID, err := r.I64()
	if err != nil {
		return nil, err
	}
	receiverName, err := r.StringU8()
	if err != nil {
		return nil, err
	}
	extraLen, err := r.I32()
	if err != nil {
		return nil, err
	}
	if extraLen < 0 || extraLen > int32(r.Remaining()) {
		return nil, protocol.ErrTruncated
	}
	extra, err := r.Bytes(int(extraLen))
	if err != nil {
		return nil, err
	}
	m := &inboundMail{receiverID: receiverID, receiverName: receiverName}
	if err := parseMailExtra(extra, m); err != nil {
		return nil, err
	}
	// The trailing date/read/deleted/state fields are client-supplied and not
	// trusted, so they are simply ignored.
	return m, nil
}

// parseMailExtra reads the TLV blob of a client-sent mail record.
func parseMailExtra(extra []byte, m *inboundMail) error {
	r := protocol.NewReader(extra)
	for r.Remaining() > 0 {
		tag, err := r.U16()
		if err != nil {
			return err
		}
		switch tag {
		case mailTagTitle, mailTagBody:
			n, err := r.I32()
			if err != nil {
				return err
			}
			if n < 0 || n > int32(r.Remaining()) {
				return protocol.ErrTruncated
			}
			s, err := r.String(int(n))
			if err != nil {
				return err
			}
			if tag == mailTagTitle {
				m.title = s
			} else {
				m.body = s
			}
		case mailTagCards:
			n, err := r.U16()
			if err != nil {
				return err
			}
			for i := 0; i < int(n); i++ {
				id, err := r.I32()
				if err != nil {
					return err
				}
				m.cards = append(m.cards, id)
			}
		case mailTagSystem:
			if _, err := r.I32(); err != nil {
				return err
			}
		default:
			// Unknown tag: we cannot know its length, so stop rather than desync.
			return nil
		}
	}
	return nil
}
