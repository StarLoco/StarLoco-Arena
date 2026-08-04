package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestChannelMessageBroadcast: a channel line (3151) is delivered to every
// online coach as ChannelContent(3140) carrying [channel][sender][message].
func TestChannelMessageBroadcast(t *testing.T) {
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "chan_a", "ChanA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "chan_b", "ChanB")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// A sends on channel "*": [u8 channelLen][channel][u8 msgLen][message].
	p := testclient.NewW().Str8("*").Str8("hello channel").Bytes()
	_ = a.Send(4, testclient.OpUserChannelContent, p)

	// B receives it.
	f, _, err := b.WaitFor(testclient.OpChannelContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B never got channel content: %v", err)
	}
	// Layout: [u8 chanLen][channel][u8 senderLen][sender][u8 msgLen][message].
	r := testclient.NewR(f.Payload)
	if ch := r.Str8(); ch != "*" {
		t.Errorf("channel = %q, want *", ch)
	}
	if sender := r.Str8(); sender != "ChanA" {
		t.Errorf("sender = %q, want ChanA", sender)
	}
	if msg := r.Str8(); msg != "hello channel" {
		t.Errorf("message = %q, want 'hello channel'", msg)
	}

	// The sender must NOT receive its own channel line back (the client shows
	// the outgoing message locally; a server echo would duplicate it).
	for _, fr := range a.DrainReceived(250 * time.Millisecond) {
		if fr.Opcode == testclient.OpChannelContent {
			t.Fatal("sender received its own channel message back (would show twice)")
		}
	}
}

// TestChannelMessageEmptyIgnored: a whitespace-only channel line is dropped
// (no 3140 emitted).
func TestChannelMessageEmptyIgnored(t *testing.T) {
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "chan_c", "ChanC")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "chan_d", "ChanD")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	_ = a.Send(4, testclient.OpUserChannelContent, testclient.NewW().Str8("*").Str8("   ").Bytes())

	for _, f := range b.DrainReceived(300 * time.Millisecond) {
		if f.Opcode == testclient.OpChannelContent {
			t.Fatal("empty channel message should not be broadcast")
		}
	}
}
