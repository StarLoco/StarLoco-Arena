# Deployment

Docker images + compose stacks for the DofusArena 2.70 server. The server is a
pure-Go static binary (CGO disabled), so images are tiny (~20 MB on alpine).

## Game data

The server needs the client's static game data (`data.bdat` + `indexes.bdat`).
Point `ARENA_DATA_DIR` at the host directory that contains them (the client's
`contents/bdata`). It is mounted read-only at `/data`.

```powershell
$env:ARENA_DATA_DIR = "E:\Projets\DofusArena2-06\client\compiled\game\contents\bdata"
```

(The server still runs without game data — cards/spells just won't load.)

## Stacks

### SQLite (simplest — one container)
```bash
docker compose -f deploy/docker-compose.sqlite.yml up --build
```
Embedded DB file persisted in the `arena-state` volume. Fine for dev / small
scale.

### PostgreSQL (production)
```bash
docker compose -f deploy/docker-compose.postgres.yml up --build
```
Server + Postgres 16; the server waits for a healthy DB, then auto-migrates.

### MySQL / MariaDB (production)
```bash
docker compose -f deploy/docker-compose.mysql.yml up --build
```

## Seeding an admin account

Run the bundled seed tool inside the running server container:

```bash
docker compose -f deploy/docker-compose.postgres.yml exec arena \
  /app/seedaccount --config /app/configs/config.postgres.yaml \
  --login admin --password secret --admin
```

## Configuration

Everything is set via `ARENA_*` env vars (see the compose files) or a mounted
YAML config (`configs/config.*.yaml`). Env vars override the file.

| Var | Meaning |
|---|---|
| `ARENA_ADDR` | listen address (e.g. `0.0.0.0:5555`) |
| `ARENA_DATA_DIR` | game-data dir (mounted at `/data`) |
| `ARENA_LOG_LEVEL` | debug / info / warn / error |
| `ARENA_DB_DRIVER` | sqlite / postgres / mysql |
| `ARENA_DB_DSN` | connection string / file path |
| `ARENA_DB_MAX_OPEN_CONNS` | pool size (server DBs) |
| `ARENA_DB_MAX_IDLE_CONNS` | idle pool size |

## Notes on scaling

This is a **single stateful instance** (coaches on one process interact
directly). It handles thousands of concurrent connections on modest hardware.
The DB is the first thing to scale — switch to Postgres/MySQL and tune the pool.
True horizontal auto-scaling (multiple game instances behind a gateway + shared
bus) is a separate, larger effort; the store abstraction + config keep that door
open.
