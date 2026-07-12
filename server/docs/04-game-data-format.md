# 4. Game Data File Formats & Lazy Repository Design

Reverse-engineered from `src/org/ankarton/util/document/**`. These `.dat` files are
custom binary formats with **no self-describing schema, no version field, and no length
markers for nested sections** — each loader must read fields in the exact order the
original export tool wrote them. This document is the canonical reference so we don't have
to re-derive it from Java source during Go implementation.

## 4.1 Common primitives

All files are read as a single in-memory `[]byte` buffer, big-endian, no compression. Field
readers (from `DocumentLoader.java:60-115`):

| Java method | Go equivalent | Encoding |
|---|---|---|
| `readBoolean()` | `r.Bool()` | 1 byte, non-zero = true |
| `readByte()` | `r.Int8()` / `r.Uint8()` | 1 byte |
| `readShort()` | `r.Int16()` | 2 bytes BE |
| `readInteger()` | `r.Int32()` | 4 bytes BE |
| `readFloat()` | `r.Float32()` | 4 bytes BE IEEE-754 |
| `readString()` | `r.String()` | **4-byte length prefix** (int32, not byte!) + UTF-8 bytes — note this differs from the network protocol's 1-byte string length prefix (§2.2) |
| `readIntArray()` | `r.Int32Slice()` | 4-byte count + N × int32 |
| `readShortArray()` | `r.Int16Slice()` | 4-byte count + N × int16 |
| `readFloatArray()` | `r.Float32Slice()` | 4-byte count + N × float32 |

Go implementation: `internal/gamedata/parser/reader.go` wraps a `[]byte` + offset cursor
with these exact methods, panic-recovering into an `error` at the call site (parse errors
should never crash the server — see §4.6).

## 4.2 `cards.dat` — three logical sections in one file

This single file is read by **three different loaders at three different byte offsets**,
each re-opening the file from scratch and manually skipping prior sections it doesn't
care about (`FighterCardLoader.skip()`, `FighterCardLoader.java:27-35`). This is fragile
but must be preserved byte-for-byte. The Go port should instead parse this file **once**,
sequentially, in a single `gamedata/parser/cards.go` function that returns all three
result sets together — functionally identical output, but avoids the wasteful
re-read/re-skip pattern.

### Section 1: Coach Cards (count-prefixed array)

```
int32   coachCardCount
repeated coachCardCount times (CoachCardTemplate, 16 bytes each):
  int32   id
  int32   type
  int32   value
  int32   set
```
(`CoachCardTemplate.java:13-18`)

### Section 2: Fighter Cards (count-prefixed array, variable-length entries)

```
int32   fighterCardCount
repeated fighterCardCount times:
  int32   id
  byte    type                    // 1 = weapon (WeaponFighterCardTemplate), else generic
  --- FighterCardTemplate body (FighterCardTemplate.java:20-31) ---
  byte    <unused>
  bool    <unused>
  int32   <unused>                // "weapon" leftover fields, not consumed meaningfully
  int32   <unused>
  bool    <unused>
  bool    <unused>
  int32   value
  bool    <unused>
  bool    <unused>
  int32   scriptId
  int32   subType
  --- if type == 1, WeaponFighterCardTemplate reads additional weapon-specific fields ---
  (see WeaponFighterCardTemplate.java — not yet reviewed, read before implementing;
   FighterCardTemplate's own constructor already consumes the "weapon" placeholder fields
   above regardless of type, so WeaponFighterCardTemplate likely just re-interprets/extends
   rather than reading more bytes — VERIFY against source before coding)
```

### Section 3: Effects (count-prefixed array, attaches to Fighter Cards by parent id)

```
int32   effectCount
repeated effectCount times (Effect.java:25-40):
  int32   id
  string  parentType         // e.g. "FIGHTER_CARD_..." — only entries starting with
                              //   "FIGHTER_CARD" get attached (see onEffectLoaded)
  int32   parentId
  int16   <unused, discarded>
  int32[] duration            // [tableTurnDuration, turnDuration] typically length 2;
                              //   value 63 in a slot = infinite (see 05-combat-engine.md §2)
  int32   actionId            // maps to a RunningEffect implementation (see 05-combat-engine.md)
  bool    isCritical
  float32[] params            // damage dice / magnitude, shape depends on actionId
  int16   areaShape           // maps to AreaOfEffectEnum (see 05-combat-engine.md §7)
  int32[] areaSize            // shape-specific size params
  int32[] targets             // target-validator selector ids
  int32[] triggersAfter
  int32[] triggersBefore
  bool    affectedByLocalisation
```

## 4.3 `spells.dat`

```
int32   spellCount
repeated spellCount times (Spell.java:34-52 constructor order):
  int32   id
  byte    actionPointsCost         // "PARequired"
  byte    castFrequencyMaxPerPlayers
  byte    castFrequencyMaxPerTurn
  byte    castFrequencyMinInterval
  bool    castTestLineOfSight
  bool    castOnlyLine
  byte    rangeMin
  byte    rangeMax
  int32   price
  int32   aiTargetId
  int32   scriptId
  int32   breedId
  string  criterion                 // criterion expression string, see combat engine spec
  bool    useAutoDescription

int32   effectCount
repeated effectCount times:
  int32   id
  string  parentType                // expected "SPELL" for these to attach (Spell.addEffect
                                     //   is currently a no-op stub in Java — see note below)
  int32   parentId                  // = spell id to attach to
  int16   <unused, discarded>
  int32[] duration
  int32   actionId
  bool    isCritical
  float32[] params
  int16   areaShape
  int32[] areaSize
  int32[] targets
  int32[] triggersAfter
  int32[] triggersBefore
  bool    affectedByLocalisation
```

> **Bug to fix in the port**: `Spell.addEffect()` in the current Java code
> (`Spell.java:66-68`) has its body commented out — spell effects are parsed but silently
> discarded, meaning **no spell in the current server actually carries its effects in
> memory**, even though the file is fully parsed. The Go port must actually attach parsed
> effects to their spell (this is a pure bug fix, not a design choice — the data is right
> there in the file).

## 4.4 `events.dat`

```
int32   eventCount
repeated eventCount times (Event.java:17-20):
  int32   id
  bool    useAutoDescription

int32   effectCount
repeated effectCount times:
  (same Effect layout as above)
```

> **Bug to fix in the port**: `EventLoader.read()` (`EventLoader.java:34-49`) parses each
> effect's fields into local variables and then **discards them without ever calling
> `Event.addEffect()`** — dead code, confirmed via review. The Go port should wire this up
> correctly (attach effect to `parentId`'s event) since the data is present in the file.

## 4.5 `staticEffects.dat`

**Status: fully reverse-engineered and implemented** (`internal/gamedata/parser/
staticeffects.go`, `internal/gamedata/templates.go`'s `StaticEffectAreaTemplate` — see
`docs/08-java-parity-roadmap.md` Phase M). The earlier claim in this section that
`StaticEffectLoader`'s body was "entirely commented out" was **incorrect** — a fresh
decompile of the real game's `core.jar` (a legally-owned copy, see §4.9.1's note on this
project's javap-verification workflow) produced a complete, substantial `read()` method
for `client/com/ankamagames/dofusarena/client/core/contentInitializer/
StaticEffectLoader.java`, confirmed genuine (not a decompiler artifact) via `javap -c -p`
disassembly of the real compiled `.class`, and empirically verified: parsing the real
1,868-byte `data/staticEffects.dat` with the layout below consumes **exactly** 1,868 bytes
with zero leftover.

```
int32   areaCount
repeated areaCount times (StaticEffectLoader.read(), confirmed field-for-field):
  int32    id
  int32    scriptId
  int16    areaShapeId              // AreaOfEffectEnum id (1=point, 2=circle, ...)
  int32[]  areaParams               // shape-specific size params (e.g. circle radius)
  int32[]  applicationTriggers      // trigger-bit ids; confirmed real values: 1=enter
  int32[]  unapplicationTriggers    //   (application), 2=exit (unapplication)
  int16    maxExecutionCount        // >=63 means unlimited (AbstractEffectArea's exact
                                     //   `!(count < 63 && count >= 0)` hasNoExecutionCount()
                                     //   check)
  int32[]  applicationTargets       // FightTargetValidator selector ids
  int32[]  unapplicationTargets
  int32    targetsToShow
  string   effectAreaType           // "TRAP" or "SPECIAL" -- the ONLY two values ever
                                     //   written in this project's real data (all 10 real
                                     //   areas are one or the other); NOTE the real data
                                     //   pads this string with trailing spaces to a fixed
                                     //   18-char width (e.g. "AREA" -> "AREA              " for
                                     //   the effect ParentType field below -- a genuine
                                     //   authoring quirk in the original tool, not a parsing
                                     //   bug, confirmed by the length-prefix itself saying 18)
  int32[]  deactivationDelay        // [tableTurnDelay, turnDelay]; both real TRAP entries
                                     //   have this empty (no activation delay)
  int32    applicationCondition     // 0=always; 1/2/3=ONE_TIME_FOR_EVERYONE/TEAM/TARGET
                                     //   (AbstractEffectArea's SHOW_TO_*/ONE_TIME_FOR_*
                                     //   constants) -- both real TRAP entries use 0

int32   effectCount
repeated effectCount times:
  (same Effect layout as cards.dat/spells.dat/events.dat, §4.2 section 3)
  -- parentType is "AREA" (padded, see above), parentId matches one of the area
     records' own `id` field.
```

**Real data confirmed via full parse** (`internal/gamedata/parser/
staticeffects_realdata_test.go`): 10 areas total — 8 `SPECIAL` entries (ids 1002-1009) and 2
`TRAP` entries (id=1: point-shaped, single-use, actionID pointing at an instant-death-style
effect; id=2: circle radius-2, unlimited executions) — plus 16 effect records, all with
`ParentType` prefix `"AREA"` and `ParentType`/`ParentID` correctly attaching to their
respective area by id.

The 8 `SPECIAL` ids map **1:1** to the manual's named battlefield-cell types, now
cross-referenced by each area's own effect record (actionID/params) — this is the single
source of truth for `gamedata.specialCellBaseIDToType`, and it is also exactly the mapping
recovered from the maps' baked negative-gfx `Bonus` tiles (gfx `−1002..−1009`, see §4.9):

| SPECIAL id | area effect (actionID, params) | cell type |
|---|---|---|
| 1002 | 63 (instant kill) | killer |
| 1003 | 1, dmg 10 | trap |
| 1004 | 72, +1 range | eagle_eye |
| 1005 | 29/31/33/35, +resist | shield |
| 1006 | 78, +heal received | panacea |
| 1007 | 48/50/52/54, +damage | enthusiasm |
| 1008 | 13, +1 AP | motivation |
| 1009 | 69, heal 5 | healing_heart |

The per-map special-cell layouts are **derived** from these baked markers at map-load time
(`gamedata.Map.deriveSpecialCells`), so no hand-authored coordinate data is needed; an optional
`data/maps/<id>/specialcells.json` sidecar can override the derivation.

## 4.6 `summoning.dat`

```
int32   summoningCount
repeated summoningCount times (Summoning.java:15-22):
  int32   id
  int32   hp
  int32   pa           // AP granted to the summon
  int32   pm           // MP granted to the summon
  int32   gfx           // graphic/skin id
  int32   spellId       // spell the summon uses
```

## 4.7 `breeds.dat` and `items.dat`

- `breeds.dat`: **0 bytes**, currently unused — no loader references it at all. Breed base
  stats (HP/AP/MP/INIT max, close-combat AP cost — referenced conceptually in the combat
  engine spec, §05) are **not present anywhere in the current data files or code** and must
  either be sourced from the client's decompiled `Breed.java`/related constant tables, or
  hand-authored as new game-design data for this project. Flag this explicitly as an open
  question before combat-engine implementation.
- `items.dat`: 56 bytes, effectively empty (no loader references it). Likely a vestigial/
  placeholder file from an earlier iteration of the format. Skip parsing unless content
  review reveals otherwise.

## 4.8 Lazy repository design (Go)

```go
// internal/gamedata/repository.go
type Repository[T Identifiable] struct {
    once  sync.Once
    load  func() (map[int32]T, error)
    data  map[int32]T
    err   error
}

func (r *Repository[T]) ensureLoaded() error {
    r.once.Do(func() { r.data, r.err = r.load() })
    return r.err
}

func (r *Repository[T]) Get(id int32) (T, bool) {
    _ = r.ensureLoaded() // errors logged at load time; Get returns zero-value+false on failure
    v, ok := r.data[id]
    return v, ok
}

func (r *Repository[T]) All() []T { /* ensureLoaded then return slice of r.data */ }
```

Differences from the Java `Factory<T>` (`Factory.java`):
- **Indexed by `map[int32]T`**, not a linear-scan `ArrayList` — `Get()` is O(1) instead of
  O(n). With only hundreds of cards/spells this wasn't a perf emergency in Java, but it's
  a trivial improvement and removes a code smell.
- **Lazy** (`sync.Once`-gated) instead of eagerly loaded in `FactoryManager.initialize()`
  at startup — see `01-architecture.md` §1.4.2 for why this matters for startup time.
  `cards.dat`+`spells.dat` together are under 60KB, so parse time itself is sub-millisecond;
  the win here is purely about not blocking the listener bind on any I/O that isn't
  strictly necessary yet, and about not doing work at all if a code path (e.g. a server
  that only ever handles social features on a given deployment) never touches game data.
- **Thread-safe by construction** (`sync.Once`), unlike the Java version which has no
  explicit synchronization around `FactoryManager`'s lazy... actually the Java version
  isn't lazy at all (loaded unconditionally at boot), so this is a new property, not a
  fix to an existing race.
- Effects are attached to their parent (card/spell/event) **at parse time**, fixing the two
  dead-code bugs noted in §4.3/§4.4.

One `Repository[T]` instance per data type: `CoachCards`, `FighterCards`, `Spells`,
`Effects` (if kept as a standalone lookup in addition to being embedded in
cards/spells/events), `Events`, `Summonings`. Constructed once at `main()` composition
time, injected into services that need them (`service/fighter.go`, `combat/spell/*`).

## 4.9 Map data: `elements.ade` and `.amw` (the "Alea" binary formats)

**Status: fully reverse-engineered and implemented** (`internal/gamedata/parser/{alea_reader,elements_ade,amw,map_altitude}.go`,
`internal/gamedata/map.go` — see `docs/08-java-parity-roadmap.md` Phase K). This section
documents the full byte-level format, how it was recovered, and the exact verification
trail — useful both as an implementation reference and as a starting point for a future
standalone map-editor tool, since nothing else in this project's docs covers map data.

### 4.9.1 Why this needed a different research method

Every other format in this document (§4.1–§4.8) was recovered by reading the **decompiled**
`.java` source under `client/`. That source is reliable for `.dat` files because their
loaders are simple and self-contained. Map data is different: it's read by a much larger,
more optimization-heavy code path (`WorldMapDocumentAccessor`, `WorldElementManager`,
`WorldCell`) full of decompiler artifacts — duplicate/renamed local variables, a `switch`
whose case bodies fall through into a variable that was never assigned on some paths, and
at least one spot (`WorldElementManager.read()`'s `unknownElement.read(...)` call) where the
decompiled source calls a method on a variable that was never the one actually constructed
on that branch. Trusting that source at face value for a binary format with **no reference
decoder and no test vectors** would risk silently-wrong walkability data — worse than not
having the feature at all.

Because a real, legally-owned copy of the game (`E:\Ankama\DofusArena2-06\game\core.jar`)
exists on this machine, the format was instead confirmed by **disassembling the real
compiled `.class` files** with the JDK's `javap -c -p` (bytecode-level, not decompiled to
Java) and cross-referencing every field read against the decompiled source. Where they
agreed, the decompiled source was trusted; the one place they didn't agree
(`BonusElement`'s state-property size, see §4.9.4) was resolved by trusting the bytecode's
actual class hierarchy, then **empirically verified** against the real game's own
`elements.ade`/`.amw` files (parse consumes the file's exact byte length, zero leftover) —
the strongest possible confirmation available without an official reference decoder.

If you don't have access to the real `core.jar`, the `javap` step can be skipped and the
decompiled source trusted directly for everything except the one BonusElement quirk called
out below — but re-verify against a real file's exact length before trusting any new
map-format work.

### 4.9.2 Alea document convention (shared by both formats)

Both `elements.ade` and `.amw` extend `AleaDocumentAccessor` (`com.ankamagames.alea`).
Confirmed via `javap` on the real `AleaDocumentAccessor.class`:

- **Every Alea file starts with a 2-byte header**: `byte typeCode`, `byte version`
  (`readHeader()`). Known type codes (from each subclass's constructor):
  - `elements.ade`: typeCode = 69 (`'E'`), version = 1 (`WorldElementManager`'s
    constructor: `setAleaDocumentTypeCode((byte)69)`).
  - `.amw`: typeCode = 77 (`'M'`), version = 1 (`WorldMapDocumentAccessor`'s constructor:
    `setAleaDocumentTypeCode((byte)77)`).
- **Everything after the header is little-endian** (`AleaDocumentAccessor.open()`:
  `this.m_streamBuffer.order(ByteOrder.LITTLE_ENDIAN)`). This is the **opposite** of the
  `.dat` game-data convention (§4.1, big-endian) — a real, confirmed-by-bytecode
  inconsistency in the original codebase, not a mistake in this doc.
- `m_typesSize` (a static `int[]` on `AleaDocumentAccessor`, confirmed via `javap`):
  `{0, 1, 1, 1, 2, 2, 4, 4, 8, 4}` — the byte-size of a per-parameter value for each of the
  10 possible "param type" tag bytes used inside `.amw` cell-element records (§4.9.5).
  Go: `parser.AleaParamTypeSizes`.

Go implementation: `internal/gamedata/parser/alea_reader.go`'s `AleaReader` (distinct from
`Reader`, the big-endian `.dat`-format reader) + `PeekAleaHeader()`/`AleaTypeCodeWorldMap`/
`AleaTypeCodeWorldElements` in `amw.go`.

### 4.9.3 `elements.ade` — the shared element-definition catalog

One flat file (`content/data.jar/data/elements.ade` in this project, 28,232 bytes),
read entirely by `WorldElementManager.read()` (confirmed via `javap`). Unlike every `.dat`
file in §4.2–§4.7, **there is no top-level record count** — the loop condition is simply
`while (m_streamBuffer.position() < m_streamBuffer.limit())`, i.e. read records until the
buffer is exhausted:

```
repeated until EOF:
  int32  elementId
  int16  elementType        // discriminant, see the ElementKind table below
  byte   numStates
  repeated numStates times:
    byte state
    <per-kind state-properties payload, size depends on elementType -- see below>
```

**`elementType` discriminant values** (from `BasicElement`'s `ELEMENT_TYPE_*` constants,
confirmed identical in bytecode and decompiled source):

| Value | Kind | Java class | Has real properties payload? |
|---|---|---|---|
| 0 | Basic | `BasicElement` | No (0 bytes/state) |
| 1 | SpatialData | `SpatialDataElement` | Yes, 21 bytes (see below) — not observed in this project's actual `elements.ade`, but the format supports it |
| 2 | Graphical | `GraphicalElement` | **Yes, 30 bytes** (21 spatial + 9 graphical-specific) |
| 3 | Teint | `TeintElement` | No |
| 4 | Offset | `OffsetElement` | No |
| 6 | Group | `GroupElement` | No |
| 8 | LevelUnpiled | `LevelUnpiledElement` | No |
| 9 | Particle | `ParticleElement` | No |
| 10 | Shadow/Brightness | `BrightnessElement` | No |
| **1000** | **FightStartPointElement** (custom, DofusArena-specific) | extends `BasicElement` directly | **No** |
| **1001** | **FightStartCoachPointElement** (custom) | extends `BasicElement` directly | **No** |
| **1002** | **BonusElement** (custom) | extends **`GraphicalElement`** | **Yes, 30 bytes — see §4.9.4** |

Types 1000/1001/1002 are registered via a per-game `CustomElementFactory`
(`DofusArenaCustomElementFactory.java`) — they are **not** part of the generic Alea/`baseImpl`
framework and would differ for a different game built on the same engine. Every other type
(0–10) is generic-engine machinery.

**SpatialDataElementProperties** (the 21-byte per-state payload shared by every Graphical-ish
kind), confirmed field-for-field via `javap` on the real `.class`
(`SpatialDataElementProperties.read()`):

```
int16  weight                (getShort(), despite the Java field being declared `int` -- decompiler-visible only via bytecode, NOT obvious from the .java source alone)
bool   uniqueWeightInLevel
bool   piled                 // does this element "stack" on top of what's below it? see §4.9.6
bool   mobileOnTop
int8   height                // signed byte; halved at read-time if slope!=0, see GraphicalProperties.Height()
bool   virtualHeight
int8   slope
bool   lineOfSight1
bool   lineOfSight3
bool   lineOfSight5
bool   lineOfSight7
bool   lineOfSightTop
bool   lineOfSightBottom
bool   move1
bool   move3
bool   move5
bool   move7
bool   moveTop
bool   moveBottom
bool   walkable              // THE field this whole effort exists for
```
Total: 2+1+1+1+1+1+1 + 6 (los) + 6 (move) + 1 (walkable) = **21 bytes**.

**GraphicalElementProperties** appends 9 more bytes after the 21 above (confirmed via
`javap` on `GraphicalElementProperties.read()`):
```
int32  gfxId
int16  originX   // getShort(), despite the Java field being declared `int`
int16  originY   // getShort()
bool   flip
```
Total per-state Graphical payload: 21 + 4 + 2 + 2 + 1 = **30 bytes**.

Go: `internal/gamedata/parser/elements_ade.go`'s `SpatialDataProperties`/`GraphicalProperties`,
`ParseElementsFile()`.

### 4.9.4 The one real ambiguity: does `BonusElement`(1002) carry a payload?

This is the single point where the decompiled `.java` source alone is genuinely
insufficient, and where the bytecode + empirical full-file verification was decisive.

`WorldElementManager.read()`'s `switch` only has real cases for the generic engine types
(2/3/4/6/8/9/10); every custom type (including 1000/1001/1002) falls into the `default`
branch, which asks the injected `CustomElementFactory` to construct the right Java object,
then unconditionally calls `.read(buffer)` on whatever `BasicElement` subclass came back.
**Which `read()` override actually runs therefore depends entirely on that object's real
class hierarchy**, not on anything visible in `WorldElementManager` itself:

- `FightStartPointElement`/`FightStartCoachPointElement` (`DofusArenaCustomElementFactory.
  createElement()`) both directly `extends BasicElement` — so they inherit
  `BasicElement.readStateProperties()`'s **empty, no-op** implementation. **0 bytes/state.**
- `BonusElement` **`extends GraphicalElement`** (confirmed in its own decompiled source,
  one line: `public class BonusElement extends GraphicalElement`) — so it inherits
  `GraphicalElement.readStateProperties()`, which reads the full 30-byte payload.
  **30 bytes/state**, identical to a real generic Graphical element.

This was empirically confirmed, not just inferred: parsing the real `elements.ade` while
treating type 1002 as zero-payload **desyncs partway through the file** (the parser reads a
garbage record with an absurd `numStates=255` around byte 28,049 and fails a bounds check
before reaching EOF). Treating it as 30-byte-payload makes the parser consume **exactly**
28,232 bytes — the file's real length — with zero bytes left over. This "does the parse
consume exactly the file's length" check is the strongest verification available for this
kind of reverse-engineering when no reference decoder exists, and is exactly why
`internal/gamedata/parser/alea_realdata_test.go`'s tests assert on it directly (loading the
project's own real `data/elements.ade`/`data/maps/2/*.amw` files rather than only synthetic
fixtures).

**Takeaway for future work on this format**: never assume a custom element type has no
payload just because it's absent from `WorldElementManager`'s switch — check its actual
Java class hierarchy (or, if extending the custom-type set for a map editor, be aware this
same "payload size follows real inheritance, not the type-code switch" rule applies to any
new custom type you might add).

### 4.9.5 `.amw` — the per-region map-chunk format

One file per 18×18-cell "chunk" of a map's grid, named `map_<coordX>_<coordY>.amw` (e.g.
`content/data.jar/data/maps/2/map_-1_0.amw`), under `maps/<mapID>/`. `mapID` is this
project's fight map (`fightMapID = 2` in `internal/dispatch/handlers_fight.go`), which has
exactly 3 real chunk files: `map_0_0.amw` (8,880 bytes — a real cell layout), plus
`map_-1_0.amw`/`map_1_0.amw` (335 bytes each — mostly-empty edge chunks). Read by
`WorldMapDocumentAccessor.read()`/`readCellDatas()`, confirmed via `javap`:

```
int32  coordX
int32  coordY
byte   size                        // cells per side; cellCount = size*size
repeated cellCount times (row-major: worldCellX = coordX*size + i%size, worldCellY = coordY*size + i/size):
  byte  levelCount
  repeated levelCount times:
    byte  elementCount
    repeated elementCount times:
      int32  elementId              // -> looked up in elements.ade's catalog
      byte   state                  // -> which ElementState of that element (open/closed door, etc.)
      int32  cellInstanceGroupId    // cosmetic grouping id (WorldGroupManager); no server-side meaning
      byte   paramCount
      repeated paramCount times:
        byte  type                 // index into AleaParamTypeSizes (§4.9.2)
        <AleaParamTypeSizes[type] raw bytes>
```

Confirmed byte-for-byte against all 3 real chunk files for this project's fight map: every
file's parse consumes its exact length with zero leftover bytes (see
`TestRealFightMapAMWParses` in `internal/gamedata/parser/alea_realdata_test.go`).

**The filename's `<coordX>_<coordY>` matches the parsed header's `coordX`/`coordY` fields
exactly** (confirmed: `map_-1_0.amw`'s header decodes to `coordX=-1, coordY=0`).

**Custom fight-marker elements** (`elementId` 1000/1001, see §4.9.3) carry exactly one
param: `type=3` (a single raw byte, per `AleaParamTypeSizes[3]==1`), whose value is a
team-side flag. Cross-referencing the real `map_0_0.amw`'s parsed
`FightStartCoachPointElement`(1001) cells against this project's **pre-existing, independently
hardcoded** teleport-destination cells in `handlers_fight.go`
(`(16,11)`/`(1,7)`, both at a placeholder `Z=-3`) found an **exact coordinate match** — both
hardcoded cells really do have a `FightStartCoachPointElement` at those exact (X,Y)
positions in the real game data, with param bytes `0` and `1` respectively. This is about as
strong a confirmation as reverse-engineering gets: two independently-derived pieces of
information (a hand-picked "looks about right" placeholder cell from before this format was
understood, and a from-scratch byte-level parse of the real file) agree exactly.

(`Z=-3` itself, however, does **not** correspond to any real walkable surface at either
cell once altitude is properly resolved — see §4.9.6's `TestMapStore_RealFightMap`. The
(X,Y) match confirms the *cell selection* was already correct; only the *altitude* was an
arbitrary placeholder, now resolved automatically via `Map.SurfacesAt`/
`resolveCoachStartSpots` in `handlers_fight.go` rather than a hardcoded constant.)

Go: `internal/gamedata/parser/amw.go`'s `AMWCellElement`/`AMWCell`/`AMWMapChunk`,
`ParseAMWFile()`.

### 4.9.6 Altitude/walkability resolution (turning raw elements into "can I stand here")

A cell's raw element list isn't directly usable for walkability — the *client's own*
purpose for this data is figuring out draw order and jump heights for an isometric scene,
which happens to require computing exactly the same "at what Z is this piece of ground, and
is it walkable" facts a server needs, just for a different reason. This algorithm
(`WorldMapDocumentAccessor.readCellDatas()`'s per-cell loop, confirmed via `javap`) is
ported as `parser.ResolveCellSurfaces()`:

- `currentAltitude` starts at 0 per cell and only ever changes via:
  - An **`OffsetElement`**(type 4): either sets `currentAltitude` directly (if the element's
    encoded "absolute" flag is set) or adds a signed delta to it. Mirrors
    `OffsetElement.isAbsolute()`/`getOffset()`'s slightly odd flat-byte-array indexing
    (`element.getParams()[1]`/`[3]`) — see `decodeOffsetParams()`'s doc comment for the
    exact index mapping.
  - A **piled Graphical element** (its `SpatialDataElementProperties.piled` flag is true):
    after being placed, `currentAltitude += element.height` — this is the "stacking" rule
    (a wall sitting on a floor raises the effective ground level for whatever's placed after
    it in the same cell).
- A **`LevelUnpiledElement`**(type 8) anywhere within a level marks that *entire level* as
  not-piled: instead of continuing to stack on the previous level's final altitude, it
  resets to the last **piled** level's saved altitude (`oldLevelAltitude`) — used for things
  like ceiling decorations that shouldn't raise the walkable floor height for whatever comes
  visually "above" them.
- Only **Graphical**(2) and the custom **Bonus**(1002, since it extends Graphical, see
  §4.9.4) element kinds produce a real "you can stand here" fact — every other kind (Offset/
  Teint/Brightness/Group/LevelUnpiled/Particle, and the non-Graphical custom kinds 1000/
  1001) either only adjusts the tracking state or carries no spatial-collision meaning.

Verified against the real fight map: `ResolveCellSurfaces` produces 266 walkable surfaces
out of 299 total resolved surfaces across `map_0_0.amw`'s 324 cells (105 cells have no
surfaces at all — i.e. genuinely off the playable area), and both real
`FightStartCoachPointElement` cells resolve to at least one walkable surface (see
`TestResolveCellSurfaces_RealMapData`).

Go: `internal/gamedata/parser/map_altitude.go`'s `ResolveCellSurfaces()`/`ResolvedSurface`.

### 4.9.7 The `gamedata.Map`/`MapStore` integration layer

`internal/gamedata/map.go` wraps the parser layer into the same lazy/cached shape as every
other `Repository[T]` in this package, but with its own `MapStore` type (rather than a
generic `Repository[T]`) since a map's on-disk shape — a per-`mapID` **directory** of
multiple `.amw` chunk files, sharing one `elements.ade` catalog across every map — doesn't
fit the flat single-file `Repository[T]` pattern:

- `MapStore.Get(mapID)` lazily parses every `maps/<mapID>/*.amw` chunk file the first time
  that `mapID` is requested, merging their `ResolveCellSurfaces()` output into one
  `map[(X,Y)][]ResolvedSurface` lookup, and caches the result (subsequent `Get` calls for the
  same `mapID` return the same `*Map`, no re-parse).
- `Map.IsWalkable(x, y, z)` / `Map.SurfacesAt(x, y)` / `Map.HasCell(x, y)` are the
  query surface used by the combat engine (see §4.9.8).
- `Map.CoachStartCells()`/`Map.FightStartCells()` expose every parsed
  `FightStartCoachPointElement`/`FightStartPointElement` cell, keyed by their raw team-side
  param byte, replacing the need for any hardcoded per-map spawn-cell table.
- `gamedata.Store` (the existing bundle of every game-data repository) gained a `Maps
  *MapStore` field, constructed automatically by `NewStore()` — no separate wiring needed at
  the composition root.

### 4.9.8 What this replaced in the combat engine

Wired into `internal/combat` (docs/08-java-parity-roadmap.md Phase K):

- `Fight.SetMapData(m *gamedata.Map)`: attaches real map data to a fight; `nil` (the
  default, e.g. in tests that construct a `Fight` directly) preserves the old
  always-succeeds behavior below.
- `Fight.IsWalkable`/`Fight.ArrivalAltitude` (`internal/combat/turns.go`): now query the
  attached `*gamedata.Map` for real walkability/nearest-walkable-altitude instead of the old
  permanent `return true`/`return fromZ, false` stubs. Falls back to the old stub behavior
  if no map data is attached (`f.mapData == nil`) — a fight is never blocked from running
  just because map data failed to load.
- `Fight.handleMoveToFreePlacement` (`internal/combat/phases.go`): now also rejects a
  free-placement move onto a non-walkable cell when map data is attached (previously
  occupancy-only).
- `internal/dispatch/handlers_fight.go`'s `resolveCoachStartSpots()`: resolves each coach's
  fight-start teleport cell from the map's real `CoachStartCells()` (picking the nearest
  walkable altitude to the old hardcoded `Z`, via `coachSpotAtAltitude()`) instead of the
  literal hardcoded `(16,11,-3)`/`(1,7,-3)` constants — which remain only as a fallback
  (`fallbackCoachSpotA`/`fallbackCoachSpotB`) for the rare case map data isn't loadable at
  all.
- `instantiateFight()` calls `fight.SetMapData()` right after creating the `combat.Fight`,
  before it starts processing any movement command.

**Update (July 2026 bug-fix pass, `docs/08-java-parity-roadmap.md` §8.13/§8.14):** the
paragraph below, previously claiming the real per-team fighter placement zone data "would
need a distinct piece of per-map data this pass didn't need to identify," was **stale/
incorrect** — that data (`FightStartPointElement`, kind 1000) was in fact already parsed and
exposed as `Map.FightStartCells()` in this very pass, it just was never wired into fighter
placement (`buildCombatTeam` used the coach's own anchor cell + a `Y+i` offset instead). This
was a real, reported bug (fighters visually stacked on the coach's own pedestal during
placement, and permanently unable to move once combat started if that offset landed them in
an unreachable pocket) — now fixed, see §8.14 for the full writeup. The zone-boundary
restriction for `handleMoveToFreePlacement` (mid-placement-phase repositioning, distinct from
initial fighter placement) remains open, tracked below.

**Still not wired** (narrower remaining gap): the real free-placement **zone** restriction
for `handleMoveToFreePlacement` (Recv 8021, mid-placement-phase repositioning) — only
specific cells are legal placement-move targets in the reference client, not just "any
walkable cell." `Map.FightStartCells()` is now known to be exactly this zone data (see
above), so wiring `handleMoveToFreePlacement`'s destination check against it (instead of
just walkability+occupancy) is now a well-understood, low-effort followup rather than a
data-identification problem.
