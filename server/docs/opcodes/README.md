# Opcode Reference — Client Source of Truth

This folder is the authoritative, per-domain reference for every opcode in the DofusArena wire
protocol: **what to send, what to receive, the exact payload (type & value) for each field**, and
its current implementation status in the Go server.

Unlike [`../02-protocol.md`](../02-protocol.md) (which gives the general framing rules and a
handful of worked examples), these documents aim to cover **every opcode** exhaustively, one
feature domain per file, so implementing or debugging a handler never requires re-reading the
decompiled client source from scratch.

## Methodology

For every opcode documented here:
1. The **decompiled client** (`client/com/ankamagames/...`) is the source of truth for the wire
   format — its `encode()`/`decode()` methods are read directly, not inferred from the legacy
   Java server (which is often a simplified or buggy reimplementation).
2. The **current Go server** implementation is cross-checked against that client-authoritative
   format, and every discrepancy — bug, intentional fix, or genuine gap — is called out explicitly.
3. Where the legacy `org.ankarton` Java server differs from the client (bugs, dead code, TODOs),
   this is noted for historical context, since the Go server sometimes deliberately preserves a
   legacy quirk for wire compatibility and sometimes deliberately fixes a legacy bug — both are
   called out explicitly rather than assumed.

## Files

| # | File | Covers |
|---|---|---|
| 0 | [00-implementation-status.md](./00-implementation-status.md) | **At-a-glance status of every opcode** (implemented / partial / not implemented / not wired), grouped by domain — start here |
| 1 | [01-connection-auth.md](./01-connection-auth.md) | Connection lifecycle, version check, authentication, queue notification |
| 2 | [02-social-chat.md](./02-social-chat.md) | Vicinity/private chat, friend list, ignore list |
| 3 | [03-coach-world.md](./03-coach-world.md) | Coach creation, world instance entry, actor spawn/despawn/movement, equipment/inventory |
| 4 | [04-matchmaking-invitation.md](./04-matchmaking-invitation.md) | Opponent search (auto-matchmaking), right-click fight invitations, fight-creation cancel |
| 5 | [05-exchange.md](./05-exchange.md) | Coach-to-coach card trading (item exchange) |
| 6 | [06-fighter-team.md](./06-fighter-team.md) | Fighter creation/deletion, fighter inventory, team presets |
| 7 | [07-fight-lifecycle.md](./07-fight-lifecycle.md) | `CREATE_FIGHT` byte-exact payload, presentation/placement/observation/action phase transitions |
| 8 | [08-fight-combat-engine.md](./08-fight-combat-engine.md) | Turn-based combat engine design (timeline, fighter stats, effects) + all combat action opcodes (spells, cards, close combat, movement, end of fight) |

## Status legend

- **implemented** — client and Go server agree on the wire format (byte-for-byte or with a
  documented, deliberate deviation).
- **partial** — implemented but with a known gap (e.g. missing error path, stub data).
- **not implemented** — opcode exists in the protocol but the Go server has no handler/builder for
  it yet. For most of the fight-related opcodes (7, 8) this matches the project's current scope:
  the combat engine hasn't been built yet (the legacy Java server never built one either).
- **not wired** — opcode is defined in the protocol but neither the legacy Java server nor the Go
  server ever send/handle it (dead code on both sides, kept for protocol completeness).

## Known open items across domains

- **Fighter inventory spell-blob format mismatch** (§06): the Go server currently parses the
  spell inventory sub-blob with the wrong per-entry size — flagged as a real wire-incompatibility
  with the actual client, not just a doc issue.
- **`CREATE_FIGHT` (§07) sends empty spell/equipment/stats blobs** — matches legacy Java behavior
  and is apparently tolerated by the client, but means no real loadout data reaches the client via
  this opcode; unconfirmed whether this is patched in via another opcode later.
- **The combat engine itself (§08) is greenfield** — no legacy Java implementation exists to port
  bugs *or* fixes from; the design section is reconstructed from the shared `data/stc` reference
  implementation used by other Ankama games, cross-referenced against the client's own `Fight`/
  `Timeline` classes.
