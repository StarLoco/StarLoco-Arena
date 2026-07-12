# Contributing

Thanks for your interest in DofusArena2-06. This guide covers how to work in the
repository. For the deeper agent/contributor reference (repo map, critical
constraints), read [`AGENTS.md`](./AGENTS.md).

## Scope of contributions

The **actively developed component is the Go server** (`server/`). That's where
almost all contributions belong. The other directories are reference material:

- `client/source/`, `server/data/stc/` — decompiled client (read-only reference).
- `client/compiled/`, `server/data/` — the retail client and its game data.

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

You'll also need [Git LFS](https://git-lfs.com/) (`git lfs install`) to check
out the binary assets.

## Coding conventions

- **Go:** standard `gofmt`; keep changes focused and idiomatic.
- **Wire protocol is sacred.** The original client is fixed and cannot change, so
  server output must remain byte-exact against the decompiled reference in
  `client/source/` and `server/data/stc/`. Never "improve" the wire format.
- **Do not move `server/data/`** — the config and ~30 tests resolve it relative
  to `server/`. See [`AGENTS.md`](./AGENTS.md) for the exact constraints.
- **Module path** stays `github.com/dofusarena/go-server` even though the folder
  is `server/`. Don't rewrite imports to match the folder.
- **Tests:** prefer table-driven and real-data tests; real-data tests must
  **skip** (not fail) when `server/data` is absent.
- **Docs:** update `server/docs/FEATURES-STATUS.md` and the relevant
  `server/docs/opcodes/*` when you change protocol or combat behavior.
- **Line endings:** this repo does not force normalization; preserve existing
  line endings and avoid whole-file reformatting that creates CRLF churn.

## Pull requests

1. Keep PRs small and single-purpose.
2. Ensure `go build ./...`, `go vet ./...`, and `go test ./...` pass from `server/`.
3. Describe what changed and why; reference any opcode/design doc you touched.
4. By contributing, you agree that your contributions are your own original work
   and are licensed under the project's [MIT License](./LICENSE).

## Reporting bugs & security issues

Use GitHub issues for ordinary bugs. For anything security-sensitive, follow
[`SECURITY.md`](./SECURITY.md) instead of opening a public issue.
