package combat

import "github.com/dofusarena/go-server/internal/protocol"

// Broadcaster is the boundary between the Fight actor (this package, which
// must not depend on netio/dispatch/world) and the session layer that
// actually owns TCP connections. dispatch wires a concrete implementation
// at combat.Manager.Create time, mirroring the "timeline handles
// bookkeeping, Fight handles broadcast" layering note in
// docs/opcodes/08-fight-combat-engine.md §1.4 -- Fight decides *what* to
// send and *to whom* (by coach ID), Broadcaster decides *how* delivery
// actually happens.
type Broadcaster interface {
	// SendToCoach delivers frame to coachID if they're currently
	// reachable (online); a no-op (not an error) if not, mirroring
	// dispatch.sendToCoachID's existing tolerant behavior.
	SendToCoach(coachID uint, frame protocol.OutboundFrame)
}

// CoachIDs returns every distinct coach ID participating in this fight
// (across both teams' team-mates), used for broadcasting to everyone.
func (f *Fight) CoachIDs() []uint {
	seen := make(map[uint]bool)
	var out []uint
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			if !seen[mate.CoachID] {
				seen[mate.CoachID] = true
				out = append(out, mate.CoachID)
			}
		}
	}
	return out
}

// broadcastAll sends frame to every coach participating in the fight.
func (f *Fight) broadcastAll(frame protocol.OutboundFrame) {
	if f.broadcaster == nil {
		return
	}
	for _, coachID := range f.CoachIDs() {
		f.broadcaster.SendToCoach(coachID, frame)
	}
}
