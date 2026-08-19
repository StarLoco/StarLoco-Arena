package game

import (
	"encoding/binary"
	"testing"
)

// The shipped env blob is AUTHORING data: its z is the sprite's decoration height,
// not the cell's walkable ground. World 25's Zaap carries 30 where its ground is 8,
// which drew the element away from its cell and made it invisible and impossible to
// pick (the client's click resolves a CELL, wp_2 -> bd(cellX, cellY)). See B-109.

// TestElementRUPartHandlesEveryShippedPayload: the RU part's offset is
// payload-dependent, so it must be found by parsing the part table, never assumed.
// Most payloads put it at 14 and at least one puts it at 20 — writing at a fixed 14
// corrupts that one.
func TestElementRUPartHandlesEveryShippedPayload(t *testing.T) {
	offsets := map[int]int{}
	var total int
	for _, elems := range worldElements {
		for _, e := range elems {
			total++
			start, end, ok := elementRUPart(e.payload)
			if !ok {
				t.Errorf("element %d: no RU part found in %d bytes", e.instanceID, len(e.payload))
				continue
			}
			if end-start < elementRUMinLen {
				t.Errorf("element %d: RU part is %d bytes, below the client's own minimum %d",
					e.instanceID, end-start, elementRUMinLen)
			}
			offsets[start]++
		}
	}
	if total == 0 {
		t.Skip("no elements")
	}
	if len(offsets) < 2 {
		t.Errorf("every payload put the RU part at the same offset (%v) — the "+
			"fixed-offset shortcut this guards against would look correct", offsets)
	}
	t.Logf("RU part offsets across %d payloads: %v", total, offsets)
}

// TestElementPayloadWritesGroundAltitude: the z on the wire must be the element's
// ground altitude, and it must land in the RU part rather than at a guessed offset.
func TestElementPayloadWritesGroundAltitude(t *testing.T) {
	var checked, rewritten int
	for _, elems := range worldElements {
		for _, e := range elems {
			start, _, ok := elementRUPart(e.payload)
			if !ok {
				continue
			}
			checked++
			before := int16(binary.BigEndian.Uint16(e.payload[start+elementRUZOffset:]))
			out := elementPayloadAtGroundAltitude(e.payload, e.alt)
			after := int16(binary.BigEndian.Uint16(out[start+elementRUZOffset:]))
			if after != e.alt {
				t.Errorf("element %d: wire z = %d, want ground altitude %d",
					e.instanceID, after, e.alt)
			}
			// Only the two z bytes may change.
			for i := range out {
				if i >= start+elementRUZOffset && i < start+elementRUZOffset+2 {
					continue
				}
				if out[i] != e.payload[i] {
					t.Fatalf("element %d: byte %d changed outside the z field", e.instanceID, i)
				}
			}
			if before != e.alt {
				rewritten++
			}
		}
	}
	if checked == 0 {
		t.Skip("no element payloads")
	}
	// If nothing needs rewriting the fix is inert; world 25's Zaap alone (30 vs 8)
	// guarantees at least one.
	if rewritten == 0 {
		t.Error("no payload needed its z corrected: either the authored-height " +
			"problem is gone, or we are reading a field that never differs")
	}
	t.Logf("z corrected on %d of %d payloads", rewritten, checked)
}

// TestElementPayloadLeavesTheApproachMaskAlone. An earlier version cleared bit 256
// of the mask, believing it made every element inert. do_1.a(coach) — the only
// reader — has NO CALLER, and an A/B on the live client showed the interaction
// works with the mask exactly as shipped. This pins that we no longer touch it, so
// the theory cannot quietly come back.
func TestElementPayloadLeavesTheApproachMaskAlone(t *testing.T) {
	const maskInRU = 2 + 4 + 4 + 2 + 2 + 1 + 1 + 1
	for _, elems := range worldElements {
		for _, e := range elems {
			start, end, ok := elementRUPart(e.payload)
			if !ok || start+maskInRU+2 > end {
				continue
			}
			out := elementPayloadAtGroundAltitude(e.payload, e.alt)
			in := binary.BigEndian.Uint16(e.payload[start+maskInRU:])
			got := binary.BigEndian.Uint16(out[start+maskInRU:])
			if got != in {
				t.Fatalf("element %d: approach mask changed 0x%04X -> 0x%04X",
					e.instanceID, in, got)
			}
		}
	}
}

// TestElementPayloadDoesNotMutateTheSource: the table is package-level and shared
// by every session, so patching must copy.
func TestElementPayloadDoesNotMutateTheSource(t *testing.T) {
	for _, elems := range worldElements {
		for _, e := range elems {
			if _, _, ok := elementRUPart(e.payload); !ok {
				continue
			}
			orig := make([]byte, len(e.payload))
			copy(orig, e.payload)
			_ = elementPayloadAtGroundAltitude(e.payload, e.alt+7)
			for i := range orig {
				if e.payload[i] != orig[i] {
					t.Fatalf("element %d: source payload mutated at byte %d", e.instanceID, i)
				}
			}
			return
		}
	}
}

// TestElementPayloadTolerantOfJunk: never panic, whatever a future data drop holds.
func TestElementPayloadTolerantOfJunk(t *testing.T) {
	cases := [][]byte{
		nil, {}, {0}, {1}, {2, 0},
		{1, 0, 0xFF, 0xFF, 0xFF, 0xFF},     // offset way out of range
		{1, 0, 0, 0, 0, 200},               // offset past the end
		{2, 0, 0, 0, 0, 20, 1, 0, 0, 0, 5}, // parts out of order
	}
	for i, in := range cases {
		out := elementPayloadAtGroundAltitude(in, 5)
		if len(out) != len(in) {
			t.Errorf("case %d: returned %d bytes for %d", i, len(out), len(in))
		}
	}
}
