# Bug log — DofusArena 2.70 server

A running record of protocol/behaviour bugs found while making the from-scratch
Go server wire-compatible with the retail 2.70 client. For humans **and** the AI:
each entry has the symptom, the root cause (with the client class that proves
it), the fix, and how it was verified.

**Verification legend:** `unit` (Go unit test) · `e2e` (scripted wire client) ·
`live` (real retail client via the control agent) · `audit` (byte-compared vs
decompiled client, no runtime).

---

## Open / suspected

### Coach action deck — nothing populates it in the 2.70 build (investigation CLOSED)

The wrong-namespace half is fixed (B-088). The remaining question was what should
populate `coachActionDeckSpellIDs`. Answer: **nothing does, in this build** — so
the empty deck is the complete and correct behaviour, not a stub awaiting work.

Every mechanism that could grant a coach an action spell was followed to ground:

- **`np_1` type 27**, literally *"Ajouter un sort de coach"* — appears on **no**
  shipped coach card. The 13 rule types that do appear are catalogue entries with
  no operands.
- **`azk.h()` / `azk.i()`**, which bucket castables by breed 99 / 98 (`xq` is the
  breed enum: `axT(98)`, `axU(99)`), and **`azk.aLO()`**, which would draw up to 3
  at random from the breed-99 bucket — **none of the three has a single caller**,
  so the buckets are never filled and the draw never runs.
- **The coach card record** carries no spell reference. It is decoded to the end;
  its last field `tE()` is the colour PALETTE index (the client builds
  `"fighterColor" + tE()`), not a link to anything castable.
- **`zd_2`** is the Masqueraider mask picker (only the 5 parented spells
  471/472/473→462, 474/475→452), and **`aJt.Qx()`** is a SUMMON's spell list
  (`ta_0`'s own error string says *"SummonedFighter"*).

The client-side feature is fully built — the deck is exposed as
`"coachSpellInventory"` and played with **8109**, the ordinary spell cast — but
2.70 ships no data to fill it. If a source is ever found or invented, the only
change needed is the candidate list in `coachActionDeckSpellIDs`; the filter, cap
and wire format are already right, and the cast handler would then also need to
accept a deck spell the FIGHTER does not know (`fighterKnowsSpell`), since it
belongs to the coach.

- **The coach META layer is only PARTLY built.** Slice 1 — XP, morale, fatigue and
  coach reputation — landed in B-065. Still missing: the wound roll, the death roll
  and the drop table, so ~30 of the 78 card-set effects remain inert.
- **Fighter conditions (type 902) are unmodelled** — wounds never accrue between fights.
  Fully decodable (0 unknown fields); needs the roller `bf_1.b` ported.
- ~~**Most `np_1` rule types are decoded but not ENFORCED.**~~ — **resolved: they are a
  CATALOGUE, not rules.** Types 1–32 are rules, 900–930 are the typed OPERANDS
  (`ajr_2` names every one *"Paramètre de …"*), and `np_1.b()` concatenates a rule with
  following entries until it has `T()` of them. The 13 rule types on coach cards each
  appear once with zero params — a menu for composing a custom ruleset, which `jk_1`
  ("coachCardFightParametersManager") pairs with parameters to build the picker. Rules
  that really apply arrive already parameterised via the challenge records (10/12/13/14,
  all wired). Pinned by `TestNp1RuleCatalogueShape`.
  **Consult `content.54.<type>` for a rule's exact semantics before implementing one** —
  it is the authoritative label table, and it is what revealed the timing rules are
  deltas rather than absolutes.
- ~~**Spell `TargetMasks` / `MaxActive` decoded but not evaluated.**~~ — `TargetMasks` is
  enforced (B-081, 3 spells). `MaxActive` is deliberately NOT enforced: its decay window
  is one turn (`arm_0.lQ(1)`, a literal), which is the granularity `CastMaxPerTarget`
  already has, and all 6 spells are already bound at least as tightly by an enforced
  limit. Pinned by `TestMaxActiveIsRedundantInShippedData`.
- **[RESOLVED — kept for the reasoning trail] The end-of-fight dialog.** It now
  renders: see B-098, which found the actual cause (the evolution mode that
  produces it could not be started at all, because 23003 went unanswered), and
  B-096, verified live by the same run. The investigation below is left in place
  because the *method* mistake it records is the reusable lesson.

  The earlier "it never appears" claim was wrong. It rested on fights started by
  injecting CREATE_FIGHT at a client that had not asked for one. Capturing the
  client's own log (see the tooling note below) showed what really happened:

  ```
  WARN [DEFAUT DE CONCEPTION] Message (aAt) non traite, de type 8000, ...
  WARN [DEFAUT DE CONCEPTION] Message (YP)  non traite, de type 8300, ...
  ```

  **CREATE_FIGHT itself was never handled.** `WE` — the only handler for 8300 — is
  registered in exactly one place, `adu_0` line 244, i.e. by the fight object the
  client builds *when it processes 8000*. A client that never entered fight mode has
  no 8300 handler, so the absent dialog was an artefact of the test method, not a
  server fault. The screenshots agree: they show arena scenery with no fighters, no
  timeline and no fight HUD — the map had loaded from ENTER_INSTANCE and nothing more.

  What was still needed was a fight the client STARTS ITSELF, then watching for the
  result screen. Doing that produced the real answer twice over: the "Tester"
  button gave a genuine client-initiated fight that ran and ended cleanly but
  carries no reports (practice and challenge fights skip progression by design),
  and Evolution → COMBATTRE, the mode that DOES produce the dialog, turned out to
  be unreachable — B-098.

  *Confirmed live in passing:* the client's **calendar** renders the three standing
  tournaments from the database across the month, so the 17003 path works end to end
  in the real UI.

  Eliminated so far, each by reading the client and matching the server against it:
  the 8300 payload decodes (`YP.a` read field-by-field against our writer, including
  the two-i32 action header `ue_0.o` and the `len >= 9` guard); the fight kind now
  reaches `aKl()` (B-095); the per-fighter reports are now resolvable in the
  recipient's roster (B-096); and the coach ids in the winner/loser lists match the
  ones `writeFightCoachBlock` registers, so `bv.ef(id)` can find them.

  Next suspects inside `y_0.run()`, which is where the dialog is pushed:
  the `fight.team0` / `fight.team1` properties it reads into `teArray` and then
  dereferences unconditionally (`teArray[0].hM(...)`) — if either is unset the
  method throws just before `apN.aDK().a(ajo_1.azb())` — and `bC`'s `OW` blob
  length, since `new OW(bytes)` may itself be strict about the 40-byte record.

---

## Fixed

### B-100 - the TOURNAMENT "Combattre" was also unanswered; refused visibly

The third and last member of the pattern (client frame `ds_2`, twin of `vu_1` /
`wp_0`). Its C2S pair — **28611** `ly_1` search and **28609** `bt_0` cancel — was
unserved, so the Tournois tab's "Combattre" went silent exactly like B-098/B-099.
28611 is sent by **two** tabs: Tournois (with a real team id) and **Légendes**
(with the legend pseudo-preset 9999).

**It is NOT a clean twin, and assuming it was would have shipped a broken frame.**
The request, cancel and result all carry a leading tournament id, 28614 carries one
too, and **28616 has a SECOND byte**: when `code == 2` the client ignores the usual
message table and calls `zN.M(subCode)`. A one-byte error — the shape the other two
families use — is a short frame and a decode failure.

**This REFUSES rather than queues, deliberately.** For the other two families
accepting the search is truthful: two coaches really can pair and fight. A
tournament match is not a free pairing — it is a specific bracket fixture between
two registered entrants, and this server has no bracket/match layer (28649 is
answered with an empty tree, and the live-match layer is deliberately deferred).
Pairing arbitrary searchers would invent semantics and produce fights that advance
nothing, i.e. silently pretend tournaments work. So the answer is the client's own
`matchfinder.impossibleToStartOpponentsSearch` (code 1), which shows a message and
leaves no overlay behind, and 28609 is answered so the Cancel path works.

When the bracket layer lands this becomes: verify the coach is an entrant of `tid`,
accept with 28612, pair by fixture, then 28614 followed by CREATE_FIGHT.

**Verified** `e2e` — refusal with an exactly-2-byte payload and code 1, the same
for the Légendes preset 9999, and the cancel reply. Mutation-checked: shortening
28616 to one byte (i.e. treating it as a clean twin) fails the test.

**NOT verified live**, stated plainly: reaching 28611 from the UI needs a team, a
saved preset AND a selected tournament, and the client cannot even say so —
clicking "Combattre" with no tournament selected renders the literal
`!error.noTournamentSelected!`, because `hu_2:814` asks for
`error.noTournamentSelected` while all four `texts_*.properties` define it as
`tournaments.noTournamentSelected`. That is a **retail client defect**, not ours;
recorded in `client/analysis/PROTOCOL-messages.md` along with the full three-family
table.

### B-099 - the CLASSIC "Combattre" had the same silent-queue defect, plus a double-queue bug

Found by asking, after B-098, whether the classic twin had the same gap. It did.

**Symptom.** 23103 was served — the coach really did enter the queue and really did
get a fight when someone else readied — but **none of the replies were sent**. So
while waiting the player saw nothing at all: no "Recherche en cours" overlay, and
because the Cancel button lives *inside* that overlay
(`avl_0.cancelSearch` is registered by the 23104 handler), **no way to leave the
queue**. Clicking "Combattre" again just queued them a second time.

**A stale comment had covered this up.** The handler documented itself as "the
coach waits (the client shows `waitingForOpponentCoach`) until an opponent
readies". That string exists, but it belongs to the fight-INVITATION flow
(`B:96,125,154`, `aqr_0:25`) and is never used on the 23103 path. Checking it was
what exposed the bug — the premise-check habit paying off on our own prose rather
than on the roadmap's.

**Root cause.** `vu_1` (classic) is character-for-character `wp_0` (evolution)
with one string changed, `classicSearchStatusDialog` for
`evolutionSearchStatusDialog` — same four cases, same branches, same teardown
rules. The whole family was simply unimplemented:

| classic | evolution | dir | payload |
|---|---|---|---|
| 23101 `bm_1` | 23001 | C2S | `[i64 coachId][i16 teamId]` cancel |
| 23102 `ada_1` | 23002 | S2C | `[i8 accepted]` |
| 23103 `atj_0` | 23003 | C2S | `[i64 coachId][i16 teamId]` search |
| 23104 `aLi` | 23004 | S2C | `[i16 teamId][i8 accepted]` |
| 23106 `ads_2` | 23006 | S2C | *(empty)* |
| 23108 `M` | 23008 | S2C | `[i8 code]` |

**Fix.** The handshake now lives in one place (`search_handshake.go`,
`searchFamily`) and both tabs share it, so the traps only had to be written once
and the twin relationship is explicit. 23103 accepts with 23104, announces 23106
to both sides on pairing, and 23101 is handled and answered with 23102. A
`CancelSearch` before enqueueing makes a double click idempotent.

One deliberate asymmetry: the evolution preset is the synthetic 99 and is refused
if it is anything else, but the classic i16 is a **real team id and may be -1**
("no preset selected", `hu_2:969-973`), arriving as 65535 and resolving to no
roster. That is tolerated — `buildFightTeamFor` falls back to the coach's own
fighters, which is this path's long-standing behaviour and not something to
tighten while fixing an overlay.

**Verified** `live` — Elite tab → COMBATTRE showed **"Recherche en cours……"** with
its Cancel button (`combattre: waiting for opponent team=1`), and clicking
**Annuler** closed it and logged `combattre: search cancelled`. That button was
unreachable before this fix.

Also `e2e` — accept + cancel (including the -1 preset passing through), pairing
with 23106 ahead of CREATE_FIGHT, and a double-click guard that pairs a third
coach against a stale duplicate entry if the dedupe is removed. All three
mutation-checked: dropping the 23104 send, the 23106 send, or the dedupe each
fails its own test with the specific diagnostic.

### B-098 - the EVOLUTION tab's "Combattre" was unanswered, so the mode was unreachable

**Symptom.** Team panel → Evolution → **COMBATTRE** did nothing at all. The client
passed its own checks, sent one message and waited forever on a silent screen:

```
level=INFO msg="unhandled opcode" opcode=23003 arch=2 len=10
```

**Every evolution fight this server had ever run was created by the test
harness.** The mode the whole progression system exists for — XP, morale,
fatigue, wounds, permanent death, the graveyard — had never once been reachable
from the retail client.

**What the client is asking for.** `ajw_0` (23003, C2S, arch 2) is
`{i64 coachId, i16 preset}`, and it is the byte-identical twin of the classic
`atj_0` (23103) this server already served. Its whole family mirrors the classic
one, frame for frame (`wp_0` vs `vu_1`):

| evolution | classic | dir | payload | role |
|---|---|---|---|---|
| 23001 `abn_0` | 23101 | C2S | `{i64 coachId, i16 preset}` | cancel the search |
| 23002 `wf_2` | 23102 | S2C | `{i8 accepted}` | reply to the cancel |
| 23003 `ajw_0` | 23103 | C2S | `{i64 coachId, i16 preset}` | start the search |
| 23004 `amh_0` | 23104 | S2C | `{i16 preset, i8 accepted}` | reply to the search |
| 23006 `azl_0` | 23106 | S2C | *(empty)* | "Lancement du combat" |
| 23008 `KL` | 23108 | S2C | `{i8 code}` | search error |

**The 99 is not a mode** — an earlier revision of this entry said it was, and that
was wrong. `sw_1.bMm = 99` is a synthetic **team preset id** meaning "the
evolution team", a peer of graveyard (`10000`) and legend (`9999`); the object
carrying it (`xz_0`, bound to the Lua property `evolutionTeam`) sets it in its own
constructor, and the tournament path sends `xz_0.amc().tI()` rather than a
literal. The client's own minimum-budget rule is gated on it —
`hu_2:1073`, `xz_02.afr() && getValue() < 5000`, where `afr()` is `tI() == 99`.
It is therefore **not a database team id** and must map to the coach's TITULAR
line-up; looking it up in the teams table would miss, or worse, hit an unrelated
coach's real team.

**The handshake is not optional, and the order is load-bearing:**

```
C2S 23003            ->  S2C 23004 {preset, 1}   opens the "Searching…" overlay
   (opponent found)  ->  S2C 23006 {}            closes it, then CREATE_FIGHT
   (cancelled)       ->  S2C 23002 {1}
   (failed)          ->  S2C 23008 {code}
```

Two traps found by reading `wp_0`:

- **`23004` with accepted=0 is a dead end.** The client pops the team panels
  either way, but only opens the overlay when the flag is true — a refusal that
  way leaves the player on a bare screen with no message. A refusal must be
  `23008`, not a rejected search.
- **`23008` codes 1 and 2 show their message but leave the overlay up**; only
  3, 4 and 5 tear it down. So codes 1/2 are safe only *before* an accepted
  23004.

**Fix.** `handlers_evolution_search.go`: 23003 validates the preset, refuses an
empty line-up with 23008/2, accepts with 23004, and enqueues in the existing
matchmaker under a dedicated mode so evolution searchers only ever pair with each
other. On pairing both sides get 23006 and then the fight — created with
`evolution=true`, so it feeds progression. 23001 cancels and answers 23002.
Because `WE` re-sends 23003 unprompted at end-of-fight, a stale queue entry is
dropped first so a coach cannot pair with itself.

**Verified** `live` — the payoff run, with a synthetic second coach
(`internal/testclient`) to pair against:

1. COMBATTRE → the retail client showed **"Recherche en cours……"** with its
   Cancel button (the `evolutionSearchStatusDialog` overlay) — 23004 working.
2. The partner searched → server logged
   `evolution search: paired -> starting fight a=Chrono b=Sparrer` and
   `fight started practice=false evolution=true challenge=0`, the overlay closed
   and **a real evolution fight rendered in the retail client**.
3. Ending it opened the **`fightResultEvolutionDialog`** — see B-096/B-097 below,
   which this finally verified end to end.

Also `e2e` — `evolution_search_test.go`: accept-and-wait, cancel, empty-team
refusal, pairing, and that the produced fight actually banks XP (i.e. is not a
practice fight). The pairing test asserts 23006 arrives **before** CREATE_FIGHT
by inspecting the frames `WaitFor` saw ahead of it; deleting the 23006 send fails
it on both clients.

> Note on that ordering: it is structural, not delicate. CREATE_FIGHT is emitted
> from the fight goroutine (`startFightWithTeams` → `f.Post`), so anything sent
> synchronously from the handler necessarily precedes it. Reordering the two
> statements in the handler is therefore *not* observable and the test cannot
> catch it — what it does catch is the send being dropped or moved into the fight
> actor, which is the change that would actually break the client.

### B-097 - being knocked out in an evolution fight killed the fighter for good

**Symptom.** Every fighter that finished an evolution fight at 0 HP was written
to the database as permanently dead (state 2) - on the winning side too. The
modelled per-fighter death *chance* (B-066) therefore almost never decided
anything, because it was skipped for exactly the fighters most likely to be
affected.

**Root cause.** Two independent passes, neither of them derived from the client:

```go
// postfight_apply.go - the meta pass, per fighter
if f.Evolution && ff.HP <= 0 {
    rep.dead = true                       // skips the roll entirely
    fr.State = domain.FighterStateDead
} else if d.Conditions != nil { ... }     // the client-exact roll lives here

// handlers_fight_combat.go - a SECOND unconditional sweep, after the first
for _, ff := range t.Fighters {
    if ff.HP > 0 { continue }
    d.Store.Fighters.SetState(ff.Fighter.ID, domain.FighterStateDead)
}
```

The comment on the first (*"a fighter the FIGHT already killed... cannot be hurt
twice"*) reads like a derivation but is invented: nothing in the client links
HP to permanent death. B-043 introduced it honestly as a placeholder
(*"minimal-correct"*), and it then outlived the mechanic that replaced it.

**What the client actually says.** Permanent death is a per-fighter probability
computed from that fighter's own lifetime XP (`adl_0.atd()`:
`death% = (totalXp/1000)²/100`), with no HP input anywhere:

- `fightEndAchievementDeathDescriptionFailed` - *"vous n'avez pas occasionné de
  **mort définitive** chez les combattants adverses"*. You down the enemy team to
  win, so this string could never appear after a win if downing killed. The
  client also keeps the two words apart: `fight.die` (*"[#1] est mort"*) is the
  in-fight KO; *"mort définitive"* is the permanent one.
- `content.29.301` - *"et un **grand nombre de blessures** peut provoquer la
  mort"*: death is the end of an injury/fatigue chain built up over many fights.
- `bf_1.b` kills only when an upgrade roll lands on a fighter already holding 3
  serious wounds - cumulative, never HP-driven.
- Opcode 4520 `FighterDiesMessage` (`cd_2`) carries a bare fighter id and no
  permanence flag, and **no client code links `HP == 0` to `isDead()`/state 2**.

**Fix.** Delete the override; every fielded fighter takes the same roll. The
second sweep no longer decides or persists anything either - `runPostFightMeta`
already banks the result through `SaveProgress`, which writes `state` - so it is
now `announceDeaths`, whose only job is the 6006 roster refresh.

That rename fixed a **second bug hiding inside the first**: being the code that
pushed the roster, it gated the push on *a downed fighter existing*. A fighter
killed by the roll alone (i.e. every death that will now actually happen) never
triggered a refresh and stayed alive on the player's screen until relog. The
push is now keyed off who the roll killed.

`deathIsRolledNotDealt` in `postfight_apply.go` carries the evidence, including
the honest limit: `adl_0.atd()` and `bf_1.b` have no callers in the client (they
are server logic shipped inside `core.jar`), so we cannot prove whether retail
gated the *roll* on participation. What the evidence settles is that a KO does
not replace the roll with certain death.

**Verified** `unit` - `TestDownedFighterIsNotKilled` (a downed rookie survives,
death% being 0 at TotalXP 0) and `TestVeteranDiesFromTheRoll` (a fighter at 100%
death chance dies *while standing at full HP* - the dead one is the untouched
fighter and the survivor is the one who hit the floor, which is the whole point).
`TestDeathIsReportedForTheRosterPush` covers the push keying. All three were
mutation-tested: reinstating the old `HP <= 0` branch fails them with the exact
diagnostics quoted above.

**Verified** `live` (once B-098 made evolution mode reachable) - a real evolution
fight in the retail client, won by wiping the opposing team. Server:
`post-fight meta reports=9 killed=0 injured=0`. The result dialog's achievement
panel read, in the client's own words:

> **IMPITOYABLE** — *Hélas, vous n'avez pas occasionné de mort définitive chez les
> combattants adverses.*

That is the exact string quoted as evidence above, displayed after downing every
enemy — which is precisely the outcome the old rule made impossible, and the
clearest possible confirmation that a KO is not a permanent death.

### B-096 - END_FIGHT's per-fighter reports were keyed in the wrong id space

The post-fight debriefs in 8300 are keyed by an id the client resolves against
its **own roster**, and it does so without a nil check:

```java
// y_0.run(), after every other end-of-fight update
object22 = this.bC.eJ();
for (int j = 0; j < object22.length; ++j) {
    adY.atu().dz((long)object22[j]).a((OW)this.bC.t((long)object22[j]));
}
apN.aDK().a(ajo_1.azb());   // <- the line that opens the result dialog
```

`adY` is filled from the fighter list, which sends the raw database fighter id
(`buildFighterList` → `w.I64(int64(fighters[i].ID))`). The server was keying the
reports by the **fight wire id** instead — `FighterWireIDBase + fr.ID*16 + …`,
a value around 1.1e12 that is not in the roster at all. `dz()` returns null, the
`.a(...)` throws, and `run()` dies **before** the line that opens the dialog.

There is a second instance of the same mistake in the same place: the reports
were built once and sent to *both* coaches, so even with the right id space each
client received the opponent's fighters, which are equally unresolvable in its
roster.

Fixed by keying reports with the roster id and tagging each with its owning
coach, then narrowing per recipient (`reportsFor`). Spectators get none — they
have no roster to resolve against.

*Verified:* unit (the id is a roster id and specifically not in the wire-id
space; the scoping helper). Both of those passed against the broken code, since
they only exercise the builder — so the real guard is an e2e that runs a ranked
fight with **real roster fighters** (progression skips placeholder fighters with
id 0, and `fightFeedsProgression` excludes practice and challenge bouts, which
is why the obvious existing tests carry no reports at all) and checks the ids in
the actual frame against each recipient's roster. Mutation-checked: reverting to
wire ids, and dropping the per-coach scoping, each fail it with the specific id
named.

**Verified** `live` (after B-098 made evolution mode reachable at all): a real
evolution fight between two coaches ended with `post-fight meta reports=9` — 6
for one side, 3 for the other — and the retail client **opened the
`fightResultEvolutionDialog`** with a per-fighter XP breakdown for its own six
and nothing for the opponent's. That is the first time this dialog has been seen,
and it is what the wrong id space and the unscoped send would each have thrown
before reaching.

*Historical note:* this entry used to end "this did not, on its own, make the
result dialog appear — see the open item". That open item was itself wrong (the
earlier evidence came from injected fights the client never entered), and the
dialog was in fact unreachable for a completely different reason: B-098.

### B-095 - CREATE_FIGHT's fight kind was written into a byte the client never reads

Every decision the retail client makes about *what sort of fight this is* — which
result dialog to open, whether to read a coach's evolution level instead of its
strength, whether to look up challenge metadata — comes from one value, and the
server was putting it somewhere else.

`aat_2.ac` reads the 8000 header as:

| slot | lands in | read back as |
|---|---|---|
| i32 | `mv_1.cAq` | **`aKl()`** — the fight kind |
| i64 | `adu_0.cmF` | **`asy()`** — the challenge id |
| i8 | `mv_1.byp` | `ZC()` — **no reader anywhere in the client** |
| i64 | `mv_1.byv` | turn-display budget, `Math.max(31000, byv)` ms |
| i32 | `axw.aW` | fight instance id |

The server wrote a constant `1` into the i32, `0` into the first i64, the kind
(5 or 6) into the **unread** i8, and the challenge id into the turn-clock slot.
So `aKl()` was always 1 and `asy()` always 0, which means:

- `WE` case 8300 could never take `aKl() == 5`, so the challenge reward/XP panel
  was unreachable, and `dC(0)` would have returned null even if it had;
- `aKl() == 6` was never true, so the evolution result path and its
  Death/Injury achievement rows were unreachable;
- `aat_2` lines 194/214 never took the evolution branch, so an evolution fight's
  coach block was read as *strength* instead of the evolution level;
- `aKl() == 3`, the tournament path, was equally unreachable.

The semantics had actually been worked out correctly before — the old comment
named `WE case 8300 -> adu_02.aKl() == 5` — but the value was written to the
wrong field, and `aKl()` is the i32, not the byte that looks like a kind.

Fixed by deriving the kind once (`Fight.wireKind()`: evolution 6 > challenge 5 >
normal 1) into the i32, putting the challenge id in the first i64, leaving the
unread byte at zero, and sending the real turn clock in the slot that wants a
duration. `Fight.FightType` and `Fight.Bet` are gone with it: the first was a
constant 1 that only fed the wrong slot, and the second was never set by
anything (betting is vestigial in 2.70 — see the note below).

*Verified:* unit (`TestCreateFightKindLandsInTheSlotTheClientReads` decodes the
header exactly as `aat_2.ac` does and asserts each value's slot;
`TestCreateFightLeavesTheUnreadByteZero` stops the kind being put back into the
i8). Mutation-checked by restoring the whole original mapping, which fails five
assertions naming the specific slots.

**Not yet visually confirmed, and now known to be blocked by something else:** a
live challenge fight and a live evolution fight were both driven to a settled
result against the retail client, and **neither opened any end-of-fight dialog**
— not the challenge panel, not the evolution debrief, not the ordinary result
screen. Since the ordinary screen is also missing, the dialog is failing for a
reason upstream of the fight kind. This fix is necessary for those panels but is
evidently not sufficient; the missing result dialog is a separate open item.

### B-094 - `CardLocked` was read in three places and set nowhere

The last of the three persistence defects, and the answer turned out to be that
the question was wrong: nothing sets the flag because **2.70 has no per-instance
card flag at all**.

`CoachCard.Flag` carried two bits, `CardLocked` (1) and `CardCursed` (2). The
locked bit gated trading, mailing and the commit-time exchange invariant, and
was never written by anything. The cursed bit was written to every card the
server ever created and was never read.

Neither exists in the client:

- the card object on the wire is `eb_1`'s four bytes — one i32 reference id,
  `NT()` returns 4 — with no flag byte anywhere;
- the owned-card view model `wy_2.ce` lists 28 bindable property names and none
  of them is locked, cursed, linked or tradable;
- the only `isLocked()` in the client is `mi_2.isLocked()`, a local
  drag-and-drop lock on an inventory container that never touches the network;
- there is no "cursed" concept for cards in the i18n tables in any language —
  the only *maudit* strings are spell descriptions.

The real rules are **per-template**, in the `aPp` card record: field 12 `tp()`
(**Bound** — "on ne peut échanger/envoyer une kard liée") and field 13 `tq()`
(**Undestructible** — blocks destroy, sell, fuse and give-to-demon). The server
already parsed both into `gamedata.CoachCard` and already used them for trading
via `cardIsTradable`; only mail and the store were still consulting the dead
bit.

Fixed by deleting the flag outright — the field, both constants, every
`Flag: CardCursed` initialiser, and the portal's "Flags" column, which had been
rendering "Cursed" against every card a player owned. Mail now gates on
`cardIsBound`, which matches the client exactly: mail checks `tp()` **alone**, so
an indestructible card may be posted even though it cannot be destroyed or sold.
Using the broader tradability check there would have quietly refused a card the
retail client sends happily.

The `flag` column itself stays in existing databases — `AutoMigrate` never drops
— but nothing reads or writes it, and inserts fall back to its default.

*Verified:* unit (`cardIsBound` truth table, including that Undestructible is
NOT bound), e2e (`TestMailRefusesBoundCardsButAllowsUndestructible` posts all
three kinds and checks what actually left the sender's inventory).
Mutation-checked both ways: dropping the gate lets a Bound card through, and
substituting `cardIsTradable` wrongly refuses the Undestructible one.

### B-093 - The whole card-exchange block was on 2006 opcode numbering

Trading could never have worked with the retail client. The exchange messages
were implemented as a contiguous run, 5105–5112 in order, which is the 2006
layout. 2.70 renumbered the block, and the mapping is not contiguous:

| Ours (2006) | 2.70 | Client class | Direction |
|---|---|---|---|
| 5105 add card | **5105** | `ua_2` | C2S |
| 5106 remove card | **5107** | `wd_0` | C2S |
| 5107 set ready | **5109** | `ahJ` | C2S |
| 5108 cancel | **5111** | `any` | C2S |
| 5109 card added | **5110** | `asH` | S2C |
| 5110 card removed | **5112** | `aaz_1` | S2C |
| 5111 end | **5114** | `aqX` | S2C |
| 5112 user ready | **5116** | `dl_0` | S2C |
| — | **5113** | `Or` | S2C (new: refusal notice) |

Two of the opcodes the server *broadcast* — 5109 and 5111 — are **client-sent**
messages in 2.70 (`extends so_0`, `encode()` only) and have no case in the
client's decode factory `gz_1`, so the client could not have instantiated them.
Meanwhile the client's real remove-card (5107) would have arrived at the
server's set-ready handler.

The card payload was wrong too. The server wrote the 2006 shape
`[i32 refCardId][i64 uid][i8 flags]`, but 2.70's card object is `eb_1`'s four
bytes and nothing else (`NT()` returns 4, `b()` reads a single `getInt()`), so
`asH` reads `[i64 exId][i8 userIdx][i32 refCardId][i16 qty]` — 15 bytes against
the 24 being sent. There is **no per-instance uid on the wire at all**: the
client generates its own locally in `eb_1.b` via `uq_1.ahR()`. Cards are
therefore identified by **template id**, and the server now resolves them as
`(coach, template, pos = 0)`, which is how the rest of the codebase already
treats inventory (`ConsumeAndGrant`).

**Why it was not caught:** `COVERAGE.md` recorded all twelve opcodes as
*audited & correct*, and the end-to-end tests passed — because
`internal/testclient` had the same 2006 numbers hard-coded. The server was only
ever tested against itself. Both are fixed, and `TestExchangeOpcodesMatchTheClient`
now pins every opcode and direction to the client class that implements it, so a
server message can never again land on an opcode the client only sends.

Also added: **5113**, the refusal notice, which 2.70 has and the server did not.
It is now sent when the server refuses a stake — for a non-tradable card, and
for a unique card the receiver already owns (`ky_2.a` returns 2 in that case, so
the client would have rejected the incoming card and desynced its inventory
against a trade the server had already committed).

*Verified:* unit (opcode/direction table, byte-exact payload shapes for
5110/5112/5113/5114/5116), e2e (the exchange flow now runs over the corrected
numbering). Mutation-checked: restoring the 2006 numbering and re-adding the
uid+flags bytes each fail a named test.

**Live-verified end to end** against the retail client, using a synthetic second
player built on `internal/testclient` (it logs in over the real socket, places
itself beside the target coach so the client can resolve the inviter actor, and
grants itself a card the shipped data says is tradable). Observed in the real
UI:

| Message | What the client did |
|---|---|
| 5102 invitation | showed *"ExBot t'invite à participer à un échange."* |
| 5103 answer → 5104 result 3 | **opened the trade window** with both panels |
| 5105 add card (i32 template) | server staged it |
| 5110 card added, 15-byte payload | **the card appeared in the trade panel** |
| 5107 remove card | server unstaged it |
| 5112 card removed | **the card disappeared** |
| 5113 | sent when a non-tradable card was staked |
| 5114 | showed *"Proposition d'échange annulée"* |

That last row is the clearest demonstration of the bug: the server used to send
the end notice as **5111**, which this client implements as a *client-sent*
message, so it was discarded in silence. The same is true of card-added, which
went out as 5109.

The result codes were confirmed at the same time: `ug_1`'s 5104 switch opens the
trade window on **3** (`nk.c()` → `sd()`) and shows the cancelled-invitation
notice on 1 and 2, which is what the server already sent.

*Not covered:* clicking **Oui** in the invitation dialog through the test
harness produced a refusal rather than an accept, so the accept was injected
instead. That is unexplained and is a harness question (click placement) rather
than a protocol one — an injected `accept = 1` is read correctly by the server,
and `tw_0.encode` writes `[i64 exId][i8 accept]`, exactly the order the handler
reads.

### B-092 - The two play-time statistics were never incremented

`Coach.TimeInFightSecs` and `Coach.TotalPlaySecs` were fully wired *except* for
the part that counts: declared on the model, written to the wire as the 2400
statistics panel's `dL`/`dM` entries, persisted by `CoachRepo.Save`, even
asserted in a packet test with fixture values — and incremented in no code path
at all. Both showed 0 for every player forever.

It went unnoticed because nothing displayed them prominently. Building the web
portal's account page, which shows "Time in fight" and "Time played" as their
own rows, made it obvious.

Fixed in two halves:

- **Play time** is stamped on the session in `completeLogin` and banked by
  `Session.creditPlayTime`, called at the top of `onClose` — deliberately
  *before* the replaced-session early return, since a kicked session's time was
  really played. It only mutates the in-memory coach; the incoming session owns
  the struct and saves it later, carrying the total with it.
- **Fight time** is stamped in `FightManager.Create` (the one chokepoint every
  fight passes through) and credited by `Deps.creditFightTime`, called from all
  three ways a fight can conclude — declared winner, forfeit, and teardown with
  no winner — and made idempotent with a CAS rather than trusting a single
  call site that a future path might bypass.

Practice fights count toward time. They are excluded from wins, losses and
ladder movement because those are competitive records; time spent is not.

*Verified:* unit (arithmetic, idempotence, sub-second and zero-timestamp
guards), e2e (`TestPlayTimeIsPersistedOnDisconnect` over a real socket, plus an
assertion added to the existing full-fight `TestChallengeVictoryConditionEndsFight`
so the victory path is covered), and live — a 1m48s retail-client session showed
as `1m 48s` on the portal, having previously always read `0s`.

### B-090 - Every player who logged in became a server administrator

`handleAuthentication` auto-created unknown logins with `admin=true` **and**
promoted every existing non-admin account to admin on each successful login:

```go
} else if !acc.IsAdmin {
    // Dev/preservation server: promote existing accounts so GM commands
    // work without a manual reseed.
    if err := s.deps.Store.Accounts.SetAdmin(acc.ID, true); err == nil {
```

That was a deliberate convenience, and while `is_admin` only gated chat GM
commands on a LAN server it was merely generous. It stopped being harmless the
moment the web portal hung **account deletion, admin granting and
impersonation** off the same flag: every player who had ever logged in would
have arrived at the site already holding the keys to everyone else's account.

Fixed by making the flag mean something: only the **first** account on a fresh
server is created as admin (matching what the web portal already did for web
sign-ups), and logging in never changes the flag. Admin is granted afterwards by
`seedaccount --admin` or the console's own grant button.

**Operator note:** accounts already promoted by the old code keep `is_admin` in
an existing database — the fix stops the bleeding, it does not rewrite history.
`SELECT id, name, is_admin FROM accounts WHERE is_admin = 1;` shows who has it,
and the console's *Revoke admin* button removes it.

*Verified:* unit (`TestLoginDoesNotGrantAdmin`, `TestFirstAccountBecomesAdmin`).

### B-089 - Fusion consumed the player's CHOSEN card as fuel

The 5490 request is `[i32 count]{i32 cardId}` and the handler read every id as
an input. The LAST one is the target.

The client builds the array as the input list with the chosen card inserted at
index 0 (`add.java`: `jg_02.v(0, ajt_16.azv())`) and `ahg_0.encode()` then
writes it REVERSED, so the chosen card lands last on the wire. `azv()` is
`cCr`, which the fusion panel exposes as the property **"fusionCard"** - the
card the player is trying to MAKE.

So the server was fusing the player's target away as fuel and then handing back a
RANDOM card from the set, ignoring their choice entirely.

**Fix.** The last id is parsed as the target and the outcome IS that card. It is
still constrained to the inputs' CardSet - a player-supplied target with no
constraint would let anyone name the best card in the game and fuse two commons
into it. The altar's slot count now bounds the input count too
(`"slotCount" = lab.azi() - 1`, from the newly decoded type 1100).

Failure now also reports the target as **notObtained**, which is what makes the
client show *"fusionRecipeFailed"* naming the card that was missed instead of a
bare *"fusionFailed"* (`cp_0`, case 5491).

**The target's own COST is enforced**, straight out of the client's formula:
`kardsPower` = Σ inputs' `RequiredLevel` − target's `FusionPower` must not go
negative, and the altar's quality must reach the target's `FusionQuality`. Only
7 cards in the game carry those (all type 27, set 149: power 5/15/30/50, quality
5/15/30) - for the other 900 both are 0 and the checks are no-ops, which is
exactly why they are safe to add. A refused fusion consumes nothing and names the
target as `notObtained`.

**Still approximated:** the success probability CURVE. The panel shows "labPower"
beside "kardsPower" (Σ inputs' `RequiredLevel` − target's `FusionPower`) and
"quality", but the server owns the roll and no client code reveals the curve. A
hard `kardsPower >= labPower` gate would be WRONG: 543 of the 907 cards have
`RequiredLevel` 0, so most fusions would become impossible. Left as a flat
chance, documented, rather than guessed.

**The altar is chosen by POSITION.** The six in-world altars are six different
TIERS of the type-1100 table (ids 2-7: power 1/10/20/30/5/15, slots 2/3/4/5/2/3),
so which one you use decides how many cards may be fed in. The client resolves it
that way - `xx_2`, the fusion-altar element, parses its own descriptor as a
single parameter, the lab-definition id, and looks it up with `CN.by(id)` - and
our element table already carried that value as `worldElement.arg` (2,6,4,3,7,5
for the six altars). The handler now picks the nearest fusion altar in the coach's
world instead of a fixed default. Nearest wins rather than requiring adjacency: a
legitimate client is always standing at the altar it opened, and a hard distance
gate would risk refusing real fusions over a stale coordinate.

**Verified:** `e2e` - the three fusion tests now send the real 3-id layout and
assert the chosen target is what comes back, and that a failed roll names it.
Both mutation-checked: reading the id from the wrong end yields `obtained 700,
want the chosen target 702`, and dropping the notObtained report is caught too.
`unit` - TestFusionLabPickedByPosition, mutation-checked against the fixed-default
behaviour.

### B-088 - The 8000 coach-deck blob carried the wrong ID NAMESPACE

`writeCoachCardBlob` emitted the coach's equipped cards as bare i32 **CoachCard
template ids**. That field is a list of **SPELL** ids.

**Proof, end to end in the client.** The coach deserialises the blob with

    public void L(byte[] byArray) {          // aez_0.L, and Te.L identically
        this.bMQ = new ajO(je_1.Wa(), 8);
        this.bMQ.b(byArray);
    }

`je_1 extends azk`, whose `E(ByteBuffer)` reads an i32 and resolves it in its
castable map. That map is filled ONLY by `apS` - its line 55 is the sole
registration - which iterates the SPELL records (`co_1`, type 220) and registers
one `yp_2` per spell under the spell id. Cards are a different registry
altogether: `eh_2` loads type-100 records into `la_0.XJ()` as `xj`. There is no
second source that could rescue a card id.

**It was wrong in both directions.** An id that misses is dropped and the client
logs *"impossible d'ajouter l'item"*. An id that HITS is worse: it renders an
unrelated spell as a castable action card. Measured: 65 of the 325 cards with
`HasUsableAction` collide with a real spell id.

**Fix.** `writeCoachActionDeck` replaces it and emits spell ids only.
`filterCoachDeckSpellIDs` drops anything the client could not resolve, de-dupes,
and caps at the client's own capacity of 8 (`new ajO(je_1.Wa(), 8)`), so the
wrong-namespace bug cannot be reintroduced by accident.

**The deck is empty today, and that is the correct output, not a stub.** Nothing
in the shipped data grants a coach an action spell - see the Open entry "Which
spell ids belong in the coach action deck" for what is still missing and the
leads for finding it. Filling in that one source is the only remaining change;
everything downstream of it is already correct.

**Verified:** `unit` - TestCoachActionDeckNeverEmitsCardIDs (equipped cards whose
ids deliberately COLLIDE with real spells still produce an empty blob) and
TestFilterCoachDeckSpellIDs (unknown ids dropped, duplicates collapsed, capped at
8); both mutation-checked, the first against the old card-id behaviour.
`live` - a real fight still creates cleanly, placement phase renders, client log
error-free.

### B-087 - The AI walked through and onto sudden-death cells

Found by auditing the movement flood after the Killer-tile fix, on the theory
that if one lethal-cell class was unmodelled another might be. It was.

There are TWO movement paths and only one was guarded. A human's move goes
through `validateFightMove`, which rejects any path touching a cell sudden death
has removed. The AI calls `applyFighterMove` DIRECTLY on a path from
`reachableCells`, which checked `walkable` and occupancy but not `cellDestroyed` -
so the AI got a move no player could make.

Two consequences, and the second is the serious one:

- it could END its move on a destroyed cell, and `shrinkArena` kills whoever
  stands on one outright (HP to 0, no save, no resist);
- it could PATH THROUGH destroyed cells, which the client has flagged
  movement-blocked (`asF.bV`) - i.e. the server animating a walk the client
  believes is impossible.

**Fix.** `reachableCells` now skips destroyed cells, which is the shared source
for every AI movement behaviour and for the `/script` move command, and brings it
in line with what `validateFightMove` already enforced for players.

**Verified:** `unit` - TestPathfindAvoidsDestroyedCells asserts the cell is gone
as a destination AND that nothing routes through it, plus the end-to-end case
that the AI does not land on one. Mutation-checked: without the guard the flood
offers the destroyed cell and three separate paths route straight over it.

### B-086 - The AI froze: positioning and casting disagreed about what was castable

**Third self-inflicted bug from the repertoire work, found by watching a live
5v4 stall for eight rounds** - every demon on full AP and full MP, doing nothing
at all.

`moveIntoSpellRange` asked "could I cast anything from there?" using harm,
affordability and the targeting validator. `chooseAISpell` asked the same
question PLUS cooldown, cast-frequency limits and friendly fire. A spell that
passed the first and failed the second froze the fighter: it would not move,
believing it could already fire, and then would not cast.

**The live case was exact.** The Cra's spell 3 reaches 5-8 cells and its nearest
enemy stood at distance 8, so "it could fire". Its best spell (18, d38) was on
its 1-turn cooldown and everything else was range 2-5, so nothing was actually
castable - and it stood still, every turn.

**Fix.** `aiSpellCastableFrom` is now THE predicate, used by `chooseAISpell`
(from the caster's own cell) and `aiCanFireFrom` (from each candidate cell), so a
plan and the action that follows it cannot disagree. `aiFiringGap` also skips a
spell on cooldown or out of casts, so the AI does not walk toward one it could
not cast even from the perfect spot. `areaFighters` gained an explicit-origin
variant so the friendly-fire question can be asked about a cell not yet moved to.

Same class as making positioning consult the real targeting validator - and a
reminder that fixing that for RANGE only was half the job.

**Verified:** `live` - re-running the same challenge, all four demons now spend
their MP closing in (mp=0/3) where before they sat at mp=3/3 for eight rounds.
`unit` - TestAIDoesNotFreezeWhenTheOnlyInRangeSpellIsUncastable, mutation-checked
against the weaker positioning predicate.

Also in this pass: the AI **will not end a move on a Killer tile**
(`aiCellIsSuicide`). Watched live in the same fight - a Xelor closing on the
player's team stepped onto one and was dead at the start of its next turn,
because movement scoring only measured distance. Passing OVER one is still
allowed, since it fires at turn start; the Trap tile is deliberately not avoided,
as 10 HP is a cost to weigh rather than certain death, and refusing to path near
it would distort movement more than the damage is worth.
(`unit` - TestAIWillNotWalkOntoAKillerCell, mutation-checked.)

### B-085 - The AI would nuke its own team with area spells

**Self-inflicted, same review pass as B-084.** Friendly fire here is real and
authentic - `areaFighters` lands an area effect on allies, enemies and the caster
alike, and you are meant to position to spare your team. The AI had no idea it
existed, which did not matter while it cast one fixed spell chosen by CHEAPEST AP
(`pickBreedSpell`). Giving it a repertoire changed the selection to
HARDEST-HITTING, which systematically favours the area spells.

**Measured:** 15 of the damaging breed spells carry an area shape, and several
are the strongest their breed has - the Cra's best (spell 18, d38, affordable at
exactly 6 AP) is a size-3 T, and Iop spell 9 is shape 32767, i.e. *every living
fighter*, which damages the caster's whole team and the caster itself. Both were
in the live repertoires observed in the retail client.

**Fix.** `aiWouldHitOwnTeam` runs each candidate's HARMFUL effects through the
real `areaFighters` from the caster's actual cell (so the directional shapes
resolve exactly) and disqualifies the spell if any living same-team fighter,
including the caster, falls in the zone. `aiSpellHarmsEnemy` was split so
`aiEffectHarms` can ask the question per effect - a buff or heal riding along in
the same spell is not friendly fire.

The policy is deliberately strict: any friendly splash disqualifies the spell
rather than weighing ally damage against enemy damage. That is predictable and
cheap to reason about; the cost is declining a cast a human might judge worth it.

**Verified:** `unit` - TestAIAvoidsFriendlyFire (ally in the blast forces the
weaker single-target spell; ally moved clear or dead restores the AoE) and
TestAIWillNotNukeItself (a 32767 area is never cast, and no AP is spent trying).
Both mutation-checked by removing the gate, which reproduces the bug.

### B-084 - The AI would heal the enemy it was attacking

**Self-inflicted, caught by reviewing my own change before moving on.** Giving
the AI a spell repertoire (it previously cast one fixed spell) opened a hole:
`chooseAISpell` aims at the nearest OPPONENT and ranked purely by damage, so any
spell in the fighter's loadout became a candidate - including a heal or a buff.

**Why it was reachable, and not just theoretical.** Challenge demons are safe by
construction (`breedSpellRepertoire` filters to damaging spells), but they are
not the only AI-driven fighters. When a coach drops mid-fight,
`coachLeftFightOnActor` nils that team's session, and `isAIControlled` then hands
their fighters to the AI - carrying whatever spells the PLAYER equipped. The
targeting validator does not save us either: only 3 shipped spells carry an
enforced ally-only target mask (B-081), so a heal aimed at an enemy passes
validation and lands as a heal.

**Fix.** `aiSpellHarmsEnemy` gates every candidate in `chooseAISpell`,
`aiCanFireFrom` and `aiFiringGap`, so the AI neither casts nor walks into
position for a spell that would help its target. It is a deliberate WHITELIST of
harmful effect kinds (damage, leech, %HP, poison, AP/MP-scaled, instant death,
zone/line damage, AP/MP loss and steal, states, push/pull): an effect kind we do
not model reads as "not known to harm", so a new or unsupported effect is never
fired at an enemy on a guess.

**Verified:** `unit` - TestAINeverAimsSupportSpellsAtEnemies, mutation-checked by
removing the guard, which reproduces the bug exactly (the AI picks the heal and
spends all 6 AP on the enemy).

### B-083 - Fighter conditions were missing from CREATE_FIGHT (and the "effects" slot is not what the roadmap thought)

The fighter blob in CREATE_FIGHT ends with two id lists, and both were sent
empty. The roadmap filed this as "buff icons on reconnect/spectate - fill the
8000 effects/conditions slots". **The effects half of that is wrong**, and
filling it as planned would have caused a real bug rather than a missing icon.

**The effects list is the SPHERE BOARD.** `gn_0.b` reads
`[i16 count][i32 x count]` into a `jg_0` and hands it to
`gn_0.a(jg_0, vy_1, ib_2)`, which resolves each id through `akp_1` and then
**re-applies every effect of the object it finds**. `akp_1` is filled by
`dq_1`, whose `getName()` is `contentLoader.sphereBoard` - it is the
sphere-board node registry (types 900/901, 17 542 records, the largest
unimplemented system). Writing buff ids there would not draw an icon; the client
would look them up among sphere nodes and apply whatever shared the id. The slot
stays empty, now with that written next to it so the next person does not repeat
the assumption.

**The conditions list is real and is now filled.** It is
`[i16 count][i16 x count]`, read into `gn_0.uk` - the same container the
roster blob's evolution tail already fills through `et_2.uk` - and drawn on the
fighter's portrait. Sending it in CREATE_FIGHT is what keeps an injured fighter
looking injured after a reconnect or to a spectator, since both rebuild the fight
from that message. The client adds each id at a fixed level of 1
(`vy_1.b(id, (byte)1)`), so the remaining-fights counter has no slot here; it
travels in the roster blob, which does have a duration byte.

The count is capped at 255 for the same reason the roster writer caps it: a
corrupt row must not wrap the length and desynchronise the rest of the blob.

**Verification.** A fighter with two conditions puts both ids on the wire in
order; a fighter with none still produces a well-formed blob; and the existing
byte-exact layout test still passes, since the empty case is the same two bytes
as before. Mutation-checked by restoring the hardcoded empty list.

**Still open:** in-fight BUFF icons (the timed spell buffs) have no slot in this
message at all - the two lists here are sphere nodes and persistent conditions.
Restoring those on reconnect would need whatever per-effect message the client
uses during normal play, which is a separate piece of RE.

---

### B-082 - Matchmaking paired anyone with anyone

The queue took the first waiting coach in the same mode, so a 3000-strength
coach was matched against a 1000 instantly. There was no rating band and no
queue timeout.

Coaches are now paired only while their ladder-strength gap fits inside a band
that WIDENS with waiting - 300 points to start, +150 for each second either side
has been queued, both configurable (`world.match_band` /
`world.match_band_growth`, 0 disables the check entirely).

**The widening is why there is no separate queue timeout.** A fixed band on a
server with a handful of players online is a deadlock: the lone high-rated coach
waits forever and a timeout would only turn that into a failed search. Relaxing
the requirement instead means the search always terminates in a match rather than
in a giving-up. With the defaults, two coaches 1500 points apart meet after about
8 seconds, and two similarly-rated ones still pair instantly - fairness must not
add latency to the common case.

The band grows with the LONGER of the two waits, not the shorter: waiting earns
a wider net, and using the shorter wait would let a freshly-queued coach veto a
match for someone who had been waiting for minutes.

**These numbers are ours.** The client has no say in matchmaking - it sends a
search and is told about a match - so nothing here is recoverable from retail
data, exactly like the post-fight constants already flagged as honest limits.
Said so at the definition rather than leaving it to be assumed.

Default for existing embedders is unchanged: `NewMatchmaker` starts with the
band disabled and only `cmd/server` applies the configured values, so the e2e
harness (which builds Deps directly) pairs instantly as before.

**A flawed test caught itself.** The first version probed the same matchmaker
repeatedly as the clock advanced - but a failed Search ENQUEUES its searcher, so
the second and third probes paired with each other instead of with the waiting
coach, and the assertion failed for a reason that had nothing to do with the
band. Each probe now runs against a fresh queue. A second bug in the same test
was plain arithmetic: 11 x 150 is 1650, not 1800, so the "should now pair" case
was asserted one second too early. Both were mistakes in the test, and both were
worth fixing rather than loosening.

**Verification.** The exact boundary either side of the qualifying second,
instant pairing for close ratings, band 0 pairing anyone, the longer-wait rule,
and the pre-existing mode filter still refusing cross-mode pairs.
Mutation-checked by dropping the band check and by taking the shorter wait.

---

### B-081 - Spell-level target masks were decoded and never evaluated

`TargetMasks` (field 22) are CAST-level target conditions, distinct from the
per-effect conditions that filter an area's expanded targets. 202 of the 203
shipped spells carry one, but the client only APPLIES the check when the spell's
`EnforceTargetMasks` flag (field 19, `eF()`) is set - and exactly three
spells set it:

| spell | mask | meaning |
|---:|---|---|
| 468 | `4` (bit 2) | the target must be an **ally** |
| 83 | `36` (bits 2+5) | an ally **and** summoned - an allied summon |
| 449 | `1<<62` | the target must be a ground **effect area** |

The first two are plain per-effect condition bits, so the existing evaluator
decides them with no new machinery. The third is not: bit 62 lives in
`aLc.n(ack_1)`, which asks whether the target is a live trap/glyph rather than
a fighter - a targeting MODE this server does not model, since casts here aim at
a cell or the fighter standing on it, never at a ground area.

**A mask carrying any bit this evaluator cannot represent is skipped whole.**
Judging spell 449 with the fighter evaluator would reject every cast of it,
which is a worse outcome than not enforcing a rule at all, and half-enforcing a
mixed mask is not the rule either.

**The mutation test caught a worthless assertion here.** The first version of
the "unrepresentable bit" test used spell 449's own mask, and it passed with the
escape hatch REMOVED - because `targetConditionPasses` already ignores bits it
does not know, so a pure-unknown mask is permissive either way. The test proved
nothing. The case that actually exercises the escape is a MIXED mask (one
decidable bit plus one that is not), where partial enforcement and skipping
diverge; the test now asserts on that and fails when the escape is disabled.

**MaxActive is deliberately still not enforced.** Six spells carry it (8, 15,
46, 141, 167, 173 - buff spells, not summons: poison, AP boosts, all-element
damage%). The field's SCOPE is the unknown - whether the cap counts live
instances per caster, per target, or across the whole fight - and the client
side of it is a runtime counter that `apS` passes into `yp_2` rather than
anything readable from the record. Implementing a cap against a guessed scope
would change which casts are legal, so it stays decoded and documented.

**Verification.** Ally-only and ally+summoned masks (accepting the right target
and refusing the wrong one), the `EnforceTargetMasks` flag acting as the gate,
the mixed-mask escape, and a real-data canary pinning the three enforced spells
and their exact masks so a future data set that enforces more of them fails
loudly. Mutation-checked by removing the enforcement call and by disabling the
escape.

---

### B-080 - AoE shape 8 was unimplemented and the cross ignored two of its three arities

**Shape 8 (`acg_0`, "forme a base de points")** fell through to a single cell.
It is an explicit list of `(dx,dy)` offsets from the centre: `acg_0.a(int[])`
rejects an odd-length array outright and reads consecutive pairs, and its
parameter labels name them x1,y1,x2,y2... ("Liste de N points").

It is **directional**, which is the part worth getting right. The client's shapes
carry a symmetry flag `fi()` - true for the circle and the point, which look
the same whichever way you face, and FALSE for the T, the inverted T and this
one. The authored offsets sit in a fixed reference frame, which the labels state
outright: "prendre l'axe sud-est pour construire". So the list is rotated by the
caster->centre cardinal step exactly as the T shapes rotate their stem, and a
caster standing on the centre degrades to the centre cell, the same degradation
the T shapes already use. Malformed (odd-length) lists degrade rather than throw
the way `acg_0` does - this is attacker-reachable data.

One shipped row uses it: spell 469's action-125 effect, size `[0 0 -1 0]`.

**The cross (shape 3, `qv`)** applied `size[0]` to all four arms with a note
calling the other forms a rare approximation. `qv.a(int[])` in fact accepts
exactly 1, 2 or 4 lengths and rejects anything else, and the arm-to-axis mapping
is legible from the cell list it builds and confirmed by its own debug name
`"cross-h"+aeD+"b"+aeF+"-g"+aeG+"d"+aeE`:

    aeD = haut   -> (+n, 0)      1 param : all four arms alike
    aeF = bas    -> (-n, 0)      2 params: face-a-soi (+-x), then cote (+-y)
    aeG = gauche -> (0, -n)      4 params: haut, bas, gauche, droite
    aeE = droite -> (0, +n)

The cross is NOT directional (`qv.fi()` returns true), so the arms stay on the
grid axes - our existing non-directional handling was right about that much.

**Scope, honestly.** A dump of every shape/size combination across the spell AND
static-effect tables shows shape 3 carries exactly one size in every shipped row,
so the 2-/4-param work is forward safety rather than a live fix. It still beats
the status quo: the old code would have produced a WRONG footprint for those
forms rather than an obviously missing one. Shapes 7 and 10 exist in the client's
`zg_1` table and remain unimplemented - nothing ships them either.

**Verification.** Arity tables for all three cross forms with per-axis inside and
outside cases; an exhaustive equivalence test over r=0..3 and dx,dy=-4..4 proving
the 1-param form - the only one any record uses - behaves exactly as the old
implementation; and shape-8 coverage of the identity rotation, a quarter turn,
the zero-direction degradation and a malformed list. Mutation-checked by
unrouting shape 8 and by dropping the 4-param cross branch.

---

### B-079 - The "triggeree en zone" effect family was 1 of 6 implemented

Action 177 ("Perte de points de mouvement triggeree en zone") was implemented and
its five siblings were logged as unresolved no-ops. The client's `mh_2` table
shows they are ONE shape with six members:

| id | class | label |
|---:|---|---|
| 165 | `aez_1(fv_1.bam)` | Perte de points de vie **feu** triggeree en zone |
| 166 | `aez_1(fv_1.ban)` | ... **eau** |
| 167 | `aez_1(fv_1.bao)` | ... **air** |
| 168 | `aez_1(fv_1.bap)` | ... **terre** |
| 169 | `MM()`            | Perte de points d'**action** triggeree en zone |
| 177 | `vn_1()`          | Perte de points de **mouvement** triggeree en zone |

All six are the spell's own zone centred on the CASTER with the caster excluded,
so 169 is literally 177's body with AP substituted for MP (both now share
`applyZoneResourceLoss`), and 165-168 differ only by the element
`damageElement` returns.

The roadmap listed 165/166/169 - the three with shipped rows - and missed 167 and
168 entirely. They have no rows today, but they are the same class with a
different element constant, so including them costs nothing and omitting them
would leave the identical silent hole the moment data used them. An unimplemented
action id is a silent no-op, which is the failure mode worth designing against.

The elemental variants resolve through the ordinary pipeline
(`computeElementalDamage` -> `applyDamageRebound` -> `applyHPDelta`) rather
than subtracting HP, so resistance, rebound and damage transfer all apply, and
the magnitude is rolled PER VICTIM to match every other multi-target path here.

**Effect-row coverage: 502/533 -> 505/533 (94.2 % -> 94.7 %).** Remaining
unresolved: 9 action ids over 28 rows, down from 12 over 31.

**Verification.** Zone AP loss (drains in-zone enemies, spares out-of-zone ones
and the caster, does not touch MP), its clamp at 0, all four elemental variants
(footprint + the element `damageElement` returns), and a resistance case
proving it goes through the damage pipeline. Mutation-checked by unmapping 169
and by removing 165 from the fire branch.

---

### B-078 - Effective AP/MP were never derived, and StringU8 could crash every client

The last two Tier 0 items.

**1. Rooted/petrified fighters reported and SPENT resources they do not have.**
The client has two characteristic getters: `gn_0.c` returns the raw stored
value, `gn_0.d` returns the EFFECTIVE one, and `d` zeroes it:

    d(Lr.bqz /*MP*/): 0 if the fighter has avx_0.dew (petrified) OR dex (rooted)
    d(Lr.bqy /*AP*/): 0 if the fighter has avx_0.dew (petrified)

This server had no equivalent, which is not merely a cosmetic gauge issue as the
roadmap assumed. "Dommages par PM possede" scales off exactly this value, so in
the retail client a ROOTED caster's MP-scaled spell deals **nothing**, while we
were reading the raw MP and hitting at full strength.

`effectiveAP`/`effectiveMP` now mirror `gn_0.d` and are used by the
scaled-damage effect and by the AP/MP deltas in CREATE_FIGHT (so a resume or
spectate rebuilds the same gauges the client would derive). They are DERIVED,
not stored - refillFighter deliberately still refills the raw value, exactly as
the client keeps the raw characteristic, so a root that ends restores mobility
with nothing to restore. Rooted deliberately does not zero AP: a rooted fighter
can still cast.

**2. `Writer.StringU8` documented a 127-byte limit and enforced nothing.**
Several 2.70 decoders read that prefix as a SIGNED byte, so 128 bytes present a
length of -128 and crash the client's reader. That made it a **remote
client-crash vector**, not a style rule: the channel-chat path clamps the
message it echoes, but NOT the channel NAME, and both come straight off the
wire - so one client could crash every other client that received the message.

The writer now truncates. Enforcing it at the single choke point makes every
call site safe by construction, and the limit is applied to the ENCODED bytes
because that is what the prefix counts (the wire charset is cp1252, single-byte,
so cutting bytes cannot split a character). The existing call-site clamps are
left in place as belt and braces.

**Verification.** A table over 0/1/126/127/128/255/1000 bytes asserting the
prefix never exceeds 127 and never reads negative when signed, plus an
accented-text case proving the limit counts cp1252 bytes rather than the Go
string's UTF-8 length. For the getters: a state table (rooted zeroes MP only,
petrified zeroes both, unrelated states change nothing, raw values never
mutated) and a behavioural test that a rooted caster's MP-scaled spell deals
zero while an unrooted one deals damage. Both mutation-checked.

---

### B-077 - Dispel stripped summons of what they ARE, and Standing was thrown away

Two more Tier 0 items, unrelated except that both are one-line omissions with
outsized consequences.

**1. Dispel cleared the whole state map.** The buff half of applyDispel always
kept permanent entries ("dispel leaves permanent enchantments"); the state half
deleted every key unconditionally. Summon innate properties are applied at spawn
as INFINITE states - of the 53 shipped creatures **22 are rooted, 21 anchored,
18 stabilised, 15 intransposable** - so a single dispel made a stationary summon
mobile, or a carry-proof one carryable, for the rest of the fight. Those are not
enchantments to undo; they are what the creature IS.

Infinite states (>= infiniteStateTurns) now survive, which also makes dispel
agree with `tickStates`, which has always refused to age them, and with the
buff loop beside it.

**The client's model is richer, and is the eventual general fix.** Fighter
properties (rooted/anchored/intransposable/...) live in a REFERENCE-COUNTED
store: `Kt.g()` increments, `Kt.h()` decrements and removes at zero,
`c()` reads the count and `b()` is "count != 0" - and `gn_0` really does
read the count (`this.c(avx_0.deu) != 0`). So a summon's innate root and a
spell's root coexist as count 2, and removing either leaves the other. This
server's `States` map holds remaining TURNS, conflating "how long" with "how
many sources" - the same shortcut behind the buff-stacking gap. Keeping infinite
states is correct for every case the shipped data produces; counting sources
would additionally fix overlapping FINITE ones. Recorded rather than attempted,
because it touches skip-turn charges, ageing and removal-by-source-id.

**2. `Coach.Standing` was computed, then thrown away three different ways.**
Standing is the coach's EVOLUTION experience - a different axis from Strength,
the ladder rating - and the client derives the evolution LEVEL from it and pops
its level-up dialog when it changes. The post-fight META already computed and
applied it (`t.Coach.Standing += standing`, with the level transition logged),
but:

- `CoachRepo.Save`'s field map omitted the column, so every point died on
  relog. The column existed and was migrated; it was simply never written.
- The 2052 coach descriptor hardcoded `w.I32(0)`, so the coach's OWN level
  read as 1 however much it had earned.
- The 4096 actor-spawn record hardcoded `w.I32(0)` too, so every other coach
  visible in the world also rendered as level 1.

All three now carry the real value. **No wire layout changed** - both sites
already wrote an i32 in the right place, they just wrote a zero into it - so
this is a value fix, not a protocol change.

**Verification.** Store round-trip through Save/Get; byte-offset assertions on
both wire records; dispel tests covering a summon's four innate properties, an
infinite state on an ordinary fighter (a Masqueraider mask), and that a genuine
finite enchantment is still stripped. All five mutation-checked: reverting each
of the three Standing sites, and restoring the blanket state clear, each fail
the tests that claim to cover them.

---

### B-076 - Forced displacement walked straight past every trap

checkEffectAreasMove had exactly ONE caller - the voluntary walk path - so push,
pull, teleport, swap and throw all repositioned fighters with no trap check at
all. Shoving an enemy onto a glyph is a core tactic of this game, and its
absence also made every trap trivially avoidable: any displacement crossed them
for free.

**The client settles it, and gives the full trigger model.** `he_1.a(fromX,
fromY, fromZ, toX, toY, toZ, fighter)` partitions the live areas by whether
they contain the FROM cell and the TO cell:

| fires | when | meaning |
|---:|---|---|
| **10001** | in TO, not in FROM | **entered** the area |
| **10008** | in TO and in FROM | **stayed inside** it |
| **10002** | in FROM, not in TO | **left** it |

It is a pure position-change notification - nothing in it cares HOW the fighter
moved - and **eight distinct effect classes call it**, including `go`
(teleport, the class our applyTeleport comment already cited) and `aox_1`
(swap, the class our applySwap comment already cited, which calls it ONCE PER
SWAPPED FIGHTER).

The fix was correspondingly small, because checkEffectAreasMove already
implements the 10001 half of that partition exactly - `contains(arrival) &&
!contains(start)` - and its own doc comment already claimed it was "called per
step by applyFighterMove (and any server-driven reposition)". The repositions
were simply never wired. Teleport, swap (both fighters), push/pull and throw now
call it. Carry is deliberately excluded: a carried fighter is stacked on its
carrier and explicitly holds no ground in this server's model.

The push path checks HP after collision damage, so a fighter the impact already
killed does not also spring the trap it landed on.

**Trigger ids this server still ignores** (dumped from all 16 shipped type-210
templates): **10008** stayed-inside, **10002** left, **10003** (the ONLY trigger
on template 1016 "mauvaisOeil", so that trap can never fire here), and **10006**
(templates 2 and 1015). Three templates - 1017, 1018, 1019 - carry an EMPTY
trigger array and so fire from nothing. Their meanings are now recorded in
DATA-COVERAGE rather than left as a blank.

**A latent panic fell out of this.** The new "a shove springs a lethal trap"
test was the first trap test able to reach endFight - previously only a
voluntary walk could spring a trap, and the walker was never the last enemy in
those fixtures. endFight dereferences `deps.Log` unguarded, so a fixture
without a logger panicked the fight actor. Production always sets Log, so this
was reachable only from tests; the fixture now provides one rather than papering
over it with nil checks that would hide the next fixture mistake.

**Verification.** One test per displacement path (teleport, swap for both
fighters, push, throw), each mutation-checked independently by removing that one
hook. Plus the half of the partition that is easy to get wrong: a fighter shoved
from one cell to another INSIDE the same area has not entered it, so nothing
fires - that is the client's 10008 case, which this server does not implement,
and the wrong reading would have re-fired the walk-on effect.

---

### B-075 - Two anti-cheat holes: placement was a free teleport, casts skipped spell ownership

Both were Tier 0 items on the roadmap. Both were exploitable by a forged packet
from an otherwise ordinary client session.

**1. MoveToPlacementReq (8021) had no phase guard and no cell validation.** The
handler checked only that the fighter belonged to the requesting coach, then
assigned the coordinates verbatim. So the placement opcode worked at ANY time,
including mid-fight, which made it a free teleport: no MP cost, ignoring
rooting, tackle, walk-on traps and line of sight. It also accepted any
coordinate at all - off-map, into scenery or void, onto a cell sudden death had
destroyed, onto the ENEMY's starting area, or onto a cell another fighter
already occupied. That last one silently stacks two fighters on one cell and
corrupts targeting, tackle and LoS for the rest of the fight.

Now gated to PhasePlacement, and the cell must be one of the fighter's OWN
side's start cells - the same set the server seeded the team from, and the only
set the client ever offers - and free. The phase is read on the fight ACTOR
rather than in the handler, because the phase can advance while the message sits
in the mailbox. Altitude stays unvalidated, matching the movement path: (x,y) is
the unit of placement and the client owns per-cell z.

**2. castSpellByFighter never checked that the caster knows the spell.** It
resolved the id straight out of the 203-entry table, so a forged 8109 could fire
any spell in the game from any fighter. The equipment path (8107) has always
checked ownership via fighterHasEquipped; this closes the same hole on the spell
path, in the same place and style (deep, not in the handler - castSpellByFighter
already re-checks isCurrentTurn even though the handler did, and that
defence-in-depth is deliberate).

The check has to accept TWO sources, and getting this wrong would have been
worse than the hole. A real coach fighter casts what it has equipped
(Fighter.Spells, preloaded by the store on both fighter-load paths). But a
SERVER-DRIVEN fighter casts its single SummonSpellID - and that covers not only
summoned creatures but every AI opponent in PvE: challenge demons are built with
a domain.Fighter for breed and stats and an EMPTY spell list, their one spell
living in SummonSpellID. A naive "must be in Fighter.Spells" check would have
muted every demon in the game and broken all 39 challenges.

**Two e2e tests were codifying the defects.**

- TestPlacementMove drove the fight to the ACTION phase and placed from there,
  onto the arbitrary cell (2,9), with a comment stating outright that "the
  placement handler has no phase guard". Rewritten to place during placement,
  onto a legal start cell, plus a new TestPlacementRejectsIllegalCellsAndPhases
  covering off-map, outside-any-start-area, scenery, the enemy's start cell, and
  8021 after the phase has passed.
- TestCombatSpellDamage cast spell id 0 from the synthesized "Champion"
  placeholder - the fallback fighter the server invents when a coach has none -
  which owns nothing. It now creates real fighters that own the spell they cast,
  which is what a real client does.

Fixing them exposed that the combat e2e fixtures never created fighters at all:
every one of those fights ran on the placeholder. buildFighterBlob now takes
spell ids, and matchIntoFight lets a test prepare its coaches and stop before
any phase gate.

**Verification.** Unit tests cover both legitimate spell sources and the demon
case explicitly; e2e covers legal placement, four illegal cells, wrong-phase
placement, and a forged cast that must neither damage nor spend AP. Every
assertion mutation-checked: removing the phase gate, removing the cell
validation and removing the ownership check each fail the tests that claim to
cover them. The e2e suite was run three times end to end for flakiness, since a
shared helper used by nine tests changed.

---

### B-074 - np_1 rule types 12 and 14 were decoded but nothing consumed them
B-071/B-073 decoded every 
p_1 element, including the three type-12 fight-start
effects and the nine type-14 victory conditions. Both were then carried, inert:
12 of the 39 shipped challenges were playing by rules the server had read and
ignored. The "Defi du temps" ("time challenge") demons in particular had NO win
condition at all - the only way to finish one was to eliminate the whole demon
team, which is not what the challenge asks for.

**Type 14 - alternative victory conditions.** Read content.55 first, as the
method demands, and it stops at entry 1: the client cannot even DISPLAY a type-4
condition. It goes further than that. wi_0.a(mv_1) hands the decoded condition
to the fight via mv_1.b(mp_2), and **mv_1.b is an empty method**; the
three-argument evaluator (mv_1, yg_0, yg_0) has **no call site anywhere in the
client**; and h()/i()/j() (is_necessary, victory_points, affected_team)
have no callers either. Retail arbitrated victory conditions entirely
server-side and the client kept the machinery as dead reference.

What IS recoverable is the CONDITION, because each of the four mp_2 subclasses
is a one-line body. qk_1 names them and jm_0 - the only subtype the shipped
data uses - is simply:

`java
return mv_12.ZB().JI() > this.JI[0];
`

JI() returns NC, incremented in cn_0.dm() on each timeline wrap: the same
table-turn counter this server calls 	ableTurn. So subtype 4 is "the round
counter passed N", strictly greater.

Three independent things agree on the reading, which is what makes it safe:
qk_1 labels subtype 4 **"Atteindre un tour donne"**; the nine holders are
challenge 14 plus **"Defi du temps : Poison / Violence / Pont mortel / Kawotte /
Lac / Quai des brumes / Altruisme"** and its finale - literally *time*
challenges; and the parameter is 20 or 30 turns. Survive to the turn and you
win. None of them touches sudden death, so the default collapse at turn 15 still
lands first and the last 5-15 rounds are fought on a shrinking arena. That is
the mechanic, not an accident.

**The arbitration is ours and is labelled as such.** ffected_team is the only
field that could name a winner, it is 0 on all nine, and the client never reads
it; we read it as the team index it is named for. This server builds a PvE
challenge with the coach as team 0, so the shipped value makes the coach win by
surviving. ictory_points (all 0) and is_necessary (all true) would matter
only for scoring several partial conditions, so they are carried and
deliberately unused rather than guessed at. Subtypes 1/2/3 are documented from
their client bodies but NOT implemented - no shipped record uses them, so there
would be nothing to validate against.

checkFightEnd gained an explicit decided-winner path, because a fight can now
end with **both teams still standing** and a survivor count cannot express that.
Nobody is killed to make the result work: downing the loser would have been the
easy shortcut and would have silently destroyed evolution fighters, whose deaths
are driven by HP rather than by the result.

**Type 12 - fight-start effects.** Applied through the same path round event
cards already use (pplyRoundEvent): each fighter is both caster and target so
a percentage scales off its own stats, and every effect is still gated by its own
target conditions.

That last part turned out to be the real work. The three effects carry target
mask **1024**, and B-073 recorded that as needing "the client's aLc evaluator, a
separate open item". That was over-cautious - 	arget_conditions.go already IS
a port of Lc.a; it was simply missing bits 512/1024, which are one line each:

`java
(0x200 & c) != 0 && (!(t instanceof gn_0) || t.NY().lV() != xq.axE.lV())  -> reject
(0x400 & c) != 0 && (!(t instanceof gn_0) || t.NY().lV() == xq.axE.lV())  -> reject
`

xq.axE is breed id **0**, the stat-less pseudo-breed listed between xD(-1)
and the 14 real breeds. So 512 = "is a creature", 1024 = "is a real player-breed
fighter". **An unimplemented condition bit is silently permissive**, so without
them the +40% dodge would also have landed on every summon - the mask is there
precisely to stop that.

While confirming which class our validator ports: the file credited **ap.a**,
but ap is a genuinely different validator (its low bits are is/is-not pairs
and its 512+ bits are count thresholds). Our port matches Lc.a bit for bit.
The wrong attribution is what sent B-073 looking for a second evaluator that did
not need to exist; corrected.

**Verification.** Two real-data canaries assert the shipped shape (9 conditions,
all subtype 4, param 20 or 30, (true, 0, 0); 3 start effects, action 122,
params [40], targets [1024]) so a future field-order slip fails loudly instead of
silently disabling the mechanic. Behavioural tests cover the strictly-greater
boundary, a fight ending with both teams alive, the winner coming from
ffected_team rather than a hardcoded side, an unknown team being skipped, and
fights without conditions being untouched. Each was mutation-checked: > to
>= and dropping the ffected_team read each fail multiple tests, and
removing the 1024 check reproduces the summon-buff bug.

**Not done, deliberately:** subtypes 1/2/3, ictory_points scoring, and the
np_1 rules that still have no consumer (budget, roster limits, spell/equipment
and class bans, prices, arena choice, event lists).

---

### B-073 · Challenges 29/30/31 stopped short on an inline, unlength-prefixed effect
B-071 left three challenge records deliberately unfinished: each carries an `np_1`
type-12 parameter ("Lance un effet sur tous les combattants à la création du combat")
whose trailing `Ht` effect is **inline with no length prefix**. Every other effect on
this format is length-prefixed — `decodeEffectList` slices the blob first — so the
existing decoder could stop reading early with no consequence. An inline effect can
only be passed by parsing it **exactly**: a byte too few or too many desynchronises
every field after it.

**The fix was one field short of free.** `decodeEffectBlob` already read through field
19 (the i64 target masks); the full `Ht` record is just **two trailing flag bytes**
longer (`beL`/`beM`, getters `Tj`/`Tk`, which the client hands straight to its runtime
effect constructor and whose meaning is not established). Reading those two makes the
decoder self-delimiting, so it was split into `decodeEffectCursor` (consumes a
caller-owned cursor exactly) with `decodeEffectBlob` as a thin wrapper. Nothing about
the length-prefixed path changes.

**All 39 challenge records now decode to zero residual bytes** (was 36).

**The decoded values are the real evidence this is byte-exact.** All three effects come
out as:

```
container "FIGHT_PARAMETER"   action 122   params [40]   duration [63 0]
```

Every one of those is independently meaningful: `FIGHT_PARAMETER` is a container type
we had not seen, and it is exactly what a rule applied at fight creation should say;
action **122** is the dodge-GAIN action from the same `mh_2` table as the tackle stats
(B-063); `[63 0]` is the same infinite-duration marker the `FIGHTER_CONDITION` rows
use. A misaligned read does not land on four coherent values at once. So challenges
29/30/31 each grant **+40% dodge to every fighter for the whole fight**.

**The guard test did its job.** `TestChallengeTailReal` pinned those three ids with a
message saying "an inline-Ht parser must have landed; move it out of the blocked set" —
and that is exactly how the change announced itself, failing loudly on all three the
moment the parser worked. The blocked set is gone and the decoded effect values are now
asserted in its place.

**Still NOT applied.** The effect is decoded and carried, not executed: rule type 12
would need the fight-start application path, and its target mask (`[1024]`) needs the
client's `aLc` evaluator, which is a separate open item. Half-wiring it against a mask
I cannot evaluate would be worse than leaving it inert and documented.

**Verified:** `unit` — 39/39 challenges consumed exactly; the three inline effects
asserted field by field; a synthetic well-formed inline effect consumed to the byte
(a sentinel placed immediately after it must still read back, which is the assertion
that actually proves exactness); and a truncated inline effect failing the decode
rather than returning junk.

### B-072 · The turn clock and sudden-death turn were hardcoded — and package-global
Two fight rules the data actually specifies were invented constants in this server:
`turnClock = 30s` and `suddenDeathTurn = 15`. Both are `np_1` rule types (10 and 11,
decoded in B-071), so the data was there to read as soon as the element layout was.

**The second half of the bug is worse than the first.** Both were **package-level**,
so a fight that changed either would have changed it for *every other fight in the
process*. Nothing set them at runtime yet, so it had never fired — but wiring the
ruleset without noticing would have introduced a genuine cross-fight leak on the very
first challenge that customises a turn.

**Fix.** A per-`Fight` `Rules` struct resolved from the fight's parameter list at
creation, with `turnClockFor()` / `suddenDeathTurnFor()` accessors that fall back to
the package defaults. The existing test hooks keep working because the defaults are
what `defaultFightRules()` reads.

**THE TIMING RULES ARE DELTAS — my first version of this got that wrong.** I initially
applied both as absolute values. The client's own label table settles it:

```
content.54.10 = "[£1] secondes en {[+1]?plus:moins} pour jouer chaque combattant"
content.54.11 = "La mort subite a lieu [£1] tours plus {[+1]?tard:tôt}"
```

"N seconds **more/less**", "sudden death happens N turns **later/earlier**". The
`{[+1]?…:…}` construct selects wording from the SIGN, which only makes sense for a
signed offset. `suddendeath.go` had even recorded it already — "tournament rule cards
shift it by ±5/±10 turns" — and I did not read my own comment carefully enough the
first time. `content.54.*` is the authoritative per-rule semantics table and should be
consulted before implementing any further rule.

So challenge 46 ("Tuto de Baan") does not set a one-hour turn; it **adds** an hour to
the default. For a tutorial that must not time out on a player who is reading, the
effect is the same, which is exactly why the error would have been easy to miss.

**What the shipped data uses.** Of the 39 challenges, exactly **one** carries a
turn-duration rule (challenge 46). **No** challenge sets a sudden-death delta — that is
presumably tournament-side, and the mechanism is now in place for when those are
decoded. **Five** carry a bonus-cell multiplier (×2, ×2, ×5, ×10), now applied. Type
1000 ("Pas de limite de budget", used by challenge 12) is named but not enforced.

**Robustness choices:** a delta that would drive the value non-positive is IGNORED, not
applied — a zero-length turn clock would end every turn instantly, and a sudden-death
turn of 0 would shrink the arena from turn one. A multiplier of 0 or absent behaves as
×1, never ×0, which would silently disable every bonus cell.

**The multiplier covers the BENEFICIAL tiles only** — the five stat buffs and the
healing heart. The killer cell has no magnitude to scale, and the trap is a *piège*, a
malus: scaling it ×10 under a rule the client advertises as a *bonus* would be a
perverse reading of "il bénéficie de ses effets… lui apporter des PA, de la
résistance". Including the healing heart is a judgement call, flagged as such in the
code — nothing in the data distinguishes it either way.

**Verified:** `unit` — deltas in both directions (a negative one shortening the clock
and moving sudden death earlier); over-large negative deltas being ignored rather than
producing a zero clock; the multiplier being absolute while the timings are relative;
an unimplemented rule type inert rather than fatal; per-fight isolation (a customised
and a normal fight side by side, plus a zero-valued `Fight` still playable); and the
multiplier applied at ×2/×5/×10 with ×0 and "no rules" both behaving as ×1.

### B-071 · The `np_1` element layout — the last decode blocker on three records
`np_1` was the one unknown standing between us and the tail of the coach-card
record (fields 19-26), the tail of the challenge record, and parts of the
tournament tables. Its layout turned out to be plainly readable in
`np_1.k(ByteBuffer)`, cross-checkable against both its writer `cd()` and its size
function `nj()`:

```
[i32 type][i32 id][i32 parentId][u8 n][i32 × n params][i16 effectVersion]
  if effectVersion != 0: [i32 effectId][Ht blob, inline, NO length prefix]
```

**Two traps, both different from every other effect list in this format:** the
trailing effect is written **version first, then id** (the reverse of
`decodeEffectList`'s `[id][ver][len]`), and it has **no length prefix**.

**And `np_1` is polymorphic.** Exactly one of its 30-odd subclasses overrides the
read: type **14, "Condition de victoire"** (`wi_0`), which has no param array and
no effect, just `[i32 id][i32 parentId][mp_2 blob]`. Decoding it generically reads
the `mp_2`'s leading `i16` as a param count and desynchronises everything after —
which is precisely what happened to challenges 14 and 37..44, whose "effect
version" came out as the nonsense value 1024. That 1024 is the tell: it is an
`mp_2` type field being read one field too early.

**What the type enum turns out to be.** `ajr_2` names all 32 low types, and they
are a **fight-ruleset system**: budget, min/max fighters, banned or allowed
spells and equipment, class limits and prices, arena choice, event-list choice —
and notably **type 10 "modifies each fighter's turn duration in milliseconds"**
and **type 11 "modifies the sudden-death start turn"**, both of which this server
currently hardcodes. The 900+ block is per-breed spell parameters. Recorded in
`DATA-COVERAGE.md`; nothing is wired to it yet.

**Results.**
- Coach cards: **26/26 fields**, and all **907 records consume to exactly zero
  residual bytes**. A format that ends precisely where the decoder stops, 907
  times out of 907, is the strongest evidence a layout is right.
- Challenges: **36 of 39** records exact. The other three (29/30/31) each carry a
  type-12 parameter ("Lance un effet sur tous les combattants à la création du
  combat") whose inline `Ht` cannot be skipped without a full effect parser;
  `decodeParameters` stops there deliberately rather than desynchronise, and the
  test pins those three by id so the day someone writes that parser, it fails and
  tells them to move them out of the blocked set.

**An independent cross-check fell out of it:** exactly **7 cards** carry a pet
model id, and the client ships exactly **7 pet descriptions**
(`content.24.71/75/80/88/92/99/103`, "Ce familier Augmente les drops dans tous les
modes de jeu"). Two unrelated sources agreeing on 7 is worth more than either
alone.

Field names came from the few unobfuscated fragments in the jar: the fusion
laboratory's Xulor field names give `tz` = **labPower** and `tA` = **quality**,
and the method `setFighterColorIndex` gives `tD` = colour slot (0 hair / 1 skin /
2 eyes) and `tE` = palette index. `tB` is the pet model id — `aez_0.aQv()` spawns
one visual instance per owned pet from it. `tw`/`tx` are handed to the runtime
card object and never read again: dead in the client, decoded here only so the
record round-trips.

**Verified:** `unit` — zero-residual over all 907 coach cards and 36/39
challenges; the element decoded against the client's own size functions
(`np_1.nj()` and `wi_0.nj() = 12 + mp_2.nj()`); the inline-effect guard; and the
victory-condition case built from the exact byte pattern (type `1024`) that used
to be misread.

### B-070 · You could not create an evolution fighter at all
Recruiting from the **Évolution** tab produced a CLASSIC fighter. The evolution roster
could therefore only ever contain fighters that got there some other way, its
substitutes bench was permanently empty, and the tab refused to start a match.

The client says which roster it means, in the `type` byte of the `et_2` blob it sends
with FIGHTER_CREATE (1 = classic, 2 = evolution). We decoded it —
`fb.Type = typ` — and then **never read it again**: the same "decoded but dead"
pattern behind most of this session's bugs. `buildFighter` never set `State`, so every
fighter defaulted to `FighterStateTitular`, `IsEvolution()` answered false, and the
fighter was serialized back as type 1 with no evolution tail.

**Fix.** A persisted `Fighter.Evolution` flag, set from the blob's type byte.

**It is deliberately separate from `State`,** and that distinction is the actual bug
underneath the bug. `IsEvolution()` used to be `State != Titular`, which conflates two
different things: the client buckets an evolution roster as **line-up = state 0 *or*
2**, bench = 1, graveyard = 3 (`xz_0`). So state 0 means "in the line-up", *not* "not
an evolution fighter" — and any correct implementation of recruitment was impossible
while the two were the same field. `IsEvolution()` now returns the flag, falling back
to the old state test so rows written before the column existed (benched, dead or
interred fighters, which could only have got there through evolution play) keep
working.

**Verified:** `unit` — a type-2 blob yields `Evolution=true` and a type-1 blob false,
both starting in the line-up; the legacy fallback across all four non-titular states;
and that the flag reaches the wire (type byte 2 *and* a longer blob, because the tail
is what files the fighter into the client's roster). `live` — a fighter recruited on
the Évolution tab came back `evolution=true state=0`, appeared in the roster with its
fatigue/morale bars and a 600/6000 budget, and the tab then started a real fight.

### B-069 · Challenge rewards were granted but never shown, and the card blobs were documented backwards
Two problems in the same three lines of END_FIGHT.

**1. The won-cards blob was hardcoded empty.** `awardChallengeRewards` grants the
cards and pushes the inventory, but 8300 reported none — so the results panel's
**"Cartes gagnées"** section was blank on the very fight that paid out. The player got
the cards and was told nothing.

**2. The two blobs were commented in the wrong order.** Our source said
`// lost cards blob` first, `// won cards blob` second. Traced through the client, it
is the other way round:

```java
YP.c(blob, bl2)   ->  arrayList = bl2 ? this.by : this.bz
   first blob is read with bl2 = false   ->  bz
   second blob is read with bl2 = true   ->  by
ajo_1:  bz -> "fight.wonCards"      by -> "fight.lostCards"
```

So the **first blob is WON**. Both were zero, so nothing was broken *yet* — but the
next person to fill them in from those comments would have shown players their
winnings in the "Cartes perdues" column, and the packet would have looked perfectly
well-formed while doing it. Wrong comments on a byte-oriented protocol are latent
bugs; this one is now pinned by a test rather than a comment.

**Format** (`YP.c`): `[u16 blobLen][u8 groupCount]{[u8 n]{[i32 cardId]}}`. An empty
list writes a zero *length*, not an empty group — the client only parses the blob when
the length is > 0. The granted list is expanded by quantity, because the panel shows
one icon per copy won rather than one per distinct template.

**Verified:** `unit` — the blob is decoded back field by field from a real
`buildEndFightFull` frame, asserting the WON list is the FIRST one (the test fails
loudly with "won cards must go first" if the order is ever flipped back), and that an
empty fight writes two zero lengths. `live` — challenge 11 paid out card 186 on a first
clear and the retail client parsed the enlarged frame with an empty `output.log`.

**Visually confirmed** (after B-070 made the Évolution tab usable): challenge 12 paid
out cards 183/188/192 on a first clear and the results panel rendered **three card
images under "Cartes gagnées"**, where it had always been blank.

### B-068 · Every string on the wire was the wrong charset
The server wrote and read UTF-8. The client does neither. Both of its string paths
name **no charset at all**:

```java
read:   this.setName(new String(byArray));      // aez_0.V, gn_0, sw_1, ta_0, axD…
write:  byte[] byArray = this.bY.getBytes();    // acS, aey_0, afy_2…
```

Both take the JVM **platform default**, and the shipped client runs on the bundled
**Java 1.6**, whose default is the OS code page — UTF-8 only became Java's default in
18. Confirmed at runtime against the live client rather than assumed:

```
Charset.defaultCharset().name()  ->  windows-1252
```

**Two bugs, and the second is the worse one.**
1. *Outbound*: every accented literal we send was mangled. Spotted live — the
   challenge opponent "Défi" rendered on the end-of-fight panel as `DÃƒÆ'Ã‚Â©fi`.
2. *Inbound*: an accented name or chat line **from the player** is not valid UTF-8
   (`é` is the lone byte `0xE9`), so it decoded to replacement characters — and then
   got **persisted** that way. Corrupting stored data is worse than rendering it
   wrong, and nothing was watching for it.

**Fix.** A single `EncodeText`/`DecodeText` pair in `internal/protocol` (cp1252 via
`x/text/encoding/charmap`, already in the module graph), used by every string helper
on both the reader and the writer. Three call sites were hand-rolling
`I32(len(s)) + Raw([]byte(s))` and bypassing the helpers entirely — chat, ladder and
matchmaking — so they were fixed to delegate; chat is exactly where players type
accents. `internal/testclient` encodes the same way, so the e2e suite now sends what a
retail client sends.

**The subtle part is the length prefix.** It counts **encoded** bytes. "Défi" is 4
runes, 5 bytes in UTF-8 and 4 in cp1252 — so the old code wrote a prefix that
disagreed with its own payload for any non-ASCII string, desyncing every field after
it in the frame. There is a test dedicated to that (a sentinel byte after the string
must still be readable).

A rune cp1252 cannot represent is written as `?`, matching Java's own encoder, so the
byte count stays honest.

**Verified:** `unit` — the exact cp1252 bytes for "Défi"/"Démon"/"Maître d'élevage"
(and `€`, which is what makes this cp1252 rather than latin-1), round trips for 11
accented strings, the byte-accurate length prefix at all three widths, the
unmappable-rune fallback, and that decoding never fails for any of the 256 byte
values. `live` — an accented chat message now round-trips client → server → client and
renders correctly as **"Café"**.

**Residual — and my first write-up of this claimed more than it should have.** I wrote
that the end-of-fight panel was fixed "because it is fed by the same encoder". It is
not, and the truth is more interesting: **the client itself mangles server-provided
NAMES, and no server-side encoding can prevent it.**

Established with two controlled experiments (the coach renamed in the DB, the frame
bytes dumped from a unit test, the result read off the screen):

| stored name | bytes we send (correct cp1252) | client renders |
|---|---|---|
| `Loové` (`E9`) | `05 4C 6F 6F 76 E9` | `LoovÃ©` |
| `LoovÃ©` (`C3 A9`) | `06 4C 6F 6F 76 C3 A9` | `LoovÃƒÂ©` |

Each input gains **exactly one extra UTF-8→cp1252 hop**. That rules out a workaround:
to display `é` the client would have to receive a string whose mangle *is* `é`, i.e.
byte `E9` decoded as UTF-8 — which is not valid UTF-8. Pre-compensating makes it
strictly worse, as the second row shows. The fight panel shows the damage twice
(`Défi` → `DÃƒÂ©fi`) because the name passes through the mechanism twice: once onto
the coach object, once into the results-panel property.

Message BODIES are unaffected — an accented chat message renders correctly — so this
is specific to the name path, not to our encoder.

**Conclusion: the server is right and stays as it is.** Our writers emit correct
cp1252 on every path (`writeFightCoachBlock`, `buildVicinityMessage`,
`EncodeCoachInformations` all dump `…76 E9`). The remaining defect is in a client we
cannot change. What I did **not** do is locate the exact client code performing the
extra hop — the evidence is behavioural, not a decompiled line — so the open item is
"find the mangling call in the client", not "fix the encoding".

### B-067 · 325 usable coach cards did nothing, because the decoder threw their effects away
The card decoder walked the `akw_0` effect array (field 15) only to sniff out the
resurrection percentage and **discarded every other effect**. So of the 907 coach
cards, the **325 flagged usable** — every healing potion, rest balm, morale boost and
blessing — were inert: dropping one on a living fighter hit a handler that only knew
how to resurrect the dead and returned silently.

That made the wound layer from B-066 a **one-way ratchet**: a roster could accumulate
injuries forever with no way to repair them.

**Fix.** Keep the whole effect list on the card (decoded with the shared `decodeAkw`,
the same one card sets and conditions use) and derive `ResurrectPercent` from it, then
extend `FIGHTER_USE_ITEM` (22099) so a card dropped on a LIVING fighter applies its
consumable actions. Each is transcribed from the client class that implements it —
note every one has two methods, a passive `a(et_2)` that only annotates the post-fight
report and a consumable path that mutates the fighter for real:

| AI | usable cards | what the consumable path does | client |
|---|---:|---|---|
| 11 | 30 | heal LIGHT wounds, per-wound roll at x% | `aic_1.c` |
| 5 | 30 | heal SERIOUS wounds, per-wound roll at x% | `ze_1.c` |
| 16 | 8 | `fatigue = clamp(fatigue + x, 0, 100)` | `cm_1.c` |
| 9 | 8 | `morale = clamp(morale + x, 0, 100)` | `cc_1.e` |
| 2 | 4 | permanent XP, capped at 50 000 | `adl_2.c` |
| 15 | **165** | apply condition `[id, duration]` | `vm_2.c` |

The AI-15 duration is used **as-is**: the passive path adds +1 so an equipped card's
condition survives the fight it was applied in, which is meaningless for a card used
out of combat.

**Design choice: a card that changes nothing is NOT consumed.** Unlike the resurrection
gamble — where the card is spent on a failed roll because the roll *is* the point — a
healing potion dropped on an unwounded fighter is refused and kept. A mis-drop should
not destroy an expensive card.

**Why keeping the whole array mattered, concretely.** The live check healed a fighter
carrying `[1 4 7 9]` (2 light + 2 serious) and the result was `[17 20]`, which looked
wrong until the cards were dumped:

```
card 320  AI 15 [17 5]  +  AI 11 [100]      "heal all light wounds, and gain condition 17 for 5 fights"
card 340  AI 15 [20 5]  +  AI  5 [100]      "heal all serious wounds, and gain condition 20 for 5 fights"
```

These potions do **two** things each. A decoder that kept one action per card — as this
one did for resurrection — would have silently dropped half of every such card's
behaviour, with nothing to indicate it.

**Verified:** `unit` — healing by severity (a light potion must not clear a serious
wound), the no-op refusal, fatigue/morale clamping, the XP cap, AI-15 duration and its
exclusion rule, the usable-flag gate, and cross-coach ownership. `real-data` — a canary
pinning 907 cards / 325 usable and the per-action counts (4/30/8/30/4/165/8), that every
usable AI-15 effect carries both params, and that the resurrection percentages still
decode after the refactor. `live` — the real 22099 opcode healed all four wounds off a
fighter and applied both potions' 5-fight buffs.

### B-066 · Fights left no mark: the wound / condition layer did not exist
Type 902 — 111 records — was undecoded, `domain.Fighter` had no conditions, and the
evolution tail shipped a hardcoded `conditionCount = 0`. A fighter could fight forever
and come out untouched: no wounds, no permanent death from injury, and ~30 card-set
effects with nowhere to apply.

**What a condition is.** A persistent status carried BETWEEN fights: the wound layer,
the blessings a coach card grants, and the curses. Decoded from `ahm_1`:

```
[i16 id][i8 grade][i16 type][u8 n]{n × akw_0}[i32 m]{m × Ht}
```

`grade` is the default duration in FIGHTS (-1 = permanent, which every wound is), and
`type` is a mutual-exclusion class. The record needed **no new primitives** — `akw_0`
is the same structure card sets use and `Ht` the same one spells use, so both existing
decoders were reused verbatim. **Field coverage 5/5, zero unknowns.**

Every record carries **either** meta effects **or** in-fight effects, never both
(54/57, asserted in the canary) — which is what lets the apply path route a condition
by inspecting a single list.

**The wound table**, and note where it lands:

| wound | light | serious |
|---|---|---|
| leg | −20% **dodge** (action 123) | −1 MP (18) |
| arm | −20% **block** (action 121) | −1 AP (14) |
| head | −10% XP (meta AI 1) | −20% XP (meta AI 1) |
| torso | −5% resistance (81) | −20% resistance (81) |
| other | −10 initiative (77) | −1 morale (meta AI 9) |

Actions **121/123 are the block/dodge-down actions wired in B-063**, so the wound layer
plugs straight into the tackle model: a leg wound makes you easier to pin, an arm wound
makes you worse at pinning. Card equip effects and condition effects now resolve through
one shared `applyPassiveEffect`, because they are the same kind of thing — an always-on
`Ht` row.

**Apply rules** (`vm_2.a`), all three easy to get backwards:
- **One per type, FIRST wins** — an existing wound on a body part blocks a new one; the
  newcomer is dropped, not swapped in.
- **Type 21 is exempt** and stacks without limit.
- **Type 70 is refused** on this path (it is reached only via the Sphere Board hook).

**The wound roller** (`bf_1.b` — which has *zero callers in the client*, i.e. it is
server logic that happens to ship inside `core.jar`, so it could be reproduced rather
than invented):
1. A wound on a body part — light **or** serious, via a deliberate switch fall-through —
   excludes that part from the draw.
2. Upgrade if 3+ light wounds, or all 5 parts wounded, or a d100 under
   `lightHeld² × 10` (10 / 40 / 90% at 1 / 2 / 3 light wounds).
3. Upgrade while already holding **3 serious wounds → the fighter dies for good**.
4. Otherwise upgrade in place: remove a light wound, add the serious one of that part.
5. Else add a light wound on an unwounded part.

**Injury and death chances** (`adl_0.atd` + `ate`): `injury% = totalXp / 1000`, then
`death% = injury²/100`, then fatigue amplifies the injury chance. The shape is worth
reading twice — **a veteran is far more fragile than a rookie** (10 000 XP → 10% / 1%;
50 000 XP → 50% / 25%), and a rookie's zero chances auto-set the cancel flags that spare
it entirely.

**Two shipped-client quirks reproduced deliberately, not fixed:**
1. The draw pool is built from body parts **1..4 only**, so the roller can never inflict
   a light "other" wound. Diverging would change our wound distribution away from retail.
2. When every drawable part is already wounded the client calls `nextInt(0)` and
   **throws**. A fight actor must not panic, so we report "no injury" instead — the only
   safe reading. Covered by `TestWoundRollNeverPanics`.

**Honest limit:** nothing in the client decrements the duration byte, so the per-fight
countdown is a server choice. The evidence for it is `vm_2.a` adding +1 to a card-applied
duration "so it survives this fight", which only makes sense if the tick happens once per
fight at the end. We expire AFTER the wound roll, so a wound taken this fight is not
immediately aged.

**Verified:** `unit` — the 111-record canary (population, the meta/fight split, all 10
wounds' ids/types/payloads, the light↔serious symmetry), the apply rules, expiry, the
three roller outcomes, the two no-panic states, healing by severity, and wounds actually
changing AP/MP/block/dodge through the same path cards use. `live` — ~120 real evolution
fights: light wounds appeared, upgraded into serious wounds, **two fighters died**, and
the retail client rendered it — the wound count as skull icons (3 and 4, matching the
server's `conds=3`/`conds=4`), dead fighters as **RIP gravestones**, plus the fatigue
("Zzz") and morale bars — with an empty `output.log` throughout.

### B-065 · No fighter ever gained XP: the post-fight report was never sent
END_FIGHT (8300) carries a **per-fighter debrief record** — the client's `adl_0`,
read back as `OW` — and the server sent a count of **zero**, forever. Consequences:
the evolution debrief panel (`fightResultEvolutionDialog`, which binds all 13 of its
exposed fields) had nothing to render, and no fighter gained XP, morale or fatigue
from playing. Coach reputation was likewise hardcoded to 0, so
`standing += amW()` in `WE.java` always added nothing and the level-up dialog could
never fire.

The byte was also **mislabelled in our own source** as "object stats count", which is
probably why it stayed at zero: `YP.a()` lines 90-97 read it as
`[u8 n]{[i64 fighterId][i16 len][OW blob]}` → `cbF.a(fighterId, new OW(bytes))`.

**Why this was worth doing first:** it is the single shared blocker for two whole
subsystems. 78 already-decoded card-set effects and the entire type-902 condition
layer are inert *only* because there is no report for them to modify.

**Implemented (slice 1 — XP, morale, fatigue, reputation).** Every formula is
transcribed verbatim from the shipped client, because `core.jar` contains the
*shared* client/server code — the real server's arithmetic is literally in the jar:

| Rule | Source | Behaviour |
|---|---|---|
| XP | `adl_0.a(base, hours, morale)` | `base * (100+morale)/100`, then `+50%` if idle **> 12 h**. The morale value **is** the bonus percentage. |
| Fatigue recovery | `et_2.a(fatigue, hours)` | `(sqrt(f) - sqrt(h-1))²` — accelerating: 100 → 0 takes 101 h, 25 → 0 takes 26 h. |
| Fatigue cost | `adl_0.a(t, h, true)` | `+rand(25)` per fight, capped at 100; at the cap the fighter is **exhausted**. |
| Morale drift | `adl_0.dg` | Damped by distance from the extreme — a win moves by `(100-morale)/50`, a loss by `morale/50`, so morale converges instead of pinning. |
| Fighter level | `nr_0.cs` + `PP` | 1..6 at 860 / 4 000 / 10 000 / 20 000 / 40 000 XP. |
| Coach evolution level | `aet_0.nJ` / `nr_0.ct` | `max(1, min(sqrt(standing/10), 50))`, inverse `n²*10`. |
| XP cap | `et_2.ft` | Gains refused once spendable XP reaches 50 000. |

**Two numbers are NOT client-derived and are flagged as such in the code**:
`baseXPPerFight` and `standingForResult`. Both arrive on the wire pre-computed
(`cnr`, `YP.cbG`), so the real server's formula for them is not in the jar.
Everything they flow *through* is exact; only the seeds are ours, and they are single
named constants so they can be replaced the moment evidence appears.

**Design note — the guard is on EVOLUTION, not on "not practice".** A GM/solo
evolution fight is flagged practice as well, so testing `Practice` alone would skip
exactly the mode the whole debrief exists for. `fightFeedsProgression` therefore
returns true for any evolution fight, and false for plain practice and for
challenges (the client is explicit: *"tu n'auras pas de fatigue ni de blessure ou de
mort dans un défi du temps"*).

**Also:** 8300 is now sent **per coach** rather than broadcast, because
`standingWon` is a single scalar — one shared frame would credit the loser with the
winner's reputation. Spectators get a neutral copy.

**Verified:** `unit` — the 40-byte layout asserted byte-for-byte against
`adl_0.cd()` (including the signed `moraleDelta`), the XP formula across 7 cases
(incl. "exactly 12 h does not qualify"), recovery monotonicity, morale convergence at
both extremes, the level tables round-tripped for every level 2..50, and the XP cap /
clamping guards. `live` — a real fight logged `post-fight meta reports=7 killed=0
injured=0 standing=map[3:10]`, the retail client parsed the enlarged 8300 with **no**
`output.log` error, and the result screen still renders correctly.

**Not yet visually confirmed:** the evolution debrief *panel* itself. It only opens
when the client **exits its own fight state** (`ajo_1.a`, gated on fight kind 6), and
reaching a true evolution fight through the UI needs a second player — the Évolution
tab's "Tester" button starts a *challenge* (challenge=12), not an evolution fight.

### B-064 · The e2e combat flake was a wrong assumption, not a timing problem
`TestCombatSpellDamage` failed roughly 1 run in 4 on a loaded machine with `no SPELL_CAST
(8110)`. Two earlier "fixes" (a 30s→6s turn clock, and a collect loop that swallowed
`FIGHTER_TURN_BEGIN`) were real improvements but **did not touch the cause**, and the
failure kept coming back. Both were guesses at timing.

**Method that actually found it.** Reproduce on demand instead of waiting for luck: pin
23 of 24 cores with busy loops, and the failure goes from ~1-in-4 to **4-in-4**. Then log
each refusal branch server-side rather than reason about them. The first instrumented run
was decisive:

```
DIAGCAST dropped in handler casterID=1099511627776 nilCaster=false
    coachMismatch=true notCurrentTurn=false currentWire=1099511627776 turnIndex=0
```

`notCurrentTurn=false` — the turn was fine all along. **`coachMismatch=true`**: the test
was casting with a fighter belonging to the *other* coach, and `handleSpellCast` dropped it
silently (correctly).

**Cause.** The tests identified their own fighters with `isTeamAFighter()`, i.e. "team A is
side 0". But side 0 is simply **whoever reached the matchmaker queue first** —
`Matchmaker.Search` pairs the new searcher with the one already queued, and
`handleFight` builds `buildFightTeam(pm.a, 0)` / `buildFightTeam(pm.b, 1)`. The harness
fires A's and B's `Search` back-to-back with no happens-before, so under load **B can win
the race and A becomes side 1**. Every assumption then inverts: the loop casts with B's
fighter (dropped) and skips its own, burning all four attempts — exactly the observed
"cast, skip, cast, skip" pattern.

The server was right at every step; three tests were wrong (`TestCombatSpellDamage`,
`TestFightMove`, `TestPlacementMove` all shared the helper).

**Fix — make the tests side-agnostic rather than force the race.** An action for a fighter
you do not own is a silent server-side no-op, so a test can simply *try*:
- cast/move on **every** turn offered, using that fighter's own side-appropriate cells
  (`enemyStartCell`, `oneStepFromStart`);
- `TestPlacementMove` probes for ownership — the 8022 echo only comes back for a fighter
  the requesting coach owns, so the echo **is** the proof;
- the final assertion became "damage did not land on the **caster's** side" (`fighterSide`,
  computed from the wire id) instead of "not on team A";
- `isTeamAFighter` is deleted so the assumption cannot come back.

Also kept: B now **ends its own turns** instead of letting them expire, so rotation no
longer depends on the turn clock; the clock could therefore go 6s→**12s**, which removes
the *other* latent race (acting inside your own turn on a slow machine).

**Verified:** under the same 23/24-core load that gave 4/4 failures, the three combat tests
now pass **6/6**; the full e2e suite passes repeatedly under half-machine load (~70-82s,
barely above its idle 77s).

### B-063 · Tackle used a flat 67% instead of the fighters' real block/dodge
Tackle (zone of control) *was* implemented — this document and `STATUS.md` both wrongly
listed it as missing — but it rolled a **hardcoded 67%** evasion for every fighter against
every holder, with a comment admitting "the per-side Evasion modifier is not modelled yet
(no fighter evasion stat)". The stats existed all along; nothing read them.

**The characteristics.** `Lr.brd` = block %, `Lr.bre` = dodge %, both fed by:
- the breed table (`xq` args 13/14 → `DT()`/`DU()`),
- card/spell actions **120/121** (block ±) and **122/123** (dodge ±),
- a summon's own type-300 template (29 creatures carry a block %, 36 a dodge %).

The shipped breed values are strikingly coherent — **dodge is 100 for every breed**, and
block tracks class identity: **Feca 60, Iop and Pandawa 40, Sram and Sacrier 20, everyone
else 0**. The client's help text confirms the dodge base independently: *"Tous les
personnages ont, de base, 100% en esquive"* — an external check that argument 14 is
identified correctly.

**The rule**, from the same help text: *"Ce sont tes chances d'esquive qui donneront ton
pourcentage de chance de ne pas être bloqué. Avoir 100% en esquive te garantis de toujours
esquiver les personnages qui ont 0% de chance de blocage… Chaque fois que ton combattant
quittera le corps à corps d'un adversaire, celui-ci pourra tenter de le bloquer, pour lui
faire perdre son tour."*

**Fix.** `tackleEvasionChance = clamp(mover.Dodge - holder.Block, 0, 100)`, rolled once per
adjacent holder. Block/Dodge now flow from breed → equipped-card actions 120-123 →
in-fight buffs (new `BuffBlock`/`BuffDodge`) → summon templates, and are stored unclamped
so a timed debuff reverts exactly.

**Honest limit:** the client's `TackleAction` is a cosmetic animation in both 2.70 and
2.04b, so the arithmetic is server-side and *not* recoverable from the client. The
**anchor** is verified (100 dodge vs 0 block always escapes; higher block is worse); the
**curve between the endpoints is a server choice**. Subtraction is the simplest shape
satisfying the anchor. This is flagged in `tackle.go` so nobody later mistakes it for a
client-derived formula.

**Also dropped: the "4+ adjacent enemies = movement impossible" cap.** It came from the
2.04b manual and directly contradicts the 2.70 guarantee above — four adjacent Cras would
pin a fighter the client promises free passage to. Being surrounded is punishing on its
own: four Fecas give 0.4⁴ ≈ 2.6%.

**Two more mappings corrected while here:** actions **147/148** (crit-rate / fumble-rate
*malus*) were classified as generic buffs and never applied; they now feed CritRate and
FumbleRate. Action 120 is consequently no longer "render-only" — `TestBuffLifecycle`'s
render-only case moved to action 155 (damage/resistance bluff).

**Verified:** `unit` — the formula and its bounds across 8 cases; 100-dodge-vs-0-block
escaping 200/200 times even when surrounded on all four sides; ~40% escape vs one Feca and
~16% vs two (independent rolls); breed block/dodge pinned to the client table with the
"a Feca must block better than a Cra" sanity check.

### B-062 · Card-set bonuses ("panoplies") did not exist
Record type 101 — 138 sets, referenced by all 907 coach cards through their `CardSet`
field — was never read, so wearing a matching set granted nothing.

**The rule, from the client** (`sj_1`'s aggregated-bonus builder, not from the UI):

```
count = the coach's EQUIPPED cards of this set
for each set effect:  if effect.Threshold <= count  ->  it applies
several sources of the same action id are SUMMED
```

The threshold is `akw_0.aAm()`, the trailing byte of each effect entry. Note that
`fe_1`'s `halfSetEffects` / `fullSetEffects` split (`threshold < size` vs `== size`) is
**only how the UI groups them** — the engine rule is the per-effect threshold and nothing
else. Real thresholds range 2..10.

**What set effects are.** They use the client's `AI` enum, i.e. the coach META layer:
XP % and flat (28 effects), fatigue (9), morale (9), reputation (6), death chance (5),
wound cancellation (4), drops — and **resurrection chance (10)**.

**Scope, deliberately limited.** Of those meta systems this server implements exactly one:
resurrection. So that is the only one wired up — `setBonusFor(aiActionResurrect)` is added
to the card's own chance (capped at 100) in the graveyard revive path. The other bonuses
are decoded and inert, waiting for their mechanics, rather than half-built against
consumers that do not exist. `docs/DATA-COVERAGE.md` lists them.

**Verified:** `unit` — the threshold rule across 0/1/2/4/5 equipped cards; summing across
two different sets; unequipped cards (Pos 0) not counting; a different action id not
mixed in; no catalogue staying inert rather than panicking. Real data: every set a card
points at exists, all 88 effects have a plausible threshold (1..20) and AI action (1..21)
and carry params, and at least one set grants resurrection. `live` — the server logs
`cardSets=138`.

### B-061 · Bound / undestructible cards could be traded
`exchangeMoveCard` validated only the per-instance state — the player's own lock flag
and "is it equipped". It never looked at the card TEMPLATE, so the two flags that make a
card non-tradable were ignored: **171 of the 907 shipped cards are `Bound`** ("linked" to
their owner) and **65 are `Undestructible`**.

The client refuses both itself (`error.exchange.linkedCard` /
`coachInventory.undestructibleCard`), which is exactly why the server has to as well — a
client-side rule is not a rule. A forged 5105 could stake a bound card and trade it away.

**Fix.** `Deps.cardIsTradable` checks the template's `Bound`/`Undestructible`, called
before a card is staged. Unknown templates and an absent catalog stay permissive, so a
server running without data files behaves as it did rather than blocking all trade.

**Fixing it required decoding the record properly.** The coach-card decoder read only the
first 5 fields and left `Rank` permanently 0, with a comment deferring the rest. It now
decodes through field 18 in one pass: required level, firework type + colour, isUnique,
obtainable-in-draw + drop %, **bound**, **undestructible**, has-usable-action, the effect
array (which the resurrection scan already walked) and rank. Fields 19-26 remain unread
behind an `np_1[]` array whose element layout is not yet known — recorded in
`docs/DATA-COVERAGE.md` rather than silently skipped.

**Verified:** `unit` — tradability across plain/bound/undestructible/unknown templates and
with no catalog at all; real-data canaries on the newly-decoded fields (implausible
level/rank/drop-% ranges fail, and a zero `ranked` count fails, so a field-order slip is
caught); the pre-existing resurrection test still passes, which independently confirms
the record stays aligned through field 15.

### B-060 · Summons ignored their innate properties — every creature could be shoved
The type-300 summon record was decoded only as far as field 6 (id/HP/AP/MP/spells/gfx);
the whole tail was skipped as "not needed server-side". It very much was.

Fields 7–12 are the properties a creature is **born with** — the client's `adT`/`ta_0`
stamp them onto the fighter at spawn, which is how a wall, a doll or a Xelor dial is
immovable:

| field | getter | client property | effect |
|---|---|---|---|
| 7 | `op()` | `avx_0.deA` | cannot be carried (blocks Carry 58) |
| 8 | `oq()` | `avx_0.deB` | intransposable (blocks swap 64) |
| 9 | `or()` | `avx_0.dev` | stabilised (blocks Push 37 / Pull 38) |
| 10 | `os()` | `avx_0.dex` | rooted — MP reads as 0, cannot walk |
| 11 | `ov()` | `avx_0.deD` | set by the client, never tested by it |
| 12 | `oy()` | `avx_0.deF` | disables positional damage bonus (inert: 2.70 dropped it) |

Measured on the 53 shipped creatures: **22 rooted, 21 cannot-be-carried, 18 stabilised,
15 intransposable**. We honoured none, so any summon in the game could be pushed, pulled,
swapped or picked up.

**Fix.** The record is now decoded in full (17/17 fields), and
`applySummonInnateProperties` grants the four displacement properties as permanent states
at spawn, so the existing enforcement covers them with no new rules. Fields 13/14
(**block %** on 29 creatures, **dodge %** on 36) are decoded but deliberately inert —
tackle/dodge is unimplemented server-wide, and that is a mechanic gap, not a summon one.

**Verified:** `unit` — real-data canaries on the flag populations and on block/dodge
plausibility (a field-order slip collapses them to zero and fails); and a game-side test
that a wall creature gets all four properties, keeps them across a round tick, actually
resists a push and a carry, and that a flagless creature is left fully mobile.

### B-059 · Spell COOLDOWNS were read from the wrong field — 97 spells had none
The spell record has four distinct cast-limit bytes and we had the cooldown on the wrong
one. Each maps to a different bucket of the client's cast-history tracker `sH`, which is
what fixes their meanings:

| field | getter | client bucket | meaning |
|---|---|---|---|
| 7 | `iS()` | `akU`, keyed `spellId<<32\|targetId` | max casts per target, per turn |
| 8 | `iT()` | `akV`, counted up/down | max **live instances** at once |
| 9 | `iU()` | `akT`, reset each turn | max casts per turn |
| 10 | `iV()` | `akS`, stores `turn + value` | **cooldown in table turns; 63 = once per fight** |

We read the cooldown from **field 8**. Measured against the shipped table: field 10 is
non-zero on **97 of 203 spells, 28 of them 63 (once per fight)**, while field 8 is
non-zero on just 6. So nearly half the spell book had **no cooldown enforced at all** —
including every once-per-fight spell, which could be cast every single turn — and the 6
max-active spells were instead wrongly rate-limited.

**Also newly decoded from the same record:**
- **Field 18 `eD()` = range NOT boostable** (5 spells): the caster's Range characteristic
  must not extend these. The client's gate is
  `if (!(maxRange <= 1 || boost >= 0 && eD())) maxRange += boost`, so the flag suppresses
  only a *positive* boost — `spellTargetValid` now mirrors that exactly.
- Field 11 `et()` (deferred unlock delay) is decoded for completeness.

**Still unread on this record:** field 22 (spell-level target bitmasks — we honour only
the per-effect ones) and field 23 (parent spell id — the client shares cooldowns and caps
with the parent). Both are logged in `docs/DATA-COVERAGE.md`.

**Verified:** `unit` — a real-data test asserts cooldowns are common (≥50 spells), that
once-per-fight spells exist, and that the max-active cap is rarer than the cooldown, so a
future field swap fails loudly.

### B-058 · Every breed's INITIATIVE (and base value) was the 2.04b figure — turn order was wrong
The breed table's initiative column, and the per-breed base value, were the **2006**
numbers. Initiative decides the fight timeline, so turn order in every fight was built
on the wrong data.

**How it was pinned.** 2.70's breed table is the obfuscated enum `xq`, but v2.04b ships
the **unobfuscated twin of the same table** (`Breed.java`) and the two line up argument
for argument, which fixes every position beyond doubt:

|  | id | HP | AP | MP | **init** | crit | fumble | **value** | element | ccAP | ccDmg | ccCrit |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| 2.04b `FECA` | 1 | 70 | 6 | 3 | **50** | 5 | 1 | **400** | WATER | 5 | 5 | 7 |
| 2.70 `axF` | 1 | 70 | 6 | 3 | **20** | 0 | 0 | **600** | `ban` | 5 | 5 | 7 |

The client reaches them as `ok()`=HP, `ol()`=AP, `om()`=MP, `DK()`=initiative — and
`DK()` is what seeds characteristic `Lr.bqA`, the one `ee_2` renders as
`initiativePoints`. That chain is what proves the 5th argument is initiative.

**2.70 re-tuned three things, and all three were wrong here:**
- **Initiative — all 12 values changed.** 2.70's are unique per breed (Feca 20,
  Osamodas 40, Enutrof 60, Sram 50, Xelor 80, Ecaflip 70, Eniripsa 0, Iop 10, Cra 75,
  Sadida 30, Sacrier 90, Pandawa 100), so the timeline is fully determined with no ties;
  the 2.04b values we carried had many duplicates.
- **Base value 400 → 600**, so every fighter's budget was 200 short (compounding B-056).
- **Cra's close-combat element** (already fixed as B-057 — and this is where it came
  from: 2.04b's `CRA` really is `WATER`).

**Verified:** `unit` — the whole table is pinned to the client's values, including a
check that no two breeds share an initiative.

**Still open — base crit/fumble.** In the same positions where 2.04b holds `5, 1`, the
2.70 client holds **`0, 0`** for every breed, and those feed `xq.DL()`/`DM()` →
`Lr.bqU`/`bqV`, which `ee_2` renders as `criticalHitBonus`/`criticalMissMalus`. Read
literally, 2.70 gives fighters no innate crit or fumble and expects equipment to supply
it. `baseCritRate`/`baseFumbleRate` are deliberately left at 5/1: zeroing them silently
removes crits from every fight, and the fighter-card record has an unidentified i32
(`uh_0.eA`) that is a plausible per-weapon crit rate. That field needs identifying first.

### B-057 · Cra's close-combat element was water, not air
`breedTable`'s per-breed close-combat element put **Cra** in water. The client's own
breed table (enum `xq`, whose 9th ctor arg is an `fv_1` element) splits the 12 breeds
cleanly **3/3/3/3**:

| element | breeds |
|---|---|
| fire (`fv_1.bam`) | Xelor, Eniripsa, Pandawa |
| water (`fv_1.ban`) | Feca, Enutrof, Ecaflip |
| air (`fv_1.bao`) | **Sram, Cra, Sacrier** |
| earth (`fv_1.bap`) | Osamodas, Iop, Sadida |

Ours made water a 4-breed group and air a 2-breed one, so every Cra punch was resolved
against the target's **water** resistance instead of air — wrong damage on every
close-combat hit by a Cra.

**Also verified while there** (no change needed): the close-combat constants really are
uniform across breeds — every `xq` entry carries the same 5/5/7, exposed as
`DO()`/`DP()`/`DQ()`, and the client renders "Corps à corps (N AP)" from `DO()`. HP/AP/MP
match the client for all 12 breeds.

**Still unverified:** `Init` is *not* from this table (the client's 5th ctor arg spans
0..100 and matches no plausible initiative spread), so our initiative values — which
decide turn order — remain unconfirmed against the 2.70 client. Flagged in the code.

**Verified:** `unit` — the element groups are pinned to the client's 3/3/3/3 split, and
base HP/AP/MP to its table.

### B-056 · Fighter equipment was valued from the COACH-card table
`computeFighterBudget`/`computeLoadoutBudget` summed a fighter's equipment value out
of `Deps.Cards` — the **coach**-card table (type 100). A fighter's objects are
**fighter** cards (type 250, client `ve_0`). The two id spaces overlap almost
completely with entirely unrelated prices, so the lookup silently succeeded and
returned nonsense.

Measured across the shipped tables: of the 75 fighter cards, **66 got a different
value and not one matched** (9 ids are absent from the coach table). Card 85 — a
weapon worth 200 — was valued at **18200**; card 97 (50) at **46000**. Since the
budget is clamped to int16, a fighter with two pieces of gear saturated at 32767,
making team-budget limits meaningless.

**Fix.** Both functions now read `Deps.FighterCards`. **Verified:** `unit` — a card id
present in BOTH tables with different values must be valued from the fighter table.

### B-055 · Using a fighter's equipment (8107) applied no effects at all
Playing a fighter's gear in a fight was acknowledged and animated but did **nothing**:
no AP was spent, no damage dealt. The handler broadcast FIGHTER_CARD_USE (8108) and
stopped.

**Root cause — a misread that had been carried in a comment.** The code claimed card
effects "use the client's own action enum (`AI.aHI`), a different table from spell
effect ids", making it a separate subsystem. That is wrong on both counts:

- `AI` is the **COACH**-card meta enum — XP bonus, wounds, morale, fatigue, drops,
  reputation, resurrection chance. It has nothing to do with in-fight equipment.
- 8107 is sent by the client's `abt_1` targeting mode carrying a **`ve_0`**, which is
  fighter EQUIPMENT (its icons come from `fighterEquipmentIconsPath`; its `cardType`
  is "weapon"/"equipment"). Its effects are ordinary `xj_0` effects — the *same*
  structure spells use, which the existing resolver already handles.

So this is the **weapon attack**, and it needed no new effect machinery. (It is also
distinct from CLOSE_COMBAT 8111, which is the breed's fixed unarmed strike.)

**Data.** A fighter card (type 250, `uh_0`) carries two effect lists split by
container type, exactly as the client splits them in `jb_2.a`:
`FIGHTER_CARD_EQUIP` (passive) and `FIGHTER_CARD_USE` (the active ability). A card is
playable iff it has the latter (`jb_2.isUsable`) — **23 of the 75 shipped cards**, all
weapons: an AP cost, a range band, and elemental damage in a normal and a critical
variant.

Two decode traps, both caught by the data rather than assumed:
- **The container type is space-padded** to a fixed width (`"FIGHTER_CARD_USE  "`).
  Matching it untrimmed found ZERO use-effects while the same-length
  `"FIGHTER_CARD_EQUIP"` happened to work — a silent, one-sided failure.
  `decodeEffectBlob` now trims, as the client does.
- **The record stores range MAX before MIN.** Three independent signals agree: the
  client renders the band as `AA()-Az()` (ascending only this way), a max of 0 is what
  makes a card self-target-only (`ve_0`'s "cast.targetCaster"), and the reverse
  reading yields min > max on every ranged weapon shipped.

**Fix.** `FighterCard` now decodes `APCost`, `RangeMin`/`RangeMax` and `UseEffects`
(+`Usable()`); `useFighterCard` (`fightercard_use.go`) mirrors `castSpellByFighter` —
validate, roll fumble/crit, broadcast 8108 with the real flags, debit AP, resolve the
crit/normal effect subset, flush, check for fight end. Ownership is now checked
against the FIGHTER's own objects (it was checking the coach's inventory, the wrong
collection entirely).

**Deliberately not enforced:** the record's six flag bytes (line-of-sight / only-line
/ free-cell / carry gates). Their individual positions are unverified against the
client's targeting code, and guessing wrong would silently reject legitimate attacks —
worse than being permissive toward a forged packet, which the cell/range/AP checks
already bound.

**Verified:** `unit` — a played weapon deals damage and costs its AP; unowned gear,
passive-only gear, out-of-range targets (both too far and inside the minimum), out-of-
turn use and insufficient AP are all refused with no side effects; a crit resolves the
IsCritical subset (higher damage) and a fumble spends AP but deals nothing; the Range
stat extends a ranged weapon but never a melee one. Real data: 23/75 cards usable,
every one with `min <= max`, a plausible AP cost and an action on every effect; card 85
is 4 AP range 1-1 with both variants; card 37 decodes as range 2-5 (the max-before-min
canary). `live` — 8107 now reaches the resolver and correctly refuses the test
fighter's passive-only card 149, logging why.

**Live-verified end-to-end** (follow-up pass). Two `/script` commands were added to
drive this — `usecard <wire> <card> <x> <y>` and `cc <wire> <x> <y>` — then weapon 85
was equipped on a real fighter through the REAL protocol path (6011
UpdateFighterInventory), a Tester fight was started, the sparring AI was allowed to
walk into contact, and the weapon was played:

- `used=true ap 6->2/6` — the card's own 4 AP is charged.
- Target HP **70 → 55** on the first hit, then exactly **10 per hit** over the next
  four (55 → 15). Card 85 ships `params=[10]` normal / `[15]` critical, so the 15 was a
  genuine 5%-crit roll and the crit subset is being selected correctly.
- The loadout save logged `budget=600` = 400 base + card 85's **fighter-card** value of
  200, which also confirms B-056 live (the old coach-table lookup would have scored
  18200 and saturated the int16 clamp).

### B-054 · The per-round EVENT CARD was never implemented
Every round of every fight sent `NEW_TABLE_TURN_BEGIN` (8100) with `eventId 0` and
applied nothing, so the card that is supposed to be drawn at the start of each table
turn — and to affect BOTH teams for that round — simply did not exist.

**Root cause / provenance.** The mechanic is server-authoritative. 8100 is
`[i32 uid][i32 -1][i8 turn][i32 eventId]`; the client's `jg_1` reads the id and
resolves it via `cw_1.eO().w(id)` into a `tO` ("ClientEvent") **purely to display the
card** (name from i18n table 8, art from `eventsIllustrationsPath`). Nothing in the
client applies the effects — the server draws the card AND applies it. Our
`buildNewTableTurn` hard-coded `I32(0)`.

The cards live in `data.bdat` **type 230** (client record `ama_1`, loaded by `aGl`):
`[i32 id][i16 unused][u8 descriptionFlag]` + the standard embedded effect list. 51
records ship.

**Which cards are drawable.** The data is not one pool, and the deciding evidence is
the 2006 client: its `events.dat` holds **exactly ids 1..27**. So 1..27 is the base
deck — the 12 breed-god cards (each restricted to its own breed by the effect's breed
target condition) plus 15 neutral arena cards — and everything from 43 up was added
later for PvE: the creature cards (43..47, 67) and the "Démon des Minutes 3"
boosts/debuffs (61..66) carry creature-scoped target masks, and 48..59 are a second,
far stronger god set (+3 AP, +100% resist, arena-wide invisibility) that would
dominate a fight if it came up every few rounds. Only 1..27 are dealt.

**Fix.**
- `gamedata/events.go` — decodes type 230 into `Event{ID, Effects, DescriptionFlag}`
  with `LoadEvents`/`Get`/`IDs`/`Len`; wired as `Deps.Events` in `main.go`.
- `game/events.go` — a per-fight shuffled deck of ids 1..27 dealt one card per round
  and reshuffled once exhausted (every card appears once per cycle), shuffled with the
  fight's own RNG so a seeded fight is reproducible. `beginTableTurn` broadcasts
  8100 with the real id FIRST (the client must instantiate the card before the effect
  actions arrive) and only then applies it. Reconnect resends the round's CURRENT card
  rather than drawing a new one.
- **Application model:** each effect is applied once per living fighter with that
  fighter as BOTH caster and target. Self-casting is what makes it work — an event has
  no caster, and the per-fighter handlers read the caster's own characteristics (a
  "+30% damage" card must scale off each fighter's own stats). Events deliberately
  bypass cell-area resolution: a round card has no aimed cell, and the shipped data is
  inconsistent anyway (most effects are `areaShape=32767` "all", but event 7 ships
  `areaShape=1`). Each effect is still gated by its own target conditions, which is how
  "Dieu Iop" buffs only Iops. Summons are NOT blanket-excluded — the data targets them
  explicitly where intended (the Osamodas card buffs summons via IS_SUMMONED), so the
  conditions decide.
- **The opening round is fixed:** round 1 always draws event 14 ("Cloué au lit").
  Implemented by moving that card to the top of the freshly-shuffled deck, so it is
  still dealt exactly once per cycle and cannot repeat on round 2.

**Target conditions gained a negative breed bank.** The live draw of event 8 ("Dieu
Enutrof") exposed it: one effect buffs Enutrofs, the other buffs everyone *else*, and
the second is expressed as bit 34. Per the client's evaluator (`aap.a`), bit **16+k**
means "breed IS k+1" and bit **32+k** means "breed is NOT k+1", each bit checked
independently. `target_conditions.go` previously masked only bits 16..27 and compared
the whole masked value at once, so a "not an Enutrof" effect landed on the Enutrof too.
2.70 also widened the bank from 12 breeds to 14 slots.

**Verified:** `unit` — decoder against the real table (51 cards; every base-deck id
present; each record's inner id matches its key, the canary for a mis-aligned header;
event 14 is exactly 94+127+128 with a one-round duration; event 1 carries the Iop breed
bit; the two non-"all" effects are both on event 7); deck behaviour (inert with no data,
first draw is card 14 for 50 different seeds, every card dealt once per cycle with no
early repeat of the opening card, twice over two cycles, never a non-base card in 200
draws, deterministic under seed); effects reach BOTH teams; a breed card buffs only its
breed; the negative-breed card buffs each side exactly once. `live` — server logs
`eventCards=51`, and two separate fights both drew `turn=1 eventCard=14` then a random
card (8, then 3) on turn 2, with the fight dump showing both fighters carrying the
opening card's three states.

**Not verified:** that the client visually renders the drawn card — an injected fight
builds no client-side match object, so nothing renders in the harness. The id is on the
wire and the client's own resolver is a pure display lookup.

### B-053 · Actions 127/128 were mis-mapped onto root/stabilise — the opening card froze the arena
`states.go` folded action **127** ("S'enraciner") into `stateRooted` and **128**
("Rendre intransposable") into `stateStabilized`. Both were wrong, and 127 was wrong in
a way that mattered: it zeroed the target's MP and blocked its movement outright.

**Root cause.** Each state action maps to a distinct client FIGHTER PROPERTY (enum
`avx_0`), and the client shows exactly what each one forbids:

| action | class | property | what it actually blocks |
|---|---|---|---|
| 65 "Immobilisé" | `rc_0` | `dex` (ROOTED) | sets MP (`Lr.bqz`) to 0 → **cannot walk** |
| 96 "Pétrifié" | `asy` | `dew` | AP **and** MP read as 0 → cannot act |
| 94 "Stabilisation" | `cw_2` | `dev` | **only** Push (37 `na_2`) / Pull (38 `sa_2`) |
| 127 "S'enraciner" | `fj_1` | `deA` | **only** Carry (58 `Jk`) |
| 128 "Rendre intransposable" | `ky_1` | `deB` | **only** swap position (64 `aox_1`) |

`mv_1` gates each displacement effect on its own property, and `gn_0` zeroes the MP
characteristic for `dew`/`dex` only. So 127 and 128 never stop a fighter walking — they
stop it being *moved by someone else*.

This surfaced through the round-card work: event 14, the card the opening round always
draws, is exactly 94 + 127 + 128. Under the old mapping it rooted and MP-zeroed every
fighter, freezing the whole arena on round 1 instead of simply making everyone immune to
being pushed, pulled, carried or swapped.

**Fix.** Split into five distinct states (`stateRooted`, `statePetrified`,
`stateStabilized`, `stateAnchored`, `stateIntransposable`); `applyPushPull` keeps
checking stabilised, `applySwap` now checks intransposable (it was checking stabilised),
and `applyCarry` now checks anchored (it checked nothing).

**Verified:** `unit` — each action maps to its own state; a stabilised fighter resists a
push but can still be swapped and keeps its MP; an anchored fighter cannot be carried but
still walks and keeps its MP; an intransposable fighter resists a swap but can still be
pushed; and the opening card leaves everyone able to walk while granting all three
immunities. `live` — the in-fight dump shows both fighters at round 1 with
`[anchor:1 intransp:1 stab:1]` and **MP 3/3**.

### B-052 · Only ONE fight map existed — all 47 arenas are now supported
Every fight was played on world 5, because the arena was a single hand-decoded
package-level value (`practiceArena`): its topology, scenery, start cells, coach
pedestals and special cells were transcribed into Go by hand. Any fight on another
map would have been validated against world 5's geometry.

**Fix — the server now reads the client's own map files.** `maps/fight` and
`maps/tplg` were copied into `data/maps` (~1.2 MB; the same duplication already used
for `data.bdat`), and `gamedata.LoadFightMaps` decodes them:

* **`.fmd`** (client `Om.b`, little-endian): 6 packed coach slots, a packed
  `(team0<<8)|team1` count, both teams' start cells, then the special cells as
  `{i32 packedPos, i32 templateId}`. A packed position is
  `x=(v>>>20 &0xFFF)-2047, y=(v>>>8 &0xFFF)-2047, z=(v&0xFF)-127`.
* **topology tiles**: a 7-byte header `[u8 type][i16 chunkX][i16 chunkY][i16 wp]`
  then one of three per-cell layouts — type **2** `ajg_2` (a 16-entry altitude
  palette, a 4-entry ground palette and 18×18 cell bytes), type **3** `aji_2` (one
  packed i16 per cell, `ground` read from the SIGN-EXTENDED short so a negative value
  means no floor), and type **5** `ajj_2` (a sorted list of packed ints, several
  LAYERS per cell). Types 0/1/6 carry no floor data. A cell has no floor when its
  ground entry is −1; combined with the altitude sentinel that separates true void
  from solid scenery.

Two decisions were driven by evidence rather than guesswork:
* **Layer choice.** A type-5 cell can stack several layers (a platform over a pit).
  Taking the first layer put start cells at the wrong height on 6 maps; taking the
  HIGHEST floor layer matched every arena. Each `.fmd` records the exact z the client
  expects for each start cell, so those 47 maps' start points are the oracle.
* **Map 42's content quirk.** Two of its special cells sit on cells the topology
  gives no floor, so they could never be stood on and never fire. They are dropped as
  unreachable (`UnreachableSpecials`) rather than papered over.

**Refactor.** `arena` is now one flat cell grid (`floor` / `scenery` / `void`) with a
bounding box, so a map need not start at (0,0); `Fight` carries its own `*arena` and
every rule reads `f.Arena()` — movement, spell targeting, line of sight, pathfinding,
summons, carry, effect areas, the streamed fight grid, ACTOR_APPEAR, EnterInstance and
sudden death. `pickArena` rotates over the whole set, so fights are spread across all
47 maps. The hand-decoded world 5 is kept as the fallback (folded into the same cell
grid by `init`), so a checkout without map files — and the entire unit suite — still
works unchanged.

**Verified:** `unit` — the decoder must reproduce the hand-decoded world 5 EXACTLY
(start cells, all 22 scenery cells, the 9 specials with template ids, the 151/22/151
floor/scenery/void split, pedestals on scenery); all 47 arenas load with every start
cell and special on real floor at the altitude the `.fmd` records; every arena's
streamed grid classifies each cell as exactly one of floor/scenery/void; two fights on
different arenas stay isolated and collapse toward their own centres; an arena-less
Fight still falls back to world 5; and sudden death terminates leaving a core on
**every** arena. `live` — the server logs `fight maps loaded arenas=47`; three
successive fights started on three different maps (start cells (6,2,0) / (15,8,0) /
(14,10,-1), none of them world 5's), and the client rendered the arena correctly.

### B-051 · Every private message and GM reply rendered EMPTY
Noticed while testing GM commands: each reply appeared in the chat as
`de Server :` with **no text**. It affected every whisper too, so the private
channel was effectively mute.

**Root cause:** PrivateContent (3154) writes its body with a **u16** length, but
the client's `ais_2` decoder reads it as a single BYTE
(`new byte[byteBuffer.get() & 0xFF]`). For any message shorter than 256 bytes the
first byte of our u16 is the ZERO high byte, so the client read a length of 0 and
rendered an empty line — silently, with no decode error.

Subtle detail that made this easy to miss: the otherwise byte-identical **vicinity**
message (3152, `ck_0`) really does use a `short` for its body length, so
`buildVicinityMessage` was correct and only the private variant was wrong. The two
had been written from one template.

**Fix**: `buildPrivateMessage` now writes `[u8 nameLen][name][i64 senderId]
[u8 msgLen][message]`. Because a byte prefix caps the body at 255, an over-long
message is truncated on a **rune boundary** (the client decodes the body as UTF-8, so
cutting mid-character would render mojibake).

**Verified:** `live` — `/WHERE` and `/HELP` now print their text in the client's chat
(`de Server : pos=(40,-20,8)` and the full command list), where both were blank
before.

### B-050 · Sudden death ("Mort Subite") — the arena never collapsed
Reported from live play: *"after X turns (normally 15) the fight map starts to get
shrinked"*, followed by *"29 turns and nothing triggered by the client — it must be
server side that sends something"*. Correct on both counts: the rule exists in 2.70,
the client never self-triggers it, and our server sent nothing.

**The rule is real and configurable.** The client's i18n states the default outright
— *"Pourquoi attendre **15 tours** avant que les équipes se rencontrent"*
(`content.42.16`) — and tournament rule cards shift it: *"retarde/avance de 5/10
tours la mort subite"* (cards 788/789/790/793), with presets pinning it to turn 2
(`content.53.16`) or 10 (`content.53.19`). Hence `suddenDeathTurn` is a var.

**The mechanism.** `mh_2.java:114` binds action id **117** ("Destruction de terrain")
to class `mw_2`, whose own error string names it **MapDestruction**. The server runs
it with **8121** (`rq_2`):

```
[i32 mh_2 actionId = 117][i16 blobLen][blob][i64][i16][i8]
```

`of_1` case 8121 resolves the class from the action id, instantiates the `ZT`
animation and feeds it the blob. The blob is the same Ankama part-serialisation as a
running effect; part 0 (`yi_1`, 34 B) is
`[i64 caster][i64 target][i32 genericEffectId][i32 x][i32 y][i16 z][i32 r]`, where
**x/y/z is the spiral centre** and **r is a progressive destroy counter**.
`mw_2` then walks a square spiral outward from that centre and per cell calls
`asF.bV`, which sets the cell's movement-block bit *and* hides its graphics, then
kills every entity standing on a destroyed cell — matching the reported rules
exactly (fighters and summons die instantly; nobody can move onto a removed cell).

**Two client details that shaped the design.** `xb_2.bWv` defaults to **true**, so
`akf()` is true and the client takes the *instant* branch: it calls the param init
`mw_2.a(xb_2)` (which dereferences the effect behind `genericEffectId` — so that id
MUST resolve or the client NPEs) and destroys `Ny² − Nz²` cells in one go, ignoring
`r`. And `mw_2.aI()` is true, so the effect **requires a resolvable target fighter**.

**First attempt was wrong — it killed everyone.** I initially made the collapse a
single shot that destroyed everything outside a 5×5 core (the client's defaults).
Both teams start at y=15/16 and y=2/3, far outside that core, so *every* fighter died
the instant it fired. Reported as: *"the /suddendeath is just killing all fighters.
After 15 turns, all fighters die as well."* The mechanic is **progressive**: the arena
shrinks ring by ring so fighters can retreat, and a fighter dies **only** if the cell
under it is the one being removed.

**Fix** (`suddendeath.go`): from `suddenDeathTurn` onward, each new table turn removes
**one ring** (Chebyshev distance from the arena centre), counting inward from the
outermost until only `suddenDeathCoreRing = 2` — a 5×5 core — remains. `shrinkRing`
kills only the fighters whose own ring is the one removed; being outside the eventual
core is not lethal by itself, you simply cannot walk back out. Destroyed cells live on
the Fight (never on the shared `practiceArena` value) and are refused by
`validateFightMove`, `spellTargetValid` and `applyTeleport`.

The surviving core is chosen to match the client exactly: its spared area is the first
`Nz²`=25 cells of the spiral, which is precisely Chebyshev ≤ 2. So server and client
agree on the FINAL geometry even though the ramp is currently server-only.

**Wrong opcode — the second mistake.** I first drove the client with **8121**
(`rq_2`) and nothing rendered. 8121 only **attaches** an effect to a fighter as a
persistent buff (`of_1` does `zT.ajR().PJ().o(zT)` and flags `"hasBuff"`); it never
executes one. Executing goes through the ordinary **RUNNING_EFFECT (8120)** — the
same message damage and heals use — whose handler looks the effect up by the mh_2
action id in the `runningEffectId` field and calls `run()`.

That single change also dissolved the two blockers I had wrongly concluded were
fatal:
1. I thought the geometry was unobtainable because no shipped effect carries action
   117 and none has 2 parameters. Irrelevant: `mv_0` (the 8120 executor) calls
   `akd()` on the effect, clearing the "instant" flag, and the **progressive branch
   never reads the parameters** — `Ny/Nz` simply keep their field defaults 18/5.
2. I thought progressive mode was unreachable because `bWv` defaults to true and is
   reset to true on release. True on the 8121 path; on the 8120 path `mv_0` clears it.

So the server drives the whole collapse through **`r`**, the last field of
running-effect part 0: the client destroys the first `r` cells of its spiral list
(ordered outermost → centre). `buildRunningEffect` already writes `r` as its `value`
argument, so no new builder was needed.

**Schedule.** `suddenDeathSchedule` walks the destruction order and cuts a step every
time another `suddenDeathFloorCellsPerTurn` (12) **walkable** cells have been passed,
ending exactly on `Ny²−Nz²` = 299 — the client's own core. Counting *floor* rather
than raw spiral cells is what keeps the pace even: the arena's outer band is mostly
void, so a fixed step in `r` eats nothing for several turns and then takes a huge
bite. Measured on world 5 the arena goes 151 → 139 → 127 → … → 16 floor cells, one
bite per turn across turns 15–26.

**Animation.** The effect is sent with `mustExecNow=false`, which QUEUES it on the
client's action sequence (the 8200 flush plays it) rather than running it the instant
the frame lands — the same way damage and heals are sent, and what lets the client
animate the collapse instead of snapping the cells out of existence.

**GM aids:** `/SUDDENDEATH` applies one shrink step immediately; call it repeatedly to
watch the arena close in. **`/MAPDESTRUCT [r [x y]]`** sends ONLY the client
animation (8120, action 117) and changes nothing server-side — no cells removed, no
fighter harmed — purely to see what the client renders; `r` is the destroy count
(299 = everything outside the 5×5 core). Caveat: the client kills entities on the
cells it removes, so it can show deaths the server does not have; end the fight
afterwards rather than playing on.

**Verified:** `unit` — 9 tests, including two direct regressions for the reported bug:
a fighter on a removed cell dies while one on a surviving cell lives, and a fighter
survives the first step and dies only on the step that takes its OWN cell (both
discover the per-step cell sets empirically rather than assuming them). Plus: the
spiral matches a hand-trace of `in_0`; the destroy order is outermost-first with the
centre last and no repeats; the schedule rises monotonically and lands exactly on
`Ny²−Nz²`; the first step does not flatten the map; it does not start early; it halts
with the core intact; and removed cells are refused by move validation while
surviving neighbours stay reachable.

**Not live-confirmed:** the on-screen collapse. The harness cannot verify it — fights
started by packet injection never build a client-side match object, so that client
processes no fight frames at all (fighters do not even render). Needs a check in a
normally-started fight.

### B-049 · Special cells fired at the END of the turn and granted nothing visible
Reported: *"the special effect cells are animated at the end of the turn, it should be
at the beginning, and the fighter should get the effect."* Both symptoms, one cause
each — the mechanic itself (B-048) was correctly hooked into `beginTurn`.

1. **Late animation.** The client queues fight frames and only plays them when an
   `ACTION_SEQUENCE_EXECUTE` (8200) barrier arrives. The special-cell path broadcast
   its 6200 animation and effects but never flushed, so everything sat in the queue
   until the next flush later in the turn. Now flushed via `defer`, so every exit path
   (killer / trap / heal / buff) closes its sequence exactly once.
2. **Invisible buff.** The buff tiles changed `AP`/`Range`/`Stats` server-side and
   broadcast **nothing**, so the player saw no effect land. Each tile now emits its
   characteristic-boost running effect using the client's own action ids (AP 13,
   Range 72, Heal 78, Res% 80, Dmg% 82 — v2.04b `charcRunningEffectID` parity).

### B-048 · Arena had no obstacles and no special cells (two reported fight bugs)
Reported from live play: *"the special cell actions aren't working"* and *"with the
Iop's Bond I can jump into a cell that shouldn't be possible (the ice spike)"*.

Both came from the **same gap: our arena model was a bare altitude grid.** It knew
which cells were void, and nothing else — no obstacles, no special tiles.

**1 · Obstacles.** `cellFlag` returned `0xFC00` ("open, obstacle-free floor") for
*every* non-void cell, so the map's scenery — ice spikes, trees, the coach pedestals
— was advertised to the client as plain floor, and `walkable()` agreed. Decoded the
real data from `maps/tplg/5.jar!0_0`: each cell's ground-palette entry `cCJ == -1`
means "no walkable ground", giving **22 obstacle cells** for world 5 (independently
confirmed: all six `.fmd` coach pedestals sit on them — coaches stand *on* the
scenery). RE'd the client's `aoq_0` cell word: bits 0-5 topology layer, **bit 7**
not-a-valid-arena-cell, **bit 8** blocks LoS, **bit 9** blocks movement, bits 10-15
dynamic obstacle id. Scenery must be **`0xFFFF`** — *not* `0xFE00`/`0xFD00`, which
leave bit 7 clear so the client still draws a walkable tile on top of the spike.

`spellTargetValid` also never checked walkability at all: `NeedFreeCell` only tested
for **fighters**, so any spell could be aimed at a void cell or an obstacle — and for
a displacement spell (Bond) the caster then *landed* there. `applyTeleport` did check
`walkable()`, which is why this needed the data fix and not just a guard. Server-side
LoS was terrain-altitude-only and would have stayed more permissive than the client
(whose bit-8 test now blocks on these cells), so obstacles report an impassable
pseudo-altitude and block rays too.

**2 · Special cells.** The 8000 blob carries a `[i8 specialCellCount]` section and we
wrote **0**, so the client never instantiated a live EffectArea per tile and every
special cell was inert decoration (the *art* comes from the client's own `.fmd`,
which is why they looked present). Decoded `fight/5.jar!5.fmd`: after the coach slots
and the two team start lists it stores `[u8 count]` + N × `{i32 packedPos, i32
templateId}` — **9 special cells** for world 5. The wire tuple is
`[i64 templateId][i64 instanceId][i32 x][i32 y][i16 z]`, placed after the rule-id
list and before the `i16 mapInstanceId`. Template ids index the client's
`staticEffect` table (SPECIAL **1002-1009**, TRAP **1/2/1015-1020** — all present in
this build).

Ported the mechanic from the v2.04b reference (`internal/combat/specialcells.go`),
including its template→behaviour table: 1002 Killer · 1003 Trap · 1004 EagleEye ·
1005 Shield · 1006 Panacea · 1007 Enthusiasm · 1008 Motivation · 1009 HealingHeart.
Per the manual (§5.0.4) a tile fires **only when a fighter STARTS its turn on it**
("no use to walk on or to fly over"), and the bonus lasts that turn. World 5 carries
4× EagleEye, 2× Shield, 2× Motivation, 1× Panacea — no Killer/Trap, as befits a
practice arena. Motivation raises **both** `AP` and `MaxAP` (bumping only the current
value is silently clamped back, so the tile would do nothing), and the revert drops
the ceiling while leaving already-spent AP alone so a fighter isn't charged twice.
The tile animation is **6200** `EFFECT_AREA_ACTION`, which must reference the
**instance id** (not the template id); it is purely cosmetic — the client's
`EffectArea.execute` is empty, so it can never double-apply the effect.

**Verified:** `unit` — 6 new tests (all 22 obstacles unwalkable + `0xFFFF` + LoS-
blocking and *not* void; all 9 special cells on walkable floor with z matching the
topology altitude and 1-based instance ids; the template table; buff apply/revert for
motivation/eagle-eye/shield/panacea; "plain cell grants nothing"; spent-AP not
double-charged). Three EXISTING tests failed on the new data and were **correct to
fail** — they encoded the old wrong map: a "valid 3-step" path routed straight
through the obstacle at (7,13), an only-line cast targeted the **void** cell (4,15),
and a LoS precondition asserted a raw altitude for what is actually an obstacle.
Fixed the fixtures and added explicit coverage that targeting a void cell or a
scenery obstacle is refused. `live` — the retail client accepted the new 8000 with
all 9 tiles and logged **no** "Impossible de trouver la cellule spéciale", i.e. every
template id resolved in its registry, with no new client errors.

**Not yet live-confirmed:** that a tile actually *fires* in-client. The client
currently builds no match object from our 8000 at all (`apN.aDK().aDL()` is null —
tracked separately), so the parse may not even reach the section; and no fighter
starts within MP range of a tile, so triggering one needs a multi-round setup. The
mechanic is unit-proven but should be re-checked once the match-construction issue is
resolved.

**Open, deliberately not changed:** `validateFightMove` refuses to move a **rooted**
fighter. v2.04b's `turns.go` does the same ("a ROOTED or PETRIFIED fighter cannot move
under its own power") and also blocks displacement, but live feedback says 2.70's root
should only prevent push/pull/teleport. The two disagree, so this was left alone
pending a decompiled-client check rather than changed on a guess.

### B-047 · Spell cast (8109) was unhandled; card use (8107) cast an arbitrary spell
Reported from live play: *"I couldn't cast spell or card in fight."*

**Root cause — two distinct actions were conflated.** Spell casting and in-fight
action-card play send **byte-identical** 22-byte requests (`[i64 fighterId][i32 id]
[i32 x][i32 y][i16 z]`, arch 3), which is how they came to be treated as one:

| | class | request | reply |
|---|---|---|---|
| **Spell cast** | `alx_2` holds a spell (`yp_2`) | **8109** `mc_2` | 8110 `axn_0` |
| **Card use** | `abt_1` holds a card (`ve_0`) | **8107** `sg_2` | 8108 `arn_0` |

The server registered **only 8107** and ran the *spell* handler on it, so:
- **Spells (8109) hit no handler at all** → casting did nothing.
- **Card use (8107)** was fed into `castSpellByFighter` with the **card id used as a
  spell id**. The id spaces overlap, so playing a card could cast an unrelated spell —
  actively wrong, not merely inert.

Its own doc comment said `handleSpellCast (8109 …)` while it was registered on
`OpSpellCastRequest = 8107`, and the inventory even named 8107
`FighterCardUseRequestMessage` while asserting it was "the real spell-cast request"
and marking 8109 **"DORMANT"**. The RE was right and the wiring was wrong.

**Why no test caught it:** `internal/testclient` also sent spells on **8107** — the
harness was written to match the *server's* assumption instead of the decompiled
client, so the entire e2e combat suite passed against the wrong opcode. A harness
that mirrors the implementation cannot falsify it; it must be pinned to the client.

**Fix**: `OpSpellCastRequest = 8109` (registered to `handleSpellCast`, logic
unchanged); new `OpFighterCardUseRequest = 8107` → `handleFighterCardUse`, which
validates owner/alive/turn, requires the card to be in the coach's **equipped deck**
(the same set `writeCoachCardBlob` streams in the 8000 blob, so a client cannot play
a card it does not own), and broadcasts **8108** via `buildFighterCardUse`. The
testclient now sends 8109 for `CastSpell` and gains `UseCard` for 8107.

**Scope limit:** per-card *effects* are not resolved. Card effects use the client's
own action enum (`AI.aHI`, e.g. action 13 = resurrect), a different table from spell
effect ids, so mapping them is a separate subsystem. The play is validated,
acknowledged and animated, but applies no effect yet.

**Verified:** `unit` (full `internal/game` suite green on the corrected constants);
`e2e` (`TestCombatSpellDamage` now casts on **8109** and still resolves damage
end-to-end — the same test previously passed on 8107 and proved nothing about the
retail client); `live` (injected 8109 in a running fight: **no `unhandled opcode`**,
handler reached; previously 8109 had no handler at all).

### B-046 · Ranking window has SEVEN tabs — "Tournoi" was blank and "Ligue Pro" unhandled
Audit triggered by the question "is the tournament work actually finished?". It was
not: the tournament feature has a **second surface** that was never served.

**Two mislabels, one missing board.** `ladderInformationDialog.xml` defines **seven**
tabs, not six:

| tab | i18n key | C2S → S2C | was |
|---|---|---|---|
| 1 vs 1 | `coachType1Tab` | 27500 → 27501 | served |
| Coach | `coachReputationType1Tab` | 27508 → 27509 | served (empty) |
| 2 vs 2 | `coachType2Tab` | 27504 → 27505 | served (empty) |
| Clan | `guildType1Tab` | 27502 → 27503 | served (empty) |
| **Tournoi** | **`tournamentType1Tab`** | **27506 → 27507** | **blank — no reply sent** |
| **Ligue Pro** | **`glickoRatingTab`** | **27514 → 27515** | **no handler at all** |
| Démon | `demonReputationType1Tab` | 27512 → 27513 | served |

B-041 had labelled **27506/27507** the *"seasonal (Ligue Pro)"* board and made it
deliberately silent ("complex, 3 sub-lists"). In fact it is the **Tournoi** tab — a
tournament-**points** board — and the genuine *Ligue Pro* tab is the untouched
**27514/27515** pair. B-041's live note "the Ligue Pro tab renders correctly" was
actually the *Tournoi* tab drawing its static headers with no rows.

**Fix**: `handleTournamentLadderRequest` (27506) now replies with a real **27507**:
`[i8 month][i8 trimester][i16 year][i32 ptsM][i32 ptsT][i32 ptsY]` followed by
**three** windows (month, trimester, year), each
`[i32 total][i32 start][i32 end][i32 myRank] rows{[i32 len][name][i32 points]} [i8 search]`.
`handleProLeagueLadderRequest` (27514) replies with **27515**
`[i32 total][i32 start][i32 end][i32 myRank][i32 leagueId] rows{…} [i8 search]`, echoing
the league id (it drives the client's league-name lookup, i18n group 58, which falls
back safely if absent). Both are legitimately **empty**: no tournament match has ever
been played (the live-match layer is deferred, B-044) so no tournament points exist,
and there is no pro league.

**Crash trap:** every `afl_1` row loop indexes a **pre-sized** client list via
`list.get(n)`, and 27515 additionally *clears* slots from `end-start` up to **total**.
So an empty board must send **zero counts**, not merely zero rows — a non-zero `total`
with no rows walks off the end of that list and throws.

**Verified:** `unit` (`TestTournamentLadderEmptyIsWellFormed` locks the exact 67-byte
form, all-zero counts, `end==start` per window and the echoed selectors;
`TestProLeagueLadderEmptyIsWellFormed` locks the 21-byte form and the echoed league
id, both replaying the client's decode cursor to assert full consumption); `e2e`
(`TestLadderAllBoardsRenderCleanly` extended — the old assertion that 27507 is *never*
sent was itself the bug's fingerprint and is now inverted into a real shape check).

**Not a bug — the empty "Récompenses" panel.** The same audit checked why the
tournament window's rewards column is blank. It is **client-side dead UI**, nothing
server-owed: `qr_0.tournamentRewards` resolves `aub.aHi()` (a single reward Kard id
from local `data.bdat` type-1000) against the local card registry, and **no packet
contributes any reward data**. Both XML instances of that list
(`tournamentsOfTheDay.xml`, `tournamentListDialog.xml`) declare only an `<isNull/>`
item renderer, so the widget can *only* ever paint the empty slot art — choosing a
definition with a non-zero reward (e.g. defId 11 or 18) would still render nothing.
The neighbouring `tournamentTokenReward` binding is likewise unimplemented by `qr_0`
and always resolves to null. Documented so nobody hunts a server-side cause.

### B-045 · In-fight facing change (4521) was dropped — "unhandled opcode" on every turn
The client sends **4521 `FighterActorDirectionChangeRequestMessage`** (`lr_2`) every
time a fighter turns during a fight. The server had no handler, so each one logged
`unhandled opcode 4521` and the fighter's new facing never reached the other client
or the spectators — everyone but the acting player kept seeing the old facing.

This was masked by a **wrong premise in B-037 K**, which asserted 2.70 had "no
direction-change opcode (no 4521 equivalent)". The opcode map (`opcode_map.csv`)
disproves it: 4521 is C2S high-priority and **4522 `FighterChangeDirectionMessage`**
(`u_0`) is its S2C pair. What 2.70 actually dropped is *directional damage*, not
facing itself.

**Fix**: `handleFighterDirectionChange` (4521) validates and relays;
`buildFighterDirectionChange` emits **4522** `[i32 uid][i32 -1][i64 fighterId][u8 dir]`
(the `ue_0` action header + payload, 17 bytes). Facing is stored on
`FightFighter.Orientation` (a `qc_0` index) via `Fight.applyDirectionChange`, which
enforces that a coach may turn only its **own, living** fighter and only on **that
fighter's turn**. It is a free action: no AP/MP/position change, and a rejected
request is a silent no-op (cosmetic only — it must never desync a turn). Relaying the
client's direction byte verbatim is crash-safe for peers because `qc_0.hf()` maps any
out-of-range byte to `NONE` rather than returning null.

**Verified:** `unit` (`TestFighterDirectionChangeWire` locks the exact 17-byte 4522
layout incl. the `-1` triggeringId; `TestFighterDirectionChangeValidation` covers the
happy path, re-facing, a spoofing coach, an out-of-turn fighter, an unknown wire id
and a dead fighter); `live` — two stages:
1. **Wart removal, A/B controlled:** injecting 4521 logged **nothing**, while a
   control inject of **4523** (a genuinely unregistered C2S opcode) logged
   `unhandled opcode … opcode=4523` in the same session. The 4521 wart is gone.
2. **Real fight:** `/EVOFIGHT` (7 titular fighters) driven to the action phase, then
   a facing request injected for **all 7** wire ids at once. The server logged
   exactly **1 `fight facing`** (the fighter whose turn it actually was — …8145,
   which had advanced from …8128 since the previous log read, so the gate was
   evaluated against live turn state) and **6 `fight facing ignored … currentTurn=false`**.
   **Zero client exceptions**, i.e. the retail client decoded the 17-byte 4522.

A diagnosability gap found during that live run: the handler was originally silent on
success, so a broadcast could not be confirmed from the log (the sibling move handler
logs its rejects). It now logs both outcomes — which is what made the 1-vs-6 result
above provable. The name field is nil-guarded (`ff.Fighter` is nil for summons, cf.
`breed.go`); the unit tests call `applyDirectionChange` directly and would not have
caught that deref.

### B-044 · Tournament totem opened an empty window (subsystem now live)
The tournament totem answered `17002`/`28601` with empty `17003`/`28602` stubs, so
the "Tournois du jour" window opened blank — the single biggest untouched block. The
blocker was a belief that populating it would crash the client: the calendar entry
(`17003 awa_0`) is keyed by a content `typeId` that must resolve to a registered
prototype, and each list row (`28602 ng_2`) carries a `tournamentDefinitionId` the
client's list/detail/register paths dereference **unguarded** (`aug.registerTournament`
→ `LS.Yf().gG(defId).qo()`), so a wrong id NPEs.

**Root of the un-blocking:** a parse of the retail `data.bdat` settled a dispute — it
holds **22 real tournament definitions** (type-1000 `aub`, ids {1,4..24}), and the
calendar content id **4** decodes as a tournament (`qr_0`). 20 of the 22 have
`referenceCardId == 0` (joinable with no card). Earlier "the table is empty" readings
were a mis-parse (enum ordinal vs `getId()`, or treating `data.bdat` as one zlib
stream instead of per-record members).

**Fix** (`tournaments.go`, `handlers_totems.go`): serve a fixed set of **standing
tournaments**, each referencing a real no-card definition (defIds 1/4/17, wire kind 1
"private" so registration completes without the search/bracket flows). `17003` emits
`typeId=4` `qr_0` events; `28602` emits registerable rows; `4607` registration is
accepted (`28608 err 0`) and tracked in-memory (`TournamentManager`), reflected back
as the row's `coachStatus`; `28649` bracket requests get an empty `28650` tree.

**Two crash traps, both handled:** (1) `qr_0` reads registration-period pair **[0]**
unguarded → every event carries ≥1 pair; (2) the "of-the-day" filter (`vk_1.Cd` →
`de_2.a`) reads the two event instants **inverted** vs their field names — the
"startDate" slot (OV) is the *runs-until* bound (must be ≥ now/end-of-today) and
"extraDate" (bOF) is the *already-started* bound (must be ≤ now). My first dates had
these backwards, so the events decoded fine but were silently filtered out of the
list (empty panel, no exception); swapping them made all three appear.

**Verified:** `unit` (5: exact-consume calendar/list decode, registration status,
manager idempotency, and a guard that every standing defId is a real no-card id) +
`e2e` (4: list/calendar shape, register→accepted→re-list-registered, empty tree);
`live` — the retail totem window listed all three tournaments with real names,
per-defId illustrations, schedules and registration periods, and clicking the real
**S'inscrire** button sent a genuine `4607` (server `tid=2600002 code=0`) → *"Inscription
au tournoi acceptée"* → the row flipped to the green ✓, with **zero client exceptions**.
The live-match layer (opponent search, scheduled fights, brackets, rewards) is
deferred — it needs many coaches and wall-clock scheduling.

### B-043 · Fight deaths persist into the graveyard (evolution mode)
The graveyard could only be filled with the `/FSTATE` GM workaround — a fighter
that died in a fight came back fine, so the evolution/graveyard system never filled
from real play. Root: the server had the evolution *state* machine (states 0–5,
23000 burial, 22099 resurrection) but no fight ever wrote a death.

RE'd the rule: only the **evolution** fight mode is lethal (client `adu_0.aKl() ==
6`); ranked, "Tester" practice and PvE-challenge fights never persist deaths. It
is **server-authoritative** — the client sends no death report at end-of-fight
(only 26321 ack / 23003 requeue), and applies fighter state solely from a **6006**
roster push (`dx_2` case 6006 → `awy.b` → `ee_2.f`, state byte included). Downed
fighters land in state **2 (dead)**, not 3 (graveyard); 2→3 stays the player's
burial (23000).

**Fix**: `Fight.Evolution` flag (8000 kind byte **6**, `WE` case 8300); on
`checkFightEnd`, `persistEvolutionDeaths` sets every fighter that fell to 0 HP —
on either side — to state 2 (`FighterRepo.SetState`) and pushes 6006 to its online
coach. Only real persisted fighters count: synthetic opponents (sparring/challenge,
DB id 0, `isSyntheticCoach`) are skipped. The rule is minimal-correct "all downed
die"; the retail per-fighter death *chance* (effect-7 cards) is not modelled.
XP/tiredness/morale (the 8300 `OW` block) is a separate subsystem, deferred. The
direct-challenge evo flag now sets `Fight.Evolution`; **`/EVOFIGHT`** (GM) starts a
solo evolution practice fight so the chain is reachable without a second coach.

**Verified:** `unit` (`evolution_death_test.go`: a downed real fighter → dead(2)
even on the winning side, a survivor untouched, a NON-evolution fight persists
nothing, and synthetic fighters are never written / no phantom rows); `live`
(`/EVOFIGHT` with Loov's 7 titular fighters → `/ENDFIGHT lose` → server logs
`evolution fight deaths [...7 names]`, the DB shows all 7 at state 2 while the
un-fielded bench fighter stays state 1, and **zero client exceptions** on the
kind-6 CREATE_FIGHT + 6006 push).

### B-042 · Correctness sweep (inactive opcodes + resurrection odds)
A pass over long-standing small gaps. Research turned several "TODO" opcodes into
*decided* ones — sometimes the correct outcome is to do nothing, and documenting
why closes the gap.

- **8 InvalidClientVersion — now active.** `handleClientVersion` sends
  `[u8 major][u16 minor]` (the expected version) on a mismatch; the client
  (`oq_1`/`apN.r`) shows a modal and self-disconnects, so no server-side close is
  needed (which also dodges a write-queue/close race). `e2e`
  (`version_test.go`: a version-69 client gets opcode 8 with 2.70; the accept path
  is covered by every other login).
- **Resurrection is now a gamble (22099).** Previously always succeeded. The
  dropped card must carry a real resurrection effect (client effect **action 13**,
  `AI.aHI`) — the same gate the client applies via `akw_0.c`'s `aaF()` — and its
  `param[0]` is the success %. Roll `rand(1..100) <= pct` (mirrors `nz_1`): consume
  the card either way, revive only on success, refuse (no consume) a card with no
  resurrection effect. Required decoding the CoachCard (`aPp`) **effect array**
  (field 15): ground-truthed against `aPp.a()` — note floatParams uses an **i32**
  count, not the i8 DATA-FORMAT.md §6 implied, which would misalign the array.
  `gamedata.CoachCard.ResurrectPercent` decodes it (verified: 305/316/317/318=100,
  51=12, 53=10, 35=5, 137=1). `unit` (`cards_resurrect_test.go` real-data decode;
  `resurrection_test.go` roll boundaries/rate + handler revive/refuse/fail-consume
  against a real store).
- **1026 / 2302 / 3202 / 5202 — intentionally left inactive**, each for a
  documented, evidence-based reason (see OPCODE-INVENTORY): 1026 has no honest
  trigger in a monolithic server; 2302's whole 2300-series has no client handler
  (an empty payload would crash the client's decoder); 3202 can't fire because the
  server accepts every channel; 5202 would re-skin nothing (the overworld avatar is
  hair/skin/sex, already correct at spawn; the deck is delivered per-fight via
  8000). Fusion was found to already validate same-set inputs and roll a 60%
  success — the only unmodelled part is per-altar power levels (a reasonable
  single-altar approximation, not a bug).

### B-041 · Ladder / leaderboard panel (six boards)
The ranking window opened but every tab was blank. Root cause was two-fold. (1)
The panel is **six independent boards**, each its own opcode pair (client
controller `afl_1`): **1v1** `27500 dp_0 → 27501 azd_0`, **guild/clan**
`27502 pc_1 → 27503 ij_1`, **2v2** `27504 vg_1 → 27505 aka_0`, **seasonal
("Ligue Pro")** `27506 qk_2 → 27507 uj_0`. Only the first two opcode *numbers*
were handled, and **27502 was mislabeled as a 1v1 "compact page"** — it is the
guild board, whose rows are `clanName + leaderName + score` and whose reply the
client (`ij_1`, `if cB()==1`) **discards unless the echoed board id is 1**. 2v2
and seasonal were unhandled, so those tabs never populated. (2) The 1v1 reply's
row loop is `for j < (windowEnd − windowStart)` with **no bounds check**, so
`windowEnd` must equal `windowStart + len(rows)` exactly or the client reads past
the buffer → decode exception → blank list.

**Fix** (`handlers_ladder.go`): reclassified 27502/27503 as the guild board
(well-formed empty until guilds exist, board id pinned to 1); added 27504/27505
(2v2) and 27506 (seasonal); made `windowEnd = windowStart + len(rows)` structural
so no caller can desync it. Guild/2v2 return well-formed **empty** windows (no
guild/2v2 subsystems); the seasonal reply (`uj_0`, three monthly/quarterly/yearly
sub-lists) is **deliberately never sent** — its tab renders empty cleanly and the
other boards are independent, whereas a malformed 27507 would throw. The 1v1
board shows ranked coaches (`strength > 0`, seeded to 1000 on first ranked fight),
each row carrying rating/streak/wins/losses; the client derives rank number and
level/rank icon itself, and `myRank` (field 4) drives the self-highlight.

The remaining two tabs — **Coach** `27508 aa_2 → 27509 jw_0` and **Démon**
`27512 ow_2 → 27513 xn_2` — are the reputation family (demon/guild reputation, not
modelled). The Coach tab returns a well-formed empty reputation window; the Démon
tab lists the **24 overworld demons** (ids 1–24, 12/page) with 0 reputation and no
guild — real structure, honestly zeroed, rather than blank. The per-demon
drill-down (`27510 → 27511`, a DemonTotem or a Démon-row click) stays an empty
window; its "page" field was really the **statusFlag** (=1), so the earlier stub
was already byte-correct — just relabelled, with the 3-i64-per-row shape documented.

**Verified:** `unit` (`handlers_ladder_test.go`: a cursor replays the client's
exact per-board decode loop and asserts it consumes the whole payload — the
over/under-read that blanks the panel — plus board-id-1 and the u8-vs-i32 trailing
flags); `e2e` (`ladder_test.go`: a rated coach appears on the 1v1 board with its
stats and `myRank=1`; guild/2v2 come back well-formed empty; seasonal stays
silent); `live` (Loov set to strength 1500 → **1v1 tab shows N=1, Niveau
13(1500), Loov** — level 13 = the client's `1 + round((1500−1000)/2000·49)`; the
**2 vs 2**, **Clan** and **Ligue Pro** tabs each render with their own correct
headers and an empty list, **zero client decode exceptions**).

> **Correction (see B-046):** this entry called the panel "six boards" and labelled
> **27506/27507** the *seasonal / "Ligue Pro"* board, left deliberately silent. Both
> claims were wrong. `ladderInformationDialog.xml` has **SEVEN** tabs: 27506/27507 is
> the **"Tournoi"** tab (`ladderInformation.tournamentType1Tab`), and the real
> **"Ligue Pro"** tab (`ladderInformation.glickoRatingTab`) is a separate pair,
> **27514/27515**, which had no handler at all. The "Ligue Pro renders correctly"
> observation above was really the *Tournoi* tab rendering its static headers with no
> data. Both are served as of B-046.

### B-040 · Direct challenges ("Proposer un entraînement" / training fight)
One coach directly challenges another (instead of random matchmaking). RE'd the
26300-family flow; the wire handle throughout is the CHALLENGER's coach id.
Handshake:
- **26301** `hk_1` `[i64 targetCoachId][i8 evo]` — A challenges B →
  **handleChallengeInvite** (new `handlers_challenge.go`): a silent no-op if the
  target is offline/self or either coach is already fighting or in another
  challenge; else registers a `challenge` and pushes **26300** `wu_2`
  `[i64 handle][i8 outgoing][i8 evo][i8 nNames]{[i32 len][name]}` to B (incoming,
  name=challenger) and to A (outgoing "waiting", name=target).
- **26305** `vT` — B accepts → **handleChallengeAccept**: only the TARGET can
  accept; pushes **26302** `pu_1` `[i64 handle][i8 evo]` to both (they open the
  team panel).
- **26307** `mz_0` — B declines OR A cancels → **handleChallengeDecline**: drops
  the challenge and tells the other side **26304** `gz_0` `[i64 handle]`.
- **26303** `bl_1` `[i64 coachId][i16 teamId]` — team confirm. `handleFightReadyConfirm`
  now branches: a coach already IN a fight → the in-fight "Prêt"
  (ready-for-placement, unchanged); a coach in an ACCEPTED challenge → record its
  team, and once BOTH confirm, `startChallengeFight` builds both teams and calls
  the normal `startFightWithTeams` (non-practice) → the standard `8000` fight.

State lives in a new **`ChallengeManager`** (`challenge.go`, mirrors the
Matchmaker): thread-safe `Create`/`Accept`/`ConfirmTeam`/`Remove`, both coaches
mapped to the one challenge, at most one challenge per coach. `Deps.Challenges`
wired in `cmd/server` + both test harnesses. A disconnect cancels a pending
challenge and notifies the other coach (26304, in `session.go onClose`). Opcodes
`OpChallengeInvite/Invitation/Accept/Accepted/Decline/Cancelled` added.

Scope: only the **1v1 training** path (evolution flag carried through verbatim);
the X-vs-X-with-allies variant (26313/26314) and the setup-abort reason codes
(26310/26312) are not implemented.

`unit` (`TestChallengeManager`: create/one-per-coach/target-only-accept/
double-accept/both-confirm-starts/remove/other/evolution). `e2e`
(`test/e2e/challenge_test.go`, the two-coach path the single GUI client can't
drive): **TestDirectChallenge** — A→B, B & A get 26300 with correct
incoming/outgoing flags + handle, B accepts, both get 26302, both confirm 26303,
both receive CREATE_FIGHT(8000); **TestDirectChallengeDecline** — decline → 26304
to A, no fight.

### B-039 · Spectators (watch an ongoing fight)
Read-only spectating, reusing the B-038 `sendFightResync` snapshot path. Client
flow (RE'd earlier): the client asks whether a coach is in a spectatable fight
(**2260** `py_0` `[i64 coachId]` → **2261** `wv_2` `[i8 spectatable]`), and if so
offers "enterSpectatorMode" which sends the join (**26331** `x_0` `[i64 coachId]`).
- **handleSpectateQuery** (new `handlers_spectate.go`): replies 2261 = 1 iff
  `Fights.ByCoach(id)` is a live (non-ended) fight, else 0. Reads phase off the
  atomic (no actor hop).
- **handleSpectateJoin**: for a caller not already in/​watching a fight, binds the
  session as a spectator and, on the fight actor, adds it to the new `Fight.spectators`
  list, removes it from the overworld (SetInFight + despawn), and replays the fight
  via `sendFightResync(sess, f, spectator=true)` — the same snapshot as a resume but
  with the CREATE_FIGHT **spectator flag** set and an empty action deck (a spectator
  can't cast).
- **broadcast** now also reaches `f.spectators`, so a spectator sees every
  move/cast/turn frame and the final END_FIGHT(8300).
- A spectator **cannot act**: it owns no fighter and is not in `Fights.ByCoach`, so
  every fight-command handler no-ops for it.
- **Lifecycle**: `Session.spectating` links a viewer to its fight (touched only by
  that session's own goroutine — the actor owns the reciprocal slice, so no race).
  On fight end the spectator receives END_FIGHT and is returned to the overworld by
  the existing `handleEndFightDone` (now also clears `spectating`); on disconnect
  `onClose` posts `removeSpectator`.
- Opcodes `OpSpectateQuery=2260` / `OpSpectateReply=2261` / `OpSpectateJoin=26331` /
  `OpSpectateTeardown=26332` (26332 defined, not yet emitted). `buildCreateFight`
  gained a `spectator bool` param (byte-identical except the flag).

`unit` (`TestCreateFightEncodes`: the spectator blob differs from the player blob by
exactly one byte). `e2e` (`test/e2e/spectate_test.go` `TestSpectateFight`, the
three-client path the single GUI client can't drive): a 3rd client queries
2260→2261=1 for a fighter and =0 for a non-fighter, joins 26331, receives
CREATE_FIGHT(8000)+FIGHTER_TURN_BEGIN(8104), and receives END_FIGHT(8300) when a
player gives up.

Same documented limitation as resume: active buff/debuff icons aren't restored in
the snapshot (server keeps the buffs working). 26332 local-teardown isn't used —
spectators end via the normal END_FIGHT + overworld return.

### B-038 · Mid-fight RESUME (reconnect back into an ongoing fight)
Completes the B-034 reconnect seam: a coach who dropped mid-fight can now rejoin
and keep playing instead of being forfeited on return. RE'd the client flow —
resume is **server-pushed**: on reconnect the server pushes the empty QUESTION
**26333** (`uz_0`) while the coach is in the lobby; the client shows a Yes/No
`reconnectionInFightQuestion` dialog and replies **26334** (`aiw_1`, `[i8 accept]`
1=resume / 0=decline).
- **enterWorld** (`handlers_connection.go`): a returning coach whose team is still
  `Absent` in a live fight is now offered resume (push 26333) instead of being
  force-forfeited. The 60 s reconnect grace keeps running, so a coach who never
  answers still forfeits.
- **handleReconnectFightAnswer** (new `handlers_reconnect.go`, registered in
  `RegisterAll`): on **accept** it re-attaches the session to its `FightTeam`
  (clears `Absent`, restores `Session`, `stopGrace()`), re-marks the coach in-fight
  (SetInFight + despawn from the lobby), and replays the fight; on **decline** it
  `forfeitCoach`s.
- **sendFightResync**: replays the fresh-start sequence to the one returning
  session, sourced from CURRENT state — EnterInstance(4600, dynamic arena) →
  CreateFight(8000) → ActorAppear(4102, current cells) → FIGHTER_DIES for the
  fallen → the phase cues fast-forwarded to the live phase (8010→…→8040) → the
  current NewTableTurn(8100) + FighterTurnBegin(8104).
- **writeCombatFighterBlob** now emits the live `hpLost/mpUsed/apUsed` deltas
  (`max − current`) instead of hardcoded 0 — byte-identical at a fresh start (all
  full) but carrying real damage/spend on a resync.
- Opcodes `OpReconnectFightQuestion=26333` / `OpReconnectFightAnswer=26334` added.
- **LIMITATION** (documented): active buff/debuff icons are not restored (the
  CREATE_FIGHT effects/conditions slots are still sent empty) — the buffs keep
  working server-side, only their client icons are missing until they expire.

`e2e` (`test/e2e/reconnect_test.go`, the two-coach path the single GUI/practice
client can't drive): **TestReconnectResumeFight** — A drops, reconnects, is pushed
26333, accepts with 26334(1), receives CREATE_FIGHT(8000)+FIGHTER_TURN_BEGIN(8104),
and the fight keeps going (opponent gets no END_FIGHT); **TestReconnectDeclineForfeits**
— decline (26334=0) → opponent END_FIGHT. The old `TestDisconnectGraceThenForfeit`
was updated to `TestDisconnectGraceHoldsFightForResume` (return now offers resume,
not an instant forfeit).

Also fixed a **pre-existing** e2e bug found en route: `TestFullFightToVictory`
waited for END_FIGHT before the winner's WalletUpdate(4001), but the reward is
broadcast BEFORE END_FIGHT (`awardFightWin` precedes `buildEndFight`), so the
END_FIGHT wait consumed the 4001 and the reward wait timed out — reordered the
test to the wire order.

### B-037 · Scaled-damage element (audit item J) + hit-location deferral (K)
- **J · AP/MP-scaled damage is now ELEMENTAL.** The effect ids whose damage
  scales by the caster's current AP/MP (`151`/`152` neutral, `156`/`157` fire,
  `158`/`159` air, `160`/`161` water, `162`/`163` earth — the client tooltip's
  "par PA/PM possédé") were dealt as **raw neutral** HP loss, bypassing the
  target's resistances. `applyScaledDamage` now routes the scaled value
  (`perPoint × current AP|MP`) through `computeElementalDamage` with the effect's
  element (`damageElement` extended with the scaled ids) and the rebound step,
  exactly like a normal elemental hit. `unit` (`TestScaledDamageElement`: id→
  element map; AP-neutral 18; AP-fire 12 −50% res → 6; MP-earth 12 −flat 2 → 10;
  scaled hit feeds rebound). No practice spell casts a scaled effect, so it rides
  the damage path already live-verified in B-036 (client renders the value
  verbatim under the effect's own action id).
- **K · Hit-location / facing directional damage — deliberately NOT implemented.**
  The v2.04b engine has directional damage (`hitLocationBonus`: back +30% / side
  +15% / front +0%, gated on the effect's `affectedByLocalisation` flag). 2.70
  **dropped it**: the effect record still carries field 7 `affectedByLocalisation`
  (documented as unmodeled in `gamedata/effects.go`), and the 2.70 client i18n has
  zero front/side/back damage tooltips. Implementing it would fabricate a mechanic
  the client neither drives nor renders, so it is an intentional gap (same posture
  as the flat AP/MP-resist "no dodge roll" decision and the inert STATE_APPLY
  registry).

  > **Correction (see B-045):** this entry originally justified the gap partly by
  > claiming the server "has no facing infrastructure … no direction-change opcode
  > (no 4521 equivalent)". **That premise was wrong** — 2.70 *does* have
  > `4521 FighterActorDirectionChangeRequestMessage` / `4522
  > FighterChangeDirectionMessage`, and the client sends 4521 during fights. Facing
  > is now tracked and broadcast (B-045). The **conclusion is unchanged**: facing is
  > purely cosmetic and never feeds the damage formula, because the *damage* half of
  > the mechanic (the `hitLocationBonus` table and its tooltips) really is absent
  > from 2.70.

### B-036 · Damage-formula stat batch (audit items F/G/H/I)
Four more fight-system gaps, each ported from the proven v2.04b damage engine and
folded into the existing `combatStats` profile (new SCALAR characteristics fed by
the same buff/card machinery as the elemental stats — `Stats.apply` +
track/revert + `summary()`):
- **F · AP/MP-loss resistance (esquive PA/PM, actions 86/87).** New scalar stats
  `resAPLoss`/`resMPLoss` reduce every AP/MP drain by the reference flat-percent
  model `removed = v − trunc(v*resist/100)` (floor 0, negative resist amplifies,
  clamp to [-100,100]) — NOT a probability dodge. A plain LOSS (16/20) resists the
  full roll then caps at the current resource; a STEAL (85/103) caps at the current
  resource first, then resists, and the caster gains the resisted amount (mirrors
  v2.04b CharacLeech). `unit` (7-case formula pin + loss/steal/immune/negative/
  buff-revert scenarios).
- **G · Damage rebound (action 89, "Renvoie les dégâts").** New scalar stat
  `dmgRebound` (0-99) reflects a share of every mitigated elemental hit back to the
  attacker: `rebound = final*pct/100`, subtracted from the victim's damage and
  dealt straight to the attacker as neutral HP loss (single hop, no re-rebound, no
  transfer), guarded by `caster != victim`. Hooked into the single-target damage
  path (`applyDamageEffect`) and close-combat. Unlike the v2.04b reference — which
  leaves the caster's loss un-broadcast (a documented gap) — the reflected HP IS
  broadcast, matching the 2.70 damage-transfer (129) convention so the attacker's
  gauge stays in sync. `unit` (50% reflect, no-stat, self-hit guard, 99% clamp +
  lethal-to-attacker, buff feed).
- **H · Push/pull collision parity.** The collided-into fighter now takes the same
  collision damage on a PULL as on a push (v2.04b `applyPushPull` damages the
  obstacle regardless of direction); coefficients already matched (6/cell into
  void, 3/cell into a fighter). `unit` (pull into a blocker: both take 9).
- **I · Heal power (actions 78 up / 79 down).** New scalar stat `healPct` scales
  every heal the caster casts: `healed = base*(100+healPct)/100` (integer trunc,
  clamp [-100,100]) — a port of v2.04b `ComputeHeal`. `unit` (+50%/-50%/0% +
  buff apply/revert).

Plumbing: `combatStats` gained the four scalar fields + a `scalarStatOps` map;
`combatStats.apply` and the new `isStatBuff` predicate (used by `applyBuff` and
`computeFighterStats`) now cover elemental AND scalar buffs; the `activeBuff`
`elemental` flag was renamed `statBuff` (it reverts both). `summary()` (the
`/fight` dump) now prints `resLoss`/`rebound`/`heal`. **LIVE** (practice, /script):
a normal cast still lands damage through the refactored universal path (rebound a
no-op at rate 0) — Poolcheck spell 4 → Tanko −15 HP; the buff→`combatStats`→
`summary()` pipeline the scalar stats ride works end-to-end — a Feca armor cast
showed `stats[…Res…]` +25% all-element resist that visibly reduced a follow-up hit
(15→7); a self-buff raised MaxHP 75→100 with `allRes10%/allDmg15%`; zero client
decode errors throughout. (No practice spell carries a scalar action 86/87/89/78/
79, so the scalar mechanics themselves are unit-only, like the batch-A–E tackle/
rebound cases.)

### B-035 · Combat-mechanics + ladder batch (audit items A/B/C/D/E + P2)
Six gaps from the fight-system audit, each ported from the proven v2.04b reference
(or RE'd from the client) and verified:
- **A · Spell cast-frequency limits.** The spell record's fields 7/8/9 (decoded and
  discarded) are now stored: `CastMaxPerTarget` / `MinCastInterval` / `CastMaxPerTurn`
  (field 7 is the loader's misnamed "maxPerPlayer" but is semantically per-target).
  A per-fighter `spellCastHistory` (port of `SpellCastHistory.java`) enforces them in
  `castSpellByFighter` and resets per-turn counters in `beginTurn`. Closes the
  "recast a once-per-turn nuke/summon unlimited times" exploit. `unit` + `live`
  (Iop spell 8, perTurn=1: 2nd cast blocked, AP unchanged; re-castable next turn).
- **B · Poison / DoT scheduler.** Poison (61) now tracks an `activePoison` per
  victim (caster/params/turnsLeft) and re-rolls + re-applies it at each new table
  turn (`tickPoisons`), after the immediate first tick — a port of
  `ActiveEffectPoisonTick`. Real data confirms multi-turn poisons (Sadida 173 =
  3-turn, 455/458 infinite). `unit` + `live` (spell 193 = 5 dmg/round × duration).
- **C · Tackle / lock (zone-of-control).** `tackle.go` (port of v2.04b): leaving a
  cell orthogonally adjacent to a living enemy requires an evasion roll (67% each,
  ALL must pass; 4+ adjacent = impossible) — a fail forfeits the move and ends the
  turn, broadcasting `FIGHTER_TACKLED 4506` (`acg`, 24-byte format decompile-
  verified); and a walk stops on contact (`truncatePathOnEnemyContact`). Wired into
  `handleFighterMoveInFight`. `unit` (adjacency, truncate, 67% distribution, 4+/0).
- **D · Critical hits & fumbles.** Effect field 11 `IsCritical` is now decoded;
  fighters carry `CritRate/FumbleRate` (breed base 5/1 + card/buff actions 70/71,
  now mechanical). Each cast rolls fumble then crit (`rng+1 <= rate`): a fumble
  spends AP but applies nothing, a crit runs the spell's `isCritical` effect subset
  (`selectEffectsForCrit`); the crit/fumble flag rides the existing `buildSpellCast`
  bytes. `unit` (crit→15 / normal→10 / fumble→no-effect) + `live` (crit-rate buff
  spell 14 raised Poolcheck 5→35 via the correct normal subset).
- **E · Close-combat (weapon attack).** New opcode **8111** `CloseCombatRequest`
  (`aso_0`) → `handleCloseCombat`: an adjacent-enemy melee costing `closeCombatAP`
  (5) for the breed's close-combat element damage (`closeCombatDamages` 5 /
  `closeCombatCritDamages` 7 on a crit), replying **8112** `CloseCombat` (`aAD`,
  17/28-byte format decompile-verified). Per-breed close-combat elements added to
  the breed table. `unit` + `live` (injected 8111 → Poolcheck −5 AP, Sparring −5
  earth HP, no client error).
- **P2 · Ranked ladder now moves.** `checkFightEnd` applies
  `domain.ApplyFightStrength` (±25, seed unranked→1000, clamp [1000,3000] — a port
  of the client's `DofusArenaConstants` ladder model) to each real coach and
  populates the 8300 `YP` **strength maps** (`bA`/`bB` = `{i64 coachId, i32
  strength}`, previously sent empty) so the results screen shows the new Level/Rank.
  `unit` + `e2e` (`test/e2e/fightladder_test.go`: two real coaches, one gives up →
  winner Strength 1025 / loser 1000 persisted).

### B-034 · Mid-fight disconnect — grace period + forfeit (reconnect-ready)
A coach dropping its connection mid-fight used to call `endFight`, which just
tore the fight down and teleported everyone to the overworld — the surviving
opponent got **no victory** (no `END_FIGHT 8300`, no win/loss stat, no reward),
despite `endFight`'s comment claiming it "declared the remaining coach winner".
- **RE finding (why not just insta-forfeit):** the 2.70 client — unlike 2.04b —
  **supports reconnecting to a live fight** (S2C `26333` pops
  `reconnectionInFightQuestion`, C2S `26334` answers; it also spectates via `2261`
  + `26331`). The client's model is *keep the fight alive, turn-pass the absent
  coach, offer reconnect, and only finish with the normal 8300*. There is no
  dedicated "player left" packet. So an instant end fights the client's design.
- **Fix (grace period + forfeit, reconnect-ready):** a disconnect now routes
  through `coachLeftFight` (was `endFight`). A **practice** fight (synthetic
  opponent) still tears down. A **real** fight is KEPT ALIVE: the leaver's team is
  flagged `Absent` + its `Session` detached, its in-progress turn is passed at once,
  and its turns thereafter auto-pass (a new `beginTurn` case — the fighters are NOT
  AI-played, since the coach may return). An **independent grace timer**
  (`disconnectGraceClock`, its own generation so a turn-clock never cancels it)
  forfeits the absent coach if it doesn't return; the opponent can also just win by
  killing the idle fighters. If **both** sides are absent the fight tears down.
  Forfeit is unified in `forfeitCoach` (kills the coach's whole team incl. summons →
  `checkFightEnd`), now shared by the give-up button, the grace timeout and the
  return path. A returning coach (relogin, `enterWorld`) forfeits the abandoned
  fight so it resolves and the coach is freed — the **reconnect-ready seam** where a
  later full RESUME (the `26333`/`26334` + `EnterInstance 4600 dynamic` →
  `ActorSpawn 4096` replay) would instead re-attach and resync.
- **Verified:** `unit` (`internal/game/disconnect_test.go`: fight survives a single
  drop + team flagged absent + the leaver's turn is passed + grace/return forfeit
  hands the opponent the win with stats; both-absent tears down; practice tears
  down) · `e2e` (`test/e2e/disconnect_test.go` — TWO real coaches matchmade into a
  real fight over the wire; dropping one coach's TCP socket yields NO instant
  `END_FIGHT` (grace holds it open), and the coach returning to the world forfeits
  so the opponent receives `END_FIGHT 8300` — the win the old insta-teardown never
  delivered; stable ×3) · `live` (a normal practice turn still casts fine — the new
  `beginTurn` case doesn't disturb play — and a client drop from a practice fight
  logs a clean `fight ended (teardown)`, no panic). Only the FULL mid-fight RESUME
  (26333/26334 re-attach + `ActorSpawn 4096` resync) remains unverified; the
  forfeit victory path is now wire-proven. (The single-client GUI/MCP harness can't
  drive a two-coach disconnect — no second real coach in a practice fight and no
  close-client-without-killing-the-server control — which is why the e2e wire
  harness is the right tool here.)
- Also corrected two stale comments the audit flagged: `resolveMatchAccept` (real
  matchmade fights ARE created via `startFight`) and `endFight` (it declares no
  winner; the disconnect victory path is `coachLeftFight`).

### B-033 · Action 149 "Retire un effet" — targeted effect removal (closes the B-032 nuance)
The remaining B-032 nuance — on a mask *switch* the displaced mask's self-aura and
stat malus lingered — is now fully resolved by implementing the general
targeted-removal mechanic the client uses, effect **action 149** (handler `dw_0`).
- **Client spec (RE'd from `dw_0.java`):** 149 iterates the target's running-effects
  and removes every one whose **source effectId** (`bWj.ST()`, field 1 — NOT
  actionId/parentId/spellId) equals `params[0]`; `params[1]` caps the count
  (default −1 = all), `params[2]` is an optional gate. Removal is delegated to each
  effect's ordinary unapply (`aky()`→`aK()`) — i.e. **identical to an early
  expiry**: the state bit is cleared, the buff's characteristic reverted, the aura
  area destroyed. It is a fully general mechanic (mask spells are just its heaviest
  user).
- **Server implementation:** every applied buff/state/aura now carries its **source
  effectId** — `activeBuff.effectID` (+ an `infinite` flag), `FightFighter.stateSrc`
  (a `state→effectId` map, stamped in `applyState`), and `effectArea.effectID`.
  `KindRemoveEffect` (149) → `applyRemoveEffect` → `removeEffectByID(ff, effectId,
  limit)` strips matching buffs (via the new shared `revertBuff`), states, and
  self-placed auras, and broadcasts the 149 so the client's `dw_0` drops the same
  effects. **Infinite buffs are now TRACKED** (flagged `infinite`, skipped by
  `tickBuffs`/`applyDispel`) instead of fire-and-forget — required so a mask's
  permanent (`dur=[63,0]`) malus can be reverted by 149. This **replaces the B-032
  ad-hoc mask-state exclusivity**: masks are now made mutually exclusive purely by
  the shipped data (each switch spell bundles ~15 action-149 removes), so the whole
  displaced mask — state, malus AND aura — is stripped, not just the state.
- **Data (enumerated from `data.bdat`):** each mask = distinct effectId per
  component — Class state `9192`/malus `9213`(102 MP−)/aura `9260`(176 tmpl1017),
  Coward state `9193`/malus `9215`(73 range−)/aura `9261`(1018), Berzerk state
  `9194`/malus `9217`(81 res%−)/aura `9262`(1019). Spell 471 (→Berzerk) removes the
  Class+Coward components (`9192,9213,9260,9193,…`), etc. Only 5 shipped spells use
  149 (15 Ecaflip, 444 Rogue, 471/472/473 masks) — all handled generically.
- **Verified:** `unit` (`TestRemoveEffectByID`: a Class bundle — state + infinite MP
  malus + aura — each stripped and reverted by its effectId, and the infinite malus
  is NOT aged by a normal tick; rewritten `TestMaskStates`; `TestBuffLifecycle`
  updated for infinite-tracking) · `live` (cast Class 473 → `[maskCls]`, MP 3→2,
  aura 1017; switch to Berzerk 471 → `[maskBzk]` ONLY, **MP reverted 2→3**, aura
  **1017 gone**, only 1019; switch to Coward 472 → `[maskCow]` ONLY, **Berzerk
  `allRes-25%` reverted**, aura **1019 gone**, only 1018; zero client decode errors
  across all 17-per-switch removal broadcasts). Nothing lingers on a mask switch —
  the carry/aura/mask exotic-effect line is now complete with no deferrals.

### B-032 · NB_SUMMONS + Masqueraider masks (closes the B-031 deferrals)
The three items B-031 consciously left behind, now resolved with client-RE +
real-data evidence (v2.04b cross-checked where it existed):
- **NB_SUMMONS characteristic (action 74) — now mechanical.** `criteria.go`'s
  `canSummon` was `livingSummons < 1 + nbSummons()` with `nbSummons()` hardcoded 0,
  so the cap was stuck at one. The client (`ahG.java`) reads characteristic
  `Lr.bqW` = **id 26**, and effect **action 74** ("Augmente le nombre d'invocs",
  `mh_2.java:83`, bound to `Lr.bqW`) raises it — exactly as the v2.04b reference
  applies it (`74 → NbSummons`). Wired: new `BuffSummons` `BuffResource` +
  `resourceBuff[74]`, a `FightFighter.NbSummons` field, an `applyResourceDelta`
  case, and `nbSummons()` reads the field. **Two subtleties the shipped data
  forced:** (1) action 74 is **param-signed** — spells 55/79/450 (Osamodas/Sadida/
  Rogue) carry `params=[1] dur=[63,0]` (a permanent +1 slot) while spell 476
  (Masqueraider) carries `params=[-1] dur=[1,0]` (a 1-turn summon-*steal*); since
  `Roll()` returns a positive magnitude, the buff uses `signedFirstParam` so the
  steal subtracts. (2) NB_SUMMONS is stored **unclamped** (the client charac is
  bounded by Integer.MIN/MAX): a steal can push the effective cap (1+NB_SUMMONS)
  to 0, and clamping-at-write would break apply/revert symmetry (a steal on a base
  summoner would leak a summon back on expiry). Infinite (+1) buffs apply
  permanently (untracked); finite (−1) steals are tracked and reverted.
- **Masqueraider masks (actions 173/174/175) — now settable.** The three mask
  states (`stateMaskClass/Coward/Berzerk`) had no setter, so their
  `canCastWhenMask*` criteria were dead. The client grants them via three distinct
  effect ids — **173 Class / 174 Coward / 175 Berzerk** (`mh_2.java:168-170`,
  handlers `acl_2`/`Ew`/`akp_2` → states `avx_0.deH/deI/deJ`), shipped on spells
  **473/472/471** (`dur=[63,0]` infinite). Wired into `effectkind.go` (`KindState`)
  and `stateByAction`. Masks are **mutually exclusive**: `applyState` strips the
  other two when one is donned (the client does this via the grant spell's bundled
  removes + each spell's `cannotCastWhenMask<self>` self-gate). NB: they belong to
  breed 14 (Masqueraider), **not** the Sram (breed 4) — B-031's "Sram masks" note
  was a misnomer.
- **akw_0 coach-card criteria — deliberately NOT implemented (verdict, not a
  punt).** Deep RE (`akw_0.java` + 20 subclasses, `AI.java:6-26`, `aap.java`)
  proves it is a post-fight **reward/roster meta** system: every subclass modifies
  XP / drops / injuries / death-chance / morale / fatigue / resurrection /
  reputation / "apply a persistent condition", and **none** touch live combat
  (HP/AP/MP/cells/spells). It has **no protocol dependency** — the client reads
  full card definitions (criteria included) from its own local `data.bdat`; the
  server transmits only ownership (template ids/quantities/equip slots), which it
  already does. Both servers run fights correctly with it unparsed; v2.04b ignored
  it entirely. The current parser (`cards.go`) reads type-100 fields 1–5 and stops
  — correct and safe. (If the reward *economy* is ever wanted, the work is scoped
  in memory: parse fields #15/#19, port the 20 subclass effects + `aap` mask
  predicate + `adl_0` fold + `operator`=set-tier logic + the `aiz_2` condition
  system.)
- **Verified:** `unit` (`TestNbSummonsBuff`: infinite +1 persists through a tick,
  param-signed −1 steal reverts symmetrically; `TestMaskStates`: set → criteria →
  exclusive switch → infinite no-age) · `live` (real spell 55 lifted Poolcheck's
  cap 1→2, second Gobball then summoned on a fresh 6-AP turn; mask spell 473 set
  `[maskClass]`, its re-cast was blocked by `cannotCastWhenMaskClass`, spell 471
  switched to `[maskBzk:63]` stripping Class; zero client decode errors throughout,
  incl. the masks' bundled resistance malus + self-auras).
- **Remaining nuance (documented, not hand-waved):** on a mask *switch* the
  displaced mask's self-aura (`template 1017/1019`) and stat malus linger, because
  the real client removes them via the grant spell's bundled **action-149** "Retire
  un effet" entries. Faithfully modelling that needs a general targeted-effect-
  removal mechanic (per-effect-id tracking on buffs/states/areas) — a separate
  subsystem, breed-14-only, absent from the practice flow. The core mask STATE +
  criteria gating (the actual B-031 gap) is complete; the state is always
  single-masked, so criteria are always correct.

### B-031 · Spell cast-criteria + carried-fighter occupancy (completes B-030)
- **Full criterion parsing (`criteria.go`):** the spell's field-20 `criterion`
  string (previously discarded) is now decoded and enforced. It is a
  `;`-separated list of case-insensitive named tokens combined with implicit AND
  (empty = no gate, unknown = permissive) — NOT an operator grammar — ported from
  the client's CriteriaCompiler (ahp_1). All 15 tokens implemented, reading only
  caster state: `canSummon` (living summons < 1+NB_SUMMONS), `can/cantCastWhen
  Carrying`, `cantCastWhenCarried`, `canCastWhenDying`/`Injured` (HP ≤ 25%/99%),
  `canCastWhenDrunk`, `can/cannotCastWhenMask{Class,Berzerk,Coward}`,
  `canCastWhenCarryAlly`/`Ennemy`. Enforced in `castSpellByFighter` before AP is
  spent — this SUPERSEDES the B-030 hardcoded carry check (which wrongly blocked a
  carried fighter from casting ANY spell; the real data marks only 4 spells with
  `cantCastWhenCarried`). Real data uses all 15 token families (57 spells;
  `cantCastWhenCarrying`×24, `canSummon`×18). Drunk (126) moved from a render-only
  KindVisual to a tracked KindState so `canCastWhenDrunk` can read it; the three
  Sram mask states are tracked-but-unset (their `canCastWhenMask*` spells stay
  gated off — faithful, as no effect grants a mask yet).
- **Carried-fighter occupancy:** `cellHeldByOther` and `cellOccupied` now skip a
  carried fighter — it is held on its carrier's cell and is not an independent
  board obstacle (the carrier already holds the cell).
- **Verified:** `unit` (TestCastCriteria, rewritten TestCarryCastGating with real
  criterion tokens) · `live` (drunk spell 407 → `[drunk:63]` state rendered, no
  decode error; summon spell 110 cast twice → the SECOND was blocked by `canSummon`
  at the 1-summon limit — fired=false, timeline unchanged, cell adjacent + AP
  available). This closes the carry/aura work with nothing left behind.

### B-030 · Carry/aura fidelity polish — cast gating, untargetability, aura target conditions
- **Cast-while-carried gating** (`castSpellByFighter`): a carried fighter cannot
  cast at all; a carry spell (a KindCarry effect) needs an empty grip; a throw
  spell (KindThrow) needs to be carrying someone — mirrors the client's
  cantCastWhenCarried / can(t)CastWhenCarrying criteria, rejected before AP spend.
- **Carried-fighter untargetability** (`fighterAtCell`): a carried fighter, which
  shares its carrier's cell, is skipped by the single-target selector, so a cast
  on that cell resolves onto the carrier (the front fighter) — the carried one is
  protected. (Area effects still catch it; only single-target is redirected.)
- **Aura target conditions** (`fireEffectArea`): an aura ticks everyone in its
  radius, so its inner effects now respect their target-condition mask (an
  enemies-only debuff aura skips allies). A trap still fires unconditionally on
  whoever stepped on it (unchanged, preserving B-025).
- **Verified:** `unit` (TestCarryCastGating, TestCarriedUntargetable,
  TestAuraTargetFilter). Logic-only — no new wire message — building on the
  live-verified carry/aura paths (B-029).

### B-029 · Hard exotic batch — carry/throw, aura, line/zone damage, damage-transfer
- **Carry/throw (58 "Porter" / 59 "Jeter"):** direct port of the v2.04b resolver
  (`carry.go`). Bidirectional `CarriedFighter`/`CarriedByFighter` links; carry
  stacks the target on the caster's cell, moving the carrier drags the passenger,
  throw drops it at the target cell (no landing damage from the carry itself);
  a carried fighter dismounts when it walks, and death breaks the links.
- **Aura (176 "Pose une aura"):** a caster-FOLLOWED effect area — `effectArea`
  gained `follow`/`turnsLeft`; its centre tracks the caster's live cell, it fires
  on turn-start for fighters in radius (reusing `checkEffectAreasTurnStart`, but
  never on its own caster), lives for its duration and dies with the caster.
- **Zone MP-loss (177):** `applyZoneMPLoss` drains `params[0]` MP from every
  fighter in the spell's zone centred on the CASTER (caster excluded).
- **Line damage (178-181):** `applyLineDamage` hits every fighter in the
  axis-aligned bounding box spanned by caster↔target (both excluded), each via the
  real elemental formula, no flanking bonus. `damageElement` extended (178 fire /
  179 water / 180 air / 181 earth).
- **Damage transfer (129):** no v2.04b reference — a derived link (`damageTransfer`
  on the fighter) hooked in `applyHPDelta`: a % of the bearer's incoming damage is
  redirected to the caster (one hop, no re-check). Direction/percentage may need
  live refinement.
- **Verified:** `unit` (TestCarryThrow, TestLineDamage, TestZoneMPLoss,
  TestDamageTransfer, TestAura) · `live` (Pandawa spell 126 carried Tanko onto
  Poolcheck → walking dragged him → spell 127 threw him to (10,15); spell 468
  line-fire hit Tanko in the box for 10, sparing the off-row Sparring; spell 463
  placed 3 caster-followed auras — all with no client decode error). 129 has no
  castable spell in the shipped data (unit-only).

### B-028 · Exotic-effect batch — skip-turn, dispel, client-visual effects
- **Scope:** a batch of small deferred effects, resolved from their exact mh_2
  labels (decompiled `mh_2.java`):
  - **Skip-turn (56 "Fin de tour" / 111 "Passe son tour"):** a new `stateSkipTurn`
    — `beginTurn` passes the fighter's turn and consumes one skip (it is spent per
    skipped turn, not aged per round by `tickStates`).
  - **Dispel (62 "Désenvoûtement"):** `applyDispel` reverts every tracked buff
    (resource + elemental stat) and clears every timed state on the target.
  - **Client-visual effects (60/98 look change, 126 "Devenir ivre" drunk, 139
    "Redirection des dégâts (purement visuel)"):** new `KindVisual` — broadcast the
    running-effect so the client renders/animates it, with no server mechanic.
- **Classifier:** `KindDispel` + `KindVisual` added; 56/111 added to `KindState`.
- **Verified:** `unit` (TestSkipTurnState, TestDispel) · `live` (Tanko buffed via
  spell 32 → `[stab:5] stats[fRes0/25% aRes0/25%]`, then dispel spell 40 cleared
  ALL of it; drunk spell 407 rendered with no decode error). Skip-turn has no
  castable spell in the shipped data (trap/monster-only), so it is unit-only.
- **Still deferred:** carry/throw (58/59), aura (176), damage-transfer (129) and
  the zone-triggered MP/HP-loss variants (177/178) — they need real
  positioning/link/follow-caster models, not a render-only path.

### B-027 · Damage formula — elemental resistances + damage bonuses (buffs made mechanical)
- **Symptom:** combat dealt FLAT damage — the whole family of resistance/damage
  buffs (mh_2 21-55, 80-83) rendered on the client but did nothing, so every
  fight with a resist/damage buff was mis-scored, and the gap grew as more buff
  spells were used. This was the last big deferred piece of B-020.
- **Model (`combat_stats.go`, ported from the proven v2.04b `damage.go`):** a
  `combatStats` per fighter (per-element flat + % damage, per-element flat + %
  resist, all-element damage %/resist %). Populated from equipped-card passive
  effects at fight build (`computeFighterStats`; the breed contributes none) and
  mutated by in-fight buffs. Values stored unclamped so a timed buff reverts by
  exact subtraction; the Dofus bounds (flat resist ≥ 0, percents ±100) apply at
  read time.
- **Formula (`computeElementalDamage`):** `value = base + casterFlatDmg[e] −
  max(0,targetFlatRes[e])`, then a single percent modifier `casterDmg%(e+all) −
  targetRes%(e+all)` applied LAST, floored at 0. **Neutral/physical (and poison)
  bypass everything** (raw value) — verified against the v2.04b PHYSICAL
  short-circuit. Element from the action id: 1/130 neutral, 2/131 fire, 3/132
  earth, 4/133 water, 5/134 air (leech 6-10 same). `applyDamageEffect` now runs
  it; `applyBuff` applies the elemental stat buffs mechanically + reverts on
  expiry. The `/fight` dump shows a fighter's `stats[…]`.
- **Verified:** `unit` (TestDamageElement, TestElementalStatOpsApplyRevert,
  TestComputeElementalDamage — flat-before-percent, floor-at-0, clamps, neutral
  bypass; TestDamageWithResistBuff; TestBuffLifecycle updated) · `live` (Tanko
  spell 32 → dump showed `stats[fRes0/25% aRes0/25%]`; then ONE fire circle hit
  both Poolcheck (0 fire res, took **19**) and Tanko (25% fire res, took **14** =
  19×0.75 floored) — exact 25 % reduction, no client desync).

### B-026 · AoE geometry — the real zone size (decoder off-by-one) + Manhattan circle
- **Symptom:** every circle/cross/ring/T spell collapsed to a single cell (the
  fighter on the aimed cell, or NOBODY when that cell was empty). B-022 blamed
  "radius baked into the shape ordinal"; that was wrong.
- **Root cause 1 — decoder off-by-one.** The client's `Ht` effect record has SIX
  `int[]` arrays after `params`, then one `int64[]`: triggersBefore, triggersAfter,
  endTriggers, a **vestigial** array (`Tf`/`beH`, never populated → always empty),
  **areaSize** (`Tg`/`beI`, DB column `effect_area_size`), duration; then targets.
  `effects.go` read areaSize from the **4th** array (the empty vestigial one)
  instead of the **5th**, so every real zone arrived size-less and
  `areaFighters` hit its point-fallback. Fixed by reading the 5th array (duration
  and targets were already correct, so it is a safe one-line swap). Confirmed on
  real data (`TestSpellAreaSizeReal`): circle(2)=23, cross(3)=5, ring(5)=10,
  T-inv(9)=8 effects now carry a size; point(1) and all(32767) carry none.
- **Root cause 2 — Euclidean circle.** A Dofus "circle" is a **Manhattan diamond**
  (`|dx|+|dy| ≤ r`, client `nw_0`), not a Euclidean disk — they diverge at r≥3.
  `area.go` used `dx²+dy² ≤ r²`. Fixed, and the real shapes were added: ring (5,
  diamond annulus), square (6), and inverted-T (9, directional). T/inverted-T
  orient the stem along the caster→target cardinal step (client `sp_2`/`arG`).
- **Verified:** `unit` (TestSpellAreaSizeReal on real data; TestPointInArea now
  asserts the diamond-vs-Euclidean divergence at r3, plus ring + inverted-T) ·
  `live` (circle-r2 spell 128 cast at (8,15) between two fighters dist-1 apart →
  BOTH took 19 fire damage, the far fighter untouched, no client decode error;
  pre-fix the same cast on the empty centre cell would have hit no one).

### B-025 · Traps / glyphs — persistent ground-effect areas (action 66)
- **Scope:** the "Pose un piège" effect (mh_2 action 66, client handler `ds_1` /
  SetEffectArea) — recognised but skipped before. A spell effect with action 66
  places a persistent area on the battlefield that replays a template's inner
  effects on whichever fighter triggers it.
- **Data:** action 66's `params[0]` is a **type-210 StaticEffect** template id.
  New loader `gamedata/effectareas.go` decodes all 16 shipped templates (8 TRAP +
  8 SPECIAL) — id/type/label/areaShape/maxExec/appCondition + the trigger id
  arrays + the embedded inner-effect list (exact `rf_2` layout, string=`[i32]`,
  arrays=`[i32 count]`, BitSet stored as a plain id array). Validated against the
  known records (id=1 point trap, id=2 circle r2).
- **Runtime (`game/effectarea.go`):** `applySetEffectArea` places an `effectArea`
  (unique id, footprint, caster, `maxExec`) at the cast cell and broadcasts the
  action-66 RUNNING_EFFECT (no target fighter → target mirrors the caster, like
  teleport; the client reads the template id from the VALUE field). Triggering is
  server-authoritative: `checkEffectAreasMove` (hooked per step into
  `applyFighterMove`) fires a **walk-on** trap (trigger id 10001) when a fighter
  enters the footprint; `checkEffectAreasTurnStart` (hooked into `beginTurn`)
  fires a **turn-start** glyph/special (id 10000). Firing replays the template's
  inner effects through the normal resolver (each broadcasting its own
  RUNNING_EFFECT, so the client renders the trap's damage/state with no bespoke
  message), then decrements a finite `maxExec` and self-removes when exhausted
  (`>=63`/`<0` = unlimited). `KindTrap` added to the classifier; the dev `/fight`
  dump lists live areas.
- **Verified:** `unit` (loader real-data TestLoadStaticEffectsReal — 16 templates;
  TestApplySetEffectAreaPlacesTrap, TestTrapTriggersOnWalkOnAndExhausts,
  TestTrapWalkOnViaApplyFighterMove, TestTrapUnlimitedNotRemoved) · `live` (Sram
  spell 153 placed two circle-r2 traps at (8,13) with no client decode error;
  Tanko walked in → `[invis:3]` applied (walk-on 10001); Poolcheck standing in it
  at turn-start → `[invis:2]` (turn-start 10000); both areas persisted, unlimited).

### B-024 · Deep-combat test harness — the `/script` fight-scenario runner
- **Problem:** deep combat testing against the retail client was hand-driven raw
  `/c2s` opcode injection in a PowerShell poll-act loop, which fought the 30s turn
  clock and let short (1-turn) buffs/states expire between slow MCP tool calls — a
  scenario spanning several fighters' turns within ONE round could not be
  expressed reliably.
- **Fix:** a DEV-only `/script` endpoint (`game/debug_script.go`) that runs a whole
  fight scenario IN-PROCESS on the fight actor in milliseconds. `Fight.callSync`
  posts a closure to the fight mailbox and blocks for its result (a synchronous
  "call an actor" over the fire-and-forget `Post`), so each step touches fight
  state race-free. Commands (`;`-separated): `goto <wire> [ai]` (advance turns,
  skipping or AI-playing intermediates), `move`, `cast`, `castself`, `end`,
  `dump`, `wait`. `DebugDump` was refactored to a reusable `Fight.writeSnapshot`
  (now also prints `round=`); added `FightManager.Only()/Get(id)`.
- **Verified:** `live` — a single call did `goto Tanko → castself 34 (immune+stab)
  → goto Poolcheck (auto-skip the Sparring AI) → move (BFS) → cast damage at the
  immune Tanko`, and Tanko's HP stayed 70/70 (immunity blocked it); a second call
  confirmed the 1-turn state ticked away a round later; used throughout B-025.

### B-023 · Status states — root / petrify / stabilise / invisibility / immunity
- **Scope:** the state-effect family (mh_2 65/127 root, 96 petrify, 94/128
  stabilise/intransposable, 57 invisibility, 95/124 immunity) — recognised but
  skipped before. They now apply, render, tick down and are ENFORCED server-side.
- **Wire:** a state is an ordinary running effect (the standard 3-part blob,
  value 0, `Nx` = duration) whose action id selects the client handler that
  renders + tracks it — same shape as a buff (already proven). The server mirrors
  it with a per-fighter remaining-turn count (`states.go`, ticked each new round
  alongside buffs) and enforces the rule the client cannot on a server-driven
  (summon/AI) fighter.
- **Enforcement:** rooted (65/127) → `validateFightMove` + the AI movement reject
  the move (and MP is zeroed to match the client's rc_0); petrified (96) →
  `beginTurn` passes the turn on the short clock; stabilised (94/128) →
  `applyPushPull`/`applySwap` no-op on the target; invisible (57) → the AI's
  `nearestOpponent`/`minEnemyDistance` skip it; immune (95/124) → `applyHPDelta`
  blocks damage (a heal still lands). `KindState` added to the classifier; the dev
  `/fight` dump now prints a fighter's states (e.g. `[immune:1 stab:1]`).
- **Verified:** `unit` (TestClassifyState, TestApplyStateAndEnforcement,
  TestPetrifiedSkipAndTickStates) · `live` (Tanko spell 34 → `[immune:1 stab:1]`
  applied + client-rendered with no decode error; then Poolcheck cast a 25-damage
  spell 4 on the immune Tanko — it paid AP and animated but Tanko's HP stayed 45,
  the immunity blocking the damage).

### B-022 · Area-of-effect + target conditions — area spells hit the right fighters
- **Scope:** effects only ever touched the single fighter on the target cell.
  Area spells (circle/cross/T and the `32767` "Target: All" sentinel) now hit
  every fighter in the zone, and the per-effect **target-condition** mask decides
  who each expanded target legitimately affects.
- **Why it was subtle (2.70 data specifics):** an area spell's `AreaSize` field
  (16) is EMPTY in the 2.70 data (the geometric radius is baked into the shape
  ordinal — a later RE), so the common non-point area is `AreaShape 32767`
  ("all"), used by SELF-BUFFS: e.g. Iop spell 7 is `32767` with `Targets=[2]`
  (IS_CASTER). Naively expanding `32767` to "all living fighters" would have
  wrongly buffed the enemy team (a desync bug). The fix decodes the effect
  record's `targets` (field 19, i64[]) and ports the client's
  `FightTargetValidator` (`target_conditions.go`: IS_CASTER/ALLY/ENEMY/HUMAN/
  SUMMONED + breed bits; valid if ANY condition passes, a condition passing iff
  ALL its bits hold; empty = permissive).
- **Design:** AoE is server-authoritative — `area.go` `areaFighters` expands the
  aimed cell (point hit-test ported from v2.04b: circle = Euclidean r², cross =
  row/col arms, T = directional beam+bar, empty = all living) and the resolver
  applies the effect once per hit fighter (each broadcasting its own 8120 — no
  new wire). A single-target (point) effect is NOT re-filtered (the client
  already validated the aimed cell); only the SERVER-EXPANDED area/all targets are
  filtered by the target conditions. `resolveEffect` now dispatches
  positioning/summon single-target and loops `areaFighters` → `applyPerTargetEffect`
  for every cell-targeting kind.
- **Superseded:** the original note here claimed the geometric zone SIZE was
  "baked into the shape ordinal" (empty `AreaSize`), leaving circle/cross to fall
  back to point. That was WRONG — it was a decoder off-by-one; see **B-026**,
  which reads the real `AreaSize` and gives circle/cross/ring/T their true radius.
- **Verified:** `unit` (TestPointInArea, TestAreaFighters, TestAreaTargetConditions,
  TestResolveEffectAreaDamage) · `live` (spell 7 self-buff → Poolcheck MaxHP
  75→135 while ally Tanko + enemy Sparring stayed 70, proving the IS_CASTER filter;
  spell 9 hit-all → all three fighters took 5 in one cast; client rendered both
  with no decode error).

### B-021 · Summons + AI — summoned creatures spawn, render and are played by a built-in AI
- **Scope:** the summon effect family (67 "Invoque une créature", 75 "double", 97
  "mirror") plus the AI that plays any fighter no client controls (a summon AND
  the sparring opponent, which previously just idled until its turn clock).
- **Client wire (RE of `hy_1`/`api_0`/`mv_0`/`jz_2` + memory #180):** there is NO
  add-fighter message — the client CREATES the summon itself from an 8120
  RUNNING_EFFECT whose action id is 67/75/97. `hy_1.execute` builds the fighter
  via `gn_0.d(nv, cell, templateId)`, where **nv (the new fighter id) is read from
  part-2** (the `api_0` codec, same i64 wire as a normal target ref) and the
  **template id is read from part-0's VALUE field** (`yi_1.f` sets `r` = value —
  so no client compute path is needed, which is essential because the client's
  `adu_0.al()` id-allocator throws in a real fight). So a summon is just the
  standard `buildRunningEffect` with `value=templateId, targetWireID=nv`. The
  client then inserts the fighter into team + timeline + renders the avatar via
  the same `qg_2.g` path as ACTOR_APPEAR — no separate 4102.
- **Server:** `internal/gamedata/summonings.go` decodes the type-300 `jz_2`
  template (`id, HP, AP, MP, [i8]i32 spellIds, i32 look`; 53 templates load).
  `internal/game/summon.go` `applySummon` allocates a summon wire id in a
  collision-free namespace, builds the `FightFighter` from the template (Father =
  caster, SummonSpellID = template's first spell), inserts it into the team +
  the turn timeline right after the caster (matching the client so both timelines
  stay in lock-step), and broadcasts the 8120. `KindSummon` added to the effect
  classifier.
- **AI (`internal/game/ai.go` + `pathfind.go`, ported from the v2.04b
  `summon_ai.go`):** `reachableCells` is a 4-directional BFS movement flood
  (bounded by MP, blocked by fighters). `runAITurn` — armed from `beginTurn` on
  the short AI clock instead of a bare force-pass — derives the archetype from the
  fighter's spell (no spell → blocker; damage → aggressive/kite; debuff → kite;
  self-buff → self-buff), then closes to spell range (or adjacency), casts until
  dry, optionally retreats, and ends its turn. The move/cast internals were
  factored out of the handlers (`applyFighterMove`, `castSpellByFighter`) so the
  AI drives them exactly like a player.
- **Verified:** `unit` (TestApplySummon{,FallbackStatsAndBlockedCell},
  TestReachableCells, TestNearestOpponent, TestMoveTowardNearestOpponent,
  TestClassifyAIBlocker, TestRunAITurnBlockerEndsTurn, TestLoadSummoningsReal) ·
  `live` (injected Osamodas summon spell 110 → a Gobball creature (template 1,
  20 HP) spawned at the target cell, client rendered it + grew the timeline to 4
  with no decode error; on its turn the summon AI-walked toward the enemy; and the
  sparring dummy — a spell-less blocker — now advances on the players each turn
  instead of idling).

### B-020 · Spell effects: full effect system (positioning, buffs, damage variants) — completes B-017
- **Scope:** B-017 resolved only damage/heal/AP-MP; push/pull/teleport, buffs and
  the damage variants were deferred. This wires the whole set the 203 shipped
  spells use (98 distinct mh_2 action ids), classified once in
  `gamedata/effectkind.go` (`EffectKind`) and dispatched in
  `game/spell_effects.go`. Now handled: damage (direct 1-5 / "par sort" 130-134)
  with the real **dice roll** (`Effect.Roll`: 1-param fixed, 3-param
  `[count,faces,mod]` — the old params[0]-only under-reported dice damage), HP
  **leech** (6-10, heals the caster), **heal** (69), **%HP** (125), **poison**
  (61, first tick), AP/MP **loss** (16/20) / **steal** (85/103) / **gain** (15/19),
  **instant death** (63), **teleport** (39, caster→cell), **swap** (64),
  **push/pull** (37/38, faithful ray-trace port of the client's `na_2`/`sa_2` +
  the v2.04b collision formula: `cellsBlocked × (void?6:3)`), and timed
  **characteristic buffs** (CharacBuff/Gain/Debuff/Loss) — resource buffs
  (AP/MP/HP/Range) modelled + reverted on expiry, pure-stat buffs rendered.
- **Key wire facts (RE of `mv_0.ax`/`xb_2`/`yi_1`/`mh_2`, cross-checked vs the
  v2.04b resolver):** (1) the client renders `RunningEffect.getValue()`
  **verbatim** and never re-rolls on receive (`disableValueComputation`), so the
  server value is authoritative. (2) A buff's **duration rides in the 8120 `Nx`
  field** (`Nu.jt(Nx)`), NOT the blob — `buildRunningEffect` gained a
  `durationTurns` arg; instant effects pass 0. (3) The effect record's field 18
  (`duration`, ≥63 = infinite) and 16 (`areaSize`) are now decoded
  (`gamedata/effects.go`, six consecutive i32[] after params per the client's `Ht`
  deserializer). (4) A per-fight RNG (`Fight.rng`) rolls dice; buffs tick down at
  each new table turn (`tickBuffs`).
- **Deferred at the time (now mostly DONE):** summon (B-021), trap/glyph (B-025),
  states — invisibility/root/stabilise/petrify/immunity (B-023), AoE expansion
  (B-022/B-026), and the resist/damage-% damage formula (B-027) are all since
  implemented. Still deferred: aura (176), carry/throw (58/59), dispel (62),
  look-change, damage-transfer (129/139), drunk (126) — documented KindUnsupported
  no-ops (the cast animates; the exotic effect is a safe no-op).
- **Verified:** `unit` (TestEffectKindClassification, TestEffectRoll,
  TestDurationTurns, TestBuffResource, TestResolveEffect{,HPVariants,Positioning},
  TestBuffLifecycle, TestCardinalStep) · `live` (Poolcheck spell 7 → HP **75→135**
  from the infinite HP-boost buff, client orb rendered 135; spell 6 → **teleport**
  (7,15)→(5,15) rendered; Tanko spell 32 self resist-buff rendered with no decode
  error; AP debited correctly throughout).

### B-019 · Fighter movement rejected — the client's 4503 path EXCLUDES the origin cell
- **Symptom (live):** clicking a destination in a fight never moved the fighter
  ("nothing happens"); neither left- nor right-click worked. The server received
  the move but silently dropped it.
- **Root cause:** two things. (1) The retail client's move request (`md_1` built
  from the `arh_0` pathfinder, opcode 4503) sends the path as the STEP cells
  ONLY, EXCLUDING the fighter's current (origin) cell — verified live: a fighter
  at (7,15) sent `path[0]=(8,15)`. `validateFightMove` required `path[0]==origin`
  and rejected every move (the old e2e test hid this by sending an origin-included
  path — self-consistent but wrong). (2) A move is a RIGHT-click: `S.java` sends
  only when `ado.aqY()==n2`, `n2 = adc_0.clW("inverseMouseControl") ? 1 : 3`,
  default false → **button 3**; left-click only shows the path preview.
- **Fix:** `handlers_fight_combat.go` — `validateFightMove` now treats `path[0]`
  as the first STEP (must be adjacent to the fighter's cell), each subsequent step
  adjacent, MP cost = `len(path)`; the FIGHTER_MOVE (4524) broadcast PREPENDS the
  origin (`[origin, step1, …, dest]`) so the client's `HB`/`abm_2` walk animation
  starts at the fighter. Control-agent `/click` gained a `button` param so the
  harness can right-click. e2e testclient updated to send the origin-excluded path.
- **Verified:** `unit` (TestValidateFightMove origin-excluded cases) · `e2e`
  (TestFighterMoveInFight) · `live` (right-click via agent: Tanko (9,15)→(9,14),
  MP 3→2, animated; inject: Poolcheck (7,15)→(8,15)).

### B-018 · Spell casts never took effect; give-up never teleported back
- **Symptom (live):** a spell animated but dealt no damage / no "perd X PV"; the
  end-of-fight popup showed but never returned to the overworld.
- **Root cause:** (1) the client sends the spell-cast request as **8107** (`sg_2`),
  not 8109, and the results-ack as **26321** (`nv_0`), not 4321 — both were
  "unhandled opcode". (2) The RUNNING_EFFECT (8120) blob is Ankama's
  part-serialized "BinarSerial" (`amb_0`/`aJj.ad`/`ajl_2`), NOT a flat struct:
  `[i8 numParts]` + directory `{[i8 idx][i32 off]}` + parts; an HP-loss needs
  parts 0 (`yi_1`, 34B `[i64 caster][i64 target][i32 genericId][x][y][z][value]`),
  1 (`Yk`, caster) and 2 (`yl_2`, target). The old flat blob's first byte read as
  `numParts=0`, so the client parsed nothing and dropped the effect.
- **Fix:** `protocol/opcodes.go` (8107, 26321, mh_2 running-effect ids),
  `fight_combat_packets.go` (`buildRunningEffect` + `writeBinarSerial`).
- **Verified:** `e2e` (TestCombatSpellDamage) · `live` (cast spell 4 → target HP
  70→45 rendered; give-up → close popup → teleported to overworld).

### B-017 · Spell effects: only flat damage was resolved
- **Symptom:** utility spells (heal, AP/MP drain/steal) did nothing.
- **Fix:** `spell_effects.go` — `resolveSpellEffects` iterates a spell's effects
  and resolves flat damage (1-10 / 130-134), heal (69), AP/MP loss (16/20) and
  AP/MP steal (85/103), each broadcasting its own 8120 keyed to the effect's
  ActionID; `handleSpellCast` refactored to use it (unknown spell → neutral
  fallback). Push/pull/teleport (37/38/39), AoE, buffs and DoT are recognised but
  deferred (need the client push/area handlers + duration model).
- **Verified:** `unit` (TestResolveEffect) · `live` (damage).

### B-016 · Team membership never persists — fighters revert to the pool on reopen
- **Symptom (live+db):** building a team (Recruter a fighter into a slot, or drag
  a pool fighter into a slot) showed the fighter in the slot, but the
  `team_fighters` table stayed empty and the fighter was back in the pool after a
  reconnect. Only teams seeded directly in the DB ever rendered members. This is
  the B-015 follow-up ("adding a pool fighter to a team is drag-drop, not yet
  driven/verified").
- **Root cause:** the client assigns/removes a team member with the `qp_1` packet
  = **opcode 6013**, body `[i64 fighterId][i16 srcTeam][i16 dstTeam][i64 am]`
  (sent by `acx_2.onFighterDropped` / `onFighterRemoved`; `dstTeam=-1` removes,
  `srcTeam=-1` is the pool). The server registered **no handler for 6013**, so
  every assignment was silently dropped. Red herrings ruled out along the way:
  `saveTeam`/`loadTeam` map to client-internal `20127`/`20126` and export/import a
  **local file** (`%saveTeam%` renders "Exporter équipe" → "Fichier sauvegardé");
  there is no server "save team" button. `selectTeamPreset` is client-internal
  `16617`. The create packet `6001` carries a slot but **no team id**, so
  create-into-slot cannot be assigned server-side — the retail persistence path
  is drag pool→slot (6013).
- **Fix:** `internal/game/handlers_team.go` — new `handleFighterAssignTeam`
  registered for `protocol.OpFighterAssignTeam` (6013): validates coach ownership
  (IDOR guard), unlinks the fighter from `srcTeam`, links it into `dstTeam` under
  the client's caps (≤6 fighters, ≤2 of the same breed), persists via new
  `TeamRepo.AddMember`/`RemoveMember`, then re-pushes the roster (6006) and team
  list (6030) so the pool/slots reconcile.
- **Verified:** `unit` (`TestFighterAssignTeamPersists` — pool→team add, 2-per-
  breed cap, removal, IDOR reject, survives a store reload) · `audit` (payload
  byte-matches `qp_1.encode`; frame `[i16 len][i8 2][i16 op][body]` matches the
  already-correct `aad_1`/`ot_2`). `live` drag not demonstrable — the client's GL
  fighter-card drag does not respond to synthetic drag events (tooling gap), and
  create-into-slot emits no 6013.

### B-014 · Ping keepalive desync — client logs "reply number is low" every 60s
- **Symptom (live):** client `output.log` logged `Too high ping detected:
  Server reply number is low, 0 != 2` every 60 s, plus `Pas de connexion
  disponible pour envoyer le message`.
- **Root cause:** the keepalive uses **two** opcodes. `107` (`asg_0`, C2S only)
  is the client's ping request; `108` (`abj_0`, S2C only, 29 bytes) is the
  server's ack. The client (`pl_2` case 108) credits its counter `nW.sL()` only
  on a **108**. `nW` sends 2 pings (flags 1 & 2) per 60 s and expects 2 replies;
  our server replied with **107**, so the counter never advanced → reset + error.
- **Fix:** `internal/handshake/ping.go` — `EncodePingReply` now emits opcode
  **108** (`PingReplyOpcode`). Body was already correct.
- **Verified:** `live` — ran the real client 75 s past a keepalive cycle, zero
  errors (was every 60 s).

### B-015 · Fighters never appear in the Elite team pool (empty roster)
- **Symptom (live):** created fighters were in the client model
  (`adY.atu() size=4`) but the Elite team panel showed an empty
  available-fighters pool — you could never build a team.
- **Root cause:** the B-013 fix prepended a `type=-4` "bench" team to the 6030
  team list **containing all the coach's fighters**. But `type=-4` is the
  Evolution-mode graveyard, and the client's fighter-pool filter (`U`/`Z`)
  **excludes any fighter that is a member of ANY team in the 6030**. So listing
  the fighters in the -4 team made the pool render empty. (Verified in the
  decompiled client: `Z.a` excludes fighters in `bs_0.IF().IH()` or on the
  `xz_0` bench.)
- **Fix:** the `type=-4` team must be **empty** — it exists only so the client's
  Evolution first-open handler (`ce_1` case 6030) can safely do an unchecked
  `arrayList.get(0)`. Fighters flow purely via 6006 (type=1) into `adY.atu()`,
  and since they're in no team and not on the bench they appear in the pool.
  (`internal/game/team_codec.go` `benchTeamPreset()` now writes 0 members.)
- **Verified:** `live` — the agent's `/roster` now reports `pool=4 fighterList=4`
  (was 0), and a created fighter renders in a team slot. `e2e`/`unit` green.
- **Notes for follow-up:** the team-create opcode is **6021** and works
  (`members=0` is correct for a newly-created empty team). Adding an existing pool
  fighter to a team is a drag-drop that sends **6013** — now handled (see B-016).
  Evolution fighters are `type=2` on the wire but our encoder always writes
  `type=1` — Evolution-mode round-trip is a separate open item.

### B-013 · Fighter roster empty after create / panel reopen
- **Symptom (live):** Evolution "Recruter" → create → popup closes, no fighter
  shown; and reopening any team-management tab showed an empty roster.
- **Root cause (four stacked):**
  1. `6006` (FighterInformationList) was pushed only at login.
  2. The panel-open request `6031` returned only teams, not the roster.
  3. **The `6006` leading i64 is a server TIMESTAMP (seconds), not the coach
     id** — the client computes each fighter's form as `(now - lead)/3600` hours
     (`xi_0`/`awy` → `et_2.a`); the coach id made that huge → form zeroed. The
     `6000` create result doesn't apply this fatigue, which is why fighters
     showed on first create but vanished on reopen.
  4. **The `6030` team list must lead with a special `type=-4` "Evolution bench"
     team** holding all fighters — the client's first-open handler (`ce_1` case
     6030) does an unchecked `arrayList.get(0)` and the normal handler (`adi_2`)
     scans for the `type==-4` team to fill the bench.
- **Fix:** re-push `6006` after create/delete; `6031` now returns `6030`+`6006`;
  `buildFighterList` sends `time.Now().Unix()`; `pushTeamPresetList` prepends the
  `type=-4` bench team. (`internal/game/handlers_fighter.go`, `handlers_team.go`,
  `team_codec.go`.)
- **Verified:** `unit` + `e2e`; `live` verification pending a create-flow drive.

### B-012 · Chat messages shown twice to the sender
- **Symptom (live):** every vicinity/channel/private message the player sent
  appeared twice in their own chat.
- **Root cause:** all three chat handlers echoed the frame back to the sender in
  addition to broadcasting. The client already renders its own outgoing line
  locally, so the echo duplicated it.
- **Fix:** removed the self-echo — vicinity → `SessionsNear` (excludes sender),
  channel → `SessionsWithout(coachID)`, whisper → target only. Matches the
  reference server. (`internal/game/handlers_chat.go`.)
- **Verified:** `e2e` (asserts sender receives no echo).

### B-011 · PlayerStatisticsReport (2400) wrong field ids
- **Root cause:** put `Strength` in field 6 (an internal model value `dN`, not a
  displayed stat) and never sent field 8 (consecutive losses `dO`). Verified vs
  the decompiled `PlayerStatisticsReport` class: 1=playTime, 2=fightTime,
  3=fights, 4=won, 5=lost, 7=consecWins, 8=consecLosses.
- **Fix:** emit fields 1,2,3,4,5,7,8; added `Coach.ConsecutiveLosses`.
- **Verified:** `unit` (`TestPlayerStatisticsReportFields`).

---

## Earlier fixes (pre-live-harness, audit/e2e verified)

| ID | Area | Root cause | Where |
|---|---|---|---|
| B-010 | Social acks | `sendSocialAck` wrote a fixed `[name][i64]` for all 4 acks; real layouts differ per opcode (3156 kz_1 / 3158 ft_0 / 3160 adw_1 / 3162 ahm_0) — would BufferUnderflow the client on 3156/3158 | `handlers_social.go` |
| B-009 | Matchmaking | `sendMatchFound` wrote `mode` into both the mode AND fightType i16 fields | `handlers_matchmaking.go` |
| B-008 | ActorSpawn 4096 | coach record missing 16 trailing bytes at flags 3179 → client BufferUnderflow | `packets.go` |
| B-007 | Coach look | client reads SKIN then HAIR (not hair-then-skin); swap in 2049 decode + 2052/8000/4096 encoders | multiple |
| B-006 | TeamPresetDelete 6023 | read as u16, real id is i64 → silent no-op | `handlers_fighter.go` |
| B-005 | FriendList/IgnoreList | nil-join count desync | `packets.go` |
| B-004 | CREATE_FIGHT 8000 | two-stage decode; leading error byte required; `et_2` reads `zv`(sex) before `ey` | `fight_packets.go` |
| B-003 | Overworld culling | 100% server-side; must spawn/despawn (4096/4098) across AoI; a 4500 move for an un-spawned actor is dropped | `world.go` |
| B-002 | Exchange completion | 5111 reason byte is FIRST (0=success/1=cancel) | `handlers_exchange.go` |
| B-001 | Login | plaintext (not RSA); wire is big-endian `[u16 len][u8 arch][u16 op]` C2S / `[u16 len][u16 op]` S2C | `protocol/` |

---

## How bugs get found now

1. **Live client via the control agent** (`client/control-agent/`) — drive
   the retail client, screenshot panels, read its `output.log` for
   errors/exceptions, and read client-side model state via `/eval`. This class
   of bug (silent wrong-state, decode crashes, protocol desyncs) is invisible on
   the wire alone — see B-012, B-013, B-014.
2. **Byte audit** vs the decompiled client in
   `E:\Projets\DofusArena2-06\client\decompiled\core` — for exact wire layouts.
3. **e2e / unit** regression tests lock every fix.
