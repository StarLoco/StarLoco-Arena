package game

import (
	"github.com/StarLoco/arena-2.70/internal/handshake"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerLifecycleHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpDisconnect, handleDisconnect)
	r.Register(protocol.OpTutorialChangeInstance, handleTutorialChangeInstance)
	r.Register(protocol.OpStatisticUpdate, handleStatisticUpdate)
	r.Register(protocol.OpStatisticRequest, handleStatisticRequest)
	r.Register(protocol.OpDestroyCoach, handleDestroyCoach)
}

// handleDisconnect handles the client's graceful DisconnectionNotification
// (opcode 1), sent right before it closes the socket. We tear the session down
// ourselves so the reader/writer stop promptly; onClose does the rest.
func handleDisconnect(s *Session, _ *protocol.C2SFrame) error {
	s.kick()
	return nil
}

// handleTutorialChangeInstance handles opcode 4517 (client `aae_2`), an empty
// "instance/tutorial ready" acknowledgement. The client expects no response;
// accepting it silently is correct behaviour.
//
// Do NOT hang world-entry work off this: the client only emits 4517 while
// achievement 456 (criterion 229) is unset, and it sets criterion 229 itself on
// the first world entry — so the ack stops arriving for good after that. Interactive
// elements are spawned unconditionally after each ENTER_INSTANCE instead
// (see Session.sendEnterOverworld).
func handleTutorialChangeInstance(_ *Session, _ *protocol.C2SFrame) error {
	return nil
}

// handleStatisticUpdate handles opcode 22003 (client `nq`): the client reporting
// progress on a keyed achievement/statistic counter. We persist it against the
// coach; the client expects no response.
func handleStatisticUpdate(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	su, err := handshake.DecodeStatisticUpdate(f.Payload)
	if err != nil {
		return err
	}
	if err := s.deps.Store.Coaches.UpsertStat(s.Coach.ID, su.StatID, int32(su.Value)); err != nil {
		s.log.Warn("persist statistic update", "coach", s.Coach.Name,
			"stat", su.StatID, "err", err)
		return nil
	}
	s.log.Debug("statistic update", "coach", s.Coach.Name,
		"stat", su.StatID, "value", su.Value, "flag", su.Flag)
	return nil
}

// handleStatisticRequest handles opcode 22001 (client `anp_0`, empty): the client
// asking for its criteria, sent when the achievement tab is opened.
//
// The reply is what OPENS the tab. Client-side, clicking the achievements button
// only registers handler A and sends this; A's 22002 case is what actually pops
// "achievementDialog". Leaving 22001 unanswered therefore makes the button inert
// - which is exactly how the tab behaved before this handler existed.
//
// Replying is safe here specifically because the request implies A is registered.
// The client dispatches newest-handler-first and stops at the first handler that
// consumes (fh_2: qe.add(0,...), break when a.a() returns false), so A takes the
// frame and the permanently-registered tutorial handler asA - which would pop the
// tutorial-guide dialog - never sees it. That is why 22002 must never be sent
// spontaneously; see protocol.OpStatisticData.
func handleStatisticRequest(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	// Reload so the tab reflects criteria earned since login (the in-memory coach
	// is not the authority for these - postfight/challenge code writes them
	// straight through the store).
	coach, err := s.deps.Store.Coaches.Get(s.Coach.ID)
	if err != nil || coach == nil {
		// Fall back to the session's copy rather than leaving the tab shut.
		coach = s.Coach
	}
	frame, err := protocol.EncodeS2C(protocol.OpStatisticData,
		handshake.EncodeStatisticData(coachCriteria(coach)))
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// handleDestroyCoach handles opcode 27529 (client `bl`): the "Détruire le coach"
// action. The client sends this, then immediately disconnects itself. We remove
// the coach and all of its data, and detach it from the in-memory session so the
// onClose teardown does not try to re-save a now-deleted coach. No frame is sent
// back (the client tears itself down).
func handleDestroyCoach(s *Session, _ *protocol.C2SFrame) error {
	coach := s.Coach
	if coach == nil {
		return nil
	}

	// Despawn from everyone who currently sees the coach, then drop it from the
	// world registry so no broadcast targets a dead actor.
	viewers := s.deps.World.LeaveAoI(coach.ID)
	s.deps.World.Remove(coach.ID)
	if frame, err := buildActorDespawn([]uint{coach.ID}); err == nil {
		for _, other := range viewers {
			_ = other.Send(frame)
		}
	}
	// Tell watching friends/ignorers the coach went offline.
	s.notifyPresence(coach, false)

	// Delete the coach and every associated row.
	if err := s.deps.Store.Coaches.DeleteCoach(coach.ID); err != nil {
		s.log.Warn("destroy coach", "coach", coach.Name, "err", err)
		return nil
	}
	s.log.Info("coach destroyed", "coach", coach.Name, "id", coach.ID)

	// Detach in-memory state so onClose skips coach persistence/despawn (already
	// done) and the account is treated as coach-less.
	s.Coach = nil
	if s.Account != nil {
		s.Account.CoachID = nil
		s.Account.Coach = nil
	}
	return nil
}
