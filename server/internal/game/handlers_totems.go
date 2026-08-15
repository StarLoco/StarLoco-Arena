package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Demon totems and tournament totems.
//
// Both elements open NO UI when clicked: the click only fires a request, and the
// dialog is opened purely by the server's reply. Without an answer the totem is a
// completely inert click — it looks broken, though it cannot hang the client.
//
// The demon ladder is answered with a well-formed EMPTY window (no guild/clan
// reputation is modelled). The tournament totem now serves real data: see
// tournaments.go for the calendar (17003) / list (28602) builders and the standing
// tournament catalogue. Registration (4607 -> 28608) and the bracket request
// (28649 -> empty 28650) are handled below; the live-match layer stays deferred.
//
// Every decoder here reads its trailing fields unconditionally and has no length
// guards, so the byte counts must be exact — a short buffer is silently dropped
// inside the client's frame decoder and the dialog never opens.

func registerTotemHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpDemonLadderRequest, handleDemonLadderRequest)
	r.Register(protocol.OpTournamentCalReq, handleTournamentCalendarRequest)
	r.Register(protocol.OpTournamentListReq, handleTournamentListRequest)
	r.Register(protocol.OpTournamentRegister, handleTournamentRegister)
	r.Register(protocol.OpTournamentTreeReq, handleTournamentTreeRequest)
}

// handleDemonLadderRequest handles DEMON_LADDER_REQUEST (27510):
// [i16 demonId][i16 flag][i32 startRank] — the per-demon drill-down, sent both by
// a DemonTotem click and by selecting a row in the ranking window's Démon tab.
// Replies with an empty ranking for that demon (no guild reputation modelled).
func handleDemonLadderRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	demonID, err := r.U16()
	if err != nil {
		return err
	}
	frame, err := buildDemonLadder(demonID)
	if err != nil {
		return err
	}
	s.log.Debug("demon ladder", "coach", s.Coach.Name, "demon", demonID)
	return s.Send(frame)
}

// buildDemonLadder builds DEMON_LADDER (27511 anc_0) — guilds ranked by
// reputation on one demon:
//
//	[i16 demonId][i16 statusFlag][i32 startRank][i32 count]
//	count × { [i32 nameLen][guildName][i64 quarterly][i64 quarterlyCumul][i64 monthly] }
//	[i64 demonAffiliation]   <- PER-MESSAGE, not per-row
//
// The trailing i64 is read unconditionally, so it must be present even with zero
// rows — omitting it throws inside the client's decoder and the dialog never
// opens. statusFlag MUST be 1 for the client (awj) to (re)fill its rows. Guild
// reputation is not modelled, so this is always an empty (count 0) window.
func buildDemonLadder(demonID uint16) ([]byte, error) {
	const statusPopulate uint16 = 1
	w := protocol.NewWriter().
		U16(demonID).
		U16(statusPopulate).
		I32(0). // startRank
		I32(0). // row count: no guild-reputation data yet
		I64(0)  // per-message demon affiliation
	return protocol.EncodeS2C(protocol.OpDemonLadder, w.Bytes())
}

// handleTournamentCalendarRequest handles TOURNAMENT_CALENDAR_REQUEST (17002,
// empty) and replies with the standing tournaments as calendar events. This is one
// of the two requests the tournament window fires on open (the other is the list,
// below); together they populate the totem's calendar and its "Tournois du jour"
// panel.
func handleTournamentCalendarRequest(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	ts, err := s.deps.Store.Tournaments.ListEnabled()
	if err != nil {
		return err
	}
	frame, err := buildTournamentCalendar(ts)
	if err != nil {
		return err
	}
	s.log.Debug("tournament calendar", "coach", s.Coach.Name, "events", len(ts))
	return s.Send(frame)
}

// handleTournamentListRequest handles TOURNAMENT_LIST_REQUEST (28601, empty), sent
// alongside the calendar request, and replies with the registerable tournaments.
// The rows carry this coach's registration status so a re-opened window is correct.
func handleTournamentListRequest(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	ts, err := s.deps.Store.Tournaments.ListEnabled()
	if err != nil {
		return err
	}
	frame, err := buildTournamentList(s.Coach.ID, s.deps.Tournaments, ts)
	if err != nil {
		return err
	}
	s.log.Debug("tournament list", "coach", s.Coach.Name, "count", len(ts))
	return s.Send(frame)
}

// handleTournamentRegister handles TOURNAMENT_REGISTER (4607 aik_0):
// [i64 tournamentId][i64 coachId][i16 teamPreset][i32 cardId]. It is sent by the
// register button; the definitions we offer need no card, so cardId is 0 and
// teamPreset is -1. The server trusts the session identity and ignores the
// client-supplied coach id. Reply is 28608 dy_0: [i64 tournamentId][i8 errorCode]
// where 0 = accepted (2 = full, any other = refused).
func handleTournamentRegister(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	tid, err := r.I64()
	if err != nil {
		return err
	}

	const (
		regAccepted uint8 = 0
		regRefused  uint8 = 1
	)
	code := regAccepted
	switch t, lookupErr := s.deps.Store.Tournaments.GetByWireID(tid); {
	case lookupErr != nil || !t.Enabled:
		code = regRefused // unknown, or an admin has taken it offline
	case !t.RegistrationOpen:
		code = regRefused // listed, but closed to new entrants
	default:
		if s.deps.Tournaments != nil {
			s.deps.Tournaments.Register(s.Coach.ID, tid)
		}
	}

	w := protocol.NewWriter().I64(tid).U8(code)
	frame, err := protocol.EncodeS2C(protocol.OpTournamentRegisterReply, w.Bytes())
	if err != nil {
		return err
	}
	s.log.Debug("tournament register", "coach", s.Coach.Name, "tid", tid, "code", code)
	return s.Send(frame)
}

// handleTournamentTreeRequest handles TOURNAMENT_TREE_REQUEST (28649 alf_0):
// [i64 tournamentId][i32 round][i32 nameLen][name]. There is no live match graph
// yet, so it replies with an EMPTY tree (28650 IL): [i32 treeSize=0][i32 count=0]
// [i32 bib=0]. The client renders that as "the tournament tree is unavailable" and
// closes cleanly — the trailing i32 is read unconditionally, so it must be present.
func handleTournamentTreeRequest(s *Session, _ *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	w := protocol.NewWriter().
		I32(0). // treeSize
		I32(0). // node count
		I32(0)  // bib (trailing, read unconditionally)
	frame, err := protocol.EncodeS2C(protocol.OpTournamentTree, w.Bytes())
	if err != nil {
		return err
	}
	s.log.Debug("tournament tree (empty)", "coach", s.Coach.Name)
	return s.Send(frame)
}
