package encode

import "encoding/binary"

// AleaWriter is the little-endian counterpart to Writer, mirroring
// parser.AleaReader's layout for the "Alea" binary formats (.amw map chunks,
// elements.ade). Distinct from Writer (big-endian .dat convention): Alea
// files are little-endian. See parser/alea_reader.go.
type AleaWriter struct {
	buf []byte
}

// NewAleaWriter returns an empty AleaWriter.
func NewAleaWriter() *AleaWriter {
	return &AleaWriter{}
}

// Bytes returns the accumulated buffer.
func (w *AleaWriter) Bytes() []byte {
	return w.buf
}

// Byte writes a single unsigned byte.
func (w *AleaWriter) Byte(b byte) {
	w.buf = append(w.buf, b)
}

// Raw appends raw bytes verbatim (used for preserved param blobs).
func (w *AleaWriter) Raw(b []byte) {
	w.buf = append(w.buf, b...)
}

// Int16 writes a little-endian signed 16-bit integer.
func (w *AleaWriter) Int16(v int16) {
	w.buf = binary.LittleEndian.AppendUint16(w.buf, uint16(v))
}

// Int32 writes a little-endian signed 32-bit integer.
func (w *AleaWriter) Int32(v int32) {
	w.buf = binary.LittleEndian.AppendUint32(w.buf, uint32(v))
}
