package gamedata

import (
	"bytes"
	"encoding/binary"
	"testing"
)

// buildSpellRecord encodes a co_1 record payload (DATA-FORMAT §4): the scalar
// header (fields 1-19), an empty criterion string (field 20), then the embedded
// effect list. Reuses the testEffect encoder from fightercards_test.go.
func buildSpellRecord(id, breed, value int32, ap int8, effects ...testEffect) []byte {
	var b bytes.Buffer
	w := func(v any) { _ = binary.Write(&b, binary.BigEndian, v) }
	w(id)       // 1 id
	w(breed)    // 2 breedId
	w(value)    // 3 value
	w(int32(0)) // 4 aiTargetId
	w(int32(0)) // 5 scriptId
	w(ap)       // 6 actionPoints
	w(int8(0))  // 7 castFreq maxPerPlayer
	w(int8(0))  // 8 castFreq minInterval
	w(int8(0))  // 9 castFreq maxPerTurn
	w(int8(0))  // 10 castFreq
	w(int8(0))  // 11 castFreq
	w(int8(5))  // 12 rangeMax (stored max,min)
	w(int8(1))  // 13 rangeMin
	for i := 0; i < 6; i++ {
		w(uint8(0)) // 14-19 bools
	}
	w(int32(0)) // 20 criterion (empty)
	w(int32(len(effects)))
	for _, e := range effects {
		blob := e.encode()
		w(e.effectID)
		w(int16(1))
		w(int32(len(blob)))
		b.Write(blob)
	}
	return b.Bytes()
}

func TestSpellDamageDecode(t *testing.T) {
	// Fire spell: 25 fire damage (action 131 = "…par sort" fire) + an AP-loss
	// rider (16) which must NOT count as damage.
	sp, err := decodeSpell(buildSpellRecord(4, 8, 200, 3,
		testEffect{9001, 131, "SPELL", []float32{25}},
		testEffect{9002, 16, "SPELL", []float32{1}},
	))
	if err != nil {
		t.Fatal(err)
	}
	if sp.ID != 4 || sp.BreedID != 8 || sp.Value != 200 || sp.AP != 3 {
		t.Fatalf("header = %+v, want id4 breed8 value200 ap3", sp)
	}
	// Range is at fields 12/13 (stored max,min), normalized to [min,max].
	if sp.RangeMin != 1 || sp.RangeMax != 5 {
		t.Errorf("range = %d-%d, want 1-5", sp.RangeMin, sp.RangeMax)
	}
	amount, elem, ok := sp.Damage()
	if !ok || amount != 25 || elem != ElementFire {
		t.Errorf("Damage() = %d elem=%d ok=%v, want 25/fire", amount, elem, ok)
	}
	if sp.IsHeal() {
		t.Error("fire spell wrongly flagged as heal")
	}
}

func TestSpellDamageDirectAndLeech(t *testing.T) {
	// Two direct-damage effects (neutral loss id 1 + water leech id 9) sum.
	sp, _ := decodeSpell(buildSpellRecord(50, 11, 200, 4,
		testEffect{1, 1, "SPELL", []float32{10}},
		testEffect{2, 9, "SPELL", []float32{7}},
	))
	if amount, _, ok := sp.Damage(); !ok || amount != 17 {
		t.Errorf("Damage() = %d ok=%v, want 17 (10 loss + 7 leech)", amount, ok)
	}
}

func TestSpellUtilityAndHeal(t *testing.T) {
	// Utility spell: only a DMG% buff (action 82) — deals no flat damage.
	util, _ := decodeSpell(buildSpellRecord(99, 1, 150, 3,
		testEffect{1, 82, "SPELL", []float32{15}},
	))
	if _, _, ok := util.Damage(); ok {
		t.Error("utility spell should deal 0 flat damage")
	}
	if util.IsHeal() {
		t.Error("buff spell wrongly flagged as heal")
	}
	// Heal spell (action 69).
	heal, _ := decodeSpell(buildSpellRecord(100, 7, 100, 3,
		testEffect{1, actionHeal, "SPELL", []float32{20}},
	))
	if !heal.IsHeal() {
		t.Error("heal spell not detected")
	}
	if _, _, ok := heal.Damage(); ok {
		t.Error("heal spell should deal 0 flat damage")
	}
}
