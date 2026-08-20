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

// TestIslandBelongsToTheDemonsTopClan is the retail rule, and the reason the
// island is derived rather than stored: "Seul le clan le plus puissant au service
// de chaque Demon recoit une ile de clan". An allotted island would stay with a
// clan that had been overtaken.
func TestIslandBelongsToTheDemonsTopClan(t *testing.T) {
	s := newTestStore(t)
	a := guildTestCoach(t, s, "ChefA")
	b := guildTestCoach(t, s, "ChefB")
	ga, _ := s.Guilds.Create("ClanA", a, "Chef", "Membre")
	gb, _ := s.Guilds.Create("ClanB", b, "Chef", "Membre")

	// Both serve demon 3, so both are competing for world 88.
	if err := s.Guilds.SetDemon(ga.ID, 3); err != nil {
		t.Fatalf("affiliate A: %v", err)
	}
	if err := s.Guilds.SetDemon(gb.ID, 3); err != nil {
		t.Fatalf("affiliate B: %v", err)
	}
	if _, err := s.Guilds.AddDemonReputation(ga.ID, 3, 100); err != nil {
		t.Fatalf("rep A: %v", err)
	}
	if _, err := s.Guilds.AddDemonReputation(gb.ID, 3, 50); err != nil {
		t.Fatalf("rep B: %v", err)
	}

	world, ok, err := s.Guilds.IslandOf(ga.ID)
	if err != nil || !ok {
		t.Fatalf("the leading clan has no island (%v)", err)
	}
	want, _ := IslandWorldForDemon(3)
	if world != want {
		t.Errorf("island = world %d, want %d (demon 3's island)", world, want)
	}
	if _, ok, _ := s.Guilds.IslandOf(gb.ID); ok {
		t.Error("the SECOND-placed clan also holds an island")
	}

	// B overtakes A: the island must move with the standing.
	if _, err := s.Guilds.AddDemonReputation(gb.ID, 3, 100); err != nil {
		t.Fatalf("rep B2: %v", err)
	}
	if _, ok, _ := s.Guilds.IslandOf(ga.ID); ok {
		t.Error("the overtaken clan kept its island")
	}
	if w, ok, _ := s.Guilds.IslandOf(gb.ID); !ok || w != want {
		t.Errorf("the new leader got world %d (ok=%v), want %d", w, ok, want)
	}
}

// TestUnaffiliatedClanHasNoIsland: serving no demon means no island, which is the
// state every clan starts in.
func TestUnaffiliatedClanHasNoIsland(t *testing.T) {
	s := newTestStore(t)
	a := guildTestCoach(t, s, "Chef")
	g, _ := s.Guilds.Create("ClanNeutre", a, "Chef", "Membre")
	if _, ok, err := s.Guilds.IslandOf(g.ID); ok || err != nil {
		t.Errorf("an unaffiliated clan holds an island (ok=%v, err=%v)", ok, err)
	}
}

// TestDemonIslandsAreOneToOne: 24 demons, 24 islands, no two demons sharing one.
func TestDemonIslandsAreOneToOne(t *testing.T) {
	seen := map[int16]int16{}
	for d := int16(1); d <= DemonCount; d++ {
		w, ok := IslandWorldForDemon(d)
		if !ok {
			t.Fatalf("demon %d has no island", d)
		}
		if w < GuildIslandFirst || w > GuildIslandLast {
			t.Errorf("demon %d -> world %d, outside %d-%d", d, w, GuildIslandFirst, GuildIslandLast)
		}
		if prev, dup := seen[w]; dup {
			t.Errorf("demons %d and %d share world %d", prev, d, w)
		}
		seen[w] = d
	}
	if len(seen) != int(DemonCount) {
		t.Errorf("%d distinct islands for %d demons", len(seen), DemonCount)
	}
	if _, ok := IslandWorldForDemon(0); ok {
		t.Error("demon 0 (unaffiliated) was given an island")
	}
	if _, ok := IslandWorldForDemon(DemonCount + 1); ok {
		t.Error("a demon past the last one was given an island")
	}
}

// TestAffiliationIsOneWay: the client only offers the affiliate control while
// demonId == 0, and switching would take an island from its holder for free.
func TestAffiliationIsOneWay(t *testing.T) {
	s := newTestStore(t)
	a := guildTestCoach(t, s, "Chef")
	g, _ := s.Guilds.Create("ClanFidele", a, "Chef", "Membre")
	if err := s.Guilds.SetDemon(g.ID, 5); err != nil {
		t.Fatalf("first affiliation: %v", err)
	}
	if err := s.Guilds.SetDemon(g.ID, 6); !errors.Is(err, ErrGuildAlreadyAffiliated) {
		t.Errorf("re-affiliation returned %v, want ErrGuildAlreadyAffiliated", err)
	}
	if err := s.Guilds.SetDemon(g.ID, 99); err == nil {
		t.Error("a nonexistent demon was accepted")
	}
}
