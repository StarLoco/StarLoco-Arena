package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestFriendOnlineOfflineNotify: a coach watching a friend receives a
// NotificationFriendOnline(3148) when the friend logs in and a
// NotificationFriendOffline(3150) when the friend disconnects.
func TestFriendOnlineOfflineNotify(t *testing.T) {
	addr := testServer(t)

	// A is the watcher; create B first so A can befriend it, then take B offline.
	a, _ := dialLogin(t, addr, "pres_a", "PresA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "pres_b", "PresB")
	reachWorld(t, b)

	// A adds B as a friend (notify defaults true).
	_ = a.Send(4, testclient.OpAddFriend, testclient.NewW().Str8("PresB").Bytes())
	if _, _, err := a.WaitFor(testclient.OpFriendAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no FriendAdded ack: %v", err)
	}
	// Take B offline so we can observe the online push on its re-login.
	_ = b.Close()
	a.DrainReceived(200 * time.Millisecond)

	// B logs back in -> A should get FriendOnline(3148) for B.
	b2, _ := dialLogin(t, addr, "pres_b", "PresB")
	reachWorld(t, b2)

	on, _, err := a.WaitFor(testclient.OpFriendOnline, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("A never got FriendOnline(3148): %v", err)
	}
	// Layout: [u8 name][u8 s2][u8 s3][i64 id][i16][i8 sex][i64].
	r := testclient.NewR(on.Payload)
	if name := r.Str8(); name != "PresB" {
		t.Errorf("FriendOnline name = %q, want PresB", name)
	}
	_ = r.Str8() // s2
	_ = r.Str8() // s3
	if r.I64() == 0 {
		t.Error("FriendOnline id should be non-zero")
	}

	// B disconnects -> A should get FriendOffline(3150) for B.
	_ = b2.Close()
	off, _, err := a.WaitFor(testclient.OpFriendOffline, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("A never got FriendOffline(3150): %v", err)
	}
	if name := testclient.NewR(off.Payload).Str8(); name != "PresB" {
		t.Errorf("FriendOffline name = %q, want PresB", name)
	}
}

// TestIgnoreOnlineOfflineNotify: a coach watching an ignored coach receives
// NotificationIgnoreOnline(3164) / Offline(3166) on that coach's login/logout.
func TestIgnoreOnlineOfflineNotify(t *testing.T) {
	addr := testServer(t)

	a, _ := dialLogin(t, addr, "ipres_a", "IPresA")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "ipres_b", "IPresB")
	reachWorld(t, b)

	_ = a.Send(4, testclient.OpAddIgnore, testclient.NewW().Str8("IPresB").Bytes())
	if _, _, err := a.WaitFor(testclient.OpIgnoreAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no IgnoreAdded ack: %v", err)
	}
	_ = b.Close()
	a.DrainReceived(200 * time.Millisecond)

	b2, _ := dialLogin(t, addr, "ipres_b", "IPresB")
	reachWorld(t, b2)

	on, _, err := a.WaitFor(testclient.OpIgnoreOnline, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("A never got IgnoreOnline(3164): %v", err)
	}
	// Layout: [u8 name][i64 id].
	r := testclient.NewR(on.Payload)
	if name := r.Str8(); name != "IPresB" {
		t.Errorf("IgnoreOnline name = %q, want IPresB", name)
	}
	if r.I64() == 0 {
		t.Error("IgnoreOnline id should be non-zero")
	}

	_ = b2.Close()
	off, _, err := a.WaitFor(testclient.OpIgnoreOffline, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("A never got IgnoreOffline(3166): %v", err)
	}
	if name := testclient.NewR(off.Payload).Str8(); name != "IPresB" {
		t.Errorf("IgnoreOffline name = %q, want IPresB", name)
	}
}

// TestSocialAckLayouts asserts the exact wire layout of the four social acks,
// which each differ (regression guard for the per-opcode encoder).
func TestSocialAckLayouts(t *testing.T) {
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "ack_a", "AckA")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "ack_b", "AckB")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)

	// FriendAdded(3156): [u8 name][u8 s2][i64 id][i16][i8 sex][i16].
	_ = a.Send(4, testclient.OpAddFriend, testclient.NewW().Str8("AckB").Bytes())
	f, _, err := a.WaitFor(testclient.OpFriendAdded, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FriendAdded: %v", err)
	}
	r := testclient.NewR(f.Payload)
	if r.Str8() != "AckB" {
		t.Error("FriendAdded name")
	}
	_ = r.Str8() // s2
	if r.I64() != bID {
		t.Error("FriendAdded id")
	}
	_ = r.U16() // status
	_ = r.U8()  // sex
	_ = r.U16() // trailing
	if r.Remaining() != 0 {
		t.Errorf("FriendAdded has %d trailing bytes, want 0", r.Remaining())
	}

	// FriendRemoved(3160): [u8 name] only.
	_ = a.Send(4, testclient.OpRemoveFriend, testclient.NewW().Str8("AckB").Bytes())
	f, _, err = a.WaitFor(testclient.OpFriendRemoved, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FriendRemoved: %v", err)
	}
	r = testclient.NewR(f.Payload)
	if r.Str8() != "AckB" {
		t.Error("FriendRemoved name")
	}
	if r.Remaining() != 0 {
		t.Errorf("FriendRemoved has %d trailing bytes, want 0 (name only)", r.Remaining())
	}

	// IgnoreAdded(3158): [u8 name][u8 s2].
	_ = a.Send(4, testclient.OpAddIgnore, testclient.NewW().Str8("AckB").Bytes())
	f, _, err = a.WaitFor(testclient.OpIgnoreAdded, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no IgnoreAdded: %v", err)
	}
	r = testclient.NewR(f.Payload)
	if r.Str8() != "AckB" {
		t.Error("IgnoreAdded name")
	}
	_ = r.Str8() // s2
	if r.Remaining() != 0 {
		t.Errorf("IgnoreAdded has %d trailing bytes, want 0", r.Remaining())
	}

	// IgnoreRemoved(3162): [u8 name] only.
	_ = a.Send(4, testclient.OpRemoveIgnore, testclient.NewW().Str8("AckB").Bytes())
	f, _, err = a.WaitFor(testclient.OpIgnoreRemoved, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no IgnoreRemoved: %v", err)
	}
	r = testclient.NewR(f.Payload)
	if r.Str8() != "AckB" {
		t.Error("IgnoreRemoved name")
	}
	if r.Remaining() != 0 {
		t.Errorf("IgnoreRemoved has %d trailing bytes, want 0 (name only)", r.Remaining())
	}
}
