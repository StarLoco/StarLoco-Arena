package store

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestRankRightsCannotSmuggleTheLeaderBit covers the privilege-escalation path.
//
// SECURITY: rights arrive as a raw wire bitmask. UpdateRank only ever ADDED the
// leader bit to rank 1 and never stripped it elsewhere, and AddRank stored the
// mask verbatim - so a leader could mint a low rank carrying GuildRightLeader.
// guildRightAllows treats that bit as "everything", handleGuildSetRank orders
// only by LEVEL and never by rights, and anyone can found a guild - so an officer
// with only the promote right could raise a member into a leader-rights rank, and
// that member could then destroy the guild.
func TestRankRightsCannotSmuggleTheLeaderBit(t *testing.T) {
	cases := []struct {
		name   string
		level  int16
		rights int32
		want   int32
	}{
		{"leader rank keeps the bit even if not asked", GuildRankLeader, 0, GuildRightLeader},
		{"leader rank keeps the bit when asked", GuildRankLeader, GuildRightLeader, GuildRightLeader},
		{"a lower rank cannot smuggle it in", 5, GuildRightLeader, 0},
		{"a lower rank keeps its other rights", 5,
			GuildRightLeader | GuildRightInvite, GuildRightInvite},
		{"the default rung cannot smuggle it either", GuildRankDefault,
			GuildRightLeader, 0},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			if got := sanitizeRankRights(tc.level, tc.rights); got != tc.want {
				t.Errorf("sanitizeRankRights(level=%d, rights=%d) = %d, want %d",
					tc.level, tc.rights, got, tc.want)
			}
		})
	}
}

// TestAddRankStripsTheLeaderBit drives the repo, not just the helper - the
// mistake this work has repeatedly made is testing the predicate and not the
// caller.
func TestAddRankStripsTheLeaderBit(t *testing.T) {
	st := newTestStore(t)
	acc, _ := st.Accounts.CreateAccount("rankboss", "pw", false)
	coach, _ := st.Coaches.Create(acc.ID, "RankBoss", 0, 0, 0)

	g, err := st.Guilds.Create("RankGuild", coach.ID, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create guild: %v", err)
	}
	if err := st.Guilds.AddRank(g.ID, GuildRightLeader|GuildRightInvite, "Sneaky"); err != nil {
		t.Fatalf("add rank: %v", err)
	}

	var ranks []domain.GuildRank
	if err := st.DB().Where("guild_id = ?", g.ID).Find(&ranks).Error; err != nil {
		t.Fatalf("load ranks: %v", err)
	}
	seen := false
	for _, rk := range ranks {
		if rk.Name != "Sneaky" {
			continue
		}
		seen = true
		if rk.Rights&GuildRightLeader != 0 {
			t.Errorf("rank %q at level %d carries the LEADER bit; anyone holding it "+
				"can destroy the guild", rk.Name, rk.Level)
		}
		if rk.Rights&GuildRightInvite == 0 {
			t.Error("the rank lost its legitimate invite right too")
		}
	}
	if !seen {
		t.Fatal("the rank was not created; the test proves nothing")
	}
}
