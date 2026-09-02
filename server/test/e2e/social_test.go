package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestWhisperDelivered: a whisper (3155) reaches the named online target as a
// PrivateContent (3154).
func TestWhisperDelivered(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "whisper_a", "Waa")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "whisper_b", "Wbb")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// A whispers B: [u8 targetLen][target][u8 msgLen][message].
	p := testclient.NewW().Str8("Wbb").Str8("hi there").Bytes()
	_ = a.Send(4, testclient.OpUserPrivateContent, p)

	f, _, err := b.WaitFor(testclient.OpPrivateContent, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B never got the whisper: %v", err)
	}
	// Payload: [u8 nameLen][sender][i64 id][u16 msgLen][message].
	r := testclient.NewR(f.Payload)
	sender := r.Str8()
	if sender != "Waa" {
		t.Errorf("whisper sender = %q, want Wa", sender)
	}

	// The sender must NOT get its own whisper echoed back (the client shows the
	// outgoing line locally; a server echo would duplicate it).
	for _, fr := range a.DrainReceived(250 * time.Millisecond) {
		if fr.Opcode == testclient.OpPrivateContent {
			t.Fatal("sender received its own whisper back (would show twice)")
		}
	}
}

// TestWhisperUserNotFound: whispering an offline/unknown name returns
// UserNotFound (3204).
func TestWhisperUserNotFound(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "whisper_c", "Wcc")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	p := testclient.NewW().Str8("Ghost").Str8("hello?").Bytes()
	_ = a.Send(4, testclient.OpUserPrivateContent, p)

	if _, _, err := a.WaitFor(testclient.OpUserNotFound, testclient.DefaultTimeout); err != nil {
		t.Fatalf("expected UserNotFound(3204): %v", err)
	}
}

// TestFriendAddRemove: adding a friend acks (3156), and the persisted friend
// then appears in the FriendList (3144) on relogin.
func TestFriendAddRemove(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "friend_a", "Faa")
	reachWorld(t, a)
	// The target must exist as a coach; create it via a second login.
	b, bID := dialLogin(t, addr, "friend_b", "Fbb")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)

	// A adds B by name: [u8 nameLen][name].
	_ = a.Send(4, testclient.OpAddFriend, testclient.NewW().Str8("Fbb").Bytes())
	if _, _, err := a.WaitFor(testclient.OpFriendAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no FriendAdded ack: %v", err)
	}

	// Verify persisted: A's coach has a friend edge to B.
	c, err := st.Coaches.Get(uint(aID))
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	found := false
	for _, fr := range c.Friends {
		if fr.FriendID == uint(bID) {
			found = true
		}
	}
	if !found {
		t.Error("friend edge not persisted")
	}

	// Remove and verify the edge is gone.
	_ = a.Send(4, testclient.OpRemoveFriend, testclient.NewW().Str8("Fbb").Bytes())
	if _, _, err := a.WaitFor(testclient.OpFriendRemoved, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no FriendRemoved ack: %v", err)
	}
	c2, _ := st.Coaches.Get(uint(aID))
	for _, fr := range c2.Friends {
		if fr.FriendID == uint(bID) {
			t.Error("friend edge still present after remove")
		}
	}
}

// TestIgnoreAddRemove: adding an ignore acks (3158) and persists the directed
// edge; removing acks (3162) and drops it.
func TestIgnoreAddRemove(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "ignore_a", "Iaa")
	reachWorld(t, a)
	// Target must exist as a coach.
	b, bID := dialLogin(t, addr, "ignore_b", "Ibb")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)

	// A ignores B by name: [u8 nameLen][name].
	_ = a.Send(4, testclient.OpAddIgnore, testclient.NewW().Str8("Ibb").Bytes())
	ack, _, err := a.WaitFor(testclient.OpIgnoreAdded, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no IgnoreAdded ack: %v", err)
	}
	// Ack layout (client ft_0): [u8 nameLen][name][u8 s2Len][s2]. No id.
	r := testclient.NewR(ack.Payload)
	if name := r.Str8(); name != "Ibb" {
		t.Errorf("IgnoreAdded name = %q, want Ib", name)
	}
	_ = bID // id is not carried by IgnoreAdded (verified vs client ft_0)

	// Verify persisted: A's coach has an ignore edge to B.
	c, err := st.Coaches.Get(uint(aID))
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	found := false
	for _, ig := range c.Ignored {
		if ig.IgnoredID == uint(bID) {
			found = true
		}
	}
	if !found {
		t.Error("ignore edge not persisted")
	}

	// Remove and verify the edge is gone.
	_ = a.Send(4, testclient.OpRemoveIgnore, testclient.NewW().Str8("Ibb").Bytes())
	if _, _, err := a.WaitFor(testclient.OpIgnoreRemoved, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no IgnoreRemoved ack: %v", err)
	}
	c2, _ := st.Coaches.Get(uint(aID))
	for _, ig := range c2.Ignored {
		if ig.IgnoredID == uint(bID) {
			t.Error("ignore edge still present after remove")
		}
	}
}
