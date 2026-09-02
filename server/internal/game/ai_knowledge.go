package game

// AI knowledge model - what a fighter is ALLOWED to know.
//
// The AI must play from the same information a human at the keyboard would have.
// It is very easy for a server-side AI to be accidentally omniscient: every trap,
// every hidden state and every enemy's exact remaining HP is sitting in the same
// struct it is reasoning over, and using them is a one-line mistake that never
// shows up as a bug - only as an opponent that feels unfair.
//
// So the rule is made STRUCTURAL rather than remembered: anything the AI consults
// about the battlefield goes through this file, and each function states what a
// human would see.
//
// Current policy:
//
//   - Arena special cells (killer tiles, trap tiles) are MAP FEATURES, drawn for
//     everyone. Known.
//   - Effect areas placed by the fighter's OWN TEAM are known - you placed them,
//     you can see them.
//   - Effect areas placed by the ENEMY are NOT known. This is the rule the AI is
//     most likely to violate by accident and the one a player would notice
//     immediately: an AI that walks around a trap it was never told about is
//     visibly cheating.
//
// If trap visibility ever changes, this file is the single place to change it.

// aiKnowsEffectArea reports whether `ff` may take account of `a`.
func aiKnowsEffectArea(ff *FightFighter, a *effectArea) bool {
	if ff == nil || a == nil {
		return false
	}
	if a.caster == nil {
		// No owner recorded: treat as a map feature rather than secret knowledge.
		return true
	}
	return a.caster.TeamID == ff.TeamID
}

// aiKnownHazardAt reports whether `ff` has legitimate reason to believe standing
// on `p` is dangerous, and roughly how bad it is.
//
// Returns lethal=true only for things that reliably END the fighter (the arena's
// Killer tile fires at turn start), and damaging=true for known survivable harm.
// Enemy traps contribute NOTHING here, by design - see the note above.
func (f *Fight) aiKnownHazardAt(ff *FightFighter, p Pos) (lethal, damaging bool) {
	if sc, _, ok := f.Arena().specialAt(p.X, p.Y); ok {
		switch specialCellByTemplate[sc.Template] {
		case specialCellKiller:
			lethal = true
		case specialCellTrap:
			damaging = true
		}
	}
	for _, a := range f.effectAreas {
		if !aiKnowsEffectArea(ff, a) {
			continue
		}
		if a.isAura() {
			continue // auras follow a fighter; they are not a property of the cell
		}
		if a.contains(p) {
			damaging = true
		}
	}
	return lethal, damaging
}

// aiCellRisk scores how dangerous it is to STAND on `p`, using only what `ff` is
// allowed to know. Lower is safer; the units are arbitrary and only ever compared
// against each other.
//
// Two components, both things a player weighs without thinking:
//
//   - adjacent living enemies, because standing next to someone invites a melee
//     hit and, in this game, being surrounded is how fights are lost;
//   - a KNOWN damaging area on the cell - the fighter's own team's trap. This is
//     a penalty rather than a veto: a human will step on their own trap if that
//     is the only way to reach the fight, they just prefer not to.
//
// Enemy traps contribute nothing, by construction (ai_knowledge.go).
func (f *Fight) aiCellRisk(ff *FightFighter, p Pos) int32 {
	if ff == nil {
		return 0
	}
	var risk int32
	for _, fr := range f.allFighters() {
		if fr.HP <= 0 || fr == ff || !f.areOpponents(ff, fr) || fr.hasState(stateInvisible) {
			continue
		}
		if manhattanDist(p, fr.Pos) <= 1 {
			risk += 2
		}
	}
	if _, damaging := f.aiKnownHazardAt(ff, p); damaging {
		risk += 3 // worse than one adjacent enemy, cheaper than being surrounded
	}
	return risk
}
