package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestPostFightMetaReReadsXPFromTheDatabase closes the test gap SECURITY.md
// recorded: refreshAccumulatingFields' CALL SITE was not mutation-covered,
// because reaching it needs a real fight-end. It does not - the evolution-death
// harness already drives runPostFightMeta, which is the same path.
//
// The bug this guards: fr is the fighter SNAPSHOT taken when the fight was built
// and SaveProgress writes ABSOLUTE values, so anything that changed the stored
// row while the fight ran was silently reverted at fight end. That is how a
// mid-fight sphere purchase got its XP refunded while the fighter_spheres rows
// survived - the free talent tree.
func TestPostFightMetaReReadsXPFromTheDatabase(t *testing.T) {
	d, coachID, downed, survivor, st := evoDeathHarness(t)

	// Both fighters start with XP the fight snapshot will capture.
	for _, fr := range []*domain.Fighter{downed, survivor} {
		fr.XP = 5000
		if err := d.Store.Fighters.SaveProgress(fr); err != nil {
			t.Fatalf("seed xp: %v", err)
		}
	}

	// Build the fight: this is where the snapshot is taken (XP 5000).
	f := buildEvoFight(d, coachID, downed, survivor, true)

	// Now something spends 3000 XP in the DATABASE while the fight is running -
	// exactly what a sphere purchase does.
	if err := st.DB().Model(&domain.Fighter{}).Where("id = ?", survivor.ID).
		Update("xp", 2000).Error; err != nil {
		t.Fatalf("simulate mid-fight spend: %v", err)
	}

	// Fight ends.
	d.runPostFightMeta(f, 0)

	after, err := st.Fighters.Get(survivor.ID)
	if err != nil {
		t.Fatalf("reload: %v", err)
	}
	// The spend must survive. Without the re-read the snapshot's 5000 is written
	// back and the 3000 is refunded, so XP lands at or above 5000.
	if after.XP >= 5000 {
		t.Errorf("XP = %d after a mid-fight spend of 3000 from 5000: the fight "+
			"snapshot was written back and the spend was refunded", after.XP)
	}
	if after.XP < 2000 {
		t.Errorf("XP = %d, below the post-spend balance of 2000: the fight's own "+
			"reward was lost as well", after.XP)
	}
}
