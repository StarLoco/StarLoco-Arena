# DofusArena Go Server — Feature Status & Remaining Work

> Consolidated snapshot of what is **done**, what is **left to do**, what is **partial /
> to improve**, and what is **to check/verify**, per category.
>
> This is a code-verified summary (formulas/AI sections were cross-checked against the
> actual Go source, not just the design docs). For the exhaustive history and per-opcode
> byte layouts see:
> - `server/docs/08-java-parity-roadmap.md` — the single living roadmap (§8.1 status
>   table, §8.11 follow-ups, §8.12 next-phases, §8.13–8.22 real-user bug fixes)
> - `server/docs/opcodes/00-implementation-status.md` — per-opcode implementation status
> - `server/docs/05-combat-engine.md` — combat engine design reference
>
> Legend: ✅ Done · 🟡 Partial / to improve · ⛔ Not implemented · 🔬 To check / verify ·
> 🚫 Explicit non-goal (out of scope for v1)

---

## TL;DR — Are we finished?

**Core PvP is feature-complete and shipping.** A full 1v1 / 2v2 duel runs end-to-end:
login → matchmaking → duel → `CREATE_FIGHT` → presentation/placement/observation/action →
turn cycle → close combat / spells / cards → damage/effects → fight end → ladder + stats
persistence. All under `go test -race` green.

**What's genuinely left** is a short list of combat-fidelity gaps (reactive trigger-bus,
state bundles, a couple of formulas) plus intentionally out-of-scope systems (AI opponents,
shop/economy, bans). None block normal duels. Details below.

The specific mechanics you asked about:

| Mechanic | Status |
|---|---|
| **Resistances** (flat + %, per element) | ✅ Done |
| **Critical strike (CC)** | ✅ Done — close combat AND spells/cards now select their `isCritical` effect subset (HV2) |
| **Fumble (échec critique)** | ✅ Done |
| **Esquive PA/PM** (AP/MP-loss dodge roll) | ✅ Verified-correct — this game has **no roll**; the faithful port is the flat `ResAPLoss`/`ResMPLoss` % resist (HV3) |
| **Tacle / Tackle** | ✅ Done — now scales with the per-side `EvasionBonus` (HV4) |
| **Hit location** (front/side/back) | ✅ Done |
| **Rebound (renvoi)** | ✅ Done |
| **AI / PvE opponents** | 🚫 Non-goal — only a minimal summon-move AI exists |

> **Update (HV pass):** all five high-value items from §9 are now implemented and green
> under `go test -race` across the whole suite (incl. e2e). See §10 for the change log.

---

## 1. Combat Formulas & Mechanics

Code lives in `server/internal/combat/`.

| Feature | Status | Where | Notes / To improve |
|---|---|---|---|
| **Resistances — flat** (`Res` + per-element `ResFire/Water/Wind/Earth`) | ✅ | `damage.go:70-78` `ComputeHPLoss` | Element→char map `breed.go:68-100` |
| **Resistances — percent** (`ResInPercent` + per-element %) | ✅ | `damage.go:75-80` | Applied last, matching reference |
| **Physical damage bypasses Dmg/Res** (correct per decompiled `HPLoss`) | ✅ | `damage.go:70` | Regression test `damage_test.go:14` |
| **Critical strike — roll** (`CriticalRate`) | ✅ | `damage.go:157-163` `RollCriticalHit`; wired `combat_actions.go:52,331,366` | Breed base = 5 for all breeds |
| **Critical strike — damage bonus** | ✅ | close combat: `combat_actions.go:59-63`; spells/cards: `executeEffectsForHit`/`selectEffectsForCrit` (`effects.go`) | **HV2:** a crit now runs the spell/card's `isCritical` effect subset (parsed from `EffectDef.IsCritical`), mirroring the reference's dual normal/critical effect lists. Falls back to normal effects if the container has no critical effects. |
| **Fumble / échec critique** (`FumbleRate`) | ✅ | `damage.go:168-174` `RollFumble`; wired `combat_actions.go:51,330,365` | Fumble suppresses both damage and crit |
| **Esquive PA/PM** (AP/MP-loss dodge) | ✅ | `effects.go:471-486` `applyResistance` (`ResAPLoss`/`ResMPLoss`) | **HV3 — verified-correct:** this game has **no** probabilistic AP/MP dodge in the reference; the deterministic `Res*Loss` percent-resist IS the faithful port. Pinned by `TestEsquiveAPMP_IsFlatResistNotRoll` (same resist → same loss, no RNG). Not a gap. |
| **Tackle + Evasion** (zone of control on move) | ✅ | `tackle.go` `evasionChanceAgainst`/`attemptEvadeTackle`, gated in `turns.go` | **HV4:** base 67% evade now shifted by the per-side `Fighter.EvasionBonus` (evader's raises, tackler's grip lowers), clamped [0,100]. Kept as a non-wire fighter field since the reference `FighterCharacteristicType` enum (ids 1-34) has no evasion stat. Default 0 = unchanged flat-67% behavior. |
| **Hit location** (front +0 / side +15% / back +30%) | ✅ | `damage.go:117-140` `hitLocationBonus` | Dot-product of facing; test `damage_test.go:86` |
| **Rebound / renvoi de dommages** | ✅ | in-formula `damage.go:87-100` (`DmgRebound`, mirrors `HPLoss.computeValue`); reactive `combat_actions.go:219-233` (`StrikeBackPercent` only) | **Verified no double-count:** `DmgRebound` is applied exactly once, inside `ComputeHPLoss`, matching the reference. The reactive step in `applyDamageFromEffect` deliberately does NOT re-read `DmgRebound` (see its comment at `combat_actions.go:219-233`) — only the independent `StrikeBackPercent` (STRIKE_BACK, id 90) is handled there. Pinned by `TestReboundAppliedExactlyOnceEndToEnd` and `TestReboundNotAppliedTwiceForCloseCombatStyleDamage` (`damage_property_test.go:263-330`), both green. |
| **`randomRound` / ValueRounder** | ✅ | `damage.go` (probabilistic rounding) | Confirmed matches decompiled `ValueRounder.randomRound` |
| **Characteristic semantics** (Gain/Loss=current, Buff/Debuff=max, Leech) | ✅ | `effects.go`, `characteristic_semantics_test.go` | Real bug found & fixed (see roadmap §8.11 item 14) |

**To do / improve (combat formulas):**
- ✅ ~~Apply crit-damage to spell/card effects~~ — done (HV2).
- ✅ ~~Add Evasion scaling to the tackle roll~~ — done (HV4).
- ✅ ~~Decide on esquive PA/PM~~ — decided: keep the reference flat-resist model (HV3), verified-correct.
- ✅ ~~Verify rebound is not double-applied across its two paths~~ — verified: `DmgRebound` only fires inside `ComputeHPLoss`; the reactive step only handles the separate `StrikeBackPercent`. See `TestReboundAppliedExactlyOnceEndToEnd` / `TestReboundNotAppliedTwiceForCloseCombatStyleDamage`.

---

## 2. Combat Engine — Lifecycle & Turn Loop

| Feature | Status | Notes |
|---|---|---|
| Fight lifecycle: `CREATE_FIGHT` → presentation → placement → observation → action | ✅ | `fight.go`, `phases.go` (opcodes 8018–8040) |
| Presentation-ready skip (both "Prêt" → skip clock) | ✅ | Single ready gate, corrected in roadmap §8.22 |
| Timeline / turn order (validate-gated, no auto-advance) | ✅ | `timeline.go` |
| Turn cycle: begin/end, 30s turn clock, manual end-turn | ✅ | `turns.go` |
| Forced-progress ready timers (match-ready, placement-ready) | ✅ | `world/duel.go`, configurable clocks |
| Movement (A*, server re-derives path, MP cost, wire path incl. start cell) | ✅ | `pathfind.go`, `turns.go` |
| Close combat / spell cast / card use | ✅ | `combat_actions.go` |
| Action-sequence batching (`FIGHT_ACTION_SEQUENCE_EXECUTE` 8200 flush model) | ✅ | `turns.go:222` |
| Fight end (normal + forfeit), `END_FIGHT`, `EndFightDone` ack-gating | ✅ | `fightend.go` |
| Fighter ownership authorization (can't act on opponent's fighters) | ✅ | `resolveOwnedFighter`, roadmap §8.18 |
| Real map data (`.amw`/`elements.ade`): walkability, altitude, start cells | ✅ | `internal/gamedata/parser/*`, `gamedata/map.go` |
| Real per-fighter placement cells (`FightStartPointElement`) | ✅ | roadmap §8.14 |
| Fighter render altitude (standing = base+height), facing (diagonals only) | ✅ | roadmap §8.17 |
| AP/MP debit broadcast to client (`RUNNING_EFFECT_ACTION` 8120) | ✅ | roadmap §8.20 |
| `ACTOR_APPEAR` (fighters offset IDs + coaches real IDs, no collision) | ✅ | roadmap §8.16, §8.18 |
| Coach equipment/loadout + fighter spells/equipment in `CREATE_FIGHT` | ✅ | roadmap §8.15, §8.20 |

**To do / verify:**
- ✅ ~~Re-validate A* against a height-blocked / corner-cut real-map path~~ — done. See §1's "Rebound"-adjacent Phase K note and §11 V3: map 2 (the default fight map) turned out to have zero genuine height-blocked cells, so a new test pins a real one from map 4 instead; corner-cutting is structurally impossible given this engine's single-axis-only movement, independent of map data.
- ✅ ~~Free-placement **zone restriction**~~ — **done** (§15). Placement is now restricted to the fighter's own team's `FightStartPointElement` cells (`Map.FightStartCells()[TeamID-1]`), matching the reference client; the "per-map placement-zone data source" was simply the kind-1000 start cells the map parser already extracts.

---

## 3. Effect System

| Feature | Status | Where | Notes |
|---|---|---|---|
| Instantaneous effect executor (damage/heal/leech, AP/MP use, charac mods, push/pull, teleport, property toggles) | ✅ | `effects.go`, `effects_registry.go` | The big `runningEffectTable` dispatch |
| Duration tracking (poison DoT re-tick, timed buff/debuff auto-revert) | ✅ | `duration.go`, `tickActiveEffects` | Table-turn granularity (roadmap Phase J) |
| Persistent ground-effect areas / **traps / glyphs** (`checkInAndOut`) | ✅ | `effectarea.go` | Per-step trap trigger; `staticEffects.dat` parsed (Phase M) |
| Special battlefield cells (Trap, Enthusiasm, Shield, Eagle eye, Panacea, Motivation, Healing heart, Killer) | ✅ | `specialcells.go` | Mechanic ✅ & tested; **per-map layouts DERIVED from each map's own baked negative-gfx Bonus tiles** (gfx −1002..−1009 → cellBaseId 1002-1009, mapping cross-checked field-for-field against `staticEffects.dat`). Optional `data/maps/<id>/specialcells.json` overrides the derivation. |
| **Summons** | ✅ | `effects.go` `applySummon`/`applySummonTemplateStats`, `criteria.go` gate | **HV5:** a plain `EffectSummon` now resolves its `SummoningTemplate` via `params[0]` (the template id `Summon.execute()` passes to `summonCreature`) and sets the summon's real HP/AP/MP. `SummonDouble`/`SummonMirror` correctly keep the caster's breed stats (they're clones). Falls back to breed stats if the template is missing. **Wire fix:** the summon is spawned client-side from the `SUMMON` `RUNNING_EFFECT_ACTION` alone (id in the target field, `SummoningDefinition` id in the `value` field = `params[0]`); the server no longer also sends a redundant `ACTOR_APPEAR`, which double-added the mobile and **froze the client** (Sadida "La folle" repro). `TestEffectSummon_WireCorrectness`. |
| **Summon behaviour rules** (pbworks beta-13 wiki) | ✅ | `fightend.go`, `summon_ai.go`, `events.go`, `combat_actions.go` | Four wiki rules added on request (⚠️ **beta-13 wiki, NOT retail-client parity** — the decompiled retail client does the opposite for #1/#4 and the original `org.ankarton` turn-AI backing #2/#3 is absent, so these are deliberate wiki-flavour choices): **(1)** summons disappear when their creator dies — `killFighter`'s `killChildSummons` recursively kills every living `Father==dead` summon before the fight-end check (retail keeps them: `AbstractFight.onFighterDeath` only drops running effects linked to the dead caster). **(2)** summon AI ignores INVISIBLE enemies in target-select/flee (`summonCannotSee` in `nearestOpponent`/`minEnemyDistance`). **(3)** summon AI never ends a move on a lethal special cell (`cellIsLethalToSummon`: KILLER always, TRAP when HP≤10 — wired into all three movement scorers). **(4)** cards don't work on summons — per-round event cards skip `Father!=nil` fighters (`applyEventEffects`) and fighter-card use (8107) rejects a summon target cell (`handleCardUse`). Tests: `summon_rules_test.go`, `summon_ai_test.go`, `events_test.go`. |
| **Reactive trigger-bus** (`TriggersBefore`/`TriggersAfter`) | ✅ | `triggerbus.go` | **HV1:** effects declaring a before/after listen-set are now DEFERRED (stored on the carrier via `deferReactiveEffect`) instead of executing instantly, and fire on the matching event — `trigOnAttacked` (on HP loss), `trigOnAPUse`, and the `trigTableTurn` tick — for the effect's duration. Covers exactly the trigger ids the real `spells.dat`/`events.dat` data uses (empirically: 2, 52, 54, 55, 56, 64, 1001); an unlisted id degrades gracefully (stored but inert). **Sacrieur's Sacrifice** (spell 135, a before-HP-loss ExchangePosition) is fully wired via `applySacrificeRedirect` in `applyDamageFromEffect`: an incoming hit on a sacrificed ally swaps it with the Sacrieur and redirects the whole hit onto them (single hop, persists for the buff's duration). |
| **Caster-death effect removal** (`AbstractFight.onFighterDeath`) | ✅ | `fightend.go`, `duration.go` | When a fighter dies, `removeEffectsCastBy` reverts+removes every duration-tracked `ActiveEffect` and armed `ReactiveEffect` it placed on OTHER living fighters — an ally's Stimulating Word / Devotion / Word of Torture HP boost, or a Sacrieur's Sacrifice buff, all end (wiki: "if the eniripsa dies the buff is lost"). Stat reverts reuse `revertActiveEffect` (shared with expiry); current value clamps to the reduced max (minor deviation from the wiki's "keep current HP above max" note for Word of Torture). |
| **`EndTriggers`** | ⛔ (n/a) | — | Parsed as `null` in this project's real data (`EffectContentDocumentLoader`), so there is nothing to wire. |
| **State bundles** (`ApplyState`/`State`, actionID 112) | ✅ (inert by default) | `state.go` `applyStateBundle`/`stateManager`/`RegisterState` | **Mechanic fully implemented** — a `State` is a `(baseId,level)`-keyed bundle (`uniqueId=(baseId<<8)+level`, byte-exact to the reference); applying it executes every sub-effect through the shared executor (triggered sub-effects defer, instant ones run), ported from `ApplyState.computeValue`/`execute`. The per-fight registry is **empty by default** because state data is genuinely absent from every artifact AND **STATE_APPLY is used 0× in this project's real spell/card/event data** (empirically confirmed). So it's the same "built, awaiting optional data" posture as special cells — `RegisterState` expands a state correctly the moment any is sourced. |
| **Equipment passive stat bonuses** (`FIGHTER_CARD_EQUIP` effects → fighter characteristics, incl. Initiative) | ✅ | `combat.ApplyEquipmentBonuses` (`equipment.go`), wired in `buildCombatTeam`; `gamedata.splitFighterCardEffects` (`store.go`) | Each equipped fighter card's `FIGHTER_CARD_EQUIP` subset (`CharacBuff`/`CharacGain`/`CharacDebuff`, e.g. +60 Init, +25 HP, +1 AP, −40 Init tradeoffs) is applied to the fighter's characteristics **at fight-build time, before `NewTimeline`** — so equipment feeds turn order, matching the client's `AbstractFighter.applyCardEffects()` (applied on `ITEM_ADDED` pre-combat). The separate `FIGHTER_CARD_USE` subset is the actively-castable card ability fired on `FIGHTER_CARD_USE`(8107) and is **not** re-applied as a passive. `CardEquipped` (93) is a genuine no-op marker, NOT the stat source. Real `cards.dat`: 127 cards, 168 use-time + 122 equip-time effects (74 Initiative). Tests: `equipment_test.go`, `fightercard_split_test.go`, real-data assertion in `store_realdata_test.go`. |

**⚠️ Important — don't confuse "status effects" with "the State bundle wrapper":**

There are TWO different things people call "states":

1. **Status *effects*** that spells actually apply — **Rooted** (65), **Petrified** (96),
   **Stabilized** (94), **Invisible** (57), **Poison** (61), **Death** (63), and all the
   `Charac*` buffs/debuffs (76/77/80–89, …). These ARE in the real data and **ARE fully
   implemented and used** — they're plain `RunningEffect`s applied directly by spells
   through the effect executor. When a spell petrifies/roots/poisons a target, it works.
   (Verified from the actual effect-actionID histogram: spells use 52 distinct actionIDs
   from 2→97, incl. 57/61/63/64/65/94/96.)

2. **The `State` *bundle wrapper*** (`ApplyState`, actionID 112) — a *separate* Ankama
   mechanism that groups **multiple** sub-effects under one `(baseId, level)` key and applies
   them atomically. This is the ONLY thing that was ever a gap, and it's now implemented.

**Verified finding (why the registry being empty is correct, not a shortcut):**
- The highest effect actionID present anywhere in the real data is **97**; nothing in the
  111/112/113 range appears. STATE_APPLY (112) is used **0× across all spells + cards +
  events** — re-confirmed with a full actionID histogram (the parser is healthy: 52 distinct
  ids in spells alone). So DofusArena content applies its status effects *directly*, never
  through the `ApplyState` wrapper.
- The wrapper's data (which sub-effects each `(baseId, level)` state contains) is genuinely
  absent from every artifact: no `StateLoader`, no `states.dat`, no `CONTENT_STATE_FILE`
  config key, no server source — `StateManager.addState()` is never called anywhere (the
  client comment `ApplyState.java:100` admits states were server-side data never shipped in
  the client jar).
- Net: there is **no missing status-effect behavior**. Every effect spells actually have is
  handled; the `ApplyState` wrapper is a complete-but-dormant mechanism because no content
  invokes it.

**To do:**
- 🔎 (optional, only if ever needed) source a real state table and feed it via `RegisterState` — no shipping content requires it.
- 🔎 The AP/charac-loss reactive triggers (52/54/56/64) are honored on the *listen* side (effects listening for them are stored), but only `trigOnAttacked`/`trigOnAPUse`/`trigTableTurn` are currently *emitted* by the engine — extend emission if a spell that listens for the others turns out to be used in practice.

---

## 4. Spell / Card Cast Validation

| Feature | Status | Where |
|---|---|---|
| AP cost / range / free-cell / line-alignment | ✅ | `combat_actions.go` `validateCast` |
| Range extension via `Range` characteristic | ✅ | `combat_actions.go` |
| Cast-frequency limits (min interval / max-per-turn / max-per-target) | ✅ | `spell_cast_history.go` (Phase L) |
| Line of sight | ✅ | `line_of_sight.go` — bit-exact port of the reference's real 3D DDA (`LineOfSightUtils.getCellsInputs`/`WorldCell.isLineOfSightValid`) |
| Custom cast criteria (`canSummon`, carry criteria) | ✅ | `criteria.go` — covers the 4 tokens actually used in `spells.dat` |
| Per-effect target-validity (`ValidButNoEffectOnTarget`) | ✅ | `effects.go` point-shaped early return |

**To improve:**
- ✅ ~~LOS is a documented approximation~~ — now bit-exact. The per-direction `LineOfSight1/3/5/7/Top/Bottom` flags were already being parsed from `elements.ade` but silently dropped before reaching the combat layer (`ResolveCellSurfaces` never copied them into `ResolvedSurface`); threading them through plus porting the real 3-axis DDA walk (`gamedata.LineOfSightValidAt`/`LineOfSightEndValidAt`, `combat.generateLOSCellInputs`) closed the gap. Verified to have real, exercised impact on the shipped map data: 1 of 296 solid element states in the real `elements.ade` has genuinely non-uniform LOS flags — a *walkable* raised platform (height 3) whose Top/Bottom flags are blocked while all 4 edges are open, meaning it blocks a vertical (altitude-crossing) sightline through its body while not blocking any horizontal one. The old approximation only ever considered NON-walkable cells and only ever walked the flat X/Y line, so it would have missed this entirely. Pinned by `TestLineOfSightValidAt_*`/`TestLineOfSightEndValidAt*` (`internal/gamedata`) and `TestGenerateLOSCellInputs_*`/`TestHasLineOfSight_DirectionSensitiveBlocking` (`internal/combat`).

---

## 5. Non-Combat Server (Java parity) — all ✅

From `docs/08-java-parity-roadmap.md` §8.1, all ported & mostly improved over Java:

| Feature | Status |
|---|---|
| Auth / login (bcrypt) | ✅ |
| Coach creation / profile / world join-leave | ✅ |
| Social (friends / ignore) | ✅ |
| Chat (private / vicinity) | ✅ |
| GM chat-cheat commands (`/TP`, `/CARD`, `/APPEAR`, …), admin-gated | ✅ |
| Fighter CRUD | ✅ |
| Team presets | ✅ |
| Coach inventory / equipment | ✅ |
| Matchmaking → duel → `CREATE_FIGHT` | ✅ |
| Item exchange (card trading) | ✅ |
| Player statistics report (real per-coach tracking) | ✅ |
| Ladder / ranking (strength → Level/Rank) | ✅ |
| Disconnect cleanup (matchmaker/duel/exchange) | ✅ |
| Admin/observability endpoints (`/healthz`, `/stats`, pprof) | ✅ |
| Admin-console opcodes (8193–8195) | ✅ |
| Account web portal (register/login, data view, admin, impersonation) | ✅ |
| Graceful shutdown broadcast (`WORLD_SERVER_UNAVAILABLE`) | ✅ |

**Partial / to check:**
- ✅ ~~`RecvFighterUpdateInventoryRequest` (6011) — spell-blob per-entry size~~ — verified correct: spells use `StackInventory<Spell>(serializeQuantity=false)` (flat `int32[]`, 4 bytes/entry, no pos/quantity), equipment uses `ArrayInventory` (`[short pos][int32 id]`, 6 bytes/entry) — two genuinely different formats, both handled correctly by `parseSpellIDs`/`buildSpellBlob` vs `parseInventoryIDs`/`buildInventoryBlob` (`inventory_codec.go`). See §11 V4.
- 🟡 Fight-invitation (right-click challenge) — implemented Go-only, but was a stub in Java; verify against real client.

**Concurrency — online-coach shared state (found & fixed):**
- ✅ **Cross-goroutine data race on `domain.Coach` fields.** `world.Registry` synchronized only its *map*, but `Snapshot`/`SnapshotWithout`/`Get` handed out live shared `*domain.Coach` pointers whose mutable fields (`Strength`/stats, `PosX/Y/Z`, `Inventory`) callers then read/wrote lock-free. Concrete race (surfaced by `TestE2E_ConcurrentFightsLoad` under `-race`, only visible with a C toolchain so normal CI missed it): a fight-end hook writing a coach's `Strength` on the **fight actor goroutine** while another concurrent fight's return-to-world broadcast read that coach's fields via `buildActorSpawn` on a **background goroutine**. **Fix:** added `world.CoachView` value snapshots copied under the registry lock (`ViewOf`/`SnapshotViews[Without]`) for all cross-goroutine reads, and routed the three writers (`UpdateStats` from the fight-end hook, `UpdatePosition` from the movement handler + GM `/TP`) through the write lock. Every broadcast serializer (`buildActorSpawn`, `returnCoachToWorld`, GM `/stats`) now reads a value copy, never a live pointer. Pinned by `TestRegistryConcurrentViewAndUpdate` (unit, `-race`) and the now-green `TestE2E_ConcurrentFightsLoad` (`-race`). Remaining `Snapshot`/`SnapshotWithout` callers were verified to read only immutable fields (`Session`/`ID`/`Name`).
- ✅ **In-memory inventory staleness (fixed alongside).** Equipment/inventory edits and wagered-card transfers were DB-only, so a cached `coach.Inventory` went stale and `ACTOR_SPAWN` served new joiners a coach's *login-time* equipment. Now refreshed via `Registry.UpdateInventory` after each card mutation (`refreshOnlineInventory`), backed by the new `CoachService.GetInventory`.

---

## 6. Opcode Coverage

From `docs/opcodes/00-implementation-status.md`:

| Direction | Defined | Implemented | Partial | Not impl. | Not wired |
|---|---|---|---|---|---|
| Recv (client→server) | 42 | 41 | 1 | 0 | 0 |
| Send (server→client) | 80 | 68 | 1 | 5 | 6 |

**Send — not implemented (⛔) but defined:** `ACTOR_DISAPEAR` (4104), `ACTOR_REPOSITION`
(4106), `ACTOR_TELEPORT` (4510), `NO_INSTANCE_SERVER_AVAILABLE` (5000), `MEMBER_NOT_FOUND`
(3208). **Not wired (dead on both sides):** reconnection-ticket opcodes, etc.

**To check:** whether any real client flow needs `ACTOR_DISAPEAR`/`ACTOR_REPOSITION`/
`ACTOR_TELEPORT` (e.g. non-fight world movement, teleport animations).

---

## 7. Explicit Non-Goals (🚫 out of scope for v1)

| Feature | Status | Notes |
|---|---|---|
| **AI / PvE / CPU opponents** | 🚫 (except summons) | No bot/CPU/NPC opponents. A **full summon AI** exists (`summon_ai.go`): it derives one of 4 behaviors from the summon's spell + stats (no behavior data exists in the game files — the original `org.ankarton` turn-AI is absent from the decompiled reference) — **Blocker** (no spell → body-block nearest enemy), **Aggressive** (damage spell → close into range & cast till AP out; e.g. Gobball/Crackler/Victim-kamikaze), **Kite/fear** (debuff-only or high-MP → cast then retreat; e.g. Prespic/Madoll/Tofu), **Self-buff** (self-targeting buff → cast on self then block; e.g. Dial). |
| ~~Card-wagering payload in `END_FIGHT`~~ | ✅ **Done** | **Implemented end-to-end** (§12). The old "always empty / needs design" note was WRONG — the client protocol has a full wagering wire format; the server now stakes, transfers, and reports cards. |
| TLS / wire encryption | 🚫 | Client can't speak it |
| Multi-channel chat (`CHANNEL_*`) | 🚫 | |
| Rate limiting / idle-timeout / ban system | 🚫 | Not in Java either |
| Currency / shop / gold / mail | 🚫 | "gold" in repo = CSS theme only |
| Horizontal scaling / multi-instance world | 🚫 | Single-process monolith is the target |
| Prometheus `/metrics` | 🟡 | Deferred stretch goal (needs new dependency) |

---

## 8. Testing & Hardening

| Item | Status |
|---|---|
| `go test -race` clean across whole suite (incl. `test/e2e`) | ✅ |
| Concurrent-fights load test (`cmd/loadtest`, pprof-enabled) | ✅ 300 fights @ concurrency 50 |
| Per-opcode wire-format tests | ✅ |
| Security: bounds-checked packet readers | ✅ |
| **Per-effect-kind unit tests** (one per `EffectKind`) | ✅ Complete — every `EffectKind` now has dedicated coverage (last gap `EffectAdaptLook` filled) |
| Damage-formula property tests (0 / negative resist, rebound edge cases, flat-before-percent, physical-bypasses-dmg/res) | ✅ `damage_property_test.go` (Phase N) |
| Height/altitude pathfinding (blocked cell, ascend/descend limits) + real-map A* | ✅ `pathfind_test.go` + `pathfind_realdata_test.go` |
| Spell-blob vs card-blob wire format (4-byte vs 6-byte entry) | ✅ `inventory_codec_test.go` |

---

## 9. Prioritized Remaining Work

**High value / affects fidelity — ✅ ALL DONE (see §10):**
1. ✅ ~~Reactive trigger-bus~~ — built (HV1). State bundles remain data-blocked (see below).
2. ✅ ~~Crit-damage for spells/cards~~ — done (HV2).
3. ✅ ~~Esquive PA/PM decision~~ — decided + verified: keep flat `Res*Loss` (HV3).
4. ✅ ~~Tackle Evasion scaling~~ — done (HV4).
5. ✅ ~~Summon stat-template wiring~~ — done (HV5).

**Medium:**
6. ✅ ~~State-bundle expansion~~ — done (`state.go`), see §10 HV6.
7. ✅ ~~Per-map **special-cell** data sourcing + random fight-map selection~~ — **already implemented** (JSON sidecars for all 15 fight maps + random map selection); verified end-to-end + coverage gaps closed (§14). Free-placement-zone restriction also now done (§15).
8. ✅ ~~Per-effect-kind + property-based tests~~ — done (§11 V1).
9. ✅ ~~Verify rebound double-count~~ — was a **real bug**, now fixed (§11 V2).
10. ✅ ~~A* against height-blocked real-map path~~ — verified correct + tested against a **genuine** real-map obstacle (§11 V3, updated).

**Low / decide-if-needed:**
11. ⛔ `ACTOR_DISAPEAR` / `ACTOR_REPOSITION` / `ACTOR_TELEPORT` opcodes (§6).
12. ✅ ~~`FIGHTER_UPDATE_INVENTORY` (6011) spell-blob entry-size~~ — verified correct + tested (§11 V4).
13. ✅ ~~Card-wagering flow~~ — **done** end-to-end (§12).
14. 🟡 Prometheus `/metrics` (§7).

---

## 10. High-Value Change Log (this pass)

All five high-value items from §9 implemented, tested, and green under `go test -race`
across the whole suite (`internal/...` + `test/e2e`, ~150s e2e). New code lives in
`internal/combat/`.

| # | Item | What changed | Tests |
|---|---|---|---|
| **HV1** | Reactive trigger-bus | New `triggerbus.go`: `deferReactiveEffect` stores effects declaring `TriggersBefore`/`TriggersAfter` on their carrier (mirrors `RunningEffect.mustBeTriggered`→`storeEffect`); `fireTrigger` executes them on `trigOnAttacked`(HP loss)/`trigOnAPUse`/`trigTableTurn`; wired into `applyDamageFromEffect`, `broadcastCharacUse`, and the table-turn tick. `Fighter.ReactiveEffects` field added. Also hardened `AreOpponents` against nil (reactive effects may have no fighter caster). | `TestReactiveEffect_*`, `TestEffectMustBeDeferred` |
| **HV2** | Crit for spells/cards | `executeEffectsForHit`/`selectEffectsForCrit` (`effects.go`): a crit runs the spell/card's `IsCritical` effect subset; `handleSpellCast`/`handleCardUse` pass the rolled `crit`. Data confirms real impact (108 spell + 84 card critical-flagged effects). | `TestSelectEffectsForCrit_*` |
| **HV3** | Esquive PA/PM | No behavior change — decided the flat `ResAPLoss`/`ResMPLoss` percent-resist IS the faithful port (this game has no dodge roll). Added a regression test locking the deterministic contract + updated docs/comments. | `TestEsquiveAPMP_IsFlatResistNotRoll` |
| **HV4** | Tackle Evasion | `Fighter.EvasionBonus` (non-wire) + `evasionChanceAgainst` shift the 67% base by the per-side Evasion difference, clamped [0,100]; wired into `attemptEvadeTackle`. Default 0 preserves old behavior. | `TestEvasionChanceAgainst_*`, `TestAttemptEvadeTackle_*` |
| **HV5** | Summon stats | `applySummonTemplateStats` resolves a plain `EffectSummon`'s `SummoningTemplate` via `params[0]` and sets real HP/AP/MP; Double/Mirror keep caster stats; breed fallback if template missing. | `TestApplySummon_*` |
| **HV6** | State bundles (`ApplyState`) | New `state.go`: full `State`/`stateManager` port. `applyStateBundle` reads `params=[baseId,level]`, resolves the state (`uniqueId=(baseId<<8)+level`), and executes each sub-effect through the shared executor (triggered sub-effects defer, instant run), then notes endTriggers. Registry empty by default; `RegisterState` populates it. Ported from `ApplyState.computeValue`/`execute`. | `TestStateUniqueID_*`, `TestApplyStateBundle_*`, `TestRegisterAndLookupState` |

**Why HV6's registry is empty by default (not a shortcut — a verified finding):** the state
*definitions* are genuinely absent from every artifact in this project (no `StateLoader`,
no `states.dat`, no config key, no server source; `addState()` is never called — Ankama's
own client comment confirms states were server-side data never shipped in the jar), **and**
a full scan of the real `spells.dat`/`cards.dat`/`events.dat` found STATE_APPLY (actionID
112) is used **0 times**. So there is no shipping content to reproduce; the mechanic is
complete and will expand any state the moment one is sourced via `RegisterState`.

**Still not applicable:** `EndTriggers` (parsed `null` in this project's effect data —
nothing to wire).

---

## 11. Verification & Hardening Change Log (this pass)

Verification pass on the open 🔬 items + test-hardening. Full suite (`internal/...` +
`test/e2e`, ~155s e2e) green under `-race`. One item (V2) turned out to be a **real bug**;
the rest were verified correct and pinned with tests.

| # | Item | Outcome | Tests |
|---|---|---|---|
| **V1** | Trigger-bus emission (52/54/56/64) | The reactive bus now EMITS every trigger any real spell listens for. `characteristicTriggerEmission` maps `(kind, charac)` → trigger id (crit-rate/AP loss→52, crit-rate debuff→54, crit-rate leech→56, dmg debuff→64); `emitCharacTrigger` fires it after `EffectCharacLoss`/`Debuff`/`Leech`. Previously these listeners were stored-but-inert. | `TestEmit_*` |
| **V2** | Rebound double-count | **Real bug, fixed.** `DmgRebound` was applied twice for spell/close-combat hits — once inside `ComputeHPLoss` (correct, mirroring `HPLoss.computeValue` lines 312-331) and again in the reactive-return step of `applyDamageFromEffect`. The reference has NO separate reactive rebound (only `STRIKE_BACK` is a distinct reactive effect). Removed `DmgRebound` from the reactive step; it now applies exactly once. | `TestReboundAppliedExactlyOnceEndToEnd`, `TestReboundNotAppliedTwiceForCloseCombatStyleDamage` |
| **V3** | A* height-blocking (Phase K) | **Verified correct**, and the roadmap's remaining honest caveat is now closed. `FindPath`/`ValidateClientPath` already respected `ArrivalAltitude` `blocked=true` and the `maxAscend`/`maxDescend` limits — first pinned with a height-aware mock provider, then a **real-map** (`fightMapID=2`) integration test (generic walkable-path round trip). That map-2 test, however, could only ever pass *vacuously*: an exhaustive scan of map 2's 972 populated cells found **zero** adjacent walkable-cell pairs whose altitude delta exceeds ±`maxAscend`/`maxDescend` — so no test using map 2 can ever exercise a genuine height-blocked step. Scanning the rest of the fight-map pool (`MapStore.FightMapIDs()`, which duels are randomly assigned from) found real ones on maps 4, 5, 6, 7, 8, 9, 12, 13. Added `TestFindPath_RealMapRejectsGenuineHeightBlockedStep` pinned to a concrete map-4 cliff ((9,0) z=5 → (9,1) z=-1, a 6-level drop), proving both `ValidateClientPath` rejects the illegal direct step (server-authority/anti-cheat) and `FindPath` never emits it while routing. **Corner-cutting is a separate, already-closed non-issue**: fight movement only ever takes single-axis steps (see `pathfind.go`'s `fightMoveDirections` doc comment), so the two-axis diagonal move a corner-cut bug requires is structurally impossible here — no real-map data could ever exercise it either way. | `TestFindPath_*` (height), `TestFindPath_RealMap*`, `TestFindPath_RealMapRejectsGenuineHeightBlockedStep` |
| **V4** | 6011 spell-blob entry size | **Verified correct.** Confirmed against the decompiled reference: spells serialize via `StackInventory<Spell>(serializeQuantity=false)` = flat `int32[]` (**4 bytes**/entry, no pos/quantity); equipment via `ArrayInventory` = `[short pos][int32 id]` (**6 bytes**/entry). The Go 4-vs-6 split is right; `parseSpellIDs` was previously untested. | `TestParseSpellIDs*`, `TestBuildSpellBlobIsFlatInt32Array` |
| **V5** | Per-EffectKind + property tests (Phase N) | Per-kind coverage completed (last gap `EffectAdaptLook`). Added formula property tests: flat-resist-before-percent ordering, and physical-bypasses-Dmg/Res. | `TestEffectAdaptLook_*`, `TestComputeHPLoss_FlatResistAppliedBeforePercent`, `TestComputeHPLoss_PhysicalIgnoresDmgAndRes` |

---

## 12. Card-Wagering — implemented end-to-end

**Research correction:** the pbworks wiki documents the *beta13 browser game* (5000-kamas
team budget, no card collection, no wagering) — it does NOT describe this mechanic. The
authoritative source is the **decompiled client**, which has a **fully-built wagering wire
protocol**. The old §7 "always empty payload / needs design" note was wrong.

**Key facts (from the client decompile):**
- The wagered entity is the **`CoachCard`** (cosmetic/equipment cards), NOT the `FighterCard`.
- Coaches can **lock ≤10** cards to protect them from being staked (opcode 5203, already handled).
- The bet is an `int bet` (0/1) on matchmaking/invite/CREATE_FIGHT (already plumbed).
- `END_FIGHT` (8300) already carries won/lost/bonus card arrays (per card: `int templateId` +
  `byte cursed`); the Go builder existed but was called with `nil, nil`.

**System built (one random non-locked card staked per coach):**

| Layer | What | Where |
|---|---|---|
| Service | `SelectStakeCard` (random unlocked pos=0 card), transactional `TransferCard` (ownership move, cursed-preserving, multi-quantity aware, re-verifies eligibility), `LockCard` now enforces the **10-card cap** | `internal/service/coach.go` |
| Duel | `stakeCards` map + `SetStakeCard`/`StakeCard` so the stake survives setup→fight-end | `internal/world/duel.go` |
| Fight creation | `prepareCreateFight` selects a stake per coach (bet fights only); rejects with **cancel reason 47** (`cantHoldTheBet`) if a coach has no eligible card; threads stakes into `duelTeamInfo.BetCard` → announced in CREATE_FIGHT's per-team bet-card list | `internal/dispatch/handlers_fight.go`, `packets_fight.go` |
| Fight end | `settleWageredCards` transfers each loser's stake to the winner (authoritatively), populates `FightEndOutcome.LostCards/WonCards` → `buildEndFight` now sends the real won/lost blobs | `internal/dispatch/fightend_hook.go`, `combat/fightend.go`, `combat/fightresult.go` |
| Bet OFF | No stake, no transfer (CREATE_FIGHT sends count 0) — a friendly fight, matching the client's `bet=0` path | — |

**Tests:** service (`wagering_test.go` — lock cap, stake eligibility, transfer/cursed/multi-qty/
locked-skip/gone), wire (`endfight_cards_test.go`, `TestBuildCreateFightBetCardPayload`), and a
full **e2e bet fight** (`TestE2E_BetFightTransfersStakedCardToWinner` — seed cards → bet
matchmaking → CREATE_FIGHT announces both stakes → forfeit → winner gains the loser's card,
loser loses it, winner keeps his own). Full `internal/...` + `test/e2e` suite green under
`-race`. Also fixed a **pre-existing flaky** lifecycle test (`TestFight_CloseCombatDealsDamageAndEndsFight`)
whose 2s phase-progression deadline was too tight under race+parallel load (bumped to 10s).

**Deferred (small, optional):** the `bonusCards` END_FIGHT array (server sends none — it was
for a separate bonus-reward flow no content drives); value-matched/multi-card staking (the
protocol supports arrays, but the chosen rule is one random card per the design decision).

---

## 13. Event cards (per-round) — implemented end-to-end

**Wiki cross-check outcome:** I audited the wiki's game-mechanics pages against the code and
the decompiled client. Almost everything the wiki describes is either done or **correctly
absent** (Levels/Skills/XP, Lethal Blows, TileDrop's round-15 board-shrink, and the
Charge/Combos geometric spell system are all **beta13-only features removed in this card
version** — confirmed absent from the reference client too). The **one genuine, version-
relevant gap** was the per-round **event card**, now implemented.

**Mechanic (server-authoritative, matching the decompiled client):** each round (table-turn),
the server draws a random event from `events.dat`, sends its real `eventId` in
`NEW_TABLE_TURN_BEGIN` (8100) so the client displays the card, and **applies the event's
effects to every fighter** (the wiki's "a random event card which affects the whole round").
Previously the server always sent `eventId 0` and applied nothing.

| Piece | Where |
|---|---|
| Deck draw (shuffled deck of all events, no-repeat-until-exhausted, RNG-seeded → deterministic; mirrors `AbstractEventManager.newShufflizedEvents`) | `combat/events.go` `selectEventID`/`buildEventDeck`/`shuffleEventDeck` |
| Effect application to all living fighters (each event effect self-applied per fighter; `areaShape=32767`=AreaEmpty means "whole round", so it bypasses cell-area resolution; round-scoped effects auto-expire via the existing duration tick) | `combat/events.go` `applyDrawnEvent`/`applyEventEffects` |
| Wire: real `eventId` in `NEW_TABLE_TURN_BEGIN` (message sent BEFORE effects so the client instantiates the card first) | `combat/packets.go` `buildNewTableTurnBegin`, `combat/turns.go` |

**Bonus cards (wiki's "1–3 dealt to team leaders, playable once") — NOT applicable:** research
of the decompiled client confirmed this is a **beta13-only** feature. In this card version there
is **no** bonus-card-play opcode, and the client's `NEW_TABLE_TURN_BEGIN`/`FIGHTER_CARD_USE`
paths have no team-leader-hand concept. So nothing to implement.

**Tests:** `combat/events_test.go` (inert without data, deals every event once before repeat,
deterministic under seed, effect hits all fighters for heal + damage events, wire eventId) and
a full **e2e** `TestE2E_NewTableTurnCarriesEventCard` (real fight → first `NEW_TABLE_TURN_BEGIN`
carries a real non-zero event id from `events.dat`). Full `internal/...` + `test/e2e` suite
green under `-race`. Updated the shared `startFullFight` e2e helper to drain past the new
event-effect frames.

---

## 14. Special cells & fight-map data — derived from real map art

**Update (this pass): special cells are now sourced from the maps themselves.** An earlier pass
authored per-map `specialcells.json` sidecars *by hand* (6 invented cells per map). That was
wrong — the original game **paints every special tile into the map's own art** as a negative-gfx
"Bonus" element. Those markers are recovered directly from the `.amw` data, so every map now
carries its *authentic* special-cell layout with no hand-authoring.

**How the derivation works:**
- Each special tile is a `Bonus` element whose resolved gfx is **negative** (`−1002..−1009`).
  `parser.ResolveCellGfx` already surfaces these; `Map.deriveSpecialCells` (`gamedata/map.go`)
  collects them at load time into `Map.SpecialCells()`.
- The gfx magnitude **is** the client render-template id (the `staticEffects.dat` SPECIAL area
  id `1002-1009`), which maps 1:1 to the gameplay type. The mapping was confirmed
  **field-for-field against the real `staticEffects.dat`** (each area's effect actionID/params):

  | gfx | cellBaseId | staticEffects effect | type |
  |---|---|---|---|
  | −1002 | 1002 | actionID 63 (instant kill) | **killer** |
  | −1003 | 1003 | actionID 1, dmg 10 | **trap** |
  | −1004 | 1004 | actionID 72, +1 range | **eagle_eye** |
  | −1005 | 1005 | actionID 29/31/33/35, +resist | **shield** |
  | −1006 | 1006 | actionID 78, +heal-received | **panacea** |
  | −1007 | 1007 | actionID 48/50/52/54, +dmg | **enthusiasm** |
  | −1008 | 1008 | actionID 13, +1 AP | **motivation** |
  | −1009 | 1009 | actionID 69, heal 5 | **healing_heart** |

  (`gamedata.specialCellBaseIDToType` is the single source of truth for this table.)
- `SpecialCellStore.Get` returns the derived layout by default; an optional
  `data/maps/<id>/specialcells.json` **overrides** it when present (for bespoke curation).
- **Random fight-map selection** is unchanged: `selectFightMapID` picks a random map from
  `MapStore.FightMapIDs()` (ids **2–16**, plus 1000); map 2 is only the discovery fallback.
- **Wired into the live fight:** `resolveSpecialCellRenders` fills the CREATE_FIGHT special-cell
  list, and `applySpecialCells` registers BOTH the gameplay cells (`SetSpecialCell` → trigger at
  turn start) and the render tuples (`AddSpecialCellRender` → drawn by the client).

**Coverage:**

| Test | What it locks in |
|---|---|
| `TestSpecialCellDerivationGroundTruth` (gamedata) | Derived cells for maps 2 & 8 match the ground truth from scanning their baked negative-gfx tiles |
| `TestSpecialCellLayoutDerivedFromMapArt` (gamedata) | Every fight map derives special cells whose `cellBaseId`↔`Type` agree (212 cells across 16 maps) |
| `TestE2E_CreateFightCarriesRealSpecialCells` (e2e) | A live duel's CREATE_FIGHT carries the map's derived cells with valid `cellBaseId`s (special 1002–1009) |
| `TestResolveSpecialCellRendersFromRealMap` / `TestApplySpecialCellsRegistersGameplayAndRenderCells` (dispatch) | Render tuples load from a real map; `applySpecialCells` registers both gameplay + render cells |
| `combat/specialcells_test.go` | All 8 cell types trigger correctly at turn start |

**Net:** every fight takes place on a random real map with its **real, baked** special tiles,
covered at every layer (data → gameplay → wire → e2e). Full suite green.

---

## 15. Free-placement zone restriction — implemented

The last open combat-fidelity item. During the PLACEMENT phase, `handleMoveToFreePlacement`
(`combat/phases.go`) previously accepted **any** walkable, unoccupied cell. It now also rejects
any cell outside the fighter's **own team's placement zone**.

**Zone definition (matches the reference client):** exactly the team's `FightStartPointElement`
cells (map element kind 1000), keyed by team side. The reference client only sends a
`MoveToFreePlacementRequest` (8021) when `StartPointManager.containsTarget(teamId, target)` is
true, and that set is built solely from type-1000 elements split by team id. The "per-map
placement-zone data source" that was previously "not yet identified" turned out to be the
kind-1000 start cells the `.amw` parser **already extracts** — exposed as
`Map.FightStartCells()`, keyed by `teamSideByte = TeamID-1` (the same convention
`resolveFighterPlacementCells` uses for initial auto-placement).

**Implementation:** `isInPlacementZone(fighter, pos)` (`combat/phases.go`) checks
`Map.FightStartCells()[fighter.TeamID-1]` for a matching X/Y. Permissive fallbacks preserve
prior behavior where the zone can't be known: no map data attached (dev/test), or a map with no
start cells for that team side → allow (never lock a player out over a data quirk). A rejected
move produces no `MoveToFreePlacement` (8022) frame, mirroring the existing occupancy/ownership
silent-reject.

**Tests** (`combat/placement_zone_test.go`): permissive-without-map-data; real-map (fightMapID 2)
accept-onto-own-zone-cell + **reject an out-of-zone walkable cell** (the exact gap); every
own-zone cell is a legal placement. No dispatch/e2e placement-move tests existed to break (the
e2e movement tests exercise action-phase A* movement, not placement repositioning). Full
`internal/...` + `test/e2e` suite green under `-race`.
