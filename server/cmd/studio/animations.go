package main

import (
	"bytes"
	"encoding/base64"
	"fmt"
	"image/png"
	"path"
	"sort"
	"strings"

	"github.com/dofusarena/go-server/cmd/studio/sba"
)

// This file exposes the Phase 6 .sba (Sprite Byte Animation) inspector to the
// frontend. The format was reverse-engineered from the real client classes
// (see cmd/studio/sba and tools/sba-re/). The current decoder recovers the
// full document/tag structure; a rendered timeline player is a documented
// follow-on (tools/README.md Phase 6).

// AnimationInfo is one .sba entry in the animations/equipments jars.
type AnimationInfo struct {
	Jar   string `json:"jar"`
	Path  string `json:"path"`
	Name  string `json:"name"`
	Bytes int64  `json:"bytes"`
}

// AnimationDoc is the decoded structure of one .sba file: its header plus a
// tag summary the inspector renders.
type AnimationDoc struct {
	Jar        string         `json:"jar"`
	Path       string         `json:"path"`
	Signature  string         `json:"signature"`
	Compressed bool           `json:"compressed"`
	Version    int            `json:"version"`
	FileLength int64          `json:"fileLength"`
	TagCount   int            `json:"tagCount"`
	Tags       []AnimationTag `json:"tags"`
	Counts     map[string]int `json:"counts"` // tag name -> occurrence count
}

// AnimationTag mirrors sba.Tag for JSON.
type AnimationTag struct {
	Code   int    `json:"code"`
	Name   string `json:"name"`
	Length int    `json:"length"`
	ID     int    `json:"id"`
	HasID  bool   `json:"hasId"`
}

// animationJars are the content jars that hold .sba files.
var animationJars = []string{"animations.jar", "equipments.jar"}

// ListAnimations returns every .sba entry across the animation jars, sorted
// by jar then path.
func (a *App) ListAnimations() ([]AnimationInfo, error) {
	var out []AnimationInfo
	for _, jarName := range animationJars {
		r, err := a.openNamedJar(jarName)
		if err != nil {
			continue // jar may be absent in some builds
		}
		for _, f := range r.File {
			if f.FileInfo().IsDir() || !strings.HasSuffix(strings.ToLower(f.Name), ".sba") {
				continue
			}
			out = append(out, AnimationInfo{
				Jar:   jarName,
				Path:  f.Name,
				Name:  path.Base(f.Name),
				Bytes: int64(f.UncompressedSize64),
			})
		}
	}
	if len(out) == 0 {
		return nil, fmt.Errorf("no .sba animations found (client dir set?)")
	}
	sort.Slice(out, func(i, j int) bool {
		if out[i].Jar != out[j].Jar {
			return out[i].Jar < out[j].Jar
		}
		return out[i].Path < out[j].Path
	})
	return out, nil
}

// GetAnimation decodes one .sba file's structure.
func (a *App) GetAnimation(jarName, entryPath string) (AnimationDoc, error) {
	if !isAnimationJar(jarName) {
		return AnimationDoc{}, fmt.Errorf("%q is not an animation jar", jarName)
	}
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return AnimationDoc{}, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return AnimationDoc{}, fmt.Errorf("animation %q not found in %s", entryPath, jarName)
	}
	raw, err := readZipEntry(f, 64<<20)
	if err != nil {
		return AnimationDoc{}, err
	}
	doc, err := sba.Parse(raw)
	if err != nil {
		return AnimationDoc{}, err
	}

	tags := make([]AnimationTag, 0, len(doc.Tags))
	counts := map[string]int{}
	for _, t := range doc.Tags {
		tags = append(tags, AnimationTag{
			Code:   t.Code,
			Name:   t.Name,
			Length: t.Length,
			ID:     t.ID,
			HasID:  t.HasID,
		})
		counts[t.Name]++
	}

	return AnimationDoc{
		Jar:        jarName,
		Path:       entryPath,
		Signature:  doc.Header.Signature,
		Compressed: doc.Header.Compressed,
		Version:    int(doc.Header.Version),
		FileLength: int64(doc.Header.FileLength),
		TagCount:   len(tags),
		Tags:       tags,
		Counts:     counts,
	}, nil
}

func isAnimationJar(name string) bool {
	for _, n := range animationJars {
		if n == name {
			return true
		}
	}
	return false
}

// ---- Phase 6 (player): fully-decoded, renderable playback ----

// animDefaultSymbol is the sentinel a caller passes as symbolID to get the
// movie's own best default symbol (the frontend doesn't know valid ids yet on
// its first request). It can't collide with a real id (uint16) or the synthetic
// root id (-1).
const animDefaultSymbol = -999999

// PlayerBitmap is one decoded bitmap ready for the canvas.
type PlayerBitmap struct {
	ID      int    `json:"id"`
	Width   int    `json:"width"`
	Height  int    `json:"height"`
	DataURL string `json:"dataUrl"` // PNG base64 data URL
}

// PlayerOp is one bitmap draw within a frame. Matrix is the fully-composed
// canvas affine (hot point already baked in): the frontend draws the bitmap
// with setTransform(matrix) then drawImage(img, 0, 0).
type PlayerOp struct {
	BitmapID int        `json:"bitmapId"`
	Depth    int        `json:"depth"`
	Matrix   [6]float64 `json:"matrix"`   // a,b,c,d,e,f (canvas setTransform order)
	ColorMul [4]float64 `json:"colorMul"` // r,g,b,a multipliers (1.0 == identity)
	ColorAdd [4]int     `json:"colorAdd"` // r,g,b,a additive terms (0..255 scale)
}

// PlayerFrame is a composited timeline frame.
type PlayerFrame struct {
	Duration int        `json:"duration"`
	Ops      []PlayerOp `json:"ops"`
}

// PlayerSymbol is a selectable definition (or the synthetic root timeline).
type PlayerSymbol struct {
	ID         int    `json:"id"`
	Linkage    string `json:"linkage"`
	Kind       string `json:"kind"`
	FrameCount int    `json:"frameCount"`
}

// AnimationPlayback is the fully-decoded, renderable animation.
type AnimationPlayback struct {
	Jar        string         `json:"jar"`
	Path       string         `json:"path"`
	Signature  string         `json:"signature"`
	Version    int            `json:"version"`
	Compressed bool           `json:"compressed"`
	Symbols    []PlayerSymbol `json:"symbols"`
	Selected   int            `json:"selected"`
	LoopCount  int            `json:"loopCount"`
	Bitmaps    []PlayerBitmap `json:"bitmaps"`
	Frames     []PlayerFrame  `json:"frames"`
	Bounds     [4]float64     `json:"bounds"` // minX, minY, maxX, maxY
}

// GetAnimationPlayback fully decodes an .sba and flattens the given symbol into
// renderable frames. Pass symbolID == animDefaultSymbol (-999999) for the
// movie's best default symbol.
func (a *App) GetAnimationPlayback(jarName, entryPath string, symbolID int) (AnimationPlayback, error) {
	m, err := a.loadMovie(jarName, entryPath)
	if err != nil {
		return AnimationPlayback{}, err
	}
	if symbolID == animDefaultSymbol {
		symbolID = m.RootID
	}
	pb := m.Build(symbolID)

	out := AnimationPlayback{
		Jar:        jarName,
		Path:       entryPath,
		Signature:  m.Header.Signature,
		Version:    int(m.Header.Version),
		Compressed: m.Header.Compressed,
		Selected:   pb.SymbolID,
		LoopCount:  pb.LoopCount,
		Symbols:    symbolList(m),
		Bounds:     [4]float64{pb.MinX, pb.MinY, pb.MaxX, pb.MaxY},
	}
	if err := fillPlayback(&out, pb, 0); err != nil {
		return AnimationPlayback{}, err
	}
	return out, nil
}

// LayerRef selects one .sba layer (a base or an equipment) and which symbol of
// it to play, for the fighter dress-up compositor.
type LayerRef struct {
	Jar      string `json:"jar"`
	Path     string `json:"path"`
	SymbolID int    `json:"symbolId"` // animDefaultSymbol for the layer's default
}

// ComposeFighter composites a base animation with equipment .sba layers drawn on
// top, frame-aligned, into a single playback. Layers with fewer frames hold
// their last frame. Bitmap ids are namespaced per layer so they never collide.
func (a *App) ComposeFighter(base LayerRef, equipment []LayerRef) (AnimationPlayback, error) {
	layers := append([]LayerRef{base}, equipment...)
	playbacks := make([]*sba.Playback, 0, len(layers))
	var baseMovie *sba.Movie
	for i, ly := range layers {
		m, err := a.loadMovie(ly.Jar, ly.Path)
		if err != nil {
			return AnimationPlayback{}, fmt.Errorf("layer %d (%s): %w", i, ly.Path, err)
		}
		sym := ly.SymbolID
		if sym == animDefaultSymbol {
			sym = m.RootID
		}
		if i == 0 {
			baseMovie = m
		}
		playbacks = append(playbacks, m.Build(sym))
	}

	out := AnimationPlayback{
		Jar:        base.Jar,
		Path:       base.Path,
		Signature:  baseMovie.Header.Signature,
		Version:    int(baseMovie.Header.Version),
		Compressed: baseMovie.Header.Compressed,
		Selected:   playbacks[0].SymbolID,
		LoopCount:  playbacks[0].LoopCount,
		Symbols:    symbolList(baseMovie),
	}

	// Bitmaps + bounds across all layers.
	var minX, minY, maxX, maxY float64
	haveBounds := false
	for i, pb := range playbacks {
		if err := fillPlayback(&out, pb, layerBitmapOffset(i)); err != nil {
			return AnimationPlayback{}, err
		}
		if len(pb.Bitmaps) == 0 {
			continue
		}
		if !haveBounds {
			minX, minY, maxX, maxY = pb.MinX, pb.MinY, pb.MaxX, pb.MaxY
			haveBounds = true
			continue
		}
		minX, minY = min64(minX, pb.MinX), min64(minY, pb.MinY)
		maxX, maxY = max64(maxX, pb.MaxX), max64(maxY, pb.MaxY)
	}
	out.Bounds = [4]float64{minX, minY, maxX, maxY}

	// Frame-align: composite frame f = base frame f (held) with each layer's
	// frame f (held) drawn on top.
	total := 0
	for _, pb := range playbacks {
		if len(pb.Frames) > total {
			total = len(pb.Frames)
		}
	}
	out.Frames = make([]PlayerFrame, total)
	for f := 0; f < total; f++ {
		dur := 1
		var ops []PlayerOp
		for i, pb := range playbacks {
			if len(pb.Frames) == 0 {
				continue
			}
			idx := f
			if idx >= len(pb.Frames) {
				idx = len(pb.Frames) - 1 // hold last frame
			}
			fr := pb.Frames[idx]
			if i == 0 && fr.Duration > 0 {
				dur = fr.Duration
			}
			for _, op := range fr.Ops {
				ops = append(ops, toPlayerOp(op, layerBitmapOffset(i)))
			}
		}
		out.Frames[f] = PlayerFrame{Duration: dur, Ops: ops}
	}
	return out, nil
}

func layerBitmapOffset(layer int) int { return (layer + 1) * 10_000_000 }

// loadMovie opens a jar entry and fully decodes it into an sba.Movie.
func (a *App) loadMovie(jarName, entryPath string) (*sba.Movie, error) {
	if !isAnimationJar(jarName) {
		return nil, fmt.Errorf("%q is not an animation jar", jarName)
	}
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return nil, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return nil, fmt.Errorf("animation %q not found in %s", entryPath, jarName)
	}
	raw, err := readZipEntry(f, 64<<20)
	if err != nil {
		return nil, err
	}
	return sba.ParseFull(raw)
}

func symbolList(m *sba.Movie) []PlayerSymbol {
	out := make([]PlayerSymbol, 0, len(m.Order))
	for _, id := range m.Order {
		s := m.Symbols[id]
		out = append(out, PlayerSymbol{
			ID:         s.ID,
			Linkage:    s.Linkage,
			Kind:       string(s.Kind),
			FrameCount: s.FrameCount,
		})
	}
	return out
}

// fillPlayback appends a playback's bitmaps (offset by bitmapOffset) and frames
// (only appended when bitmapOffset == 0, i.e. the single-animation case; the
// compositor builds frames itself) to out.
func fillPlayback(out *AnimationPlayback, pb *sba.Playback, bitmapOffset int) error {
	for id, bmp := range pb.Bitmaps {
		url, err := bitmapDataURL(bmp)
		if err != nil {
			return err
		}
		out.Bitmaps = append(out.Bitmaps, PlayerBitmap{
			ID:      id + bitmapOffset,
			Width:   bmp.Width,
			Height:  bmp.Height,
			DataURL: url,
		})
	}
	if bitmapOffset == 0 {
		out.Frames = make([]PlayerFrame, 0, len(pb.Frames))
		for _, fr := range pb.Frames {
			pf := PlayerFrame{Duration: fr.Duration}
			for _, op := range fr.Ops {
				pf.Ops = append(pf.Ops, toPlayerOp(op, 0))
			}
			out.Frames = append(out.Frames, pf)
		}
	}
	return nil
}

func toPlayerOp(op sba.FrameOp, bitmapOffset int) PlayerOp {
	return PlayerOp{
		BitmapID: op.BitmapID + bitmapOffset,
		Depth:    op.Depth,
		Matrix:   [6]float64(op.Matrix),
		ColorMul: [4]float64{
			float64(op.Color.RedMult) / 256.0,
			float64(op.Color.GreenMult) / 256.0,
			float64(op.Color.BlueMult) / 256.0,
			float64(op.Color.AlphaMult) / 256.0,
		},
		ColorAdd: [4]int{op.Color.RedAdd, op.Color.GreenAdd, op.Color.BlueAdd, op.Color.AlphaAdd},
	}
}

// bitmapDataURL PNG-encodes a decoded bitmap as a base64 data URL. Empty
// (0-size or pixel-less) bitmaps yield an empty string, which the frontend
// simply skips when drawing.
func bitmapDataURL(b *sba.BitmapData) (string, error) {
	if b == nil || b.Width <= 0 || b.Height <= 0 || len(b.RGBA) == 0 {
		return "", nil
	}
	var buf bytes.Buffer
	if err := png.Encode(&buf, b.NRGBA()); err != nil {
		return "", fmt.Errorf("bitmap png encode: %w", err)
	}
	return "data:image/png;base64," + base64.StdEncoding.EncodeToString(buf.Bytes()), nil
}

func min64(a, b float64) float64 {
	if a < b {
		return a
	}
	return b
}

func max64(a, b float64) float64 {
	if a > b {
		return a
	}
	return b
}
