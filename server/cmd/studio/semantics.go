package main

import "github.com/dofusarena/go-server/internal/combat"

// This file exposes the combat engine's effect semantics to the frontend so
// the studio can render raw EffectDef records (actionID/params/area/targets) as
// human game-tooltip sentences with icons -- instead of opaque numbers. The
// data comes straight from internal/combat (the same table the engine executes
// with), so the decoder can never drift from real behavior.

// EffectLore bundles everything the UI needs to decode effects locally.
type EffectLore struct {
	// Effects maps every modeled actionID to its plain-language semantics.
	Effects []combat.EffectSemantic `json:"effects"`
	// AreaShapes maps an AreaShape id to a label (point/circle/cross/T/none).
	AreaShapes map[int]string `json:"areaShapes"`
	// TargetConditions maps a FightTargetValidator condition bit to a label.
	TargetConditions map[int]string `json:"targetConditions"`
}

// GetEffectLore returns the static semantic tables (no data dir required).
func (a *App) GetEffectLore() EffectLore {
	return EffectLore{
		Effects: combat.EffectSemantics(),
		AreaShapes: map[int]string{
			1:     "point",
			2:     "circle",
			3:     "cross",
			4:     "T",
			32767: "none",
		},
		// FightTargetValidator condition bits (client). Only a curated set is
		// surfaced; unknown bits are shown raw by the UI.
		TargetConditions: map[int]string{
			1:  "in area",
			2:  "self (caster)",
			4:  "ally",
			8:  "enemy",
			16: "not caster",
			32: "summon",
			64: "not summon",
		},
	}
}
