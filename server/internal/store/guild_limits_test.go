package store

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestGuildCannotBeLeftLeaderless covers the orphaning primitive.
//
// handleGuildLeave let a leader remove ITSELF with no succession and no
// last-leader check. guilds.leader_coach_id then pointed at a non-member and no
// rank-1 member remained, so destroy, every rank edit and demon affiliation
// became permanently unreachable - while the name stayed reserved
// (case-insensitively unique), making it a name-squatting primitive.
func TestGuildCannotBeLeftLeaderless(t *testing.T) {
	st := newTestStore(t)
	acc, _ := st.Accounts.CreateAccount("orphan", "pw", false)
	leader, _ := st.Coaches.Create(acc.ID, "TheLeader", 0, 0, 0)

	g, err := st.Guilds.Create("OrphanClan", leader.ID, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create guild: %v", err)
	}

	// The sole leader leaving would orphan the clan.
	orphan, err := st.Guilds.WouldOrphanGuild(g.ID, leader.ID)
	if err != nil {
		t.Fatalf("WouldOrphanGuild: %v", err)
	}
	if !orphan {
		t.Fatal("removing the only leader was not reported as orphaning the clan")
	}

	// Add a second leader; now either may leave.
	acc2, _ := st.Accounts.CreateAccount("orphan2", "pw", false)
	second, _ := st.Coaches.Create(acc2.ID, "CoLeader", 0, 0, 0)
	if err := st.Guilds.AddMember(g.ID, second.ID); err != nil {
		t.Fatalf("add member: %v", err)
	}
	if err := st.Guilds.SetMemberRank(g.ID, second.ID, GuildRankLeader); err != nil {
		t.Fatalf("promote: %v", err)
	}
	orphan, err = st.Guilds.WouldOrphanGuild(g.ID, leader.ID)
	if err != nil {
		t.Fatalf("WouldOrphanGuild: %v", err)
	}
	if orphan {
		t.Error("with two leaders, one leaving must be allowed - the guard is too " +
			"strict and would trap people in clans")
	}

	// An ordinary member leaving is never orphaning.
	acc3, _ := st.Accounts.CreateAccount("orphan3", "pw", false)
	plain, _ := st.Coaches.Create(acc3.ID, "PlainMember", 0, 0, 0)
	if err := st.Guilds.AddMember(g.ID, plain.ID); err != nil {
		t.Fatalf("add member: %v", err)
	}
	if orphan, _ := st.Guilds.WouldOrphanGuild(g.ID, plain.ID); orphan {
		t.Error("an ordinary member leaving was reported as orphaning the clan")
	}
}

// TestGuildRosterIsCapped covers the O(N^2) fan-out bound: refreshGuild rebuilds
// the full member list once per online member, so every join and rank edit is
// O(N) queries and O(N) frames.
func TestGuildRosterIsCapped(t *testing.T) {
	st := newTestStore(t)
	acc, _ := st.Accounts.CreateAccount("capclan", "pw", false)
	leader, _ := st.Coaches.Create(acc.ID, "CapLeader", 0, 0, 0)
	g, err := st.Guilds.Create("CapClan", leader.ID, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create guild: %v", err)
	}

	// Fill to the cap (the leader already occupies one seat).
	for i := 1; i < GuildMaxMembers; i++ {
		a, _ := st.Accounts.CreateAccount("m"+string(rune('a'+i%26))+itoa(i), "pw", false)
		c, _ := st.Coaches.Create(a.ID, "M"+itoa(i), 0, 0, 0)
		if err := st.Guilds.AddMember(g.ID, c.ID); err != nil {
			t.Fatalf("add member %d: %v", i, err)
		}
	}
	n, err := st.Guilds.CountMembers(g.ID)
	if err != nil {
		t.Fatalf("count: %v", err)
	}
	if n != GuildMaxMembers {
		t.Fatalf("fixture broken: %d members, want %d", n, GuildMaxMembers)
	}

	// One more must be refused.
	a, _ := st.Accounts.CreateAccount("overflow", "pw", false)
	c, _ := st.Coaches.Create(a.ID, "Overflow", 0, 0, 0)
	if err := st.Guilds.AddMember(g.ID, c.ID); err == nil {
		t.Error("a member was added past the cap")
	}
}

func itoa(n int) string {
	if n == 0 {
		return "0"
	}
	var b []byte
	for n > 0 {
		b = append([]byte{byte('0' + n%10)}, b...)
		n /= 10
	}
	return string(b)
}

// TestCurrencySaturates covers the int32 overflow guard.
func TestCurrencySaturates(t *testing.T) {
	const maxI32, minI32 = int32(1<<31 - 1), int32(-1 << 31)
	cases := []struct {
		amount, delta, want int32
	}{
		{100, 50, 150},
		{maxI32 - 10, 5, maxI32 - 5},
		{maxI32 - 10, 100, maxI32}, // saturates instead of wrapping negative
		{maxI32, 1, maxI32},
		{minI32 + 10, -100, minI32}, // and the other direction
		{0, -5, -5},
	}
	for _, tc := range cases {
		if got := addCurrencySaturating(tc.amount, tc.delta); got != tc.want {
			t.Errorf("addCurrencySaturating(%d, %d) = %d, want %d",
				tc.amount, tc.delta, got, tc.want)
		}
	}
}

var _ = domain.CoachCurrency{}
