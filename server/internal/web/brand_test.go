package web

import (
	"net/http"
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/config"
)

// The published project must be white-label: somebody who clones it and runs
// it gets a working site that carries nobody else's identity. These tests are
// the guard on that, because the failure mode is silent - a fork would simply
// ship somebody else's brand and nobody would notice until it was live.

func TestUnbrandedByDefault(t *testing.T) {
	if got := config.Default().Web.BrandDir; got != "" {
		t.Errorf("default brand_dir = %q, want empty", got)
	}

	p := newPortal(t)
	body := p.get("/").Body

	// No previous operator's name anywhere in the shipped strings.
	for _, brand := range []string{"Arena Reborn", "arenareborn"} {
		if strings.Contains(body, brand) {
			t.Errorf("the default build ships %q - a fork would impersonate that server", brand)
		}
	}

	// No requests for assets that are not in the binary: a <link> or <img>
	// pointing at a missing file makes every page load fetch a 404.
	for _, asset := range []string{"logo.png", "logo-large.png", "favicon.ico", "fighter-1.webp"} {
		if strings.Contains(body, asset) {
			t.Errorf("unbranded build still references %q", asset)
		}
	}

	// The server name has to appear as text instead, or the header is blank.
	if !strings.Contains(body, "Arena Server") {
		t.Error("no wordmark fallback: an unbranded header would render empty")
	}
}

// The whole point of brand_dir is that a deployment looks like itself without
// forking. Dropping a file in must shadow the embedded one.
func TestBrandDirOverridesEmbeddedAssets(t *testing.T) {
	dir := t.TempDir()
	if err := os.WriteFile(filepath.Join(dir, "logo.png"), []byte("PNG-not-really"), 0o644); err != nil {
		t.Fatalf("write logo: %v", err)
	}
	if err := os.WriteFile(filepath.Join(dir, "app.css"), []byte(":root{--accent:#custom}"), 0o644); err != nil {
		t.Fatalf("write css: %v", err)
	}

	p := newPortalWith(t, func(c *config.WebConfig) {
		c.BrandDir = dir
		c.ServerName = "Someone Else's Arena"
	})

	body := p.get("/").Body
	if !strings.Contains(body, "logo.png") {
		t.Error("a supplied logo.png is not used by the header")
	}
	if strings.Contains(body, "brand-wordmark") {
		t.Error("the text fallback is still rendered despite a logo being supplied")
	}
	if !strings.Contains(body, "Someone Else&#39;s Arena") && !strings.Contains(body, "Someone Else's Arena") {
		t.Error("configured server_name not used")
	}

	// An overridden file must actually be served, not just linked.
	css := p.get("/static/app.css")
	if css.Code != http.StatusOK || !strings.Contains(css.Body, "--accent:#custom") {
		t.Errorf("brand_dir app.css not served (status %d)", css.Code)
	}

	// A file NOT overridden must still fall back to the embedded copy.
	font := p.get("/static/fonts/sora-latin.woff2")
	if font.Code != http.StatusOK {
		t.Errorf("embedded fallback broken: font status = %d", font.Code)
	}
}

// A replaced logo has to invalidate the cache. Assets are served with a
// year-long max-age keyed on ?v=, so if brand files are left out of that hash
// an operator's new logo would never reach anybody who had already visited.
func TestBrandDirChangesAssetVersion(t *testing.T) {
	dir := t.TempDir()
	logo := filepath.Join(dir, "logo.png")
	if err := os.WriteFile(logo, []byte("first"), 0o644); err != nil {
		t.Fatalf("write: %v", err)
	}
	before := brandAssetVersion(dir)

	if err := os.WriteFile(logo, []byte("second, different bytes"), 0o644); err != nil {
		t.Fatalf("rewrite: %v", err)
	}
	after := brandAssetVersion(dir)

	if before == after {
		t.Error("changing a brand asset did not change the asset version - " +
			"browsers would keep serving the old logo from cache")
	}
	if brandAssetVersion("") != assetVersion {
		t.Error("an unconfigured brand_dir should leave the embedded version untouched")
	}
}

// brand_dir is operator-controlled but the path within it comes from the URL,
// so it must not be usable to read arbitrary files off the disk.
func TestBrandDirRejectsTraversal(t *testing.T) {
	dir := t.TempDir()
	secret := filepath.Join(filepath.Dir(dir), "secret.txt")
	if err := os.WriteFile(secret, []byte("SENSITIVE"), 0o644); err != nil {
		t.Fatalf("write: %v", err)
	}
	t.Cleanup(func() { _ = os.Remove(secret) })

	p := newPortalWith(t, func(c *config.WebConfig) { c.BrandDir = dir })

	for _, attempt := range []string{
		"/static/../secret.txt",
		"/static/%2e%2e/secret.txt",
		"/static/..%2fsecret.txt",
	} {
		pg := p.get(attempt)
		if strings.Contains(pg.Body, "SENSITIVE") {
			t.Errorf("%s escaped brand_dir and read a file outside it", attempt)
		}
	}
}
