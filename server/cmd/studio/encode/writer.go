// Package encode implements byte-exact encoders for the legacy .dat
// game-data files, the inverse of internal/gamedata/parser's readers. It is
// the write side of the studio's Phase 5 data-editing/export feature.
//
// The Writer mirrors parser.Reader's primitive layout EXACTLY (big-endian
// ints/floats, 4-byte length-prefixed strings and count-prefixed slices) so
// that for every real data file `Encode(Parse(f)) == f` byte-for-byte. That
// round-trip identity is enforced by tests against the real files in
// encode/*_test.go, and is the safety contract the export pipeline relies
// on before it is ever allowed to overwrite a client file.
package encode

import (
	"encoding/binary"
	"math"
)

// Writer accumulates big-endian .dat-format bytes.
type Writer struct {
	buf []byte
}

// NewWriter returns an empty Writer.
func NewWriter() *Writer {
	return &Writer{}
}

// Bytes returns the accumulated buffer (not a copy; callers should not
// mutate it).
func (w *Writer) Bytes() []byte {
	return w.buf
}

// Bool writes a single byte: 1 for true, 0 for false. NOTE: parser.Reader
// treats any non-zero byte as true, so a source file that stored a bool as
// some other non-zero value (e.g. 2) will NOT byte-match on re-encode. In
// this project's real data every bool is stored as 0/1, so round-trip is
// exact; the round-trip tests will flag any file where this doesn't hold.
func (w *Writer) Bool(b bool) {
	if b {
		w.buf = append(w.buf, 1)
	} else {
		w.buf = append(w.buf, 0)
	}
}

// Byte writes a single unsigned byte.
func (w *Writer) Byte(b byte) {
	w.buf = append(w.buf, b)
}

// Int16 writes a big-endian signed 16-bit integer.
func (w *Writer) Int16(v int16) {
	w.buf = binary.BigEndian.AppendUint16(w.buf, uint16(v))
}

// Int32 writes a big-endian signed 32-bit integer.
func (w *Writer) Int32(v int32) {
	w.buf = binary.BigEndian.AppendUint32(w.buf, uint32(v))
}

// Float32 writes a big-endian IEEE-754 32-bit float.
func (w *Writer) Float32(v float32) {
	w.buf = binary.BigEndian.AppendUint32(w.buf, math.Float32bits(v))
}

// String writes a 4-byte length-prefixed UTF-8 string.
func (w *Writer) String(s string) {
	w.Int32(int32(len(s)))
	w.buf = append(w.buf, s...)
}

// Int32Slice writes a 4-byte count followed by that many int32 elements.
// A nil slice encodes as count 0 (matching how parser.Reader reads a 0
// count back as a nil/empty slice).
func (w *Writer) Int32Slice(s []int32) {
	w.Int32(int32(len(s)))
	for _, v := range s {
		w.Int32(v)
	}
}

// Int16Slice writes a 4-byte count followed by that many int16 elements.
func (w *Writer) Int16Slice(s []int16) {
	w.Int32(int32(len(s)))
	for _, v := range s {
		w.Int16(v)
	}
}

// Float32Slice writes a 4-byte count followed by that many float32 elements.
func (w *Writer) Float32Slice(s []float32) {
	w.Int32(int32(len(s)))
	for _, v := range s {
		w.Float32(v)
	}
}
