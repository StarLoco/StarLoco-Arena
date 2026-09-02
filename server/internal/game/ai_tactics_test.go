package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestAISpendsAPEfficiently: greedy-by-raw-damage wastes a turn. With 6 AP, a
// 2-AP/30 spell beats a 4-AP/40 one - three casts is 90, greedy scores 70.
//
// Measured as TOTAL damage over the turn rather than by inspecting the choice,
// so it tests the outcome a player would notice.
func TestAISpendsAPEfficiently(t *testing.T) {
	f, caster, enemy := summonTestFight()
	efficient := dmgSpell(700, 2, 1, 12, 30) // 15 dmg/AP
	brutal := dmgSpell(701, 4, 1, 12, 40)    // 10 dmg/AP
	f.deps.Spells = gamedata.NewSpells(efficient, brutal)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 700}, {SpellID: 701}}}
	caster.AP, caster.MaxAP = 6, 6
	enemy.HP, enemy.MaxHP = 100000, 100000 // never dies: measure pure throughput

	before := enemy.HP
	f.castAISpellRepeatedly(caster)
	dealt := before - enemy.HP

	// Greedy-by-damage spends 4+2 and deals 70. Efficiency spends 2+2+2 for 90.
	if dealt < 90 {
		t.Errorf("dealt %d over the turn; an AP-efficient line reaches 90 "+
			"(3 x 30 at 2 AP) while greedy-by-raw-damage stops at 70", dealt)
	}
	if caster.AP != 0 {
		t.Errorf("AP left = %d, want 0", caster.AP)
	}
}

// TestAITakesTheKillOverEfficiency: efficiency is the rule UNTIL something dies.
// A finisher beats a better damage-per-AP line, because a dead enemy stops
// attacking.
func TestAITakesTheKillOverEfficiency(t *testing.T) {
	f, caster, enemy := summonTestFight()
	efficient := dmgSpell(702, 2, 1, 12, 30) // better dmg/AP, does NOT kill
	finisher := dmgSpell(703, 4, 1, 12, 40)  // worse dmg/AP, DOES kill
	f.deps.Spells = gamedata.NewSpells(efficient, finisher)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 702}, {SpellID: 703}}}
	caster.AP, caster.MaxAP = 4, 4
	enemy.HP, enemy.MaxHP = 35, 100 // 30 leaves it alive; 40 kills

	got := f.chooseAISpell(caster, enemy)
	if got != 703 {
		t.Errorf("chose spell %d, want the finisher 703 - a kill beats a better "+
			"damage-per-AP line", got)
	}
}

// TestAIPrefersTheSaferFiringCell: given two cells it can shoot from equally
// well, take the one that is not standing next to an enemy.
func TestAIPrefersTheSaferFiringCell(t *testing.T) {
	f, caster, enemy := summonTestFight()

	exposed := Pos{X: enemy.Pos.X + 1, Y: enemy.Pos.Y} // adjacent to the enemy
	safe := caster.Pos                                 // wherever it already is

	riskExposed := f.aiCellRisk(caster, exposed)
	riskSafe := f.aiCellRisk(caster, safe)
	if riskExposed <= riskSafe {
		t.Fatalf("fixture: the 'exposed' cell (risk %d) is not riskier than the "+
			"'safe' one (risk %d), so this proves nothing", riskExposed, riskSafe)
	}
}

// TestAIAvoidsItsOwnTrapWhenItCan: a known friendly trap makes a cell less
// attractive - but must NOT make it unreachable, because a human steps on their
// own trap rather than refusing to move.
func TestAIAvoidsItsOwnTrapWhenItCan(t *testing.T) {
	f, caster, _ := summonTestFight()
	cell := Pos{X: caster.Pos.X + 1, Y: caster.Pos.Y}

	ally := &FightFighter{WireID: 66, TeamID: caster.TeamID, Pos: Pos{X: 1, Y: 1}, HP: 50}
	f.Teams[caster.TeamID].Fighters = append(f.Teams[caster.TeamID].Fighters, ally)

	clean := f.aiCellRisk(caster, cell)
	f.effectAreas = append(f.effectAreas, &effectArea{
		id: 3, center: cell, caster: ally,
		tmpl: &gamedata.StaticEffect{ID: 9002, Type: "TRAP", AreaShape: 2, AreaSize: []int32{0}, MaxExec: 63},
	})
	trapped := f.aiCellRisk(caster, cell)

	if trapped <= clean {
		t.Errorf("a known friendly trap did not raise the cell's risk (%d -> %d)",
			clean, trapped)
	}
	// Still reachable: risk is a preference, not a veto.
	if f.aiCellIsSuicideFor(caster, cell) {
		t.Error("a friendly TRAP made the cell count as suicide; only lethal " +
			"hazards may do that, or the AI will refuse to move through its own field")
	}
}

// TestAIMovesToTheSaferFiringCell is the behavioural half of the risk rule: not
// "aiCellRisk returns a bigger number" but "the AI actually walks somewhere else
// because of it".
//
// Geometry: from (7,15) the fighter can fire on the enemy at (12,15) from either
// (9,15) or (10,15), with an identical firing gap. (9,15) is the SHORTER walk, so
// without the safety rule it wins on path length. Putting a second enemy beside
// (9,15) must flip the choice - which is exactly the judgement a player makes
// when they decline the closer cell because it is next to someone.
func TestAIMovesToTheSaferFiringCell(t *testing.T) {
	f, caster, enemy := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(dmgSpell(801, 3, 1, 3, 20))
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 801}}}
	caster.MP, caster.AP = 3, 6

	near := Pos{X: 9, Y: 15} // shorter walk, about to become dangerous
	far := Pos{X: 10, Y: 15} // longer walk, safe

	// Fixture: both must be equally good firing cells, or "safer" is not the
	// thing being tested.
	if !f.aiCanFireFrom(caster, near, enemy) || !f.aiCanFireFrom(caster, far, enemy) {
		t.Skip("arena geometry changed; both cells must be able to fire")
	}
	if f.aiFiringGap(caster, near, enemy) != f.aiFiringGap(caster, far, enemy) {
		t.Skip("arena geometry changed; the two cells must score equally")
	}

	// A second enemy stands beside the closer cell.
	lurker := &FightFighter{
		WireID: 99, TeamID: enemy.TeamID,
		Pos: Pos{X: 9, Y: 16},
		HP:  50, MaxHP: 50,
	}
	f.Teams[enemy.TeamID].Fighters = append(f.Teams[enemy.TeamID].Fighters, lurker)

	if f.aiCellRisk(caster, near) <= f.aiCellRisk(caster, far) {
		t.Fatalf("fixture: the near cell (risk %d) is not riskier than the far one "+
			"(risk %d)", f.aiCellRisk(caster, near), f.aiCellRisk(caster, far))
	}

	f.moveIntoSpellRange(caster, enemy)

	if caster.Pos == near {
		t.Errorf("the AI stopped at %v, right beside an enemy, when %v fires just "+
			"as well and is clear", caster.Pos, far)
	}
	if !f.aiCanFireFrom(caster, caster.Pos, enemy) {
		t.Errorf("the AI ended at %v where it cannot fire; safety must not cost it "+
			"the shot", caster.Pos)
	}
}
