package main

import (
	"fmt"
	"image"
	"image/color"
)

// decodeTGA decodes a standard Targa image into an image.NRGBA. The 2.70
// client's gui.jar ships thousands of plain .tga files (uncompressed truecolor
// type 2, 32bpp BGRA -- confirmed by sampling real headers) plus the common
// near neighbours (RLE truecolor type 10, and 24bpp), so the viewer is robust.
// Colour-mapped (type 1/9) and grayscale (type 3/11) are not needed and return
// an error rather than a wrong image.
//
// TGA header (18 bytes, little-endian):
//
//	0  idLength        u8
//	1  colorMapType    u8   (0 = none)
//	2  imageType       u8   (2 = uncompressed truecolor, 10 = RLE truecolor)
//	3  colorMapSpec    5 bytes (skipped; 0 for our files)
//	8  xOrigin         u16
//	10 yOrigin         u16
//	12 width           u16
//	14 height          u16
//	16 pixelDepth      u8   (24 or 32)
//	17 imageDescriptor u8   (bit5 = top-origin; bits0-3 = alpha depth)
func decodeTGA(data []byte) (image.Image, error) {
	if len(data) < 18 {
		return nil, fmt.Errorf("tga: too short (%d bytes)", len(data))
	}
	idLength := int(data[0])
	colorMapType := data[1]
	imageType := data[2]
	width := int(uint16(data[12]) | uint16(data[13])<<8)
	height := int(uint16(data[14]) | uint16(data[15])<<8)
	pixelDepth := int(data[16])
	descriptor := data[17]

	if colorMapType != 0 {
		return nil, fmt.Errorf("tga: colour-mapped images unsupported (colorMapType=%d)", colorMapType)
	}
	if imageType != 2 && imageType != 10 {
		return nil, fmt.Errorf("tga: unsupported image type %d (only 2/10 truecolor)", imageType)
	}
	if pixelDepth != 32 && pixelDepth != 24 {
		return nil, fmt.Errorf("tga: unsupported pixel depth %d", pixelDepth)
	}
	if width <= 0 || height <= 0 || width > 8192 || height > 8192 {
		return nil, fmt.Errorf("tga: implausible dimensions %dx%d", width, height)
	}

	bytesPerPixel := pixelDepth / 8
	off := 18 + idLength // skip image ID field (0 in our files)
	if off > len(data) {
		return nil, fmt.Errorf("tga: header/id overruns buffer")
	}

	pixelCount := width * height
	raw := make([]byte, pixelCount*bytesPerPixel) // BGRA/BGR pixel stream, top-to-bottom in file order

	if imageType == 2 {
		if off+len(raw) > len(data) {
			return nil, fmt.Errorf("tga: pixel data truncated (need %d, have %d)", len(raw), len(data)-off)
		}
		copy(raw, data[off:off+len(raw)])
	} else {
		if err := decodeTGARLE(data[off:], raw, bytesPerPixel); err != nil {
			return nil, err
		}
	}

	img := image.NewNRGBA(image.Rect(0, 0, width, height))
	topOrigin := descriptor&0x20 != 0 // bit5: 1 = origin top-left, 0 = bottom-left

	for i := 0; i < pixelCount; i++ {
		b := raw[i*bytesPerPixel+0]
		g := raw[i*bytesPerPixel+1]
		r := raw[i*bytesPerPixel+2]
		a := uint8(255)
		if bytesPerPixel == 4 {
			a = raw[i*bytesPerPixel+3]
		}
		x := i % width
		row := i / width
		y := row
		if !topOrigin {
			y = height - 1 - row // file rows run bottom-to-top; flip into image space
		}
		img.SetNRGBA(x, y, color.NRGBA{R: r, G: g, B: b, A: a})
	}
	return img, nil
}

// decodeTGARLE expands an RLE (image type 10) pixel stream into dst.
func decodeTGARLE(src, dst []byte, bpp int) error {
	di := 0
	si := 0
	for di < len(dst) {
		if si >= len(src) {
			return fmt.Errorf("tga: RLE stream ended early")
		}
		packet := src[si]
		si++
		count := int(packet&0x7f) + 1
		if packet&0x80 != 0 {
			if si+bpp > len(src) {
				return fmt.Errorf("tga: RLE run pixel truncated")
			}
			for c := 0; c < count && di < len(dst); c++ {
				copy(dst[di:di+bpp], src[si:si+bpp])
				di += bpp
			}
			si += bpp
		} else {
			n := count * bpp
			if si+n > len(src) {
				return fmt.Errorf("tga: RLE raw run truncated")
			}
			for c := 0; c < n && di < len(dst); c++ {
				dst[di] = src[si+c]
				di++
			}
			si += n
		}
	}
	return nil
}
