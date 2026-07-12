package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// TestCharacteristic_AddMax_ClampsValueDown mirrors
// FighterCharacteristic.setMax()'s "if currentValue > maxValue, clamp it
// down" behavior.
func TestCharacteristic_AddMax_ClampsValueDown(t *testing.T) {
	c := &Characteristic{Value: 6, Max: 6}
	c.AddMax(-3)
	if c.Max != 3 {
		t.Errorf("Max after AddMax(-3) = %d, want 3", c.Max)
	}
	if c.Value != 3 {
		t.Errorf("Value after Max shrank below it = %d, want clamped to 3", c.Value)
	}
}

// TestCharacteristic_AddMax_DoesNotRaiseValueOnItsOwn confirms AddMax
// alone (without a separate Add call) never increases Value -- this is
// the crux of the CharacDebuff.unapply()/CharacBuff semantics: raising Max
// back up after a debuff expires must NOT hand the fighter free resource
// they hadn't earned back via a real regen tick.
func TestCharacteristic_AddMax_DoesNotRaiseValueOnItsOwn(t *testing.T) {
	c := &Characteristic{Value: 2, Max: 3}
	c.AddMax(3) // Max 3->6, Value should stay 2 (not jump to 6)
	if c.Value != 2 {
		t.Errorf("Value after AddMax(+3) with room to spare = %d, want unchanged 2", c.Value)
	}
	if c.Max != 6 {
		t.Errorf("Max after AddMax(+3) = %d, want 6", c.Max)
	}
}

func TestEffectCharacBuff_RaisesMaxAndValue(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	_ = b

	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	def := runningEffectDef{Kind: EffectCharacBuff, Charc: AP}
	f.applyRunningEffect(a, a, def, effectDefWithParams(2), -1)

	if a.Characteristics[AP].Max != 8 {
		t.Errorf("AP.Max after CharacBuff(+2) = %d, want 8", a.Characteristics[AP].Max)
	}
	if a.Characteristics[AP].Value != 8 {
		t.Errorf("AP.Value after CharacBuff(+2) = %d, want 8 (buff raises both)", a.Characteristics[AP].Value)
	}
}

func TestEffectCharacGain_DoesNotRaiseMax(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	_ = b

	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 4

	def := runningEffectDef{Kind: EffectCharacGain, Charc: AP}
	f.applyRunningEffect(a, a, def, effectDefWithParams(10), -1) // try to gain 10, but capped by existing Max=6

	if a.Characteristics[AP].Max != 6 {
		t.Errorf("AP.Max after CharacGain = %d, want unchanged 6", a.Characteristics[AP].Max)
	}
	if a.Characteristics[AP].Value != 6 {
		t.Errorf("AP.Value after CharacGain(+10, capped) = %d, want clamped to existing Max 6", a.Characteristics[AP].Value)
	}
}

func TestEffectCharacDebuff_LowersMaxNotJustValue(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	_ = b

	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	def := runningEffectDef{Kind: EffectCharacDebuff, Charc: AP}
	f.applyRunningEffect(b, a, def, effectDefWithParams(2), -1)

	if a.Characteristics[AP].Max != 4 {
		t.Errorf("AP.Max after CharacDebuff(-2) = %d, want 4", a.Characteristics[AP].Max)
	}
	if a.Characteristics[AP].Value != 4 {
		t.Errorf("AP.Value after CharacDebuff shrank Max below it = %d, want clamped to 4", a.Characteristics[AP].Value)
	}
}

func TestEffectCharacLeech_StealsMinOfRequestedAndCurrent(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)

	b.Characteristics[AP].Max = 6
	b.Characteristics[AP].Value = 1 // target only has 1 AP left
	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 3

	def := runningEffectDef{Kind: EffectCharacLeech, Charc: AP}
	// Request to leech 5, but target only has 1 -> leech exactly 1.
	f.applyRunningEffect(a, b, def, effectDefWithParams(5), -1)

	if b.Characteristics[AP].Max != 5 {
		t.Errorf("target AP.Max after leech = %d, want 5 (only 1 stolen, not the requested 5)", b.Characteristics[AP].Max)
	}
	if a.Characteristics[AP].Value != 4 {
		t.Errorf("caster AP.Value after leech = %d, want 4 (3 + 1 stolen)", a.Characteristics[AP].Value)
	}
}

func effectDefWithParams(v float32) gamedata.EffectDef {
	return gamedata.EffectDef{Params: []float32{v}}
}
