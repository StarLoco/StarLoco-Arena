# Coach Creation, World Instance & Actor Opcodes

> Client source of truth, cross-checked against the current Go server implementation.

## COACH_CREATION (Recv 2049)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/com/ankamagames/dofusarena/client/network/protocol/message/world/clientToServer/CoachCreationMessage.java:30-40`
**Go source:** `server/internal/dispatch/handlers_connection.go:104-144` (`handleCoachCreation`)

**Payload:**
```
byte    nameLen
byte[]  name          (nameLen bytes, UTF-8)
byte    skin           // LocalCoach.getSkinColorIndex()
byte    hair           // LocalCoach.getHairColorIndex()
byte    sex            // LocalCoach.getSex()
```
Client always sends all 4 fields; no options/flags byte. Go's `CoachService.CreateCoach`
additionally normalizes name casing (Title Case) and rejects a forbidden-fragment list
(server-side game logic, not a protocol difference).

## COACH_CREATION_REQUEST (Send 2048)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/CoachCreationRequestMessage.java:22-24` (always returns `true`, no fields)
**Go source:** `handlers_connection.go:90-92` (sent when `account.CoachID == nil`)
**Payload:** `(empty)` — signal-only, tells client to show the coach-creation UI.

## COACH_CREATION_RESULT (Send 2050)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/CoachCreationResultMessage.java:22-28`; consumer switch in `NetCoachFrame.java:65-88`
**Go source:** `handlers_connection.go:203-207` (`sendCoachCreationResult`); codes `internal/service/coach.go:14-22`
**Payload:**
```
byte    errorCode     // client requires exactly 1 byte
```
Client handling: `0`=success (closes UI); `11`/`12`=`error.coachCreation.invalidName`;
`10`/`13`=`error.coachCreation` (generic); other values silently ignored (no `default` case).
**Cross-check:** Go only ever sends `0`, `11` (`CoachCreationInvalidName`), or `12`
(`CoachCreationNameTaken`) — codes `10`/`13` are dead client-side branches, never emitted by Go.

## COACH_INFORMATION (Send 2052)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/CoachInformationsMessage.java:29-37` → `LocalCoach.unserialize(buffer, 14)`;
field semantics in `client/.../core/game/coach/Coach.java:463-535`
**Go source:** `server/internal/dispatch/packets_coach.go:9-39` (`buildCoachInformation`)

**IMPORTANT:** the client's *generic* `Coach.unserialize()`/`AbstractCoachCard` engine uses a
**13-byte-per-card** core (`int templateId, long uid, byte flags`). The legacy Java server (and
the Go port, which mirrors it) instead sends cards using a coarser, hand-rolled **15-byte** layout.
Both are self-consistent between legacy Java and Go (byte-for-byte), and the client evidently
accepts this shape in production, but this combines two different serialization code paths that
happen to reconcile only by matching byte counts — **flagged as needing runtime/capture
verification**. The Go implementation (mirroring the legacy hand-rolled format actually used in
production) is:
```
long    coachId
byte    nameLen
byte[]  name
byte    skin
byte    hair
byte    sex
short   equipmentByteLength     // = 15 * equippedCardCount
  repeated (equipped cards, pos != 0):
    short pos
    int   templateId
    long  cardInstanceId
    byte  flag
short   inventoryByteLength     // = 15 * unequippedCardCount
  repeated (unequipped cards, pos == 0):
    int   templateId
    long  cardInstanceId
    byte  flag
    short quantity
short   locketSetCount          // always 0
byte    laddersStrengthCount    // 0 if unranked, else 1
  repeated (count times):       // options=14 sets the 0x8 LADDERS_STRENGTH bit
    byte  ladderId              // 1 = the 1v1 ladder
    short strength              // ranked range 1000..3000
```
**Bug fixed:** the old code emitted a bare `byte 2` here (a mislabeled "legacy constant"). The
client (`Coach.unserializeLaddersStrength`, `Coach.java:543-553`) reads that byte as the ladder
*count*, then tries to read 2 nonexistent `{byte,short}` entries — underflowing and silently
aborting `unserialize`, which left the coach's ladder strength unset so **Level/Rank rendered
empty ("-") in the Statistics window at login**. The block is now serialized correctly
(`writeLaddersStrength` in `packets_coach.go`): a ranked coach sends one entry, an unranked coach
(strength 0) sends an empty block. ACTOR_SPAWN (options=11, also 0x8) carries the same block so
other players see a coach's Level/Rank too.

Followed immediately by `FRIEND_LIST_MESSAGE` (3144), `IGNORE_LIST_MESSAGE` (3146), and
`PLAYER_STATISTICS_REPORT` (2400) — see `handlers_connection.go` (`completeLogin`).

## PLAYER_STATISTICS_REPORT (Send 2400)
**Direction:** Server → Client
**Status:** implemented (real per-coach statistics)
**Client source:** `client/.../world/serverToClient/PlayerStatisticsReportMessage.java:29-40` →
`StatisticsReportManager.createReport(byte[])`; format in
`client/.../baseImpl/common/clientAndServer/game/statistics/AbstractStatisticsReport.java:65-86`
**Go source:** `server/internal/dispatch/packets_stats.go` (`buildPlayerStatisticsReport` /
`serializePlayerStatisticsReport`)

**Payload:**
```
short   blobSize
byte[]  blob                  // consumed by AbstractStatisticsReport.unserializeReport:
  short   modelId
  long    reportId
  short   entryCount
    repeated (entryCount times):
      short   entryId
      byte    entryType        // 1=int, 2=long, 3=float
      (int|long|float) value   // 4, 8, or 4 bytes depending on entryType
```
**Entry values** are now sourced from the coach's persisted statistics
(`domain.Coach.{TotalPlayTimeSecs, TimeInFightSecs, StatFights, StatWins, StatLosses, Strength,
ConsecutiveWins}`), tracked for real: incremented at fight end by `CoachService.ApplyFightResult`
(via the combat `FightEndHook`, see `internal/dispatch/fightend_hook.go`), with play time accrued
per session at disconnect (`CoachService.AddPlayTime`). The packet is sent at login
(`completeLogin`), on the GM `/STATS` command, and pushed live to each participant the moment a
fight ends.

**Legacy Java bug:** `PlayerStatisticsReport.java:18-66` declared `nbEntries=7` but wrote **8**
entry blocks (off-by-one). **Go deliberately writes exactly 7 entries**, dropping the 8th
("consecutive losses"), to avoid a client-side parse desync — documented, intentional deviation.

## ACTOR_SPAWN (Send 4096)
**Direction:** Server → Client · **Status:** implemented (coach-join case only)
**Client source:** `client/.../game/serverToClient/actor/ActorSpawnMessage.java:33-69`
**Go source:** `packets_coach.go:93-117` (`buildActorSpawn`); sent from `handlers_connection.go:170-189` (`enterWorld`)

**Payload (per client decode):**
```
int     charactersCount
  repeated:
    byte    type              // 1 = Coach -> Coach.unserialize(buffer, options=11)
                               // 2 = Fighter -> Fighter.unserialize(buffer)
    <type-dependent payload>
```
For `type==1`, `options=11=0b1011` = POSITION(1)|EQUIPMENT(2)|LADDERS_STRENGTH(8) — **no**
CARD_INVENTORY bit, unlike COACH_INFORMATION's options=14.

**Go implementation** only ever sends `type=1` (coach) entries, matching `Coach.onJoinMap()`
(`src/org/ankarton/world/entity/coach/Coach.java:260-299`):
```
int     coachCount
  repeated:
    byte    actorType         // always 1 = coach
    long    coachId
    byte    nameLen
    byte[]  name
    int     x
    int     y
    short   z
    byte    unknownFlag        // always 1
    byte    skin
    byte    hair
    byte    sex
    short   equipmentByteLength
      repeated: short pos, int templateId, long cardInstanceId, byte flag
    byte    trailingZero        // always 0
```
`type=2` (Fighter) entries are used by legacy Java's fight-context spawn (a different, 21-byte-
per-fighter shape) — **the Go server has no combat engine yet and never sends `type=2` entries**,
out of scope for the current world/coach-management phase.

## ACTOR_DESPAWN (Send 4098)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../game/serverToClient/actor/ActorDespawnMessage.java:29-45`
**Go source:** `packets_coach.go:121-125` (`buildActorDespawn`); sent from `disconnect.go:51-53`
**Payload:**
```
byte    count                 // client reads as a plain unsigned byte, NOT a short
  repeated (count times):
    long    actorId
```
Both legacy Java and Go always send `count=1` (single-entity despawn) — the byte-vs-short
distinction has never mattered in practice, but a future batch-despawn must respect the 1-byte
count field.

## ACTOR_APPEAR (Send 4102)
**Direction:** Server → Client
**Status:** implemented (July 2026 bug-fix pass, §8.13 of `../08-java-parity-roadmap.md`) — this
was the root cause of a real-user-reported bug ("coach teleported into fight map but doesn't
appear"), since this is the **sole** mechanism (`NetFightActorsFrame.java`'s case 4102 →
`addMobile()`) the client uses to instantiate a visible mobile inside the fight-map scene, for both
coaches' sideline anchor pawns and every fighter.
**Client source:** `client/.../game/serverToClient/actor/ActorAppearMessage.java:67-77`
**Go source:** `internal/dispatch/packets_coach.go` (`buildActorAppear`/`actorAppearEntry`), built
by `buildActorAppearForFight` (`internal/dispatch/handlers_fight.go`) and sent from
`startPresentationForDuel` right after `ENTER_WORLD_INSTANCE`/`instantiateFight` and right before
`START_PRESENTATION`.
**Payload:**
```
byte    count
  repeated (count times):
    long    id
    int     worldX
    int     worldY
    short   altitude
    byte    directionIndex     // Direction8.getDirectionFromIndex
```
**Entities included — BOTH coaches AND fighters** (§8.18 of `../08-java-parity-roadmap.md`,
superseding §8.16's "fighters only"): the Go server sends one entry per coach (at its pedestal
`resolveCoachStartSpots` cell, facing SOUTH_EAST) followed by one entry per `combat.Fighter` (at
its real `Position` and standing altitude base+height §8.17, facing its own **diagonal**
orientation — `defaultTeamFacing` team 1 → SOUTH_WEST=3, team 2 → SOUTH_EAST=1; only the four
diagonal Direction8 values render correctly for fighter sprites, §8.17). Both coaches and fighters
MUST appear here — ACTOR_APPEAR is the **only** message that instantiates a visible mobile in the
fight scene (`NetFightActorsFrame` case 4102 → `addMobile()`); coaches are NOT auto-spawned from
CREATE_FIGHT or the map's `FightStartCoachPointElement` data (confirmed via the client:
`FightCreationMessage.decode()` only builds the logical team model, and `DofusArenaCustomElementProcessor`
treats the type-1001 coach-point element as a no-op). The pedestals visible on-screen are separate
type-1002 scenery meshes, which is why they render while the coach figures previously did not.

**ID scheme — the collision fix (§8.18):** the client's case-4102 handler
(`NetFightActorsFrame.java`) disambiguates each entry purely by ID — it calls
`fight.getFighterById(id)` **first**, only falling back to a coach/team-mate lookup if that returns
nil. There is NO per-entry "type" byte (legacy `Fight.java`'s `.put(2)`/`// type` comment is wrong —
`ActorAppearMessage.decode()` never reads one). Coach and fighter DB ids both auto-increment from 1
in separate tables, so naively they collide and a colliding coach entry resolves onto the fighter.
The fix: **fighters carry an OFFSET wire id** (`combat.FighterWireIDBase` = 1,000,000,000 + real DB
id) applied identically at `combat.Fighter` construction (`buildCombatTeam`) and in CREATE_FIGHT
(`buildCreateFight`), while **coaches keep their REAL id** (the client compares coach ids against the
login-supplied local coach id in `setFight()`/`FightEndAction`, so they must NOT be offset — an
earlier "offset the coach id" idea would have hung the ready-gates and broken end-fight stats). With
disjoint ranges, fighter-first resolution never mistakes one for the other. An intermediate
iteration (§8.16) dropped coaches entirely to dodge the collision, which fixed fighter rendering but
left coaches invisible — §8.18 restores coaches with the offset scheme.

## ACTOR_DISAPEAR (Send 4104)
**Status:** not implemented — constant only (`opcodes.go:80`/`names.go:69`)
**Client source:** `client/.../game/serverToClient/actor/ActorDisapearMessage.java:29-36`
**Payload:**
```
short   count                  // SHORT count (unlike ACTOR_DESPAWN's byte count)
  repeated: long characterId
```

## ACTOR_REPOSITION (Send 4106)
**Status:** not implemented — constant only (`opcodes.go:81`/`names.go:70`)
**Client source:** `client/.../game/serverToClient/actor/ActorRepositionMessage.java:60-71`
**Payload:**
```
short   count
  repeated: long id; int worldX; int worldY; short altitude   // NOTE: no direction byte
```

## ACTOR_MOVEMENT_REQUEST (Recv 4501)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../game/clientToServer/CoachActorMovementRequestMessage.java:32-48`
(world/coach-context; distinct from `FighterActorMovementRequestMessage.java` opcode 4503, in-fight, out of scope here)
**Go source:** `server/internal/dispatch/handlers_fight.go:29-31,166-195` (`handleActorMovementRequest`)
**Payload:**
```
repeated (until buffer exhausted, no leading count):
  int     worldX
  int     worldY
  short   altitude
```
Each path cell = 10 bytes (`CELL_BYTE_SIZE=10`). **No fighter/coach ID field** — mover identified
implicitly by connection. Go reads exactly this shape and matches.

## ACTOR_MOVEMENT (Send 4500)
**Direction:** Server → Client · **Status:** implemented (with a flagged shape ambiguity)
**Client source:** `client/.../game/serverToClient/actor/ActorMovementMessage.java:32-54`
**Go source:** `packets_fight.go:124-133` (`buildActorMovement`); called from `handlers_fight.go:186-189`
**Payload (client-authoritative):**
```
long    actorId
repeated (until buffer exhausted, 10 bytes/cell):
  int     worldX
  int     worldY
  short   altitude
```
Client computes `pathLength = (rawDatas.length - 8) / 10` — **no explicit path-length prefix**,
and **no distinct "from" field** — every 10-byte block after `actorId` is a path cell.

**Discrepancy:** Go's `buildActorMovement` unconditionally prepends an explicit `fromX/fromY/fromZ`
triplet before the cell list — the client will interpret this as waypoint #0, not skip it as an
origin marker. Very likely intentional (server re-adds the coach's pre-move position as the first
waypoint so remote clients animate the full path) — `handlers_fight.go:186` passes the coach's
pre-move position specifically. Flagged for confirmation rather than assumed correct/incorrect.

## ACTOR_TELEPORT (Send 4510)
**Direction:** Server → Client
**Status:** **not implemented** — corrects `../02-protocol.md` line 135 ("implemented (used to
move players into fight map)"), which is stale
**Client source:** `client/.../game/serverToClient/actor/ActorTeleportsMessage.java:31-43`
**Go source:** constant only (`opcodes.go:85`/`names.go:74`) — no builder, no call site.
**Payload:**
```
long    actorId
int     worldX
int     worldY
short   altitude
```
(18 bytes, matches client's `checkMessageSize(...,18,true)`.) The actual mechanism used to move a
coach into the fight map is **`ENTER_WORLD_INSTANCE`** (Send 4600) — see
`handlers_fight.go:154-159`. The legacy Java server does the same (`Coach.java:124-129`), so
`ACTOR_TELEPORT` appears entirely unused by either server implementation.

## ENTER_WORLD_INSTANCE (Send 4600)
**Direction:** Server → Client · **Status:** implemented
**Client source:** corroborated via legacy server usage `src/org/ankarton/world/entity/coach/Coach.java:124-129,252-255`
(exact client decode class not directly reviewed, but layout confirmed via both legacy `Buffer`
construction and Go's mirrored implementation)
**Go source:** `server/internal/dispatch/packets_coach.go:85-89` (`buildEnterWorldInstance`)
**Payload:**
```
float   x
float   y
short   z
short   mapId
byte    dynamic       // 0/1
```
13 bytes total. Used for: (1) initial world entry after login/coach-creation (mapId=0,
dynamic=false), (2) teleporting both coaches into the fight map once placement-ready (fightMapID=2,
dynamic=true), (3) the GM `/TP` command.

## NO_INSTANCE_SERVER_AVAILABLE (Send 5000)
**Direction:** Server → Client
**Status:** opcode defined, never sent — dead in both legacy Java and Go (corrects
`../02-protocol.md` line 140's "implemented ... kept for completeness" framing)
**Client source:** `client/.../game/serverToClient/serverStatus/NoInstanceServerAvailableMessage.java:22-24`
(always returns `true`, no fields)
**Go source:** constant only (`opcodes.go:87`) — no builder, no send site.
**Payload:** `(empty)`. Full-tree search of legacy `src/org/ankarton` found no reference either —
never sent by either server. Architecturally appropriate for the Go monolith (no multi-instance-
server routing to fail), but the doc language should say "opcode defined, never sent" not
"implemented."

## COACH_EQUIPMENT_UPDATE_REQUEST (Recv 5201)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../game/clientToServer/coachManagement/CoachEquipmentUpdateRequestMessage.java:29-37`
**Go source:** `server/internal/dispatch/handlers_coach_management.go:75-164` (`handleCoachEquipmentUpdate`)
**Payload:**
```
repeated exactly 14 times (fixed-size, no count prefix):
  long    equipmentSlotUID     // 0 = empty slot; buffer always 112 bytes = 14*8
```
No explicit slot-index field — slot identity is purely positional (array index 0..13). Go reads
exactly 14 fixed `Int64` slot UIDs, matching. Then: unequips everything, re-equips per non-zero
slot UID (storing `Pos = wireSlot+1` internally, a deliberate off-by-one to disambiguate "equipped
at slot 0" from "unequipped"), broadcasts `COACH_EQUIPMENT_UPDATE` (5202) to the world, replies to
requester with `COACH_INVENTORY_UPDATE` (5200) delta.

## COACH_EQUIPMENT_UPDATE (Send 5202)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../game/serverToClient/coachManagement/CoachEquipmentUpdateMessage.java:28-36`
**Go source:** `packets_inventory.go:60-68` (`buildCoachEquipmentUpdateMessage`); legacy
`src/org/ankarton/network/login/parser/packet/coach/management/CoachEquipmentUpdateMessage.java:18-28`
**Payload:**
```
long    coachId
short   equipmentByteLength    // = 15 * equippedCardCount
byte[]  equipmentData          // repeated: short pos, int templateId, long cardInstanceId, byte flag
```
Broadcast to **every online coach** (not just requester) — confirmed in both Go
(`handlers_coach_management.go:156-159`) and legacy (`buffer.sendToAll()`).

## COACH_INVENTORY_UPDATE_REQUEST (Recv 5203)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../game/clientToServer/coachManagement/CoachInventoryUpdateRequestMessage.java:32-66`
**Go source:** `handlers_coach_management.go:23-73` (`handleCoachInventoryUpdate`)
**Payload:**
```
short   removedCardCount
  repeated: long cardUniqueId
short   lockedCardCount
  repeated: long cardUniqueId
short   unlockedCardCount
  repeated: long cardUniqueId
```
Field order: **removed, then locked, then unlocked** — no "added" section (server-initiated only).
Go reads the three sections in the exact same order.

**Documented bug fix vs legacy:** legacy Java's lock/unlock overwrote the entire flag byte
(`FLAG_LOCKED`/`FLAG_CURSED`) instead of bitwise set/clear, risking silently un-cursing a card. Go
correctly does bitwise OR/AND-NOT on just the locked bit, matching the client's own
`AbstractCoachCard.setLocked()` semantics. Deliberate, documented fix — not a wire-format issue.

## COACH_INVENTORY_UPDATE (Send 5200)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../game/serverToClient/coachManagement/CoachInventoryUpdateMessage.java:34-70`
**Go source:** `packets_inventory.go:8-57` (`inventoryDelta.build()`); legacy
`src/org/ankarton/network/login/parser/packet/coach/management/CoachInventoryUpdateMessage.java:42-98`
**Payload (four fixed sections, in this exact order):**
```
short   equipmentAddedCount
  repeated: short pos; int templateId; long cardInstanceId; byte flag
short   equipmentRemovedCount
  repeated: short pos
short   inventoryAddedCount
  repeated: int templateId; long cardInstanceId; byte flag; short quantity
short   inventoryRemovedCount
  repeated: long cardInstanceId
```
Per-card core is 13 bytes via `AbstractCoachCard.serialize()` (`templateId(int), uid(long),
flags(byte)`), matching what Go writes for the "added" sections. Sent only to requesting session
(not broadcast), only if at least one section is non-empty.

---

## Summary of discrepancies found

1. **ACTOR_APPEAR (4102), ACTOR_DISAPEAR (4104), ACTOR_REPOSITION (4106), ACTOR_TELEPORT (4510),
   NO_INSTANCE_SERVER_AVAILABLE (5000)** — all five are declared as Go constants but have **zero**
   builders/send-sites. `../02-protocol.md` mis-states several as "implemented" — **needs
   correcting** for ACTOR_APPEAR and ACTOR_TELEPORT specifically. The real mechanism for moving a
   coach into a fight map is `ENTER_WORLD_INSTANCE` (4600), not `ACTOR_TELEPORT`.
2. **ACTOR_MOVEMENT (4500) wire-shape ambiguity**: Go prepends an explicit `fromX/fromY/fromZ`
   before the waypoint list; client treats every 10-byte block after `actorId` as a path cell (no
   separate origin field exists). Likely intentional, needs live-client verification.
3. **COACH_INFORMATION / ACTOR_SPAWN per-card encoding**: client's generic engine uses 13-byte
   cards; legacy Java + Go use a hand-rolled 15-byte layout. Self-consistent between legacy/Go,
   needs runtime/capture verification against the real client.
4. **PLAYER_STATISTICS_REPORT off-by-one**: legacy declares 7, writes 8 (bug). Go deliberately
   writes 7 — documented, intentional divergence.
5. **Equipment/inventory lock-flag clobbering bug**: legacy overwrites entire flag byte; Go fixes
   with proper bitwise set/clear — documented, intentional fix.
6. **COACH_CREATION_RESULT dead branches**: client supports codes 10/13 (generic error); Go only
   ever returns 0/11/12 — consistent, not a gap.
