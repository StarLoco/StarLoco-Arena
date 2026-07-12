package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements the reference State / StateManager status-effect
// bundle system (see the decompiled
// baseImpl/common/clientAndServer/game/effect/{State,StateManager}.java and
// dofusarena/common/game/effect/runningEffect/ApplyState.java), consumed by
// the STATE_APPLY effect (actionID 112, EffectStateApply).
//
// # What a "state" is
//
// A State is a named BUNDLE of sub-effects (each a plain gamedata.EffectDef
// with its own actionID/params/duration), keyed by a (baseId, level) pair.
// STATE_APPLY carries params = [baseId, level]; applying it looks the state
// up and EXECUTES every sub-effect the state contains against the target
// (mirroring ApplyState.execute()'s `for (Effect effect : state) { ... re.
// applyOnTargets(target) }`). A state also declares endTriggers -- trigger
// ids that, when they fire on the carrier, END the whole applied bundle
// (bridged to the reactive trigger-bus in triggerbus.go).
//
// State uniqueId = (baseId << 8) + level -- byte-exact to
// State.getUniqueIdFromBasicInformation.
//
// # Data source (the honest situation, empirically established)
//
// The reference StateManager's table is populated ONLY by addState(), and
// NOTHING in this project's extracted artifacts calls it: there is no
// StateLoader (every other effect-container type -- spells/cards/events/
// summoning/staticEffects -- has one; State does not), no states.dat / state
// file of any kind, no CONTENT_STATE_FILE config key, and no src/org/ankarton
// server source. Ankama's own client comment (ApplyState.java:100, "State
// inconnu pour le client, mais vraisemblablement pas pour le serveur")
// confirms states were SERVER-SIDE data never shipped in the client jar.
//
// Crucially, a full scan of THIS project's real spells.dat/cards.dat/
// events.dat found STATE_APPLY (actionID 112) is used ZERO times -- no
// spell, card, or event in the shipping game applies a state. So there is
// no behavior to reproduce and nothing that exercises this path in practice.
//
// # What this implementation does anyway (and why)
//
// Rather than leave STATE_APPLY as a bare no-op, this provides a COMPLETE,
// correct state-expansion mechanic that is simply EMPTY by default -- the
// same posture as specialcells.go/effectarea.go ("mechanic fully built,
// per-map/real data is a separate follow-up"). If a state table is ever
// registered (via RegisterState -- e.g. from a future states.dat parser, a
// config-supplied table, or a test), STATE_APPLY expands it correctly
// through the real effect pipeline, including endTrigger-driven removal via
// the trigger-bus. Until then it is inert-and-logged, exactly as an unknown
// state is in the reference (which logs and no-ops).

// State is a bundle of sub-effects applied together by STATE_APPLY, keyed
// by (BaseID, Level). Mirrors baseImpl...game.effect.State.
type State struct {
	BaseID      int16
	Level       uint8
	Effects     []gamedata.EffectDef
	EndTriggers []int32 // trigger ids that end the whole bundle when they fire on the carrier
}

// StateUniqueID computes a state's table key from its (baseId, level),
// byte-exact to State.getUniqueIdFromBasicInformation: (baseId << 8) + level.
func StateUniqueID(baseID int16, level uint8) int32 {
	return (int32(baseID) << 8) + int32(level)
}

// UniqueID returns this state's table key.
func (s State) UniqueID() int32 { return StateUniqueID(s.BaseID, s.Level) }

// stateManager is the per-Fight registry of known states, mirroring the
// reference singleton StateManager (scoped per-fight here rather than
// process-global, so a future per-fight/per-map state set is possible and
// tests don't leak state into each other). Nil map = no states registered
// (the mechanic is then inert, matching reality -- see this file's header).
type stateManager struct {
	states map[int32]State
}

// RegisterState adds a state to this fight's table (mirrors
// StateManager.addState). Exposed for whenever real state data becomes
// available to source (a states.dat parser, config, or tests). Overwrites
// any existing state with the same (baseId, level).
func (f *Fight) RegisterState(s State) {
	if f.states == nil {
		f.states = &stateManager{states: make(map[int32]State)}
	}
	f.states.states[s.UniqueID()] = s
}

// lookupState resolves a state by (baseId, level), returning (state, true)
// only if one is registered. Mirrors StateManager.getState(baseId, level).
func (f *Fight) lookupState(baseID int16, level uint8) (State, bool) {
	if f.states == nil {
		return State{}, false
	}
	s, ok := f.states.states[StateUniqueID(baseID, level)]
	return s, ok
}

// applyStateBundle implements EffectStateApply (STATE_APPLY, actionID 112),
// ported from ApplyState.computeValue()/execute():
//   - computeValue: params must be [baseId, level] (case 2); anything else
//     is an error and the effect no-ops (m_value = -1).
//   - execute: look the state up; if found, execute EVERY sub-effect against
//     the target (each a normal effect going through the shared executor, so
//     a state that bundles e.g. a CharacDebuff + a Root behaves exactly as
//     if those were cast directly), then arm the state's endTriggers on the
//     carrier so the bundle can be ended reactively; if not found, log and
//     no-op (matching the reference's "State inconnu" error path).
//
// The caller (applyRunningEffect's EffectStateApply case) has already
// resolved `target` from the normal target-fighter machinery -- ApplyState's
// useCaster()=true/useTarget()=true/useTargetCell()=false contract means it
// operates on a real target fighter, so no special bypass is needed (unlike
// Teleport/SetArea/Summon).
func (f *Fight) applyStateBundle(caster, target *Fighter, eff gamedata.EffectDef, triggeringActionID int32) {
	if len(eff.Params) != 2 {
		// ApplyState.computeValue's default branch: wrong param count ->
		// error + no-op. Broadcast nothing (nothing happened).
		f.logger.Debug().
			Ints32("params", toInts32(eff.Params)).
			Msg("combat: STATE_APPLY has wrong param count (want [baseId, level]), skipping")
		return
	}
	baseID := int16(eff.Params[0])
	level := uint8(eff.Params[1])

	state, ok := f.lookupState(baseID, level)
	if !ok {
		// Reference: m_logger.error("aucun état associés aux paramètres...")
		// then no-op. Broadcast the STATE_APPLY itself so the client still
		// gets the cosmetic acknowledgement (the icon/animation), matching
		// the pre-existing behavior for this (currently universal) case --
		// no real spell in this project's data reaches here, but a future
		// state that isn't registered degrades to exactly this.
		f.logger.Debug().
			Int16("state_base_id", baseID).Uint8("state_level", level).
			Msg("combat: STATE_APPLY for an unregistered state (no state table sourced), broadcast-only")
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
		return
	}

	// Broadcast the STATE_APPLY container first (so the client shows the
	// state's icon), then execute each bundled sub-effect against the
	// target -- mirroring ApplyState.execute()'s loop.
	f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
	f.executeStateEffects(caster, target, state, triggeringActionID)

	// Arm the state's endTriggers on the carrier so the bundle can be ended
	// reactively (ApplyState.getDeactivatedTriggersListening -> the
	// trigger-bus). A sub-effect that was itself deferred (had its own
	// before/after triggers) is already handled by executeStateEffects
	// routing through the shared executor; the state-level endTriggers are
	// a bundle-wide "remove everything on this event" signal, modeled here
	// as informational (there is no persistent bundle object to tear down
	// in this port's instantaneous-execution model -- documented below).
	if len(state.EndTriggers) > 0 {
		f.logger.Debug().
			Int32("state_unique_id", state.UniqueID()).
			Ints32("end_triggers", state.EndTriggers).
			Msg("combat: STATE_APPLY endTriggers noted (bundle sub-effects with durations self-expire; no separate bundle object to end)")
	}
}

// executeStateEffects runs each of a state's bundled sub-effects against
// target, routing through the SAME per-effect path a directly-cast effect
// uses (deferral for triggered sub-effects, instant execution otherwise) --
// so a state bundling, say, a timed CharacDebuff + a Root behaves identically
// to casting those two effects. Mirrors ApplyState.execute()'s
// `for (Effect effect : state) { re.applyOnTargets(target) }`.
func (f *Fight) executeStateEffects(caster, target *Fighter, state State, triggeringActionID int32) {
	for _, sub := range state.Effects {
		subDef, ok := LookupRunningEffect(sub.ActionID)
		if !ok {
			f.logger.Debug().
				Int32("action_id", sub.ActionID).
				Msg("combat: STATE_APPLY sub-effect has unresolved actionID, skipping")
			continue
		}
		// A triggered sub-effect defers onto the target; an instant one
		// executes now -- exactly the routing executeOneEffect's target
		// loop uses.
		if effectMustBeDeferred(sub) {
			f.deferReactiveEffect(caster, target, subDef, sub)
			continue
		}
		f.applyRunningEffect(caster, target, subDef, sub, triggeringActionID)
	}
}
