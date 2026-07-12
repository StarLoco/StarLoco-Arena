package sba

import (
	"bytes"
	"compress/zlib"
	"fmt"
	"image"
	_ "image/jpeg" // v1 bitmaps may be JPEG
	_ "image/png"  // v1 bitmaps may be PNG
	"io"
)

// decodeBitmapV23 decodes a v2/v3 bitmap blob: an outer zlib stream wrapping an
// AlphaBitmapData record of raw RGBA pixels.
//
// v3 record (AlphaBitmapData.read):
//
//	UI8  version (== 1)
//	1b   alphaPremultiplied
//	UI16 width
//	UI16 height
//	UI32 length
//	length bytes  raw RGBA (R,G,B,A per pixel, row-major top-to-bottom)
//
// v2 record (AlphaBitmapData.OldVersionReader.read2) omits the version byte and
// premultiplied bit -- just width/height/length/data.
func decodeBitmapV23(blob []byte) (*BitmapData, error) {
	inflated, err := zlibInflate(blob)
	if err != nil {
		return nil, fmt.Errorf("sba: bitmap inflate: %w", err)
	}
	r := newBitReader(inflated)

	premult := false
	// v3 records begin with a version byte (1) + a premultiplied bit. v2
	// records begin directly with width. We detect v3 by the leading version
	// byte == 1 followed by a plausible width; otherwise fall back to v2.
	if len(inflated) >= 9 && inflated[0] == 1 {
		_ = r.readUI8() // version == 1
		premult = r.readBooleanBit()
	}
	width := int(r.readUI16())
	height := int(r.readUI16())
	length := int(r.readUI32())
	if r.Err() != nil {
		return nil, r.Err()
	}
	if width <= 0 || height <= 0 {
		return &BitmapData{Width: width, Height: height, Premultiplied: premult}, nil
	}
	want := width * height * 4
	if length != want {
		return nil, fmt.Errorf("sba: bitmap %dx%d expects %d RGBA bytes, header says %d", width, height, want, length)
	}
	raw := r.readBytes(length)
	if r.Err() != nil {
		return nil, r.Err()
	}
	pix := make([]byte, length)
	copy(pix, raw)
	bd := &BitmapData{Width: width, Height: height, Premultiplied: premult, RGBA: pix}
	if premult {
		bd.unpremultiply()
	}
	return bd, nil
}

// decodeBitmapV1 decodes a legacy v1 bitmap: a standard encoded image (PNG/JPEG)
// stored inline (not zlib-wrapped).
func decodeBitmapV1(blob []byte) (*BitmapData, error) {
	img, _, err := image.Decode(bytes.NewReader(blob))
	if err != nil {
		return nil, fmt.Errorf("sba: v1 bitmap decode: %w", err)
	}
	b := img.Bounds()
	w, h := b.Dx(), b.Dy()
	pix := make([]byte, w*h*4)
	i := 0
	for y := b.Min.Y; y < b.Max.Y; y++ {
		for x := b.Min.X; x < b.Max.X; x++ {
			r, g, bl, a := img.At(x, y).RGBA() // 16-bit, straight? (image models vary)
			pix[i+0] = byte(r >> 8)
			pix[i+1] = byte(g >> 8)
			pix[i+2] = byte(bl >> 8)
			pix[i+3] = byte(a >> 8)
			i += 4
		}
	}
	return &BitmapData{Width: w, Height: h, RGBA: pix}, nil
}

// unpremultiply converts premultiplied RGBA to straight alpha, mirroring
// AlphaBitmapData.demultiplyAlpha, so the pixels display correctly as a normal
// (straight-alpha) PNG on the canvas.
func (b *BitmapData) unpremultiply() {
	d := b.RGBA
	for i := 0; i+3 < len(d); i += 4 {
		a := d[i+3]
		if a == 0 {
			d[i], d[i+1], d[i+2] = 0, 0, 0
			continue
		}
		d[i+0] = clamp255(int(d[i+0]) * 255 / int(a))
		d[i+1] = clamp255(int(d[i+1]) * 255 / int(a))
		d[i+2] = clamp255(int(d[i+2]) * 255 / int(a))
	}
	b.Premultiplied = false
}

func clamp255(v int) byte {
	if v < 0 {
		return 0
	}
	if v > 255 {
		return 255
	}
	return byte(v)
}

// NRGBA converts the decoded straight-alpha RGBA pixels to an *image.NRGBA.
func (b *BitmapData) NRGBA() *image.NRGBA {
	if b == nil || b.Width <= 0 || b.Height <= 0 {
		return image.NewNRGBA(image.Rect(0, 0, 0, 0))
	}
	img := image.NewNRGBA(image.Rect(0, 0, b.Width, b.Height))
	// RGBA (R,G,B,A) maps straight to NRGBA's Pix (R,G,B,A).
	copy(img.Pix, b.RGBA)
	return img
}

func zlibInflate(b []byte) ([]byte, error) {
	zr, err := zlib.NewReader(bytes.NewReader(b))
	if err != nil {
		return nil, err
	}
	defer zr.Close()
	return io.ReadAll(zr)
}
