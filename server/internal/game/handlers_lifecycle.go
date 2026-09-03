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
	// SECURITY: only ids the CLIENT's own criterion enum defines may be written.
	//
	// The server keeps its own bookkeeping in this same table, namespaced above
	// MaxCriterionID - statChallengeDoneBase (2000) + challengeID marks a PvE
	// challenge as already cleared, which is what stops its reward cards being
	// paid twice. That namespacing was documented as making collision impossible,
	// but it only stops the server from ECHOING those ids in buildCriteriaBlob; it
	// never stopped the client from WRITING them. su.StatID is a full int16 and
	// UpsertStat overwrites rather than maxes, so:
	//
	//   1. beat a challenge with reward cards -> paid once, flag set
	//   2. send 22003 with statID = 2000+challengeID, value = 0 -> flag cleared
	//   3. re-run the challenge -> paid again. Loop.
	//
	// Unbounded card creation, fully client-driven, no race needed. Rejecting the
	// server's namespace here is the fix; the client never sends those ids, so no
	// legitimate flow is affected.
	if su.StatID <= 0 || uint16(su.StatID) > handshake.MaxCriterionID {
		s.log.Warn("rejected out-of-range statistic update",
			"coach", s.Coach.ID, "stat", su.StatID, "value", su.Value)
		return nil
	}
	if err := s.deps.Store.Coaches.UpsertStat(s.Coach.ID, su.StatID, int32(su.Value)); err != nil {
		s.log.Warn("persist statistic update", "coach", s.Coach.Name,
			"stat", su.StatID, "err", err)
		return nil
	}
	s.log.Debug("statistic update", "coach", s.Coach.Name,
		"stat", su.StatID, "value", su.Value, "flag", su.Flag)
	// A criterion moved, so an achievement may have completed.
	s.evaluateAchievements()
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

	// SECURITY: release every subsystem BEFORE nilling s.Coach.
	//
	// This handler used to clean only the world registry, then set s.Coach = nil.
	// Everything else - matchmaker queue, pending match, challenge, exchange, 2v2
	// pairing - kept holding this session, and because onClose gates its own
	// cleanup on `s.Coach != nil`, disconnecting afterwards released none of it
	// either. The queue entry in particular became a landmine that panicked the
	// next player to search (the managers now tolerate nil coaches, but leaving
	// the entries behind would still pair honest players with a destroyed coach).
	//
	// Order matters: releaseSubsystems reads s.Coach, so it cannot run after the
	// nil assignment below.
	s.releaseSubsystems()

	// A destroyed coach must not leave a Fight holding a *domain.Coach whose rows
	// are about to be deleted: the fight would keep running SaveProgress /
	// SaveConditions on deleted fighters and Coaches.Save on a deleted coach.
	// Unlike a disconnect there is nothing to reconnect to, so the fight is
	// released rather than given a grace period.
	if s.deps.Fights != nil {
		if f := s.deps.Fights.ByCoach(coach.ID); f != nil {
			s.deps.coachLeftFight(f, coach.ID)
		}
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
