package parser

import "fmt"

// This file parses elements.ade (the "Alea Document Elements" catalog):
// the shared registry of element *definitions* (walkability/height/line-
// of-sight/movement flags per graphical tile-piece) that every .amw map
// file's per-cell element records reference by ID. See
// docs/04-game-data-format.md §4.9 for the full reference derived from
// disassembling the real client's compiled classes (javap -c), covering
// WorldElementManager.read()/BasicElement.read()/
// SpatialDataElementProperties.read()/GraphicalElementProperties.read().

// ElementKind mirrors BasicElement's ELEMENT_TYPE_* constants: the
// per-element-type discriminant read as an int16 right after the int32 id.
type ElementKind int16

const (
	ElementKindBasic        ElementKind = 0
	ElementKindSpatialData  ElementKind = 1
	ElementKindGraphical    ElementKind = 2
	ElementKindTeint        ElementKind = 3
	ElementKindOffset       ElementKind = 4
	ElementKindGroup        ElementKind = 6
	ElementKindLevelUnpiled ElementKind = 8
	ElementKindParticle     ElementKind = 9
	ElementKindShadow       ElementKind = 10
	// Custom (game-specific) element types registered via
	// CustomElementFactory -- confirmed values from
	// DofusArenaCustomElementFactory.java, the ONLY custom-type registry
	// this project's client build uses (there is no generic "any custom
	// type falls back to graphical" rule -- unregistered/unknown types
	// become an UnknownElement, see below).
	ElementKindFightStartPoint      ElementKind = 1000 // FightStartPointElement: team spawn-area cell
	ElementKindFightStartCoachPoint ElementKind = 1001 // FightStartCoachPointElement: coach's own placement cell
	ElementKindBonus                ElementKind = 1002 // BonusElement: map bonus/pickup cell
)

// SpatialDataProperties mirrors SpatialDataElementProperties's fields, in
// exact read() order (confirmed via javap bytecode disassembly of the real
// client class -- see docs/04-game-data-format.md §4.9.3):
//
//	weight        int16 (getShort, despite the Java field being declared int)
//	uniqueWeightInLevel bool
//	piled         bool
//	mobileOnTop   bool
//	height        int8  (raw signed byte, see Height()/GetHeight below for the slope-halving rule)
//	virtualHeight bool
//	slope         int8
//	lineOfSight1/3/5/7/Top/Bottom bool (6 flags)
//	move1/3/5/7/Top/Bottom        bool (6 flags)
//	walkable      bool
//
// Total: 2(weight) + 3(unique+piled+mobile) + 1(height) + 1(virtualHeight)
// + 1(slope) + 6(los) + 6(move) + 1(walkable) = 21 bytes per state record.
type SpatialDataProperties struct {
	Weight              int16
	UniqueWeightInLevel bool
	Piled               bool
	MobileOnTop         bool
	HeightRaw           int8
	VirtualHeight       bool
	Slope               int8
	LineOfSight1        bool
	LineOfSight3        bool
	LineOfSight5        bool
	LineOfSight7        bool
	LineOfSightTop      bool
	LineOfSightBottom   bool
	Move1               bool
	Move3               bool
	Move5               bool
	Move7               bool
	MoveTop             bool
	MoveBottom          bool
	Walkable            bool
}

// Height returns the effective height, mirroring
// SpatialDataElementProperties.getHeight(): halved when Slope != 0
// (sloped tiles visually rise half as much per raw height unit).
func (p SpatialDataProperties) Height() float32 {
	if p.Slope != 0 {
		return float32(p.HeightRaw) * 0.5
	}
	return float32(p.HeightRaw)
}

func readSpatialDataProperties(r *AleaReader) SpatialDataProperties {
	var p SpatialDataProperties
	p.Weight = r.Int16()
	p.UniqueWeightInLevel = r.Bool()
	p.Piled = r.Bool()
	p.MobileOnTop = r.Bool()
	p.HeightRaw = r.SByte()
	p.VirtualHeight = r.Bool()
	p.Slope = r.SByte()
	p.LineOfSight1 = r.Bool()
	p.LineOfSight3 = r.Bool()
	p.LineOfSight5 = r.Bool()
	p.LineOfSight7 = r.Bool()
	p.LineOfSightTop = r.Bool()
	p.LineOfSightBottom = r.Bool()
	p.Move1 = r.Bool()
	p.Move3 = r.Bool()
	p.Move5 = r.Bool()
	p.Move7 = r.Bool()
	p.MoveTop = r.Bool()
	p.MoveBottom = r.Bool()
	p.Walkable = r.Bool()
	return p
}

// GraphicalProperties extends SpatialDataProperties with the extra fields
// GraphicalElementProperties.read() appends (confirmed via javap): gfxId
// (int32), originX/originY (int16 each, despite being declared `int`
// fields -- read via getShort()), flip (bool). Total per-state size:
// 21 (spatial) + 4 + 2 + 2 + 1 = 30 bytes -- this is the ONLY element kind
// whose per-state property record has a payload beyond the base 21 bytes;
// every other kind (Teint/Offset/Group/LevelUnpiled/Particle/Brightness/
// Basic/custom 1000/1001/1002) reads ZERO extra property bytes per state
// (BasicElementProperties.read() is an empty no-op method), confirmed by
// the fact that types 1000/1001/1002 never override readStateProperties
// and by the full-file-length verification described in the doc.
type GraphicalProperties struct {
	SpatialDataProperties
	GfxID   int32
	OriginX int16
	OriginY int16
	Flip    bool
}

func readGraphicalProperties(r *AleaReader) GraphicalProperties {
	var p GraphicalProperties
	p.SpatialDataProperties = readSpatialDataProperties(r)
	p.GfxID = r.Int32()
	p.OriginX = r.Int16()
	p.OriginY = r.Int16()
	p.Flip = r.Bool()
	return p
}

// ElementState is one (state byte -> properties) entry for an element
// definition. Only Graphical-kind elements carry real Properties data;
// for every other kind Properties is the zero value (an empty read, per
// BasicElementProperties.read()'s no-op body).
type ElementState struct {
	State      byte
	Properties GraphicalProperties
}

// ElementDef is one parsed elements.ade record: a definition (referenced
// by ID from .amw cell-element records) of a specific tile/decoration
// piece, keyed by (ID) with one or more per-state property variants (a
// single graphical element can have multiple visual "states", e.g. an
// opened/closed door, each with its own walkable/height/etc. flags).
type ElementDef struct {
	ID     int32
	Kind   ElementKind
	States []ElementState
}

// StateProperties looks up this element's properties for a given state
// byte (as referenced by a .amw cell-element record's own `state` field),
// or (zero, false) if that state isn't defined for this element (or this
// element kind carries no real properties at all, e.g. Offset/Teint).
func (e ElementDef) StateProperties(state byte) (GraphicalProperties, bool) {
	for _, s := range e.States {
		if s.State == state {
			return s.Properties, true
		}
	}
	return GraphicalProperties{}, false
}

// ElementsFile is the fully-parsed content of elements.ade: every element
// definition keyed by ID.
type ElementsFile struct {
	Elements map[int32]ElementDef
}

// ParseElementsFile parses elements.ade in full. data must NOT include the
// leading 2-byte Alea header (type code + version) -- callers should strip
// (and optionally verify) those first via PeekAleaHeader, mirroring
// AleaDocumentAccessor.readHeader()'s type-code(77 for maps' WorldElement-
// Manager reference is actually 69='E', see doc)+version check.
//
// Confirmed via javap bytecode disassembly of WorldElementManager.read():
// the file body is a flat sequence of variable-length element records,
// read until the buffer is exhausted (no top-level count prefix) --
// `while (m_streamBuffer.position() < m_streamBuffer.limit())`:
//
//	int32  elementId
//	int16  elementType          // ElementKind
//	byte   numStates
//	repeated numStates times:
//	  byte state
//	  <per-kind state properties: 30 bytes if type==Graphical(2), else 0 bytes>
//
// ElementKindGraphical(2) has non-empty (30-byte) per-state properties in
// this project's data, and so does the custom ElementKindBonus(1002)
// type -- confirmed by cross-referencing DofusArenaCustomElementFactory's
// class hierarchy: `BonusElement extends GraphicalElement` (NOT
// BasicElement directly, unlike FightStartPointElement(1000)/
// FightStartCoachPointElement(1001), which both extend BasicElement
// directly and therefore inherit BasicElement's empty no-op
// readStateProperties). This was confirmed empirically too: treating
// type 1002 as zero-byte-payload causes a full-file parse of the real
// elements.ade to desync and fail partway through; treating it as
// 30-byte (same as Graphical) makes the parse consume the file's exact
// length with zero leftover bytes (see docs/04-game-data-format.md §4.9.4
// for the full verification trail). Every other kind (1000, 1001, and the
// built-in Teint/Offset/Group/LevelUnpiled/Particle/Brightness/Basic
// types) contributes 0 extra bytes per state.
func elementKindHasGraphicalProperties(kind ElementKind) bool {
	return kind == ElementKindGraphical || kind == ElementKindBonus
}

func ParseElementsFile(data []byte) (ElementsFile, error) {
	r := NewAleaReader(data)
	out := ElementsFile{Elements: make(map[int32]ElementDef)}

	for r.Remaining() > 0 {
		id := r.Int32()
		kind := ElementKind(r.Int16())
		if r.Err() != nil {
			break
		}
		numStates := int(r.Byte())
		states := make([]ElementState, 0, numStates)
		for i := 0; i < numStates; i++ {
			state := r.Byte()
			var props GraphicalProperties
			if elementKindHasGraphicalProperties(kind) {
				props = readGraphicalProperties(r)
			}
			states = append(states, ElementState{State: state, Properties: props})
			if r.Err() != nil {
				break
			}
		}
		out.Elements[id] = ElementDef{ID: id, Kind: kind, States: states}
		if r.Err() != nil {
			break
		}
	}

	if err := r.Err(); err != nil {
		return ElementsFile{}, fmt.Errorf("gamedata: parse elements.ade: %w", err)
	}
	return out, nil
}
