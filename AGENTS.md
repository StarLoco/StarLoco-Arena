# AGENTS.md — working in this repository

Guidance for AI agents (and humans) contributing to **DofusArena2-06**. Read
this before making changes. For the human-facing overview see [`README.md`](./README.md).

## What this project is

A preservation + reverse-engineering project for **DofusArena 2**. The actively
developed component is a **from-scratch Go server** that is wire-compatible with
the original client. Everything else is reference material or the runnable
retail client used to validate the server.

## Repository map

| Path | What it is | Do you edit it? |
|---|---|---|
| `server/` | **The Go 1.26 server — the active project.** | Yes — primary work happens here |
| `server/data/` | Game data read at runtime (`.dat/.ade/.amw`, `maps/`) + `stc/` decompiled reference | Rarely; data files are inputs |
| `server/docs/` | Server design spec + `FEATURES-STATUS.md` | Yes, keep in sync with code |
| `client/source/` | Decompiled Java/Lua client **source** (reference only) | No — read for protocol provenance |
| `client/compiled/` | Runnable retail client (launcher, `core.jar`, assets, bundled JRE) | No — treat as a shipped binary |
| `tools/` | RE reference snippets + `cfr.jar` (Java decompiler) | Occasionally |
| `docs/` | Top-level docs (in-client console commands) | Occasionally |

## Critical constraints — do not break these

1. **Do not move `server/data/`.** The server loads it via
   `server/configs/*.yaml` (`gamedata.dir: "data"`, resolved from the `server/`
   working directory) and ~30 tests read it via relative paths
   (`../../data`, `../../../data` from `internal/**` and `cmd/studio/**`; the e2e
   harness uses `../../data` in `test/e2e/server_test.go`). Moving it breaks
   runtime data loading and the test suite.
2. **The Go module path is `github.com/dofusarena/go-server`** (in `server/go.mod`)
   even though the folder is `server/`. This is intentional — do **not** rewrite
   imports to match the folder name unless explicitly asked. When editing docs,
   never mangle `github.com/dofusarena/go-server`.
3. **`server/cmd/studio` embeds `frontend/dist` via `//go:embed`.** A committed
   placeholder `frontend/dist/.gitkeep` keeps the directory present so
   `go build ./...` works without first building the frontend. Do not delete it.
4. **Binaries are Git LFS.** `.gitattributes` routes `*.jar/*.dll/*.so/*.exe/`
   `*.amw/*.dat/*.png/…` through LFS. Server build artifacts (`server/**/*.exe`,
   `server/bin/`) are **git-ignored**, not committed.

## Build / test / run (from `server/`)

```powershell
go mod tidy
go build ./...                                   # must pass; studio needs frontend/dist/.gitkeep
go vet ./...
go test ./...                                    # full suite, no CGO
go test -race ./...                              # needs a C toolchain
go run ./cmd/server --config configs/config.dev.yaml
go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123
```

- First server run creates `arena.dev.db` (SQLite, WAL) in `server/` — git-ignored.
- `TestFight_ForfeitEndsFightImmediately` (internal/combat) is a known timing
  flake; re-run in isolation to confirm before treating a failure as real.

## Conventions

- **Go**: standard `gofmt`; keep the byte-exact wire protocol — the client is
  fixed and cannot be changed, so server output must match the decompiled
  reference in `client/source/` and `server/data/stc/`.
- **Tests**: prefer table/real-data tests; real-data tests **skip** (not fail)
  when `server/data` is absent. Keep that behavior.
- **Docs**: update `server/docs/FEATURES-STATUS.md` and the relevant
  `server/docs/opcodes/*` when you change protocol or combat behavior.
- **Line endings / encoding**: this repo does not force text normalization
  (`.gitattributes` only marks binaries). Preserve existing line endings; avoid
  whole-file reformatting that creates CRLF churn.
