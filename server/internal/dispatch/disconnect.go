package dispatch

import (
	"context"
	"time"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/netio"
)

// HandleDisconnect performs the full cleanup for a session that just closed:
// marks the account disconnected in the DB, cancels any pending
// matchmaking search / in-progress duel-setup / in-progress item exchange
// the coach was part of (notifying the other side where applicable),
// removes the coach from the online world registry, and broadcasts
// ACTOR_DESPAWN to every remaining online coach -- mirroring
// World.removeOnlineCoach's fan-out (World.java:37-41), plus robustness
// fixes over the legacy server: neither the legacy Java server nor this Go
// port originally cleaned up stale matchmaking/duel/exchange state on
// disconnect (see docs/08-java-parity-roadmap.md §8.3.1).
func HandleDisconnect(session *netio.Session, deps *Deps) {
	accountRef := session.Account()
	if accountRef == nil {
		return
	}

	if err := deps.Auth.SetDisconnected(context.Background(), accountRef.AccountID); err != nil {
		deps.Logger.Warn().Err(err).Uint("account_id", accountRef.AccountID).Msg("dispatch: failed to mark account disconnected")
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	// Accrue this session's elapsed wall-clock time onto the coach's
	// lifetime "Temps total de jeu" (TotalPlayTimeSecs). Best-effort: a
	// failure here must not abort the rest of disconnect cleanup.
	if loginAt, ok := sessionLoginAt(session); ok {
		secs := int64(time.Since(loginAt) / time.Second)
		if err := deps.Coach.AddPlayTime(context.Background(), coach.ID, secs); err != nil {
			deps.Logger.Warn().Err(err).Uint("coach_id", coach.ID).Msg("dispatch: failed to accrue play time on disconnect")
		}
	}

	// Cancel a pending matchmaking search, if the coach was queued waiting
	// for an opponent.
	deps.Matchmaking.CancelSearch(coach.ID)

	// Cancel any pending fight invitation the coach was part of (as
	// inviter or target), notifying the OTHER party so their client
	// dismisses its pending-invitation message box. FIGHT_INVITATION_
	// REJECTED (4304) is the message that removes the invitation
	// client-side (FightInvitationManager.removeInvitation), and is a
	// sensible "it fell through" signal to either party regardless of
	// which one dropped. Without this, a disconnected coach's invitation
	// would linger forever (an accept/reject would still be re-validated
	// against online status by the handlers above, but the surviving
	// client would keep showing a dead invitation box).
	for _, inv := range deps.Invitations.RemoveByCoach(coach.ID) {
		other := inv.OpponentOf(coach.ID)
		if oc, online := deps.World.Get(other); online {
			oc.Session.Send(buildFightInvitationRejected(inv.ID))
		}
	}

	// Cancel an in-progress duel-setup (fighter-selection or
	// placement-readiness phase), notifying the other coach if still
	// online. If a real combat.Fight has already been instantiated (past
	// TEAM_MATE_SET_READY_FOR_PLACEMENT, see docs/08-java-parity-roadmap.md
	// Phase B), forfeit it instead -- a disconnect mid-fight must not
	// leave the opponent stuck waiting forever for a turn that will never
	// come.
	//
	// Also unconditionally send an EndFightDone ack (docs/08-java-parity-
	// roadmap.md §8.11 item 9): if this coach disconnected AFTER
	// END_FIGHT was already sent but BEFORE their own
	// EndFightDoneMessage(4321) arrived, that ack would otherwise never
	// come, leaking the Fight actor/goroutine (and its Manager entry)
	// until process restart -- allEndFightDoneAcked() would never return
	// true for this fight. Sending both commands unconditionally is safe
	// regardless of the fight's actual phase: GiveUp is silently ignored
	// once the fight has already ended (handleCommand filters everything
	// but cmdEndFightDone once PhaseEnded), and EndFightDone is itself a
	// no-op if the fight hasn't ended yet (handleEndFightDone's own
	// `CurrentPhase() != PhaseEnded` guard) -- so this doesn't change
	// behavior for the normal mid-fight-disconnect-forfeits case at all,
	// it only closes the narrow post-END_FIGHT/pre-ack window.
	if duel, ok := deps.Duels.GetByCoach(coach.ID); ok {
		if fight, ok := duel.Fight(); ok {
			fight.Send(combat.NewGiveUp(coach.ID))
			fight.Send(combat.NewEndFightDone(coach.ID))
		} else {
			cancelDuel(duel, deps, CancelReasonTargetDisconnected)
		}
	}

	// Cancel an in-progress item exchange, notifying the other coach if
	// still online.
	if exchange, ok := deps.Exchanges.GetByCoach(coach.ID); ok {
		endExchangeWithError(deps, exchange)
	}

	deps.World.Remove(coach.ID)

	despawn := buildActorDespawn(coach.ID)
	for _, oc := range deps.World.Snapshot() {
		oc.Session.Send(despawn)
	}
}
