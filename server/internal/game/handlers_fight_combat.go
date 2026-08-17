package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Combat fallbacks for when a spell cannot be resolved from gamedata — an
// unknown spell id, or a server started without the data files at all. Real
// spell data IS wired in (this comment used to say "until"), so these only apply
// on that fallback path; `defaultMPPerCell` is the exception and is the live
// movement cost.
//
// The fallback damage is deliberately small: fighters have breed-scale HP
// (~60-80, see breed.go), so a big default would one-shot them and desync death
// timing.
const (
	defaultSpellAPCost = 3
	defaultSpellDamage = 15
	defaultMPPerCell   = 1
)

// handleFighterDirectionChange (4521 C2S lr_2: [i64 fighterId][u8 direction])
// broadcasts FIGHTER_CHANGE_DIRECTION (4522) so every client animates the fighter's
// new facing. Facing is purely cosmetic in 2.70 (directional damage was dropped —
// see B-037), so nothing is debited; the value is simply relayed. Registering this
// handler also removes the "unhandled opcode 4521" the client produced every time a
// fighter turned during a fight. qc_0.hf() maps any byte to a valid direction (NONE
// for out-of-range), so relaying the client's byte verbatim can never crash a peer.
func handleFighterDirectionChange(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	wireID, err := r.I64()
	if err != nil {
		return err
	}
	dir, err := r.U8()
	if err != nil {
		return err
	}
	cid := s.Coach.ID
	deps := s.deps
	f.Post(func(f *Fight) {
		ff := f.applyDirectionChange(cid, wireID, dir)
		if ff == nil {
			deps.Log.Debug("fight facing ignored", "wireID", wireID, "dir", dir,
				"currentTurn", f.isCurrentTurn(wireID))
			return
		}
		msg, err := buildFighterDirectionChange(f.nextActionUID(), wireID, dir)
		if err != nil {
			return
		}
		f.broadcast(msg)
		// .Fighter is nil for summons (see breed.go), so never deref it bare.
		name := ""
		if ff.Fighter != nil {
			name = ff.Fighter.Name
		}
		deps.Log.Debug("fight facing", "wireID", wireID, "name", name, "dir", dir)
	})
	return nil
}

// handleFighterMoveInFight (4503 C2S: [i64 fighterId] + path {i32 x,i32 y,i16 z})
// broadcasts FIGHTER_MOVE (4524) prefixed with the start cell, and debits MP.
func handleFighterMoveInFight(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	wireID, err := r.I64()
	if err != nil {
		return err
	}
	var path []Pos
	for r.Remaining() >= 10 {
		x, _ := r.I32()
		y, _ := r.I32()
		z, _ := r.U16()
		path = append(path, Pos{X: x, Y: y, Z: int16(z)})
	}
	if len(path) == 0 {
		return nil
	}
	cid := s.Coach.ID
	deps := s.deps

	f.Post(func(f *Fight) {
		ff := f.fighterByWireID(wireID)
		if ff == nil || ff.CoachID != cid || !f.isCurrentTurn(wireID) {
			deps.Log.Debug("fight move ignored", "wireID", wireID,
				"haveFighter", ff != nil, "currentTurn", f.isCurrentTurn(wireID))
			return
		}
		// The retail client's 4503 path is the list of STEP cells, EXCLUDING the
		// fighter's current (origin) cell — verified live: for a fighter at (7,15)
		// the path began at (8,15). So:
		//   - MP cost = number of steps = len(path).
		//   - the FIGHTER_MOVE (4524) walk animator (HB.oS -> abm_2.a) starts at
		//     the FIRST path cell, so the broadcast path MUST prepend the origin:
		//     [origin, step1, ..., dest]. (Broadcasting the origin-excluded path
		//     as-is makes the fighter jump from its cell straight to step1.)
		//
		// Validate the whole walk server-side (anti-desync/cheat) before applying:
		// path[0] adjacent to the origin, each subsequent step adjacent, every cell
		// a walkable arena cell not held by another fighter, within MP. The client
		// pathfinds on the exact grid the server streamed (writeFightGrid), so a
		// genuine path always passes; only forged moves are rejected.
		if !f.validateFightMove(ff, path) {
			deps.Log.Debug("fight move rejected", "origin", ff.Pos,
				"path0", path[0], "dest", path[len(path)-1],
				"steps", len(path), "mp", ff.MP)
			return
		}
		// Tackle / zone-of-control: leaving a cell adjacent to a living enemy
		// requires an evasion roll — failing it forfeits the move and ends the turn.
		if !f.attemptEvadeTackle(ff) {
			deps.Log.Debug("fighter tackled (move forfeited)", "wire", ff.WireID)
			f.handleTackled(ff)
			return
		}
		// Stop-on-contact: halt at the first step that enters an enemy's ZoC.
		path = f.truncatePathOnEnemyContact(ff, path)
		f.applyFighterMove(ff, path)
	})
	return nil
}

// applyFighterMove debits MP, advances the fighter to the end of `path` (the
// origin-EXCLUDED step list) and broadcasts FIGHTER_MOVE (origin-PREFIXED so the
// client walk animation starts at the fighter) + the silent MP-use + the action
// flush. `path` must already be validated (a client path via validateFightMove,
// or an AI path from reachableCells). Shared by the move handler and the summon
// AI.
func (f *Fight) applyFighterMove(ff *FightFighter, path []Pos) {
	if len(path) == 0 {
		return
	}
	f.dismountIfCarried(ff) // a carried fighter drops off before it walks
	origin := ff.Pos
	cost := int32(len(path)) * defaultMPPerCell
	ff.MP -= cost
	ff.Pos = path[len(path)-1]
	if ff.CarriedFighter != nil {
		ff.CarriedFighter.Pos = ff.Pos // drag the carried fighter along
	}

	fullPath := append([]Pos{origin}, path...)
	move, _ := buildFighterMoveInFight(f.nextActionUID(), ff.WireID, fullPath)
	f.broadcast(move)
	// MP-use running effect: genericEffectId 0 (no spell container); id
	// RunEffectMPUse (92) is a SILENT debit (the client suppresses its chat).
	mp, _ := buildRunningEffect(f.nextActionUID(), protocol.RunEffectMPUse, 0,
		0, ff.WireID, ff.Pos, cost, 0, true)
	f.broadcast(mp)
	seq, _ := buildActionSequenceExecute()
	f.broadcast(seq)

	// Trap/glyph triggers: walking onto a ground-effect area springs it. Checked
	// per step (origin excluded) so a trap on ANY cell of the walk fires, not
	// just the destination.
	prev := origin
	for _, step := range path {
		f.checkEffectAreasMove(prev, step, ff)
		prev = step
		if ff.HP <= 0 {
			break // died in a trap mid-walk
		}
	}
}

// validateFightMove reports whether path is a legal walk for ff. The client's
// 4503 path is the list of STEP cells EXCLUDING ff's current (origin) cell, so:
// path[0] must be adjacent to ff's cell, each subsequent step adjacent to the
// previous, every cell a walkable arena cell not held by another living fighter,
// and the whole walk within ff's MP (one MP per step). The client pathfinds on
// the same grid the server streamed (writeFightGrid/cellFlag), so a genuine path
// always passes; this rejects forged requests (teleport, through void/obstacles,
// onto a fighter, or with insufficient MP). Altitude (z) is not validated — (x,y)
// is the unit of movement and the client owns per-cell altitude.
func (f *Fight) validateFightMove(ff *FightFighter, path []Pos) bool {
	if ff == nil || len(path) < 1 {
		return false
	}
	if ff.hasState(stateRooted) {
		return false // a rooted fighter cannot move
	}
	if int32(len(path))*defaultMPPerCell > ff.MP {
		return false
	}
	prev := ff.Pos // the origin (not part of the wire path)
	for _, c := range path {
		if !f.Arena().walkable(c.X, c.Y) {
			return false
		}
		if f.cellDestroyed(c.X, c.Y) {
			return false // sudden death removed this cell
		}
		if !cellsAdjacent(prev, c) {
			return false
		}
		if f.cellHeldByOther(c, ff) {
			return false
		}
		prev = c
	}
	return true
}

// cellsAdjacent reports whether p and q are one grid step apart (Chebyshev
// distance 1), ignoring altitude. The client's pathfinder only emits contiguous
// cells, so a single legitimate step is always adjacent; a larger jump is a
// forged "teleport".
func cellsAdjacent(p, q Pos) bool {
	dx := p.X - q.X
	if dx < 0 {
		dx = -dx
	}
	dy := p.Y - q.Y
	if dy < 0 {
		dy = -dy
	}
	if dy > dx {
		dx = dy
	}
	return dx == 1
}

// cellHeldByOther reports whether a living fighter other than self stands on cell
// (x,y). Dead fighters (HP<=0) leave their cell free; a CARRIED fighter is held
// on its carrier's cell and does not independently hold ground (the carrier
// already does).
func (f *Fight) cellHeldByOther(c Pos, self *FightFighter) bool {
	for _, other := range f.allFighters() {
		if other == self || other.HP <= 0 || other.CarriedByFighter != nil {
			continue
		}
		if other.Pos.X == c.X && other.Pos.Y == c.Y {
			return true
		}
	}
	return false
}

// spellTargetValid reports whether target is a legal cell for caster to cast sp
// at, per the client's targeting rules (ported from the 2.04 server's
// validateCast):
//   - RANGE: Manhattan |dx|+|dy| must be within [RangeMin, RangeMax]; RangeMax is
//     boostable by the caster's Range stat, but only for a spell whose base
//     RangeMax > 1 (a melee range-1 spell never extends).
//   - ONLY-LINE: a straight-line-only spell's target must share the caster's row
//     or column.
//   - FREE-CELL: a free-cell spell's target must be empty.
//   - LINE-OF-SIGHT: a LoS spell's target must be visible from the caster
//     (terrain-only altitude LoS — see line_of_sight.go).
//
// The client enforces the same rules, so a genuine cast always passes.
func (f *Fight) spellTargetValid(caster *FightFighter, sp *gamedata.Spell, target Pos) bool {
	return f.spellTargetValidFrom(caster, caster.Pos, sp, target)
}

// spellEffectiveMaxRange is a spell's maximum range for this caster.
//
// The caster's Range characteristic extends a spell whose base max is > 1,
// UNLESS the spell is flagged range-not-boostable — and that flag suppresses
// only a positive boost, exactly as the client gates it:
//
//	if (!(maxRange <= 1 || boost >= 0 && eD())) maxRange += boost
//
// Shared with the AI so its idea of where it can stand and fire from cannot
// drift from what the validator will accept.
func spellEffectiveMaxRange(caster *FightFighter, sp *gamedata.Spell) int32 {
	maxRange := int32(sp.RangeMax)
	if caster == nil {
		return maxRange
	}
	if maxRange > 1 && !(caster.Range >= 0 && sp.RangeNotBoostable) {
		maxRange += caster.Range
		if maxRange < int32(sp.RangeMin) {
			maxRange = int32(sp.RangeMin)
		}
	}
	return maxRange
}

// spellTargetValidFrom is spellTargetValid evaluated from an ARBITRARY origin
// rather than the caster's current cell, so the AI can ask "could I cast this if
// I stood there?" while planning a move and get the SAME answer the validator
// will give once it arrives. The AI used to approximate this with a bare
// Manhattan range window, which ignored the Range-stat extension, only-line,
// free-cell and target masks — so it both walked closer than it needed to and
// sometimes walked somewhere its cast was then refused, wasting the turn.
func (f *Fight) spellTargetValidFrom(caster *FightFighter, from Pos, sp *gamedata.Spell, target Pos) bool {
	// A spell can only ever be aimed at a real, walkable arena cell. This was
	// missing (B-048): NeedFreeCell only tested for FIGHTERS, so nothing stopped a
	// cast — including a displacement spell like the Iop's Bond — from targeting a
	// void cell or a scenery obstacle and landing the caster on top of it.
	if !f.Arena().walkable(target.X, target.Y) || f.cellDestroyed(target.X, target.Y) {
		return false
	}
	dist := manhattanDist(from, target)
	maxRange := spellEffectiveMaxRange(caster, sp)
	if dist < int32(sp.RangeMin) || dist > maxRange {
		return false
	}
	if sp.OnlyLine && target.X != from.X && target.Y != from.Y {
		return false
	}
	if sp.NeedFreeCell && f.cellOccupied(target) {
		return false
	}
	if sp.TestLoS && !f.Arena().hasLineOfSight(from, target) {
		return false
	}
	if !f.spellTargetMaskAllows(caster, sp, target) {
		return false
	}
	return true
}

// spellTargetMaskAllows evaluates a spell's CAST-level target conditions
// (`TargetMasks`, field 22) — distinct from the per-effect conditions, which
// filter an area's expanded targets.
//
// The client only runs this check when the spell's `EnforceTargetMasks` flag
// (field 19, `eF()`) is set, and exactly THREE of the 203 shipped spells set it:
//
//	spell 468  mask 4  = bit 2         -> the target must be an ALLY
//	spell 83   mask 36 = bits 2 and 5  -> an ally AND summoned, i.e. an allied summon
//	spell 449  mask 1<<62              -> the target must be a ground EFFECT AREA
//
// The first two are plain per-effect condition bits, so the existing evaluator
// decides them unchanged. The third is not: bit 62 lives in `aLc.n(ack_1)`,
// which asks whether the target is a live trap/glyph rather than a fighter — a
// targeting MODE this server does not model (casts aim at a cell or the fighter
// on it, never at a ground area). Enforcing it with the fighter evaluator would
// reject every cast of spell 449, which is worse than not enforcing it, so a
// mask carrying bits this evaluator cannot represent is deliberately left
// permissive and the spell keeps working.
func (f *Fight) spellTargetMaskAllows(caster *FightFighter, sp *gamedata.Spell, target Pos) bool {
	if sp == nil || !sp.EnforceTargetMasks || len(sp.TargetMasks) == 0 {
		return true
	}
	for _, m := range sp.TargetMasks {
		if m&^evaluableTargetBits != 0 {
			return true // carries a bit we cannot represent — do not guess
		}
	}
	victim := f.fighterAtCell(target)
	if victim == nil {
		// No fighter on the aimed cell. Every enforced mask in the shipped data
		// names a property OF A FIGHTER, so there is nothing to satisfy.
		return false
	}
	return effectTargetAllowed(caster, victim, sp.TargetMasks)
}

// manhattanDist returns |dx|+|dy| between two cells, ignoring altitude — the
// grid distance metric the client uses for spell range.
func manhattanDist(a, b Pos) int32 {
	dx := a.X - b.X
	if dx < 0 {
		dx = -dx
	}
	dy := a.Y - b.Y
	if dy < 0 {
		dy = -dy
	}
	return dx + dy
}

// cellOccupied reports whether any living fighter stands on cell (x,y). A carried
// fighter (held on its carrier's cell) does not count as an independent occupant.
func (f *Fight) cellOccupied(c Pos) bool {
	for _, ff := range f.allFighters() {
		if ff.HP > 0 && ff.CarriedByFighter == nil && ff.Pos.X == c.X && ff.Pos.Y == c.Y {
			return true
		}
	}
	return false
}

// fighterHasEquipped reports whether the fighter carries cardID in its own
// equipment. 8107 plays a FIGHTER's gear — the client sends the id of a `ve_0`
// (its "itemIconUrl"/fighterEquipmentIconsPath prove it is fighter equipment, not
// a coach card), so ownership must be checked against the fighter's objects.
func fighterHasEquipped(ff *FightFighter, cardID int32) bool {
	if ff == nil || ff.Fighter == nil {
		return false
	}
	for _, obj := range ff.Fighter.Objects {
		if obj.TemplateID == cardID {
			return true
		}
	}
	return false
}

// handleFighterCardUse (8107 C2S sg_2: [i64 fighterId][i32 cardId][i32 x][i32 y]
// [i16 z]) is a fighter using its EQUIPMENT'S active ability at a target cell —
// in practice, attacking with its weapon. It broadcasts FIGHTER_CARD_USE (8108)
// so every client animates it, and resolves the card's effects.
//
// This is NOT the spell cast (8109) — the two requests are byte-identical, which is
// how they were previously conflated (B-047). Because the old code fed the card id
// into castSpellByFighter as a SPELL id, playing a card whose template id happened
// to collide with a spell id cast that unrelated spell; the id space is shared, so
// this was not hypothetical. Routing them separately fixes that class of bug.
//
// Nor is it CLOSE_COMBAT (8111), which is the breed's fixed unarmed strike on an
// adjacent cell; this uses the equipped card's own AP cost, range and effects.
func handleFighterCardUse(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	userID, err := r.I64()
	if err != nil {
		return err
	}
	cardID, _ := r.I32()
	x, _ := r.I32()
	y, _ := r.I32()
	z, _ := r.U16()
	target := Pos{X: x, Y: y, Z: int16(z)}
	cid := s.Coach.ID

	f.Post(func(f *Fight) {
		user := f.fighterByWireID(userID)
		if user == nil || user.CoachID != cid || user.HP <= 0 {
			return
		}
		f.useFighterCard(user, cardID, target)
	})
	return nil
}

// handleSpellCast (8109 C2S mc_2: [i64 fighterId][i32 spellId][i32 x][i32 y][i16 z])
// resolves a damaging spell: broadcast cast, debit AP, apply damage, death check.
func handleSpellCast(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	casterID, err := r.I64()
	if err != nil {
		return err
	}
	spellID, _ := r.I32()
	x, _ := r.I32()
	y, _ := r.I32()
	z, _ := r.U16()
	target := Pos{X: x, Y: y, Z: int16(z)}
	cid := s.Coach.ID

	f.Post(func(f *Fight) {
		caster := f.fighterByWireID(casterID)
		if caster == nil || caster.CoachID != cid || !f.isCurrentTurn(casterID) {
			return
		}
		f.castSpellByFighter(caster, spellID, target)
	})
	return nil
}

// fighterKnowsSpell reports whether `ff` is allowed to cast `spellID` at all.
//
// Two legitimate sources, because this server has two kinds of caster:
//
//   - A real coach fighter casts what it has equipped (`Fighter.Spells`, capped
//     at maxFighterSpells and chosen at creation).
//   - A SERVER-DRIVEN fighter casts its single `SummonSpellID`. That covers both
//     summoned creatures (from the type-300 template) and the AI opponents in
//     PvE challenges — challenge demons are built with a `domain.Fighter` for
//     their breed and stats but an EMPTY spell list, their one spell living in
//     SummonSpellID. Checking only `Fighter.Spells` would therefore have muted
//     every demon in the game and broken all 39 challenges.
func fighterKnowsSpell(ff *FightFighter, spellID int32) bool {
	if ff == nil {
		return false
	}
	if ff.SummonSpellID != 0 && ff.SummonSpellID == spellID {
		return true
	}
	if ff.Fighter == nil {
		return false
	}
	for _, sp := range ff.Fighter.Spells {
		if sp.SpellID == spellID {
			return true
		}
	}
	return false
}

// castSpellByFighter runs a spell cast for `caster` at `target`: it validates AP
// and targeting (range/only-line/free-cell/LoS), broadcasts SPELL_CAST, debits AP
// (silent), resolves every effect (each broadcasting its own RUNNING_EFFECT), and
// checks for fight end. Returns whether the cast actually fired. Shared by the
// spell-cast handler (after the coach/turn-ownership check) and the summon AI
// (which drives its own summon directly). It must run on the fight goroutine.
func (f *Fight) castSpellByFighter(caster *FightFighter, spellID int32, target Pos) bool {
	if caster == nil || !f.isCurrentTurn(caster.WireID) {
		return false
	}
	// The caster must actually KNOW the spell. Without this a forged 8109 could
	// cast any id in the 203-spell table from any fighter — the whole table is
	// reachable through f.deps.Spells, so a level-1 fighter could fire a boss
	// spell. The equipment path (8107) has always checked ownership this way
	// (fighterHasEquipped); this closes the same hole on the spell path.
	if !fighterKnowsSpell(caster, spellID) {
		if f.deps != nil && f.deps.Log != nil {
			f.deps.Log.Debug("spell cast refused: caster does not know this spell",
				"fight", f.ID, "wireID", caster.WireID, "spell", spellID)
		}
		return false
	}
	// Resolve AP cost + spell template (fallback for an unknown spell / absent
	// data files).
	apCost := int32(defaultSpellAPCost)
	var sp *gamedata.Spell
	if f.deps != nil && f.deps.Spells != nil {
		sp = f.deps.Spells.Get(spellID)
	}
	if sp != nil {
		if sp.AP > 0 {
			apCost = int32(sp.AP)
		}
		// Targeting validation (range/only-line/free-cell/LoS) — mirrors the 2.04
		// server's validateCast; the client enforces the same, so a genuine cast
		// passes.
		if !f.spellTargetValid(caster, sp, target) {
			return false
		}
	}
	// Cast-precondition criteria (the spell's field-20 token string:
	// carry/summon/HP%/state gates). Data-driven and authoritative — it supersedes
	// the earlier hardcoded carry check (a carried fighter can still cast spells
	// that don't carry cantCastWhenCarried). The effect-level applyCarry/applyThrow
	// guards remain a safety net for spells with missing/covering tokens.
	if sp != nil && !f.meetsCastCriteria(caster, sp.Criterion) {
		return false
	}
	// Cast-frequency limits (min interval / max per turn / max per target). The
	// per-target cap keys on the fighter under the aimed cell (none = bare cell).
	var targetID int64
	var hasTarget bool
	if tf := f.fighterAtCell(target); tf != nil {
		targetID, hasTarget = tf.WireID, true
	}
	if sp != nil && !caster.CastHistory.canCast(sp.LimitKeyID(), sp.Cooldown,
		sp.CastMaxPerTurn, sp.CastMaxPerTarget, f.tableTurn, targetID, hasTarget) {
		return false
	}
	if caster.AP < apCost {
		return false // not enough AP
	}

	// Roll fumble, then crit (a fumble precludes a crit). A FUMBLE spends the AP
	// but applies NO effects; a CRIT runs the spell's isCritical effect subset.
	fumble := caster.rollFumble(f.rngSource())
	crit := !fumble && caster.rollCrit(f.rngSource())

	// 1. Broadcast the cast with the rolled crit/fumble flags.
	cast, _ := buildSpellCast(f.nextActionUID(), caster.WireID, spellID, target, fumble, crit)
	f.broadcast(cast)

	// 2. Debit AP (silent — RunEffectAPUse suppresses its own chat line) and record
	//    the cast against this fighter's cast-frequency history (both even on a
	//    fumble: the AP is spent and the cast counts against the frequency limits).
	caster.AP -= apCost
	if sp != nil {
		caster.CastHistory.storeCast(sp.LimitKeyID(), sp.Cooldown, sp.CastMaxPerTurn,
			sp.CastMaxPerTarget, f.tableTurn, targetID, hasTarget)
	}
	ap, _ := buildRunningEffect(f.nextActionUID(), protocol.RunEffectAPUse, 0,
		0, caster.WireID, caster.Pos, apCost, 0, true)
	f.broadcast(ap)

	// 3. Resolve effects — a fumble applies none; otherwise the crit/normal subset.
	//    An unknown spell / absent data falls back to a flat neutral hit.
	if !fumble {
		if sp != nil {
			f.resolveSpellEffects(caster, sp, target, crit)
		} else {
			f.applyFallbackDamage(caster, target)
		}
	}

	// 4. Flush the action group.
	seq, _ := buildActionSequenceExecute()
	f.broadcast(seq)

	// 5. Victory check (runs on the fight goroutine).
	if f.deps != nil {
		f.deps.checkFightEnd(f)
	}
	return true
}

// handleEndFightDone (4321 C2S, empty) acks the results screen; once both
// coaches ack, returns them to the overworld.
func handleEndFightDone(s *Session, _ *protocol.C2SFrame) error {
	// A spectator acking the result screen just detaches (the fight is already
	// removed; its actor is stopped, so no removeSpectator post is needed).
	s.spectating = nil
	// The fight is already removed at end; just return this coach to the world.
	if s.Coach != nil {
		s.deps.World.SetInFight(s.Coach.ID, false)
		// Full overworld re-entry (4600 + 4516 unlock + element re-spawn), back to
		// the world the coach left, at its stored position/altitude.
		world := s.currentWorld
		if world == 0 {
			world = startWorldID
		}
		_ = s.sendEnterOverworld(float32(s.Coach.PosX), float32(s.Coach.PosY), s.Coach.PosZ, world)
		// Re-seed AoI: spawn nearby coaches to this client and this client to
		// them (rebuilds the known sets after the fight).
		spawnToJoiner, joinerView, spawnJoinerTo := s.deps.World.EnterAoI(s.Coach.ID)
		if len(spawnToJoiner) > 0 {
			if spawn, err := buildActorSpawn(spawnToJoiner); err == nil {
				_ = s.Send(spawn)
			}
		}
		if len(spawnJoinerTo) > 0 {
			if spawn, err := buildActorSpawn([]CoachView{joinerView}); err == nil {
				for _, sess := range spawnJoinerTo {
					_ = sess.Send(spawn)
				}
			}
		}
	}
	return nil
}

// handleCloseCombat (8111 C2S: [i64 fighterId][i32 x][i32 y][i16 z]) is the retail
// client's weapon/melee attack. It runs on the fight actor after ownership +
// current-turn checks.
func handleCloseCombat(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	wireID, err := r.I64()
	if err != nil {
		return err
	}
	x, _ := r.I32()
	y, _ := r.I32()
	z, _ := r.U16()
	target := Pos{X: x, Y: y, Z: int16(z)}
	cid := s.Coach.ID
	f.Post(func(f *Fight) {
		ff := f.fighterByWireID(wireID)
		if ff == nil || ff.CoachID != cid || !f.isCurrentTurn(wireID) {
			return
		}
		f.closeCombat(ff, target)
	})
	return nil
}

// closeCombat resolves a weapon attack by ff on the fighter at an ADJACENT cell:
// it costs closeCombatAP, rolls fumble/crit, broadcasts CLOSE_COMBAT (8112), and
// (unless it fumbled) deals the breed's close-combat element damage
// (closeCombatDamages, or closeCombatCritDamages on a crit) through the elemental
// formula. Must run on the fight actor.
func (f *Fight) closeCombat(ff *FightFighter, target Pos) {
	if ff.AP < closeCombatAP || posManhattan(ff.Pos, target) != 1 {
		return // not enough AP, or the target is not orthogonally adjacent
	}
	victim := f.fighterAtCell(target)
	if victim == nil || victim.TeamID == ff.TeamID {
		return // no living enemy on the target cell
	}
	ff.AP -= closeCombatAP

	fumble := ff.rollFumble(f.rngSource())
	crit := !fumble && ff.rollCrit(f.rngSource())
	if cc, err := buildCloseCombat(f.nextActionUID(), ff.WireID, target, fumble, crit); err == nil {
		f.broadcast(cc)
	}
	// Silent AP debit for the client-side counter.
	ap, _ := buildRunningEffect(f.nextActionUID(), protocol.RunEffectAPUse, 0,
		0, ff.WireID, ff.Pos, closeCombatAP, 0, true)
	f.broadcast(ap)

	if !fumble {
		elem := breedBase(breedOf(ff)).CCElement
		base := closeCombatDamages
		if crit {
			base = closeCombatCritDamages
		}
		final := f.computeElementalDamage(ff, victim, base, elem)
		final = f.applyDamageRebound(ff, victim, final) // 89: reflect a share to the attacker
		f.applyHPDelta(ff, victim, directElementActionID(elem), 0, -final)
	}
	if seq, err := buildActionSequenceExecute(); err == nil {
		f.broadcast(seq)
	}
	if f.deps != nil {
		f.deps.checkFightEnd(f)
	}
}

// checkFightEnd ends the fight if one team has no living fighters, or if
// something else has already decided a winner (victory.go).
func (d *Deps) checkFightEnd(f *Fight) {
	if f.Phase() == PhaseEnded {
		return
	}
	var winnerTeam uint8
	if f.decidedWinner != nil {
		// A victory condition ended the fight while both sides may still have
		// living fighters, so the survivor count cannot decide it. Nobody is
		// killed to make the numbers work: the loser simply loses, which also
		// keeps evolution deaths (driven by HP, not by the result) correct.
		winnerTeam = *f.decidedWinner
	} else {
		aliveByTeam := map[uint8]int{}
		for _, ff := range f.allFighters() {
			if ff.HP > 0 {
				aliveByTeam[ff.TeamID]++
			}
		}
		teamsAlive := 0
		for team, n := range aliveByTeam {
			if n > 0 {
				teamsAlive++
				winnerTeam = team
			}
		}
		if teamsAlive >= 2 {
			return // fight continues
		}
	}

	// Persist win/loss stats + update the ranked ladder strength for each real
	// coach, THEN declare the winner (so END_FIGHT carries the NEW strengths).
	// Hold the coach lock while mutating so a concurrent session-side save (on
	// disconnect) can't race the struct. Practice ("Tester") fights are unranked:
	// skip stats/strength/rewards (also avoids persisting the synthetic sparring).
	// Lifetime time-in-fight is credited here rather than inside the !Practice
	// branch below: sparring is excluded from competitive records, but the time
	// was still spent. Idempotent, so the later teardown does not double-count.
	d.creditFightTime(f)

	var winners, losers []endFightCoach
	wonCardsByCoach := map[uint][]int32{}
	for _, t := range f.Teams {
		if t == nil || t.Coach == nil {
			continue
		}
		won := t.ID == winnerTeam
		if !f.Practice {
			t.Coach.Mu.Lock()
			t.Coach.StatFights++
			if won {
				t.Coach.StatWins++
				t.Coach.ConsecutiveWins++
				t.Coach.ConsecutiveLosses = 0
			} else {
				t.Coach.StatLosses++
				t.Coach.ConsecutiveLosses++
				t.Coach.ConsecutiveWins = 0
			}
			// Ranked ladder: shift the coach's 1v1 Strength (+win / -loss). This
			// moves its ladder position and drives the Level/Rank the client shows
			// on the results screen from the 8300 strength map.
			t.Coach.Strength = domain.ApplyFightStrength(t.Coach.Strength, won)
			t.Coach.Mu.Unlock()
			if d.Store != nil {
				_ = d.Store.Coaches.Save(t.Coach)
			}
			if won { // faucet: award tokens + push the updated wallet (4001)
				d.awardFightWin(t.Coach.ID, t.Session)
			}
		}
		// Challenge (PvE) rewards are granted even though the fight is unranked —
		// the cards ARE the point of a challenge. Deliberately outside the
		// !f.Practice guard above, which only governs stats and ladder movement.
		// Guarded on the coach being real rather than on having a session, so a
		// coach that wins while disconnected still gets paid (the session is only
		// needed to push the inventory live), and so a win by the demon side never
		// writes cards to the synthetic opponent's id.
		if won && f.ChallengeID != 0 && !isSyntheticCoach(t.Coach.ID) {
			// Keep the granted cards: they are what the results panel shows under
			// "Cartes gagnées". Awarding them without reporting them (as before)
			// left that panel blank on the very fight that paid out.
			wonCardsByCoach[t.Coach.ID] = d.recordChallengeVictory(t.Coach.ID, t.Session, f.ChallengeID)
		}
		entry := endFightCoach{ID: t.Coach.ID, Strength: t.Coach.Strength}
		if won {
			winners = append(winners, entry)
		} else {
			losers = append(losers, entry)
		}
	}
	// Coach META pass: XP / morale / fatigue per fighter + coach reputation. It
	// must run BEFORE the packet is built, because its output IS part of 8300.
	reports, standingByCoach, killed, injured := d.runPostFightMeta(f, winnerTeam)

	// `standingWon` is a single scalar in 8300, so each coach must receive its
	// own frame when the awards differ (they do: a win pays more than a loss).
	// One shared frame would credit the loser with the winner's reputation.
	if len(standingByCoach) > 0 {
		for _, t := range f.Teams {
			if t == nil || t.Coach == nil || t.Session == nil {
				continue
			}
			end, err := buildEndFightFull(f.nextActionUID(), winners, losers,
				reportsFor(reports, t.Coach.ID), standingByCoach[t.Coach.ID], killed, injured,
				wonCardsByCoach[t.Coach.ID])
			if err == nil {
				_ = t.Session.Send(end)
			}
		}
		// Spectators still need the result, without any reputation of their own.
		// Spectators have no roster of their own to resolve reports against.
		if end, err := buildEndFightFull(f.nextActionUID(), winners, losers,
			nil, 0, killed, injured, nil); err == nil {
			f.broadcastSpectators(end)
		}
	} else if len(wonCardsByCoach) > 0 {
		// No progression (a challenge fight), but cards WERE won, so each coach
		// still needs its own frame carrying its own winnings.
		for _, t := range f.Teams {
			if t == nil || t.Coach == nil || t.Session == nil {
				continue
			}
			// This branch is only reached when progression did NOT run
			// (fightFeedsProgression is false for practice and challenge fights), so
			// there are no per-fighter debriefs to send - only the cards won.
			end, err := buildEndFightFull(f.nextActionUID(), winners, losers,
				nil, 0, killed, injured, wonCardsByCoach[t.Coach.ID])
			if err == nil {
				_ = t.Session.Send(end)
			}
		}
		if end, err := buildEndFight(f.nextActionUID(), winners, losers); err == nil {
			f.broadcastSpectators(end)
		}
	} else {
		end, _ := buildEndFight(f.nextActionUID(), winners, losers)
		f.broadcast(end)
	}

	// Evolution mode: a fighter that fell to 0 HP dies for good. Persist those
	// deaths and push the refreshed roster so the graveyard fills from real play.
	if f.Evolution {
		d.persistEvolutionDeaths(f)
	}

	f.setPhase(PhaseEnded)
	f.stopClock()
	f.stopGrace()
	f.stopActor()
	d.Fights.Remove(f)
	d.Log.Info("fight ended", "id", f.ID, "winnerTeam", winnerTeam)
}

// persistEvolutionDeaths marks every fighter that fell to 0 HP in an evolution
// fight as DEAD (state 2), persists it, and pushes the refreshed roster (6006) to
// its online coach. This is the only path that fills the graveyard from real play
// (state 2→3 burial and 2→0 resurrection are the player's own actions).
//
// Rules (from the client's evolution end-fight path): both sides' downed fighters
// can die — we take the minimal-correct "all downed fighters die" (the retail
// per-fighter death-CHANCE, modifiable by effect-7 cards, is not modelled). Only
// real persisted fighters count: synthetic opponents (sparring dummy, challenge
// demons) have no DB row and no real coach, so they are skipped.
//
// Fighters land in state 2 (dead), NOT 3 (graveyard); the client shows them as
// "dead but present" until the player buries (23000) or resurrects (22099) them.
func (d *Deps) persistEvolutionDeaths(f *Fight) {
	if d.Store == nil {
		return
	}
	for _, t := range f.Teams {
		if t == nil || t.Coach == nil || isSyntheticCoach(t.Coach.ID) {
			continue
		}
		var dead []string
		for _, ff := range t.Fighters {
			if ff.HP > 0 || ff.Fighter == nil || ff.Fighter.ID == 0 {
				continue // survived, or a synthetic/placeholder fighter with no DB row
			}
			if err := d.Store.Fighters.SetState(ff.Fighter.ID, domain.FighterStateDead); err != nil {
				d.Log.Warn("persist fight death", "fighter", ff.Fighter.ID, "err", err)
				continue
			}
			dead = append(dead, ff.Fighter.Name)
		}
		if len(dead) == 0 {
			continue
		}
		d.Log.Info("evolution fight deaths", "coach", t.Coach.Name, "dead", dead)
		if t.Session != nil {
			_ = t.Session.pushFighterList() // 6006: the client applies the new states
		}
	}
}
