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

// TestGuildLadderRanksByMemberStrength pins the clan board's ordering and the
// score definition. The score is the SUM of member ratings - a server choice, not
// a decoded one, so it is stated in a test rather than left implicit.
func TestGuildLadderRanksByMemberStrength(t *testing.T) {
	s := newTestStore(t)

	strong := guildTestCoach(t, s, "Fort")
	weak := guildTestCoach(t, s, "Faible")
	helper := guildTestCoach(t, s, "Aide")
	setStrength(t, s, strong, 1500)
	setStrength(t, s, weak, 900)
	setStrength(t, s, helper, 300)

	gw, err := s.Guilds.Create("ClanFaible", weak, "Chef", "Membre")
	if err != nil {
		t.Fatalf("create weak: %v", err)
	}
	if _, err := s.Guilds.Create("ClanFort", strong, "Chef", "Membre"); err != nil {
		t.Fatalf("create strong: %v", err)
	}
	// 900 + 300 = 1200 < 1500, so the two-member clan still ranks second.
	if err := s.Guilds.AddMember(gw.ID, helper); err != nil {
		t.Fatalf("add: %v", err)
	}

	rows, err := s.Guilds.Ladder(10)
	if err != nil {
		t.Fatalf("ladder: %v", err)
	}
	if len(rows) != 2 {
		t.Fatalf("%d rows, want 2", len(rows))
	}
	if rows[0].Name != "ClanFort" || rows[0].Score != 1500 {
		t.Errorf("row 0 = %s/%d, want ClanFort/1500", rows[0].Name, rows[0].Score)
	}
	if rows[1].Name != "ClanFaible" || rows[1].Score != 1200 {
		t.Errorf("row 1 = %s/%d, want ClanFaible/1200 (900+300)", rows[1].Name, rows[1].Score)
	}
	if rows[0].Leader != "Fort" {
		t.Errorf("leader = %q, want Fort", rows[0].Leader)
	}
}

// TestGuildNamesByCoachName is the ladder tag lookup: one query for a page, and
// a clanless coach must simply be absent rather than mapped to "".
func TestGuildNamesByCoachName(t *testing.T) {
	s := newTestStore(t)
	inClan := guildTestCoach(t, s, "Membre")
	loner := guildTestCoach(t, s, "Solo")
	if _, err := s.Guilds.Create("Les Bouftous", inClan, "Chef", "Membre"); err != nil {
		t.Fatalf("create: %v", err)
	}
	// A second clan member who is NOT on the requested page. Without one, a
	// lookup that ignores its filter and returns every membership in the
	// database still looks correct.
	offPage := guildTestCoach(t, s, "HorsPage")
	if _, err := s.Guilds.Create("Autre Clan", offPage, "Chef", "Membre"); err != nil {
		t.Fatalf("create other: %v", err)
	}

	got, err := s.Guilds.NamesByCoachName([]string{"Membre", "Solo", "Inconnu"})
	if err != nil {
		t.Fatalf("lookup: %v", err)
	}
	if _, ok := got["HorsPage"]; ok {
		t.Error("the lookup returned a coach that was not asked for - the name filter is not applied")
	}
	if got["Membre"] != "Les Bouftous" {
		t.Errorf("Membre -> %q, want Les Bouftous", got["Membre"])
	}
	if _, ok := got["Solo"]; ok {
		t.Error("a clanless coach appeared in the tag map")
	}
	_ = loner
}

func setStrength(t *testing.T, s *Store, coachID uint, v int32) {
	t.Helper()
	if err := s.DB().Table("coaches").Where("id = ?", coachID).
		Update("strength", v).Error; err != nil {
		t.Fatalf("set strength: %v", err)
	}
}

// TestClanIslandAllocationIsStableAndUnique: there are only 24 islands, so two
// clans must never resolve to the same world, and a clan's island must not move
// on a second call. Deriving the world from the guild id would satisfy neither
// once ids pass 24.
func TestClanIslandAllocationIsStableAndUnique(t *testing.T) {
	s := newTestStore(t)
	a := guildTestCoach(t, s, "ChefA")
	b := guildTestCoach(t, s, "ChefB")
	ga, _ := s.Guilds.Create("ClanA", a, "Chef", "Membre")
	gb, _ := s.Guilds.Create("ClanB", b, "Chef", "Membre")

	w1, err := s.Guilds.AssignIsland(ga.ID)
	if err != nil {
		t.Fatalf("assign A: %v", err)
	}
	if w1 < GuildIslandFirst || w1 > GuildIslandLast {
		t.Fatalf("island %d is outside the shipped range %d-%d", w1, GuildIslandFirst, GuildIslandLast)
	}
	again, err := s.Guilds.AssignIsland(ga.ID)
	if err != nil || again != w1 {
		t.Errorf("second call moved the island: %d -> %d (%v)", w1, again, err)
	}
	w2, err := s.Guilds.AssignIsland(gb.ID)
	if err != nil {
		t.Fatalf("assign B: %v", err)
	}
	if w2 == w1 {
		t.Errorf("two clans were given the same island (%d)", w1)
	}
}

// TestClanIslandsRunOut: the client has an "your clan is not ranked high enough
// to have an island" message precisely because they are finite. Once all 24 are
// held, the next clan must be refused rather than handed a duplicate or a world
// that does not exist.
func TestClanIslandsRunOut(t *testing.T) {
	s := newTestStore(t)
	total := int(GuildIslandLast-GuildIslandFirst) + 1
	for i := 0; i <= total; i++ {
		leader := guildTestCoach(t, s, "Chef"+itoaTest(i))
		g, err := s.Guilds.Create("Clan"+itoaTest(i), leader, "Chef", "Membre")
		if err != nil {
			t.Fatalf("create %d: %v", i, err)
		}
		w, err := s.Guilds.AssignIsland(g.ID)
		if i < total {
			if err != nil || w == 0 {
				t.Fatalf("clan %d got no island (%v) but %d should exist", i, err, total)
			}
			continue
		}
		if !errors.Is(err, ErrNoIslandFree) {
			t.Errorf("clan %d past the limit got island %d (%v), want ErrNoIslandFree", i, w, err)
		}
	}
}

func itoaTest(n int) string {
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
