package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestCloseCombat verifies the weapon attack: it hits an adjacent enemy for the
// breed's close-combat element damage (crit boosts it), spends AP even on a
// fumble, and is a no-op against a non-adjacent cell or an ally.
func TestCloseCombat(t *testing.T) {
	mk := func() (*Fight, *FightFighter, *FightFighter) {
		attacker := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70,
			AP: 6, MaxAP: 6, Fighter: &domain.Fighter{BreedID: 8}} // Iop -> earth close-combat
		victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 50, MaxHP: 50}
		f := &Fight{Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{attacker}},
			{ID: 1, Fighters: []*FightFighter{victim}},
		}}
		f.Timeline = []*FightFighter{attacker, victim}
		f.turnIndex = 0
		f.setPhase(PhaseAction)
		return f, attacker, victim
	}

	// Normal hit: 5 damage, AP 6 -> 1.
	f, a, v := mk()
	a.CritRate, a.FumbleRate = 0, 0
	f.closeCombat(a, v.Pos)
	if v.HP != 45 {
		t.Errorf("normal close-combat: HP=%d, want 45 (5 dmg)", v.HP)
	}
	if a.AP != 1 {
		t.Errorf("close-combat AP=%d, want 1 (6-5)", a.AP)
	}

	// Critical: 7 damage.
	f, a, v = mk()
	a.CritRate, a.FumbleRate = 100, 0
	f.closeCombat(a, v.Pos)
	if v.HP != 43 {
		t.Errorf("crit close-combat: HP=%d, want 43 (7 dmg)", v.HP)
	}

	// Fumble: no damage, AP still spent.
	f, a, v = mk()
	a.FumbleRate = 100
	f.closeCombat(a, v.Pos)
	if v.HP != 50 || a.AP != 1 {
		t.Errorf("fumble close-combat: HP=%d AP=%d, want 50/1 (no damage, AP spent)", v.HP, a.AP)
	}

	// Non-adjacent target: no-op.
	f, a, v = mk()
	f.closeCombat(a, Pos{X: 10, Y: 15}) // distance 3
	if v.HP != 50 || a.AP != 6 {
		t.Errorf("non-adjacent close-combat should be a no-op: HP=%d AP=%d", v.HP, a.AP)
	}

	// Ally on the adjacent cell: no-op (only enemies).
	f, a, v = mk()
	v.TeamID = 0
	f.closeCombat(a, v.Pos)
	if v.HP != 50 || a.AP != 6 {
		t.Errorf("close-combat on an ally should be a no-op: HP=%d AP=%d", v.HP, a.AP)
	}
}

// TestCloseCombatLayout verifies the 8112 (aAD) wire sizes: 17 bytes on a fumble,
// 28 on a normal/crit hit.
func TestCloseCombatLayout(t *testing.T) {
	frame, err := buildCloseCombat(1, 100, Pos{X: 5, Y: 6, Z: 1}, true, false)
	if err != nil {
		t.Fatal(err)
	}
	if got := len(frame[4:]); got != 17 {
		t.Errorf("fumble close-combat payload = %d bytes, want 17", got)
	}
	frame, _ = buildCloseCombat(1, 100, Pos{X: 5, Y: 6, Z: 1}, false, true)
	if got := len(frame[4:]); got != 28 {
		t.Errorf("normal close-combat payload = %d bytes, want 28", got)
	}
}

// TestBreedCloseCombatElementGroups pins the per-breed close-combat element to the
// client's own breed table (enum xq, 9th ctor arg = an fv_1 element), which splits
// the 12 breeds cleanly 3/3/3/3. Cra was previously listed as water, making water a
// 4-breed group and air a 2-breed one, so every Cra punch hit the wrong resistance.
func TestBreedCloseCombatElementGroups(t *testing.T) {
	want := map[int][]uint8{
		elemFire:  {5, 7, 12}, // Xelor, Eniripsa, Pandawa   (fv_1.bam)
		elemWater: {1, 3, 6},  // Feca, Enutrof, Ecaflip     (fv_1.ban)
		elemAir:   {4, 9, 11}, // Sram, Cra, Sacrier         (fv_1.bao)
		elemEarth: {2, 8, 10}, // Osamodas, Iop, Sadida      (fv_1.bap)
	}
	got := map[int][]uint8{}
	for id := uint8(1); id <= 12; id++ {
		b := breedBase(id)
		got[b.CCElement] = append(got[b.CCElement], id)
	}
	for elem, breeds := range want {
		if len(got[elem]) != 3 {
			t.Errorf("element %d has %v, want exactly the 3 breeds %v", elem, got[elem], breeds)
		}
		for _, id := range breeds {
			if breedBase(id).CCElement != elem {
				t.Errorf("breed %d close-combat element = %d, want %d",
					id, breedBase(id).CCElement, elem)
			}
		}
	}
}

// TestBreedBaseHPMatchesClient locks the HP/AP/MP the client derives (xq ctor args
// 2/3/4); a mismatch desyncs damage numbers and death timing.
func TestBreedBaseHPMatchesClient(t *testing.T) {
	wantHP := map[uint8]int32{1: 70, 2: 65, 3: 65, 4: 70, 5: 60, 6: 70,
		7: 60, 8: 75, 9: 65, 10: 65, 11: 80, 12: 75}
	for id, hp := range wantHP {
		b := breedBase(id)
		if b.HP != hp {
			t.Errorf("breed %d HP = %d, want %d", id, b.HP, hp)
		}
		if b.AP != 6 || b.MP != 3 {
			t.Errorf("breed %d AP/MP = %d/%d, want 6/3", id, b.AP, b.MP)
		}
	}
}

// TestBreedTableMatchesClient pins every breed's base stats to the 2.70 client's
// own breed enum `xq`. The positions are fixed by v2.04b's UNOBFUSCATED twin of the
// same table (Breed.java), which lines up argument for argument:
//
//	id, HP, AP, MP, INITIATIVE, critBonus, fumbleMalus, VALUE, element, 5, 5, 7
//
// Our table used to be the 2.04b one, so every initiative was the 2006 value and
// turn order was built on it. Anything that edits these numbers must justify itself
// against the client, not against v2.04b.
func TestBreedTableMatchesClient(t *testing.T) {
	// {HP, init} straight off xq's 2nd and 5th ctor args.
	want := map[uint8][2]int32{
		1: {70, 20}, 2: {65, 40}, 3: {65, 60}, 4: {70, 50},
		5: {60, 80}, 6: {70, 70}, 7: {60, 0}, 8: {75, 10},
		9: {65, 75}, 10: {65, 30}, 11: {80, 90}, 12: {75, 100},
	}
	seen := map[int32]uint8{}
	for id, exp := range want {
		b := breedBase(id)
		if b.HP != exp[0] {
			t.Errorf("breed %d HP = %d, want %d", id, b.HP, exp[0])
		}
		if b.Init != exp[1] {
			t.Errorf("breed %d initiative = %d, want %d (2.70 value; 2.04b's differ)",
				id, b.Init, exp[1])
		}
		if prev, dup := seen[b.Init]; dup {
			t.Errorf("breeds %d and %d share initiative %d; 2.70's values are unique per breed",
				prev, id, b.Init)
		}
		seen[b.Init] = id
	}
}

// TestBreedBaseValueMatchesClient: the breed's contribution to a fighter's budget
// is xq's 8th ctor arg â€” 600 in 2.70, 400 in v2.04b (whose Breed.java exposes that
// very position as getValue()).
func TestBreedBaseValueMatchesClient(t *testing.T) {
	if breedBaseValue != 600 {
		t.Errorf("breedBaseValue = %d, want 600 (2.70; 400 was the 2.04b figure)", breedBaseValue)
	}
}

// TestBreedTackleStatsMatchClient pins block/dodge to the client's breed table.
// Dodge is 100 for every breed, which the client's help text states independently
// ("Tous les personnages ont, de base, 100%% en esquive") â€” an external check that
// xq's 14th ctor arg is identified correctly. Block tracks class identity.
func TestBreedTackleStatsMatchClient(t *testing.T) {
	wantBlock := map[uint8]int32{
		1: 60, 2: 0, 3: 0, 4: 20, 5: 0, 6: 0,
		7: 0, 8: 40, 9: 0, 10: 0, 11: 20, 12: 40,
	}
	for id, want := range wantBlock {
		b := breedBase(id)
		if b.Block != want {
			t.Errorf("breed %d block = %d, want %d", id, b.Block, want)
		}
		if b.Dodge != 100 {
			t.Errorf("breed %d dodge = %d, want 100 for every breed", id, b.Dodge)
		}
	}
	// The blocker classes must actually be able to hold someone, and the rest
	// must not â€” that is the whole point of the stat.
	if breedBase(1).Block <= breedBase(9).Block {
		t.Error("a Feca must block better than a Cra")
	}
	if breedBase(9).Block != 0 {
		t.Error("a Cra should never hold anyone (block 0)")
	}
}
