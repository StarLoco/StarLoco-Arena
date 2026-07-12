// Package parser implements binary readers for the legacy .dat game-data
// file format (cards.dat, spells.dat, events.dat, staticEffects.dat,
// summoning.dat). This format is distinct from the network wire protocol
// (internal/protocol): notably, strings here use a 4-byte length prefix,
// not 1-byte. See go-server/docs/04-game-data-format.md for the full
// per-file layout reference.
package parser

import (
	"encoding/binary"
	"fmt"
	"math"
)

// Reader provides sequential, bounds-checked reads over a .dat file buffer.
type Reader struct {
	buf []byte
	pos int
	err error
}

// NewReader wraps buf for sequential reading.
func NewReader(buf []byte) *Reader {
	return &Reader{buf: buf}
}

// Err returns the first error encountered by any Read* call.
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
		r.err = fmt.Errorf("gamedata: short read: need %d bytes, have %d", need, r.Remaining())
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

// Int16 reads a big-endian signed 16-bit integer.
func (r *Reader) Int16() int16 {
	if r.fail(2) {
		return 0
	}
	v := int16(binary.BigEndian.Uint16(r.buf[r.pos:]))
	r.pos += 2
	return v
}

// Int32 reads a big-endian signed 32-bit integer.
func (r *Reader) Int32() int32 {
	if r.fail(4) {
		return 0
	}
	v := int32(binary.BigEndian.Uint32(r.buf[r.pos:]))
	r.pos += 4
	return v
}

// Float32 reads a big-endian IEEE-754 32-bit float.
func (r *Reader) Float32() float32 {
	if r.fail(4) {
		return 0
	}
	v := math.Float32frombits(binary.BigEndian.Uint32(r.buf[r.pos:]))
	r.pos += 4
	return v
}

// String reads a 4-byte length-prefixed UTF-8 string (game-data file
// convention -- NOT the same as the 1-byte-prefixed strings used on the
// network wire, see internal/protocol.Reader.String).
func (r *Reader) String() string {
	n := int(r.Int32())
	if r.err != nil || n < 0 {
		return ""
	}
	if r.fail(n) {
		return ""
	}
	v := string(r.buf[r.pos : r.pos+n])
	r.pos += n
	return v
}

// Int32Slice reads a 4-byte count followed by that many int32 elements.
func (r *Reader) Int32Slice() []int32 {
	n := int(r.Int32())
	if r.err != nil || n < 0 {
		return nil
	}
	out := make([]int32, n)
	for i := range out {
		out[i] = r.Int32()
	}
	return out
}

// Int16Slice reads a 4-byte count followed by that many int16 elements.
func (r *Reader) Int16Slice() []int16 {
	n := int(r.Int32())
	if r.err != nil || n < 0 {
		return nil
	}
	out := make([]int16, n)
	for i := range out {
		out[i] = r.Int16()
	}
	return out
}

// Float32Slice reads a 4-byte count followed by that many float32 elements.
func (r *Reader) Float32Slice() []float32 {
	n := int(r.Int32())
	if r.err != nil || n < 0 {
		return nil
	}
	out := make([]float32, n)
	for i := range out {
		out[i] = r.Float32()
	}
	return out
}
