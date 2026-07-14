# 8. Global Roadmap: Java-Parity Status + Combat Engine Delivery Plan

This is the **single living roadmap** for the project: a status snapshot of everything
ported from the legacy Java server (`src/org/ankarton`) so far, plus the concrete, ordered
delivery plan for the one major piece of work left — **the combat engine**, which the
legacy Java server never built either (it only implemented the presentation phase).

A fresh session/thread should be able to read this document alone and know exactly: what's
done, what's intentionally deferred, and what the next concrete step is. **If you're
picking this up fresh, skip straight to §8.12 ("What's next") — everything before it is
historical record of what already shipped.**

**Companion references** (read these when you reach the relevant phase, don't re-derive
them):
- [`docs/opcodes/`](./opcodes/) — exhaustive, per-domain, client-source-of-truth wire
  protocol reference. §7-8 of that folder (`07-fight-lifecycle.md`,
  `08-fight-combat-engine.md`) are the primary input to the plan in §8.9 onward.
- [`05-combat-engine.md`](./05-combat-engine.md) — the original engine design doc. Several
  of its claims were corrected/expanded during the opcode-documentation pass — see the
  "Corrections" section at the end of `opcodes/08-fight-combat-engine.md` for the full diff.
  Treat that file as authoritative where the two disagree.
- [`02-protocol.md`](./02-protocol.md) — general framing/primitive rules, now cross-linked
  to `opcodes/`.

---

## 8.1 Summary: feature parity status (everything except combat)

| Area | Status | Notes |
|---|---|---|
| Auth (login) | ✅ Ported + improved | bcrypt instead of Java's cleartext compare; duplicate-login rejection preserved |
| Coach creation/profile | ✅ Ported | Name filtering, world join/leave broadcast, position tracking |
| Social (friends/ignore) | ✅ Ported | Directional edges, idempotent add, matches Java semantics |
| Chat (private/vicinity) | ✅ Ported (minus GM cheat-commands) | See §8.4 for the `/TP` etc. commands |
| Fighter CRUD | ✅ Ported + fixed | Proper join tables instead of Java's CSV-string columns; real `FIGHTER_DELETE` (Java's never actually deleted the DB row) |
| Team presets | ✅ Ported + fixed | Slot allocation fixed (Java's `-1` sentinel handling was broken); fighter IDs now actually included in `TEAM_PRESET_LIST` (Java bug: never sent them) |
| Coach inventory/equipment | ✅ Ported + fixed | Equip/unequip fixed vs Java (slot-0 ambiguity, "unequip-all-first" ordering); inventory-unlock bug fixed, see §8.2 |
| Matchmaking → Duel → `CREATE_FIGHT` | ✅ Ported + improved | Real coach/fighter data now flows into `CREATE_FIGHT` (Java hardcoded bet/type and sent no real fighter data at all); team names still cosmetic placeholders, harmless |
| Item exchange (trading) | ✅ Ported + fixed | Actual card transfer + client notification on completion (Java's post-trade notification was commented out); also fixed a bug where handlers looked up the exchange by the client-sent `exchangeId` instead of by coach, see the note in `internal/dispatch/handlers_exchange.go` |
| Fight-invitation (right-click challenge) | 🟰 Stub in both servers | Preserved as-is; Java's own version has an inline TODO admitting it's unfinished. Opcode numbers for accept/reject now resolved (4305/4307), see `opcodes/04-matchmaking-invitation.md` |
| Player statistics report | ✅ Ported + implemented | Go tracks real per-coach stats (fights/wins/losses, consecutive wins, time-in-fight, total play time) persisted in the `coachs` table; Java only ever sent hardcoded placeholders. See `internal/dispatch/{packets_stats.go,fightend_hook.go}` and `CoachService.ApplyFightResult` |
| Ladder/ranking | ✅ Ported + fixed | Per-coach `strength` drives Level/Rank via `domain.ladder.go` (simple fixed-delta model on fight end); the legacy `ladder = 2` byte was actually a mis-serialized ladders-strength block that broke Level/Rank display — now emitted correctly (`writeLaddersStrength`) |
| GM chat-cheat-commands (`/TP`, `/CARD`, ...) | ✅ Ported + fixed | Gated on `Account.IsAdmin` (Java had zero permission check), real client feedback added. See §8.4 |
| Disconnect cleanup (matchmaker/duel/exchange) | ✅ Fixed in Go | Java never cleaned these up; Go now does, see §8.3.1 |
| Admin/observability endpoints (`/healthz`, `/stats`, pprof) | ✅ Implemented | `internal/adminhttp`; `/metrics` (Prometheus) remains a deferred stretch goal |
| Account web portal (register/login, data view, admin, impersonation) | ✅ Implemented | `internal/webadmin` + `cmd/web`; see `docs/10-web-portal.md` |
| Rate limiting, idle-timeout, ban system, currency/shop, mail | ❌ Not implemented in either | Out of scope for now |
| **Combat engine (turns/spells/damage)** | ✅ **Implemented (Phases A–I complete)** | Full lifecycle CREATE_FIGHT → presentation/placement/observation/action → turn cycle → spell/card/close-combat casting → effects/damage → fight end, all wired end-to-end. See §8.8/§8.9 for what shipped and the remaining follow-up items (§8.11) |

---

## 8.2 Done: fixed the coach-inventory "unlock" bug

**The bug (Java, carried into Go for parity until now):** `CoachInventoryUpdateRequest`'s
unlock branch sets the card's flag to `CoachCardFlagCursed` instead of clearing
`CoachCardFlagLocked` — a copy-paste bug, not an intentional design choice.

**Decision: fixed in Go.**

- [x] `internal/service/coach.go`: `LockCard`/`UnlockCard` do a proper bitwise set/clear of
      just `CoachCardFlagLocked`, mirroring the client's own correct
      `AbstractCoachCard.setLocked()`.
- [x] `internal/dispatch/handlers_coach_management.go` uses the new helpers.
- [x] Regression test: `TestLockUnlockCardRoundTrip` (`internal/service/coach_test.go`).
- [x] Investigated the default-cursed question: Java's real call sites disagree by
      context (`CoachExchange.ok()` creates cursed trade cards; GM `/CARD`/`/ALLCARDS`
      create non-cursed granted cards). `CoachService.AddCard` now takes an explicit
      `flag uint8` param instead of hardcoding cursed for every caller.

## 8.3 Decided: robustness fixes (Go improves on Java here)

### 8.3.1 Done: disconnect cleanup for matchmaker/duel/exchange

**Problem:** `dispatch.HandleDisconnect` only removed the coach from `world.Registry` and
broadcast `ACTOR_DESPAWN`. It didn't cancel a pending matchmaking search, notify the other
side of an in-progress duel, or notify the other side of an in-progress exchange.

- [x] `HandleDisconnect` now calls `deps.Matchmaking.CancelSearch(coach.ID)`.
- [x] `HandleDisconnect` looks up `deps.Duels.GetByCoach` and calls `cancelDuel` with
      `CancelReasonTargetDisconnected` if found.
- [x] `HandleDisconnect` looks up `deps.Exchanges.GetByCoach` and calls
      `endExchangeWithError` if found.
- [x] Tests in `test/e2e/disconnect_cleanup_test.go` cover all three paths.

### 8.3.2 Done: admin/observability endpoint

- [x] New package `internal/adminhttp`: `/healthz`, `/debug/pprof/*`, `/stats`
      (online players, active fights).
- [x] Wired into `internal/app/app.go`, independently toggleable via `Server.AdminAddr`.
- [x] `docs/06-config-and-ops.md` §6.5 documents the actual endpoints.
- [ ] Stretch/deferred: Prometheus `/metrics` (needs a new dependency).
- [x] Tests: `internal/adminhttp/adminhttp_test.go`, `test/e2e/admin_http_test.go`.

## 8.4 Done: GM chat-cheat-commands, ported with real permission checks

**Java's version:** `VicinityMessage.java` intercepts `/`-prefixed chat with **zero
permission check** — any player can teleport anywhere, spawn any card for free, etc.

**Decision:** ported (useful for local testing/debugging), gated behind
`Account.IsAdmin`.

**Also investigated and explicitly rejected as out of scope**: the client's separate
`CONSOLE_ADMIN_COMMAND`/`CONSOLE_ADMIN_COMMAND_RESULT` opcode pair (8193/8194) is a
server-operations/monitoring console (JMX-style), never wired up even in legacy Java. Not
in-game GM tooling — not pursued.

- [x] Schema: `IsAdmin bool` on `domain.Account`, migrations across all 3 dialects.
- [x] `netio.AccountRef.IsAdmin`, snapshotted at login; `sessionIsAdmin(session)` helper.
- [x] `handleVicinityMessage` intercepts `/`-prefixed messages before broadcast, routes to
      `handleGMCommand`, no-ops for non-admins.
- [x] All commands ported with real fixes over Java (`/STATS`, `/CELLID`, `/TP`, `/CARD`,
      `/ALLCARDS`, `/PRES`, `/CANCEL`) — see `docs/02-protocol.md` §2.4.9 for the full
      command table.
- [x] Tests: `test/e2e/gm_commands_test.go`.

## 8.5 (superseded)

Combat engine sequencing decisions previously tracked here are superseded by §8.8 onward —
this is now the active section of the document.

## 8.6 Reference notes (context, not action items)

Confirmed during past comparison passes, intentional, don't re-litigate without new info:

- **`CREATE_FIGHT` packet**: already improved over Java (real coach/fighter IDs, names,
  breed/sex/skin, real bet/fight-type). Team names still cosmetic placeholders
  (`"team1"`/`"team2"`), harmless. Spell/equipment/stats-report sub-blobs are still sent
  empty — see §8.8 and §8.9 below for why this now matters and what to do about it.
- **Fight-invitation (right-click challenge)** and **multi-channel chat (`CHANNEL_*`)**
  remain intentionally unimplemented stubs, matching Java's own incompleteness.
- **Player statistics report** and the ladder Level/Rank are now fully implemented in the Go
  server (real tracking + persistence), unlike the legacy Java stub. The old hardcoded
  `ladder = 2` byte turned out to be a mis-serialized LADDERS_STRENGTH block that silently broke
  the client's Level/Rank parse — now fixed. See `opcodes/03-coach-world.md`
  (COACH_INFORMATION / PLAYER_STATISTICS_REPORT sections).

## 8.7 Done: account web portal (register/login, data view, admin, impersonation)

A public-facing account website: register/login, self-service data view, admin console,
impersonation. Full design in `docs/10-web-portal.md`.

- [x] `internal/webadmin` — stdlib-only (`html/template`, embedded assets, no build step).
- [x] `internal/service/account.go` — `AccountService` (register, verify/change password,
      `SetAdmin`, `ListAccounts`, `GetAccountDetail`, `DeleteAccount`).
- [x] `Account.IsAdmin` column + migration.
- [x] Config `web` block; `cmd/web` standalone binary; `cmd/seedaccount --admin`.
- [x] Session/CSRF/impersonation security model — see `docs/10-web-portal.md`.
- [x] Tests: `internal/service/account_test.go`, `internal/webadmin/webadmin_test.go`.

---

## 8.8 Where the combat engine actually stands today

**Status: implemented end-to-end** (Phases A–I in §8.9, all complete). This section is
now a "what shipped" summary — read `internal/combat`'s package doc comments and
§8.11 (follow-ups) for anything you're about to touch.

### What exists

- `internal/combat/breed.go` — the full 12-playable-breed base-stat table (HP/AP/MP/
  INIT/CriticalRate/FumbleRate/close-combat element+AP+damage), ported directly from
  `client/.../fighter/Breed.java`'s enum constructor values. `GetBreedStats`/
  `AllPlayableBreeds` lookups.
- `internal/combat/fighter.go` — `Fighter` struct (no more invented `DmgNeutral`; the
  characteristic enum matches the reference exactly), `NewFighterFromBreed` populates
  real HP/AP/MP/INIT/crit/fumble from breed data, `AddCharacteristic` clamps to the
  correct per-characteristic bounds table (with the `65036` decompiler artifact
  corrected to `-100`, not ported verbatim).
- `internal/combat/timeline.go` — redesigned around an explicit validate-gate: turns
  don't auto-advance, `StartNextTurn`/`EndCurrentTurn` require an external caller
  (Fight) to explicitly end the current turn before the next one can start, mirroring
  `askForFighterStartTurn`/`askForFighterEndTurn`. Summon insertion correctly queues
  multiple summons from the same father together (not just "right after father").
- `internal/combat/fight.go` + `phases.go` + `turns.go` — the full
  Presentation→Placement→Observation→Action phase state machine, each transition
  reachable via *either* its phase clock firing *or* every coach signaling ready,
  funneling through the same `askForXEnd()` method either way (matching the reference's
  single-entry-point design). `Phase` is atomic-backed (`CurrentPhase()`) so external
  goroutines (tests, admin endpoints) can safely observe it without racing the actor.
- `internal/combat/combat_actions.go` + `effects.go` + `effects_registry.go` — close
  combat, spell casting, card use, all sharing a common (simplified) validation
  pipeline (AP cost / range / free-cell / line-alignment), and an effect executor
  covering the MVP-priority mechanics from `05-combat-engine.md` §5.4.1 (damage/heal/
  leech, AP/MP cost, characteristic mods, push/pull, property toggles, summon).
- `internal/combat/damage.go` — `ComputeHPLoss` ported per §5.6's formula (flat +
  percent modifiers, rebound handling); `randomRound` implements the documented
  "probabilistic rounding" pattern (exact reference behavior was never recoverable, see
  §8.11).
- `internal/combat/pathfind.go` — A* with short-diagonal cost, no corner-cutting;
  height/altitude hooks now backed by real `.amw`/`elements.ade` map data when attached to
  a fight (Phase K, §8.11 item 3), falling back to the old always-walkable/never-blocked
  stub otherwise.
- `internal/combat/fightend.go` — `checkFightEnd`/`killFighter`/`endFight`/forfeit
  routing, `EndFightDoneMessage` ack-gating before the actor's `Run()` returns.
- `internal/combat/manager.go` — `Manager.Create` now takes real `[]*Team` +
  a `Broadcaster` and actually starts a live `*Fight`; `Count()` reflects real fights.
- `internal/dispatch/handlers_fight.go` (`instantiateFight`) — wires
  `TEAM_MATE_SET_READY_FOR_PLACEMENT` to build real `combat.Team`/`combat.Fighter` data
  (breed + spells + objects loaded fresh from DB/gamedata) and call
  `deps.Fights.Create`, storing the `*combat.Fight` on the `world.Duel`.
- `internal/dispatch/handlers_fight_combat.go` — every in-fight Recv opcode (8021, 8023,
  8031, 8105, 8107, 8109, 8111, 4503, 4521, 8151, 4321) decodes its wire payload and
  forwards a `combat.Command`; all validation/mutation happens inside the Fight actor.
  `internal/world/combat_broadcaster.go` implements `combat.Broadcaster` over
  `world.Registry`, the answer to "how does the Fight goroutine emit outbound packets."
- Opcode constants for the entire previously-missing 8018–8300 + 4500s range added to
  `internal/protocol/opcodes.go`/`names.go`.

### What's still simplified / deferred (not blockers, see §8.11 for the full list)

- Free-placement/movement validation has no real map-boundary data (no `.amw` parser
  exists yet) — only fighter-occupancy collision is enforced.
- The effect system implements the *instantaneous* execution path only; full
  duration/trigger-bus scheduling (poison ticks continuing across turns, timed buffs
  expiring, state-bundle end-triggers) is not built — see §8.11 item 2.
- Hit-location (front/side/back) damage bonus is not implemented (no
  `FourSidedPartLocalisator` port).
- `randomRound`'s exact reference behavior, the full `RunningEffectConstants` trigger-ID
  table, and `FighterPropertymanager`'s complete bitmask remain open research items
  carried over unchanged from the original roadmap.

---

## 8.9 Combat engine delivery plan — ordered phases

Each phase is meant to be independently shippable/testable before moving to the next.
Do not skip ahead — later phases assume earlier ones are solid, especially the timeline
redesign in Phase C, which the rest of the engine is built on top of.

### Phase A — Unblock: breed data + engine corrections ✅ done

- [x] Sourced the breed base-stat table from the client's decompiled
      `client/com/ankamagames/dofusarena/common/game/fighter/Breed.java` (all 12
      playable breeds' HP/AP/MP/INIT/critical/fumble/close-combat element+AP+damage
      values, ported byte-for-byte from the enum constructor calls) —
      `internal/combat/breed.go`.
- [x] `NewFighterFromBreed` populates HP/AP/MP/INIT max (fully topped up) +
      CriticalRate/FumbleRate from the breed table; bare `NewFighter` kept for tests
      that want to hand-populate stats themselves.
- [x] Removed `DmgNeutral` from `CharacteristicType` — confirmed no other code
      referenced it; physical damage uses plain `Dmg`/`Res` throughout.
- [x] The `65036` bound artifact was never ported literally (confirmed); the corrected
      `-100` bound is used for the DMG_*_PERCENT/HEAL characteristics, with the artifact
      explicitly noted in a code comment.
- [x] Unit test: `TestNewFighterFromBreed_MatchesBreedTable` (`internal/combat/breed_test.go`)
      asserts HP/AP/MP/INIT/crit/fumble match the table for all 12 breeds.

### Phase B — Wire the fight lifecycle end-to-end ✅ done

- [x] `instantiateFight` (`handlers_fight.go`), called from
      `handleTeamMateSetReadyForPlacement`, calls `deps.Fights.Create(...)` populated
      with real `combat.Team`/`combat.Fighter` data (breed + spells + objects loaded
      fresh from the DB/gamedata via `buildCombatTeam`).
- [x] The `*combat.Fight` is stored on `world.Duel` (`SetFight`/`Fight()`), so subsequent
      in-fight opcodes resolve it via `sessionFight` (`handlers_fight.go`).
- [x] Fight-actor → session broadcast boundary: `combat.Broadcaster` interface
      (`internal/combat/broadcast.go`), concretely implemented by
      `world.RegistryBroadcaster` (`internal/world/combat_broadcaster.go`) so `combat`
      never imports `netio`/`world` directly (no import cycle).
- [x] `Manager.Count()` reflects real fights — verified by
      `TestE2E_FightLifecycleReachesActionPhase`'s `/stats` assertion (`active_fights == 1`
      during a live fight).
- [x] Test: `TestE2E_FightLifecycleReachesActionPhase` (`test/e2e/combat_test.go`) drives
      both coaches through the full setup flow and confirms a live fight exists via the
      admin `/stats` endpoint.

### Phase C — Timeline redesign: the validate-gate mechanic ✅ done

- [x] `internal/combat/timeline.go` redesigned: `StartNextTurn`/`EndCurrentTurn` replace
      the old bare `NextTurn()` — a turn does not end on its own; `Fight` must explicitly
      call `EndCurrentTurn` (via `askForFighterEndTurn`, triggered by either the player's
      end-turn request or the 30s clock) before the next `StartNextTurn` can proceed.
      Simplified vs. the full generic `TimeEvent` two-pass activate/deactivate queue
      described in the opcode doc (no separate object-pool-style event queue was built),
      but the externally-observable behavior — "nothing advances without explicit
      validation" — is preserved exactly; see §8.11 item 1 for the acknowledged gap.
- [x] Phase-end is a single path either way: `askForPresentationEnd`/
      `askForPlacementEnd`/`askForObservationEnd`/`askForFighterEndTurn`
      (`internal/combat/phases.go`, `turns.go`) are called identically whether triggered
      by `handleClockFired` or by every coach reaching ready.
- [x] Concrete clock parameters preserved exactly (`config.Default()`'s
      `combat.*_clock` keys: 20s/30s/10s/30s) and used per-phase, not a single timeout.
- [x] Phase-guard assertions: `startObservation()`/`startAction()` check the previous
      phase and log+abort (don't panic, don't silently advance) on an out-of-order call.
- [x] Mid-fight roster changes: `Timeline.InsertAfter` correctly queues multiple summons
      from the same father together; `RemoveFighter` collapses the turn-order slice and
      clears `currentFighter` if the removed fighter was mid-turn. Implemented as direct
      slice splicing (not a separate timestamp-shift-on-queued-events model, since this
      port doesn't use a generic sorted-event-queue at all — see §8.11 item 1) but with
      equivalent observable ordering behavior.
- [x] Unit tests: `internal/combat/timeline_test.go` — validate-gate behavior
      (`TestTimeline_ValidateGate_TurnDoesNotAutoAdvance`), table-turn wrap
      (`TestTimeline_WrapsIntoNewTableTurn`), dead-fighter skipping, summon insertion
      (single + multiple-from-same-father), mid-round removal.

### Phase D — Fight-phase transition opcodes (8018–8040) ✅ done

- [x] `END_PRESENTATION` (8018), `START_PLACEMENT` (8020), `END_PLACEMENT` (8028),
      `START_OBSERVATION` (8030), `END_OBSERVATION` (8038), `START_ACTION` (8040, new
      constant added) — all implemented in `internal/combat/phases.go`, broadcast at the
      right point in the `askForXEnd`/`startX` chain.
- [x] `MoveToFreePlacementRequestMessage`(8021)/`MOVE_TO_FREE_PLACEMENT`(8022) —
      implemented (`handleMoveToFreePlacement` in `phases.go`), but cell validation is
      simplified to fighter-occupancy only (no real map-boundary data — see §8.11 item 3).
- [x] `TeamMateSetReadyForObservationRequestMessage`(8023)/`TEAM_MATE_SET_READY_FOR_OBSERVATION`(8024)
      and `TeamMateSetReadyForActionRequestMessage`(8031)/`TEAM_MATE_SET_READY_FOR_ACTION`(8032)
      — same per-coach readiness-gate pattern as placement-ready.
- [x] All missing opcode constants (8021/8022/8023/8024/8031/8032/8040 and the entire
      8100-8300 + 4500s range) added to `internal/protocol/opcodes.go`/`names.go`.
- [x] Test: `TestE2E_FightLifecycleReachesActionPhase` walks the full phase chain via the
      wire protocol and confirms every opcode fires in order.

### Phase E — Turn cycle + basic combat actions ✅ done

- [x] `NEW_TABLE_TURN_BEGIN`(8100)/`FIGHTER_TURN_BEGIN`(8104) — broadcast in
      `startNextTurn` (`internal/combat/turns.go`), AP/MP refilled via
      `OnFighterStartTurn`.
- [x] `FighterEndTurnRequestMessage`(8105) → `FIGHTER_TURN_END`(8106) — player-initiated,
      validated against `Timeline.CurrentFighter()`.
- [x] 30s turn-clock timeout auto-ends the turn via the same `askForFighterEndTurn` path
      as the player-initiated end (stale-clock generation guard prevents a late timer
      from firing against an already-ended turn).
- [x] `GiveUpFightRequestMessage`(8151, forfeit) — routes into fight-end
      (`fightend.go`'s `fleeCoach`), also triggered automatically on disconnect
      (`dispatch/disconnect.go`).
- [x] In-fight movement: `FighterActorMovementRequestMessage`(4503) → `FIGHTER_MOVE`(4524),
      re-deriving the path server-side via A* rather than trusting the client's literal
      step list, deducting MP cost.
- [x] `FighterActorDirectionChangeRequestMessage`(4521) → `FIGHTER_CHANGE_DIRECTION`(4522)
      — self-flushing, no 8200 barrier.
- [x] `CloseCombatRequestMessage`(8111) → `CLOSE_COMBAT`(8112) — fixed AP cost + element
      + damage from breed data, range must be exactly 1/adjacent.
- [x] Test: `TestFight_CloseCombatDealsDamageAndEndsFight` (`internal/combat/fight_test.go`)
      — two fighters, full turn cycle, close combat kills the opponent, fight ends,
      asserts HP/opcode sequence with a fixed RNG seed.

### Phase F — Spell/card casting + the action-sequence batching model ✅ done

- [x] `combat/pathfind.go` — A* with short-diagonal cost (1.0 orthogonal, 1.4142
      diagonal), no corner-cutting, MP cost = 1/step. Height/altitude hooks
      (`ArrivalAltitude`) exist on the `CellInfoProvider` interface but are simplified
      (`Fight.ArrivalAltitude` always returns "no change, never blocked") since no real
      `.amw` map-height data is parsed in this pass — see §8.11 item 3.
- [x] Shared validation pipeline (`combat_actions.go`'s `validateCast`/`castCandidate`) —
      implements AP cost / range (incl. the `Range` characteristic extending max range) /
      line-alignment / free-cell checks. Cast-frequency limits, LOS, custom criteria, and
      per-effect target-validity (`ValidButNoEffectOnTarget`) are **not** implemented —
      simplified vs. the full 10-step list, see §8.11 item 4.
- [x] `SPELL_CAST_REQUEST`(8109) → `SPELL_CAST`(8110) — wired end-to-end
      (`handleSpellCastRequest`/`handleSpellCast`).
- [x] `FighterCardUseRequestMessage`(8107) → `FIGHTER_CARD_USE`(8108) — wired end-to-end.
- [x] `FIGHT_ACTION_SEQUENCE_EXECUTE`(8200) flush/barrier model implemented exactly as
      spec'd: `flushActionSequence()` sends the empty commit frame after each action;
      `NEW_TABLE_TURN_BEGIN`/`FIGHTER_TURN_BEGIN`/`FIGHTER_TURN_END`/standalone
      `CHANGE_DIRECTION` correctly skip it (self-flushing).
- [x] Every fight-action opcode carries a correctly-incrementing `uniqueId` via
      `Fight.nextActionID()`; `triggeringActionUniqueId` is threaded through for
      damage/effect broadcasts caused by a cast (`-1` otherwise).

### Phase G — Effect system + damage formulas ✅ done (simplified scope)

- [x] `internal/combat/effects_registry.go` — `EffectKind` + `runningEffectTable` maps
      every `RunningEffectConstants.java` actionID actually needed to an executor kind,
      ported directly from the decompiled constants file (not just the doc's partial
      sketch) — HP loss/gain/leech/debuff, AP/MP use, characteristic gain/loss/buff/
      debuff/leech, push/pull/teleport/exchange-position, root/stabilize/petrified/
      (in)visible, summon, death, card-equipped, automatic-end-turn.
- [x] `internal/combat/effects.go` — the executor (`executeEffects`/`applyRunningEffect`)
      implements the **instantaneous execution path only** (no duration/trigger-bus
      scheduling) — see §8.11 item 2 for exactly what this means is missing (poison
      ticking across turns, timed buffs expiring, state-bundle end-triggers).
- [x] `internal/combat/damage.go`'s `ComputeHPLoss` implements the documented formula
      (flat dmg/res, percent modifiers applied last, rebound) — **hit-location bonus
      (front/side/back) is NOT implemented** (no `FourSidedPartLocalisator` port), see
      §8.11 item 5.
- [x] `RUNNING_EFFECT_ACTION`(8120) — full 34-byte `serializedRunningEffect` blob
      implemented exactly per spec (`buildRunningEffectAction` in `packets.go`).
- [x] `EFFECT_AREA_ACTION`(6200) — packet builder exists
      (`buildEffectAreaAction`), but `BasicEffectArea`/`EffectAreaManager`/
      `checkInAndOut()` (persistent ground zones/traps) are **not implemented** — no
      effect in `runningEffectTable` currently creates one. See §8.11 item 6.
- [x] `randomRound()` implements the documented "round, weighted probabilistically by the
      fractional part" pattern — the exact reference `ValueRounder` behavior was never
      recoverable (open research item, unchanged from before this pass).
- [x] Property tests: `TestCharacteristicBounds_Clamp` covers bound-clamping edge cases;
      damage-formula-specific property tests (0/negative resist, rebound interactions)
      were not added as a separate suite — covered indirectly by
      `TestFight_CloseCombatDealsDamageAndEndsFight`'s fixed-seed assertion. Flagged as a
      thinner-than-ideal test surface, see §8.11 item 7.

### Phase H — Fight end ✅ done

- [x] `checkFightEnd()` ported exactly: **not** auto-called by `killFighter` internally
      as a language-level guarantee, but every call site in this codebase (`applyDamage`,
      `effects.go`'s `EffectDeath`) does call it immediately after, matching the
      required contract.
- [x] `END_FIGHT`(8300) — full branching payload (flee vs. normal, winner/loser lists)
      implemented (`buildEndFight` in `packets.go`). Ladder-strength deltas and
      `PlayerStatisticsReport` blobs are now **populated for real**: a `FightEndHook`
      (`internal/dispatch/fightend_hook.go`) persists each participant's result via
      `CoachService.ApplyFightResult` and returns the updated strength + serialized
      report to embed per-coach (see §8.1). Lost/won card blobs are still empty (no
      card-wagering logic wired into fight-end yet) — see §8.11 item 8.
- [x] `EndFightDoneMessage`(4321) — client ack after closing the results screen; the
      Fight actor's `Run()` loop only returns once every participating coach has acked
      (or, as a fallback, disconnected — a disconnect after end-fight doesn't currently
      auto-ack, see §8.11 item 9), which is when `Manager` truly forgets the fight.
- [x] Forfeit path (`GiveUpFightRequestMessage`) routes here with `flee=1`, including the
      automatic-forfeit-on-disconnect path in `dispatch/disconnect.go`.
- [x] `Manager`'s registered fight is removed once `Run()` returns (mirrors legacy
      `destroyFight` being the fight's own last act).
- [x] End-to-end fight test: `TestFight_CloseCombatDealsDamageAndEndsFight`
      (`internal/combat/fight_test.go`) — full fight to completion with a fixed RNG seed,
      asserting final HP/death/END_FIGHT/EndFightDone-ack teardown.

### Phase I — Hardening ✅ done

- [x] `go test -race` clean across the **entire** test suite (`internal/combat`,
      `internal/dispatch`, `internal/world`, `test/e2e`, everything else) — verified
      repeatedly during this rollout, including the full e2e suite (~110s under `-race`).
- [x] Load test: `TestE2E_ConcurrentFightsLoad` (`test/e2e/load_test.go`) drives 8
      concurrent fights (16 connections) through login → matchmaking → fight setup →
      forfeit → END_FIGHT simultaneously against one server instance. Not a dedicated
      `pprof`-profiled perf benchmark (flagged as a lighter-weight version of what was
      asked for, see §8.11 item 10) but does exercise real concurrency correctness.
- [x] Security pass: every new packet reader in `handlers_fight_combat.go` uses the
      shared bounds-checked `protocol.Reader` (short reads set a sticky error, checked
      via `payload.Err()` before any command is forwarded to the Fight actor) — same
      standard as the rest of `internal/dispatch`.
- [x] `combat/effect` doesn't exist as a separate package in the final implementation
      (effects live directly in `internal/combat` — a design deviation from the original
      plan's `combat/effect` sub-package, made to avoid an import cycle with `Fight`
      itself needing effect execution as a core loop step, not a pluggable one) — full
      unit coverage of the *implemented* effect kinds exists via `fight_test.go`'s
      close-combat/damage test, but not one dedicated per-effect-kind test file. Flagged
      as thinner test coverage than ideal, see §8.11 item 7.
- [ ] `opcodes/07-fight-lifecycle.md` and `opcodes/08-fight-combat-engine.md` still need
      a pass to flip their per-opcode "not implemented" status markers to "implemented"
      now that this rollout is done — **not done as part of this pass** (this roadmap
      document was updated instead; the opcode reference docs are next, tracked as a
      explicit follow-up so they don't silently drift out of sync).

---

## 8.10 Explicit non-goals (unchanged from `07-roadmap.md`)

- TLS/encryption on the wire (client can't speak it).
- Multi-channel chat (`CHANNEL_*` opcodes).
- AI/PvE opponents.
- Horizontal scaling / multi-instance world state (single-process monolith is the
  explicit target).

## 8.11 Combat engine follow-ups (deferred/simplified during Phases A–I)

Everything in §8.9 shipped and is exercised by passing tests (`internal/combat`'s unit
suite + `test/e2e`'s wire-protocol suite, all clean under `go test -race`). The items
below are known, explicitly-acknowledged simplifications made to land a complete,
correct-at-the-observable-behavior-level engine without porting every last mechanic from
the reference implementation. None of them block normal 1v1 duels with close
combat/spells/cards; they matter for specific mechanics (traps, DoTs, precise
positioning) that weren't exercised by this pass's test scenarios.

1. **No generic `TimeEvent` priority-queue engine.** `Timeline` (Phase C) reproduces the
   validate-gate *behavior* (nothing auto-advances without explicit validation) using
   direct method calls (`StartNextTurn`/`EndCurrentTurn`) rather than porting
   `BasicTimeline`'s actual sorted-list-of-events-with-activate/deactivate-passes
   architecture. This is indistinguishable from the outside for everything this engine
   currently does (phase transitions, turn cycling, summon insertion), but would need
   revisiting if a future effect needs to schedule itself onto the timeline at an
   arbitrary future tick (e.g. "this buff's tick happens 2 turns from now regardless of
   whose turn it is") — the current design has no generic scheduling primitive for that.
2. ~~Effects execute instantly only — no duration/trigger-bus~~ **PARTIALLY RESOLVED
   (Phase J)**: a minimal table-turn-granularity duration primitive
   (`Fighter.ActiveEffects`, `duration.go`) now exists, hooked into the table-turn
   boundary via `Fight.tickActiveEffects` (called from `startNextTurn` in `turns.go`
   whenever `Timeline.StartNextTurn` reports a new table-turn). `CharacPoison` (actionID
   61) now actually re-ticks every table-turn when `EffectDef.Duration` specifies a real
   count (re-rolling its damage each time), and `CharacBuff`/`CharacDebuff` now
   auto-revert their exact applied `Max` delta at the documented expiry boundary — see
   `docs/08-java-parity-roadmap.md` §8.12 Phase J for the full writeup and test list.
   **Now resolved for the real data's triggers** (HV1 reactive trigger-bus, `triggerbus.go`):
   `TriggersBefore`/`TriggersAfter` effects are deferred and fire on the trigger ids the
   shipping data actually uses (2, 52, 54, 55, 56, 64, 1001). The one before-HP-loss effect
   in the data — **Sacrieur's Sacrifice** (spell 135) — is fully wired (position-swap +
   damage-redirect onto the Sacrieur, via `applySacrificeRedirect`), and **caster-death
   effect removal** (`AbstractFight.onFighterDeath`) is implemented (`removeEffectsCastBy`
   in `fightend.go`). **Still not resolved**: the generic `EndTriggers` set and the full
   ~180-id reactive pub-sub for trigger ids no shipping spell references, plus state-bundle
   expansion (`ApplyState`/`State`) — these stay inert-but-graceful (stored, never fires),
   with zero observable impact on the real spell catalog.
3. ~~No real map data — movement/placement/pathfinding validation is occupancy-only~~
   **RESOLVED (Phase K)**: `.amw`/`elements.ade` fully reverse-engineered (via `javap`
   bytecode disassembly of the real `core.jar`, cross-verified against the decompiled
   source and empirically confirmed byte-exact against every real map file for this
   project's fight map) and parsed by `internal/gamedata/parser/{alea_reader,elements_ade,
   amw,map_altitude}.go` + `internal/gamedata/map.go`'s `Map`/`MapStore`. `Fight.IsWalkable`/
   `Fight.ArrivalAltitude` (`turns.go`) now query real per-cell walkability/altitude when a
   `*gamedata.Map` is attached via `Fight.SetMapData` (falls back to the old always-true
   stub if unattached, so tests/dev setups without the game's data files still work).
   `instantiateFight`/`resolveCoachStartSpots` (`handlers_fight.go`) now resolve each
   coach's fight-start cell from the map's real `FightStartCoachPointElement` data instead
   of a hardcoded constant (the hardcoded `(16,11)`/`(1,7)` cells turned out to be an exact
   coordinate match for the real data — only their placeholder `Z=-3` was wrong, now
   resolved to the nearest real walkable altitude). Full reverse-engineering trail +
   complete byte-layout reference: `docs/04-game-data-format.md` §4.9. **Not yet wired**:
   the real free-placement *zone* restriction (a proper subset of walkable cells, not
   "any walkable cell") — `handleMoveToFreePlacement` only checks walkability+occupancy,
   which is strictly more correct than before this phase but not byte-identical to the
   reference's zone-boundary check; would need a distinct piece of per-map placement-zone
   data not yet identified. Also as a side-effect of researching this phase's effect
   dispatch code for the map-data write-up: found and fixed a real, pre-existing
   `EffectTeleport` bug (`effects.go`) — it was resolving a target-fighter via the normal
   AoE-target machinery and then no-op'ing (`target.Position = target.Position`) instead
   of moving the caster to the target cell, per the decompiled `Teleport.java`'s
   `useCaster()=true/useTarget()=false/useTargetCell()=true` contract; fixed with a new
   `applyTeleport()` + regression test (`TestEffectTeleport_MovesCasterToTargetCell`).
4. ~~Spell/card cast validation is a simplified subset of the documented 10-step list~~
   **RESOLVED (Phase L)**: `validateCast` (`combat_actions.go`) now also enforces
   cast-frequency limits (`Fighter.CastHistory`, a byte-for-byte port of
   `SpellCastHistory.java` — `spell_cast_history.go`), a line-of-sight check
   (`hasLineOfSight`/`line_of_sight.go`, using Phase K's real map data with a **bit-exact
   port** of the reference's exact 3D DDA algorithm, upgraded from an earlier Bresenham
   approximation — see the LOS bit-exactness follow-up below), and custom
   cast criteria (`evaluateCastCriteria`/`criteria.go`, a small named-criterion registry
   covering the 4 tokens actually used in this project's real `spells.dat`). Per-effect
   target-validity (`ValidButNoEffectOnTarget`) turned out to already be implemented
   (`effects.go`'s point-shaped-no-target early return), just not previously
   cross-referenced against this item's checklist. See §8.12 Phase L for the full
   writeup + naming-quirk discovery (`CastFrequencyMaxPerPlayer` is actually enforced as
   a per-TARGET cap, not per-player, in the reference itself).
5. ~~No hit-location (front/side/back) damage bonus~~ **RESOLVED**: implemented in
   `hitLocationBonus()` (`damage.go`), ported from the decompiled
   `FourSidedPartLocalisator.getMainPartInSightFromPosition()`'s dot-product/threshold
   geometry (>=0.5 back, >=-0.5 side, else front) — ties to the target's actual
   `Direction8` facing rather than the simplified stand-in used before. Confirmed against
   the game manual's own stated bonuses (back +30%, side +15%). **Also fixed in the same
   pass**: cross-checking the decompiled `HPLoss.computeValue()` revealed physical/
   close-combat damage must NOT go through the `Dmg`/`Res`/`DmgInPercent`/`ResInPercent`
   characteristics at all (the element `switch` has no `PHYSICAL` case) — a real bug in
   the original implementation, now fixed with a regression test
   (`TestComputeHPLoss_PhysicalIgnoresDmgResCharacteristics`).
6. ~~No persistent ground-effect areas (traps/glyphs)~~ **RESOLVED (Phase M)**:
   `BasicEffectArea`/`EffectAreaManager.checkInAndOut()` ported (`internal/combat/
   effectarea.go`), actionID 66 (`SET_EFFECT_AREA`) added to `runningEffectTable`, and
   `EFFECT_AREA_ACTION`(6200) now has a real caller. As a direct side-effect of this
   work, `staticEffects.dat` (previously believed unrecoverable) turned out to be fully
   parseable — see `docs/04-game-data-format.md` §4.5's corrected status and §8.12
   Phase M for the full writeup.
7. **Effect-kind test coverage is integration-level, not per-effect-kind.** The damage/
   death/turn-cycle path is well-covered end-to-end
   (`TestFight_CloseCombatDealsDamageAndEndsFight`), but there's no dedicated unit test
   exercising each `EffectKind` in `effects_registry.go` individually (push/pull fall
   damage, characteristic leech, property toggles, summon, etc.) in isolation. Property-
   based tests for damage-formula edge cases (0 resist, negative resist stacking with
   rebound) were also not added as their own suite.
8. **`END_FIGHT`'s card-wagering payload is always empty.** `buildEndFight` sends empty
   lost/won card blobs and zero ladder-strength/stats-report data. **Re-investigated in
   Phase O**: this is NOT simply unwired — there is genuinely no card-wager-selection
   mechanism anywhere in this codebase or the reference client to wire up in the first
   place (`CREATE_FIGHT`'s bet-card-count is hardcoded to 0 server-side; only a flat
   gold-style `Bet int32` amount ever flows through matchmaking/duel/fight). Building
   this properly would require inventing a new selection flow (opcode/UI + persistence),
   not porting existing behavior — see §8.12 Phase O's item 8 for the full breakdown of
   what a real implementation would need. Left unimplemented, now accurately scoped as
   "needs new design" rather than "needs wiring".
9. ~~A disconnect *after* `END_FIGHT` has already been sent doesn't auto-ack
   `EndFightDoneMessage`~~ **RESOLVED (Phase O)**: `dispatch/disconnect.go` now
   synthesizes the missing ack automatically. A related bug (duel removed from
   `DuelManager` after only one coach's ack, breaking this very fix for the other
   coach) was found and fixed in the same pass — see §8.12 Phase O.
10. ~~The concurrent-fights load test is a correctness check, not a profiled
    benchmark~~ **RESOLVED (Phase O)**: `cmd/loadtest` now exists, verified stable at
    300 fights/concurrency 50 with a real pprof endpoint reachable during a run — see
    §8.12 Phase O.
11. ~~`opcodes/07-fight-lifecycle.md` and `opcodes/08-fight-combat-engine.md` still say
    "not implemented" for opcodes that now are~~ **RESOLVED (Phase O)**: both files'
    Status markers refreshed, zero remaining stale "not implemented" occurrences — see
    §8.12 Phase O.
12. ~~Genuine open research items~~ **`randomRound()` RESOLVED**: the actual decompiled
    `ValueRounder.randomRound(float)` was located
    (`baseImpl/common/clientAndServer/utils/ValueRounder.java`) and matches this port's
    existing implementation exactly (floor + probabilistically round up weighted by the
    fractional part, via a Mersenne Twister PRNG in the reference -- Go's `math/rand` is
    an intentional, harmless substitution). No code change was needed, just confirmation.
    `FighterPropertymanager`'s bitmask was also resolved: the reference
    `FighterPropertyType` enum has exactly the four properties already modeled
    (`INVISIBLE=1, STABILIZED=2, PETRIFIED=3, ROOTED=4`) — no fifth/hidden property
    exists. **Still open**: the full `RunningEffectConstants` trigger-ID table (only the
    action-ID-to-`EffectKind` mapping in `runningEffectTable` was ported; the separate
    reactive trigger-bus IDs referenced by `TriggersBefore`/`TriggersAfter`/`EndTriggers`
    were not cross-checked against the equivalent trigger-int constants scattered across
    each `RunningEffect` subclass's `setTriggersToExecute()` -- resolvable but not done
    in this pass, tracked under item 2 above since it's moot until the duration/
    trigger-bus itself is built).
13. **New mechanics identified from the official game manual
    (`Game_Guide_DofusArena_v2.pdf`, cross-checked against the decompiled client) — now
    implemented**:
    - **Tackle/Evasion** (manual §5.0.4): a fighter adjacent to a living enemy must
      "evade" (67% base success, all adjacent enemies independently) before being
      allowed to move; failing ends their turn immediately. Being adjacent to 4+ enemies
      makes movement impossible outright, no roll. No equivalent exists in the
      decompiled server-side reference (the client's own `TackleAction` is a pure
      animation trigger) — this is genuinely greenfield, implemented in
      `internal/combat/tackle.go` per the manual's exact wording, wired into
      `handleFighterMove`.
    - **Special battlefield cells** (manual §5.0.4): Trap (-10 HP), Enthusiasm (+10%
      damage dealt), Shield (+10% resistance), Eagle eye (+1 range), Panacea (+10%
      heal bonus), Motivation (+1 AP), Healing heart (+5 HP, injured-only), and Killer
      (instant death) — triggered exactly once, only when a fighter *starts* their
      activation phase standing on the cell, with non-instant bonuses lasting exactly
      one full turn. Implemented in `internal/combat/specialcells.go`, wired into
      `startNextTurn`/`askForFighterEndTurn`. **Populating actual per-map cell layouts
      is a separate follow-up** (no `.amw`/map-authored cell data is parsed yet, see
      item 3) — the mechanic itself is complete and tested
      (`SetSpecialCell` is exposed for whenever that data becomes available).
14. **A real characteristic-semantics bug was found and fixed while implementing item
    13**: cross-checking the decompiled `CharacGain`/`CharacBuff`/`CharacLoss`/
    `CharacDebuff`/`CharacLeech` `RunningEffect` subclasses revealed these are NOT
    interchangeable as this port had originally treated them — `CharacGain`/`CharacLoss`
    only ever touch a characteristic's **current value** (clamped to its existing max),
    while `CharacBuff`/`CharacDebuff` change the **max bound itself** (a real, lasting
    stat change), and `CharacLeech` steals `min(requested, target's current value)` from
    the target's max while adding that same amount to the caster's current value. This
    matters concretely for AP/MP-capped characteristics: a "+1 AP this turn" cell/effect
    that only raised current value (a plain `Add`) would be silently clamped right back
    down by the existing max, having no effect at all -- exactly the bug the
    `Motivation` special-cell test (`TestSpecialCell_MotivationGrantsAndRevertsAPBonus`)
    caught. Fixed via a new `Characteristic.AddMax()` method and split
    `EffectCharacGain`/`EffectCharacBuff`/`EffectCharacLoss`/`EffectCharacDebuff`/
    `EffectCharacLeech` handling in `effects.go`, each mirroring its decompiled
    counterpart's exact `execute()`/`unapply()` shape, with dedicated regression tests
    in `characteristic_semantics_test.go`.
15. **A real wire-protocol bug was found and fixed**: `Direction8`'s Go enum values
    didn't match `framework.kernel.core.maths.Direction8`'s decompiled ordinals
    (`EAST=0, SOUTH_EAST=1, SOUTH=2, SOUTH_WEST=3, WEST=4, NORTH_WEST=5, NORTH=6,
    NORTH_EAST=7`) — since `FIGHTER_CHANGE_DIRECTION`(4522) and
    `FighterActorDirectionChangeRequestMessage`(4521) both send/receive this as a raw
    byte the client resolves via `Direction8.getDirectionFromIndex()`, the old values
    would have desynced facing animations between server and client even though no
    other wire field was affected. Fixed with a regression test
    (`TestDirection8_WireValuesMatchClient`); the internal grid-stepping math used for
    pathfinding/push-pull (a different, intentionally simplified model, see item 3)
    was deliberately left alone since it never touches the wire.

---

## 8.12 What's next — prioritized follow-up plan

The combat engine is feature-complete for standard 1v1/2v2 duels using close combat,
spells, and cards without traps/DoTs/precise-positioning edge cases. Everything below is
drawn directly from the numbered gaps in §8.11 — re-read the relevant item there before
starting any of these, it has the full context (why it's simplified, what exactly is
missing, where the code lives).

Ordered by recommended sequence — later items build on or are motivated by earlier ones,
but none are hard-blocked the way Phases A–I were, so feel free to reorder based on
product priority (e.g. if traps matter more than DoTs to you, do Phase K before J).

### Phase J — Duration/trigger-bus effect scheduling (§8.11 item 2) — PARTIALLY DONE

**The single biggest remaining fidelity gap.** Without this, poison/DoT spells only ever
tick once, buffs/debuffs never expire, and no reactive ("on next hit", "on death",
spell-rebound-style) effect can exist.

- [x] Design a minimal duration-tracking primitive on `Fighter`: `Fighter.ActiveEffects
      []ActiveEffect` (`duration.go`), each entry carrying `Kind` (poison-tick vs.
      charac-buff/debuff), the resolved `Charc`/`Elem`/`Delta`/`Params` needed to re-execute
      or revert, and `RemainingTableTurns`/`Infinite` — the full generic `TimeEvent` queue
      (§8.11 item 1) was deliberately NOT ported, per that item's own explicit allowance for
      a simpler table-turn-granularity approach as long as observable behavior matches.
- [x] Hooked duration-effect ticking/expiry into the table-turn boundary:
      `Fight.tickActiveEffects` is called from `startNextTurn` (`turns.go`) exactly when
      `Timeline.StartNextTurn` reports `isNewTableTurn=true` — mirrors
      `EffectDef.Duration`'s `[tableTurns, turns]` table-turn component (`Duration[0]`)
      exactly; the `turns` component (`Duration[1]`) is preserved on `ActiveEffect` for
      completeness but not currently decremented separately, since this engine's
      `Timeline` has no per-fighter turn-within-table-turn counter distinct from the
      table-turn itself (see `timeline.go`'s doc comment on `m_currentTurn` not being
      ported) to hang a second counter off of.
- [x] Wired `CharacPoison` (actionID 61, `EffectCharacPoison` in `effects.go`) to actually
      re-tick every table-turn (re-rolling its own damage each time, not repeating the
      first roll) when `EffectDef.Duration` specifies a real table-turn count, instead of
      firing once unconditionally. A zero/absent `Duration` (still the common case for most
      of this project's spell/card data) preserves the exact old one-shot-only behavior —
      confirmed via a dedicated regression test
      (`TestEffectCharacPoison_ZeroDurationDoesNotTrack`).
- [x] `EffectCharacBuff`/`EffectCharacDebuff` also gained duration tracking (a lasting stat
      change auto-reverts its exact signed `Max` delta at expiry via the same
      `ActiveEffect` primitive) — not originally scoped in this item's checklist, but
      immediately obvious as the same mechanism once `CharacPoison`'s tracking existed, and
      matches the reference's own `CharacBuff.unapply()`/`TurnBasedTimeInterval` duration
      contract exactly (including the "don't double-charge an already-spent resource on
      revert" nuance already documented for the special-cells mechanic in
      `specialcells.go`).
- [x] `killFighter` (`fightend.go`) now clears a dead fighter's `ActiveEffects` entirely —
      a corpse's poison shouldn't keep ticking, and any buff it had is moot.
- [x] Tests (`duration_test.go`): a DoT tick applies damage on 2+ subsequent table-turns
      (`TestEffectCharacPoison_TicksAgainOnNextTableTurn`), continues indefinitely for an
      infinite (`Duration[0]>=63`) poison
      (`TestEffectCharacPoison_InfiniteDurationNeverStopsTicking`), a timed buff/debuff
      reverts exactly at its documented expiry table-turn — not before, not after
      (`TestEffectCharacBuff_RevertsExactlyAtExpiryTableTurn`/
      `TestEffectCharacDebuff_RevertsExactlyAtExpiryTableTurn`), a spent resource isn't
      double-charged on revert
      (`TestEffectCharacBuff_RevertDoesNotDoubleChargeSpentResource`), dead fighters don't
      keep ticking (`TestTickActiveEffects_SkipsDeadFighters`,
      `TestClearActiveEffectsOnDeath_RemovesAllTrackedEffects`), and a full integration
      test drives the real `Timeline`/`startNextTurn` machinery end-to-end rather than
      calling `tickActiveEffects` directly
      (`TestFight_PoisonTicksAcrossRealTableTurnBoundary`).
- [ ] **Not done, deliberately out of scope for this pass**: the full reactive trigger-bus
      (`TriggersBefore`/`TriggersAfter`/`EndTriggers` — "on next hit", "on death",
      spell-rebound-style conditional effects) and state-bundle expansion (`ApplyState`/
      `State`, a named bundle of sub-effects with its own end-trigger set). Extracting the
      real trigger-ID table from `RunningEffectConstants`'s `setTriggersToExecute()` calls
      (cross-referencing every `RunningEffect` subclass, not just the ones already ported)
      remains open research, still tracked under item 12's "still open" note — this pass
      only covered the two duration-tracked mechanics (`CharacPoison`/`CharacBuff`/
      `CharacDebuff`) that don't actually need the trigger-bus itself, since they resolve
      on a fixed table-turn schedule rather than in response to an arbitrary game event.
      A future pass wanting real reactive effects (damage reflection beyond `DmgRebound`,
      "on next hit" buffs, conditional state removal) still needs this trigger-ID
      extraction done first.

### Phase K — Real map data (`.amw` parser) (§8.11 item 3) — DONE

**Prerequisite for fixing items 4 and 6 properly** (LOS, real free-placement bounds, and
ground-effect areas all need real cell/height data to mean anything).

- [x] Locate and reverse-engineer the `.amw` binary map format. Done via `javap -c -p`
      bytecode disassembly of the real `core.jar` (a legally-owned copy exists at
      `E:\Ankama\DofusArena2-06\game\core.jar` on this machine), cross-checked against the
      decompiled `.java` source, and empirically verified byte-exact (full-file-length
      consumption, zero leftover bytes) against every real map file for this project's
      fight map. One genuine decompiled-source ambiguity was found and resolved this way
      (`BonusElement` inheriting `GraphicalElement`'s 30-byte state payload, invisible from
      `WorldElementManager`'s switch statement alone). Full byte-layout reference + the
      entire verification trail: `docs/04-game-data-format.md` §4.9 (written specifically
      to double as future map-editor documentation, per explicit request).
- [x] Build `internal/gamedata`-style parser (`parser/alea_reader.go`, `parser/elements_ade.go`,
      `parser/amw.go`, `parser/map_altitude.go`) + a `Map`/`MapStore` type
      (`internal/gamedata/map.go`, wired into `gamedata.Store.Maps`) exposing
      walkability/altitude per cell for the map actually used by 1v1/2v2 duels (`fightMapID
      = 2`). `combatPlacementSpotA`/`B` (`handlers_fight.go`) replaced with
      `resolveCoachStartSpots()`, which reads the map's real `FightStartCoachPointElement`
      cells (falling back to the old hardcoded constants, now named
      `fallbackCoachSpotA`/`B`, only if map data fails to load).
- [x] Wire the parsed map into `Fight.IsWalkable`/`ArrivalAltitude` (`turns.go`) via a new
      `Fight.SetMapData(*gamedata.Map)` method, called from `instantiateFight`
      (`handlers_fight.go`) right after fight creation. Falls back to the old
      always-true/never-blocked behavior if unattached (nil), so any test/dev setup that
      doesn't wire real map data keeps working unchanged. Also wired into
      `handleMoveToFreePlacement` (`phases.go`), which now additionally rejects a
      free-placement move onto a non-walkable cell.
- [x] **Done**: re-validated `combat/pathfind.go`'s A* against a real map with actual
      height-blocked steps. An exhaustive scan of fightMapID=2 (the default/canonical fight
      map) confirmed it genuinely has **zero** adjacent walkable-cell pairs whose altitude
      delta exceeds the ±4 ascend/descend limit — so no test against map 2 alone could ever
      exercise this path, vacuous-pass or not. Scanning the full random-selection pool
      (`MapStore.FightMapIDs()`) turned up real height-blocked steps on several other maps
      duels actually get assigned to (4, 5, 6, 7, 8, 9, 12, 13). Added
      `internal/combat/pathfind_realdata_heightblock_test.go`
      (`TestFindPath_RealMapRejectsGenuineHeightBlockedStep`), pinned to a concrete map-4
      cliff ((9,0) standing z=5 → its single-axis neighbor (9,1) standing z=-1, a 6-level
      drop), proving `ValidateClientPath` rejects the illegal direct step and `FindPath`
      never emits it while routing (nil — no legal detour — is also an accepted outcome).
      Corner-cutting turned out to be a non-issue independent of map data: fight movement
      only ever takes single-axis steps (`fightMoveDirections`), so the two-axis diagonal
      move a corner-cut bug requires is structurally impossible in this engine.
- [ ] **Not done, deliberately deferred**: the real free-placement *zone* restriction
      (only a proper subset of walkable cells are legal placement spots at fight start, not
      "any walkable cell") — `FightStartCoachPointElement` only marks the single per-coach
      anchor cell, not a placement zone boundary; that would need a distinct piece of
      per-map data not yet identified in `elements.ade`/`.amw` (possibly one of the other
      unused custom element-kind slots, or a separate file format entirely — genuinely open
      research if this matters for full fidelity later).

### Phase L — Complete spell/card cast validation (§8.11 item 4) — DONE

Builds on Phase K for the LOS piece; the cast-frequency and criteria pieces don't need
map data and could be done independently/first if you want a smaller, self-contained
task.

- [x] Ported `SpellCastHistory` (`MinCastInterval`/`CastMaxPerTurn`/`CastMaxPerTarget`)
      byte-for-byte from the decompiled `dofusarena/common/game/spell/
      SpellCastHistory.java` onto a new `Fighter.CastHistory SpellCastHistory` field
      (`spell_cast_history.go`), wired into `validateCast` (`combat_actions.go`) and reset
      each fighter's own turn via `OnFighterStartTurn` (`timeline.go`, mirroring
      `SpellCastHistory.onNewTurn()`'s exact per-turn-not-per-fight scope). **Real naming
      quirk found and documented**: the `.dat` loader's own local variable name
      `spellCastFrequencyMaxPerPlayer` (and this project's matching
      `SpellTemplate.CastFrequencyMaxPerPlayer` field) is actually wired into
      `AbstractSpell`'s constructor as `castMaxPerTarget` and enforced by
      `SpellCastHistory` as a genuine PER-TARGET cap, not a per-player-overall cap —
      preserved as-is (source-level curiosity), just used with its real enforced
      semantics in `validateCast`.
- [x] Line-of-sight check (`CastTestLineOfSight`, `line_of_sight.go`'s `hasLineOfSight`) —
      uses Phase K's real map data when attached to a fight, falling back to permissive
      (always-visible) otherwise. **UPDATE — now a bit-exact port**, not an approximation:
      originally this used a standard 2D Bresenham line walk checking each intermediate
      cell for "a non-walkable surface with real height" as a documented stand-in for
      "contains a wall/obstacle" (this project's established policy, `docs/
      04-game-data-format.md` §4.9.1, of preferring a conservative documented
      approximation over an unverifiable guess when no test vectors exist). That
      changed: the elements.ade parser was ALREADY extracting the reference's real
      per-direction `LineOfSight1/3/5/7/Top/Bottom` flags (`elements_ade.go`'s
      `SpatialDataProperties`) — they were just being silently dropped before reaching
      the combat layer (`ResolveCellSurfaces` never copied them into `ResolvedSurface`).
      Threading them through, decoding the reference's `LineOfSightUtils.getCellsInputs`/
      `WorldCell.isLineOfSightValid`/`isLineOfSightEndValid` (including working around a
      JD-Core decompiler artifact that lost every `break` in `isLineOfSightValid`'s
      direction switch — the real 1:1 mapping was recovered from `Direction8`'s actual
      enum ordinals, SOUTH_EAST=1/SOUTH_WEST=3/NORTH_WEST=5/NORTH_EAST=7/TOP=8/BOTTOM=9),
      and porting the real 3-axis DDA walk (`gamedata.LineOfSightValidAt`/
      `LineOfSightEndValidAt`, `combat.generateLOSCellInputs`) turned this into a genuine
      bit-exact reproduction. Confirmed to have real, exercised impact: scanning the real
      `elements.ade` found 1 of 296 solid element states with genuinely non-uniform LOS
      flags — a *walkable* raised platform (height 3) whose Top/Bottom flags are blocked
      while all 4 edges are open (blocks a vertical/altitude-crossing sightline through
      its body, blocks nothing horizontally) — a case the old approximation could never
      have caught (it only considered non-walkable cells, and never had a Z/altitude
      dimension at all).
- [x] Custom cast criteria (`criteria.go`'s `evaluateCastCriteria`) — a small named-
      criterion registry (not a full expression-language compiler), sufficient because a
      one-off inspection of this project's real `spells.dat` confirmed only 4 distinct
      criterion tokens are ever used in practice: `canSummon`, `canCastWhenCarrying`,
      `cantCastWhenCarrying`, `cantCastWhenCarried` (and `;`-joined combinations of the
      carry ones) — ported from the decompiled `CriteriaCompiler`/`CanSummonCriterion`/
      `CanCastWhenCarryCriterion`/`CantCastWhenCarriedCriterion.java`, including the
      reference's own permissive-on-unrecognized-token behavior (logs, doesn't reject).
- [x] Per-effect target-validity (`ValidButNoEffectOnTarget`) — already implemented
      before this phase (`effects.go`'s point-shaped-effect-with-no-target early return),
      confirmed to satisfy step 10's contract, no changes needed.
- [x] Tests: `spell_cast_history_test.go` (8 tests: `CastMaxPerTurn` rejection + reset on
      new turn, `MinCastInterval` too-soon rejection + the `==63` "never again" special
      case + not-applied-on-first-cast, `CastMaxPerTarget` independent-per-target tracking
      + skipped-when-no-target, all-zero-limits-unconstrained), `criteria_test.go` (11
      tests covering every criterion token + combos + case/whitespace tolerance),
      `line_of_sight_test.go`/`line_of_sight_realdata_test.go` (bit-exact DDA correctness,
      direction-sensitive-blocking end-to-end proof, + real-map-data integration; plus
      `internal/gamedata/line_of_sight_test.go` for the per-direction validity/end-
      validity checks themselves), `combat_actions_test.go` (6 integration tests
      exercising the full `validateCast` pipeline together, confirming cards are
      correctly exempted from spell-only checks).

### Phase M — Persistent ground-effect areas / traps (§8.11 item 6) — DONE

Depends on Phase K for meaningful placement, but the mechanic itself (trigger-on-
enter/exit, `checkInAndOut` algorithm) can be built and unit-tested independently first.

- [x] Ported `BasicEffectArea`/`EffectAreaManager.checkInAndOut()` (`internal/combat/
      effectarea.go`) — direct port confirmed via the decompiled
      `baseImpl/common/clientAndServer/game/effectArea/{BasicEffectArea,
      EffectAreaManager}.java`. Scoped to exactly what this project's real
      `staticEffects.dat` data uses (`ApplicationCondition=0` always-appliable, no
      activation delay on either real trap) — the richer `ONE_TIME_FOR_*` restriction
      cases and delayed-activation-via-timeline path are NOT implemented since no real
      data exercises them (documented in `effectarea.go`'s top comment for future
      extension if new game data ever needs them).
- [x] **Bonus discovery while implementing this phase**: `docs/04-game-data-format.md`
      §4.5's claim that `staticEffects.dat` was unrecoverable (`StaticEffectLoader`'s body
      supposedly "entirely commented out") was **wrong** — a fresh decompile of the real
      `core.jar` produced a complete, substantial `read()` method, confirmed genuine via
      `javap -c -p` bytecode disassembly and empirically verified byte-exact (1,868/1,868
      bytes consumed) against the real file. Implemented
      `internal/gamedata/parser/staticeffects.go` + `gamedata.StaticEffectAreaTemplate` +
      `Store.StaticEffectAreas` repository — the real data has exactly 10 areas (8
      `SPECIAL` matching `specialcells.go`'s 8 cell types by count, 2 real `TRAP` entries)
      plus 16 attached effects. See `docs/04-game-data-format.md` §4.5 for the full
      corrected format reference.
- [x] Added actionID 66 (`SET_EFFECT_AREA`, new `EffectSetArea` kind) to
      `runningEffectTable` (`effects_registry.go`)/`effects.go`'s dispatch
      (`applySetEffectArea`, mirroring `SetEffectArea.java`'s `useCaster()=false/
      useTarget()=false/useTargetCell()=true` contract exactly — bypasses normal
      target-fighter resolution, same pattern as `EffectTeleport`), looks up the
      referenced template via `Fight.data.StaticEffectAreas`, and instantiates a live
      `EffectArea` registered with a new per-`Fight` `EffectAreaManager`
      (`Fight.effectAreas`, nil until first used).
- [x] Wired `checkInAndOut` into `handleFighterMove` (`turns.go`), called once **per
      resolved path step** (not just start→final-destination) — a fighter merely passing
      through a trap mid-path must still trigger it, matching the reference's own
      per-step semantics exactly.
- [x] Wired `buildEffectAreaAction`(6200) as the broadcast for both `apply`(enter) and
      `unapply`(exit) triggers (`applyEffectArea`/`unapplyEffectArea` in
      `effectarea.go`) — the packet builder already existed pre-Phase-M with zero
      callers; now has real ones.
- [x] Tests (`effectarea_test.go`, 9 tests): placing an area at the cast target cell,
      unknown-template-id is a no-op, entering triggers application (damage applied),
      staying inside an area across a move doesn't re-trigger, exiting triggers
      unapplication (broadcast confirmed with `apply=0`), a single-use trap
      (`maxExecutionCount=1`, matching real trap id=1's data) removes itself after
      triggering once, an unlimited-execution trap (`maxExecutionCount=63`, matching real
      trap id=2's data) survives repeated enter/exit cycles, `checkInAndOut` with no
      areas registered is a safe no-op, and the exact `hasUnlimitedExecutions()` boundary
      condition (`<63 && >=0` mirrored precisely, including the `-1` edge case).

### Phase N — Effect-kind test coverage + damage-formula property tests (§8.11 item 7)

Lower-risk, high-value cleanup — no design work, just filling in test gaps. Good
"warm-up" task if picking this project up cold.

- [ ] One test per `EffectKind` in `effects_registry.go` (push/pull fall damage,
      characteristic leech edge cases, property toggles, summon insertion +
      turn-order placement, teleport/exchange-position).
- [ ] Property-based tests for `ComputeHPLoss`: zero resist, negative resist (i.e. a
      buffed target takes MORE damage), rebound interacting with 0/negative final damage,
      hit-location bonus combined with rebound.

### Phase O — Small, independent fixes (any order, all low-effort)

- [~] **§8.11 item 8**: **RESCOPED, not wired this pass (genuine design gap found, not a
      wiring task)**. The original checklist wording ("move actual cards between
      winner/loser per the existing bet-selection flow") assumed a card-selection flow
      already exists — it doesn't, anywhere in this codebase or the reference client.
      Confirmed by re-reading the decompiled `FightCreationMessage.java`/
      `EndFightMessage.java`/`BetCoachCard.java`: `CREATE_FIGHT`'s wire format has a
      `betCardCount`/`referenceCardId` list field, but this project's own
      `buildCreateFight` (`internal/dispatch/packets_fight.go:58`) hardcodes it to 0 —
      only a flat gold-style `Bet int32` amount ever flows through matchmaking→duel→
      fight (`world.Duel.Bet`). There is no opcode/UI flow anywhere (client or Go server)
      that lets a player select which SPECIFIC cards to wager. `buildEndFight`'s wire
      format for lost/won card blobs is real and functional (`packets.go`'s
      `writeCardBlob`), it's simply never populated because there's nothing to populate
      it *with*. **What a real fix would need** (not attempted, since it's new
      game-design work, not a port of existing behavior): (1) decide what "wagering
      cards" even means for this project (likely: each coach pre-selects N cards from
      their `CoachCards`/`FighterCards` inventory before the fight starts); (2) a new
      opcode/UI flow to capture that selection (nothing today captures it beyond the
      flat bet integer); (3) persisting the selection on `world.Duel` alongside `Bet`;
      (4) `endFight` (`fightend.go`) reading that selection and moving the actual DB
      rows between winner/loser's card inventories, then populating
      `buildEndFight`'s blobs from the same data. Left as `nil, nil` (unchanged).
      Ladder-strength deltas remain separately out of scope (pre-existing fake-ladder
      item in §8.1/§8.6).
- [x] **§8.11 item 9**: synthesized an `EndFightDoneMessage` ack on disconnect —
      `dispatch/disconnect.go`'s `HandleDisconnect` now unconditionally sends
      `combat.NewEndFightDone(coach.ID)` alongside the existing forfeit path (safe
      regardless of fight phase, both commands are no-ops outside their relevant
      window). **Bonus fix found in the same pass**: `handleEndFightDoneRequest`
      (`internal/dispatch/handlers_fight_combat.go`) was removing the `Duel` from
      `DuelManager` after only the FIRST coach's ack rather than once the fight was
      fully torn down — this broke the disconnect-synthesis fix's own
      `Duels.GetByCoach` lookup for the coach who hadn't acked yet, silently
      reintroducing the exact Fight-actor leak this fix was meant to close via a
      different path. Fixed by deferring duel removal to a goroutine waiting on
      `Fight.Done()` (closed only once every coach has acked). New e2e regression test:
      `test/e2e/disconnect_endfight_test.go`'s
      `TestE2E_DisconnectAfterEndFightSynthesizesAck`.
- [x] **§8.11 item 10**: built a dedicated `cmd/loadtest` harness (`cmd/loadtest/main.go`)
      — boots a real, fully-wired server in-process via `internal/app` (same composition
      root as `cmd/server`) with its admin/pprof HTTP endpoint enabled, drives
      `-fights`/`-concurrency`-configurable concurrent fights to completion, and reports
      p50/p90/p99/max per-fight latency + throughput. Because the admin HTTP server is
      real, a genuine CPU/memory profile can be captured DURING a run with the standard
      `go tool pprof http://<admin-addr>/debug/pprof/profile?seconds=10` workflow —
      confirmed reachable (HTTP 200) while a load test was actively running. Verified
      stable up to 300 concurrent-ish fights at concurrency 50 (all succeeding, ~72
      fights/sec on this dev machine). **Bug found and fixed while building/stress-
      testing this tool**: at higher `-concurrency` values, `world.Matchmaker.FindMatch`
      (correctly, by design — matches purely on `(Type, Bet)`) was cross-pairing
      Alice/Bob from DIFFERENT concurrently-running simulated fights when they all used
      the same flat `bet=0` — fixed by using a unique bet value per fight index in the
      load-test script itself (not a server bug; the matchmaker was working exactly as
      designed, the *script's own scenario* needed each simulated pair to be
      unambiguously self-matchable).
- [x] **§8.11 item 11**: refreshed `docs/opcodes/07-fight-lifecycle.md` (16 Status markers
      updated) and `docs/opcodes/08-fight-combat-engine.md` (20 Status markers updated,
      plus the top-of-file summary blurb and the EndFightDoneMessage section's stale
      note about item 9, since that gap is now closed) to reflect reality — every
      previously-"not implemented" opcode in both files now cites the actual
      implementing Go function. Zero remaining "not implemented" markers in either file
      (confirmed via full-file search); the only remaining caveats are genuine
      known-simplifications already tracked elsewhere (free-placement zone restriction,
      END_FIGHT's empty card-wagering blobs per item 8 above), not missing opcode
      wiring.
- [ ] **§8.11 item 12 (remainder)**: extract the full `RunningEffectConstants`
      trigger-bus ID table across every `RunningEffect` subclass's
      `setTriggersToExecute()` — bundle this into Phase J above rather than doing it
      standalone, since it's meaningless until the trigger bus itself exists.

---

## 8.13 Real-user bug reports (post-Phase-O, July 2026): pre-fight forced-progress
timers + fight-map actor visibility

Three bugs reported by the user while play-testing against a real client, all traced to
the same class of gap: two duel-setup gates were purely state-based with **no server-side
timer at all**, and one wire opcode (`ACTOR_APPEAR`) was never built/sent anywhere despite
being the sole mechanism the client uses to render an entity inside the fight-map scene.

- [x] **Bug 1 — "20s ready countdown popup expires, fight doesn't start"** and
      **bug 3 — "ready again on the fight map doesn't start the fight, no auto-timer
      either"**: both traced to the exact same root cause, at two different points in the
      flow. Investigation (delegated to an explore sub-agent, cross-checked against both
      the decompiled client and the legacy `org.ankarton` reference server) confirmed:
      - `world.Duel.SetSelection`/`SET_READY_FOR_FIGHT` (opcode Recv 4303,
        `dispatch/handlers_fight.go`) and `world.Duel.SetPlacementReady`/
        `TEAM_MATE_SET_READY_FOR_PLACEMENT` (opcode Recv 8011) were both purely
        both-coaches-state-based gates with **zero timer of any kind** — a project-wide
        grep for `Timer|time.After|AfterFunc|Ticker` in `internal/dispatch`/`internal/world`
        returned zero matches before this fix. If one coach never sent the expected
        packet, the duel stalled forever.
      - The client's visible ~20s countdown (`Fight.onPresentationStart()`'s
        `Countdown.start(20)`, decompiled client source) is confirmed **100% cosmetic
        UI** — `Countdown.java`'s tick handler never sends any network message on
        expiry, it just decrements a local counter and stops. The server has always
        needed its own independent enforcement; it never had one.
      - The legacy Java reference server had the same gap for the
        `SET_READY_FOR_FIGHT`/`TEAM_MATE_SET_READY_FOR_PLACEMENT` gates themselves, but
        interestingly its `Fight.startPreparation()` (`Fight.java:208`) DOES call
        `FightManager.getInstance().schedule(this::startPresentation, 20)` right after
        `CREATE_FIGHT` — an unconditional 20s forced call, uncoordinated with the
        ready-based path (could double-fire) and for a slightly different transition
        than what this fix targets. Not ported as-is (the Go port's existing
        `askForXEnd()`-with-generation-counter pattern, already used one layer deeper by
        `combat.Fight`'s phase clocks, is the more robust approach and was reused here
        conceptually).
      - **Fix**: added two new configurable timers,
        `combat.CombatConfig.MatchReadyClock`/`PlacementReadyClock` (both default 20s,
        `internal/config/config.go`), threaded through a new `dispatch.Deps.Combat`
        field. `world.Duel` gained a single reusable forced-progress timer slot
        (`ArmReadyTimer`/`CancelReadyTimer`, `internal/world/duel.go`) plus
        `SelectedCoaches()`/`PlacementReadyCoaches()` introspection helpers and two new
        idempotency guards (`MarkPresentationStarted`, alongside the pre-existing
        `MarkPrepared`) so the timer-fired path and the normal-ack path can safely race
        without double-executing (both call sites are now funneled through shared
        `tryPrepareCreateFight`/`startPresentationForDuel` helpers in
        `dispatch/handlers_fight.go`). `DuelManager.Remove` now also cancels any
        still-armed timer, so a canceled/completed duel's timer never fires late against
        a duel no longer in the registry.
      - **Behavior on timeout, per explicit user decision**: at the
        `SET_READY_FOR_FIGHT` gate, since the server has no partial/fallback fighter
        roster for a coach who never selected anything (the client only ever transmits
        the final atomic selection), the timeout **cancels the duel for both sides**
        (`CancelReasonNoSelectedFighter`) rather than guessing a roster. At the
        `TEAM_MATE_SET_READY_FOR_PLACEMENT` gate, both rosters are already known (loaded
        for `CREATE_FIGHT`), so the timeout instead **forces presentation to start
        anyway**, exactly as if the non-responsive coach had also acked — teleport,
        `combat.Fight` instantiation, `ACTOR_APPEAR`, and `START_PRESENTATION` all
        proceed normally.
      - New tests: `internal/world/duel_test.go` (8 new tests covering
        `ArmReadyTimer`/`CancelReadyTimer`/replacement/`DuelManager.Remove`
        interaction/the new introspection + idempotency helpers) and
        `test/e2e/duel_test.go` (3 new e2e tests:
        `TestE2E_SetReadyForFightTimeoutCancelsDuelWhenIncomplete`,
        `TestE2E_SetReadyForFightTimeoutIsCanceledByNormalCompletion` — confirms no
        spurious double-fire when the normal path wins the race — and
        `TestE2E_PlacementReadyTimeoutForcesPresentationStart`). `test/e2e/server_test.go`
        gained `startTestServerConfigured` to let individual tests override the new
        clock durations independently of the shared 2s test defaults.

- [x] **Bug 2 — "coach teleported into the fight map but doesn't appear / client seems
      locked in place"**: traced to `ACTOR_APPEAR` (Send opcode 4102) never being built or
      sent anywhere in the Go server — confirmed via `internal/protocol/opcodes.go`
      defining the constant with zero other references project-wide before this fix. Per
      the decompiled client (`NetFightActorsFrame.java`'s case 4102), `ACTOR_APPEAR` is
      the **sole** mechanism that calls `MobileManager.addMobile(...)` to actually
      instantiate a visible entity inside the fight-map scene, for both coaches'
      sideline anchor pawns and every fighter — `ENTER_WORLD_INSTANCE` (already
      implemented) only repositions the client's own camera/instance context and never
      spawns anything (confirmed via `NetInstanceFrame.java`'s dynamic-fight branch,
      which conspicuously lacks the `addMobile` call its non-dynamic/world-join sibling
      branch has). `CREATE_FIGHT`'s fighter serialization carries no position data at
      all (matches the client's own non-positional `Fighter.unserialize()`), so
      `ACTOR_APPEAR` (or an equivalent) was always a hard requirement, not an optional
      nicety — this was a genuine, total gap, not a simplification. (The legacy Java
      reference server built the identical packet in `Fight.java:257-292` but had its
      own send call commented out — `//buffer2.sendTo(...)` — so it never worked there
      either, for a different, dead-code reason.)
      - **Fix**: new `buildActorAppear`/`actorAppearEntry`
        (`internal/dispatch/packets_coach.go`) implementing the exact client-verified
        wire format (`byte count` + repeated `{long id, int worldX, int worldY, short
        altitude, byte directionIndex}`, no type-discriminator byte — the client
        disambiguates coach vs. fighter purely by ID against the fight's own roster).
        New `combat.Fight.AllFighters()` accessor. New
        `buildActorAppearForFight`/`startPresentationForDuel` helper
        (`internal/dispatch/handlers_fight.go`) assembling both coaches' anchor pawns
        (at their resolved `resolveCoachStartSpots` cells) plus every fighter (at its
        real `Fighter.Position`), all facing `DirSouth` (matching the legacy reference's
        own hardcoded orientation byte for every entry, `Fight.java:276,291`). Sent to
        both coaches right after `ENTER_WORLD_INSTANCE`/`instantiateFight` and right
        before `START_PRESENTATION`.
      - New tests: `internal/dispatch/packets_coach_test.go` (3 new tests: exact wire
        format byte-for-byte, empty-entries edge case, and the full
        `buildActorAppearForFight` assembly against a real `combat.Fight`). Existing
        e2e test helpers (`test/e2e/combat_test.go`'s `startFullFight`,
        `test/e2e/load_test.go`) updated to expect the new `SendActorAppear` frame
        between `SendEnterWorldInstance` and `SendStartPresentation`.

- Debugging note for the user: all three bugs were fully diagnosed from server-side code
  + decompiled client source alone, without needing a live-client opcode capture. If a
  similar report comes in again, the fastest path is (1) `server.logging.trace_packets:
  true` in the YAML config (or `--trace-packets` CLI flag) to log every inbound/outbound
  opcode name + hex payload at info level, and (2) note the last few opcodes seen on each
  side of the connection right before the client visibly gets stuck — that reliably
  narrows down which gate/handler is the culprit, exactly as it did here.

All new/changed code covered above builds cleanly (`go build -buildvcs=false ./...`),
passes `go vet`, and the full test suite (`go test -buildvcs=false ./...`, including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` (all packages) and `test/e2e/...`.

---

## 8.14 Real-user bug report (post-§8.13, July 2026): fighters stacked on the coach's
own cell during placement, permanently stuck once combat started

- [x] **Bug — "every fighter goes to the coach's own placement spot; after the match
      starts I can't move the fighter (it is blocked); there should be ~8 distinct
      per-fighter cells"**: root cause was in `buildCombatTeam`
      (`internal/dispatch/handlers_fight.go`), which placed every fighter at
      `(anchor.X, anchor.Y+i, anchor.Z)` — literally the COACH's own resolved teleport
      spot (`resolveCoachStartSpots`'s `FightStartCoachPointElement`/`CoachStartCells()`
      cell) plus a vertical index offset, not any dedicated fighter-placement data. This
      visually stacked every fighter on/around the coach's own pedestal exactly as
      reported, and — worse — several of the synthetic `anchor.Y+i` cells for larger
      rosters landed **off the parsed map entirely or in an isolated pocket with zero
      walkable cells reachable from it** (confirmed via a real-map-data empirical
      reproduction: fighter slots 5/6/7 at a `(16,11)` anchor landed at `(16,16)`/
      `(16,17)`/`(16,18)`, the last of which isn't even on the map — `FindPath` correctly
      returned "no path" for all of them since none genuinely exists, not because of any
      artificial "start cell must be walkable" rule in the pathfinder itself
      (`internal/combat/pathfind.go`'s `FindPath` never validates `start`, only
      candidate neighbors) — this is exactly the "fighter is blocked" symptom reported.
      - **Real data already existed and was already parsed, just never wired up**: the
        real per-team fighter placement zone is `FightStartPointElement` (kind 1000),
        genuinely distinct from the coach's own `FightStartCoachPointElement` (kind
        1001) — confirmed via the decompiled client's `StartPointManager.java:147-150`,
        which scans specifically for kind **1000** to build its own "where can fighters
        be placed" highlight overlay, separate from the coach's pedestal. This was
        already exposed as `gamedata.Map.FightStartCells()`
        (`internal/gamedata/map.go`) from Phase K, but `buildCombatTeam` never called
        it. Real data for `fightMapID=2` has **10 distinct cells per team side**, not
        exactly the 8 the user recalled from playing the original game, but the same
        *kind* of dedicated small scatter of cells — every one independently confirmed
        to have at least one walkable surface, and genuinely distinct from (not merely
        adjacent to) the coach's own anchor cell for the same side
        (`TestMapStore_RealFightStartCellsDistinctFromCoachAnchor`,
        `internal/gamedata/map_realdata_test.go`). The
        previously-stale `docs/04-game-data-format.md` §4.9.8 note claiming this data
        "would need... a distinct piece of per-map data this pass didn't need to
        identify" has been corrected — it was identified, just unused.
      - **Fix, per explicit user decisions**: (1) each fighter is now assigned a
        **randomly-chosen, distinct** real `FightStartCells()` cell for its team side
        (not a fixed/deterministic order — the user asked for "a random cell for each
        fighter according to the right team"); (2) a brand-new fallback utility,
        `gamedata.Map.NearbyWalkableCells` (`internal/gamedata/map.go`), implements the
        user's other explicit request ("if you don't know where, then use random
        walkable cell") — an outward Chebyshev-ring scan from an anchor cell collecting
        every distinct walkable cell found, shuffled, used whenever a team's roster
        exceeds the real cell pool for that side (or, transitively, whenever no real
        map data is attached at all falls back one level further to the historical
        anchor+offset placement, so a fight still never fails to start). New
        `resolveFighterPlacementCells`/`buildCombatTeam` (`handlers_fight.go`)
        implements the full priority chain: real `FightStartCells()` (shuffled) → 
        `NearbyWalkableCells` fallback for any shortfall → historical anchor+`Y+i`
        offset if no map data is attached at all. `buildCombatTeam` gained a
        `teamSideByte` parameter (0/1, matching `resolveCoachStartSpots`'s own
        team-side-byte convention for `CoachStartCells()`/`FightStartCells()` — both
        element kinds share the same per-map authoring convention). Renamed
        `coachSpotAtAltitude` → `cellSpotAtAltitude` since it's now shared by both the
        coach's own anchor resolution and each fighter's placement-cell altitude
        resolution.
      - New tests: `internal/gamedata/map_test.go` (7 new synthetic-map unit tests for
        `NearbyWalkableCells`: anchor-itself, ring-expansion when the anchor isn't
        walkable, count-capping, exhaustion, zero-count, non-walkable-surface
        exclusion, RNG-seeded determinism), `internal/gamedata/map_realdata_test.go`
        (1 new real-data test, `TestMapStore_RealFightStartCellsDistinctFromCoachAnchor`,
        confirming every real `FightStartCells()` cell is distinct from the coach anchor
        and walkable), `internal/dispatch/fighter_placement_test.go` (4 new tests, new
        file: real-cell-usage, roster-exceeds-real-cells fallback, no-map-data fallback,
        zero-count edge case). New e2e test
        `test/e2e/combat_test.go`'s `TestE2E_FighterCanMoveFromRealPlacementCell` drives
        a real fight end-to-end, reads the CURRENT fighter's real starting position
        straight out of the `ACTOR_APPEAR` broadcast (not a hardcoded assumption), and
        confirms a one-step move request in some cardinal direction actually produces a
        `FIGHTER_MOVE` broadcast rather than being silently rejected — this is the exact
        end-to-end symptom the user reported, now covered by a regression test.
        `startFullFight`'s return signature gained `positions map[int64][3]int32` (parsed
        from `ACTOR_APPEAR`) and `currentFighterID int64` (parsed from
        `FIGHTER_TURN_BEGIN`'s payload, since turn order does NOT necessarily put
        Alice's fighter first — a latent test-helper assumption this fix's own test
        writing exposed and corrected); all existing callers updated. New
        `testClient.tryExpectOpcode` helper (`test/e2e/client_test.go`) added for
        "probe whether X produces Y within a short deadline, without failing the test if
        not" assertions.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.15 Real-user bug reports (post-§8.14, July 2026): fighter rendering altitude +
missing spells/equipment in CREATE_FIGHT

Two more bugs reported by the user while play-testing against a real client.

- [x] **Bug — "I can see the placement cell, but not the fighter; once combat starts I can
      see it if I change direction, and it seems to be under the map"**: extensively
      investigated against the decompiled client to rule out several plausible causes before
      settling on the fix below.
      - **Ruled out** (all confirmed correct via decompiled-source tracing, no fix needed):
        (1) `ACTOR_APPEAR`'s wire format — byte-exact against `ActorAppearMessage.java`;
        (2) client-side visibility mechanics — confirmed `addMobile()` (the call that makes
        an actor visible/rendered) genuinely fires for fighters inside the `case 4102`
        handler, identically to coaches; the *first* research pass wrongly concluded
        otherwise because one of the two decompiled copies of `NetFightActorsFrame.class` in
        this repo (`client/...` vs `data/stc/...`, both from identical bytecode but
        decompiled by different JD-Core versions) contains an internally-inconsistent
        control-flow reconstruction (a `coach` variable used-but-never-assigned in the
        fighter branch — proof of a decompiler bug, not real source); the older/cleaner
        decompilation resolves this unambiguously; (3) direction-change "fixing" visibility —
        confirmed this only forces a display-object rebuild (`Mobile.process()`'s dirty-flag
        check), no actual visibility/registration logic lives there — a red herring, not a
        real fix, explaining why the user only "discovered" the fix once combat started (the
        direction-change opcode is gated to `PhaseAction`, so it was literally impossible to
        send during PLACEMENT); (4) coordinate scale/units — confirmed no grid-to-pixel
        conversion exists anywhere in the traced call chain (`EnterInstanceMessage`/
        `ActorAppearMessage` → `Fighter.setPosition`/`Coach.setWorldPosition` →
        `Mobile.setWorldPosition`) — cell-index integers are used directly and identically by
        both the (confirmed-working) coach path and the fighter path; (5) frame-stack
        timing — confirmed `NetFightActorsFrame` (the 4102 handler) is pushed as a direct
        synchronous side effect of `CREATE_FIGHT` (opcode 8000) handling, well before
        `ENTER_WORLD_INSTANCE`/`ACTOR_APPEAR` are ever sent, so it's guaranteed active in time.
      - **Actual fix**: `resolveFighterPlacementCells` (Phase K/§8.14) resolved each fighter's
        cell altitude via `cellSpotAtAltitude` — the same helper used for the coach's own
        anchor cell, whose rule is "pick the walkable surface nearest to a given reference
        altitude." For fighter cells this reference was the **coach's own, unrelated**
        altitude, an arbitrary cross-cell heuristic that could pick the wrong (lower/hidden)
        surface for any fighter cell with more than one resolved walkable surface (confirmed
        via real map data: cell `(4,9)` on `fightMapID=2`, part of team side 1's
        `FightStartCells()` pool, has two walkable surfaces at altitude -11 and -4) — landing
        the fighter's sprite at a Z that doesn't match the terrain mesh at that cell,
        producing the reported "under the map" symptom (confirmed via decompiled source that
        the client applies altitude as a raw, unconverted pass-through value with zero
        ground-snap correction, so a wrong server-sent Z has no client-side self-correction).
        New `topmostWalkableSpot` (`internal/dispatch/handlers_fight.go`) instead always picks
        a fighter's cell's own **highest** walkable surface — the natural "ground you'd stand
        on" viewed from above — with no dependency on any other cell's altitude. Wired into
        `resolveFighterPlacementCells` in place of `cellSpotAtAltitude` (the coach's own
        anchor resolution, `resolveCoachStartSpots`, is unaffected and still uses
        `cellSpotAtAltitude`, since it doesn't have this multi-surface ambiguity problem in
        the same way — it directly maps its own coach altitude to itself). Not 100%
        confirmed as the complete/exact root cause without a live-client verification pass
        (per explicit user decision, this was implemented as a real, defensible improvement
        first, to be confirmed or iterated on after in-game testing).
      - New tests: `internal/dispatch/fighter_placement_test.go` gained
        `TestTopmostWalkableSpotPicksHighestSurfaceNotNearestToAnchor` (uses the real
        multi-surface cell `(4,9)` to prove the old nearest-to-anchor rule and the new
        highest-surface rule diverge) and `TestTopmostWalkableSpotFallsBackWhenNoWalkableSurface`.

- [x] **Bug — "fighters don't have equipment and spells in fight while I equipped them"**:
      confirmed `buildCreateFight` (`internal/dispatch/packets_fight.go`) still hardcoded
      both `spellsBlobLen`/`equipmentBlobLen` to 0 for every fighter — a known, previously-
      documented simplification (`docs/opcodes/07-fight-lifecycle.md`'s "Go server status"
      section) that was never actually fixed. Confirmed via decompiled client source
      (`FightCreationMessage.decode()` → `Fighter.unserialize()`) that `CREATE_FIGHT` is the
      **sole** place the client ever populates a fighter's in-fight spell/equipment
      inventory — no later opcode in the 8010-8040 phase-transition range or the 8100+
      combat-engine range carries any per-fighter loadout data; this was a complete
      functional gap, not an intentionally-minimal format awaiting a follow-up message. The
      real loadout data was already loaded correctly elsewhere (`buildCombatTeam` already
      populated the real `combat.Fighter.SpellIDs`/`ObjectIDs` via
      `FighterService.LoadoutMaps` for the actual turn-based engine) — the gap was purely
      that `CREATE_FIGHT`'s packet is built by a separate, earlier code path
      (`buildDuelTeam`/`duelTeamInfo`, using plain `domain.Fighter` rows with no loadout
      fields at all) that never had access to that data.
      - **Fix**: `duelTeamInfo` (`internal/dispatch/packets_fight.go`) gained
        `SpellsByFighter`/`ObjectsByFighter map[uint][]int32` fields. `buildDuelTeam`
        (`internal/dispatch/handlers_fight.go`) now calls `FighterService.LoadoutMaps`
        (same source `buildCombatTeam` already used) and populates them. `buildCreateFight`
        replaces its two hardcoded `w.PutUint16(0)` spell/equipment-length writes with calls
        to the already-implemented, already-tested `buildSpellBlob`/`buildInventoryBlob`
        helpers (`inventory_codec.go`, previously only used for `FIGHTER_CREATE_RESULT`/
        `FIGHTER_INFORMATION_LIST`/`FIGHTER_UPDATED_INFORMATION_INVENTORY`) — no new
        wire-format code needed, purely a wiring fix. `buildCreateFight` gained a
        `store *gamedata.Store` parameter (used only for `buildInventoryBlob`'s
        equipment-slot-position lookup; may be nil, in which case equipment degrades to
        empty rather than panicking, while spells are unaffected since `buildSpellBlob`
        needs no store lookup — a fight must never fail/crash just because gamedata lookups
        are unavailable).
      - New tests: `internal/dispatch/packets_fight_test.go` (new file, 2 tests: real
        spells/equipment appear correctly in the serialized payload for one fighter while
        another fighter with no loadout correctly serializes empty blobs; nil-store
        graceful degradation). New e2e test `test/e2e/duel_test.go`'s
        `TestE2E_CreateFightIncludesRealFighterLoadout` equips a real fighter with real
        spell/card IDs via `UPDATE_FIGHTER_INVENTORY_REQUEST`, drives a real duel through
        `CREATE_FIGHT`, and confirms that fighter's wire entry now actually carries the
        equipped loadout — this is the exact end-to-end symptom the user reported, now
        covered by a regression test.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.16 Real-user bug report (post-§8.15, July 2026): ACTOR_APPEAR coach/fighter ID
collision — the ACTUAL root cause of "fighters invisible until re-positioned"

This finally pins down the real root cause of the fighter-visibility bug that §8.14 and §8.15
each took partial (and, for §8.15's altitude theory, ultimately incorrect) stabs at. The user
enabled `server.logging.trace_packets` (§8.13's own suggested debugging path) and provided a
full inbound/outbound packet trace, which made the bug immediately obvious.

- [x] **Bug — "I have to change the fighter's direction to see it; when positioning I don't
      see the fighters, only once the fight starts and I change direction"**: the packet trace
      showed the real `ACTOR_APPEAR` (4102) payload:
      `04 [id=2 x=16 y=11 z=-8 dir=2] [id=1 x=1 y=7 z=-6 dir=2] [id=2 x=14 y=3 z=-11 dir=2]
      [id=1 x=3 y=11 z=-11 dir=2]` — **4 entries, with IDs `2, 1, 2, 1`**. The first two are the
      coach anchor pawns (Albert=coach 2, Koikoi=coach 1); the last two are the fighters (tttt=
      fighter 2, other=fighter 1). **Coach IDs and fighter IDs collide** because they come from
      two independent, each-auto-incrementing-from-1 DB tables (`coachs` vs `fighters`).
      - **Why this mis-renders** (confirmed via the authoritative `data/stc/...`
        `NetFightActorsFrame.java` case-4102 decompilation — the cleaner of the two copies in
        this repo): the client resolves each entry's ID by calling `fight.getFighterById(id)`
        **first**, only falling back to a coach lookup if that returns nil. So the COACH entry
        `id=2` resolves to FIGHTER 2 and applies the coach's pedestal position `(16,11,-8)` to
        the fighter; the fighter's own later entry `id=2` then overwrites it with the correct
        `(14,3,-11)`. Net effect: the fighter is positioned at the coach's cell first, corrected
        only on the second entry — and depending on client render timing this leaves it visibly
        misplaced/"under the map"/effectively invisible until a later `FIGHTER_CHANGE_DIRECTION`
        forces a display-object rebuild at the (by-then) correct position. This also fully
        explains why §8.15's "altitude heuristic" theory, while a real minor improvement, did
        not fix the symptom: the altitude being sent was fine; the POSITION was being clobbered
        by a colliding coach entry.
      - **Fix**: `buildActorAppearForFight` (`internal/dispatch/handlers_fight.go`) no longer
        includes coach entries at all — it now sends ONLY fighter entries, exactly matching the
        real reference server (`src/org/ankarton/.../Fight.java:257-292`, whose ACTOR_APPEAR
        buffer count is `fighters.size()` and never appends coaches; coaches are rendered via
        the separate `ENTER_WORLD_INSTANCE`/coach-world flow). Its signature dropped the now-
        unused `coachASpot/coachBSpot/coachAID/coachBID` params.
      - New/updated tests: `internal/dispatch/packets_coach_test.go`'s
        `TestBuildActorAppearForFight` rewritten to assert exactly 2 entries (fighters only, no
        coaches) and — as the specific regression guard — uses **colliding** coach/fighter IDs
        (coach 10 == fighter 10, coach 20 == fighter 20) so any future reintroduction of coach
        entries would immediately show up as duplicate/shadowed IDs.
      - This supersedes §8.15's altitude-heuristic change as the fix for the visibility symptom;
        `topmostWalkableSpot` (from §8.15) is retained as an independent correctness improvement
        (picking a cell's own highest walkable surface is still more correct than the old
        nearest-to-an-unrelated-cell rule), just no longer credited as the visibility fix.

- **Note on the "spell cast does nothing" report** (same session): the same packet trace showed
  the acting fighter in the user's test (tttt, fighter id 2, coach Albert) had **empty**
  spell/equipment blobs in its `CREATE_FIGHT` entry — i.e. that specific fighter genuinely has no
  spells equipped in the DB — while the OTHER fighter (id 1, coach Koikoi) correctly received its
  real spell/equipment blobs (confirming §8.15's `CREATE_FIGHT` loadout fix works). No
  `SPELL_CAST_REQUEST` (8109) ever reached the server in the trace, consistent with the client
  simply having no spell to cast for that fighter, rather than a server-side handling gap
  (`handleSpellCastRequest` is present and correct). Recommended follow-up: equip the acting
  fighter with spells and re-test; the spell-cast pipeline itself (validateCast → effects) is
  already implemented and unit-tested (§8.12 Phase L).

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.17 Real-user bug report (post-§8.16, July 2026): fighter altitude convention
(base vs. base+height) — the REST of the "sunk under the map" bug + live-debug tooling

After §8.16 fixed the coach/fighter ID collision, a fresh packet trace confirmed
`ACTOR_APPEAR` now correctly carries only the two fighter entries — but the fighters still
rendered sunk into the terrain (only "popping up" after a client-side redraw triggered by a
manual direction change). This section fixes the remaining, distinct altitude-convention bug,
and adds live-debugging tooling so this class of "tweak a wire value, see the visual result"
problem no longer requires a full server-restart-and-replay loop.

- [x] **Bug — fighters render at the wrong (too-low) altitude, appearing under/inside the
      terrain until a client redraw**: the trace showed fighters at e.g. `z=-11` (their cell's
      raw base altitude) while the map's actual standing surface there is at `-4`. Root cause
      (confirmed via the authoritative `data/stc` decompilation): the client renders a `Mobile`
      at `mobile.getAltitude() * elevationUnit` screen pixels and expects that altitude to be
      the **top-of-block STANDING altitude** = a surface's base `Altitude + Height`, NOT its raw
      base `Altitude`. Every place the client itself picks a cell to stand/move/cast on uses
      base+height (`WorldSceneInteractionUtils`'s `getAltitude()+getHeight()`,
      `UIFightPlacementFrame` sending `coordinates.getZ()`=base+height, `StartPointManager`
      registering start cells at `getAltitude()+getHeight()`), and the reference server itself
      hardcodes exactly `-4` for fighter placement (`Fight.java:274,289`). Sending the raw base
      altitude drops the fighter `Height` altitude-units (~`Height`×`elevationUnit` px) into the
      ground; a later client redraw (e.g. from a direction change or move that recomputes
      `coordinates.getZ()`=base+height) corrects it — exactly the reported "pops up after I
      change direction" behavior. §8.15's `topmostWalkableSpot` "pick the highest walkable
      surface" was on the right track but used the surface's raw base `Altitude`; the missing
      piece was adding `Height`.
      - **Reconciliation with `IsWalkable` (why there's no conflict)**: the reference
        `WorldCell.isWalkable(z)` matches `z` against a surface's raw base `Altitude`, which
        seems to conflict with "everyone else uses base+height." It doesn't: a *flat* walkable
        ground tile has `Height == 0`, so for it `base == base+height`. The base+height
        convention only visibly differs for the occasional raised-walkable-platform surface
        (e.g. this map's `base=-11 height=7` tile, standing at `-4`). The go-server's own
        `Fight.IsWalkable` (`internal/combat/turns.go`) already ignores the exact Z entirely
        (it only checks "does any walkable surface exist at this X/Y"), so storing the
        standing (base+height) Z in `Fighter.Position.Z` does not break movement validation.
      - **Fix**: new `gamedata.Map.StandingAltitudeAt(x,y)` (`internal/gamedata/map.go`)
        encapsulates the "highest walkable base+height" rule. `topmostWalkableSpot`
        (`internal/dispatch/handlers_fight.go`) now delegates to it, so every fighter's
        `ACTOR_APPEAR`/placement Z is the standing altitude. `Fight.ArrivalAltitude`
        (`internal/combat/turns.go`) likewise now resolves movement destinations to the
        standing (base+height) altitude nearest `fromZ`, keeping a moving fighter's Z
        consistent with its placement Z so it never sinks mid-move. The coach path
        (`resolveCoachStartSpots`/`cellSpotAtAltitude`) was deliberately left untouched, since
        coaches already render correctly (their own cell/altitude flow happens to land on a
        working value) and changing it risked regressing what works.
      - New tests: `internal/gamedata/map_test.go`'s
        `TestStandingAltitudeAtUsesBasePlusHeight` (synthetic surfaces: raised platform
        base=-11 height=7 → -4, flat ground height=0 → base, non-walkable → not found) and
        `internal/dispatch/fighter_placement_test.go`'s
        `TestTopmostWalkableSpotUsesStandingAltitude` (real cell (4,9) on fightMapID=2 →
        exactly -4, matching the reference server's hardcoded value).
      - **In-game confirmed** via `/APPEAR z -4`: correct altitude.

- [x] **Bug — fighter sprites render wrong when facing a CARDINAL direction**: confirmed
      in-game via `/APPEAR dir <d>` — even indices (0/EAST, 2/SOUTH, 4/WEST, 6/NORTH) render
      wrong/blank, odd indices (1/SOUTH_EAST, 3/SOUTH_WEST, 5/NORTH_WEST, 7/NORTH_EAST) render
      correctly. Root cause: fighter sprite art only exists for the four **diagonal**
      orientations (the client's `Direction8.DIRECTION_4_VALUES` = {SOUTH_EAST, SOUTH_WEST,
      NORTH_WEST, NORTH_EAST}); the four cardinals have no fighter animation. The Go server was
      sending `DirSouth`(2, a cardinal), copied from the reference server's hardcoded
      `.put(2)` — which was itself wrong, but never exposed since that reference send was
      commented out and never ran.
      - **Fix**: new `combat.defaultTeamFacing(teamID)` (`internal/combat/fighter.go`) returns a
        valid diagonal per team (team 1 → SOUTH_WEST=3, team 2 → SOUTH_EAST=1, so the sides
        broadly face each other); `NewFighterFromBreed` sets each fighter's `Direction` from it
        (previously left at the zero value, `DirEast`=0, also a wrong cardinal).
        `buildActorAppearForFight` and the `/APPEAR` GM default now use the fighter's own
        `Direction` instead of a hardcoded `DirSouth`.
      - Test: `TestBuildActorAppearForFight` (`internal/dispatch/packets_coach_test.go`) now
        asserts each fighter's emitted direction equals its team default AND — as a
        convention guard — that every emitted direction is an odd/diagonal index.

- [x] **Live-debug tooling — new `/APPEAR` GM chat command**
      (`internal/dispatch/handlers_gm_commands.go`): per the user's request for a faster
      test-feedback loop (the restart-and-replay-a-whole-fight cycle was the real bottleneck),
      this admin-gated chat command re-sends `ACTOR_APPEAR` for every fighter in the requester's
      current fight, with optional live overrides — `/APPEAR z <z>`, `/APPEAR dir <d>` (0-7),
      `/APPEAR raw <z> <d>`, `/APPEAR here` (place all fighters at the requester's own coach
      cell, to A/B against a known-good position) — and echoes the exact values sent back to the
      client (also captured server-side via `trace_packets`). This let the altitude convention be
      validated by trying candidate Z values in-client in seconds. Documented in
      `docs/02-protocol.md` §2.4.9's GM command table.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.18 Real-user bug report (post-§8.17, July 2026): fighter-ownership authorization +
coach rendering (fighter wire-ID offset)

Rendering is now fully correct (user confirmed §8.17's altitude+direction fixes in-game). Two
new issues surfaced from the next packet trace.

- [x] **Bug — a coach could act on the OPPONENT's fighters**: the trace showed `session=1`
      sending `MOVE_TO_FREE_PLACEMENT_REQUEST` for BOTH fighter id 1 (its own) AND fighter id 2
      (the opponent's), and the server accepting/broadcasting both. Root cause: every
      player-driven fighter command (`handleMoveToFreePlacement`, `handleFighterMove`,
      `handleFighterDirectionChange`, `handleFighterEndTurn`, `handleCloseCombat`,
      `handleSpellCast`, `handleCardUse`) looked the fighter up by the wire-supplied `FighterID`
      via `f.fightersByID[...]` with **no ownership check** — a coach could drive any fighter,
      including the opponent's.
      - **Fix**: the six player-driven fighter commands gained a `RequesterCoachID uint` field,
        populated in dispatch from the authenticated session's coach (`sessionFight` already
        returns it — the handlers just stopped discarding it with `_`). A new shared
        `Fight.resolveOwnedFighter(requesterCoachID, fighterID)` (`internal/combat/fight.go`)
        replaces the raw `fightersByID` lookups: it returns the fighter only if the requesting
        coach owns it (via `Fighter.CoachID`). A `requesterCoachID` of 0 is a trusted-internal/
        test sentinel that bypasses the check (no real coach has id 0). The `New*` command
        constructors gained a leading `requesterCoachID` param; all dispatch call sites pass
        `coach.ID`.
      - New tests: `internal/combat/ownership_test.go` (resolveOwnedFighter rejects foreign/
        unknown fighters, coach-0 bypass; plus `TestHandleMoveToFreePlacement_RejectsForeignFighter`
        — the exact reported scenario: coach 100 cannot move coach 200's fighter during
        placement, but can move its own).

- [x] **Bug — coaches not visible on the fight map** (regression introduced by §8.16, which
      dropped coach entries from ACTOR_APPEAR to dodge the ID collision): investigated against the
      client and confirmed ACTOR_APPEAR is the **only** way to spawn a visible coach mobile —
      coaches are NOT auto-placed from CREATE_FIGHT or the map's `FightStartCoachPointElement`
      cells (`DofusArenaCustomElementProcessor` treats the type-1001 coach-point element as a
      no-op; the on-screen pedestals are separate type-1002 scenery). So coaches must be sent in
      ACTOR_APPEAR after all — but with **non-colliding IDs**.
      - **Why coaches must keep their REAL ids, and fighters get offset instead**: a deep client
        trace showed the coach id in CREATE_FIGHT is compared against the login-supplied
        `m_localCoach.getId()` (real id) in `DofusArenaGameEntity.setFight()` (to resolve the
        local fighting coach, which gates all three ready-phases) and again in `FightEndAction`
        (for end-fight stats). Offsetting coach ids would break both. So the **fighter** id space
        is shifted instead (per explicit user decision): `combat.FighterWireIDBase` = 1,000,000,000
        added to each fighter's real DB id, applied identically at `combat.Fighter` construction
        (`buildCombatTeam`) and in CREATE_FIGHT (`buildCreateFight`). Coaches keep their real ids.
        Because the client echoes fighter ids back verbatim and the whole combat engine uses this
        offset id as the fighter's identity, no per-packet translation is needed anywhere — the
        offset is applied once at construction. Summons (`nextFighterID = maxID + 1_000_000`)
        naturally land above the base too.
      - **Fix**: `buildActorAppearForFight` restored to emit both coaches (real ids, pedestal
        spots, facing SOUTH_EAST) and all fighters (offset ids), in coach-then-fighter order.
        Its signature regained the coach id/spot params.
      - Tests updated: `TestBuildActorAppearForFight` now asserts 4 entries (2 coaches + 2
        fighters) with a real fighter id deliberately equal to a coach id, proving the offset
        removes the collision (plus a no-duplicate-id guard). Two e2e tests
        (`TestE2E_FighterCanMoveFromRealPlacementCell`, `TestE2E_CreateFightIncludesRealFighterLoadout`)
        and `startFullFight` updated to account for the wire offset when correlating DB fighter
        ids against wire ids.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.19 Real-user request (post-§8.18, July 2026): skip the presentation countdown when
both coaches click "Prêt"

The user confirmed §8.18's fixes work in-game (coaches render, opponent-fighter actions
rejected), and asked: during the PRESENTATION phase, when both players click "Prêt", the fight
should proceed to placement immediately instead of waiting out the presentation timer.

- [x] **Feature — presentation-ready gate (skip the presentation clock)**: investigation
      (against the authoritative `data/stc` decompilation) found the client re-uses **opcode
      8011** (`TeamMateSetReadyForPlacementRequestMessage`) for the "Prêt" button during
      presentation — the SAME opcode as the pre-fight teleport gate (via
      `UIFightPresentationFrame`'s UI event 18009). The client's `NetFightPresentationFrame`
      reacts to the server's 8012 ack (hiding the "Prêt" dialog, showing a "waiting for
      opponent" spinner) and to `END_PRESENTATION` (8018, which cancels its own local countdown
      and advances to placement). So the intended handshake is: both clients send 8011 → server
      emits 8018 + 8020 immediately, skipping the remaining countdown. Presentation was
      previously purely clock-driven in the Go server (its `askForPresentationEnd` doc even said
      "no client Recv opcode exists for presentation ready" — that was wrong; the opcode exists,
      just overloaded).
      - **Fix**: `handleTeamMateSetReadyForPlacement` (`internal/dispatch/handlers_fight.go`)
        now disambiguates opcode 8011 by fight state — if a `combat.Fight` already exists and is
        in `PhasePresentation`, the packet is a presentation-skip vote and is routed into the
        fight actor via new `combat.NewCoachReadyPresentation`; otherwise it's the teleport gate
        (unchanged). The new `Fight.handleCoachReadyPresentation` (`internal/combat/phases.go`)
        mirrors the existing placement/observation ready-gates: it records the vote in a new
        `presentationReadyCoaches` map, broadcasts the 8012 ack (new
        `buildTeamMateSetReadyForPlacement` in `internal/combat/packets.go`), and once both
        coaches have voted calls the existing `askForPresentationEnd()` (which cancels the
        presentation clock and drives END_PRESENTATION → START_PLACEMENT). A stray 8011 arriving
        after presentation is a safe no-op. The presentation clock remains as the fallback if a
        coach never clicks "Prêt".
      - New tests: `internal/combat/fight_test.go`'s
        `TestFight_PresentationReadyBothCoachesSkipsClock` (uses a 30s presentation clock that
        must be SKIPPED, asserts one-ready-is-not-enough then both-ready ends presentation, and
        checks the 8012 ack broadcast) and `test/e2e/duel_test.go`'s
        `TestE2E_PresentationReadySkipsPresentationClock` (drives a real duel to
        START_PRESENTATION with a 30s presentation clock, sends the second 8011 from both
        coaches, and confirms END_PRESENTATION + START_PLACEMENT arrive in ~0.5s rather than
        30s).

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.20 Real-user bug reports (post-§8.19, July 2026): MP/AP not debited on client,
coach altitude/facing, coach equipment invisible

Three issues from the next play-test.

- [x] **Bug — PM (movement points) don't decrease client-side; a fighter can move without
      limit**: the server DID deduct MP internally but never told the client. Confirmed via the
      decompiled client: `FIGHTER_MOVE` (4524) carries only the path (no MP field), and the
      client is **server-authoritative for AP/MP** — it never debits them locally (its only
      local MP subtraction is a path-preview highlight, not the real characteristic). The client
      debits AP/MP exclusively via a `RUNNING_EFFECT_ACTION` (opcode 8120) carrying an `MPUse`
      (id 92) or `APUse` (id 91) running effect, whose `execute()` calls
      `characteristic.substract`. The Go server already had the builder
      (`buildRunningEffectAction`) and the effect kinds, but only ever sent them when a *spell*
      contained an MP_USE/AP_USE effect — never for the MP spent by plain movement or the AP
      spent by the act of casting.
      - **Fix**: new `Fight.broadcastCharacUse(runningEffectID, target, value)`
        (`internal/combat/effects.go`) sends the 8120 packet with the right effect id (92 MP_USE
        / 91 AP_USE), `casterId=0` (MPUse/APUse `useCaster()==false`), `targetId=` the fighter,
        `value=` points spent. Wired in: after `handleFighterMove`'s FIGHTER_MOVE broadcast
        (MP_USE = path cost), after `handleSpellCast`'s SPELL_CAST (AP_USE = spell AP cost), and
        after `handleCloseCombat`'s CLOSE_COMBAT (AP_USE = close-combat AP). Card use debits no
        AP directly (unchanged).
      - Test: `test/e2e/combat_test.go`'s `TestE2E_FighterCanMoveFromRealPlacementCell` extended
        to assert a RUNNING_EFFECT_ACTION with runningEffectId 92 follows the FIGHTER_MOVE.

- [x] **Bug — coach altitude wrong**: coaches now render (§8.18) but at the wrong height, because
      their ACTOR_APPEAR position reused `resolveCoachStartSpots`/`cellSpotAtAltitude` (the
      "nearest to a reference altitude" rule) rather than the STANDING altitude (base+height)
      that §8.17 established as correct for fighter sprites (and confirmed in-game at z=-4). New
      `resolveCoachAppearSpots` (`internal/dispatch/handlers_fight.go`) computes the coach's
      ACTOR_APPEAR altitude via `topmostWalkableSpot` (standing altitude), kept SEPARATE from
      `resolveCoachStartSpots` (still used for the `ENTER_WORLD_INSTANCE` camera, which works at
      its own altitude — the rendered sprite and the camera/instance altitude are distinct
      concerns).

- [x] **Bug — coaches face the wrong way (back to the battlefield)**: both coaches originally
      faced `DirSouthEast`; a first attempt set coach A `SOUTH_EAST` / coach B `NORTH_WEST`, but
      an in-game screenshot showed that faced them AWAY from the field (backs turned). Corrected
      to coach A `NORTH_WEST` / coach B `SOUTH_EAST` (§8.21) so both look toward the battlefield /
      each other. `buildActorAppearForFight`.

- [x] **Bug — coach equipment doesn't show on the fight map**: `buildCreateFight`
      (`internal/dispatch/packets_fight.go`) always wrote an empty coach-equipment blob
      (`PutUint16(0)`). Confirmed via the decompiled client that this blob (read by
      `Coach.unserialize` with `options=EQUIPMENT`) DOES compose the coach's on-map sprite —
      each equipped card triggers `Coach.applyEquipment` → `setPartDescriptor`, swapping in the
      item's sprite parts (hat=`Chapeau`, cloak=`Cape`, weapon=`Arme`, etc.). The wire format is
      the exact 15-byte-per-card layout (`[int16 slot][int32 templateId][int64 uniqueId][int8
      flags]`) already implemented for `buildActorSpawn`/`buildCoachInformation`.
      - **Fix**: `duelTeamInfo` gained a `CoachEquipment []domain.CoachCard` field, loaded in
        `buildDuelTeam` via the existing `CoachService.GetEquippedCards` (filters `Pos != 0`);
        `buildCreateFight` now serializes it (via the shared `wireSlotForStoredPos` slot
        translation) instead of writing an empty blob. Non-fatal: a coach with no gear (or a
        load error) serializes an empty blob.
      - Test: `internal/dispatch/packets_fight_test.go`'s
        `TestBuildCreateFightIncludesCoachEquipment` (two equipped cards serialize to the correct
        slots/ids; a coach with none serializes empty).

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.21 Real-user bug reports (post-§8.20, July 2026): fighter facing, presentation stall,
teleporty movement (+ end-turn clarification)

Four items from the next play-test.

- [x] **Fighters should face the same way as their coach**: `defaultTeamFacing` now returns
      team 1 → `NORTH_WEST` and team 2 → `SOUTH_EAST`, matching each team's coach facing
      (`buildActorAppearForFight`), so a team's fighters look the same direction as their coach
      (toward the battlefield), not away.

- [x] **Coaches face the wrong way (back to the field)** — corrected here: coach A `NORTH_WEST`,
      coach B `SOUTH_EAST` (§8.20's first attempt had them swapped, facing away, per an in-game
      screenshot).

- [x] **Bug — presentation stalls at "waiting for opponent to be ready" even though both clicked
      Prêt**: root cause was a **redundant 8012 broadcast during the pre-fight TELEPORT gate**
      (`handleTeamMateSetReadyForPlacement`). The teleport gate's real output is
      ENTER_WORLD_INSTANCE + START_PRESENTATION; it also (needlessly) broadcast an 8012 ack. The
      client's `NetFightPresentationFrame` (registered only once START_PRESENTATION arrives) could
      then receive that queued teleport-gate 8012 and mistake it for a PRESENTATION-phase ready
      ack — auto-dismissing the "Prêt" dialog and showing "waiting for opponent" **before the
      player ever clicks Prêt**. That client then never sends its real presentation-skip 8011, so
      the server never reaches two votes and never sends END_PRESENTATION → the fight hangs in
      presentation. **Fix**: the teleport gate no longer broadcasts 8012 at all; the 8012 ack is
      sent only for the genuine PRESENTATION-phase vote (`handleCoachReadyPresentation`, §8.19).
      Removed the now-dead `dispatch.buildTeamMateSetReadyForPlacement` (the combat package has its
      own). E2e helpers (`startFullFight`, the presentation/placement-timeout tests, the load
      test) updated to no longer expect the teleport-gate 8012.

- [x] **Bug — fighter movement teleports / looks weird / sometimes doesn't move**: confirmed via
      the client that its move animator (`MoveAction`/`PathMobile`) treats the FIRST cell of the
      FIGHTER_MOVE (4524) path as the fighter's CURRENT position and interpolates from it toward
      the next. But the Go server's A* path (`FindPath`/`reconstructPath`) EXCLUDES the start
      cell, so the fighter visibly jumped one cell at the start of every move, and a 1-cell move
      (wire path length 1) snapped to the destination with no walk animation at all. **Fix**:
      `handleFighterMove` (`internal/combat/turns.go`) now PREPENDS the fighter's current cell to
      the wire path before broadcasting (`wirePath = [start] + A*path`), giving the animator its
      origin. MP cost and per-step trap checks still use the A* path WITHOUT the start cell (the
      start cell costs no MP and isn't an entered cell). New unit test
      `TestFighterMoveWirePathIncludesStartCell` (asserts path[0] == start, path[last] == dest, MP
      debited by 1 for a 1-cell move) + e2e assertion in
      `TestE2E_FighterCanMoveFromRealPlacementCell` that FIGHTER_MOVE's path[0] equals the
      fighter's start cell (new `testClient.tryExpectFrame` helper).

- **Clarification — "end-turn still waits 30s"**: investigated and confirmed **NOT a bug**. A
      manual end-turn (`FIGHTER_END_TURN_REQUEST`) correctly cancels the acting fighter's turn
      clock and advances immediately (`askForFighterEndTurn` → `cancelTurnClock` → `startNextTurn`).
      The 30s the user observed is the OPPONENT's fresh turn clock (a new 30s is armed for whoever
      goes next); if the opponent doesn't manually end their turn, waiting out their clock is
      correct. Added `TestFight_ManualEndTurnAdvancesImmediately` (a 30s clock that must be skipped
      by a manual end-turn) and `TestFight_EndTurnRejectedForForeignFighter` (a coach can't end the
      opponent's turn) to lock in the correct behavior, since the happy path was previously
      untested.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.22 Real-user bug (post-§8.21, July 2026): presentation "VS" panel not dismissed by
"Prêt" — corrected flow model (there is NO separate teleport gate)

The presentation VS panel kept showing after both players clicked "Prêt", only vanishing after
the 20s countdown. A file-based packet trace (new logging feature below) plus disassembly of
the ACTUAL running client (`E:\Ankama\DofusArena2-06\game\core.jar`, via `javap`) finally
corrected a flawed mental model that §8.13/§8.19/§8.21 were all built on.

- **New logging feature (packet traces to file)**: `internal/config.LoggingConfig.Dir` (koanf
  `dir`), wired through `internal/log.New` (tees every log line to a fresh timestamped file
  `logs/server-YYYY-MM-DD_HH-MM-SS.log` via `zerolog.MultiLevelWriter`; newest file = latest
  run). Set `dir: logs` in `configs/config.dev.yaml`. This let the trace be read from disk
  instead of copy-pasted.

- [x] **Corrected flow model**: the trace showed only TWO inbound 8011s total, BOTH right after
      CREATE_FIGHT (before START_PRESENTATION), and ZERO inbound packets between START_PRESENTATION
      and the 20s-later END_PRESENTATION. Disassembly of the real client proved:
      - `TeamMateSetReadyForPlacementRequestMessage` (8011) is emitted by EXACTLY ONE class in
        the entire client: `UIFightPresentationFrame` (the VS panel's "Prêt" → UI event 18009).
        There is **no** auto-ready and **no** separate pre-fight "teleport gate" 8011. The
        pre-teleport team-selection "Prêt" sends a DIFFERENT opcode (4303 SET_READY_FOR_FIGHT).
      - So the "two 8011s right after CREATE_FIGHT" ARE the presentation "Prêt" votes (the panel
        eagerly fires 18009 as it loads) — the same button, not two different gates.
      - The client hides its VS panel only on receiving an **8012 ack for its own coach id**
        (`NetFightPresentationFrame` case 8012 → `removeFrame(UIFightPresentationFrame)`), and
        fully leaves presentation on **8018 END_PRESENTATION** (or its own local 20s clock).
      - **The §8.21 change was wrong**: it removed the 8012 ack (believing it a "redundant
        teleport-gate ack"), so the client's panel never got the ack it needs to dismiss —
        leaving it up until the local 20s clock. And the whole "teleport gate vs presentation
        gate" split (§8.13/§8.19) was an artifact of this misunderstanding.
      - **Fix (single ready gate)**: `tryPrepareCreateFight` now sends CREATE_FIGHT and then
        IMMEDIATELY runs `startPresentationForDuel` (teleport + `instantiateFight` + ACTOR_APPEAR
        + START_PRESENTATION) — no longer gated behind an 8011, since CREATE_FIGHT is what makes
        the client show the VS panel and the map/fighters must exist by then.
        `handleTeamMateSetReadyForPlacement` (opcode 8011) is now purely the presentation-ready
        vote: it routes to the fight actor's `handleCoachReadyPresentation` (§8.19), which
        broadcasts the 8012 ack (real coach id — so each client dismisses its own panel and
        shows "waiting for opponent") and, once both coaches vote, ends presentation →
        START_PLACEMENT. `armPlacementReadyTimeout` → `armPresentationReadyTimeout` now
        force-votes both coaches through the fight actor as a prompt fallback (the fight's own
        presentation clock, and the client's local 20s clock, are additional independent
        fallbacks — matching the user's "both: Prêt skips, else countdown auto-proceeds"
        requirement).
      - Tests updated: `startFullFight`, `TestE2E_PresentationReadySkipsPresentationClock` (now
        asserts the 8012 ack carries the real coach id), the renamed
        `TestE2E_PresentationReadyTimeoutForcesEnd` (only-one-coach-votes fallback), the
        SET_READY_FOR_FIGHT no-double-fire test, and the load test — all updated to the new
        "presentation starts right after CREATE_FIGHT; 8011 is the presentation-ready vote" flow.

All new/changed code builds cleanly, passes `go vet`, and the full test suite (including
`test/e2e`) is green with zero regressions, confirmed additionally under `-race` for both
`internal/...` and `test/e2e/...`.

---

## 8.23 High-value combat-fidelity pass (post-§8.22): crit spells/cards, tackle
evasion scaling, summon stats, reactive trigger-bus, esquive PA/PM verification,
state bundles (STATE_APPLY)

This pass cleared the five "high value / affects fidelity" items tracked in the root
`FEATURES-STATUS.md` §9. All are in `internal/combat/`; the full suite (`internal/...` +
`test/e2e/...`) is green under `-race` with zero regressions.

- [x] **HV1 — Reactive trigger-bus (§8.11 item 2 remainder, `TriggersBefore`/
      `TriggersAfter`)**: new `triggerbus.go`. Effects declaring a before/after listen-set
      are now DEFERRED at cast time (stored on the carrier via `deferReactiveEffect`,
      `Fighter.ReactiveEffects`) instead of executed instantly — mirroring
      `RunningEffect.mustBeTriggered()`→`RunningEffectManager.storeEffect`. They fire on the
      matching in-fight event via `fireTrigger`: `trigOnAttacked`(2, wired in
      `applyDamageFromEffect` after a real attacker deals HP loss — nil-attacker/reactive
      returns skipped to avoid ping-pong), `trigOnAPUse`(55, wired in `broadcastCharacUse`
      for AP spends), and `trigTableTurn`(1001, wired into the existing table-turn tick in
      `duration.go`'s `tickFighterActiveEffects`→`tickReactiveEffects`, which also ages out
      expired reactive effects). Scope is FOCUSED on exactly the trigger ids this project's
      real `spells.dat`/`events.dat` listen-sets use (empirically 2/52/54/55/56/64/1001; a
      one-off diagnostic scan found only ~14 triggered effects total, all in spells/events,
      none in cards) rather than the full generic BitSet activation engine — an unlisted id
      degrades gracefully (stored but inert). **Bonus fix**: `AreOpponents` now nil-guards
      (a reactive/environmental effect may have no fighter caster; previously this could
      panic in the `EffectHPLoss` path). Tests: `TestReactiveEffect_*`,
      `TestEffectMustBeDeferred` (`highvalue_test.go`).
      **Still deferred**: `EndTriggers` (parsed `null` in this data — nothing to wire) and
      state-bundle expansion (see the HV note below).

- [x] **HV2 — Critical damage for spells/cards**: the reference model (confirmed via
      `EffectContentDocumentLoader.readAndLoadEffect`'s `effectIsCritical` byte → `Effect`
      flag bit 1 → `AbstractSpell.addEffect`'s `checkFlags(1L)` → `canCanBeCritical`) is that
      a container holds BOTH normal and (separately-authored) critical effects.
      `selectEffectsForCrit`/`executeEffectsForHit` (`effects.go`) now run the `IsCritical`
      subset on a crit, the non-critical subset otherwise, with fallbacks so a cast never
      no-ops when its authored effects don't match the rolled hit type.
      `handleSpellCast`/`handleCardUse` pass the rolled `crit`. `EffectDef.IsCritical` was
      already parsed. Real data confirms impact (108 spell + 84 card critical-flagged
      effects). Tests: `TestSelectEffectsForCrit_*`.

- [x] **HV3 — Esquive PA/PM decision**: DECIDED and VERIFIED, no behavior change.
      Cross-checked the decompiled reference (`APLoss`/`MPUse`/`CharacLoss` +
      `FighterCharacteristicType` ids 1-34): this game has NO probabilistic AP/MP-loss dodge
      and no evasion/dodge characteristic — the faithful port is exactly the existing
      deterministic `ResAPLoss`/`ResMPLoss` percent-resist (`effects.go`'s `applyResistance`).
      Locked with a regression test proving the same resist yields the same loss with zero
      RNG variance (`TestEsquiveAPMP_IsFlatResistNotRoll`), plus doc/comment updates so this
      is recorded as verified-correct rather than a gap.

- [x] **HV4 — Tackle Evasion scaling (§8.11 item 13)**: `tackle.go`'s flat-67% base is now
      shifted by the per-side Evasion difference via `evasionChanceAgainst` (evader's
      `EvasionBonus` raises it, the tackler's "grip" lowers it, clamped [0,100]), honoring
      the manual's "modified by the Evasion characteristic per-side" wording.
      `Fighter.EvasionBonus` is a deliberately NON-wire field (the reference
      `FighterCharacteristicType` enum ends at id 34 `DMG_REBOUND` with no evasion stat, and
      the whole tackle mechanic is greenfield — the client's `TackleAction` is a cosmetic
      no-op), so this keeps the wire-exact characteristic enum untouched. Default 0
      reproduces the exact prior flat-67% behavior. Tests: `TestEvasionChanceAgainst_*`,
      `TestAttemptEvadeTackle_*`.

- [x] **HV5 — Summon stat-template wiring**: a plain `EffectSummon` (actionID 67) now
      resolves its `SummoningTemplate` and sets the summon's real HP/AP/MP. The reference
      `Summon.computeValue()` reads `params[0]` into `m_value` and `execute()` passes it to
      `summonCreature(newId, cell, m_value)` as the template reference id — so
      `applySummonTemplateStats` (`effects.go`) looks that template up in
      `gamedata.Summonings` and overrides HP/AP/MP (each only if the template specifies a
      positive value; missing template → keep the breed-derived fallback so a summon never
      fails). `EffectSummonDouble`/`EffectSummonMirror` correctly keep the caster's breed
      stats (they're clones of the caster, not distinct creatures). Tests:
      `TestApplySummon_UsesSummoningTemplateStats`,
      `TestApplySummon_FallsBackToBreedWhenTemplateMissing`.

All new/changed high-value code builds cleanly, passes `go vet`, and the full test suite
(including `test/e2e`) is green with zero regressions, confirmed additionally under `-race`
for both `internal/...` and `test/e2e/...`.

### HV6 — State bundles (`ApplyState` / `STATE_APPLY`, actionID 112) — now implemented

The previous §8.23 note deferred state-bundle expansion as "blocked by missing state data."
A follow-up research pass turned this from a bare data-limited no-op into a **complete,
forward-compatible mechanic** (`internal/combat/state.go`), while confirming the data
situation precisely.

- [x] **Ported `State`/`StateManager`/`ApplyState`**: `State` is a `(baseId, level)`-keyed
      bundle of sub-effects (`StateUniqueID = (baseId<<8)+level`, byte-exact to
      `State.getUniqueIdFromBasicInformation`) plus its `EndTriggers`. `stateManager` is a
      per-fight registry (`RegisterState`/`lookupState`, mirroring
      `StateManager.addState`/`getState`). `applyStateBundle` ports
      `ApplyState.computeValue` (params must be `[baseId, level]`; wrong count → no-op) +
      `ApplyState.execute` (resolve the state, then execute EVERY sub-effect against the
      target through the SHARED executor — a triggered sub-effect defers onto the carrier
      via the HV1 trigger-bus, an instant one runs now — then note the state's endTriggers).
      Wired into `applyRunningEffect`'s `EffectStateApply` case.

- [x] **Definitively established the data situation** (delegated to a thorough explore
      sub-agent + an empirical data scan):
      - State DEFINITIONS are genuinely absent from every artifact: no `StateLoader` (every
        other effect-container type — spells/cards/events/summoning/staticEffects — has one;
        State does not), no `states.dat`/state file, no `CONTENT_STATE_FILE` config key, no
        `src/org/ankarton` server source. `StateManager.addState()` is never called
        anywhere. Ankama's own client comment (`ApplyState.java:100`, "State inconnu pour le
        client, mais vraisemblablement pas pour le serveur") confirms states were
        server-side data never shipped in `core.jar`.
      - **STATE_APPLY (actionID 112) is used ZERO times** across this project's real
        `spells.dat`/`cards.dat`/`events.dat` (confirmed by a one-off scan). No spell, card,
        or event in the shipping game applies a state.
      - Therefore there is nothing to faithfully reproduce and nothing that exercises the
        path in practice. Fabricating arbitrary state bundles would be inventing game design,
        not porting — deliberately NOT done. The registry is empty by default and the
        mechanic is inert-and-logged until a real state table is ever sourced (via
        `RegisterState`), the exact "built, awaiting optional data" posture as
        `specialcells.go`/`effectarea.go`.

- [x] Tests (`state_test.go`, 7): unique-id formula, register/lookup, full expansion into
      sub-effects (HP-loss + MP-loss bundle applied to a target), unregistered-state
      broadcast-only no-op (the current universal case), wrong-param-count no-op, a triggered
      sub-effect correctly deferring rather than executing, and the end-to-end path through
      `applyRunningEffect`'s `EffectStateApply` kind.

**Still not applicable**: `EndTriggers` at the effect level is parsed as `null` in this
project's effect data (nothing to wire); the state-level `EndTriggers` are carried on `State`
and noted for whenever real state data with a persistent-bundle model needs them.

All new/changed code (HV1-HV6) builds cleanly, passes `go vet` (`internal/...` +
`test/...`; the unrelated `cmd/studio` GUI shell has a pre-existing missing `wails`
dependency), and the full `internal/...` + `test/e2e/...` suite is green with zero
regressions under `-race`.

---

## 8.24 Verification & hardening pass (post-§8.23): rebound double-count fix, trigger
emission completion, A*/6011 verification, Phase N test completion

A verification pass on the remaining open 🔬 items in `FEATURES-STATUS.md` §9, plus
test-hardening. One item (rebound) turned out to be a genuine bug; the others were verified
correct and pinned with tests. Full `internal/...` + `test/e2e/...` suite green under
`-race` (~155s e2e), zero regressions.

- [x] **V2 — Rebound double-count (REAL BUG, fixed)**: `DAMAGES_REBOUND_IN_PERCENT`
      (`DmgRebound`) was being applied TWICE for every spell/close-combat hit — once inside
      `ComputeHPLoss` (correct: mirrors `HPLoss.computeValue()` lines 312-331, which deals
      rebound% straight to the caster and subtracts it from the delivered damage) and AGAIN
      in the reactive damage-return step of `applyDamageFromEffect`. Cross-checking the
      decompiled `HPLoss.java` confirmed the reference has NO separate reactive rebound: only
      `STRIKE_BACK` (a distinct `RunningEffect` keyed to the "was attacked" trigger) is
      reactive; `DmgRebound` belongs entirely to the damage formula. Fix: removed `DmgRebound`
      from the reactive-return step in `combat_actions.go` (kept `StrikeBackPercent`), so
      every rebound now applies exactly once. Regression tests:
      `TestReboundAppliedExactlyOnceEndToEnd`, `TestReboundNotAppliedTwiceForCloseCombatStyleDamage`.

- [x] **V1 — Trigger-bus emission completion**: HV1 emitted `trigOnAttacked(2)`/
      `trigOnAPUse(55)`/`trigTableTurn(1001)`, but a few real spells (spell-rebound 138/203,
      poison 134, debuff 135) also LISTEN for the niche characteristic-operation triggers
      52/54/56/64 — previously stored-but-inert. Added `characteristicTriggerEmission`, a
      `(EffectKind, CharacteristicType) → trigger id` table ported from the reference
      `setTriggersToExecute()` switches (crit-rate/AP loss→52, crit-rate debuff→54, crit-rate
      leech→56, dmg debuff→64), fired via `emitCharacTrigger` from the `EffectCharacLoss`/
      `CharacDebuff`/`CharacLeech` handlers. Every trigger any real spell listens for is now
      emitted. Tests: `TestEmit_*`.

- [x] **V3 — A* height-blocking verification (Phase K)**: `FindPath`/`ValidateClientPath`
      already consulted `ArrivalAltitude` (`blocked=true` divert + `maxAscend`/`maxDescend`
      delta rejection) — but the existing `openField`/`blockedField` mocks always returned
      `blocked=false`, so those branches were untested. Added a height-aware `heightField`
      provider (blocked cells, cliffs, drops, altitude-carrying) AND a **real-map**
      integration test (`fightMapID=2` via `MapStore.Get(2)` + `SetMapData`) that routes A*
      over actual `.amw`/elements.ade walkability+altitude and rejects off-map goals. Verified
      correct, no code change. Tests: `TestFindPath_*` (height), `TestFindPath_RealMap*`
      (`pathfind_realdata_test.go`).

- [x] **V4 — 6011 spell-blob entry size verification**: confirmed against the decompiled
      reference that the SPELL inventory blob is serialized via
      `StackInventory<Spell>(serializeQuantity=false)` (`StackInventory.serialize` writes only
      `item.serialize()` = `putInt(id)` → **4 bytes/entry**, no pos/quantity), while the
      EQUIPMENT/card blob uses `ArrayInventory` (`putShort(pos)` + `putInt(id)` → **6
      bytes/entry**). The Go `parseSpellIDs`(4)/`parseInventoryIDs`(6) split is correct;
      `parseSpellIDs` was previously untested. Verified correct, no code change. Tests:
      `TestParseSpellIDs*`, `TestBuildSpellBlobIsFlatInt32Array`,
      `TestParseSpellIDsRejectsCardFormatMisread`.

- [x] **V5 — Phase N test completion**: per-`EffectKind` coverage completed by filling the
      last gap (`EffectAdaptLook`, a cosmetic broadcast-only kind). Added damage-formula
      property tests for the flat-resist-before-percent ordering invariant
      (`value*(100+mod)/100` applied last, not to the raw base) and the
      physical-bypasses-Dmg/Res correction. Tests: `TestEffectAdaptLook_IsNoOpOnCombatState`,
      `TestComputeHPLoss_FlatResistAppliedBeforePercent`,
      `TestComputeHPLoss_PhysicalIgnoresDmgAndRes`.

All V1-V5 changes build cleanly, pass `go vet`, and the full `internal/...` + `test/e2e/...`
suite is green with zero regressions under `-race`.

---

## 8.25 Card wagering (bet fights) — implemented end-to-end (§8.11 item 8, previously
"needs design")

Item 8 in §8.11 ("card-wagering payload in END_FIGHT is always empty") was long marked as
needing product design. Research settled it: the pbworks community wiki documents the OLD
beta13 browser game (fixed 5000-kamas team budget, no card collection, no wagering) and does
NOT describe this mechanic — the authoritative source is the decompiled client, which has a
**fully-built wagering wire protocol**. The "always empty" characterization was wrong.

**Ground truth from the client decompile:** the wagered entity is the **CoachCard**
(cosmetic/equipment cards, NOT FighterCard); coaches lock ≤10 cards to protect them (opcode
5203, already handled here); the bet is an `int bet` 0/1 on matchmaking/invite/CREATE_FIGHT
(already plumbed); `EndFightMessage` (8300) carries won/lost/bonus card arrays (per card:
`int templateId` + `byte cursed`); the transfer itself is server-authoritative (the client
only displays the lists).

Chosen rule (per the design decision): **each coach stakes ONE random non-locked card; the
winner takes the loser's stake.** Bet-off = no stake, no transfer.

- [x] **Service (`internal/service/coach.go`)**: `SelectStakeCard` (random unlocked, pos=0,
      quantity>0 card via `ORDER BY RANDOM()`); transactional `TransferCard` (moves one unit
      loser→winner, re-verifies ownership/unlocked/unequipped at settle time to survive a
      mid-fight lock/equip race, decrements multi-quantity stacks, stacks onto the winner's
      existing same-template card, preserves the cursed flag, returns the template+cursed for
      the END_FIGHT blob). `LockCard` now enforces the client's **10-card cap**
      (`MaxLockedCards`), idempotently (re-locking the same card doesn't consume the cap) and
      preserving the cursed bit.
- [x] **Duel (`internal/world/duel.go`)**: added a mutex-guarded `stakeCards` map +
      `SetStakeCard`/`StakeCard`, so the stake selected during CREATE_FIGHT setup survives to
      the fight-end transfer (the end hook reaches the duel via `deps.Duels.GetByCoach`).
- [x] **Fight creation (`internal/dispatch/handlers_fight.go`, `packets_fight.go`)**:
      `prepareCreateFight` selects a stake per coach for a bet fight; if either coach has no
      eligible card it rejects with **cancel reason 47** (`cantHoldTheBet`, the client's own
      code). Stakes are threaded via a new `duelTeamInfo.BetCard` field and written into
      CREATE_FIGHT's per-team bet-card list (previously a hardcoded `count=0`), so the client
      shows what's at stake (`FightCreationMessage` → `coach.addBetCoachCard`).
- [x] **Fight end (`internal/dispatch/fightend_hook.go`, `combat/fightend.go`,
      `combat/fightresult.go`)**: `settleWageredCards` transfers each loser's stake to the
      winner (via `TransferCard`) and records the results in each coach's
      `FightEndOutcome.LostCards`/`WonCards` (new exported `FightEndCard` fields, since the
      dispatch package can't build the internal `cardBlobEntry`). `endFight` maps those into
      `cardBlobEntry` and passes them to `buildEndFight` (previously `nil, nil`), so END_FIGHT
      now carries the real lost/won card blobs. The flee/forfeit path settles too (the
      forfeiter is the loser). A draw (no winner) transfers nothing.
- [x] **Tests**: `internal/service/wagering_test.go` (lock cap + idempotency + cursed
      preservation; stake eligibility skips locked/equipped/empty; transfer moves ownership,
      decrements stacks, preserves cursed, skips locked, no-ops on a gone card);
      `internal/combat/endfight_cards_test.go` (END_FIGHT card payload + cursed round-trip +
      empty-blob); `TestBuildCreateFightBetCardPayload` (stake list on the wire); and a full
      **e2e** `TestE2E_BetFightTransfersStakedCardToWinner` (seed cards → bet matchmaking →
      CREATE_FIGHT announces both stakes → forfeit → winner gained the loser's card, loser
      lost it, winner kept his own).
- [x] **Robustness bonus**: fixed a PRE-EXISTING flaky lifecycle test
      (`TestFight_CloseCombatDealsDamageAndEndsFight`) whose 2s/1s phase-progression deadlines
      were too tight under `-race` + full-suite parallelism (the fight was correct, its actor
      goroutine was just scheduling-starved); bumped both to 10s.

**Deferred (small/optional)**: the END_FIGHT `bonusCards` array (server sends none — it was
for a separate bonus-reward flow no content drives); value-matched or multi-card staking (the
wire supports arrays, but the one-random-card rule was the chosen design).

All changes build cleanly, pass `go vet` (`internal/...` + `test/...`; the unrelated
`cmd/studio` GUI shell still has its pre-existing missing `wails` dependency), and the full
`internal/...` + `test/e2e/...` suite is green with zero regressions under `-race`.

---

## 8.26 Per-round EVENT CARDS — implemented (wiki-gap audit follow-up)

A pass reading the community wiki's game-mechanics pages against the code + decompiled client
found that almost everything the wiki describes is either implemented or **correctly absent**
(Levels/Skills/XP, Lethal Blows, TileDrop's round-15 board-shrink, and the Charge/Combos
geometric spell system are **beta13-only features removed in this card version** — verified
absent from the reference client, not just unimplemented). The **one genuine, version-relevant
gap** was the per-round event card, now built.

**Mechanic (server-authoritative, matching the decompile):** the wiki's Round page — "At the
start of each round a random event card which affects the whole round is drawn." Confirmed via
the decompiled client that this is server-driven: `NEW_TABLE_TURN_BEGIN` (8100) carries only an
`int eventId`; the client resolves it (`AbstractEventManager.getAbstractEventFromId`) purely to
DISPLAY the card (`NewTableTurnAction` → `Fight.addEvent` → UI property), and the SERVER applies
the effects. Previously the Go server hardcoded `eventId 0` (`buildNewTableTurnBegin`) and
applied nothing.

- [x] **Draw (`internal/combat/events.go`)**: `selectEventID` deals from a shuffled deck of
      every event id in `gamedata.Events` (`buildEventDeck` sorts ids for a stable pre-shuffle
      order, `shuffleEventDeck` Fisher-Yates with the fight RNG → deterministic under
      `SetRNGSeed`), reshuffling once exhausted so every event appears once per cycle (mirrors
      re-calling `AbstractEventManager.newShufflizedEvents`; the data has no weight/probability
      field). Returns 0 (inert) when no event data is loaded.
- [x] **Apply (`applyDrawnEvent`/`applyEventEffects`)**: each event's effects apply to EVERY
      living fighter (the event "affects the whole round"). Empirically, every event effect in
      the real `events.dat` carries `areaShape=32767` (AreaEmpty) — the reference's "no
      cell-area; target set is the whole round" sentinel — which resolves to ZERO cells in the
      normal cast path, so events deliberately bypass cell-area resolution and apply directly.
      Each effect is self-applied per fighter (fighter as both caster and target), so the stat
      handlers that read caster characteristics work without nil-caster casing and self==target
      correctly skips the rebound/opponent guards. Round-scoped effects (all events are
      `duration=[1,0]`) expire at the next table-turn via the existing duration tick.
- [x] **Wire (`combat/packets.go`, `combat/turns.go`)**: `buildNewTableTurnBegin` now takes the
      drawn `eventId`; `startNextTurn` selects the id, broadcasts `NEW_TABLE_TURN_BEGIN` FIRST
      (the client must instantiate the card before the effect actions reference the round), then
      applies the effects.
- [x] **Tests**: `combat/events_test.go` (inert-without-data; deals every event once before a
      repeat; deterministic under seed; heal + damage events hit all fighters; nil-attacker
      damage doesn't panic; wire eventId round-trip) and e2e
      `TestE2E_NewTableTurnCarriesEventCard` (real fight → first `NEW_TABLE_TURN_BEGIN` carries a
      real non-zero event id from `events.dat`). Updated the shared `startFullFight` e2e helper
      (and the lifecycle/forfeit/move tests that build on it) to `drainUntil(FIGHTER_TURN_BEGIN)`
      past the new event-effect `RUNNING_EFFECT_ACTION` frames.

**Bonus cards NOT applicable**: the wiki's "1–3 bonus cards dealt to team leaders, playable
once" is a **beta13-only** feature. The card-version client has no bonus-card-play opcode and no
team-leader-hand concept (the only in-fight castable is the fighter card via
`FIGHTER_CARD_USE_REQUEST` 8107; the CREATE_FIGHT/END_FIGHT "bonus/bet" cards are the wagering
`BetCoachCard`s handled in §8.25, not a playable hand). Nothing to implement.

All changes build cleanly, pass `go vet`, and the full `internal/...` + `test/e2e/...` suite is
green with zero regressions under `-race`.

---

## 8.27 Special cells & fight-map data — verified already-implemented; test coverage closed

A re-audit of the "special cells never populated / fight map hardcoded to 2" item (long carried
as an open gap, and repeated in a stale memory) found it was **already fully implemented** in an
earlier pass. This entry records the verification and the coverage gaps closed — no behavioral
change was needed.

**Confirmed already in place:**
- **Random fight-map selection**: `selectFightMapID` (`dispatch/handlers_fight.go`) draws a
  random map from `MapStore.FightMapIDs()` (`gamedata/map.go`), the 15 imported fight maps
  (ids 2–16); `defaultFightMapID = 2` is only the fallback when discovery fails. NOT hardcoded.
- **Per-map special-cell data**: authored as JSON sidecars `data/maps/<id>/specialcells.json`
  (`gamedata.SpecialCellStore`), since the `.amw`/`elements.ade` carry no special-cell markers
  (verified against the decompiled client: `StaticEffectLoader`/`StaticEffectAreaManager` key
  templates by baseId only, with no map/coordinate association; the client gets coordinates
  purely from the server's CREATE_FIGHT packet, `FightCreationMessage.decode`). All 15 maps have
  a sidecar with 6 cells each.
- **Wiring**: `resolveSpecialCellRenders` fills the CREATE_FIGHT special-cell list, and
  `applySpecialCells` (from `instantiateFight`) registers both the gameplay cells
  (`SetSpecialCell`) and render tuples (`AddSpecialCellRender`).

**Coverage gaps closed (this pass):**
- [x] `test/e2e/specialcells_test.go` — `TestE2E_CreateFightCarriesRealSpecialCells`: a live
      duel's CREATE_FIGHT carries the map's 6 authored cells with valid cellBaseIds (the full
      data→wire pipeline in a real fight, previously only unit-tested in isolation).
- [x] `dispatch/fightmap_selection_test.go` — `selectFightMapID` returns only real pool members,
      actually **varies** across 200 draws (proving it isn't stuck on one map), and falls back to
      the default without data; `resolveSpecialCellRenders`/`applySpecialCells` populate from a
      real map (valid baseIds, 1-based cell ids, both gameplay + render cells registered).

Pre-existing coverage (unchanged): `gamedata/fightmaps_realdata_test.go`,
`combat/specialcells_test.go` (all 8 cell types trigger at turn start),
`dispatch/packets_fight_test.go` (CREATE_FIGHT special-cell serialization + type mapping).

Free-placement **zone restriction** was the one smaller item still open here; it is now done —
see §8.28.

> **⚠️ SUPERSEDED by §8.29 (below).** The claim above that "the `.amw`/`elements.ade` carry no
> special-cell markers" was **wrong**. The special tiles ARE baked into every map's art as
> negative-gfx `Bonus` elements, and the per-map layouts are now *derived* from that data, not
> hand-authored. The 6-cell JSON sidecars described above have been removed.

---

## 8.29 Special cells — CORRECTION: derived from the maps' baked negative-gfx `Bonus` tiles

§8.27 concluded the per-map special-cell coordinates were unrecoverable and had to be authored by
hand (6 invented cells per map in `data/maps/<id>/specialcells.json`). **That was incorrect.**
Scanning the resolved cell gfx across all maps (`cmd/studio` `TestDebugMapGfxFreq`) shows every
map paints its special tiles into its own art as `Bonus` elements whose resolved gfx is
**negative** (`−1002..−1009`). Those are the authentic positions; the hand-authored sidecars were
both misplaced *and* mis-typed (e.g. map 8 had labelled cellBaseId 1008 as *killer* when the real
effect is *motivation*).

**What changed:**
- `gamedata.Map.deriveSpecialCells` (`internal/gamedata/map.go`) collects each cell's negative-gfx
  `Bonus` markers at load time into `Map.SpecialCells()`. The gfx magnitude is the client
  render-template id (the `staticEffects.dat` SPECIAL area id `1002-1009`).
- `gamedata.specialCellBaseIDToType` (`internal/gamedata/specialcells.go`) is the single
  gfx/baseId → gameplay-type table, cross-checked **field-for-field against the real
  `staticEffects.dat`** (each area's effect actionID/params — see `04-game-data-format.md` §4.5):
  1002=killer, 1003=trap, 1004=eagle_eye, 1005=shield, 1006=panacea, 1007=enthusiasm,
  1008=motivation, 1009=healing_heart.
- `SpecialCellStore` now takes a `*MapStore` and returns the **derived** layout by default; an
  optional `data/maps/<id>/specialcells.json` **overrides** it. The 16 wrong sidecars were
  deleted, so all maps use derivation.
- Studio: `App.GetSpecialCells` reads through the store (so the viewer shows the derived cells),
  and the frontend `SPECIAL_TYPES` palette base ids were corrected to `1002-1009`.

**Also fixed while here:** `internal/gamedata` real-data tests used `../../../data` (three levels
up) which resolves *outside* the repo, so they had been silently **skipping**. Corrected to
`../../data` (`server/data`), so `fightmaps_realdata_test.go` now actually runs.

**Coverage:** `TestSpecialCellDerivationGroundTruth` (maps 2 & 8 derived cells match the
scan), `TestSpecialCellLayoutDerivedFromMapArt` (212 cells across 16 maps; every `cellBaseId`↔
`Type` agrees), updated `TestE2E_CreateFightCarriesRealSpecialCells` (valid baseIds 1002-1009,
no longer assumes exactly 6). Full build + vet + `go test ./...` (incl. e2e) green.

All changes build cleanly, pass `go vet`, and the full `internal/...` + `test/e2e/...` suite is
green with zero regressions under `-race`.

---

## 8.28 Free-placement zone restriction — implemented

The last open combat-fidelity item. During the PLACEMENT phase,
`Fight.handleMoveToFreePlacement` (`combat/phases.go`) previously accepted **any** walkable,
unoccupied cell; it now also rejects any cell outside the fighter's own team's placement zone.

**Zone = the team's `FightStartPointElement` (kind-1000) cells, keyed by team side** — verified
against the decompiled client: it only sends `MoveToFreePlacementRequest` (8021) when
`StartPointManager.containsTarget(teamId, target)` is true, and that set is built solely from
type-1000 elements split by `getParams()[1]` team id (`StartPointManager.java:147-149`,
`UIFightPlacementFrame.java:109`, `FightStartPointElement.java:16-20`). NOT a map-half, NOT any
walkable cell.

The "per-map placement-zone data source" flagged as "not yet identified" in the old handler
comment turned out to be data the `.amw` parser **already extracts**: the kind-1000 start cells,
exposed as `Map.FightStartCells() map[byte][][2]int32`, keyed by `teamSideByte = TeamID-1` (same
convention `resolveFighterPlacementCells` / `buildCombatTeam` already use for initial
auto-placement).

- [x] `isInPlacementZone(fighter, pos)` (`combat/phases.go`): matches `pos` X/Y against
      `Map.FightStartCells()[fighter.TeamID-1]`; wired into `handleMoveToFreePlacement` after the
      walkable check. A rejected move applies nothing and broadcasts no 8022 (mirrors the
      existing occupancy/ownership silent-reject).
- [x] Permissive fallbacks (preserve prior behavior where the zone is unknowable): no map data
      attached (dev/test), TeamID 0, or a map with no start cells for that team side → allow.
      This is why the pre-existing `TestHandleMoveToFreePlacement_RejectsForeignFighter`
      (no-map-data) still passes unchanged.
- [x] Tests (`combat/placement_zone_test.go`): permissive-without-map-data; real-map
      (fightMapID 2) accept-onto-own-zone-cell + **reject an out-of-zone walkable cell** (the
      exact gap); every own-zone cell is legal. No dispatch/e2e placement-move tests existed to
      break (the e2e movement tests exercise action-phase A* movement, not placement).

The server-side reject is not in the decompiled artifact (client jar only), but the zone
definition is unambiguous from the client gate; this adds the authoritative server enforcement.

All changes build cleanly, pass `go vet`, and the full `internal/...` + `test/e2e/...` suite is
green with zero regressions under `-race`.
