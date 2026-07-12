package protocol

import (
	"encoding/binary"
	"math"
)

// Writer is a fluent, growable byte-buffer builder for packet payloads. It
// replaces the old Java Buffer class's pre-sized-capacity approach with a
// safe, auto-growing byte slice -- callers no longer need to hand-compute
// an exact byte count up front (a frequent source of bugs in the original
// server, e.g. Coach.java's inventory/equipment size arithmetic).
//
// All multi-byte fields are written big-endian to match the wire protocol
// (see docs/02-protocol.md §2.2).
type Writer struct {
	buf []byte
}

// NewWriter creates an empty Writer. sizeHint, if > 0, pre-allocates
// capacity to reduce reallocations for packets whose approximate size is
// known ahead of time.
func NewWriter(sizeHint int) *Writer {
	var buf []byte
	if sizeHint > 0 {
		buf = make([]byte, 0, sizeHint)
	}
	return &Writer{buf: buf}
}

// Bytes returns the accumulated payload.
func (w *Writer) Bytes() []byte {
	return w.buf
}

// Len returns the number of bytes written so far.
func (w *Writer) Len() int {
	return len(w.buf)
}

// PutBool writes a single byte, 1 for true, 0 for false.
func (w *Writer) PutBool(v bool) *Writer {
	if v {
		return w.PutByte(1)
	}
	return w.PutByte(0)
}

// PutByte writes a single unsigned byte.
func (w *Writer) PutByte(v byte) *Writer {
	w.buf = append(w.buf, v)
	return w
}

// PutInt8 writes a single signed byte.
func (w *Writer) PutInt8(v int8) *Writer {
	return w.PutByte(byte(v))
}

// PutUint16 writes a big-endian unsigned 16-bit integer.
func (w *Writer) PutUint16(v uint16) *Writer {
	var b [2]byte
	binary.BigEndian.PutUint16(b[:], v)
	w.buf = append(w.buf, b[:]...)
	return w
}

// PutInt16 writes a big-endian signed 16-bit integer.
func (w *Writer) PutInt16(v int16) *Writer {
	return w.PutUint16(uint16(v))
}

// PutUint32 writes a big-endian unsigned 32-bit integer.
func (w *Writer) PutUint32(v uint32) *Writer {
	var b [4]byte
	binary.BigEndian.PutUint32(b[:], v)
	w.buf = append(w.buf, b[:]...)
	return w
}

// PutInt32 writes a big-endian signed 32-bit integer.
func (w *Writer) PutInt32(v int32) *Writer {
	return w.PutUint32(uint32(v))
}

// PutUint64 writes a big-endian unsigned 64-bit integer.
func (w *Writer) PutUint64(v uint64) *Writer {
	var b [8]byte
	binary.BigEndian.PutUint64(b[:], v)
	w.buf = append(w.buf, b[:]...)
	return w
}

// PutInt64 writes a big-endian signed 64-bit integer.
func (w *Writer) PutInt64(v int64) *Writer {
	return w.PutUint64(uint64(v))
}

// PutFloat32 writes a big-endian IEEE-754 32-bit float.
func (w *Writer) PutFloat32(v float32) *Writer {
	return w.PutUint32(math.Float32bits(v))
}

// PutBytes appends raw bytes with no length prefix.
func (w *Writer) PutBytes(v []byte) *Writer {
	w.buf = append(w.buf, v...)
	return w
}

// PutString writes a 1-byte length-prefixed UTF-8 string (network protocol
// convention). Panics if len(s) > 255, since that would silently truncate
// the length prefix -- callers must validate string lengths (e.g. coach
// names) before reaching this layer.
func (w *Writer) PutString(s string) *Writer {
	if len(s) > 255 {
		panic("protocol: PutString: string longer than 255 bytes")
	}
	w.PutByte(byte(len(s)))
	w.buf = append(w.buf, s...)
	return w
}

// PutStringShort writes a 2-byte (short) length-prefixed UTF-8 string.
func (w *Writer) PutStringShort(s string) *Writer {
	if len(s) > 0xFFFF {
		panic("protocol: PutStringShort: string longer than 65535 bytes")
	}
	w.PutUint16(uint16(len(s)))
	w.buf = append(w.buf, s...)
	return w
}
