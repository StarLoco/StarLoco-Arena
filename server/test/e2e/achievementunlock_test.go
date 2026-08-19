package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const opAchievementUnlocked = 22000 // S2C ade_0: [i16 achievementId]

// achievementServer starts a server whose catalogue is synthetic, so these tests
// assert the ENGINE rather than the shipped data (which is covered byte-exactly
// in internal/gamedata).
func achievementServer(t *testing.T) string {
	t.Helper()
	_, addr := testServerWithDeps(t, func(d *game.Deps) {
		d.Achievements = gamedata.NewAchievements(
			// Completes when criterion 221 reaches 2.
			&gamedata.Achievement{
				ID: 7001, Points: 5,
				Conditions: []gamedata.AchievementCondition{{StatID: 221, Threshold: 2}},
			},
			// Same trigger but hidden: must never be announced.
			&gamedata.Achievement{
				ID: 7002, Points: 5, Hidden: true,
				Conditions: []gamedata.AchievementCondition{{StatID: 221, Threshold: 2}},
			},
			// Needs a card nobody owns: must never complete.
			&gamedata.Achievement{
				ID: 7003, Points: 5,
				Conditions: []gamedata.AchievementCondition{{StatID: 221, Threshold: 2}},
				Cards:      []int32{123456},
			},
		)
	})
	return addr
}

func setCriterion(c *testclient.Client, statID, value uint16) {
	_ = c.Send(2, opStatisticUpdate, testclient.NewW().U16(statID).U8(1).U16(value).Bytes())
}

// TestAchievementUnlockIsAnnounced: crossing the threshold produces 22000.
func TestAchievementUnlockIsAnnounced(t *testing.T) {
	addr := achievementServer(t)
	c, _ := dialLogin(t, addr, "ach_unlock", "AchUnlock")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	// Below the threshold: nothing should fire.
	setCriterion(c, 221, 1)
	if f, _, err := c.WaitFor(opAchievementUnlocked, 700*time.Millisecond); err == nil {
		t.Fatalf("achievement announced below its threshold (payload %x)", f.Payload)
	}

	setCriterion(c, 221, 2)
	f, _, err := c.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no AchievementUnlocked(22000) after the criterion was met: %v", err)
	}
	if got := testclient.NewR(f.Payload).U16(); got != 7001 {
		t.Errorf("announced achievement %d, want 7001", got)
	}
}

// TestAchievementUnlockFiresOnce guards the whole reason the unlock table exists.
// Completion is recomputed from the criteria every time, so without the record
// the player would be re-toasted on every evaluation — including every login.
func TestAchievementUnlockFiresOnce(t *testing.T) {
	addr := achievementServer(t)
	c, _ := dialLogin(t, addr, "ach_once", "AchOnce")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	setCriterion(c, 221, 2)
	if _, _, err := c.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout); err != nil {
		t.Fatalf("first unlock not announced: %v", err)
	}
	// Re-assert the same criterion: the achievement is still complete, but it has
	// already been announced.
	setCriterion(c, 221, 3)
	if f, _, err := c.WaitFor(opAchievementUnlocked, 700*time.Millisecond); err == nil {
		t.Errorf("achievement announced twice (id %d): the unlock record is not "+
			"suppressing repeats", testclient.NewR(f.Payload).U16())
	}
}

// TestAchievementUnlockSurvivesRelog: the record is persistent, so logging back in
// must not re-announce. This is the case a purely in-memory guard would pass and
// a real player would notice immediately.
func TestAchievementUnlockSurvivesRelog(t *testing.T) {
	addr := achievementServer(t)
	c, _ := dialLogin(t, addr, "ach_relog", "AchRelog")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	setCriterion(c, 221, 2)
	if _, _, err := c.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout); err != nil {
		t.Fatalf("first unlock not announced: %v", err)
	}
	_ = c.Close()

	// Same login: the account (and its coach) already exist.
	c2, _ := dialLogin(t, addr, "ach_relog", "AchRelog")
	reachWorld(t, c2)
	if f, _, err := c2.WaitFor(opAchievementUnlocked, 900*time.Millisecond); err == nil {
		t.Errorf("achievement %d re-announced after relog", testclient.NewR(f.Payload).U16())
	}
}

// TestHiddenAchievementIsNotAnnounced: a hidden record is a no-op client-side
// (zN gates its whole 22000 body on !isHidden), so sending one is pure noise.
// 7002 shares 7001's trigger, so exactly one frame must arrive, not two.
func TestHiddenAchievementIsNotAnnounced(t *testing.T) {
	addr := achievementServer(t)
	c, _ := dialLogin(t, addr, "ach_hidden", "AchHidden")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	setCriterion(c, 221, 2)
	f, _, err := c.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no unlock announced: %v", err)
	}
	if got := testclient.NewR(f.Payload).U16(); got != 7001 {
		t.Fatalf("announced %d, want 7001", got)
	}
	if f2, _, err := c.WaitFor(opAchievementUnlocked, 700*time.Millisecond); err == nil {
		t.Errorf("a second unlock (%d) was announced: the hidden achievement "+
			"should have been recorded silently", testclient.NewR(f2.Payload).U16())
	}
}

// TestCardGatedAchievementStaysLocked: 7003 has the same criterion as 7001 but
// also requires a card, so meeting the criterion alone must not unlock it.
func TestCardGatedAchievementStaysLocked(t *testing.T) {
	addr := achievementServer(t)
	c, _ := dialLogin(t, addr, "ach_card", "AchCard")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	setCriterion(c, 221, 2)
	seen := map[uint16]bool{}
	deadline := time.Now().Add(1200 * time.Millisecond)
	for time.Now().Before(deadline) {
		f, _, err := c.WaitFor(opAchievementUnlocked, 300*time.Millisecond)
		if err != nil {
			break
		}
		seen[testclient.NewR(f.Payload).U16()] = true
	}
	if seen[7003] {
		t.Error("a card-gated achievement unlocked without the card")
	}
	if !seen[7001] {
		t.Error("7001 should still have unlocked")
	}
}
