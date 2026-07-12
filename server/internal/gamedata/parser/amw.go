package parser

import "fmt"

// This file parses .amw map files (the compiled per-region cell/element
// layout format read by WorldMapDocumentAccessor.read()/readCellDatas()).
// See docs/04-game-data-format.md §4.9 for the full reference, derived
// from disassembling the real client's compiled WorldMapDocumentAccessor
// .class via javap -c and verified byte-for-byte against real map files
// under client/content/data.jar/data/maps/**/*.amw (every file parsed
// consumes EXACTLY its own length, with zero leftover/underrun bytes,
// across every fixture checked including the two files backing this
// project's actual fight map, mapID=2).

// AMWCellElement is one parsed cell-level element record (one "thing on
// this cell" -- ground tile, wall piece, decoration, or a custom
// DofusArena element like a fight-start point), see readCellDatas() in
// WorldMapDocumentAccessor.java (confirmed via javap):
//
//	int32 elementId          // -> looked up in elements.ade's ElementsFile
//	byte  state               // -> which ElementState variant of that element
//	int32 cellInstanceGroupId // grouping id for WorldGroupManager (cosmetic; unused server-side)
//	byte  paramCount
//	repeated paramCount times:
//	  byte type               // index into AleaParamTypeSizes
//	  <type-sized raw bytes>  // e.g. FightStartPointElement/FightStartCoachPointElement's
//	                          //   single param is type=3 (1 byte): 1=team-A/coach-A side,
//	                          //   0=team-B/coach-B side (confirmed by cross-referencing
//	                          //   real cell coordinates against this project's existing
//	                          //   hardcoded teleport-destination cells for fightMapID=2,
//	                          //   see docs/04-game-data-format.md §4.9.5)
type AMWCellElement struct {
	ElementID  int32
	State      byte
	GroupID    int32
	ParamBytes []byte // raw concatenated (type-byte + payload) blobs, one per param
	ParamTypes []byte // the type tag of each param, parallel to slicing ParamBytes -- see Param() helper
}

// Param decodes the i'th parameter's raw payload bytes (excluding its own
// type tag byte), or nil if i is out of range. Use ParamAsByte for the
// single most common case (FightStartPointElement/FightStartCoachPointElement's
// team-side flag, a lone type=3 1-byte param).
func (e AMWCellElement) Param(i int) []byte {
	if i < 0 || i >= len(e.ParamTypes) {
		return nil
	}
	off := 0
	for j := 0; j < i; j++ {
		off += 1 + AleaParamTypeSizes[e.ParamTypes[j]] // +1 for that param's own type tag byte
	}
	size := AleaParamTypeSizes[e.ParamTypes[i]]
	start := off + 1 // skip this param's own type tag byte
	if start+size > len(e.ParamBytes) {
		return nil
	}
	return e.ParamBytes[start : start+size]
}

// ParamAsByte returns the i'th param's first payload byte (the common
// case for the single-byte type=3 params used by FightStartPointElement/
// FightStartCoachPointElement's team-side flag), or (0, false) if that
// param doesn't exist.
func (e AMWCellElement) ParamAsByte(i int) (byte, bool) {
	b := e.Param(i)
	if len(b) == 0 {
		return 0, false
	}
	return b[0], true
}

// AMWLevel is one Z-level's worth of elements on a single cell (a cell can
// stack multiple levels -- floor, then a piece of furniture on top of it,
// etc., mirroring readCellDatas()'s per-cell `levelCount` loop).
type AMWLevel struct {
	Elements []AMWCellElement
}

// AMWCell is one fully-parsed map cell: world X/Y plus every level's
// elements.
type AMWCell struct {
	X, Y   int32
	Levels []AMWLevel
}

// AMWMapChunk is the fully-parsed content of one .amw file: a
// `size`x`size` square chunk of cells at world-cell-offset (CoordX*size,
// CoordY*size), matching the filename convention `map_<CoordX>_<CoordY>.amw`
// (confirmed empirically: map_-1_0.amw's header CoordX decodes to exactly
// -1).
type AMWMapChunk struct {
	CoordX int32
	CoordY int32
	Size   int32 // cells per side; cell count = Size*Size
	Cells  []AMWCell
}

// CellAt returns the cell at absolute world coordinates (x,y), or
// (zero, false) if out of this chunk's range.
func (m AMWMapChunk) CellAt(x, y int32) (AMWCell, bool) {
	baseX, baseY := m.CoordX*m.Size, m.CoordY*m.Size
	if x < baseX || x >= baseX+m.Size || y < baseY || y >= baseY+m.Size {
		return AMWCell{}, false
	}
	localX, localY := x-baseX, y-baseY
	idx := int(localY*m.Size + localX) // matches readCellDatas(): worldCellX = coordCellX + j%size, worldCellY = coordCellY + j/size
	if idx < 0 || idx >= len(m.Cells) {
		return AMWCell{}, false
	}
	return m.Cells[idx], true
}

// ParseAMWFile parses the full contents of one .amw map chunk file. data
// must NOT include the 2-byte Alea header (type code 'M'=77 + version 1)
// -- callers should verify/strip that first via PeekAleaHeader, mirroring
// AleaDocumentAccessor.readHeader()'s check
// (WorldMapDocumentAccessor's constructor sets
// setAleaDocumentTypeCode((byte)77) i.e. ASCII 'M', setAleaDocumentVersion((byte)1)).
//
// Confirmed via javap bytecode disassembly of
// WorldMapDocumentAccessor.read()/readCellDatas():
//
//	int32 coordX
//	int32 coordY
//	byte  size                 // cells per side; cellCount = size*size
//	repeated cellCount times (row-major: cellIndex = localY*size + localX):
//	  byte levelCount
//	  repeated levelCount times:
//	    byte elementCount
//	    repeated elementCount times:
//	      int32 elementId
//	      byte  state
//	      int32 cellInstanceGroupId
//	      byte  paramCount
//	      repeated paramCount times:
//	        byte type
//	        <AleaParamTypeSizes[type] raw bytes>
//
// All integers are little-endian (the Alea convention, distinct from the
// big-endian .dat game-data format -- see AleaReader's doc comment).
func ParseAMWFile(data []byte) (AMWMapChunk, error) {
	r := NewAleaReader(data)
	var out AMWMapChunk

	out.CoordX = r.Int32()
	out.CoordY = r.Int32()
	out.Size = int32(r.Byte())
	if r.Err() != nil {
		return AMWMapChunk{}, fmt.Errorf("gamedata: parse .amw header: %w", r.Err())
	}

	cellCount := int(out.Size) * int(out.Size)
	out.Cells = make([]AMWCell, 0, cellCount)

	baseX, baseY := out.CoordX*out.Size, out.CoordY*out.Size
	for i := 0; i < cellCount; i++ {
		cell := AMWCell{
			X: baseX + int32(i)%out.Size,
			Y: baseY + int32(i)/out.Size,
		}

		levelCount := int(r.Byte())
		cell.Levels = make([]AMWLevel, 0, levelCount)
		for lvl := 0; lvl < levelCount; lvl++ {
			elemCount := int(r.Byte())
			level := AMWLevel{Elements: make([]AMWCellElement, 0, elemCount)}
			for e := 0; e < elemCount; e++ {
				el := AMWCellElement{}
				el.ElementID = r.Int32()
				el.State = r.Byte()
				el.GroupID = r.Int32()
				paramCount := int(r.Byte())
				el.ParamTypes = make([]byte, 0, paramCount)
				var paramBuf []byte
				for p := 0; p < paramCount; p++ {
					ptype := r.Byte()
					el.ParamTypes = append(el.ParamTypes, ptype)
					paramBuf = append(paramBuf, ptype)
					size := AleaParamTypeSizes[ptype]
					paramBuf = append(paramBuf, r.Bytes(size)...)
				}
				el.ParamBytes = paramBuf
				level.Elements = append(level.Elements, el)
				if r.Err() != nil {
					return AMWMapChunk{}, fmt.Errorf("gamedata: parse .amw cell(%d,%d) level %d element %d: %w", cell.X, cell.Y, lvl, e, r.Err())
				}
			}
			cell.Levels = append(cell.Levels, level)
		}
		out.Cells = append(out.Cells, cell)
	}

	if err := r.Err(); err != nil {
		return AMWMapChunk{}, fmt.Errorf("gamedata: parse .amw: %w", err)
	}
	if r.Remaining() != 0 {
		return AMWMapChunk{}, fmt.Errorf("gamedata: parse .amw: %d trailing bytes after full parse (format mismatch)", r.Remaining())
	}
	return out, nil
}

// AleaFileHeader is the 2-byte type-code+version prefix every Alea-format
// document starts with (AleaDocumentAccessor.readHeader()).
type AleaFileHeader struct {
	TypeCode byte
	Version  byte
}

// PeekAleaHeader reads (without erroring on mismatch -- that's the
// caller's call) the 2-byte Alea header from the start of data, returning
// it alongside the remaining payload bytes ready to hand to
// ParseElementsFile/ParseAMWFile.
func PeekAleaHeader(data []byte) (AleaFileHeader, []byte, error) {
	if len(data) < 2 {
		return AleaFileHeader{}, nil, fmt.Errorf("gamedata: alea file too short for header (%d bytes)", len(data))
	}
	return AleaFileHeader{TypeCode: data[0], Version: data[1]}, data[2:], nil
}

// Known Alea document type codes (AleaDocumentAccessor.setAleaDocumentTypeCode),
// confirmed via the decompiled constructors of WorldMapDocumentAccessor
// (.amw, 77='M') and WorldElementManager (.ade, 69='E').
const (
	AleaTypeCodeWorldMap      byte = 77 // 'M' -- .amw
	AleaTypeCodeWorldElements byte = 69 // 'E' -- .ade
	AleaDocumentVersion1      byte = 1
)
