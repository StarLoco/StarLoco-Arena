// Package update asks GitHub, once at startup, whether a newer server release
// has been published, so an operator running an old binary finds out without
// having to watch the repository.
//
// It is deliberately minimal and privacy-preserving:
//
//   - one anonymous HTTPS GET of a public endpoint,
//   - nothing about the server, its config or its players is transmitted,
//   - nothing is ever downloaded, unpacked or executed — the operator is told
//     where to get the release and decides for themselves,
//   - every failure is non-fatal and silent by default (offline servers must
//     not be nagged, and a GitHub outage must never affect a running game).
package update

import (
	"context"
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"net/http"
	"time"

	"github.com/StarLoco/arena-2.70/internal/version"
)

// ErrNoRelease means the repository has no published release to compare
// against (a brand-new project, or only drafts/pre-releases exist).
var ErrNoRelease = errors.New("update: no published release")

// DefaultTimeout bounds the whole check.
const DefaultTimeout = 5 * time.Second

// Release is the slice of GitHub's release payload we care about.
type Release struct {
	TagName    string `json:"tag_name"`
	HTMLURL    string `json:"html_url"`
	Name       string `json:"name"`
	Draft      bool   `json:"draft"`
	Prerelease bool   `json:"prerelease"`
}

// Result reports the outcome of a check.
type Result struct {
	// Current is the running build's version.
	Current string
	// Latest is the newest published release tag.
	Latest string
	// URL is the release page for Latest.
	URL string
	// Available is true when Latest is strictly newer than Current.
	Available bool
	// Major is true when the jump crosses a major version, i.e. the release
	// notes are worth reading before upgrading (semver: breaking changes).
	Major bool
}

// Checker performs the lookup. The zero value is usable; fields exist so tests
// can point it at a stub server.
type Checker struct {
	// BaseURL is the GitHub API root. Empty = https://api.github.com.
	BaseURL string
	// Owner/Repo default to the project's own repository.
	Owner, Repo string
	// HTTPClient defaults to a client bounded by Timeout.
	HTTPClient *http.Client
	// Timeout defaults to DefaultTimeout.
	Timeout time.Duration
}

func (c *Checker) baseURL() string {
	if c.BaseURL != "" {
		return c.BaseURL
	}
	return "https://api.github.com"
}

func (c *Checker) owner() string {
	if c.Owner != "" {
		return c.Owner
	}
	return version.RepoOwner
}

func (c *Checker) repo() string {
	if c.Repo != "" {
		return c.Repo
	}
	return version.RepoName
}

func (c *Checker) timeout() time.Duration {
	if c.Timeout > 0 {
		return c.Timeout
	}
	return DefaultTimeout
}

func (c *Checker) httpClient() *http.Client {
	if c.HTTPClient != nil {
		return c.HTTPClient
	}
	return &http.Client{Timeout: c.timeout()}
}

// Latest fetches the newest published (non-draft, non-prerelease) release.
// GitHub's /releases/latest already excludes drafts and pre-releases.
func (c *Checker) Latest(ctx context.Context) (Release, error) {
	var rel Release

	ctx, cancel := context.WithTimeout(ctx, c.timeout())
	defer cancel()

	url := fmt.Sprintf("%s/repos/%s/%s/releases/latest", c.baseURL(), c.owner(), c.repo())
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, url, nil)
	if err != nil {
		return rel, err
	}
	// GitHub rejects requests without a User-Agent outright.
	req.Header.Set("User-Agent", "arena-server/"+version.Short())
	req.Header.Set("Accept", "application/vnd.github+json")
	req.Header.Set("X-GitHub-Api-Version", "2022-11-28")

	resp, err := c.httpClient().Do(req)
	if err != nil {
		return rel, err
	}
	defer func() {
		_, _ = io.Copy(io.Discard, resp.Body)
		_ = resp.Body.Close()
	}()

	switch resp.StatusCode {
	case http.StatusOK:
	case http.StatusNotFound:
		return rel, ErrNoRelease
	default:
		return rel, fmt.Errorf("update: github returned %s", resp.Status)
	}

	// Cap the read: we only need a small JSON object, and an unbounded read of
	// an unexpected response is a needless memory risk.
	body := io.LimitReader(resp.Body, 1<<20)
	if err := json.NewDecoder(body).Decode(&rel); err != nil {
		return rel, fmt.Errorf("update: decode release: %w", err)
	}
	if rel.TagName == "" {
		return rel, ErrNoRelease
	}
	return rel, nil
}

// Check compares the running build against the latest published release.
//
// Development builds (unstamped `go build` / `go run`) return ErrDevBuild:
// "dev" has no position in the version ordering, so any comparison would
// wrongly claim an update on every start.
func (c *Checker) Check(ctx context.Context, current string) (Result, error) {
	res := Result{Current: current}

	cur, ok := version.ParseSemver(current)
	if !ok {
		return res, ErrDevBuild
	}
	rel, err := c.Latest(ctx)
	if err != nil {
		return res, err
	}
	res.Latest = rel.TagName
	res.URL = rel.HTMLURL
	if res.URL == "" {
		res.URL = version.ReleasesURL
	}
	latest, ok := version.ParseSemver(rel.TagName)
	if !ok {
		return res, fmt.Errorf("update: unparseable release tag %q", rel.TagName)
	}
	res.Available = latest.Compare(cur) > 0
	res.Major = res.Available && latest.Major > cur.Major
	return res, nil
}

// ErrDevBuild is returned when the running binary has no release version.
var ErrDevBuild = errors.New("update: development build, skipping check")

// Check runs a one-shot check of the project's own repository for the running
// build. It is the entry point cmd/server uses.
func Check(ctx context.Context, timeout time.Duration) (Result, error) {
	c := &Checker{Timeout: timeout}
	return c.Check(ctx, version.Version)
}
