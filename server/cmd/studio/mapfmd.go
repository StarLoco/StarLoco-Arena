package main

import (
	"archive/zip"
	"fmt"
	"path"
	"strings"
)

// This file decodes the 2.70 client's fight-placement files (contents/maps/
// fight/<world>.jar!/<world>.fmd) into per-team start cells and coach pedestal
// cells. Reverse-engineered from the client's Om class; verified against
// internal/game/arena.go (world 5) in maptopo_test.go. LITTLE-ENDIAN.

// fmdCell is a packed placement cell (x, y, altitude).
type fmdCell struct {
	X int32 `json:"x"`
	Y int32 `json:"y"`
	Z int16 `json:"z"`
}

// fmdData holds a fight map's placement cells.
type fmdData struct {
	Coach []fmdCell // 6 coach-pedestal slots (slot 0 = side 0, slot 1 = side 1)
	Team0 []fmdCell // side-0 fighter start cells
	Team1 []fmdCell // side-1 fighter start cells
}

// unpackFmdCell decodes a packed placement cell (Om.hb):
//
//	x = (v>>20 & 0xFFF) - 2047, y = (v>>8 & 0xFFF) - 2047, z = (v & 0xFF) - 127.
func unpackFmdCell(v uint32) fmdCell {
	return fmdCell{
		X: int32((v>>20)&0xFFF) - 2047,
		Y: int32((v>>8)&0xFFF) - 2047,
		Z: int16(int(v&0xFF) - 127),
	}
}

// parseFmd decodes a <world>.fmd payload (Om.b):
//
//	6× i32 coach cells, u16 count word (hi=team0 count, lo=team1 count),
//	team0 cells (i32×), team1 cells (i32×), u8 special count, {i32,i32}× specials.
func parseFmd(data []byte) (*fmdData, error) {
	c := &leCur{b: data}
	fd := &fmdData{}
	for i := 0; i < 6; i++ {
		fd.Coach = append(fd.Coach, unpackFmdCell(c.u32()))
	}
	word := int(uint16(c.i16()))
	t0 := word >> 8
	t1 := word & 0xFF
	if t0 < 0 || t0 > 4096 || t1 < 0 || t1 > 4096 {
		return nil, fmt.Errorf("fmd: implausible team counts %d/%d", t0, t1)
	}
	for i := 0; i < t0; i++ {
		fd.Team0 = append(fd.Team0, unpackFmdCell(c.u32()))
	}
	for i := 0; i < t1; i++ {
		fd.Team1 = append(fd.Team1, unpackFmdCell(c.u32()))
	}
	// Trailing special cells are not needed for the viewer.
	if !c.ok() {
		return nil, fmt.Errorf("fmd: truncated")
	}
	return fd, nil
}

// parseFightJar opens fight/<id>.jar and decodes its "<id>.fmd" entry, or
// returns (nil, nil) if the jar has no .fmd (i.e. the world is not an arena).
func parseFightJar(zpath string) (*fmdData, error) {
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return nil, err
	}
	defer r.Close()
	for _, f := range r.File {
		if strings.HasSuffix(strings.ToLower(path.Base(f.Name)), ".fmd") {
			data, rerr := readAll(f)
			if rerr != nil {
				return nil, rerr
			}
			return parseFmd(data)
		}
	}
	return nil, nil
}

// fightJarHasFmd reports whether fight/<id>.jar exists and contains a .fmd
// (cheap arena test; no decode).
func fightJarHasFmd(zpath string) bool {
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return false
	}
	defer r.Close()
	for _, f := range r.File {
		if strings.HasSuffix(strings.ToLower(path.Base(f.Name)), ".fmd") {
			return true
		}
	}
	return false
}
