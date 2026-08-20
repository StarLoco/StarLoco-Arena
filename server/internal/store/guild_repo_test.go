package store

import (
	"errors"
	"testing"
)

func guildTestCoach(t *testing.T, s *Store, name string) uint {
	t.Helper()
	acc, err := s.Accounts.CreateAccount(name+"_acc", "secret", false)
	if err != nil {
		t.Fatalf("account: %v", err)
	}
	c, err := s.Coaches.Create(acc.ID, name, 1, 2, 0)
	if err != nil {
		t.Fatalf("coach: %v", err)
	}
	return c.ID
}

// TestGuildCreateSeedsRanksAndLeader: a guild is useless without its two
// mandatory ranks and a leader row - the client would show an empty member list
// and offer nobody the management button. Create does all three or none.
func TestGuildCreateSeedsRanksAndLeader(t *testing.T) {
	s := newTestStore(t)
	leader := guildTestCoach(t, s, "Chef")

	g, err := s.Guilds.Create("Les Bouftous", leader, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create: %v", err)
	}
	ranks, err := s.Guilds.Ranks(g.ID)
	if err != nil {
		t.Fatalf("ranks: %v", err)
	}
	if len(ranks) != 2 {
		t.Fatalf("seeded %d ranks, want 2", len(ranks))
	}
	if ranks[0].Level != GuildRankLeader || ranks[0].Rights&GuildRightLeader == 0 {
		t.Errorf("rank[0] = level %d rights %d, want the leader rank with bit 0",
			ranks[0].Level, ranks[0].Rights)
	}
	if ranks[1].Level != GuildRankDefault || ranks[1].Rights != 0 {
		t.Errorf("rank[1] = level %d rights %d, want level 10 with no rights",
			ranks[1].Level, ranks[1].Rights)
	}
	m, err := s.Guilds.MembershipOf(leader)
	if err != nil {
		t.Fatalf("leader membership: %v", err)
	}
	if m.RankLevel != GuildRankLeader {
		t.Errorf("leader joined at rank %d, want %d", m.RankLevel, GuildRankLeader)
	}
}

// TestGuildNameIsUnique: the client shows one error for "invalid or already
// used", so the server must actually enforce the uniqueness that error implies.
func TestGuildNameIsUnique(t *testing.T) {
	s := newTestStore(t)
	a := guildTestCoach(t, s, "A")
	b := guildTestCoach(t, s, "B")

	if _, err := s.Guilds.Create("Doublon", a, "Chef", "Membre"); err != nil {
		t.Fatalf("first create: %v", err)
	}
	_, err := s.Guilds.Create("Doublon", b, "Chef", "Membre")
	if !errors.Is(err, ErrGuildNameTaken) {
		t.Errorf("second create with the same name returned %v, want ErrGuildNameTaken", err)
	}
}

// TestCoachCannotBeInTwoGuilds: the client assumes one clan per coach (`aPY()`
// is a single reference), so a second membership would make the coach's own view
// depend on row order.
func TestCoachCannotBeInTwoGuilds(t *testing.T) {
	s := newTestStore(t)
	leader := guildTestCoach(t, s, "Chef")
	other := guildTestCoach(t, s, "Autre")

	g1, err := s.Guilds.Create("Un", leader, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create: %v", err)
	}
	g2, err := s.Guilds.Create("Deux", other, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create 2: %v", err)
	}
	if err := s.Guilds.AddMember(g2.ID, leader); !errors.Is(err, ErrAlreadyInGuild) {
		t.Errorf("joining a second guild returned %v, want ErrAlreadyInGuild", err)
	}
	// And creating one while already a member is refused for the same reason.
	if _, err := s.Guilds.Create("Trois", leader, "Chef", "Membre"); !errors.Is(err, ErrAlreadyInGuild) {
		t.Errorf("creating while already a member returned %v, want ErrAlreadyInGuild", err)
	}
	m, _ := s.Guilds.MembershipOf(leader)
	if m == nil || m.GuildID != g1.ID {
		t.Error("the original membership was disturbed")
	}
}

// TestGuildMembersAndRemoval covers the list the client renders and the removal
// that has to make a coach eligible to join again.
func TestGuildMembersAndRemoval(t *testing.T) {
	s := newTestStore(t)
	leader := guildTestCoach(t, s, "Chef")
	joiner := guildTestCoach(t, s, "Recrue")

	g, _ := s.Guilds.Create("Clan", leader, "Chef", "Membre")
	if err := s.Guilds.AddMember(g.ID, joiner); err != nil {
		t.Fatalf("add: %v", err)
	}
	members, _ := s.Guilds.Members(g.ID)
	if len(members) != 2 {
		t.Fatalf("%d members, want 2", len(members))
	}
	if members[0].CoachID != leader {
		t.Error("members are not ordered by rank (the leader must come first)")
	}
	ids, _ := s.Guilds.CoachIDsIn(g.ID)
	if len(ids) != 2 {
		t.Errorf("CoachIDsIn returned %d, want 2 - clan chat would miss a member", len(ids))
	}

	gone, err := s.Guilds.RemoveMember(g.ID, joiner)
	if err != nil || !gone {
		t.Fatalf("remove: %v (removed=%v)", err, gone)
	}
	if _, err := s.Guilds.MembershipOf(joiner); !errors.Is(err, ErrNotFound) {
		t.Errorf("membership after removal = %v, want ErrNotFound", err)
	}
	if err := s.Guilds.AddMember(g.ID, joiner); err != nil {
		t.Errorf("a removed coach cannot rejoin: %v", err)
	}
}
