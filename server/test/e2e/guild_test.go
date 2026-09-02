package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// utf8U8 writes a [u8 len][bytes] UTF-8 string, the encoding the whole guild
// family uses (client aey_0). Written by hand here rather than reusing the
// server's writer so the test states the wire form independently.
func utf8U8(w *testclient.W, s string) *testclient.W {
	b := []byte(s)
	w.U8(uint8(len(b)))
	w.Raw(b)
	return w
}

func readU8String(r *testclient.R) string {
	n := int(r.U8())
	return string(r.RawN(n))
}

// createGuild drives 509 and waits for the 504 result.
func createGuild(t *testing.T, c *testclient.Client, name string) int32 {
	t.Helper()
	w := testclient.NewW().U8(2)
	utf8U8(w, name)
	// 509 is the one guild message the client sends with arch 3.
	_ = c.Send(3, testclient.OpGuildCreate, w.Bytes())
	f, _, err := c.WaitFor(testclient.OpGuildResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 504 after create: %v", err)
	}
	r := testclient.NewR(f.Payload)
	_ = r.U8() // type
	return r.I32()
}

// TestGuildCreateInviteAcceptAndClanChat walks the whole reachable flow over a
// real socket: create a clan, invite a second coach, accept, and exchange a line
// on /c. Each step is one the retail client actually performs - creation from the
// Pacte card dialog, invitation from the social tab, acceptance from the popup,
// and clan chat from the `/c` pipe.
func TestGuildCreateInviteAcceptAndClanChat(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "guild_a", "Chef")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "guild_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "Les Bouftous"); code != 403 {
		t.Fatalf("create result = %d, want 403 (created)", code)
	}

	// The creator must receive its own membership (552) - that message is what
	// sets `aPY()` mid-session, and without it the client keeps believing it has
	// no clan even though the server just made one.
	if _, _, err := a.WaitFor(testclient.OpGuildMembership, testclient.DefaultTimeout); err != nil {
		t.Fatalf("creator got no 552 membership: %v", err)
	}

	// Invite by name (mode 0), the path the social tab's text field uses.
	w := testclient.NewW().U8(2).U8(0)
	utf8U8(w, "Recrue")
	w.I64(0) // the client's believed guild id; the server re-derives it
	_ = a.Send(2, testclient.OpGuildInvite, w.Bytes())

	inv, _, err := b.WaitFor(testclient.OpGuildInvitation, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("invitee got no 502: %v", err)
	}
	ir := testclient.NewR(inv.Payload)
	_ = ir.U8()
	gotInviter := readU8String(ir)
	gotGuild := readU8String(ir)
	if gotInviter != "Chef" || gotGuild != "Les Bouftous" {
		t.Fatalf("invitation = inviter %q guild %q, want Chef / Les Bouftous", gotInviter, gotGuild)
	}

	// Accept, echoing both names exactly as the client does.
	aw := testclient.NewW().U8(2).U8(1)
	utf8U8(aw, gotInviter)
	utf8U8(aw, gotGuild)
	_ = b.Send(2, testclient.OpGuildInviteAnswer, aw.Bytes())

	f, _, err := b.WaitFor(testclient.OpGuildResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("joiner got no 504: %v", err)
	}
	rr := testclient.NewR(f.Payload)
	_ = rr.U8()
	if code := rr.I32(); code != 404 {
		t.Fatalf("join result = %d, want 404 (accepted)", code)
	}

	// The member list must now hold both coaches.
	mf, _, err := b.WaitFor(testclient.OpGuildMembers, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("joiner got no 512: %v", err)
	}
	// Assert the CONTENT, not just the count: the client keys off the part index
	// inside each blob, so a list sent with the wrong index still has the right
	// number of entries and decodes into entirely the wrong fields.
	mr := testclient.NewR(mf.Payload)
	n := mr.I32()
	if n != 2 {
		t.Fatalf("member list has %d entries, want 2", n)
	}
	seen := map[string]bool{}
	for i := int32(0); i < n; i++ {
		blob := mr.RawN(int(mr.I32()))
		if blob[0] != 1 {
			t.Fatalf("entry %d: partCount = %d, want 1", i, blob[0])
		}
		if blob[1] != 0 {
			t.Fatalf("entry %d: part index = %d, want 0 (uy_2 member row)", i, blob[1])
		}
		off := int(uint32(blob[2])<<24 | uint32(blob[3])<<16 | uint32(blob[4])<<8 | uint32(blob[5]))
		p := testclient.NewR(blob[off+1:])
		_ = p.I64() // memberId
		_ = p.I32() // rights
		_ = p.U16() // rankLevel
		_ = readU8String(p)
		seen[readU8String(p)] = true
	}
	if !seen["Chef"] || !seen["Recrue"] {
		t.Errorf("member names = %v, want both Chef and Recrue", seen)
	}

	// Clan chat: B speaks, A hears. This is the pipe that emitted nothing at all
	// before guilds existed.
	a.DrainReceived(150 * time.Millisecond)
	msg := "salut le clan"
	cw := testclient.NewW()
	cw.U16(uint16(len(msg)))
	cw.Raw([]byte(msg))
	cw.I64(1) // guild id, re-validated server-side
	_ = b.Send(2, testclient.OpUserClanContent, cw.Bytes())

	cf, _, err := a.WaitFor(testclient.OpClanContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("clan chat did not reach the other member: %v", err)
	}
	if len(cf.Payload) == 0 {
		t.Error("empty clan chat frame")
	}
}

// TestGuildCreateRejectsAShortName: the creation dialog will not submit fewer
// than five characters, so a shorter one arriving means a modified client. It
// must be refused with the same code as a taken name, which is the only "bad
// name" code the client has.
func TestGuildCreateRejectsAShortName(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "guild_short", "Court")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "abc"); code != 11 {
		t.Errorf("short-name result = %d, want 11 (invalid or taken)", code)
	}
}

// TestGuildNameCannotBeTakenTwice pins the uniqueness the client's single error
// string implies.
func TestGuildNameCannotBeTakenTwice(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "guild_x", "Uno")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "guild_y", "Deux")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "Doublon"); code != 403 {
		t.Fatalf("first create = %d, want 403", code)
	}
	if code := createGuild(t, b, "Doublon"); code != 11 {
		t.Errorf("duplicate name = %d, want 11", code)
	}
}

// TestClanChatIsScopedToTheGuild: a coach in a different clan must not receive
// it. Chat scoping is the kind of thing that looks fine in a two-player test and
// leaks in a three-player one.
func TestClanChatIsScopedToTheGuild(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "clan_a", "AlphaChef")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "clan_b", "BetaChef")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanAlpha"); code != 403 {
		t.Fatalf("A create = %d", code)
	}
	if code := createGuild(t, b, "ClanBeta"); code != 403 {
		t.Fatalf("B create = %d", code)
	}
	b.DrainReceived(200 * time.Millisecond)

	msg := "secret"
	cw := testclient.NewW()
	cw.U16(uint16(len(msg)))
	cw.Raw([]byte(msg))
	cw.I64(1)
	_ = a.Send(2, testclient.OpUserClanContent, cw.Bytes())

	if _, _, err := b.WaitFor(testclient.OpClanContent, 400*time.Millisecond); err == nil {
		t.Error("a coach in a DIFFERENT clan received the message")
	}
}
