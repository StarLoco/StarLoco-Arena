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
- `client/compiled/` — the retail client. **Not committed** (git-ignored); you
  supply it locally.
- `server/data/` — a git-ignored scratch copy of your own client's data, for
  local development.
- `server/data-dist/` — the small, deliberately-curated data subset that *is*
  committed and ships in releases (see `AGENTS.md` constraint 4). Changes to it
  are a maintainer decision, not a routine contribution.

Please **do not** add newly decompiled third-party code without a clear
provenance note, and do not commit copyrighted assets that aren't already part
of the preservation set (`server/data-dist/` and `client/decompiled/` are the
two deliberate exceptions, already covered by `DISCLAIMER.md`).

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

## Commit messages

Releases are automated, and the version number and changelog are derived from
commit messages, so this project uses
[Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<optional scope>): <description>
```

| Type | Use it for | Effect on the next release |
|---|---|---|
| `feat` | a new capability | bumps the **minor** version, listed under *Features* |
| `fix` | a bug fix | bumps the **patch** version, listed under *Bug fixes* |
| `perf` | a performance improvement | patch bump |
| `docs` | documentation only | no version bump |
| `refactor`, `test`, `build`, `ci`, `chore` | internal work | no version bump, hidden from the changelog |

Examples:

```
feat(web): let players register their own accounts
fix(fight): stop the turn clock leaking after a forfeit
docs: explain the update check in the quick start
```

A breaking change adds a `!` after the type (`feat!: ...`) or a
`BREAKING CHANGE:` footer. While the project is pre-1.0 that bumps the minor
version, not the major one.

If a commit doesn't follow this format nothing breaks — it simply won't appear
in the changelog.

## Pull requests

1. Keep PRs small and single-purpose.
2. Ensure `go build ./...`, `go vet ./...`, and `go test ./...` pass from
   `server/`. CI runs these on Linux and Windows for every PR.
3. Run `gofmt -w` on files you touched (CI enforces it).
4. Describe what changed and why; reference any opcode/design doc you touched.
5. By contributing, you agree that your contributions are your own original work
   and are licensed under the project's [MIT License](./LICENSE).

## How a release happens

Nobody tags by hand:

1. Commits land on `v2.70`.
2. **release-please** keeps a *"chore: release x.y.z"* pull request open,
   containing the version bump and the generated `CHANGELOG.md`.
3. Merging that PR creates the git tag and the GitHub release.
4. **GoReleaser** then builds Windows/Linux/macOS binaries and attaches them,
   with a `checksums.txt`.

To cut a release, merge the release PR. To hold one back, leave it open.

## Reporting bugs & security issues

Use GitHub issues for ordinary bugs. For anything security-sensitive, follow
[`SECURITY.md`](./SECURITY.md) instead of opening a public issue.
