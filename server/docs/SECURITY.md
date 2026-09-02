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

## NOT fixed — open, with severity

Recorded here rather than left in a chat log. Each entry names the file so it can
be picked up cold.

### High

**H-1 · Spell loadout is client-authored, so spell ownership is bypassable.**
`decodeLoadoutSpells` (`handlers_fighter.go:282`) accepts up to 6 arbitrary spell
ids from opcode 6011 with **no legality check**, and `SaveLoadout`
(`store/fighter_repo.go:150`) validates only *whose* fighter it is, never *what*
spells. `castSpellByFighter` then gates on `fighterKnowsSpell`, which reads that
same client-written list — so the mitigation the code believes it has is anchored
on attacker-controlled data. Any fighter can equip and cast any of the 203 spells
(boss/demon/cross-breed), bypassing breed and Sphere-Board progression, and it
persists across reconnect. Note the asymmetry in the same handler: **cards are
validated** (`canonicalEquipSlots` + `entitledEquip`), spells are passed through.
`fighter.go:75` admits the gap ("breed-legality check would need spell gamedata;
deferred"). *Fixing this needs a breed→legal-spell mapping from `gamedata`, which
is why it is not a one-liner.*

**H-2 · No rate limiting, no connection cap, no timeouts on the game socket.**
Every limiter in the tree lives in `internal/web`. `server.go:74` accepts
unconditionally and spawns two goroutines per connection; there is no per-IP cap,
no read/write/idle deadline, and nothing evicts a socket that connects and never
speaks. Each idle connection costs ~12 KB of buffers plus a 256-slot queue that
can hold up to 16 MB of frames. Worse, opcode 1025 runs **bcrypt** synchronously
on the session goroutine (`repos.go:67`/`:80`, ~50–100 ms), so a few dozen
connections spamming logins saturate every core — the cheapest full-server DoS
left. There is also no lockout or delay on password guessing.

**H-3 · Auto-registration on the game port, and first-account-becomes-admin.**
`handlers_connection.go:107` creates an account for any unknown login, with no
cap and no config gate (`web.registration_enabled` does **not** apply). And
`:119` grants `IsAdmin` to whoever authenticates first against an empty database
— on a fresh public deployment an attacker scanning for new instances wins that
race against the operator. `handlers_gm.go:30` gates GM commands on exactly that
flag.

**H-4 · Fusion mints value from nothing.** `handlers_fusion.go:58`. The target
card is player-supplied and the only real gates are same-`CardSet` and
`Σ inputs.RequiredLevel ≥ target.FusionPower`. Per the code's own comments,
`FusionPower`/`FusionQuality` are non-zero for **7 cards out of 907** and 543
cards have `RequiredLevel 0` — so for nearly the whole catalogue both gates
evaluate `0 >= 0`. Two cheap commons become the most valuable card in the set at a
flat 60 %. Card *conservation* is correct (`ConsumeAndGrant` is transactional and
tallies duplicates); this is a *value* break.

**H-5 · Guild name uniqueness is case-sensitive.** `store/guild_repo.go:82` uses
`Where("name = ?")` while the coach repo correctly uses
`LOWER(name) = LOWER(?)` — so `Elite`, `elite` and `ELITE` are four distinct
guilds. Handler-side validation (`handlers_guild.go:104`) enforces a **minimum
only**: no maximum, no control-character filter, no `<`/`>` strip. On success the
name is broadcast to **every online session**.

**H-6 · Guild rank names have no validation at all.** `handlers_guild2.go:210`
and `:247` → `guild_repo.go:227`/`:236` store the string verbatim: no trim, no
bounds, no content filter. Pushed to every guild member. Gated on leader rights,
but anyone can found their own guild.

### Medium

- **M-1 · `consumeCard` is a non-transactional read-then-write.**
  `handlers_evolution.go:240` — no transaction, no `WHERE quantity = <old>`, no
  `gorm.Expr("quantity - 1")`, and write errors are discarded so it returns
  `true` even when nothing was written. Reachable from 22099, 5470 and 23009.
  Same pattern in `takeCardsForMail`/`restoreCardsFromFailedMail`.
- **M-2 · Overworld movement is unvalidated.** `handlers_movement.go:26` takes the
  **last** step verbatim with no adjacency, walkability or speed check — instant
  teleport anywhere in the overworld. The code says so itself. Fight movement is
  fully validated and unaffected.
- **M-3 · Barter memory amplification.** `handlers_shop.go:79` expands a `u16`
  per-entry quantity into one slice element each; 64 × 65535 ≈ 16.8 MB from a
  ~400-byte frame (~42,000×).
- **M-4 · Any coach can spectate any fight.** `handlers_spectate.go:23` accepts an
  arbitrary `[i64 coachId]` and requires only that the target be in a live fight,
  then sends full state. 2260 is also a cheap "is coach N fighting?" oracle.
  Spectating is a real retail feature, but retail-parity is not authorization.
- **M-5 · Mail can be addressed to an arbitrary coach id.**
  `handlers_mail.go:166` — if the receiver name does not resolve but the
  client-supplied `receiverID` is non-zero, the raw id is used with an
  attacker-chosen display name. Attachments are already consumed by then, so
  mailing to a nonexistent id destroys them.
- **M-6 · Fighter budget is computed and never enforced.** `computeFighterBudget`
  writes `fighters.budget`; nothing compares it to a cap. The client warns above
  6000 (`hu_2`: `n4 > 6000`) but does **not** refuse, so retail leaks this too.
- **M-7 · Equipping a stack moves the whole row.** `handlers_inventory.go:129` —
  `Pos` lives on the stack row, not per copy, so equipping one of five copies
  hides all five from trade/fusion/mail. The **dedup is correct** (`Pos == 0` in
  the match is what defeats the "same card in 14 slots" attack); this is a
  desync, not duplication.
- **M-8 · Cross-goroutine access to `Session.Coach` and its slices.**
  `Coach.Mu` is taken only in `creditPlayTime`, `creditFightTime` and
  `CoachRepo.Save`; fight-actor and peer goroutines assign
  `sess.Coach.Inventory`/`.Wallet` without it. Confirmed race; economy is
  protected by the transactional repo methods, so the exposure is torn reads and
  lost scalar stats rather than duplication.
- **M-9 · Two sessions hold separate `*domain.Coach` copies.** `CoachRepo.Get`
  allocates per login, and `Save` blind-writes scalar stats — so a stale copy can
  roll back ladder rating and win/loss records written by the other.

### Low

- **L-1 · A fighter that dies mid-turn keeps acting.** `applyHPDelta` sets HP 0
  and broadcasts the death but never advances the turn; 8109, 8111 and 4503 have
  no `HP <= 0` check (8107 and 4521 do). Bounded by the turn clock and remaining
  AP.
- **L-2 · Uncapped allocation from a `u16` count.**
  `handlers_demon_affiliate.go:49` — `make([]offer, 0, count)` with no cap; a
  9-byte frame allocates ~512 KB (~58,000×). Transient and GC'd, but it happens
  *before* the authorization check.
- **L-3 · Self-trade locks a coach out of trading.** `exchange.go:47` —
  `Start(s, s)` leaves `ex.A == ex.B`, so `sideOf` always returns 0, `ready[1]`
  can never be set, and the coach stays "busy". Inviting a stranger locks *them*
  the same way (no proximity or ignore-list check).
- **L-4 · `ex.accepted` is never read.** Staging and ready-up work before the
  invite is answered.
- **L-5 · A player can hand-drive their own summon.** `summon.go:79` gives summons
  the owner's `CoachID`, so the ownership check passes during the summon's turn.
  AP/MP are debited normally, so there is no resource gain.
- **L-6 · Oversize mail can self-disconnect the sender.** `mail_repo.go:45`
  returns `ErrMailboxFull` *before* truncation, and the handler echoes the
  untruncated record, exceeding `MaxFrameLen`. Sender only.
- **L-7 · Write errors are discarded** in several store paths
  (`handlers_evolution.go:249`, `handlers_mail.go:241`,
  `handlers_inventory.go:147`), so a failed mutation looks like a success.

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
