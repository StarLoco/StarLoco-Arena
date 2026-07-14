package main

import (
	"fmt"
	"time"

	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// exchange.go implements the two sides of a bot-vs-bot card trade. The
// paired bots agree (via the pairBroker) on who initiates; the initiator
// invites the partner by coach id, the partner accepts, both offer one
// inventory card and set ready, completing the trade.
//
// Robustness: a trade can legitimately fail (e.g. the partner just entered a
// fight, or has no tradeable card) -- those surface as classified failures
// in the report rather than aborting the bot.

const exchangeStepTimeout = 12 * time.Second

// exchangeInviteTimeout is the acceptor's patience for the initiator's
// invitation to arrive. Longer than a normal step because the two paired
// bots reach the rendezvous at slightly different times and the initiator's
// INVITATION_REQUEST has to round-trip through the server.
const exchangeInviteTimeout = 18 * time.Second

// playExchange runs one full card exchange for this bot. match.Initiator
// decides the role.
func (s *swarm) playExchange(c *botclient.Client, id *botIdentity, match pairMatch) error {
	if match.Initiator {
		return s.runExchangeInitiator(c, id, match)
	}
	return s.runExchangeAcceptor(c, id, match)
}

// runExchangeInitiator invites the partner, waits for the accepted
// confirmation, offers a card, sets ready.
func (s *swarm) runExchangeInitiator(c *botclient.Client, id *botIdentity, match pairMatch) error {
	if err := c.ExchangeInvite(match.PartnerID); err != nil {
		return fmt.Errorf("invite: %w", err)
	}
	// Our own pending confirmation (result=0 pending). Consume it.
	if _, err := c.Expect(protocol.SendItemExchangeInvitationConfirmation, exchangeStepTimeout); err != nil {
		return fmt.Errorf("invite-confirm: %w", err)
	}
	// Wait for the accepted/refused confirmation once the partner answers.
	conf, err := c.Expect(protocol.SendItemExchangeInvitationConfirmation, exchangeStepTimeout)
	if err != nil {
		return fmt.Errorf("answer-confirm: %w", err)
	}
	r := protocol.NewReader(conf)
	result := r.Byte()
	exchangeID := r.Int64()
	if result != exchangeAccepted {
		return fmt.Errorf("declined: result=%d", result)
	}
	return s.offerCardAndReady(c, id, exchangeID)
}

// runExchangeAcceptor waits for the invitation, accepts, offers a card, sets
// ready.
func (s *swarm) runExchangeAcceptor(c *botclient.Client, id *botIdentity, match pairMatch) error {
	inv, err := c.Expect(protocol.SendItemExchangeInvitationRequest, exchangeInviteTimeout)
	if err != nil {
		return fmt.Errorf("await-invite: %w", err)
	}
	exchangeID := protocol.NewReader(inv).Int64()
	if err := c.ExchangeAnswer(exchangeID, true); err != nil {
		return fmt.Errorf("answer: %w", err)
	}
	// Consume the accepted confirmation.
	if _, err := c.Expect(protocol.SendItemExchangeInvitationConfirmation, exchangeStepTimeout); err != nil {
		return fmt.Errorf("accept-confirm: %w", err)
	}
	return s.offerCardAndReady(c, id, exchangeID)
}

// offerCardAndReady adds one inventory card to the offer (if the bot owns
// one) and sets ready. Both parties must set ready for the trade to
// complete; we wait for either the completion (END) or a card-added echo to
// confirm the offer registered.
func (s *swarm) offerCardAndReady(c *botclient.Client, id *botIdentity, exchangeID int64) error {
	if len(id.InventoryCardUIDs) > 0 {
		uid := id.InventoryCardUIDs[0]
		if err := c.ExchangeAddCard(exchangeID, uid, 1); err != nil {
			return fmt.Errorf("add-card: %w", err)
		}
		// The server echoes CARD_ADDED to both parties. If the partner
		// already completed/canceled the trade (END arrives first), that's
		// still a successful, non-erroring exchange lifecycle.
		f, err := waitForEither(c, protocol.SendItemExchangeCardAdded, protocol.SendItemExchangeEnd, exchangeStepTimeout)
		if err != nil {
			return fmt.Errorf("card-added: %w", err)
		}
		if f == protocol.SendItemExchangeEnd {
			return nil
		}
	}
	if err := c.ExchangeSetReady(exchangeID); err != nil {
		return fmt.Errorf("set-ready: %w", err)
	}
	// Wait (best-effort) for the trade to end. If the partner never sets
	// ready within the window, cancel so neither side is left hanging.
	if _, err := c.DrainUntil(protocol.SendItemExchangeEnd, 12, exchangeStepTimeout); err != nil {
		_ = c.ExchangeCancel(exchangeID)
	}
	return nil
}

// waitForEither returns whichever of two opcodes arrives first (skipping
// broadcast noise), or an error on timeout/close.
func waitForEither(c *botclient.Client, a, b protocol.SendOpcode, timeout time.Duration) (protocol.SendOpcode, error) {
	deadline := time.Now().Add(timeout)
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return 0, err
		}
		if f.Opcode == a || f.Opcode == b {
			return f.Opcode, nil
		}
		// ignore everything else (broadcast noise, other exchange frames)
	}
	return 0, fmt.Errorf("timeout waiting for %s/%s", a.Name(), b.Name())
}

// exchange confirmation result codes (buildItemExchangeInvitationConfirmation).
const (
	exchangePending  = 0
	exchangeRefused  = 2
	exchangeAccepted = 3
)

var _ = exchangePending
var _ = exchangeRefused
