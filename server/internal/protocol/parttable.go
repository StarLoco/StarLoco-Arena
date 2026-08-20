package protocol

// Ankama "BinarSerial" part tables.
//
// Several 2.70 payloads are not flat structs but a directory of optional PARTS,
// decoded by the client's `aJj.ad`. A class exposes an ordered part array (its
// `Kl()`), the sender writes only the parts it has, and the reader sizes each one
// from the NEXT directory entry's offset:
//
//	[u8 partCount]
//	partCount × { [u8 partIdx][i32 absOffset] }   // directory
//	per part:   [u8 partIdx][payload]             // absOffset points at the idx byte
//
// Two consequences the callers must respect, both load-bearing:
//
//   - parts must be written in ASCENDING index order, because a part's length is
//     derived from the following offset;
//   - the offsets are ABSOLUTE positions inside the blob, so the directory size
//     has to be accounted for before the first payload.
//
// Used by the running-effect blob (8120) and by the guild member/membership blob
// (`ca_0`, parts 0/1/2), which is why this lives in protocol rather than in one
// of the two packages that build them.

// Part is one entry of a part table: its index in the owning class's part array
// and the already-serialized payload.
type Part struct {
	Idx  uint8
	Data []byte
}

// PartTable encodes parts into the wire form `aJj.ad` expects. Parts must be
// supplied in ascending Idx order; PartTable does not sort, because a caller
// that has them out of order almost certainly has a bug rather than a preference.
func PartTable(parts ...Part) []byte {
	w := NewWriter()
	w.U8(uint8(len(parts)))
	off := 1 + len(parts)*5 // count byte + 5 bytes per directory entry
	for _, p := range parts {
		w.U8(p.Idx)
		w.I32(int32(off))
		off += 1 + len(p.Data) // the part's own idx byte + payload
	}
	for _, p := range parts {
		w.U8(p.Idx)
		w.Raw(p.Data)
	}
	return w.Bytes()
}

// StringU8UTF8 appends a [u8 len][bytes] string encoded as UTF-8.
//
// Distinct from StringU8, which encodes the wire charset (cp1252) that the coach
// and fighter names use. The GUILD family is explicitly UTF-8: every string on it
// goes through `aey_0.hH`/`aey_0.V`, which name the charset outright
// (`getBytes("UTF-8")`) instead of taking the platform default the way `aez_0`
// and `et_2` do. Using the cp1252 encoder here would corrupt any accented clan or
// rank name, and only for those - which is exactly the kind of difference that
// survives every ASCII test.
func (w *Writer) StringU8UTF8(s string) *Writer {
	b := []byte(s)
	if len(b) > MaxStringU8 {
		b = b[:MaxStringU8]
		// Never cut a multi-byte rune in half: back off to the last boundary.
		for len(b) > 0 && b[len(b)-1]&0xC0 == 0x80 {
			b = b[:len(b)-1]
		}
		if len(b) > 0 && b[len(b)-1]&0x80 != 0 {
			b = b[:len(b)-1]
		}
	}
	w.buf = append(w.buf, byte(len(b)))
	w.buf = append(w.buf, b...)
	return w
}
