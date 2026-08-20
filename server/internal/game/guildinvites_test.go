package game

import "testing"

// TestGuildInviteMatchedByBothNames: the client's answer (503) identifies an
// invitation only by the inviter's and the guild's names, so those two together
// are the whole key. With more than one offer outstanding, matching loosely puts
// the coach in a clan it did not accept - and the packet stream looks perfectly
// normal afterwards.
func TestGuildInviteMatchedByBothNames(t *testing.T) {
	inv := newGuildInvites()
	const target = uint(7)
	inv.add(target, pendingGuildInvite{guildID: 1, inviterName: "Chef", guildName: "ClanAlpha"})
	inv.add(target, pendingGuildInvite{guildID: 2, inviterName: "Autre", guildName: "ClanBeta"})

	got, ok := inv.take(target, "Autre", "ClanBeta")
	if !ok {
		t.Fatal("the second invitation was not found")
	}
	if got.guildID != 2 {
		t.Errorf("accepted guild %d, want 2 - the wrong invitation was taken", got.guildID)
	}
	// The other one must survive untouched.
	if still, ok := inv.take(target, "Chef", "ClanAlpha"); !ok || still.guildID != 1 {
		t.Error("accepting one invitation consumed the other")
	}
}

// TestGuildInviteRejectsAMismatch: a wrong or stale pair must find nothing
// rather than fall through to whatever is pending.
func TestGuildInviteRejectsAMismatch(t *testing.T) {
	inv := newGuildInvites()
	const target = uint(7)
	inv.add(target, pendingGuildInvite{guildID: 1, inviterName: "Chef", guildName: "ClanAlpha"})

	if _, ok := inv.take(target, "Chef", "ClanBeta"); ok {
		t.Error("matched an invitation with the wrong GUILD name")
	}
	if _, ok := inv.take(target, "Imposteur", "ClanAlpha"); ok {
		t.Error("matched an invitation with the wrong INVITER name")
	}
	if _, ok := inv.take(target, "Chef", "ClanAlpha"); !ok {
		t.Error("the genuine invitation was consumed by the failed attempts")
	}
}

// TestGuildInviteIsOneShot: an invitation must not be reusable, or a coach could
// rejoin after being kicked by replaying the same answer.
func TestGuildInviteIsOneShot(t *testing.T) {
	inv := newGuildInvites()
	const target = uint(7)
	inv.add(target, pendingGuildInvite{guildID: 1, inviterName: "Chef", guildName: "ClanAlpha"})
	if _, ok := inv.take(target, "Chef", "ClanAlpha"); !ok {
		t.Fatal("first take failed")
	}
	if _, ok := inv.take(target, "Chef", "ClanAlpha"); ok {
		t.Error("the same invitation was accepted twice")
	}
}

// TestGuildInviteDoesNotStackDuplicates: spamming the invite button must not
// build an unbounded list per target.
func TestGuildInviteDoesNotStackDuplicates(t *testing.T) {
	inv := newGuildInvites()
	const target = uint(7)
	for i := 0; i < 5; i++ {
		inv.add(target, pendingGuildInvite{guildID: 1, inviterName: "Chef", guildName: "ClanAlpha"})
	}
	if _, ok := inv.take(target, "Chef", "ClanAlpha"); !ok {
		t.Fatal("take failed")
	}
	if _, ok := inv.take(target, "Chef", "ClanAlpha"); ok {
		t.Error("duplicate invitations accumulated")
	}
}
