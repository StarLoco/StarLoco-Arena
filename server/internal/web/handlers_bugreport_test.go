package web

import (
	"bytes"
	"fmt"
	"net/http"
	"net/http/httptest"
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/config"
)

// clientBugReportBody reproduces EXACTLY what the retail client writes in
// aOG.a / aOG.a(auY,...): a multipart body with the client's own boundary shape
// ("***hex-hex***"), the screenshot part first, then the config block, then the
// bug fields. Field names are copied from the decompiled source, not guessed -
// if a name here stops matching the client, this test is the thing that says so.
func clientBugReportBody(t *testing.T, jpeg []byte) (body *bytes.Buffer, contentType string) {
	t.Helper()
	const boundary = "***1a2b3c4d-5e6f7a8b***"

	var b bytes.Buffer
	part := func(name, value string) {
		fmt.Fprintf(&b, "\r\n--%s\r\nContent-Disposition: form-data; name=%q\r\n\r\n%s", boundary, name, value)
	}

	// The client opens with the screenshot, with no leading CRLF.
	fmt.Fprintf(&b, "--%s\r\nContent-Disposition: form-data; name=\"screenshot\"; "+
		"filename=\"screenBug.jpg\"\r\nContent-Type: application/octet-stream\r\n\r\n", boundary)
	b.Write(jpeg)

	// lQ(): the machine/config block.
	part("config[graphic-device][GL_RENDERER]", "Test GL")
	part("config[OS][arch]", "amd64")
	part("config[OS][name]", "Windows 10")
	part("config[OS][version]", "10.0")
	part("config[memory][total]", "268435456")
	part("config[VM][name]", "Java HotSpot(TM) Client VM Oracle")
	// Per-thread rows: the client sends five of these for EVERY live thread.
	part("config[thread-12][name]", "AWT-EventQueue-0")
	part("config[thread-12][cpuTime]", "1234")

	// The bug itself.
	part("bug[title]", "Zaap arch renders below the platform")
	part("bug[type]", "Affichage / Map / Son")
	part("bug[seen_comportment]", "The arch is drawn under the platform")
	part("bug[awaited_comportment]", "It should be drawn on the platform")
	part("bug[way_to_reproduce]", "Log in on Venivici and look at the Zaap")
	part("config[screen][height]", "768.0") // client sends a float here
	part("config[screen][width]", "1024.0")
	part("config[screen][fullscreen]", "false")
	part("config[client_version]", "2.70 (build 72909)")
	part("log", "INFO some log line\nERROR the interesting one")
	part("replay", "replay-bytes")
	part("user[character][id]", "42")
	part("user[character][name]", "Probe")
	part("user[character][world][x]", "40")
	part("user[character][world][y]", "-20")
	part("user[character][world][name]", "Venivici")
	part("user[account][id]", "7")
	part("user[account][name]", "reporter")
	part("user[lang]", "fr")

	fmt.Fprintf(&b, "\r\n--%s--\r\n\r\n", boundary)
	return &b, "multipart/form-data; boundary=" + boundary
}

func postBugReport(t *testing.T, s *Server, body *bytes.Buffer, contentType string) *httptest.ResponseRecorder {
	t.Helper()
	req := httptest.NewRequest(http.MethodPost, "/fr/bug-report", body)
	req.Header.Set("Content-Type", contentType)
	req.RemoteAddr = "198.51.100.20:5555"
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, req)
	return rec
}

// TestBugReportAcceptsTheClientsOwnSubmission is the wire contract with the
// retail client. The client tells the player "sent" BEFORE it posts, so a
// mismatch here is invisible in game and shows up only as a line in the
// player's local log - which is precisely why it needs a test.
func TestBugReportAcceptsTheClientsOwnSubmission(t *testing.T) {
	dir := t.TempDir()
	s, st := newTestServer(t, func(c *config.WebConfig) {
		c.BugReportsEnabled = true
		c.BugReportDir = dir
	})

	// An account whose name the client will claim, so the resolve path is real.
	if _, err := st.Accounts.CreateAccount("reporter", "password1", false); err != nil {
		t.Fatalf("create account: %v", err)
	}

	jpeg := []byte("\xff\xd8\xff\xe0 not really a jpeg, but bytes are bytes \xff\xd9")
	body, ctype := clientBugReportBody(t, jpeg)
	rec := postBugReport(t, s, body, ctype)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200", rec.Code)
	}
	// aOG.a reads the FIRST LINE and logs an error unless it is exactly "OK".
	if first := strings.SplitN(rec.Body.String(), "\n", 2)[0]; first != "OK" {
		t.Errorf("first response line = %q, want %q (the client rejects anything else)", first, "OK")
	}

	reports, total, err := st.BugReports.List(true, 10, 0)
	if err != nil {
		t.Fatalf("list: %v", err)
	}
	if total != 1 || len(reports) != 1 {
		t.Fatalf("stored %d reports, want 1", total)
	}

	full, err := st.BugReports.Get(reports[0].ID)
	if err != nil {
		t.Fatalf("get: %v", err)
	}

	checks := []struct {
		field, got, want string
	}{
		{"Title", full.Title, "Zaap arch renders below the platform"},
		{"Type", full.Type, "Affichage / Map / Son"},
		{"Seen", full.Seen, "The arch is drawn under the platform"},
		{"Awaited", full.Awaited, "It should be drawn on the platform"},
		{"Reproduce", full.Reproduce, "Log in on Venivici and look at the Zaap"},
		{"CoachName", full.CoachName, "Probe"},
		{"WorldName", full.WorldName, "Venivici"},
		{"AccountName", full.AccountName, "reporter"},
		{"Lang", full.Lang, "fr"},
		{"ClientVersion", full.ClientVersion, "2.70 (build 72909)"},
	}
	for _, c := range checks {
		if c.got != c.want {
			t.Errorf("%s = %q, want %q", c.field, c.got, c.want)
		}
	}

	// The client sends the screen size as a float ("1024.0"); a naive ParseInt
	// gives 0 and silently loses it.
	if full.ScreenWidth != 1024 || full.ScreenHeight != 768 {
		t.Errorf("screen = %dx%d, want 1024x768 (the client sends floats)",
			full.ScreenWidth, full.ScreenHeight)
	}
	if full.WorldX != 40 || full.WorldY != -20 {
		t.Errorf("world position = (%d,%d), want (40,-20)", full.WorldX, full.WorldY)
	}
	if full.CoachRef != 42 {
		t.Errorf("CoachRef = %d, want 42", full.CoachRef)
	}
	if !strings.Contains(full.Log, "ERROR the interesting one") {
		t.Errorf("client log not stored: %q", full.Log)
	}

	// The claimed account name matched a real account, so it is resolved.
	if full.AccountID == 0 {
		t.Error("AccountID was not resolved from the claimed account name")
	}

	// Per-thread noise is dropped; the useful config is kept.
	if strings.Contains(full.SystemInfo, "thread-12") {
		t.Error("per-thread rows should not be stored (hundreds of useless lines)")
	}
	if !strings.Contains(full.SystemInfo, "amd64") {
		t.Errorf("OS info missing from SystemInfo: %q", full.SystemInfo)
	}

	// The screenshot went to disk, byte for byte, and not into the database.
	if full.ScreenshotFile == "" {
		t.Fatal("no screenshot stored")
	}
	got, err := os.ReadFile(filepath.Join(dir, full.ScreenshotFile))
	if err != nil {
		t.Fatalf("screenshot not on disk: %v", err)
	}
	if !bytes.Equal(got, jpeg) {
		t.Error("screenshot bytes differ from what was submitted")
	}
}

// An unknown account name must be kept as a claim, not silently attached to
// somebody. The endpoint is unauthenticated, so anyone can put any name in it.
func TestBugReportDoesNotTrustAnUnknownAccountName(t *testing.T) {
	s, st := newTestServer(t, func(c *config.WebConfig) {
		c.BugReportsEnabled = true
		c.BugReportDir = t.TempDir()
	})

	body, ctype := clientBugReportBody(t, []byte("x"))
	if rec := postBugReport(t, s, body, ctype); rec.Code != http.StatusOK {
		t.Fatalf("status = %d", rec.Code)
	}

	reports, _, err := st.BugReports.List(true, 10, 0)
	if err != nil || len(reports) != 1 {
		t.Fatalf("list: %v (%d reports)", err, len(reports))
	}
	full, _ := st.BugReports.Get(reports[0].ID)
	if full.AccountName != "reporter" {
		t.Errorf("claimed name should still be recorded, got %q", full.AccountName)
	}
	if full.AccountID != 0 {
		t.Errorf("AccountID = %d, want 0: no such account exists, so nothing may be attributed", full.AccountID)
	}
}

func TestBugReportCanBeDisabled(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) {
		c.BugReportsEnabled = false
		c.BugReportDir = t.TempDir()
	})
	body, ctype := clientBugReportBody(t, []byte("x"))
	if rec := postBugReport(t, s, body, ctype); rec.Code != http.StatusNotFound {
		t.Errorf("status = %d, want 404 when bug reports are disabled", rec.Code)
	}
}

// Screenshots must never be readable without admin rights: one can show the
// reporter's account name, their chat and their mailbox.
func TestBugScreenshotRequiresAdmin(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) {
		c.BugReportsEnabled = true
		c.BugReportDir = t.TempDir()
	})
	body, ctype := clientBugReportBody(t, []byte("x"))
	postBugReport(t, s, body, ctype)

	for _, path := range []string{"/admin/bugs", "/admin/bugs/1", "/admin/bugs/1/screenshot"} {
		rec := get(t, s, path)
		if rec.Code == http.StatusOK {
			t.Errorf("%s served without a session (code %d)", path, rec.Code)
		}
	}
}

// TestBugReportListenerExposesNothingElse is the whole justification for that
// second listener existing.
//
// It runs on plain http because the client's Java 1.6 cannot do modern TLS. If
// it ever served the full portal, the login form and the admin console would be
// on an unencrypted port - so this pins that it answers the bug endpoint and
// NOTHING else, and that a GET (rather than POST) does not reach it either.
func TestBugReportListenerExposesNothingElse(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) {
		c.BugReportsEnabled = true
		c.BugReportDir = t.TempDir()
	})
	h := s.BugReportHandler()

	// The one route that must work.
	body, ctype := clientBugReportBody(t, []byte("x"))
	req := httptest.NewRequest(http.MethodPost, "/fr/bug-report", body)
	req.Header.Set("Content-Type", ctype)
	req.RemoteAddr = "198.51.100.20:5555"
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)
	if rec.Code != http.StatusOK {
		t.Fatalf("POST /fr/bug-report = %d, want 200", rec.Code)
	}

	// Everything else must be unreachable here, especially anything that could
	// leak a credential over plain http.
	for _, tc := range []struct{ method, path string }{
		{http.MethodGet, "/"},
		{http.MethodGet, "/login"},
		{http.MethodPost, "/login"},
		{http.MethodGet, "/register"},
		{http.MethodPost, "/register"},
		{http.MethodGet, "/account"},
		{http.MethodGet, "/admin"},
		{http.MethodGet, "/admin/bugs"},
		{http.MethodGet, "/admin/bugs/1/screenshot"},
		{http.MethodGet, "/status"},
		{http.MethodGet, "/ladder"},
		{http.MethodGet, "/static/app.css"},
		// The bug route itself must not answer to GET.
		{http.MethodGet, "/fr/bug-report"},
	} {
		rec := httptest.NewRecorder()
		h.ServeHTTP(rec, httptest.NewRequest(tc.method, tc.path, nil))
		if rec.Code == http.StatusOK {
			t.Errorf("%s %s returned 200 on the bug-report listener; only the "+
				"bug endpoint may be reachable on a plain-http port",
				tc.method, tc.path)
		}
	}
}

// TestLoginScreenLinks pins the two routes the game client's login plaques
// point at. Those URLs are compiled into core.jar - into the client players
// have already downloaded - so the server must keep answering them, and the
// Discord invite behind /discord has to stay changeable here rather than in
// the client. The client builds base + language + path, hence both the bare
// and language-prefixed forms.
func TestLoginScreenLinks(t *testing.T) {
	t.Run("discord redirects to the configured invite", func(t *testing.T) {
		s, _ := newTestServer(t, func(c *config.WebConfig) {
			c.DiscordURL = "https://discord.gg/example"
		})
		for _, path := range []string{"/discord", "/fr/discord", "/en/discord"} {
			rec := get(t, s, path)
			if rec.Code != http.StatusFound {
				t.Errorf("%s: status = %d, want 302", path, rec.Code)
			}
			if loc := rec.Header().Get("Location"); loc != "https://discord.gg/example" {
				t.Errorf("%s: Location = %q", path, loc)
			}
		}
	})

	t.Run("unset invite falls back to the home page, not a dead end", func(t *testing.T) {
		s, _ := newTestServer(t, func(c *config.WebConfig) { c.DiscordURL = "" })
		rec := get(t, s, "/fr/discord")
		if rec.Code < 300 || rec.Code > 399 {
			t.Fatalf("status = %d, want a redirect", rec.Code)
		}
		if loc := rec.Header().Get("Location"); loc != "/" {
			t.Errorf("Location = %q, want /", loc)
		}
	})

	t.Run("register plaque reaches the sign-up page", func(t *testing.T) {
		s, _ := newTestServer(t, nil)
		rec := get(t, s, "/fr/register")
		if rec.Code < 300 || rec.Code > 399 {
			t.Fatalf("status = %d, want a redirect", rec.Code)
		}
		if loc := rec.Header().Get("Location"); loc != "/register" {
			t.Errorf("Location = %q, want /register", loc)
		}
	})
}

// truncateTail keeps the END, because the last lines of a client log are the
// ones describing the crash that prompted the report.
func TestTruncateTailKeepsTheEnd(t *testing.T) {
	long := strings.Repeat("a", 100) + "THE-CRASH"
	got := truncateTail(long, 20)
	if !strings.HasSuffix(got, "THE-CRASH") {
		t.Errorf("tail lost: %q", got)
	}
	if !strings.HasPrefix(got, "[... truncated ...]") {
		t.Errorf("truncation not signposted: %q", got)
	}
}
