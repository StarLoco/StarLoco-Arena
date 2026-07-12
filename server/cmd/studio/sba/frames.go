package sba

import "sort"

// This file flattens a decoded Symbol's timeline into a sequence of drawable
// frames. It simulates the client's recursive display tree (see
// com.ankamagames.framework.graphics.animation.instances.MovieClip.
// processCurrentFrame + Mesh2D/Matrix2D): every movie-clip nests its children
// under its own transform, children are drawn in ascending depth order, and
// nested movie clips advance their own timelines. Each leaf bitmap is emitted
// as a FrameOp carrying the fully-composed affine transform (hot point baked
// in) so the frontend can draw it with a single setTransform + drawImage(0,0).

// FrameOp is one resolved bitmap draw within a frame.
type FrameOp struct {
	BitmapID int            `json:"bitmapId"`
	Depth    int            `json:"depth"`
	Matrix   Mat2D          `json:"matrix"` // composed, hot-point-baked affine
	Color    ColorTransform `json:"color"`
}

// Frame is a composited timeline frame: its bitmap draws in draw order.
type Frame struct {
	Duration int       `json:"duration"`
	Ops      []FrameOp `json:"ops"`
}

// Playback bundles the frames with the bitmap pixel set they reference and an
// overall bounding box (in parent space) for initial fit/centering.
type Playback struct {
	SymbolID  int
	Kind      SymbolKind
	LoopCount int
	Frames    []Frame
	Bitmaps   map[int]*BitmapData
	MinX      float64
	MinY      float64
	MaxX      float64
	MaxY      float64
}

type placement struct {
	charID int
	matrix Matrix
	color  ColorTransform
	hasMtx bool
	hasCol bool
}

// Build flattens the symbol with the given id into a Playback. Unknown ids fall
// back to the movie's default symbol.
func (m *Movie) Build(symbolID int) *Playback {
	sym := m.Symbols[symbolID]
	if sym == nil {
		sym = m.Symbols[m.RootID]
	}
	pb := &Playback{Bitmaps: map[int]*BitmapData{}}
	if sym == nil {
		return pb
	}
	pb.SymbolID = sym.ID
	pb.Kind = sym.Kind
	pb.LoopCount = sym.LoopCount

	b := &builder{movie: m, pb: pb, synthNext: synthBase}
	switch sym.Kind {
	case KindBitmap:
		b.buildStaticBitmap(sym)
	case KindBitmapSequence:
		b.buildBitmapSequence(sym)
	case KindMovieClip:
		b.buildMovieClip(sym)
	}
	b.computeBounds()
	return pb
}

const synthBase = 1_000_000 // synthetic bitmap ids (embedded/sequence frames)

type builder struct {
	movie     *Movie
	pb        *Playback
	synthNext int
}

// leafOp composes a bitmap leaf's transform exactly as the client does (Mesh2D
// setTransformation + Bitmap.process), reproducing the client's *Y-up* world
// math verbatim. The composited modelview the client builds (Mesh2D.java:406-409)
// is, applied to a mesh-local vertex (offset innermost, transformation outermost):
//
//	world = parent * placement * scale(invertScaling) * hotCenter * offset
//
// The exact hotCenter/offset values (traced through Bitmap.process, Mesh2D and
// HotSpot3D.setup which negates ONLY X):
//
//   - descriptor: getHotX() = -rawX*0.1, getHotY() = rawY*0.1  (BitmapDescriptor)
//   - Bitmap.process sets m_hotCenter = (getHotX(), -getHotY()) = (-rawX*0.1, -rawY*0.1)
//   - HotSpot3D.setup applies glTranslatef(-m_x, +m_y) = translate(+rawX*0.1, -rawY*0.1)
//   - offset = (0, -height)  (Mesh2D.setHeight transformation branch)
//
// So the hot-point pivot translate is (+rawHotX*0.1, -rawHotY*0.1) — note the X
// sign is POSITIVE (HotSpot3D negates the already-negated descriptor X). Because
// rotation lives in the OUTER placement matrix, a wrong-signed hot pivot makes
// large-hot-point parts (e.g. the head, hot=(-179,-345)) swing away under
// rotation — which was the detached-limb bug.
//
// The Y-up->Y-down bridge: the client's quad grows UP (local y in [0,h]) with
// the texture top at y=h; a canvas image draws top-down from (0,0). We prepend a
// local image flip flipH = translate(0,h)*scale(1,-1) so the canvas image maps
// onto the client's quad, and apply ONE Y flip at the view level (frontend).
func leafOp(parent Mat2D, place Matrix, inv float64, hotX, hotY, height float64) Mat2D {
	m := parent.mul(Mat2D(place.Affine())) // placement (Y-up), rotation OUTER
	m = m.scale(inv, inv)                  // invertScalingValue (uniform)
	// hotCenter pivot: (+rawHotX*0.1, -rawHotY*0.1) then offset (0,-h).
	m = m.translate(hotX*twipScale, -hotY*twipScale)
	m = m.translate(0, -height)
	// Local image flip: map the canvas image (top at y=0, growing down) onto the
	// client's up-growing quad (top at y=h).
	m = m.translate(0, height)
	m = m.scale(1, -1)
	return m
}

func (b *builder) buildStaticBitmap(sym *Symbol) {
	if sym.Bitmap == nil {
		return
	}
	b.pb.Bitmaps[sym.ID] = sym.Bitmap
	inv := normInv(sym.InvertScaling)
	mtx := leafOp(identityMat2D(), identityMatrix(), inv,
		float64(sym.HotX), float64(sym.HotY), float64(sym.Bitmap.Height))
	b.pb.Frames = []Frame{{
		Duration: 1,
		Ops:      []FrameOp{{BitmapID: sym.ID, Matrix: mtx, Color: identityColor()}},
	}}
}

func (b *builder) buildBitmapSequence(sym *Symbol) {
	inv := normInv(sym.InvertScaling)
	for _, f := range sym.Frames {
		if f.Bitmap == nil {
			b.pb.Frames = append(b.pb.Frames, Frame{Duration: normDuration(f.Duration)})
			continue
		}
		id := b.synthNext
		b.synthNext++
		b.pb.Bitmaps[id] = f.Bitmap
		mtx := leafOp(identityMat2D(), identityMatrix(), inv,
			float64(f.HotX), float64(f.HotY), float64(f.Bitmap.Height))
		b.pb.Frames = append(b.pb.Frames, Frame{
			Duration: normDuration(f.Duration),
			Ops:      []FrameOp{{BitmapID: id, Matrix: mtx, Color: identityColor()}},
		})
	}
}

// buildMovieClip simulates a movie-clip timeline: for each of its ShowFrame
// instants it snapshots the sorted display list and recurses into child symbols
// so nested clips/sequences contribute their own (transform-composed) leaf
// draws. Crucially the frame index is threaded down the tree so nested clips
// advance their OWN timelines in lock-step with the parent (looping when
// shorter) — this mirrors the client passing the same deltaTime to every child
// (SequenceObject.setCurrentTime maps time->frame per clip, looping on its own
// totalTime).
func (b *builder) buildMovieClip(sym *Symbol) {
	n := clipFrameCount(sym)
	if n == 0 {
		// A static clip (placements but no ShowFrame) still yields one frame.
		display, _ := clipDisplayAt(sym, 0)
		if len(display) > 0 {
			b.pb.Frames = append(b.pb.Frames, b.snapshot(display, 1, 0))
		}
		return
	}
	for f := 0; f < n; f++ {
		display, dur := clipDisplayAt(sym, f)
		b.pb.Frames = append(b.pb.Frames, b.snapshot(display, dur, f))
	}
}

// snapshot renders one movie-clip frame at parent-frame index frame: for each
// occupied depth (ascending) it recurses into the placed child with the child's
// placement transform/color as the parent context.
func (b *builder) snapshot(display map[int]placement, duration, frame int) Frame {
	depths := make([]int, 0, len(display))
	for d := range display {
		depths = append(depths, d)
	}
	sort.Ints(depths)
	f := Frame{Duration: normDuration(duration)}
	for _, d := range depths {
		p := display[d]
		if p.charID == 0 {
			continue
		}
		parent := Mat2D(p.matrix.Affine())
		b.emit(p.charID, parent, p.color, d, frame, &f.Ops, map[int]bool{})
	}
	return f
}

// emit recursively flattens the symbol referenced by charID into leaf draw ops,
// carrying the accumulated parent transform, color and the current timeline
// frame down the tree. This is the port of MovieClip.processCurrentFrame's
// child.process(mesh, deltaTime, ...) recursion.
func (b *builder) emit(charID int, parent Mat2D, parentColor ColorTransform, depth, frame int, ops *[]FrameOp, seen map[int]bool) {
	if charID == 0 || seen[charID] {
		return
	}
	sym := b.movie.Symbols[charID]
	if sym == nil {
		return
	}
	switch sym.Kind {
	case KindBitmap:
		if sym.Bitmap == nil {
			return
		}
		b.pb.Bitmaps[sym.ID] = sym.Bitmap
		mtx := leafOp(parent, identityMatrix(), normInv(sym.InvertScaling),
			float64(sym.HotX), float64(sym.HotY), float64(sym.Bitmap.Height))
		*ops = append(*ops, FrameOp{BitmapID: sym.ID, Depth: depth, Matrix: mtx, Color: parentColor})

	case KindBitmapSequence:
		// A nested bitmap sequence advances its own timeline with the parent
		// frame, looping (modulo) when it is shorter than the parent.
		if len(sym.Frames) == 0 {
			return
		}
		fr := sym.Frames[frame%len(sym.Frames)]
		if fr.Bitmap == nil {
			return
		}
		id := b.synthNext
		b.synthNext++
		b.pb.Bitmaps[id] = fr.Bitmap
		mtx := leafOp(parent, identityMatrix(), normInv(sym.InvertScaling),
			float64(fr.HotX), float64(fr.HotY), float64(fr.Bitmap.Height))
		*ops = append(*ops, FrameOp{BitmapID: id, Depth: depth, Matrix: mtx, Color: parentColor})

	case KindMovieClip:
		// Recurse into the nested clip's display list AT ITS OWN CURRENT FRAME
		// (frame mod its length), composing each child placement under the
		// parent transform/color. Guard against reference cycles via seen.
		seen[charID] = true
		defer delete(seen, charID)
		n := clipFrameCount(sym)
		childFrame := 0
		if n > 0 {
			childFrame = frame % n
		}
		display, _ := clipDisplayAt(sym, childFrame)
		depths := make([]int, 0, len(display))
		for d := range display {
			depths = append(depths, d)
		}
		sort.Ints(depths)
		for _, d := range depths {
			p := display[d]
			if p.charID == 0 {
				continue
			}
			childParent := parent.mul(Mat2D(p.matrix.Affine()))
			childColor := parentColor.combine(p.color)
			b.emit(p.charID, childParent, childColor, d, childFrame, ops, seen)
		}
	}
}

// clipFrameCount returns the number of ShowFrame instants in a movie clip.
func clipFrameCount(sym *Symbol) int {
	n := 0
	for _, op := range sym.Ops {
		if op.Op == "showframe" {
			n++
		}
	}
	return n
}

// clipDisplayAt replays a movie clip's display-list ops up to (and including)
// the target-th ShowFrame, returning the sticky display list at that instant
// plus that frame's duration. PlaceObject state is cumulative across frames
// (FrameDataDescriptor sticky semantics), so playing forward to `target`
// reproduces the exact on-screen set for that frame.
func clipDisplayAt(sym *Symbol, target int) (map[int]placement, int) {
	display := map[int]placement{}
	frame := 0
	for _, op := range sym.Ops {
		switch op.Op {
		case "place":
			p := display[op.Depth]
			if op.CharID != 0 {
				p.charID = op.CharID
			}
			if op.Matrix != nil {
				p.matrix = *op.Matrix
				p.hasMtx = true
			} else if !p.hasMtx {
				p.matrix = identityMatrix()
			}
			if op.Color != nil {
				p.color = *op.Color
				p.hasCol = true
			} else if !p.hasCol {
				p.color = identityColor()
			}
			display[op.Depth] = p
		case "remove":
			delete(display, op.Depth)
		case "showframe":
			if frame == target {
				return display, op.Duration
			}
			frame++
		}
	}
	return display, 1
}

func normInv(v float64) float64 {
	if v == 0 || v != v {
		return 1
	}
	return v
}

// computeBounds accumulates the transformed bitmap rectangles across all frames
// so the viewer can auto-fit. Each op's matrix already maps the (0,0)-(w,h)
// local quad into parent space.
func (b *builder) computeBounds() {
	pb := b.pb
	first := true
	upd := func(x, y float64) {
		if first {
			pb.MinX, pb.MaxX, pb.MinY, pb.MaxY = x, x, y, y
			first = false
			return
		}
		if x < pb.MinX {
			pb.MinX = x
		}
		if x > pb.MaxX {
			pb.MaxX = x
		}
		if y < pb.MinY {
			pb.MinY = y
		}
		if y > pb.MaxY {
			pb.MaxY = y
		}
	}
	for _, f := range pb.Frames {
		for _, op := range f.Ops {
			bmp := pb.Bitmaps[op.BitmapID]
			if bmp == nil {
				continue
			}
			w, h := float64(bmp.Width), float64(bmp.Height)
			for _, c := range [][2]float64{{0, 0}, {w, 0}, {0, h}, {w, h}} {
				X, Y := op.Matrix.apply(c[0], c[1])
				upd(X, Y)
			}
		}
	}
	if first { // no drawable content
		pb.MinX, pb.MinY, pb.MaxX, pb.MaxY = 0, 0, 1, 1
	}
}

func normDuration(d int) int {
	if d <= 0 || d == 0xFFFF { // 0 / INFINIT_DURATION -> a sane single tick
		return 1
	}
	return d
}
