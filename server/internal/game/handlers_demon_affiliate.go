package game

import (
	"errors"

	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// Demon affiliation (5470 -> 5403).
//
// Not the button it looks like. The `%affiliate%` control opens a card-offering
// TRADE (`demonAffiliationDialog`, with a split-quantity sub-dialog) and sends a
// basket of cards; the demon's favour is bought, not requested. That is why this
// is the mechanism behind clan islands: the strongest servant of each demon holds
// its island, and "strongest" means "gave the most".
//
// The client gates the control on `guildCanAffiliate = (myRank == 1 && demonId ==
// 0)` (`pq_1.java:56`) - leader only, and only while the clan serves nobody.

// demonAffiliateOK is the result byte `au_1` treats as success (`mj_1.rr() == 0`);
// anything else shows the generic exchange error.
const demonAffiliateOK uint8 = 0
const demonAffiliateFailed uint8 = 1

func registerDemonAffiliationHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpDemonAffiliate, handleDemonAffiliate)
}

// handleDemonAffiliate (5470, arch 3):
// [i16 demonId][i16 count] count x {[i32 cardId][i16 qty]}.
func handleDemonAffiliate(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil || s.deps.Store == nil || s.deps.Store.Guilds == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	demonID, err := r.U16()
	if err != nil {
		return err
	}
	count, err := r.U16()
	if err != nil {
		return err
	}
	type offer struct {
		card int32
		qty  int16
	}
	// SECURITY: do not pre-size from a wire count.
	//
	// count is a u16 and this ran BEFORE the guild-leader authorization check, so
	// any logged-in account could turn a 9-byte frame into a ~512 KB allocation
	// (~58,000x amplification) and repeat it for GC pressure. The loop below is
	// bounded by the reader anyway, so growing the slice naturally costs nothing.
	offers := make([]offer, 0, 16)
	for i := 0; i < int(count); i++ {
		card, err := r.I32()
		if err != nil {
			return err
		}
		qty, err := r.U16()
		if err != nil {
			return err
		}
		if qty > 0 {
			offers = append(offers, offer{card, int16(qty)})
		}
	}

	guildID, rankLevel, _, ok := s.guildActor()
	if !ok || rankLevel != store.GuildRankLeader {
		return s.sendDemonAffiliateResult(demonAffiliateFailed)
	}
	if len(offers) == 0 {
		return s.sendDemonAffiliateResult(demonAffiliateFailed)
	}

	// Affiliate on the first offering. A clan that already serves this demon may
	// keep giving; one that serves ANOTHER is refused, matching the client, which
	// only shows the control while demonId == 0.
	g, err := s.deps.Store.Guilds.ByID(guildID)
	if err != nil || g == nil {
		return s.sendDemonAffiliateResult(demonAffiliateFailed)
	}
	if g.DemonID == 0 {
		if err := s.deps.Store.Guilds.SetDemon(guildID, int16(demonID)); err != nil {
			if errors.Is(err, store.ErrGuildDemonInvalid) || errors.Is(err, store.ErrGuildAlreadyAffiliated) {
				return s.sendDemonAffiliateResult(demonAffiliateFailed)
			}
			return err
		}
	} else if g.DemonID != int16(demonID) {
		return s.sendDemonAffiliateResult(demonAffiliateFailed)
	}

	// Take the cards BEFORE crediting: a coach that does not actually hold what
	// it offered must buy no favour at all. consumeCard removes one instance, so
	// a quantity is that many removals - and a partial basket is still counted
	// for what was genuinely given rather than rolled back, because the cards are
	// already gone from the client's own view by the time it sends this.
	var points int64
	for _, o := range offers {
		for n := int16(0); n < o.qty; n++ {
			if !s.consumeCard(o.card) {
				break
			}
			points += demonCardValue(s, o.card)
		}
	}
	if points == 0 {
		return s.sendDemonAffiliateResult(demonAffiliateFailed)
	}
	total, err := s.deps.Store.Guilds.AddDemonReputation(guildID, int16(demonID), points)
	if err != nil {
		return err
	}
	s.log.Info("demon affiliation", "guild", guildID, "demon", demonID,
		"gave", points, "total", total, "by", s.Coach.Name)
	return s.sendDemonAffiliateResult(demonAffiliateOK)
}

// demonCardValue is what one card is worth to a demon.
//
// The card's own VALUE is used where the data has one, so offering a rare card
// counts for more than a common one; otherwise a card is worth 1. Nothing in the
// client fixes this rate - it only ever renders a reputation total - so it is the
// server's rule, stated here rather than buried.
func demonCardValue(s *Session, templateID int32) int64 {
	if s.deps != nil && s.deps.Cards != nil {
		if c := s.deps.Cards.Get(templateID); c != nil && c.Value > 0 {
			return int64(c.Value)
		}
	}
	return 1
}

// sendDemonAffiliateResult replies 5403: [u8 result][u8 n] with no detail
// entries. `au_1` reads the result byte, then the count, and only loops if it is
// non-zero - so an empty list is well-formed and keeps the message minimal.
func (s *Session) sendDemonAffiliateResult(result uint8) error {
	w := protocol.NewWriter().U8(result).U8(0)
	frame, err := protocol.EncodeS2C(protocol.OpDemonAffiliateResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}
