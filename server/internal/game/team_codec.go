package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// specialTeamType reports whether a preset type carries the 4 appearance bytes.
func specialTeamType(t int16) bool { return t == -5 || t == -6 || t == -7 }

// TeamPreset is a decoded sw_1 team-preset blob.
type TeamPreset struct {
	Type       int16
	TeamID     int16
	GameMode   int16
	Name       string
	App        [4]uint8
	FighterIDs []int64
	// FighterOwners[i] is the coach that owns FighterIDs[i] - the second i64 of
	// each entry, which is how the client attributes fighters to coaches
	// (sw_1.cF), NOT a budget as an earlier revision assumed.
	FighterOwners []int64
	CoachIDs      []int64
}

// decodeTeamPreset parses an sw_1 blob (big-endian):
//
//	[i16 type][i16 teamId][i16 gameMode][u8 nameLen][name]
//	[4×u8 appearance -- only if type in {-5,-6,-7}]
//	[u8 fighterCount]{i64 fighterId, i64 owningCoachId}
//	[u8 coachCount]{i64 coachId}
func decodeTeamPreset(data []byte) (*TeamPreset, error) {
	r := protocol.NewReader(data)
	tp := &TeamPreset{}

	typ, err := r.U16()
	if err != nil {
		return nil, err
	}
	tp.Type = int16(typ)
	teamID, err := r.U16()
	if err != nil {
		return nil, err
	}
	tp.TeamID = int16(teamID)
	gm, err := r.U16()
	if err != nil {
		return nil, err
	}
	tp.GameMode = int16(gm)
	tp.Name, err = r.StringU8()
	if err != nil {
		return nil, err
	}
	if specialTeamType(tp.Type) {
		for i := 0; i < 4; i++ {
			if tp.App[i], err = r.U8(); err != nil {
				return nil, err
			}
		}
	}
	fCount, err := r.U8()
	if err != nil {
		return nil, err
	}
	for i := 0; i < int(fCount); i++ {
		id, err := r.I64()
		if err != nil {
			return nil, err
		}
		owner, err := r.I64() // the OWNING COACH, not a budget
		if err != nil {
			return nil, err
		}
		tp.FighterIDs = append(tp.FighterIDs, id)
		tp.FighterOwners = append(tp.FighterOwners, owner)
	}
	cCount, err := r.U8()
	if err != nil {
		return nil, err
	}
	for i := 0; i < int(cCount); i++ {
		id, err := r.I64()
		if err != nil {
			return nil, err
		}
		tp.CoachIDs = append(tp.CoachIDs, id)
	}
	return tp, nil
}

// benchTeamType is the special "Evolution" team type (-4). The 2.70 client's
// Evolution/first-open handler (ce_1 case 6030) does an unchecked
// arrayList.get(0) on the team-preset list, so the list must be non-empty; we
// lead it with an EMPTY -4 team purely to keep that get(0) safe. Kept as a var
// (not a typed const) so the uint16 wire conversion isn't rejected.
var benchTeamType = int16(-4)

// benchTeamPreset builds the leading type=-4 "Evolution bench" team as an EMPTY
// team (no fighter members). This is critical: the client's fighter-pool filter
// (U/Z) excludes any fighter that is a member of ANY team in the 6030, so a -4
// team listing the coach's fighters would HIDE them from the Elite available-
// fighters grid (renders empty). The fighters must flow only via 6006 (type=1)
// into adY.atu(); 6030 must not reference them. Type -4 carries no appearance
// bytes (only -5/-6/-7 do). Layout: [i16 type=-4][i16 teamId=0][i16 gameMode=0]
// [u8 nameLen=0][u8 fighterCount=0][u8 coachCount=0].
func benchTeamPreset() []byte {
	return protocol.NewWriter().
		U16(uint16(benchTeamType)).
		U16(0).       // teamId (0 — never collides with a real preset's DB id)
		U16(0).       // gameMode
		StringU8(""). // no name
		U8(0).        // fighter count = 0 (must not reference any fighter)
		U8(0).        // coach count = 0
		Bytes()
}

// encodeTeamPreset serializes a persisted Team as an sw_1 blob.
//
// The second i64 of each fighter entry is the fighter's OWNING COACH, not a
// budget. `sw_1.j(id, v)` stores the pair as `bMJ[id] = v`, and `cF(coachId)`
// keeps the entries where `bMJ.du(id) == coachId || == -1` - so that long is how
// the client decides which coach controls which fighter. `afH()` walks the coach
// list and requires each one's `cF()` to be non-empty, which is what raises
// "Un des coachs ne controle aucun combattant".
//
// This used to write the fighter's budget there. A 1v1 never noticed, because
// afH() only evaluates when the preset has more than one coach - but a 2v2 could
// never be launched, since a budget (2200) matches neither coach id nor -1 and
// both sides therefore counted zero fighters.
//
// Note cF() reads the preset's OWN map and never consults the client's roster,
// so an ally's fighter validates here without the client having to know anything
// else about it.
func encodeTeamPreset(t *domain.Team, ownerOf map[uint]uint) []byte {
	w := protocol.NewWriter().
		U16(uint16(t.Type)).
		U16(uint16(t.ID)).
		U16(uint16(t.GameMode)).
		StringU8(t.Name)
	if specialTeamType(t.Type) {
		w.U8(t.App1).U8(t.App2).U8(t.App3).U8(t.App4)
	}
	// Only members with a valid fighter id: the client reads the paired value
	// long only when id != -1, so filtering keeps the count consistent with the
	// entries actually written. (FighterID is a uint DB id, never -1 on the
	// wire, but this guards the count against stray zero rows.)
	members := make([]domain.TeamFighter, 0, len(t.Members))
	for _, m := range t.Members {
		if m.FighterID > 0 {
			members = append(members, m)
		}
	}
	w.U8(uint8(len(members)))
	for _, m := range members {
		// Default to the preset's own coach: on a solo team every fighter is its
		// owner's, and on a duo the map supplies the ally's fighters.
		owner := t.CoachID
		if o, ok := ownerOf[m.FighterID]; ok && o != 0 {
			owner = o
		}
		w.I64(int64(m.FighterID))
		w.I64(int64(owner))
	}
	// The trailing COACH list, and its LENGTH is load-bearing: the 2VS2 tab is
	// populated from `teamManagement.teamPreset2vs2List`, which selects presets
	// where `sw_1.afL()` is true - and afL() is exactly `bMK.size() == 2`. A
	// one-entry list is not "a 2v2 with the ally named", it is invisible.
	//
	// Order matters too: `afG()` returns bMK.get(0), and that is the i64 the
	// client sends back in 23103 "Combattre" as the ally. So the PARTNER goes
	// first and the owner second.
	//
	// A solo preset writes none; afG() then returns -1, which the client's own
	// launch path reads as "no ally, use my own coach id".
	if t.AllyCoachID != 0 {
		w.U8(2).I64(int64(t.AllyCoachID)).I64(int64(t.CoachID))
	} else {
		w.U8(0)
	}
	return w.Bytes()
}
