# Security posture — DofusArena 2.70 server

Threat model, what has been fixed, and **what has not**. Written for a hostile
client, because that is the only realistic one: the retail client can be
recompiled, and everything it validates locally can simply be omitted.

> **The rule this document exists to enforce:** every input rule the client
> appears to apply is *advisory*. `restrict="[.*&[^<>]]"` on a name field, the
> 6-fighter check in `hu_2`, the `length() <= 20` in
> `aBC.validateCoachCreationForm` — a modified client keeps none of them. If the
> server relies on any of it, it is not enforced at all.

## Threat model

The attacker:

- controls the client completely (MITM, custom client, or a patched `core.jar`);
- can send **any** opcode, at any time, in any order, with arbitrary payloads,
  regardless of what UI state the real client would require;
- can open **many simultaneous connections**, including several for the same
  account;
- knows the protocol at least as well as this repository documents it.

The attacker is assumed **not** to have the server's database or filesystem.

## Structural properties that hold

These are the reasons the audit came back as narrowly as it did. They are
load-bearing — breaking one re-opens a whole class.

| Property | Where | Why it matters |
|---|---|---|
| Every wire read is bounds-checked | `protocol.Reader.need` | No handler indexes a raw payload slice; a truncated frame is an error, never a panic. Only two direct payload accesses exist in non-test code and both length-check first. |
| Frame length is `u16`-bounded both ways | `protocol/frame.go` | Caps a single frame at 64 KB by construction. |
| Outbound strings are truncated at the writer | `Writer.StringU8` (127 bytes) | Makes *remote client* crashes impossible from a long field, at every call site at once. |
| Fights are a single-goroutine actor | `fight_actor.go` | All combat state mutation is serialised; AP/MP cannot be double-spent by racing. |
| The session is the authority, the wire id is a selector | all combat handlers | Every one of the seven client-supplied-fighter-id sites re-checks `ff.CoachID != cid`. You cannot act with an opponent's fighter. |
| Economy mutations are transactional and re-read inside the transaction | `BuyCards`, `ConsumeAndGrant`, `CreditCurrency`, `CompleteExchange` | This is what stops the two-session coach-copy problem from becoming item duplication. |
| Panics are contained | `dispatchSafely`, `Fight.runEvent` | A panic drops one session (or logs one fight event) instead of killing the process. |

## Fixed

See `BUGS.md` B-145 … B-152 for full detail.

| ID | Severity | Issue |
|---|---|---|
| B-145 | **Critical** | No `recover()` anywhere + two remote full-server crashes (matchmaker and challenge ghost coaches, 3 packets each) |
| B-146 | **Critical** | Empty / markup / invisible-character coach names; 2049 replayable for unlimited coaches |
| B-147 | **Critical** | Channel chat (3140) unsanitized to every online player, and unthrottled |
| B-148 | **High** | 6021 team-preset IDOR (steal/overwrite any coach's preset) + roster duplication and size bypass |
| B-149 | **High** | Duplicate logins leaked coach state into every subsystem; second session invisible world-wide |
| B-150 | **Critical** | 22003 could clear the server's own anti-replay flags → unlimited challenge reward cards |
| B-151 | **Critical** | 5450 sold unpriced cards for free (764 of 907 templates reachable) |
| B-152 | **Critical** | 28611 could re-claim a tournament reward card indefinitely |

## Everything from the audit is now fixed

The second pass closed the remaining High, Medium and Low findings. Nothing from
the original audit is left open.

| Area | Fix |
|---|---|
| **Spell loadouts (H-1)** | 6011/6001 now filter client-authored spell lists against the fighter's breed. Own-breed only; the two pseudo-breeds are excluded (breed 0 is monster/summon material — id 428 is range 1-30 value 250 — and breed 99 is boss utility). Sphere unlocks, summons and challenge demons are added server-side *after* the filter, so nothing legitimate is affected. |
| **DoS + auth surface (H-2, H-3)** | New `limits:` config block: global and per-IP connection caps, handshake and idle read deadlines, and a per-IP login throttle (each 1025 costs a bcrypt hash on the session goroutine). Auto-registration and first-account-becomes-admin are now opt-out switches. 1025 also gained an already-authenticated guard — it could previously be replayed forever, leaking a registry entry per attempt. |
| **Fusion (H-4)** | Target value is now bounded by what was consumed. |
| **Guild names + ranks (H-5, H-6)** | Guild uniqueness is case-insensitive (it was exact, so `Elite`/`elite`/`ELITE` were distinct guilds). Guild names and rank names go through `sanitizeDisplayName`; rank names previously had no validation at all. |
| **consumeCard TOCTOU (M-1)** | One conditional `UPDATE ... quantity = quantity - 1 WHERE quantity > 0`, branching on `RowsAffected`. Errors are propagated instead of discarded. Same treatment for the mail attachment paths. |
| **Overworld movement (M-2)** | Displacement per 4501 is capped (`maxOverworldJump`). Deliberately a displacement bound rather than path validation, because overworld movement is client-authoritative by design — see the comment in `handlers_movement.go`. It is not cosmetic: fusion gates on proximity to an altar. |
| **Barter amplification (M-3)** | Per-entry quantity clamped before expansion (was 64 × 65535 int32 ≈ 16.8 MB from a ~400-byte frame). |
| **Mail receiver (M-5)** | The recipient is resolved by name server-side or the send fails — the client's `receiverID` is no longer trusted. Refusing happens *before* `takeCardsForMail`, so a bad address can no longer destroy the attachments. |
| **Equip splits the stack (M-7)** | Equipping now splits one unit off the stack and merges it back on unequip. `Pos` lives on the stack row, so equipping one of five copies used to hide all five from trade/fusion/mail while counting as one for set bonuses — and it was how duplicate `pos = 0` rows accumulated. |
| **Coach struct races (M-8, M-9)** | `SetInventory`/`SetWallet`/`Snapshot*` accessors take `Coach.Mu`; all six cross-goroutine write sites go through them. The unsynchronised slice-header write was undefined behaviour, not just a stale read. |
| **Dead fighters acting (L-1)** | 8109, 8111 and 4503 now check `HP > 0` (8107 and 4521 already did). A fighter killed during its own turn stopped acting only at the next turn boundary. |
| **Uncapped allocation (L-2)** | `handlers_demon_affiliate.go` no longer pre-sizes from a wire `u16` — and note that allocation ran *before* the authorization check. |
| **Self-trade lockout (L-3)** | `Start(s, s)` is refused. It used to leave `ex.A == ex.B`, so `sideOf` always returned 0, `ready[1]` could never be set, and the coach was permanently "busy". Aimed at a stranger it locked *them* out. |
| **`ex.accepted` (L-4)** | Now actually read: staging and readying up require the invitation to have been accepted. It was written and never consulted. |
| **Oversize-mail self-DoS (L-6)** | Title/body are truncated *before* the mailbox-full check, so every early return carries an already-bounded record. |
| **Discarded write errors (L-7)** | Checked and logged. A failed restore of a mail attachment destroys a player's card, so that one logs at `Error`. |

### Deliberately not changed

- **Spectating any fight (M-4).** Any coach can attach to any live fight and 2260
  is an "is coach N fighting?" oracle. This is retail behaviour and the
  spectator's `deckCoach` is already nil-ed so decks do not leak. Restricting it
  is a product decision, not a defect — raise it if you want fights private.
- ~~**Fighter budget (M-6).**~~ **CORRECTED AND FIXED.** This entry used to say
  enforcing the budget "would diverge from retail" because the client only warns.
  That reasoning was wrong: `zN.java` shows the retail SERVER sent code 46 for
  exactly this, and the client warning-and-submitting means honest clients were
  relying on a server check that did not exist. Now enforced in `validateRoster`.
- **Summons are hand-drivable (L-5).** A player can manually act with their own
  summon during its turn. AP/MP are debited normally, so there is no resource
  gain — it contradicts a comment, not a rule.
## Client-enforced rules the server must re-check

A distinct bug class, and the most productive one in this project: **the client
enforces a rule locally, so an honest client never violates it, so nobody notices
the server has no check.** A modified client simply omits it.

### The correction that unlocked this

These rules look like client-side validation, and the natural conclusion is
"enforcing them server-side would diverge from retail". **That was wrong, and this
document previously said so about the team budget.**

`zN.java:214-322` is the client's handler for **opcode 25000, a SERVER→client
error frame**. It maps numeric codes to exactly these messages — and codes
34/38/39/40, which this server already sends, sit in the *same switch* as
45/46/63/69/78. The retail server re-validated all of it. Implementing these
**restores** retail behaviour.

Worse for the "it would diverge" reading: on two paths the retail client only
**warns** about the team budget and submits anyway (`hu_2.java:456-459`,
`:489-492`). Even honest clients were relying on a server check that did not exist.

### How to hunt them

1. Enumerate the client's local refusals:
   `grep 'aOG().*getString("error\.' *.java` — each is a rule the client applies
   itself. There are ~45.
2. For each, read the surrounding method and decide **BLOCKS vs WARNS-AND-SENDS**.
   That distinction decides whether server enforcement is a restoration or a
   change, and it is the single most important thing to get right.
3. Check whether the message also appears in the **25000 switch** (`zN.java`) or
   has a dedicated refusal frame (e.g. 5113 for exchange). If so, retail enforced
   it server-side and there is no divergence question at all.
4. Then check the Go server. Prefer a **choke point** over per-handler checks:
   `buildFightTeamFor` closes five roster rules at once because every fight path
   funnels through it.

### Rules now enforced (were not)

| Rule | Client | Retail code | Where enforced now |
|---|---|---|---|
| Team budget ≤ 6000 | warns on 2 paths, blocks on others | 46 | `validateRoster` |
| Max 6 fighters | blocks | 45 | `validateRoster` (was 6013/6021 only) |
| Max 2 per breed | blocks | 63 | `validateRoster` (was 6013/6021 only) |
| No duplicate fighter | blocks | 45 | `validateRoster` (was 6021 only) |
| No dead/graveyard fighter | blocks | — | `validateRoster` |
| Min evolution budget 5000 | blocks | 78 | `validateRoster` |
| No roster edits while queued | server rule | 69 | `Session.rosterLocked` |
| Fighter pool ≤ 100 | blocks | 20 (on 6000) | `handleFighterCreate` |
| Petrified cannot act | blocks (both validators) | — | `FightFighter.canAct` |
| Slot ↔ card type | blocks | — | `coachCardFitsSlot` |
| Unique card not re-acquirable | blocks (shop), warns (mail) | 5113 for trade | shop buy/barter, mail claim |
| Undestructible not consumable | blocks | — | fusion, demon, barter |
| Exchange staging ≤ 5 | blocks | — | `Exchange.stageCard` |
| Summon cap | data token only | — | backstop in `applySummon` |

### Known remaining gaps

- **Codes 62 (`equipmentForbidden`) and 66 (`badCoachCardQuantity`)** appear in the
  25000 switch, so retail validated two further rules we do not. Their exact
  conditions were not recovered.
- **`RequiredLevel` on coach cards** is decoded but never filtered in
  `equippedCountsPerSet`, so a level-1 coach gets set bonuses from cards it cannot
  benefit from. The retail client only *warns* on equip and then silently drops
  the card from its own bonus maths (`sj_1.java:347`), so honest clients are
  affected too — it is a fidelity gap as much as a security one.
- **Bench/titular caps** (7 bench, 6 titular, 9 legendary bench) are enforced on
  resurrection and the graveyard, but not on the plain state toggle.
- **`CooldownUnlockDelay`** (spell field 11) is decoded and never read. Impact
  depends on whether any shipped spell has it non-zero while `Cooldown` is zero —
  that data query was not run.
- **Target-mask bits 49-62** (states, HP/AP/MP, elemental resist) are
  unrepresentable server-side, so a mask using them is skipped whole. Only three
  spells set `EnforceTargetMasks` today and none rely on those bits.
## Testing conventions for security fixes

Four rules, each of which caught a fix that would otherwise have shipped
unverified:

1. **Test the door, not the lock.** Unit-testing `validateCoachName` passed while
   the handler ignored its verdict. A mutation that discarded the result survived
   the *entire* suite because nothing drove opcode 2049 with hostile input.
   Security regressions belong at `test/e2e/` wherever an opcode is involved.
2. **Mutation-verify every guard, and make sure the mutant COMPILES.** One check
   reported "caught" only because the mutated code had an unused variable.
   Rewritten to compile, it reported "missed" — the honest answer.
3. **Assert the fixture can exercise the thing.** A `ConfirmTeam` subtest that
   never accepted the challenge returned early and tested nothing. A dedup test
   sending 20 copies of *one* fighter leaves a single member, so the size cap is
   never reached.
4. **Cover both directions.** Guarding `c.challenger` and not `c.target` left the
   identical crash reachable from the other side; either party can send 27529.

When a guard is genuinely defence-in-depth and no single mutation can break it,
**say so in the comment** rather than implying it is load-bearing —
`maxChannelField` is the worked example.
