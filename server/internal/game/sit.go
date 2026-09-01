package game

import "github.com/StarLoco/arena-2.70/internal/protocol"

// Sitting (4601).
//
// There is no client-side sit: `/sit` is forwarded to the server like any other
// slash-command (verified live - the retail client replies nothing locally and
// our own handler answered "unknown command: SIT"). The client can only ever
// show a coach sitting because the SERVER said so, two ways:
//
//   - the actor blob's dBg byte, read on spawn (`no_2.g` -> AnimAssis-Debut), and
//   - 4601, which toggles it in bulk for coaches already on screen.
//
// Both are needed: the blob covers "was already sitting when you arrived", 4601
// covers "sat down while you watched".

// buildSitStand builds SitStand (4601): [i16 n]{i64 sitting} then
// [i16 n]{i64 standing}. The client plays AnimAssis-Debut for the first list and
// AnimAssis-Fin for the second, nudging each actor's facing as it goes.
func buildSitStand(sitting, standing []int64) ([]byte, error) {
	w := protocol.NewWriter()
	w.U16(uint16(len(sitting)))
	for _, id := range sitting {
		w.I64(id)
	}
	w.U16(uint16(len(standing)))
	for _, id := range standing {
		w.I64(id)
	}
	return protocol.EncodeS2C(protocol.OpSitStand, w.Bytes())
}

// setSitting flips a coach's sit state and tells everyone who can see it,
// including the coach itself - the client does not animate its own sit locally
// any more than it does its own emote.
//
// Addressed by AoI membership (ViewersOf) rather than proximity: 4601 makes the
// client resolve each id against its spawned actors, and an id it has not
// spawned is the case that NPEs a retail handler (B-136).
func (s *Session) setSitting(sitting bool) error {
	if s.Coach == nil {
		return nil
	}
	if !s.deps.World.SetSitting(s.Coach.ID, sitting) {
		return nil // already in that state - do not broadcast a no-op
	}
	id := int64(s.Coach.ID)
	var frame []byte
	var err error
	if sitting {
		frame, err = buildSitStand([]int64{id}, nil)
	} else {
		frame, err = buildSitStand(nil, []int64{id})
	}
	if err != nil {
		return err
	}
	n := 0
	for _, other := range s.deps.World.ViewersOf(s.Coach.ID) {
		if other.Send(frame) == nil {
			n++
		}
	}
	_ = s.Send(frame)
	// Logged with the audience size because this is otherwise unobservable: the
	// only way to tell "broadcast correctly" from "broadcast to nobody" is to
	// stand a second client next to the first and look, and player sprites do not
	// render in the headless test client at all (animations.jar cannot load).
	s.log.Debug("sit state changed", "coach", s.Coach.Name, "sitting", sitting, "viewers", n)
	return nil
}

// standIfSitting clears the sit state on movement. Called from the walk handler:
// a coach that walks away while still rendered sitting would slide across the
// ground in a sitting pose.
func (s *Session) standIfSitting() {
	if s.Coach == nil || !s.deps.World.IsSitting(s.Coach.ID) {
		return
	}
	_ = s.setSitting(false)
}
