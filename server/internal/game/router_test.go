package game

import (
	"log/slog"
	"testing"
)

// TestRegisterAllNoDuplicates ensures every handler group registers without a
// duplicate-opcode panic (the router panics on dup registration).
func TestRegisterAllNoDuplicates(t *testing.T) {
	defer func() {
		if r := recover(); r != nil {
			t.Fatalf("RegisterAll panicked (duplicate opcode?): %v", r)
		}
	}()
	r := NewRouter(slog.Default())
	RegisterAll(r, &Deps{})
	if len(r.handlers) == 0 {
		t.Fatal("no handlers registered")
	}
	t.Logf("registered %d handlers", len(r.handlers))
}
