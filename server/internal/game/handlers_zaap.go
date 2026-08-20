package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerZaapHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpZaapTeleport, handleZaapTeleport)
}

// handleZaapTeleport handles ZAAP_USE (opcode 4512, client Gs): [i32 cardTemplateId].
// The client sends the double-clicked Zaap card; the server validates ownership,
// maps the card to a destination world and teleports the coach there via
// ENTER_INSTANCE (4600). The destination island's elements (including its own
// Zaap) are re-spawned unconditionally as part of that entry — see
// Session.sendEnterOverworld. NOT off the client's 4517 ack, which stops arriving
// once criterion 229 is set.
func handleZaapTeleport(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	cardID, err := r.I32()
	if err != nil {
		return err
	}
	dest, ok := zaapCardDest[cardID]
	if !ok && cardID == clanIslandZaapCard {
		// The one Zaap card with no fixed destination: it goes to the coach's
		// OWN clan island, allotted on first use from the 24 the map data ships.
		dest, ok = s.deps.clanIslandDest(s.Coach.ID)
		if !ok {
			s.log.Debug("zaap use: no clan island for this coach", "coach", s.Coach.Name)
			return nil
		}
	}
	if !ok {
		s.log.Debug("zaap use: not a known zaap card", "card", cardID)
		return nil
	}
	if !s.coachOwnsCard(cardID) {
		// The client already enforces ownership before sending 4512; a request
		// for an unowned card is forged/stale — ignore it.
		s.log.Debug("zaap use: card not owned", "card", cardID, "coach", s.Coach.Name)
		return nil
	}
	z, ok := zaapAt(dest.world, dest.instanceID)
	if !ok {
		s.log.Warn("zaap use: destination zaap missing", "card", cardID,
			"world", dest.world, "instance", dest.instanceID)
		return nil
	}

	// Teleport: place the coach on the destination island at that card's Zaap so
	// it arrives right at a teleporter it can travel onward from. The altitude
	// MUST be the cell's ground wp (z.alt) or the coach can't move (see
	// zaapElement.alt). Keep the server position and world registry in sync.
	s.Coach.PosX, s.Coach.PosY, s.Coach.PosZ = z.cellX, z.cellY, z.alt
	s.deps.World.UpdatePosition(s.Coach.ID, z.cellX, z.cellY, z.alt)
	_ = s.deps.Store.Coaches.Save(s.Coach)

	if err := s.sendEnterOverworld(float32(z.cellX), float32(z.cellY), z.alt, dest.world); err != nil {
		return err
	}
	s.log.Info("zaap teleport", "coach", s.Coach.Name, "card", cardID, "world", dest.world,
		"zaap", dest.instanceID, "cell", []int32{z.cellX, z.cellY}, "alt", z.alt)
	return nil
}

// coachOwnsCard reports whether the coach's inventory holds the given card
// template (any positive quantity).
func (s *Session) coachOwnsCard(templateID int32) bool {
	for _, c := range s.Coach.Inventory {
		if c.TemplateID == templateID && c.Quantity > 0 {
			return true
		}
	}
	return false
}

// grantZaapCards ensures the coach owns every starter Zaap card so the Zaap
// network is usable. Idempotent: only inserts cards the coach lacks, so it also
// back-fills existing coaches on login.
func (s *Session) grantZaapCards(coach *domain.Coach) {
	owned := make(map[int32]bool, len(coach.Inventory))
	for _, c := range coach.Inventory {
		owned[c.TemplateID] = true
	}
	db := s.deps.Store.DB()
	added := 0
	for _, id := range starterZaapCards {
		if owned[id] {
			continue
		}
		card := domain.CoachCard{CoachID: coach.ID, TemplateID: id, Quantity: 1}
		db.Create(&card)
		coach.Inventory = append(coach.Inventory, card)
		added++
	}
	if added > 0 {
		s.log.Info("granted zaap cards", "count", added, "coach", coach.Name)
	}
}
