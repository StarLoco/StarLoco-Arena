package sba

import (
	"archive/zip"
	"bytes"
	"compress/zlib"
	"encoding/binary"
	"io"
	"os"
	"path/filepath"
	"testing"
)

// --- synthetic unit tests (independent of real assets) ---

// buildSBA builds a minimal uncompressed ("sba") document with the given raw
// tag-stream body, for testing the walker without zlib.
func buildSBA(version byte, body []byte) []byte {
	h := []byte{'s', 'b', 'a', version, 0, 0, 0, 0}
	binary.LittleEndian.PutUint32(h[4:], uint32(len(body)+8))
	return append(h, body...)
}

// shortTag encodes a SWF short-form tag header + body.
func shortTag(code int, body []byte) []byte {
	th := uint16(code<<6) | uint16(len(body))
	out := make([]byte, 2)
	binary.LittleEndian.PutUint16(out, th)
	return append(out, body...)
}

func TestParse_SyntheticUncompressed(t *testing.T) {
	// A DefineBitmap(2) with id=7 (uint16 LE), a ShowFrame(1), then End(0).
	bmp := shortTag(TagDefineBitmap, []byte{7, 0, 0xAA, 0xBB})
	show := shortTag(TagShowFrame, nil)
	end := shortTag(TagEnd, nil)
	doc, err := Parse(buildSBA(3, concat(bmp, show, end)))
	if err != nil {
		t.Fatalf("Parse: %v", err)
	}
	if doc.Header.Compressed {
		t.Error("expected uncompressed")
	}
	if doc.Header.Version != 3 {
		t.Errorf("version = %d, want 3", doc.Header.Version)
	}
	if len(doc.Tags) != 3 {
		t.Fatalf("tags = %d, want 3", len(doc.Tags))
	}
	if doc.Tags[0].Code != TagDefineBitmap || !doc.Tags[0].HasID || doc.Tags[0].ID != 7 {
		t.Errorf("tag0 = %+v, want DefineBitmap id=7", doc.Tags[0])
	}
	if doc.Tags[2].Code != TagEnd {
		t.Errorf("last tag should be End, got %+v", doc.Tags[2])
	}
}

func TestParse_LongTag(t *testing.T) {
	// Force the extended-length form (0x3F): a 100-byte body.
	body := make([]byte, 100)
	th := make([]byte, 2)
	binary.LittleEndian.PutUint16(th, uint16(TagDefineMovieClip<<6)|0x3f)
	lng := make([]byte, 4)
	binary.LittleEndian.PutUint32(lng, uint32(len(body)))
	binary.LittleEndian.PutUint16(body, 12) // id
	raw := concat(th, lng, body, shortTag(TagEnd, nil))
	doc, err := Parse(buildSBA(3, raw))
	if err != nil {
		t.Fatalf("Parse: %v", err)
	}
	if doc.Tags[0].Length != 100 || doc.Tags[0].ID != 12 {
		t.Errorf("long tag = %+v, want length=100 id=12", doc.Tags[0])
	}
}

func TestParse_BadSignature(t *testing.T) {
	if _, err := Parse([]byte("xxx\x03\x00\x00\x00\x00")); err == nil {
		t.Fatal("expected bad-signature error")
	}
}

func TestParse_SyntheticCompressed(t *testing.T) {
	// zlib-compress a tag body and wrap with an "SBA" (uppercase) header.
	inner := concat(shortTag(TagDefineBitmap, []byte{3, 0}), shortTag(TagEnd, nil))
	var zbuf bytes.Buffer
	zw := zlib.NewWriter(&zbuf)
	zw.Write(inner)
	zw.Close()
	h := []byte{'S', 'B', 'A', 3, 0, 0, 0, 0}
	binary.LittleEndian.PutUint32(h[4:], uint32(len(inner)+8))
	doc, err := Parse(append(h, zbuf.Bytes()...))
	if err != nil {
		t.Fatalf("Parse compressed: %v", err)
	}
	if !doc.Header.Compressed {
		t.Error("expected compressed")
	}
	if len(doc.Tags) != 2 || doc.Tags[0].ID != 3 {
		t.Errorf("tags = %+v", doc.Tags)
	}
}

// --- real-data integration: parse actual client .sba files ---

func TestParse_RealAnimations(t *testing.T) {
	jar := findClientJar(t, "animations.jar")
	r, err := zip.OpenReader(jar)
	if err != nil {
		t.Fatalf("open jar: %v", err)
	}
	defer r.Close()

	n, withBitmaps := 0, 0
	for _, f := range r.File {
		if filepath.Ext(f.Name) != ".sba" {
			continue
		}
		data := readZip(t, f)
		// Sanity: real files use the uppercase (compressed) signature + zlib.
		if len(data) >= 8 && string(data[0:3]) == "SBA" {
			if data[8] != 0x78 { // zlib CMF byte
				t.Errorf("%s: expected zlib after header, got 0x%02x", f.Name, data[8])
			}
		}
		doc, err := Parse(data)
		if err != nil {
			t.Fatalf("Parse(%s): %v", f.Name, err)
		}
		if len(doc.Tags) == 0 {
			t.Errorf("%s: no tags decoded", f.Name)
		}
		// The last tag should be End for a well-formed document.
		if last := doc.Tags[len(doc.Tags)-1]; last.Code != TagEnd {
			t.Logf("%s: last tag is %s (not End) -- tolerated", f.Name, last.Name)
		}
		for _, tg := range doc.Tags {
			if tg.Code == TagDefineBitmap {
				withBitmaps++
				break
			}
		}
		n++
		if n >= 20 {
			break
		}
	}
	if n == 0 {
		t.Skip("no .sba files in animations.jar")
	}
	t.Logf("parsed %d real .sba files (%d contained DefineBitmap tags)", n, withBitmaps)
}

// helpers

func concat(parts ...[]byte) []byte {
	var out []byte
	for _, p := range parts {
		out = append(out, p...)
	}
	return out
}

func findClientJar(t *testing.T, name string) string {
	t.Helper()
	wd, _ := os.Getwd()
	// The runnable client lives under either "client-compiled/game/contents"
	// or "client/compiled/game/contents" depending on the checkout layout.
	candidates := []string{
		filepath.Join(wd, "..", "..", "..", "..", "client-compiled", "game", "contents", name),
		filepath.Join(wd, "..", "..", "..", "..", "client", "compiled", "game", "contents", name),
	}
	for _, p := range candidates {
		if _, err := os.Stat(p); err == nil {
			return p
		}
	}
	t.Skipf("client jar %s not found (looked in %v)", name, candidates)
	return ""
}

func readZip(t *testing.T, f *zip.File) []byte {
	t.Helper()
	rc, err := f.Open()
	if err != nil {
		t.Fatalf("open %s: %v", f.Name, err)
	}
	defer rc.Close()
	b, err := io.ReadAll(rc)
	if err != nil {
		t.Fatalf("read %s: %v", f.Name, err)
	}
	return b
}
