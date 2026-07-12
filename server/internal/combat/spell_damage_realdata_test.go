package combat

import (
	"os"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// TestSpell169DealsDamageToTargetedFighter reproduces the reported bug:
// casting spell 169 (an HP-leech damage spell) on an ENEMY fighter runs the
// animation but deals no damage. Drives the real handleSpellCast pipeline
// with real spell data.
func TestSpell169DealsDamageToTargetedFighter(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/spells.dat"); err != nil {
		t.Skipf("real data dir not available (%v), skipping", err)
	}
	store := gamedata.NewStore(dataDir)

	// newTestFightForEffects already sets f.rng to a seeded RNG.
	f, a, b := newTestFightForEffects(t)
	f.data = store
	f.setPhase(PhaseAction)

	// Place caster and enemy adjacent; make caster the current fighter and
	// give it the spell + enough AP.
	a.Position = Point3{X: 5, Y: 5, Z: -4}
	b.Position = Point3{X: 5, Y: 6, Z: -4} // adjacent, distance 1
	a.SpellIDs = []int32{169}
	a.Characteristics[AP].Value = 10
	a.Characteristics[AP].Max = 10
	b.Characteristics[HP].Value = 200
	b.Characteristics[HP].Max = 200
	f.Timeline = NewTimeline([]*Fighter{a, b})
	// Start the timeline so `a` (higher INIT) becomes the current fighter,
	// which handleSpellCast requires.
	cur, _ := f.Timeline.StartNextTurn()
	if cur != a {
		t.Fatalf("expected fighter a to be current, got %v", cur)
	}
	f.currentFighterID.Store(a.ID)

	hpBefore := b.Characteristic(HP)
	bc := f.broadcaster.(*fakeBroadcaster)

	f.handleSpellCast(cmdSpellCast{
		RequesterCoachID: a.CoachID,
		FighterID:        a.ID,
		SpellID:          169,
		Target:           b.Position, // targeting the enemy's cell
	})

	if got := b.Characteristic(HP); got >= hpBefore {
		t.Errorf("spell 169 dealt no damage: target HP %d -> %d (want a decrease)", hpBefore, got)
	}

	// The damage RUNNING_EFFECT_ACTION must carry a NON-ZERO effect id +
	// generic id, or the client drops the packet (getObjectFromId(0)=null)
	// and never shows the damage -- the exact "animation runs but no
	// damage" symptom. The last RUNNING_EFFECT_ACTION to fighter a's coach
	// is one of the HP-leech hits (id 10, the leech effect).
	fr, ok := lastRunningEffectFrame(t, bc, a.CoachID)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for the damage")
	}
	if id := runningEffectIDOf(t, fr); id == 0 {
		t.Errorf("damage RUNNING_EFFECT_ACTION effect id = 0 -> client drops the packet, no damage shown")
	}
	if gid := genericEffectIDOf(t, fr); gid == 0 {
		t.Errorf("damage RUNNING_EFFECT_ACTION GenericEffectID = 0 -> client can't resolve the effect")
	}
}
