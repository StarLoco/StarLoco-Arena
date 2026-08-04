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

- **Original work (MIT-licensed):** the Go server under `server/`, the utilities
  under `tools/`, the RE tooling under `client/arena-mcp/`,
  `client/control-agent/` and `client/deobf-lab/scripts/`, and this project's
  documentation.
- **Third-party / copyrighted material (NOT licensed by this project):**
  - `client/decompiled/` — decompiled client code, reproduced only as an
    interoperability and study reference, together with the mappings and
    analysis derived from it (`client/deobf-lab/mappings/`, `client/analysis/`).
- **Not distributed here at all** (git-ignored — you supply your own copy):
  - `client/compiled/` — the retail game client and its bundled Oracle/Sun JRE.
  - `server/data/` (`data.bdat`, `indexes.bdat`, `maps/`) — original game data,
    copied out of that client.

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
