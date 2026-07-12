package encode

import "github.com/dofusarena/go-server/internal/gamedata/parser"

// EncodeAMWChunk encodes a parsed .amw map chunk BODY (the bytes AFTER the
// 2-byte Alea header) back to bytes -- the exact inverse of
// parser.ParseAMWFile. Because AMWCellElement preserves each param's raw
// concatenated type+payload blob (ParamBytes) and its parallel ParamTypes,
// the encoder reproduces the param section byte-for-byte without needing to
// re-derive per-type sizes.
//
// Cell order MUST match the parser's row-major layout (cellIndex =
// localY*size + localX); ParseAMWFile fills Cells in exactly that order, so
// re-encoding an unmodified parse round-trips. The round-trip tests in
// amw_test.go verify this against every real map chunk.
func EncodeAMWChunk(chunk parser.AMWMapChunk) []byte {
	w := NewAleaWriter()
	w.Int32(chunk.CoordX)
	w.Int32(chunk.CoordY)
	w.Byte(byte(chunk.Size))

	for _, cell := range chunk.Cells {
		w.Byte(byte(len(cell.Levels)))
		for _, lvl := range cell.Levels {
			w.Byte(byte(len(lvl.Elements)))
			for _, el := range lvl.Elements {
				w.Int32(el.ElementID)
				w.Byte(el.State)
				w.Int32(el.GroupID)
				w.Byte(byte(len(el.ParamTypes)))
				// ParamBytes already holds the concatenated
				// [type-tag, payload...] blobs for every param in order,
				// exactly as read; emit verbatim to guarantee byte parity.
				w.Raw(el.ParamBytes)
			}
		}
	}
	return w.Bytes()
}

// EncodeAMWFile re-prepends the 2-byte Alea header (type code 'M'=77 +
// version) to an encoded chunk body, producing a complete .amw file ready to
// write back into data/maps or repack into data.jar. header should be the
// same AleaFileHeader PeekAleaHeader returned for the original file so the
// version byte is preserved.
func EncodeAMWFile(header parser.AleaFileHeader, chunk parser.AMWMapChunk) []byte {
	body := EncodeAMWChunk(chunk)
	out := make([]byte, 0, 2+len(body))
	out = append(out, header.TypeCode, header.Version)
	out = append(out, body...)
	return out
}
