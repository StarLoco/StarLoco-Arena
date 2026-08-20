# DofusArena 2.70 — Part-Table & Entity Blob Formats

Closes the last protocol gap: the self-describing "part-table" blobs and entity
serializers referenced (as opaque byte[]) by messages 200, 204, 512/552/554,
6006, 8121, and the ActorSpawn coach reader. All **big-endian**.

Companion to `PROTOCOL.md` / `PROTOCOL-messages.md`.

---

## 1. The part-table container (`aJj.ad`)

Several entities serialize themselves as a **part table**: an index of numbered
parts, each an independent sub-record.

```
[u8 partCount]
partCount × { u8 partId, i32 offset }          // index table
per part: payload bytes = buffer[offset+1 .. nextOffset-1]
          (skip 1 byte at `offset`; span up to the next part's offset, or limit-1
           for the last part)
          decode with entity.Kl()[partId].f(ByteBuffer)
```

- `partId` indexes into **that entity's own ordered part list** `Kl()` — the same
  numeric id means different things for different entities.
- An entity's part slot may be `EMPTY` (sentinel `aea_0.dBr`) — such parts are
  skipped.
- **Strings on this path** are `[u8 len][UTF-8]` — **except** the interactive-
  element name (§3) which uses `[u16 len][UTF-8]`.

---

## 2. Guild rank `ca_0` — messages 512 / 552 / 554

Message wrapper (`kf_1`): `[i32 count]` then `count × { i32 blobLen, byte[blobLen]
→ ca_0.ad }`.

`ca_0.Kl()` = `[0]=uy_2, [1]=ut_2, [2]=uu_2` — three variants; server sends the
one matching context.

**part 0 `uy_2` (connected member):**
`i64 memberId, i32 privileges, i16 rank, str:u8 rankName, str:u8 memberName,
bool connected`.

**part 1 `ut_2` (another player's clan tag, opcode 554):**
`str:u8 guildName, i64 playerId, i16, i16, i32, i32, i16 demonId`.

> The `i64` was previously documented as a guildId. It is the **player** id:
> `ut_2.f` calls `ca_0.a(ca_0, long)`, which sets `Pl` = `Ke()`, and the handler
> `lh_1.java:153` reads it back with `ca_07.Ke()` to find the actor to tag.

**part 2 `uu_2` (the local coach's OWN membership — opcode 552, and the 0x20
blob of CoachInformations 2052 / ActorSpawn 4096):**
`i64 guildId, i32 rights, i16 rankLevel, str:u8 rankName, i16, str:u8 guildName,
i16, i32, i32, i16 demonId`.

> The leading `i64` was previously documented as a memberId. It is the **guild**
> id: `uu_2.f` calls `ca_0.c(ca_0, long)`, which sets `bZ` = `Kd()` — and that is
> the id the client echoes back in `/c` (3199) and in every guild operation, so
> sending a member id here would make every subsequent request address the wrong
> guild.

**Strings on the guild family are UTF-8**, not the cp1252 the coach/fighter names
use: they go through `aey_0.hH`/`aey_0.V`, which name the charset outright
(`getBytes("UTF-8")`). The difference is invisible until a clan or rank name
carries an accent.

---

## 3. Interactive element `do_1` — message 200 (`rz_2`), also 204

Message wrapper (`rz_2`, opcode 200): `[i16 count]` then
`count × { i64 elementId, u16 dataLen, byte[dataLen] → do_1.ad }`.
(Opcode 204 `il_0`: `[i16 count]{ i64 keyId, i64, u16 len, blob→do_1.ad, i64 }`.)

`do_1.Kl()` = `[0]=amp_2, [1]=RU, [2]=rv_0, [3]=EMPTY, [4]=EMPTY]` (all concrete
element subclasses share these).

**part 0 `amp_2` (actions):**
`u8 count` (payload must be `count*6` bytes) then `count × { i16 actionId, i32
value }`.

**part 1 `RU` (spawn / position / name):**
`i16, i32 x, i32 y, i16 altitude, i16, bool visible, bool, u8 direction,
i16 flags, i16 pathCount, pathCount × { i32 x, i32 y, i16 altitude },
str:u16 name  ⚠(u16 length prefix — unique on this path), u8 propertyCount(=0)`.

**part 2 `rv_0` (synced state):** `i16, bool visible`.

---

## 4. FighterInformation `et_2` — message 6006 (`jt_2`)

`et_2` is **not** a part-table — it has a hand-rolled decoder. Message wrapper
(`jt_2`): `i64 id, u8 count, count × { i64 fighterId, u16 blobLen, byte[blobLen]
→ et_2.b }`.

`et_2.b(byte[])`:
```
u8   type            1 = info, 2 = full combat
i16  budget
u8   breedId
i32  (only if breedId is the "special" breed)
u8   nameLen; name bytes    ⚠ platform-default charset (not explicit UTF-8)
u8   sex
i8   ey                     color selector; if ey < 0, read 3 more u8 colors
i16  aRm_len; aRm bytes     appearance/spells sub-blob (see below)
i16  aRn_len; aRn bytes     cards/persistent sub-blob (see below)
-- if type == 2 (combat block):
i32, i32, i32, u8, u8, u8, i16, i16
i16 spellCount + i32 × spellCount
u8  itemCount  + { i16, u8 } × itemCount
i16 count + i32 × count
i16 count + i32 × count
```
**Sub-blobs (flat, length = blob size):**
- `aRm` — a flat list of `i32` until exhausted (appearance/spell ids).
- `aRn` — a flat list of `{ i16, i32 }` pairs until exhausted (6 bytes each,
  card/persistent state).

---

## 5. Coach `aez_0` — ActorSpawn (opcode 4096) coach reader

Not a part-table — a **bitmask** reader `b(bb, flags)`. Sections read in this
fixed order (present only if the flag bit is set):
```
always      : i64 id, str:u8 name  ⚠(platform-default charset)
flag 0x1    : i32 x, i32 y, i16 altitude, u8 direction
always      : u8 hair, u8 skin, u8 sex, i16
flag 0x100  : i32, then i16 len + { u8, i32 } pairs
flag 0x400  : i32 rank
flag 0x40   : bool
flag 0x20   : i16 len + guild-rank blob → ca_0.ad   (see §2)
flag 0x80   : i16 len + i32 ids
flag 0x2    : i16 len + opaque spell-inventory blob (dAS.k)
flag 0x4    : i16 len + opaque blob (dAS.l)
flag 0x200  : i16 len, then (len/4) × { i16, i16 } stats
flag 0x8    : u8 count + { u8, i16 } × count
flag 0x800  : i32
```
(The `ActorSpawn` flags value observed is `3179 = 0xC6B`.)

---

## 6. Combat `rq_2` — opcode 8121

Not a part-table:
```
i32  id
i16  len + Nw blob      (opaque — interpreted by downstream combat handler via aew())
akv_0: i64 timerId, i16 durationTurns (<0 = infinite), bool
```

---

## 7. Remaining opaque sub-blobs

A few nested blobs are stored raw and interpreted by dedicated subsystems, not
by the message/entity that carries them:
- `et_2.aRm` / `aRn` — resolved above (flat lists).
- `rq_2.Nw` — combat effect blob; parsed downstream (consumer of `aew()`).
- `aez_0` flag-0x2 / flag-0x4 blobs (`dAS.k` / `dAS.l`) — coach spell inventory /
  linkage, parsed by `ky_2`.

These are non-critical for a first playable server (appearance/inventory detail);
decode them when implementing full spectator/appearance fidelity.

---

## Charset warning

Most strings here are UTF-8 (`aey_0.V`), **except** `et_2` and `aez_0.V` names,
which use the platform-default charset (`new String(bytes)`). Emit ASCII-safe
names to avoid mismatches, or match the client JVM's default charset.
