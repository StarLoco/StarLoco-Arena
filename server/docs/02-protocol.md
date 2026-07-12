# 2. Wire Protocol Specification

This is the authoritative, formally-documented version of the protocol that today only
exists as scattered inline comments in `Fight.java` and the shape of `Parser.java` /
`Buffer.java`, cross-verified against the decompiled client's
`ClientProxyMessage.addClientHeader()` and `AbstractClientMessageDecoder.decode()`. The
Go server **must** produce/consume this exact byte layout — this is a hard compatibility
constraint, not a design choice.

## 2.1 Transport

- Raw TCP, port `443` in the current config (chosen historically to bypass firewalls, not
  for TLS — there is **no encryption** on this connection). The Go server keeps this
  configurable (`network.listen_addr`) and defaults to the same port for drop-in
  compatibility, but the config should make it trivial to change.
- No MINA-style codec filter is needed in Go — framing is handled explicitly by
  `internal/protocol/frame.go` using `encoding/binary` directly on top of a `bufio.Reader`/
  `bufio.Writer` around the `net.Conn`.

## 2.2 Frame format

All multi-byte integers are **big-endian**.

### Client → Server (inbound)

```
+----------------+----------------+----------------+------------------------+
| uint16         | uint8          | uint16          | payload                |
| totalSize      | architecture   | opcode (Recv)   | (totalSize - 5) bytes  |
|                | Target         |                 |                        |
+----------------+----------------+----------------+------------------------+
```

- `totalSize` = 5 (header) + payload length. Confirmed from
  `ClientProxyMessage.addClientHeader()`
  (`client/.../ClientProxyMessage.java:29-41`): `msgSize = 5 + datas.length`, then
  `putShort(msgSize); put(architectureTarget); putShort(getId()); put(datas)`.
- `architectureTarget` is a routing byte the original Ankama multi-server architecture used
  to route a message to the correct backend service. Observed values in the decompiled
  client: `0` (connection/console), `1` (auth), `2` (world/coach/opponent/fight-invite),
  `3` (game/team-management/fight-actions), `4` (chat/social). **This server is a
  single-process monolith**, so this byte is accepted for protocol compatibility but not
  used for routing — it is simply skipped/logged. (This resolves the "spurious extra byte"
  noted in the original Java `Parser.java:43` — it is not a bug, it is this
  architecture-target byte; the Java code correctly reads and discards it, it's just
  undocumented there.)
- `opcode` matches the `Recv` (client→server) opcode table below.

### Server → Client (outbound)

```
+----------------+----------------+------------------------+
| uint16         | uint16         | payload                |
| totalSize      | opcode (Send)  | (totalSize - 4) bytes  |
+----------------+----------------+------------------------+
```

- `totalSize` = 4 (header) + payload length. Confirmed from `Buffer.java:22-28`
  (`putShort(capacity+4).putShort(send.value)`) and matches the client decoder's
  `msgSize`/`msgType` read in `AbstractClientMessageDecoder.decode()` (lines 45,64): the
  decoder subtracts exactly 4 from `msgSize` to get payload length
  (`byte[] messageDatas = new byte[msgSize - 4]`, line 112).
- No architecture-target byte on the outbound side — only client→server messages carry it.

### Primitive encodings

| Type | Encoding |
|---|---|
| `bool` | 1 byte, `0`/`1` |
| `byte` | 1 byte |
| `short` | 2 bytes, big-endian, signed |
| `int` | 4 bytes, big-endian, signed |
| `long` | 8 bytes, big-endian, signed |
| `float` | 4 bytes, big-endian IEEE-754 |
| `string` | length-prefixed: 1 byte length (0-255) + raw UTF-8 bytes (NOT null-terminated). Some larger buffers use a `short` length prefix instead — always specified per-field below. |
| array | typically: length prefix (`byte`/`short` count) + N elements; count size varies per message, documented per-field |

> **See also:** [`opcodes/`](./opcodes/) contains an exhaustive, per-domain opcode reference
> (client source of truth, cross-checked against the current Go server) that supersedes the
> summary tables and worked examples below wherever they conflict. Known corrections found while
> building that reference: §2.3.1's "implemented" status for `MEMBER_NOT_FOUND` (3208),
> `ACTOR_APPEAR` (4102), `ACTOR_TELEPORT` (4510), `WORLD_SERVER_UNAVAILABLE` (1026), and
> `NO_INSTANCE_SERVER_AVAILABLE` (5000) are all stale/inaccurate — none of these are actually sent
> by the current Go server. §2.4.7 (`CREATE_FIGHT`) and §2.4.8 (Fighter serialization) have been
> fully corrected and expanded in [`opcodes/07-fight-lifecycle.md`](./opcodes/07-fight-lifecycle.md)
> and [`opcodes/06-fighter-team.md`](./opcodes/06-fighter-team.md) respectively.

## 2.3 Opcode tables

Values below are the union of what the current Java server implements (`OpCode.java`) and
the **full** protocol surface the client supports
(`DofusArenaMessageDecoder.createMessageFromType`). Anything marked **[NOT IMPLEMENTED]**
exists in the client but has no server-side logic yet in the Java version — these are
primarily the real-time combat opcodes (8100-8300 range) that phase 2 of this rewrite
(the new combat engine) must implement.

### 2.3.1 `Send` (Server → Client)

| Opcode | Name | Status |
|---|---|---|
| 2 | RECONNECTION_TICKET | not used by this game (baseImpl leftover) |
| 4 | RECONNECTION_TICKET_REQUEST_RESULT | not used |
| 8 | INVALID_VERSION | implemented |
| 1024 | AUTHENTICATION_RESULT | implemented |
| 1026 | WORLD_SERVER_UNAVAILABLE | implemented (rarely sent) |
| 2048 | COACH_CREATION_REQUEST | implemented |
| 2050 | COACH_CREATION_RESULT | implemented |
| 2052 | COACH_INFORMATION | implemented |
| 2300 | OPPONENT_FOUND | implemented |
| 2302 | OPPONENT_SEARCH_ERROR | implemented |
| 2304 | OPPONENT_SEARCH_IN_PROGRESS | implemented |
| 2306 | OPPONENT_SEARCH_CANCEL_RESULT | implemented |
| 2400 | PLAYER_STATISTICS_REPORT | stub (always empty report) |
| 3128 | CHANNEL_FLAGS | **[NOT IMPLEMENTED]** — multi-channel chat, out of scope v1 |
| 3130-3142 | CHANNEL_* (join/leave/members/content/flags) | **[NOT IMPLEMENTED]** — out of scope v1 |
| 3144 | FRIEND_LIST_MESSAGE | implemented |
| 3146 | IGNORE_LIST_MESSAGE | implemented |
| 3148/3150 | NOTIFICATION_FRIEND_ONLINE/OFFLINE | **[NOT IMPLEMENTED]** in Java; add in port |
| 3152 | VICINITY_MESSAGE | implemented |
| 3154 | PRIVATE_MESSAGE | implemented |
| 3156 | FRIEND_ADDED_MESSAGE | implemented |
| 3158 | IGNORE_ADDED_MESSAGE | implemented |
| 3160 | FRIEND_REMOVED_MESSAGE | implemented |
| 3162 | IGNORE_REMOVED_MESSAGE | implemented |
| 3202 | CHANNEL_NOT_FOUND | out of scope v1 |
| 3204 | USER_NOT_FOUND | implemented |
| 3206 | MALFORMED_COMMAND | not wired |
| 3208 | MEMBER_NOT_FOUND | implemented |
| 3210 | NOT_ENOUGH_PRIVILEGES | not wired |
| 3212 | NOT_YET_IMPLEMENTED | not wired |
| 3214 | TARGET_IS_YOURSELF | not wired |
| 3216 | OPERATION_NOT_PERMITTED | not wired |
| 4096 | ACTOR_SPAWN | implemented |
| 4098 | ACTOR_DESPAWN | implemented |
| 4102 | ACTOR_APPEAR | implemented (fight only, unfinished) |
| 4104 | ACTOR_DISAPEAR | **[NOT IMPLEMENTED]** |
| 4106 | ACTOR_REPOSITION | **[NOT IMPLEMENTED]** |
| 4300 | FIGHT_INVITATION | implemented (Go-only) |
| 4302/4304 | FIGHT_INVITATION_ACCEPTED/REJECTED | implemented (Go-only) |
| 4306 | READY_FOR_FIGHT | implemented |
| 4309 | FIGHT_INVITATION_ERROR | implemented (Go-only) |
| 4310 | FIGHT_CREATION_CANCELED_MESSAGE | implemented (handler present, logic incomplete) |
| 4500 | ACTOR_MOVEMENT | **[NOT IMPLEMENTED]** (world movement, non-fight) |
| 4506 | FIGHTER_TACKLED | **[NOT IMPLEMENTED]** |
| 4510 | ACTOR_TELEPORT | implemented (used to move players into fight map) |
| 4520 | FIGHTER_DIES | **[NOT IMPLEMENTED]** — combat engine scope |
| 4522 | FIGHTER_CHANGE_DIRECTION | **[NOT IMPLEMENTED]** — combat engine scope |
| 4524 | FIGHTER_MOVE | **[NOT IMPLEMENTED]** — combat engine scope |
| 4600 | ENTER_WORLD_INSTANCE | implemented |
| 5000 | NO_INSTANCE_SERVER_AVAILABLE | implemented (unused in single-process monolith, kept for completeness) |
| 5102 | ITEM_EXCHANGE_INVITATION_REQUEST | implemented |
| 5104 | ITEM_EXCHANGE_INVITATION_CONFIRMATION | implemented |
| 5109 | ITEM_EXCHANGE_CARD_ADDED | implemented |
| 5110 | ITEM_EXCHANGE_CARD_REMOVED | implemented |
| 5111 | ITEM_EXCHANGE_END | implemented |
| 5112 | ITEM_EXCHANGE_USER_READY | implemented |
| 5200 | COACH_INVENTORY_UPDATE | implemented |
| 5202 | COACH_EQUIPMENT_UPDATE | implemented |
| 6000 | FIGHTER_CREATE_RESULT | implemented |
| 6002 | FIGHTER_DELETION_RESULT | implemented |
| 6006 | FIGHTER_INFORMATION_LIST | implemented |
| 6010 | FIGHTER_UPDATED_INFORMATION_INVENTORY | implemented |
| 6020 | TEAM_PRESET_SAVE | implemented |
| 6022 | TEAM_PRESET_DELETION | implemented |
| 6030 | TEAM_PRESET_LIST | implemented |
| 6200 | EFFECT_AREA_ACTION | **[NOT IMPLEMENTED]** — combat engine scope |
| 8000 | CREATE_FIGHT | implemented (hand-built, see §2.4 below) |
| 8010 | START_PRESENTATION | implemented (partial — teleport + notify only) |
| 8012 | TEAM_MATE_SET_READY_FOR_PLACEMENT | **[NOT IMPLEMENTED]** |
| 8018 | END_PRESENTATION | **[NOT IMPLEMENTED]** |
| 8020 | START_PLACEMENT | opcode defined, never sent |
| 8022 | MOVE_TO_FREE_PLACEMENT | **[NOT IMPLEMENTED]** |
| 8024 | TEAM_MATE_SET_READY_FOR_OBSERVATION | **[NOT IMPLEMENTED]** |
| 8028 | END_PLACEMENT | opcode defined, never sent |
| 8030 | START_OBSERVATION | opcode defined, never sent |
| 8032 | TEAM_MATE_SET_READY_FOR_ACTION | **[NOT IMPLEMENTED]** |
| 8038 | END_OBSERVATION | opcode defined, never sent |
| 8040 | START_ACTION | **[NOT IMPLEMENTED]** |
| 8100 | NEW_TABLE_TURN_BEGIN | **[NOT IMPLEMENTED]** — combat engine scope |
| 8104 | FIGHTER_TURN_BEGIN | **[NOT IMPLEMENTED]** — combat engine scope |
| 8106 | FIGHTER_TURN_END | **[NOT IMPLEMENTED]** — combat engine scope |
| 8108 | FIGHTER_CARD_USE | **[NOT IMPLEMENTED]** — combat engine scope |
| 8110 | SPELL_CAST | **[NOT IMPLEMENTED]** — combat engine scope |
| 8112 | CLOSE_COMBAT | **[NOT IMPLEMENTED]** — combat engine scope |
| 8120 | RUNNING_EFFECT_ACTION | **[NOT IMPLEMENTED]** — combat engine scope |
| 8192 | QUEUE_NOTIFICATION | implemented |
| 8193/8194 | CONSOLE_ADMIN_COMMAND(_RESULT) | not wired (admin tooling, low priority) |
| 8195 | DEFAULT_RESULT | not wired |
| 8200 | FIGHT_ACTION_SEQUENCE_EXECUTE | **[NOT IMPLEMENTED]** — combat engine scope |
| 8300 | END_FIGHT | **[NOT IMPLEMENTED]** — combat engine scope |

### 2.3.2 `Recv` (Client → Server)

| Opcode | Name | Status |
|---|---|---|
| 1 | DISCONNECT | implemented |
| 7 | VERSION | implemented |
| 1025 | AUTHENTICATION | implemented |
| 2049 | COACH_CREATION | implemented |
| 2301 | OPPONENT_SEARCH_REQUEST | implemented |
| 2303 | OPPONENT_SEARCH_CANCEL | implemented |
| 3129 | ADD_FRIEND_MESSAGE | implemented |
| 3131 | ADD_IGNORE_MESSAGE | implemented |
| 3133 | REMOVE_FRIEND_MESSAGE | implemented |
| 3135 | REMOVE_IGNORE_MESSAGE | implemented |
| 3153 | VICINITY_MESSAGE | implemented |
| 3155 | PRIVATE_MESSAGE | implemented |
| 4301 | FIGHT_INVITATION_REQUEST_MESSAGE | implemented (Go-only full flow: sends 4300, creates duel on accept) |
| 4305 | FIGHT_INVITATION_ACCEPT_MESSAGE | implemented (Go-only) |
| 4307 | FIGHT_INVITATION_REJECT_MESSAGE | implemented (Go-only) |
| 4303 | SET_READY_FOR_FIGHT | implemented |
| 4311 | FIGHT_CREATION_CANCEL_MESSAGE | implemented (handler present) |
| 4501 | ACTOR_MOVEMENT_REQUEST | handler present, no-op body |
| 5101 | ITEM_EXCHANGE_INVITATION_REQUEST | implemented |
| 5103 | ITEM_EXCHANGE_INVITATION_ANSWER | implemented |
| 5105 | ITEM_EXCHANGE_ADD_CARD | implemented |
| 5106 | ITEM_EXCHANGE_REMOVE_CARD | implemented |
| 5107 | ITEM_EXCHANGE_SET_READY | implemented |
| 5108 | ITEM_EXCHANGE_CANCEL | implemented |
| 5201 | COACH_EQUIPMENT_UPDATE_REQUEST | implemented |
| 5203 | COACH_INVENTORY_UPDATE_REQUEST | implemented |
| 6001 | FIGHTER_CREATE_REQUEST | implemented |
| 6003 | FIGHTER_DELETE_REQUEST | implemented |
| 6005 | FIGHTER_INFORMATION_LIST_REQUEST | implemented |
| 6011 | FIGHTER_UPDATE_INVENTORY_REQUEST | implemented |
| 6021 | TEAM_PRESET_SAVE_REQUEST | implemented |
| 6023 | TEAM_PRESET_DELETE_REQUEST | implemented |
| 6031 | TEAM_PRESET_LIST_REQUEST | implemented |
| 8011 | TEAM_MATE_SET_READY_FOR_PLACEMENT | opcode defined, no handler body |
| 8109 | SPELL_CAST_REQUEST | opcode defined, no handler body — combat engine scope |

Additional client→server opcodes seen in the client (`*RequestMessage.java` classes) that
the Java server never registered in `OpCode.Recv` at all — the Go port should add all of
these for full parity, especially the fight-action ones:

| Opcode source | Name (from client class) | Notes |
|---|---|---|
| `FightInvitationAcceptMessage` / `RejectMessage` | fight invite accept/reject | client-side ids not in decompiled snippet reviewed; extract via same method as others when implementing |
| `TeamMateSetReadyForObservationRequestMessage` | observation-ready | combat engine scope |
| `TeamMateSetReadyForActionRequestMessage` | action-ready | combat engine scope |
| `MoveToFreePlacementRequestMessage` | placement move | combat engine scope |
| `FighterEndTurnRequestMessage` | end turn | combat engine scope |
| `FighterCardUseRequestMessage` | use equipment card in fight | combat engine scope |
| `CloseCombatRequestMessage` | basic melee attack | combat engine scope |
| `GiveUpFightRequestMessage` | forfeit | combat engine scope |
| `FighterActorMovementRequestMessage` / `CoachActorMovementRequestMessage` | in-fight movement | combat engine scope |
| `FighterActorDirectionChangeRequestMessage` | change facing | combat engine scope |
| `EndFightDoneMessage` | client ack after fight end screen | combat engine scope |

> **Action item for implementation phase**: read each of these `*RequestMessage.java`
> `encode()` bodies (same pattern as documented in §2.4) to get their exact field layout
> before implementing the corresponding Go handler; this doc gives the authoritative
> **list**, per-field byte layout should be extracted file-by-file as each opcode is
> implemented, following the method shown in §2.4.

## 2.4 Documented packet layouts (worked examples)

The following are fully reverse-engineered from paired client encode/decode + current
Java server usage, to serve as the pattern to follow for the rest.

### 2.4.1 `AUTHENTICATION` (Recv 1025)

Client: `ClientAuthenticationMessage.encode()`
(`client/.../ClientAuthenticationMessage.java:37-51`)

```
byte    loginLen
byte[]  login        (loginLen bytes, UTF-8)
byte    passwordLen
byte[]  password      (passwordLen bytes, UTF-8)
```

Server reply: `AUTHENTICATION_RESULT` (Send 1024)

```
byte    resultCode    // 0=ok, 2=invalidLogin, 3=alreadyConnected, 4=saveInProgress, 127=closedBeta
```
(confirmed by both `ClientAuthenticationResultsMessage.decode()`, which reads a single
byte, and current server `ClientAuthentication.java:46-63`.)

Server flow on success additionally sends, in order:
1. `AUTHENTICATION_RESULT` code 0
2. `QUEUE_NOTIFICATION` (Send 8192) — `int -1` payload (no queue)
3. Either `COACH_CREATION_REQUEST` (Send 2048, empty payload) if the account has no coach,
   or `COACH_INFORMATION` (Send 2052) + world-enter sequence (§2.4.3) if it does.

### 2.4.2 `COACH_CREATION` (Recv 2049) / `COACH_CREATION_RESULT` (Send 2050)

Request:
```
byte    nameLen
byte[]  name          (nameLen bytes)
byte    skin
byte    hair
byte    sex
```
Response:
```
byte    resultCode     // 0=ok, 11/12=invalidName, 10/13=undefinedMessage
```

### 2.4.3 `COACH_INFORMATION` (Send 2052)

From `Coach.java:159-213` (`sendInformation()`):
```
long    coachId
byte    nameLen
byte[]  name
byte    skin
byte    hair
byte    sex
short   equipmentByteLength     // = 15 * equippedCardCount
  repeated (one per equipped card, pos != 0):
    short pos
    int   templateId
    long  cardInstanceId
    byte  flag
short   inventoryByteLength     // = 15 * unequippedCardCount   -- NOTE: format below is 15
                                 //   bytes/card in size calc but only 15 bytes written
                                 //   (int+long+byte+short = 4+8+1+2=15) — consistent
  repeated (one per unequipped card, pos == 0):
    int   templateId
    long  cardInstanceId
    byte  flag
    short quantity
short   locketSetCount          // always 0 currently
byte    ladder                  // always 2 currently
```
Followed immediately by `FRIEND_LIST_MESSAGE` (Send 3144) and `IGNORE_LIST_MESSAGE`
(Send 3146) — see `Coach.sendFriendsInformation()` / `sendIgnoredInformation()`
(`Coach.java:215-250`):

`FRIEND_LIST_MESSAGE`:
```
byte    friendCount
  repeated:
    byte    nameLen
    byte[]  name
    byte    online          // 0/1
    long    friendCoachId
```
`IGNORE_LIST_MESSAGE`:
```
byte    totalPayloadSize     // NOTE: legacy quirk — this is a byte SUM of name lengths,
                              //  not a count; preserve exactly as-is for compatibility
  repeated:
    byte    nameLen
    byte[]  name
```

### 2.4.4 `ENTER_WORLD_INSTANCE` (Send 4600)

From `Coach.java:124-129,252-255`:
```
float   x
float   y
short   z
short   mapId
byte    dynamic       // 0/1
```

### 2.4.5 `ACTOR_SPAWN` (Send 4096) — world join broadcast

From `Coach.onJoinMap()` (`Coach.java:260-299`), one packet sent to the newly-joined coach
listing all other online coaches:
```
int     coachCount
  repeated:
    byte    actorType         // 1 = coach
    long    coachId
    byte    nameLen
    byte[]  name
    int     x
    int     y
    short   z
    byte    unknownFlag        // always 1 (purpose unclear, preserve as-is)
    byte    skin
    byte    hair
    byte    sex
    short   equipmentByteLength
      repeated (equipped cards):
        short pos
        int   templateId
        long  cardInstanceId
        byte  flag
    byte    trailingZero        // always 0 (purpose unclear, preserve as-is)
```

Client's matching decode (`ActorSpawnMessage.java:33-69`) reads `int charactersCount`
then per-entry a `byte type` (1=Coach via `Coach.unserialize(buffer, 11)`, 2=Fighter via
`Fighter.unserialize(buffer)`) — confirming the discriminator byte and that fight-context
spawns reuse the same opcode with `type=2` fighter entries (see §2.4.7).

### 2.4.6 `ACTOR_DESPAWN` (Send 4098)

```
byte    count           // always 1 in current server (single-entity despawn)
long    coachId
```

### 2.4.7 `CREATE_FIGHT` (Send 8000) / decode reference

This is the most complex packet and is **hand-documented with placeholder/guessed field
names in the current server's inline comments** (`Fight.java:65-209`). The client's
`FightCreationMessage.decode()` (`client/.../FightCreationMessage.java:46-173`) is the
**authoritative** source — use it, not the server's guesses, as ground truth:

```
byte    errorCode                          // 0 = ok, non-zero aborts here
if errorCode == 0:
  short   serializedCoachCardsLen
  byte[]  serializedCoachCards             // opaque blob, per-coach card unlock state
  int     fightTypeId                      // 1=DEFY, 4=TRAINING
  int     bet
  byte    teamCount
  repeated (teamCount times):
    byte    teamId
    byte    teamNameLen
    byte[]  teamName
    byte    coachCount
    repeated (coachCount times):
      <Coach.unserialize(buffer, mode=2) payload>   // coach identity/look, mode-dependent
                                                       // length — see note below
      <initializeCoachSpellInventory(serializedCoachCards)>  // consumes no extra bytes,
                                                                just cross-references the
                                                                blob above
      byte    fighterCount
      repeated (fighterCount times):
        <Fighter.unserialize(buffer) payload>         // fighter identity/stats
      short   statsReportLen
      byte[]  statsReport                              // opaque PlayerStatisticsReport blob
      byte    betCardCount
      repeated (betCardCount times):
        int     referenceCardId
  byte    timelineFighterCount
  repeated (timelineFighterCount times):
    long    fighterId
  byte    eventCount
  repeated (eventCount times):
    int     eventId
  byte    specialCellCount
  repeated (specialCellCount times):
    long    cellBaseId
    long    cellId
    int     x
    int     y
    short   z
```

> **Implementation note**: `Coach.unserialize(buffer, mode)` and `Fighter.unserialize(buffer)`
> are themselves client-side deserializers whose exact byte layout must be extracted from
> `client/com/ankamagames/dofusarena/client/core/game/coach/Coach.java` and
> `.../fighter/Fighter.java` (not yet reviewed in this pass — **do this before implementing
> CREATE_FIGHT in the Go server**, since it's the single most complex packet in the
> protocol and the current Java server's `startPreparation()` builds it by hand with
> comments that only approximate the real format). The mode parameter (`2` for
> CREATE_FIGHT, `11` for ACTOR_SPAWN per §2.4.5) suggests a shared serializer with
> different verbosity levels depending on context — confirm before implementing.

### 2.4.8 Fighter binary serialization (`Fighter.serialize()`, DB-adjacent)

Used both for the `FIGHTER_CREATE_RESULT` (Send 6000) response payload and stored
implicitly via the DB-backed spell/object CSV columns. From `Fighter.java:181-193`:
```
byte    marker               // always 1
short   budget
byte    breed
byte    nameLen
byte[]  name
byte    sex
byte    skin
short   spellsByteLength      // = spells.size() * 4
  repeated: int spellId
short   objectsByteLength     // = objects.size() * 4
  repeated: int cardTemplateId
```

### 2.4.9 GM chat-cheat-commands (via `VICINITY_MESSAGE`, Recv 3153)

Any `VICINITY_MESSAGE` payload starting with `'/'` is intercepted **before** the normal
vicinity-chat broadcast and treated as a GM command instead — it is **never** broadcast as
chat, matched or not (mirrors `VicinityMessage.java`'s `parse()`, which always returns
`true` for any `'/'`-prefixed text). See `internal/dispatch/handlers_gm_commands.go` and
`docs/08-java-parity-roadmap.md` §8.4 for the full rationale/fixes over the Java version.

**Authorization:** every command requires the session's `Account.IsAdmin` flag (snapshotted
at login onto `netio.AccountRef`, see §2.4.1). Non-admins get a silent no-op — the message
is swallowed with no reply and no side effect, same as an unauthenticated attempt.

**Feedback:** replies use the existing `PRIVATE_MESSAGE` (Send 3154) wire shape with a
synthetic sender name `"Server"` and coach ID `0` (which no real coach ever has), since the
protocol has no dedicated system/debug-message opcode.

| Command | Syntax | Behavior |
|---|---|---|
| `/STATS` | `/STATS` | Re-sends `PLAYER_STATISTICS_REPORT` (Send 2400) to self. |
| `/CELLID` | `/CELLID` | Replies with `"x,y,z"` (the coach's current position). |
| `/TP` | `/TP x y z map orientation` | Persists the new position and replies with `ENTER_WORLD_INSTANCE` (Send 4600). |
| `/CARD` | `/CARD id` | Grants one coach card of the given template id (validated against loaded game data) and replies with `COACH_INVENTORY_UPDATE_MESSAGE` (Send 5200) + a confirmation. |
| `/ALLCARDS` | `/ALLCARDS` | Grants one of every known coach-card template. |
| `/PRES` | `/PRES` | Sends `START_PRESENTATION` (Send 8010) to self (empty payload). |
| `/CANCEL` | `/CANCEL id` | Sends `FIGHT_CREATION_CANCELED_MESSAGE` (Send 4310) to self with `fightId=0` and the given reason byte. |
| `/APPEAR` | `/APPEAR [z <z>] [dir <d>] [raw <z> <d>] [here]` | **Debug aid.** Re-sends `ACTOR_APPEAR` (Send 4102) for every fighter in the requester's current fight, optionally overriding altitude (`z`), direction (`dir` 0-7), or placing them all at the requester's own coach cell (`here`), then echoes the exact values sent. Lets fighter placement/rendering parameters be tuned live during a fight without restarting. See §8.16/§8.17 of `08-java-parity-roadmap.md`. |
| *(anything else)* | `/whatever` | Replies `"Unknown command: /whatever"`. |

Deliberately **not** implemented: the client's separate `CONSOLE_ADMIN_COMMAND` /
`CONSOLE_ADMIN_COMMAND_RESULT` opcode pair (Send/Recv 8193/8194, see §2.3.1/§2.3.2) — this
belongs to an unrelated, never-wired-up server-operations/monitoring console protocol, not
in-game GM tooling; see §8.4's investigation notes.

## 2.5 Encryption / auth note

The current protocol sends **passwords in cleartext** over an unencrypted TCP socket.
This is a client-side constraint we cannot unilaterally change without breaking wire
compatibility (the client has no TLS/cipher negotiation in its `AbstractClientMessageDecoder`).
For the Go rewrite:
- The wire format stays cleartext (required for compatibility).
- Server-side, passwords are **never stored or compared in cleartext** — bcrypt-hash on
  account creation, `bcrypt.CompareHashAndPassword` on login. This fixes the current
  `account.getPassword().equals(sPassword)` cleartext-compare bug without touching the wire
  format.
- Recommend documenting to operators that this protocol should only run behind a VPN or
  stunnel/TLS-terminating proxy in production if that's operationally feasible — out of
  scope for the server code itself since the client can't speak TLS.
