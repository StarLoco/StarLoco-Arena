package game

import (
	"errors"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// In-game mailbox: coach-to-coach letters with card attachments, reached from the
// overworld mailbox element (elements.go, kindMailbox).
//
// The client opens its mailbox dialog ONLY when it receives OpMailList (15001);
// clicking the mailbox just sends OpMailListRequest (15000). So answering 15000
// is mandatory — an empty list is a perfectly good answer, but silence makes the
// mailbox look inert.

// mailExtra TLV tags inside a mail record's "extra" blob.
const (
	mailTagTitle   uint16 = 1 // [i32 byteLen][utf8]
	mailTagBody    uint16 = 2 // [i32 byteLen][utf8]
	mailTagCards   uint16 = 3 // [u16 count]{[i32 cardTemplateId]}
	mailTagSystem  uint16 = 4 // [i32 systemMessageId] (system mails only)
	mailSenderGame int32  = 2 // the client sends 2; it never reads the field back
)

func registerMailHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpMailListRequest, handleMailListRequest)
	r.Register(protocol.OpMailDelete, handleMailDelete)
	r.Register(protocol.OpMailTakeCards, handleMailTakeCards)
	r.Register(protocol.OpMailCheckName, handleMailCheckName)
	r.Register(protocol.OpMailSend, handleMailSend)
}

// handleMailListRequest handles MAIL_LIST_REQUEST (15000, empty): replies with the
// coach's whole mailbox. This is what opens the client's mailbox dialog.
func handleMailListRequest(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	mails, err := s.deps.Store.Mail.ListForCoach(s.Coach.ID)
	if err != nil {
		s.log.Warn("mail list", "coach", s.Coach.Name, "err", err)
		mails = nil // still answer, or the dialog never opens
	}
	frame, err := buildMailList(mails)
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// handleMailDelete handles MAIL_DELETE (15004): [u8 count][i64 mailId]×count. The
// client removes the mail from its own view immediately and expects no reply.
func handleMailDelete(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	n, err := r.U8()
	if err != nil {
		return err
	}
	ids := make([]uint, 0, n)
	for i := 0; i < int(n); i++ {
		id, err := r.I64()
		if err != nil {
			return err
		}
		ids = append(ids, uint(id))
	}
	if err := s.deps.Store.Mail.Delete(s.Coach.ID, ids); err != nil {
		s.log.Warn("mail delete", "coach", s.Coach.Name, "err", err)
		return nil
	}
	s.log.Debug("mail deleted", "coach", s.Coach.Name, "count", len(ids))
	return nil
}

// handleMailTakeCards handles MAIL_TAKE_CARDS (15006): [i64 mailId] (+1 pad byte
// the client always sends). Grants the mail's attachments to the receiver and
// replies MAIL_CARDS_TAKEN (15007) so the client moves them into the inventory.
func handleMailTakeCards(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	mailID, err := r.I64()
	if err != nil {
		return err
	}
	cards, err := s.deps.Store.Mail.TakeAttachments(s.Coach.ID, uint(mailID))
	if err != nil && !errors.Is(err, store.ErrNotFound) {
		s.log.Warn("mail take cards", "coach", s.Coach.Name, "mail", mailID, "err", err)
		return nil
	}
	// Reading a mail's attachments also marks it read (there is no separate
	// "mark read" message in this build).
	_ = s.deps.Store.Mail.MarkRead(s.Coach.ID, uint(mailID))

	frame, err := buildMailCardsTaken(mailID, int64(s.Coach.ID), cards)
	if err != nil {
		return err
	}
	if err := s.Send(frame); err != nil {
		return err
	}
	if len(cards) > 0 {
		s.refreshAndPushInventory()
		s.log.Info("mail attachments collected",
			"coach", s.Coach.Name, "mail", mailID, "cards", len(cards))
	}
	return nil
}

// handleMailCheckName handles MAIL_CHECK_NAME (15506): [u8 len][utf8 name]. The
// compose form uses it to validate the recipient; it replies with the coach id, or
// 0 when there is no such coach (the client then refuses to send).
func handleMailCheckName(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	name, err := r.StringU8()
	if err != nil {
		return err
	}
	var id int64
	if coach, err := s.deps.Store.Coaches.GetByName(name); err == nil && coach != nil {
		id = int64(coach.ID)
	}
	w := protocol.NewWriter().I64(id)
	frame, err := protocol.EncodeS2C(protocol.OpMailNameResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// handleMailSend handles MAIL_SEND (539): a full mail record. The client fills in
// its own id/name as sender, a zero mail id and the current time; the server owns
// the real id, validates the recipient and capacity, then replies
// MAIL_SEND_RESULT (15003) echoing the stored mail.
func handleMailSend(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	in, err := decodeMailRecord(protocol.NewReader(f.Payload))
	if err != nil {
		return err
	}

	// Trust the session for the sender, never the client.
	mail := &domain.Mail{
		SenderID:     s.Coach.ID,
		SenderName:   s.Coach.Name,
		ReceiverID:   uint(in.receiverID),
		ReceiverName: in.receiverName,
		Title:        in.title,
		Body:         in.body,
		SentAtMillis: time.Now().UnixMilli(),
	}
	// Resolve/repair the recipient from its name so a stale id can't misdeliver.
	if coach, err := s.deps.Store.Coaches.GetByName(in.receiverName); err == nil && coach != nil {
		mail.ReceiverID, mail.ReceiverName = coach.ID, coach.Name
	} else if mail.ReceiverID == 0 {
		return s.sendMailSendResult(protocol.MailSendFull, mail, nil) // no such coach
	}

	// Only cards the sender actually owns may be attached, and each is consumed.
	attach := s.takeCardsForMail(in.cards)

	stored, err := s.deps.Store.Mail.Send(mail, attach)
	if err != nil {
		if errors.Is(err, store.ErrMailboxFull) {
			s.restoreCardsFromFailedMail(attach)
			return s.sendMailSendResult(protocol.MailSendFull, mail, attach)
		}
		s.log.Warn("mail send", "coach", s.Coach.Name, "err", err)
		s.restoreCardsFromFailedMail(attach)
		return s.sendMailSendResult(-1, mail, attach)
	}
	if len(attach) > 0 {
		s.refreshAndPushInventory()
	}
	s.log.Info("mail sent", "from", s.Coach.Name, "to", stored.ReceiverName,
		"mail", stored.ID, "cards", len(attach))

	// Nudge the recipient if they are online.
	if online := s.deps.World.Get(stored.ReceiverID); online != nil && online.Session != nil {
		if notice, err := buildMailNewNotice(1); err == nil {
			_ = online.Session.Send(notice)
		}
	}
	return s.sendMailSendResult(int64(stored.ID), stored, attach)
}

// sendMailSendResult replies MAIL_SEND_RESULT (15003): [i64 result][mail record].
// result > 0 = accepted (the client files the echoed mail under "Sent"),
// MailSendFull = recipient's mailbox is full, anything else = generic failure.
func (s *Session) sendMailSendResult(result int64, m *domain.Mail, cards []int32) error {
	w := protocol.NewWriter().I64(result)
	writeMailRecord(w, m, cards)
	frame, err := protocol.EncodeS2C(protocol.OpMailSendResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// takeCardsForMail removes one unit of each requested card from the sender's
// inventory, skipping any it does not own or that is equipped/locked. Returns the
// ids actually consumed (capped to the client's own attachment limit).
func (s *Session) takeCardsForMail(want []int32) []int32 {
	if len(want) == 0 {
		return nil
	}
	if len(want) > domain.MailMaxAttachments {
		want = want[:domain.MailMaxAttachments]
	}
	db := s.deps.Store.DB()
	taken := make([]int32, 0, len(want))
	for _, id := range want {
		var card domain.CoachCard
		err := db.Where("coach_id = ? AND template_id = ? AND pos = 0 AND quantity > 0",
			s.Coach.ID, id).First(&card).Error
		if err != nil {
			continue // not owned / equipped — silently skip
		}
		// "On ne peut pas envoyer de kard liée par mail." The client gates this
		// on the TEMPLATE's tp() flag (ay.java), not on anything stored per
		// owned card — there is no per-instance flag in 2.70. Note it checks
		// only Bound, not Undestructible: an indestructible card may be posted,
		// it just cannot be destroyed or sold.
		if s.deps.cardIsBound(id) {
			continue
		}
		if card.Quantity > 1 {
			db.Model(&domain.CoachCard{}).Where("id = ?", card.ID).
				Update("quantity", card.Quantity-1)
		} else {
			db.Delete(&domain.CoachCard{}, card.ID)
		}
		taken = append(taken, id)
	}
	return taken
}

// restoreCardsFromFailedMail gives back cards consumed for a mail that was then
// rejected, so a full mailbox never destroys attachments.
func (s *Session) restoreCardsFromFailedMail(cards []int32) {
	if len(cards) == 0 {
		return
	}
	db := s.deps.Store.DB()
	for _, id := range cards {
		var card domain.CoachCard
		err := db.Where("coach_id = ? AND template_id = ? AND pos = 0", s.Coach.ID, id).
			First(&card).Error
		if err == nil {
			db.Model(&domain.CoachCard{}).Where("id = ?", card.ID).
				Update("quantity", card.Quantity+1)
			continue
		}
		db.Create(&domain.CoachCard{
			CoachID: s.Coach.ID, TemplateID: id, Quantity: 1,
		})
	}
	s.refreshAndPushInventory()
}
