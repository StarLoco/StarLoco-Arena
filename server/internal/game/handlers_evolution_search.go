package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// registerEvolutionSearchHandlers wires the EVOLUTION tab's opponent search —
// the "Combattre" button of the evolution team panel. Until this existed the
// server never answered 23003 and the client waited on a silent screen, so an
// evolution fight could not be started from the retail client at all.
func registerEvolutionSearchHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpEvolutionSearchRequest, handleEvolutionSearch)
	r.Register(protocol.OpEvolutionSearchCancel, handleEvolutionSearchCancel)
}

// evolutionTeamPreset is the i16 the client sends in 23003/23001. It is NOT a
// database team id: `sw_1.bMm = 99` is a synthetic pseudo-preset for "the
// evolution team", a peer of graveyard (10000) and legend (9999), and the object
// carrying it (`xz_0`, bound to the Lua property "evolutionTeam") sets it in its
// own constructor. All four client call sites inline the literal; the tournament
// path (`en_2`) sends `xz_0.amc().tI()`, which is the same 99.
//
// So it must be mapped to the coach's TITULAR line-up rather than looked up in
// the teams table, where preset 99 would either miss or — worse — collide with
// some unrelated coach's real team.
const evolutionTeamPreset uint16 = 99

// evolutionTeamSlots is how many fighters the evolution panel fields (six slots,
// "Recruter" for the empty ones).
const evolutionTeamSlots = 6

// modeEvolutionSearch tags evolution searchers in the shared matchmaker queue so
// they only ever pair with each other — never with a 2301 quick-search or a
// 23103 classic ready-up. Same trick, and same reasoning, as modeClassicReady.
const modeEvolutionSearch int16 = 23003

// Error codes for OpEvolutionSearchError (23008 KL: [i8 code]). Taken from the
// client's own branch table in wp_0:
//
//	1 matchfinder.impossibleToStartOpponentsSearch   message only
//	2 matchfinder.badTeam                            message only
//	3 matchfinder.canceledByCoach                    message + tears down
//	4 matchfinder.opponentNotFound                   message + tears down
//	5 (silent)                                       tears down
//
// Codes 1 and 2 show their message but LEAVE THE OVERLAY UP, so they are only
// safe before an accepted 23004 (i.e. when no overlay exists yet). Anything that
// fails after the search has been accepted must use 3, 4 or 5.
const (
	evoSearchErrCannotStart uint8 = 1
	evoSearchErrBadTeam     uint8 = 2
	evoSearchErrCancelled   uint8 = 3
	evoSearchErrNoOpponent  uint8 = 4
)

// handleEvolutionSearch (23003 ajw_0: [i64 coachId][i16 preset=99]) enters the
// coach into the evolution opponent queue and pairs it with the next coach that
// does the same.
//
// Ordering matters and is not ours to choose: the client opens its "Searching…"
// overlay on the accepted 23004 and closes it only on 23006/23002/23008(3,4,5),
// so both frames must reach it before CREATE_FIGHT or the fight runs underneath
// the overlay. The client has already pushed its own fight frame by this point,
// so 8000 is routable the moment we send it.
//
// That ordering is in fact structural rather than delicate: CREATE_FIGHT is
// emitted from the fight goroutine (`startFightWithTeams` → `f.Post`), so
// anything sent synchronously here necessarily precedes it. Worth knowing before
// "tidying" these sends into the fight actor, which WOULD break it — and which
// is the failure TestEvolutionSearchPairsAndStartsFight actually catches.
func handleEvolutionSearch(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	if _, err := r.I64(); err != nil { // team-owner coach id (trust the session)
		return err
	}
	preset, err := r.U16()
	if err != nil {
		return err
	}
	// Refuse anything that is not the evolution pseudo-preset rather than
	// guessing: the classic/tournament tabs have their own opcodes, and a real
	// team id arriving here would mean we misread the message.
	if preset != evolutionTeamPreset {
		s.log.Warn("evolution search: unexpected preset", "coach", s.Coach.Name, "preset", preset)
		return s.sendEvolutionSearchError(evoSearchErrCannotStart)
	}
	if s.deps.Fights.ByCoach(s.Coach.ID) != nil {
		return s.sendEvolutionSearchError(evoSearchErrCannotStart)
	}
	roster := s.deps.titularRoster(s.Coach.ID, evolutionTeamSlots)
	if len(roster) == 0 {
		return s.sendEvolutionSearchError(evoSearchErrBadTeam)
	}

	// Re-sending 23003 while already queued is possible (the end-of-fight path
	// in WE fires it unprompted), so drop any stale queue entry first rather
	// than letting the same coach sit in the queue twice and pair with itself.
	s.deps.Matchmaker.CancelSearch(s.Coach.ID)

	if err := s.sendEvolutionSearchResult(preset, true); err != nil {
		return err
	}
	pm := s.deps.Matchmaker.Search(s, modeEvolutionSearch, 0, roster)
	if pm == nil {
		s.log.Info("evolution search: waiting for opponent",
			"coach", s.Coach.Name, "fighters", len(roster))
		return nil
	}
	// Paired. Like the classic ready-up, the evolution search has no
	// accept prompt — the fight starts at once — so the pending match is
	// discarded rather than left for a 23114 that will never come.
	s.deps.Matchmaker.Discard(pm)
	s.log.Info("evolution search: paired -> starting fight",
		"a", pm.a.session.Coach.Name, "b", pm.b.session.Coach.Name)
	for _, sr := range []*searcher{pm.a, pm.b} {
		if sr.session != nil {
			_ = sr.session.sendEvolutionFightStarting()
		}
	}
	return s.deps.startEvolutionFight(pm)
}

// handleEvolutionSearchCancel (23001 abn_0: [i64 coachId][i16 preset]) is the
// overlay's Cancel button (Lua dofusarena.evolutionSearchStatus:cancelSearch).
// The reply is what actually closes the overlay and unregisters the client's
// search frame, so it must be sent even when the coach was not queued.
func handleEvolutionSearchCancel(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	if s.deps.Matchmaker.CancelSearch(s.Coach.ID) {
		s.log.Info("evolution search cancelled", "coach", s.Coach.Name)
	}
	return s.sendEvolutionSearchCancelResult(true)
}

// sendEvolutionSearchResult sends OpEvolutionSearchResult (23004 amh_0):
// [i16 preset][i8 accepted].
//
// The preset is echoed verbatim for symmetry with the classic twin (23104 aLi),
// though the client never reads it back — wp_0 casts the message and calls only
// eY(). Do NOT send this with accepted=false on its own: the client still pops
// the team panels but opens nothing and shows nothing, leaving the player on a
// bare screen. A refusal is an error code (23008), not a rejected search.
func (s *Session) sendEvolutionSearchResult(preset uint16, accepted bool) error {
	w := protocol.NewWriter().U16(preset).U8(boolU8(accepted))
	frame, err := protocol.EncodeS2C(protocol.OpEvolutionSearchResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendEvolutionSearchCancelResult sends OpEvolutionSearchCancelResult
// (23002 wf_2): [i8 accepted].
func (s *Session) sendEvolutionSearchCancelResult(accepted bool) error {
	w := protocol.NewWriter().U8(boolU8(accepted))
	frame, err := protocol.EncodeS2C(protocol.OpEvolutionSearchCancelResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendEvolutionFightStarting sends OpEvolutionFightStarting (23006 azl_0), an
// empty message meaning "Lancement du combat". It closes the "Searching…"
// overlay and re-arms the client's fight frame, and must precede CREATE_FIGHT.
func (s *Session) sendEvolutionFightStarting() error {
	frame, err := protocol.EncodeS2C(protocol.OpEvolutionFightStarting, nil)
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendEvolutionSearchError sends OpEvolutionSearchError (23008 KL): [i8 code].
// See the code constants for which ones tear the overlay down.
func (s *Session) sendEvolutionSearchError(code uint8) error {
	w := protocol.NewWriter().U8(code)
	frame, err := protocol.EncodeS2C(protocol.OpEvolutionSearchError, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// startEvolutionFight builds the fight for a paired evolution search. It is
// startFight with the evolution flag set: ranked-style (not practice), no
// challenge, and it FEEDS PROGRESSION — XP, morale, fatigue, wounds and the
// per-fighter death roll all apply, which is the entire point of the mode.
func (d *Deps) startEvolutionFight(pm *pendingMatch) error {
	a := pickArena()
	teamA, err := d.buildFightTeam(a, pm.a, 0)
	if err != nil {
		return err
	}
	teamB, err := d.buildFightTeam(a, pm.b, 1)
	if err != nil {
		return err
	}
	return d.startFightWithTeams(a, teamA, teamB, false, 0, true)
}
