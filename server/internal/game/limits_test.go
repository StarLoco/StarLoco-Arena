package game

import (
	"net"
	"testing"
	"time"
)

// TestConnGuardCaps covers the connection limits that did not exist: the accept
// loop took every socket unconditionally, so 10k connections was free.
func TestConnGuardCaps(t *testing.T) {
	t.Run("per-IP cap", func(t *testing.T) {
		g := newConnGuard(Limits{MaxConnsPerIP: 2})
		if ok, _ := g.acquire("1.2.3.4"); !ok {
			t.Fatal("first connection must be allowed")
		}
		if ok, _ := g.acquire("1.2.3.4"); !ok {
			t.Fatal("second connection must be allowed")
		}
		if ok, reason := g.acquire("1.2.3.4"); ok {
			t.Errorf("third connection from one IP allowed (reason=%q)", reason)
		}
		// A DIFFERENT address must be unaffected, or one abuser locks out everyone.
		if ok, _ := g.acquire("5.6.7.8"); !ok {
			t.Error("a different address was blocked by another address's usage")
		}
	})

	t.Run("global cap", func(t *testing.T) {
		g := newConnGuard(Limits{MaxConns: 2})
		g.acquire("1.1.1.1")
		g.acquire("2.2.2.2")
		if ok, _ := g.acquire("3.3.3.3"); ok {
			t.Error("global connection cap not enforced")
		}
	})

	t.Run("release frees a slot and does not leak map entries", func(t *testing.T) {
		g := newConnGuard(Limits{MaxConnsPerIP: 1})
		g.acquire("9.9.9.9")
		g.release("9.9.9.9")
		if ok, _ := g.acquire("9.9.9.9"); !ok {
			t.Error("slot was not released")
		}
		g.release("9.9.9.9")
		g.mu.Lock()
		n := len(g.perIP)
		g.mu.Unlock()
		if n != 0 {
			t.Errorf("perIP still holds %d entries after full release - a "+
				"long-running server would accumulate one per address ever seen", n)
		}
	})

	t.Run("zero value is permissive", func(t *testing.T) {
		g := newConnGuard(Limits{})
		for i := 0; i < 100; i++ {
			if ok, _ := g.acquire("1.2.3.4"); !ok {
				t.Fatalf("zero-value Limits blocked connection %d; it must be inert", i)
			}
		}
		if !g.limits.AutoRegisterAllowed() || !g.limits.FirstAccountAdminAllowed() {
			t.Error("zero-value Limits must not silently disable auto-registration " +
				"or first-account-admin - that broke every e2e test when the flags " +
				"were phrased positively")
		}
	})
}

// TestLoginRateLimit covers the bcrypt DoS: each 1025 costs ~50-100ms of CPU on
// the session goroutine, so an unthrottled login was the cheapest way to
// saturate every core, and password guessing had no limit either.
func TestLoginRateLimit(t *testing.T) {
	now := time.Now()
	g := newConnGuard(Limits{LoginRatePerMin: 3})
	g.now = func() time.Time { return now }

	for i := 0; i < 3; i++ {
		if !g.allowLogin("1.2.3.4") {
			t.Fatalf("attempt %d should be allowed inside the budget", i+1)
		}
	}
	if g.allowLogin("1.2.3.4") {
		t.Error("4th attempt in the same minute was allowed")
	}
	// Another address keeps its own budget.
	if !g.allowLogin("5.6.7.8") {
		t.Error("a different address was throttled by another address's attempts")
	}
	// The window slides.
	now = now.Add(61 * time.Second)
	if !g.allowLogin("1.2.3.4") {
		t.Error("attempts did not expire after a minute")
	}

	// And the bookkeeping is swept, or a long-running server leaks a map key per
	// address that ever tried to log in.
	now = now.Add(10 * time.Minute)
	g.sweepLoginHits()
	g.mu.Lock()
	n := len(g.loginHit)
	g.mu.Unlock()
	if n != 0 {
		t.Errorf("loginHit still holds %d stale entries after a sweep", n)
	}
}

// TestHostOfNeverReturnsEmptyForRealAddresses guards the per-IP key: if hostOf
// collapsed different addresses to one value the cap would be global, and if it
// returned "" for odd input an attacker could pick that bucket deliberately.
func TestHostOfNeverReturnsEmptyForRealAddresses(t *testing.T) {
	cases := []net.Addr{
		&net.TCPAddr{IP: net.ParseIP("1.2.3.4"), Port: 5555},
		&net.TCPAddr{IP: net.ParseIP("::1"), Port: 5555},
	}
	seen := map[string]bool{}
	for _, a := range cases {
		h := hostOf(a)
		if h == "" {
			t.Errorf("hostOf(%v) = empty", a)
		}
		if seen[h] {
			t.Errorf("hostOf collapsed a distinct address onto %q", h)
		}
		seen[h] = true
	}
	if hostOf(nil) != "" {
		t.Error("hostOf(nil) should be empty rather than panicking or inventing a key")
	}
}
