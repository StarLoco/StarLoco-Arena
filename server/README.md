# DofusArena — Go Server

A rewrite of the DofusArena game server (originally Java, `../src/org/ankarton`) in
**Go 1.26**, preserving byte-for-byte wire compatibility with the existing decompiled
client (`../client/source`, `data/stc`). See [`docs/`](./docs) for the full design
specification this implementation follows.

## Why this rewrite

The original Java server is a small (~4.6k LOC), functionally incomplete prototype:
auth, social features, card/fighter management, and matchmaking work, but **no real
turn-based combat engine exists** — only the fight "presentation" phase is implemented.
It also carried a number of production-readiness issues: hardcoded DB credentials,
cleartext password comparison, unsynchronized shared state accessed from concurrent I/O
threads, no connection pooling despite the dependency being present, no schema
migrations, and (discovered while porting) an off-by-one bug in the statistics report
packet and two dead-code bugs where parsed spell/event effects were silently discarded.

This Go rewrite:
- Fixes all of the above as a straight port of every existing feature (see Status below).
- Will build a **real combat engine** from scratch, reconstructed from the game-logic
  reference implementation embedded in the decompiled client (`data/stc/com/ankamagames/baseImpl/common/clientAndServer/game/**`),
  which the original server never finished porting. See [`docs/05-combat-engine.md`](./docs/05-combat-engine.md).
  This is the next major phase of work (see Status/Roadmap below).
- Targets **sub-1-second startup** (measured: ~30-50ms cold start to accepting
  connections in local testing), low idle memory, and lazy-loading of game data so
  nothing heavy runs until it's actually needed.

## Status

**Full feature parity with the Java server's non-combat functionality, with tests.**
Every opcode the Java server implements a handler for is implemented here too (verified
by cross-referencing `OpCode.Recv`/`Parser.java`'s switch statement against
`dispatch.RegisterAll`'s registrations — 32/32 opcodes match, the only Java-side opcode
without a handler body, `SPELL_CAST_REQUEST`, is likewise left for the combat-engine
phase in this port).

- ✅ Config loading (YAML + env overrides), structured logging (zerolog)
- ✅ Wire protocol framing + opcode tables + typed packet reader/writer
- ✅ Multi-dialect database layer (SQLite / PostgreSQL / MySQL) via GORM, with migrations
- ✅ TCP listener with panic-isolated per-connection goroutines and a non-blocking
  outbound queue (a real send/close race was found and fixed here via testing, see
  `internal/netio/conn.go`'s doc comments)
- ✅ Lazily-loaded, indexed game-data repositories (`.dat` file parsers for
  cards/spells/events/summoning)
- ✅ Auth: login, bcrypt password hashing (fixes the legacy cleartext comparison),
  duplicate-login rejection, disconnect cleanup
- ✅ Coach: creation (name validation, forbidden-word filtering), profile info, world
  join/leave broadcast (`ACTOR_SPAWN`/`ACTOR_DESPAWN`), position tracking/movement
- ✅ Social: friends/ignore lists (add/remove), private messages, vicinity (world) chat
- ✅ Fighters: create/delete/list, spell & equipment loadout management
- ✅ Team presets: save/update/delete/list
- ✅ Coach inventory/equipment: card lock/unlock, remove, equip/unequip with world
  broadcast
- ✅ Matchmaking → Duel setup flow: opponent search/cancel, fighter-ready selection,
  `CREATE_FIGHT` packet generation, fight-creation cancel, placement readiness + map
  teleport + `START_PRESENTATION`
- ✅ Item exchange (player trading): invitation, accept/reject, add/remove cards
  (with quantity clamping), ready-up, completed trade (actual card transfer), cancel
- ✅ Combat fight actor **skeleton** (phase state machine, turn timeline, fighter/team
  models) — structurally wired, but turn/spell/damage resolution itself is not yet
  implemented (see [`docs/05-combat-engine.md`](./docs/05-combat-engine.md) and
  [`docs/07-roadmap.md`](./docs/07-roadmap.md) phase 4)

### What's NOT yet done

- The actual turn-based combat engine: spell validation, damage formulas, pathfinding,
  running effects. This is the one major feature the Java server itself never finished
  either — see `docs/05-combat-engine.md` for the full design already written for it.
- A few Java-side stubs are intentionally preserved as stubs rather than invented:
  `FightInvitationRequest` (right-click "challenge player", incomplete in the Java
  version too, confirmed via its own inline `TODO`) and the multi-channel chat opcodes
  (`CHANNEL_*`, never used by this game mode).

### Test coverage

```
internal/protocol       - wire framing + reader/writer round-trip, edge cases
internal/gamedata        - lazy-repository semantics + all .dat file format parsers
internal/world            - registry/matchmaker/duel/exchange state machines (incl.
                             concurrency tests)
internal/service           - full business-logic suite against a real in-memory SQLite
                              DB with migrations applied (auth, coach, fighter, team,
                              social) -- this suite caught a real bug: the SQLite driver
                              swap silently disabled foreign-key/cascade-delete
                              enforcement due to a DSN syntax mismatch
test/e2e                    - full server, real TCP sockets, exercises the exact
                              production wiring (internal/app) end-to-end: login flows,
                              fighters, teams, social/chat, matchmaking->duel->
                              CREATE_FIGHT, and the complete item-exchange trade flow
```

All packages pass `go test -race ./...`.

## Requirements

- Go **1.26.4** or later (`go.mod` pins `go 1.26.4`)
- No CGO / C toolchain required for production builds (`CGO_ENABLED=0` works) — the
  SQLite driver (`glebarez/sqlite`) is pure Go. A C toolchain is only needed if you want
  to run `go test -race` locally (Go's race detector requires cgo).

## Getting started

```powershell
# from server/
go mod tidy

# run against local SQLite (zero setup)
go run ./cmd/server --config configs/config.dev.yaml

# accounts aren't provisioned over the wire (matching the legacy design) --
# seed one for local testing:
go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123

# build a binary
go build -o bin/arena-server.exe ./cmd/server
./bin/arena-server.exe --config configs/config.dev.yaml
```

On first run this will create `arena.dev.db` (SQLite, WAL mode) in the working directory
and apply all pending migrations automatically.

For production, copy `configs/config.example.yaml`, set `database.driver` to `postgres`
(recommended) or `mysql`, and supply a real DSN — either directly in the file or via the
`ARENA_DATABASE__DSN` environment variable (see `configs/.env.example`).

> **SQLite DSN note**: the pure-Go SQLite driver used here (`glebarez/sqlite`) only
> recognizes `_pragma=name(value)` query parameters, NOT the `mattn/go-sqlite3`-style
> `_journal_mode=WAL&_foreign_keys=on` shorthand (which it silently ignores rather than
> erroring on). Always write `?_pragma=journal_mode(WAL)&_pragma=foreign_keys(1)` — see
> `configs/config.dev.yaml` for a working example. This one bit us once already.

### CLI flags

| Flag | Effect |
|---|---|
| `--config <path>` | Path to a YAML config file (optional; defaults + env vars are used otherwise) |
| `--no-migrate` | Skip running database migrations on startup |
| `--migrate-only` | Apply pending migrations then exit immediately |
| `--warm-cache` | Eagerly load all game-data repositories at startup instead of lazily |

## Project layout

```
server/
├── cmd/
│   ├── server/            main entry point (thin wrapper around internal/app)
│   └── seedaccount/         dev helper: insert a bcrypt-hashed test account
├── internal/
│   ├── app/                 composition root shared by cmd/server AND test/e2e
│   ├── config/               YAML + env configuration
│   ├── log/                   zerolog setup
│   ├── protocol/               wire framing, opcode tables, packet reader/writer
│   ├── netio/                   TCP listener, per-connection goroutines
│   ├── db/                       GORM multi-driver setup + migrations
│   ├── domain/                    persistence models (GORM structs)
│   ├── gamedata/                   lazy-loaded .dat file repositories
│   │   └── parser/                  binary .dat format readers
│   ├── world/                        online-player registry, matchmaking queue,
│   │                                  duel setup, item-exchange state
│   ├── combat/                        fight actor, turn timeline, fighter/team models
│   ├── service/                        business logic (auth, coach, fighter, team,
│   │                                    social, matchmaking)
│   ├── dispatch/                        opcode -> handler routing + packet builders
│   └── testutil/                         shared test helpers (in-memory SQLite + migrations)
├── migrations/{sqlite,postgres,mysql}/  versioned SQL migrations, embedded in the binary
├── data/                    game data consumed at runtime (.dat/.ade/.amw + maps/)
│                            and the decompiled game-logic reference under data/stc/
├── test/e2e/                full-server end-to-end tests (real TCP + real DB)
├── scripts/                 manual Python smoke-test clients (not part of the build)
├── configs/                 example configs
└── docs/                    full design specification (read this first)
```

## Documentation

Start with [`docs/README.md`](./docs/README.md) for the full design specification index,
covering architecture, the wire protocol spec, data model, game-data file formats, the
combat engine design, config/ops, and the phased delivery roadmap.

## Testing

```powershell
go vet ./...
go build ./...
go test ./...              # full suite, no CGO required
go test -race ./...        # requires a C toolchain (e.g. winget install BrechtSanders.WinLibs.POSIX.UCRT)
```
