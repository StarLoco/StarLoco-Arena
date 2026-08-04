# DofusArena 2.70 — Network Protocol Specification

Reference for building a **Go server v2.70** that is wire-compatible with the
retail 2.70 client (`build.version=2.70`, `revision=72909`, Feb 2012).

Everything here was reverse-engineered from the obfuscated client
(`compiled/game/core.jar`, decompiled to `decompiled/core/`), cross-referenced
against the fully-named 2007 client source
(`client/source/` on the **`v2.04`** branch - the 2.04b line). Opcode numbers are stable across
versions; **payload layouts are not** — this doc reflects the **2.70** layouts.

Companion files:
- `opcode_map.csv` / `opcodes.md` — full opcode ⇄ obfuscated class ⇄ 2007 name table.
- `payloads.md` / `payloads.csv` — auto-extracted field layouts (fixed-size msgs).
- `rename_map.csv` — CFR name ⇄ original obfuscated class name.

---

## 1. Transport & framing

- **One TCP socket** from the client to a **Proxy** (`host:port` from
  `config.properties` keys `proxyGroup` / `proxyAddresses`). The proxy fronts
  three logical back-ends, selected per-message by a **1-byte architecture
  target**:

  | target | back-end | example |
  |:---:|---|---|
  | `0` | basics / connection | ClientVersion (7) |
  | `1` | **auth** (RSA secure) | ClientAuthentication (1025) |
  | `2` | **world** | CoachCreation (2049) |

  (An **instance** server also lives behind the proxy for fights.)

- **Endianness: BIG-ENDIAN** (network byte order) for the entire message path.
  Messages decode via plain `java.nio.ByteBuffer.wrap()` (no `.order()` call).
  *(The little-endian `acf` reader in the client is for asset/map files only —
  not the wire.)*

- **Client → Server frame** (built by `acb_2.addClientHeader`, 5-byte header):
  ```
  [u16 totalLen = 5 + payloadLen][u8 archTarget][u16 opcode][payload...]
  ```
  `archTarget` (a.k.a. flag byte) is **3** for most messages, **2** for
  matchmaking (opcode 2301/2308/23114/23116/26313), **0** for ClientVersion,
  **1** for ClientAuthentication.

- **Server → Client frame** (parsed by `fp_0.g`, 4-byte header — **no arch
  byte** inbound):
  ```
  [u16 totalLen][u16 opcode][payload...]     // payload = totalLen - 4
  ```
  The dispatcher hands the message class **only the payload** (header stripped).

- **Auth channel framing** (server-side view, `qc_2.g`, only for archTarget=1):
  ```
  [u16 len][i32 opcode][u8 secure][payload]  // if secure==1: RSA-decrypt payload
  ```

### Primitive types on the wire (all big-endian)

| Notation | Java read | Bytes |
|---|---|---|
| `i8` / `u8` | `get()` / `get()&0xFF` | 1 |
| `i16` / `u16` | `getShort()` / `getShort()&0xFFFF` | 2 |
| `i32` | `getInt()` | 4 |
| `i64` | `getLong()` | 8 |
| `f32` / `f64` | `getFloat()` / `getDouble()` | 4 / 8 |
| `bool` | `get() == 1` | 1 |
| `str:u8` | `[u8 len]` + `len` UTF-8 bytes (client helper `m(bb)`) | 1+len |
| `str:u16` | `[u16 len]` + `len` UTF-8 bytes | 2+len |
| `str:i32` | `[i32 len]` + `len` bytes (`new String(...)`) | 4+len |

> **String prefix width is not uniform** — it is chosen per call site. Each
> message table below states which width it uses.

### Length assertion

`pr_0.a(actualLen, expected, exact)`: if `exact==true` the payload length must
equal `expected`; if `false`, it must be `>= expected`. Servers should emit
exactly the byte count the client asserts.

### Compression

Only **ActorSpawnMessage (4096)** is compressed. Its body is wrapped by `wa_1`:
an `i32` prefix — **positive** = original inflated length followed by zlib data;
**negative** = `-len` followed by that many raw (stored) bytes. Decompress before
parsing the actor list below.

---

## 2. Connection & login handshake

Client sends steps 1 and 2 **back-to-back without waiting**.

| # | Dir | Opcode | Target | Message | Crypto | Payload |
|---|:---:|:---:|:---:|---|:---:|---|
| 0 | — | — | — | TCP connect to proxy | — | — |
| 1 | C→S | 7 | 0 | ClientVersion (`na`) | plain | `u8 0x02`, `u16 ver=70`, `str:u8 buildVersion` |
| 2 | C→S | 1025 | 1 | ClientAuthentication (`bu_1`) | **RSA** | inner: `str:u8 login`, `str:u8 password` |
| 3a | S→C | 8 | — | InvalidClientVersion | plain | required-version bytes (only if version rejected) |
| 3b | S→C | 1024 | — | ClientAuthenticationResults | plain | `u8 resultCode` |
| 3c | S→C | 1026 | — | WorldServerUnavailable | plain | empty (world not ready → client disconnects) |
| 4 | — | — | — | client enters game protocol | — | on 1024 result 0; **no wire/socket change** |
| 5 | S→C | 2048 | — | CoachCreationRequest | plain | (server asks new account to create a coach) |
| 6 | C→S | 2049 | 2 | CoachCreation | plain | `str:u8 name`, `u8 skinColor`, `u8 hairColor`, `u8 sex` |
| 7 | S→C | 2050 / 2052 | — | CoachCreationResult / CoachInformations | plain | confirm coach → in-world |

**Auth result codes (1024):** `0`=success, `2`=invalidLogin,
`3`=alreadyConnected, `4`=saveInProgress, `127`=closedBeta.

**Encryption:** RSA (`RSA/ECB/PKCS1Padding`, JCE default). Keys come from a Java
KeyStore. Client holds the **public** cert (encrypts); **server must hold the
matching private key** (decrypts the 1025 payload). No session-key negotiation —
static asymmetric keypair. Only archTarget==1 messages are encrypted.

**Reconnection tickets** (opcodes 2/3/4) exist for session hand-off but are
**not** part of first login.

---

## 3. Opcode ranges (feature map)

| Range | Feature | Notes |
|---|---|---|
| 1–20 | Login / version / properties (auth channel) | RSA on 1025 |
| 100–560 | Session / world basics, generic lists (200/204/206) | |
| 1024–1026 | Auth results / world availability | |
| 2048–2070 | Coach creation & info | world server |
| 2260–2411 | Opponent search, player statistics | 2301 flag=2 |
| 2600–2601 | (misc) | |
| 3128–3216 | **Chat / social** (channels, friends, ignores, vicinity, private) | stable since 2007 |
| 4000–4900 | **Actors** (spawn/appear/move/reposition) + fight actor requests | 4096 compressed |
| 5000–5116 | Instance availability, **item exchange / card trading** (5101–5116) | card object changed vs 2007 |
| 5200–5491 | **Coach inventory / equipment** | |
| 6000–6032 | **Fighter presets / team management** | |
| 8000–8400 | **Fight lifecycle** (presentation, placement, turns, spells, end) | ue_0-based, 8-byte header |
| 15000–15507 | (misc game state) | |
| 16384+, 19000+, 30000+ | **UI-internal messages** (NOT wire opcodes) | `sb_0`/`aed_2` bus |
| 17000–17010 | **Calendar / events** (incl. tournament calendar) | new in 2.70 |
| 22000–22099 | **Achievements / trophies** | new in 2.70 |
| 23000–23116 | **Opponent search / match confirmation**, guild pages | new in 2.70 |
| 26300–26334 | **Fight invitations (X vs X)** | new in 2.70; 26313 flag=2 |
| 27500–27552 | **Ladder / ranking** (1v1, 2v2, reputation, glicko, guild-demon, tournament) | new in 2.70 |
| 28601–28650 | **Tournaments** (list, brackets, finales, registration) | new in 2.70 |

Even opcodes in the ladder/tournament ranges are the paginated **C2S requests**
(cursor + category); odd/following are the **S2C** pages.

---

## 4. Message base classes (obfuscated)

| Obf | Role |
|---|---|
| `pr_0` | abstract root: `encode()`, `a(byte[])` decode, `getId()`=opcode |
| `acb_2` | adds the 5-byte C2S wire header (`= ClientProxyMessage`) |
| `so_0` | client→server (has `encode()`; `a()` throws) |
| `ael_2` | server→client (has `a()`; `encode()` throws) |
| `ue_0` | fight/combat S2C base — payload begins with `apt:i32, apu:i32` (8-byte header) |
| `axX` | secure/login C2S (int opcode) |
| `aed_2` / `sb_0` | **UI message bus — not wire opcodes** |

---

## 5. Detailed payload layouts

- Fixed-size messages: `payloads.md` (auto-generated exact field/size tables).
- Complex variable-length messages: `PROTOCOL-messages.md`.
- Part-table / entity blobs (actors, guild rank, fighter info, coach, combat):
  `PROTOCOL-parttable.md`.
- Game data files (cards/spells/effects/…): `DATA-FORMAT.md`.

Highlights every server must get right:

- **Card object on the wire is minimal:** the reference-card reader
  (`wy_2.b`) reads only `i32 referenceCardId`. Quantity (`i16`) and local uid
  (`i64`) are separate explicit fields where present. **No templateId/flags block
  on the wire** (looked up client-side) — this differs from the 2007
  `CoachCard.unserialize`.
- **Count widths vary** per message (i8 / i16 / i32) — do not assume.
- **Some C2S encoders write arrays in reverse order** (5490, 2301, 2308, 23114,
  23116) and some over-allocate with trailing zero padding (5201, 5470).
- **Movement messages carry no explicit count** — the step count is derived from
  the frame length (`(len - headerBytes) / 10`).

---

## 6. Game data format (⚠ CHANGED vs 2007)

The v2.70 server must load the same static game data the client uses (cards,
spells, effects, summons, events, breeds). **The 2.70 data format is
rearchitected — the 2007/`DofusArena2-06` `.dat` parsers will NOT work.**

- **Runtime store:** the client does **not** read `cards.dat`/`spells.dat` at
  runtime. It reads one merged, **compressed, indexed** store:
  - `data.bdat` — concatenated record blobs
  - `indexes.bdat` — index: `(type, indexName, key) → offset`
  - Loaded by `aly_1` (`extends akf_1`), constructed
    `super("data.bdat","indexes.bdat", true)` where `true` = a compression
    filter (`mt_0` = `ym_0(new ah_0())`). To read `data.bdat` directly you must
    apply that decompression filter first.
  - The per-type `.dat` files inside `data.jar` are **legacy import/export
    artifacts** (2007-style flat framing + one extra i32 id/version tag per
    record). Usable as a fallback but not what the client consumes.

- **Endianness: BIG-ENDIAN** for all data records (plain `ByteBuffer`, default).
  The little-endian `acf` reader is for maps/graphics, **not** game data.

- **Index entry (`le_2`, big-endian via DataInputStream):**
  `i32 type`, `UTF indexName`, `UTF indexValue`, `i64 position`
  (`UTF` = Java modified-UTF: `u16 len` + bytes).

- **Record block (`azf`) in `data.bdat`:**
  `i32 id`, `i16 version`, `i32 dataLen`, `byte[dataLen] payload`
  — 10-byte header, then `payload` decoded by the type's record class
  `a(ByteBuffer, id, version)`. **Every record now carries an `i16 version`**
  (validated `== 1`); 2007 had no per-record version.

- **Effects are embedded per record** in 2.70 (each spell/card carries its own
  effect list), vs 2007's single global trailing effects section.

- **Record classes:** `aPp` = CoachCard (**26 fields** vs 2007's 4 — adds a
  `byte→i32` map, `float[]`, criteria objects `akw_0[]`, bonus objects `np_1[]`,
  three `i16`, etc.), `uh_0` = FighterCard, `co_1` = Spell (**23 fields** —
  gained booleans, a `long[]`, a trailing `i32`, and an embedded effect list).
  Loaders: `eh_2` (cards), `apS` (spells). Strings = `i32 len` + UTF-8.

**Action for the server:** write a new 2.70 data parser (decompress `data.bdat`
→ read `azf` blocks → decode per-type records big-endian). Budget real time for
this — it is the largest non-protocol task.

---

## 7. Coverage & known gaps (read before implementing)

Solid / fully decoded:
- Framing, RSA login handshake, all ~59 variable-length messages.
- **PlayerStatisticsReport** (opcodes 2400/2401/8300): typed field-map `rs_2` =
  `[u16 modelId][u64 entityId][u16 fieldCount]` then `fieldCount × {u16 fieldId,
  u8 typeCode, value}` where typeCode INT=i32, LONG=i64, **FLOAT=f32**.
- Item-exchange card object: `[u32 cardId]` + caller `[u16 quantity]`.
- ~190 of the ~207 new-in-2.70 opcodes are simple flat structs.

Special encodings a naive parser breaks on:
- **PropertyItem (opcode 11)** uses a type-discriminated value:
  `1=byte,2=short,3=int,4=long,5=double,6=float,7=str`. Only wire message with
  `double`. Branch on the type byte.
- Statistics fields (above) include **float32**.

**Entity "part-table" blobs — NOW DECODED** (`PROTOCOL-parttable.md`):
the self-describing part-table container (`aJj.ad`: `[u8 partCount]{u8 partId,
i32 offset}`) and all leaf schemas are documented — guild rank (200/512/552/554),
interactive elements (200/204), FighterInformation (6006), coach ActorSpawn
(4096), combat (8121). Opcodes 512/552/554 are now in `opcode_map.csv`.

Residual minor opaque blobs (non-critical, appearance/inventory detail only):
`rq_2.Nw` combat blob, `aez_0` coach spell-inventory/linkage sub-blobs
(`dAS.k`/`dAS.l`). Decode when implementing full spectator/appearance fidelity.

**Game data — NOW DECODED** (`DATA-FORMAT.md`): the `data.bdat`/`indexes.bdat`
store (zlib per-record, big-endian), all record schemas (cards, spells, effects,
static effects, events, summons) and the type-id registry.

Dead / ignorable:
- **3164 / 3166** (ignore-online/offline notifications) have no client decoder —
  the client can't receive them in this build; safe to skip.
