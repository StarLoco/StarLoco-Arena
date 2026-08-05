// Package version carries the build metadata stamped into the binary at link
// time, and the identity of the GitHub repository releases are published to.
//
// Release builds set these with -ldflags:
//
//	-X github.com/StarLoco/arena-2.70/internal/version.Version=v0.1.0
//	-X github.com/StarLoco/arena-2.70/internal/version.Commit=abc1234
//	-X github.com/StarLoco/arena-2.70/internal/version.Date=2026-08-05T12:00:00Z
//
// A plain `go build` / `go run` leaves them at their defaults, which report the
// build as a development build. Nothing in the server depends on the version
// being meaningful — it is used for the startup banner and the update check.
package version

import (
	"fmt"
	"runtime"
	"runtime/debug"
	"strconv"
	"strings"
)

// Build metadata. Overwritten at link time for release builds; see the package
// doc. Do not rename these without updating .goreleaser.yaml.
var (
	// Version is the release tag, e.g. "v0.1.0". "dev" for a local build.
	Version = "dev"
	// Commit is the short git SHA the binary was built from.
	Commit = ""
	// Date is the RFC-3339 build timestamp.
	Date = ""
)

// Repository identifies the GitHub project releases are published to. The
// update check asks GitHub for this repository's latest release.
const (
	RepoOwner = "StarLoco"
	RepoName  = "StarLoco-Arena"
)

// ReleasesURL is the human-facing page pointed at when an update is available.
const ReleasesURL = "https://github.com/" + RepoOwner + "/" + RepoName + "/releases/latest"

// IsDevBuild reports whether this binary was built without release stamping.
// Development builds skip the update check: "dev" cannot be meaningfully
// compared against a release tag, and every check would report an update.
func IsDevBuild() bool { return Version == "dev" || Version == "" }

// Short returns just the version, falling back to the VCS revision recorded by
// the Go toolchain when the binary was not stamped (e.g. `go install`).
func Short() string {
	if !IsDevBuild() {
		return Version
	}
	if rev := vcsRevision(); rev != "" {
		return "dev+" + rev
	}
	return "dev"
}

// String returns the full one-line build identity used by --version.
func String() string {
	var b strings.Builder
	b.WriteString("DofusArena 2.70 server ")
	b.WriteString(Short())
	if Commit != "" {
		b.WriteString(" (")
		b.WriteString(Commit)
		b.WriteString(")")
	}
	if Date != "" {
		b.WriteString(" built ")
		b.WriteString(Date)
	}
	b.WriteString(fmt.Sprintf(" [%s %s/%s]", runtime.Version(), runtime.GOOS, runtime.GOARCH))
	return b.String()
}

// vcsRevision digs the git SHA out of the embedded build info, which the Go
// toolchain records automatically for builds made inside a git checkout.
func vcsRevision() string {
	info, ok := debug.ReadBuildInfo()
	if !ok {
		return ""
	}
	for _, s := range info.Settings {
		if s.Key == "vcs.revision" && len(s.Value) >= 7 {
			return s.Value[:7]
		}
	}
	return ""
}

// Semver is a parsed "vMAJOR.MINOR.PATCH" release tag.
type Semver struct {
	Major, Minor, Patch int
	// Pre is the pre-release suffix without the leading '-' ("rc.1"), empty for
	// a final release. A version with a pre-release sorts BELOW the same
	// version without one, per semver §11.
	Pre string
}

// ParseSemver parses a release tag. The leading "v" is optional, and any build
// metadata ("+meta") is ignored. It is deliberately lenient about extra numeric
// components so a tag like "v1.2" still parses.
func ParseSemver(s string) (Semver, bool) {
	var out Semver
	s = strings.TrimSpace(s)
	s = strings.TrimPrefix(s, "v")
	if s == "" {
		return out, false
	}
	// Drop build metadata: it never affects precedence.
	if i := strings.IndexByte(s, '+'); i >= 0 {
		s = s[:i]
	}
	// Split off the pre-release suffix.
	if i := strings.IndexByte(s, '-'); i >= 0 {
		out.Pre = s[i+1:]
		s = s[:i]
	}
	parts := strings.Split(s, ".")
	if len(parts) == 0 || len(parts) > 3 {
		return Semver{}, false
	}
	dst := []*int{&out.Major, &out.Minor, &out.Patch}
	for i, p := range parts {
		n, err := strconv.Atoi(p)
		if err != nil || n < 0 {
			return Semver{}, false
		}
		*dst[i] = n
	}
	return out, true
}

// Compare returns -1 if a sorts before b, +1 if after, 0 if equal.
func (a Semver) Compare(b Semver) int {
	if c := cmpInt(a.Major, b.Major); c != 0 {
		return c
	}
	if c := cmpInt(a.Minor, b.Minor); c != 0 {
		return c
	}
	if c := cmpInt(a.Patch, b.Patch); c != 0 {
		return c
	}
	// Equal core version: a pre-release sorts below a final release.
	switch {
	case a.Pre == b.Pre:
		return 0
	case a.Pre == "":
		return 1
	case b.Pre == "":
		return -1
	default:
		return strings.Compare(a.Pre, b.Pre)
	}
}

// String renders the version back as a "vX.Y.Z" tag.
func (a Semver) String() string {
	s := fmt.Sprintf("v%d.%d.%d", a.Major, a.Minor, a.Patch)
	if a.Pre != "" {
		s += "-" + a.Pre
	}
	return s
}

func cmpInt(a, b int) int {
	switch {
	case a < b:
		return -1
	case a > b:
		return 1
	default:
		return 0
	}
}
