package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Chat safety over a real socket. The unit tests in internal/game cover the
// helpers; these exist because a helper cannot tell you whether the CALL SITE
// uses it — which is the mistake this project keeps making.
const (
	opAddIgnore      = 3131 // C2S: [u8 len]name
	opIgnoreAdded    = 3158 // S2C ack
	opUserPrivate    = 3155 // C2S: [u8 len]target [u8 len]msg
	opPrivateContent = 3154 // S2C
	opUserNotFound   = 3204 // S2C

	// Chat error signals - empty payload, the opcode IS the message (om_0 renders
	// a fixed i18n string and ignores the body).
	opChatErrTargetIsYourself = 3214 // S2C
	opChatErrNotEnoughRights  = 3210 // S2C
	opChatErrMalformedCommand = 3206 // S2C
	opServerMessage           = 2070 // S2C: [i32 len][cp1252 message]
)

// ignore makes `by` ignore `name`, over the wire, and waits for the ack — so the
// in-memory edge list really is updated mid-session (it is loaded once at login,
// and chat filtering reads it per message).
func ignore(t *testing.T, by *testclient.Client, name string) {
	t.Helper()
	_ = by.Send(4, opAddIgnore, testclient.NewW().Str8(name).Bytes())
	if _, _, err := by.WaitFor(opIgnoreAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no IgnoreAdded ack for %q: %v", name, err)
	}
}

// TestIgnoredWhisperIsDropped is the one that matters most. The client does NOT
// filter private messages by sender — `om_0` case 3154 has no ignore check — and
// receiving one force-maximises and force-opens the chat window. Without server
// filtering, an ignored player can pop the recipient's UI at will.
func TestIgnoredWhisperIsDropped(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "ign_a", "IgnA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "ign_b", "IgnB")
	reachWorld(t, b)

	ignore(t, a, "IgnB") // A ignores B
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = b.Send(4, opUserPrivate, testclient.NewW().Str8("IgnA").Str8("hello").Bytes())

	// B is told the same thing it would be told for an offline target, so being
	// ignored is not disclosed.
	if _, _, err := b.WaitFor(opUserNotFound, testclient.DefaultTimeout); err != nil {
		t.Errorf("sender got no UserNotFound for an ignored target: %v", err)
	}
	for _, fr := range a.DrainReceived(400 * time.Millisecond) {
		if fr.Opcode == opPrivateContent {
			t.Fatal("an IGNORED player's whisper was delivered — the client has no " +
				"filter for 3154 and would pop the chat window open")
		}
	}
}

// TestIgnoredTradeLineIsDropped: the same edge must filter a broadcast pipe, and
// must not affect anybody else.
func TestIgnoredTradeLineIsDropped(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "ign_c", "IgnC")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "ign_d", "IgnD")
	reachWorld(t, b)
	c, _ := dialLogin(t, addr, "ign_e", "IgnE")
	reachWorld(t, c)

	ignore(t, a, "IgnD") // A ignores B
	for _, cl := range []*testclient.Client{a, b, c} {
		cl.DrainReceived(200 * time.Millisecond)
	}

	_ = b.Send(3, opUserTradeContent, tradePayload("wtb dofus"))

	// The uninvolved third player still receives it...
	if _, _, err := c.WaitFor(opTradeContent, testclient.DefaultTimeout); err != nil {
		t.Fatalf("an uninvolved player did not receive the trade line: %v", err)
	}
	// ...and the ignorer does not.
	for _, fr := range a.DrainReceived(300 * time.Millisecond) {
		if fr.Opcode == opTradeContent {
			t.Error("an ignored player's trade line was delivered")
		}
	}
}

// TestIgnoredVicinityLineIsDropped: General is filtered client-side too, so this
// is defence in depth — but it costs nothing and a modified client is exactly
// what an ignore list is for.
func TestIgnoredVicinityLineIsDropped(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "ign_f", "IgnF")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "ign_g", "IgnG")
	reachWorld(t, b)

	ignore(t, a, "IgnG")
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = b.Send(3, opUserVicinity, testclient.NewW().StrU16("hello there").Bytes())

	for _, fr := range a.DrainReceived(400 * time.Millisecond) {
		if fr.Opcode == opVicinity {
			t.Error("an ignored player's general line was delivered")
		}
	}
}

// TestChatMarkupIsStrippedOnTheWire: the client renders chat as markup with no
// escaping, so '<' from an untrusted source is a live tag. The stock client's
// input widget strips them; a modified one would not, so the server must.
func TestChatMarkupIsStrippedOnTheWire(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "mk_a", "MkA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "mk_b", "MkB")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserTradeContent, tradePayload("<c=FF0000>free kamas</c>"))

	f, _, err := b.WaitFor(opTradeContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no trade line: %v", err)
	}
	r := testclient.NewR(f.Payload)
	r.Str8()
	r.I64()
	got := r.StrU16()
	for _, bad := range []string{"<", ">"} {
		if containsRune(got, bad) {
			t.Errorf("relayed body %q still contains %q — markup injection is live", got, bad)
		}
	}
	if got != "c=FF0000free kamas/c" {
		t.Errorf("relayed body = %q, want the same text with < and > removed", got)
	}
}

// TestTradeCooldownIsEnforcedOverTheWire: the client throttles Trade to one line
// per 30 s, so a second line arriving immediately can only come from a modified
// client. The server must not relay it.
func TestTradeCooldownIsEnforcedOverTheWire(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "cd_a", "CdA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "cd_b", "CdB")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserTradeContent, tradePayload("first line here"))
	if _, _, err := b.WaitFor(opTradeContent, testclient.DefaultTimeout); err != nil {
		t.Fatalf("the first trade line did not arrive: %v", err)
	}

	_ = a.Send(3, opUserTradeContent, tradePayload("second line here"))
	for _, fr := range b.DrainReceived(400 * time.Millisecond) {
		if fr.Opcode == opTradeContent {
			t.Error("a second trade line was relayed inside the 30s cooldown the " +
				"client itself enforces")
		}
	}
}

func containsRune(s, sub string) bool {
	for i := 0; i+len(sub) <= len(s); i++ {
		if s[i:i+len(sub)] == sub {
			return true
		}
	}
	return false
}

// TestWhisperToYourselfIsRefusedVisibly: the client has a dedicated error for
// whispering yourself ("error.chat.targetIsYourself"), and before this the server
// simply relayed the message straight back - so the player saw their own whisper
// arrive and no explanation.
//
// 3214 carries an EMPTY payload: om_0 discards the decoded body and renders a
// fixed string, so the opcode is the whole message.
func TestWhisperToYourselfIsRefusedVisibly(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "self_w", "SelfW")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	p := testclient.NewW().Str8("SelfW").Str8("hello me").Bytes()
	_ = a.Send(4, opUserPrivate, p)

	f, _, err := a.WaitFor(opChatErrTargetIsYourself, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 3214 for a self-whisper: the client shows nothing at all: %v", err)
	}
	if n := len(f.Payload); n != 0 {
		t.Errorf("3214 payload = %d bytes, want 0 (om_0 ignores the body)", n)
	}
}

// TestWhisperToSomeoneElseStillWorks guards the obvious over-correction: the
// self-check must compare against the SENDER's own name, not refuse every
// whisper.
func TestWhisperToSomeoneElseStillWorks(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "w_from", "WFrom")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "w_to", "WTo")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	p := testclient.NewW().Str8("WTo").Str8("hi there").Bytes()
	_ = a.Send(4, opUserPrivate, p)
	if _, _, err := b.WaitFor(opPrivateContent, testclient.DefaultTimeout); err != nil {
		t.Fatalf("a normal whisper was not delivered: %v", err)
	}
}

// TestNonAdminGMCommandGetsThePrivilegeError: a '/'-command from a normal account
// used to get an invented English private message from "Server". The client has
// its own localised string for exactly this, so send that instead.
func TestNonAdminGMCommandGetsThePrivilegeError(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	// The FIRST account on a fresh database is made admin (handlers_connection.go),
	// and every test gets a fresh database - so burn one login before testing the
	// non-admin path, or the "non-admin" is silently an admin.
	first, _ := dialLogin(t, addr, "adm_first", "AdmFirst")
	reachWorld(t, first)
	a, _ := dialLogin(t, addr, "nonadm", "NonAdm")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserVicinity, testclient.NewW().StrU16("/WHERE").Bytes())
	f, _, err := a.WaitFor(opChatErrNotEnoughRights, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 3210 for a non-admin GM command: %v", err)
	}
	if n := len(f.Payload); n != 0 {
		t.Errorf("3210 payload = %d bytes, want 0", n)
	}
}

// TestBareSlashIsAnsweredNotIgnored: "/" on its own returned nil, so the player
// typed a command and the server said nothing at all.
func TestBareSlashIsAnsweredNotIgnored(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	// Promote BEFORE logging in: the session caches the account at login.
	acc, err := st.Accounts.CreateAccount("adm_u", "pw", true)
	if err != nil {
		t.Fatalf("create admin account: %v", err)
	}
	_ = acc
	a, _ := dialLogin(t, addr, "adm_u", "AdmU")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserVicinity, testclient.NewW().StrU16("/").Bytes())
	if _, _, err := a.WaitFor(opChatErrMalformedCommand, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 3206 for a bare slash: the server answers nothing: %v", err)
	}
}

// TestAnnounceReachesEveryConnectedSession: /ANNOUNCE is the operator's "server
// restarting" channel. 2070 does not merely print - om_0 force-maximises the chat
// panel and force-opens chatDialog - so it must reach everyone, and only admins
// must be able to fire it.
func TestAnnounceReachesEveryConnectedSession(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	adm, _ := dialLogin(t, addr, "ann_adm", "AnnAdm") // first account => admin
	reachWorld(t, adm)
	b, _ := dialLogin(t, addr, "ann_b", "AnnB")
	reachWorld(t, b)
	adm.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = adm.Send(3, opUserVicinity, testclient.NewW().StrU16("/ANNOUNCE server restarting").Bytes())

	f, _, err := b.WaitFor(opServerMessage, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("the other session never received 2070: %v", err)
	}
	r := testclient.NewR(f.Payload)
	n := r.I32()
	got := string(r.RawN(int(n)))
	if got != "server restarting" {
		t.Errorf("announcement = %q, want %q", got, "server restarting")
	}
	// The sender is a session too and must see it as well.
	if _, _, err := adm.WaitFor(opServerMessage, testclient.DefaultTimeout); err != nil {
		t.Errorf("the announcing admin did not receive its own announcement: %v", err)
	}
}

// TestAnnounceIsAdminOnly: a non-admin must get the privilege error, not a
// server-wide broadcast.
func TestAnnounceIsAdminOnly(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	first, _ := dialLogin(t, addr, "ann_first", "AnnFirst") // burns the admin slot
	reachWorld(t, first)
	a, _ := dialLogin(t, addr, "ann_non", "AnnNon")
	reachWorld(t, a)
	first.DrainReceived(200 * time.Millisecond)
	a.DrainReceived(200 * time.Millisecond)

	_ = a.Send(3, opUserVicinity, testclient.NewW().StrU16("/ANNOUNCE hijack").Bytes())
	if _, _, err := a.WaitFor(opChatErrNotEnoughRights, testclient.DefaultTimeout); err != nil {
		t.Fatalf("a non-admin /ANNOUNCE was not refused: %v", err)
	}
	if _, _, err := first.WaitFor(opServerMessage, 700*time.Millisecond); err == nil {
		t.Error("a non-admin /ANNOUNCE was broadcast to other sessions")
	}
}
