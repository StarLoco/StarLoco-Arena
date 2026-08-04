package testclient

import (
	"encoding/binary"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// W builds a big-endian payload.
type W struct{ b []byte }

func NewW() *W               { return &W{} }
func (w *W) U8(v uint8) *W   { w.b = append(w.b, v); return w }
func (w *W) U16(v uint16) *W { w.b = binary.BigEndian.AppendUint16(w.b, v); return w }
func (w *W) I32(v int32) *W  { w.b = binary.BigEndian.AppendUint32(w.b, uint32(v)); return w }
func (w *W) I64(v int64) *W  { w.b = binary.BigEndian.AppendUint64(w.b, uint64(v)); return w }
func (w *W) Raw(b []byte) *W { w.b = append(w.b, b...); return w }
func (w *W) Bytes() []byte   { return w.b }

// Str8 writes [u8 len][bytes] in the WIRE charset, exactly as the retail client
// does (String.getBytes() on a cp1252 JVM). Encoding here rather than emitting
// raw UTF-8 keeps the e2e tests honest about what a real client sends.
func (w *W) Str8(s string) *W {
	b := protocol.EncodeText(s)
	w.b = append(w.b, byte(len(b)))
	w.b = append(w.b, b...)
	return w
}

// R reads a big-endian payload.
type R struct {
	b   []byte
	pos int
}

func NewR(b []byte) *R { return &R{b: b} }

func (r *R) Remaining() int { return len(r.b) - r.pos }

func (r *R) U8() uint8 {
	if r.pos >= len(r.b) {
		return 0
	}
	v := r.b[r.pos]
	r.pos++
	return v
}

func (r *R) U16() uint16 {
	if r.pos+2 > len(r.b) {
		return 0
	}
	v := binary.BigEndian.Uint16(r.b[r.pos:])
	r.pos += 2
	return v
}

func (r *R) I32() int32 {
	if r.pos+4 > len(r.b) {
		return 0
	}
	v := int32(binary.BigEndian.Uint32(r.b[r.pos:]))
	r.pos += 4
	return v
}

func (r *R) I64() int64 {
	if r.pos+8 > len(r.b) {
		return 0
	}
	v := int64(binary.BigEndian.Uint64(r.b[r.pos:]))
	r.pos += 8
	return v
}

func (r *R) Str8() string {
	n := int(r.U8())
	if r.pos+n > len(r.b) {
		return ""
	}
	s := protocol.DecodeText(r.b[r.pos : r.pos+n])
	r.pos += n
	return s
}

// RawN returns the next n bytes (empty if not enough remain).
func (r *R) RawN(n int) []byte {
	if n < 0 || r.pos+n > len(r.b) {
		return nil
	}
	b := r.b[r.pos : r.pos+n]
	r.pos += n
	return b
}
