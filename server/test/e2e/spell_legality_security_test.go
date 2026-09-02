package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const opFighterInventoryUpdate = 6011

// spellTestServer injects a small, deterministic spell table so these tests do
// not depend on the git-ignored client data files.
//
// Ids mirror the real shape: 10/11 belong to breed 1, 20 to breed 2, 428 is
// pseudo-breed 0 (monster/summon material) and 193 is pseudo-breed 99 (boss).
func spellTestServer(t *testing.T) (*store.Store, string) {
	t.Helper()
	return testServerWithDeps(t, func(d *game.Deps) {
		d.Spells = gamedata.NewSpells(
			&gamedata.Spell{ID: 10, BreedID: 1},
			&gamedata.Spell{ID: 11, BreedID: 1},
			&gamedata.Spell{ID: 20, BreedID: 2},
			&gamedata.Spell{ID: 428, BreedID: 0},
			&gamedata.Spell{ID: 193, BreedID: 99},
		)
	})
}

func sendLoadout(t *testing.T, c *testclient.Client, fighterID uint, spellIDs []int32) {
	t.Helper()
	sw := testclient.NewW()
	for _, id := range spellIDs {
		sw = sw.I32(id)
	}
	blob := sw.Bytes()
	payload := testclient.NewW().
		I64(int64(fighterID)).
		U16(0).                 // teamId
		U16(uint16(len(blob))). // spellsLen
		Raw(blob).
		U16(0). // cardsLen
		Bytes()
	_ = c.Send(3, opFighterInventoryUpdate, payload)
	c.DrainReceived(400 * time.Millisecond)
}

// TestForgedSpellLoadoutIsFiltered drives the real 6011 frame.
//
// castSpellByFighter gates casting on fighterKnowsSpell, which reads the list the
// CLIENT writes here - so a forged loadout let any fighter cast any spell in the
// table (boss, monster, cross-breed), bypassing breed and Sphere-Board
// progression, and it persisted across reconnect.
//
// Unit tests on the filter alone did not prove the handler applies it: mutations
// bypassing the filter at BOTH call sites survived the entire suite until this
// test existed.
func TestForgedSpellLoadoutIsFiltered(t *testing.T) {
	t.Parallel()
	st, addr := spellTestServer(t)
	c, coachID := dialLogin(t, addr, "spellforge", "SpellForge")
	c.DrainReceived(200 * time.Millisecond)

	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "Victim"}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}

	forged := []int32{20, 428, 193, 9999}
	sendLoadout(t, c, f.ID, forged)

	stored, err := st.Fighters.Get(f.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	for _, sp := range stored.Spells {
		for _, bad := range forged {
			if sp.SpellID == bad {
				t.Errorf("fighter kept forged spell %d (breed-1 fighter) - any "+
					"fighter can cast any spell in the table", bad)
			}
		}
	}
}

// TestLegitimateSpellLoadoutStillSaves guards against over-filtering: a breed-1
// fighter must still equip its own breed's spells, or the fix has broken the game
// rather than secured it.
func TestLegitimateSpellLoadoutStillSaves(t *testing.T) {
	t.Parallel()
	st, addr := spellTestServer(t)
	c, coachID := dialLogin(t, addr, "spelllegit", "SpellLegit")
	c.DrainReceived(200 * time.Millisecond)

	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "Legit"}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}

	sendLoadout(t, c, f.ID, []int32{10, 11})

	stored, err := st.Fighters.Get(f.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	if len(stored.Spells) != 2 {
		t.Errorf("own-breed loadout stored %d spells, want 2 - the filter is too "+
			"broad", len(stored.Spells))
	}
}

// TestForgedLoadoutForAnotherCoachIsRefused covers the ownership half of the same
// handler: the fighter is now resolved from the store before anything is decoded.
func TestForgedLoadoutForAnotherCoachIsRefused(t *testing.T) {
	t.Parallel()
	st, addr := spellTestServer(t)
	_, victimID := dialLogin(t, addr, "loadout_victim", "LoadVictim")
	attacker, _ := dialLogin(t, addr, "loadout_attacker", "LoadAttacker")
	attacker.DrainReceived(200 * time.Millisecond)

	victimFighter := &domain.Fighter{CoachID: uint(victimID), BreedID: 1, Name: "Theirs"}
	if err := st.Fighters.Create(victimFighter); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}

	sendLoadout(t, attacker, victimFighter.ID, []int32{10, 11})

	stored, err := st.Fighters.Get(victimFighter.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	if len(stored.Spells) != 0 {
		t.Errorf("attacker wrote %d spells onto another coach's fighter", len(stored.Spells))
	}
}
