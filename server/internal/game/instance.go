package game

import (
	"github.com/StarLoco/arena-2.70/internal/handshake"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// sendEnterOverworld performs a complete overworld instance change. Every
// overworld entry (login, Zaap teleport, GM /TP and /WORLD, returning from a
// fight) must go through here — all three frames matter:
//
//  1. ENTER_INSTANCE (4600) renders the world and places the coach. `alt` MUST be
//     the arrival cell's walkable ground altitude (the tplg layer "wp"; see
//     zaapElement.alt) — the client seeds its pathfinder with the coach's cell +
//     altitude and requires a walkable layer at exactly that altitude, so a wrong
//     `alt` leaves the coach unable to walk.
//
//  2. INSTANCE_READY (4516, empty) clears the client's movement lock. A Zaap
//     request (4512) SETS that lock client-side (auv_0) and 4516 is the ONLY thing
//     that clears it — there is no timeout, so without it the coach can never walk
//     again for the rest of the session. It also fires the client's
//     walked-onto-element triggers.
//
//  3. INTERACTIVE_ELEMENT_SPAWN (200) re-spawns the destination world's elements
//     (Zaaps, Card Masters, Fusion altars, mailboxes, graveyards — see
//     elements.go): the client CLEARS its element manager on every 4600, so they
//     must be re-sent on each entry. Do NOT drive this from the client's 4517 ack
//     — the client only sends 4517 while achievement 456 / criterion 229 is unset,
//     and it sets 229 itself on the first world entry, so the ack stops arriving
//     after that.
//
// It also keeps Session.currentWorld in sync.
func (s *Session) sendEnterOverworld(x, y float32, alt, worldID int16) error {
	enter, err := handshake.EncodeEnterInstance(x, y, alt, worldID, false)
	if err != nil {
		return err
	}
	if err := s.Send(enter); err != nil {
		return err
	}
	// The client clears its element manager on EVERY ENTER_INSTANCE, not just when
	// the world changes — so our record of what it holds has to reset every time
	// too. Gating this on a world change looks right and is not: a same-world
	// teleport (GM /TP, and any Zaap that lands on the island you are already on)
	// then leaves us believing the elements are still spawned, and we never re-send
	// them, so the island loses every element until you cross to another world.
	s.resetSpawnedElements()
	s.currentWorld = worldID

	if ready, err := protocol.EncodeS2C(protocol.OpInstanceReady, nil); err == nil {
		if err := s.Send(ready); err != nil {
			return err
		}
	}
	s.refreshWorldElements(worldID, int32(x), int32(y))
	return nil
}
