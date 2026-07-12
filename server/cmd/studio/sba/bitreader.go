package sba

import (
	"fmt"
	"math"
)

// bitReader is a faithful port of the client's
// com.ankamagames.framework.fileFormat.io.InputBitStream. It reads from an
// in-memory byte slice with the same semantics the format relies on:
//
//   - All multi-byte integers are little-endian.
//   - Bit fields are read MSB-first within each byte (readUnsignedBits).
//   - Byte-aligned reads (readUI8/16/32, readBytes) ALWAYS fetch fresh bytes
//     from the stream and re-align afterwards, so a partially-consumed byte
//     (from a preceding bit read) is abandoned -- exactly mirroring
//     fillBitBuffer()/align() in the Java source.
//
// Any read past the end of the buffer sets err (checked via Err) and returns
// zero values, so callers can walk optimistically and validate once at the end.
type bitReader struct {
	data      []byte
	pos       int // index of the next byte to fetch from the stream
	bitBuffer int // value of the most recently fetched byte
	bitCursor int // 0..8; 8 means "need a refill before the next bit"
	err       error
}

func newBitReader(b []byte) *bitReader {
	return &bitReader{data: b, bitCursor: 8}
}

// Err returns the first error encountered, if any.
func (r *bitReader) Err() error { return r.err }

// Remaining reports how many unread bytes are left in the stream.
func (r *bitReader) Remaining() int { return len(r.data) - r.pos }

// fill fetches the next byte into the bit buffer (InputBitStream.fillBitBuffer).
func (r *bitReader) fill() {
	if r.err != nil {
		return
	}
	if r.pos >= len(r.data) {
		r.err = fmt.Errorf("sba: unexpected end of stream at byte %d", r.pos)
		return
	}
	r.bitBuffer = int(r.data[r.pos])
	r.pos++
	r.bitCursor = 0
}

// align resets the bit cursor so the next byte-aligned read starts fresh.
func (r *bitReader) align() { r.bitCursor = 8 }

func (r *bitReader) readUnsignedBits(n int) uint64 {
	if n == 0 {
		return 0
	}
	var result uint64
	bitsLeft := n
	for bitsLeft > 0 {
		if r.bitCursor == 8 {
			r.fill()
			if r.err != nil {
				return result
			}
		}
		if r.bitBuffer&(1<<(7-r.bitCursor)) != 0 {
			result |= 1 << (bitsLeft - 1)
		}
		r.bitCursor++
		bitsLeft--
	}
	return result
}

func (r *bitReader) readSignedBits(n int) int64 {
	v := r.readUnsignedBits(n)
	if n > 0 && v&(1<<(n-1)) != 0 {
		v |= ^uint64(0) << n // sign-extend
	}
	return int64(v)
}

func (r *bitReader) readBooleanBit() bool { return r.readUnsignedBits(1) == 1 }

func (r *bitReader) readUI8() uint8 {
	r.fill()
	r.align()
	return uint8(r.bitBuffer)
}

func (r *bitReader) readUI16() uint16 {
	r.fill()
	b0 := r.bitBuffer
	r.fill()
	b1 := r.bitBuffer
	r.align()
	return uint16(b0) | uint16(b1)<<8
}

func (r *bitReader) readUI32() uint32 {
	r.fill()
	b0 := r.bitBuffer
	r.fill()
	b1 := r.bitBuffer
	r.fill()
	b2 := r.bitBuffer
	r.fill()
	b3 := r.bitBuffer
	r.align()
	return uint32(b0) | uint32(b1)<<8 | uint32(b2)<<16 | uint32(b3)<<24
}

func (r *bitReader) readSI16() int16 { return int16(r.readUI16()) }
func (r *bitReader) readSI32() int32 { return int32(r.readUI32()) }

// readFPBits reads an n-bit signed field as 16.16 fixed-point (readFPBits).
func (r *bitReader) readFPBits(n int) float64 {
	return float64(r.readSignedBits(n)) / 65536.0
}

// readFloat16 reads an IEEE half-precision float (InputBitStream.readFloat16).
func (r *bitReader) readFloat16() float32 {
	bits16 := r.readUI16()
	sign := uint32(bits16&0x8000) >> 15
	exp16 := (bits16 & 0x7C00) >> 10
	man16 := bits16 & 0x3FF
	var exp32 uint32
	if exp16 != 0 {
		if exp16 == 31 {
			exp32 = 255
		} else {
			exp32 = uint32(exp16) - 15 + 127
		}
	}
	man32 := uint32(man16) << 13
	bits32 := sign << 31
	bits32 |= exp32 << 23
	bits32 |= man32
	return math.Float32frombits(bits32)
}

// readBytes reads n raw bytes from the stream and re-aligns (InputBitStream.
// readBytes). The returned slice aliases the underlying buffer; callers that
// retain it beyond the reader's lifetime should copy.
func (r *bitReader) readBytes(n int) []byte {
	if r.err != nil {
		return nil
	}
	if n <= 0 {
		return []byte{}
	}
	if r.pos+n > len(r.data) {
		r.err = fmt.Errorf("sba: readBytes(%d) overruns stream (pos %d, len %d)", n, r.pos, len(r.data))
		return nil
	}
	out := r.data[r.pos : r.pos+n]
	r.pos += n
	r.align()
	return out
}

// readString reads a null-terminated UTF-8 string (InputBitStream.readString).
func (r *bitReader) readString() string {
	var buf []byte
	r.fill()
	for r.err == nil && r.bitBuffer != 0 {
		buf = append(buf, byte(r.bitBuffer))
		r.fill()
	}
	return string(buf)
}
