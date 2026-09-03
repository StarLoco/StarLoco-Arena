package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const (
	opSphereBuy        = 23009
	opEquipmentUpdate2 = 5201
)

// TestSphereBuyIsRefusedWhileQueued covers the FREE TALENT TREE.
//
// BuySphere debits XP atomically in the database, but a running fight holds a
// fighter SNAPSHOT taken at fight-build time, and postFightReport.bank writes that
// stale row back wholesale at fight end - so every node bought during the fight
// had its cost refunded while the fighter_spheres rows (a different table, never
// touched by SaveProgress) survived. Queue, buy while the fight runs, repeat.
func TestSphereBuyIsRefusedWhileQueued(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "spherelock", "SphereLock")
	c.DrainReceived(200 * time.Millisecond)

	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "Evo",
		Budget: 100, XP: 100000, Evolution: true}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}
	before, err := st.Fighters.Get(f.ID)
	if err != nil {
		t.Fatalf("reload: %v", err)
	}

	// Queue for a fight: rosterLocked() must now be true.
	_ = c.Send(3, opOpponentSearch,
		testclient.NewW().U16(1).U16(0).I32(1).I64(int64(f.ID)).Bytes())
	c.DrainReceived(300 * time.Millisecond)

	// Try to buy a sphere node while queued. Assert the REFUSAL FRAME, not just
	// the absence of a purchase: a sphere buy fails for many reasons (node not on
	// the board, unreachable, unaffordable), so "nothing changed" would pass
	// whether or not the lock exists - a mutation removing the lock survived
	// exactly that version of this test.
	_ = c.Send(3, opSphereBuy, testclient.NewW().I64(int64(f.ID)).I32(1).Bytes())
	if !gotFightErrorCode(c, matchfinderOccuring, 2*time.Second) {
		t.Errorf("no matchfinder refusal (code %d) after a sphere buy while queued: "+
			"the roster lock did not fire", matchfinderOccuring)
	}
	c.DrainReceived(200 * time.Millisecond)

	after, err := st.Fighters.Get(f.ID)
	if err != nil {
		t.Fatalf("reload: %v", err)
	}
	if after.XP != before.XP {
		t.Errorf("XP changed while queued (%d -> %d): a sphere purchase during a "+
			"fight is refunded by the post-fight write-back", before.XP, after.XP)
	}
	var owned int64
	if err := st.DB().Model(&domain.FighterSphere{}).
		Where("fighter_id = ?", f.ID).Count(&owned).Error; err != nil {
		t.Fatalf("count spheres: %v", err)
	}
	if owned != 0 {
		t.Errorf("%d sphere node(s) were bought while queued", owned)
	}
}

// TestCoachEquipmentIsRefusedWhileQueued covers the settlement-time gear swap:
// runPostFightMeta reads the LIVE inventory at fight END, so equipping a
// wound-cancel / death-reduction / XP% set in the last seconds applied it to the
// outcome - in evolution mode, a way to dodge permanent death.
func TestCoachEquipmentIsRefusedWhileQueued(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "equiplock", "EquipLock")
	c.DrainReceived(200 * time.Millisecond)

	const tmpl = int32(4321)
	if err := st.DB().Create(&domain.CoachCard{
		CoachID: uint(coachID), TemplateID: tmpl, Quantity: 1, Pos: 0,
	}).Error; err != nil {
		t.Fatalf("seed card: %v", err)
	}
	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "F", Budget: 100}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}

	_ = c.Send(3, opOpponentSearch,
		testclient.NewW().U16(1).U16(0).I32(1).I64(int64(f.ID)).Bytes())
	c.DrainReceived(300 * time.Millisecond)

	w := testclient.NewW()
	for i := 0; i < 14; i++ {
		if i == 0 {
			w = w.I32(tmpl)
		} else {
			w = w.I32(0)
		}
	}
	_ = c.Send(3, opEquipmentUpdate2, w.Bytes())
	c.DrainReceived(400 * time.Millisecond)

	var rows []domain.CoachCard
	if err := st.DB().Where("coach_id = ? AND template_id = ?", coachID, tmpl).
		Find(&rows).Error; err != nil {
		t.Fatalf("reload inventory: %v", err)
	}
	for _, r := range rows {
		if r.Pos != 0 {
			t.Errorf("card was equipped (Pos %d) while queued: post-fight set "+
				"bonuses are read at fight END, so this changes the outcome", r.Pos)
		}
	}
}

const (
	opFightCreationError = 26310
	matchfinderOccuring  = 69
)

// gotFightErrorCode waits for a 26310 carrying the given code.
// Layout: [i64 fightId][i8 errorCode].
func gotFightErrorCode(c *testclient.Client, code byte, d time.Duration) bool {
	deadline := time.Now().Add(d)
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return false
		}
		if f.Opcode == opFightCreationError && len(f.Payload) >= 9 && f.Payload[8] == code {
			return true
		}
	}
	return false
}
