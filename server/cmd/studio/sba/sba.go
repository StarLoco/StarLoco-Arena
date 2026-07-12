// Package sba decodes the client's .sba (Sprite Byte Animation) files, an
// SWF-derived tag container used by animations.jar / equipments.jar.
//
// The format was reverse-engineered from the real client's compiled classes
// (com.ankamagames.framework.graphics.sba.* and the fileFormat.tag.*
// framework base classes), disassembled/decompiled from
// client-compiled/game/core.jar -- see tools/sba-re/ for the recovered
// source and tools/README.md Phase 6 for the write-up. Confirmed details:
//
//   - 8-byte header: 3-byte signature + 1-byte version + 4-byte little-
//     endian uncompressed length.
//     Signature "sba" (lowercase) => body uncompressed;
//     "SBA" (uppercase) => body is zlib-compressed (real files use "SBA",
//     with the classic 0x78 0xDA zlib header immediately after byte 8).
//     Version is one of {1,2,3} (READABLE_VERSION); real files are v3.
//   - After (optionally) zlib-inflating the body, the stream is a sequence
//     of SWF-style tags. Each tag begins with a little-endian uint16
//     "tag header" = (code<<6)|shortLength; if shortLength == 0x3F the real
//     length follows as a little-endian uint32 ("long" tag). The tag body is
//     exactly that many bytes. A tag with code 0 (End) terminates the stream.
//   - All multi-byte integers are little-endian (InputBitStream.readUI16/32
//     read low byte first).
//
// This decoder recovers the document structure (every tag's code/id/length)
// and is the foundation the studio's Phase 6 animation inspector builds on.
// The full timeline/movie-clip compositor (turning DefineMovieClip +
// PlaceObject + ShowFrame tags into rendered frames) is a documented
// follow-on; what's here is exact and fully tested against real files.
package sba

import (
	"bytes"
	"compress/zlib"
	"encoding/binary"
	"fmt"
	"io"
)

// SBA tag codes. These are the format's OWN small namespace as assigned by
// com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder
// (creatTagInstanceFromCode) and each tag class's m_code -- they are NOT the
// SWF tag codes. Getting these wrong silently mislabels every real tag, so
// they are pinned by tests against the disassembled source.
const (
	TagEnd             = 0 // End of tag stream (framework EndTag)
	TagShowFrame       = 1 // commit a timeline frame (carries a UI16 duration)
	TagDefineBitmap    = 2 // DefineBitmap: an embedded RGBA bitmap + hot point
	TagDefineBitmapSeq = 3 // DefineBitmapSequence: a flipbook of RGBA frames
	TagDefineMovieClip = 4 // DefineMovieClip: a nested tag-stream timeline
	TagPlaceObject     = 5 // PlaceObject: put/modify a display object at a depth
	TagRemoveObject    = 6 // RemoveObject: clear a depth
	TagActionFlag      = 7 // ActionFlag: timeline action marker (opaque here)
)

// tagName maps a code to a human label (best-effort; unknown => "Tag<code>").
func tagName(code int) string {
	switch code {
	case TagEnd:
		return "End"
	case TagShowFrame:
		return "ShowFrame"
	case TagDefineBitmap:
		return "DefineBitmap"
	case TagDefineBitmapSeq:
		return "DefineBitmapSequence"
	case TagDefineMovieClip:
		return "DefineMovieClip"
	case TagPlaceObject:
		return "PlaceObject"
	case TagRemoveObject:
		return "RemoveObject"
	case TagActionFlag:
		return "ActionFlag"
	default:
		return fmt.Sprintf("Tag%d", code)
	}
}

// Header is the parsed 8-byte SBA file header.
type Header struct {
	Signature  string // "SBA" or "sba"
	Compressed bool   // true when signature is uppercase
	Version    byte   // 1, 2, or 3
	FileLength uint32 // little-endian length field
}

// Tag is one decoded tag: its code, name, byte length, and (for definition
// tags) the leading definition identifier when present.
type Tag struct {
	Code   int    `json:"code"`
	Name   string `json:"name"`
	Length int    `json:"length"`
	ID     int    `json:"id"`    // definition identifier (uint16) for definition tags, else -1
	HasID  bool   `json:"hasId"` // whether ID was decoded
}

// Document is the fully-walked SBA: header + every top-level tag.
type Document struct {
	Header Header `json:"header"`
	Tags   []Tag  `json:"tags"`
}

// Parse decodes an .sba file: validates the header, inflates the body if
// compressed, and walks the tag stream. It does not (yet) decode tag bodies
// beyond the leading definition id -- see package doc.
func Parse(data []byte) (*Document, error) {
	h, body, err := parseHeaderAndBody(data)
	if err != nil {
		return nil, err
	}
	tags, err := walkTags(body)
	if err != nil {
		return nil, err
	}
	return &Document{Header: h, Tags: tags}, nil
}

// parseHeaderAndBody validates the 8-byte header and returns the (possibly
// inflated) tag-stream body bytes.
func parseHeaderAndBody(data []byte) (Header, []byte, error) {
	if len(data) < 8 {
		return Header{}, nil, fmt.Errorf("sba: too short (%d bytes)", len(data))
	}
	sig := string(data[0:3])
	var compressed bool
	switch sig {
	case "sba":
		compressed = false
	case "SBA":
		compressed = true
	default:
		return Header{}, nil, fmt.Errorf("sba: bad signature %q", sig)
	}
	h := Header{
		Signature:  sig,
		Compressed: compressed,
		Version:    data[3],
		FileLength: binary.LittleEndian.Uint32(data[4:8]),
	}
	if h.Version != 1 && h.Version != 2 && h.Version != 3 {
		return Header{}, nil, fmt.Errorf("sba: unreadable version %d", h.Version)
	}

	body := data[8:]
	if compressed {
		zr, err := zlib.NewReader(bytes.NewReader(body))
		if err != nil {
			return Header{}, nil, fmt.Errorf("sba: zlib header: %w", err)
		}
		defer zr.Close()
		inflated, err := io.ReadAll(zr)
		if err != nil {
			return Header{}, nil, fmt.Errorf("sba: inflate: %w", err)
		}
		body = inflated
	}
	return h, body, nil
}

// walkTags walks the SWF-style tag stream (little-endian) until an End tag
// (code 0) or the buffer is exhausted.
func walkTags(body []byte) ([]Tag, error) {
	var tags []Tag
	pos := 0
	for pos+2 <= len(body) {
		tagHeader := binary.LittleEndian.Uint16(body[pos:])
		pos += 2
		code := int(tagHeader >> 6)
		length := int(tagHeader & 0x3f)
		if length == 0x3f {
			if pos+4 > len(body) {
				return nil, fmt.Errorf("sba: truncated long-tag length at %d", pos)
			}
			length = int(binary.LittleEndian.Uint32(body[pos:]))
			pos += 4
		}
		if length < 0 || pos+length > len(body) {
			return nil, fmt.Errorf("sba: tag %d (code %d) length %d overruns body (pos %d, len %d)", len(tags), code, length, pos, len(body))
		}
		tagBody := body[pos : pos+length]
		pos += length

		t := Tag{Code: code, Name: tagName(code), Length: length, ID: -1}
		// Definition tags start their body with a uint16 identifier
		// (DefinitionTag.readDefinitionTagHeader); expose it for the
		// inspector. DefineBitmap(2)/DefineBitmapSequence(3)/
		// DefineMovieClip(4) are definition tags.
		if isDefinitionTag(code) && len(tagBody) >= 2 {
			t.ID = int(binary.LittleEndian.Uint16(tagBody))
			t.HasID = true
		}
		tags = append(tags, t)

		if code == TagEnd {
			break
		}
	}
	return tags, nil
}

func isDefinitionTag(code int) bool {
	switch code {
	case TagDefineBitmap, TagDefineBitmapSeq, TagDefineMovieClip:
		return true
	default:
		return false
	}
}
