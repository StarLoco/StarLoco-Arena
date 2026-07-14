package botai

import (
	"math/rand"
	"sort"
)

// SpellInfo is the subset of a spell's gamedata a tactical AI needs to
// decide whether a cast is worth attempting. Provided by the host (the
// swarm builds it from gamedata.Store) so botai stays decoupled from the
// game-data layer.
type SpellInfo struct {
	ID       int32
	APCost   int32
	RangeMin int32
	RangeMax int32
	// Damage is a rough offensive weight (higher = prefer). The swarm
	// derives it from the spell's damage effects; 0 for non-damaging
	// spells (buffs/debuffs), which the AI casts last or not at all.
	Damage int32
	// Summon is true if the spell summons a creature (effect ActionID 67).
	Summon bool
}

// SpellBook maps spell id -> info for the bot's own fighters' spells.
type SpellBook map[int32]SpellInfo

// IntentKind enumerates the turn actions an AI can request.
type IntentKind int

const (
	IntentEndTurn IntentKind = iota
	IntentMove
	IntentCast
	IntentCloseCombat
)

// Intent is one action the AI wants to perform this turn. The Driver
// executes intents in order, re-observing between each; an intent the
// server silently drops (invalid move/cast) simply produces no state change
// and the AI moves to its next intent or ends the turn.
type Intent struct {
	Kind      IntentKind
	FighterID int64
	// Move: the single destination cell to step toward (the Driver builds
	// the one-step path). Cast/CloseCombat: the target cell.
	Target Cell
	// Cast: the spell to cast.
	SpellID int32
}

// AI decides a fighter's turn from the observed state. Implementations must
// be deterministic given the same state+rng for reproducible swarms.
type AI interface {
	// Name identifies the AI in telemetry.
	Name() string
	// PlanTurn returns the ordered intents for the fighter whose turn it
	// is (state.CurrentTurn, guaranteed to be one of the bot's living
	// fighters). An empty/nil result means "just end the turn".
	PlanTurn(state *FightState, book SpellBook, rng *rand.Rand) []Intent
}

// nearestEnemy returns the living enemy closest (Manhattan) to `from`, or
// nil if none is known/positioned.
func nearestEnemy(state *FightState, from Cell) *ObservedFighter {
	var best *ObservedFighter
	var bestDist int32
	for _, e := range state.EnemyLivingFighters() {
		if !e.HasPos {
			continue
		}
		d := manhattan(from, e.Pos)
		if best == nil || d < bestDist {
			best, bestDist = e, d
		}
	}
	return best
}

// stepToward returns a single adjacent cell that reduces Manhattan distance
// from `from` toward `to`, preferring the axis with the larger gap. Z is
// carried from the destination-adjacent axis move (the server re-derives
// the true standing altitude, so an approximate Z is fine as the client
// path is validated server-side).
func stepToward(from, to Cell) Cell {
	dx := to.X - from.X
	dy := to.Y - from.Y
	step := from
	if abs32(dx) >= abs32(dy) && dx != 0 {
		if dx > 0 {
			step.X++
		} else {
			step.X--
		}
	} else if dy != 0 {
		if dy > 0 {
			step.Y++
		} else {
			step.Y--
		}
	}
	step.Z = to.Z
	return step
}

func abs32(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}

// --- Dumb AI: the default swarm brain ---

// Dumb is a minimal, cheap AI whose only goal is to reach a KO so the fight
// ends naturally (there is no round cap server-side, so a fight only ends
// when a team is wiped). It ignores spells and just closes to melee: step
// toward the nearest enemy and, when adjacent, close-combat. This exercises
// the move / close-combat / turn-end / end-fight paths at swarm scale
// without the cost of tactical spell evaluation.
type Dumb struct{}

func (Dumb) Name() string { return "dumb" }

func (Dumb) PlanTurn(state *FightState, _ SpellBook, _ *rand.Rand) []Intent {
	me := state.Fighters[state.CurrentTurn]
	if me == nil || !me.HasPos {
		return nil
	}
	enemy := nearestEnemy(state, me.Pos)
	if enemy == nil {
		return nil
	}
	if manhattan(me.Pos, enemy.Pos) == 1 {
		return []Intent{{Kind: IntentCloseCombat, FighterID: me.WireID, Target: enemy.Pos}}
	}
	// Step toward the enemy; if the step lands us adjacent, also try to
	// close-combat this same turn.
	next := stepToward(me.Pos, enemy.Pos)
	intents := []Intent{{Kind: IntentMove, FighterID: me.WireID, Target: next}}
	if manhattan(next, enemy.Pos) == 1 {
		intents = append(intents, Intent{Kind: IntentCloseCombat, FighterID: me.WireID, Target: enemy.Pos})
	}
	return intents
}

// --- Smart AI: the reusable "outplay a real player" brain ---

// Smart is the tactical AI intended for eventual PvE use against humans. Its
// per-turn plan, richest-first:
//
//  1. Focus-fire: target the enemy with the FEWEST remaining living
//     allies-adjacent... (best-effort: lowest wire distance = most reachable),
//     preferring the nearest enemy so damage lands this turn.
//  2. Summon: if the fighter has an affordable summon spell and few allied
//     summons are out, deploy one near the enemy to gain board presence.
//  3. Cast: pick the highest-damage spell that is in range of the target
//     from the current cell; if none is in range, step toward the target
//     until a damaging spell is in range, then cast.
//  4. Melee: if adjacent (or after stepping in), close-combat as a
//     guaranteed source of damage.
//  5. Otherwise advance toward the nearest enemy.
//
// Because the bot lacks authoritative AP, Smart plans a conservative
// sequence (one summon + one cast + one melee attempt + one move) and lets
// the Driver attempt each; the server drops any action the fighter can't
// afford, so an over-optimistic plan degrades gracefully instead of
// desyncing.
type Smart struct {
	// MaxSummonsOut caps how many summons the AI will deploy across the
	// fight (best-effort, counted from observed summon-appearances). 0 uses
	// a sane default.
	MaxSummonsOut int
}

func (Smart) Name() string { return "smart" }

func (s Smart) PlanTurn(state *FightState, book SpellBook, rng *rand.Rand) []Intent {
	me := state.Fighters[state.CurrentTurn]
	if me == nil || !me.HasPos {
		return nil
	}
	target := chooseTarget(state, me)
	if target == nil {
		return nil
	}

	var intents []Intent
	dist := manhattan(me.Pos, target.Pos)

	// Rank the fighter's damaging spells by damage desc; summon spells kept
	// separately.
	dmgSpells, summonSpells := rankSpells(me.SpellIDs, book)

	// 1. Summon (once per turn, biased by rng so not every turn spawns).
	maxSummons := s.MaxSummonsOut
	if maxSummons == 0 {
		maxSummons = 3
	}
	if len(summonSpells) > 0 && countMySummons(state) < maxSummons && rng.Float64() < 0.5 {
		sp := summonSpells[0]
		// Summon adjacent to ourselves toward the enemy (a free-ish cell
		// guess; server validates NeedFreeCell).
		spawn := stepToward(me.Pos, target.Pos)
		intents = append(intents, Intent{Kind: IntentCast, FighterID: me.WireID, SpellID: sp.ID, Target: spawn})
	}

	// 2. Cast the best in-range damaging spell; if none in range, step in.
	if best, inRange := bestInRangeSpell(dmgSpells, dist); inRange {
		intents = append(intents, Intent{Kind: IntentCast, FighterID: me.WireID, SpellID: best.ID, Target: target.Pos})
	} else if len(dmgSpells) > 0 {
		// Move toward the target to get a spell in range, then cast the
		// longest-range damaging spell (most likely to land after one step).
		next := stepToward(me.Pos, target.Pos)
		intents = append(intents, Intent{Kind: IntentMove, FighterID: me.WireID, Target: next})
		longest := longestRangeSpell(dmgSpells)
		intents = append(intents, Intent{Kind: IntentCast, FighterID: me.WireID, SpellID: longest.ID, Target: target.Pos})
	}

	// 3. Melee finisher: if adjacent now, or step in and close-combat.
	if dist == 1 {
		intents = append(intents, Intent{Kind: IntentCloseCombat, FighterID: me.WireID, Target: target.Pos})
	} else if len(dmgSpells) == 0 {
		// No spells at all: advance and try melee (like Dumb, but toward the
		// tactically-chosen target).
		next := stepToward(me.Pos, target.Pos)
		intents = append(intents, Intent{Kind: IntentMove, FighterID: me.WireID, Target: next})
		if manhattan(next, target.Pos) == 1 {
			intents = append(intents, Intent{Kind: IntentCloseCombat, FighterID: me.WireID, Target: target.Pos})
		}
	}

	return intents
}

// chooseTarget picks the enemy to focus. Heuristic: the nearest positioned
// enemy (so damage lands this turn); ties broken by lower wire id for
// determinism. A more elaborate lowest-HP focus is impossible without
// authoritative HP, which the wire doesn't give the bot.
func chooseTarget(state *FightState, me *ObservedFighter) *ObservedFighter {
	enemies := state.EnemyLivingFighters()
	positioned := enemies[:0]
	for _, e := range enemies {
		if e.HasPos {
			positioned = append(positioned, e)
		}
	}
	if len(positioned) == 0 {
		return nil
	}
	sort.Slice(positioned, func(i, j int) bool {
		di, dj := manhattan(me.Pos, positioned[i].Pos), manhattan(me.Pos, positioned[j].Pos)
		if di != dj {
			return di < dj
		}
		return positioned[i].WireID < positioned[j].WireID
	})
	return positioned[0]
}

// rankSpells splits a fighter's spells into damage spells (sorted by damage
// desc) and summon spells, using the book. Unknown ids are skipped.
func rankSpells(ids []int32, book SpellBook) (dmg []SpellInfo, summon []SpellInfo) {
	for _, id := range ids {
		info, ok := book[id]
		if !ok {
			continue
		}
		if info.Summon {
			summon = append(summon, info)
			continue
		}
		if info.Damage > 0 {
			dmg = append(dmg, info)
		}
	}
	sort.Slice(dmg, func(i, j int) bool { return dmg[i].Damage > dmg[j].Damage })
	return dmg, summon
}

// bestInRangeSpell returns the highest-damage spell whose range covers dist.
func bestInRangeSpell(dmg []SpellInfo, dist int32) (SpellInfo, bool) {
	for _, sp := range dmg { // dmg is damage-desc, so first match is best
		if dist >= sp.RangeMin && dist <= sp.RangeMax {
			return sp, true
		}
	}
	return SpellInfo{}, false
}

// longestRangeSpell returns the damaging spell with the largest RangeMax
// (best chance to land after a single step-in).
func longestRangeSpell(dmg []SpellInfo) SpellInfo {
	best := dmg[0]
	for _, sp := range dmg[1:] {
		if sp.RangeMax > best.RangeMax {
			best = sp
		}
	}
	return best
}

// countMySummons counts living fighters that appeared mid-fight owned by the
// bot (summons share the owner's side but arrive without a CREATE_FIGHT
// entry, so they have CoachID 0 and Mine=false; we approximate by counting
// living fighters we can't attribute to either roster). Best-effort.
func countMySummons(state *FightState) int {
	n := 0
	for _, f := range state.Fighters {
		if f.Alive && f.CoachID == 0 && !f.Mine {
			n++
		}
	}
	return n
}
