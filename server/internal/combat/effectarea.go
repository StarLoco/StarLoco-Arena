package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase M: a
// direct port of BasicEffectArea/EffectAreaManager.checkInAndOut()
// (confirmed via the decompiled
// baseImpl/common/clientAndServer/game/effectArea/{BasicEffectArea,
// EffectAreaManager}.java, cross-checked against the real bytecode-
// verified data/staticEffects.dat -- see
// docs/04-game-data-format.md §4.5's corrected status), covering
// persistent ground-effect areas (traps/glyphs) that trigger when a
// fighter steps into or out of their area.
//
// Scope note: this port covers exactly what this project's real
// staticEffects.dat data actually uses -- both real TRAP entries have
// ApplicationCondition=0 (always-appliable, no ONE_TIME_FOR_*
// restriction) and no activation delay (DeactivationDelay is empty for
// both). The richer AbstractEffectArea.canBeApply() cases (1/2/3 =
// ONE_TIME_FOR_EVERYONE/TEAM/TARGET) and hasActivationDelay()'s deferred-
// execution-via-timeline path are therefore NOT implemented here (no
// real data exercises them) -- EffectArea.CanBeApplied always returns
// true, matching this project's real trap data's actual observable
// behavior exactly, with a doc note for future extension if new game
// data ever needs the restricted cases.

// EffectAreaTriggerKind mirrors the two real trigger-bit values used by
// this project's staticEffects.dat data: 1 = enter (application), 2 =
// exit (unapplication) -- confirmed via checkInAndOut's own hardcoded
// `area.triggers(1, applicant)`/`area.triggers(2, applicant)` calls.
type EffectAreaTriggerKind int

const (
	EffectAreaTriggerEnter EffectAreaTriggerKind = 1
	EffectAreaTriggerExit  EffectAreaTriggerKind = 2
)

// EffectArea is one live ground-effect-area instance (a placed trap),
// mirroring BasicEffectArea's runtime state -- distinct from
// gamedata.StaticEffectAreaTemplate, which is the static, data-only
// definition it's instantiated from (mirrors the Effect/RunningEffect
// static-vs-instance split used throughout this package, see effects.go).
type EffectArea struct {
	ID       int64 // unique instance id (this project's equivalent of BasicEffectArea.getId()/m_newTargetId)
	BaseID   int32 // gamedata.StaticEffectAreaTemplate.ID this was instantiated from
	Position Point3
	Area     AreaOfEffect
	Caster   *Fighter // may be nil for a map-authored trap with no fighter owner
	Effects  []gamedata.EffectDef

	applicationTriggers   map[EffectAreaTriggerKind]bool
	unapplicationTriggers map[EffectAreaTriggerKind]bool

	// maxExecutionCount mirrors BasicEffectArea.m_maxExecutionCount: >=63
	// means unlimited (AbstractEffectArea.hasNoExecutionCount()'s exact
	// `!(m_maxExecutionCount < 63 && >= 0)` check), decremented once per
	// successful Apply() otherwise; the area removes itself once
	// exhausted (mirrors BasicEffectArea.shouldBeDead()'s
	// `m_maxExecutionCount == 0` check).
	maxExecutionCount int32
}

// hasUnlimitedExecutions mirrors AbstractEffectArea.hasNoExecutionCount().
func (a *EffectArea) hasUnlimitedExecutions() bool {
	return !(a.maxExecutionCount < 63 && a.maxExecutionCount >= 0)
}

// shouldBeRemoved mirrors BasicEffectArea.shouldBeDead(): exhausted a
// finite execution count.
func (a *EffectArea) shouldBeRemoved() bool {
	return !a.hasUnlimitedExecutions() && a.maxExecutionCount == 0
}

// Contains mirrors BasicEffectArea.contains(Point3): whether pt falls
// inside this area's shape, centered at (and cast from) this area's own
// fixed Position -- a placed trap's shape is always centered on itself,
// unlike a spell's AoE which is cast from a caster toward a separate
// target cell.
func (a *EffectArea) Contains(pt Point3) bool {
	return a.Area.IsPointInside(a.Position, a.Position, pt)
}

// EffectAreaManager owns every live EffectArea for one Fight, mirroring
// EffectAreaManager.java's role (folded into this one small type rather
// than a separate pooled-object-pattern class, matching this package's
// general simplification approach for reference machinery not needed at
// this project's scale -- see docs/01-architecture.md's design notes).
type EffectAreaManager struct {
	areas map[int64]*EffectArea
	seq   int64
}

func newEffectAreaManager() *EffectAreaManager {
	return &EffectAreaManager{areas: make(map[int64]*EffectArea)}
}

// Add registers a newly-instantiated EffectArea, mirroring
// EffectAreaManager.addEffectArea() (the `containsKey` dedupe-by-id guard
// is trivially satisfied here since IDs are always freshly allocated by
// nextEffectAreaID).
func (m *EffectAreaManager) Add(area *EffectArea) {
	m.areas[area.ID] = area
}

// Remove drops area from the active set, mirroring
// EffectAreaManager.removeEffectArea().
func (m *EffectAreaManager) Remove(area *EffectArea) {
	delete(m.areas, area.ID)
}

// All returns every currently-active EffectArea (for tests/inspection).
func (m *EffectAreaManager) All() []*EffectArea {
	out := make([]*EffectArea, 0, len(m.areas))
	for _, a := range m.areas {
		out = append(out, a)
	}
	return out
}

// CheckInAndOut is a direct port of
// EffectAreaManager.checkInAndOut(start, arrival, applicant): computes
// which active areas the mover was inside at start vs. arrival, and
// triggers enter (1) for newly-entered areas / exit (2) for newly-left
// areas. Called once per movement step (see turns.go's handleFighterMove
// hook), not once per whole path, matching the reference's own per-step
// semantics (a mover who passes through a trap mid-path without ending
// their move there must still trigger it).
func (f *Fight) checkInAndOut(start, arrival Point3, applicant *Fighter) {
	m := f.effectAreas
	if m == nil || len(m.areas) == 0 {
		return
	}

	alreadyIn := make(map[int64]bool)
	for id, area := range m.areas {
		if area.Contains(start) {
			alreadyIn[id] = true
		}
	}

	var enteredNow, exitedNow []*EffectArea
	for id, area := range m.areas {
		in := area.Contains(arrival)
		if in && !alreadyIn[id] {
			enteredNow = append(enteredNow, area)
		} else if !in && alreadyIn[id] {
			exitedNow = append(exitedNow, area)
		}
	}

	for _, area := range enteredNow {
		f.triggerEffectArea(area, EffectAreaTriggerEnter, applicant)
	}
	for _, area := range exitedNow {
		f.triggerEffectArea(area, EffectAreaTriggerExit, applicant)
	}
}

// triggerEffectArea mirrors BasicEffectArea.triggers(BitSet, Target):
// dispatches to Apply (application trigger) and/or Unapply
// (unapplication trigger) depending on which of this area's own
// registered trigger sets `kind` intersects -- a single trigger kind CAN
// match both sets simultaneously in principle (the reference uses two
// independent BitSet.intersects checks, not an if/else), though this
// project's real data never exercises that overlap (trap id=1 has only
// an enter trigger, trap id=2 has enter+exit as two DISTINCT trigger
// kinds, never both matching the same kind).
func (f *Fight) triggerEffectArea(area *EffectArea, kind EffectAreaTriggerKind, applicant *Fighter) {
	if area.applicationTriggers[kind] {
		f.applyEffectArea(area, applicant)
	}
	if area.unapplicationTriggers[kind] {
		f.unapplyEffectArea(area, applicant)
	}
}

// applyEffectArea mirrors BasicEffectArea.apply(Target): gate via
// canBeApply() (always true for this project's real data, see this
// file's top doc comment), decrement the execution count, execute the
// area's effects against applicant, broadcast EFFECT_AREA_ACTION(6200)
// with apply=1, and remove the area if its execution count is now
// exhausted.
func (f *Fight) applyEffectArea(area *EffectArea, applicant *Fighter) {
	if !area.hasUnlimitedExecutions() && area.maxExecutionCount > 0 {
		area.maxExecutionCount--
	}

	actionID := f.nextActionID()
	f.broadcastAll(buildEffectAreaAction(actionID, -1, true, area.ID, applicant.ID))
	f.executeEffects(area.Caster, area.Effects, applicant.Position, actionID)

	if area.shouldBeRemoved() {
		f.effectAreas.Remove(area)
	}
}

// unapplyEffectArea mirrors BasicEffectArea.unapply(Target): broadcasts
// EFFECT_AREA_ACTION(6200) with apply=0 AND reverses any persistent property
// this area applied to the leaving fighter. The reference's unapply() tells
// the target's RunningEffectManager to remove effects linked to this area as
// caster; the one case that matters for real data is Sram's FOG (area id 2),
// whose enter effect is SET_INVISIBLE (action 57): a fighter is invisible
// only WHILE inside the fog, so LEAVING it must restore visibility. Without
// this, a fighter who walked through fog stayed permanently invisible
// server-side. Instant-execute trap effects (HPLoss/Death) have nothing to
// undo, so only the invisibility toggle is reversed here.
func (f *Fight) unapplyEffectArea(area *EffectArea, applicant *Fighter) {
	actionID := f.nextActionID()
	f.broadcastAll(buildEffectAreaAction(actionID, -1, false, area.ID, applicant.ID))

	// Reverse persistent SET_INVISIBLE from this area (fog exit -> visible).
	for _, e := range area.Effects {
		if def, ok := LookupRunningEffect(e.ActionID); ok && def.Kind == EffectSetInvisible {
			applicant.Properties &^= PropertyInvisible
			// Broadcast the visibility restore (SET_VISIBLE, action 84) so
			// the client re-shows the sprite, mirroring the enter->apply
			// SET_INVISIBLE broadcast.
			f.broadcastRunningEffect(gamedata.EffectDef{ActionID: 84}, area.Caster, applicant, 0, actionID)
		}
	}
}

// nextEffectAreaID allocates a fresh instance id for a newly-placed
// EffectArea, mirroring SetEffectArea.computeValue()'s
// `m_context.getEffectUserInformationProvider().getNextFreeEffectUserId()`
// call -- this project reuses the same monotonic-counter pattern already
// used for summon fighter IDs (nextSummonID), just a separate counter
// namespace since EffectArea ids and Fighter ids are unrelated id spaces
// on the wire (EFFECT_AREA_ACTION's areaId field vs. any fighterId field).
func (f *Fight) nextEffectAreaID() int64 {
	f.nextEffectAreaIDCounter++
	return f.nextEffectAreaIDCounter
}

// applySetEffectArea implements actionID 66 (SET_EFFECT_AREA), mirroring
// SetEffectArea.execute(): looks up the static template by the effect's
// own Params[0] (mirrors `this.m_value = (int)this.m_genericEffect.getParam(0)`,
// i.e. the FIRST effect param is the static-area-template id to
// instantiate, not a dice-roll magnitude like most other effect kinds),
// instantiates a live EffectArea at the cast's target cell, and registers
// it with this fight's EffectAreaManager.
func (f *Fight) applySetEffectArea(caster *Fighter, targetCell Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	if f.data == nil || len(eff.Params) == 0 {
		return
	}
	templateID := int32(eff.Params[0])
	tmpl, ok := f.data.StaticEffectAreas.Get(templateID)
	if !ok {
		f.logger.Debug().Int32("template_id", templateID).Msg("combat: SET_EFFECT_AREA references unknown static-effect-area template, skipping")
		return
	}

	area := &EffectArea{
		ID:                    f.nextEffectAreaID(),
		BaseID:                tmpl.ID,
		Position:              targetCell,
		Area:                  AreaOfEffect{Shape: AreaShape(tmpl.AreaShapeID), Size: tmpl.AreaParams},
		Caster:                caster,
		Effects:               tmpl.Effects,
		maxExecutionCount:     int32(tmpl.MaxExecutionCount),
		applicationTriggers:   triggerSet(tmpl.ApplicationTriggers),
		unapplicationTriggers: triggerSet(tmpl.UnapplicationTriggers),
	}

	if f.effectAreas == nil {
		f.effectAreas = newEffectAreaManager()
	}
	f.effectAreas.Add(area)

	// Mirrors SetEffectArea.execute()'s own notifyExecution() call --
	// broadcasts the RUNNING_EFFECT_ACTION confirming the placement.
	// CRITICAL: the client's SetEffectArea.execute() places the trap at
	// m_targetCell (useTargetCell=true, useCaster/useTarget=false), so the
	// payload's TargetCell MUST be the CAST cell, not the caster's position.
	// The previous broadcastRunningEffect(eff, caster, caster, ...) sent the
	// caster's own cell, so the fog/trap (e.g. Sram's Brume) was placed on
	// the caster instead of where the player aimed -- the reported "Brume
	// does nothing". Broadcast the effect with the real target cell instead.
	f.broadcastRunningEffectAtCell(eff, caster, targetCell, 0, triggeringActionID)
}

// triggerSet converts a raw []int32 of trigger-bit values (as parsed from
// staticEffects.dat) into the map[EffectAreaTriggerKind]bool lookup
// EffectArea uses, mirroring how the reference sets each value into a
// BitSet.
func triggerSet(raw []int32) map[EffectAreaTriggerKind]bool {
	out := make(map[EffectAreaTriggerKind]bool, len(raw))
	for _, v := range raw {
		out[EffectAreaTriggerKind(v)] = true
	}
	return out
}
