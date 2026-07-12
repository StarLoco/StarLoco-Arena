package sba

import (
	"math"
	"testing"
)

// These tests pin the compositor's transform math to the client's convention
// (Matrix2D.set + Mesh2D transformation stack), so a regression in the
// nested-clip flattening is caught without needing the real client jars.

func approxMat(a, b Mat2D, eps float64) bool {
	for i := range a {
		if math.Abs(a[i]-b[i]) > eps {
			return false
		}
	}
	return true
}

// A translate-only placement matrix must map to a canvas translate scaled by
// 0.1 (the twips->units factor), with no sign changes on identity scale.
func TestMatrix_AffineTranslateScale(t *testing.T) {
	m := Matrix{ScaleX: 1, ScaleY: 1, HasTranslate: true, TranslateX: 50, TranslateY: -30}
	got := Mat2D(m.Affine())
	want := Mat2D{1, 0, 0, 1, 5, -3}
	if !approxMat(got, want, 1e-6) {
		t.Errorf("Affine translate = %v, want %v", got, want)
	}
}

// The rotate-skew terms must map exactly like the client's Matrix2D.set:
// buffer[1] = -c, buffer[4] = -b with set(scaleX, rotateSkew1, rotateSkew0,
// scaleY, ...). In canvas [A,B,C,D] terms: A=scaleX, B=-rotateSkew0,
// C=-rotateSkew1, D=scaleY.
func TestMatrix_AffineRotateSkewSign(t *testing.T) {
	m := Matrix{
		ScaleX: 2, ScaleY: 3,
		HasRotate:   true,
		RotateSkew0: 0.5,  // -> canvas B = -0.5
		RotateSkew1: 0.25, // -> canvas C = -0.25
	}
	got := Mat2D(m.Affine())
	want := Mat2D{2, -0.5, -0.25, 3, 0, 0}
	if !approxMat(got, want, 1e-6) {
		t.Errorf("Affine rotate/skew = %v, want %v", got, want)
	}
}

// A static bitmap's leaf transform reproduces the client's Y-up stack plus the
// local image flip (offset (0,-h) then flip translate (0,+h) cancel, leaving
// scale(1,-1)); the hot pivot is (+rawHotX*0.1, -rawHotY*0.1) — X POSITIVE.
func TestLeafOp_StaticBitmapHotPoint(t *testing.T) {
	// hot (10,20) -> pivot (+1,-2); height 8. Stack: T(1,-2)*T(0,-8)*T(0,8)*S(1,-1)
	// = T(1,-2)*S(1,-1) = {1,0,0,-1,1,-2}.
	got := leafOp(identityMat2D(), identityMatrix(), 1, 10, 20, 8)
	want := Mat2D{1, 0, 0, -1, 1, -2}
	if !approxMat(got, want, 1e-6) {
		t.Errorf("leafOp = %v, want %v", got, want)
	}
}

// invertScaling scales the local quad (and the hot/offset translations that
// come after it in the stack). The image flip's Y sign multiplies through.
func TestLeafOp_InvertScaling(t *testing.T) {
	got := leafOp(identityMat2D(), identityMatrix(), 2, 10, 20, 8)
	// scale(2) * T(1,-2) * S(1,-1) (offset/flip cancel): world = 2*(1,-2)
	// translate, Y flipped -> {2,0,0,-2,2,-4}.
	want := Mat2D{2, 0, 0, -2, 2, -4}
	if !approxMat(got, want, 1e-6) {
		t.Errorf("leafOp inv=2 = %v, want %v", got, want)
	}
}

// The crucial regression test: a movie clip that places a NESTED movie clip
// (which in turn places a bitmap) must compose BOTH placement matrices, not
// flatten to a single bitmap with only one matrix. Before the fix, the nested
// clip's own translation was silently dropped.
func TestBuild_NestedMovieClipComposesMatrices(t *testing.T) {
	bmp := &BitmapData{Width: 4, Height: 4, RGBA: make([]byte, 4*4*4)}
	m := &Movie{Symbols: map[int]*Symbol{}, TagCounts: map[string]int{}}

	// Leaf bitmap id=1, no hot point, inv 1.
	m.addSymbol(&Symbol{ID: 1, Kind: KindBitmap, InvertScaling: 1, Bitmap: bmp})

	// Inner clip id=2 places the bitmap translated by (100,0) -> world +10px.
	inner := &Symbol{ID: 2, Kind: KindMovieClip, InvertScaling: 1, FrameCount: 1}
	inner.Ops = []DisplayOp{
		{Op: "place", CharID: 1, Depth: 0, Matrix: &Matrix{ScaleX: 1, ScaleY: 1, HasTranslate: true, TranslateX: 100}},
		{Op: "showframe", Duration: 1},
	}
	m.addSymbol(inner)

	// Root clip id=3 places the inner clip translated by (0,200) -> world +20px.
	root := &Symbol{ID: 3, Kind: KindMovieClip, InvertScaling: 1, FrameCount: 1}
	root.Ops = []DisplayOp{
		{Op: "place", CharID: 2, Depth: 0, Matrix: &Matrix{ScaleX: 1, ScaleY: 1, HasTranslate: true, TranslateY: 200}},
		{Op: "showframe", Duration: 1},
	}
	m.addSymbol(root)
	m.RootID = 3

	pb := m.Build(3)
	if len(pb.Frames) != 1 {
		t.Fatalf("frames = %d, want 1", len(pb.Frames))
	}
	if len(pb.Frames[0].Ops) != 1 {
		t.Fatalf("ops = %d, want 1 leaf draw", len(pb.Frames[0].Ops))
	}
	op := pb.Frames[0].Ops[0]
	// Composed translate = root(0, 20) * inner(10, 0) = (10, 20). hot 0; the
	// offset (0,-4) and image-flip translate (0,+4) cancel, leaving scale(1,-1).
	// So {a,b,c,d,e,f} = {1,0,0,-1,10,20}.
	want := Mat2D{1, 0, 0, -1, 10, 20}
	if !approxMat(op.Matrix, want, 1e-6) {
		t.Errorf("nested composed matrix = %v, want %v (nested translate must be preserved)", op.Matrix, want)
	}
}

// A nested movie clip must advance its OWN timeline in lock-step with the
// parent frame (looping when shorter), not freeze on its frame 0. This is the
// fix for the "detached limb stuck in place" symptom: an arm sub-clip that
// moves each frame must be sampled at the parent's current frame.
func TestBuild_NestedClipAdvancesTimeline(t *testing.T) {
	bmp := &BitmapData{Width: 2, Height: 2, RGBA: make([]byte, 2*2*4)}
	m := &Movie{Symbols: map[int]*Symbol{}, TagCounts: map[string]int{}}
	m.addSymbol(&Symbol{ID: 1, Kind: KindBitmap, InvertScaling: 1, Bitmap: bmp})

	// Inner clip id=2 has 2 frames: frame0 places the bitmap at x=0, frame1 at
	// x=1000 (world +100px). Depth 0 is sticky across frames.
	inner := &Symbol{ID: 2, Kind: KindMovieClip, InvertScaling: 1}
	inner.Ops = []DisplayOp{
		{Op: "place", CharID: 1, Depth: 0, Matrix: &Matrix{ScaleX: 1, ScaleY: 1, HasTranslate: true, TranslateX: 0}},
		{Op: "showframe", Duration: 1},
		{Op: "place", CharID: 1, Depth: 0, Matrix: &Matrix{ScaleX: 1, ScaleY: 1, HasTranslate: true, TranslateX: 1000}},
		{Op: "showframe", Duration: 1},
	}
	m.addSymbol(inner)

	// Root clip id=3 places the inner clip (no translation) and has 2 frames.
	root := &Symbol{ID: 3, Kind: KindMovieClip, InvertScaling: 1}
	root.Ops = []DisplayOp{
		{Op: "place", CharID: 2, Depth: 0},
		{Op: "showframe", Duration: 1},
		{Op: "showframe", Duration: 1},
	}
	m.addSymbol(root)
	m.RootID = 3

	pb := m.Build(3)
	if len(pb.Frames) != 2 {
		t.Fatalf("frames = %d, want 2", len(pb.Frames))
	}
	// Frame 0: inner at its frame 0 -> world x = 0.
	if got := pb.Frames[0].Ops[0].Matrix[4]; got != 0 {
		t.Errorf("frame0 x = %v, want 0", got)
	}
	// Frame 1: inner advanced to its frame 1 -> world x = 1000*0.1 = 100.
	if got := pb.Frames[1].Ops[0].Matrix[4]; got != 100 {
		t.Errorf("frame1 x = %v, want 100 (nested clip must advance its timeline)", got)
	}
}

// A placed child with a color transform must inherit the parent's color chain.
func TestBuild_NestedColorCombine(t *testing.T) {
	bmp := &BitmapData{Width: 2, Height: 2, RGBA: make([]byte, 2*2*4)}
	m := &Movie{Symbols: map[int]*Symbol{}, TagCounts: map[string]int{}}
	m.addSymbol(&Symbol{ID: 1, Kind: KindBitmap, InvertScaling: 1, Bitmap: bmp})

	root := &Symbol{ID: 2, Kind: KindMovieClip, InvertScaling: 1, FrameCount: 1}
	// Half-alpha via alphaMult 128 (/256 = 0.5).
	col := ColorTransform{HasMult: true, RedMult: 256, GreenMult: 256, BlueMult: 256, AlphaMult: 128}
	root.Ops = []DisplayOp{
		{Op: "place", CharID: 1, Depth: 0, Color: &col},
		{Op: "showframe", Duration: 1},
	}
	m.addSymbol(root)
	m.RootID = 2

	pb := m.Build(2)
	if len(pb.Frames) == 0 || len(pb.Frames[0].Ops) == 0 {
		t.Fatal("expected a drawable op")
	}
	if got := pb.Frames[0].Ops[0].Color.AlphaMult; got != 128 {
		t.Errorf("alphaMult = %d, want 128 (color must propagate to leaf)", got)
	}
}
