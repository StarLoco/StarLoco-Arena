# Contributing

Thanks for your interest in this project. This guide covers how to work in the
repository. For the deeper agent/contributor reference (repo map, critical
constraints), read [`AGENTS.md`](./AGENTS.md).

## Scope of contributions

This branch (`v2.70`) targets the **DofusArena 2.70** line (the Feb-2012 retail
build, rev 72909). The primary actively developed component is the **Go server**
in [`server/`](./server). Most contributions belong there.

> The 2006-era **2.04b** line lives on the **`v2.04`** branch. Contributions for
> that line belong there, not here.

The other directories are reference material:

- `client/decompiled/` — decompiled (obfuscated) 2.70 client, read-only reference.
- `client/analysis/` — protocol and data-format write-ups derived from it.
- `client/arena-mcp/`, `client/control-agent/`, `client/deobf-lab/` — RE tooling.
- `client/compiled/`, `server/data/` — the retail client and its game data.
  **Not committed** (git-ignored); you supply them locally.

Please **do not** add newly decompiled third-party code without a clear
provenance note, and do not commit copyrighted assets that aren't already part
of the preservation set.

## Getting set up

Prerequisites and full instructions are in [`README.md`](./README.md). In short,
from `server/`:

```powershell
go mod tidy
go build ./...
go vet ./...
go test ./...        # full suite, no CGO required
go test -race ./...  # requires a C toolchain
```

No Git LFS is needed — a plain `git clone` gets everything that is tracked.

## Coding conventions

- **Go:** standard `gofmt`; keep changes focused and idiomatic.
- **Wire protocol is sacred.** The retail client is fixed and cannot change, so
  server output must remain byte-exact against the decompiled reference in
  `client/decompiled/` and the specs in `client/analysis/`. Never "improve" the
  wire format.
- **Do not move `server/data/`** — `server/configs/*.yaml` resolves it as
  `data_dir: "data"`, relative to the `server/` working directory.
  See [`AGENTS.md`](./AGENTS.md) for the exact constraints.
- **Module path** stays `github.com/StarLoco/arena-2.70` even though the folder
  is just `server/`. Don't rewrite imports to match the folder.
- **Tests:** prefer table-driven and real-data tests; real-data tests must
  **skip** (not fail) when `server/data` is absent.
- **Docs:** keep `server/docs/STATUS.md` current, add a `server/docs/BUGS.md`
  entry and a `server/docs/DATA-COVERAGE.md` row for each fix, and update
  `server/COVERAGE.md` when opcode coverage changes.
- **Line endings:** this repo does not force normalization; preserve existing
  line endings and avoid whole-file reformatting that creates CRLF churn.

## Pull requests

1. Keep PRs small and single-purpose.
2. Ensure `go build ./...`, `go vet ./...`, and `go test ./...` pass from
   `server/`.
3. Describe what changed and why; reference any opcode/design doc you touched.
4. By contributing, you agree that your contributions are your own original work
   and are licensed under the project's [MIT License](./LICENSE).

## Reporting bugs & security issues

Use GitHub issues for ordinary bugs. For anything security-sensitive, follow
[`SECURITY.md`](./SECURITY.md) instead of opening a public issue.
