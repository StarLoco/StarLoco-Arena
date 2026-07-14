package botclient

import (
	"bufio"
	"bytes"
	"net"
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// fakeConn lets us capture what Send writes to the wire without a real
// socket. It implements net.Conn (reads always EOF-empty; only writes
// matter for these encoding tests).
type fakeConn struct {
	bytes.Buffer
}

func (f *fakeConn) Read([]byte) (int, error)         { return 0, nil }
func (f *fakeConn) Close() error                     { return nil }
func (f *fakeConn) LocalAddr() net.Addr              { return netAddr{} }
func (f *fakeConn) RemoteAddr() net.Addr             { return netAddr{} }
func (f *fakeConn) SetDeadline(time.Time) error      { return nil }
func (f *fakeConn) SetReadDeadline(time.Time) error  { return nil }
func (f *fakeConn) SetWriteDeadline(time.Time) error { return nil }

type netAddr struct{}

func (netAddr) Network() string { return "fake" }
func (netAddr) String() string  { return "fake" }

// decodeInboundFrame parses a client->server frame (5-byte header) the way
// the server's ReadInboundFrame does, returning opcode + payload.
func decodeInboundFrame(t *testing.T, b []byte) (protocol.RecvOpcode, []byte) {
	t.Helper()
	if len(b) < protocol.InboundHeaderSize {
		t.Fatalf("frame too short: %d bytes", len(b))
	}
	total := int(b[0])<<8 | int(b[1])
	if total != len(b) {
		t.Fatalf("totalSize %d != actual %d", total, len(b))
	}
	opcode := protocol.RecvOpcode(int(b[3])<<8 | int(b[4]))
	return opcode, b[protocol.InboundHeaderSize:]
}

// newTestClient wires a Client around an in-memory buffer for Send capture.
func newTestClient(buf *fakeConn) *Client {
	return &Client{conn: buf, w: bufio.NewWriter(buf)}
}

func TestWalk_EncodesPathWithoutCountPrefix(t *testing.T) {
	buf := &fakeConn{}
	c := newTestClient(buf)
	if err := c.Walk(Cell{X: 1, Y: 2, Z: 3}, Cell{X: 4, Y: 5, Z: 6}); err != nil {
		t.Fatal(err)
	}
	op, payload := decodeInboundFrame(t, buf.Bytes())
	if op != protocol.RecvActorMovementRequest {
		t.Fatalf("opcode = %d, want ACTOR_MOVEMENT_REQUEST", op)
	}
	// Two cells * 10 bytes = 20, no count prefix.
	if len(payload) != 20 {
		t.Fatalf("payload len = %d, want 20", len(payload))
	}
	r := protocol.NewReader(payload)
	if r.Int32() != 1 || r.Int32() != 2 || r.Int16() != 3 {
		t.Fatal("first cell mis-encoded")
	}
	if r.Int32() != 4 || r.Int32() != 5 || r.Int16() != 6 {
		t.Fatal("second cell mis-encoded")
	}
}

func TestSayVicinity_Uses2ByteLengthPrefix(t *testing.T) {
	buf := &fakeConn{}
	c := newTestClient(buf)
	if err := c.SayVicinity("hello"); err != nil {
		t.Fatal(err)
	}
	op, payload := decodeInboundFrame(t, buf.Bytes())
	if op != protocol.RecvVicinityMessage {
		t.Fatalf("opcode = %d, want VICINITY_MESSAGE", op)
	}
	r := protocol.NewReader(payload)
	if got := r.StringShort(); got != "hello" {
		t.Fatalf("message = %q, want hello", got)
	}
}

func TestCastSpell_Encodes22Bytes(t *testing.T) {
	buf := &fakeConn{}
	c := newTestClient(buf)
	if err := c.CastSpell(fighterWire(1), 500, Cell{X: 7, Y: 8, Z: 9}); err != nil {
		t.Fatal(err)
	}
	op, payload := decodeInboundFrame(t, buf.Bytes())
	if op != protocol.RecvSpellCastRequest {
		t.Fatalf("opcode = %d, want SPELL_CAST_REQUEST", op)
	}
	if len(payload) != 22 {
		t.Fatalf("payload len = %d, want 22", len(payload))
	}
	r := protocol.NewReader(payload)
	if r.Int64() != fighterWire(1) {
		t.Fatal("fighter id mis-encoded")
	}
	if r.Int32() != 500 {
		t.Fatal("spell id mis-encoded")
	}
	if r.Int32() != 7 || r.Int32() != 8 || r.Int16() != 9 {
		t.Fatal("target cell mis-encoded")
	}
}

func TestEndTurn_Encodes8Bytes(t *testing.T) {
	buf := &fakeConn{}
	c := newTestClient(buf)
	if err := c.EndTurn(fighterWire(42)); err != nil {
		t.Fatal(err)
	}
	op, payload := decodeInboundFrame(t, buf.Bytes())
	if op != protocol.RecvFighterEndTurnRequest {
		t.Fatalf("opcode = %d, want FIGHTER_END_TURN_REQUEST", op)
	}
	if len(payload) != 8 {
		t.Fatalf("payload len = %d, want 8", len(payload))
	}
}

func TestExchangeAddCard_Encodes18Bytes(t *testing.T) {
	buf := &fakeConn{}
	c := newTestClient(buf)
	if err := c.ExchangeAddCard(1234, 5678, 3); err != nil {
		t.Fatal(err)
	}
	op, payload := decodeInboundFrame(t, buf.Bytes())
	if op != protocol.RecvItemExchangeAddCard {
		t.Fatalf("opcode = %d, want ITEM_EXCHANGE_ADD_CARD", op)
	}
	if len(payload) != 18 {
		t.Fatalf("payload len = %d, want 18", len(payload))
	}
	r := protocol.NewReader(payload)
	if r.Int64() != 1234 || r.Int64() != 5678 || r.Int16() != 3 {
		t.Fatal("add-card fields mis-encoded")
	}
}

func fighterWire(dbID int64) int64 { return 1_000_000_000 + dbID }
