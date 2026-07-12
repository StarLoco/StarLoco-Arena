# Item Exchange (Card Trading) Opcodes

> Client source of truth, cross-checked against the current Go server implementation.

Coach-to-coach card trading. Lifecycle: **invite** → **accept/reject** → both sides **add/remove
cards** freely → both sides **set ready** → exchange **completes** (implicit, no dedicated
"success" opcode — client infers success from `COACH_INVENTORY_UPDATE`) **or** either side / server
**cancels** at any point, ending with `ITEM_EXCHANGE_END`.

All client `encode()` methods wrap the payload via `addClientHeader((byte)3, ...)`. All layouts
below are big-endian.

**Cross-check result: full parity.** No discrepancies found between client, legacy Java, and Go
for this entire opcode family — see summary at the end.

---

## ITEM_EXCHANGE_INVITATION_REQUEST_MESSAGE (Recv 5101)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeInvitationRequestMessage.java:26-52`
**Go source:** `server/internal/dispatch/handlers_exchange.go:39-59` (`handleExchangeInvitationRequest`)
**Payload (8 bytes):**
```
long    otherUserId    // coach ID of the coach being invited to trade
```
Go resolves target coach, creates the exchange, replies to **requester** with
`ITEM_EXCHANGE_INVITATION_CONFIRMATION` (result=pending=0) and sends
`ITEM_EXCHANGE_INVITATION_REQUEST` to the **target** — matches legacy `CoachExchange.start()`.

## ITEM_EXCHANGE_INVITATION_REQUEST (Send 5102)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeInvitationMessage.java:26-73`
**Go source:** `packets_exchange.go:26-32` (`buildItemExchangeInvitationRequest`)
**Payload (17 + nameLen bytes):**
```
long    exchangeId       // newly-created exchange ID
long    requesterId      // coach ID of the inviter
byte    nameLen          // length of UTF-8 requester name
byte[]  requesterName    // UTF-8, nameLen bytes
```
Sent only to the **invited** coach. Legacy server builds this identically
(`CoachExchange.java:72-77`). Verified by `test/e2e/exchange_test.go:38-45`.

## ITEM_EXCHANGE_INVITATION_ANSWER_MESSAGE (Recv 5103)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeInvitationAnswerMessage.java:25-62`
**Go source:** `handlers_exchange.go:61-93` (`handleExchangeInvitationAnswer`)
**Payload (9 bytes):**
```
long    exchangeId               // the exchange being answered
byte    exchangeInvitationResult // 0 = accept, 1 = decline
```
Can be sent by **either** side (invitee answering, or requester canceling their own pending
invite). Go sends `ITEM_EXCHANGE_INVITATION_CONFIRMATION` to **both** sides regardless of who
answered; on decline the exchange is removed — matches legacy exactly.

## ITEM_EXCHANGE_INVITATION_CONFIRMATION (Send 5104)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeInvitationConfirmationMessage.java:23-70`
**Go source:** `packets_exchange.go:17-24` (`buildItemExchangeInvitationConfirmation`)
**Payload (17 bytes):**
```
byte    invitationResult   // 0 = pending, 2 = refused, 3 = accepted (1 never used for this opcode)
long    exchangeId
long    requestedId        // "the other coach's ID"
```
**Legacy quirk preserved:** on the *initial* pending confirmation
(`CoachExchange.start()` / `handleExchangeInvitationRequest`), `exchangeId` is sent as literal `0`
— not the real exchange ID, despite the field carrying the real ID everywhere else. Go's
`buildItemExchangeInvitationConfirmation(ExchangeConfirmPending, 0, targetCoachID)` preserves this
intentionally. Verified by `test/e2e/exchange_test.go:32-36`.

## ITEM_EXCHANGE_ADD_CARD_MESSAGE (Recv 5105)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeAddCardMessage.java:19-25`
(overrides `getId()`→5105; encode inherited from `ItemExchangeMoveCardMessage.java:22-58` base)
**Go source:** `handlers_exchange.go:95-129` (`handleExchangeAddCard`)
**Payload (18 bytes):**
```
long    exchangeId
long    cardUniqueId    // CoachCard's unique instance ID (m_uid), NOT the template ID
short   cardQuantity
```
On lookup failure, Go ends the exchange with an error (`ITEM_EXCHANGE_END`) — matches legacy
reference server behavior. On success, `AddCard` stacks/clamps quantity and broadcasts
`ITEM_EXCHANGE_CARD_ADDED` to **both** sides.

## ITEM_EXCHANGE_CARD_ADDED (Send 5109)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeCardAddedMessage.java:23-72`
**Go source:** `packets_exchange.go:34-42` (`buildItemExchangeCardAdded`)
**Payload (24 bytes):**
```
long    exchangeId
byte    userIndex        // 0 = "from"/initiator side, 1 = "to" side
int32   templateId        // CoachCard reference/template ID
long    cardUid          // CoachCard unique instance ID
byte    flags            // bit0=LOCKED, bit1=CURSED
short   quantity          // quantity being added
```
`card` decoded via `CoachCard.unserialize()` (13 bytes: cardId, uid, flags), followed by a
separate `getShort()` for quantity (NOT part of the core card serialization). Verified by
`test/e2e/exchange_test.go:67-78`.

## ITEM_EXCHANGE_REMOVE_CARD_MESSAGE (Recv 5106)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeRemoveCardMessage.java:19-25`
(same base class as Add)
**Go source:** `handlers_exchange.go:131-165` (`handleExchangeRemoveCard`)
**Payload (18 bytes):** identical layout to ADD_CARD — `long exchangeId; long cardUniqueId; short cardQuantity`.
If the template wasn't offered or card lookup fails, exchange torn down via error path.

## ITEM_EXCHANGE_CARD_REMOVED (Send 5110)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeCardRemovedMessage.java:23-72`
**Go source:** `packets_exchange.go:44-53` (`buildItemExchangeCardRemoved`)
**Payload:** byte-for-byte identical to CARD_ADDED (5109) — `long exchangeId, byte userIndex,
int32 templateId, long cardUid, byte flags, short quantity`.

## ITEM_EXCHANGE_SET_READY_MESSAGE (Recv 5107)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeSetReadyMessage.java:23-51`
**Go source:** `handlers_exchange.go:167-190` (`handleExchangeSetReady`)
**Payload (8 bytes):** `long exchangeId`.
No explicit ready/unready boolean — this is a **toggle** message (confirmed client- and
server-side). Go's `exchange.SetReady(coach.ID)` flips the appropriate boolean, matching legacy
`exchange.setReadyFrom(!exchange.isReadyFrom())` semantics exactly.

## ITEM_EXCHANGE_USER_READY (Send 5112)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeUserReadyMessage.java:21-59`
**Go source:** `packets_exchange.go:64-70` (`buildItemExchangeUserReady`)
**Payload (9 bytes):** `long exchangeId; byte userIndex` (0="from" toggled, 1="to" toggled).
Carries only *which side* toggled, not the resulting boolean — client tracks state locally. Sent
to **both** participants on every toggle.

## ITEM_EXCHANGE_CANCEL_MESSAGE (Recv 5108)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../clientToServer/exchange/ItemExchangeCancelMessage.java:22-50`
**Go source:** `handlers_exchange.go:192-208` (`handleExchangeCancel`)
**Payload (8 bytes):** `long exchangeId`.
Go validates `InvolvesCoach` then unconditionally tears down (`ITEM_EXCHANGE_END` to both sides +
removal) — matches legacy exactly (no validation of which side is canceling there either).

Go additionally triggers the same teardown on **disconnect** mid-exchange — a Go-only robustness
addition (legacy has no disconnect cleanup for exchanges at all).

## ITEM_EXCHANGE_END (Send 5111)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../serverToClient/exchange/ItemExchangeEndMessage.java:22-60`
**Go source:** `packets_exchange.go:55-62` (`buildItemExchangeEnd`)
**Payload (9 bytes, NOTE reversed field order vs. most other exchange messages):**
```
byte    exchangeEndReason   // legacy code only ever sends 1
long    exchangeId
```
Reason-before-ID ordering confirmed from client decode. `exchangeEndReason` value semantics beyond
`1` are **undetermined** — no other reason values found anywhere in decompiled sources. Go
hardcodes `PutByte(1)` for all call sites (cancel / add-card failure / remove-card failure /
disconnect cleanup) — full parity with legacy "reason is always 1" behavior. Verified by
`test/e2e/exchange_test.go:122-127`.

**No dedicated "trade succeeded" opcode exists.** When both sides are ready, the exchange
completes silently from the wire protocol's perspective — clients only observe success via
`COACH_INVENTORY_UPDATE` (5200) sent to each side. Confirmed in both legacy `CoachExchange.ok()`
(no END message on success) and Go's `completeExchange` (no `buildItemExchangeEnd` call). Exercised
in `test/e2e/exchange_test.go:89-99`.

## ItemExchangeMoveCardMessage.java — resolved: not a distinct opcode
`client/.../clientToServer/exchange/ItemExchangeMoveCardMessage.java` is an **abstract base class**
with no `getId()` override — it is the shared base for Add (5105) and Remove (5106), which each
supply only their own `getId()`. It is correctly absent from `OpCode.Recv`. A same-named but
unrelated `UIExchangeMoveCardMessage.java` exists in the `ui.protocol` package — a purely internal
UI-bus message (ids 16807/16808), never serialized over the wire. **Conclusion: no hidden/
undiscovered opcode exists for "move card."**

---

## Summary of discrepancies found

**None.** Full parity confirmed across all six Recv and six Send opcodes between client, legacy
Java reference server, and the current Go implementation — including subtle quirks: the
`exchangeId=0` literal in the initial pending confirmation, the reason-before-id field order in
`ITEM_EXCHANGE_END`, the "reason is always 1" behavior, toggle-not-boolean ready semantics, and
quantity clamping rules. One deliberate Go-only addition beyond parity: disconnect-triggered
cleanup (not in legacy Java) — a robustness improvement, not a protocol deviation, since it reuses
the exact same `ITEM_EXCHANGE_END` wire format.
