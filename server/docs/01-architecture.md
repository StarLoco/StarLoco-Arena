# 1. Architecture

## 1.1 Goals recap

- Cold start to "accepting connections" in **< 1s** (target: < 200ms).
- Low idle memory footprint (target: < 30MB RSS at idle with no players connected).
- Lazy-loading: nothing heavy loads until it's actually needed.
- Resilience: a panic in one connection or one fight must never take down the process.
- Byte-for-byte wire compatibility with the existing decompiled client.

## 1.2 Module layout

```
server/
├── cmd/
│   └── server/
│       └── main.go                 entry point: wires config → db → repos → net → serve
├── internal/
│   ├── config/                     YAML + env config loading & validation (koanf)
│   ├── log/                        zerolog setup, contextual logger helpers
│   ├── protocol/                   wire framing + opcode registry + packet codec
│   │   ├── frame.go                 [uint16 size][byte target][uint16 opcode][payload] framing
│   │   ├── opcodes.go                Send/Recv opcode constants (generated from docs/02-protocol.md table)
│   │   ├── reader.go                 binary.Read helpers: length-prefixed strings, arrays
│   │   └── writer.go                 PacketBuilder (fluent, mirrors old Buffer.java but safe/typed)
│   ├── netio/                      TCP listener & connection lifecycle
│   │   ├── listener.go               net.Listen, accept loop, graceful shutdown
│   │   ├── conn.go                   per-connection goroutine: read loop, write queue, panic recovery
│   │   └── session.go                Session struct (replaces Client.java): wraps net.Conn + Account
│   ├── db/                         GORM setup
│   │   ├── db.go                     driver selection (sqlite/postgres/mysql) from config
│   │   └── migrate.go                golang-migrate wiring
│   ├── domain/                     persistence models (GORM structs) — see 03-data-model.md
│   ├── gamedata/                   lazy-loaded, indexed repositories for .dat content — see 04
│   │   ├── repository.go             generic Repository[T] with sync.Once lazy load
│   │   ├── coachcard.go / fightercard.go / spell.go / effect.go / event.go / summoning.go
│   │   └── parser/                   binary .dat file parsers (byte-compatible with DocumentLoader.java)
│   ├── world/                      online-player registry, matchmaking queue
│   │   ├── registry.go               sync.Map-based online coach registry (fixes Java's ArrayList race)
│   │   └── matchmaking.go            waiting-opponent queue
│   ├── combat/                     NEW real-time turn-based combat engine — see 05-combat-engine.md
│   │   ├── fight.go                   Fight actor (goroutine + channel inbox)
│   │   ├── manager.go                 FightManager: registry + spawn
│   │   ├── timeline.go                turn-order / table-turn timeline
│   │   ├── fighter.go                 in-fight fighter state (HP/AP/MP/position/etc.)
│   │   ├── effect/                    running-effect implementations (HPLoss, Push, Teleport, ...)
│   │   ├── spell/                     spell cast validation pipeline
│   │   └── pathfind/                  A* pathfinding (ported from framework/ai/pathfinder)
│   ├── service/                    business logic layer (replaces "fat entity" Java classes)
│   │   ├── auth.go                    login/authentication, bcrypt verification
│   │   ├── coach.go                   coach creation, info broadcast
│   │   ├── social.go                  friends/ignore lists, private/vicinity chat
│   │   ├── fighter.go                 fighter CRUD
│   │   ├── team.go                    team preset CRUD
│   │   ├── exchange.go                item exchange session
│   │   └── matchmaking.go             opponent search
│   └── dispatch/                   opcode → handler routing table (replaces Parser.java switch)
├── migrations/                     versioned .sql files (golang-migrate)
├── configs/
│   ├── config.example.yaml
│   └── .env.example
├── go.mod
└── go.sum
```

## 1.3 Concurrency model

Three concurrency domains, deliberately kept separate:

1. **Connection I/O** — one goroutine per TCP connection (`netio/conn.go`), doing blocking
   reads off `net.Conn` and dispatching parsed packets to `service/*` handlers. A second
   goroutine per connection owns a buffered outbound channel and does the actual
   `conn.Write` — this avoids interleaved/torn writes from concurrent senders (broadcasts,
   fight events, direct replies) without needing a mutex around the socket.
2. **World state** — the online-coach registry (`world/registry.go`) is a `sync.Map` keyed
   by coach ID (replaces Java's unsynchronized `ArrayList<Coach>`, a real race condition
   in the current code). Broadcast operations (join/leave notifications, chat) range over
   the map and push onto each target's outbound channel — non-blocking, bounded, drops
   (with a log warning) if a slow client's queue is full rather than blocking the sender.
3. **Fights** — each active `Fight` runs as its own **actor goroutine** with a buffered
   channel inbox (`combat/fight.go`). All mutations to fight state (turn advancement,
   spell casts, damage application) happen serially inside that one goroutine — no locks
   needed inside a fight. Connection goroutines that need to affect a fight send a command
   struct into the fight's channel and (optionally) await a response future. A
   `recover()` at the top of the fight's run loop means a bug in combat logic can only
   kill that one fight (and gracefully end it / notify participants), never the server.

This mirrors Go's "share memory by communicating" idiom and avoids the global mutable
singletons + implicit thread-safety assumptions of the Java version.

## 1.4 Startup sequence (target < 200ms)

```
main()
 1. Load config (env + yaml)                      — microseconds
 2. Init structured logger                         — microseconds
 3. Open DB connection pool (sql.DB, NOT gorm yet) — a few ms (connection is lazy/pinged
                                                       async; startup does not block on a
                                                       full round-trip if DB is slow —
                                                       see 1.4.1 below)
 4. Run pending migrations (skippable via flag)     — depends on migration count, only on
                                                        first boot / upgrades
 5. Construct gamedata repositories (NOT loaded yet, — microseconds
    just sync.Once wrappers around parser funcs)
 6. Construct world registry, fight manager          — microseconds
 7. Bind TCP listener                                — a few ms
 8. Flip readiness flag, start accept loop            — ready
```

### 1.4.1 DB connection strategy

Unlike the Java version (which calls `System.exit(0)` if the DB isn't reachable at
startup — a startup-blocking anti-pattern), the Go server:
- Opens the `sql.DB` pool eagerly (cheap, doesn't dial) but performs the first real
  `Ping()` **asynchronously** with a bounded timeout, logging a warning (not fatal) if it
  fails — the listener still binds and accepts connections; auth attempts simply fail
  with a clean error until the DB becomes reachable. This favors "the server is up and
  diagnosable" over "the server refuses to start."
- Connection pool tuned via config: `max_open_conns`, `max_idle_conns`, `conn_max_lifetime`.

### 1.4.2 Lazy game-data loading

Each `gamedata.Repository[T]` wraps its `.dat` parser in a `sync.Once`. The first call to
`.Get(id)` or `.All()` triggers the parse; subsequent calls are O(1) map lookups. Because
matchmaking/fight-creation is the only code path that needs spell/card/effect data, and a
freshly-booted server has zero players for at least a few seconds, this means the actual
parse work happens off the critical startup path, overlapped with the time it takes the
first player to authenticate and load into the world. If desired, an optional
`--warm-cache` startup flag can force eager loading for ops environments that prefer
predictable steady-state latency over minimal startup time.

## 1.5 Error handling & resilience

- No `os.Exit` calls outside of `main()`'s top-level fatal config-validation errors.
- Every goroutine boundary (`netio/conn.go` per-connection loop, `combat/fight.go` per-fight
  loop) has a deferred `recover()` that logs the panic with stack trace and cleans up
  (closes the connection / ends the fight and notifies participants) instead of crashing
  the process.
- All DB-touching service methods return `error`; handlers translate errors to the
  appropriate wire-protocol error opcode (e.g. `AUTHENTICATION_RESULT` code 2) rather than
  leaking Go error strings to the client.

## 1.6 Why not an actor framework / existing game-server framework?

Evaluated and rejected for v1, to keep the dependency surface small and the codebase easy
to reason about:
- `gnet` / `eventloop`-style reactor libraries: unnecessary at this player-count scale;
  stdlib `net` + goroutines already scales to far more concurrent connections than a
  niche PvP arena server will ever see. Revisit only if profiling shows goroutine-per-conn
  overhead is an actual bottleneck.
- Full ECS (entity-component-system) frameworks: overkill for ~86 files' worth of game
  logic; the actor-per-fight model gives enough isolation without the complexity of a
  general ECS.
