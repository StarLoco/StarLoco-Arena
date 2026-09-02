package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// OpCoachCreationResult (2050) carries [u8 resultCode]; 11 is the client's
// "invalid or already taken" code.
const (
	opCoachCreateResult      = 2050
	coachResultInvalidOrDupe = 11
)

// TestHostileCoachNameIsRejected drives the ACTUAL attack path: a modified client
// sending 2049 directly with a name its own UI would never submit.
//
// This is the test that matters. Unit tests on validateCoachName passed while the
// handler was still unguarded - a mutation that ignored the validator's verdict
// survived the entire suite, because nothing exercised opcode 2049 with hostile
// input. Testing the validator is not testing the door.
func TestHostileCoachNameIsRejected(t *testing.T) {
	t.Parallel()
	addr := testServer(t)

	cases := []struct{ name, why string }{
		{"", "empty name - previously accepted, only TrimSpace was applied"},
		{"   ", "whitespace only, collapses to empty"},
		{"<b>Admin</b>", "markup injection into every client that renders the name"},
		{"Ad\u00admin", "soft hyphen: renders as 'Admin', distinct DB row"},
		{"Ad\nmin", "newline in a display name"},
		{"A", "below the client's own 3-character minimum"},
	}

	for _, tc := range cases {
		t.Run(tc.why, func(t *testing.T) {
			c := dialNoCoach(t, addr, "hostile_"+sanitizeSub(tc.why))
			// Wait for the creation prompt, then submit the hostile name.
			if _, _, err := c.WaitFor(testclient.OpCoachCreateReq, 3*time.Second); err != nil {
				t.Fatalf("expected a coach-creation prompt: %v", err)
			}
			payload := testclient.NewW().Str8(tc.name).U8(1).U8(1).U8(0).Bytes()
			if err := c.Send(2, testclient.OpCoachCreate, payload); err != nil {
				t.Fatalf("send 2049: %v", err)
			}

			f, _, err := c.WaitFor(opCoachCreateResult, 3*time.Second)
			if err != nil {
				t.Fatalf("no 2050 result for %q (%s): %v", tc.name, tc.why, err)
			}
			if len(f.Payload) < 1 {
				t.Fatal("2050 carried no result code")
			}
			if got := f.Payload[0]; got != coachResultInvalidOrDupe {
				t.Errorf("name %q accepted with result code %d, want %d (%s)",
					tc.name, got, coachResultInvalidOrDupe, tc.why)
			}
		})
	}
}

// TestCoachCreationCannotBeReplayed pins the one-coach-per-account rule. Without
// it a single authenticated account could loop 2049 forever, minting a fresh
// coach row - plus starter cards and wallet - on every iteration.
func TestCoachCreationCannotBeReplayed(t *testing.T) {
	t.Parallel()
	addr := testServer(t)

	c := dialNoCoach(t, addr, "replay_acct")
	if _, err := c.CreateCoach("Legit"); err != nil {
		t.Fatalf("first coach should be created: %v", err)
	}
	c.DrainReceived(200 * time.Millisecond)

	// Replay 2049 with a different name.
	payload := testclient.NewW().Str8("Squatter").U8(1).U8(1).U8(0).Bytes()
	if err := c.Send(2, testclient.OpCoachCreate, payload); err != nil {
		t.Fatalf("send: %v", err)
	}
	f, _, err := c.WaitFor(opCoachCreateResult, 3*time.Second)
	if err != nil {
		t.Fatalf("replayed 2049 got no 2050 refusal: %v", err)
	}
	if got := f.Payload[0]; got != coachResultInvalidOrDupe {
		t.Errorf("second coach creation returned %d, want %d - an account must "+
			"not be able to mint unlimited coaches", got, coachResultInvalidOrDupe)
	}
}

// dialNoCoach connects and authenticates but does NOT create a coach, leaving the
// session at the creation prompt so a test can submit its own 2049.
func dialNoCoach(t *testing.T, addr, login string) *testclient.Client {
	t.Helper()
	c, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("dial: %v", err)
	}
	t.Cleanup(func() { _ = c.Close() })
	if err := c.Login(login, "pw"); err != nil {
		t.Fatalf("login: %v", err)
	}
	return c
}

// sanitizeSub turns a subtest description into a usable account login.
func sanitizeSub(s string) string {
	out := make([]rune, 0, len(s))
	for _, r := range s {
		if r >= 'a' && r <= 'z' || r >= 'A' && r <= 'Z' || r >= '0' && r <= '9' {
			out = append(out, r)
		}
		if len(out) >= 12 {
			break
		}
	}
	return string(out)
}
