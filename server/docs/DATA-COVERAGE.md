# Client data coverage — state of the art

> Picking the project up cold? Start at [`STATUS.md`](./STATUS.md).

**The rule: the client's data files are the single source of truth.** The 2.70 server
must derive every game value by *reading the client's own files*, never by hardcoding a
number or by porting one from v2.04b. When the client's data is updated, the server must
pick the change up with no code change. This document records exactly how far we are
from that, so the gap is visible instead of implicit.

Last audited: 2026-08-03, against the shipped
`compiled/game/contents/bdata/{data.bdat,indexes.bdat}` (19 781 records, 24 types) and
`compiled/game/contents/maps/`.

**Why this matters (measured, not hypothetical).** Every value we ever hardcoded or
inherited from v2.04b has eventually turned out to be wrong in 2.70: all 12 breed
initiatives, the breed base value (400 vs 600), Cra's close-combat element, base
crit/fumble (5/1 vs 0/0), and the spell cooldown field — which left **97 of 203 spells,
28 of them once-per-fight, with no cooldown at all**. See BUGS.md B-055…B-059.

---

## 1. Where the data lives

| Source | What | Read by |
|---|---|---|
| `bdata/indexes.bdat` | `[i32 type][utf name][utf value][i64 pos]` per record | `gamedata.Open` |
| `bdata/data.bdat` | one zlib stream per record: `[i32 id][i16 ver][i32 len][payload]` | `gamedata.Store.ReadRecord` |
| `maps/fight/*.jar` | `.fmd` start points + special cells (47 arenas) | `gamedata.LoadFightMaps` |
| `maps/tplg/*.jar` | per-map topology tiles | `gamedata.LoadFightMaps` |
| `maps/env/*.jar` | interactive elements | **not read** — element layouts are hand-authored in `game/elements.go` |
| `i18n/texts_*.properties` | all display strings, keyed `content.<table>.<id>` | **not read** — the server sends ids and the client resolves them (correct: strings are client-side) |

Record types are the `atr_0` enum. A record class is the `lJ` subclass whose `cq()`
returns that id; a loader is the `hR` implementation that turns records into runtime
objects.

---

## 2. Record-type coverage

**8 of 24 populated types are decoded.** Legend: ✅ decoded · ⚠️ partially used · ❌ not read.

| Type | Records | Client record → runtime | What it is | Status | Our decoder |
|---|---:|---|---|---|---|
| 1 | 1 | `aet_1` → — | Standard fight parameters (singleton) | ❌ | — |
| **100** | 907 | `aPp` → `xj` | **Coach cards** | ✅ 18/26 | `cards.go` |
| **101** | 138 | `yp_0` → `fe_1` | **Card sets ("panoplies")** | ✅ 2/2 | `cardsets.go` |
| **210** | 16 | `rf_2` → `er_1` | **Traps / special zones** | ✅ partial | `effectareas.go` |
| **220** | 203 | `co_1` → `yp_2` | **Spells** | ✅ 23/23 | `spells.go` |
| **230** | 51 | `ama_1` → `tO` | **Per-round event cards** | ✅ full | `events.go` |
| **250** | 75 | `uh_0` → `ve_0` | **Fighter equipment / weapons** | ✅ good | `fightercards.go` |
| 251 | 11 | `alf_2` → — | Equipment pools granted by Sphere Board nodes | ❌ | — |
| **300** | 53 | `jz_2` → `aJt` | **Summons** | ✅ 17/17 | `summonings.go` |
| 360 | 42 | `rb_0` → `yn_2` | Interactive-element rendering (gfx/colour/height) | ❌ | — |
| **400** | 39 | `GE` → `afz_0` | **PvE challenges** | ✅ partial | `challenges.go` |
| 700 | 7 | `fw_2` → `iz_0`* | Calendar events (7 subtypes) | ❌ | hand-built in `tournaments.go` |
| 800 | 332 | `ru_1` → `aau_1` | Achievements (+ thresholds, required cards) | ❌ | — |
| 801 | 5 | `fw_0` → `ajk_1` | Achievement categories | ❌ | — |
| 802 | 13 | `wr_0` → `li_2` | Achievement subcategories | ❌ | — |
| 900 | 15 | `bg_0` → `Ei` | Sphere Board headers (per breed/season) | ❌ | — |
| 901 | **17 527** | `aeI` → `ayr_0` | Sphere Board nodes (xp cost, spell, cards) | ❌ | — |
| 902 | 111 | `ahm_1` → `aiz_2` | **Fighter conditions** — the persistent wound/blessing layer | ❌ | — |
| 1000 | 22 | `aub` → — | Tournament definitions (rules, rewards, prizes) | ❌ | hand-built in `tournaments.go` |
| 1001 | 4 | `ek_2` → — | Tournament level list | ❌ | — |
| 1100 | 30 | `ajd_0` → `abe_1` | Fusion-laboratory definitions | ❌ | hand-built |
| 1400 | 2 | `cb_2` → `atk_0` | Pro League definitions | ❌ | served empty |
| 1500 | 148 | `atF` → `ana_2` | NPC dialog replies | ❌ | — |
| 1600 | 29 | `mw_0` → — | Per-map metadata (music/background refs) | ❌ | — |

Declared in `atr_0` but **absent from this store** (9): 110, 231, 232, 500, 600, 1101,
1102, 1200, 1300. Type **200** (`Ht`, the effect row) is never a standalone record — it is
embedded in 100/210/220/230/250/902 and is decoded by `effects.go`.

### Type 101 — Card sets (`yp_0`) — 2 of 2 fields ✅ complete
`[i32 setId][u8 n + akw_0 effects]`. Membership runs the other way, from each coach
card's `CardSet` field. Each effect carries its own **threshold** (`akw_0.aAm()`, the
entry's trailing byte): it applies once the coach has that many of the set's cards
equipped. 138 sets, 88 effects, thresholds 2..10.

The effects are `AI`-enum coach META bonuses. Only **resurrection** (10 effects) has a
consumer in this server today and is wired up; XP (28), fatigue (9), morale (9),
reputation (6), death chance (5) and wound-cancellation (4) are decoded and **inert** —
each is blocked on its own unimplemented mechanic, not on the data.

### Biggest gaps, by impact
1. **902 fighter conditions (111 records)** — `[i16 id][i8 grade][i16 type][u8 n + gfx
   refs][effects]`. **Correction:** an earlier revision of this document called these "the
   authoritative definitions of every in-fight state, the real source for
   `game/states.go`". That was wrong. They are a *separate, persistent* layer: conditions
   are applied by **coach cards** (effect `AI.aHK` = *"Applique une condition"*, resolved
   by `vm_2`), surfaced as the fighter's `conditions` field, and named by `content.40.*`
   — 116 names such as *Blessure légère jambe*, *Ange gardien*, *Champion*, *Fatigué*.
   They are the **wound / blessing / morale layer** carried between fights, not the mh_2
   in-fight state actions that `states.go` maps. Conditions of the same `type` are
   mutually exclusive (`vm_2` replaces same-type, except types 21 and 70). We model
   none of this: fighters have no conditions at all, so wounds never accrue.
2. **901/900 Sphere Board (17 542 records)** — an entire unimplemented progression system.
3. **1000 tournaments (22)** — we serve three hand-built tournaments; the real rules,
   rewards and prize tables are here.
4. ~~**101 card sets (138)**~~ — done (B-062); the bonuses are meta, not combat.
5. **800/801/802 achievements (350)** — an unimplemented subsystem.

---

## 3. Field coverage for the types we DO decode

Field numbers are the record's binary order. "consumer" = who reads it in the client.

### Type 220 — Spells (`co_1`) — 19 of 23 fields
| # | field | our name | status |
|---|---|---|---|
| 1,2,3 | id, breedId, value | `ID/BreedID/Value` | ✅ |
| 4 | target id | — | ⬜ dead in client too (never read for logic) |
| 5 | animation script id | `ScriptID` | ✅ (client-render only) |
| 6 | AP cost | `AP` | ✅ |
| 7 | per-target cap | `CastMaxPerTarget` | ✅ |
| 8 | **max live instances** | `MaxActive` | ✅ decoded, ❌ **not enforced** |
| 9 | per-turn cap | `CastMaxPerTurn` | ✅ |
| 10 | **cooldown (63 = once/fight)** | `Cooldown` | ✅ **fixed — was read from field 8** |
| 11 | deferred unlock delay | `CooldownUnlockDelay` | ✅ decoded, not needed server-side |
| 12,13 | range bounds | `RangeMin/Max` | ✅ (stored max-then-min; normalised) |
| 14,15,16 | LoS / only-line / free-cell | `TestLoS/OnlyLine/NeedFreeCell` | ✅ |
| 17 | description toggle | — | ⬜ client display only |
| 18 | **range not boostable** | `RangeNotBoostable` | ✅ **newly decoded + enforced** (5 spells) |
| 19 | run target-validity check | — | ⬜ we always apply target conditions |
| 19 | enforce target masks | `EnforceTargetMasks` | ✅ decoded (true on only 3 spells) |
| 20 | criterion tokens | `Criterion` | ✅ |
| 21 | effects | `Effects` | ✅ |
| 22 | spell-level target masks | `TargetMasks` | ✅ decoded (202/203 spells), ❌ not yet evaluated |
| 23 | parent spell id | `ParentID` | ✅ **decoded + enforced** — `LimitKeyID()` shares all cast limits with the parent (5 spells) |

Type 220 is now **23 of 23 fields decoded**. Two are decoded but not yet acted on:
`TargetMasks` needs the client's fuller `aLc` evaluator (it extends the per-effect
condition bits with state-based ones — bit 49 intransposable, 50 stabilised, 51
cannot-be-carried, 56 rooted, 57 petrified), and it is only *enforced* by the client on 3
spells, so the payoff is small; `MaxActive` needs a live-instance counter.

### Type 250 — Fighter equipment (`uh_0`) — 15 of 15 fields
All decoded. Flags 9–13 were unidentified until this audit and are now enforced:
only-line, line-of-sight, free-cell, usable-while-dead, usable-while-carried. Field 7 is
a client animation script id (**not** a crit rate, an earlier hypothesis — now dead).

### Type 230 — Event cards (`ama_1`) — 4 of 4 fields ✅ complete
Field 2 is dead in the client too (no callers).

### Type 100 — Coach cards (`aPp`) — 18 of 26 fields
Was 8 of 26 (and `Rank` was permanently 0). Now decoded in one pass through field 18:
id, type, set, value, price map, required level, firework type + colour, `isUnique`,
obtainable-in-draw + drop %, **bound**, **undestructible**, has-usable-action, the effect
array (incl. the resurrection scan) and rank. The two tradability flags are now enforced
in exchanges (B-061).

**Fields 19-26 are now read too (B-071),** so this record is **26 of 26** and every one
of the 907 shipped records is consumed to **zero residual bytes** — the strongest check
available that the layout is right. They are: the `np_1[]` gameplay parameters (19), two
i16 the client hands to its runtime card object and never reads again (20-21), the
fusion laboratory's `labPower` and `quality` (22-23), the pet model id (24, which
`aez_0.aQv()` uses to spawn one visual per owned pet), and a colouring card's slot and
palette index (25-26, named by the unobfuscated `setFighterColorIndex`: slot 0 hair /
1 skin / 2 eyes).

Cross-check worth keeping: exactly **7** cards carry a pet model id, and the client
ships exactly **7** pet descriptions (`content.24.71/75/80/88/92/99/103`).

### Type 300 — Summons (`jz_2`) — 17 of 17 fields ✅ complete
Was 7 of 17. The tail is now decoded, and the four innate displacement properties are
**applied at spawn** (B-060): 22 of the 53 shipped creatures are rooted, 21 cannot be
carried, 18 are stabilised and 15 intransposable — none of which we honoured.
`Block`/`Dodge` (29 and 36 creatures) are decoded AND applied — they feed the tackle roll
(B-063). `DeadFlag` is dead
in the client too; `NoPositionalBonus` is inert because 2.70 dropped directional damage.

### Type 400 — Challenges (`GE`) — 5 of 17 fields
Decoded: id, six raw ints, reward cards, time-challenge. Of the rest, most have **no
callers in the client either**; the meaningful unread ones are the linked achievement id,
the XP reward and the XP cap.

### Type 210 — Traps / special zones (`rf_2`) — 8 of 13 fields
Decoded: id, type string, AoE shape+params, max triggers, effects. **Not read**: the
re-trigger policy (once per team / per target / always), the two trigger bitmasks, and
the delayed re-trigger timer.

---

## 4. Values still hardcoded that SHOULD come from data

| Value | Where | Correct source |
|---|---|---|
| Breed HP/AP/MP/init/element/value | `game/breed.go` | client enum `xq` — **compiled into the client, not in .bdat**; hardcoded of necessity, now pinned to `xq` by tests |
| Close-combat 5 AP / 5 dmg / 7 crit | `game/breed.go` | same (`xq.DO/DP/DQ`) |
| Fighter states (`stateByAction`) | `game/states.go` | **type 902** — should be read |
| Tournaments | `game/tournaments.go` | **type 1000/1001** |
| Interactive elements | `game/elements.go` | `maps/env/*.jar` + **type 360** |
| Zaap destinations | `game/zaap.go` | `maps/env` descriptors |

`xq` is a Java enum inside `core.jar`, not a data record, so it cannot be read at runtime;
hardcoding is unavoidable there. The mitigation is the test suite: `breed.go`'s table is
pinned field-for-field to `xq`, so drift is caught rather than silent.

---

## 5. Recommended order of work

A pattern is now visible and worth stating: **the remaining gaps are mostly MECHANICS, not
data.** Card sets made it explicit — 78 of its 88 effects are decoded and inert because
XP, morale, fatigue, drops and wounds simply do not exist server-side yet. Decoding more
records will not change that. The list is ordered accordingly.

1. **The coach META layer, slice 2** (wounds / death / drops) — slice 1 shipped (B-065); one
   subsystem that would activate ~78 already-decoded set effects, most coach-card
   effects, and the type-902 condition layer at once. This is the biggest *unlock* per
   unit of work, but it is a feature, not a fix; size it first.
2. ~~**`np_1[]` element layout**~~ — DONE (B-071). Was: one unknown that unblocks 8 coach-card fields *and*
   parts of the challenge and tournament records. Decode it once, gain three records.
3. **Spell `TargetMasks` + `MaxActive`** — both decoded, neither evaluated; needs the
   client's `aLc` evaluator and a live-instance counter respectively. Small payoff (3
   and 6 spells).
4. **Type 1000/1001 tournaments** — replaces hand-built definitions with real rules,
   rewards and prize tables.
5. **Types 900/901 Sphere Board** — the largest unimplemented system (17 542 records).

## 5b. The `np_1` fight-ruleset types (`ajr_2`)

Decoded in B-071; types 10/11/13 wired in B-072. The low block is a fight-ruleset
system — the mechanism a challenge or tournament uses to customise a match:

| type | meaning | note |
|---|---|---|
| 1-3 | budget, min/max fighters | |
| 4-9 | spell/equipment allow + ban lists (incl. "ban everything") | |
| **10** | **per-fighter turn duration (ms) — a DELTA** | ✅ wired (B-072); only challenge 46 uses it: +3 600 000 ms |
| **11** | **sudden-death turn — a DELTA (±turns)** | ✅ wired (B-072); unused by challenges, expected tournament-side |
| 12 | cast an effect on all fighters at fight creation | carries an inline `Ht` |
| 13 | multiply bonus-cell effects (absolute) | ✅ wired (B-072); 5 challenges (x2, x2, x5, x10) |
| **14** | **victory condition** | the ONE type with its own layout (`wi_0` + `mp_2`); 9 challenges |
| 15-25 | class limits, class bans, fighter/spell/equipment prices | |
| 26, 32 | event list, sudden-death event list | |
| 27 | add a coach spell | |
| 28 | max distinct classes | |
| 29 | choose the arena | |
| 30 | max league | |
| 31 | hide opponent statistics | |
| 900 | class parameter | |
| 1000 | `Pas de limite de budget` (no params) | named, not enforced; challenge 12 |
| 901-912 | per-breed spell parameters (Féca … Pandawa) | |

## 6. Change log

| Date | Change |
|---|---|
| 2026-08-04 | np_1 rule 13 (bonus-cell multiplier) applied to the beneficial tiles; timing rules 10/11 corrected to DELTAS per content.54.* (B-072). |
| 2026-08-04 | Wired np_1 rule types 10/11: the turn clock and sudden-death turn are per-fight and read from data instead of hardcoded package globals (B-072). |
| 2026-08-04 | Decoded the `np_1` element (B-071): coach cards now 26/26 fields with zero residual across all 907 records; challenges 36/39 exact; the ajr_2 type enum documented as a fight-ruleset system. |
| 2026-08-04 | Evolution fighters can be created: the et_2 type byte is honoured via a persisted Fighter.Evolution flag, separate from State (B-070). |
| 2026-08-04 | Challenge reward cards reported on the end-of-fight panel; the won/lost card-blob order corrected (B-069). |
| 2026-08-04 | Wire text encoding corrected to windows-1252 in both directions; the length prefix now counts encoded bytes (B-068). |
| 2026-08-04 | Coach cards keep their full akw_0 effect array; the 325 usable cards (healing, rest, morale, XP, blessings) now work via 22099 (B-067). |
| 2026-08-04 | Coach META slice 2: type 902 decoded (111 records, 5/5 fields), conditions persisted + on the wire, the wound roller (bf_1.b) and the death roll (adl_0.atd) ported (B-066). |
| 2026-08-04 | Coach META slice 1: the 8300 per-fighter post-fight report (adl_0/OW) now exists; XP, morale, fatigue and coach reputation applied with the client's own formulas (B-065). |
| 2026-08-03 | e2e combat tests made side-agnostic; the "client A == side 0" assumption was the long-standing flake (B-064). |
| 2026-08-03 | Tackle wired to the real block/dodge characteristics — breed table args 13/14, actions 120-123, summon template fields; actions 147/148 (crit/fumble malus) applied (B-063). |
| 2026-08-03 | First audit. Fixed the spell cooldown field (B-059); decoded the summon tail 7→17 fields and applied innate properties (B-060); decoded coach cards 8→18 fields and enforced tradability (B-061); completed the spell record 19→23 fields with parent-spell limit sharing; decoded card sets and wired the resurrection bonus (B-062); corrected this document's wrong claim about type 902. Types decoded: 7 → 8. |
