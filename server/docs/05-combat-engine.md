# 5. Combat Engine Design

This is the primary value-add of this rewrite: the current Java server has **no combat
engine at all** — only the presentation phase (teleport players in, spawn actors) is
implemented; placement, turn order, spell casting, damage, and fight-end are unbuilt
(`Fight.java` stops after `startPresentation()`, and several combat opcodes are defined
but never sent — see `02-protocol.md` §2.3.1's `[NOT IMPLEMENTED]` rows).

The design below reconstructs the intended rules from Ankama's own reference
implementation, present in this repo's decompiled `data/stc/com/ankamagames/baseImpl/common/clientAndServer/game/**`
and `data/stc/com/ankamagames/dofusarena/common/game/**` — a shared client/server engine
that the original DofusArena team clearly intended to port server-side but never finished.
We reimplement it idiomatically in Go rather than transliterating the Java, but preserve
the exact game rules (formulas, ordering, timing) for compatibility with client-side
prediction/animation logic that already exists compiled into the client.

## 5.1 Fight lifecycle (state machine)

```
PRESENTATION → PLACEMENT → OBSERVATION → ACTION → (fight end) → NONE
```

Reconstructed from `AbstractFight` (dofusarena reference) + current opcodes:

| Phase | Trigger to enter | Trigger to leave | Wire opcodes |
|---|---|---|---|
| PRESENTATION | Fight created (matchmaking or duel accept) | 20s clock, or both teams ready | `CREATE_FIGHT`(8000) → `START_PRESENTATION`(8010) → `END_PRESENTATION`(8018) |
| PLACEMENT | End of presentation | 30s clock, or both teams ready | `START_PLACEMENT`(8020), `MOVE_TO_FREE_PLACEMENT`(8022) per-move, `TEAM_MATE_SET_READY_FOR_PLACEMENT`(8012), `END_PLACEMENT`(8028) |
| OBSERVATION | End of placement | 10s clock, or both teams ready | `START_OBSERVATION`(8030), `TEAM_MATE_SET_READY_FOR_OBSERVATION`(8024), `END_OBSERVATION`(8038) |
| ACTION | End of observation | last standing team, or forfeit | `START_ACTION`(8040) → repeated table-turns (§5.2) → `END_FIGHT`(8300) |

Each phase transition is implemented in Go as a `Fight` actor state (`combat/fight.go`)
with:
- an entry function (broadcast phase-start packet, reset any phase-scoped state),
- a `time.Timer` for the phase's clock (20s/30s/10s respectively — matches
  `AbstractFightTimeline.startClock(ms, eventType)` calls found in reference impl),
- a "ready" tracker per team-mate; phase ends early the instant all participants have
  signaled ready, or the timer fires (forced advance), whichever comes first.

## 5.2 Turn-based timeline

Reconstructed from `BasicTimeline`/`TurnBasedTimeline`/`AbstractFightTimeline`.

### 5.2.1 Turn order ("initiative")

Turn order is **fixed once at fight start**, NOT re-sorted every round. Higher `INIT`
characteristic goes earlier:

```go
// combat/timeline.go — BuildTurnOrder
func BuildTurnOrder(fighters []*Fighter) []*Fighter {
    order := make([]*Fighter, 0, len(fighters))
    for _, f := range fighters {
        i := sort.Search(len(order), func(i int) bool {
            return order[i].Characteristic(Init) <= f.Characteristic(Init)
        })
        order = append(order, nil)
        copy(order[i+1:], order[i:])
        order[i] = f
    }
    return order
}
```
(direct port of the insertion-sort-by-INIT logic in `AbstractFightTimeline.addFighter`).
Summoned creatures are inserted immediately after their summoner (`father`), not
re-sorted by their own INIT — preserve this for compatibility, it affects turn order
predictability that players may rely on.

### 5.2.2 Table-turn / turn structure

A **table turn** ("round") = one full pass through the fixed turn order. `newTableTurn()`
increments a round counter and re-queues every living fighter, in order, for one
individual turn each (`FighterTurnTimeEvent` per fighter).

```go
type Timeline struct {
    order          []*Fighter // fixed at fight start (+ summons inserted after father)
    tableTurn      int
    turnIndexInTable int
    currentFighter *Fighter
    turnClock      *time.Timer // 30s per-fighter turn timer
}

func (tl *Timeline) NextTurn() *Fighter {
    // skip dead/fled fighters; if we've walked past the end of order, call NewTableTurn()
    // and wrap to the start; return the next living fighter and (re)arm the 30s clock.
}
```

**Turn begin**: reset the fighter's AP and MP to their max values (direct port of
`AbstractFight.onFighterStartTurn`: `AP.toMax()`, `MP.toMax()`), broadcast
`FIGHTER_TURN_BEGIN`(8104) (and `NEW_TABLE_TURN_BEGIN`(8100) if this is turn 1 of a new
round).

**Turn end**: player-initiated (`FighterEndTurnRequestMessage` → broadcast
`FIGHTER_TURN_END`(8106)) or clock-driven (30s timeout auto-ends the turn — matches
`AbstractFightTimeline.startTurnClock()`).

## 5.3 Fighter data model

```go
// combat/fighter.go
type Fighter struct {
    ID       int64
    TeamID   uint8
    Breed    uint8
    Position Point3          // {X, Y int32; Z int16}
    Direction Direction8      // 8-way facing, used for hit-location + push direction calc

    Characteristics map[CharacteristicType]*Characteristic // HP/AP/MP/INIT/RES_*/DMG_*/etc.
    Properties       PropertyFlags                          // bitmask: Invisible/Stabilized/Petrified/Rooted
    RunningEffects   []*RunningEffectInstance                // active buffs/debuffs/DoTs/states

    CarriedFighter   *Fighter // this fighter is carrying another (piggyback mechanic)
    CarriedByFighter *Fighter

    SpellCastHistory *SpellCastHistory // per-spell cast counters for frequency limits (§5.5)
    IsDead           bool
}

func (f *Fighter) Height() int {
    const baseHeight = 6
    if f.CarriedFighter != nil {
        return baseHeight + f.CarriedFighter.Height()
    }
    return baseHeight
}
```

### 5.3.1 Characteristics

Direct port of `FighterCharacteristicType` enum, with bounds preserved:

```go
type CharacteristicType int

const (
    HP CharacteristicType = iota // 0..max, breed-defined max
    AP                            // 0..12, default 6
    MP                            // 0..8, default 3
    Init
    ResPercent; ResFirePercent; ResWaterPercent; ResWindPercent; ResEarthPercent   // -100..100
    DmgPercent; DmgFirePercent; DmgWaterPercent; DmgWindPercent; DmgEarthPercent   // -100..100
    Res; ResFire; ResWater; ResWind; ResEarth                                      // flat, unbounded
    Dmg; DmgFire; DmgWater; DmgWind; DmgEarth; DmgNeutral                          // flat, unbounded
    Range                          // spell range boost
    CriticalRate; FumbleRate       // 0..100
    NbSummons
    Heal
    ResAPLoss; ResMPLoss           // -100..100, resistance to AP/MP removal effects
    ResInPercent; DmgInPercent     // generic all-element %
    DmgRebound                     // 0..99, % of damage reflected to attacker
)
```

Bounds enforcement lives on `Characteristic.Add(delta)`/`.ToMax()`/`.SetValue()`, clamping
per-type min/max exactly as `AbstractCharacteristic` does.

### 5.3.2 Breed base stats — OPEN QUESTION

The Java codebase and `.dat` files contain **no breed base-stat table** (`breeds.dat` is a
0-byte file, see `04-game-data-format.md` §4.7). Breed HP/AP/MP/INIT maxes and
close-combat AP cost must be sourced from the client's compiled `Breed.java`/related
classes (not yet reviewed) or hand-authored. **This must be resolved before the combat
engine can be functionally tested end-to-end** — flagged as a phase-4 blocker in
`07-roadmap.md`.

## 5.4 Effect system

Two-layer design, ported faithfully from `Effect` (static definition) +
`RunningEffect` (behavior):

```go
// combat/effect/effect.go — static, data-only, loaded from spells.dat/cards.dat/events.dat
type EffectDef struct {
    ID                     int32
    ActionID                int32          // selects which RunningEffect implementation runs
    Params                  []float32       // dice/magnitude args, shape depends on ActionID
    Area                    AreaOfEffect    // §5.7
    Duration                [2]int32        // [tableTurns, turns]; 63 in either slot = infinite
    ApplyDelay               int32
    TriggersBefore, TriggersAfter, EndTriggers []int32 // trigger-bit IDs, see §5.4.2
    IsCritical                bool
    AffectedByLocalisation     bool          // hit-location bonus applies (see damage formula)
    TargetValidator           TargetValidator
}

// combat/effect/running.go — behavior, one implementation per ActionID
type RunningEffect interface {
    ActionID() int32
    ComputeValue(ctx *EffectContext) float64
    Execute(ctx *EffectContext) error
}
```

`Registry: map[int32]RunningEffect` — populated at package-init time with all concrete
implementations (§5.4.1), looked up by `EffectDef.ActionID` when a spell/card effect
fires. This mirrors `StaticRunningEffect`'s singleton-per-actionId registry pattern from
the reference implementation but as an explicit Go map instead of reflection/DI magic.

### 5.4.1 Effect catalog to implement (from reference `runningEffect/` package, ~35 classes)

Grouped by mechanic, implement roughly in this priority order for an MVP-to-full pipeline:

1. **Damage/heal** (MVP-critical): `HPLoss` (§5.6 has the full formula), `HPGain`,
   `HPLeech`, `HPDebuff`.
2. **Resource cost** (MVP-critical): `APLoss`, `APUse`, `MPUse` — AP/MP loss is resistable
   via `ResAPLoss`/`ResMPLoss` characteristics.
3. **Characteristic modification**: `CharacGain`, `CharacLoss`, `CharacBuff`,
   `CharacDebuff`, `CharacLeech`, `CharacPoison` — generic "add/subtract X from
   characteristic Y for a duration", each characteristic type has its own trigger-bit ID
   for the pub-sub system (§5.4.2).
4. **Movement**: `Push`, `Pull`, `Rapprochement`, `Teleport`, `ExchangePosition` — see
   §5.6.2 for the push/pull cell-walk + fall-damage algorithm.
5. **Property/state toggles**: `Root`, `Petrified`, `Stabilize`, `SetVisible`/`SetInvisible`
   — flip `PropertyFlags` bits (`Invisible=1, Stabilized=2, Petrified=3, Rooted=4`).
6. **State bundles**: `ApplyState`/`State` — a named bundle of sub-effects with its own
   end-trigger set (e.g. "Poisoned" = HPLoss-over-time + a status icon), not a single
   characteristic change.
7. **Summoning**: `Summon`, `SummonDouble`, `SummonMirror` — spawn a new `Fighter` as a
   child of the caster, inserted into turn order right after the summoner (§5.2.1).
   **Wire note:** a mid-fight summon is spawned client-side *entirely* from the
   `SUMMON` `RUNNING_EFFECT_ACTION` — the client's `Summon.execute()` calls
   `caster.summonCreature(newTargetId, cell, value)` → `summonFighter()` →
   `NetFightActorsFrame.addMobile()`. The server must therefore **NOT** also send
   an `ACTOR_APPEAR` (4102) for the summon (doing so `addMobile()`s a duplicate at
   the same id and freezes the client's action sequence). The blob carries the new
   summon's id in the **target** field (`Summon.unserializeTarget` reads it as
   `m_newTargetId`) and the `SummoningDefinition` id in the **value** field
   (`= params[0]`, `Summon.computeValue`); `value 0` makes `summonCreature` fail
   with "SummoningDefinition id=0 est inconnue". See `applySummon` (`effects.go`).
8. **Special mechanics**: `Carry`/piggyback (§5.3, `Height()`), `SpellRebound`,
   `StrikeBack`, `Death`, `SetEffectArea` (spawns a persistent ground zone, §5.7's
   `BasicEffectArea`).

### 5.4.2 Trigger (pub-sub) system

Every combat event (start-turn, end-turn, take-damage-of-element-X, death, spell-cast, ...)
maps to a numeric trigger ID. Effects declare interest via a bitset
(`TriggersBefore`/`TriggersAfter`/`EndTriggers`). This is how reactive effects (damage
reflection, "on next hit" buffs, conditional removal) work without hardcoding every
interaction pairwise.

```go
// combat/effect/trigger.go
type TriggerBus struct {
    active map[int64][]*RunningEffectInstance // fighterID -> active instances
}

func (b *TriggerBus) Fire(fighterID int64, trigger TriggerID, ctx *EffectContext) {
    for _, inst := range b.active[fighterID] {
        if inst.Def.MatchesTrigger(trigger) {
            if inst.Def.IsEndTrigger(trigger) {
                b.unapply(inst)
            } else {
                inst.Effect.Execute(ctx) // re-execute on trigger (e.g. DoT tick)
            }
        }
    }
}
```
Known trigger IDs to preserve for compatibility (extracted from reference impl
mentions): generic damage = bit 2, element-specific damage = bits 5(fire)/6(physical)/
7(water)/8(wind), AP change = bit 3, CRITICAL_RATE = bit 53, DMG = bit 63, DMG_EARTH =
bit 171. **Full trigger-ID table must be extracted from
`dofusarena/common/game/effect/RunningEffectConstants.java` during implementation** — not
fully enumerated in this pass, flagged as a phase-4 research task.

## 5.5 Spell cast validation pipeline

Direct port of `AbstractFight.getSpellCastValidity()`. Validation short-circuits on first
failure, returns one of:

```go
type SpellCastValidity int

const (
    Valid SpellCastValidity = iota
    ValidButNoEffectOnTarget
    InvalidSpell
    InvalidLineOfSight
    InvalidTargetCell
    InvalidRange
    NotEnoughAP
    TooManyCastsOnThisTarget
    TooManyCastsThisTurn
    LastCastTooRecent
    SpellUnknown
    CellNotFree
    CellsNotAligned
    CastCriterionsNotValid
)
```

Order of checks (`combat/spell/validate.go`):

1. Spell must exist and be owned by the caster (in their `Fighter.SpellIDs`, or a shared
   teammate inventory if the fighter is a summon).
2. **AP cost**: `spell.ActionPoints > fighter.AP.Value` → `NotEnoughAP`.
3. If a target cell is specified, it must pass `getCellValidity()` (walkable/exists).
4. **Range** (Manhattan distance, `RANGE` characteristic extends max range only, never
   min):
   ```go
   dist := abs(target.X-caster.X) + abs(target.Y-caster.Y)
   maxRange := spell.RangeMax
   if maxRange > 1 {
       maxRange = max(maxRange + caster.Characteristic(Range), spell.RangeMin)
   }
   if dist < spell.RangeMin || dist > maxRange { return InvalidRange }
   ```
5. **Line alignment**: if `spell.CastOnlyLine`, target must share X or Y with caster
   (orthogonal only, no diagonals).
6. **Free-cell requirement**: if `spell.NeedFreeCell` and target cell occupied →
   `CellNotFree`.
7. **Cast-frequency limits** (`SpellCastHistory`, per fighter per spell):
   - `MinCastInterval` (in table-turns; `63` = never again this fight).
   - `CastMaxPerTurn`.
   - `CastMaxPerTarget` (same spell on same target, per turn).
8. **Line of sight**: if `spell.CastTestLOS`, check from caster position, retry from
   caster's "head" (`position.Z + Height()`) if blocked at ground level before failing
   (allows lobbing over obstacles). If LOS isn't spell-required, the target cell itself
   still needs `getLineOfSightEndValidity()`.
9. **Custom criteria**: pluggable `Criterion` checks parsed from the spell's `criterion`
   string field (§4.3) — e.g. "can only cast while summon count < N". Implement as a small
   expression evaluator or a registry of named criterion functions, mirroring
   `dofusarena/common/game/ai/*Criterion.java`.
10. **Per-effect target validity**: for each of the spell's `EffectDef`s, check its
    `TargetValidator` against the resolved target(s) at that cell; if none of the spell's
    effects can legally affect anything there → `ValidButNoEffectOnTarget` (cast succeeds,
    consumes AP, does nothing — e.g. casting a friendly-only heal on an empty cell).

Execution (`combat/spell/cast.go`), once validity == `Valid` or `ValidButNoEffectOnTarget`:
1. For each `EffectDef` in the spell, resolve targets via `AreaOfEffect` (§5.7) +
   `TargetValidator`, then run the matched `RunningEffect` (§5.4).
2. Record the cast in `SpellCastHistory` (for frequency-limit checks on subsequent casts).
3. Deduct AP via the same `APUse` running-effect pathway used by other AP-costing actions
   (keeps AP deduction uniform/triggerable rather than a special case).
4. Broadcast `SPELL_CAST`(8110) + any resulting `RUNNING_EFFECT_ACTION`(8120) /
   `FIGHT_ACTION_SEQUENCE_EXECUTE`(8200) packets so the client can animate the result.

Card use (`getCardUseValidity`) and close combat (`getCloseCombatValidity`, fixed AP cost
from breed data, range must be exactly 1/adjacent) follow the identical shape and should
share the validation pipeline via a common interface (`Castable`) rather than duplicating
the 10-step check list.

## 5.6 Damage formula (worked example — `HPLoss`)

Ported exactly from `HPLoss.computeValue` (reference impl), since this is the formula
players will feel most directly and any deviation would be a noticeable balance/compat
break:

```go
func ComputeHPLoss(caster, target *Fighter, params []float32, element Element, affectedByLoc bool) int {
    base := rollBaseValue(params) // 1 param = fixed value; 3 params = diceCount,diceFaces,modifier via DiceRoll

    modificatorPercent := caster.Characteristic(DmgInPercent) + caster.Characteristic(dmgPercentFor(element)) -
        target.Characteristic(ResInPercent) - target.Characteristic(resPercentFor(element))

    value := base + float64(caster.Characteristic(Dmg)+caster.Characteristic(dmgFlatFor(element))) -
        float64(target.Characteristic(Res)+target.Characteristic(resFlatFor(element)))

    value = value * (100 + modificatorPercent) / 100 // percent modifiers applied LAST

    if affectedByLoc && element != Physical {
        switch hitLocation(caster, target) {
        case Back:
            value *= 1.30
        case Side:
            value *= 1.15
        } // Front: no bonus
    }

    finalDamage := max(0, randomRound(value))

    if reboundPct := target.Characteristic(DmgRebound); reboundPct > 0 && caster != target {
        rebound := randomRound(float64(finalDamage) * float64(reboundPct) / 100)
        if rebound > 0 {
            applyForcedDamage(caster, rebound) // no further computation, straight HP subtraction
            finalDamage -= rebound
        }
    }
    return finalDamage
}
```

Hit-location bonus (`isAffectedByLocalisation`): resolved via `PartLocalisator` — the
attack vector determines which of front/side/back was struck; **back +30%, side +15%,
front +0%**. Only applies to non-physical elements and only when the spell effect flags
`AffectedByLocalisation`.

### 5.6.1 `randomRound`

Not fully detailed in the reference exploration pass — likely a "round half up/down
randomly weighted by the fractional part" function (common in Dofus-family games to avoid
systematic rounding bias on repeated small-damage ticks). **Extract exact behavior from
`ValueRounder.randomRound()` in the reference source before finalizing `combat/effect/`
implementation** — flagged as a phase-4 research task.

### 5.6.2 Push/Pull movement + fall damage

```go
func ComputePush(mover *Fighter, direction Direction8, distance int, provider CellInfoProvider) (finalPos Point3, fallDamage int) {
    if mover.Properties.Has(Stabilized) || mover.Properties.Has(Rooted) {
        return mover.Position, 0 // immune, effect does not execute
    }
    pos := mover.Position
    for i := 0; i < distance; i++ {
        next := pos.Step(direction)
        altitude, blocked := provider.ArrivalAltitude(mover, pos.Z, direction)
        if blocked || abs(int(altitude)-int(pos.Z)) > 2 {
            // stopped by obstacle/height: 3 HP fall damage per remaining cell
            return pos, 3 * (distance - i)
        }
        if provider.IsOffMap(next) {
            // pushed into the void: 6 HP fall damage per remaining cell
            return pos, 6 * (distance - i)
        }
        pos = next
    }
    return pos, 0
}
```
Fall damage rates (**3 HP/cell if stopped by an obstacle, 6 HP/cell if pushed off-map**)
are preserved exactly from the reference `Push.computeMovement()`.

Two further reference behaviors from `Push.execute`/`computeValue` (implemented in
`applyPushPull`, matching the pbworks wiki's Fear notes):
- **Obstacle fighter shares the impact.** If the push is stopped by *another fighter*,
  that blocking fighter takes the SAME fall damage as the pushed target — the wiki's
  "Fearing someone into another character will cause both to lose 3 damage each"
  (`Push.execute` fires a second EARTH `HPLoss` on the obstacle `EffectUser`).
- **Push triggers ground effect-areas.** After moving, `applyPushPull` calls
  `checkInAndOut(startPos, arrivalPos, target)` (mirroring `Push.execute`'s own
  `EffectAreaManager.checkInAndOut`), so shoving a fighter across/onto a trap or glyph
  fires it — the wiki's "push someone into a trap".

## 5.7 Area-of-effect shapes

Ported from `AreaOfEffectEnum` + concrete shape classes:

```go
type AreaShape int16
const (
    Point AreaShape = 1   // single target cell only
    Circle AreaShape = 2  // hit-test = true Euclidean circle; PREVIEW pattern = diamond (quirk, see below)
    Cross  AreaShape = 3  // plus-sign along both axes, `size` cells each direction
    T      AreaShape = 4  // directional beam (`height`) + perpendicular bar (`width`, forced odd) at the far end
    Empty  AreaShape = 32767 // no targets
)

func (a AreaOfEffect) IsPointInside(source, center, point Point3) bool {
    switch a.Shape {
    case Point:
        return point == center
    case Circle:
        r := a.Size[0]
        dx, dy := point.X-center.X, point.Y-center.Y
        return dx*dx+dy*dy <= r*r // true circle for hit-testing
    case Cross:
        size := a.Size[0]
        return (point.X == center.X && abs(point.Y-center.Y) <= size) ||
               (point.Y == center.Y && abs(point.X-center.X) <= size)
    case T:
        return tShapeContains(source, center, point, a.Size[0], a.Size[1]) // height, width
    case Empty:
        return false
    }
    return false
}
```

**Known quirk to preserve**: `Circle`'s *client-preview pattern* (used for cell
highlighting before cast) is generated as a taxicab diamond
(`for x in -r..r: y range = ±(r-|x|)`), while the actual `IsPointInside` hit-test uses true
Euclidean distance. This mismatch exists in the reference implementation — replicate it
exactly rather than "fixing" it, since the client's own highlight rendering already
expects this exact behavior and changing only the server side would desync visual preview
from actual hit resolution.

`BasicEffectArea` (persistent ground zones — traps/glyphs, distinct from instantaneous
spell splash) wraps an `AreaOfEffect` + effect list + per-target "already triggered"
tracking (for glyphs that fire once per fighter until they leave and re-enter) — implement
as `combat/effectarea/ground_zone.go`, evaluated once per fighter movement/turn-start
tick rather than continuously.

## 5.8 Pathfinding & movement

Ported from `framework/ai/pathfinder/PathFinder.java` — classic **A\*** :

- Orthogonal step cost = 1.0, diagonal = 1.4142 (configurable "short diagonal" heuristic
  variant, matching Dofus-style movement rather than true-Euclidean diagonal).
- Heuristic: Manhattan distance if diagonals disabled; otherwise
  `min(dx,dy)*1.4142 + |dx-dy|` (true diagonal) or `min(dx,dy) + |dx-dy|` (short-diagonal
  variant) depending on config.
- Height-aware: each step queries `ArrivalAltitude(mover, z, direction)`; a step is
  blocked if the height delta exceeds the mover's jump limits (**default max ascend 4,
  max descend 4**, or the carrier's height if being carried piggyback, §5.3).
- **No corner-cutting**: diagonal moves require both orthogonal "corner" cells to also be
  passable.
- Movement obstruction from other fighters is a simple linear scan over the fight's
  current fighter list checking cell+height overlap (matches `BasicFight.getMovementObstacle`,
  a linear scan — acceptable at this scale of ~2-12 fighters per fight, no spatial index
  needed).
- MP cost = 1 per path step, deducted via the same `MPUse` running-effect pathway spell
  casting uses for AP (§5.5 step 3), so movement participates in the same trigger/resist
  system as spell-driven MP loss.

Go implementation: `combat/pathfind/astar.go`, operating on the `Fight`'s own fighter list
as the obstacle provider (implements a small `CellInfoProvider` interface) — no separate
persistent map/grid structure needed since the fight itself is the spatial index, exactly
as in the reference implementation (`BasicFight` implements the LOS/obstacle provider
interfaces directly rather than delegating to a separate grid object).

## 5.9 Fight end conditions

Ported from `BasicFight.checkFightEnd()`:
- Called after every `killFighter()`.
- Collect the set of distinct teams that still have at least one living fighter.
- If fewer teams remain than the fight's minimum team count (2 for a standard duel), the
  fight ends: fire `onTeamWin` for each surviving team, `onTeamLose` for eliminated ones,
  then tear down (stop timeline, clear fighters, notify `FightManager` to remove this
  fight from its registry, broadcast `END_FIGHT`(8300)).
- `AreOpponent(f1, f2)`: true iff both fighters belong to fight-teams and those teams
  differ — used throughout target validation (e.g. offensive spells reject same-team
  targets).

## 5.10 What's explicitly out of scope for v1 combat engine

To keep the initial combat engine shippable, the following reference-implementation
features are documented here for completeness but deferred:
- `CHANNEL_*` multi-channel chat opcodes (unrelated to combat but flagged in protocol doc).
- AI-controlled fighters/PvE (the `ai/CriteriaCompiler` exists in the reference tree but
  there's no evidence the current game mode uses AI opponents — confirm with product
  intent before investing here).
- Full `Card` (equipment-in-combat) effect catalog — implement the validation pipeline
  (§5.5) generically enough that cards slot in later without a redesign, but the full
  ~35-effect catalog port (§5.4.1) can be prioritized to cover spells first, cards second.
