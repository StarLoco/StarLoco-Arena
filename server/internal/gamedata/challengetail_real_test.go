package gamedata

import (
	"os"
	"testing"
)

// TestChallengeTailReal locks the challenge record now that `np_1` is decodable.
//
// 36 of the 39 records are consumed EXACTLY. The three that are not are
// characterised rather than hand-waved: challenges 29/30/31 each carry a type-12
// parameter ("Lance un effet sur tous les combattants à la création du combat")
// whose trailing `Ht` effect is inline and has no length prefix, so it cannot be
// skipped without a full effect parser. decodeParameters stops there by design.
func TestChallengeTailReal(t *testing.T) {
	if _, err := os.Stat(clientBdataDir + `\data.bdat`); err != nil {
		t.Skip("no client data")
	}
	st, err := Open(clientBdataDir)
	if err != nil {
		t.Fatal(err)
	}

	blockedByInlineEffect := map[int32]bool{29: true, 30: true, 31: true}
	var total, exact int
	var withVictory, withBonuses int
	for _, e := range st.EntriesOf(TypeChallengeDef) {
		rec, err := st.ReadRecord(e.Position)
		if err != nil {
			continue
		}
		total++
		c := &cur{b: rec.Data}
		ch := decodeChallengeCursor(c)
		if ch == nil {
			t.Errorf("challenge at %d failed to decode", e.Position)
			continue
		}
		resid := len(rec.Data) - c.pos
		if blockedByInlineEffect[ch.ID] {
			if resid == 0 {
				t.Errorf("challenge %d now decodes fully — an inline-Ht parser must have "+
					"landed; move it out of the blocked set", ch.ID)
			}
			continue
		}
		if c.err {
			t.Errorf("challenge %d: short read", ch.ID)
			continue
		}
		if resid != 0 {
			t.Errorf("challenge %d: %d byte(s) left over", ch.ID, resid)
			continue
		}
		exact++
		for _, p := range ch.Bonuses {
			if p.Type == ParamTypeVictoryCondition {
				withVictory++
			}
		}
		if len(ch.Bonuses) > 0 {
			withBonuses++
		}
	}

	if total != 39 {
		t.Errorf("read %d challenges, want 39", total)
	}
	if want := total - len(blockedByInlineEffect); exact != want {
		t.Errorf("%d/%d challenges consumed exactly, want %d", exact, total, want)
	}

	// The type-14 "Condition de victoire" elements are the reason this record
	// needed a per-type layout at all: nine challenges carry one, and decoding
	// them with the generic np_1 layout desynchronised the whole tail.
	if withVictory != 9 {
		t.Errorf("%d victory-condition parameters, want 9", withVictory)
	}
	if withBonuses < withVictory {
		t.Errorf("bonus-bearing challenges (%d) < victory conditions (%d)", withBonuses, withVictory)
	}
}

// TestVictoryConditionLayout pins the type-14 element against the client's own
// size functions: wi_0.nj() = 12 + mp_2.nj(), and mp_2.nj() = 7 + 4*len + 6.
func TestVictoryConditionLayout(t *testing.T) {
	w := []byte{1}
	put32 := func(v int32) { w = append(w, byte(v>>24), byte(v>>16), byte(v>>8), byte(v)) }
	put32(ParamTypeVictoryCondition)
	put32(355) // id
	put32(0)   // parent
	// mp_2: [i16 type][i32 id][u8 n][i32 x n][u8 flag][i32 value][u8 grade]
	w = append(w, 0x04, 0x00) // type 1024 — the value that used to be misread as an effect version
	put32(99)
	w = append(w, 1)
	put32(7)
	w = append(w, 1)
	put32(42)
	w = append(w, 3)

	c := &cur{b: w}
	got, ok := decodeParameters(c)
	if !ok {
		t.Fatal("decodeParameters failed on a victory-condition element")
	}
	if len(got) != 1 || got[0].Victory == nil {
		t.Fatalf("got %+v, want one element with a victory condition", got)
	}
	v := got[0].Victory
	if v.Type != 1024 || v.ID != 99 || len(v.Params) != 1 || v.Params[0] != 7 ||
		!v.Flag || v.Value != 42 || v.Grade != 3 {
		t.Errorf("victory condition = %+v", v)
	}
	// wi_0.nj() = 12 + mp_2.nj() = 12 + (7 + 4*1 + 6) = 29, after the count byte.
	if want := 1 + 12 + 7 + 4 + 6; c.pos != want {
		t.Errorf("consumed %d bytes, want %d (wi_0.nj() + count byte)", c.pos, want)
	}
	// And it must NOT be mistaken for an effect-bearing element.
	if got[0].HasEffect() {
		t.Error("a victory condition was read as carrying a trailing effect")
	}
}
