package protocol

import (
	"encoding/binary"
	"errors"
	"math"
)

// ErrTruncated is returned when a reader runs out of bytes mid-field.
var ErrTruncated = errors.New("protocol: truncated payload")

// Reader is a big-endian cursor over a message payload. All DofusArena wire
// fields are big-endian; strings on the login path use a u8 length prefix.
type Reader struct {
	buf []byte
	pos int
}

// NewReader wraps a payload for sequential big-endian reads.
func NewReader(buf []byte) *Reader { return &Reader{buf: buf} }

// Remaining reports how many unread bytes are left.
func (r *Reader) Remaining() int { return len(r.buf) - r.pos }

func (r *Reader) need(n int) error {
	if r.pos+n > len(r.buf) {
		return ErrTruncated
	}
	return nil
}

// U8 reads an unsigned byte.
func (r *Reader) U8() (uint8, error) {
	if err := r.need(1); err != nil {
		return 0, err
	}
	v := r.buf[r.pos]
	r.pos++
	return v, nil
}

// U16 reads a big-endian unsigned 16-bit integer.
func (r *Reader) U16() (uint16, error) {
	if err := r.need(2); err != nil {
		return 0, err
	}
	v := binary.BigEndian.Uint16(r.buf[r.pos:])
	r.pos += 2
	return v, nil
}

// I32 reads a big-endian signed 32-bit integer.
func (r *Reader) I32() (int32, error) {
	if err := r.need(4); err != nil {
		return 0, err
	}
	v := int32(binary.BigEndian.Uint32(r.buf[r.pos:]))
	r.pos += 4
	return v, nil
}

// I64 reads a big-endian signed 64-bit integer.
func (r *Reader) I64() (int64, error) {
	if err := r.need(8); err != nil {
		return 0, err
	}
	v := int64(binary.BigEndian.Uint64(r.buf[r.pos:]))
	r.pos += 8
	return v, nil
}

// String reads exactly n bytes as a string.
func (r *Reader) String(n int) (string, error) {
	if err := r.need(n); err != nil {
		return "", err
	}
	s := DecodeText(r.buf[r.pos : r.pos+n])
	r.pos += n
	return s, nil
}

// Bytes reads exactly n bytes, returning a copy.
func (r *Reader) Bytes(n int) ([]byte, error) {
	if err := r.need(n); err != nil {
		return nil, err
	}
	b := make([]byte, n)
	copy(b, r.buf[r.pos:r.pos+n])
	r.pos += n
	return b, nil
}

// StringU8 reads a [u8 len][bytes] length-prefixed string (the login format).
func (r *Reader) StringU8() (string, error) {
	n, err := r.U8()
	if err != nil {
		return "", err
	}
	if err := r.need(int(n)); err != nil {
		return "", err
	}
	s := DecodeText(r.buf[r.pos : r.pos+int(n)])
	r.pos += int(n)
	return s, nil
}

// StringU32 reads an [i32 len][bytes] length-prefixed UTF-8 string (the wide
// prefix the 2.70 tournament messages use). A negative length is rejected.
func (r *Reader) StringU32() (string, error) {
	n, err := r.I32()
	if err != nil {
		return "", err
	}
	if n < 0 {
		return "", ErrTruncated
	}
	if err := r.need(int(n)); err != nil {
		return "", err
	}
	s := DecodeText(r.buf[r.pos : r.pos+int(n)])
	r.pos += int(n)
	return s, nil
}

// Writer builds a message payload with big-endian primitives.
type Writer struct{ buf []byte }

// NewWriter returns an empty payload writer.
func NewWriter() *Writer { return &Writer{} }

// Bytes returns the accumulated payload.
func (w *Writer) Bytes() []byte { return w.buf }

// U8 appends an unsigned byte.
func (w *Writer) U8(v uint8) *Writer { w.buf = append(w.buf, v); return w }

// U16 appends a big-endian unsigned 16-bit integer.
func (w *Writer) U16(v uint16) *Writer {
	w.buf = binary.BigEndian.AppendUint16(w.buf, v)
	return w
}

// I32 appends a big-endian signed 32-bit integer.
func (w *Writer) I32(v int32) *Writer {
	w.buf = binary.BigEndian.AppendUint32(w.buf, uint32(v))
	return w
}

// I64 appends a big-endian signed 64-bit integer.
func (w *Writer) I64(v int64) *Writer {
	w.buf = binary.BigEndian.AppendUint64(w.buf, uint64(v))
	return w
}

// F32 appends a big-endian IEEE-754 32-bit float.
func (w *Writer) F32(v float32) *Writer {
	w.buf = binary.BigEndian.AppendUint32(w.buf, math.Float32bits(v))
	return w
}

// StringU8 appends a [u8 len][bytes] length-prefixed string. The caller must keep
// s <= 127 bytes: several 2.70 decoders read this length as a signed byte, so a
// longer string would present a negative length and crash the client.
func (w *Writer) StringU8(s string) *Writer {
	b := EncodeText(s)
	w.buf = append(w.buf, byte(len(b)))
	w.buf = append(w.buf, b...)
	return w
}

// StringU16 appends an [i16 len][bytes] length-prefixed UTF-8 string.
func (w *Writer) StringU16(s string) *Writer {
	b := EncodeText(s)
	w.buf = binary.BigEndian.AppendUint16(w.buf, uint16(len(b)))
	w.buf = append(w.buf, b...)
	return w
}

// StringU32 appends an [i32 len][bytes] length-prefixed UTF-8 string (the prefix
// width the 2.70 tournament list/tree messages use for their text fields).
func (w *Writer) StringU32(s string) *Writer {
	b := EncodeText(s)
	w.buf = binary.BigEndian.AppendUint32(w.buf, uint32(len(b)))
	w.buf = append(w.buf, b...)
	return w
}

// Raw appends bytes verbatim (no length prefix).
func (w *Writer) Raw(b []byte) *Writer {
	w.buf = append(w.buf, b...)
	return w
}
