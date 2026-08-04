# DofusArena 2.70 — Game Data Format (`data.bdat` / `indexes.bdat`)

Byte-for-byte spec for parsing the 2.70 static game data (cards, spells,
effects, summons, events, static effects) in a Go server v2.70.

**The 2.70 format is rearchitected vs 2007** — the old flat per-type `.dat`
parsers will not work. Everything below is verified against the decompiled
client and the raw bytes of the shipped files.

Files (note the `bdata\` subdir):
- `compiled/game/contents/bdata/data.bdat` (~814 KB)
- `compiled/game/contents/bdata/indexes.bdat` (~449 KB)

All integers **big-endian**.

---

## 1. Storage engine

Two files, two different filters:

| File | Compression | Contents |
|---|---|---|
| `indexes.bdat` | **raw / none** | index entries mapping `(type, "id", value) → offset` |
| `data.bdat` | **zlib (per record)** | N independent zlib streams, one per record |

### `indexes.bdat` — raw, no header
Sequence of entries until EOF (no count, no magic):
```
entry (le_2):
  i32   type          record type id
  UTF   indexName     Java modified-UTF: u16 len + bytes (always "id")
  UTF   indexValue    the key, as a string (e.g. "1")
  i64   position      absolute byte offset into data.bdat
```
Load: read entries until EOF, group into `map[type]map[indexName][]entry`.
(Shipped file: 19,781 entries, 24 type codes, all `indexName=="id"`.)

### `data.bdat` — concatenation of independent zlib streams, no header
Each record was written by its own closed `DeflaterOutputStream`, so the file is
N back-to-back complete **zlib streams** (RFC 1950: `78 01` header + DEFLATE body
+ Adler-32 trailer; level 1). The index `position` is the byte offset of that
record's `78 01`.

**To read one record:** seek `data.bdat` to `position`, wrap in a zlib reader,
then read the block:
```
azf block (from the inflated stream):
  i32   id
  i16   version        (always 1)
  i32   dataLen
  byte[dataLen] payload
```
Parse `payload` with the per-type record decoder (big-endian), using `id` and
`version`.

### Go recipe
```go
// load: read indexes.bdat raw, loop until EOF
//   type=int32BE; name=readJavaUTF(); value=readJavaUTF(); pos=int64BE
// fetch one record:
f.Seek(pos, io.SeekStart)
zr, _ := zlib.NewReader(f)          // compress/zlib, NOT flate (Java uses zlib wrapper)
id := readInt32BE(zr)
ver := readInt16BE(zr)
n := readInt32BE(zr)
payload := make([]byte, n); io.ReadFull(zr, payload)
// decode payload big-endian per type
```
Gotchas: use `compress/zlib` (the `78 01` proves the zlib wrapper); each record
is its own stream (seek before each inflate); `readJavaUTF` = `u16` length +
modified-UTF-8 bytes.

---

## 2. Record type-id registry (enum `atr_0`, `record.cq()`)

| Type id | Class | Loader name | Meaning |
|---:|---|---|---|
| 100 | `aPp` | `contentLoader.card` | CoachCard |
| 200 | `Ht` | (embedded) | Effect (reused nested type) |
| 210 | `rf_2` | `contentLoader.staticEffect` | StaticEffect |
| 220 | `co_1` | `contentLoader.spell` | Spell |
| 230 | `ama_1` | `contentLoader.event` | Event |
| 250 | `uh_0` | `contentLoader.card` | FighterCard |
| 300 | `jz_2` | `contentLoader.summoning` | Summoning |

Other type codes in the index (1, 101, 210…1600) are non-gameplay assets
(graphics, UI, i18n metadata) — not needed for combat/server logic.

**Record conventions:** version guard `== 1`; strings `[i32 len][UTF-8]`; int
array `[i32 count][i32×count]`; float array `[i32 count][f32×count]`; long array
`[i32 count][i64×count]`; map `aim_1` = `[i8 count][(i8 key, i32 val)×count]`.

---

## 3. Effect record `Ht` (type 200) — the reused nested type

Field order (big-endian):
| # | field | type | meaning |
|--:|---|---|---|
| 1 | effectId | i32 | |
| 2 | actionId | i32 | RunningEffect / action id |
| 3 | parentId | i32 | container id |
| 4 | parentType | string | e.g. `FIGHTER_CARD`, `TRAP`, `SPECIAL` |
| 5 | areaShape | i32 | |
| 6 | areaOrdering | i16 | |
| 7 | affectedByLocalisation | bool | |
| 8 | targetTriggerIsSelf | bool | |
| 9 | isPersonal | bool | |
| 10 | hasSingleTarget | bool | |
| 11 | isCritical | bool | |
| 12 | params | f32[] | |
| 13 | triggersBefore | i32[] | |
| 14 | triggersAfter | i32[] | |
| 15 | endTriggers | i32[] | |
| 16 | areaSize | i32[] | |
| 17 | (reserved) | i32[] | no setter — reserved |
| 18 | duration | i32[] | |
| 19 | targets | i64[] | target validator masks |
| 20 | triggeredWithDuration | bool | |
| 21 | appliedIfTargetValid | bool | |

**Embedding in parent records (effect list):**
```
[i32 effectCount]
effectCount × { i32 innerId, i16 innerVer, i32 blobLen, byte[blobLen] }
    → parse each blob as Ht.a(blob, innerId, innerVer)
```
> Different from the `np_1` bonus embedding (see §9) which is inline with no
> length prefix.

---

## 4. Spell `co_1` (type 220), loader `contentLoader.spell`

| # | field | type | meaning |
|--:|---|---|---|
| 1 | spellId | i32 | |
| 2 | breedId | i32 | → Breed enum |
| 3 | value | i32 | |
| 4 | aiTargetId | i32 | |
| 5 | scriptId | i32 | |
| 6 | actionPoints | i8 | |
| 7 | maxPerPlayer | i8 | cast-frequency |
| 8 | minInterval | i8 | cast-frequency |
| 9 | maxPerTurn | i8 | cast-frequency |
| 10 | (byte) | i8 | cast-frequency (verified 0 for most spells) |
| 11 | (byte) | i8 | cast-frequency (verified 0 for most spells) |
| 12 | **rangeMax** | i8 | range is at 12/13, NOT 10/11 — verified vs raw bytes (Iop melee 4=1/1, Cra 3=8/5, Feca 31=4/1) |
| 13 | **rangeMin** | i8 | fields stored (max,min); normalize to [min,max] |
| 14 | testLos | bool | verified: 146/203 spells, 58/71 damage spells |
| 15 | onlyLine | bool | verified: 19/203 spells |
| 16 | testFreeCell | bool | verified: NeedFreeCell true on only 1/71 damage spells |
| 17 | useAutomaticDescription | bool | |
| 18 | (bool) | bool | new in 2.70 |
| 19 | (bool) | bool | new in 2.70 |
| 20 | criterion | string | |
| 21 | effects | effect list | see §3 |
| 22 | (longs) | i64[] | new in 2.70 |
| 23 | (int) | i32 | new in 2.70 |

---

## 5. FighterCard `uh_0` (type 250), loader `contentLoader.card`

| # | field | type | meaning |
|--:|---|---|---|
| 1 | id | i32 | |
| 2 | type | i16 | FighterCardType (was byte-index in 2007) |
| 3 | weaponActionPoints | i8 | |
| 4 | value | i32 | |
| 5 | rangeMin | i32 | |
| 6 | rangeMax | i32 | |
| 7 | scriptId | i32 | |
| 8 | subType | i32 | |
| 9 | testLos | bool | |
| 10 | onlyLine | bool | |
| 11 | testFreeCell | bool | |
| 12 | allowedWhenCarrying | bool | |
| 13 | allowedWhenCarried | bool | |
| 14 | (bool) | bool | new in 2.70 |
| 15 | effects | effect list | see §3 |

---

## 6. CoachCard `aPp` (type 100), loader `contentLoader.card`

| # | field | type | meaning |
|--:|---|---|---|
| 1 | id | i32 | |
| 2 | iconRef | i32 | gfx reference |
| 3 | cardSet | i32 | group id |
| 4 | value | i32 | |
| 5 | bonusMap | `aim_1` map | `[i8 count][(i8 key, i32 val)]` |
| 6 | (int) | i32 | |
| 7 | (byte) | i8 | |
| 8 | floatParams | f32[] | |
| 9 | isUnique | bool | |
| 10 | (bool) | bool | |
| 11 | (int) | i32 | |
| 12 | (bool) | bool | |
| 13 | (bool) | bool | |
| 14 | (bool) | bool | |
| 15 | criteria | `[i8 count]` × `akw_0` | see §9 |
| 16 | (byte) | i8 | |
| 17 | (int) | i32 | |
| 18 | rank | i32 | |
| 19 | bonuses | `[i8 count]` × `np_1` | see §9 |
| 20 | (short) | i16 | |
| 21 | (short) | i16 | |
| 22 | (short) | i16 | |
| 23 | (byte) | i8 | |
| 24 | (int) | i32 | |
| 25 | (byte) | i8 | |
| 26 | (int) | i32 | |

---

## 7. Event `ama_1` (type 230), loader `contentLoader.event`

| # | field | type |
|--:|---|---|
| 1 | id | i32 |
| 2 | (short) | i16 (new in 2.70) |
| 3 | useAutomaticDescription | bool |
| 4 | effects | effect list (see §3) |

---

## 8. StaticEffect `rf_2` (type 210), loader `contentLoader.staticEffect`

| # | field | type | meaning |
|--:|---|---|---|
| 1 | id | i32 | |
| 2 | type | string | `TRAP` / `SPECIAL` dispatch |
| 3 | label | string | |
| 4 | areaShape | i32 | |
| 5–7 | (ints) | i32 ×3 | |
| 8 | scriptId | i32 | |
| 9 | areaSize | i32[] | |
| 10 | applicationTriggers | i32[] | |
| 11 | unapplicationTriggers | i32[] | |
| 12 | deactivationDelay | i32[] | |
| 13 | effects | effect list (see §3) | |

---

## 9. Summoning `jz_2` (type 300), loader `contentLoader.summoning`

| # | field | type | meaning |
|--:|---|---|---|
| 1 | id | i32 | |
| 2 | baseHp | i32 | |
| 3 | baseAp | i32 | |
| 4 | baseMp | i32 | |
| 5 | gfx/spell ids | `[i8 count]` × i32 | |
| 6 | (int) | i32 | |
| 7–12 | (bools) | bool ×6 | |
| 13 | (int) | i32 | |
| 14 | (int) | i32 | |
| 15 | (byte) | i8 | |
| 16 | blob list | `[i8 count]` × `{i32 len, bytes}` | |
| 17 | (int) | i32 | |

---

## 10. Nested objects

### Criteria `akw_0.J(bb)`
`i32 type` (dispatches concrete subclass), `i32[] operands` (`[i8 count][i32×count]`),
`i64 mask`, `i8 operator`.

### Bonus `np_1.j(bb)`
`i32 type`, `i32 id`, `i32 parentId`, `i32[] params` (`[i8 count][i32×count]`),
then `i16 ver`; **if ver != 0**: `i32 id` then **inline** `Ht.a(buf, id, ver)`
(no length prefix — contrast with §3). If ver == 0, no effect.

---

## 11. Breed — NOT a data record

Breed is a hardcoded Java **enum** (no loader). Transcribe these into the server:

| Breed | id | notes |
|---|---:|---|
| NONE | -1 | |
| MONSTER | 0 | |
| FECA … PANDAWA | 1 … 12 | the 12 playable classes |
| GOD | 98 | |
| COACH | 99 | |

Baked-in per-breed stats: `baseHp, baseAp=6, baseMp=3, baseInit, baseCH=5,
baseCM=1, value, closeCombatElement, closeCombatAp=5, closeCombatDamages=5,
closeCombatCriticalDamages=7`. Read the exact per-breed numbers from
`client/source/com/ankamagames/dofusarena/common/game/fighter/Breed.java` on the **`main`** branch (the 2.04b line)
(the enum was stable across versions).

---

## 12. 2.70 vs 2007 deltas (summary)

1. Storage: flat per-type `.dat` → one compressed indexed store (`data.bdat`).
2. Per-record `i16 version` tag added.
3. Effects: inline in 2007 → self-describing length-prefixed blobs, embedded per
   record (+ new fields: reserved int[], 2 bools).
4. Spell +2 bytes, +2 bools, +long[], +int.
5. FighterCard: type widened i8→i16, fields reordered, +1 bool.
6. CoachCard: massively expanded (bonus map, criteria[], bonus[], shorts…).
7. Summoning: 6 ints → int-list + 6 bools + 2 ints + byte + blob-list + int.
8. Event: +i16 between id and bool.
9. StaticEffect: restructured to 2 strings + ints + int[]s + effect list.
10. Breed: still an enum, no record.
