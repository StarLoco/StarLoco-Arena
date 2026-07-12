# Fight Lifecycle: Creation & Phase Transitions

> Client source of truth, cross-checked against the current Go server implementation.
> Covers everything from `CREATE_FIGHT` (Send 8000) through the phase-transition state machine
> (presentation → placement → observation → action) up to the point where turn-based combat
> begins (see [08-fight-combat-engine.md](./08-fight-combat-engine.md) for 8100+ opcodes).
>
> This file **replaces `../02-protocol.md` §2.4.7** (CREATE_FIGHT), which contained
> "placeholder/guessed field names" — see the corrections section below.

---

## CREATE_FIGHT (Send 8000) — byte-exact payload

**Source of truth:** `client/com/ankamagames/dofusarena/client/network/protocol/message/game/serverToClient/fight/FightCreationMessage.java:46-173`
(`decode()`), which calls into `client/.../core/game/coach/Coach.java:463-485`
(`Coach.unserialize(ByteBuffer, int options)`) and
`client/.../common/game/fighter/AbstractFighter.java:543-571`
(`AbstractFighter.unserialize(ByteBuffer)`, inherited by `Fighter` — **not** `FighterInformation`,
see the note in §"Fighter.unserialize" below).

**Status:** implemented, but only as a simplified/empty-blob port of the legacy Java reference
server — see "Go server status" below.
**Go source:** `server/internal/dispatch/packets_fight.go:31-74` (`buildCreateFight`), wired
from `handlers_fight.go:72-89` (`prepareCreateFight`)

```
byte    errorCode                          // 0 = ok; any other value aborts decode here,
                                             // no further bytes are read
if errorCode == 0:
  short   serializedCoachCardsLen
  byte[]  serializedCoachCards              // StackInventory<Spell> blob, see note (1) below
                                             // shared by ALL coaches in the fight (both teams)
  int     fightTypeId                       // looked up via FightDefinitionManager;
                                             // 1=DEFY, 4=TRAINING per current server usage
  int     bet
  byte    teamCount
  repeated (teamCount times):
    byte    teamId
    byte    teamNameLen
    byte[]  teamName
    byte    coachCount
    repeated (coachCount times):
      <Coach.unserialize(buffer, options=2) payload>   // see "Coach.unserialize" below —
                                                          // options=2 means EQUIPMENT only,
                                                          // NOT position
      // -- initializeCoachSpellInventory(serializedCoachCards) consumes 0 bytes here;
      //    it just re-parses the blob read once at the top of the packet --
      byte    fighterCount
      repeated (fighterCount times):
        <Fighter.unserialize(buffer) payload>            // see "Fighter.unserialize" below —
                                                            // AbstractFighter's unserialize(),
                                                            // NOT FighterInformation
      short   statsReportLen                              // unsigned (buffer.getShort() & 0xFFFF)
      byte[]  statsReport                                 // only if statsReportLen > 0;
                                                            // opaque PlayerStatisticsReport blob
      byte    betCardCount
      repeated (betCardCount times):
        int     referenceCardId                           // -> new BetCoachCard(referenceCardId)
  byte    timelineFighterCount
  repeated (timelineFighterCount times):
    long    fighterId                                     // -> fight.addFighterToTimeline(id,
                                                            //    false, false)
    // CRITICAL: this list MUST be in INITIATIVE-DESCENDING order (the exact
    // order the combat engine plays turns / sends FIGHTER_TURN_BEGIN, i.e.
    // combat.BuildTurnOrder). The client adds these with ordered=false and
    // does NOT re-sort by INIT; it then accepts a FIGHTER_TURN_BEGIN only if
    // that fighter is the FRONT of its queue (TurnBasedTimeline.askForFighter
    // StartTurn no-ops otherwise). Emitting team-insertion order instead
    // desyncs turns -> a player can only act on the one fighter that happens
    // to line up, the rest are skipped/time out. Go: timelineFighterOrder()
    // in packets_fight.go reproduces BuildTurnOrder for this block.
  byte    eventCount
  repeated (eventCount times):
    int     eventId                                       // -> AbstractEventManager lookup
  byte    specialCellCount
  repeated (specialCellCount times):
    long    cellBaseId                                    // -> StaticEffectAreaManager lookup
    long    cellId                                        // instance id for this cell
    int     x
    int     y
    short   z
```

**Note (1):** `serializedCoachCards` is read **once** at the top of the packet (before any team
loop), then re-applied identically to **every** coach in the fight via
`coach.initializeCoachSpellInventory(serializedCoachCards)` — it is **not** per-coach data; every
coach ends up with the exact same `CoachSpellInventory` content. Format: `StackInventory<Spell>`
constructed with `serializeQuantity=false` → each entry is simply `int spellId` (4 bytes),
repeated until buffer exhausted. Max size 4 (coach can equip at most 4 fight spells).

**Other notes:**
- `fightTypeId` is read as a raw `int` but immediately truncated to `byte` for the
  `FightDefinitionManager` lookup — the upper 3 bytes are always zero in practice but are
  technically part of the wire format as a full 4-byte int.
- If `FightDefinitionManager.getDefinitionFromFightTypeId()` returns null, decode aborts (returns
  `false`) without reading any more of the packet — a hard client-side error condition.
- If a `BufferUnderflowException` occurs mid-parse for a coach or fighter entry, `unserialize()`
  returns `false`, the client logs an error, and **skips** adding that entity — but parsing
  continues with whatever remains, effectively desyncing the rest of the packet in practice. This
  is unrecoverable corruption, not a soft failure path.

### Coach.unserialize(buffer, options) — exact format

From `Coach.java:463-553`. `options` is a bitmask:

| Bit | Constant | Value | Meaning |
|---|---|---|---|
| 0x1 | `UNSERIALIZE_OPTION_POSITION` | 1 | world x/y/z + direction present |
| 0x2 | `UNSERIALIZE_OPTION_EQUIPMENT` | 2 | equipped coach-cards blob present |
| 0x4 | `UNSERIALIZE_OPTION_CARD_INVENTORY` | 4 | full inventory blob present |
| 0x8 | `UNSERIALIZE_LADDERS_STRENGTHS` | 8 | per-ladder ELO/strength map present |

**CREATE_FIGHT calls this with `options = 2`** (`FightCreationMessage.java:81`) — this **confirms**
the old docs' claim of `mode=2`, and confirms it means **equipment only, no position, no card
inventory, no ladders**. (For comparison, ACTOR_SPAWN's `Coach.unserialize(buffer, 11)` = `0x1|
0x2|0x8` = position + equipment + ladders strength, but not full card inventory — a genuinely
different bit combination, not just "more verbose" — see
[03-coach-world.md](./03-coach-world.md).)

Field order is **fixed** regardless of options (id/name and look are always present; optional
blocks are interleaved in this exact sequence):
```
long    coachId                            // unserializeIdAndName()
byte    nameLen
byte[]  name
if (options & 0x1):                        // unserializePosition() — NOT present for CREATE_FIGHT
  int     worldX
  int     worldY
  short   altitude
  byte    directionIndex                   // Direction8.getDirectionFromIndex(byte)
byte    skinColorIndex                     // unserializeLook() — ALWAYS present
byte    hairColorIndex
byte    sex
if (options & 0x2):                        // unserializeEquipment() — PRESENT for CREATE_FIGHT (options=2)
  short   equipmentBlobLen
  byte[]  equipmentBlob                    // ArrayInventory<CoachCard>.unserialize() format, see below
if (options & 0x4):                        // unserializeCardInventory() — NOT present for CREATE_FIGHT
  short   inventoryBlobLen
  byte[]  inventoryBlob                    // StackInventory<CoachCard>.unserialize() format
if (options & 0x8):                        // unserializeLaddersStrength() — NOT present for CREATE_FIGHT
  byte    ladderCount
  repeated (ladderCount times):
    byte    ladderId
    short   strength
```

**`equipmentBlob` sub-format** (`ArrayInventory.unserialize`):
```
while (bytesRemaining):
  short   position
  int     cardId          // \_ AbstractCoachCard.unserialize(buf), 13 bytes total per entry
  long    uid              // /
  byte    flags            // bit0 = locked, bit1 = cursed
```
(`inventoryBlob`, when present via bit `0x4`, is the same 13-byte-per-entry format but via
`StackInventory.unserialize` — no position prefix, each entry followed by a `short quantity`.
**Neither blob type is present in CREATE_FIGHT's coach entries** except the equipment one.)

### Fighter.unserialize(buffer) — exact format

**Important:** `Fighter` (subclass of `AbstractFighter`) does **not** override `unserialize()` — it
inherits it directly from `common/game/fighter/AbstractFighter.java:543-571`. This is a
**completely different code path and wire format** from `FighterInformation.unserialize()` (used
by `FIGHTER_CREATE_RESULT`/Send 6000, documented in
[06-fighter-team.md](./06-fighter-team.md)) — do not conflate the two.
`FighterInformation` only carries name/breed/sex/skin/serialized inventories/budget with no id and
no live characteristics; `Fighter.unserialize()` (this section) is the richer live-fight format:

```
long    fighterId
byte    breedId
byte    nameLen
byte[]  name
byte    sex
byte    skinIndex
// -> initializeCharacteristics() called client-side here (no bytes consumed; resets
//    HP/AP/MP/etc. to breed defaults before parsing spells)
short   spellsBlobLen
byte[]  spellsBlob            // StackInventory<Spell>.unserialize() -- per-fighter breed spells +
                                // equipped fight-spells, NOT the same instance as the coach spell
                                // inventory above
short   equipmentBlobLen
byte[]  equipmentBlob         // ArrayInventory<FighterCard>.unserialize()
```

Sub-formats:
- **spellsBlob**: `StackInventory<Spell>` constructed with `serializeQuantity=false` → each entry
  is simply `int spellId` (4 bytes, looked up via `SpellManager.unserializeContent`, wrapped in a
  `UsableSpell`), repeated until the blob is exhausted. No quantity field.
- **equipmentBlob**: `ArrayInventory<FighterCard>` (max size 6, one slot per equipment type:
  weapon/pet/cloak/hat/dofus/+1) → each entry is `short position (2 bytes)` + `int cardId (4
  bytes)`. 6 bytes/entry, repeated until blob exhausted.

If a `BufferUnderflowException` is thrown, `unserialize()` returns `false`, the client logs an
error and skips `coach.addFighter(fighter)` for that entry — again no early abort, so a truncated
fighter effectively desyncs the rest of the packet.

### Go server status / cross-check

- **CREATE_FIGHT is implemented** — sends real coach IDs/names/skin/hair/sex and real fighter
  IDs/breed/name/sex/skin, plus a correct timeline-fighter-id list.
- **Update (July 2026 bug-fix pass, `docs/08-java-parity-roadmap.md` §8.15):** fighter
  `spellsBlobLen`/`equipmentBlobLen` are **no longer hardcoded to 0** — this was a real,
  user-reported functional gap ("fighters don't have equipment and spells in fight"), now fixed.
  `buildDuelTeam` (`internal/dispatch/handlers_fight.go`) now loads each fighter's real loadout via
  `FighterService.LoadoutMaps` (the same source `buildCombatTeam` already used for the actual
  `combat.Fight`'s in-memory `Fighter` objects) and threads it through `duelTeamInfo`'s new
  `SpellsByFighter`/`ObjectsByFighter` maps into `buildCreateFight`
  (`internal/dispatch/packets_fight.go`), which now calls the already-implemented/tested
  `buildSpellBlob`/`buildInventoryBlob` helpers (`inventory_codec.go`) instead of writing two
  hardcoded zero-length fields. Confirmed via decompiled client source that `CREATE_FIGHT` is the
  **sole** place the client ever populates a fighter's in-fight spell/equipment inventory (no
  later opcode patches it in) — this was a complete functional gap, not an intentionally-minimal
  format awaiting a follow-up message.
- The remaining simplifications inherited from the **legacy Java reference server's hand-built
  buffer** (`src/org/ankarton/world/entity/fight/Fight.java:79-206`, `startPreparation()`) are
  unaffected by the above fix and still always send, for every coach/fighter:
  - `serializedCoachCards` length = 0 (empty coach-spell blob)
  - `options=2` equipment blob length = 0 (coach has no visible fight-equipment)
  - `statsReportLen` = 0, `betCardCount` = 0, `eventCount` = 0, `specialCellCount` = 0
  - team names hardcoded to `"team1"`/`"team2"` (cosmetic only, matches Java, harmless)

  These remain believed-harmless per the original analysis (the client tolerates zero-length
  blobs fine), but were not re-investigated as part of the spell/equipment fix above.

### Remaining genuine unknowns

- **`PlayerStatisticsReport` binary format** (the `statsReport` blob) not fully decoded here — see
  [03-coach-world.md](./03-coach-world.md) PLAYER_STATISTICS_REPORT section for a partial format.
  Not required for the current Go server since it always sends length 0 here.
- **`AbstractEventManager` event IDs** (`eventCount`/`eventId` list) — semantic meaning
  (fight modifiers? environmental events?) not investigated; Go always sends `eventCount=0`.
- **Special cell semantics** — now resolved. `cellBaseId` is the `StaticEffectAreaManager`
  SPECIAL/TRAP template id (SPECIAL 1002-1009, TRAP 1-2 from `staticEffects.dat`); `cellId` is a
  per-fight instance id. The 8 SPECIAL ids map 1:1 to the manual's battlefield-cell types
  (killer/trap/eagle_eye/shield/panacea/enthusiasm/motivation/healing_heart — see
  `04-game-data-format.md` §4.5). Go now sends the real list: layouts are **derived from each
  map's baked negative-gfx `Bonus` tiles** (`gamedata.Map.SpecialCells`) and serialized here by
  `buildCreateFight`, so `specialCellCount` is the map's actual special-tile count.

  **Turn-start triggering (`internal/combat`).** When a fighter *starts* their turn on a special
  cell, `startNextTurn` (after `FIGHTER_TURN_BEGIN`) calls `applyTurnStartSpecialCell`, which:
  (1) broadcasts `EFFECT_AREA_ACTION(6200)` referencing the cell's `cellId` so the client plays
  the tile's own scripted animation (the client registered a live `EffectArea` per cell from the
  CREATE_FIGHT list; its `execute()` is empty so this is cosmetic — HP/buffs stay
  server-authoritative), then (2) broadcasts the gameplay effect's `RUNNING_EFFECT_ACTION(8120)`.
  Both are **queued** (`mustBeExecutedNow=false`), so `startNextTurn` **must** then emit a
  `FIGHT_ACTION_SEQUENCE_EXECUTE(8200)` flush — otherwise the client holds them in its pending
  group until the fighter's first action (the "effect only applies on the first move, and the HP
  bar doesn't drop" bug). The killer-cell path flushes too (it queued `FIGHTER_DIES` + the tile
  animation with no follow-up action to flush them).
- Whether the client can tolerate an equipment/spell blob of length 0 for **every** fighter without
  downstream UI/logic breakage (e.g. close-combat validity checks, spell-bar rendering) was not
  verified — a runtime/gameplay concern outside static wire-format analysis.

---

## Fight Phase Transitions (Presentation → Placement → Observation → Action)

Scope: opcodes 8010–8040, the phase-transition state machine that runs between `CREATE_FIGHT`
(8000) and the start of turn-based combat (`8100`+, see
[08-fight-combat-engine.md](./08-fight-combat-engine.md)).

Wire header for all Recv messages in this range: `architectureTarget = 3` (game), standard 5-byte
client header. Only the payload is shown below.

### START_PRESENTATION (Send 8010)
**Direction:** Server → Client
**Status:** implemented, including the phase timer and follow-up `END_PRESENTATION` (originally
documented here as missing — since resolved by Phase D, `docs/08-java-parity-roadmap.md`):
`Fight.Run()` arms the presentation clock (`f.armPhaseClock(f.Clocks.Presentation)`,
`internal/combat/fight.go:265`) right after actor start, and its expiry drives `endPresentation()` →
`SendEndPresentation` (8018) → `startPlacement()`. **As of §8.19**, presentation ALSO ends early once
both coaches click "Prêt" during it (via the overloaded opcode 8011 → `handleCoachReadyPresentation`
→ `askForPresentationEnd()`), so it no longer always waits out the full clock — see the
TEAM_MATE_SET_READY_FOR_PLACEMENT (8011) section below.
**Client source:** `client/.../fight/StartPresentationMessage.java:20-35` (empty `decode()`)
**Go source:** `server/internal/dispatch/packets_fight.go:76-79`; sent from `handlers_fight.go:161-163`
**Payload:** `(empty)`. Client handler `NetFightFrame` case 8010 calls
`Fight.getTimeline().askForPresentation()` — purely a UI/timeline trigger. Go sends this right
after both coaches' `SetPlacementReady` become true — matches legacy
`TeamMateSetReadyForPlacement.java:28-35` (not `Fight.startPresentation()`, which is dead/commented
out in Java, `Fight.java:295`).

### TEAM_MATE_SET_READY_FOR_PLACEMENT (Recv 8011)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../fight/TeamMateSetReadyForPlacementRequestMessage.java:21-36`
**Go source:** `opcodes.go:48`; `handlers_fight.go:26-28,134-164` (`handleTeamMateSetReadyForPlacement`)
**Payload:** `(empty)`.

**Readiness granularity note:** despite the "TEAM_MATE" naming, readiness is tracked **per-coach**,
not per-individual-fighter within a team — legacy `TeamMateSetReadyForPlacement.java:28`
(2-coach check) and Go's `Duel.SetPlacementReady` (`len(d.placementReady) >= 2`). For a 1v1 duel
this is equivalent to "both coaches ready"; neither implementation tracks readiness below coach
granularity even though the opcode name implies per-teammate tracking for hypothetical multi-coach
team fights.

**Opcode 8011 is OVERLOADED — two distinct gates (§8.19 of `docs/08-java-parity-roadmap.md`):**
the client sends this same message for BOTH (1) the pre-fight teleport gate (before the
`combat.Fight` actor exists) AND (2) the "Prêt" button clicked DURING the PRESENTATION phase, to
skip the presentation countdown (confirmed via the decompiled `UIFightPresentationFrame` → UI event
18009 → `TeamMateSetReadyForPlacementRequestMessage`). `handleTeamMateSetReadyForPlacement`
(`internal/dispatch/handlers_fight.go`) disambiguates by fight state: if a fight already exists and
is in `PhasePresentation`, the packet is routed into the fight actor as a presentation-skip vote
(`combat.NewCoachReadyPresentation` → `handleCoachReadyPresentation`), which broadcasts the 8012 ack
and, once both coaches vote, ends presentation immediately (skipping the presentation clock); a fight
that already exists but is past presentation treats it as a no-op. Otherwise it's the teleport gate.

**Forced-progress timer (July 2026 fix, `docs/08-java-parity-roadmap.md` §8.13):** previously this
gate had no timeout at all — if one coach never sent this packet, the duel stalled forever, with no
server-side counterpart to the client's own purely-cosmetic ~20s ready-countdown UI
(`Fight.onPresentationStart()`'s `Countdown.start(20)`, confirmed to never send any packet on
expiry). Now armed via `world.Duel.ArmReadyTimer`/`dispatch.armPlacementReadyTimeout`
(`internal/config.CombatConfig.PlacementReadyClock`, default 20s): if the timeout fires before both
coaches ack, presentation is forced to start anyway (`startPresentationForDuel`), exactly as if the
non-responsive coach had also acked — both fighter rosters are already known at this point (loaded
for `CREATE_FIGHT`), so there's no missing-data problem forcing this gate.

### TEAM_MATE_SET_READY_FOR_PLACEMENT_MESSAGE (Send 8012)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../fight/TeamMateSetReadyForPlacementMessage.java:22-49`
**Go source:** `opcodes.go:105`; built by `packets_fight.go:115-122`; sent from `handlers_fight.go:145-146`
**Payload:** `long coachId` — ID of the coach who just became ready.
Legacy Java broadcasts to **both** coaches every time either becomes ready; Go replicates exactly.

### END_PRESENTATION (Send 8018)
**Status:** implemented · sent from `Fight.endPresentation()` (`internal/combat/phases.go:28-31`),
invoked via `askForPresentationEnd` (`phases.go:19-26`), itself driven by the presentation phase
clock (`handleClockFired`, `internal/combat/fight.go:368-374`) — Phase D
(`docs/08-java-parity-roadmap.md`).
**Client source:** `client/.../fight/EndPresentationMessage.java:19-33` (empty `decode()`)
**Go source:** `opcodes.go:116` (`SendEndPresentation = 8018`)
**Payload:** `(empty)`. No client-side `NetFightFrame` switch case for 8018 either.

### START_PLACEMENT (Send 8020)
**Status:** implemented · sent from `Fight.endPresentation()` (`internal/combat/phases.go:30,33-38`,
`startPlacement`), which arms the placement clock immediately after — Phase D.
**Client source:** `client/.../fight/StartPlacementMessage.java:20-35` (empty `decode()`)
**Go source:** `opcodes.go:117`
**Payload:** `(empty)`. Legacy Java's `Fight.startPresentation()` has the send commented out
(`Fight.java:295`), and `TeamMateSetReadyForPlacement.java:35` likewise — the legacy Java server
**was never finished past `START_PRESENTATION`**, confirmed in `../05-combat-engine.md:3-7`. The Go
server now implements this transition itself (see above), rather than porting Java's incompleteness
here. Client handler exists: `NetFightFrame` case 8020 → `askForStartPlacement()`.

### MoveToFreePlacementRequestMessage (Recv 8021)
**Status:** implemented · handled by `handleMoveToFreePlacementRequest`
(`internal/dispatch/handlers_fight_combat.go:19-32`), forwarded to
`Fight.handleMoveToFreePlacement` (`internal/combat/phases.go:130-160`) — validates
fighter-occupancy (and real map walkability when attached, see `docs/08-java-parity-roadmap.md`
Phase K) before moving the fighter and broadcasting `MOVE_TO_FREE_PLACEMENT`.
**Client source:** `client/.../fight/MoveToFreePlacementRequestMessage.java:23-79` — opcode **8021**
resolved directly via `getId()` (not in `OpCode.java`)
**Go source:** `opcodes.go:49` (`RecvMoveToFreePlacementRequest`)
**Payload (18 bytes):** `long fighterId; int worldX; int worldY; short altitude`.

### MOVE_TO_FREE_PLACEMENT (Send 8022)
**Status:** implemented · built by `buildMoveToFreePlacement` (`internal/combat/packets.go:32-36`),
broadcast from `Fight.handleMoveToFreePlacement` (`phases.go:159`) after occupancy/walkability
validation passes.
**Client source:** `client/.../fight/MoveToFreePlacementMessage.java:22-78`
**Go source:** `opcodes.go:118` (`SendMoveToFreePlacement`)
**Payload:** identical 18-byte layout to the Recv counterpart. This does **not** yet enforce the
reference's real free-placement *zone* restriction (only a subset of walkable cells, not "any
walkable cell") — see `docs/08-java-parity-roadmap.md` §8.11 item 3's "not yet wired" note.

### TeamMateSetReadyForObservationRequestMessage (Recv 8023)
**Status:** implemented · handled by `handleCoachReadyForObservation`
(`internal/dispatch/handlers_fight_combat.go:34-42`), forwarded to
`Fight.handleCoachReadyObservation` (`internal/combat/phases.go:101-114`) — same per-coach
readiness-gate pattern as `TEAM_MATE_SET_READY_FOR_PLACEMENT`.
**Client source:** `client/.../fight/TeamMateSetReadyForObservationRequestMessage.java:21-36` —
opcode **8023** resolved via `getId()`
**Go source:** `opcodes.go:50` (`RecvTeamMateSetReadyForObservation`)
**Payload:** `(empty)`.

### TEAM_MATE_SET_READY_FOR_OBSERVATION (Send 8024)
**Status:** implemented · built by `buildTeamMateSetReadyForObservation`
(`internal/combat/packets.go:20-24`), broadcast from `Fight.handleCoachReadyObservation`
(`phases.go:110`) every time a coach becomes ready.
**Client source:** `client/.../fight/TeamMateSetReadyForObservationMessage.java:22-49`
**Go source:** `opcodes.go:119` (`SendTeamMateSetReadyForObservationMessage`)
**Payload:** `long coachId`.

### END_PLACEMENT (Send 8028)
**Status:** implemented · sent from `Fight.endPlacement()` (`internal/combat/phases.go:53-56`),
invoked via `askForPlacementEnd` (`phases.go:44-51`) — either the placement clock firing or every
coach signaling ready-for-observation, per the single `askForXEnd()` entry-point pattern.
**Client source:** `client/.../fight/EndPlacementMessage.java:19-33` (empty `decode()`)
**Go source:** `opcodes.go:120` (`SendEndPlacement`)
**Payload:** `(empty)`.

### START_OBSERVATION (Send 8030)
**Status:** implemented · sent from `Fight.startObservation()`
(`internal/combat/phases.go:62-71`), guarded to only fire when the previous phase was PLACEMENT
(mirrors the reference's defensive `startObservation()` check).
**Client source:** `client/.../fight/StartObservationMessage.java:20-35` (empty `decode()`)
**Go source:** `opcodes.go:121` (`SendStartObservation`)
**Payload:** `(empty)`. Client handler: `NetFightFrame` case 8030 → `askForStartObservation()`.

### TeamMateSetReadyForActionRequestMessage (Recv 8031)
**Status:** implemented · handled by `handleCoachReadyForAction`
(`internal/dispatch/handlers_fight_combat.go:44-52`), forwarded to
`Fight.handleCoachReadyAction` (`internal/combat/phases.go:116-128`).
**Client source:** `client/.../fight/TeamMateSetReadyForActionRequestMessage.java:21-36` — opcode
**8031** resolved via `getId()`
**Go source:** `opcodes.go:51` (`RecvTeamMateSetReadyForAction`)
**Payload:** `(empty)`.

### TEAM_MATE_SET_READY_FOR_ACTION (Send 8032)
**Status:** implemented · built by `buildTeamMateSetReadyForAction`
(`internal/combat/packets.go:26-30`), broadcast from `Fight.handleCoachReadyAction`
(`phases.go:124`).
**Client source:** `client/.../fight/TeamMateSetReadyForActionMessage.java:22-49`
**Go source:** `opcodes.go:122` (`SendTeamMateSetReadyForActionMessage`)
**Payload:** `long coachId`.

### END_OBSERVATION (Send 8038)
**Status:** implemented · sent from `Fight.endObservation()` (`internal/combat/phases.go:82-85`),
invoked via `askForObservationEnd` (`phases.go:73-80`).
**Client source:** `client/.../fight/EndObservationMessage.java:19-33` (empty `decode()`)
**Go source:** `opcodes.go:123` (`SendEndObservation`)
**Payload:** `(empty)`.

### START_ACTION (Send 8040)
**Status:** implemented · sent from `Fight.startAction()` (`internal/combat/phases.go:91-99`),
guarded to only fire when the previous phase was OBSERVATION, then immediately calls
`startNextTurn()` (`internal/combat/turns.go:16`) — the entry point into the turn-based combat
engine (opcodes 8100+, see [08-fight-combat-engine.md](./08-fight-combat-engine.md)).
**Go source:** `opcodes.go:124` (`SendStartAction`)
**Client source:** `client/.../fight/StartActionMessage.java:20-35` (empty `decode()`)
**Payload:** `(empty)`. Client handler: `NetFightFrame` case 8040 → `askForStartAction()`.

### Phase flow diagram

```
CREATE_FIGHT (8000)
   │  (sent to both coaches once matchmaking/duel-accept selects fighters)
   ▼
[coach ready-for-placement loop, per-coach, any order]
Recv TEAM_MATE_SET_READY_FOR_PLACEMENT (8011)
Send TEAM_MATE_SET_READY_FOR_PLACEMENT_MESSAGE (8012)  ── broadcast to both, every time either becomes ready
   │
   │  when BOTH coaches ready (2-party gate, not per-individual-teammate in either impl):
   │    teleport both (ENTER_WORLD_INSTANCE, 4600) then START_PRESENTATION
   ▼
Send START_PRESENTATION (8010)   [IMPLEMENTED]
   │  ... 20s clock or both-ready (see 08-fight-combat-engine.md timeline design) ...
   ▼
Send END_PRESENTATION (8018)     [IMPLEMENTED]
   ▼
Send START_PLACEMENT (8020)      [IMPLEMENTED — Phase D, docs/08-java-parity-roadmap.md]
   │
   │  [fighters move to free cells, repeatable, per-fighter]
   │  Recv MoveToFreePlacementRequestMessage (8021) ──► Send MOVE_TO_FREE_PLACEMENT (8022) [IMPLEMENTED]
   │
   │  [readiness loop, per-coach like placement-ready]
   │  Recv TeamMateSetReadyForObservationRequestMessage (8023) ──► Send TEAM_MATE_SET_READY_FOR_OBSERVATION (8024) [IMPLEMENTED]
   ▼
Send END_PLACEMENT (8028)        [IMPLEMENTED]
   ▼
Send START_OBSERVATION (8030)    [IMPLEMENTED]
   │
   │  Recv TeamMateSetReadyForActionRequestMessage (8031) ──► Send TEAM_MATE_SET_READY_FOR_ACTION (8032) [IMPLEMENTED]
   ▼
Send END_OBSERVATION (8038)      [IMPLEMENTED]
   ▼
Send START_ACTION (8040)         [IMPLEMENTED]
   ▼
combat begins — turn-based engine, opcodes 8100+ (see 08-fight-combat-engine.md)
   │
   │  ... a team is wiped or a coach forfeits ...
   ▼
Send END_FIGHT (8300)            [IMPLEMENTED — combat/fightend.go endFight()]
   │  IMPORTANT: once the fight is PhaseEnded, NO more turn traffic may be
   │  emitted. A fighter's action can kill the last enemy MID-TURN; the
   │  turn-advance choke points (startNextTurn / askForFighterEndTurn) both
   │  guard on PhaseEnded so no trailing FIGHTER_TURN_END / NEW_TABLE_TURN_BEGIN
   │  leaks after END_FIGHT (which would desync the client's fight teardown).
   ▼
[each coach dismisses the results screen]
Recv END_FIGHT_DONE (4321)       [IMPLEMENTED]
   │  when BOTH coaches have acked (fight actor Run() loop exits, Fight.Done()):
   │    the server sends each participant back to the overworld —
   │    Send ENTER_WORLD_INSTANCE (4600, map 0) + ACTOR_SPAWN of the other
   │    online coaches (returnCoachToWorld in handlers_fight_combat.go). The
   │    client's onFightEnded() only tears down the fight net-frames; without
   │    this it stays stuck on the fight map after dismissing the results popup.
```

### Summary of discrepancies / implementation gaps

**Updated (Phase D, `docs/08-java-parity-roadmap.md`): all 14 wire messages in this
phase-transition range are now implemented in the Go server.** This section originally documented
a large gap versus the legacy Java server (which never finished this phase machine); that gap has
since been closed on the Go side:

| Opcode | Name | Go status |
|---|---|---|
| 8010 | START_PRESENTATION | **implemented** (including phase timer + `END_PRESENTATION` follow-up) |
| 8011 | TEAM_MATE_SET_READY_FOR_PLACEMENT (Recv) | **implemented** |
| 8012 | TEAM_MATE_SET_READY_FOR_PLACEMENT_MESSAGE (Send) | **implemented** |
| 8018, 8020, 8028, 8030, 8038 | END/START phase markers | **implemented** — `internal/combat/phases.go` |
| 8040 | START_ACTION | **implemented** — `internal/combat/phases.go`'s `startAction()`, `opcodes.go:124` |
| 8021/8022, 8023/8024, 8031/8032 | placement-move / observation-ready / action-ready pairs | **implemented** — `internal/combat/phases.go` + `internal/dispatch/handlers_fight_combat.go` |

Key findings (historical — accurate as of the legacy-Java/pre-Phase-D comparison, kept for
context):
1. **The legacy Java server itself never got past `START_PRESENTATION`** — `Fight.java:295` has
   the `START_PLACEMENT` send commented out, confirming the fight phase machine was never finished
   server-side even in the original codebase. (The Go server has since implemented this phase
   machine itself, independent of Java's incompleteness — see the table above.)
2. Three client Recv opcodes are **not registered anywhere** in `OpCode.java` (8021, 8023, 8031) —
   their values were only recoverable via each class's own `getId()` literal.
3. Go's `opcodes.go` now defines and uses live `SendOpcode`/`RecvOpcode` constants for all of
   8018/8020/8028/8030/8038/8040 and the placement-move (8021/8022), observation-ready
   (8023/8024), and action-ready (8031/8032) pairs — none are dead/unreferenced anymore.
4. Payload shapes for all three "TEAM_MATE_SET_READY_FOR_*" Send messages (8012/8024/8032) are
   identical: a single 8-byte `long coachId`. Matching Recv triggers (8011/8023/8031) are all
   empty-payload. `MOVE_TO_FREE_PLACEMENT`/its Recv counterpart (8021/8022) share an identical
   18-byte `{long fighterId, int worldX, int worldY, short altitude}` layout in both directions.
