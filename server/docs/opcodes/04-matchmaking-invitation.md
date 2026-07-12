# Matchmaking & Fight Invitation Opcodes

> Client source of truth, cross-checked against the current Go server implementation.

Header format for all client→server (Recv) messages in this domain:
`addClientHeader((byte)2, payload)`. All multi-byte integers are big-endian.

---

## OPPONENT_SEARCH_REQUEST (Recv 2301)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../world/clientToServer/OpponentSearchRequestMessage.java:22-56`
**Go source:** `server/internal/dispatch/handlers_matchmaking.go:12-14,20-46` (`handleOpponentSearchRequest`)
**Payload (5 bytes):**
```
byte    fightTypeId   // e.g. 1 = DEFY
int32   bet           // wager amount, 0 = no bet
```
Java server (`OpponentSearchRequest.java:19-26`) replies `OPPONENT_SEARCH_IN_PROGRESS`
unconditionally first, then enqueues via `WaitingOpponent.add(...)`, which synchronously searches
for a compatible waiting opponent (same type, same bet, both ready==-1, not self) and fires
`OPPONENT_FOUND` to both sides if matched. Go matches this ordering exactly.

`OPPONENT_SEARCH_ERROR` (2302) was dead code in the original — Java has it commented out with no
discoverable error-code enum. The Go server now **repurposes it as a request-validation signal**
(Go-only improvement): the three request-validation failures in `handleOpponentSearchRequest` that
previously returned silently now emit `OPPONENT_SEARCH_ERROR` with a Go-defined error code. See the
`OPPONENT_SEARCH_ERROR (Send 2302)` section below.

## OPPONENT_SEARCH_CANCEL (Recv 2303)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../world/clientToServer/OpponentSearchCancelMessage.java:17-31`
**Go source:** `handlers_matchmaking.go:15-17,48-55` (`handleOpponentSearchCancel`)
**Payload:** none (0 bytes).
**Discrepancy (Go improvement):** Java (`OpponentSearchCancel.java:13-16`) does **not** actually
dequeue the coach — it unconditionally replies `OPPONENT_SEARCH_CANCEL_RESULT` without touching
`WaitingOpponent.list`. Go's version actually calls `deps.Matchmaking.CancelSearch(coach.ID)`,
correctly removing the coach from the waiting queue — documented improvement in
`../08-java-parity-roadmap.md:31`.

## OPPONENT_FOUND (Send 2300)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/OpponentFoundMessage.java:21-58`
**Go source:** `packets_matchmaking.go:17-23` (`buildOpponentFound`); sent from `handlers_matchmaking.go:40-45`
**Payload (13 bytes):**
```
int64   fightId
int32   bet
byte    fightTypeId
```
Field order confirmed identical in client decode, legacy Java build
(`WaitingOpponent.java:35`), and Go build — byte-for-byte parity. Sent to **both** matched coaches.

## OPPONENT_SEARCH_ERROR (Send 2302)
**Direction:** Server → Client
**Status:** implemented — **Go-only** request-validation signal (dead code in legacy Java)
**Client source:** `client/.../world/serverToClient/OpponentSearchErrorMessage.java:19-44`
(reads `rawDatas[0]` into an unused field; never branches on the value)
**Go source:** `packets_matchmaking.go` (`buildOpponentSearchError`); sent from
`handlers_matchmaking.go` (`handleOpponentSearchRequest`); error-code enum in
`protocol/opcodes.go` (`OpponentSearchErrorCode`).
**Payload (1 byte):** `byte errorCode`.

The legacy Java server never sent this and no error-code enum was ever defined. Because the client
decodes but ignores the specific value, the Go server safely repurposes 2302 to replace three
formerly-silent validation drops in `handleOpponentSearchRequest`. The error codes below are a
**Go-only convention** (value 0 reserved as "no error"):

| Code | Name | Condition |
|---|---|---|
| 1 | `OpponentSearchErrBadRequest` | `OPPONENT_SEARCH_REQUEST` payload malformed / truncated |
| 2 | `OpponentSearchErrNoCoach` | session has no coach bound (pre-coach-selection) |
| 3 | `OpponentSearchErrInvalidParams` | `fightType != 1` or `bet < 0` |

**fightType validation rationale:** `fightType` is an opaque byte server-side (no `FightDefinition`
table exists in the Go gamedata store). The stock client only ever sends `fightType=1` over the
2301 matchmaking path (`UIRandomFightCreationFrame.java:51`, `UIChatFrame.java:176`); type `4` is
exclusive to the invitation path (4301, `UIIslandWorldSceneInteractionFrame.java:175,185`), never
matchmaking. So `1` is the pragmatic allowlist (`protocol.FightTypeMatchmakingDefy`).

Unlike `OPPONENT_SEARCH_CANCEL_RESULT` (which sends 0 bytes despite the client's `rawDatas[0]`
read), this message correctly emits the ≥1 byte the client expects.

Tested in `test/e2e/duel_test.go` (`TestE2E_OpponentSearchErrorOnInvalidRequest`).

## OPPONENT_SEARCH_IN_PROGRESS (Send 2304)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/OpponentSearchInProgressMessage.java:19-33`
**Go source:** `packets_matchmaking.go:5-9`; sent from `handlers_matchmaking.go:32`
**Payload:** none (client `decode()` ignores `rawDatas` entirely). Sent immediately and
unconditionally in response to `OPPONENT_SEARCH_REQUEST`, before matchmaking is attempted, in
both implementations — ordering identical.

## OPPONENT_SEARCH_CANCEL_RESULT (Send 2306)
**Direction:** Server → Client · **Status:** implemented (with a latent payload-size mismatch)
**Client source:** `client/.../world/serverToClient/OpponentSearchCancelResultMessage.java:19-44`
**Go source:** `packets_matchmaking.go:11-15`; sent from `handlers_matchmaking.go:54`
**Payload (client-expected, 1 byte):** `byte errorCode` (client reads `rawDatas[0]`).
**Discrepancy:** Java sends this with `new Buffer(0, ...)` — a **zero-length** payload, which is
inconsistent with the client's `rawDatas[0]` read (would throw if the transport didn't guarantee
≥1 byte). Go's `buildOpponentSearchCancelResult()` also sends an empty payload — faithfully
reproducing the same (possibly buggy) zero-byte behavior for parity with Java, not fixed.

---

## FIGHT_INVITATION_REQUEST_MESSAGE (Recv 4301)
**Direction:** Client → Server
**Status:** implemented (Go-only completion — beyond the Java stub)
**Client source:** `client/.../game/clientToServer/FightInvitationRequestMessage.java:23-70`
**Go source:** `server/internal/dispatch/handlers_fight.go` (`handleFightInvitationRequest`),
`packets_invitation.go` (builders), `internal/world/invitation.go` (`InvitationManager`)
**Payload (13 bytes):**
```
int64   targetCoachId
byte    fightTypeId
int32   bet
```
Java's handler (`FightInvitationRequest.java:8-21`) read the fields, resolved the target coach,
**then did nothing else** — ending with `// TODO: Finir d'implementer le défi par clic droit sur un
joueur`. The Go server **completes this flow** (the client-side decode logic for the whole chain
already exists, so no protocol reverse-engineering was needed):

`handleFightInvitationRequest`:
1. Resolves the inviter from the session; rejects self-challenge with
   `FIGHT_INVITATION_ERROR` code 33.
2. Requires the target to be online (`World.Get`) — else code 30 (target not found).
3. Requires neither party to already be in a pending invitation (`InvitationManager.GetByCoach`)
   or an in-progress duel/fight (`Duels.GetByCoach`) — else code 32 (you're busy) / 31 (target busy).
4. Allocates a pending `Invitation` (monotonic id, distinct from the eventual fightId) and sends
   `FIGHT_INVITATION` (4300) to **both** parties: the inviter's copy carries the target's team
   (inviter flag = 1), the target's copy carries the inviter's team (inviter flag = 0).

The target's client shows an accept/reject message box; its result returns as
`FightInvitationAccept` (4305) or `FightInvitationReject` (4307), both now handled server-side.
On accept, a real `world.Duel` is created via the **same** `DuelManager.Create` path matchmaking
uses after `OPPONENT_FOUND`, so the shared `SET_READY_FOR_FIGHT → CREATE_FIGHT → presentation →
combat` pipeline takes over unchanged.

This is a **Go-only enhancement**; the legacy Java server never implemented anything past the
initial parse. It does not alter any existing flow.

## FIGHT_INVITATION (Send 4300)
**Direction:** Server → Client
**Status:** implemented (Go-only) — `buildFightInvitation` in `packets_invitation.go`
**Client source:** `client/.../game/serverToClient/FightInvitationMessage.java:23-115`
**Go source:** `server/internal/dispatch/packets_invitation.go` (`buildFightInvitation`)
**Payload (variable length):**
```
int64   invitationId
byte    inviter          // decoded as boolean: buffer.get() == 1
byte    fightTypeId      // consumed to resolve FightDefinition, not stored raw
int32   bet
byte    opponentTeamCount
  repeat opponentTeamCount times:
    byte    teamId
    int64   leaderId
    byte    teamMateCount
      repeat teamMateCount times:
        int64   coachId
        byte    nameLength
        byte[nameLength]  name   // decoded as new String(name), platform default charset
```
Consumer: `FightInvitationManager.addInvitation(...)` shows an accept/reject message box UI
(`messageBoxOptions` = 4 if inviter, 24 if invitee; appends "with bet" text if `bet != 0`).

## FIGHT_INVITATION_ACCEPTED (Send 4302)
**Status:** implemented (Go-only) — `buildFightInvitationAccepted`
**Client source:** `client/.../game/serverToClient/FightInvitationAcceptedMessage.java:22-58`
**Go source:** `packets_invitation.go` (`buildFightInvitationAccepted`); sent from
`handleFightInvitationAccept`
**Payload (16 bytes):** `int64 invitationId; int64 fightId`
Sent to **both** coaches on accept, carrying the separate `fightId` = the newly-created
`world.Duel.ID`. The client swaps to its fight-creation / team-selection frame seeded with this
fightId, which then drives `SET_READY_FOR_FIGHT` (4303).

## FIGHT_INVITATION_REJECTED (Send 4304)
**Status:** implemented (Go-only) — `buildFightInvitationRejected`
**Client source:** `client/.../game/serverToClient/FightInvitationRejectedMessage.java:22-49`
**Go source:** `packets_invitation.go` (`buildFightInvitationRejected`)
**Payload (8 bytes):** `int64 invitationId`
Sent to the inviter when the target rejects (`handleFightInvitationReject`), **and** to the
surviving party if either coach disconnects while an invitation is pending (`disconnect.go` —
dismisses the dead invitation box client-side via `FightInvitationManager.removeInvitation`).

## FightInvitationAcceptMessage (Recv 4305)
**Status:** implemented (Go-only) — `handleFightInvitationAccept`
**Client source:** `client/.../game/clientToServer/FightInvitationAcceptMessage.java:23-50` —
`getId()` at line 41 **directly returns the literal `4305`**.
**Go source:** `server/internal/dispatch/handlers_fight.go` (`handleFightInvitationAccept`)
**Payload (8 bytes):** `int64 invitationId`
Triggered from `UIFightInvitationFrame.onMessage` case `16500` (internal UI message id, not a wire
opcode) after the player clicks "accept". The handler validates the invitation still exists and
this coach is its target, re-checks both parties are online and un-dueled (else emits 4309),
creates a `world.Duel` (reusing `DuelManager.Create`), sends 4302 to both, and arms the
`SET_READY_FOR_FIGHT` forced-progress timer — exactly as the `OPPONENT_FOUND` path does.

## FightInvitationRejectMessage (Recv 4307)
**Status:** implemented (Go-only) — `handleFightInvitationReject`
**Client source:** `client/.../game/clientToServer/FightInvitationRejectMessage.java:23-50` —
`getId()` at line 41 directly returns literal `4307`.
**Go source:** `server/internal/dispatch/handlers_fight.go` (`handleFightInvitationReject`)
**Payload (8 bytes):** `int64 invitationId`
Triggered from `UIFightInvitationFrame.onMessage` case `16501` on message-box close with any
non-accept result. The handler validates target ownership, notifies the inviter with 4304, and
discards the invitation.

## FIGHT_INVITATION_ERROR (Send 4309)
**Status:** implemented (Go-only) — `buildFightInvitationError`
**Client source:** `client/.../game/serverToClient/FightInvitationErrorMessage.java:19-44`
**Go source:** `packets_invitation.go` (`buildFightInvitationError`)
**Error codes** (from client `NetFightInvitationFrame` case 4309): 30 = target not found
(offline/unknown), 31 = target busy, 32 = you're busy, 33 = target is yourself.
**Payload (1 byte):** `byte errorCode` — no error-code enum found in reviewed sources.

---

## SET_READY_FOR_FIGHT (Recv 4303)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../world/clientToServer/SetReadyForFightMessage.java:25-77`
**Go source:** `server/internal/dispatch/handlers_fight.go:20-22,37-70` (`handleSetReadyForFight`)
**Payload (variable, minimum 9 bytes):**
```
int64   fightId
byte    fighterCount
  repeated fighterCount times:
    int64   fighterId
```
Client sends a 9-byte message (`fighterCount=0`) if `m_teamPreset.getFightersIds()` is null.

Java server (`SetReadyForFight.java:19-48`): looks up `Fight` and `WaitingOpponent` pairing; marks
this side ready, stores selected fighters, replies `READY_FOR_FIGHT` errorCode=0 + own coachId to
**sender only**. If the *other* side was already ready, both reset to 0 and
`fight.startPreparation()` triggers (eventually sends `CREATE_FIGHT` to both).

Go's `handleSetReadyForFight` mirrors this with an added idempotency guard (`duel.MarkPrepared()`)
to prevent duplicate/concurrent double-trigger — an improvement Java lacks.

**Forced-progress timer (July 2026 fix, `docs/08-java-parity-roadmap.md` §8.13):** previously this
gate had no timeout at all in either implementation — if one coach never sent this packet after
being matched (`OPPONENT_FOUND`), the duel stalled forever. Now armed via
`world.Duel.ArmReadyTimer`/`dispatch.armMatchReadyTimeout`
(`internal/config.CombatConfig.MatchReadyClock`, default 20s), started right after
`OPPONENT_FOUND` is sent. Since the server has no fallback fighter roster for a coach who never
selects anything (the client only ever transmits its final atomic selection, never a partial one),
the timeout **cancels the duel for both sides** (`CancelReasonNoSelectedFighter`) rather than
guessing — a deliberate choice, not a limitation: this is the one gate in the whole flow where
"force the other side's default in" isn't a safe option.

## READY_FOR_FIGHT (Send 4306)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/ReadyForFightMessage.java:22-59`
**Go source:** `packets_fight.go:81-86` (`buildReadyForFight`); sent from `handlers_fight.go:59`
**Payload (variable — 1 or 9 bytes):**
```
byte    errorCode
if errorCode == 0:
    int64   coachId
```
Client only reads `coachId` **if `errorCode==0`** — a conditional-length message. Both Java and Go
always send `errorCode=0` (fixed 9-byte payload) in practice — no error path is ever exercised by
either implementation, so the conditional-length contract is unverified/untested on both sides.

## FIGHT_CREATION_CANCEL_MESSAGE (Recv 4311)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../world/clientToServer/FightCreationCancelMessage.java:22-48`
**Go source:** `handlers_fight.go:23-25,106-126` (`handleFightCreationCancel`)
**Payload (8 bytes):** `int64 fightId`

Java (`FightCreationCancel.java:12-36`): if no opponent pairing found, replies
`FIGHT_CREATION_CANCELED_MESSAGE(fightId, reason=38 INTERNAL_ERROR_DURING_CREATION)` to sender
only. Otherwise sends `reason=42 NO_SELECTED_FIGHTER` to sender **and**
`reason=40 CANCELED_BY_OPPONENT` to the opponent — each side gets a *different* reason.

Go mirrors this exactly, and additionally removes the duel (`deps.Duels.Remove(duelID)`) — an
improvement over Java, which never explicitly removes the stale pairing here.

## FIGHT_CREATION_CANCELED_MESSAGE (Send 4310)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../world/serverToClient/FightCreationCanceledMessage.java:21-56`
**Go source:** `packets_fight.go:107-113` (`buildFightCreationCanceled`)
**Payload (9 bytes):** `int64 fightId; byte cancelReason`

Cancel-reason byte constants identical between Java (`Fight.java:23-37`) and Go
(`packets_fight.go:90-105`): 34=UNABLE_TO_CREATE_FIGHT, 35=TARGET_DISCONNECTED,
36=NO_SELECTED_TEAM, 37=NO_PENDING_FIGHT, 38=INTERNAL_ERROR_DURING_CREATION,
39=NO_INSTANCE_SERVER, 40=CANCELED_BY_OPPONENT, 41=BAD_FIGHT_PARAMETERS, 42=NO_SELECTED_FIGHTER,
43=NOT_ENOUGH_FIGHTERS, 44=NOT_ENOUGH_COACH, 45=INVALID_FIGHTERS_COUNT, 46=INVALID_TEAM_BUDGET,
47=CANT_HOLD_THE_BET.

Sent in three scenarios: (1) explicit cancel (above), (2) disconnect cleanup — Go-only robustness
fix, sends `TARGET_DISCONNECTED`(35) to the surviving side (Java has no disconnect handling for
the duel queue at all), (3) `prepareCreateFight` team-build error path with
`INTERNAL_ERROR_DURING_CREATION`(38) — Go-only, Java's `Fight.startPreparation()` has no error
handling.

---

## Full flow (reconstructed from client source, cross-checked against Go)

**1. Matchmaking (auto-search):**
```
Client → Server : OPPONENT_SEARCH_REQUEST (2301)          {fightTypeId, bet}
Server → Client : OPPONENT_SEARCH_IN_PROGRESS (2304)       {}  (always sent first)
Server → Client : OPPONENT_FOUND (2300)                    {fightId, bet, fightTypeId}  (to BOTH)
```
Cancel path:
```
Client → Server : OPPONENT_SEARCH_CANCEL (2303)            {}
Server → Client : OPPONENT_SEARCH_CANCEL_RESULT (2306)     {errorCode}  (empty payload, both servers)
```

**2. Right-click "challenge" invitation (implemented end-to-end in the Go server; Java stub only):**
```
Client → Server : FIGHT_INVITATION_REQUEST_MESSAGE (4301)  {targetCoachId, fightTypeId, bet}
Server → Client : FIGHT_INVITATION (4300)                  {invitationId, inviter, fightTypeId, bet, teams[]}  (to BOTH)
   [validation failure instead: FIGHT_INVITATION_ERROR (4309) {errorCode 30-33} → inviter]
Client → Server : FightInvitationAcceptMessage (4305)       {invitationId}
   -- or --
Client → Server : FightInvitationRejectMessage (4307)       {invitationId}
Server → Client : FIGHT_INVITATION_ACCEPTED (4302)         {invitationId, fightId}   (to BOTH; creates a Duel)
   -- or --
Server → Client : FIGHT_INVITATION_REJECTED (4304)         {invitationId}            → inviter
```
On accept, the created `fightId` (= `world.Duel.ID`) feeds directly into step 3 below, sharing the
matchmaking fight-setup pipeline unchanged. Disconnect while pending: the surviving party receives
`FIGHT_INVITATION_REJECTED (4304)` to dismiss its dead invitation box.

**3. Fight setup (shared by matchmaking AND invitation-on-accept, once a fightId exists):**
```
Client → Server : SET_READY_FOR_FIGHT (4303)               {fightId, fighterCount, fighterIds[]}
Server → Client : READY_FOR_FIGHT (4306)                   {errorCode(=0), coachId}   (to sender only)
  ... once BOTH sides ready ...
Server → Client : CREATE_FIGHT (8000)                       (see 07-fight-lifecycle.md)
```
Cancel path:
```
Client → Server : FIGHT_CREATION_CANCEL_MESSAGE (4311)      {fightId}
Server → Client : FIGHT_CREATION_CANCELED_MESSAGE (4310)    {fightId, reason=NO_SELECTED_FIGHTER(42)}   → sender
Server → Client : FIGHT_CREATION_CANCELED_MESSAGE (4310)    {fightId, reason=CANCELED_BY_OPPONENT(40)}  → opponent
```
Disconnect-mid-setup (Go-only):
```
Server → Client : FIGHT_CREATION_CANCELED_MESSAGE (4310)    {fightId, reason=TARGET_DISCONNECTED(35)}  → surviving side
```

---

## Summary of discrepancies found

1. **`OPPONENT_SEARCH_ERROR` (2302) was dead code in Java** — no discoverable semantics. Go
   repurposes it as a Go-only request-validation signal (bad-request / no-coach / invalid-params),
   replacing three formerly-silent drops in `handleOpponentSearchRequest`.
2. **`OPPONENT_SEARCH_CANCEL_RESULT` (2306) payload mismatch**: client expects ≥1 byte, both
   servers send 0 bytes — pre-existing Java bug, faithfully (if unintentionally) reproduced by Go.
3. **Fight-invitation chain now implemented in the Go server (Go-only, beyond the Java stub):**
   the legacy Java `FightInvitationRequest` handler ended at a `TODO` and never sent 4300/4302/
   4304/4309. The Go server completes the flow: 4301 request → 4300 invitation (both parties) →
   4305/4307 accept/reject → 4302/4304 result, with 4309 error codes (30-33) and a real
   `world.Duel` created on accept (reusing the matchmaking `DuelManager.Create` path). See
   `internal/world/invitation.go`, `dispatch/packets_invitation.go`, and
   `dispatch/handlers_fight.go`.
4. **Resolved open question:** `FightInvitationAcceptMessage` = opcode **4305**,
   `FightInvitationRejectMessage` = opcode **4307** — both declared directly via `getId()`, no
   runtime capture needed. Both are now wired server-side in Go (never in Java).
5. **`READY_FOR_FIGHT` conditional-length payload is unverified on both sides** — neither
   implementation ever exercises the non-zero-errorCode branch.
6. **Go-side robustness improvements over Java** (intentional, documented): actual dequeue on
   `OPPONENT_SEARCH_CANCEL`; disconnect-mid-duel-setup notification; explicit team-build error path.
7. **Cancel-reason byte constants match exactly** between Java and Go — no discrepancy.
8. **`OPPONENT_FOUND` field ordering matches byte-for-byte** across client/Java/Go — no discrepancy.
