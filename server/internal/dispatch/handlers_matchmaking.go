package dispatch

import (
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterMatchmakingHandlers wires opponent search/cancel opcodes, see
// docs/02-protocol.md OPPONENT_SEARCH_REQUEST/CANCEL and
// OpponentSearchRequest.java/OpponentSearchCancel.java/WaitingOpponent.java.
func RegisterMatchmakingHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvOpponentSearchRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleOpponentSearchRequest(session, payload, deps)
	})
	r.Register(protocol.RecvOpponentSearchCancel, func(session *netio.Session, _ *protocol.Reader) {
		handleOpponentSearchCancel(session, deps)
	})
}

func handleOpponentSearchRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fightType := payload.Byte()
	bet := payload.Int32()
	if payload.Err() != nil {
		// Malformed/truncated request. Legacy Java dropped this silently;
		// we now signal the client explicitly (Go-only, see 2302 docs).
		session.Send(buildOpponentSearchError(protocol.OpponentSearchErrBadRequest))
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		// Session isn't bound to a coach yet (login/coach-selection flow
		// incomplete). Previously a silent return.
		session.Send(buildOpponentSearchError(protocol.OpponentSearchErrNoCoach))
		return
	}

	// The stock client only ever searches with fightType=1 (defy) and a
	// non-negative bet over the 2301 path. Reject anything else rather than
	// queueing a request that can never legitimately be matched.
	if fightType != protocol.FightTypeMatchmakingDefy || bet < 0 {
		session.Send(buildOpponentSearchError(protocol.OpponentSearchErrInvalidParams))
		return
	}

	session.Send(buildOpponentSearchInProgress())

	duel := deps.Matchmaking.SearchOpponent(coach.ID, fightType, bet)
	if duel == nil {
		// No match yet; coach is now queued, waiting for a future searcher.
		return
	}

	frame := buildOpponentFound(duel.ID, bet, fightType)
	session.Send(frame)
	opponentID := duel.OpponentOf(coach.ID)
	if opponentOnline, ok := deps.World.Get(opponentID); ok {
		opponentOnline.Session.Send(frame)
	}

	// Arm the SET_READY_FOR_FIGHT forced-progress timer: previously this
	// stage had no server-side timeout at all, matching the client's own
	// purely-cosmetic ready-countdown UI (see
	// docs/08-java-parity-roadmap.md's write-up on this fix).
	armMatchReadyTimeout(duel, deps)
}

func handleOpponentSearchCancel(session *netio.Session, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	deps.Matchmaking.CancelSearch(coach.ID)
	session.Send(buildOpponentSearchCancelResult())
}
