package game

import (
	"github.com/StarLoco/arena-2.70/internal/handshake"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerMovementHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpCoachMovementRequest, handleMovement)
}

// maxOverworldJump caps how far one 4501 may displace a coach, in cells.
//
// Generous on purpose - comfortably beyond the default AoI radius of 75 - because
// it exists to remove "teleport anywhere in one packet", not to police pathing,
// and a false refusal would be worse than the hole it closes.
//
// It is not merely cosmetic: handleFusionRequest gates on proximity to an altar
// (handlers_fusion.go), so an unbounded jump is a real bypass of a real check.
// See handleMovement for why a displacement cap rather than path validation.
const maxOverworldJump int32 = 100

// handleMovement records a CoachActorMovementRequest(4501) and broadcasts it to
// OTHER overworld coaches as ActorMovement(4500) so they see the walk. Movement
// is otherwise unvalidated for now.
//
// The mover is deliberately NOT sent its own 4500. Overworld movement is
// client-authoritative: the client starts the walk locally the moment it finds a
// path, before it even sends 4501. Its 4500 handler does not special-case the
// local coach — it recomputes a path from the actor's CURRENT (mid-walk) position
// to the last cell and restarts the animation, or hard-teleports the actor if that
// recompute fails. Echoing the move back therefore made walking stutter and snap.
func handleMovement(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	steps, err := handshake.DecodeCoachMovementRequest(f.Payload)
	if err != nil {
		return err
	}
	if len(steps) == 0 {
		return nil
	}

	move, err := handshake.EncodeActorMovement(int64(s.Coach.ID), steps)
	if err != nil {
		return err
	}

	// Update position + compute the area-of-interest diff (who enters/leaves
	// view) in one pass, then send the exact spawn/despawn/move frames.
	last := steps[len(steps)-1]

	// SECURITY: bound the DISPLACEMENT, not the path.
	//
	// This handler took the last step verbatim - no adjacency, walkability or
	// speed check - so a single 4501 carrying one arbitrary cell teleported a
	// coach anywhere in the overworld, including into position-gated areas, and
	// let AoI membership be manipulated at will.
	//
	// Full path validation is deliberately NOT the fix: overworld movement is
	// client-authoritative by design (see the comment above - the client starts
	// walking locally before it sends 4501, and echoing corrections makes it
	// stutter). Rejecting paths the client already committed to would break normal
	// play to close a griefing hole.
	//
	// A displacement cap keeps that design and still removes the primitive: a real
	// walk moves a bounded distance per request, an arbitrary jump does not. The
	// bound is deliberately generous so it cannot bite a legitimate long walk, and
	// it logs when it trips so an operator can tell if it ever does. Fight
	// movement is separately and fully validated (validateFightMove).
	if cur := s.deps.World.Get(s.Coach.ID); cur != nil && cur.Coach != nil {
		if dx, dy := last.X-cur.Coach.PosX, last.Y-cur.Coach.PosY; dx*dx+dy*dy > maxOverworldJump*maxOverworldJump {
			s.log.Warn("overworld move refused: implausible displacement",
				"coach", s.Coach.ID, "from_x", cur.Coach.PosX, "from_y", cur.Coach.PosY,
				"to_x", last.X, "to_y", last.Y)
			return nil
		}
	}
	// Walking stands the coach up. A coach that walks off while still flagged
	// sitting would slide across the ground in a sitting pose on every client
	// that has it spawned. Done BEFORE the move so the 4601 reaches the viewers
	// that can still see it.
	s.standIfSitting()

	d := s.deps.World.ApplyMove(s.Coach.ID, last.X, last.Y, last.Z)

	// Coaches that entered the mover's view: spawn them to the mover.
	if len(d.SpawnToMover) > 0 {
		if frame, err := buildActorSpawn(d.SpawnToMover); err == nil {
			_ = s.Send(frame)
		}
	}
	// Coaches that left the mover's view: despawn from the mover.
	if len(d.DespawnToMover) > 0 {
		if frame, err := buildActorDespawn(d.DespawnToMover); err == nil {
			_ = s.Send(frame)
		}
	}
	// Sessions that just gained sight of the mover: spawn the mover to them
	// (BEFORE the move, so the client knows the actor when the move arrives).
	if len(d.SpawnMoverTo) > 0 {
		if frame, err := buildActorSpawn([]CoachView{d.MoverView}); err == nil {
			for _, other := range d.SpawnMoverTo {
				_ = other.Send(frame)
			}
		}
	}
	// Sessions that already see the mover: send the walk animation.
	for _, other := range d.MoveViewers {
		_ = other.Send(move)
	}
	// Sessions that lost sight of the mover: despawn the mover from them.
	if len(d.DespawnMoverFrom) > 0 {
		if frame, err := buildActorDespawn([]uint{s.Coach.ID}); err == nil {
			for _, other := range d.DespawnMoverFrom {
				_ = other.Send(frame)
			}
		}
	}
	// Interactive elements have their own area of interest, on the client's env
	// CHUNK grid rather than the actor sight radius: the client can only resolve an
	// element whose chunk is streamed in. Walking is what brings them into range,
	// so this has to run on every move, not just on world entry.
	s.refreshWorldElements(s.currentWorld, last.X, last.Y)
	return nil
}
