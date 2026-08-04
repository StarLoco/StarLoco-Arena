# DofusArena 2.70 — Variable-Length Message Layouts

Companion to `PROTOCOL.md`. Detailed field-by-field layouts for the complex
(loop / nested-object) messages that the auto-extractor (`payloads.md`) can only
flag as variable-length. **All fields big-endian.** String prefix widths stated
per field (`str:u8` / `str:u16` / `str:i32`).

Obfuscated class name in parentheses. Direction: S2C = server→client, C2S =
client→server. C2S bodies below are the payload **inside** the 5-byte frame.

---

## Actors (4000 range)

### 4096 ActorSpawn (`xe_2`, S2C) — **zlib-compressed**
Body wrapped by `wa_1` (see PROTOCOL §1 Compression). Decoded buffer:
- `i32 count`, then per actor: `i8 actorType` (1=Coach, 2=Fighter) + nested object.

**Coach `aez_0.b(bb, flags=3179)`** — read in this fixed order:
| field | type | note |
|---|---|---|
| id | i64 | |
| name | str:u8 | |
| x, y | i32, i32 | (flag 0x1) |
| z | i16 | (flag 0x1) |
| orientation | i8 | (flag 0x1) |
| hairColor, skinColor, sex | i8 ×3 | |
| uf | i16 | |
| bMU | i32 | (flag 0x400) |
| dBg | i8/bool | (flag 0x40) |
| guild | str:u16 | (flag 0x20) |
| link | str:u16 | (flag 0x2) |
| pairs | u16 byteLen then (i16,i16)* | (flag 0x200) |
| stats | u8 count then (i8 key,i16 val)* | (flag 0x8) |
| dBi | i32 | (flag 0x800) |

**Fighter `gn_0.b(bb)`:**
| field | type |
|---|---|
| id | i64 |
| breedId | i8 |
| name | str:u8 |
| b2, b3 | i8, i8 |
| hairColor, skinColor, eyeColor | i8 ×3 |
| isSummoned | bool |
| totalXp | i32 |
| equip1 / equip2 / equip3 | str:u16 ×3 |
| spells | i16 count then i32* |
| conditions | i16 count then i16* |
| hpLost, mpLost, apLost | i32 ×3 |

### 4098 ActorDespawn (`th_1`, S2C)
`i32 count`, then `i64 id` × count.

### 4102 ActorAppear (`aEV`, S2C)
`i8 count`, then per elem (19 B): `i64 id`, `i32 x`, `i32 y`, `i16 z`, `i8 orientation`.

### 4104 ActorDisappear (`aya_0`, S2C)
`i16 count`, then `i64 id` × count.

### 4106 ActorReposition (`aqb_0`, S2C)
`i16 count`, then per elem (18 B): `i64 id`, `i32 x`, `i32 y`, `i16 z` (no orientation).

### 4500 ActorMovement (`avf_0`, S2C)
`i64 actorId`, then `(len-8)/10` path steps of `{i32 x, i32 y, i16 z}`.

### 4501 CoachActorMovementRequest (`aLY`, C2S)
`n × {i32 x, i32 y, i16 z}` (no count; derived from frame length).

### 4503 FighterActorMovementRequest (`md_1`, C2S)
`i64 actorId`, then `n × {i32 x, i32 y, i16 z}`.

### 4524 FighterMove (`yr_1`, S2C, ue_0)
`i32 apt`, `i32 apu` (fight header), `i64 actorId`, then `(len-16)/10` steps of `{i32 x, i32 y, i16 z}`.

---

## Generic lists (200 range)

- **200 (`rz_2`, S2C):** `i16 count` + `{i64 id, i16 len, byte[len]}`*.
- **204 (`il_0`, S2C):** `i16 count` + `{i64, i64, i16 len, byte[len], i64}`*.
- **206 (`acc_2`, S2C):** `i16 count` + `i64`*.
- **4001 (`tc_2`, S2C):** `i8 count` + `{i8 key, i32 val}`*.

---

## Chat / social lists (3100 range)

### 3144 FriendList (`aaf_1`, S2C)
`u8 count`, then per friend: `i16 elemLen` + blob parsed as
`{str:u8 name, str:u8 adM, str:u8 adN, bool adO, i64 adP, i16 adQ, i8 adR, i16 adS}`.

### 3146 IgnoreList (`abh_0`, S2C)
`i8 count`, then `str:u8` × count.

---

## Item exchange / card trading (5100 range)

Opcode numbers identical to 2007 (5101–5112) but **card object narrowed** — see
`opcode_map.csv`. Add-card (5105) body in 2.70 = `{i64 exchangeId, i32 cardId,
i16 quantity}` (14 B) vs 2007's `long,long,short` (18 B). Verify each against the
2.70 class body before implementing.

---

## Coach inventory / equipment (5200 range)

### 5200 CoachInventoryUpdate (`air_2`, S2C)
Four sequential loops, each prefixed by an `i16 count`:
1. added: `{i16 slotIndex, i32 referenceCardId}` (6 B)
2. removedShorts: `i16` (2 B)
3. updated: `{i32 referenceCardId, i16 quantity}` (6 B)
4. ints: `i32` (4 B)

### 5201 CoachEquipmentUpdateRequest (`aEl`, C2S)
Fixed 56-byte body: `i32` × up to 14 (no count; unused tail zero-filled).

### 5203 CoachInventoryUpdateRequest (`fh_0`, C2S)
`i16 count` + `i64 uid` × count.

### 5400 (`aOo`, C2S)
`i32 btt`, `i16 count` + `i32`*, `i16 count` + `{i32 refCardId, i16 quantity}`*.

### 5401 (`NN`, S2C)
`bool flag`, `i32 btt`, then while remaining: `{i32 refCardId, i16 quantity}`.

### 5403 (`mj_1`, S2C)
`i8 Lo`, `u8 count` + `{i8 key, i32 val}`*.

### 5450 (`mo_2`, C2S)
`i32 btt`, `i16 count` + `i32`*.

### 5470 (`Zu`, C2S)
`i16 atZ`, `i16 count` + `{i32 refCardId, i16 quantity}`* (+2 trailing zero pad).

### 5490 (`ahg_0`, C2S)
`i32 count` + `i32`* (written in **reverse** order).

---

## Fighter presets / teams (6000 range)

### 6006 FighterInformationList (`jt_2`, S2C)
`i64 leadId`, `i8 count` + `{i64 id, i16 len, byte[len]}`*.

### 6020 SaveTeamPreset (`aic_0`, S2C)
`i8 status` (0 ⇒ body follows), nested `sw_1` preset, then per coach:
`{i64 id, str:u8 name}` (count derived from preset's coach list).

### 6030 TeamPresetList (`ar_0`, S2C)
`i8 count` + `sw_1`*; then `i8 count2` + `{i64 id, str:u8 name}`*.

**Nested `sw_1` (team preset):**
`i16 type(Gp)`, `i16 teamId`, `i16 gameMode`, `str:u8 name`,
if type ∈ {-5,-6,-7}: 4× `i8` appearance,
`i8 fighterCount` + `{i64 fighterId, i64 value}`*,
`i8 coachCount` + `i64`*.

### 6032 (`gd_0`, S2C)
`i8 count` + `{i16 key, i8 val}`*.

---

## Fight lifecycle (8000 range)

Most are fixed-size (see `payloads.md`); all fight S2C messages carry the
`ue_0` 8-byte header (`i32 apt, i32 apu`) first.

### 8300 EndFight (`YP`, S2C, ue_0)
`i32 apt, i32 apu`, `i8 cbE` (branch):
- **cbE==true:** `i16 len` + blob (team-set groups).
- **cbE==false:** full report —
  `i32 n` + `{i64,i32}`* (map bA), `i32 n` + `{i64,i32}`* (map bB),
  `i8 count` + winners, `i8 count` + losers (each `{i64 id, i16 strength, i16
  reportLen + opaque PlayerStatisticsReport blob}`),
  `i16 len`+blob, `i16 len`+blob,
  `i8 count` + `{i64 id, i16 len, byte[len]}`*, `i8 cbI`, `i8 cbH`, `i32 cbG`.

---

## Matchmaking / opponent search (2300 / 23000 range)

### 2301 OpponentSearchRequest (`agp_1`, C2S, flag=2)
`i16 fA`, `i16 fO`, `i32 count` + `i64`* (reverse order).

### 2308 (`Pg`, C2S, flag=2)
`i64 anl`, `i16 aKn`, `i32 count` + `i64`* (reverse), `bool jy`.

### 2307 (`bx_1`, S2C)
`i64 anl`, `str:i32 name1`, `str:i32 name2`, `i16 aKn`, `i32 count` + `i64`*.

### 2411 (`HJ`, S2C)
`i32 count`, then per elem (18 B): `i16, i16, i16, i32, i32, i32`.

### 23110 (`tb_2`, S2C) — match confirmation request
`i64 id`, `str:i32 name1`, `str:i32 name2`, `i64 Ho`, `i16 Gm`, `i16 fA`,
`i32 n` + `i64`* (participants).

### 23114 (`acz_2`, C2S, flag=2) — search response
`i64 id`, `i64 Ho`, `i16 Gm`, `i16 fA`, `i32 n` + `i64`* (reverse), `i8 accepted`.

### 23116 (`aex_0`, C2S, flag=2) — cancel
`i32 n` + `i64`* (reverse).

---

## Calendar / events (17000 range, new in 2.70)

### 17003 (`awa_0`, S2C) — event list
`u16 count`, then per event: `i32 type` + nested `iz_0` subclass.
Base `iz_0`: `i64 id, i64 startDate, i64 endDate, i64 duration, i32 refId`.
- **type 2** (`mb_2`): base only.
- **type 4** (`qr_0`, tournament event): base + `i64 linkedDate, i64 tournamentId,
  str:u8 name, str:u16 desc, str:u8 illustration, u8 nPhase × {i64,i64},
  u8 nRegPeriods × {i64,i64}`.

### 17008 (`ald_2`, C2S) / 17010 (`aFu`, C2S)
Admin add/create events (see `decompiled/core/ald_2.java`, `aFu.java`).

---

## Achievements (22000 range, new in 2.70)

### 22002 (`ls_0`, S2C)
`i32 byteLen`, then while `idx*4 < byteLen`: `{i16 key, i16 value}` (achievement→state).

---

## Fight invitations — X vs X (26300 range, new in 2.70)

### 26300 (`wu_2`, S2C) — incoming invitation
`i64 senderId, i8 isOutgoing, i8 isEvolution, i8 nNames` + `str:i32 name`*.

### 26313 (`aju_1`, C2S, flag=2) — invite player + allies
`i8 kind(=14), i64 invitedId, i8 nAllies` + `i64`*.

---

## Ladder / ranking (27500 range, new in 2.70)

Paginated pages; header pattern `i32 total, i32 startIdx, i32 endIdx, i32
selfRank`, then `(end-start)` rows, then `i8/i32 searchButtonVisible`.

| Opcode | Class | Page |
|---|---|---|
| 27501 | `azd_0` | 1v1 ladder — row: `str:i32 coach, str:i32 guild, i16, 5×i16` |
| 27503 | `ij_1` | guild list — `i16 fA, i32 cursor, i32 n` + `{str:i32, str:i32, i16}` |
| 27505 | `aka_0` | 2v2 best-team — header + `i32 m`+`i32`* members, rows of 3×str:i32, i16, 2×i32, 4×i16 |
| 27507 | `uj_0` | tournament ladder (month/trimester/year blocks) |
| 27509 | `jw_0` | reputation ladder |
| 27511 | `anc_0` | guild/demon ladder — `{str:i32 name, i64, i64, i64}` + trailing i64 |
| 27513 | `xn_2` | guild-demon list — `{i16, i64, str:i32}` |
| 27515 | `amu_0` | glicko-rating ladder — `{str:i32, str:i32, i16}` |

---

## Tournaments (28600 range, new in 2.70)

### 28602 (`ng_2`, S2C) — tournament list
`i32 n`, per row: `i64 id, bool, i8, i16, bool, i32 k`+`i32`*,
`str:i32 name, str:i32 desc, str:i32 organizer, i8`.

### 28620 (`Yq`, S2C) — finale announcement / cancel
`i8 status`; if active: `i64 id, i32 m`+`i64`* fighters, `i32 k`+`str:i32`* names,
`str:i32 label`; if cancel: `i64 id`.

### 28622 (`uw_2`, S2C) — team / registration data
`i32 n`, per row: `str:i32 name, i8 type, i32 a`+`i64`*, `i32 b`+`str:i32`*,
`i32 c`+`str:i32`*, `i64`.

### 28650 (`IL`, S2C) — bracket / tree
`i32 treeSize, i32 n` + `{i32 id, str:i32 name}`*, `i32 bib`.
