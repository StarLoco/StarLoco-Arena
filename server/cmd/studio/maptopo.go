package main

import (
	"archive/zip"
	"encoding/binary"
	"fmt"
	"io"
	"path"
	"regexp"
	"sort"
	"strconv"
)

// This file decodes the 2.70 client's topology tiles (contents/maps/tplg/
// <world>.jar!/<i>_<j>) into a world's real floor cells (x, y, altitude). The
// format was reverse-engineered from the decompiled client (TopologyMapManager
// auU, tile parsers ajo_2/ajq_2/ajg_2/aji_2/ajj_2/ajc_2, base header acm_1) and
// is verified byte-for-byte against the server's hand-decoded practice arena
// (internal/game/arena.go world 5) in maptopo_test.go.
//
// Everything here is LITTLE-ENDIAN (client reader class acf). A tile is an 18x18
// block of cells; a cell is a real floor cell (part of the arena) iff it has a
// layer whose movement mask cCJ != -1 and a non-void altitude. Void cells (no
// floor) are simply omitted.

// topoVoid is the Short.MIN_VALUE altitude sentinel used by tile types 3/5/6.
const topoVoid = int16(-32768)

// topoCell is one real floor cell of a world's topology. A cell is "real"
// (has a floor, so it is rendered) iff its altitude is not the void sentinel;
// Walkable distinguishes a standable floor (movement mask != -1) from an
// obstacle cell (a raised block a fighter can't stand on but that exists
// geometrically).
type topoCell struct {
	X        int32 `json:"x"`
	Y        int32 `json:"y"`
	Alt      int16 `json:"alt"`
	Walkable bool  `json:"w"`
}

// worldTopo is a fully-assembled world topology (all tiles merged).
type worldTopo struct {
	WorldID                int
	MinX, MinY, MaxX, MaxY int32
	Cells                  []topoCell
	Truncated              bool
}

// leCur is a bounds-checked little-endian cursor over a tile/fmd payload.
type leCur struct {
	b   []byte
	p   int
	err bool
}

func (c *leCur) ok() bool { return !c.err }

func (c *leCur) need(n int) bool {
	if c.err || n < 0 || c.p+n > len(c.b) {
		c.err = true
		return false
	}
	return true
}

func (c *leCur) u8() byte {
	if !c.need(1) {
		return 0
	}
	v := c.b[c.p]
	c.p++
	return v
}

func (c *leCur) i16() int16 {
	if !c.need(2) {
		return 0
	}
	v := int16(binary.LittleEndian.Uint16(c.b[c.p:]))
	c.p += 2
	return v
}

func (c *leCur) u32() uint32 {
	if !c.need(4) {
		return 0
	}
	v := binary.LittleEndian.Uint32(c.b[c.p:])
	c.p += 4
	return v
}

var tileNameRe = regexp.MustCompile(`^(-?\d+)_(-?\d+)$`)

// decodeTile decodes one tplg tile into its real floor cells (absolute x,y).
// It reads the 7-byte common header (u8 type, i16 tileI, i16 tileJ, i16 wpBase),
// then dispatches on the low nibble of type to one of the six body encodings.
func decodeTile(data []byte) ([]topoCell, error) {
	c := &leCur{b: data}
	typ := c.u8() & 0x0F
	aG := int32(c.i16()) * 18
	aH := int32(c.i16()) * 18
	wp := int(c.i16())

	out := make([]topoCell, 0, 32)
	// emit records a cell only when it has a real floor (altitude != void).
	// walkable = movement mask != -1 (obstacle cells are kept, flagged unwalkable).
	emit := func(idx int, alt int16, cCJ int8) {
		if alt == topoVoid {
			return
		}
		out = append(out, topoCell{
			X: aG + int32(idx%18), Y: aH + int32(idx/18), Alt: alt, Walkable: cCJ != -1,
		})
	}

	switch typ {
	case 0: // ajo_2: uniform tile, one movement mask for all 324 cells.
		cCJ := int8(c.u8())
		for idx := 0; idx < 324; idx++ {
			emit(idx, int16(wp), cCJ)
		}
	case 1: // ajq_2: nibble-packed (8-altitude palette, 2-move palette).
		var dRS [8]int16
		for i := range dRS {
			dRS[i] = int16(wp + int(c.i16()))
		}
		var dRT [2]int8
		for i := range dRT {
			dRT[i] = int8(c.u8())
		}
		dRU := make([]byte, 162)
		for i := range dRU {
			dRU[i] = c.u8()
		}
		for idx := 0; idx < 324; idx++ {
			var nib byte
			if idx&1 != 0 {
				nib = dRU[idx>>1] & 0x0F
			} else {
				nib = (dRU[idx>>1] >> 4) & 0x0F
			}
			emit(idx, dRS[nib>>1], dRT[nib&1])
		}
	case 2: // ajg_2: byte-per-cell (16-altitude palette, 4-move palette).
		var dRS [16]int16
		for i := range dRS {
			dRS[i] = int16(wp + int(c.i16()))
		}
		var dRT [4]int8
		for i := range dRT {
			dRT[i] = int8(c.u8())
		}
		dRU := make([]byte, 324)
		for i := range dRU {
			dRU[i] = c.u8()
		}
		for idx := 0; idx < 324; idx++ {
			b := dRU[idx]
			emit(idx, dRS[b&0x0F], dRT[(b>>4)&3])
		}
	case 3: // aji_2: short-per-cell (10-bit altitude, 4-bit move nibble).
		cells := make([]uint16, 324)
		for i := range cells {
			cells[i] = uint16(c.i16())
		}
		for idx := 0; idx < 324; idx++ {
			s := cells[idx]
			altBits := int(s & 0x3FF)
			n := int((s >> 12) & 0xF)
			if n >= 8 {
				n -= 16 // sign-extend the 4-bit move nibble; 15 -> -1
			}
			if altBits == 0 {
				continue // void: no floor
			}
			emit(idx, int16(wp-512+altBits), int8(n))
		}
	case 5: // ajj_2: multilayer, palette aba.
		var dSi [64]byte
		for i := range dSi {
			dSi[i] = c.u8()
		}
		n := int(uint16(c.i16()))
		reduceLayers(c, n, wp, 22, emit)
	case 6: // ajc_2: multilayer, direct aba + slope.
		n := int(uint16(c.i16()))
		reduceLayers(c, n, wp, 20, emit)
	default:
		return nil, fmt.Errorf("topo: unsupported tile type %d", typ)
	}
	if !c.ok() {
		return nil, fmt.Errorf("topo: tile truncated (type %d)", typ)
	}
	return out, nil
}

// reduceLayers reads n packed i32 layer entries (types 5/6 share the key/altitude
// bit layout; the movement nibble sits at moveShift: 22 for type 5, 20 for type
// 6) and emits, per cell, its lowest-altitude non-void layer (keeping obstacle
// layers, whose walkable flag is carried through). The trailing per-entry
// palette/slope arrays are not needed for the floor grid, so we stop after the
// entry table.
func reduceLayers(c *leCur, n, wp int, moveShift uint, emit func(idx int, alt int16, cCJ int8)) {
	type layer struct {
		alt int16
		cCJ int8
	}
	best := make(map[int]layer, n)
	for k := 0; k < n && c.ok(); k++ {
		v := c.u32()
		lx := int(v & 0x1F)
		ly := int((v >> 5) & 0x1F)
		altBits := int((v >> 10) & 0x3FF)
		if altBits == 0 {
			continue // void layer: no floor here
		}
		mv := int((v >> moveShift) & 0xF)
		if mv >= 8 {
			mv -= 16 // sign-extend; 15 -> -1 (obstacle)
		}
		alt := int16(wp - 512 + altBits)
		idx := ly*18 + lx
		if cur, has := best[idx]; !has || alt < cur.alt {
			best[idx] = layer{alt: alt, cCJ: int8(mv)}
		}
	}
	for idx, l := range best {
		emit(idx, l.alt, l.cCJ)
	}
}

// parseTplgJar opens a tplg world jar, decodes every "<i>_<j>" tile, and merges
// them into a single world topology. cellCap bounds the returned cell count (0 =
// unlimited) so a huge overworld can't flood the UI; Truncated reports a cap hit.
func parseTplgJar(zpath string, cellCap int) (*worldTopo, error) {
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return nil, err
	}
	defer r.Close()

	wt := &worldTopo{}
	first := true
	for _, f := range r.File {
		name := path.Base(f.Name)
		if !tileNameRe.MatchString(name) {
			continue
		}
		data, rerr := readAll(f)
		if rerr != nil {
			return nil, rerr
		}
		cells, derr := decodeTile(data)
		if derr != nil {
			continue // skip a malformed tile rather than failing the whole world
		}
		for _, c := range cells {
			if first {
				wt.MinX, wt.MaxX, wt.MinY, wt.MaxY = c.X, c.X, c.Y, c.Y
				first = false
			} else {
				if c.X < wt.MinX {
					wt.MinX = c.X
				}
				if c.X > wt.MaxX {
					wt.MaxX = c.X
				}
				if c.Y < wt.MinY {
					wt.MinY = c.Y
				}
				if c.Y > wt.MaxY {
					wt.MaxY = c.Y
				}
			}
			if cellCap > 0 && len(wt.Cells) >= cellCap {
				wt.Truncated = true
				continue
			}
			wt.Cells = append(wt.Cells, c)
		}
	}
	sort.Slice(wt.Cells, func(i, j int) bool {
		if wt.Cells[i].Y != wt.Cells[j].Y {
			return wt.Cells[i].Y < wt.Cells[j].Y
		}
		return wt.Cells[i].X < wt.Cells[j].X
	})
	return wt, nil
}

// tileCount returns how many "<i>_<j>" tile entries a tplg jar holds (cheap; no
// decode) plus a flag if it could be opened.
func tileCount(zpath string) (int, bool) {
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return 0, false
	}
	defer r.Close()
	n := 0
	for _, f := range r.File {
		if tileNameRe.MatchString(path.Base(f.Name)) {
			n++
		}
	}
	return n, true
}

// readAll reads a whole zip entry.
func readAll(f *zip.File) ([]byte, error) {
	rc, err := f.Open()
	if err != nil {
		return nil, err
	}
	defer rc.Close()
	return io.ReadAll(rc)
}

// worldIDFromJar parses "5" from ".../5.jar".
func worldIDFromJar(name string) (int, bool) {
	base := name
	if i := len(base) - 4; i > 0 && base[i:] == ".jar" {
		base = base[:i]
	}
	id, err := strconv.Atoi(base)
	return id, err == nil
}
