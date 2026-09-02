package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestTeamPresetCannotBeStolen is the SECURITY regression for the 6021 IDOR.
//
// TeamRepo.Upsert's doc comment claimed it was "scoped to the owning coach"; the
// code had no coach_id predicate at all. t.ID comes straight off the wire, and
// gorm.Save with a non-zero PK issues UPDATE teams SET * WHERE id = ?, so naming
// a victim's team id wiped their members and reassigned coach_id to the attacker.
// Team ids are small sequential integers, so enumeration was trivial.
func TestTeamPresetCannotBeStolen(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)

	_, victimID := dialLogin(t, addr, "idor_victim", "Victim")
	attacker, attackerID := dialLogin(t, addr, "idor_attacker", "Attacker")

	// The victim owns a preset.
	victimTeam := &domain.Team{CoachID: uint(victimID), Name: "VictimTeam", Type: -1, GameMode: 1}
	if err := st.Teams.Upsert(victimTeam); err != nil {
		t.Fatalf("seed victim team: %v", err)
	}
	if victimTeam.ID == 0 {
		t.Fatal("fixture broken: victim team has no id, so nothing is being targeted")
	}

	// The attacker sends 6021 naming the VICTIM's team id.
	attacker.DrainReceived(200 * time.Millisecond)
	_ = attacker.Send(2, opTeamPresetSave, teamPresetSaveBlobWithID("Stolen", int16(victimTeam.ID)))
	attacker.DrainReceived(400 * time.Millisecond)

	// The victim's team must be untouched and still theirs.
	after, err := st.Teams.Get(victimTeam.ID)
	if err != nil {
		t.Fatalf("victim team vanished entirely: %v", err)
	}
	if after.CoachID != uint(victimID) {
		t.Errorf("victim team owner changed to %d (attacker is %d) - preset stolen",
			after.CoachID, attackerID)
	}
	if after.Name != "VictimTeam" {
		t.Errorf("victim team renamed to %q - preset overwritten", after.Name)
	}
}

// TestTeamPresetRosterIsDeduplicatedAndCapped covers the duplication vector: the
// same owned fighter repeated fills a roster with copies of one fighter, which
// also collides WireIDs (base + fighterID*16 + side*8 + i) and stacks fighters on
// one cell. canPlaceFighter enforced these rules but was only reachable from 6013.
func TestTeamPresetRosterIsDeduplicatedAndCapped(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "dedup_acct", "Dedup")

	// Give the coach one fighter.
	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "Solo"}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}
	if f.ID == 0 {
		t.Fatal("fixture broken: fighter has no id")
	}

	// Save a preset listing that ONE fighter 20 times.
	ids := make([]int64, 20)
	for i := range ids {
		ids[i] = int64(f.ID)
	}
	c.DrainReceived(200 * time.Millisecond)
	_ = c.Send(2, opTeamPresetSave, teamPresetSaveBlobWithFighters("Dupes", ids))
	c.DrainReceived(400 * time.Millisecond)

	teams, err := st.Teams.ListByCoach(uint(coachID))
	if err != nil {
		t.Fatalf("list teams: %v", err)
	}
	var saved *domain.Team
	for i := range teams {
		if teams[i].Name == "Dupes" {
			saved = &teams[i]
		}
	}
	if saved == nil {
		t.Fatal("preset was not saved at all; cannot assess dedup")
	}
	if len(saved.Members) != 1 {
		t.Errorf("roster has %d members, want 1 - the same fighter was accepted "+
			"more than once", len(saved.Members))
	}
}

// teamPresetSaveBlobWithID builds a 6021 blob naming an explicit team id, which
// is the IDOR vector. Layout from decodeTeamPreset:
// [u16 type][u16 teamId][u16 gameMode][Str8 name][u8 fCount]{[i64 id][i64 owner]}
// [u8 cCount].
func teamPresetSaveBlobWithID(name string, teamID int16) []byte {
	return testclient.NewW().
		U16(uint16(0xFFFF)). // type -1 (not a "special" type, so no app bytes)
		U16(uint16(teamID)).
		U16(1).
		Str8(name).
		U8(0).
		U8(0).
		Bytes()
}

// teamPresetSaveBlobWithFighters builds a 6021 blob carrying a fighter list, used
// to send the same owned fighter id many times.
func teamPresetSaveBlobWithFighters(name string, ids []int64) []byte {
	w := testclient.NewW().
		U16(uint16(0xFFFF)).
		U16(0).
		U16(1).
		Str8(name).
		U8(uint8(len(ids)))
	for _, id := range ids {
		w = w.I64(id).I64(0) // owner field is read and not trusted
	}
	return w.U8(0).Bytes()
}

// TestTeamPresetRosterCapsAreEnforced covers the two caps that the dedup test
// cannot reach: with 20 copies of ONE fighter, dedup leaves a single member and
// the size cap never engages, so a mutation disabling it survived. These fixtures
// use DISTINCT fighters across several breeds so each rule is actually exercised.
func TestTeamPresetRosterCapsAreEnforced(t *testing.T) {
	t.Parallel()

	t.Run("six member cap", func(t *testing.T) {
		st, addr := testServerWithStore(t)
		c, coachID := dialLogin(t, addr, "cap_size", "CapSize")

		// 8 fighters over 4 breeds: the per-breed cap (2) admits all 8, so the
		// only thing that can reduce the roster to 6 is the size cap.
		var ids []int64
		for i := 0; i < 8; i++ {
			f := &domain.Fighter{CoachID: uint(coachID), BreedID: uint8(i/2 + 1),
				Name: "Cap" + string(rune('A'+i))}
			if err := st.Fighters.Create(f); err != nil {
				t.Fatalf("seed fighter %d: %v", i, err)
			}
			ids = append(ids, int64(f.ID))
		}
		if len(ids) != 8 {
			t.Fatalf("fixture broken: %d fighters, need 8 to exceed the cap of 6", len(ids))
		}

		c.DrainReceived(200 * time.Millisecond)
		_ = c.Send(2, opTeamPresetSave, teamPresetSaveBlobWithFighters("CapTeam", ids))
		c.DrainReceived(400 * time.Millisecond)

		saved := findTeamByName(t, st, uint(coachID), "CapTeam")
		if len(saved.Members) > 6 {
			t.Errorf("roster has %d members, want at most 6", len(saved.Members))
		}
		if len(saved.Members) != 6 {
			t.Errorf("roster has %d members, want exactly 6 - fewer means the "+
				"fixture is not exercising the size cap", len(saved.Members))
		}
	})

	t.Run("two per breed cap", func(t *testing.T) {
		st, addr := testServerWithStore(t)
		c, coachID := dialLogin(t, addr, "cap_breed", "CapBreed")

		// 5 fighters, ALL breed 1: the size cap (6) cannot bite, so only the
		// per-breed rule can reduce this to 2.
		var ids []int64
		for i := 0; i < 5; i++ {
			f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1,
				Name: "Br" + string(rune('A'+i))}
			if err := st.Fighters.Create(f); err != nil {
				t.Fatalf("seed fighter %d: %v", i, err)
			}
			ids = append(ids, int64(f.ID))
		}

		c.DrainReceived(200 * time.Millisecond)
		_ = c.Send(2, opTeamPresetSave, teamPresetSaveBlobWithFighters("BreedTeam", ids))
		c.DrainReceived(400 * time.Millisecond)

		saved := findTeamByName(t, st, uint(coachID), "BreedTeam")
		if len(saved.Members) != 2 {
			t.Errorf("roster has %d members of one breed, want 2", len(saved.Members))
		}
	})
}

func findTeamByName(t *testing.T, st *store.Store, coachID uint, name string) *domain.Team {
	t.Helper()
	teams, err := st.Teams.ListByCoach(coachID)
	if err != nil {
		t.Fatalf("list teams: %v", err)
	}
	for i := range teams {
		if teams[i].Name == name {
			return &teams[i]
		}
	}
	t.Fatalf("preset %q was not saved at all; nothing to assess", name)
	return nil
}
