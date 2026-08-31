# Disclaimer

## Non-affiliation

This project is an **independent, non-commercial** effort for the preservation,
study, and interoperability of **DofusArena 2**, a discontinued game. It is
**not affiliated with, authorized, endorsed by, or in any way officially
connected to Ankama Games**, Oracle, or any of their subsidiaries or affiliates.

**DofusArena**, **Dofus**, and all related names, logos, artwork, sounds, and
game data are trademarks or copyrighted works of **Ankama Games**. **Java** and
the bundled Java Runtime Environment are the property of **Oracle Corporation**
(formerly Sun Microsystems). All other trademarks are the property of their
respective owners.

## What is original vs. third-party

- **Original work (MIT-licensed):** the Go server under `server/` (excluding
  the data described below), the utilities under `tools/`, the RE tooling
  under `client/arena-mcp/`, `client/control-agent/` and
  `client/deobf-lab/scripts/`, and this project's documentation.
- **Third-party / copyrighted material reproduced here (NOT licensed by this
  project — © Ankama Games):**
  - `client/decompiled/` — decompiled client code, reproduced only as an
    interoperability and study reference, together with the mappings and
    analysis derived from it (`client/deobf-lab/mappings/`, `client/analysis/`).
  - `server/data-dist/` (`data.bdat`, `indexes.bdat`, `maps/` — the latter
    holding `fight/`, `tplg/` and `env/`) — the card, spell, arena and
    **overworld-layout records** the server's own logic needs to run a fight and
    place the interactive elements players click (no art, audio, or executable
    code: `env/` carries element ids, positions and descriptors, not the sprites
    they display). This is a small (~3 MB), deliberate subset the maintainer has
    chosen to include so the server runs without requiring every operator to
    separately source a client. It ships in every downloadable release and in
    `git clone`.
- **Not distributed or linked anywhere by this project** (© Ankama Games):
  - `client/compiled/` — the full retail game client (launcher, `core.jar`,
    art, audio, ~436 MB) and its bundled Oracle/Sun JRE. It is not in this
    repository's source tree, not in `git clone`, not in any release archive,
    and **not linked from anywhere this project controls** — not the release
    notes, not the `README`, and not the web portal. It stays git-ignored (see
    `AGENTS.md` constraint 4). Players must supply their own lawfully obtained
    copy.

    A maintainer-hosted mirror used to be linked from those three places, and
    the server binary shipped with that link as its *default* config value, so
    every operator who ran a release republished it from their own portal
    without having chosen to. Both are gone: `web.client_download_url` now
    defaults to empty, and an operator who has a lawful copy to share must opt
    in deliberately, under their own name.
  - `server/data/` — a git-ignored scratch folder for pointing a local build
    at your own client copy instead of the bundled `server/data-dist/`.

See [`NOTICE`](./NOTICE) for third-party attributions and [`LICENSE`](./LICENSE)
for the scope of the MIT license.

## Purpose

The decompiled references exist solely to allow the original client to be
studied and to validate that the from-scratch server is wire-compatible with it.
No attempt is made to circumvent any live commercial service — the game and its
official servers are discontinued.

## No warranty

Everything here is provided "as is", without warranty of any kind. Use it at
your own risk. The authors accept no liability for any damages arising from its
use.

## Takedown requests

If you are a rights holder and believe material in this repository should not be
distributed, please open a confidential report (see [`SECURITY.md`](./SECURITY.md))
or contact the maintainer, and the material in question will be removed promptly.
You do not need to involve a lawyer to get a response.

This applies to `server/data-dist/` and any release built from it as much as to
anything else here.

A server operator running this software publishes the same commitment at
`/legal` on their own portal, together with a contact address
(`web.contact_email`).
