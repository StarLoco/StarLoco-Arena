package combat

import (
	"math/rand"
	"testing"

	"github.com/rs/zerolog"
)

func TestAdjacentOpponents_OnlyLivingEnemies(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Position = Point3{X: 0, Y: 0}
	b := NewFighter(2, 2, BreedFeca) // opponent, adjacent
	b.Position = Point3{X: 1, Y: 0}
	c := NewFighter(3, 2, BreedFeca) // opponent, adjacent but dead
	c.Position = Point3{X: 0, Y: 1}
	c.IsDead = true
	d := NewFighter(4, 1, BreedIop) // same team, adjacent
	d.Position = Point3{X: -1, Y: 0}

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a, d}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b, c}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, nil, nil, zerolog.Nop())

	opponents := f.adjacentOpponents(a)
	if len(opponents) != 1 || opponents[0] != b {
		t.Fatalf("adjacentOpponents = %v, want [b]", opponents)
	}
}

func TestAttemptEvadeTackle_NoOpponentsAlwaysSucceeds(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Position = Point3{X: 0, Y: 0}
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, nil, nil, zerolog.Nop())

	if !f.attemptEvadeTackle(a) {
		t.Fatalf("expected evasion to always succeed with no adjacent opponents")
	}
}

func TestAttemptEvadeTackle_FourOpponentsAlwaysFails(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Position = Point3{X: 0, Y: 0}
	var opponents []*Fighter
	positions := []Point3{{X: 1, Y: 0}, {X: -1, Y: 0}, {X: 0, Y: 1}, {X: 0, Y: -1}}
	for i, pos := range positions {
		o := NewFighter(int64(10+i), 2, BreedFeca)
		o.Position = pos
		opponents = append(opponents, o)
	}
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: opponents}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, nil, nil, zerolog.Nop())
	f.SetRNGSeed(1)

	// Force a favorable roll for individual evasion; being surrounded by
	// 4+ should still always fail per the manual, regardless of RNG.
	for i := 0; i < 20; i++ {
		if f.attemptEvadeTackle(a) {
			t.Fatalf("expected always-tackled when surrounded by 4 opponents")
		}
	}
}

func TestAttemptEvadeTackle_StatisticalRate(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Position = Point3{X: 0, Y: 0}
	b := NewFighter(2, 2, BreedFeca)
	b.Position = Point3{X: 1, Y: 0}
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, nil, nil, zerolog.Nop())
	f.rng = rand.New(rand.NewSource(7))

	successes := 0
	const trials = 5000
	for i := 0; i < trials; i++ {
		if f.attemptEvadeTackle(a) {
			successes++
		}
	}
	rate := float64(successes) / float64(trials)
	// Expect ~67% +/- a generous tolerance for a statistical test.
	if rate < 0.60 || rate > 0.74 {
		t.Fatalf("evasion success rate = %.3f, want ~0.67", rate)
	}
}

// TestTruncatePathOnEnemyContact verifies a moving fighter's path is cut
// short at the first cell adjacent to a living opponent (stop-on-contact),
// keeping that contact step, and is left unchanged when no cell touches an
// enemy.
func TestTruncatePathOnEnemyContact(t *testing.T) {
	// Mover starts at (0,0); a lone enemy sits at (5,0). A straight path
	// east passes (4,0) which is adjacent to the enemy -> stop there.
	a := NewFighter(1, 1, BreedIop)
	a.Position = Point3{X: 0, Y: 0}
	enemy := NewFighter(2, 2, BreedFeca)
	enemy.Position = Point3{X: 5, Y: 0}

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{enemy}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, nil, nil, zerolog.Nop())

	// Full requested path walks past the enemy to (6,0).
	full := []Point3{{X: 1}, {X: 2}, {X: 3}, {X: 4}, {X: 5, Y: 0}, {X: 6}}
	// Note (5,0) is occupied by the enemy; a real resolved path wouldn't
	// include it, but truncation should stop at (4,0) before that anyway.
	got := f.truncatePathOnEnemyContact(a, full)
	if len(got) != 4 || got[len(got)-1] != (Point3{X: 4, Y: 0}) {
		t.Errorf("path truncated to %v, want it to stop at (4,0) on contact with the enemy at (5,0)", got)
	}

	// A path that never comes within distance 1 of the enemy is unchanged.
	enemy.Position = Point3{X: 5, Y: 5}
	clear := []Point3{{X: 1}, {X: 2}, {X: 3}}
	if got := f.truncatePathOnEnemyContact(a, clear); len(got) != 3 {
		t.Errorf("path with no enemy contact truncated to %v, want unchanged (len 3)", got)
	}
}
