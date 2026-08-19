package game

import (
	"encoding/binary"
	"testing"
)

// The shipped env blobs are authoring data, not wire data. Two of their fields
// contradict the world they describe, and both are load-bearing for whether an
// element can be used at all:
//
//   - the approach mask carries bit 256 (client agm_2.ctZ), which do_1.gh() reads
//     as "inert" and which makes do_1.a(coach) — the "can this coach use it" test —
//     return false wherever the coach stands;
//   - the z is the sprite's authored decoration height, not the cell's ground: the
//     login island's Zaap carries 30 while its cell's ground is 8, which drew the
//     element far above its cell (it was invisible in-game) and built its approach
//     cells at the wrong altitude.
//
// Both are rewritten on the way out. See BUGS.md B-109.

// TestUsableElementPayloadClearsInertBit: every shipped element carries mask
// 0xFFFF, so without this every interactive object in the game is inert.
func TestUsableElementPayloadClearsInertBit(t *testing.T) {
	for _, elems := range worldElements {
		for _, e := range elems {
			if len(e.payload) < elementMaskOffset+2 {
				continue
			}
			out := usableElementPayload(e.payload, e.alt)
			mask := binary.BigEndian.Uint16(out[elementMaskOffset:])
			if mask&elementMaskDisabled != 0 {
				t.Fatalf("element %d: inert bit still set (mask 0x%04X)", e.instanceID, mask)
			}
			// The direction bits themselves must survive, or the element gains the
			// flag's removal but loses every cell it can be used from.
			in := binary.BigEndian.Uint16(e.payload[elementMaskOffset:])
			if got, want := mask, in&^uint16(elementMaskDisabled); got != want {
				t.Fatalf("element %d: mask = 0x%04X, want 0x%04X (only bit 256 may change)",
					e.instanceID, got, want)
			}
		}
	}
}

// TestUsableElementPayloadWritesGroundAltitude: the z on the wire must be the
// element's ground altitude, not the authored decoration height.
func TestUsableElementPayloadWritesGroundAltitude(t *testing.T) {
	var checked, rewritten int
	for _, elems := range worldElements {
		for _, e := range elems {
			if len(e.payload) < elementZOffset+2 {
				continue
			}
			checked++
			before := int16(binary.BigEndian.Uint16(e.payload[elementZOffset:]))
			out := usableElementPayload(e.payload, e.alt)
			after := int16(binary.BigEndian.Uint16(out[elementZOffset:]))
			if after != e.alt {
				t.Errorf("element %d: wire z = %d, want the ground altitude %d",
					e.instanceID, after, e.alt)
			}
			if before != e.alt {
				rewritten++
			}
		}
	}
	if checked == 0 {
		t.Skip("no element payloads")
	}
	// If nothing needed rewriting the fix is inert and this test proves nothing;
	// the login island's Zaap alone (30 vs 8) guarantees at least one.
	if rewritten == 0 {
		t.Error("no payload needed its z corrected: the authored-height problem " +
			"has gone away, or the offset is wrong and we are reading a stable field")
	}
	t.Logf("z corrected on %d of %d payloads", rewritten, checked)
}

// TestUsableElementPayloadDoesNotMutateTheSource: the table is package-level and
// shared by every session, so patching must copy. Mutating it in place would
// corrupt the table for all later sends (and make the tests above pass trivially
// on a second run).
func TestUsableElementPayloadDoesNotMutateTheSource(t *testing.T) {
	for _, elems := range worldElements {
		for _, e := range elems {
			if len(e.payload) < elementMaskOffset+2 {
				continue
			}
			orig := make([]byte, len(e.payload))
			copy(orig, e.payload)
			_ = usableElementPayload(e.payload, e.alt+7)
			for i := range orig {
				if e.payload[i] != orig[i] {
					t.Fatalf("element %d: source payload mutated at byte %d",
						e.instanceID, i)
				}
			}
			return // one is enough; the copy is unconditional
		}
	}
}

// TestUsableElementPayloadTolerantOfShortBlobs: never panic on a blob too small to
// hold the fields, whatever a future data drop contains.
func TestUsableElementPayloadTolerantOfShortBlobs(t *testing.T) {
	for n := 0; n < elementMaskOffset+2; n++ {
		in := make([]byte, n)
		out := usableElementPayload(in, 5)
		if len(out) != n {
			t.Fatalf("len %d: returned %d bytes", n, len(out))
		}
	}
}
