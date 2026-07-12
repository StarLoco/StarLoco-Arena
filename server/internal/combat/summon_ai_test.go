package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// fightWithSpells builds the standard two-fighter test fight and attaches a
// gamedata.Store whose Spells repo serves the given spells (keyed by ID) --
// enough for the summon AI's classify/cast paths, which need f.data.
func fightWithSpells(t *testing.T, spells ...gamedata.SpellTemplate) (*Fight, *Fighter, *Fighter) {
	t.Helper()
	f, a, b := newTestFightForEffects(t)
	byID := make(map[int32]gamedata.SpellTemplate, len(spells))
	for _, s := range spells {
		byID[s.ID] = s
	}
	f.data = &gamedata.Store{
		Spells: gamedata.NewRepository(func() (map[int32]gamedata.SpellTemplate, error) {
			return byID, nil
		}),
	}
	f.Timeline = NewTimeline([]*Fighter{a, b})
	return f, a, b
}

// damageSpell / debuffSpell / selfBuffSpell are minimal spell fixtures used
// to classify summon behavior. Action IDs: 3 = HP-loss (Earth damage),
// 18 = MP debuff, 88 = spell-rebound (self buff). Targets bit 2 =
// CONDITION_IS_CASTER (self).
func damageSpell(id int32, apCost, rangeMax byte) gamedata.SpellTemplate {
	return gamedata.SpellTemplate{
		ID: id, ActionPointsCost: apCost, RangeMin: 1, RangeMax: rangeMax,
		Effects: []gamedata.EffectDef{{ID: id * 10, ActionID: 3, Params: []float32{10}, Targets: []int32{1}}},
	}
}

func debuffSpell(id int32, apCost, rangeMax byte) gamedata.SpellTemplate {
	return gamedata.SpellTemplate{
		ID: id, ActionPointsCost: apCost, RangeMin: 1, RangeMax: rangeMax,
		Effects: []gamedata.EffectDef{{ID: id * 10, ActionID: 18, Params: []float32{1}, Targets: []int32{1}}},
	}
}

func selfBuffSpell(id int32, apCost byte) gamedata.SpellTemplate {
	return gamedata.SpellTemplate{
		ID: id, ActionPointsCost: apCost, RangeMin: 0, RangeMax: 0,
		Effects: []gamedata.EffectDef{{ID: id * 10, ActionID: 88, Params: []float32{100}, Targets: []int32{targetCondIsCaster}}},
	}
}

// makeSummonCurrent rebuilds the fight timeline as [summon, enemy] and
// advances to the summon's turn, so the AI's "only act while I'm the current
// fighter" guards are satisfied in a direct unit test (in production
// runSummonTurn is invoked exactly when the summon becomes current).
func makeSummonCurrent(f *Fight, summon, enemy *Fighter) {
	f.Timeline = NewTimeline([]*Fighter{summon, enemy})
	f.Timeline.StartNextTurn()
	for f.Timeline.CurrentFighter() != summon {
		f.Timeline.StartNextTurn()
	}
}

// TestIsAISummon confirms only fighters with a Father are AI-driven.
func TestIsAISummon(t *testing.T) {
	if isAISummon(nil) {
		t.Error("nil is not an AI summon")
	}
	if isAISummon(&Fighter{}) {
		t.Error("a fighter with no Father is a normal (player) fighter, not an AI summon")
	}
	if !isAISummon(&Fighter{Father: &Fighter{}}) {
		t.Error("a fighter with a Father is an AI summon")
	}
}

// TestNearestOpponentPicksClosest verifies the AI target selection.
func TestNearestOpponentPicksClosest(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 5, Y: 0}

	got := f.nearestOpponent(a)
	if got != b {
		t.Fatalf("nearestOpponent = %v, want b", got)
	}
	// A dead opponent must be ignored.
	b.IsDead = true
	if f.nearestOpponent(a) != nil {
		t.Error("nearestOpponent should ignore dead opponents")
	}
}

// TestMoveSummonTowardNearestOpponentClosesDistance verifies the summon
// walks toward its nearest opponent, spending MP, and stops adjacent
// (never onto the opponent's own cell).
func TestMoveSummonTowardNearestOpponentClosesDistance(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	// a is the summoner's stand-in opponent; b is the summon.
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 6, Y: 0}
	b.Father = a                    // mark b as a summon (AI)
	b.Characteristics[MP].Value = 3 // only enough MP to advance 3 cells
	b.Characteristics[MP].Max = 3
	startMP := b.Characteristic(MP)

	f.moveSummonTowardNearestOpponent(b)

	// It should have moved exactly 3 cells closer along X (6 -> 3), toward
	// a at X=0, without landing on a.
	if b.Position.X != 3 || b.Position.Y != 0 {
		t.Errorf("summon position after move = %v, want {3,0} (3 cells toward opponent)", b.Position)
	}
	if b.Position == a.Position {
		t.Error("summon must not stand on the opponent's cell")
	}
	spent := startMP - b.Characteristic(MP)
	if spent != 3 {
		t.Errorf("MP spent = %d, want 3 (one per cell moved)", spent)
	}
}

// TestMoveSummonTowardNearestOpponentNoOpWhenAdjacent verifies a summon
// already adjacent to an opponent doesn't move.
func TestMoveSummonTowardNearestOpponentNoOpWhenAdjacent(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0} // Manhattan distance 1 = adjacent
	b.Father = a
	b.Characteristics[MP].Value = 3
	b.Characteristics[MP].Max = 3

	f.moveSummonTowardNearestOpponent(b)

	if b.Position != (Point3{X: 1, Y: 0}) {
		t.Errorf("adjacent summon should not move, got %v", b.Position)
	}
	if b.Characteristic(MP) != 3 {
		t.Errorf("adjacent summon should spend no MP, MP=%d", b.Characteristic(MP))
	}
}

// TestMoveSummonTowardNearestOpponentNoOpWithoutMP verifies no movement
// happens with zero MP.
func TestMoveSummonTowardNearestOpponentNoOpWithoutMP(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 6, Y: 0}
	b.Father = a
	b.Characteristics[MP].Value = 0
	b.Characteristics[MP].Max = 0

	f.moveSummonTowardNearestOpponent(b)

	if b.Position != (Point3{X: 6, Y: 0}) {
		t.Errorf("summon with 0 MP should not move, got %v", b.Position)
	}
}

// --- Behavior classification (data-driven from the summon's spell) ---

func TestClassifySummon_NoSpellIsBlocker(t *testing.T) {
	f, _, b := fightWithSpells(t)
	b.Father = &Fighter{}
	b.SummonSpellID = 0
	if got := f.classifySummon(b); got != behaviorBlocker {
		t.Errorf("no-spell summon classified %d, want blocker(%d)", got, behaviorBlocker)
	}
}

func TestClassifySummon_DamageSpellIsAggressive(t *testing.T) {
	f, _, b := fightWithSpells(t, damageSpell(500, 4, 1))
	b.Father = &Fighter{}
	b.SummonSpellID = 500
	b.Characteristics[MP].Value, b.Characteristics[MP].Max = 2, 2 // below kite threshold
	if got := f.classifySummon(b); got != behaviorAggressive {
		t.Errorf("low-MP damage summon classified %d, want aggressive(%d)", got, behaviorAggressive)
	}
}

func TestClassifySummon_HighMPDamageSpellIsKite(t *testing.T) {
	f, _, b := fightWithSpells(t, damageSpell(501, 3, 2))
	b.Father = &Fighter{}
	b.SummonSpellID = 501
	b.Characteristics[MP].Value, b.Characteristics[MP].Max = 6, 6 // Tofu-like: hit and run
	if got := f.classifySummon(b); got != behaviorKite {
		t.Errorf("high-MP damage summon classified %d, want kite(%d)", got, behaviorKite)
	}
}

func TestClassifySummon_DebuffOnlySpellIsKite(t *testing.T) {
	f, _, b := fightWithSpells(t, debuffSpell(502, 4, 4))
	b.Father = &Fighter{}
	b.SummonSpellID = 502
	b.Characteristics[MP].Value, b.Characteristics[MP].Max = 3, 3
	if got := f.classifySummon(b); got != behaviorKite {
		t.Errorf("debuff-only summon classified %d, want kite(%d)", got, behaviorKite)
	}
}

func TestClassifySummon_SelfBuffSpellIsSelfBuff(t *testing.T) {
	f, _, b := fightWithSpells(t, selfBuffSpell(503, 2))
	b.Father = &Fighter{}
	b.SummonSpellID = 503
	if got := f.classifySummon(b); got != behaviorSelfBuff {
		t.Errorf("self-buff summon classified %d, want self-buff(%d)", got, behaviorSelfBuff)
	}
}

// --- End-to-end behavior turns ---

// TestAggressiveSummonMovesIntoRangeAndCasts: a melee-damage summon closes on
// the enemy and casts, dealing damage and spending AP.
func TestAggressiveSummonMovesIntoRangeAndCasts(t *testing.T) {
	f, enemy, summon := fightWithSpells(t, damageSpell(500, 4, 1))
	enemy.Position = Point3{X: 0, Y: 0}
	enemy.Characteristics[HP].Value, enemy.Characteristics[HP].Max = 100, 100
	summon.Position = Point3{X: 5, Y: 0}
	summon.Father = enemy // (opponent-team father is fine; AI only needs Father != nil)
	summon.TeamID = enemy.TeamID + 1
	summon.SummonSpellID = 500
	summon.SpellIDs = []int32{500}
	summon.Characteristics[MP].Value, summon.Characteristics[MP].Max = 4, 4
	summon.Characteristics[AP].Value, summon.Characteristics[AP].Max = 8, 8
	makeSummonCurrent(f, summon, enemy)

	hpBefore := enemy.Characteristic(HP)
	f.playAggressiveSummon(summon)

	if manhattanDistance(summon.Position, enemy.Position) != 1 {
		t.Errorf("aggressive summon should end adjacent to enemy, dist=%d pos=%v",
			manhattanDistance(summon.Position, enemy.Position), summon.Position)
	}
	if enemy.Characteristic(HP) >= hpBefore {
		t.Errorf("aggressive summon should have dealt damage, enemy HP %d -> %d", hpBefore, enemy.Characteristic(HP))
	}
	if summon.Characteristic(AP) >= 8 {
		t.Errorf("aggressive summon should have spent AP casting, AP=%d", summon.Characteristic(AP))
	}
}

// TestSelfBuffSummonCastsOnItself: a self-buff summon casts its spell on its
// own cell (spending AP) rather than attacking.
func TestSelfBuffSummonCastsOnItself(t *testing.T) {
	f, enemy, summon := fightWithSpells(t, selfBuffSpell(503, 2))
	enemy.Position = Point3{X: 0, Y: 0}
	summon.Position = Point3{X: 5, Y: 0}
	summon.Father = enemy
	summon.TeamID = enemy.TeamID + 1
	summon.SummonSpellID = 503
	summon.SpellIDs = []int32{503}
	summon.Characteristics[MP].Value, summon.Characteristics[MP].Max = 1, 1
	summon.Characteristics[AP].Value, summon.Characteristics[AP].Max = 4, 4
	makeSummonCurrent(f, summon, enemy)

	f.playSelfBuffSummon(summon)

	if summon.Characteristic(AP) != 2 {
		t.Errorf("self-buff summon should spend its 2 AP casting on itself, AP=%d", summon.Characteristic(AP))
	}
	if summon.SpellReboundRate == 0 {
		t.Error("self-buff (spell-rebound) should have set a SpellReboundRate on the summon")
	}
}

// TestKiteSummonRetreatsAfterCasting: a kite summon, already in range, casts
// then moves AWAY from the enemy with leftover MP.
func TestKiteSummonRetreatsAfterCasting(t *testing.T) {
	f, enemy, summon := fightWithSpells(t, debuffSpell(502, 2, 4))
	enemy.Position = Point3{X: 0, Y: 0}
	summon.Position = Point3{X: 3, Y: 0} // within range 4 already
	summon.Father = enemy
	summon.TeamID = enemy.TeamID + 1
	summon.SummonSpellID = 502
	summon.SpellIDs = []int32{502}
	summon.Characteristics[MP].Value, summon.Characteristics[MP].Max = 3, 3
	summon.Characteristics[AP].Value, summon.Characteristics[AP].Max = 4, 4
	makeSummonCurrent(f, summon, enemy)

	distBefore := manhattanDistance(summon.Position, enemy.Position)
	f.playKiteSummon(summon)
	distAfter := manhattanDistance(summon.Position, enemy.Position)

	if distAfter <= distBefore {
		t.Errorf("kite summon should retreat after casting: dist %d -> %d", distBefore, distAfter)
	}
	if summon.Characteristic(AP) >= 4 {
		t.Errorf("kite summon should have spent AP casting, AP=%d", summon.Characteristic(AP))
	}
}

// TestSummonBlockerMakesProgressNoOscillation verifies the BFS-based summon
// movement always closes distance on the enemy (never oscillates) and stops
// when adjacent -- the fix for "the summon walks forward then back".
func TestSummonBlockerMakesProgressNoOscillation(t *testing.T) {
	f, enemy, summon := fightWithSpells(t) // no spell -> blocker
	enemy.Position = Point3{X: 0, Y: 0}
	summon.Position = Point3{X: 8, Y: 0}
	summon.Father = enemy
	summon.TeamID = enemy.TeamID + 1
	summon.Characteristics[MP].Value, summon.Characteristics[MP].Max = 3, 3
	makeSummonCurrent(f, summon, enemy)

	// Turn 1: closes 3 cells (8 -> 5).
	d0 := manhattanDistance(summon.Position, enemy.Position)
	f.moveSummonTowardNearestOpponent(summon)
	d1 := manhattanDistance(summon.Position, enemy.Position)
	if d1 >= d0 {
		t.Fatalf("summon did not close distance on turn 1: %d -> %d", d0, d1)
	}

	// Refill MP and take a second turn: must keep closing, never reverse.
	summon.Characteristics[MP].Value = 3
	f.moveSummonTowardNearestOpponent(summon)
	d2 := manhattanDistance(summon.Position, enemy.Position)
	if d2 >= d1 {
		t.Fatalf("summon oscillated / stalled on turn 2: %d -> %d (want strictly closer)", d1, d2)
	}

	// Once adjacent, it must NOT move (no pointless shuffle).
	summon.Position = Point3{X: 1, Y: 0} // adjacent to enemy at (0,0)
	summon.Characteristics[MP].Value = 3
	before := summon.Position
	f.moveSummonTowardNearestOpponent(summon)
	if summon.Position != before {
		t.Errorf("adjacent summon moved to %v, want to stay put at %v", summon.Position, before)
	}
}

// TestReachableCellsBounded verifies the BFS reachability helper returns
// exactly the cells within the given MP budget on an open field, with
// correct-length paths.
func TestReachableCellsBounded(t *testing.T) {
	f, _, _ := newTestFightForEffects(t) // no map data -> open field
	mover := NewFighter(1, 1, BreedIop)
	mover.Position = Point3{X: 0, Y: 0}
	f.Timeline = NewTimeline([]*Fighter{mover})

	got := ReachableCells(mover, mover.Position, 2, f)
	// On an open field with single-axis steps, cells within Manhattan
	// distance 2 (excluding the origin) number 8 (dist1) + ... actually the
	// diamond of radius 2 minus center = 12 cells.
	for k, path := range got {
		d := abs32(k[0]) + abs32(k[1])
		if d == 0 {
			t.Errorf("ReachableCells included the origin cell")
		}
		if int(d) != len(path) {
			t.Errorf("cell %v path length = %d, want %d (Manhattan distance)", k, len(path), d)
		}
		if d > 2 {
			t.Errorf("cell %v is distance %d, beyond the 2-MP budget", k, d)
		}
	}
	if len(got) == 0 {
		t.Fatal("ReachableCells returned no cells for a 2-MP open-field flood")
	}
}

// --- #1: summons do not see invisible characters ---

// TestNearestOpponentIgnoresInvisible verifies the summon AI skips an
// INVISIBLE enemy when choosing a target, falling back to a farther visible
// one (or nil if the only enemy is invisible).
func TestNearestOpponentIgnoresInvisible(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	// a = summon, b = the closest enemy but INVISIBLE. Add a farther visible
	// enemy on a's own team's opponent side.
	far := NewFighterFromBreed(9, b.TeamID, BreedFeca, "Far", 0, 0)
	f.Timeline = NewTimeline([]*Fighter{a, b, far})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0} // closest
	far.Position = Point3{X: 5, Y: 0}
	b.Properties |= PropertyInvisible

	if got := f.nearestOpponent(a); got != far {
		t.Fatalf("nearestOpponent = %v, want the farther VISIBLE enemy (invisible one must be ignored)", got)
	}

	// With the only enemy invisible, target selection yields nil.
	f.Timeline = NewTimeline([]*Fighter{a, b})
	if got := f.nearestOpponent(a); got != nil {
		t.Errorf("nearestOpponent with only an invisible enemy = %v, want nil", got)
	}
}

// TestOrderedReachablePaths_SEFirstPriority verifies the summon path
// tie-break follows the wiki's SE>SW>NW>NE priority: given two equally-short
// routes to different cells, the SE-first route is ordered before the others,
// making the summon AI's "first strictly-better wins" deterministic.
func TestOrderedReachablePaths_SEFirstPriority(t *testing.T) {
	start := Point3{X: 5, Y: 5}
	// Four one-step routes, one in each direction.
	reachable := map[[2]int32][]Point3{
		{6, 5}: {{X: 6, Y: 5}}, // SE (rank 0)
		{5, 6}: {{X: 5, Y: 6}}, // SW (rank 1)
		{4, 5}: {{X: 4, Y: 5}}, // NW (rank 2)
		{5, 4}: {{X: 5, Y: 4}}, // NE (rank 3)
	}
	ordered := orderedReachablePaths(start, reachable)
	if len(ordered) != 4 {
		t.Fatalf("orderedReachablePaths returned %d paths, want 4", len(ordered))
	}
	wantFirst := Point3{X: 6, Y: 5} // SE
	if ordered[0][0] != wantFirst {
		t.Errorf("first ordered path ends at %v, want SE cell %v", ordered[0][0], wantFirst)
	}
	// Full priority order SE,SW,NW,NE.
	wantOrder := []Point3{{X: 6, Y: 5}, {X: 5, Y: 6}, {X: 4, Y: 5}, {X: 5, Y: 4}}
	for i, w := range wantOrder {
		if ordered[i][0] != w {
			t.Errorf("ordered[%d] ends at %v, want %v (SE>SW>NW>NE)", i, ordered[i][0], w)
		}
	}
}

// TestDirRank_MapsSingleAxisStepsToPriority checks the direction-priority
// ranking used by the summon path tie-break.
func TestDirRank_MapsSingleAxisStepsToPriority(t *testing.T) {
	o := Point3{X: 5, Y: 5}
	cases := []struct {
		to   Point3
		want int
	}{
		{Point3{X: 6, Y: 5}, 0}, // SE
		{Point3{X: 5, Y: 6}, 1}, // SW
		{Point3{X: 4, Y: 5}, 2}, // NW
		{Point3{X: 5, Y: 4}, 3}, // NE
		{Point3{X: 6, Y: 6}, 4}, // diagonal (not a legal single-axis step)
	}
	for _, c := range cases {
		if got := dirRank(o, c.to); got != c.want {
			t.Errorf("dirRank(%v->%v) = %d, want %d", o, c.to, got, c.want)
		}
	}
}

// TestNearestOpponentTieBreaksByInitiative verifies the wiki rule: on a
// distance tie, the summon targets the enemy with the HIGHEST initiative.
func TestNearestOpponentTieBreaksByInitiative(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	lowInit := b
	highInit := NewFighterFromBreed(9, b.TeamID, BreedFeca, "HighInit", 0, 0)
	f.registerFighter(highInit, 999)
	f.Timeline = NewTimeline([]*Fighter{a, b, highInit})
	a.Position = Point3{X: 0, Y: 0}
	lowInit.Position = Point3{X: 3, Y: 0}  // same distance
	highInit.Position = Point3{X: 0, Y: 3} // same distance
	lowInit.Characteristics[Init].Value = 10
	highInit.Characteristics[Init].Value = 90

	if got := f.nearestOpponent(a); got != highInit {
		t.Errorf("nearestOpponent on a distance tie = %v, want the higher-initiative enemy", got)
	}
}

// TestNearestOpponentPrefersSummonerOverEnemySummon verifies the wiki rule:
// on a distance+initiative tie, the summon prefers the summoner (a real
// fighter) over an enemy summon.
func TestNearestOpponentPrefersSummonerOverEnemySummon(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	summoner := b
	enemySummon := NewFighterFromBreed(9, b.TeamID, BreedFeca, "EnemySummon", 0, 0)
	enemySummon.Father = summoner // marks it as a summon
	f.registerFighter(enemySummon, 999)
	f.Timeline = NewTimeline([]*Fighter{a, b, enemySummon})
	a.Position = Point3{X: 0, Y: 0}
	summoner.Position = Point3{X: 3, Y: 0}    // same distance
	enemySummon.Position = Point3{X: 0, Y: 3} // same distance
	// Equal initiative so the summoner-preference rule is what decides.
	summoner.Characteristics[Init].Value = 50
	enemySummon.Characteristics[Init].Value = 50

	if got := f.nearestOpponent(a); got != summoner {
		t.Errorf("nearestOpponent on a full tie = %v, want the summoner (non-summon) over the enemy summon", got)
	}
}

// TestMinEnemyDistanceIgnoresInvisible verifies the flee/kite distance
// helper also treats invisible enemies as unseen.
func TestMinEnemyDistanceIgnoresInvisible(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Properties |= PropertyInvisible

	if got := f.minEnemyDistance(a, a.Position); got != -1 {
		t.Errorf("minEnemyDistance with only an invisible enemy = %d, want -1 (no seen enemies)", got)
	}
}

// --- #3: summons will not step on lethal traps ---

// TestSummonAvoidsKillerCellWhenApproaching verifies a summon walking toward
// its target routes so it does NOT end its move on a KILLER special cell.
func TestSummonAvoidsKillerCellWhenApproaching(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 6, Y: 0}
	b.Father = a
	b.Characteristics[MP].Value, b.Characteristics[MP].Max = 3, 3
	// The straight-line landing cell (3,0) is a killer cell; the summon must
	// pick a different, non-lethal cell to end on.
	f.SetSpecialCell(3, 0, SpecialCellKiller)

	f.moveSummonTowardNearestOpponent(b)

	if b.Position == (Point3{X: 3, Y: 0}) {
		t.Fatal("summon ended its move on a KILLER cell -- it must avoid lethal cells")
	}
	if b.IsDead {
		t.Fatal("summon walked onto a lethal cell and died")
	}
}

// TestSummonTrapCellLethalOnlyWhenItWouldKill verifies a TRAP cell is
// avoided only when its fixed damage would kill the summon; a summon with
// plenty of HP may still end on it.
func TestSummonTrapCellLethalOnlyWhenItWouldKill(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Father = a

	// Low HP (<= trapDamage): the trap is lethal -> avoided.
	b.Position = Point3{X: 3, Y: 0}
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = trapDamage, trapDamage
	if !f.cellIsLethalToSummon(b, Point3{X: 5, Y: 0}) {
		// (control: a plain cell is never lethal)
	}
	f.SetSpecialCell(5, 0, SpecialCellTrap)
	if !f.cellIsLethalToSummon(b, Point3{X: 5, Y: 0}) {
		t.Error("a TRAP cell should be lethal to a summon whose HP <= trapDamage")
	}

	// High HP: same trap cell is survivable -> not avoided.
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 100, 100
	if f.cellIsLethalToSummon(b, Point3{X: 5, Y: 0}) {
		t.Error("a TRAP cell should NOT be lethal to a summon that survives its damage")
	}

	// The summon's own current cell is never treated as lethal-to-avoid.
	f.SetSpecialCell(3, 0, SpecialCellKiller)
	if f.cellIsLethalToSummon(b, b.Position) {
		t.Error("a summon's current cell must never be treated as lethal-to-avoid")
	}
}
