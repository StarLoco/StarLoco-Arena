package gamedata

import (
	"bytes"
	"encoding/binary"
	"math"
	"testing"
)

// testEffect describes one embedded effect for the synthetic record encoder.
type testEffect struct {
	effectID  int32
	actionID  int32
	container string
	params    []float32
}

// encode writes one Ht effect blob (DATA-FORMAT §3): effectId, actionId,
// parentId, parentType string, areaShape, areaOrdering, 5 bools, params.
func (e testEffect) encode() []byte {
	var b bytes.Buffer
	w := func(v any) { _ = binary.Write(&b, binary.BigEndian, v) }
	w(e.effectID)              // field 1 effectId
	w(e.actionID)              // field 2 actionId
	w(int32(0))                // field 3 parentId
	w(int32(len(e.container))) // field 4 parentType length
	b.WriteString(e.container) //          parentType bytes
	w(int32(0))                // field 5 areaShape
	w(int16(0))                // field 6 areaOrdering
	for i := 0; i < 5; i++ {   // fields 7-11 bools
		w(uint8(0))
	}
	w(int32(len(e.params))) // field 12 params count
	for _, p := range e.params {
		w(math.Float32bits(p))
	}
	return b.Bytes()
}

// buildFighterCardRecord encodes a minimal uh_0 record payload (DATA-FORMAT §5):
// the fixed 33-byte header then the embedded effect list.
func buildFighterCardRecord(id int32, typ int16, value int32, effects ...testEffect) []byte {
	var b bytes.Buffer
	w := func(v any) { _ = binary.Write(&b, binary.BigEndian, v) }
	w(id)       // 1  id
	w(typ)      // 2  type
	w(int8(0))  // 3  weaponActionPoints
	w(value)    // 4  value
	w(int32(0)) // 5  rangeMin
	w(int32(0)) // 6  rangeMax
	w(int32(0)) // 7  scriptId
	w(int32(0)) // 8  subType
	for i := 0; i < 6; i++ {
		w(uint8(0)) // 9-14 bools
	}
	w(int32(len(effects))) // effect count
	for _, e := range effects {
		blob := e.encode()
		w(e.effectID)       // wrapper innerId
		w(int16(1))         // wrapper innerVer
		w(int32(len(blob))) // wrapper blobLen
		b.Write(blob)
	}
	return b.Bytes()
}

// TestDecodeFighterCardBonus locks the full record→bonus decode path (33-byte
// header, effect-list wrapper, effectId-leading blob, containerType, params)
// without needing the copyrighted client data. It also asserts a FIGHTER_CARD_USE
// (active) effect is NOT folded into the passive bonus.
func TestDecodeFighterCardBonus(t *testing.T) {
	rec := buildFighterCardRecord(500, 3, 300,
		testEffect{9001, actionHPBoost, "FIGHTER_CARD_EQUIP", []float32{40}},
		testEffect{9002, actionAPBoost, "FIGHTER_CARD_EQUIP", []float32{1}},
		testEffect{9003, 5, "FIGHTER_CARD_USE  ", []float32{15}}, // active ability: must be ignored
		testEffect{9004, actionMPBoost, "FIGHTER_CARD_EQUIP", []float32{2}},
		testEffect{9005, actionInitBoost, "FIGHTER_CARD_EQUIP", []float32{60}},
		testEffect{9006, actionRangeGain, "FIGHTER_CARD_EQUIP", []float32{2}},
		testEffect{9007, 33, "FIGHTER_CARD_EQUIP", []float32{15}}, // dmg%: not a HP/AP/MP/init/range stat, ignored
	)
	card := decodeFighterCard(rec)
	if card == nil {
		t.Fatal("decodeFighterCard returned nil")
	}
	if card.ID != 500 || card.Type != 3 || card.Value != 300 {
		t.Errorf("header = id%d type%d value%d, want 500/3/300", card.ID, card.Type, card.Value)
	}
	want := FighterStatBonus{HP: 40, AP: 1, MP: 2, Init: 60, Range: 2}
	if card.Bonus != want {
		t.Errorf("bonus = %+v, want %+v", card.Bonus, want)
	}
}

// TestDecodeFighterCardNoEffects: a well-formed record with no effects decodes to
// a zero bonus (and not nil).
func TestDecodeFighterCardNoEffects(t *testing.T) {
	card := decodeFighterCard(buildFighterCardRecord(7, 1, 100))
	if card == nil {
		t.Fatal("nil for a valid no-effect record")
	}
	if !card.Bonus.IsZero() {
		t.Errorf("bonus = %+v, want zero", card.Bonus)
	}
}

// TestDecodeFighterCardTruncated: a too-short record degrades to nil rather than
// panicking (the cursor trips its bounds guard).
func TestDecodeFighterCardTruncated(t *testing.T) {
	if card := decodeFighterCard([]byte{0, 0, 0, 1, 2}); card != nil {
		t.Errorf("truncated record decoded to %+v, want nil", card)
	}
}
