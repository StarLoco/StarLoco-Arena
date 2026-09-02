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
- **Fighter budget (M-6).** Computed and stored, never enforced. The retail
  client only *warns* above 6000 (`hu_2`: `n4 > 6000`) and still submits, so
  enforcing server-side would diverge from retail. Needs a rules decision.
- **Summons are hand-drivable (L-5).** A player can manually act with their own
  summon during its turn. AP/MP are debited normally, so there is no resource
  gain — it contradicts a comment, not a rule.
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
