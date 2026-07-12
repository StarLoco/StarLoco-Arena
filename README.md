# DofusArena 2 — Server Rewrite & Client Preservation

A preservation and reverse-engineering project for **DofusArena 2** (the card
version of Ankama's tactical arena game). It bundles the original game client,
the decompiled client source used as a reference, the raw game data, and a
**from-scratch Go server** that speaks the client's exact wire protocol.

The Go server is the actively developed component; everything else is reference
material or the runnable retail client used to test the server against.

---

## Repository layout

```
DofusArena2-06/
├── client/
│   ├── source/       Decompiled Java/Lua client SOURCE (reference only, not built here)
│   └── compiled/     The runnable retail client (launcher + core.jar + assets + bundled JRE)
├── server/           The Go 1.26 server rewrite — the active project
│   ├── cmd/          Entry points: server, seedaccount, studio (Wails tool), web, loadtest…
│   ├── internal/     Server implementation (protocol, gamedata, combat, world, service…)
│   ├── data/         Game data consumed at runtime (.dat/.ade/.amw + maps/) and the
│   │                 decompiled game-logic reference under data/stc/
│   ├── migrations/   Versioned SQL migrations (sqlite/postgres/mysql), embedded
│   ├── configs/      Example YAML configs
│   └── docs/         Full server design specification + FEATURES-STATUS.md
├── tools/            Reverse-engineering references (i18n/sba/zorder) + cfr.jar (decompiler)
└── docs/             Top-level docs (e.g. in-client console commands)
```

> **Why `data/` lives inside `server/`:** it is the server's runtime input. The
> server config (`server/configs/*.yaml`) and its tests resolve it relative to
> the `server/` working directory. Do **not** move it back to the repo root — see
> [`AGENTS.md`](./AGENTS.md).

---

## Prerequisites

| To… | You need |
|---|---|
| Build/run the **server** | [Go 1.26.4+](https://go.dev/dl/) (no CGO required) |
| Run `go test -race` | A C toolchain (e.g. `winget install BrechtSanders.WinLibs.POSIX.UCRT`) |
| Build the **studio** tool UI | [Node.js](https://nodejs.org/) + the [Wails v2 CLI](https://wails.io/) |
| Run the **compiled client** | Nothing extra — it ships its own bundled JRE |
| Clone with binaries | [Git LFS](https://git-lfs.com/) (`git lfs install`) — see below |

---

## Quick start — the Go server

All commands run from `server/`:

```powershell
# 1. Fetch dependencies
go mod tidy

# 2. Run against a local SQLite DB (zero setup — created on first run)
go run ./cmd/server --config configs/config.dev.yaml

# 3. Accounts aren't provisioned over the wire — seed one for testing
go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123

# 4. Build a standalone binary
go build -o bin/arena-server.exe ./cmd/server
./bin/arena-server.exe --config configs/config.dev.yaml
```

The server listens for the retail client and reconstructs the full 1v1 / 2v2
duel flow (login → matchmaking → combat → ladder). For production, copy
`configs/config.example.yaml` and point `database.driver` at PostgreSQL/MySQL.

See [`server/README.md`](./server/README.md) for full details, CLI flags, and
the design docs in [`server/docs/`](./server/docs).

## Quick start — the retail client

Launch the bundled game (Windows):

```
client/compiled/game/DofusArena.exe
```

Point it at a running server, then log in with the account you seeded above.
In-client console commands (open with `Ctrl + ²`) are listed in
[`docs/client-console-commands.md`](./docs/client-console-commands.md).

## Studio (data & asset tool)

`server/cmd/studio` is a Wails desktop app for browsing/editing game data and
client assets. Build its frontend once, then run it:

```powershell
# from server/cmd/studio/frontend
npm install
npm run build

# then, from server/cmd/studio
wails dev
```

---

## Testing

From `server/`:

```powershell
go vet ./...
go build ./...
go test ./...            # full suite, no CGO required
go test -race ./...      # requires a C toolchain
```

---

## Git & Git LFS

Large binaries (the compiled client, its bundled JRE, and binary game data) are
tracked with **Git LFS** (see [`.gitattributes`](./.gitattributes)). Before
cloning or working with the repo:

```powershell
git lfs install
```

Regenerable outputs (build artifacts, `node_modules/`, dev databases, logs) are
excluded via [`.gitignore`](./.gitignore).

---

## License & legal

- The **original work** in this repository (the Go server under `server/`
  excluding `server/data/`, the utilities under `tools/`, and this project's
  docs) is released under the [MIT License](./LICENSE).
- This repository is an **independent, non-commercial** preservation and
  interoperability project and is **not affiliated with Ankama or Oracle**.
  **DofusArena**, **Dofus**, and all related assets are the property of
  **Ankama**; the bundled Java runtime is the property of **Oracle/Sun**. The
  MIT license does **not** cover that third-party material.
- See [`DISCLAIMER.md`](./DISCLAIMER.md) for the full non-affiliation and
  copyright notice, [`NOTICE`](./NOTICE) for third-party attributions, and
  [`SECURITY.md`](./SECURITY.md) for takedown/vulnerability reporting.

## Contributing

See [`CONTRIBUTING.md`](./CONTRIBUTING.md) and [`AGENTS.md`](./AGENTS.md). By
participating you agree to the [Code of Conduct](./CODE_OF_CONDUCT.md).
