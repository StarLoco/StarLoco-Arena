# Turn-Based Combat Engine: Design & Action Opcodes

> Client + reference-implementation source of truth. **This file corrects/expands
> [`../05-combat-engine.md`](../05-combat-engine.md)** — see the "Corrections" section for a
> consolidated diff. It also documents every combat-action wire opcode (8100-8300 range, plus
> the 4500s in-fight actor opcodes) — **all of which are now implemented and wired** in the Go
> server, across Phases A-M of `docs/08-java-parity-roadmap.md` (§8.9-8.12 there is the
> authoritative "what shipped" summary; this file's per-opcode "Status:" markers below were
> refreshed to match). The legacy Java server itself never built a combat engine (only the
> presentation phase exists in Java) — the Go implementation is new work, not a Java port, for
> everything in this file. A handful of genuine simplifications/deferred *mechanics* remain (not
> gaps in opcode wiring) — see each opcode's Status line and `docs/08-java-parity-roadmap.md` §8.11
> for the full, current list of open follow-ups.
>
> Scope: everything after `START_ACTION` (8040, see
> [07-fight-lifecycle.md](./07-fight-lifecycle.md)) through `END_FIGHT` (8300).

---

## Part 1 — Core engine design (from the reference implementation)

### 1.0 Class hierarchy

The actual inheritance chain in the shared `data/stc` reference implementation:

```
BasicFight<F>                                    (base/clientAndServer, generic pooled fight container)
  └─ AbstractTurnBasedFight<F>                    (base/clientAndServer, adds turn-based timeline glue)
       └─ AbstractFight<F extends AbstractFighter> (dofusarena/common, adds PRESENTATION/PLACEMENT/
                                                     OBSERVATION/ACTION phase state machine, spell/
                                                     card/close-combat validation)
            └─ Fight (client-only concrete class)  ← no server-side concrete subclass exists today
```

and similarly for timelines:

```
BasicTimeline<TU,TI>                     (abstract engine: sorted-list priority queue + validate-gate advance loop)
  └─ TurnBasedTimeline<F>                (adds tableturn/turn counters, current-fighter tracking)
       └─ AbstractFightTimeline<F>       (dofusarena/common: adds real wall-clock timers via MessageScheduler)
            └─ Timeline (client-only concrete class)
```

**The Go combat engine should mirror `AbstractFight`/`AbstractFightTimeline`**, not the more
generic `BasicFight`/`BasicTimeline` — those lower layers exist only to be reusable across
different Ankama games and add no fight-specific rules by themselves.

### 1.1 Fight state machine

Phases (`AbstractFight.FightStatus`, `data/stc/.../dofusarena/common/game/fight/AbstractFight.java:41-44`):
```java
enum FightStatus { PRESENTATION, PLACEMENT, OBSERVATION, ACTION, NONE }
```

| Method | Line | Effect |
|---|---|---|
| `onFightCreatedAndInitialized()` | 482-486 | allocates `EffectContext`, sets `m_createdAndInitialized=true` |
| `start()` | 153-157 | starts timeline, calls `onFightStarted()` |
| `onFightStarted()` | 491-494 | calls `startPresentation()` — actual entry point |
| `startPresentation()` | 159-164 | `m_status=PRESENTATION`, `getTimeline().startPresentation()` |
| `startPlacement()` | 166-171 | `m_status=PLACEMENT`, `getTimeline().startPlacement()` |
| `startObservation()` | 181-190 | **guarded**: only valid if `m_status==PLACEMENT`, else logs error and returns `false` |
| `startAction()` | 198-213 | **guarded**: only valid if `m_status==OBSERVATION`. Calls `pushNewTableTurnEvent()` (schedules first table-turn) |
| `endPresentation()`/`endPlacement()`/`endObservation()` | 173-195 | thin wrappers calling `onXEnd()` hooks — no state change themselves (state change happens in the *next* `startX()` call) |

**Important:** `startObservation()`/`startAction()` are defensive state machines that **assert the
previous phase** — calling out of order logs an error and aborts rather than silently proceeding.
The Go port should preserve this guard (return an error / no-op on out-of-order phase advance)
rather than assuming callers always drive it correctly.

### 1.2 Fight-end (`BasicFight.java`)

- **`checkFightEnd()`** (399-422): early-returns `false` if not yet `m_createdAndInitialized`.
  Builds a map of distinct teams that still have any living fighter in `m_currentFighters`. As
  soon as `teamsLeft.size() >= m_minTeam`, returns `false` immediately (fight continues) — does
  **not** wait to enumerate every fighter. Otherwise fires `onTeamWin(team)` for each surviving
  team, then `endFight()`, returns `true`.
- **`killFighter(fighter)`** (340-360): throws if `fighter==null` or has no `TeamMate`. Calls
  `onFighterDeath`, `removeFighter` (removes from `m_currentFighters`), scans remaining fighters —
  if none belong to the dead fighter's team, fires `onTeamLose(team)`.
  **`killFighter` does NOT itself call `checkFightEnd()`** — the caller is responsible for calling
  `checkFightEnd()` after `killFighter()`. Do not assume this is automatic in a Go port.
- **`endFight()`** (452-479): stops+resets timeline, calls `onFighterRemovedFromFight()` for every
  fighter then clears `m_currentFighters`, destroys all effect areas, calls `teammate.onFightEnd()`
  for every teammate, clears `m_teams`, calls `onFightEnded()`, and **finally calls
  `FightManager.getInstance().destroyFight(this)`** — the fight unregisters itself as its last act.
- **`removeTeamMate(teamMate)`** (314-330): kills every fighter of that teammate, then if
  `checkFightEnd()` returns `false`, fires `onTeamMateRemovedFromFight`.

### 1.3 Pooling lifecycle — ignore for Go port

Every class implements Ankama's generic object-pool pattern (`onCheckOut`/`onCheckIn`/`release`) —
a Java GC-avoidance micro-optimization with **no gameplay semantics**. Should be entirely ignored
in the Go port (normal struct allocation + GC supersedes this). Flagged only so it isn't mistaken
for meaningful state-machine behavior when reading the source.

---

### 1.4 Timeline / turn-order system

This is the area most under-specified in the prior combat-engine doc, and where actual reference
behavior differs meaningfully from a naive "advancing queue" model.

#### `BasicTimeline<TU,TI>` — the generic engine

- `m_timeline: List<TimeEvent<TU,TI>>` — a manually-maintained **sorted** list (insertion-sorted,
  not a heap), acting as a priority queue ordered by `(when(), priority)`.
- `addTimeEventAt(te, tu)`: if `tu` compares before `now()`, pushed to the **front** and
  `nextTimeEvent()` fires immediately. Otherwise inserted at the first position where
  `tu.compareTo(event.when()) <= 0`, ties broken by priority (higher priority sorts *later* at
  equal time — lower priority number runs first at the same timestamp).

- **`nextTimeEvent()` — the actual advance loop, and the most important mechanic to replicate:**
  1. If `!m_isRunning`, return immediately — nothing happens while stopped.
  2. Peek the front event `te`.
  3. **`if (te.needValidation()) return;`** — a hard gate. Every `TimeEvent` has a boolean
     `m_valid` flag (default `true`), toggled via `validate()`/`unvalidate()`. Several event
     subclasses call `unvalidate()` inside their own `switchStatus()`.
     **This means the timeline does NOT auto-advance through turn-start→turn-end or
     phase-start→phase-end transitions on its own** — it stalls at the front event until something
     *external* calls `.validate()` and re-triggers processing. This is the mechanism behind
     `askForFighterStartTurn`/`askForFighterEndTurn`/`askForPresentationEnd`/etc. — they are
     literally "unblock the stalled timeline head, then drain."
  4. If `te.isActive()`: pop it off. If not instant/infinite, `switchStatus()` (flips `isActive`,
     re-`unvalidate()`s for turn/phase events), `shiftStart(now(), duration)` (re-timestamps it —
     e.g. a turn-time-event scheduled to *end* some ticks later), re-adds itself as a soon-to-fire
     "deactivation" event. Fires `onTimeEventActivated(te)`.
  5. If not active (processing its *deactivation*): removes it entirely, fires
     `onTimeEventDesactivated(te)`, releases it (pool cleanup).
  6. **Recurses**: keeps draining as many ready events as possible until it hits another
     `needValidation()` gate or an empty timeline.

  **Net effect**: each `TimeEvent` conceptually represents a *span* (start→end), and the same
  object cycles through the timeline twice — once as "activation," once (after `shiftStart`) as
  "deactivation" — with an explicit external validation gate in between for anything requiring
  player/clock input to end (turns, phases). **This two-pass single-object design is not the same
  as a simple independent `NextTurn()`/timer model** and should be replicated: a Go `TimeEvent`
  needs an explicit "pending validation" state, not just a queue pop.

- **Two-level dispatch**: `BasicTimeline.onTimeEventActivated/Desactivated` just forwards to a
  global listener (the owning `Fight`). `TurnBasedTimeline` *overrides* these to first handle its
  own internal bookkeeping (e.g. `startFighterTurn`/`newTableTurn`) and only then calls `super` to
  forward to the Fight. This double-dispatch layering (timeline handles internal turn/round
  bookkeeping → Fight handles game-visible reactions / network broadcast) is worth preserving as
  a package boundary in Go (e.g. a `Timeline` type owning turn bookkeeping, separate from the
  session layer owning network broadcast).

#### `TurnBasedTimeline<F>` — turn-order specifics

- State: `m_currentTableturn: byte`, `m_currentTurn: byte`, `m_currentFighter: F`,
  `m_orderedFigthers: List<F>` (the fixed turn order).
- `now()` returns a mutable singleton `TurnBasedTimeUnit` (pooling detail); `compareTo` compares
  `tableTurn` first, then `turn` — lexicographic `(tableTurn, turn)` ordering.
- **`askForFighterStartTurn(fighter)`**: only succeeds if the front event is a
  `FighterTurnTimeEvent`, `isActive()`, and belongs to the requested fighter — then `validate()`s
  and advances. Whether this fires automatically on turn arrival or waits for a client "ready"
  signal is **not resolved** from the base classes alone (no `m_readyCount`-style gating for
  individual turn starts, only for phase transitions) — flagged as a genuine unknown.
- **`askForFighterEndTurn(fighter)`**: requires `fighter == m_currentFighter` AND the front event
  is a `FighterTurnTimeEvent` that is **not** active (the "end" half) AND matches the fighter.
- `startFighterTurn(fighter)`: sets `m_currentFighter`, fires `onTurnStarted` (abstract),
  **increments `m_currentTurn`** (counts total turns taken this table-turn cycle, per-fighter-turn,
  not per-round).
- `endFighterTurn(fighter)` (private): only proceeds if `fighter == m_currentFighter`; clears
  `m_currentFighter`, fires `onTurnEnded`. Logs an error (does not throw) if called for the wrong
  fighter.
- `newTableTurn()`: resets `m_currentTurn=0`, increments `m_currentTableturn`, calls
  `initFighterTurnForOneTableTurn()`, fires `onNewTableTurn()`.
- `initFighterTurnForOneTableTurn()`: for each fighter in `m_orderedFigthers` **in list order**
  (this is where "fixed turn order" is enforced), enqueues one `FighterTurnTimeEvent` per living
  fighter for the round, spaced sequentially in "turn" units.
- **Mid-fight roster changes shift already-queued events**: `addFighterTimeEventAt` shifts every
  already-queued event at/after the insertion point by +1 turn unit before pushing the new
  fighter's own event — summons literally insert a slot into the current round's remaining
  schedule, not just appended for next round. `removeFighter` (death) does the mirror-image
  collapse: shifts subsequent events **back** by -1 turn unit, closing the gap, and purges any
  other timeline events related to the fighter as an effect-user. **This is not just an in-memory
  order-list update** — it matters specifically for summons/deaths happening *during* an active
  table-turn, not just at round boundaries.
- Event-type switch on activation/deactivation:
  - **type 107 `FighterTurnTimeEvent`**: activation → `startFighterTurn`; deactivation →
    `endFighterTurn`.
  - **type 106 `TableTurnTimeEvent`**: activation → `newTableTurn()`; deactivation → forwards only.
  - **type 108 `EffectAreaActivationTimeEvent`**: activation → `area.setActive(applicant)`.
  - default → forwards only.

#### `AbstractFightTimeline<F>` — real clocks + phase gating

The layer that wires wall-clock timers to the abstract event system — model the Go engine most
directly on this layer.

- **Phase clocks** (all type `1001` = `FightClockedPeriodTimeEvent`):
  - `startPresentation()`: **20000ms timer, subId 1, priority 3**
  - `startPlacement()`: **30000ms, subId 2, priority 2**
  - `startObservation()`: **10000ms, subId 3, priority 1**
  - `startTurnClock()` (private, only fires `if (m_isRunning)`): **30000ms per-fighter-turn timer,
    subId 4** — called immediately after `startFighterTurn(fighter)` succeeds.
- **`onMessage(Message)`** — the clock-expiry handler, switches on `msg.getSubId()`:
  - `1` → if clock id matches, `askForPresentationEnd()`
  - `2` → `askForPlacementEnd()`
  - `3` → `askForObservationEnd()`
  - `4` → if clock id matches, `askForFighterEndTurn(getCurrentFighter())`; else logs a mismatch
    (stale/duplicate clock, ignored)

  Both the "all ready" path and the "timer fired" path call the **exact same** `askForXEnd()`
  method, which validates the front `FightClockedPeriodTimeEvent` (guarded by matching phase, else
  logs an error and no-ops) and drains the timeline. Readiness tracking is bookkeeping in
  `AbstractFight` (`m_readyCount`/`m_coachCount`) that calls these same `askForXEnd()` methods once
  all parties are ready — the pending clock is explicitly cancelled (`removeClock`) at the moment
  of successful advance, whichever path triggered it, so a stale clock firing later is tolerated/
  ignored via the `m_lastStepClock`/`m_lastTurnEndClock` id-matching check.

- **`addFighter(fighterToAdd, pushEvent, ordered)`** — the complete turn-order insertion algorithm:
  - Dead fighters are silently ignored.
  - First fighter added → just appended.
  - **Not a summon**: if `ordered==true`, insertion-sort by `INIT` characteristic **descending**
    (walks forward while the new fighter's init is *lower*, i.e. higher/equal init sorts earlier).
    If `ordered==false`, appended at the very end unsorted (used for fighters joining outside
    normal ordering rules).
  - **Is a summon**: walks the list looking for `fighterToAdd.getFather() == fighter`; once found,
    keeps incrementing the insertion index past any *further* summons belonging to that same
    father — **the new summon is inserted after the father AND after any of the father's other
    already-inserted summons**, not merely "right after father" as a single-slot insert. Multiple
    summons from the same father queue up together in insertion order.
  - If `pushEvent==true` (mid-fight insertion): locates the existing `FighterTurnTimeEvent` for
    whichever fighter occupies the target index, reads its `TurnBasedTimeUnit`, inserts the new
    fighter's event at essentially the same/next turn slot, triggering the shift-cascade above.
  - The `ordered` boolean parameter is worth explicitly modeling in Go — decide which callers use
    which mode (fight setup vs. mid-fight join) rather than assuming everything is always
    INIT-sorted.

#### Time-event catalog

| Type ID | Class | Purpose | Priority | Instant? | Infinite? |
|---|---|---|---|---|---|
| 106 | `TableTurnTimeEvent` | table-turn (round) boundary | default (0) | no | no |
| 107 | `FighterTurnTimeEvent` | one fighter's turn slot; toggles active↔inactive, **increments priority each toggle** | starts 0, +1/toggle | no | no |
| 108 | `EffectAreaActivationTimeEvent` | activates a ground effect-area for an applicant target | 1 | **yes** | no |
| 1 | `RunningEffectDurationTimeEvent` | tracks a running effect's duration window; on deactivation, client calls `runningEffect.askForUnapplication()` | 1 | no | possible if duration ≥ 63 |
| 2 | `StaticRunningEffectDelayedTimeEvent` | delayed execution of a static running-effect | default (0) | **yes** | no |
| 1001 | `FightClockedPeriodTimeEvent` | phase-boundary marker, carries `FightStatus` | 1/2/3 per phase | no | no |

**`TurnBasedTimeInterval.isInfinite()`**: `turnDuration >= 63 || tableTurnDuration >= 63` — **the
magic "infinite" sentinel value is 63**. This is a general `TurnBasedTimeInterval` convention (not
spell-specific) — same convention applies to any duration-based scheduling including generic
running-effect durations, so a single shared "infinite sentinel" helper in Go effect+timeline code
makes sense rather than duplicating the check.

---

### 1.5 Fighter combat state

#### `BasicFighter` interface — minimal contract
```java
interface BasicFighter extends Poolable, Releasable, EffectUser, LineOfSightObstacle,
                                 MovementObstacle, PathFindMover {
  void onJoinFight(BasicFight); void onRemovedFromFight();
  void onNowAbleToFight(); void onNowUnableToFight();
  boolean isOnFight(); boolean canJoinFight();
  long getId(); void setId(long);
  BasicFight getCurrentFight();
  TeamMate getTeamMate(); FightingTeam getTeam(); void setTeamMate(TeamMate);
  void onSpecialFighterEvent(int);
  boolean getFlag(int); void switchFlag(int);
}
```

#### `AbstractFighter` fields

| Field | Notes |
|---|---|
| `m_id` | `long` |
| `m_breed` | `Breed` |
| `m_name` | `String`, default `""` |
| `m_skinIndex`, `m_sex` | `byte` |
| `m_teamMate` | `TeamMate<? extends AbstractFighter>` |
| `m_direction` | `Direction8`, default `SOUTH_EAST` |
| `m_value` | `short` — computed "point value" (breed value + card values + spell values) |
| `m_runningEffectManager` | `RunningEffectManager`, final |
| `m_position` | `Point3`, final |
| `m_partLocalisator` | `FourSidedPartLocalisator`, final — computes front/side/back hit location |
| `m_characteristics` | `TIntObjectHashMap<AbstractCharacteristic>` keyed by `CharacteristicType.getId()` |
| `m_properties` | `FighterPropertymanager` — property/state flags (invisible, etc.) |
| `m_spellCastHistory` | `SpellCastHistory`, final |
| `m_isDead` | `boolean` |
| `m_carriedFighter`/`m_carriedByFighter` | piggyback mechanic |
| `m_specialEventListener` | set to the owning `Fight` in `onJoinFight()` |

Constructor initializes **every** `FighterCharacteristicType` into `m_characteristics` with the
type's bounds — all fighters always have all characteristic slots present (not sparse).

`initializeCharacteristics()`: calls `makeDefault()` on every characteristic, then explicitly sets
`HP.max = breed.getBaseHp()`, `MP.max = breed.getBaseMp()`, `AP.max = breed.getBaseAp()`,
`INIT.max = breed.getBaseInit()`, then all four `.toMax()`, and sets
`CRITICAL_RATE = breed.getBaseCH()`, `FUMBLE_RATE = breed.getBaseCM()` — **confirms breed data must
supply baseHp/baseMp/baseAp/baseInit/baseCH/baseCM at minimum** (this is a genuine, currently-open
data-modeling blocker for the Go engine — `Breed`'s own source wasn't read in this pass).

**Equip-time passive bonuses (implemented).** After the breed base stats above are set, each
equipped fighter card's **`FIGHTER_CARD_EQUIP`** effect subset is applied to the fighter's
characteristics — e.g. a card carrying `CharacBuff(INIT, +60)` raises Initiative by 60. The
reference client does this on the inventory `ITEM_ADDED` event via
`AbstractFighter.applyCardEffects()` (client/.../game/fighter/AbstractFighter.java:602-615), *before*
combat begins, so the fighter enters the fight with its equipment deltas already baked in — which is
why equipment feeds turn order (the timeline is built from the post-equip `INIT` characteristic).
The Go engine reproduces this in `combat.ApplyEquipmentBonuses` (`internal/combat/equipment.go`),
called from `buildCombatTeam` (`internal/dispatch/handlers_fight.go`) **before** `NewFight`/
`NewTimeline` runs. A card's **`FIGHTER_CARD_USE`** effects are the separate, actively-castable
abilities fired on `FIGHTER_CARD_USE`(8107) — NOT applied as passive stats. The split is done at
load time by `gamedata.splitFighterCardEffects` (see `FighterCardTemplate.UseEffects`/`EquipEffects`).
`CardEquipped` (actionID 93) remains a genuine no-op marker; it is NOT where the passive stats live
(those are the `CharacBuff`/`CharacGain`/`CharacDebuff` action IDs 11/13/17/76/77/… inside the equip
container). Confirmed against real `cards.dat`: 127 fighter cards, 168 use-time + 122 equip-time
effects (74 of them Initiative buffs/debuffs).

`onFighterStartTurn` (in `AbstractFight.java:125-129`): `fighter.getCharacteristic(AP).toMax()`,
`.getCharacteristic(MP).toMax()` — **AP/MP fully refill every turn** (no partial regen).

`shouldBeDead()`: `getCurrentFight()!=null && HP.isZero()` — death detection is HP-reaching-exactly-
zero, checked **externally** (not automatic on every HP change) — some caller must poll/check this
after damage application and then invoke `killFighter()`.

`rollCriticalHitTest()`/`rollCriticalMissTest()`: `DiceRoll.roll(100) <= diceLimit` where
`diceLimit` = `CRITICAL_RATE`/`FUMBLE_RATE`; fumble test short-circuits `false` if `diceLimit <= 0`.

#### `FighterCharacteristicType` enum — exact table

Format `NAME(id, lowerBound, upperBound, defaultMin, defaultMax, defaultValue)`:

| Name | id | lower..upper bound | default min/max | default value |
|---|---|---|---|---|
| HP | 1 | 0..MAX_VALUE | 0/50 | 50 |
| AP | 2 | 0..12 | 0/6 | 6 |
| MP | 3 | 0..8 | 0/3 | 3 |
| INIT | 4 | 0..MAX_VALUE | 0/0 | 0 |
| RES_FIRE_PERCENT | 5 | -100..100 | -100/100 | 0 |
| RES_WATER_PERCENT | 6 | -100..100 | -100/100 | 0 |
| RES_EARTH_PERCENT | 7 | -100..100 | -100/100 | 0 |
| RES_WIND_PERCENT | 8 | -100..100 | -100/100 | 0 |
| DMG_FIRE_PERCENT | 9 | (see note) | -100/100 | 0 |
| DMG_WATER_PERCENT | 10 | (see note) | -100/100 | 0 |
| DMG_EARTH_PERCENT | 11 | (see note) | -100/100 | 0 |
| DMG_WIND_PERCENT | 12 | (see note) | -100/100 | 0 |
| RES | 13 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| RES_FIRE | 14 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| RES_WATER | 15 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| RES_EARTH | 16 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| RES_WIND | 17 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| DMG | 18 | MIN..MAX_VALUE | MIN/MAX | 0 |
| DMG_FIRE | 19 | MIN..MAX_VALUE | MIN/MAX | 0 |
| DMG_WATER | 20 | MIN..MAX_VALUE | MIN/MAX | 0 |
| DMG_EARTH | 21 | MIN..MAX_VALUE | MIN/MAX | 0 |
| DMG_WIND | 22 | MIN..MAX_VALUE | MIN/MAX | 0 |
| RANGE | 23 | MIN..MAX_VALUE | MIN/MAX | 0 |
| CRITICAL_RATE | 24 | 0..MAX_VALUE | 0/100 | 0 |
| FUMBLE_RATE | 25 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| NB_SUMMONS | 26 | 0..MAX_VALUE | 0/MAX_VALUE | 0 |
| HEAL | 29 | (see note) | 65036/500 | 0 |
| RES_AP_LOSS | 30 | -100..100 | -100/100 | 0 |
| RES_MP_LOSS | 31 | -100..100 | -100/100 | 0 |
| RES_IN_PERCENT | 32 | -100..100 | -100/100 | 0 |
| DMG_IN_PERCENT | 33 | -100..100 | -100/100 | 0 |
| DMG_REBOUND | 34 | 0..99 | 0/99 | 0 |

**Note on the `65036` bound oddity** (ids 9-12, 29): the decompiled source literally shows
`65036..500` as the lower..upper bound pair. This is almost certainly a **decompiler artifact** of
a `short`/packed negative constant (e.g. `(short)-100` misread) rather than real game data — the
"default min/max" columns *do* correctly show `-100/100`-ish ranges. **Treat the raw `65036` as a
decompilation artifact, not literal game data** — use the semantically sensible bound (matching
default min, e.g. -100) rather than porting `65036` verbatim. Flag this explicitly rather than
silently "cleaning it up" without a note, since it could otherwise mislead a byte-for-byte
cross-check against a real client build later.

**No `DmgNeutral`/`ResNeutral` characteristic exists** in this enum. "Physical"/neutral damage uses
the flat `DMG`/`RES` (ids 18/13) with no element suffix — there is no distinct fifth-element
constant. (A prior version of the Go combat-engine doc invented a `DmgNeutral` constant not present
in the reference source — corrected here, see "Corrections" below.)

---

### 1.6 FightManager's role

- Singleton, not injectable.
- `m_fights: TIntObjectHashMap<BasicFight>` — the live-fight registry, keyed by **int** fight ID.
- `m_fightModels: TIntObjectHashMap<BasicFight>` — a **separate** registry of fight *type
  templates* keyed by `typeId`. **Prototype/factory pattern**: `createFight(fightId, params,
  cellInfoProvider)` looks up the *template* fight for the requested type, calls
  `template.newParameterizedInstance(...)` to produce the live instance, registers it, calls
  `onFightCreatedAndInitialized()`.
- `setId(fight)`: assigns the next free ID by linearly probing upward (wrapping
  `Integer.MAX_VALUE → Integer.MIN_VALUE`) — **fight IDs can go negative** after wraparound, worth
  noting for a Go port if IDs are ever assumed non-negative (e.g. in protocol serialization as
  unsigned).
- `destroyFight(fight)`: removes from registry and releases — called by `BasicFight.endFight()`
  itself as its last step, not by external code.
- `getFightFromId(id)`: simple lookup, `null` if absent.

This is a much simpler registry than a full "combat manager service" — really just an
ID-allocator + type-template factory + live-instance map. A richer Go-side `combat.Manager`
(goroutine-per-fight, channels, etc.) is new design surface, not something mirrored from the
reference.

---

## Part 2 — Combat action opcodes (8100-8300, plus in-fight 4500s)

### Wire-format preliminaries

Every server→client message in this range (everything in
`serverToClient.fight`/`serverToClient.action`, plus 4506/4520/4522/4524) extends the abstract
class `FightActionMessage`, which prepends an **8-byte fight-action header**:
```
int32   uniqueId                 // this action's unique sequence id
int32   triggeringActionUniqueId // -1 if not triggered by another action, else the parent action's uniqueId
```
Every client→server message extends `OutputOnlyProxyMessage` → `ClientProxyMessage`, wrapped with
the standard 5-byte client header (`architectureTarget = 3` for all messages below).

The `FightActionType` enum is a **Java-side dispatch tag only, never serialized on the wire**: byte
ids 0-12 (SPELL_CAST=0, CARD_USE=1, CLOSE_COMBAT=2, EFFECT_EXECUTION=3, TACKLE=4, DIE=5,
FIGHT_END=6, CHANGE_DIRECTION=7, MOVE=8, EFFEC_AREA_ACTION=9, TURN_END=10, TURN_START=11,
NEW_TABLE_TURN=12).

**Cross-check (updated): every opcode in this section is now implemented and wired in the Go
server**, wired via `internal/dispatch/handlers_fight_combat.go` (Recv side) and
`internal/combat/{turns,combat_actions,effects,effectarea,fightend,packets}.go` (Send side/game
logic) — see each opcode's own Status line below for the exact function/file. This was completed
across Phases E-M of `docs/08-java-parity-roadmap.md`; the note this replaced (claiming the whole
section was unimplemented, with only a bare `RecvSpellCastRequest` constant) predates that work and
is no longer accurate. The few remaining genuine simplifications (not gaps in *wiring*, but in
*fidelity* vs. the reference) are called out per-opcode and summarized in that roadmap's §8.11.

---

### NEW_TABLE_TURN_BEGIN (Send 8100)
**Status:** implemented · see `internal/combat/turns.go`'s `startNextTurn` (broadcasts via
`buildNewTableTurnBegin`, `internal/combat/packets.go:38-44`) — Phase E, `docs/08-java-parity-
roadmap.md`. Note: the wire `eventId` field is always sent as 0 (no event-system integration).
**Client source:** `client/.../fight/NewTableTurnBeginMessage.java:25-82`
**Payload:**
```
int32   uniqueId                 // fight-action header
int32   triggeringActionUniqueId // fight-action header
byte    numTurns                 // new "table turn" number
int32   eventId                  // resolved client-side via AbstractEventManager
```
`FightActionType` = `NEW_TABLE_TURN`. `getActionId()` always 0.

### FIGHTER_TURN_BEGIN (Send 8104)
**Status:** implemented · see `internal/combat/turns.go`'s `startNextTurn` (broadcasts via
`buildFighterTurnBegin`, `internal/combat/packets.go:46-51`); also refills AP/MP via
`OnFighterStartTurn` — Phase E.
**Client source:** `client/.../fight/FighterTurnBeginMessage.java:22-68`
**Payload:** `int32 uniqueId; int32 triggeringActionUniqueId; long fighterId` (whose turn is starting).
`FightActionType` = `TURN_START`.

### FighterEndTurnRequestMessage (Recv 8105)
**Status:** implemented · handled by `handleFighterEndTurnRequest`
(`internal/dispatch/handlers_fight_combat.go:56-66`), forwarded to `Fight.handleFighterEndTurn`
(`internal/combat/turns.go:90-95`) → `askForFighterEndTurn` (`turns.go:76-87`) — validated against
`Timeline.CurrentFighter()`.
**Client source:** `client/.../fight/FighterEndTurnRequestMessage.java:23-52`
**Payload:** `long fighterId` (8 bytes, no header — plain request).

### FIGHTER_TURN_END (Send 8106)
**Status:** implemented · see `askForFighterEndTurn` (`internal/combat/turns.go:76-87`), broadcasts
via `buildFighterTurnEnd` (`internal/combat/packets.go:53-58`) — fired identically whether
triggered by the player's request (8105) or the 30s turn-clock timeout.
**Client source:** `client/.../fight/FighterTurnEndMessage.java:22-69`
**Payload:** `int32 uniqueId; int32 triggeringActionUniqueId; long fighterId` (whose turn just ended).
`FightActionType` = `TURN_END`. Note: distinct opcode from Recv 8105, just numerically adjacent.

### FighterCardUseRequestMessage (Recv 8107)
**Status:** implemented · handled by `handleFighterCardUseRequest`
(`internal/dispatch/handlers_fight_combat.go:105-119`), forwarded to `Fight.handleCardUse`
(`internal/combat/combat_actions.go:254`).
**Client source:** `client/.../fight/FighterCardUseRequestMessage.java:26-81`
**Payload (22 bytes):**
```
long    fighterId       // caster/user fighter id
int32   cardId          // card template id being used
int32   usePositionX    // target world X
int32   usePositionY    // target world Y
short   usePositionZ    // target altitude/Z
```

### FIGHTER_CARD_USE (Send 8108)
**Status:** implemented · built by `buildFighterCardUse` (`internal/combat/packets.go:60-71`),
broadcast from `Fight.handleCardUse` (`internal/combat/combat_actions.go:254-283`, broadcast call
at line 277).
**Client source:** `client/.../action/FighterCardUseMessage.java:25-140`
**Payload:**
```
int32   uniqueId                 // fight-action header
int32   triggeringActionUniqueId // fight-action header
long    userId
int32   cardId                   // resolved client-side via FighterCardManager
byte    criticalMiss             // 0/1
// --- only present if criticalMiss == 0 ---
byte    criticalHit              // 0/1
int32   usePositionX
int32   usePositionY
short   usePositionZ
```
Minimum size 21 bytes if miss, else 32 bytes. `getActionId()` returns card id (0 if unresolved).
`FightActionType` = `CARD_USE`.

### SPELL_CAST_REQUEST (Recv 8109)
**Status:** implemented · handled by `handleSpellCastRequest`
(`internal/dispatch/handlers_fight_combat.go:87-101`), forwarded to `Fight.handleSpellCast`
(`internal/combat/combat_actions.go:196`), which runs the full `validateCast`/`castCandidate`
pipeline (AP cost, range, line-of-sight, cast-frequency history, custom criteria — Phase F/L,
`docs/08-java-parity-roadmap.md`) before executing.
**Client source:** `client/.../fight/SpellCastRequestMessage.java:26-81` (also in legacy `OpCode.java:144`)
**Payload (22 bytes):**
```
long    fighterId       // caster fighter id
int32   spellId
int32   castPositionX
int32   castPositionY
short   castPositionZ
```
**Domain context:** `UsableSpell.getCastValidity()` delegates to
`Fight.getSpellCastValidity(fighter, spell, null)` — a `SpellCastValidity` enum used purely for
client-side UI gating (greying out unusable spells). Authoritative validation must happen
server-side on receipt.

### SPELL_CAST (Send 8110)
**Status:** implemented · built by `buildSpellCast` (`internal/combat/packets.go:73-84`),
broadcast from `Fight.handleSpellCast` (`internal/combat/combat_actions.go:196-250`, broadcast
call at line 244).
**Client source:** `client/.../action/SpellCastMessage.java:23-132`
**Payload:** identical shape to `FIGHTER_CARD_USE` with `spellId` replacing `cardId` (no lookup,
stored raw as int):
```
int32   uniqueId; int32 triggeringActionUniqueId
long    casterId
int32   spellId
byte    criticalMiss
// if criticalMiss == 0:
byte    criticalHit; int32 castPositionX; int32 castPositionY; short castPositionZ
```
Minimum 21 bytes if miss, else 32. `getActionId()` = spellId. `FightActionType` = `SPELL_CAST`.

### CloseCombatRequestMessage (Recv 8111)
**Status:** implemented · handled by `handleCloseCombatRequest`
(`internal/dispatch/handlers_fight_combat.go:70-83`), forwarded to `Fight.handleCloseCombat`
(`internal/combat/combat_actions.go:21-72`) — fixed AP cost + element + damage from breed data,
range must be exactly 1/adjacent.
**Client source:** `client/.../fight/CloseCombatRequestMessage.java:26-77`
**Payload (18 bytes):**
```
long    fighterId       // attacking fighter id
int32   usePositionX    // target world X (melee target cell)
int32   usePositionY
short   usePositionZ
```
Note: no `cardId`/`spellId` field — close combat is not template-id-based.

### CLOSE_COMBAT (Send 8112)
**Status:** implemented · built by `buildCloseCombat` (`internal/combat/packets.go:86-96`),
broadcast from `Fight.handleCloseCombat` (`internal/combat/combat_actions.go:55`).
**Client source:** `client/.../action/CloseCombatMessage.java:23-123`
**Payload:**
```
int32   uniqueId; int32 triggeringActionUniqueId
long    userId
byte    criticalMiss
// if criticalMiss == 0:
byte    criticalHit; int32 usePositionX; int32 usePositionY; short usePositionZ
```
Minimum 17 bytes if miss, else 28. `getActionId()` always 0 (no action template id).
`FightActionType` = `CLOSE_COMBAT`.

### RUNNING_EFFECT_ACTION (Send 8120)
**Status:** implemented · built by `buildRunningEffectAction` (`internal/combat/packets.go:108-124`),
broadcast from `Fight.applyDamage` (`internal/combat/combat_actions.go:93-110`) and the effect
executor (`internal/combat/effects.go:321`) — the full 34-byte `serializedRunningEffect` blob per
spec, matching the documented byte layout exactly.
**Client source:** `client/.../action/RunningEffectActionMessage.java:17-139`
**Payload:**
```
int32   uniqueId; int32 triggeringActionUniqueId
byte    mustBeExecutedNow         // 0/1
byte    triggered                 // 0/1 -- triggered by another effect vs. direct execution
int32   runningEffectId
byte[34] serializedRunningEffect  // see breakdown below
int32   effectContainerType       // see Part 3 containerType table
long    effectContainerId
```
Minimum total payload after the 8-byte header: 52 bytes.

**`serializedRunningEffect` (34 bytes)** — from `ArenaRunningEffect.serialize()`/`unserialize()`.
The on-wire blob does **not** include the leading `getId()` int32 (that's the separately-read
`runningEffectId` field above):
```
int32   genericEffectId    // resolved via AbstractEffectManager.getEffect(id)
long    casterId           // 0 if no caster
long    targetId           // 0 if no target
int32   targetCellX
int32   targetCellY
short   targetCellZ
int32   value              // effect magnitude/value
```
(4+8+8+4+4+2+4 = 34 bytes, confirmed.) `getActionId()` = `runningEffectId`.
`FightActionType` = `EFFECT_EXECUTION`.

### GiveUpFightRequestMessage (Recv 8151) — forfeit
**Status:** implemented · handled by `handleGiveUpFightRequest`
(`internal/dispatch/handlers_fight_combat.go:162-168`), forwarded to `Fight.handleGiveUp`
(`internal/combat/turns.go:100-106`) → `fleeCoach` (`internal/combat/fightend.go:76-99`) — also
triggered automatically on disconnect (`internal/dispatch/disconnect.go`).
**Client source:** `client/.../fight/GiveUpFightRequestMessage.java:23-39`
**Payload:** `(empty)` — purely a signal. Routes into the fight-termination flow, culminating in
`END_FIGHT` (8300) once the server marks the forfeiting side as the loser (see Part 3 below).

### FighterActorMovementRequestMessage (Recv 4503) — in-fight movement
**Status:** implemented (distinct from world-movement opcode 4501, `CoachActorMovementRequestMessage`,
already documented in [03-coach-world.md](./03-coach-world.md)) · handled by
`handleFighterActorMovementRequest` (`internal/dispatch/handlers_fight_combat.go:125-142`),
forwarded to `Fight.handleFighterMove` (`internal/combat/turns.go:112-167`) — re-derives the path
server-side via A* (`FindPath`) rather than trusting the client's literal step list, deducts MP,
and applies the tackle/evasion check (`internal/combat/tackle.go`) before allowing the move.
**Client source:** `client/.../clientToServer/FighterActorMovementRequestMessage.java:27-76`
(lives directly under `clientToServer`, not `clientToServer.fight`) — opcode **4503** resolved via
`getId()` (not in `OpCode.java`, which only has `ACTOR_MOVEMENT_REQUEST(4501)` for the world variant)
**Payload (8 + 10×N bytes):**
```
long    fighterId          // fighter being moved (in-fight actor)
// repeated N times, one per path step:
  int32   worldX
  int32   worldY
  short   altitude
```

### FighterActorDirectionChangeRequestMessage (Recv 4521)
**Status:** implemented · handled by `handleFighterActorDirectionChangeRequest`
(`internal/dispatch/handlers_fight_combat.go:147-158`), forwarded to
`Fight.handleFighterDirectionChange` (`internal/combat/turns.go:173-183`).
**Client source:** `client/.../fight/FighterActorDirectionChangeRequestMessage.java:24-62`
**Payload (9 bytes):** `long fighterId; byte direction8` (Direction8 index, 8-directional facing).

### FIGHTER_CHANGE_DIRECTION (Send 4522)
**Status:** implemented · built by `buildFighterChangeDirection` (`internal/combat/packets.go:145-151`),
broadcast from `Fight.handleFighterDirectionChange` (`internal/combat/turns.go:182`), self-flushing
(no 8200 barrier). `Direction8`'s Go enum values were found and fixed to match the client's real
wire ordinals (`docs/08-java-parity-roadmap.md` §8.11 item 15) — see `TestDirection8_WireValuesMatchClient`.
**Client source:** `client/.../action/FighterChangeDirectionMessage.java:23-77`
**Payload:**
```
int32   uniqueId; int32 triggeringActionUniqueId
long    fighterId
byte    direction8    // decoded via Direction8.getDirectionFromIndex(byte)
```
`getActionId()` always 0. `FightActionType` = `CHANGE_DIRECTION`. Note: unlike its Recv counterpart
(4521, plain request, no header), this Send version carries the 8-byte fight-action header.

### FIGHTER_MOVE (Send 4524)
**Status:** implemented · built by `buildFighterMove` (`internal/combat/packets.go:135-143`),
broadcast from `Fight.handleFighterMove` (`internal/combat/turns.go:165`) after A* path
re-derivation/MP deduction and per-step ground-effect-area checks.
**Client source:** `client/.../action/FighterMoveMessage.java:17-99`
**Payload (8 + 8 + 10×N bytes):**
```
int32   uniqueId; int32 triggeringActionUniqueId
long    fighterId
// repeated N times (N = (payloadLength-8)/10):
  int32   worldX; int32 worldY; short altitude
```
Minimum 16 bytes (header + fighterId, zero-step path allowed). `CELL_BYTE_SIZE`=10.
`FightActionType` = `MOVE`. `getActionId()` always 0.

### FIGHTER_DIES (Send 4520)
**Status:** implemented · built by `buildFighterDies` (`internal/combat/packets.go:153-158`),
broadcast from `Fight.killFighter` (`internal/combat/fightend.go:15-34`).
**Client source:** `client/.../action/FighterDiesMessage.java:15-65`
**Payload:** `int32 uniqueId; int32 triggeringActionUniqueId; long fighterId` (the fighter that died).
Strict 16-byte minimum. `FightActionType` = `DIE`.

### FIGHTER_TACKLED (Send 4506)
**Status:** implemented · built by `buildFighterTackled` (`internal/combat/packets.go:160-166`),
broadcast from `Fight.handleTackled` (`internal/combat/tackle.go:72-81`) — part of the
greenfield Tackle/Evasion mechanic (`docs/08-java-parity-roadmap.md` §8.11 item 13; no equivalent
exists in the decompiled server-side reference, only a cosmetic client-side animation trigger).
**Client source:** `client/.../action/FighterTackledMessage.java:23-84`
**Payload:** `int32 uniqueId; int32 triggeringActionUniqueId; long tackledFighterId; long tacklerId`.
Strict 24-byte minimum. `FightActionType` = `TACKLE`.

---

## Part 3 — Effects, Action Sequencing & End of Fight

### EFFECT_AREA_ACTION (Send 6200)
**Status:** implemented (Phase M, `docs/08-java-parity-roadmap.md`) · built by
`buildEffectAreaAction` (`internal/combat/packets.go:126-133`), broadcast for both `apply`(enter)
and `unapply`(exit) triggers from `internal/combat/effectarea.go`'s `applyEffectArea`/
`unapplyEffectArea` (lines 189, 208) — real callers wired via `Fight.checkInAndOut`
(`internal/combat/turns.go:160`, called once per movement step) and actionID 66
(`SET_EFFECT_AREA`) in `runningEffectTable`. The packet builder existed before Phase M with zero
callers; it now has real ones.
**Client source:** `client/.../fight/EffectAreaActionMessage.java:29-36`
(extends `FightActionMessage`)
**Payload (25 bytes):**
```
int32   uniqueId                 // fight-action header
int32   triggeringActionUniqueId // fight-action header
byte    apply                    // 1 = area applied to target, 0 = unapplied
int64   areaId                   // BasicEffectArea.getId(), via EffectAreaManager lookup
int64   targetId                 // fighter/entity being affected
```
`getActionId()` always 0. `FightActionType` = `EFFEC_AREA_ACTION` (id 9).

Client handling: looks up the `BasicEffectArea` by `areaId`; if not found, logs error and drops
(no animation). Otherwise builds an `EffectAreaAction` and **adds it to the pending action group
but does NOT execute it immediately** — execution deferred until `FIGHT_ACTION_SEQUENCE_EXECUTE`
(8200) arrives.

### FIGHT_ACTION_SEQUENCE_EXECUTE (Send 8200) — a flush/commit barrier, NOT a batch container
**Status:** implemented · `Fight.flushActionSequence` (`internal/combat/turns.go:185-190`) sends
the empty commit frame after each queued action (movement, spell/card/close-combat casts); the
self-flushing opcodes (`NEW_TABLE_TURN_BEGIN`/`FIGHTER_TURN_BEGIN`/`FIGHTER_TURN_END`/standalone
`CHANGE_DIRECTION`) correctly skip it, matching the design exactly as spec'd below (Phase F,
`docs/08-java-parity-roadmap.md`). Note: the caster-faces-target `ChangeDirectionAction`
auto-synthesis described below (step 3) is a client-side-only behavior triggered by receiving this
barrier — no corresponding server-side synthesis was needed/added.
**Client source:** `client/.../action/FightActionSequenceExecute.java` (43 lines) — `decode()` is a
**no-op stub that just returns `true`**; extends `InputOnlyProxyMessage` (server→client only)

**Payload:** `(empty — zero-byte body)`.

**Critical design insight:** this message carries **no data whatsoever**. It is not a wrapper
holding a list of sub-actions — no such container class exists anywhere in the client codebase.
The "batching" is a purely client-side runtime concept built from a sequence of **separate,
individually-opcoded** messages.

**How the real batching/sequencing works** (`NetFightActionFrame.java`):
1. The server sends a series of individual fight-action opcodes, each merely **queued**
   client-side without running yet:
   - `EFFECT_AREA_ACTION`(6200), `FIGHTER_TACKLED`(4506), `FIGHTER_DIES`(4520),
     `FIGHTER_MOVE`(4524), `FIGHTER_CARD_USE`(8108), `CLOSE_COMBAT`(8112), `SPELL_CAST`(8110) →
     all queued, no execute.
   - `RUNNING_EFFECT_ACTION`(8120) → queued, **unless** `mustBeExecutedNow` is set, in which case
     it runs immediately out-of-band.
   - By contrast, `NEW_TABLE_TURN_BEGIN`(8100), `FIGHTER_TURN_BEGIN`(8104), `FIGHTER_TURN_END`
     (8106), and standalone `CHANGE_DIRECTION`(4522) each call
     `QueueActionGroupManager.executePendingGroup()` **themselves immediately** — self-flushing,
     NOT part of the 8200-gated batch.
2. All queued (non-self-flushing) actions accumulate into one shared `ActionGroup`
   (`QueueActionGroupManager.m_pendingActionGroup`).
3. When `FIGHT_ACTION_SEQUENCE_EXECUTE`(8200) arrives:
   - Fetches the pending group; no-op if none exists.
   - Scans for any cast action (spell/card/close-combat) and synthesizes a `ChangeDirectionAction`
     so the caster visually faces its target before the cast animation plays (auto-assigned a
     fresh `uniqueId` one higher than the max seen in the group).
   - Calls `executePendingGroup()`, which runs actions **one at a time**, each waiting for the
     previous action's finish-event before advancing, honoring each action's
     `getTriggerActionUniqueId()` to interleave/nest triggered reactive sub-actions (e.g. a
     counter-attack chained off the triggering hit).

**Design implication for the Go server** — the pattern to reproduce:
- Emit each individual action of a combat "tick" as its own already-existing opcode, each carrying
  the shared 8-byte fight-action header (`uniqueId`, `triggeringActionUniqueId`).
- `uniqueId` must be unique and increasing within the tick; `triggeringActionUniqueId` references
  another action's `uniqueId` in the same tick when one action is caused by another, or `-1` if
  none.
  - **CRITICAL — a cast's OWN primary effects MUST use `triggeringActionUniqueId = -1`, NOT the
    `SPELL_CAST`/`CLOSE_COMBAT`/`FIGHTER_CARD_USE` action's `uniqueId`.** The client's cast Lua
    script pulls each effect out of the pending action group via `executeFirstAction(3, <effectActionId>)`;
    `ActionGroup.runAction` then, if the pulled effect's `triggerActionUniqueId != -1`, looks that
    parent up and **re-runs it instead**. Pointing a primary effect back at the still-running
    `SpellAction` makes the client re-run the whole cast script — the caster's cast animation loops
    forever and all input is blocked (the "La folle animation loops" bug). Only genuinely *reactive*
    sub-actions (strike-back, spell-rebound returns, DoT ticks fired by another effect) carry a real
    parent `uniqueId`. Go server: `noTriggeringAction` (`combat_actions.go`) is passed to
    `executeEffectsForHit`/`applyDamageFrom` for all primary cast/close-combat/card effects.
- After sending all actions for that tick, send one empty `FIGHT_ACTION_SEQUENCE_EXECUTE`(8200)
  with a zero-byte body to tell the client "play the sequence now."
- `NEW_TABLE_TURN_BEGIN`/`FIGHTER_TURN_BEGIN`/`FIGHTER_TURN_END`/standalone `CHANGE_DIRECTION`
  self-flush and don't need an 8200 follow-up.

### END_FIGHT (Send 8300)
**Status:** implemented (full branching payload: flee vs. normal, winner/loser lists) · built by
`buildEndFight` (`internal/combat/packets.go:217-247`), broadcast from `Fight.endFight`
(`internal/combat/fightend.go:107-131`) — reached via `checkFightEnd`/`fleeCoach`. **Known
simplification, not a regression**: ladder-strength deltas and `PlayerStatisticsReport` blobs are
always sent as zero/empty, and lost/won card blobs are always empty (no card-wagering logic wired
into fight-end yet) — matches this project's existing fake/placeholder stats-reporting posture
elsewhere (`docs/08-java-parity-roadmap.md` §8.1), tracked as a follow-up under §8.11 item 8 /
§8.12 Phase O.
**Client source:** `client/.../fight/EndFightMessage.java:81-150`
(extends `FightActionMessage`)

**Payload (branches on `flee`):**
```
int32   uniqueId                  // fight-action header
int32   triggeringActionUniqueId  // fight-action header
byte    flee                      // 1 = fight ended via forfeit/flee

if flee == 1:
    int16  lostCardsBlobSize
    byte[] lostCardsBlob           // present only if lostCardsBlobSize > 0; see card-blob format below

if flee == 0:
    byte   winnerCount
    repeat winnerCount times:
        int64  playerId
        int16  strength            // ladder-strength delta
        int16  reportSize          // PlayerStatisticsReport blob size
        byte[] reportBytes         // present only if reportSize > 0

    byte   looserCount
    repeat looserCount times:      // identical shape to winner entries
        int64  playerId
        int16  strength
        int16  reportSize
        byte[] reportBytes  (if reportSize > 0)

    int16  lostCardsBlobSize
    byte[] lostCardsBlob            // if >0

    int16  wonCardsBlobSize
    byte[] wonCardsBlob              // if >0
```
Minimum 9 bytes (8-byte header + 1 flee byte) enforced.

**Card blob format** (`unserializeCards()`, used identically for lost/won/flee-lost blobs):
```
byte    entryCount
repeat entryCount times:
    int64  playerId       // -1L = "bonus cards" pool (goes to m_bonusCards regardless of bLostCards)
    byte   cardCount
    repeat cardCount times:
        int32  templateId      // BetCoachCard reference card id
        byte   cursed          // >0 = cursed flag
```
`getActionId()` always 0. `FightActionType` = `FIGHT_END`. Client dispatch builds a `FightEndAction`
which populates the results screen (winner/loser coaches, ladder strength, statistics report,
lost/won/bonus cards, fight duration) and calls `Fight.endFight()`.

### Recv EndFightDoneMessage (opcode 4321, not in legacy OpCode.java)
**Status:** implemented · handled by `handleEndFightDoneRequest`
(`internal/dispatch/handlers_fight_combat.go:174-207`), forwarded to `Fight.handleEndFightDone`
(`internal/combat/fightend.go:137-142`) — the `Fight` actor's `Run()` loop only returns once every
participating coach has acked (`allEndFightDoneAcked`, `fightend.go:146-153`), which is when
`Manager` truly forgets the fight. **RESOLVED (Phase O, §8.11 item 9)**: a disconnect *after*
`END_FIGHT` has already been sent but *before* that coach's own 4321 arrives now synthesizes the
missing ack automatically — `dispatch/disconnect.go`'s `HandleDisconnect` unconditionally sends
`combat.NewEndFightDone(coach.ID)` alongside the existing forfeit path (safe regardless of the
fight's actual phase, since both commands are no-ops outside their relevant window). A related
bug was found and fixed in the same pass: `handleEndFightDoneRequest` previously removed the
`Duel` from `DuelManager` after only the FIRST coach's ack (not once the fight was fully torn
down), which broke the disconnect-synthesis fix's own `Duels.GetByCoach` lookup for the *other*
coach — fixed by deferring duel removal to a goroutine that waits on `Fight.Done()`.
**Client source:** `client/.../fight/EndFightDoneMessage.java:22-37`
**Payload:** `(empty)`. Extends `OutputOnlyProxyMessage` (client→server only).

**Trigger:** sent from `FightEndAction.onActionFinished()` — fires only after the player
dismisses/closes the results screen populated by `EndFightMessage`. A genuine user-driven UI-close
acknowledgment, not an automatic protocol ack. **Server-side meaning: safe to release the fight
instance / return the coach to the lobby / world once received.**

### Effect System Design Reference

Source: `data/stc/com/ankamagames/baseImpl/common/clientAndServer/game/{effect,effectArea}/**`.

```
Effect (data-only, immutable)              — one per (effectId, actionId) definition: params, AreaOfEffect,
  │                                            TargetValidator, duration/applyDelay, trigger BitSets, flags
  │  .execute(container, launcher, context, constants, targetCell)
  │      → looks up StaticRunningEffect impl via constants.getObjectFromId(actionId)
  ▼
RunningEffect (abstract, stateful, pooled) — the runtime instance: caster, target, context, container, value
  .run(effect, container, context, launcher, targetCell, forceNow)
      → setParameters(...)
      → if hasApplyDelay() && !forceNow: pushRunningEffectDelayedTimeEventInTimeline(...)
      → if useTargetCell() && !useTarget(): apply a cell-only instance directly
      → if useTarget(): determineTargets() → applyOnTargets(EffectUser...)
  .applyOnTargets(targets)
      → for each target: build a newParameterizedInstance, computeValue(), fire BEFORE trigger,
        fire AFTER trigger, execute(), and — if hasDuration() — either stackEffect() or storeEffect()
        into target.getRunningEffectManager()
  .execute() / .checkEffectValidityAfterExecution()
      → decrements m_maxExecutionCount; if no duration & not re-triggerable → askForUnapplication()
  .askForUnapplication() → unapply() → release() back to pool (or delegates to stacking parent)
```

**EffectContainer** ("owner" abstraction — the `containerType` int is exactly what travels on the
wire inside `RUNNING_EFFECT_ACTION`(8120)'s `effectContainerType` field):

| containerType | Implementer | Purpose |
|---|---|---|
| 1 | `State` | status-effect bundle (buff/debuff), keyed by `(baseId<<8)+level` |
| 3 | `BasicEffectArea` | persistent ground-zone/AoE (traps, glyphs); also `EffectUser`+`Poolable` |
| 12 | FighterCard (client-side lookup only) | equipment card effects |
| 13 | Spell (client-side lookup only) | spell effects |
| 14 | AbstractEvent (client-side lookup only) | scripted map/event effects |

**EffectContext** (abstract, `Poolable`) bundles per-fight service lookups a `RunningEffect` needs
during execution: `CellInformationProvider`, `LineOfSightObstacleInformationProvider`,
`MovementObstacleInformationProvider`, `EffectUserInformationProvider`, `EffectExecutionListener`,
`EffectAreaManager`, `TargetInformationProvider`, plus the fight's `BasicTimeline`.
`EffectContextForUniqueEffectUser` is the trivial single-target implementation.

**EffectUser** (interface, extends `Target`): `getId()`, `getRunningEffectManager()`, position/
direction, characteristics, `isDead()/shouldBeDead()/die()`, `onEffectUsed()`. Implemented by
fighters and by `BasicEffectArea` itself.

**AbstractEffectManager**: `TIntObjectHashMap<Effect>` registry keyed by static `effectId`.
`GroupEffectManager` (singleton) maps `groupEffectId → List<Effect>` for effect *bundles*.

**State/StateManager**: the classic status-effect system (stun, poison, buffs). `StateManager` is a
singleton registry; `State.getUniqueIdFromBasicInformation(baseId, level)` computes the composite
key, allowing the same status at different levels/stacks to be tracked independently.

**Key mechanics for combat-engine design:**
- **Triggers**: each `Effect` carries `triggersBefore`/`triggersAfter` (activate reactively around
  a game trigger id) and `endTriggers` BitSets. `mustBeTriggered()` = true if either activating
  BitSet is non-empty — such effects don't execute immediately, they get pushed to a duration
  timeline and fire later via `executeOnTrigger`.
- **Stacking**: `mustBeStacked()`/`canBeStackedWith()`/`stackWith()`/`unStackWith()` support
  multiple instances of the same duration-effect coexisting on one target (e.g. stacked poison),
  with a parent/child relationship; unapplying the last stacked child cascades to ask the parent
  to also unapply if it has zero children left.
- **Serialization**: `serialize()`/`unserialize()` are abstract per concrete effect type — this is
  what travels inside `RUNNING_EFFECT_ACTION`(8120)'s `serializedRunningEffect` blob.
- **Value computation**: `computeValue(triggerRE)` is abstract per-effect-type (e.g. `HPLoss` for
  damage formulas); `disableValueComputation()` lets the server pre-compute the value once and
  ship an "already decided" result to the client — used for `RUNNING_EFFECT_ACTION` messages.

**BasicEffectArea (AoE/persistent zone) mechanics:**
- Constructed with a static `baseId`, `AreaOfEffect` shape, `applicationTriggers`/
  `unapplicationTriggers` BitSets, `maxExecutionCount`, `targetsToShow`, optional
  `deactivationDelay`, `activateCondition`, separate `TargetValidator`s for application/unapplication.
- `apply(Target)`: gate via `canBeApply()`; if `hasActivationDelay()`, schedules a delayed
  activation event; decrements `maxExecutionCount` unless unlimited; notifies
  `EffectAreaActionListener.onEffectAreaApplication`; runs `execute(Target)`; notifies
  `onEffectAreaExecuted`.
- `unapply(Target)`: tells the target's `RunningEffectManager` to remove anything linked to this
  area as caster; notifies `onEffectAreaUnapplication`.
- `triggers(BitSet, Target)`: if the incoming trigger set intersects `applicationTriggers`, calls
  `apply()`; if it intersects `unapplicationTriggers`, calls `unapply()`.
- **`EffectAreaManager.checkInAndOut(start, arrival, applicant)`** — the concrete "step into a
  glyph/trap" algorithm: computes the set of areas the mover was inside at `start` vs. `arrival`;
  for any area newly entered calls `area.triggers(1, applicant)` (enter trigger id `1`), for any
  area left calls `area.triggers(2, applicant)` (exit trigger id `2`). **This is exactly the
  algorithm the Go engine should port for persistent ground-effect areas**, called once per
  movement step during path resolution.
- `EffectAreaActionListener` callbacks (`onEffectAreaApplication`/`onEffectAreaExecuted`/
  `onEffectAreaUnapplication`) are exactly the hook points for when to broadcast
  `EFFECT_AREA_ACTION`(6200) with `apply=1`/`apply=0`.

---

## Corrections vs. the prior `../05-combat-engine.md`

1. **Add the validate-gate / two-phase (activate→deactivate) `TimeEvent` lifecycle** as the actual
   turn/phase-advance mechanism — nothing auto-advances; every transition needs an explicit
   `validate()` (triggered by player action or clock) before the timeline drains further. The
   prior doc's `Timeline.NextTurn()` sketch implies a simple advancing queue, which is not how the
   reference engine works.
2. **Remove the invented `DmgNeutral`/`ResNeutral` characteristic** — it doesn't exist in
   `FighterCharacteristicType`; physical/base damage uses plain `DMG`/`RES`.
3. **Refine the summon-insertion algorithm**: new summons queue up after the father *and* any of
   the father's already-present summons, not simply "right after father."
4. **Document mid-fight roster-change cascades**: adding/removing a fighter during an active
   table-turn shifts already-queued `FighterTurnTimeEvent` timestamps by ±1 for everyone after the
   insertion/removal point — not just an in-memory order-list update.
5. **Add the concrete clock parameters**: presentation 20000ms/priority 3, placement 30000ms/
   priority 2, observation 10000ms/priority 1, per-turn 30000ms (subId scheme 1/2/3/4) — and the
   phase-guard checks (`startObservation()`/`startAction()` assert the previous phase, error+no-op
   otherwise) that a simple linear-transition framing doesn't capture.
6. **`checkFightEnd()` is not auto-invoked by `killFighter()`** — callers must explicitly call it
   after killing a fighter. The prior doc implied this was automatic.
7. **Add `FightManager`'s template/prototype pattern** if the Go engine needs multiple fight types,
   and note fight IDs can go negative after `Integer.MAX_VALUE` wraparound.
8. **Flag the `65036` characteristic lower-bounds** (ids 9-12, 29) as a likely decompiler artifact
   rather than silently "correcting" them to -100 without a note.
9. **`FIGHT_ACTION_SEQUENCE_EXECUTE`(8200) is a flush/commit barrier, not a batch payload** — the
   prior doc's brief mention didn't describe its actual (empty) wire format or the
   queue-then-flush client behavior; this is now fully documented in Part 3 above.
10. **Full opcode-level documentation of all combat action opcodes** (8100-8300, 4500s) was
    entirely absent from the prior design doc, which only discussed the engine conceptually — now
    added in Part 2/3 above with exact byte layouts and newly-resolved opcode numbers (8105, 8107,
    8111, 8151, 4503, 4521, 4321 — none previously in `OpCode.java`).

## Remaining genuine unknowns

- **`FighterCharacteristic`'s exact clamping semantics** (does lowering max clamp current value
  down too?) — the concrete implementation class wasn't read in this pass.
- **`Breed`'s exact fields** (`getBaseHp/Mp/Ap/Init/CH/CM`, `getCloseCombatAp`, `getValue`) — the
  `Breed` enum/class itself wasn't read; this is an open blocker for populating breed base stats.
- **`MessageScheduler`/`ClockMessage` internals** (exact `addClock(handler, ms, type,
  repeatCount)` semantics, re-arm race behavior) — not read in this pass.
- **What actually calls `askForFighterStartTurn`** — automatic on turn arrival, or waits for an
  explicit client "ready" signal? Not resolved from the base classes alone (no `m_readyCount`-style
  gating visible for individual turn starts, only for phase transitions).
- **`FighterPropertymanager`/`FighterPropertyType`** (bit-flag properties like `INVISIBLE`) — not
  read; needed to enumerate the full property bitmask (Invisible/Stabilized/Petrified/Rooted).
- **`RunningEffectConstants`/trigger-ID table** — still unresolved, out of scope for this pass.
- **`PlayerStatisticsReport` binary format** referenced by `END_FIGHT` — not fully decoded (see
  [03-coach-world.md](./03-coach-world.md) for a partial format from a different opcode).
