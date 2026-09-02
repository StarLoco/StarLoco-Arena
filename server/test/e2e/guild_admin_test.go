package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// joinGuild runs the invite/accept handshake and leaves both clients drained.
func joinGuild(t *testing.T, leader, joiner *testclient.Client, joinerName, guildName string) {
	t.Helper()
	w := testclient.NewW().U8(2).U8(0)
	utf8U8(w, joinerName)
	w.I64(0)
	_ = leader.Send(2, testclient.OpGuildInvite, w.Bytes())
	if _, _, err := joiner.WaitFor(testclient.OpGuildInvitation, testclient.DefaultTimeout); err != nil {
		t.Fatalf("%s got no invitation: %v", joinerName, err)
	}
	aw := testclient.NewW().U8(2).U8(1)
	utf8U8(aw, "Chef")
	utf8U8(aw, guildName)
	_ = joiner.Send(2, testclient.OpGuildInviteAnswer, aw.Bytes())
	if _, _, err := joiner.WaitFor(testclient.OpGuildMembers, testclient.DefaultTimeout); err != nil {
		t.Fatalf("%s never got the roster: %v", joinerName, err)
	}
	leader.DrainReceived(200 * time.Millisecond)
	joiner.DrainReceived(200 * time.Millisecond)
}

// guildResultCode waits for a 504 and returns its code.
func guildResultCode(t *testing.T, c *testclient.Client, what string) int32 {
	t.Helper()
	f, _, err := c.WaitFor(testclient.OpGuildResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("%s: no 504: %v", what, err)
	}
	r := testclient.NewR(f.Payload)
	_ = r.U8()
	return r.I32()
}

// TestGuildKickRemovesTheMember covers 505 in its kick form: the victim is told
// with code 402 (which is what makes its client drop the membership) and the
// clan's roster shrinks.
func TestGuildKickRemovesTheMember(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "kick_a", "Chef")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "kick_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanKick"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	joinGuild(t, a, b, "Recrue", "ClanKick")

	w := testclient.NewW().I64(0).I64(int64(bID))
	_ = a.Send(2, testclient.OpGuildLeave, w.Bytes())

	if code := guildResultCode(t, b, "kick"); code != 402 {
		t.Errorf("victim got code %d, want 402 (kicked)", code)
	}
	// The leader's roster must now hold one member.
	mf, _, err := a.WaitFor(testclient.OpGuildMembers, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("leader got no refreshed roster: %v", err)
	}
	if n := testclient.NewR(mf.Payload).I32(); n != 1 {
		t.Errorf("roster has %d members after the kick, want 1", n)
	}
}

// TestGuildMemberCannotKickTheLeader is the authorisation case that matters: the
// client hides the option, so an attempt reaching the server is by definition
// someone who bypassed it. A plain member must not be able to remove anyone,
// least of all the leader.
func TestGuildMemberCannotKickTheLeader(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "auth_a", "Chef")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "auth_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanAuth"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	joinGuild(t, a, b, "Recrue", "ClanAuth")

	// The rank-10 recruit tries to throw the leader out.
	w := testclient.NewW().I64(0).I64(int64(aID))
	_ = b.Send(2, testclient.OpGuildLeave, w.Bytes())

	if _, _, err := a.WaitFor(testclient.OpGuildResult, 400*time.Millisecond); err == nil {
		t.Error("the leader was removed by a rank-10 member")
	}
}

// TestGuildQuitIsAllowedForAnyone: 505 aimed at yourself is quitting, which
// needs no right at all - and must yield 400, not the kick code.
func TestGuildQuitIsAllowedForAnyone(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "quit_a", "Chef")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "quit_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanQuit"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	joinGuild(t, a, b, "Recrue", "ClanQuit")

	w := testclient.NewW().I64(0).I64(int64(bID))
	_ = b.Send(2, testclient.OpGuildLeave, w.Bytes())

	if code := guildResultCode(t, b, "quit"); code != 400 {
		t.Errorf("quitter got code %d, want 400 (left)", code)
	}
}

// TestGuildDestroyTellsEveryMember covers 511. The member list has to be read
// BEFORE the delete, or the members can never be told - which is the kind of
// ordering bug that only shows up with someone else online.
func TestGuildDestroyTellsEveryMember(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "des_a", "Chef")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "des_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanDoomed"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	joinGuild(t, a, b, "Recrue", "ClanDoomed")

	_ = a.Send(2, testclient.OpGuildDestroy, testclient.NewW().I64(0).Bytes())

	if code := guildResultCode(t, a, "destroy/leader"); code != 401 {
		t.Errorf("leader got %d, want 401 (destroyed)", code)
	}
	if code := guildResultCode(t, b, "destroy/member"); code != 401 {
		t.Errorf("member got %d, want 401 (destroyed)", code)
	}
	// The clan is gone: creating the same name again must now succeed.
	if code := createGuild(t, a, "ClanDoomed"); code != 403 {
		t.Errorf("re-create after destroy = %d, want 403", code)
	}
}

// TestGuildMemberCannotDestroy: only the leader bit may dissolve a clan.
func TestGuildMemberCannotDestroy(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "nod_a", "Chef")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "nod_b", "Recrue")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanSafe"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	joinGuild(t, a, b, "Recrue", "ClanSafe")

	_ = b.Send(2, testclient.OpGuildDestroy, testclient.NewW().I64(0).Bytes())
	if _, _, err := a.WaitFor(testclient.OpGuildResult, 400*time.Millisecond); err == nil {
		t.Error("a rank-10 member destroyed the clan")
	}
}

// TestGuildRankCrud walks add / modify / delete (553/555/557) and checks the
// guild record the client re-renders from.
func TestGuildRankCrud(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "rank_a", "Chef")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanRanks"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	a.DrainReceived(200 * time.Millisecond)

	// Add a rank with the invite right.
	w := testclient.NewW().I64(0).I32(2)
	utf8U8(w, "Officier")
	_ = a.Send(2, testclient.OpGuildRankAdd, w.Bytes())

	f, _, err := a.WaitFor(testclient.OpGuildRecord, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 510 after adding a rank: %v", err)
	}
	names, levels := parseGuildRanks(t, f.Payload)
	if len(names) != 3 {
		t.Fatalf("guild has %d ranks after the add, want 3", len(names))
	}
	var added int16 = -1
	for i, n := range names {
		if n == "Officier" {
			added = levels[i]
		}
	}
	if added < 0 {
		t.Fatalf("the new rank is missing: %v", names)
	}

	// Rename it.
	mw := testclient.NewW().I64(0).I32(2).U16(uint16(added)).U16(uint16(added))
	utf8U8(mw, "Bras droit")
	_ = a.Send(2, testclient.OpGuildRankModify, mw.Bytes())
	f, _, err = a.WaitFor(testclient.OpGuildRecord, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 510 after modify: %v", err)
	}
	names, _ = parseGuildRanks(t, f.Payload)
	found := false
	for _, n := range names {
		if n == "Bras droit" {
			found = true
		}
	}
	if !found {
		t.Errorf("rank was not renamed: %v", names)
	}

	// Delete it.
	_ = a.Send(2, testclient.OpGuildRankDelete, testclient.NewW().I64(0).U16(uint16(added)).Bytes())
	f, _, err = a.WaitFor(testclient.OpGuildRecord, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 510 after delete: %v", err)
	}
	names, _ = parseGuildRanks(t, f.Payload)
	if len(names) != 2 {
		t.Errorf("guild has %d ranks after the delete, want 2", len(names))
	}
}

// TestGuildLeaderRankCannotBeDeleted: the client greys the button out for rank 1
// and rank 10, and a guild that lost either would be unmanageable or have
// nowhere to put new members.
func TestGuildLeaderRankCannotBeDeleted(t *testing.T) {
	t.Parallel()
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "prot_a", "Chef")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	if code := createGuild(t, a, "ClanProt"); code != 403 {
		t.Fatalf("create = %d", code)
	}
	a.DrainReceived(200 * time.Millisecond)

	_ = a.Send(2, testclient.OpGuildRankDelete, testclient.NewW().I64(0).U16(1).Bytes())
	_ = a.Send(2, testclient.OpGuildRankDelete, testclient.NewW().I64(0).U16(10).Bytes())
	// Ask for the record and confirm both survived.
	_ = a.Send(2, testclient.OpGuildGet, testclient.NewW().I64(0).Bytes())
	f, _, err := a.WaitFor(testclient.OpGuildRecord, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 510: %v", err)
	}
	_, levels := parseGuildRanks(t, f.Payload)
	has := map[int16]bool{}
	for _, l := range levels {
		has[l] = true
	}
	if !has[1] || !has[10] {
		t.Errorf("protected ranks were deleted: levels = %v", levels)
	}
}

// parseGuildRanks decodes 510's guild record into rank names and levels.
func parseGuildRanks(t *testing.T, payload []byte) ([]string, []int16) {
	t.Helper()
	r := testclient.NewR(payload)
	_ = r.U16() // record length
	_ = r.I64() // guildId
	_ = readU8String(r)
	_ = r.U16()
	_ = r.U16()
	_ = r.I32()
	_ = r.I32()
	_ = r.U16() // demonId
	_ = r.I32()
	n := int(r.U8())
	names := make([]string, 0, n)
	levels := make([]int16, 0, n)
	for i := 0; i < n; i++ {
		inner := testclient.NewR(r.RawN(int(r.U16())))
		levels = append(levels, int16(inner.U16()))
		_ = inner.I32()
		names = append(names, readU8String(inner))
	}
	return names, levels
}
