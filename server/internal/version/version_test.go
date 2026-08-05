package version

import "testing"

func TestParseSemver(t *testing.T) {
	tests := []struct {
		in            string
		ok            bool
		maj, min, pat int
		pre           string
	}{
		{"v1.2.3", true, 1, 2, 3, ""},
		{"1.2.3", true, 1, 2, 3, ""},
		{"v0.1.0", true, 0, 1, 0, ""},
		{"v1.2", true, 1, 2, 0, ""},
		{"v10.20.30", true, 10, 20, 30, ""},
		{"v1.2.3-rc.1", true, 1, 2, 3, "rc.1"},
		{"v1.2.3+build.7", true, 1, 2, 3, ""},    // build metadata ignored
		{"v1.2.3-rc.1+b", true, 1, 2, 3, "rc.1"}, // both
		{" v1.2.3 ", true, 1, 2, 3, ""},          // trimmed
		{"dev", false, 0, 0, 0, ""},              // development build
		{"", false, 0, 0, 0, ""},                 //
		{"v", false, 0, 0, 0, ""},                //
		{"v1.2.3.4", false, 0, 0, 0, ""},         // too many components
		{"vx.y.z", false, 0, 0, 0, ""},           //
		{"v-1.0.0", false, 0, 0, 0, ""},          // negative
		{"backup-v2.70-pre", false, 0, 0, 0, ""}, // the repo's non-release tag
	}
	for _, tt := range tests {
		got, ok := ParseSemver(tt.in)
		if ok != tt.ok {
			t.Errorf("ParseSemver(%q) ok = %v, want %v", tt.in, ok, tt.ok)
			continue
		}
		if !ok {
			continue
		}
		if got.Major != tt.maj || got.Minor != tt.min || got.Patch != tt.pat || got.Pre != tt.pre {
			t.Errorf("ParseSemver(%q) = %+v, want %d.%d.%d-%q",
				tt.in, got, tt.maj, tt.min, tt.pat, tt.pre)
		}
	}
}

func TestCompare(t *testing.T) {
	tests := []struct {
		a, b string
		want int
	}{
		{"v1.0.0", "v1.0.0", 0},
		{"v1.0.1", "v1.0.0", 1},
		{"v1.1.0", "v1.0.9", 1},
		{"v2.0.0", "v1.9.9", 1},
		{"v1.0.0", "v1.0.1", -1},
		{"v0.1.0", "v0.2.0", -1},
		// Pre-releases sort below their final release (semver §11).
		{"v1.0.0-rc.1", "v1.0.0", -1},
		{"v1.0.0", "v1.0.0-rc.1", 1},
		{"v1.0.0-rc.1", "v1.0.0-rc.2", -1},
		{"v1.0.0-rc.1", "v1.0.0-rc.1", 0},
		// A pre-release of a newer version still beats an older final release.
		{"v1.1.0-rc.1", "v1.0.0", 1},
	}
	for _, tt := range tests {
		a, okA := ParseSemver(tt.a)
		b, okB := ParseSemver(tt.b)
		if !okA || !okB {
			t.Fatalf("bad fixture %q/%q", tt.a, tt.b)
		}
		if got := a.Compare(b); got != tt.want {
			t.Errorf("Compare(%s, %s) = %d, want %d", tt.a, tt.b, got, tt.want)
		}
	}
}

func TestSemverString(t *testing.T) {
	for _, in := range []string{"v1.2.3", "v0.1.0", "v1.2.3-rc.1"} {
		v, ok := ParseSemver(in)
		if !ok {
			t.Fatalf("ParseSemver(%q) failed", in)
		}
		if got := v.String(); got != in {
			t.Errorf("round-trip %q -> %q", in, got)
		}
	}
}

// The default (unstamped) build must be recognised as a development build, or
// the update check would compare "dev" against a release tag on every start.
func TestDefaultBuildIsDev(t *testing.T) {
	if !IsDevBuild() {
		t.Errorf("Version = %q: an unstamped build must report IsDevBuild()", Version)
	}
	if _, ok := ParseSemver(Version); ok {
		t.Errorf("the placeholder version %q must not parse as a release", Version)
	}
}

func TestStringMentionsVersion(t *testing.T) {
	if s := String(); s == "" {
		t.Error("String() must not be empty")
	}
}
