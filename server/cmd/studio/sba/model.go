package sba

import "fmt"

// This file decodes SBA tag *bodies* into a rich, renderable model (the shallow
// walker in sba.go only recovers tag code/id/length). Every record here is a
// direct port of the disassembled client classes in
// com.ankamagames.framework.graphics.sba.* -- see each type's doc for the
// source class it mirrors.

// SymbolKind classifies a top-level definition.
type SymbolKind string

const (
	KindBitmap         SymbolKind = "bitmap"         // DefineBitmap
	KindBitmapSequence SymbolKind = "bitmapSequence" // DefineBitmapSequence
	KindMovieClip      SymbolKind = "movieClip"      // DefineMovieClip
)

// Matrix mirrors records.Matrix: a 2D affine in SWF layout with 16.16
// fixed-point components. The client applies it as
//
//	x' = scaleX*x + rotateSkew1*y + translateX
//	y' = rotateSkew0*x + scaleY*y + translateY
type Matrix struct {
	HasScale     bool    `json:"hasScale"`
	ScaleX       float64 `json:"scaleX"`
	ScaleY       float64 `json:"scaleY"`
	HasRotate    bool    `json:"hasRotate"`
	RotateSkew0  float64 `json:"rotateSkew0"`
	RotateSkew1  float64 `json:"rotateSkew1"`
	HasTranslate bool    `json:"hasTranslate"`
	TranslateX   float64 `json:"translateX"`
	TranslateY   float64 `json:"translateY"`
}

func identityMatrix() Matrix {
	return Matrix{ScaleX: 1, ScaleY: 1}
}

// twipScale is the client's twips->units factor applied to a placement's
// translation (FrameDataDescriptor: translateX/Y * 0.1F). Hot points are also
// stored *0.1 (BitmapDescriptor.initializeHotPointAndBitmapData).
const twipScale = 0.1

// Affine returns the placement matrix as canvas setTransform components
// [A, B, C, D, E, F] (x' = A*x + C*y + E, y' = B*x + D*y + F) matching the
// client's Matrix2D.set() convention verbatim.
//
// The client builds the GL modelview 2x2 (column-major buffer) as
//
//	buffer[0]=a  buffer[1]=-c   (column 0)
//	buffer[4]=-b buffer[5]=d    (column 1)
//	buffer[12]=tx buffer[13]=ty (translation)
//
// so the linear map is x' = a*x - b*y + tx ; y' = -c*x + d*y + ty, with
// set(a,b,c,d,tx,ty) = (scaleX, rotateSkew1, rotateSkew0, scaleY, tx*0.1,
// ty*0.1). Mapping to canvas components (note: NOT swapped):
//
//	A = a  = scaleX          B = -c = -rotateSkew0
//	C = -b = -rotateSkew1    D = d  = scaleY
//	E = tx = translateX*0.1  F = ty = translateY*0.1
//
// This reproduces the client's *Y-up* math verbatim; the single Y flip needed
// for a Y-down canvas is applied once at the view level (see the frontend).
func (m Matrix) Affine() [6]float64 {
	return [6]float64{
		m.ScaleX, -m.RotateSkew0,
		-m.RotateSkew1, m.ScaleY,
		m.TranslateX * twipScale, m.TranslateY * twipScale,
	}
}

func readMatrix(r *bitReader) Matrix {
	m := identityMatrix()
	m.HasScale = r.readBooleanBit()
	if m.HasScale {
		n := int(r.readUnsignedBits(5))
		m.ScaleX = r.readFPBits(n)
		m.ScaleY = r.readFPBits(n)
	}
	m.HasRotate = r.readBooleanBit()
	if m.HasRotate {
		n := int(r.readUnsignedBits(5))
		m.RotateSkew0 = r.readFPBits(n)
		m.RotateSkew1 = r.readFPBits(n)
	}
	m.HasTranslate = r.readBooleanBit()
	if m.HasTranslate {
		n := int(r.readUnsignedBits(5))
		m.TranslateX = r.readFPBits(n)
		m.TranslateY = r.readFPBits(n)
	}
	r.align()
	return m
}

// Mat2D is a 2x3 affine transform in canvas setTransform layout
// [a, b, c, d, e, f] where x' = a*x + c*y + e and y' = b*x + d*y + f. It is the
// composed, render-ready transform the compositor accumulates down the display
// tree (mirroring the client's GL modelview matrix stack).
type Mat2D [6]float64

func identityMat2D() Mat2D { return Mat2D{1, 0, 0, 1, 0, 0} }

// mul returns m * n (apply n first, then m) — the same order glMultMatrixf uses
// when a child mesh is pushed under its parent.
func (m Mat2D) mul(n Mat2D) Mat2D {
	return Mat2D{
		m[0]*n[0] + m[2]*n[1],
		m[1]*n[0] + m[3]*n[1],
		m[0]*n[2] + m[2]*n[3],
		m[1]*n[2] + m[3]*n[3],
		m[0]*n[4] + m[2]*n[5] + m[4],
		m[1]*n[4] + m[3]*n[5] + m[5],
	}
}

// translate returns m * T(tx,ty).
func (m Mat2D) translate(tx, ty float64) Mat2D {
	return m.mul(Mat2D{1, 0, 0, 1, tx, ty})
}

// scale returns m * S(sx,sy).
func (m Mat2D) scale(sx, sy float64) Mat2D {
	return m.mul(Mat2D{sx, 0, 0, sy, 0, 0})
}

// apply maps a local point through the affine.
func (m Mat2D) apply(x, y float64) (float64, float64) {
	return m[0]*x + m[2]*y + m[4], m[1]*x + m[3]*y + m[5]
}

// ColorTransform mirrors records.ColorTransform. Terms are integers: mult terms
// are /256 (256 == identity), add terms are absolute channel offsets.
type ColorTransform struct {
	HasMult   bool `json:"hasMult"`
	RedMult   int  `json:"redMult"`
	GreenMult int  `json:"greenMult"`
	BlueMult  int  `json:"blueMult"`
	AlphaMult int  `json:"alphaMult"`
	HasAdd    bool `json:"hasAdd"`
	RedAdd    int  `json:"redAdd"`
	GreenAdd  int  `json:"greenAdd"`
	BlueAdd   int  `json:"blueAdd"`
	AlphaAdd  int  `json:"alphaAdd"`
}

func identityColor() ColorTransform {
	return ColorTransform{RedMult: 256, GreenMult: 256, BlueMult: 256, AlphaMult: 256}
}

// combine composes a parent color transform with a child's, mirroring how the
// client's colorize() chains materials down the tree: multipliers multiply,
// additive terms of the child are scaled by the parent's multiplier then added.
func (c ColorTransform) combine(child ColorTransform) ColorTransform {
	out := ColorTransform{HasMult: true, HasAdd: c.HasAdd || child.HasAdd}
	out.RedMult = c.RedMult * child.RedMult / 256
	out.GreenMult = c.GreenMult * child.GreenMult / 256
	out.BlueMult = c.BlueMult * child.BlueMult / 256
	out.AlphaMult = c.AlphaMult * child.AlphaMult / 256
	out.RedAdd = c.RedAdd + c.RedMult*child.RedAdd/256
	out.GreenAdd = c.GreenAdd + c.GreenMult*child.GreenAdd/256
	out.BlueAdd = c.BlueAdd + c.BlueMult*child.BlueAdd/256
	out.AlphaAdd = c.AlphaAdd + c.AlphaMult*child.AlphaAdd/256
	return out
}

func readColorTransform(r *bitReader) ColorTransform {
	c := identityColor()
	// NOTE the asymmetry in the source: the FLAGS are read add-then-mult, but
	// the VALUE blocks are read mult-then-add.
	c.HasAdd = r.readBooleanBit()
	c.HasMult = r.readBooleanBit()
	n := int(r.readUnsignedBits(4))
	if c.HasMult {
		c.RedMult = int(r.readSignedBits(n))
		c.GreenMult = int(r.readSignedBits(n))
		c.BlueMult = int(r.readSignedBits(n))
		c.AlphaMult = int(r.readSignedBits(n))
	}
	if c.HasAdd {
		c.RedAdd = int(r.readSignedBits(n))
		c.GreenAdd = int(r.readSignedBits(n))
		c.BlueAdd = int(r.readSignedBits(n))
		c.AlphaAdd = int(r.readSignedBits(n))
	}
	r.align()
	return c
}

// BitmapData is a decoded AlphaBitmapData: straight (non-premultiplied) RGBA.
type BitmapData struct {
	Width         int
	Height        int
	Premultiplied bool
	RGBA          []byte // Width*Height*4, channel order R,G,B,A
}

// DisplayOp is one timeline instruction inside a movie clip (PlaceObject /
// RemoveObject / ShowFrame / ActionFlag).
type DisplayOp struct {
	Op       string          // "place" | "remove" | "showframe" | "action"
	CharID   int             // place: referenced definition id (0 = none)
	Depth    int             // place/remove: display depth
	Matrix   *Matrix         // place: optional
	Color    *ColorTransform // place: optional
	Duration int             // showframe: frame duration
}

// SeqFrame is one frame of a DefineBitmapSequence. The sequence-level
// InvertScaling (per BitmapSequenceDescriptor) is stored on the Symbol.
type SeqFrame struct {
	HotX     int
	HotY     int
	Duration int
	Bitmap   *BitmapData
}

// Symbol is a top-level definition, or the synthetic root timeline (ID -1).
type Symbol struct {
	ID      int
	Linkage string
	Kind    SymbolKind

	// Bitmap
	HotX          int
	HotY          int
	InvertScaling float64 // per-bitmap uniform scale (default 1)
	Bitmap        *BitmapData

	// BitmapSequence
	Frames []SeqFrame

	// MovieClip / sequences
	LoopCount  int
	Ops        []DisplayOp // movie-clip timeline
	FrameCount int         // number of ShowFrame ops (movie clip) or frames (sequence)
}

// Movie is a fully decoded .sba document.
type Movie struct {
	Header    Header
	Symbols   map[int]*Symbol
	Order     []int // symbol ids in file order (root -1 last if present)
	RootID    int   // best default symbol to play (-1 when a root timeline exists)
	HasRoot   bool  // whether a synthetic top-level timeline was collected
	TagCounts map[string]int
}

const rootSymbolID = -1

// ParseFull decodes the header, inflates the body, and fully decodes every tag
// body into the renderable Movie model.
func ParseFull(data []byte) (*Movie, error) {
	h, body, err := parseHeaderAndBody(data)
	if err != nil {
		return nil, err
	}
	m := &Movie{
		Header:    h,
		Symbols:   map[int]*Symbol{},
		TagCounts: map[string]int{},
	}
	root := &Symbol{ID: rootSymbolID, Kind: KindMovieClip}

	r := newBitReader(body)
	for r.Remaining() > 0 {
		code, tagData, err := readTag(r)
		if err != nil {
			return nil, err
		}
		m.TagCounts[tagName(code)]++
		if code == TagEnd {
			break
		}
		switch code {
		case TagDefineBitmap:
			sym, err := decodeDefineBitmap(tagData, h.Version)
			if err != nil {
				return nil, err
			}
			m.addSymbol(sym)
		case TagDefineBitmapSeq:
			sym, err := decodeDefineBitmapSequence(tagData, h.Version)
			if err != nil {
				return nil, err
			}
			m.addSymbol(sym)
		case TagDefineMovieClip:
			sym, err := decodeDefineMovieClip(tagData, h.Version)
			if err != nil {
				return nil, err
			}
			m.addSymbol(sym)
		case TagShowFrame, TagPlaceObject, TagRemoveObject, TagActionFlag:
			op := decodeDisplayOp(code, tagData)
			root.Ops = append(root.Ops, op)
			if code == TagShowFrame {
				root.FrameCount++
			}
		default:
			// Unknown/opaque tag: ignore body, keep walking.
		}
	}

	if len(root.Ops) > 0 {
		m.HasRoot = true
		m.Symbols[rootSymbolID] = root
		m.Order = append(m.Order, rootSymbolID)
	}
	m.RootID = m.pickDefaultSymbol()
	return m, nil
}

func (m *Movie) addSymbol(s *Symbol) {
	if _, dup := m.Symbols[s.ID]; !dup {
		m.Order = append(m.Order, s.ID)
	}
	m.Symbols[s.ID] = s
}

// pickDefaultSymbol chooses the most sensible thing to display: a root timeline
// if present, else the richest movie clip, else a bitmap sequence, else any
// bitmap.
func (m *Movie) pickDefaultSymbol() int {
	if m.HasRoot {
		return rootSymbolID
	}
	best := 0
	bestScore := -1
	found := false
	for _, id := range m.Order {
		s := m.Symbols[id]
		score := 0
		switch s.Kind {
		case KindMovieClip:
			score = 300 + s.FrameCount
		case KindBitmapSequence:
			score = 200 + len(s.Frames)
		case KindBitmap:
			score = 100
		}
		if score > bestScore {
			bestScore, best, found = score, id, true
		}
	}
	if !found {
		return rootSymbolID
	}
	return best
}

// readTag reads one SWF-style framed tag from r, returning its code and body
// bytes (a fresh copy so it stays valid after further reads).
func readTag(r *bitReader) (code int, body []byte, err error) {
	tagHeader := r.readUI16()
	code = int(tagHeader >> 6)
	length := int(tagHeader & 0x3f)
	if length == 0x3f {
		length = int(r.readUI32())
	}
	raw := r.readBytes(length)
	if r.Err() != nil {
		return 0, nil, r.Err()
	}
	body = make([]byte, len(raw))
	copy(body, raw)
	return code, body, nil
}

// defHeader decodes the shared DefinitionTag header (identifier + optional
// linkage) and leaves r positioned at the first type-specific field.
func defHeader(r *bitReader) (id int, linkage string) {
	id = int(r.readUI16())
	if r.readBooleanBit() {
		linkage = r.readString()
		r.align()
	}
	return id, linkage
}

func decodeDefineBitmap(data []byte, version byte) (*Symbol, error) {
	r := newBitReader(data)
	id, linkage := defHeader(r)
	inv := normInvertScaling(float64(r.readFloat16())) // per-bitmap uniform scale
	hotX, hotY := readPoint(r)
	bmp, err := readBitmapData(r, version)
	if err != nil {
		return nil, fmt.Errorf("DefineBitmap %d: %w", id, err)
	}
	return &Symbol{ID: id, Linkage: linkage, Kind: KindBitmap, HotX: hotX, HotY: hotY, InvertScaling: inv, Bitmap: bmp}, nil
}

// normInvertScaling guards a missing/zero invertScalingValue (which would erase
// the sprite) by falling back to 1.0.
func normInvertScaling(v float64) float64 {
	if v == 0 || v != v { // zero or NaN
		return 1
	}
	return v
}

func decodeDefineBitmapSequence(data []byte, version byte) (*Symbol, error) {
	r := newBitReader(data)
	id, linkage := defHeader(r)
	loop := int(r.readUI8())
	inv := normInvertScaling(float64(r.readFloat16())) // sequence-level uniform scale
	numFrames := int(r.readUI16())
	sym := &Symbol{ID: id, Linkage: linkage, Kind: KindBitmapSequence, LoopCount: loop, InvertScaling: inv}
	for i := 0; i < numFrames; i++ {
		hotX, hotY := readPoint(r)
		dur := int(r.readUI16())
		bmp, err := readBitmapData(r, version)
		if err != nil {
			return nil, fmt.Errorf("DefineBitmapSequence %d frame %d: %w", id, i, err)
		}
		sym.Frames = append(sym.Frames, SeqFrame{HotX: hotX, HotY: hotY, Duration: dur, Bitmap: bmp})
	}
	sym.FrameCount = len(sym.Frames)
	if err := r.Err(); err != nil {
		return nil, err
	}
	return sym, nil
}

func decodeDefineMovieClip(data []byte, version byte) (*Symbol, error) {
	r := newBitReader(data)
	id, linkage := defHeader(r)
	loop := int(r.readUI8())
	sym := &Symbol{ID: id, Linkage: linkage, Kind: KindMovieClip, LoopCount: loop}
	for r.Remaining() > 0 {
		code, body, err := readTag(r)
		if err != nil {
			return nil, fmt.Errorf("DefineMovieClip %d: %w", id, err)
		}
		if code == TagEnd {
			break
		}
		op := decodeDisplayOp(code, body)
		sym.Ops = append(sym.Ops, op)
		if code == TagShowFrame {
			sym.FrameCount++
		}
	}
	return sym, nil
}

func decodeDisplayOp(code int, data []byte) DisplayOp {
	r := newBitReader(data)
	switch code {
	case TagShowFrame:
		return DisplayOp{Op: "showframe", Duration: int(r.readUI16())}
	case TagPlaceObject:
		op := DisplayOp{Op: "place"}
		op.CharID = int(r.readUI16())
		op.Depth = int(r.readUI16())
		if r.readBooleanBit() {
			mtx := readMatrix(r)
			op.Matrix = &mtx
		}
		if r.readBooleanBit() {
			col := readColorTransform(r)
			op.Color = &col
		}
		return op
	case TagRemoveObject:
		return DisplayOp{Op: "remove", Depth: int(r.readUI16())}
	default: // ActionFlag / unknown
		return DisplayOp{Op: "action"}
	}
}

func readPoint(r *bitReader) (x, y int) {
	return int(r.readSI32()), int(r.readSI32())
}

// readBitmapData ports Bitmap.readBitmapData for the three SBA versions. v3
// (current) and v2 store an outer-zlib blob wrapping a raw-RGBA AlphaBitmapData;
// v1 wraps a standard encoded image (PNG/JPEG) that we decode via image.Decode.
func readBitmapData(r *bitReader, version byte) (*BitmapData, error) {
	_ = r.readUI8() // quality (byte; /100). Unused for rendering.
	dataLength := int(r.readUI16())
	if r.Err() != nil {
		return nil, r.Err()
	}
	if dataLength == 0 {
		return nil, nil
	}
	blob := r.readBytes(dataLength)
	if r.Err() != nil {
		return nil, r.Err()
	}
	switch version {
	case 1:
		return decodeBitmapV1(blob)
	case 2, 3:
		return decodeBitmapV23(blob)
	default:
		return nil, fmt.Errorf("sba: unknown bitmap version %d", version)
	}
}
