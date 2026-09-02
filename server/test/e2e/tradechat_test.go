package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// The Trade pipe — the client's "/t". Confirmed live before implementing: typing
// /t sends opcode 3159 (arch 3) and the server used to log "unhandled opcode" and
// drop it, while the client rendered the player's own line locally so the message
// looked sent and simply never arrived.
const (
	opUserTradeContent = 3159 // C2S: [u16 len][message]
	opTradeContent     = 3168 // S2C: [u8 len]sender [i64 id] [u16 len]message
)

func tradePayload(msg string) []byte {
	return testclient.NewW().StrU16(msg).Bytes()
}

// TestTradeMessageReachesOtherPlayers: A types in Trade, B receives it, with the
// sender name and body intact.
func TestTradeMessageReachesOtherPlayers(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "trade_a", "TradeA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "trade_b", "TradeB")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserTradeContent, tradePayload("selling a dofus"))

	f, _, err := b.WaitFor(opTradeContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no TradeContent(3168): the Trade pipe is dropped again: %v", err)
	}
	r := testclient.NewR(f.Payload)
	if name := r.Str8(); name != "TradeA" {
		t.Errorf("sender = %q, want %q", name, "TradeA")
	}
	r.I64() // sender id
	if msg := r.StrU16(); msg != "selling a dofus" {
		t.Errorf("message = %q, want %q", msg, "selling a dofus")
	}
}

// TestTradeMessageIsNotEchoedToSender: the client prints its own outgoing line, so
// echoing it back would show the sender's message twice.
func TestTradeMessageIsNotEchoedToSender(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "trade_c", "TradeC")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "trade_d", "TradeD")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserTradeContent, tradePayload("echo check"))

	// B must get it — that also gives the server time to have echoed, if it did.
	if _, _, err := b.WaitFor(opTradeContent, testclient.DefaultTimeout); err != nil {
		t.Fatalf("B did not receive the trade line: %v", err)
	}
	for _, fr := range a.DrainReceived(300 * time.Millisecond) {
		if fr.Opcode == opTradeContent {
			t.Error("the sender was echoed its own trade line; the client already " +
				"displays it locally, so it would appear twice")
		}
	}
}

// TestTradeMessageEmptyIgnored: whitespace-only lines are dropped rather than
// broadcast as blank rows.
func TestTradeMessageEmptyIgnored(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "trade_e", "TradeE")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "trade_f", "TradeF")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserTradeContent, tradePayload("   "))

	for _, fr := range b.DrainReceived(400 * time.Millisecond) {
		if fr.Opcode == opTradeContent {
			t.Error("a whitespace-only trade line was broadcast")
		}
	}
}

// TestTradeMessageAcceptsAccents guards the wire charset: the body length is a
// u16 counted in ENCODED (cp1252) bytes, so writing raw UTF-8 would both mangle
// the text and desynchronise the length from the payload.
func TestTradeMessageAcceptsAccents(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "trade_g", "TradeG")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "trade_h", "TradeH")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	const line = "vends épée à 10 kamas"
	_ = a.Send(3, opUserTradeContent, tradePayload(line))

	f, _, err := b.WaitFor(opTradeContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no TradeContent: %v", err)
	}
	r := testclient.NewR(f.Payload)
	r.Str8()
	r.I64()
	if msg := r.StrU16(); msg != line {
		t.Errorf("message = %q, want %q", msg, line)
	}
}
