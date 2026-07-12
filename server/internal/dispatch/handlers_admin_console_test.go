package dispatch

import (
	"net"
	"sync"
	"testing"

	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// capturedSession pairs a live *netio.Session with a slice collecting every
// frame it Sends, via the global outbound tracer. Not safe for parallel
// tests (the tracer is process-global), so these tests avoid t.Parallel().
type capturedSession struct {
	session *netio.Session
	mu      sync.Mutex
	frames  []protocol.OutboundFrame
}

// newCapturedSession builds a session over an in-memory pipe and installs a
// tracer that records only this session's outbound frames. The returned
// cleanup func uninstalls the tracer and closes the pipe.
func newCapturedSession(t *testing.T) (*capturedSession, func()) {
	t.Helper()
	client, server := net.Pipe()
	cs := &capturedSession{session: netio.NewSession(server)}
	id := cs.session.ID()
	netio.SetOutboundTracer(func(sessionID uint64, frame protocol.OutboundFrame) {
		if sessionID != id {
			return
		}
		cs.mu.Lock()
		cs.frames = append(cs.frames, frame)
		cs.mu.Unlock()
	})
	return cs, func() {
		netio.SetOutboundTracer(nil)
		_ = client.Close()
		_ = server.Close()
	}
}

func (cs *capturedSession) sent() []protocol.OutboundFrame {
	cs.mu.Lock()
	defer cs.mu.Unlock()
	out := make([]protocol.OutboundFrame, len(cs.frames))
	copy(out, cs.frames)
	return out
}

// encodeConsoleCommand builds a CONSOLE_ADMIN_COMMAND payload (a single
// 1-byte length-prefixed string), matching the client's encode().
func encodeConsoleCommand(command string) *protocol.Reader {
	w := protocol.NewWriter(1 + len(command))
	w.PutString(command)
	return protocol.NewReader(w.Bytes())
}

func adminTestDeps() *Deps {
	return &Deps{
		Server: config.ServerConfig{
			Version: config.VersionConfig{Major: 2, Revision: 4, Build: "7025"},
		},
		World: world.NewRegistry(),
	}
}

// TestBuildConsoleAdminResult verifies CONSOLE_ADMIN_COMMAND_RESULT (8194)
// serializes as a type byte followed by a 2-byte length-prefixed message,
// matching ConsoleAdminCommandResultMessage.decode.
func TestBuildConsoleAdminResult(t *testing.T) {
	frame := buildConsoleAdminResult(protocol.AdminResultError, "nope")
	if frame.Opcode != protocol.SendConsoleAdminCommandResult {
		t.Fatalf("opcode = %v, want SendConsoleAdminCommandResult", frame.Opcode)
	}
	r := protocol.NewReader(frame.Payload)
	if got := r.Byte(); got != byte(protocol.AdminResultError) {
		t.Errorf("messageType = %d, want %d", got, protocol.AdminResultError)
	}
	if got := r.StringShort(); got != "nope" {
		t.Errorf("message = %q, want %q", got, "nope")
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	if r.Remaining() != 0 {
		t.Errorf("payload has %d trailing bytes, want 0", r.Remaining())
	}
}

// TestBuildDefaultResult verifies DEFAULT_RESULT (8195) is exactly a 4-byte
// big-endian int (QueryResultsResultsMessage.decode reads one int).
func TestBuildDefaultResult(t *testing.T) {
	frame := buildDefaultResult(0)
	if frame.Opcode != protocol.SendDefaultResult {
		t.Fatalf("opcode = %v, want SendDefaultResult", frame.Opcode)
	}
	if len(frame.Payload) != 4 {
		t.Fatalf("payload len = %d, want 4", len(frame.Payload))
	}
	r := protocol.NewReader(frame.Payload)
	if got := r.Int32(); got != 0 {
		t.Errorf("code = %d, want 0", got)
	}
}

// TestConsoleAdminCommandRejectsNonAdmin verifies a non-admin session gets
// an ERROR line and a non-zero DEFAULT_RESULT, and that no command runs.
func TestConsoleAdminCommandRejectsNonAdmin(t *testing.T) {
	cs, cleanup := newCapturedSession(t)
	defer cleanup()
	cs.session.SetAccount(&netio.AccountRef{AccountID: 1, IsAdmin: false})

	handleConsoleAdminCommand(cs.session, encodeConsoleCommand("PING"), adminTestDeps())

	frames := cs.sent()
	if len(frames) != 2 {
		t.Fatalf("got %d frames, want 2 (error line + default result)", len(frames))
	}
	if frames[0].Opcode != protocol.SendConsoleAdminCommandResult {
		t.Errorf("frame[0] opcode = %v, want result", frames[0].Opcode)
	}
	if frames[0].Payload[0] != byte(protocol.AdminResultError) {
		t.Errorf("frame[0] type = %d, want ERROR", frames[0].Payload[0])
	}
	if code := protocol.NewReader(frames[1].Payload).Int32(); code == 0 {
		t.Errorf("default result code = 0, want non-zero for a rejected command")
	}
}

// TestConsoleAdminCommandPing verifies an admin PING yields a TRACE "pong"
// followed by a success DEFAULT_RESULT (0).
func TestConsoleAdminCommandPing(t *testing.T) {
	cs, cleanup := newCapturedSession(t)
	defer cleanup()
	cs.session.SetAccount(&netio.AccountRef{AccountID: 1, IsAdmin: true})

	handleConsoleAdminCommand(cs.session, encodeConsoleCommand("ping"), adminTestDeps())

	frames := cs.sent()
	if len(frames) != 2 {
		t.Fatalf("got %d frames, want 2 (trace + default result)", len(frames))
	}
	r := protocol.NewReader(frames[0].Payload)
	if got := r.Byte(); got != byte(protocol.AdminResultTrace) {
		t.Errorf("type = %d, want TRACE", got)
	}
	if got := r.StringShort(); got != "pong" {
		t.Errorf("message = %q, want %q", got, "pong")
	}
	if code := protocol.NewReader(frames[1].Payload).Int32(); code != 0 {
		t.Errorf("default result code = %d, want 0", code)
	}
}

// TestConsoleAdminCommandUnknown verifies an unknown verb from an admin
// returns an ERROR line and a non-zero completion code.
func TestConsoleAdminCommandUnknown(t *testing.T) {
	cs, cleanup := newCapturedSession(t)
	defer cleanup()
	cs.session.SetAccount(&netio.AccountRef{AccountID: 1, IsAdmin: true})

	handleConsoleAdminCommand(cs.session, encodeConsoleCommand("frobnicate"), adminTestDeps())

	frames := cs.sent()
	if len(frames) != 2 {
		t.Fatalf("got %d frames, want 2", len(frames))
	}
	if frames[0].Payload[0] != byte(protocol.AdminResultError) {
		t.Errorf("type = %d, want ERROR", frames[0].Payload[0])
	}
	if code := protocol.NewReader(frames[1].Payload).Int32(); code == 0 {
		t.Errorf("default result code = 0, want non-zero for unknown command")
	}
}
