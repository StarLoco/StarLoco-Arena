package parser

import (
	"encoding/binary"
	"fmt"
)

// AleaReader provides sequential, bounds-checked reads over an "Alea" format
// file buffer (elements.ade, .amw map files). This is a DISTINCT binary
// convention from the .dat game-data format read by Reader (reader.go):
// Alea files are little-endian (java.nio.ByteBuffer.order(LITTLE_ENDIAN),
// set in AleaDocumentAccessor.open()) and have no 2-byte type/version
// header consumed by this reader (callers should read/verify those two
// bytes themselves before constructing an AleaReader over the remaining
// payload, mirroring AleaDocumentAccessor.readHeader()'s type-code+version
// check). See docs/04-game-data-format.md §4.9 for the full reference,
// derived from disassembling the real client's compiled .class files
// (javap -c) to confirm exact field order/sizes, not just the decompiled
// .java source (which can have decompiler artifacts).
type AleaReader struct {
	buf []byte
	pos int
	err error
}

// NewAleaReader wraps buf for sequential little-endian reading.
func NewAleaReader(buf []byte) *AleaReader {
	return &AleaReader{buf: buf}
}

// Err returns the first error encountered by any Read call.
func (r *AleaReader) Err() error {
	return r.err
}

// Pos returns the current read offset.
func (r *AleaReader) Pos() int {
	return r.pos
}

// Len returns the total buffer length.
func (r *AleaReader) Len() int {
	return len(r.buf)
}

// Remaining returns the number of unread bytes.
func (r *AleaReader) Remaining() int {
	return len(r.buf) - r.pos
}

func (r *AleaReader) fail(need int) bool {
	if r.err != nil {
		return true
	}
	if r.Remaining() < need {
		r.err = fmt.Errorf("gamedata: alea: short read at offset %d: need %d bytes, have %d", r.pos, need, r.Remaining())
		return true
	}
	return false
}

// Byte reads a single unsigned byte.
func (r *AleaReader) Byte() byte {
	if r.fail(1) {
		return 0
	}
	v := r.buf[r.pos]
	r.pos++
	return v
}

// SByte reads a single signed byte (Java's `byte`).
func (r *AleaReader) SByte() int8 {
	return int8(r.Byte())
}

// Bool reads a single byte, == 1 meaning true (matches the exact
// `buffer.get() == 1` pattern used throughout the decompiled Alea readers
// -- NOT "non-zero", though in practice only 0/1 are ever written).
func (r *AleaReader) Bool() bool {
	return r.Byte() == 1
}

// Int16 reads a little-endian signed 16-bit integer.
func (r *AleaReader) Int16() int16 {
	if r.fail(2) {
		return 0
	}
	v := int16(binary.LittleEndian.Uint16(r.buf[r.pos:]))
	r.pos += 2
	return v
}

// Int32 reads a little-endian signed 32-bit integer.
func (r *AleaReader) Int32() int32 {
	if r.fail(4) {
		return 0
	}
	v := int32(binary.LittleEndian.Uint32(r.buf[r.pos:]))
	r.pos += 4
	return v
}

// Bytes reads n raw bytes.
func (r *AleaReader) Bytes(n int) []byte {
	if n <= 0 || r.fail(n) {
		return nil
	}
	v := make([]byte, n)
	copy(v, r.buf[r.pos:r.pos+n])
	r.pos += n
	return v
}

// AleaParamTypeSizes mirrors AleaDocumentAccessor.m_typesSize exactly
// (confirmed via javap bytecode disassembly of the real client's compiled
// class, not just the decompiled source): the byte-size of a per-param
// value for each of the 10 possible param "type" tag bytes (0-9) used in
// .amw cell-element records. Index 0 is unused (size 0); most games only
// ever emit type 3 (a single byte, e.g. a boolean/team-id flag) in
// practice for the custom fight-start-point elements, but the table
// supports every type the format allows.
var AleaParamTypeSizes = [10]int{0, 1, 1, 1, 2, 2, 4, 4, 8, 4}
