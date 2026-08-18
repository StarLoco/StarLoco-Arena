package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The "ready up and look for an opponent" handshake, shared by the CLASSIC and
// EVOLUTION tabs of the team panel.
//
// The two blocks are byte-identical twins, and so are the client frames that
// consume them — `vu_1` (classic) and `wp_0` (evolution) are the same class with
// one string changed, `classicSearchStatusDialog` vs
// `evolutionSearchStatusDialog`. They are kept as one type here so the symmetry
// is impossible to miss and the traps below only have to be written once.
//
//	                     classic   evolution
//	request      C2S     23103     23003      [i64 coachId][i16 preset]
//	result       S2C     23104     23004      [i16 preset][i8 accepted]
//	cancel       C2S     23101     23001      [i64 coachId][i16 preset]
//	cancelResult S2C     23102     23002      [i8 accepted]
//	starting     S2C     23106     23006      (empty) "Lancement du combat"
//	error        S2C     23108     23008      [i8 code]
//
// The order is what the client cares about: the accepted `result` is what opens
// its "Recherche en cours……" overlay, and only `starting`, `cancelResult` or an
// `error` with code 3/4/5 closes it again. So `starting` must reach the client
// before CREATE_FIGHT, or the fight runs underneath the overlay.
//
// Two traps, both read out of `wp_0`/`vu_1`:
//
//   - **`result` with accepted=0 is a dead end.** The client pops the team panels
//     either way, but only opens the overlay when the flag is true. Refusing that
//     way leaves the player on a bare screen with no message and no way back. A
//     refusal is an `error`, never a rejected result.
//   - **`error` codes 1 and 2 leave the overlay UP** (they only show a message);
//     only 3, 4 and 5 tear it down. Codes 1/2 are therefore safe only *before* an
//     accepted `result`, when no overlay exists yet.
type searchFamily struct {
	name         string
	result       uint16
	cancelResult uint16
	starting     uint16
	err          uint16
}

var (
	classicSearchFamily = searchFamily{
		name:         "classic",
		result:       protocol.OpClassicSearchResult,
		cancelResult: protocol.OpClassicSearchCancelResult,
		starting:     protocol.OpClassicFightStarting,
		err:          protocol.OpClassicSearchError,
	}
	evolutionSearchFamily = searchFamily{
		name:         "evolution",
		result:       protocol.OpEvolutionSearchResult,
		cancelResult: protocol.OpEvolutionSearchCancelResult,
		starting:     protocol.OpEvolutionFightStarting,
		err:          protocol.OpEvolutionSearchError,
	}
)

// Search error codes, from the client's own branch table in wp_0/vu_1:
//
//	1 matchfinder.impossibleToStartOpponentsSearch   message only
//	2 matchfinder.badTeam                            message only
//	3 matchfinder.canceledByCoach                    message + tears down
//	4 matchfinder.opponentNotFound                   message + tears down
//	5 (silent)                                       tears down
const (
	searchErrCannotStart uint8 = 1
	searchErrBadTeam     uint8 = 2
	searchErrCancelled   uint8 = 3
	searchErrNoOpponent  uint8 = 4
)

// sendSearchResult accepts (or refuses) a search. The preset is echoed verbatim
// for symmetry, though neither client frame reads it back — both cast the message
// and call only its boolean accessor.
func (fam searchFamily) sendResult(s *Session, preset uint16, accepted bool) error {
	w := protocol.NewWriter().U16(preset).U8(boolU8(accepted))
	frame, err := protocol.EncodeS2C(fam.result, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendSearchCancelResult answers a cancel. This reply is what actually closes the
// overlay and unregisters the client's search frame, so it must be sent even when
// the coach turned out not to be queued.
func (fam searchFamily) sendCancelResult(s *Session, accepted bool) error {
	w := protocol.NewWriter().U8(boolU8(accepted))
	frame, err := protocol.EncodeS2C(fam.cancelResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendFightStarting closes the overlay and re-arms the client's fight frame. Must
// precede CREATE_FIGHT.
func (fam searchFamily) sendStarting(s *Session) error {
	frame, err := protocol.EncodeS2C(fam.starting, nil)
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendSearchError refuses visibly. See the code constants for which ones close
// the overlay.
func (fam searchFamily) sendError(s *Session, code uint8) error {
	w := protocol.NewWriter().U8(code)
	frame, err := protocol.EncodeS2C(fam.err, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// announceFightStarting sends the family's `starting` frame to both sides of a
// paired match, before the fight itself is built.
//
// That ordering is structural rather than delicate: CREATE_FIGHT is emitted from
// the fight goroutine (`startFightWithTeams` → `f.Post`), so anything sent
// synchronously from a handler necessarily precedes it. Worth knowing before
// "tidying" these sends into the fight actor, which WOULD break it.
func announceFightStarting(fam searchFamily, pm *pendingMatch) {
	for _, sr := range []*searcher{pm.a, pm.b} {
		if sr != nil && sr.session != nil {
			_ = fam.sendStarting(sr.session)
		}
	}
}
