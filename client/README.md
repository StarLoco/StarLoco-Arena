# DofusArena 2.70 — Client Decompilation & Repatching

Reverse-engineering workspace for the **latest** DofusArena 2 client
(`build.version=2.70`, `revision=72909`, built **Feb 2012**). Goal: read the
network protocol (opcodes / what the client sends and expects), and be able to
edit + repackage the client for future game editing.

> The Go server is out of scope here and is **not** touched by anything in this
> folder.

> **Note (repo layout):** the 435 MB `compiled/` client (client binaries +
> bundled JRE + copyrighted game assets, incl. `core.jar`, `data.jar`,
> `contents/bdata/*.bdat`) now lives **in this folder** at `compiled/`, but is
> **git-ignored** to stay within GitHub's LFS quota — so it stays local-only and
> is not committed. The scripts in `work/` (and the `arena-mcp`/`control-agent`
> tooling) reference the in-repo `compiled/`. A backup copy also remains at
> `E:\Projets\DofusArena 2.70\compiled\`. Only the decompiled source, analysis
> docs, and scripts are committed here.

## What this client is

- `compiled/game/core.jar` — the client code, **heavily name-obfuscated**:
  5052 classes, ~4900 flattened into the *default package* with meaningless
  case-colliding names (`fW`, `fw`, `Fw`, `FW`, `so_0`, `acb_2`, ...). Only 148
  classes kept their real `com.ankamagames.*` names (console commands + some
  framework classes).
- Main class: `com.ankamagames.dofusarena.client.DofusArenaClient` (obfuscated).
- `compiled/lib/*.jar` — third-party libs (jogl, jdom, log4j, luajava, jorbis…),
  standard open source, **not** decompiled here.
- `compiled/game/contents/i18n.jar` — localization text (4 languages,
  ~6855 strings each), extracted to `decompiled/i18n/`.

## Folder layout

```
compiled/                original client (never modified)
decompiled/
  core/                  all 5052 classes decompiled with CFR (readable .java)
  i18n/                  extracted game text (.properties)
analysis/
  PROTOCOL.md            *** master spec for building a Go server v2.70 ***
  PROTOCOL-messages.md   detailed variable-length message field layouts
  PROTOCOL-parttable.md  part-table / entity blob formats (actors, guild, fighter)
  DATA-FORMAT.md         game data store (data.bdat) + all record schemas
  opcodes.md             human-readable network opcode map (C2S / S2C tables)
  opcode_map.csv         opcode -> obfuscated class -> real 2007 name + confidence
  opcodes_2.70.csv       every 2.70 message class + serialization fingerprint
  opcodes_2007.csv       every 2007 named message class (cross-reference source)
  rename_map.csv         CFR name  <->  original obfuscated name (4908/4911)
  payloads.md            per-opcode wire field layout (type/size, BIG-ENDIAN)
  payloads.csv           same, one row per field (machine-readable)
work/
  extract_opcodes.py     regenerates the opcode analysis
  build_rename_map.py    regenerates rename_map.csv
  decode_payloads.py     regenerates payloads.md / payloads.csv
  patch_class.py         surgical edit -> recompile -> inject pipeline
  patched/               patched copies of core.jar (created on demand)
deobf-lab/               whole-jar deobfuscation remap -> readable names (see deobf-lab/docs/README.md)
```

## Tooling

- JDK 1.8 at `C:\Program Files\Java\jdk1.8.0_202\bin` (`javac`, `javap`, `jar`).
- CFR 0.152 decompiler: `E:\Projets\DofusArena2-06\tools\cfr.jar`.
- Python 3.11 (analysis scripts).

Re-decompile everything:
```powershell
java -jar "E:\Projets\DofusArena2-06\tools\cfr.jar" `
  "compiled\game\core.jar" --outputdir "decompiled\core" --silent true
```

## Network protocol (summary)

Two wire framings, both dispatched by `switch` on the opcode. **The wire is
BIG-ENDIAN (network byte order)** — message `a(byte[])`/`encode()` use plain
`ByteBuffer` (default big-endian). (The little-endian `acf` reader is for
asset/map files, *not* the network path.)

| Protocol | Framing | Base class | Dispatcher |
|---|---|---|---|
| Main game (encode) | `[u16 len][u8 arch][u16 opcode][payload]` | `acb_2` (=`ClientProxyMessage`) | `fp_0` + `gz_1.fD()` (~250-case switch) |
| Main game (decode) | `[u16 len][u16 opcode][payload]` — arch byte NOT consumed inbound | | `fp_0.g()` hands only the stripped payload to `a(byte[])` |
| Login/secure | `[u16 len][i32 opcode][u8 secure][payload]` | `axX` | `qc_2` |

Fight/combat S2C messages (base `ue_0`) prepend an 8-byte header
`apt:i32, apu:i32` before their own fields. Per-opcode field layouts are in
`analysis/payloads.md`.

Key classes (obfuscated → role):

- `acf` — binary **reader** (little-endian `ByteBuffer`: `readByte/Short/Int/Long`,
  `readString` null-terminated UTF-8, `aqE()` = read bit).
- `aij_1` — binary **writer** (`writeShort/Int/Long/String`).
- `pr_0` — abstract base of all messages: `byte[] encode()`, `boolean a(byte[])`
  (decode), `int getId()` (**opcode**).
- `acb_2` → `so_0` (client→server) / `ael_2` (server→client; `ue_0` = fight S2C).
- `aed_2`/`sb_0` — **UI message bus** (internal event IDs, *not* wire opcodes).

Opcodes are **stable** between the 2007 client and 2.70 (verified on 3152, 3153,
5101, 5102, 5105 …), so the fully-named 2007 source at
the 2.04b client source (`client/source/` on the **`v2.04`** branch of this repo) is used as a Rosetta Stone.
**Caveat:** a field layout can still change even when an opcode is unchanged
(e.g. 5105 "add card" narrowed the card id `long`→`int`, 18→14 byte body).
Always confirm the 2.70 `encode()`/decode body before trusting a field layout —
`opcode_map.csv` marks matches as `high` (opcode + fingerprint) vs `opcode-only`.

Stats: **344 network messages** (340 opcodes); **137** mapped to real 2007
names (**67** fingerprint-confirmed); **207** are new in 2.70; 21 UI-internal
messages separated out.

**For building a Go server v2.70, start with `analysis/PROTOCOL.md`** — it
covers transport/framing, the RSA login handshake, the feature opcode map, and
links to `PROTOCOL-messages.md` for every complex message's exact byte layout.
New-in-2.70 feature ranges identified: 17xxx calendar/events, 22xxx achievements,
23xxx opponent-search/match-confirm, 26xxx X-vs-X invitations, 27xxx
ladder/ranking, 28xxx tournaments.

## Recompiling / editing the client (surgical patch workflow)

> **Update:** `deobf-lab/` now solves this the other way — it remaps the
> *bytecode* to globally-unique readable names, so the whole jar
> extracts/reads/recompiles. Use the surgical flow below for a quick one-off
> edit; use `deobf-lab/` for a fully readable, editable tree
> (see `deobf-lab/docs/README.md`).

A full rebuild of all 5000 CFR classes is impractical: CFR renamed the
case-colliding classes (`So`→`so_0`, `fW`→`fw_1`), so recompiled names would no
longer match what the rest of the jar references, and Windows can't even hold
`fW.class` and `fw.class` in one directory.

Instead, edit **one class at a time** and re-inject it. `patch_class.py`:

1. reads the CFR `.java` (edit it using CFR names like `so_0`),
2. reverts every name to its **original** via `rename_map.csv` (`so_0`→`So`),
3. compiles the single class against the *original* jars,
4. injects the resulting `.class` into `work/patched/core.jar`
   (the original jar is never modified).

```powershell
# dry run: reobfuscate + compile only
python work\patch_class.py fw_1

# edit a copy, then compile + inject into work\patched\core.jar
python work\patch_class.py fw_1 --src work\fw_1_edited.java --inject
```

Validated end-to-end: injected class disassembles correctly (`javap`) and the
patched jar keeps all 5059 entries intact. To run the patched client, point the
launcher/classpath at `work\patched\core.jar` instead of the original.

## Regenerating analysis

```powershell
python work\build_rename_map.py     # -> analysis\rename_map.csv
python work\extract_opcodes.py      # -> analysis\opcode_map.csv, opcodes.md, ...
```

## Known limitations / next steps

- 3 of 4911 classes (`aFg`, `aHh`, `uX`) have ambiguous CFR rename comments;
  confirm their exact original name with `javap` before patching them.
- 207 opcodes new in 2.70 are unnamed — decode their `a(byte[])` bodies to
  document fields (tournaments, spectator, hardcore, shop ranges: 15000–30002).
- Surgical patching is per-class; multi-class refactors need each class patched.
