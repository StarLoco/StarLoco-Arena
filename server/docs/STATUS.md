# STATUS — read this first

Single entry point for picking the 2.70 server back up cold. Everything else is
detail; this is state.

**Updated:** 2026-08-04

---

## 1. What this project is doing right now

Making the from-scratch Go server wire-compatible with the **retail 2.70 client**, driven
by bugs found in live play. Current theme: **the client's data files are the single source
of truth** — no hardcoded values, no values ported from v2.04b (a beta whose numbers were
re-tuned). Every v2.04b-inherited value checked so far has turned out wrong in 2.70.

## 2. Where to look

| Doc | What it answers |
|---|---|
| **this file** | What is done / in flight / next |
| `DATA-COVERAGE.md` | Which client records we read, field by field, and what is still unread |
| `BUGS.md` | Every fix: symptom, root cause (with the client class that proves it), verification |
| `OPCODE-INVENTORY.md` | Every opcode and its handler |
| `CLIENT-TESTING.md` | Live-client harness, and the e2e flakiness diagnosis |
| `CLIENT-UI-MAP.md` / `OVERWORLD-MAP.md` | Canvas click targets, world layout |

## 3. Recently completed (newest first)

| # | What |
|---|---|
| B-071 | Decoded `np_1`: coach cards 26/26 (zero residual x907), challenges 36/39 |
| B-070 | Evolution fighters could not be created (et_2 type byte was decoded and dropped) |
| B-069 | Challenge reward cards now reported in 8300; won/lost blob order corrected |
| B-068 | Wire text is windows-1252, not UTF-8 (both directions) |
| B-067 | Consumable coach cards: card effect arrays kept; 325 usable cards now work |
| B-066 | Coach META slice 2: type-902 conditions, the wound roller and the death roll |
| B-065 | Coach META slice 1: XP / morale / fatigue / reputation + the 8300 debrief record |
| B-064 | e2e combat flake: tests assumed "client A == side 0" (side 0 = first searcher) |
| B-063 | Tackle now uses real block/dodge (was a flat 67%); actions 147/148 wired |
| B-062 | Card sets ("panoplies") decoded; resurrection bonus wired |
| B-061 | Bound/undestructible cards can no longer be traded; coach card 8→18 fields |
| B-060 | Summon innate properties applied (22/53 creatures are rooted) |
| B-059 | Spell cooldown was read from the wrong field — 97/203 spells had none |
| B-058 | All 12 breed initiatives + base value were the 2.04b figures |
| B-057 | Cra's close-combat element was water, should be air |
| B-055/56 | Weapon attacks (8107) apply effects; fighter budget used the wrong card table |
| B-053/54 | Per-round event cards; actions 127/128 were mis-mapped onto root/stabilise |
| B-052 | All 47 fight maps (was hardcoded to world 5) |

## 4. Open items

Ordered by value. Item 1 is the biggest unlock; item 2 is the cheapest concrete win.

> Note: "tackle/dodge not implemented" used to head this list and was WRONG twice over —
> tackle existed, it just used a hardcoded 67% instead of the real stats (B-063). Verify a
> claim of absence against the code before acting on it.

1. **The drop table** (AI 18-21) — the last inert corner of the META layer.
   **Deliberately not implemented.** The client exposes only the four MODIFIERS
   (`avh_0`/`yt_0`/`aqm_0`/`akl` — drop bonus, drop chance, min/max level of dropped
   items); each is a pure accessor whose `a(et_2)` does nothing, i.e. the client never
   consumes them. The pool and the base rate are server-side and are **not recoverable
   from the data**, so building this means inventing the core mechanic — the same class
   of unknown as `baseXPPerFight`, but for a whole system rather than one constant.
   What IS evidenced and could be assembled if the rule ever turns up: the draw pool is
   `CoachCard.ObtainableInDraw` weighted by `DropPercent`, filtered by
   `RequiredLevel`; the payout channel is the 8300 won-cards blob (done, B-069); and
   the i18n says pets "augmentent les drops dans tous les modes de jeu, simulant un
   niveau de plus pour le joueur", which is AI 20/21 in prose.
2. **The CLIENT mangles accented names it receives** (B-068 residual). Proven with two
   controlled experiments: every server-provided name gains exactly one UTF-8→cp1252
   hop, and no input can undo it — pre-compensating makes it worse. Our encoding is
   correct and must NOT be changed to chase this. The open work is to locate the
   offending call in the decompiled client. Cosmetic; message bodies are unaffected.
3. **`gamedata` string fields are still read as UTF-8.** The `.bdat` text the client
   ships is almost certainly cp1252 too. Every field we currently read is ASCII
   (`"FIGHTER_CONDITION"`, `"FIGHTER_CARD_USE"`), so nothing is broken today — but the
   moment a name or description field is decoded it will need `protocol.DecodeText`.
4. **Wire the `np_1` fight-ruleset system** (decoded in B-071, unused). Type 10 is the
   per-fighter turn duration and type 11 the sudden-death start turn - both hardcoded
   in the server today - plus budget, roster limits, banned spells/equipment, arena and
   event-list choice. This is the mechanism challenges and tournaments use to customise
   a fight, so it is a prerequisite for doing tournaments from data properly.
5. **Spell `TargetMasks` + `MaxActive`** — decoded, not evaluated. Needs the client's
   `aLc` evaluator / a live-instance counter. Small payoff (3 and 6 spells).
6. **Tournaments** (types 1000/1001) — currently three hand-built definitions; the real
   rules/rewards/prize tables are in the data. Live-match layer (opponent search
   28609/28611, brackets, admin opcodes) deferred by the user.
7. **Sphere Board** (types 900/901, 17 542 records) — largest unimplemented system.
8. **2v2 / multi-coach fights** — deferred by the user.
8. ~~**e2e residual flake**~~ — **fixed** (B-064): the tests assumed "client A == side 0",
   but side 0 is whoever reaches the matchmaker queue first, and the harness fires both
   searches without a happens-before. Full suite now passes repeatedly under load.

## 5. Awaiting the user

Nothing. The two items that used to sit here were **verified live in the retail client**
on 2026-08-04 (see `CLIENT-TESTING.md` for the driver — a solo "Tester" fight needs no
second player, and the client HUD renders fully once *the client* starts the fight):

- **Round event card renders.** Round 1 drew **event 14** server-side and the client
  displayed the card **"Cloué au lit"**. Its own effect text — *"Stabilise la cible
  (impossible à pousser ou attirer), non portable, rend intransposable"* — is an
  independent confirmation of B-053's mapping: actions **94 + 127 + 128**, and notably
  **no** movement block, exactly as decoded.
- **Sudden death.** `/SUDDENDEATH` shrank the arena (`step=1 r=104`, then
  `step=2 r=119 killed=2`); the client rendered the collapse and both fighters standing
  on removed cells died (*"Tanko perd 70 PdV / Tanko est mort"*), ending the fight.
- Bonus: the end-of-fight panel showed coach Loov at **Niveau 13 (1500)**, which matches
  the client's own `strengthToLevel`: `1 + round((1500-1000)/2000*49) = 13`.

## 6. Invariants — do not break

- **`OPCODE-INVENTORY.md` H count must equal the `r.Register(protocol.` count in
  `internal/game`.** Currently **82 = 82**. Check after adding a handler.
- **Never send opcode 22002.** Criteria reach the client only via the 2052 `0x200` blob.
- **Do not "fix" pre-existing gofmt drift** in `handlers_team.go`, `packets_test.go`,
  `summon_test.go`, `target_conditions.go`, `team_codec.go`.
- **Data over v2.04b.** v2.04b is a useful *unobfuscated reference* for structure (its
  `Breed.java` is the twin of 2.70's `xq`), but its VALUES are beta-era and differ.
- `data/maps/` and `data/` must not move — tests read them by relative path.

## 7. Known limits of my own verification

- **Fights started by packet injection build no client-side match object**
  (`apN.aDK().aDL()` is null), so that client renders no fighters and processes no fight
  frames. I cannot self-verify any in-fight *visual*. Fights the user starts normally do
  render.
- Clicks reach AWT/Swing dialogs only, **not the GLCanvas**.
- The e2e harness builds `game.Deps` with **nil** `Cards`, `Spells`, `FighterCards`,
  `Summonings`, `CardSets`, `Events`, `FightMaps`. Most data-layer work is therefore
  provably inert in e2e — check this before blaming a gameplay change for a red suite. A
  *varying* failing test is machine load; a *consistent* one is a regression.

## 8. Test account + dev tooling

- Account **locos975 / azerty**, coach **Loov (id 3)**, fighters **22–29**.
  Fighter states: 0 titular, 1 bench, 2 dead, 3 graveyard.
- ⚠ **I modified fighter 22's loadout** during weapon testing: it now carries weapon
  **85** (melee, 4 AP, range 1-1) alongside its original card 149. Revert via the fighter
  builder if unwanted.
- Dev endpoints (loopback, DEV builds only):
  - `curl "http://127.0.0.1:5599/c2s?opcode=&arch=&hex="` — inject a client packet
  - `curl "http://127.0.0.1:5599/fight"` — live fight dump
  - `curl --get --data-urlencode "cmds=..." "http://127.0.0.1:5599/script"` — drive a
    fight synchronously: `goto <wire> [ai] | move | cast | castself | usecard | cc | end |
    dump | wait`. **URL-encode `;`** or Go's query parser eats everything after it.
- GM chat: opcode **3151** arch 3, payload `012A` + `[u8 len]` + utf8.
  `/HELP /WHERE /TP /WORLD /WHO /STRENGTH /FSTATE /ENDFIGHT /EVOFIGHT /SUDDENDEATH
  /MAPDESTRUCT`.
- Build/test from `server/`: `go build ./... && go vet ./... && go test ./...`,
  e2e `go test ./test/e2e/`.

## 9. Method that keeps finding real bugs

For each client record type: read the deserializer `a(ByteBuffer,int,short)` for the exact
field **order** → find each obfuscated field's **getter** → grep the getter's **callers**
for meaning (no callers = dead in the client too, safe to skip) → then **dump the real
distribution** across all records. A mis-assigned field shows up instantly as an
implausible histogram (spell cooldown: 97/203 on field 10 vs 6 on field 8). Finish with a
real-data canary test asserting the population size, so a future field-order slip fails
loudly instead of silently zeroing a mechanic.
