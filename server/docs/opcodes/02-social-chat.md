# Social, Chat & Friend/Ignore List Opcodes

> Client source of truth, cross-checked against the current Go server implementation.

**Client source note:** the actual wire classes for all opcodes in this domain live under
`client/com/ankamagames/baseImpl/client/proxyclient/base/network/protocol/message/chat/**` (the
*generic baseImpl chat engine*), confirmed as the classes actually wired to these opcode numbers
via `DofusArenaMessageDecoder.createMessageFromType` (`client/.../network/protocol/DofusArenaMessageDecoder.java:321-430`)
and the dispatch switch in `NetChatFrame.java:51-399`. The `com/ankamagames/dofusarena/client/**/chat/**`
tree (`ChatView`, `ChatVicinityPipe`, `console/command/*`) is UI/command-parsing only — it never
defines wire opcodes itself.

All client messages extend `ClientProxyMessage`, whose `addClientHeader(architectureTarget, datas)`
prepends the standard 5-byte frame header (`architectureTarget = 4` for chat opcodes, except
`UserVicinityContentMessage` which uses `3`) — framing detail only, not part of the payload below.

---

## ADD_FRIEND_MESSAGE (Recv 3129)
**Direction:** Client → Server
**Status:** implemented
**Client source:** `client/.../chat/clientToServer/AddFriendMessage.java:26-56`
**Go source:** `server/internal/dispatch/handlers_social.go:17-19,35-84` (`handleSocialEdit(isFriend=true, isAdd=true)`)

**Payload:**
```
byte    nameLen       // length of following name in bytes
byte[]  name          // target coach name, UTF-8
```

**Notes:**
- Legacy `org.ankarton` server (`AddFriendMessage.java:19-46`) looks up target by exact DB name
  match; on success sends `FRIEND_ADDED_MESSAGE` to **requester only**. On DB miss, legacy has a
  `//TODO: User not found` comment and **never actually sends USER_NOT_FOUND** (dead code).
- Go: on DB miss, correctly replies `USER_NOT_FOUND` (improvement). On no-op (edge already
  existed), sends nothing — matches legacy.
- **Not broadcast to the target** — one-way "friends list" concept, not a mutual friend request.

## REMOVE_FRIEND_MESSAGE (Recv 3133)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../chat/clientToServer/RemoveFriendMessage.java:27-57`
**Go source:** `handlers_social.go:20-22,35-84` (`handleSocialEdit(isFriend=true, isAdd=false)`)
**Payload:** `byte nameLen; byte[] name` — identical shape to ADD_FRIEND_MESSAGE. Result:
`FRIEND_REMOVED_MESSAGE` (Send 3160) to requester only.

## ADD_IGNORE_MESSAGE (Recv 3131)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../chat/clientToServer/AddIgnoreMessage.java:25-58`
**Go source:** `handlers_social.go:23-25,35-84` (`handleSocialEdit(isFriend=false, isAdd=true)`)
**Payload:** `byte nameLen; byte[] name`. Result: `IGNORE_ADDED_MESSAGE` (Send 3158) to requester only.

## REMOVE_IGNORE_MESSAGE (Recv 3135)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../chat/clientToServer/RemoveIgnoreMessage.java:25-57`
**Go source:** `handlers_social.go:26-28,35-84` (`handleSocialEdit(isFriend=false, isAdd=false)`)
**Payload:** `byte nameLen; byte[] name`. Result: `IGNORE_REMOVED_MESSAGE` (Send 3162) to requester only.

**Cross-check note (all four ADD/REMOVE opcodes):** Go unifies all four into a single
`handleSocialEdit` helper (`handlers_social.go:35-84`) — a clean generalization of 4
near-duplicated legacy Java classes. Response includes the target's `int64` coach ID appended
after the name (`w.PutString(target.Name).PutInt64(int64(target.ID))`).

---

## VICINITY_MESSAGE (Recv 3153)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../chat/clientToServer/UserVicinityContentMessage.java:28-58`
**Go source:** `server/internal/dispatch/handlers_chat.go:49-81` (`handleVicinityMessage`)

**Payload:**
```
short   messageContentLen   // NOTE: 2-byte length prefix (unlike most other chat opcodes' 1 byte)
byte[]  messageContent      // UTF-8
```
`architectureTarget = 3` for this message (not the usual 4) — framing detail only.

**Notes:**
- If `messageContent` starts with `'/'`, Go intercepts it as a GM command *before* broadcast
  (`handlers_chat.go:63-72`), matching legacy `VicinityMessage.java`'s `parse()` — always swallows
  `/`-prefixed text (matched or not), never broadcasts it as chat. See
  [`../02-protocol.md`](../02-protocol.md) §2.4.9 for the full GM command table.
- Otherwise broadcast as `VICINITY_MESSAGE` (Send 3152) to all *other* online coaches — sender does
  not receive their own message echoed back.

## VICINITY_MESSAGE (Send 3152)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/VicinityContentMessage.java:23-77`
**Go source:** `handlers_chat.go:74-80`
**Payload:**
```
byte    memberTalkingLen
byte[]  memberTalking       // sender name, UTF-8
long    memberIDTalking     // 8-byte sender coach ID
byte    messageContentLen   // NOTE: 1-byte prefix here (contrast with Recv's 2-byte prefix)
byte[]  messageContent
```
Client dispatch: `NetChatFrame.java:307-315` — pushed to pipe 1 (vicinity pipe).

---

## PRIVATE_MESSAGE (Recv 3155)
**Direction:** Client → Server · **Status:** implemented
**Client source:** `client/.../chat/clientToServer/UserPrivateContentMessage.java:26-76`
**Go source:** `handlers_chat.go:22-47` (`handlePrivateMessage`)
**Payload:**
```
byte    userNameLen
byte[]  userName            // target coach name
byte    messageContentLen
byte[]  messageContent
```
**Notes:** Go looks up target via `deps.World.GetByName` (online registry); if not found, replies
`USER_NOT_FOUND` (Send 3204). Legacy Java matches (`PrivateMessage.java:19-48`).

## PRIVATE_MESSAGE (Send 3154)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/PrivateContentMessage.java:23-76`
**Go source:** `handlers_chat.go:44-46`
**Payload:** byte-identical shape to VICINITY_MESSAGE (Send 3152) — `memberTalkingLen, memberTalking,
memberIDTalking(long), messageContentLen, messageContent`. Client dispatch: pipe 2 (private).
Also reused (repurposed) by the GM-command feedback mechanism with synthetic sender `"Server"`/
coachID `0` — see `../02-protocol.md` §2.4.9.

---

## FRIEND_LIST_MESSAGE (Send 3144)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/FriendListMessage.java:71-89`
**Go source:** `server/internal/dispatch/packets_coach.go:41-58` (`buildFriendList`); legacy
`src/org/ankarton/world/entity/coach/Coach.java:215-234`
**Payload:**
```
byte    friendCount
  repeated friendCount times:
    byte    nameLen
    byte[]  name
    byte    online          // 0/1, computed server-side from online registry, not stored
    long    friendCoachId
```
Sent immediately after `COACH_INFORMATION` (Send 2052) in the post-login sequence. Byte-for-byte
match confirmed between client decoder and Go builder.

## IGNORE_LIST_MESSAGE (Send 3146)
**Direction:** Server → Client · **Status:** implemented (with a preserved legacy quirk)
**Client source:** `client/.../chat/serverToClient/IgnoreListMessage.java:18-62`
**Go source:** `packets_coach.go:60-81` (`buildIgnoreList`); legacy `Coach.java:236-250`
**Payload:**
```
byte    totalPayloadSize     // *** LEGACY QUIRK: byte SUM of all name lengths, ***
                              // *** NOT an entry/list count (unlike FRIEND_LIST_MESSAGE) ***
  repeated (until bytes consumed, no explicit count):
    byte    nameLen
    byte[]  name
```
**This quirk is confirmed in three places:**
1. Legacy Java encode (`Coach.java:240-248`): `sizeList` accumulates `name.length()+1` per entry,
   and the wire byte written is the **sum**, not `this.ignored.size()`.
2. Client decode (`IgnoreListMessage.java:31`): `int m_ignoreListSize = bb.get();` then loops
   `for (i=0; i<m_ignoreListSize; i++)` reading one `nameLen+name` pair per iteration — the loop
   bound is nonsensical relative to entry count. This is a genuine client-side decode bug inherited
   from the wire format design (only "works" for short lists / short names by coincidence).
3. Go (`packets_coach.go:60-63`) deliberately preserves this exact quirk byte-for-byte for wire
   compatibility.

**Discrepancy flag:** fragile/buggy by original design. Go's preservation is correct for wire
compatibility, but should be flagged as a trap for any Go-native client rewrite.

---

## FRIEND_ADDED_MESSAGE (Send 3156)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/FriendAddedMessage.java:21-59`
**Go source:** `handlers_social.go:80-84`
**Payload:** `byte friendNameLen; byte[] friendName; long friendId`. Sent only to requester.

## FRIEND_REMOVED_MESSAGE (Send 3160)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/FriendRemovedMessage.java:21-51`
**Go source:** `handlers_social.go:80-84`
**Payload (client-expected):** `byte friendNameLen; byte[] friendName` — **no trailing `long id`**
field client-side (asymmetric with FRIEND_ADDED_MESSAGE).

**Discrepancy:** Go's shared response builder always writes `name + int64(id)` for **all four**
result opcodes uniformly, so Go sends an extra trailing 8 bytes here (and on IGNORE_ADDED/REMOVED)
that the client never reads. Harmless (client's frame-length-bounded parsing discards trailing
bytes) but means these three payloads are not byte-identical to what the client class declares it
reads — though see next entries, this actually matches the **legacy Java server's** wire output.

## IGNORE_ADDED_MESSAGE (Send 3158)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/IgnoreAddedMessage.java:22-52`
**Go source:** `handlers_social.go:80-84`
**Payload (client-expected):** `byte ignoreNameLen; byte[] ignoreName` (no trailing ID expected).
Legacy Java (`AddIgnoreMessage.java:30-34`) **does** write a trailing `long friend.getId()` despite
the client not reading it — Go's extra field matches the **legacy server's** actual wire output;
only the client decoder ignores the extra bytes. Not a Go-vs-Java discrepancy.

## IGNORE_REMOVED_MESSAGE (Send 3162)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/IgnoreRemovedMessage.java:22-52`
**Go source:** `handlers_social.go:80-84`
Same situation as IGNORE_ADDED_MESSAGE — legacy Java (`RemoveIgnoreMessage.java:30-34`) also writes
the trailing long; Go matches legacy Java's actual wire output.

---

## USER_NOT_FOUND (Send 3204)
**Direction:** Server → Client · **Status:** implemented
**Client source:** `client/.../chat/serverToClient/errorMessage/UserNotFoundMessage.java:29-59`
**Go source:** `handlers_social.go:99-103` (`sendUserNotFound`), also used by `handlers_chat.go:29-31`
**Payload:** `byte userNameLen; byte[] userName`.

## MEMBER_NOT_FOUND (Send 3208)
**Direction:** Server → Client
**Status:** **not wired** (opcode defined, never sent — corrects `../02-protocol.md` line 118
which incorrectly lists this as "implemented")
**Client source:** `client/.../chat/serverToClient/errorMessage/MemberNotFoundMessage.java:23-54`
**Go source:** `opcodes.go:76` (`SendMemberNotFound`), `names.go:65` — no call site anywhere.
**Payload (per client decoder):** `byte memberNameLen; byte[] memberName`.
This is a channel-membership error (multi-channel chat feature, out of scope v1).

## CHANNEL_NOT_FOUND (Send 3202)
**Status:** not wired (out of scope v1)
**Client source:** `client/.../chat/serverToClient/errorMessage/ChannelNotFoundMessage.java:23-55`
**Payload:** `byte channelNameLen; byte[] channelName`.

## MALFORMED_COMMAND (Send 3206) / NOT_ENOUGH_PRIVILEGES (3210) / NOT_YET_IMPLEMENTED (3212) / TARGET_IS_YOURSELF (3214) / OPERATION_NOT_PERMITTED (3216)
**Status:** not wired — all have no-op `decode()` (empty payload, 0 bytes) client-side:
- `MalformedCommandMessage.java:19-34`
- `NotEnoughPrivilegesMessage.java:19-34`
- `NotYetImplementedMessage.java:19-34`
- `TargetIsYourselfMessage.java:19-34`
- `OperationNotPermitedMessage.java:19-34` (also the decoder's fallback/default case for any
  unrecognized `msgType`, `DofusArenaMessageDecoder.java:424-430`)

**Note on TARGET_IS_YOURSELF:** neither legacy Java nor Go implement a self-target check for
friend/ignore/private-message — sending ADD_FRIEND to your own name would succeed silently
(or no-op) rather than triggering this error. Consistent behavior between old/new servers, just
an unimplemented protective check in both.

## CHANNEL_FLAGS (Send 3128) / CHANNEL_* (Send 3130-3142)
**Status:** [NOT IMPLEMENTED / out of scope] — multi-channel chat feature, vestigial/unused by
DofusArena's actual game design (which only uses vicinity + private + friend/ignore chat).
**Client source:** `client/.../chat/serverToClient/{ChannelFlagsMessage, ChannelJoinMessage,
ChannelMemberFlagsMessage, ChannelMemberKickMessage, ChannelMembersMessage, ChannelContentMessage,
ChatUserFlagsMessage}.java`. Full range per `NetChatFrame.java` switch: 3128 (flags), 3130 (join),
3132 (leave), 3134 (member flags), 3136 (member kick), 3138 (members list), 3140 (content), 3142
(user flags).

## NOTIFICATION_FRIEND_ONLINE (Send 3148) / NOTIFICATION_FRIEND_OFFLINE (Send 3150)
**Status:** not implemented in Java (legacy) or Go
**Client source:** `client/.../chat/serverToClient/NotificationFriendOnlineMessage.java:25-62`,
`NotificationFriendOfflineMessage.java:23-54`
**Payload (online):** `byte friendNameLen; byte[] friendName; long userId`
**Payload (offline):** `byte friendNameLen; byte[] friendName`
Since the server never sends these, the client's friend list only reflects online/offline state
snapshotted at the last `FRIEND_LIST_MESSAGE` (login-time) — mid-session changes are invisible
without a reconnect. Known/accepted gap per `../02-protocol.md` line 108.

(Note: client also defines `NotificationIgnoreOnlineMessage`/`NotificationIgnoreOfflineMessage`
at opcodes 3164/3166 — not implemented server-side either, same never-implemented pattern.)

---

## Summary of discrepancies found

1. **`../02-protocol.md` line 118 is wrong**: lists `MEMBER_NOT_FOUND` (3208) as "implemented" —
   no call site anywhere sends it. Should read "not wired" like its error-opcode siblings.
2. **IGNORE_LIST_MESSAGE (3146) has a genuinely fragile wire format**: the client's decode loop
   uses the byte-sum-of-name-lengths field as its *iteration count*, semantically wrong relative
   to what's counted. Go faithfully preserves this for wire compatibility (correct call), but the
   original retail client likely had unreliable ignore-list decoding for >1 entry or longer names.
3. **FRIEND_REMOVED_MESSAGE / IGNORE_ADDED_MESSAGE / IGNORE_REMOVED_MESSAGE have an extra unread
   trailing `long targetCoachID`** in both Go and legacy Java (client decoders never read it,
   unlike FRIEND_ADDED_MESSAGE). Harmless, and Go's behavior is a faithful match of the legacy
   Java server's wire output — not a Go-introduced bug.
4. **No self-target or privilege checks exist in either legacy Java or Go** for friend/ignore/
   private-message opcodes despite full client-side error UI support. Consistent, not a regression.
5. **Friend/ignore relationship model is one-way and silent** — adding/removing never notifies the
   other party. Identical in both legacy Java and Go — accurate, non-regressive port.
6. **NOTIFICATION_FRIEND_ONLINE/OFFLINE (3148/3150)** and ignore-equivalents (3164/3166, discovered
   during trace, not in original opcode list) unimplemented in both old and new servers — already
   correctly flagged in `../02-protocol.md` line 108.
7. `CHANNEL_FLAGS`/`CHANNEL_*` and the payload-less chat-error opcodes are correctly and
   consistently marked "not wired"/out-of-scope — no discrepancy, confirmed via full client review.
