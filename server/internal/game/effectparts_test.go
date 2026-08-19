package game

import (
	"testing"
)

// parseBinarSerial decodes an 8120 blob the way the CLIENT does (aJj.ad): read
// the directory, then take each part's payload from its absolute offset+1 and
// derive its length from the NEXT part's offset (blob end for the last one).
//
// Deliberately independent of writeBinarSerial: deriving lengths from the
// neighbouring offsets is what makes a wrong directory visible here, which a
// test that simply re-walked the writer's own arithmetic would miss.
func parseBinarSerial(t *testing.T, blob []byte) map[uint8][]byte {
	t.Helper()
	if len(blob) == 0 {
		t.Fatal("empty blob")
	}
	n := int(blob[0])
	idx := make([]uint8, n)
	off := make([]int, n)
	for i := 0; i < n; i++ {
		p := 1 + i*5
		idx[i] = blob[p]
		off[i] = int(uint32(blob[p+1])<<24 | uint32(blob[p+2])<<16 |
			uint32(blob[p+3])<<8 | uint32(blob[p+4]))
	}
	out := make(map[uint8][]byte, n)
	for i := 0; i < n; i++ {
		start := off[i]
		end := len(blob)
		if i+1 < n {
			end = off[i+1]
		}
		if start < 0 || end > len(blob) || start >= end {
			t.Fatalf("part %d: bad span [%d,%d) in a %d-byte blob", idx[i], start, end, len(blob))
		}
		if blob[start] != idx[i] {
			t.Fatalf("part %d: directory points at a block whose idx byte is %d", idx[i], blob[start])
		}
		out[idx[i]] = blob[start+1 : end]
	}
	return out
}

func blobOf(t *testing.T, frame []byte) []byte {
	t.Helper()
	payload := frame[4:]
	blobLen := int(uint16(payload[18])<<8 | uint16(payload[19]))
	blob := payload[20:]
	if len(blob) != blobLen {
		t.Fatalf("blobLen field = %d but %d bytes follow", blobLen, len(blob))
	}
	return blob
}

func be32(b []byte) int32 {
	return int32(uint32(b[0])<<24 | uint32(b[1])<<16 | uint32(b[2])<<8 | uint32(b[3]))
}

func be64(b []byte) int64 {
	return int64(uint64(uint32(b[0])<<24|uint32(b[1])<<16|uint32(b[2])<<8|uint32(b[3])))<<32 |
		int64(uint64(uint32(b[4])<<24|uint32(b[5])<<16|uint32(b[6])<<8|uint32(b[7])))
}

// TestSourceSpellPartLayout pins part 4 (`jf_2`, 12B): [i32 sourceType][i64 id].
//
// The client resolves the casting spell from this and NOTHING else, and its buff
// bar drops every running effect whose source is not a spell
// (ee_2.java:562 `mi() == null || mi().iP() != 13`). A wrong type byte here means
// no buff icon ever appears, which no server-side assertion would notice.
func TestSourceSpellPartLayout(t *testing.T) {
	frame, err := buildRunningEffect(1, 9, 7, 100, 200, Pos{X: 5, Y: 6, Z: 1}, 3, 4, false,
		sourceSpellPart(447))
	if err != nil {
		t.Fatalf("buildRunningEffect: %v", err)
	}
	parts := parseBinarSerial(t, blobOf(t, frame))
	p4, ok := parts[4]
	if !ok {
		t.Fatal("part 4 missing")
	}
	if len(p4) != 12 {
		t.Fatalf("part 4 is %d bytes, want 12 (jf_2 declares `new jf_2(this, 12)`)", len(p4))
	}
	if got := be32(p4[0:4]); got != 13 {
		t.Errorf("sourceType = %d, want 13 (Spell); the client's switch has no default, "+
			"so any other value leaves mi() null and the buff bar skips the effect", got)
	}
	if got := be64(p4[4:12]); got != 447 {
		t.Errorf("sourceId = %d, want 447", got)
	}
}

// TestSourceSpellPartOmittedWithoutSpell keeps non-spell broadcasts (poison
// ticks, special cells, trap damage) byte-identical to before: id 0 must add no
// part at all rather than a part claiming spell 0, which the client would try to
// resolve and fail on.
func TestSourceSpellPartOmittedWithoutSpell(t *testing.T) {
	frame, err := buildRunningEffect(1, 9, 7, 100, 200, Pos{X: 5, Y: 6, Z: 1}, 3, 0, false,
		sourceSpellPart(0))
	if err != nil {
		t.Fatalf("buildRunningEffect: %v", err)
	}
	blob := blobOf(t, frame)
	if blob[0] != 3 {
		t.Fatalf("numParts = %d, want 3 (0,1,2 only)", blob[0])
	}
	if _, ok := parseBinarSerial(t, blob)[4]; ok {
		t.Error("part 4 present for spell id 0")
	}
	plain, _ := buildRunningEffect(1, 9, 7, 100, 200, Pos{X: 5, Y: 6, Z: 1}, 3, 0, false)
	if len(plain) != len(frame) {
		t.Errorf("a zero source spell changed the frame size (%d vs %d)", len(frame), len(plain))
	}
}

// TestDisplacementPartLayout pins part 3 (`aaa_0`/`hk_0`, 18B):
// [i32 x][i32 y][i16 z][i64 collidedFighterId].
//
// Push (37) / pull (38) / 153 move the fighter to exactly this cell. The client
// never derives it on the wire path — `na_2.aaH()` is only reachable from
// `a(xb_2)`, which `mv_0.ax()` disables by calling `akd()` first — so the field is
// null (fresh instance) or stale (pooled) without this part.
func TestDisplacementPartLayout(t *testing.T) {
	frame, err := buildRunningEffect(1, 37, 7, 100, 200, Pos{X: 5, Y: 6, Z: 1}, 2, 0, true,
		displacementPart(Pos{X: 9, Y: -4, Z: 3}, 777))
	if err != nil {
		t.Fatalf("buildRunningEffect: %v", err)
	}
	parts := parseBinarSerial(t, blobOf(t, frame))
	p3, ok := parts[3]
	if !ok {
		t.Fatal("part 3 missing")
	}
	if len(p3) != 18 {
		t.Fatalf("part 3 is %d bytes, want 18 (`new aaa_0(this, 18)`)", len(p3))
	}
	if x, y := be32(p3[0:4]), be32(p3[4:8]); x != 9 || y != -4 {
		t.Errorf("destination = (%d,%d), want (9,-4)", x, y)
	}
	if z := int16(uint16(p3[8])<<8 | uint16(p3[9])); z != 3 {
		t.Errorf("destination z = %d, want 3", z)
	}
	if got := be64(p3[10:18]); got != 777 {
		t.Errorf("collided fighter = %d, want 777", got)
	}
}

// TestOptionalPartsAreOrdered guards the one property the client's decoder needs
// but the writer does not naturally give: parts must appear in ASCENDING index
// order, because aJj.ad sizes each part from the next directory entry's offset.
// Passing the options out of order must not produce a blob with a negative-length
// part.
func TestOptionalPartsAreOrdered(t *testing.T) {
	frame, err := buildRunningEffect(1, 37, 7, 100, 200, Pos{X: 5, Y: 6, Z: 1}, 2, 0, true,
		sourceSpellPart(63), displacementPart(Pos{X: 9, Y: -4, Z: 3}, 0))
	if err != nil {
		t.Fatalf("buildRunningEffect: %v", err)
	}
	blob := blobOf(t, frame)
	if blob[0] != 5 {
		t.Fatalf("numParts = %d, want 5", blob[0])
	}
	prev := -1
	for i := 0; i < 5; i++ {
		p := 1 + i*5
		if got := int(blob[p]); got <= prev {
			t.Fatalf("part indexes are not ascending: %d after %d", got, prev)
		} else {
			prev = got
		}
	}
	parts := parseBinarSerial(t, blob)
	if len(parts[3]) != 18 || len(parts[4]) != 12 {
		t.Fatalf("sizes after reordering: part3=%d part4=%d", len(parts[3]), len(parts[4]))
	}
	if got := be64(parts[4][4:12]); got != 63 {
		t.Errorf("part 4 spell = %d, want 63", got)
	}
}
