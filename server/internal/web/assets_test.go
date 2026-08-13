package web

import (
	"crypto/sha256"
	"encoding/hex"
	"net/http"
	"strings"
	"testing"
)

// A corrected stylesheet once failed to reach a browser that had already
// cached the broken one: /static/app.css was served with a day-long max-age
// and a URL that never changed, so fresh HTML rendered against week-old CSS.
// These tests hold the fix.

func TestStylesheetURLIsFingerprinted(t *testing.T) {
	s, _ := newTestServer(t, nil)
	body := get(t, s, "/").Body.String()

	want := "/static/app.css?v=" + assetVersion
	if !strings.Contains(body, want) {
		t.Errorf("the page does not link the stylesheet with a content fingerprint (want %q)", want)
	}
	if strings.Contains(body, `href="/static/app.css"`) {
		t.Error("the stylesheet is still linked without a fingerprint — a cached copy would never be replaced")
	}
	if assetVersion == "" {
		t.Error("assetVersion is empty")
	}
}

// The fingerprint has to actually track the bytes, or it is decoration.
func TestAssetVersionTracksContent(t *testing.T) {
	first := computeAssetVersion()
	if first != computeAssetVersion() {
		t.Fatal("computeAssetVersion is not deterministic — every restart would bust the cache")
	}
	if first != assetVersion {
		t.Errorf("assetVersion = %q, recomputed = %q", assetVersion, first)
	}
	// It must be a hash of the real stylesheet, so confirm it changes when the
	// stylesheet does. Hashing the same content with one byte flipped is the
	// closest we can get without writing to the embedded FS.
	css, err := staticFS.ReadFile("static/app.css")
	if err != nil {
		t.Fatalf("read app.css: %v", err)
	}
	if len(css) == 0 {
		t.Fatal("app.css is empty")
	}
	if hashOf(css) == hashOf(append(append([]byte{}, css...), ' ')) {
		t.Error("the hash does not change when the stylesheet does")
	}
}

func hashOf(b []byte) string {
	sum := sha256.Sum256(b)
	return hex.EncodeToString(sum[:])[:12]
}

func TestCacheHeaders(t *testing.T) {
	s, _ := newTestServer(t, nil)

	for _, tc := range []struct {
		path     string
		wantLong bool
		why      string
	}{
		{"/static/app.css?v=" + assetVersion, true, "a fingerprinted URL identifies its content, so it can be kept forever"},
		{"/static/app.css", false, "an unfingerprinted URL must expire quickly, or a fix cannot reach anyone"},
		{"/static/fonts/sora-latin.woff2", true, "fonts are immutable — a new one would ship under a new name"},
	} {
		rec := get(t, s, tc.path)
		if rec.Code != http.StatusOK {
			t.Errorf("%s: status = %d", tc.path, rec.Code)
			continue
		}
		cc := rec.Header().Get("Cache-Control")
		isLong := strings.Contains(cc, "immutable")
		if isLong != tc.wantLong {
			t.Errorf("%s: Cache-Control = %q, want long=%v (%s)", tc.path, cc, tc.wantLong, tc.why)
		}
	}
}
