package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The tournament bracket (28649 request / 28650 reply).
//
// The client models the bracket as a 1-INDEXED BINARY HEAP and slices it into
// rounds by slot range (`ah_1.getFieldValue`):
//
//	slot 1      winner
//	slots 2-3   finale
//	slots 4-7   semiFinale
//	slots 8-15  quarterFinal
//	slots 16-31 firstRound
//
// so a full tree is 16 first-round entrants and 31 slots, and the winner of
// slots 2i / 2i+1 occupies slot i. Nothing about that is negotiable - the ranges
// are hard-coded in the client - which is why the bracket is built to fit it
// rather than the other way round.
//
// Names are UTF-8 here (`new String(byArray2, "UTF-8")`), NOT the windows-1252
// the rest of this protocol uses (B-068). Sending cp1252 would mangle any
// accented coach name in the bracket only.
const (
	// bracketWinnerSlot is the root: the tournament winner.
	bracketWinnerSlot = 1
	// bracketFirstRoundSlot is where the entrants go.
	bracketFirstRoundSlot = 16
	// bracketSlots is one past the last first-round slot.
	bracketSlots = 32
	// bracketEntrants is how many first-round names the client can render.
	bracketEntrants = bracketSlots - bracketFirstRoundSlot
)

// bracket is a sparse slot -> display name map.
type bracket map[int32]string

// buildBracket seeds entrants into the first round in the given order.
//
// Upper slots are left EMPTY on purpose. This server has no match layer yet, so
// no round has been decided; inventing winners would render a bracket that says
// results exist when none do. An empty slot is how the client shows "not decided
// yet", which is the truthful state.
func buildBracket(names []string) bracket {
	b := make(bracket, len(names))
	for i, name := range names {
		if i >= bracketEntrants {
			break // the client has nowhere to draw a 17th entrant
		}
		b[int32(bracketFirstRoundSlot+i)] = name
	}
	return b
}

// encodeTournamentTree builds TOURNAMENT_TREE (28650, client IL):
//
//	[i32 page][i32 count]{[i32 slot][i32 nameLen][utf8 name]}[i32 unread]
//
// `page` is echoed from the request: the client stores it (`ah_1.eE`) and sends
// it back incremented or decremented when the player uses the tree's paging
// buttons (20069), so returning anything else would make those buttons jump.
func encodeTournamentTree(page int32, b bracket) ([]byte, error) {
	w := protocol.NewWriter().I32(page).I32(int32(len(b)))
	// Ascending slot order: the client keys them into a map, so the order does
	// not matter to it - but a stable one keeps the bytes diffable.
	for slot := int32(0); slot < bracketSlots; slot++ {
		name, ok := b[slot]
		if !ok {
			continue
		}
		raw := []byte(name) // UTF-8, deliberately not protocol.EncodeText
		w.I32(slot).I32(int32(len(raw))).Raw(raw)
	}
	w.I32(0) // read unconditionally by IL.a, never consulted by ajp_0
	return protocol.EncodeS2C(protocol.OpTournamentTree, w.Bytes())
}

// handleTournamentTreeRequest handles TOURNAMENT_TREE_REQ (28649, client alf_0):
// [i64 tournamentId][i32 page][i32 nameLen][utf8 highlightName].
//
// The name is the coach the client wants highlighted in the tree (it seeds it
// with its own name and lets the player type another). It is presentational and
// the server does not need it, but it is parsed rather than skipped so a short
// payload is rejected instead of silently yielding a garbage tournament id.
func handleTournamentTreeRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	tid, page := int64(0), int32(0)
	if len(f.Payload) > 0 {
		r := protocol.NewReader(f.Payload)
		v, err := r.I64()
		if err != nil {
			return err
		}
		tid = v
		if p, err := r.I32(); err == nil {
			page = p
		}
	}

	var names []string
	if s.deps.Tournaments != nil {
		for _, coachID := range s.deps.Tournaments.EntrantsFor(tid) {
			names = append(names, s.deps.coachDisplayName(coachID))
		}
	}
	b := buildBracket(names)

	frame, err := encodeTournamentTree(page, b)
	if err != nil {
		return err
	}
	s.log.Debug("tournament tree", "coach", s.Coach.Name,
		"tournament", tid, "page", page, "entrants", len(names))
	return s.Send(frame)
}

// buildTournamentSearchPeriod builds TOURNAMENT_SEARCH_PERIOD (28630, dg_0):
// [i64 tid][i8 open].
func buildTournamentSearchPeriod(tid int64, open bool) ([]byte, error) {
	w := protocol.NewWriter().I64(tid)
	if open {
		w.U8(1)
	} else {
		w.U8(0)
	}
	return protocol.EncodeS2C(protocol.OpTournamentSearchPeriod, w.Bytes())
}

// announceTournamentSearchPeriods tells a coach which of its tournaments are
// currently accepting opponent searches.
//
// This is what makes the Tournois tab usable at all. The notification it creates
// (`td_0`) is the ONLY thing that selects a tournament: clicking it runs
// `agz_1`, which sets `vk_1.ad(tid)` and opens the team panel on the right tab.
// Until then `hu_2` refuses "Combattre" with "error.noTournamentSelected",
// however valid the team is - a gate no amount of server-side testing reveals,
// found by driving the real client.
//
// Sent only for tournaments the coach is REGISTERED for: a notification for one
// it cannot enter would select a tournament whose search must then be refused.
// The client also warns to its log if the same tournament is announced twice, so
// this is sent once, at world entry.
func (s *Session) announceTournamentSearchPeriods() {
	if s.Coach == nil || s.deps == nil || s.deps.Tournaments == nil || s.deps.Store == nil {
		return
	}
	ts, err := s.deps.Store.Tournaments.ListEnabled()
	if err != nil {
		return
	}
	for i := range ts {
		tid := ts[i].WireID()
		if !s.deps.Tournaments.IsRegistered(s.Coach.ID, tid) {
			continue
		}
		frame, err := buildTournamentSearchPeriod(tid, true)
		if err != nil {
			continue
		}
		if err := s.Send(frame); err == nil {
			s.log.Debug("tournament search period announced",
				"coach", s.Coach.Name, "tournament", tid)
		}
	}
}

// coachDisplayName resolves a coach id to the name shown in the bracket.
func (d *Deps) coachDisplayName(coachID uint) string {
	if d.Store != nil {
		if c, err := d.Store.Coaches.Get(coachID); err == nil && c != nil && c.Name != "" {
			return c.Name
		}
	}
	return "?"
}
