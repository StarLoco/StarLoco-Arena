package protocol

import (
	"encoding/binary"
	"fmt"
	"math"
)

// Reader provides sequential, bounds-checked reads over a packet payload
// buffer. All multi-byte fields are big-endian. Strings use a 1-byte length
// prefix (see docs/02-protocol.md §2.2) -- this differs from the .dat game
// data file format's 4-byte string length prefix, which is handled by
// internal/gamedata/parser instead.
type Reader struct {
	buf []byte
	pos int
	err error
}

// NewReader wraps buf for sequential reading.
func NewReader(buf []byte) *Reader {
	return &Reader{buf: buf}
}

// Err returns the first error encountered by any Read* call, if any. Once
// an error has occurred, subsequent Read* calls are no-ops that return zero
// values, so callers can chain reads and check Err() once at the end.
func (r *Reader) Err() error {
	return r.err
}

// Remaining returns the number of unread bytes.
func (r *Reader) Remaining() int {
	return len(r.buf) - r.pos
}

func (r *Reader) fail(need int) bool {
	if r.err != nil {
		return true
	}
	if r.Remaining() < need {
		r.err = fmt.Errorf("protocol: short read: need %d bytes, have %d", need, r.Remaining())
		return true
	}
	return false
}

// Bool reads a single byte, non-zero meaning true.
func (r *Reader) Bool() bool {
	if r.fail(1) {
		return false
	}
	v := r.buf[r.pos] != 0
	r.pos++
	return v
}

// Byte reads a single unsigned byte.
func (r *Reader) Byte() byte {
	if r.fail(1) {
		return 0
	}
	v := r.buf[r.pos]
	r.pos++
	return v
}

// Int8 reads a single signed byte.
func (r *Reader) Int8() int8 {
	return int8(r.Byte())
}

// Uint16 reads a big-endian unsigned 16-bit integer.
func (r *Reader) Uint16() uint16 {
	if r.fail(2) {
		return 0
	}
	v := binary.BigEndian.Uint16(r.buf[r.pos:])
	r.pos += 2
	return v
}

// Int16 reads a big-endian signed 16-bit integer.
func (r *Reader) Int16() int16 {
	return int16(r.Uint16())
}

// Uint32 reads a big-endian unsigned 32-bit integer.
func (r *Reader) Uint32() uint32 {
	if r.fail(4) {
		return 0
	}
	v := binary.BigEndian.Uint32(r.buf[r.pos:])
	r.pos += 4
	return v
}

// Int32 reads a big-endian signed 32-bit integer.
func (r *Reader) Int32() int32 {
	return int32(r.Uint32())
}

// Uint64 reads a big-endian unsigned 64-bit integer.
func (r *Reader) Uint64() uint64 {
	if r.fail(8) {
		return 0
	}
	v := binary.BigEndian.Uint64(r.buf[r.pos:])
	r.pos += 8
	return v
}

// Int64 reads a big-endian signed 64-bit integer.
func (r *Reader) Int64() int64 {
	return int64(r.Uint64())
}

// Float32 reads a big-endian IEEE-754 32-bit float.
func (r *Reader) Float32() float32 {
	return math.Float32frombits(r.Uint32())
}

// Bytes reads n raw bytes.
func (r *Reader) Bytes(n int) []byte {
	if r.fail(n) {
		return nil
	}
	v := make([]byte, n)
	copy(v, r.buf[r.pos:r.pos+n])
	r.pos += n
	return v
}

// String reads a 1-byte length-prefixed UTF-8 string (network protocol
// convention; distinct from the 4-byte-prefixed strings used in .dat game
// data files).
func (r *Reader) String() string {
	n := int(r.Byte())
	if r.err != nil {
		return ""
	}
	return string(r.Bytes(n))
}

// StringShort reads a 2-byte (short) length-prefixed UTF-8 string, used by
// a handful of larger payloads (e.g. team names in some contexts).
func (r *Reader) StringShort() string {
	n := int(r.Uint16())
	if r.err != nil {
		return ""
	}
	return string(r.Bytes(n))
}
