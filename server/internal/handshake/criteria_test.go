package handshake

import (
	"encoding/binary"
	"testing"
)

// decodeCriteriaBlob mimics the client's aez_0.O(): read [i16 byteLen], then
// consume 4 bytes per pair while n*4 < byteLen. It reports how many bytes it
// consumed in total so a test can prove the client would stop exactly at the end
// of the blob and not read on into the following descriptor fields.
func decodeCriteriaBlob(t *testing.T, blob []byte) (pairs map[uint16]uint16, consumed int) {
	t.Helper()
	if len(blob) < 2 {
		t.Fatalf("blob too short: %d bytes", len(blob))
	}
	byteLen := int(int16(binary.BigEndian.Uint16(blob[0:2])))
	pairs = map[uint16]uint16{}
	consumed = 2
	for n := 0; n*4 < byteLen; n++ {
		if consumed+4 > len(blob) {
			// This is precisely the client's BufferUnderflowException path, which
			// aez_0.b() swallows by returning false -> the coach never materialises.
			t.Fatalf("byteLen %d overruns the blob (%d bytes): the client would read "+
				"into the next descriptor section and the coach would fail to load",
				byteLen, len(blob))
		}
		id := binary.BigEndian.Uint16(blob[consumed : consumed+2])
		val := binary.BigEndian.Uint16(blob[consumed+2 : consumed+4])
		pairs[id] = val
		consumed += 4
	}
	return pairs, consumed
}

// TestCriteriaBlobLengthIsExact is the load-bearing test for this blob. The
// client loops on byteLen with NO bounds check, so byteLen must equal exactly
// 4×pairs: overstate it and every later descriptor field is garbage (or the
// coach silently fails to load).
func TestCriteriaBlobLengthIsExact(t *testing.T) {
	cases := [][]Criterion{
		nil,
		{},
		{{ID: 214, Value: 1}},
		{{ID: 214, Value: 1}, {ID: 215, Value: 1}, {ID: 216, Value: 1}, {ID: 217, Value: 1}},
		{{ID: 213, Value: 1}, {ID: criterionZaapUnlock, Value: 1}},
		// Entries that must be filtered out entirely:
		{{ID: 0, Value: 1}, {ID: MaxCriterionID + 1, Value: 1}, {ID: 2034, Value: 1}, {ID: 300, Value: 0}},
		// Duplicates must collapse to one pair.
		{{ID: 214, Value: 1}, {ID: 214, Value: 1}, {ID: 214, Value: 1}},
	}
	for i, in := range cases {
		blob := buildCriteriaBlob(in)
		byteLen := int(int16(binary.BigEndian.Uint16(blob[0:2])))
		if got, want := len(blob), 2+byteLen; got != want {
			t.Errorf("case %d: blob is %d bytes but byteLen implies %d", i, got, want)
		}
		if byteLen%4 != 0 {
			t.Errorf("case %d: byteLen %d is not a multiple of 4", i, byteLen)
		}
		pairs, consumed := decodeCriteriaBlob(t, blob)
		if consumed != len(blob) {
			t.Errorf("case %d: client would consume %d of %d bytes", i, consumed, len(blob))
		}
		// The Zaap criterion must survive every case, or the island Zaap re-locks.
		if pairs[criterionZaapUnlock] != 1 {
			t.Errorf("case %d: criterion %d missing (pairs=%v)", i, criterionZaapUnlock, pairs)
		}
	}
}

// TestCriteriaBlobFiltersAndDedups pins the filtering rules.
func TestCriteriaBlobFiltersAndDedups(t *testing.T) {
	blob := buildCriteriaBlob([]Criterion{
		{ID: 214, Value: 1},
		{ID: 214, Value: 7},                // duplicate: first wins
		{ID: 0, Value: 1},                  // invalid id
		{ID: MaxCriterionID + 1, Value: 1}, // above the criterion enum
		{ID: 2034, Value: 1},               // server bookkeeping range
		{ID: 300, Value: 0},                // zero value = not achieved
	})
	pairs, _ := decodeCriteriaBlob(t, blob)

	if pairs[214] != 1 {
		t.Errorf("criterion 214 = %d, want 1 (first duplicate wins)", pairs[214])
	}
	for _, bad := range []uint16{0, MaxCriterionID + 1, 2034, 300} {
		if _, present := pairs[bad]; present {
			t.Errorf("criterion %d should have been filtered out", bad)
		}
	}
	if len(pairs) != 2 { // 214 + the always-present Zaap criterion
		t.Errorf("pairs = %v, want exactly 2 entries", pairs)
	}
}

// TestCriteriaBlobIsDeterministic: the frame must be byte-stable regardless of
// input order, so it is reproducible and diffable.
func TestCriteriaBlobIsDeterministic(t *testing.T) {
	a := buildCriteriaBlob([]Criterion{{ID: 217, Value: 1}, {ID: 213, Value: 1}, {ID: 215, Value: 1}})
	b := buildCriteriaBlob([]Criterion{{ID: 215, Value: 1}, {ID: 217, Value: 1}, {ID: 213, Value: 1}})
	if string(a) != string(b) {
		t.Errorf("blob depends on input order:\n a=% x\n b=% x", a, b)
	}
}

// TestCoachInformationsCarriesCriteria: the criteria must survive into the real
// 2052 frame, and the frame must still be well-formed with many of them (the
// regression that a longer blob would shift later fields).
func TestCoachInformationsCarriesCriteria(t *testing.T) {
	var many []Criterion
	for id := uint16(200); id < 260; id++ {
		many = append(many, Criterion{ID: id, Value: 1})
	}
	frame, err := EncodeCoachInformations(Coach{ID: 7, Name: "Loov", Criteria: many})
	if err != nil {
		t.Fatalf("encode: %v", err)
	}
	plain, err := EncodeCoachInformations(Coach{ID: 7, Name: "Loov"})
	if err != nil {
		t.Fatalf("encode plain: %v", err)
	}
	// 60 criteria replace the single default pair: +59 pairs × 4 bytes.
	if got, want := len(frame)-len(plain), 59*4; got != want {
		t.Errorf("frame grew by %d bytes, want %d", got, want)
	}
}

// decodeStatisticData mimics the client's ls_0.a(): read [i32 byteLen], then one
// pair per 4 bytes. Note the i32 — the same pairs are prefixed with an i16 in the
// 2052 descriptor blob, and mixing the two up is silent corruption.
func decodeStatisticData(t *testing.T, payload []byte) (pairs map[uint16]uint16, consumed int) {
	t.Helper()
	if len(payload) < 4 {
		t.Fatalf("payload too short: %d bytes", len(payload))
	}
	byteLen := int(int32(binary.BigEndian.Uint32(payload[0:4])))
	pairs = map[uint16]uint16{}
	consumed = 4
	for n := 0; n*4 < byteLen; n++ {
		if consumed+4 > len(payload) {
			t.Fatalf("byteLen %d overruns the payload (%d bytes)", byteLen, len(payload))
		}
		pairs[binary.BigEndian.Uint16(payload[consumed:consumed+2])] =
			binary.BigEndian.Uint16(payload[consumed+2 : consumed+4])
		consumed += 4
	}
	return pairs, consumed
}

// TestStatisticDataMatchesDescriptorBlob is the invariant that keeps the
// achievement tab honest: 22002 and the 2052 descriptor's 0x200 blob are two
// framings of the SAME criteria set. If they can disagree, opening the tab
// silently rewrites the client's criteria map (aez_0.b replaces it wholesale),
// so a coach could lose progress just by looking at it.
func TestStatisticDataMatchesDescriptorBlob(t *testing.T) {
	cases := [][]Criterion{
		nil,
		{{ID: 221, Value: 1}},
		{{ID: 221, Value: 3}, {ID: 213, Value: 7}, {ID: criterionZaapUnlock, Value: 1}},
		// Entries the normaliser must drop identically in both encodings.
		{{ID: 0, Value: 1}, {ID: MaxCriterionID + 1, Value: 1}, {ID: 300, Value: 0},
			{ID: 214, Value: 2}, {ID: 214, Value: 9}},
	}
	for i, in := range cases {
		blobPairs, blobUsed := decodeCriteriaBlob(t, buildCriteriaBlob(in))
		dataPairs, dataUsed := decodeStatisticData(t, EncodeStatisticData(in))

		if len(blobPairs) != len(dataPairs) {
			t.Errorf("case %d: descriptor has %d pairs, 22002 has %d",
				i, len(blobPairs), len(dataPairs))
			continue
		}
		for id, v := range blobPairs {
			if dataPairs[id] != v {
				t.Errorf("case %d: criterion %d = %d in the descriptor but %d in 22002",
					i, id, v, dataPairs[id])
			}
		}
		// Each framing must consume its whole payload: the length prefixes differ
		// in width (i16 vs i32) but must describe the same number of pairs.
		if blobUsed-2 != dataUsed-4 {
			t.Errorf("case %d: descriptor carries %d bytes of pairs, 22002 carries %d",
				i, blobUsed-2, dataUsed-4)
		}
	}
}
