package game

import "github.com/StarLoco/arena-2.70/internal/gamedata"

// summonWireIDOffset places summon wire ids in a namespace well above the real
// fighter id space (FighterWireIDBase + fr.ID*16+… , always < base + 1<<20) so a
// summon id can never collide with a coach or a real fighter id.
const summonWireIDOffset int64 = 1 << 28

// Default summon stats when a template is missing (unknown id / a "double" with
// no template / absent gamedata) — a modest blocker so the cast is never inert.
const (
	defaultSummonHP = 40
	defaultSummonAP = 6
	defaultSummonMP = 3
)

// nextSummonWireID allocates a unique wire id for a new summon in this fight.
func (f *Fight) nextSummonWireID() int64 {
	f.summonSeq++
	return FighterWireIDBase + summonWireIDOffset + int64(f.summonSeq)
}

// applySummon spawns a creature the caster controls (actions 67 creature / 75
// double / 97 mirror). It builds the summoned FightFighter from its type-300
// template (params[0] = template id; absent/0 falls back to modest blocker
// stats), inserts it into the caster's team + the turn timeline right after the
// caster, and broadcasts the RUNNING_EFFECT that makes the client create + render
// the same fighter. The summon is AI-driven (Father set), so the built-in AI
// (ai.go) plays its turns.
//
// Wire: the client's hy_1 reads the template id from the effect's VALUE field
// (part-0, applied verbatim) and the new fighter id from the target field
// (part-2), so this is the standard 3-part running-effect with value=templateId
// and targetWireID=newFighterId. The template id need not be a real server record
// for the cast to render — the client resolves breed/look/stats from its own
// type-300 data; the server only needs a plausible stat sheet to keep HP / turn
// order / AI in sync.
func (f *Fight) applySummon(caster *FightFighter, ef gamedata.Effect, target Pos) {
	if caster == nil {
		return
	}
	team := f.teamOf(caster)
	if team == nil {
		return
	}
	// The creature spawns on the targeted cell; it must be a real, free arena cell
	// (the client's summon also needs an empty floor cell).
	if !f.Arena().walkable(target.X, target.Y) || f.cellOccupied(target) {
		return
	}
	var templateID int32
	if len(ef.Params) > 0 {
		templateID = int32(ef.Params[0])
	}

	hp, ap, mp := int32(defaultSummonHP), int32(defaultSummonAP), int32(defaultSummonMP)
	var spellID int32
	var tmpl *gamedata.Summoning
	if f.deps != nil && f.deps.Summonings != nil {
		if tmpl = f.deps.Summonings.Get(templateID); tmpl != nil {
			if tmpl.HP > 0 {
				hp = tmpl.HP
			}
			if tmpl.AP > 0 {
				ap = tmpl.AP
			}
			if tmpl.MP > 0 {
				mp = tmpl.MP
			}
			spellID = tmpl.PrimarySpellID()
		}
	}

	spawn := Pos{X: target.X, Y: target.Y, Z: f.Arena().altitudeAt(target.X, target.Y)}
	nv := f.nextSummonWireID()
	summon := &FightFighter{
		WireID:  nv,
		CoachID: caster.CoachID,
		TeamID:  team.ID,
		Pos:     spawn,
		HP:      hp, MaxHP: hp,
		AP: ap, MaxAP: ap,
		MP: mp, MaxMP: mp,
		Init:          caster.Init,
		Father:        caster,
		SummonSpellID: spellID,
	}
	applySummonInnateProperties(summon, tmpl)
	team.Fighters = append(team.Fighters, summon)
	f.insertSummonIntoTimeline(caster, summon)

	// The client's hy_1/ud_2/aww_0 (selected by the effect's own action id)
	// creates the fighter with id = nv (part-2 target) at the spawn cell (part-0),
	// resolving breed/look/stats from its type-300 record keyed by the template id
	// in the VALUE field.
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, nv, spawn, templateID, 0, false)
	f.broadcast(eff)

	if f.deps != nil && f.deps.Log != nil {
		f.deps.Log.Debug("summon spawned", "caster", caster.WireID, "summon", nv,
			"template", templateID, "hp", hp, "ap", ap, "mp", mp, "spell", spellID, "cell", spawn)
	}
}

// applySummonInnateProperties gives a freshly-spawned creature the properties its
// type-300 template says it is born with. The client does the same in adT/ta_0,
// which is how a wall, a doll or a Xelor dial ends up immovable.
//
// They are applied as permanent states so the existing enforcement covers them for
// free: stabilised blocks push/pull, anchored blocks carry, intransposable blocks
// swaps, and rooted zeroes MP so it cannot walk. Without this every summon in the
// game could be shoved, swapped or picked up — 22 of the 53 shipped creatures are
// rooted and 21 cannot be carried.
func applySummonInnateProperties(summon *FightFighter, tmpl *gamedata.Summoning) {
	if summon == nil || tmpl == nil {
		return
	}
	if tmpl.CannotBeCarried {
		summon.addState(stateAnchored, infiniteStateTurns)
	}
	if tmpl.Intransposable {
		summon.addState(stateIntransposable, infiniteStateTurns)
	}
	if tmpl.Stabilised {
		summon.addState(stateStabilized, infiniteStateTurns)
	}
	if tmpl.Rooted {
		summon.addState(stateRooted, infiniteStateTurns)
		summon.MP, summon.MaxMP = 0, 0
	}
	// Tackle characteristics (record fields 13/14 -> Lr.brd / Lr.bre). Without
	// these a creature would have Dodge 0 and could never leave an enemy's
	// contact, and Block 0 so it could never hold anyone — 29 of the 53 shipped
	// creatures carry a block % and 36 a dodge %.
	summon.Block, summon.Dodge = tmpl.Block, tmpl.Dodge
}

// insertSummonIntoTimeline inserts `summon` into the turn order immediately after
// its summoner (and after any summons the caster already owns), matching the
// client's own insertion so both timelines stay in lock-step. The caster is the
// current fighter when it summons, so inserting after it never shifts the current
// turn index — the summon simply acts next in the round.
func (f *Fight) insertSummonIntoTimeline(caster, summon *FightFighter) {
	idx := -1
	for i, x := range f.Timeline {
		if x == caster {
			idx = i
			break
		}
	}
	if idx < 0 {
		f.Timeline = append(f.Timeline, summon)
		return
	}
	insertAt := idx + 1
	for insertAt < len(f.Timeline) && f.Timeline[insertAt].Father == caster {
		insertAt++
	}
	f.Timeline = append(f.Timeline, nil)
	copy(f.Timeline[insertAt+1:], f.Timeline[insertAt:])
	f.Timeline[insertAt] = summon
}
