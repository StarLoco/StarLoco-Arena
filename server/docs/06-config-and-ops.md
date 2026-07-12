# 6. Configuration & Operations

## 6.1 Configuration

12-factor style: a YAML file for structured defaults, overridable by environment
variables for secrets/deployment-specific values. Implemented with `knadh/koanf`
(lighter weight than viper, no hidden global state).

`configs/config.example.yaml`:

```yaml
server:
  name: "DofusArena"
  listen_addr: ":443"
  admin_addr: "127.0.0.1:9090"  # admin/observability HTTP server; "" disables it entirely
  beta: false
  version:
    major: 2
    revision: 4
    build: "7025"

database:
  driver: postgres        # sqlite | postgres | mysql
  dsn: "host=localhost user=arena password=CHANGE_ME dbname=arena sslmode=disable"
  max_open_conns: 25
  max_idle_conns: 10
  conn_max_lifetime: 30m

gamedata:
  dir: "./data"            # directory containing cards.dat, spells.dat, etc.
  warm_cache: false         # if true, force-load all gamedata repositories at startup

logging:
  level: info               # debug | info | warn | error
  format: console            # console | json (json recommended for prod log aggregation)

combat:
  turn_clock: 30s
  presentation_clock: 20s
  placement_clock: 30s
  observation_clock: 10s
```

Environment override convention: `ARENA_<SECTION>_<KEY>`, e.g. `ARENA_DATABASE_DSN`,
`ARENA_SERVER_LISTEN_ADDR`. Secrets (DB password, etc.) should be supplied via env vars
or a mounted secrets file in production, never committed — `configs/.env.example`
documents the full list.

This directly fixes the current Java server's hardcoded-credentials anti-pattern
(`Database.java:32-33`).

## 6.2 Logging

`zerolog`, structured, leveled, zero-allocation on the hot path. Every log line carries
contextual fields (`conn_id`, `account_id`, `coach_id`, `fight_id` where applicable) via
`log.With()` sub-loggers created at the start of each connection/fight rather than
string-concatenated messages (replaces the Java version's ad-hoc
`Client.logger.info("recv < " + id + " ...")` string building, which also has the side
effect of always paying string-concat cost even when the log level would filter the line).

## 6.3 Migrations workflow

```bash
# apply all pending migrations
go run ./cmd/server --migrate-only

# create a new migration pair
migrate create -ext sql -dir migrations/postgres -seq add_fighter_budget_column
```

Migrations run automatically on boot (idempotent, safe to run on every deploy) unless
`--no-migrate` is passed. See `03-data-model.md` §3.4 for per-dialect file layout.

## 6.4 Graceful shutdown

On `SIGINT`/`SIGTERM`, `cmd/server/main.go` calls `App.Shutdown(ctx)` with a 5-second
grace period (`shutdownGracePeriod`) instead of a bare listener close. `Shutdown`
(`internal/app/app.go`):

1. Closes the listener so no new connections are accepted (the online set can't grow
   while we notify it).
2. Calls `dispatch.BroadcastShutdown(deps)` (`internal/dispatch/shutdown.go`), which sends
   `WORLD_SERVER_UNAVAILABLE` (opcode 1026, empty payload) to every online coach's session
   and then `Session.Close()`s it. The client reacts by clearing its login state, showing
   an `error.connection.worldLoading` message box, and disconnecting cleanly — so players
   get a real "world unavailable" prompt rather than a silently dropped socket. This covers
   coaches idling in the world **and** those in an active fight (both stay in
   `world.Registry` until they disconnect).
3. Waits (polling `world.Registry.Len()` every 50 ms) until every session has torn down —
   each connection's write loop flushes the queued 1026 frame *before* closing the socket
   (netio's drain-before-close contract, see `netio.Session.Close` / `Conn.writeLoop`), and
   the disconnect handler calls `World.Remove`. If the grace period elapses first, Shutdown
   returns `ctx.Err()` and the remaining sockets are severed by process exit.

Why 1026 and not `NO_INSTANCE_SERVER_AVAILABLE` (5000): the stock client *decodes* 5000 but
wires it to no frame handler, so it would be silently discarded. 1026 is the one
server→client "the game backend is going away" message the client actually reacts to.
In-fight fighters are not sent a bespoke `END_FIGHT` teardown on shutdown; the 1026
disconnect is sufficient and avoids racing the fight actor goroutines during teardown.

Mirrors nothing in the current Java version (`Main.java` has no shutdown hook at all —
the JVM process just dies, leaving `connected=1` rows in the DB, which is why
`Database.java:58-61`'s constructor has to reset all `connected` flags on every boot as a
band-aid). The Go server's graceful shutdown reduces reliance on that boot-time cleanup,
though the cleanup itself is still kept as a defensive belt-and-suspenders measure for
ungraceful termination (crash, `kill -9`, power loss).

## 6.5 Observability

`internal/adminhttp` serves a small stdlib-only HTTP server bound to `server.admin_addr`
(default `127.0.0.1:9090`, i.e. loopback-only by default — bind to a wider address only if
you understand the exposure, since `/debug/pprof` allows profiling/heap dumps). It's a
separate HTTP server from the account web portal (§6.6): the portal is public-facing,
this one is meant for operators/local debugging. Set `server.admin_addr: ""` to disable it
entirely.

Endpoints:
- `GET /healthz` — `{"status":"ok","uptime_seconds":N}`, a basic liveness probe.
- `GET /debug/pprof/*` — stdlib `net/http/pprof`'s handlers (index, cmdline, profile,
  symbol, trace), registered on a dedicated `http.ServeMux` rather than
  `http.DefaultServeMux`, e.g.:
  ```bash
  go tool pprof http://127.0.0.1:9090/debug/pprof/profile?seconds=30
  ```
- `GET /stats` — `{"online_players":N,"active_fights":N}`, cheap counters from
  `world.Registry.Len()` and `combat.Manager.Count()` for quick manual inspection.

These endpoints have no auth of their own — by design, since they're expected to stay
unreachable except from localhost. For browsing them without direct network access to
`admin_addr` (e.g. from your laptop against a remote host), the web portal's authenticated
`/admin/monitoring` page fetches `/healthz`/`/stats` and reverse-proxies `/debug/pprof/*`
through `is_admin`-gated routes; see `docs/10-web-portal.md` §10.8. No separate config key is
needed — the portal reuses this same `server.admin_addr` value (`webadmin.Config.AdminHTTPAddr`,
wired in `internal/app` and `cmd/web`). If it's empty, or unreachable from wherever the
portal process runs (e.g. the standalone `cmd/web` binary on a different host than
`cmd/server`), that page just shows a "not configured"/fetch-error notice instead of the
counters.

Prometheus `/metrics` (`prometheus/client_golang`) remains a stretch goal, not implemented
— it would pull in a new dependency and wasn't needed for the v1 pass. DB pool stats are
also not yet exposed here.

These JSON endpoints are for tooling (curl, uptime monitors, `go tool pprof`), not for
humans to browse — the account web portal (§6.6) separately surfaces a couple of the same
counters as actual pages: a public, unauthenticated `/status` page, and extra tiles (active
fights, uptime) on the admin dashboard. See `docs/10-web-portal.md` §10.8.

## 6.6 Account web portal

The `web` config block controls the account website (register/login, per-user data view,
admin console, impersonation). See `docs/10-web-portal.md` for the full design.

```yaml
web:
  enabled: false                 # start the portal inside cmd/server (dev convenience)
  listen_addr: ":8080"
  session_secret: "CHANGE_ME"    # REQUIRED in prod: HMAC key for signed session cookies
  secure_cookies: true           # set true when served over HTTPS
  base_url: ""                   # optional public origin for absolute links
```

- **Local dev**: `web.enabled: true` hosts the portal inside `cmd/server` — one command
  runs both the game listener and the site.
- **Production**: leave `web.enabled: false` and run `cmd/web` as a separate process; it
  binds `web.listen_addr` regardless of the flag, sharing the same DB and services. This
  keeps the public-facing HTTP site isolated from the game TCP process.

`session_secret` must be a strong random value in production (an empty value falls back to
an ephemeral per-startup key, logged with a warning). Bootstrap the first admin account
with `go run ./cmd/seedaccount --admin --login <name> --password <pw>`.

## 6.7 Build & distribution

- `go build` produces a single static binary (no JVM, no `libs/*.jar` directory to ship,
  no classpath management) — deployment is: copy binary + `configs/config.yaml` +
  `data/*.dat` + run. The web portal's templates/CSS are embedded, so nothing extra ships
  for it.
- `go.mod` pins Go 1.26.4 (`go 1.26` directive) and all dependency versions; `go.sum`
  committed for reproducible builds.
- Optional: a minimal `Dockerfile` using a multi-stage build (Go build stage → `scratch`
  or `distroless` runtime image) for containerized deployment, given the binary has no
  runtime dependencies beyond libc (or none at all if `CGO_ENABLED=0`, which is achievable
  since the SQLite driver choice is pure-Go).
