package parser

// This file ports WorldMapDocumentAccessor.readCellDatas()'s altitude/
// z-order resolution algorithm (confirmed via javap bytecode
// disassembly -- see docs/04-game-data-format.md §4.9.6), which is the
// piece that turns a cell's raw level/element list into a concrete set of
// "at this Z altitude, this ground piece is walkable/not" facts. The
// reference implementation computes this purely for CLIENT VISUAL
// Z-ORDERING (which sprite draws in front of which), but the same
// resolved (altitude, walkable) pairs are exactly the data a server-side
// walkability/pathfinding check needs -- see ResolvedCellSurface below.

// ResolvedSurface is one concrete "you can stand here at this altitude,
// and it is/isn't walkable" fact for a cell, derived from one Graphical
// element's resolved (stacked) altitude + its elements.ade Walkable flag.
type ResolvedSurface struct {
	Altitude int16
	Walkable bool
	Height   float32 // the element's own height (for stacking/ascend-limit purposes)
}

// ResolvedGfx is one drawable sprite for a cell: which gfx to draw, its
// hot-point origin offset + horizontal flip (from the element's
// GraphicalProperties), and the resolved stacking altitude (the same value
// ResolveCellSurfaces computes, used for isometric Y-lift and z-ordering).
// LevelIndex/Order preserve the exact per-cell paint order the client uses
// (levels bottom-to-top, elements in file order within a level).
type ResolvedGfx struct {
	GfxID      int32
	OriginX    int16
	OriginY    int16
	Flip       bool
	Altitude   int16
	LevelIndex int
	Order      int
	// AltitudeOrder is the client's per-cell draw-order value
	// (WorldMapDocumentAccessor.readCellDatas()'s `zCellOrderAltitude`): a
	// monotonically-increasing-within-a-cell float derived from each
	// element's TOP altitude (altitude+height), nudged up by a tiny pad when
	// it would otherwise not increase, so stacked pieces on one cell keep a
	// stable back-to-front order. The final scene z-order the client sorts
	// on is `(x+y)*cellHeight/2 + AltitudeOrder` (see DisplayedCell
	// .updateDisplayedElements: zValue = (-screenY + altitudeOrder)/...),
	// which the renderer reproduces to draw sprites correctly.
	AltitudeOrder float32
}

// ResolveCellGfx runs the exact same altitude-tracking loop as
// ResolveCellSurfaces, but emits a ResolvedGfx for every Graphical/Bonus
// element that carries a gfx (GfxID != 0). This is what a renderer (the
// studio's map viewer) needs to draw the real tile sprites, kept in this
// file so it shares one source of truth with the walkability resolution and
// can never drift from it. See ResolveCellSurfaces for the per-step doc.
func ResolveCellGfx(cell AMWCell, elements map[int32]ElementDef) []ResolvedGfx {
	var out []ResolvedGfx
	order := 0

	var currentAltitude, oldLevelAltitude int16

	// Per-cell draw-order state, mirroring readCellDatas() exactly:
	// zCellOrderAltitude increases monotonically across the cell's elements
	// (padded by possibleAltitudePad when the next element's top wouldn't
	// exceed the running max), giving a stable within-cell paint order.
	const possibleAltitudePad = float32(1.0e-4)
	var minOrderPossibleAltitude float32

	for li, level := range cell.Levels {
		levelAltitude := currentAltitude

		if len(level.Elements) == 0 {
			oldLevelAltitude = levelAltitude
			continue
		}

		isLevelPiled := true
		for _, el := range level.Elements {
			if def, ok := elements[el.ElementID]; ok && def.Kind == ElementKindLevelUnpiled {
				isLevelPiled = false
				break
			}
		}
		if !isLevelPiled {
			levelAltitude = oldLevelAltitude
			currentAltitude = oldLevelAltitude
		}

		for _, el := range level.Elements {
			def, ok := elements[el.ElementID]
			if !ok {
				continue
			}

			switch def.Kind {
			case ElementKindOffset:
				offset, isAbs := decodeOffsetParams(el)
				if isAbs {
					currentAltitude = offset
				} else {
					currentAltitude += offset
				}
				levelAltitude = currentAltitude

			case ElementKindGraphical, ElementKindBonus:
				props, ok := def.StateProperties(el.State)
				if !ok {
					continue
				}
				elementAltitude := levelAltitude
				if props.Piled {
					elementAltitude = currentAltitude
				}

				// zCellOrderAltitude: readCellDatas()'s exact computation.
				height := props.Height()
				elementTopAltitude := float32(elementAltitude) + height
				zCellOrderAltitude := elementTopAltitude
				if minOrderPossibleAltitude > elementTopAltitude {
					zCellOrderAltitude = minOrderPossibleAltitude + possibleAltitudePad
				}

				if props.GfxID != 0 {
					out = append(out, ResolvedGfx{
						GfxID:         props.GfxID,
						OriginX:       props.OriginX,
						OriginY:       props.OriginY,
						Flip:          props.Flip,
						Altitude:      elementAltitude,
						LevelIndex:    li,
						Order:         order,
						AltitudeOrder: zCellOrderAltitude,
					})
					order++
				}

				minOrderPossibleAltitude = zCellOrderAltitude
				if props.Piled {
					currentAltitude += int16(height)
				}

			default:
			}
		}

		if isLevelPiled {
			oldLevelAltitude = levelAltitude
		}
	}

	return out
}

// ResolveCellSurfaces computes every ResolvedSurface for one AMWCell,
// mirroring readCellDatas()'s altitude-tracking loop exactly:
//   - currentAltitude accumulates upward only when a "piled" Graphical
//     element is placed (stacking, e.g. a wall standing on a floor tile).
//   - An OffsetElement(4) resets/adjusts currentAltitude directly (an
//     explicit height jump authored by the map, e.g. a raised platform).
//   - A LevelUnpiledElement(8) present anywhere in a level marks that
//     whole level as "not piled": that level's elements resolve against
//     the last PILED level's saved altitude instead of continuing to
//     stack on top of the immediately-previous level (used for e.g.
//     ceiling decorations that shouldn't raise the walkable floor
//     height).
//   - Only ElementKindGraphical(2)-kind elements produce a real
//     ResolvedSurface (they're the only kind with a real Walkable flag);
//     every other kind (Offset/Teint/Brightness/Group/LevelUnpiled/
//     Particle, and this project's custom 1000/1001/1002 kinds) is
//     skipped for walkability purposes -- they either adjust the
//     tracking state (Offset) or carry no spatial-collision meaning at
//     all (Teint/Brightness/Group/Particle/custom markers).
//
// elements is the parsed elements.ade catalog (ElementsFile.Elements),
// used to resolve each AMWCellElement.ElementID -> its Kind/Walkable/
// Height/Piled flags for the specific State that cell-element record
// references.
func ResolveCellSurfaces(cell AMWCell, elements map[int32]ElementDef) []ResolvedSurface {
	var out []ResolvedSurface

	var currentAltitude, oldLevelAltitude int16

	for _, level := range cell.Levels {
		levelAltitude := currentAltitude

		if len(level.Elements) == 0 {
			oldLevelAltitude = levelAltitude
			continue
		}

		isLevelPiled := true
		for _, el := range level.Elements {
			if def, ok := elements[el.ElementID]; ok && def.Kind == ElementKindLevelUnpiled {
				isLevelPiled = false
				break
			}
		}
		if !isLevelPiled {
			levelAltitude = oldLevelAltitude
			currentAltitude = oldLevelAltitude
		}

		for _, el := range level.Elements {
			def, ok := elements[el.ElementID]
			if !ok {
				continue // unresolved elementId -- ignore rather than guess
			}

			switch def.Kind {
			case ElementKindOffset:
				// OffsetElement's params: [type, isAbsolute-flag-byte(param index 1
				// per OffsetElement.isAbsolute checking element.getParams()[3]),
				// offset-value(param index 1's raw byte, OffsetElement.getOffset()
				// reads getParams()[1])]. Mirrors OffsetElement.java's exact
				// (decompiler-quirky, 1-indexed-into-a-flat-byte-array) accessors.
				offset, isAbs := decodeOffsetParams(el)
				if isAbs {
					currentAltitude = offset
				} else {
					currentAltitude += offset
				}
				levelAltitude = currentAltitude

			case ElementKindGraphical, ElementKindBonus:
				props, ok := def.StateProperties(el.State)
				if !ok {
					continue
				}
				elementAltitude := levelAltitude
				if props.Piled {
					elementAltitude = currentAltitude
				}
				height := props.Height()
				out = append(out, ResolvedSurface{
					Altitude: elementAltitude,
					Walkable: props.Walkable,
					Height:   height,
				})
				if props.Piled {
					currentAltitude += int16(height)
				}

			default:
				// Teint/Brightness/Group/LevelUnpiled/Particle/custom
				// fight-marker kinds (1000/1001): no walkability meaning,
				// skip.
			}
		}

		if isLevelPiled {
			oldLevelAltitude = levelAltitude
		}
	}

	return out
}

// decodeOffsetParams extracts OffsetElement's two pieces of data from an
// AMWCellElement's raw params, mirroring OffsetElement.java exactly:
//
//	getOffset(element)     = element.getParams()[1]   (a raw signed byte)
//	isAbsolute(element)    = element.getParamsCount()==2 && element.getParams()[3]==1
//
// Note the Java source indexes directly into the FLAT byte array
// underlying WorldElement.m_params (which is the concatenation of every
// param's [type-byte, value-bytes...] -- NOT a per-param-decoded array),
// so index 1 is "the first param's first payload byte" (skipping its
// type-tag byte at index 0) and index 3 is "the second param's first
// payload byte" (index 2 is the second param's type tag). This matches
// AMWCellElement.ParamBytes's own layout exactly (concatenated
// [type,payload...] blobs), so we index into el.ParamBytes directly
// rather than via the per-param Param() helper, to mirror the reference
// 1:1 including its implicit assumption about param encoding sizes.
func decodeOffsetParams(el AMWCellElement) (offset int16, isAbsolute bool) {
	if len(el.ParamBytes) > 1 {
		offset = int16(int8(el.ParamBytes[1]))
	}
	if len(el.ParamTypes) == 2 && len(el.ParamBytes) > 3 {
		isAbsolute = el.ParamBytes[3] == 1
	}
	return offset, isAbsolute
}
