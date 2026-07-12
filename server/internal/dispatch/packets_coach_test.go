package dispatch

import (
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/protocol"
)

// TestBuildActorAppearWireFormat verifies the exact byte layout against
// the client-verified format documented in
// docs/opcodes/03-coach-world.md's ACTOR_APPEAR section: byte count,
// followed by count * {long id, int worldX, int worldY, short altitude,
// byte directionIndex}. This is the packet whose absence (previously a
// dead opcode constant, never built or sent anywhere) caused teleported
// coaches/fighters to never visually appear on the fight map -- see
// docs/08-java-parity-roadmap.md's write-up on this fix.
func TestBuildActorAppearWireFormat(t *testing.T) {
	entries := []actorAppearEntry{
		{ID: 1001, X: 16, Y: 11, Z: -8, Direction: combat.DirSouth},
		{ID: 2002, X: 1, Y: 7, Z: -6, Direction: combat.DirNorth},
	}
	frame := buildActorAppear(entries)

	if frame.Opcode != protocol.SendActorAppear {
		t.Fatalf("opcode = %v, want SendActorAppear", frame.Opcode)
	}

	r := protocol.NewReader(frame.Payload)
	count := r.Byte()
	if count != 2 {
		t.Fatalf("count = %d, want 2", count)
	}
	for i, want := range entries {
		id := r.Int64()
		x := r.Int32()
		y := r.Int32()
		z := r.Int16()
		dir := r.Byte()
		if id != want.ID || x != want.X || y != want.Y || z != want.Z || combat.Direction8(dir) != want.Direction {
			t.Errorf("entry %d = {id=%d x=%d y=%d z=%d dir=%d}, want %+v", i, id, x, y, z, dir, want)
		}
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	if r.Remaining() != 0 {
		t.Errorf("Remaining() = %d, want 0 (no trailing bytes)", r.Remaining())
	}
}

// TestBuildActorAppearEmpty verifies the zero-entry case doesn't panic and
// produces a single count byte of 0.
func TestBuildActorAppearEmpty(t *testing.T) {
	frame := buildActorAppear(nil)
	if len(frame.Payload) != 1 || frame.Payload[0] != 0 {
		t.Errorf("payload = %v, want [0]", frame.Payload)
	}
}

// TestBuildActorAppearForFight verifies the helper assembles BOTH coach
// entries (at their pedestal spots, with their REAL ids) AND one entry per
// fighter (at its combat position, facing its team's default DIAGONAL
// orientation, with its offset FighterWireIDBase id). The coach-then-
// fighter ordering plus the disjoint id ranges are what let the client's
// fighter-first ACTOR_APPEAR resolution render both correctly (see §8.18).
//
// This test deliberately uses coach id 10 and a fighter whose REAL id is
// also 10 -- proving that after offsetting, the fighter's WIRE id
// (FighterWireIDBase+10) no longer collides with the coach's real id 10.
func TestBuildActorAppearForFight(t *testing.T) {
	// Fighter REAL ids 10 and 20 deliberately equal the coach ids, to
	// prove the FighterWireIDBase offset removes the collision.
	teamA := &combat.Team{ID: 1, Mates: []*combat.TeamMate{{
		CoachID:  10,
		Fighters: []*combat.Fighter{combat.NewFighterFromBreed(combat.FighterWireIDBase+10, 1, 1, "F1", 0, 0)},
	}}}
	teamB := &combat.Team{ID: 2, Mates: []*combat.TeamMate{{
		CoachID:  20,
		Fighters: []*combat.Fighter{combat.NewFighterFromBreed(combat.FighterWireIDBase+20, 2, 1, "F2", 0, 0)},
	}}}
	teamA.Mates[0].Fighters[0].Position = combat.Point3{X: 16, Y: 12, Z: -8}
	teamB.Mates[0].Fighters[0].Position = combat.Point3{X: 1, Y: 8, Z: -6}

	fight := combat.NewFight(1, 1, combat.Clocks{}, []*combat.Team{teamA, teamB}, nil, nil, zerolog.Nop())

	coachASpot := combat.Point3{X: 16, Y: 11, Z: -8}
	coachBSpot := combat.Point3{X: 1, Y: 7, Z: -6}
	// Arena center between the two pedestals. coachFacingToward snaps each
	// coach->center vector to a render-legal diagonal: A at (16,11) faces
	// NW (dx dominant, negative), B at (1,7) faces SE (dx dominant,
	// positive) -- i.e. toward the battlefield.
	fightCenter := combat.Point3{X: 8, Y: 9, Z: -7}
	frame := buildActorAppearForFight(fight, 10, coachASpot, 20, coachBSpot, fightCenter)

	r := protocol.NewReader(frame.Payload)
	count := r.Byte()
	if count != 4 {
		t.Fatalf("count = %d, want 4 (2 coaches + 2 fighters)", count)
	}

	type entry struct {
		id   int64
		x, y int32
		z    int16
		dir  byte
	}
	var got []entry
	for i := 0; i < int(count); i++ {
		got = append(got, entry{r.Int64(), r.Int32(), r.Int32(), r.Int16(), r.Byte()})
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	if r.Remaining() != 0 {
		t.Errorf("Remaining() = %d, want 0", r.Remaining())
	}

	want := []entry{
		// Coaches first, at their REAL ids and pedestal spots, facing
		// OPPOSITE diagonals toward the battlefield (coach A NORTH_WEST,
		// coach B SOUTH_EAST) so they look at each other, not away.
		{10, 16, 11, -8, byte(combat.DirNorthWest)},
		{20, 1, 7, -6, byte(combat.DirSouthEast)},
		// Fighters, at their OFFSET wire ids and combat positions, each
		// facing the SAME diagonal as its coach (team 1 NORTH_WEST like
		// coach A, team 2 SOUTH_EAST like coach B) -- see defaultTeamFacing.
		{combat.FighterWireIDBase + 10, 16, 12, -8, byte(combat.DirNorthWest)},
		{combat.FighterWireIDBase + 20, 1, 8, -6, byte(combat.DirSouthEast)},
	}
	if len(got) != len(want) {
		t.Fatalf("got %d entries, want %d", len(got), len(want))
	}
	for i := range want {
		if got[i] != want[i] {
			t.Errorf("entry %d = %+v, want %+v", i, got[i], want[i])
		}
	}

	// No two entries may share an id (the collision this whole scheme
	// prevents): with the offset, coach 10 and fighter-real-10 differ.
	seen := map[int64]bool{}
	for _, e := range got {
		if seen[e.id] {
			t.Errorf("duplicate id %d across coach/fighter entries -- collision not prevented", e.id)
		}
		seen[e.id] = true
	}
	// Every FIGHTER direction (entries 2,3) must be a DIAGONAL (odd).
	for _, e := range got[2:] {
		if e.dir%2 == 0 {
			t.Errorf("fighter id %d direction %d is CARDINAL (even) -- fighter sprites only render facing diagonals", e.id, e.dir)
		}
	}
}

// TestCoachFacingToward verifies a coach always faces the arena centroid via
// a render-legal diagonal, regardless of where its pedestal sits -- the fix
// for "coaches turn their back to the match on some maps".
func TestCoachFacingToward(t *testing.T) {
	center := combat.Point3{X: 8, Y: 8}
	cases := []struct {
		name string
		from combat.Point3
		want combat.Direction8
	}{
		// dx dominant.
		{"east-of-center faces NW (toward center)", combat.Point3{X: 16, Y: 8}, combat.DirNorthWest},
		{"west-of-center faces SE", combat.Point3{X: 0, Y: 8}, combat.DirSouthEast},
		// dy dominant.
		{"south-of-center faces NE", combat.Point3{X: 8, Y: 16}, combat.DirNorthEast},
		{"north-of-center faces SW", combat.Point3{X: 8, Y: 0}, combat.DirSouthWest},
		// Diagonal-ish offsets snap to the dominant axis.
		{"far-east slightly-south faces NW", combat.Point3{X: 16, Y: 10}, combat.DirNorthWest},
		{"slightly-east far-south faces NE", combat.Point3{X: 10, Y: 16}, combat.DirNorthEast},
		// Degenerate: already at center -> a valid diagonal, never a cardinal.
		{"at center falls back to a diagonal", center, combat.DirSouthEast},
	}
	for _, c := range cases {
		got := coachFacingToward(c.from, center)
		if got != c.want {
			t.Errorf("%s: coachFacingToward(%v -> %v) = %d, want %d", c.name, c.from, center, got, c.want)
		}
		if byte(got)%2 == 0 {
			t.Errorf("%s: direction %d is CARDINAL (even) -- coach sprites only render facing diagonals", c.name, got)
		}
	}
}
