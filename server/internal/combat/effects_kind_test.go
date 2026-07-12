package combat

import (
	"encoding/binary"
	"math/rand"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// runningEffectIDOf decodes the runningEffectId field out of a
// RUNNING_EFFECT_ACTION(8120) OutboundFrame payload, whose layout is:
// uniqueID(int32) + triggeringID(int32) + mustBeExecutedNow(bool) +
// triggered(bool) + runningEffectId(int32) + ... (see
// buildRunningEffectAction). The id lives at byte offset 10.
func runningEffectIDOf(t *testing.T, fr protocol.OutboundFrame) int32 {
	t.Helper()
	if len(fr.Payload) < 14 {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	return int32(binary.BigEndian.Uint32(fr.Payload[10:14]))
}

// RUNNING_EFFECT_ACTION(8120) payload field offsets (see
// buildRunningEffectAction): uniqueID(4)+triggeringID(4)+mustBeExecutedNow(1)
// +triggered(1)+runningEffectId(4)+genericEffectId(4)+casterId(8)+targetId(8)
// +cellX(4)+cellY(4)+cellZ(2)+value(4)+containerType(4)+containerId(8).
const (
	reoMustBeExecutedNow = 8
	reoGenericEffectID   = 14
	reoContainerType     = 48
	reoContainerID       = 52
	reoTotalLen          = 60
)

// mustBeExecutedNowOf decodes the mustBeExecutedNow bool (byte offset 8,
// right after uniqueID + triggeringID) from a RUNNING_EFFECT_ACTION frame.
// true means the client runs the effect the instant the packet arrives;
// false means it's queued to play at the cast script's hit frame.
func mustBeExecutedNowOf(t *testing.T, fr protocol.OutboundFrame) bool {
	t.Helper()
	if len(fr.Payload) <= reoMustBeExecutedNow {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	return fr.Payload[reoMustBeExecutedNow] != 0
}

func genericEffectIDOf(t *testing.T, fr protocol.OutboundFrame) int32 {
	t.Helper()
	if len(fr.Payload) < reoTotalLen {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	return int32(binary.BigEndian.Uint32(fr.Payload[reoGenericEffectID : reoGenericEffectID+4]))
}

func containerOf(t *testing.T, fr protocol.OutboundFrame) (int32, int64) {
	t.Helper()
	if len(fr.Payload) < reoTotalLen {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	ct := int32(binary.BigEndian.Uint32(fr.Payload[reoContainerType : reoContainerType+4]))
	cid := int64(binary.BigEndian.Uint64(fr.Payload[reoContainerID : reoContainerID+8]))
	return ct, cid
}

// RUNNING_EFFECT_ACTION targetId (offset 26) and value (offset 44) field
// decoders (see the field-offset comment above): targetId is an int64,
// value an int32. Offsets: uniqueID(4)+triggeringID(4)+mustBeExecutedNow(1)
// +triggered(1)+runningEffectId(4)+genericEffectId(4)+casterId(8) = 26, then
// targetId(8) ends at 34, cellX(4)+cellY(4)+cellZ(2) end at 44 = value.
const (
	reoTargetID = 26
	reoValue    = 44
)

func targetIDOf(t *testing.T, fr protocol.OutboundFrame) int64 {
	t.Helper()
	if len(fr.Payload) < reoTotalLen {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	return int64(binary.BigEndian.Uint64(fr.Payload[reoTargetID : reoTargetID+8]))
}

func valueOf(t *testing.T, fr protocol.OutboundFrame) int32 {
	t.Helper()
	if len(fr.Payload) < reoTotalLen {
		t.Fatalf("RUNNING_EFFECT_ACTION payload too short: %d bytes", len(fr.Payload))
	}
	return int32(binary.BigEndian.Uint32(fr.Payload[reoValue : reoValue+4]))
}

// triggeringIDOf decodes the fight-action header's triggeringActionUniqueId
// (int32 at offset 4, right after uniqueId). -1 means "not triggered by
// another action".
func triggeringIDOf(t *testing.T, fr protocol.OutboundFrame) int32 {
	t.Helper()
	if len(fr.Payload) < 8 {
		t.Fatalf("fight-action payload too short: %d bytes", len(fr.Payload))
	}
	return int32(binary.BigEndian.Uint32(fr.Payload[4:8]))
}

// lastRunningEffectFrame returns the most recent RUNNING_EFFECT_ACTION
// frame sent to any of the given coaches.
func lastRunningEffectFrame(t *testing.T, bc *fakeBroadcaster, coachID uint) (protocol.OutboundFrame, bool) {
	t.Helper()
	return bc.lastFrame(coachID, protocol.SendRunningEffectAction)
}

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase N: one
// dedicated unit test per EffectKind in effects_registry.go, exercising
// applyRunningEffect directly (bypassing target-resolution/AoE) so each
// kind's mechanical behavior is pinned down in isolation, independent of
// the broader integration-level fight_test.go coverage.

// newTestFight builds a minimal two-fighter Fight for direct
// applyRunningEffect calls -- no actor goroutine involved (tests call
// Fight methods synchronously from the test goroutine, which is safe
// since Run() is never started here).
func newTestFightForEffects(t *testing.T) (*Fight, *Fighter, *Fighter) {
	t.Helper()
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	f.rng = rand.New(rand.NewSource(1))
	return f, a, b
}

func TestEffectHPLoss_DealsDamageToOpponent(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectHPLoss, Elem: ElementPhysical}
	f.applyRunningEffect(a, b, def, effectDefWithParams(10), -1)

	if got := b.Characteristic(HP); got != 40 {
		t.Errorf("target HP after HPLoss(10) = %d, want 40", got)
	}
}

func TestEffectHPLoss_IgnoredBetweenNonOpponents(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	// a caster affecting a same-team ally is not "self" and not an
	// opponent -- HPLoss must not apply (AreOpponents(caster,target) is
	// false and caster != target).
	ally := NewFighterFromBreed(9, a.TeamID, BreedFeca, "Ally", 0, 0)
	ally.Characteristics[HP].Value = 50
	ally.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectHPLoss, Elem: ElementPhysical}
	f.applyRunningEffect(a, ally, def, effectDefWithParams(10), -1)

	if got := ally.Characteristic(HP); got != 50 {
		t.Errorf("ally HP after HPLoss from teammate = %d, want unchanged 50 (must not apply to non-opponent, non-self)", got)
	}
}

func TestEffectHPGain_HealsTarget(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = b
	a.Characteristics[HP].Value = 40
	a.Characteristics[HP].Max = 100

	def := runningEffectDef{Kind: EffectHPGain}
	f.applyRunningEffect(a, a, def, effectDefWithParams(15), -1)

	if got := a.Characteristic(HP); got != 55 {
		t.Errorf("HP after HPGain(15) = %d, want 55", got)
	}
}

// TestEmptyArea_ShakingHitsAllIncludingCaster is the regression for Sadida's
// Shaking/Tremblement ("inflicts a small damage to ALL characters, caster
// also affected"). Its damage effect carries AreaShape 32767 (EMPTY) with the
// permissive [0] target mask; EMPTY must resolve to EVERY living fighter (not
// zero, which made Tremblement deal no damage), then apply to all of them.
func TestEmptyArea_ShakingHitsAllIncludingCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[HP].Value, a.Characteristics[HP].Max = 50, 50
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 50, 50

	// Shaking: action 3 (earth dmg), 4 damage, EMPTY area, targets [0].
	eff := gamedata.EffectDef{
		ID: 1, ActionID: 3, Params: []float32{4},
		AreaShape: int16(AreaEmpty), Targets: []int32{0},
	}
	f.executeOneEffect(a, eff, a.Position, -1)

	if got := a.Characteristic(HP); got >= 50 {
		t.Errorf("caster HP after Shaking = %d, want < 50 (caster is also affected)", got)
	}
	if got := b.Characteristic(HP); got >= 50 {
		t.Errorf("enemy HP after Shaking = %d, want < 50 (all characters affected)", got)
	}
}

// TestEmptyArea_SelfBuffOnlyHitsCaster verifies an EMPTY-area effect with a
// caster-only target mask ([2] = IS_CASTER) lands ONLY on the caster -- e.g.
// Iop's Vitalité (max-HP buff) and the other self-buffs that were silent
// no-ops when EMPTY resolved to zero targets. The EMPTY="all" expansion plus
// the target-condition filter must yield exactly the caster.
func TestEmptyArea_SelfBuffOnlyHitsCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[AP].Value, a.Characteristics[AP].Max = 6, 6
	b.Characteristics[AP].Value, b.Characteristics[AP].Max = 6, 6

	// A caster-only (+2 AP) CharacBuff (action 13), EMPTY area, targets [2].
	eff := gamedata.EffectDef{
		ID: 1, ActionID: 13, Params: []float32{2},
		AreaShape: int16(AreaEmpty), Targets: []int32{condIsCaster},
	}
	f.executeOneEffect(a, eff, a.Position, -1)

	if got := a.Characteristics[AP].Max; got != 8 {
		t.Errorf("caster AP.Max after self-buff = %d, want 8", got)
	}
	if got := b.Characteristics[AP].Max; got != 6 {
		t.Errorf("enemy AP.Max after a CASTER-only buff = %d, want unchanged 6", got)
	}
}

// TestEffectHPGain_CapsAtMaxAndBroadcastsActualHeal is the regression for
// "healing goes above max HP". A fighter at 55/60 healed for 8 must end at 60
// (not 63), and the RUNNING_EFFECT_ACTION must carry the ACTUAL heal applied
// (5), not the raw 8 -- otherwise the client re-applies 8 to its own HP bar
// and overshoots.
func TestEffectHPGain_CapsAtMaxAndBroadcastsActualHeal(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[HP].Max = 60
	a.Characteristics[HP].Value = 55

	def := runningEffectDef{Kind: EffectHPGain}
	f.applyRunningEffect(a, a, def, effectDefWithParams(8), -1)

	if got := a.Characteristic(HP); got != 60 {
		t.Fatalf("HP after HPGain(8) on 55/60 = %d, want 60 (capped)", got)
	}
	fr, ok := lastRunningEffectFrame(t, f.broadcaster.(*fakeBroadcaster), a.CoachID)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for the heal")
	}
	if got := valueOf(t, fr); got != 5 {
		t.Errorf("broadcast heal value = %d, want 5 (actual HP restored, not the raw 8)", got)
	}
}

// TestEffectHPLoss_CapsAtZeroAndBroadcastsActualDamage is the damage-side
// mirror: a fighter with 5 HP hit for 10 must drop to 0, and the broadcast
// must carry the ACTUAL 5 HP removed (not the raw 10), so the client's HP bar
// and floating damage number match the real loss.
func TestEffectHPLoss_CapsAtZeroAndBroadcastsActualDamage(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 5

	def := runningEffectDef{Kind: EffectHPLoss, Elem: ElementPhysical}
	f.applyRunningEffect(a, b, def, effectDefWithParams(10), -1)

	if got := b.Characteristic(HP); got != 0 {
		t.Fatalf("HP after 10 damage on 5 HP = %d, want 0 (floored)", got)
	}
	fr, ok := lastRunningEffectFrame(t, f.broadcaster.(*fakeBroadcaster), a.CoachID)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for the damage")
	}
	if got := valueOf(t, fr); got != 5 {
		t.Errorf("broadcast damage value = %d, want 5 (actual HP removed, not the raw 10)", got)
	}
}

func TestEffectHPLeech_DamagesTargetAndHealsCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[HP].Value = 20
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectHPLeech, Elem: ElementPhysical}
	f.applyRunningEffect(a, b, def, effectDefWithParams(10), -1)

	if got := b.Characteristic(HP); got != 40 {
		t.Errorf("target HP after HPLeech(10) = %d, want 40", got)
	}
	if got := a.Characteristic(HP); got != 30 {
		t.Errorf("caster HP after HPLeech(10) = %d, want 30 (20 + 10 leeched)", got)
	}
}

// TestEffectHPLeech_WorksOnAllies verifies Sram's Life Theft can steal HP
// from an ALLY too (wiki: "possible to steal from both enemies as well as
// allies including summons"). The decompiled HPLeech.execute has no opponent
// gate, so leeching a teammate damages them AND heals the caster.
func TestEffectHPLeech_WorksOnAllies(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	ally := NewFighterFromBreed(9, a.TeamID, BreedFeca, "Ally", 0, 0)
	f.registerFighter(ally, 100)
	f.Timeline.order = append(f.Timeline.order, ally)
	ally.Characteristics[HP].Value = 50
	ally.Characteristics[HP].Max = 50
	a.Characteristics[HP].Value = 20
	a.Characteristics[HP].Max = 100

	def := runningEffectDef{Kind: EffectHPLeech, Elem: ElementPhysical}
	f.applyRunningEffect(a, ally, def, effectDefWithParams(10), -1)

	if got := ally.Characteristic(HP); got != 40 {
		t.Errorf("ally HP after being leeched = %d, want 40 (took 10 damage)", got)
	}
	if got := a.Characteristic(HP); got != 30 {
		t.Errorf("caster HP after leeching an ally = %d, want 30 (20 + 10 leeched)", got)
	}
}

// TestEffectHPLeech_CappedByTargetHP verifies the leech steals no more life
// than the target actually has (HPLeech.execute's Math.min(value, targetHP)):
// leeching 30 from a 5-HP target heals the caster by only 5.
func TestEffectHPLeech_CappedByTargetHP(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[HP].Value = 20
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 5
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectHPLeech, Elem: ElementPhysical}
	f.applyRunningEffect(a, b, def, effectDefWithParams(30), -1)

	if got := b.Characteristic(HP); got != 0 {
		t.Errorf("target HP after over-leech = %d, want 0", got)
	}
	if got := a.Characteristic(HP); got != 25 {
		t.Errorf("caster HP after leeching a 5-HP target = %d, want 25 (20 + 5, capped by target HP)", got)
	}
}

func TestEffectHPDebuff_LowersMaxAndClampsValue(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = a
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectHPDebuff}
	f.applyRunningEffect(a, b, def, effectDefWithParams(-20), -1)

	if got := b.Characteristics[HP].Max; got != 30 {
		t.Errorf("target HP.Max after HPDebuff(-20) = %d, want 30", got)
	}
	if got := b.Characteristic(HP); got != 30 {
		t.Errorf("target HP after Max shrank below it = %d, want clamped to 30", got)
	}
}

func TestEffectHPDebuff_MaxNeverGoesNegative(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 10
	b.Characteristics[HP].Max = 10

	def := runningEffectDef{Kind: EffectHPDebuff}
	f.applyRunningEffect(a, b, def, effectDefWithParams(-1000), -1)

	if got := b.Characteristics[HP].Max; got != 0 {
		t.Errorf("target HP.Max after huge HPDebuff = %d, want floored at 0", got)
	}
}

func TestEffectCharacLoss_PlainCurrentValueSubtract(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[MP].Value = 3
	b.Characteristics[MP].Max = 3

	def := runningEffectDef{Kind: EffectCharacLoss, Charc: MP}
	f.applyRunningEffect(a, b, def, effectDefWithParams(2), -1)

	if got := b.Characteristic(MP); got != 1 {
		t.Errorf("MP after CharacLoss(2) = %d, want 1", got)
	}
	if got := b.Characteristics[MP].Max; got != 3 {
		t.Errorf("MP.Max after CharacLoss = %d, want unchanged 3 (CharacLoss never touches Max)", got)
	}
}

func TestEffectCharacPoison_OneShotDamageTick(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	f.applyRunningEffect(a, b, def, effectDefWithParams(8), -1)

	if got := b.Characteristic(HP); got != 42 {
		t.Errorf("target HP after one poison tick = %d, want 42", got)
	}

	// Confirms the currently-known limitation (roadmap §8.11 item 2):
	// applying again is a distinct call (nothing auto-re-ticks). This
	// pins today's "instant, one-shot only" behavior so Phase J's future
	// duration-based rework has a clear before/after regression marker.
	f.applyRunningEffect(a, b, def, effectDefWithParams(8), -1)
	if got := b.Characteristic(HP); got != 34 {
		t.Errorf("target HP after second explicit poison application = %d, want 34 (each call is independent, no auto-retick yet)", got)
	}
}

func TestEffectAPUse_SpendsAPIgnoringResistance(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[AP].Value = 6
	b.Characteristics[AP].Max = 6

	def := runningEffectDef{Kind: EffectAPUse}
	f.applyRunningEffect(a, b, def, effectDefWithParams(2), -1)

	if got := b.Characteristic(AP); got != 4 {
		t.Errorf("AP after APUse(2) = %d, want 4", got)
	}
}

func TestEffectMPUse_ResistedByResMPLoss(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[MP].Value = 3
	b.Characteristics[MP].Max = 3
	b.Characteristics[ResMPLoss].Value = 50 // 50% resist

	def := runningEffectDef{Kind: EffectMPUse}
	f.applyRunningEffect(a, b, def, effectDefWithParams(2), -1)

	// 2 requested, 50% resisted -> 1 actually lost.
	if got := b.Characteristic(MP); got != 2 {
		t.Errorf("MP after MPUse(2) with 50%% ResMPLoss = %d, want 2 (only 1 lost)", got)
	}
}

func TestEffectPush_MovesTargetAwayAndDealsFallDamageIfBlocked(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	// Nothing blocks the push -- b should simply move further east.
	eff := gamedata.EffectDef{Params: []float32{2}}
	f.applyPushPull(a, b, eff, true, -1)

	if b.Position != (Point3{X: 3, Y: 0}) {
		t.Errorf("target position after push distance 2 = %v, want {3,0}", b.Position)
	}
	if got := b.Characteristic(HP); got != 50 {
		t.Errorf("target HP after unobstructed push = %d, want unchanged 50 (no fall damage)", got)
	}
}

func TestEffectPush_BlockedByObstacleDealsFallDamage(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	// A blocker fighter sits at (2,0), directly in the push path.
	blocker := NewFighterFromBreed(9, 9, BreedFeca, "Blocker", 0, 0)
	blocker.Position = Point3{X: 2, Y: 0}
	f.registerFighter(blocker, 999)
	f.Timeline.order = append(f.Timeline.order, blocker)

	eff := gamedata.EffectDef{Params: []float32{2}}
	f.applyPushPull(a, b, eff, true, -1)

	if b.Position != (Point3{X: 1, Y: 0}) {
		t.Errorf("target position after blocked push = %v, want unchanged {1,0} (stopped before the blocker)", b.Position)
	}
	// distance=2, stopped at i=0 (first step blocked) -> fallDamage = 3*(2-0) = 6.
	if got := b.Characteristic(HP); got != 44 {
		t.Errorf("target HP after blocked push = %d, want 44 (50 - 6 fall damage)", got)
	}
}

// TestEffectPush_ObstacleFighterAlsoTakesDamage is the wiki rule "Fearing
// someone into another character will cause both to lose 3 damage each": when
// a push is stopped by another fighter, BOTH the pushed target and the
// blocking fighter take the fall damage.
func TestEffectPush_ObstacleFighterAlsoTakesDamage(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 50, 50

	blocker := NewFighterFromBreed(9, 9, BreedFeca, "Blocker", 0, 0)
	blocker.Position = Point3{X: 2, Y: 0}
	blocker.Characteristics[HP].Value, blocker.Characteristics[HP].Max = 50, 50
	f.registerFighter(blocker, 999)
	f.Timeline.order = append(f.Timeline.order, blocker)

	// distance 1 push, blocked immediately -> cellLeft=1, obstacle -> 3 dmg.
	eff := gamedata.EffectDef{Params: []float32{1}}
	f.applyPushPull(a, b, eff, true, -1)

	if got := b.Characteristic(HP); got != 47 {
		t.Errorf("pushed target HP = %d, want 47 (50 - 3)", got)
	}
	if got := blocker.Characteristic(HP); got != 47 {
		t.Errorf("obstacle fighter HP = %d, want 47 (both lose 3 -- wiki rule)", got)
	}
}

// TestEffectPush_IntoVoidDealsSixPerCell verifies the wiki's "Fearing a
// character into a wall will hurt him 6 damage": a push stopped by the map
// edge / a non-walkable cell (the reference's stoppedOnVoid case) deals 6 HP
// per remaining cell, not the 3 an obstacle fighter deals.
func TestEffectPush_IntoVoidDealsSixPerCell(t *testing.T) {
	f, _ := realMapFight(t) // attaches real map data so IsWalkable rejects walls
	a := f.Timeline.Order()[0]
	b := f.Timeline.Order()[1]

	// Find a walkable cell whose east/west/north/south neighbor (a single
	// Direction8 step) is NON-walkable, so a push in that direction hits the
	// void immediately.
	open, wallDir, ok := findWalkableWithNonWalkableNeighbor(f)
	if !ok {
		t.Skip("no walkable cell with a non-walkable cardinal neighbor found on the map")
	}
	// Put the caster on the opposite side so directionFrom(caster,b)=wallDir.
	opp := open.Step(oppositeDir(wallDir))
	a.Position = opp
	b.Position = open
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 50, 50

	eff := gamedata.EffectDef{Params: []float32{1}} // push 1
	f.applyPushPull(a, b, eff, true, -1)

	if b.Position != open {
		t.Fatalf("target should not have moved into the wall, pos=%v want %v", b.Position, open)
	}
	if got := b.Characteristic(HP); got != 44 {
		t.Errorf("target HP after push into wall = %d, want 44 (50 - 6 void damage)", got)
	}
}

// findWalkableWithNonWalkableNeighbor scans the map for a walkable cell whose
// neighbor in some cardinal Direction8 is non-walkable (a wall/edge), used to
// set up a "push into the void" test. Returns the open cell and the direction
// toward the wall.
func findWalkableWithNonWalkableNeighbor(f *Fight) (Point3, Direction8, bool) {
	dirs := []Direction8{DirSouthEast, DirSouthWest, DirNorthWest, DirNorthEast}
	for x := int32(0); x < 40; x++ {
		for y := int32(0); y < 40; y++ {
			c := Point3{X: x, Y: y}
			if !f.IsWalkable(c) {
				continue
			}
			for _, d := range dirs {
				if !f.IsWalkable(c.Step(d)) && f.IsWalkable(c.Step(oppositeDir(d))) {
					return c, d, true
				}
			}
		}
	}
	return Point3{}, 0, false
}

func oppositeDir(d Direction8) Direction8 {
	switch d {
	case DirSouthEast:
		return DirNorthWest
	case DirNorthWest:
		return DirSouthEast
	case DirSouthWest:
		return DirNorthEast
	case DirNorthEast:
		return DirSouthWest
	case DirEast:
		return DirWest
	case DirWest:
		return DirEast
	case DirNorth:
		return DirSouth
	default:
		return DirNorth
	}
}

// TestEffectPush_TriggersTrapItPassesThrough is the wiki's "push someone into
// a trap": moving the target across an effect-area's cell during a push fires
// that trap (checkInAndOut), just like walking into it.
func TestEffectPush_TriggersTrapItPassesThrough(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 100, 100

	// A trap on (3,0): b pushed 2 east ends on it -> trap deals 10.
	f.effectAreas = newEffectAreaManager()
	f.effectAreas.Add(&EffectArea{
		ID:                  1,
		Position:            Point3{X: 3, Y: 0},
		Area:                AreaOfEffect{Shape: AreaPoint},
		Caster:              a,
		Effects:             []gamedata.EffectDef{{ActionID: 1, Params: []float32{10}}},
		maxExecutionCount:   1,
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
	})

	eff := gamedata.EffectDef{Params: []float32{2}}
	f.applyPushPull(a, b, eff, true, -1)

	if b.Position != (Point3{X: 3, Y: 0}) {
		t.Fatalf("target position after push = %v, want {3,0}", b.Position)
	}
	if got := b.Characteristic(HP); got != 90 {
		t.Errorf("target HP after being pushed onto a trap = %d, want 90 (trap dealt 10)", got)
	}
}

func TestEffectPush_StabilizedTargetIsImmune(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Properties |= PropertyStabilized

	eff := gamedata.EffectDef{Params: []float32{2}}
	f.applyPushPull(a, b, eff, true, -1)

	if b.Position != (Point3{X: 1, Y: 0}) {
		t.Errorf("stabilized target position after push = %v, want unchanged {1,0}", b.Position)
	}
}

func TestEffectPull_MovesTargetTowardCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 3, Y: 0}

	eff := gamedata.EffectDef{Params: []float32{2}}
	f.applyPushPull(a, b, eff, false, -1)

	if b.Position != (Point3{X: 1, Y: 0}) {
		t.Errorf("target position after pull distance 2 = %v, want {1,0} (moved toward caster)", b.Position)
	}
}

func TestEffectTeleport_MovesCasterToTargetCell(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = b
	a.Position = Point3{X: 0, Y: 0}
	dest := Point3{X: 5, Y: 7, Z: 2}

	eff := gamedata.EffectDef{ActionID: 39} // actionID 39 = EffectTeleport
	f.executeOneEffect(a, eff, dest, -1)

	if a.Position != dest {
		t.Errorf("caster position after Teleport = %v, want %v", a.Position, dest)
	}
}

func TestEffectExchangePosition_SwapsCasterAndTarget(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 5, Y: 5}

	def := runningEffectDef{Kind: EffectExchangePosition}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if a.Position != (Point3{X: 5, Y: 5}) {
		t.Errorf("caster position after ExchangePosition = %v, want {5,5}", a.Position)
	}
	if b.Position != (Point3{X: 0, Y: 0}) {
		t.Errorf("target position after ExchangePosition = %v, want {0,0}", b.Position)
	}
}

func TestEffectRoot_SetsRootedProperty(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	def := runningEffectDef{Kind: EffectRoot}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if !b.Properties.Has(PropertyRooted) {
		t.Errorf("target Properties after Root = %v, want PropertyRooted set", b.Properties)
	}
}

// TestEffectRoot_TimedRevertsAtExpiry verifies Cra's Paralyzing Arrow root
// (dur[1,0]) clears after its duration rather than immobilizing the target
// for the whole fight (previously the root was permanent).
func TestEffectRoot_TimedRevertsAtExpiry(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	def := runningEffectDef{Kind: EffectRoot}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{Duration: []int32{1}}, -1)
	if !b.Properties.Has(PropertyRooted) {
		t.Fatalf("target should be rooted right after the spell")
	}
	f.tickActiveEffects(-1) // 1 -> 0 -> revert
	if b.Properties.Has(PropertyRooted) {
		t.Errorf("root should have cleared after its 1-round duration")
	}
}

func TestEffectStabilize_SetsStabilizedProperty(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	def := runningEffectDef{Kind: EffectStabilize}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if !b.Properties.Has(PropertyStabilized) {
		t.Errorf("target Properties after Stabilize = %v, want PropertyStabilized set", b.Properties)
	}
}

func TestEffectPetrified_SetsPetrifiedProperty(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	def := runningEffectDef{Kind: EffectPetrified}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if !b.Properties.Has(PropertyPetrified) {
		t.Errorf("target Properties after Petrified = %v, want PropertyPetrified set", b.Properties)
	}
}

// TestEffectPetrified_RollsPercentChance verifies action 96's param[0] is a
// PERCENT chance to apply (decompiled Petrified.computeValue), not always-on.
// param[0]=0 never petrifies; param[0]=100 always does. This is Enutrof
// Petrifaction's "20% chance of immobilizing".
func TestEffectPetrified_RollsPercentChance(t *testing.T) {
	// 0% -> never applies.
	f, a, b := newTestFightForEffects(t)
	def := runningEffectDef{Kind: EffectPetrified}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{Params: []float32{0, 0, 1}}, -1)
	if b.Properties.Has(PropertyPetrified) {
		t.Errorf("0%% Petrified must NOT apply, but PropertyPetrified is set")
	}

	// 100% -> always applies.
	f2, a2, b2 := newTestFightForEffects(t)
	f2.applyRunningEffect(a2, b2, def, gamedata.EffectDef{Params: []float32{100, 0, 1}}, -1)
	if !b2.Properties.Has(PropertyPetrified) {
		t.Errorf("100%% Petrified must apply, but PropertyPetrified is not set")
	}
}

// TestPetrifiedBlocksMovement verifies a petrified (or rooted) fighter cannot
// move under its own power -- handleFighterMove rejects the request, so the
// "immobilize" of Petrifaction actually has a gameplay effect.
func TestPetrifiedBlocksMovement(t *testing.T) {
	for _, prop := range []PropertyFlags{PropertyPetrified, PropertyRooted} {
		f, a, b := newTestFightForEffects(t)
		f.Timeline = NewTimeline([]*Fighter{a, b})
		f.Timeline.StartNextTurn()
		f.setPhase(PhaseAction)
		f.currentFighterID.Store(a.ID)
		a.Characteristics[MP].Value, a.Characteristics[MP].Max = 3, 3
		start := Point3{X: 5, Y: 5}
		a.Position = start
		a.Properties |= prop

		f.handleFighterMove(cmdFighterMove{RequesterCoachID: a.CoachID, FighterID: a.ID, Path: []Point3{{X: 6, Y: 5}}})
		if a.Position != start {
			t.Errorf("immobilized (%v) fighter moved to %v, want stay at %v", prop, a.Position, start)
		}
	}
}

func TestEffectSetInvisible_SetsInvisibleProperty(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	def := runningEffectDef{Kind: EffectSetInvisible}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if !b.Properties.Has(PropertyInvisible) {
		t.Errorf("target Properties after SetInvisible = %v, want PropertyInvisible set", b.Properties)
	}
}

func TestEffectSetVisible_ClearsInvisibleProperty(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Properties |= PropertyInvisible

	def := runningEffectDef{Kind: EffectSetVisible}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if b.Properties.Has(PropertyInvisible) {
		t.Errorf("target Properties after SetVisible = %v, want PropertyInvisible cleared", b.Properties)
	}
}

// TestEffectSetInvisible_TimedRevertsAtExpiry verifies invisibility from a
// spell with a finite Duration (Sram Invisibility dur[3,0], Eniripsa Wiping
// Word dur[2,0], etc.) is CLEARED at expiry rather than lasting the whole
// fight -- previously the duration was ignored.
func TestEffectSetInvisible_TimedRevertsAtExpiry(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	def := runningEffectDef{Kind: EffectSetInvisible}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{Duration: []int32{2}}, -1)
	if !b.Properties.Has(PropertyInvisible) {
		t.Fatalf("target should be invisible right after the spell")
	}

	f.tickActiveEffects(-1) // 2 -> 1
	if !b.Properties.Has(PropertyInvisible) {
		t.Errorf("invisibility should persist after 1 table-turn (2-round duration)")
	}
	f.tickActiveEffects(-1) // 1 -> 0 -> revert
	if b.Properties.Has(PropertyInvisible) {
		t.Errorf("invisibility should have expired after its 2-round duration")
	}
}

func TestEffectDeath_KillsTargetOutright(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 999
	b.Characteristics[HP].Max = 999

	def := runningEffectDef{Kind: EffectDeath}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if !b.IsDead {
		t.Errorf("target IsDead after EffectDeath = false, want true (regardless of remaining HP)")
	}
	// HP is bounded at a minimum of 0 (see characteristicBounds), so
	// driving it "below 0" clamps to exactly 0 rather than going
	// negative -- what matters is ShouldBeDead()/IsDead, not the raw
	// stored value.
	if got := b.Characteristic(HP); got != 0 {
		t.Errorf("target HP after EffectDeath = %d, want 0 (floored, but still lethal)", got)
	}
}

func TestEffectAutomaticEndTurn_EndsCurrentFightersTurnOnly(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	f.rng = rand.New(rand.NewSource(1))

	tl := NewTimeline([]*Fighter{a, b})
	f.Timeline = tl
	tl.StartNextTurn() // a's turn (higher init)

	def := runningEffectDef{Kind: EffectAutomaticEndTurn}
	// a is target AND current fighter -> should end a's turn (starts b's).
	f.applyRunningEffect(a, a, def, gamedata.EffectDef{}, -1)

	if got := f.Timeline.CurrentFighter(); got != b {
		t.Errorf("current fighter after AutomaticEndTurn on the active fighter = %v, want b (turn advanced)", got)
	}
}

func TestEffectAutomaticEndTurn_NoOpIfTargetIsNotCurrentFighter(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	f.rng = rand.New(rand.NewSource(1))

	tl := NewTimeline([]*Fighter{a, b})
	f.Timeline = tl
	tl.StartNextTurn() // a's turn

	def := runningEffectDef{Kind: EffectAutomaticEndTurn}
	// b is NOT the current fighter -- effect must be a no-op.
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if got := f.Timeline.CurrentFighter(); got != a {
		t.Errorf("current fighter after AutomaticEndTurn targeting a non-current fighter = %v, want unchanged a", got)
	}
}

func TestEffectSummon_InsertsNewFighterIntoTurnOrderAfterCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	beforeCount := len(f.Timeline.Order())
	// Summon effects target a raw CELL (bypassing target-fighter
	// resolution) -- exercise applySummon directly, the path
	// executeOneEffect routes EffectSummon/Double/Mirror to. The spawn cell
	// must be free (applySummon rejects occupied cells) -- (9,9) is empty.
	spawnAt := Point3{X: 9, Y: 9}
	f.applySummon(a, spawnAt, gamedata.EffectDef{ActionID: 67}, -1)

	order := f.Timeline.Order()
	if len(order) != beforeCount+1 {
		t.Fatalf("turn order length after Summon = %d, want %d", len(order), beforeCount+1)
	}
	// Summon must be inserted immediately after its father (a is order[0]
	// since it has the higher INIT in twoTeamFight).
	summon := order[1]
	if summon.Father != a {
		t.Errorf("summon.Father = %v, want caster a", summon.Father)
	}
	if summon.Position != spawnAt {
		t.Errorf("summon position = %v, want %v", summon.Position, spawnAt)
	}
}

// TestEffectSummon_WireCorrectness is the regression for the "summon freezes
// the client" bug (Sadida "La folle", spell 109, actionID 67, params=[4]).
// The client instantiates the summon ITSELF from the SUMMON
// RUNNING_EFFECT_ACTION (Summon.execute -> summonCreature -> summonFighter
// -> addMobile), so the server must:
//   - NOT also send an ACTOR_APPEAR (4102) for it (that double-adds the same
//     mobile id and wedges the client's action sequence), and
//   - send value = params[0] (the SummoningDefinition id the client resolves
//     via SummoningManager). value 0 makes summonCreature fail with
//     "SummoningDefinition id=0 est inconnue" so the doll never appears.
//
// The summon's id must also travel in the blob's target field (Summon
// overrides unserializeTarget to read it as m_newTargetId).
func TestEffectSummon_WireCorrectness(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	bc := f.broadcaster.(*fakeBroadcaster)

	spawnAt := Point3{X: 9, Y: 9}
	// actionID 67 = SUMMON; params[0]=4 is the SummoningDefinition id.
	f.applySummon(a, spawnAt, gamedata.EffectDef{ActionID: 67, Params: []float32{4}}, -1)

	summon := f.Timeline.Order()[1]

	// (1) NO ACTOR_APPEAR must be broadcast for a mid-fight summon.
	for _, op := range bc.opcodesFor(a.CoachID) {
		if op == protocol.SendActorAppear {
			t.Fatalf("ACTOR_APPEAR was broadcast for a summon -- the client double-adds the mobile and freezes; the SUMMON RUNNING_EFFECT_ACTION is the spawn message")
		}
	}

	// (2) the SUMMON RUNNING_EFFECT_ACTION must carry effect id 67, the
	// summon's id in the target field, and value = params[0] (=4).
	fr, ok := lastRunningEffectFrame(t, bc, a.CoachID)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for the summon")
	}
	if id := runningEffectIDOf(t, fr); id != 67 {
		t.Errorf("summon RUNNING_EFFECT_ACTION effect id = %d, want 67 (SUMMON)", id)
	}
	if got := targetIDOf(t, fr); got != summon.ID {
		t.Errorf("summon RUNNING_EFFECT_ACTION target id = %d, want the new summon's id %d (client reads it as m_newTargetId)", got, summon.ID)
	}
	if got := valueOf(t, fr); got != 4 {
		t.Errorf("summon RUNNING_EFFECT_ACTION value = %d, want 4 (params[0] = SummoningDefinition id); value 0 makes the client's summonCreature fail", got)
	}
}

// TestCastSpell_PrimaryEffectsHaveNoTriggeringAction is the regression for
// the "La folle animation loops, can't act" bug. A cast's own primary
// effects must carry triggeringActionUniqueId = -1 on the wire, NOT the
// SPELL_CAST action's uniqueId. The client's cast script pulls each effect
// out of the pending action group via executeFirstAction(3, <actionId>);
// ActionGroup.runAction then, if the pulled effect's triggerActionUniqueId
// != -1, re-runs THAT parent action instead -- so pointing it at the
// still-running SpellAction re-ran the whole cast script forever (looping
// the caster's cast animation and blocking all further input).
func TestCastSpell_PrimaryEffectsHaveNoTriggeringAction(t *testing.T) {
	f, caster, enemy := fightWithSpells(t, damageSpell(700, 3, 1))
	caster.Position = Point3{X: 5, Y: 5}
	enemy.Position = Point3{X: 6, Y: 5} // adjacent, in range 1
	enemy.Characteristics[HP].Value, enemy.Characteristics[HP].Max = 100, 100
	caster.SpellIDs = []int32{700}
	caster.Characteristics[AP].Value, caster.Characteristics[AP].Max = 6, 6
	makeSummonCurrent(f, caster, enemy) // makes `caster` the current fighter

	bc := f.broadcaster.(*fakeBroadcaster)
	if !f.castSpell(caster, 700, enemy.Position) {
		t.Fatalf("castSpell did not fire")
	}
	fr, ok := lastRunningEffectFrame(t, bc, caster.CoachID)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for the cast")
	}
	if got := triggeringIDOf(t, fr); got != -1 {
		t.Errorf("cast effect triggeringActionUniqueId = %d, want -1 (a real id makes the client re-run the cast script -> animation loops)", got)
	}
}

func TestEffectCardEquipped_IsNoOp(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	posBefore, hpBefore := b.Position, b.Characteristic(HP)

	def := runningEffectDef{Kind: EffectCardEquipped}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if b.Position != posBefore || b.Characteristic(HP) != hpBefore {
		t.Errorf("target state changed after EffectCardEquipped, want no-op")
	}
}

func TestEffectUnhandled_LogsAndDoesNothing(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	posBefore, hpBefore := b.Position, b.Characteristic(HP)

	def := runningEffectDef{Kind: EffectUnhandled}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if b.Position != posBefore || b.Characteristic(HP) != hpBefore {
		t.Errorf("target state changed after EffectUnhandled, want no-op")
	}
}

// --- Phase N: previously-deferred effects ---

func TestEffectCarry_LinksAndStacksTargetOnCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 3, Y: 3, Z: 0}
	b.Position = Point3{X: 4, Y: 3, Z: 0}

	def := runningEffectDef{Kind: EffectCarry}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if a.CarriedFighter != b || b.CarriedByFighter != a {
		t.Fatalf("carry links not set: a.CarriedFighter=%v b.CarriedByFighter=%v", a.CarriedFighter, b.CarriedByFighter)
	}
	if b.Position.X != a.Position.X || b.Position.Y != a.Position.Y {
		t.Errorf("carried target not stacked on caster cell: b=%v a=%v", b.Position, a.Position)
	}
	if b.Position.Z != a.Position.Z+int16(baseFighterHeight) {
		t.Errorf("carried target Z = %d, want %d (caster Z + height)", b.Position.Z, a.Position.Z+int16(baseFighterHeight))
	}
}

func TestEffectCarry_NoOpIfAlreadyCarrying(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.CarriedFighter = &Fighter{} // already carrying someone
	def := runningEffectDef{Kind: EffectCarry}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)
	if b.CarriedByFighter != nil {
		t.Errorf("carry should be a no-op when caster already carries someone")
	}
}

func TestApplyThrow_DropsCarriedFighterAtCell(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	// Set up a as carrying b first.
	f.applyCarry(a, b, gamedata.EffectDef{ActionID: 58}, -1)
	dropCell := Point3{X: 7, Y: 8, Z: 0}

	f.applyThrow(a, dropCell, gamedata.EffectDef{ActionID: 59}, -1)

	if a.CarriedFighter != nil || b.CarriedByFighter != nil {
		t.Fatalf("throw did not clear carry links: a.CarriedFighter=%v b.CarriedByFighter=%v", a.CarriedFighter, b.CarriedByFighter)
	}
	if b.Position != dropCell {
		t.Errorf("thrown fighter position = %v, want drop cell %v", b.Position, dropCell)
	}
}

func TestApplyThrow_NoOpIfNotCarrying(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	// a carries no one; throw must be a safe no-op.
	f.applyThrow(a, Point3{X: 1, Y: 1}, gamedata.EffectDef{ActionID: 59}, -1)
	if a.CarriedFighter != nil {
		t.Errorf("throw altered state when not carrying")
	}
}

func TestApplyRapprochement_MovesCasterTowardTargetStoppingShort(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = b
	a.Position = Point3{X: 0, Y: 5, Z: 0}
	// Target cell 4 east along the SE single-axis (X+). steps = max(4,0)-1 = 3.
	target := Point3{X: 4, Y: 5, Z: 0}

	f.applyRapprochement(a, target, gamedata.EffectDef{ActionID: 113}, -1)

	// Caster should advance 3 cells toward target (to X=3), one short of it.
	if a.Position.X != 3 || a.Position.Y != 5 {
		t.Errorf("caster position after rapprochement = %v, want {3,5}", a.Position)
	}
}

func TestApplyRapprochement_BlockedWhenStabilized(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 5, Z: 0}
	a.Properties |= PropertyStabilized
	f.applyRapprochement(a, Point3{X: 4, Y: 5, Z: 0}, gamedata.EffectDef{ActionID: 113}, -1)
	if a.Position.X != 0 {
		t.Errorf("stabilized caster moved on rapprochement: %v", a.Position)
	}
}

func TestEffectAttractSight_TurnsTargetTowardCastCell(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Position = Point3{X: 0, Y: 0, Z: 0} // caster, off to the side
	b.Position = Point3{X: 5, Y: 8, Z: 0} // the fighter to turn
	castCell := Point3{X: 5, Y: 5, Z: 0}  // cast cell is due-Y- of b

	// AttractSight must turn b toward the CAST CELL, not the caster.
	// directionFrom(b(5,8) -> castCell(5,5)): dx=0, dy=-3 -> DirNorthEast.
	// Diversion is a radius-2 circle; use that so b (distance 3) is only
	// hit when standing within range -- here place the cast ON b's column
	// within radius so it resolves. Cast the effect via executeOneEffect
	// (the path that carries the cast cell).
	eff := gamedata.EffectDef{ActionID: 68, AreaShape: int16(AreaCircle), AreaSize: []int32{5}}
	f.executeOneEffect(a, eff, castCell, -1)

	if b.Direction != DirNorthEast {
		t.Errorf("target direction after AttractSight = %v, want %v (should face the cast cell, not the caster)", b.Direction, DirNorthEast)
	}
}

// TestDirection4From_QuantizesToDiagonals verifies the AttractSight/Diversion
// facing helper mirrors the reference toDirection4: it only ever returns the
// four sprite-backed diagonal facings (NE/SE/SW/NW), so a fighter due-N/S/E/W
// of the cast cell still snaps to the nearest diagonal (the wiki's "fixed
// pattern"), NOT the axis-aligned E/S/W/N that 8-way directionFrom would give.
func TestDirection4From_QuantizesToDiagonals(t *testing.T) {
	center := Point3{X: 5, Y: 5}
	// from a fighter AT `pos`, facing toward `center`.
	// Expected values computed from the reference getDirection4FromVector
	// (Vector3i.java) for the pos->center vector.
	cases := []struct {
		name string
		pos  Point3
		want Direction8
	}{
		{"pos(8,8) v(-3,-3)", Point3{X: 8, Y: 8}, DirNorthEast},
		{"pos(2,2) v(3,3)", Point3{X: 2, Y: 2}, DirSouthEast},
		{"pos(8,2) v(-3,3)", Point3{X: 8, Y: 2}, DirSouthWest},
		{"pos(2,8) v(3,-3)", Point3{X: 2, Y: 8}, DirNorthEast},
		// Due-cardinal targets must still snap to a diagonal (never E/S/W/N).
		{"due-north pos(5,8) v(0,-3)", Point3{X: 5, Y: 8}, DirNorthEast},
		{"due-south pos(5,2) v(0,3)", Point3{X: 5, Y: 2}, DirSouthWest},
		{"due-east pos(2,5) v(3,0)", Point3{X: 2, Y: 5}, DirSouthEast},
		{"due-west pos(8,5) v(-3,0)", Point3{X: 8, Y: 5}, DirNorthWest},
	}
	for _, tc := range cases {
		got := direction4From(tc.pos, center)
		if got != tc.want {
			t.Errorf("%s: direction4From(%v -> %v) = %v, want %v", tc.name, tc.pos, center, got, tc.want)
		}
		// Must always be one of the 4 diagonal facings, never E/S/W/N.
		switch got {
		case DirNorthEast, DirSouthEast, DirSouthWest, DirNorthWest:
		default:
			t.Errorf("%s: direction4From returned non-diagonal facing %v", tc.name, got)
		}
	}
}

func TestEffectSummonDouble_InsertsCopyIntoTurnOrder(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	before := len(f.Timeline.Order())

	// SummonDouble targets a raw cell -- applySummon is the path
	// executeOneEffect routes it to. (6,6) is a free cell.
	f.applySummon(a, Point3{X: 6, Y: 6}, gamedata.EffectDef{ActionID: 75}, -1)

	if got := len(f.Timeline.Order()); got != before+1 {
		t.Fatalf("turn order after SummonDouble = %d, want %d", got, before+1)
	}
	if f.Timeline.Order()[1].Father != a {
		t.Errorf("summoned double's Father = %v, want caster", f.Timeline.Order()[1].Father)
	}
}

func TestEffectSummonMirror_InsertsCopyIntoTurnOrder(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	before := len(f.Timeline.Order())

	f.applySummon(a, Point3{X: 6, Y: 6}, gamedata.EffectDef{ActionID: 97}, -1)

	if got := len(f.Timeline.Order()); got != before+1 {
		t.Fatalf("turn order after SummonMirror = %d, want %d", got, before+1)
	}
}

func TestEffectDecurse_RevertsBuffsAndClearsReactiveStates(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = a
	// Apply a tracked max-AP buff of +2 to b, then decurse it.
	b.Characteristics[AP].AddMax(2)
	maxAfterBuff := b.Characteristics[AP].Max
	b.trackActiveEffect(ActiveEffect{Kind: ActiveEffectCharacBuff, Charc: AP, Delta: 2, Infinite: true})
	b.StrikeBackPercent = 30
	b.SpellReboundRate = 50

	def := runningEffectDef{Kind: EffectDecurse}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{}, -1)

	if len(b.ActiveEffects) != 0 {
		t.Errorf("decurse left %d active effects, want 0", len(b.ActiveEffects))
	}
	if b.Characteristics[AP].Max != maxAfterBuff-2 {
		t.Errorf("decurse did not revert AP buff: max=%d, want %d", b.Characteristics[AP].Max, maxAfterBuff-2)
	}
	if b.StrikeBackPercent != 0 || b.SpellReboundRate != 0 {
		t.Errorf("decurse did not clear reactive states: strikeBack=%d reboundRate=%d", b.StrikeBackPercent, b.SpellReboundRate)
	}
}

func TestEffectStrikeBack_ReturnsDamageToAttacker(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// Give b a 50% strike-back, then a hits b for 20 -> b returns 10 to a.
	def := runningEffectDef{Kind: EffectStrikeBack}
	f.applyRunningEffect(a, b, def, effectDefWithParams(50), -1)
	if b.StrikeBackPercent != 50 {
		t.Fatalf("StrikeBackPercent = %d, want 50", b.StrikeBackPercent)
	}

	f.applyDamageFrom(a, b, 20, -1)

	if got := b.Characteristic(HP); got != 80 {
		t.Errorf("target HP after 20 dmg = %d, want 80", got)
	}
	if got := a.Characteristic(HP); got != 90 {
		t.Errorf("attacker HP after strike-back = %d, want 90 (returned 50%% of 20)", got)
	}
}

func TestEffectSpellRebound_RedirectsHostileEffectToCaster(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 1, Y: 1}
	b.Position = Point3{X: 2, Y: 1}
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// Arm b's rebound at 99% (max reliable rate). Persistent -- rolls per hit.
	rdef := runningEffectDef{Kind: EffectSpellRebound}
	f.applyRunningEffect(a, b, rdef, effectDefWithParams(99), -1)
	if b.SpellReboundRate != 99 {
		t.Fatalf("SpellReboundRate = %d, want 99", b.SpellReboundRate)
	}

	// a casts an HPLoss at b's cell -> should (very likely) bounce onto a.
	// Force a deterministic reflect by pinning the rate to 100 first.
	b.SpellReboundRate = 100
	hp := gamedata.EffectDef{ActionID: 1, Params: []float32{10}, AreaShape: int16(AreaPoint)}
	f.executeOneEffect(a, hp, b.Position, -1)

	if b.Characteristic(HP) != 100 {
		t.Errorf("rebounder took damage (%d), want 100 (bounced away)", b.Characteristic(HP))
	}
	if a.Characteristic(HP) != 90 {
		t.Errorf("caster HP after rebound = %d, want 90 (took its own spell)", a.Characteristic(HP))
	}
	// PERSISTENT: the rebound is NOT consumed -- a second hostile spell also
	// reflects (unlike the old one-shot bounce).
	f.executeOneEffect(a, hp, b.Position, -1)
	if b.Characteristic(HP) != 100 {
		t.Errorf("rebounder took damage on 2nd hit (%d), want 100 (rebound is persistent, not one-shot)", b.Characteristic(HP))
	}
	if a.Characteristic(HP) != 80 {
		t.Errorf("caster HP after 2nd rebound = %d, want 80", a.Characteristic(HP))
	}
}

// TestEffectSpellRebound_StacksAndExpires verifies the reflect chance stacks
// additively across casts (capped 99) and reverts when the buff duration ends.
func TestEffectSpellRebound_StacksAndExpires(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	rdef := runningEffectDef{Kind: EffectSpellRebound}
	// Two casts of 40% -> 80% total, each with a 1-table-turn duration.
	f.applyRunningEffect(a, b, rdef, gamedata.EffectDef{Params: []float32{40}, Duration: []int32{1}}, -1)
	f.applyRunningEffect(a, b, rdef, gamedata.EffectDef{Params: []float32{40}, Duration: []int32{1}}, -1)
	if b.SpellReboundRate != 80 {
		t.Fatalf("stacked SpellReboundRate = %d, want 80 (40+40)", b.SpellReboundRate)
	}

	// Both expire at the next table-turn boundary -> rate back to 0.
	f.tickActiveEffects(-1)
	if b.SpellReboundRate != 0 {
		t.Errorf("SpellReboundRate after expiry = %d, want 0 (buff decayed)", b.SpellReboundRate)
	}
}

func TestEffectChangeLook_IsNoOpOnCombatState(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	posBefore, hpBefore := b.Position, b.Characteristic(HP)
	def := runningEffectDef{Kind: EffectChangeLook}
	f.applyRunningEffect(a, b, def, effectDefWithParams(7), -1)
	if b.Position != posBefore || b.Characteristic(HP) != hpBefore {
		t.Errorf("ChangeLook altered combat state, want cosmetic-only")
	}
}

// TestEffectAdaptLook_IsNoOpOnCombatState completes per-EffectKind coverage:
// ADAPT_LOOK (id 98) shares ChangeLook's cosmetic broadcast-only handler and
// must likewise never touch combat state (position/HP/AP/properties).
func TestEffectAdaptLook_IsNoOpOnCombatState(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	posBefore := b.Position
	hpBefore := b.Characteristic(HP)
	apBefore := b.Characteristic(AP)
	propsBefore := b.Properties
	def := runningEffectDef{Kind: EffectAdaptLook}
	f.applyRunningEffect(a, b, def, effectDefWithParams(3), -1)
	if b.Position != posBefore || b.Characteristic(HP) != hpBefore ||
		b.Characteristic(AP) != apBefore || b.Properties != propsBefore {
		t.Errorf("AdaptLook altered combat state, want cosmetic-only broadcast")
	}
}

func TestEffectStateApply_DoesNotCrashAndIsNoOpWithoutStateTable(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	hpBefore := b.Characteristic(HP)
	def := runningEffectDef{Kind: EffectStateApply}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{Params: []float32{5, 1}}, -1)
	if b.Characteristic(HP) != hpBefore {
		t.Errorf("StateApply changed HP without a state table, want data-limited no-op")
	}
}

// --- Movement-effect wire correctness (regression for "teleport doesn't
// work"): these effects rely on the client instantiating the concrete
// RunningEffect by its RunningEffectConstants id, so the broadcast MUST
// carry the real effect id (not 0) or the client drops the packet and the
// sprite never moves. ---

func TestEffectTeleport_MovesCasterAndBroadcastsEffectID39(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)
	a.Position = Point3{X: 1, Y: 1, Z: 0}
	dest := Point3{X: 6, Y: 7, Z: 0}

	// TELEPORT is actionID 39; executeOneEffect routes it via applyTeleport.
	tele := gamedata.EffectDef{ActionID: 39}
	f.executeOneEffect(a, tele, dest, -1)

	if a.Position != dest {
		t.Fatalf("caster position after teleport = %v, want %v", a.Position, dest)
	}
	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for teleport")
	}
	if id := runningEffectIDOf(t, fr); id != 39 {
		t.Errorf("teleport RUNNING_EFFECT_ACTION effect id = %d, want 39 (id 0 makes the client drop the packet -> sprite never moves)", id)
	}
}

func TestEffectPush_BroadcastsEffectID37(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)
	a.Position = Point3{X: 1, Y: 1, Z: 0}
	b.Position = Point3{X: 2, Y: 1, Z: 0}

	// A real PUSH effect carries ActionID 37; the broadcast now derives the
	// wire effect id from eff.ActionID (not a hardcoded constant), so the
	// EffectDef must set it.
	def := runningEffectDef{Kind: EffectPush}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{ActionID: 37, Params: []float32{2}}, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for push")
	}
	if id := runningEffectIDOf(t, fr); id != 37 {
		t.Errorf("push RUNNING_EFFECT_ACTION effect id = %d, want 37", id)
	}
	// The wire Value MUST be the actual cells pushed (2 here): the client
	// disables value-computation for Push, so it walks exactly this many
	// cells. A 0 would make the client push nothing (the "Peur does nothing"
	// bug).
	if v := valueOf(t, fr); v != 2 {
		t.Errorf("push RUNNING_EFFECT_ACTION value = %d, want 2 (cells moved)", v)
	}
}

// --- Buff visibility regression ("I can't see the buff or its duration"):
// a spell-parented buff MUST broadcast its GenericEffectID (so the client
// looks up duration[] and stores the effect) AND a SPELL container
// (containerType 13 + spell id, so Fighter."runningEffects" draws the icon
// + countdown). ---

func TestEffectCharacBuff_BroadcastsGenericEffectIDAndSpellContainer(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)

	// An "armor" buff effect owned by spell 500, generic effect id 42,
	// raising a resistance for 3 table-turns.
	buff := gamedata.EffectDef{
		ID:         42,
		ActionID:   21,         // RES_FIRE_GAIN (a CharacGain), any buff-style effect
		ParentType: "SPELL   ", // padded exactly like the real .dat field
		ParentID:   500,
		Params:     []float32{15},
		Duration:   []int32{3, 0},
	}
	def := runningEffectDef{Kind: EffectCharacBuff, Charc: ResFire}
	f.applyRunningEffect(a, b, def, buff, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatalf("no RUNNING_EFFECT_ACTION broadcast for buff")
	}
	if gid := genericEffectIDOf(t, fr); gid != 42 {
		t.Errorf("buff GenericEffectID = %d, want 42 (0 => client can't resolve duration -> effect not stored, no icon)", gid)
	}
	ct, cid := containerOf(t, fr)
	if ct != containerTypeSpell {
		t.Errorf("buff containerType = %d, want %d (Spell); other values suppress the buff icon on the client", ct, containerTypeSpell)
	}
	if cid != 500 {
		t.Errorf("buff containerID = %d, want 500 (the casting spell's id)", cid)
	}
}

func TestEffectContainer_MatchesPaddedAndUnderscoredParentTypes(t *testing.T) {
	cases := []struct {
		parentType string
		parentID   int32
		wantType   int32
		wantID     int64
	}{
		{"SPELL   ", 500, containerTypeSpell, 500},           // padded
		{"FIGHTER_CARD", 200, containerTypeFighterCard, 200}, // underscored
		{"AREA", 7, containerTypeEffectArea, 7},
		{"EVENT", 3, containerTypeEvent, 3},
		{"", 9, 0, 0},        // engine-internal: no container
		{"UNKNOWN", 9, 0, 0}, // unrecognized: no container
	}
	for _, c := range cases {
		gotType, gotID := effectContainer(gamedata.EffectDef{ParentType: c.parentType, ParentID: c.parentID})
		if gotType != c.wantType || gotID != c.wantID {
			t.Errorf("effectContainer(%q,%d) = (%d,%d), want (%d,%d)",
				c.parentType, c.parentID, gotType, gotID, c.wantType, c.wantID)
		}
	}
}
