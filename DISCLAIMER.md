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
  - `server/data-dist/` (`data.bdat`, `indexes.bdat`, `maps/`) — the card,
    spell and arena **records** the server's own logic needs to run a fight
    (no art, audio, or executable code). This is a small (~2.5 MB), deliberate
    subset the maintainer has chosen to include so the server runs without
    requiring every operator to separately source a client. It ships in every
    downloadable release and in `git clone`.
- **Not committed to this repository, but linked to an external mirror**
  (© Ankama Games):
  - `client/compiled/` — the full retail game client (launcher, `core.jar`,
    art, audio, ~436 MB) and its bundled Oracle/Sun JRE. This repository's
    source tree, `git clone`, and every release archive still do not contain
    it (it stays git-ignored — see `AGENTS.md` constraint 4). What changed is
    that the maintainer (StarLoco) now hosts a personal mirror and links to it
    from the GitHub release notes, the root `README.md`, and the web portal's
    registration page, so a player doesn't have to source the client
    elsewhere. That is a deliberate distribution decision, distinct from what
    the repository itself contains — see the takedown note below.
- **Not distributed or linked anywhere by this project**:
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

This applies to `server/data-dist/` and any release built from it, and to the
client mirror linked from the release notes, README and web portal, as much as
to anything else here.
