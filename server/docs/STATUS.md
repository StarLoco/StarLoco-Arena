# STATUS — read this first

Single entry point for picking the 2.70 server back up cold. Everything else is
detail; this is state.

**Updated:** 2026-08-10

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
| B-078 | **Tier 0 complete.** Effective AP/MP derived like the client's `gn_0.d`; `StringU8` enforces the 127-byte limit (a remote client-crash vector) |
| B-077 | Dispel no longer strips summons' innate states; `Coach.Standing` persisted and put on the wire (2052 + 4096) |
| B-076 | Forced displacement (push/pull/teleport/swap/throw) now arms traps; the full type-210 trigger enum recovered from `he_1.a` |
| B-075 | Two anti-cheat holes closed: 8021 placement was a free teleport to any cell at any time; spell casts never checked the caster owned the spell |
| B-074 | np_1 types 12 + 14 wired: fight-start effects and victory conditions now drive fights (12 of 39 challenges); target-condition bits 512/1024 added |
| DIST | Shipping pipeline: automated releases, self-writing config, web sign-up, update notice (§10) |
| B-073 | Challenges 39/39: inline unlength-prefixed effects now parsed exactly |
| B-072 | Turn clock + sudden-death turn now per-fight, from data (were hardcoded globals) |
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
4. **Enforce the remaining `np_1` rules.** Turn duration, sudden death and the
   bonus-cell multiplier are wired (B-072); fight-start effects (12) and victory
   conditions (14) are wired (B-074). Still inert: budget (incl. type 1000 "no limit"),
   roster limits, banned/allowed spells and equipment, class limits and prices, arena
   choice, event-list choice. **Read `content.54.<type>` before implementing any rule**
   — it is the authoritative semantics table, and it is what revealed that the timing
   rules are deltas rather than absolutes.
   ⚠ **The blocker is the operand, not the enforcement.** Every one of those rule types
   occurs exactly ONCE in the shipped data, on a coach card, with an EMPTY parameter
   array (dumped 2026-08-10). They read like per-rule template cards. Anything actually
   parameterised lives in type 13, type 10 or the 900-930 block. Find where the operand
   comes from before writing an enforcer that would index `params[0]` on an empty slice.
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
- **`gofmt -l internal cmd test` must be EMPTY.** (This used to name five files as
  having untouchable pre-existing drift. Verified 2026-08-10 against a pristine
  `HEAD` checkout: there is none, in those five or anywhere else. The old wording
  trained people to filter those names out of `gofmt -l`, which would mask real
  drift — do not re-add it.)
- **Data over v2.04b.** v2.04b is a useful *unobfuscated reference* for structure (its
  `Breed.java` is the twin of 2.70's `xq`), but its VALUES are beta-era and differ.
- `data/maps/` and `data/` must not move — tests read them by relative path.

## 7. Known limits of my own verification

- **Fights started by packet injection build no client-side match object**
  (`apN.aDK().aDL()` is null), so that client renders no fighters and processes no fight
  frames. I cannot self-verify any in-fight *visual*. Fights the user starts normally do
  render.
- Clicks reach AWT/Swing dialogs only, **not the GLCanvas**.
- **B-074 is server-verified, not visually verified.** The victory-condition chain is
  proven over a real socket (`test/e2e/victory_condition_test.go` drives a real
  challenge fight to the condition and asserts END_FIGHT names the coach; the server
  log shows `victory condition met ... round=2 winner=0` → `fight ended winnerTeam=0`),
  and every assertion is mutation-checked. What is NOT confirmed in the retail GUI is
  how the client renders the end-of-fight panel when **the losing team is still
  alive** — a state the elimination path never produces. Judged low risk: the 8300
  payload shape is unchanged by B-074 (only the winner id differs, a field that
  already varies) and that panel is live-verified for elimination wins. Confirming it
  needs a coach with fighters plus ~20 rounds of a "Défi du temps" in the GUI; worth
  folding into the next live session rather than doing standalone.
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

## 10. Distribution — the server as a product

Added 2026-08-05. The server is now something a non-technical person downloads and runs;
it no longer assumes a Go toolchain or a terminal-literate operator.

**Release pipeline** (`.github/workflows/`, `.goreleaser.yaml`)

- `ci.yml` — build/vet/test on Linux **and** Windows for every push/PR, plus `gofmt`, a
  `go mod tidy` check, and a cross-compile of `cmd/server` for all five release targets so
  a portability break is caught on the commit that causes it, not at release time.
- `release.yml` — `release-please` maintains a release PR from Conventional Commits;
  merging it tags + creates the GitHub release, then **GoReleaser** attaches
  Windows/Linux/macOS archives (amd64 + arm64, except Windows) and `checksums.txt`.
- **Do not split GoReleaser into its own `on: push: tags` workflow.** Tags pushed with the
  built-in `GITHUB_TOKEN` do not trigger workflow runs, so it would never fire and every
  release would ship empty. It must stay a dependent job in the same run.
- `cmd/studio` is excluded from releases and from Linux CI (Wails needs CGO + GTK/WebKit);
  the shipped binary is `CGO_ENABLED=0`, which is why one Linux runner can build everything.

**First-run behaviour** (`internal/config`)

- `config.template.yaml` is embedded and written to `config.yaml` on first start —
  every setting documented inline, unused ones commented out. This file is the only
  documentation most operators will read.
- `config_template_test.go` reflects over `Config` and **fails if a field has no key in the
  template**. Add a config field ⇒ document it, or CI breaks. Deliberate.
- Template and console output are **ASCII-only**: em-dashes rendered as mojibake in the
  Windows console (cp850). Keep it that way.
- Defaults changed for end users: `addr` `127.0.0.1` → **`0.0.0.0`** (friends can connect),
  `log_level` `debug` → **`info`** (quiet console).

**Game-data discovery** (`internal/gamedata/locate.go`) — added after the first
release, then revised days later (see the dated entry below — this paragraph
describes the discovery mechanism only, not the current bundling policy).

- The client stores the two halves in **different** directories:
  `game/contents/bdata/` (data.bdat + indexes.bdat) and `game/contents/maps/`
  (fight/ + tplg/). The old instruction "copy contents/bdata to data/" was therefore
  **wrong** — it silently produced a server with cards but **zero arenas**.
- `Resolve(root)` accepts any of: a merged `data/` dir, the client's `contents/`,
  its `game/`, or the install root holding `DofusArena.exe`. `Discover(configured)`
  walks the configured path, then exe-relative, cwd-relative (incl. the committed
  `server/data-dist/` and the source-checkout `client/compiled/game`), then the usual
  per-OS install locations — and will combine halves found in **different** roots
  (e.g. records in `data/`, arenas still in the client).
- `--data` flag overrides `data_dir` for one run.
- The failure message (now rare — see below) names which half is missing, how to fix
  it, and where it looked. `Location.Complete()` distinguishes "no data" from "half".

**2026-08-05, later the same day — reversed the "never bundle" call above.** The
maintainer (StarLoco) explicitly decided the small server-data subset (records only,
~2.5 MB — `data.bdat`, `indexes.bdat`, `maps/`) should ship in the box: it is now
committed at `server/data-dist/` (a deliberate exception alongside the still-gitignored
`server/data/` scratch dir — see `AGENTS.md` constraint 4) and copied into every release
archive and the Docker image, landing next to the binary at `data/`. `Discover`'s
existing candidate list already checked a cwd-relative `data`, so this needed **zero
gamedata code changes** beyond adding `data-dist` itself as a fallback candidate (so a
source checkout behaves the same as a downloaded release). The 436 MB retail client
remains fully excluded, unbundled, unchanged. `DISCLAIMER.md`, `NOTICE`, `LICENSE`,
`AGENTS.md`, `CONTRIBUTING.md`, `CLAUDE.md`, the root `README.md`, `.gitignore`,
`.gitattributes` and the Dockerfile were all updated in the same pass so no doc is left
claiming this data is excluded when it no longer is. Verified with the real
`goreleaser` snapshot output: extracted a bare zip with nothing else present, ran it
with zero flags — 907 cards / 203 spells / 47 arenas, no setup. Archives also renamed
`dofusarena-server_*` → `arena-server_*` in the same change.

**2026-08-05, later still — the retail client itself, by link, not by commit.**
The maintainer supplied a personal Mega mirror of the full retail client and asked
for it on the web portal, the README and the release notes. Unlike the data-dist
call above, `client/compiled/` is **not** going into git — it is the actual
copyrighted game (art, audio, the executable, ~436 MB), a materially bigger claim
than the small server-record subset, and directly contradicted what `DISCLAIMER.md`
said about it minutes earlier ("not distributed here at all"). Flagged that once
before touching anything (bigger legal-exposure surface than data-dist, GitHub does
act on repos that link piracy hosts, sometimes at the account level) and let the
maintainer choose with that in view; the answer was to add it in all three places.
Implemented as `web.client_download_url` (`internal/config`, default = the mirror
link, blank hides it, overridable per-fork) rather than hardcoding the URL into the
template — Mega links get taken down and need rotating, and a config field means
that never needs a rebuild. Rendered as a new conditional panel on the portal
(`internal/web`), templated into every future GoReleaser release footer, and added
to the root README (top callout, the client/data comparison table, Step 2). Fixed
a stale, now-provably-false line in `config.template.yaml` found while in there:
the `data_dir` comment still claimed "not distributed... fights are unavailable",
left over from before the data-dist bundling above. `DISCLAIMER.md` and `NOTICE`
restructured from a two-way (committed / not-distributed-at-all) split into three:
committed-in-git, linked-to-an-external-mirror-but-not-committed, and genuinely
neither (now only `server/data/`, the local dev scratch copy). `AGENTS.md` gained
a constraint (5) so a future pass does not "clean up" the link thinking it is an
oversight, and pins the raw URL to four places on purpose, not scattered further.

**Web portal** (`internal/web`) — single embedded page, no JS, no external assets.
Players self-register; **the first account created becomes admin** (a release archive has
no `seedaccount`, so there must be some path to a GM). Rate-limited per IP, same-origin
checked, bcrypt via the existing store. Port ladder: 80 → 8080 → 8090 → 3000 → 5000 → any
free port, so it always starts even unprivileged.

**Update check** (`internal/update`) — one anonymous GET of GitHub's `/releases/latest` at
startup; prints a notice if newer. Never downloads or installs. Silent on every failure
(offline servers must not be nagged). Skipped entirely on unstamped `dev` builds.

Open follow-ups, none blocking:

1. **Windows SmartScreen** warns on the unsigned `.exe`. Real fix is a code-signing
   certificate (paid) or [SignPath](https://signpath.org/) (free for OSS). Documented as a
   click-through in `docs/QUICKSTART.md` for now.
2. **No Docker image is published** on release. The `Dockerfile` still builds locally; only
   the binaries are automated.
3. **First release must be cut manually-ish**: `.release-please-manifest.json` starts at
   `0.0.0`, so the first `feat:` commit produces `v0.1.0`.
