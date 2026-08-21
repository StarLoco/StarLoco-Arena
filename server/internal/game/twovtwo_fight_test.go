package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// A 2v2 fight is the first thing that exercises the FightMember model with more
// than one coach a side, so these assert the properties that were previously
// implicit in "one coach == one side".

// twoVTwoFight builds a fight with two coaches on each side, each owning one
// fighter, and returns it with the four sessions.
func twoVTwoFight(t *testing.T) (*Fight, []*Session) {
	t.Helper()
	d := &Deps{
		World:    NewRegistry(150),
		Fights:   NewFightManager(),
		Sessions: NewSessionRegistry(),
		TeamUps:  newTeamUps(),
		Log:      testLogger(),
	}
	var sessions []*Session
	mk := func(id uint, name string, side uint8) (*FightMember, *FightFighter) {
		s := &Session{
			log: testLogger(), deps: d,
			out: make(chan []byte, writeQueueSize), quit: make(chan struct{}),
			Coach: &domain.Coach{ID: id, Name: name},
		}
		d.Sessions.Swap(id, s)
		sessions = append(sessions, s)
		ff := &FightFighter{
			WireID: FighterWireIDBase + int64(id), CoachID: id, TeamID: side,
			Fighter: &domain.Fighter{ID: id * 10, BreedID: 1, Name: name + "F"},
			MaxHP:   100, HP: 100, AP: 6, MP: 3,
		}
		return &FightMember{Coach: s.Coach, Session: s}, ff
	}
	m1, f1 := mk(1, "A1", 0)
	m2, f2 := mk(2, "A2", 0)
	m3, f3 := mk(3, "B1", 1)
	m4, f4 := mk(4, "B2", 1)

	f := &Fight{
		arena:        &practiceArena,
		deps:         d,
		Teams:        [2]*FightTeam{{ID: 0, Members: []*FightMember{m1, m2}, Fighters: []*FightFighter{f1, f2}}, {ID: 1, Members: []*FightMember{m3, m4}, Fighters: []*FightFighter{f3, f4}}},
		readyPresent: map[uint]bool{},
		readyObserve: map[uint]bool{},
		readyAction:  map[uint]bool{},
	}
	f.Timeline = buildTimeline(f)
	return f, sessions
}

// TestTwoVTwoReachesEveryCoach: a broadcast must reach all FOUR coaches. With
// the old one-coach-per-side model this silently reached two.
func TestTwoVTwoReachesEveryCoach(t *testing.T) {
	f, sessions := twoVTwoFight(t)
	if got := len(f.sessions()); got != 4 {
		t.Fatalf("fight.sessions() = %d, want 4", got)
	}
	f.broadcast([]byte{0, 0, 0, 0})
	for _, s := range sessions {
		if len(s.out) == 0 {
			t.Errorf("coach %d received nothing from a broadcast", s.Coach.ID)
		}
	}
}

// TestTwoVTwoCreateFightCarriesFourCoaches: CREATE_FIGHT's coach list is keyed by
// id and is independent of the team list, so it must carry every member. A short
// list makes the client log "coach inexistant : probleme de serialisation" for
// every fighter the missing coach owns.
func TestTwoVTwoCreateFightCarriesFourCoaches(t *testing.T) {
	f, sessions := twoVTwoFight(t)
	if got := len(f.members()); got != 4 {
		t.Fatalf("fight.members() = %d, want 4", got)
	}
	frame, err := buildCreateFight(f, sessions[0].Coach, false)
	if err != nil {
		t.Fatalf("buildCreateFight: %v", err)
	}
	// Look for each coach's length-prefixed NAME, not its id.
	//
	// A first version searched for the coach ids and proved nothing: a fighter
	// blob is followed by its owner's id, so all four ids appear in the frame even
	// when the coach LIST is truncated to one per side - the mutation that does
	// exactly that passed. The name only appears inside a coach block, and the
	// u8 length prefix stops it matching a fighter's name.
	for _, m := range f.members() {
		name := m.Coach.Name
		want := append([]byte{byte(len(name))}, name...)
		if !frameContains(frame, want) {
			t.Errorf("CREATE_FIGHT has no coach block for %q - the client would report "+
				"\"coach inexistant\" for every fighter it owns", name)
		}
	}
}

// TestTwoVTwoAbsenceIsPerCoach: one coach dropping must not silence its ally's
// fighters, and the side is only absent once BOTH have gone.
func TestTwoVTwoAbsenceIsPerCoach(t *testing.T) {
	f, _ := twoVTwoFight(t)
	side := f.Teams[0]
	mine, ally := side.Members[0], side.Members[1]

	mine.Absent = true
	mine.Session = nil
	if side.Absent() {
		t.Error("side reported absent while one coach is still connected")
	}
	if f.allTeamsAbsent() {
		t.Error("fight torn down while a coach on each side is still connected")
	}
	// The dropped coach's own fighter auto-passes; the ally's does not.
	for _, ff := range side.Fighters {
		want := ff.CoachID == mine.Coach.ID
		if got := f.teamAbsent(ff); got != want {
			t.Errorf("fighter of coach %d: teamAbsent = %v, want %v", ff.CoachID, got, want)
		}
	}
	// And AI control follows the OWNER, not the side.
	for _, ff := range side.Fighters {
		want := ff.CoachID == mine.Coach.ID
		if got := f.isAIControlled(ff); got != want {
			t.Errorf("fighter of coach %d: isAIControlled = %v, want %v (ally still plays its own)",
				ff.CoachID, got, want)
		}
	}
	ally.Absent = true
	ally.Session = nil
	if !side.Absent() {
		t.Error("side not absent after BOTH coaches dropped")
	}
}

// TestTwoVTwoResolvesEitherMember: lookups keyed by coach must find the side from
// either member, not only from the one that happens to be first.
func TestTwoVTwoResolvesEitherMember(t *testing.T) {
	f, _ := twoVTwoFight(t)
	f.deps.Fights.Create(f)
	for _, id := range []uint{1, 2, 3, 4} {
		if f.teamOfCoach(id) == nil {
			t.Errorf("teamOfCoach(%d) = nil - an ally cannot find its own side", id)
		}
		if f.memberOfCoach(id) == nil {
			t.Errorf("memberOfCoach(%d) = nil", id)
		}
		if f.deps.Fights.ByCoach(id) != f {
			t.Errorf("Fights.ByCoach(%d) did not resolve the fight", id)
		}
	}
	want := map[uint]uint8{1: 0, 2: 0, 3: 1, 4: 1}
	for id, side := range want {
		if got := f.teamOfCoach(id).ID; got != side {
			t.Errorf("coach %d resolved to side %d, want %d", id, got, side)
		}
	}
}

// frameContains reports whether needle appears in b.
func frameContains(b, needle []byte) bool {
	for i := 0; i+len(needle) <= len(b); i++ {
		if string(b[i:i+len(needle)]) == string(needle) {
			return true
		}
	}
	return false
}

var _ = protocol.OpCreateFight
