package main

import (
	"encoding/binary"
	"fmt"
	"image"
	"image/color"
)

// decodeTGAM decodes the 2.70 client's custom "MAGT" texture container (the
// .tgam files that make up gfx.jar and the animation atlases). The format was
// reverse-engineered from the decompiled client's own reader (aj_2.java) and
// its GL upload path (ph_1/kf_0/Ss.java), which uploads with GL_RGBA (6408) and
// no channel swizzle -- so the stored pixels are straight-alpha RGBA.
//
// Layout (all integers LITTLE-endian):
//
//	0   "MAGT"                     4-byte magic (0x4D 0x41 0x47 0x54)
//	4   width          u16         content width
//	6   height         u16         content height
//	8   pixelLen       i32         = paddedW * paddedH * 4
//	12  maskLen        i32         = ceil(width*height/8)   (1-bit collision mask)
//	16  pixels[pixelLen]           RGBA, row-major, padded-width stride, top-left origin
//	..  mask[maskLen]              1-bit mask (unused for preview)
//
// The pixel buffer is a next-power-of-two padded texture: each stored row is
// paddedW = nextPow2(width) pixels wide, the content sits in the top-left, and
// the right/bottom padding is fully transparent. We crop back to width x height.
func decodeTGAM(data []byte) (image.Image, error) {
	if len(data) < 16 {
		return nil, fmt.Errorf("tgam: too short (%d bytes)", len(data))
	}
	if data[0] != 'M' || data[1] != 'A' || data[2] != 'G' || data[3] != 'T' {
		return nil, fmt.Errorf("tgam: bad magic (not MAGT)")
	}
	width := int(binary.LittleEndian.Uint16(data[4:6]))
	height := int(binary.LittleEndian.Uint16(data[6:8]))
	pixelLen := int(binary.LittleEndian.Uint32(data[8:12]))
	if width <= 0 || height <= 0 || width > 8192 || height > 8192 {
		return nil, fmt.Errorf("tgam: implausible dimensions %dx%d", width, height)
	}

	paddedW := nextPow2(width)
	paddedH := nextPow2(height)
	if paddedW*paddedH*4 != pixelLen {
		return nil, fmt.Errorf("tgam: pixelLen %d != paddedW*paddedH*4 (%d, %dx%d padded)",
			pixelLen, paddedW*paddedH*4, paddedW, paddedH)
	}
	const pixOff = 16
	if pixOff+pixelLen > len(data) {
		return nil, fmt.Errorf("tgam: pixel data truncated (need %d, have %d)", pixelLen, len(data)-pixOff)
	}
	px := data[pixOff : pixOff+pixelLen]

	img := image.NewNRGBA(image.Rect(0, 0, width, height))
	for y := 0; y < height; y++ {
		rowOff := y * paddedW * 4
		for x := 0; x < width; x++ {
			o := rowOff + x*4
			img.SetNRGBA(x, y, color.NRGBA{
				R: px[o+0],
				G: px[o+1],
				B: px[o+2],
				A: px[o+3],
			})
		}
	}
	return img, nil
}

// nextPow2 returns the smallest power of two >= n (matching the client's S()).
func nextPow2(n int) int {
	if n <= 1 {
		return 1
	}
	p := 1
	for p < n {
		p <<= 1
	}
	return p
}
