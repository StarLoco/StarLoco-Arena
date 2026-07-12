package sba

import (
	"archive/zip"
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"math"
	"os"
	"path/filepath"
	"testing"
)

// TestRenderDiag_11 renders selected frames of animations/11.sba to PNG files
// under sba/_diag/ using the SAME math the frontend uses (op matrix + a single
// view-level Y flip), so we can visually inspect what the compositor produces.
// Set DIAG=1 to run:
//
//	$env:DIAG=1; go test -run TestRenderDiag_11 -v ./cmd/studio/sba/...
func TestRenderDiag_11(t *testing.T) {
	if os.Getenv("DIAG") == "" {
		t.Skip("set DIAG=1 to render diagnostic frames")
	}
	jar := findClientJar(t, "animations.jar")
	r, err := zip.OpenReader(jar)
	if err != nil {
		t.Fatalf("open: %v", err)
	}
	defer r.Close()
	var f *zip.File
	for _, e := range r.File {
		if e.Name == "animations/11.sba" {
			f = e
		}
	}
	if f == nil {
		t.Skip("11.sba not found")
	}
	m, err := ParseFull(readZip(t, f))
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	pb := m.Build(m.RootID)

	outDir := filepath.Join("_diag")
	_ = os.MkdirAll(outDir, 0o755)

	const W, H = 480, 480
	// Fit transform (mirror of the frontend fit()).
	minX, minY, maxX, maxY := pb.MinX, pb.MinY, pb.MaxX, pb.MaxY
	cw := math.Max(1, maxX-minX)
	ch := math.Max(1, maxY-minY)
	scale := math.Min(W/cw, H/ch) * 0.9
	cx := (minX + maxX) / 2
	cy := (minY + maxY) / 2
	// View V = [scale, 0, 0, -scale, vx, vy] (single Y flip).
	vx := W/2 - scale*cx
	vy := H/2 + scale*cy

	frames := []int{0, 12, 24, 38, 44, 45, 48}
	for _, fi := range frames {
		if fi >= len(pb.Frames) {
			continue
		}
		canvas := image.NewRGBA(image.Rect(0, 0, W, H))
		// dark gray bg
		draw.Draw(canvas, canvas.Bounds(), &image.Uniform{color.RGBA{30, 32, 38, 255}}, image.Point{}, draw.Src)

		for _, op := range pb.Frames[fi].Ops {
			bmp := pb.Bitmaps[op.BitmapID]
			if bmp == nil || bmp.Width <= 0 || bmp.Height <= 0 {
				continue
			}
			src := bmp.NRGBA()
			// Composite W = V * op:
			a := scale * op.Matrix[0]
			b := -scale * op.Matrix[1]
			c := scale * op.Matrix[2]
			d := -scale * op.Matrix[3]
			e := scale*op.Matrix[4] + vx
			fv := -scale*op.Matrix[5] + vy
			blitAffine(canvas, src, a, b, c, d, e, fv)
		}
		// Log the world position (matrix e,f = where local origin maps) of each
		// op so we can spot the detached parts (far-left / far-up).
		for _, op := range pb.Frames[fi].Ops {
			bmp := pb.Bitmaps[op.BitmapID]
			dims := "nil"
			if bmp != nil {
				dims = itoa(bmp.Width) + "x" + itoa(bmp.Height)
			}
			t.Logf("  f%d depth=%d bmp=%d origin=(%.1f,%.1f) %s", fi, op.Depth, op.BitmapID, op.Matrix[4], op.Matrix[5], dims)
		}
		path := filepath.Join(outDir, "frame_"+itoa(fi)+".png")
		out, err := os.Create(path)
		if err != nil {
			t.Fatal(err)
		}
		if err := png.Encode(out, canvas); err != nil {
			t.Fatal(err)
		}
		out.Close()
		t.Logf("wrote %s (frame %d, %d ops)", path, fi, len(pb.Frames[fi].Ops))
	}
	t.Logf("bounds = [%.1f %.1f %.1f %.1f]", minX, minY, maxX, maxY)
}

// blitAffine draws src into dst under the affine [a,b,c,d,e,f] (dst = M*srcPt)
// using inverse-map nearest sampling, respecting src alpha.
func blitAffine(dst *image.RGBA, src *image.NRGBA, a, b, c, d, e, f float64) {
	sw, sh := src.Bounds().Dx(), src.Bounds().Dy()
	// dst-space bounding box of the transformed src rect.
	corners := [][2]float64{{0, 0}, {float64(sw), 0}, {0, float64(sh)}, {float64(sw), float64(sh)}}
	minX, minY := math.Inf(1), math.Inf(1)
	maxX, maxY := math.Inf(-1), math.Inf(-1)
	for _, p := range corners {
		X := a*p[0] + c*p[1] + e
		Y := b*p[0] + d*p[1] + f
		minX, maxX = math.Min(minX, X), math.Max(maxX, X)
		minY, maxY = math.Min(minY, Y), math.Max(maxY, Y)
	}
	det := a*d - b*c
	if det == 0 {
		return
	}
	// inverse affine
	ia := d / det
	ib := -b / det
	ic := -c / det
	id := a / det
	ie := -(ia*e + ic*f)
	iff := -(ib*e + id*f)
	x0, x1 := int(math.Floor(minX)), int(math.Ceil(maxX))
	y0, y1 := int(math.Floor(minY)), int(math.Ceil(maxY))
	for y := y0; y < y1; y++ {
		if y < 0 || y >= dst.Bounds().Dy() {
			continue
		}
		for x := x0; x < x1; x++ {
			if x < 0 || x >= dst.Bounds().Dx() {
				continue
			}
			sx := ia*float64(x) + ic*float64(y) + ie
			sy := ib*float64(x) + id*float64(y) + iff
			ix, iy := int(sx), int(sy)
			if ix < 0 || ix >= sw || iy < 0 || iy >= sh {
				continue
			}
			sc := src.NRGBAAt(ix, iy)
			if sc.A == 0 {
				continue
			}
			dst.Set(x, y, sc)
		}
	}
}

func itoa(n int) string {
	if n == 0 {
		return "0"
	}
	neg := n < 0
	if neg {
		n = -n
	}
	var buf [12]byte
	i := len(buf)
	for n > 0 {
		i--
		buf[i] = byte('0' + n%10)
		n /= 10
	}
	if neg {
		i--
		buf[i] = '-'
	}
	return string(buf[i:])
}
