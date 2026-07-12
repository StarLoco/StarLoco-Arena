# DofusArena Go Server — Design Documentation

This directory contains the full design/spec for the rewrite of the DofusArena game
server from Java (`src/org/ankarton`) to **Go 1.26**, preserving wire compatibility with
the existing decompiled client (`client/`, `data/stc/`).

Read in this order:

| # | Document | Content |
|---|---|---|
| 1 | [01-architecture.md](./01-architecture.md) | Package layout, concurrency model, startup sequence, lazy-loading strategy |
| 2 | [02-protocol.md](./02-protocol.md) | Binary wire protocol spec (framing, opcodes, per-packet layouts) |
| 3 | [03-data-model.md](./03-data-model.md) | GORM entities, schema, migrations, multi-DB strategy |
| 4 | [04-game-data-format.md](./04-game-data-format.md) | `.dat` binary game-data file formats + lazy repository design |
| 5 | [05-combat-engine.md](./05-combat-engine.md) | Turn-based combat engine design (new — reconstructed from client reference impl) |
| 6 | [06-config-and-ops.md](./06-config-and-ops.md) | Configuration, logging, migrations workflow, graceful shutdown |
| 7 | [07-roadmap.md](./07-roadmap.md) | Phased delivery plan (original build plan) |
| 8 | [08-java-parity-roadmap.md](./08-java-parity-roadmap.md) | Java-vs-Go feature comparison, status snapshot, and decided next action items (start here for current work) |
| 9 | [09-admin-console.md](./09-admin-console.md) | In-game admin console commands (opcodes 8193/8194/8195): how to open the console, command syntax (`/admin/game/STATUS`), account gating, and the `HELP`/`STATUS`/`PING` commands |
| — | [opcodes/](./opcodes/) | **Per-domain opcode reference** — exhaustive client-source-of-truth payload documentation for every opcode (what to send/receive, exact field types & values), cross-checked against the current Go implementation. Start here to avoid re-reading decompiled client source when implementing/debugging a handler. |

## TL;DR — Key Decisions

- **Language**: Go 1.26.4 (latest stable). Compiled static binary, ~5-10ms cold start,
  low idle memory, goroutines fit the per-connection / per-fight concurrency model well.
- **ORM**: GORM, multi-dialect. **SQLite** for local dev (zero ops). **PostgreSQL** as the
  production default. MySQL/MariaDB also supported (driver swap only, via config) for
  operators who want closer parity with the legacy DB.
- **Networking**: raw TCP, stdlib `net`, goroutine-per-connection. Wire protocol is
  **byte-for-byte compatible** with the existing client — this is a hard constraint,
  not a design choice.
- **Scope**: full feature parity port (auth, social, cards/fighters, teams, matchmaking,
  item exchange) **plus** a real turn-based combat engine, which the original Java server
  never finished implementing (only the presentation phase exists today).
- **Startup target**: < 200ms to accept-ready (well under the < 1s requirement), achieved
  via lazy-loaded game-data repositories instead of Java's eager `FactoryManager.initialize()`.

## Source-of-truth references used to build this spec

- `src/org/ankarton/**` — current Java server (what to port, and what bugs to fix)
- `client/com/ankamagames/**` — decompiled Java client (`AbstractClientMessageDecoder`,
  `DofusArenaMessageDecoder`, per-message `encode()`/`decode()` — the wire protocol ground truth)
- `data/stc/com/ankamagames/baseImpl/common/clientAndServer/game/**` — shared client/server
  reference implementation of the turn-based combat engine (fights, timeline, effects,
  spells, pathfinding) that the Java server never ported — this is the primary source for
  the new combat engine's rules.
- `data/*.dat` — binary game-data files (cards, spells, events, static effects, summoning)
