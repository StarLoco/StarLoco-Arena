# Fighter Management & Team Preset Opcodes

> Client source of truth, cross-checked against the current Go server implementation.
> **This file corrects/replaces `../02-protocol.md` §2.4.8** (Fighter binary serialization),
> which was flagged there as needing verification — see the Discrepancies section at the end.

All multi-byte integers big-endian. Strings use 1-byte length prefix + UTF-8 unless noted. Frame
header omitted below — only message bodies are shown.

## Fighter inventory sub-formats (read this first — corrects `../02-protocol.md` §2.4.8)

The `FighterInformation`/`Fighter`/`EditableFighter` format embeds two nested "inventory blobs"
(spell inventory, equipment/card inventory). **These use DIFFERENT wire formats from each other**
— this corrects `../02-protocol.md` §2.4.8, which assumed both were symmetric `[short pos][int32 id]` pairs.

- **Spell inventory** — `Fighter` constructs `new StackInventory<Spell>((short)6,
  SpellManager.getInstance(), ..., true, false, false)` (`Fighter.java:256`). Last ctor arg
  `serializeQuantity=false`. `StackInventory.serialize()` only writes a trailing `short quantity`
  if `serializeQuantity` is true — here it's false, so **no quantity**. `Spell.serialize()`
  (`AbstractSpell.serialize()`) writes only `int32 id`.
  **→ Net spell blob: flat array of `int32 spellId`, 4 bytes/entry, no position, no quantity.**

- **Equipment/card inventory** — `Fighter` constructs `new ArrayInventory<FighterCard>(
  FighterCardManager.getInstance(), ..., (short)6, false)` (`Fighter.java:258`).
  `ArrayInventory.serialize()` writes `short pos` **then** `item.serialize()`.
  `FighterCard.serialize()` (`AbstractFighterCard.serialize()`) writes only `int32 id`.
  **→ Net equipment blob: `[short pos][int32 templateId]` pairs, 6 bytes/entry.**

  **Critically, `pos` is NOT cosmetic/sequential — it is a fixed, per-item-category slot.**
  `FighterCardType` (`common/constants/FighterCardType.java:13-17`) assigns each category a fixed
  `inventoryPosition`: `WEAPON→0, PET→1, CLOAK→2, HAT→3, DOFUS→4`. On `unserialize()`
  (`ArrayInventory.java:100-134`), each entry is added via `addAt(item, pos)`
  (`ArrayInventory.java:224-250`), which consults the content checker
  `AbstractFighterCardInventoryChecker.canAddItem(inv, item, position)`
  (`common/game/card/AbstractFighterCardInventoryChecker.java:52-57`):
  ```java
  public int canAddItem(Inventory inventory, C item, short position) {
    if (item.getType().getInventoryPosition() != position) return 2; // INVALID_POSITION
    return 0;
  }
  ```
  If this returns non-zero, `addAt` returns `false` and the item is **silently dropped** (logged
  client-side only) — the rest of the blob still parses normally. **This was a real, previously
  unfixed bug**: Go's `buildInventoryBlob` assigned positions by sequential array index instead of
  by category, so only the item that happened to land at index matching its own category's slot
  survived client-side unserialization — in practice, whichever item was weapon-typed and first in
  the array (`pos 0` = `WEAPON`'s required slot), while pet/cloak/hat/dofus items landed on the
  wrong slot and were dropped. Fixed by `gamedata.FighterCardInventoryPosition` +
  `buildInventoryBlob` looking up each id's real category via `gamedata.Store.FighterCards` (see
  Discrepancies §7, new).

```
Spell inventory blob:
  repeated: int32 spellId              // 4 bytes/entry, NO pos, NO quantity

Equipment/card inventory blob:
  repeated: short pos; int32 templateId  // 6 bytes/entry
```

**Legacy `org.ankarton` server** never used the client's inventory classes: it reads/writes *both*
spells and objects as flat `int32[]` (no `pos` for either) — already disagreeing with the real
client's equipment format.

**Current Go server** (`internal/dispatch/inventory_codec.go`) now uses two distinct codecs matching
the real client: `parseSpellIDs`/`buildSpellBlob` (flat `int32[]`, 4 bytes/entry) for spells, and
`parseInventoryIDs`/`buildInventoryBlob` (`[short pos][int32 id]`, 6 bytes/entry) for equipment/cards.
Previously both used the 6-byte codec, which corrupted every spell id on the wire (see
Discrepancies §2 — fixed).

---

## FIGHTER_CREATE_REQUEST (Recv 6001)
**Direction:** Client → Server
**Status:** implemented
**Client source:** `client/.../teamManagement/CreateFighterInformationRequestMessage.java:30-43`
(serializes via `FighterInformation.java:177-203`)
**Go source:** `server/internal/dispatch/handlers_fighter.go:31-66` (`handleFighterCreate`)

**Client payload:**
```
short   fighterInfoLen        // length of the FighterInformation blob that follows
byte[]  fighterInfo           // serialized FighterInformation:
  byte    version               // always 1
  short   budget
  byte    breedId
  byte    nameLen
  byte[]  name
  byte    sex
  byte    skinIndex
  short   spellsBlobLen         // 0 if no spells
  byte[]  spellsBlob            // flat int32[] spell ids (see above)
  short   cardsBlobLen          // 0 if no cards
  byte[]  cardsBlob             // [short pos][int32 id] pairs (see above)
```
On `unserialize`, invalid name/sex/skinIndex/breedId fall back to sane defaults client-side only
(`"Noob"`, `0`, `0`, `Breed.FECA`) — not relevant to the Go encode path but worth knowing the real
client tolerates malformed values this way.

**Go server payload** (`handlers_fighter.go:32-43`):
```
short   (unused leading short, purpose unclear, preserved for framing parity)
byte    clientVersionByte     // unused
short   budget
byte    breed
pstring name
byte    sex
byte    skin
short   spellsBlobLen
byte[]  spellsBlob            // flat int32[] (parseSpellIDs)
short   objectsBlobLen
byte[]  objectsBlob           // [short pos][int32 id] pairs (parseInventoryIDs)
```
**Discrepancy:** Go's leading `short`+`byte` (3-byte prefix) does not appear anywhere in the real
client's `encode()` (which writes only `short len` + the blob, starting directly with
`byte version=1`). This looks carried over from legacy `org.ankarton`
(`FighterCreateRequest.java:19-20`). **Needs verification against a real client capture** — if
wrong, every field after it desyncs.

## FIGHTER_CREATE_RESULT (Send 6000)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../teamManagement/CreationFighterInformationMessage.java:30-49`
**Go source:** `server/internal/dispatch/packets_fighter.go:37-53`
**Payload:**
```
byte    errorCode              // 0 = success; non-zero = failure, no further fields
// --- only if errorCode == 0 ---
long    fighterId
short   fighterInfoLen
byte[]  fighterInfo             // same FighterInformation layout as above
```
Client feeds the blob into `FighterInformation.unserialize()` then `initWithFighterInformation()`.
Go's shape matches the real client wire format.

## FIGHTER_DELETE_REQUEST (Recv 6003)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/DeleteFighterInformationRequestMessage.java:28-34`
**Go source:** `handlers_fighter.go:68-76`
**Payload:** `long fighterId`. Exact match.

## FIGHTER_DELETION_RESULT (Send 6002)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../teamManagement/DeletionFighterInformationMessage.java:28-38`
**Go source:** `packets_fighter.go:55-66`
**Payload:**
```
byte    errorCode              // 0 = success
// --- only if errorCode == 0 ---
long    fighterInformationId
```
Exact match.

## FIGHTER_INFORMATION_LIST_REQUEST (Recv 6005)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/FighterInformationListRequestMessage.java:24-26`
**Go source:** `handlers_fighter.go:78-95`
**Payload:** empty.

## FIGHTER_INFORMATION_LIST (Send 6006)
**Direction:** Server → Client
**Status:** partial — Go deliberately fixes a legacy inconsistency, see below
**Client source:** `client/.../teamManagement/FighterInformationListMessage.java:27-43`
**Go source:** `packets_fighter.go:87-102` (`buildFighterInformationList`)
**Payload:**
```
byte    fighterCount
repeated fighterCount times:
  long    fighterId
  short   fighterInfoLen
  byte[]  fighterInfo          // serialized FighterInformation, same layout as create-result
```
Client stores each entry as a raw `HashMap<Long, byte[]>`, does not eagerly unserialize.

**Legacy inconsistency confirmed:** the legacy server has **two different senders** for opcode
6006 that disagree — `FightInformationListRequest.parse()` (triggered by 6005) hand-builds each
entry with `.putShort(0).putShort(0)` for spell/object blobs, **always zeroing the loadout** (a
real bug — the "manage fighters" screen shows fighters with no equipped items). Meanwhile
`TeamPresetListRequest.parse()` (triggered by 6031, also re-sends 6006 afterward) calls
`fighter.serialize()` and sends the real loadout.

Go's `buildFighterInformationList` is used by **both** triggers and **always includes the real
loadout** — a deliberate, documented fix over the legacy zeroing bug.

## FIGHTER_UPDATE_INVENTORY_REQUEST (Recv 6011)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/UpdateFighterInventoryRequestMessage.java:30-50`
**Go source:** `handlers_fighter.go:97-132`
**Client payload:**
```
long    fighterId
short   spellInventoryBlobLen
byte[]  spellInventoryBlob     // flat int32[] spell ids (4 bytes each) -- StackInventory, serializeQuantity=false
short   equipmentInventoryBlobLen
byte[]  equipmentInventoryBlob // [short pos][int32 id] pairs (6 bytes each) -- ArrayInventory
```
Go decodes the spell blob with `parseSpellIDs` (flat `int32[]`) and the equipment blob with
`parseInventoryIDs` (`[short pos][int32 id]` pairs) — matching the real client format for each
(`inventory_codec.go`).

## FIGHTER_UPDATED_INFORMATION_INVENTORY (Send 6010)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../teamManagement/UpdatedFighterInformationInventoryMessage.java:32-51`
**Go source:** `packets_fighter.go:104-129`
**Payload:**
```
long    fighterId
byte    errorCode              // 0 = success
// --- only if errorCode == 0 ---
short   spellInventoryBlobLen
byte[]  spellInventoryBlob     // flat int32[] (buildSpellBlob)
short   equipmentInventoryBlobLen
byte[]  equipmentInventoryBlob // [short pos][int32 id] pairs (buildInventoryBlob)
```
Note: `internal/protocol/names.go:87` labels this opcode `"FIGHTER_UPDATED_INVENTORY"` (cosmetic
naming mismatch vs. the client's `FIGHTER_UPDATED_INFORMATION_INVENTORY`; opcode number 6010 is
correct either way).

## TEAM_PRESET_SAVE_REQUEST (Recv 6021)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/SaveTeamPresetRequestMessage.java:27-33`
(serializes via `TeamPreset.java:142-158`)
**Go source:** `server/internal/dispatch/handlers_team.go:26-72`
**Payload:**
```
short   id                     // -1 for a brand-new, unsaved preset
byte    nameLen
byte[]  name
byte    fighterCount
repeated fighterCount times:
  long  fighterId
```
Exact match. Server allocates the real slot when client sends -1 and echoes it back.

## TEAM_PRESET_SAVE (Send 6020)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../teamManagement/SaveTeamPresetMessage.java:31-47`
(unserializes via `TeamPreset.java:176-187`)
**Go source:** `server/internal/dispatch/packets_team.go:8-23`
**Payload:**
```
byte    errorCode              // 0 = success
// --- only if errorCode == 0 ---
short   id                     // real allocated slot/id
byte    nameLen
byte[]  name
byte    fighterCount
repeated fighterCount times:
  long  fighterId
```
Client wraps this in a `try/catch(BufferUnderflowException)` — a truncated payload fails silently
client-side. Go's shape matches exactly.

## TEAM_PRESET_DELETE_REQUEST (Recv 6023)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/DeleteTeamPresetRequestMessage.java:28-34`
**Go source:** `handlers_team.go:74-90`
**Payload:** `short teamPresetId`. Exact match.

## TEAM_PRESET_DELETION (Send 6022)
**Direction:** Server → Client · **Status:** implemented (with a silent-failure asymmetry)
**Client source:** `client/.../teamManagement/DeletionTeamPresetMessage.java:28-38`
**Go source:** `packets_team.go:25-31`
**Payload:**
```
byte    errorCode              // 0 = success
// --- only if errorCode == 0 ---
short   teamPresetId
```
**Note:** unlike every other Recv/Send pair in this doc, Go's `handleTeamPresetDelete` has **no
error path** — on `DeleteTeam` failure it logs and returns without ever sending a response (no
`byte(1)` error frame). This matches legacy `org.ankarton` behavior (`TeamPresetDeleteRequest.java`
only responds `if (team != null)`), but is a silent-failure UX gap worth revisiting.

## TEAM_PRESET_LIST_REQUEST (Recv 6031)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../teamManagement/TeamPresetListRequestMessage.java:24-26`
**Go source:** `handlers_team.go:92-117`
**Payload:** empty.
Handling triggers **two** outbound sends: `TEAM_PRESET_LIST` (6030) followed by
`FIGHTER_INFORMATION_LIST` (6006) — matches legacy behavior.

## TEAM_PRESET_LIST (Send 6030)
**Direction:** Server → Client
**Status:** implemented — with a deliberate fix over a legacy data-loss bug
**Client source:** `client/.../teamManagement/TeamPresetListMessage.java:27-43`
(unserializes each entry via `TeamPreset.java:176-187`)
**Go source:** `packets_team.go:33-64` (`buildTeamPresetList`)
**Payload:**
```
byte    teamPresetCount
repeated teamPresetCount times:
  short   id
  byte    nameLen
  byte[]  name
  byte    fighterCount
  repeated fighterCount times:
    long  fighterId
```
**Legacy bug, confirmed and fixed:** legacy `TeamPresetListRequest.parse()` writes only the fighter
**count**, never the actual fighter IDs — even though `TeamPreset.unserialize` always tries to read
that many longs afterward. This structurally truncates the legacy payload relative to what the
client expects, corrupting subsequent reads on a real client. Go's `buildTeamPresetList` fixes this
by writing the fighter IDs after the count, matching the client's actual `unserialize` contract.

---

## Discrepancies & corrections vs `../02-protocol.md` §2.4.8

1. **§2.4.8's documented `Fighter.serialize()` layout is wrong on two points** (apparently reverse-
   engineered from legacy `org.ankarton Fighter.java`, a simplified non-authoritative
   reimplementation, rather than the real client):
   - Spell blob claim (`spellsByteLength = spells.size()*4`, flat `int32 spellId`) is **correct**.
   - Object blob claim (`objectsByteLength = objects.size()*4`, flat `int32 cardTemplateId`) is
     **wrong** — the real equipment blob is `[short pos][int32 id]` pairs, 6 bytes/entry, not 4.
   - The doc conflates two distinct formats: `AbstractFighter.serialize()` (general fighter wire
     format, no budget field) vs. `FighterInformation.serialize()` (used by
     FIGHTER_CREATE_RESULT/FIGHTER_INFORMATION_LIST/team-preset-triggered lists, has a `budget`
     field, no inline id). Corrected, complete versions of both are given above.
2. **FIXED.** Go's `inventory_codec.go` previously treated both spell and equipment blobs as the
   same 6-byte `[short pos][int32 id]` format. Only equipment genuinely uses that format; spells
   are a flat `int32[]`. This was a real wire-incompatibility with the actual client (a real
   client's spell sub-blob would be misparsed by Go) and also the root cause of a reported
   production bug where equipping spells silently failed to persist: the misaligned parse produced
   garbage ids that didn't match any real spell template id, `filterKnownSpells` correctly dropped
   them, and `UpdateInventory` persisted zero spell rows. Fixed by adding a dedicated
   `parseSpellIDs`/`buildSpellBlob` codec (flat `int32[]`) used for spells, while
   `parseInventoryIDs`/`buildInventoryBlob` (`[short pos][int32 id]`) remains for equipment/cards.
   `fighter_inventory_test.go`/`client_test.go` were updated with a separate `spellBlob`/
   `parseSpellBlob` test helper so the e2e regression test now validates the correct, asymmetric
   real-client wire format instead of internal self-consistency only.
3. **`FIGHTER_CREATE_REQUEST`'s leading `short`+`byte` prefix** in Go does not appear in the real
   client's encode. Carried over uncritically from legacy `org.ankarton`. **Needs verification
   against a real client packet capture** before shipping — looks like a spurious extra offset.
4. **`FIGHTER_INFORMATION_LIST`'s always-populated loadout** is a deliberate, correctly documented
   improvement over both legacy senders disagreeing — not a bug.
5. **`TEAM_PRESET_LIST`'s inclusion of fighter IDs** is a deliberate, correctly documented fix of a
   genuine legacy data-loss bug — not a bug.
6. **`TEAM_PRESET_DELETION`'s missing error-path response** is a minor asymmetry vs. other opcode
   pairs in this doc (matches legacy behavior but should be flagged as a potential silent-failure
   UX gap, independent of client-source-of-truth concerns).
7. **FIXED.** Equipment blob positions (`pos` in `[short pos][int32 id]`) were assigned by
   sequential array index (`buildInventoryBlob`'s loop index) instead of by the item's fixed
   `FighterCardType.getInventoryPosition()` slot. Since the real client's `ArrayInventory`
   content-checker rejects any (position, item) pair where they don't match (see the inventory
   sub-formats section above), this caused pet/cloak/hat/dofus equipment to be silently dropped by
   the client on unserialize whenever it didn't coincidentally land on its required slot index
   (reported symptom: "weapon persists, pet/hat/cape/dofus don't"). Fixed by adding
   `gamedata.FighterCardInventoryPosition(cardType)` (mirrors `FighterCardType` exactly:
   weapon=0/pet=1/cloak=2/hat=3/dofus=4) and changing `buildInventoryBlob` to look up each id's
   real category via `gamedata.Store.FighterCards` instead of using its array index. All three
   callers (`serializeFighter`, `buildFighterInformationList`, `buildFighterUpdatedInventory`) now
   take the `*gamedata.Store` needed for this lookup. `test/e2e/fighter_inventory_test.go` was
   extended to cover all 5 equipment categories, submitted out of category order, and to assert
   the wire position of each equipped item matches its required slot.
