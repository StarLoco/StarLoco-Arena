package config

import "testing"

// TestDebugAddrMustBeLoopback pins the control that replaced a comment.
//
// SECURITY: the packet-inject endpoint's own doc said it "must only ever bind to
// loopback" and nothing enforced it, while ARENA_DEBUG_ADDR makes binding
// 0.0.0.0 the natural Docker mistake. It is unauthenticated and can dispatch ANY
// opcode as ANY logged-in player, push arbitrary frames to every client, drive
// fight scenarios and enumerate sessions.
func TestDebugAddrMustBeLoopback(t *testing.T) {
	reject := []string{
		":5599",             // every interface
		"0.0.0.0:5599",      // every interface, explicitly
		"192.168.1.10:5599", // a LAN address
		"example.com:5599",  // a name that is not localhost
		"5599",              // not host:port at all
	}
	for _, addr := range reject {
		if err := validateDebugAddr(addr); err == nil {
			t.Errorf("validateDebugAddr(%q) accepted a non-loopback debug endpoint", addr)
		}
	}

	accept := []string{
		"",               // disabled - the default and the right production value
		"127.0.0.1:5599", // loopback v4
		"localhost:5599", // loopback by name
		"[::1]:5599",     // loopback v6
	}
	for _, addr := range accept {
		if err := validateDebugAddr(addr); err != nil {
			t.Errorf("validateDebugAddr(%q) rejected a legitimate value: %v", addr, err)
		}
	}
}

// TestValidateRejectsNonLoopbackDebugAddr proves the check is wired into the
// config validation the server actually runs, not merely available.
func TestValidateRejectsNonLoopbackDebugAddr(t *testing.T) {
	c := Config{
		Addr:      "0.0.0.0:5555",
		DebugAddr: "0.0.0.0:5599",
		DB:        DBConfig{Driver: "sqlite", DSN: "arena.db"},
	}
	if err := c.validate(); err == nil {
		t.Error("Config.validate accepted a world-reachable debug_addr")
	}
	c.DebugAddr = "127.0.0.1:5599"
	if err := c.validate(); err != nil {
		t.Errorf("Config.validate rejected a loopback debug_addr: %v", err)
	}
}
