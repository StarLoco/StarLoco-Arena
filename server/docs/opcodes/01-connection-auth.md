# Connection & Authentication Opcodes

> Client source of truth, cross-checked against the current Go server implementation.
> See [`../02-protocol.md`](../02-protocol.md) §2.2 for general frame/primitive encoding rules.

## Frame recap (needed to read the payload tables below)

**Client → Server (inbound) frame**, confirmed at
`client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/ClientProxyMessage.java:29-42`
(`addClientHeader`):
```
uint16  totalSize          // = 5 + payload.length
uint8   architectureTarget // legacy routing byte: 0=connection/console, 1=auth, 2=world, 3=game, 4=chat
uint16  opcode              // Recv opcode
byte[]  payload              // totalSize - 5 bytes
```

**Server → Client (outbound) frame**, confirmed at
`client/.../AbstractClientMessageDecoder.java:36-64,112` (decoder reads `msgSize` then `msgType`, then
`messageDatas = new byte[msgSize - 4]`):
```
uint16  totalSize   // = 4 + payload.length
uint16  opcode       // Send opcode
byte[]  payload      // totalSize - 4 bytes
```
No architecture-target byte outbound. Strings use a **1-byte length prefix**
(`Message.readString()`, `client/com/ankamagames/framework/kernel/core/common/message/Message.java:198-208`).

Go implements this identically: `server/internal/protocol/frame.go:12-26`
(`InboundHeaderSize=5`, `OutboundHeaderSize=4`), `frame.go:52-82` (`ReadInboundFrame`),
`frame.go:85-104` (`WriteOutboundFrame`).

---

## DISCONNECT (Recv 1)
**Direction:** Client → Server
**Status:** implemented
**Client source:** `client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/DisconnectionNotificationMessage.java:28-39`
**Go source:** `server/internal/dispatch/handlers_connection.go:22-24`; opcode constant `server/internal/protocol/opcodes.go:17`

**Payload:**
```
(empty)   // addClientHeader((byte)0, emptyByteArray)
```

**Flow:** Client sends this then immediately calls `flushAndCloseConnection()`
(`DofusArenaGameEntity.java:279-283`). Go handler simply calls `session.Close()` on receipt.

---

## VERSION (Recv 7)
**Direction:** Client → Server
**Status:** implemented
**Client source:** `client/com/ankamagames/dofusarena/client/network/protocol/message/connection/clientToServer/ClientVersionMessage.java:28-47`
**Go source:** `server/internal/dispatch/handlers_connection.go:39-56` (`handleClientVersion`); opcode `opcodes.go:18`

**Payload:**
```
byte    major        // hardcoded 2 (Version.MAJOR)
short   revision      // hardcoded 4 (Version.MINOR), big-endian
byte    buildLen
byte[]  build         (buildLen bytes, UTF-8, e.g. Version.BUILD_VERSION)
```
`architectureTarget = 0`.

Go read order matches exactly: `payload.Byte()` → `payload.Uint16()` → `payload.String()`, then
`strings.ReplaceAll(build, " ", "")` compared against `deps.Server.Version`
(config defaults `Major:2, Revision:4, Build:"7025"`).

**Behavioral note:** the client's own `Version.implCheckVersion()`
(`client/com/ankamagames/dofusarena/common/constants/Version.java:88-100`) only validates
`major==2` and `revision==4` — it does **not** check the build string. The Go server
additionally rejects on build-string mismatch, which is **stricter** than what the client's own
logic implies is required. Unverified whether the real legacy server enforced this too.

---

## INVALID_VERSION (Send 8)
**Direction:** Server → Client
**Status:** implemented
**Client source:** `client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/InvalidClientVersionMessage.java:23-51`
**Go source:** `server/internal/dispatch/handlers_connection.go:49-55`; opcode `opcodes.go:56`

**Payload:**
```
byte    major        // expected major version
short   revision      // expected revision, big-endian
```
Confirmed 3-byte format from `Version.java:74-78` (`INTERNAL_VERSION = byte[3]`: `put(2); putShort(4)`).
Go write matches exactly (`handlers_connection.go:51-52`).

**Flow:** On mismatch, Go sends `INVALID_VERSION` then immediately `session.Close()` — matches
client's `NetBasicsFrame` case 8 (`onInvalidClientVersion`).

---

## AUTHENTICATION (Recv 1025)
**Direction:** Client → Server
**Status:** implemented
**Client source:** `client/com/ankamagames/dofusarena/client/network/protocol/message/connection/clientToServer/ClientAuthenticationMessage.java:37-60`
**Go source:** `server/internal/dispatch/handlers_connection.go:58-102` (`handleAuthentication`); opcode `opcodes.go:19`

**Payload:**
```
byte    loginLen
byte[]  login         (loginLen bytes, UTF-8)
byte    passwordLen
byte[]  password      (passwordLen bytes, UTF-8)
```
`architectureTarget = 1` (distinct from VERSION's 0). Go read order matches exactly.

---

## AUTHENTICATION_RESULT (Send 1024)
**Direction:** Server → Client
**Status:** implemented (2 of 5 result codes currently unreachable server-side, see discrepancy)
**Client source:** `client/com/ankamagames/dofusarena/client/network/protocol/message/connection/serverToClient/ClientAuthenticationResultsMessage.java:36-92`
**Go source:** `server/internal/dispatch/handlers_connection.go:191-195` (`sendAuthResult`); codes `opcodes.go:120-126`

**Payload:**
```
byte    resultCode    // 0=ok, 2=invalidLogin, 3=alreadyConnected, 4=saveInProgress, 127=closedBeta
```
`checkMessageSize(rawDatas.length, 1, true)` enforces exactly 1 byte.

**Client dead fields:** the class declares `m_newClientId`/`m_nickName` with getters, but `decode()`
never populates them — confirming the wire format really is just 1 byte.

**Flow on success:**
1. `AUTHENTICATION_RESULT` code 0
2. `QUEUE_NOTIFICATION` (Send 8192) — `int -1` payload (no queue)
3. Either `COACH_CREATION_REQUEST` (Send 2048, empty) if the account has no coach, or the
   coach-info + world-entry sequence (`COACH_INFORMATION`, friend/ignore lists,
   `PLAYER_STATISTICS_REPORT`, `ENTER_WORLD_INSTANCE`, `ACTOR_SPAWN`) — see
   [03-coach-world.md](./03-coach-world.md).

On failure (`InvalidLogin`/`AlreadyConnected`): server sends the result byte then closes the
connection.

---

## WORLD_SERVER_UNAVAILABLE (Send 1026)
**Direction:** Server → Client
**Status:** **not implemented** (opcode/name constants exist, but no code path ever sends it —
`server/docs/02-protocol.md` line 95 inaccurately marks this "implemented (rarely sent)")
**Client source:** `client/com/ankamagames/dofusarena/client/network/protocol/message/connection/serverToClient/WorldServerUnavailableMessage.java:22-33`
**Go source:** opcode constant only, `opcodes.go:58`, name `names.go:47`. No send call site anywhere.

**Payload:**
```
(empty)
```
Client-side (`NetAuthenticationFrame.java:112-123`): clears login state, shows a "world loading"
error dialog, closes connection. Structurally moot for the single-process Go monolith but should
be reachable for protocol completeness (e.g. DB/world subsystem failure during login) — currently
dead code.

---

## RECONNECTION_TICKET (Send 2) / RECONNECTION_TICKET_REQUEST_RESULT (Send 4) — legacy/unused
**Direction:** Server → Client (both)
**Status:** not implemented (by design — legacy `baseImpl` leftover, DofusArena never uses it)
**Client source:**
- `client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/ReconnectionTicketMessage.java:23-35`
- `client/.../ReconnectionTicketRequestResultMessage.java:25-41`
- Companion Recv (opcode 3): `ReconnectionTicketRequestMessage.java:34-50`

**Go source:** opcode constants only, `opcodes.go:54-55`. No handler/send call.

**Payload (informational only):**
```
# Send 2 (RECONNECTION_TICKET)
byte[]  ticket   // opaque, whole remaining payload, no length prefix

# Send 4 (RECONNECTION_TICKET_REQUEST_RESULT)
byte    success   // 1=success, else failure (min-size 1 byte)
```
Correctly left unimplemented — matches `docs/02-protocol.md:91-92`.

---

## QUEUE_NOTIFICATION (Send 8192)
**Direction:** Server → Client
**Status:** implemented
**Client source:** `client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/QueueNotificationMessage.java:17-42`
**Go source:** `server/internal/dispatch/handlers_connection.go:197-201` (`sendQueueNotification`); opcode `opcodes.go:111`

**Payload:**
```
int    position   // -1 = queue finished / no queue, >=0 = position in queue
```
`checkMessageSize(rawDatas.length, 4, true)` enforces exactly 4 bytes. Go write matches
(`w.PutInt32(position)`).

Client-side: `position == -1` → `onQueueFinished()`; else → `onQueuePositionUpdate(position)`.
Go always sends `-1` right after a successful `AUTHENTICATION_RESULT` — no real queue implemented.

---

## DEFAULT_RESULT (Send 8195) — note only
**Direction:** Server → Client
**Status:** not wired (opcode constant exists, never sent)
**Client source:** `client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/DefaultResultsMessage.java:21-34`
(extends `QueryResultsResultsMessage.java:18-53`)
**Go source:** opcode constant only, `opcodes.go:114`. No send call.

**Payload:**
```
int   queryResultCode   // generic result code, no fixed enum found client-side
```
Generic catch-all result callback used by legacy `baseImpl` framework for misc admin/console
queries — not used by any DofusArena-specific flow. Correctly left unimplemented.

---

## Summary of discrepancies (Go vs. client source of truth)

1. **AUTHENTICATION_RESULT codes 4 (saveInProgress) and 127 (closedBeta) are unreachable.**
   `protocol.AuthResultCode` defines all 5 values, but `service.AuthResult`
   (`server/internal/service/auth.go:20-26`) only has 3 variants — no code path ever produces
   the other two. Missing feature path, not a wire bug.
2. **WORLD_SERVER_UNAVAILABLE (1026) is never actually sent.** `docs/02-protocol.md:95` is
   inaccurate ("implemented (rarely sent)") — should read "not implemented."
3. **RECONNECTION_TICKET (2) / RECONNECTION_TICKET_REQUEST_RESULT (4)** — confirmed legacy/unused
   in both client and server. No action needed.
4. **DEFAULT_RESULT (8195)** — confirmed unused for this domain in both client and Go server.
5. **VERSION check strictness**: Go rejects on build-string mismatch; the client's own internal
   check only validates major+revision. Whether the real legacy Ankama server also checked the
   build string is unconfirmed — flagged for verification, not changed.
6. **Client dead fields** (`m_newClientId`/`m_nickName` on `ClientAuthenticationResultsMessage`)
   confirm the Go server's 1-byte-only payload is complete and correct.

All other opcodes (DISCONNECT, VERSION, INVALID_VERSION, AUTHENTICATION, QUEUE_NOTIFICATION) have
byte-for-byte matching implementations between client encode/decode and the Go server.
