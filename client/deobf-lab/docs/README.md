# deobf-lab — DofusArena 2.70 client deobfuscation

An experiment lab that turns the **name-obfuscated** 2.70 `core.jar` into a
**human/AI-readable, recompilable** form — not by recovering Ankama's original
names (impossible; they were stripped), but by *remapping* every class to a
unique, legal, readable name and re-decompiling.

> **Goal (Florent):** "not to retrieve the same name as before but to have
> something more human/AI readable for future recompilation/edit."

---

## TL;DR — what this produced

| Artifact | What it is |
|---|---|
| `mappings/deob.srg` / `deob.tiny` | obfuscated→readable **class** map (SRG for SpecialSource, Tiny v1 for tiny-remapper) |
| `mappings/class_names.csv` | every class: `obf_true, cfr, readable, source, opcode, direction, base, pkg2007` |
| `build/core.deobf.jar` | the **remapped bytecode** — a drop-in `core.jar` (5059 entries, valid) with readable names |
| `decompiled/core-src/deob/*.java` | readable **source** of the remapped jar (4911 classes) |
| `mappings/naming_worklist.csv` | ranked signals for AI-naming the classes still on auto names |
| `mappings/ai_names.csv` | AI-proposed `obf_true → name` (confidence, apply, role, domain, rationale) |
| `mappings/deob_members.srg` | `FD:` **field** rename map (fed to SpecialSource with `deob.srg`) |
| `mappings/member_names.csv` | audit of field renames: `owner_obf, owner_readable, field_obf, field_new, why` |

**Numbers:** 4911 default-package classes remapped. Named so far: **137** real
2007 names (`opcode_map.csv`) + **14** hand framework names + **321** AI-proposed
names (`ai_names.csv`, high+medium confidence) = **472 semantic names**; the
remaining **4439** keep their (collision-free) CFR name. 135 `com.ankamagames.*`
classes left untouched. **Member layer:** **892 private fields** renamed in the
472 named classes (38 from surviving accessors, 78 from readable reference types,
776 typed fallbacks). Decompile: 4911 `.java` in ~23 s. The AI-naming worklist
found **1870 classes with plaintext strings** and **238 with an opcode** — the
fuel behind the 321 names (479 total proposals; 158 low-confidence held back).

---

## Why this approach (and not the tool in the original question)

`java-deobfuscator/deobfuscator` targets **commercial string/flow obfuscators**
(Zelix, Stringer, Allatori, DashO). The 2.70 client uses **none** of those:
it is *name-only* obfuscation — strings are plaintext French, control flow is
clean, and override/opcode signatures (`encode`/`a(byte[])`/`getId`) survive.
That tool's own README says name recovery is "tough luck," so it can't advance
the readability goal here.

The correct category is a **bytecode remapper** (the Minecraft-modding model:
Enigma / tiny-remapper / SpecialSource), driven by a mapping we build from assets
this repo already has. We use **SpecialSource** (self-contained, Maven Central,
resolves inheritance from the jar; a class-only remap needs no external
classpath).

### It also fixes the blocker in `../README.md`

The parent README notes a full rebuild is "impractical" because CFR-renamed
case-colliders (`fW`→`fw_1`) no longer match the jar and Windows can't hold
`fW.class` + `fw.class` in one folder — hence the *surgical single-class* patch
flow. **Remapping the bytecode to globally-unique names removes both problems:**
the whole jar extracts, reads and (given decompiler-artifact cleanup) recompiles.
This lab *complements* `work/patch_class.py` — that stays the fast path for a
one-off edit; this is the path to a fully editable tree.

---

## Pipeline

```
 obf core.jar ──(1) build_mapping.py──► mappings/deob.srg  (+ deob.tiny, class_names.csv)
      │                                        │
      └──────────(2) SpecialSource ◄───────────┘
                        │
                        ▼
                 build/core.deobf.jar   (readable BYTECODE, drop-in, valid)
                        │
                        └──(3) CFR 0.152──► decompiled/core-src/deob/*.java  (readable SOURCE)
```

1. **`scripts/build_mapping.py`** — reads `../analysis/{rename_map,opcode_map,opcodes_2007}.csv`
   **and `mappings/ai_names.csv`**, resolves CFR↔true-bytecode names, and assigns
   each default-package class a readable target (see *Naming policy*). Emits SRG +
   Tiny + a human CSV.
2. **SpecialSource** applies the class map to the real `core.jar` bytecode →
   `build/core.deobf.jar`. Bytecode-level remap = every type ref, descriptor,
   cast and signature is rewritten consistently; output is guaranteed valid.
3. **CFR 0.152** (`../../tools/cfr.jar`) decompiles the remapped jar. Names now
   propagate into fields, params and locals, so the source reads far better than
   the original CFR dump in `../decompiled/core`.

### AI-naming loop (two-pass — folds semantic names back in)

Because the namer mines the *decompiled* tree, it runs after a first pipeline pass:

```
run_pipeline.ps1  ─►  extract_naming_signals.py  ─►  propose_names.py  ─►  run_pipeline.ps1
 (pass 1: decompile)   (tree → naming_worklist.csv)   (→ ai_names.csv)     (pass 2: apply)
```

- **`extract_naming_signals.py`** mines each still-auto class for signals:
  superclass/interfaces, `getId()` opcode, plaintext string literals, references
  to already-named classes → `mappings/naming_worklist.csv`.
- **`propose_names.py`** votes a **domain** (from confirmed message refs + i18n
  strings) and a **role** (opcode→`Message`, handler-iface→`MessageHandler`,
  msg-refs→`Manager`), embedding the opcode for messages (it's a fact) →
  `mappings/ai_names.csv` with a confidence tier. `build_mapping.py` applies rows
  marked `apply=1` (high+medium); low-confidence rows are kept for review only.
- **`refresh_ai_names.ps1`** runs both steps then re-runs the pipeline.

### Member-naming pass (fields)

**`build_member_mapping.py`** → `mappings/deob_members.srg` (`FD:` lines), applied
by `run_pipeline.ps1` alongside `deob.srg`. It renames **private fields only** of
the 472 named classes (private fields aren't inherited/polymorphic → a rename is
class-local and safe). To stay **idempotent** it reads field/accessor info from
the *stable original* CFR tree (`../decompiled/core`, where field names are always
the true obfuscated ones), resolving field types through `rename_map` +
`class_names.csv`. Name source: `member_overrides.csv` (hand curation) → surviving
accessor (`getPassword()`→`password`) → readable reference type
(`ChatMessageHandler chatMessageHandler`) → typed fallback (`str`/`num`/`flag`).
Methods are a later pass (virtual methods need inheritance-aware `--live`).

### Run it

```powershell
# prereqs: JDK 1.8 (C:\Program Files\Java\jdk1.8.0_202), Python 3,
#          tools/SpecialSource-*-shaded.jar (Maven Central), ../../tools/cfr.jar
powershell -ExecutionPolicy Bypass -File scripts\run_pipeline.ps1
#   -SkipDecompile   # stages 1-2 only (~25 s)
```

Tool downloads (once, into `tools/`):
```powershell
# SpecialSource (remapper) + Vineflower (optional decompiler)
irm https://repo1.maven.org/maven2/net/md-5/SpecialSource/1.11.6/SpecialSource-1.11.6-shaded.jar -OutFile tools\SpecialSource-1.11.6-shaded.jar
irm https://repo1.maven.org/maven2/org/vineflower/vineflower/1.12.0/vineflower-1.12.0.jar -OutFile tools\vineflower-1.12.0.jar
```

---

## Naming policy

Targets go into a **single flat package `deob`**. Priority per class:

1. **Real 2007 name** — `opcode_map.csv` `real_class` (highest confidence wins),
   e.g. `ll` → `deob.LoginMessage`, `na` → `deob.ClientVersionMessage`.
2. **Framework name** — a small hand dictionary (`build_mapping.py: FRAMEWORK`)
   for infra bases documented in `../README.md`, e.g. `acf` →
   `AssetBinaryReader`, `pr_0` → `NetworkMessage`, `atG` → `MessageHandler`.
3. **AI name** — `mappings/ai_names.csv` (rows with `apply=1`), e.g.
   `of` → `CombatMessageHandler`, `alD` → `CalendarMessage17008`.
4. **Auto** — otherwise the class's CFR filename (already unique & FS-safe).

Every final name is then made **globally unique case-insensitively** (safe to
extract on Windows), a **legal Java identifier** (keywords like `do`→`C_do`),
and **not a Windows device name** (`aux`/`con`/`nul`/`com1`→`C_*`).

**Why one flat package?** All ~4900 obfuscated classes sit in the default
package today and freely touch each other's *package-private* members. Moving
them **together** into one named package preserves that access, so the result
still verifies/compiles. Splitting into the real 2007 package tree (`pkg2007`
column) is a later pass that additionally needs an **access-widener**
(package-private → public) — tiny-remapper's `--fixpackageaccess` or an ASM pass.

---

## Result — before / after

Original obfuscated `na extends so_0`; after remap+decompile:

```java
// decompiled/core-src/deob/ClientVersionMessage.java
public class ClientVersionMessage extends OutputOnlyProxyMessage {
    public byte[] encode() {
        ByteBuffer bb = ByteBuffer.allocate(4 + kS.BUILD_VERSION.length());
        bb.put((byte)2);        // major 2
        bb.putShort((short)70); // minor 70   <-- version 2.70
        bb.put((byte)kS.BUILD_VERSION.length());
        bb.put(kS.BUILD_VERSION.getBytes());
        return this.a((byte)0, bb.array());
    }
    public int getId() { return 7; }   // opcode 7
}
```

`LoginMessage` (was `ll extends axX`) now shows the exact plaintext login frame
`[u8 len][login][u8 len][password]`, `getId()==1`, and — after the member pass —
real field names: `private String password` (recovered from the surviving
`getPassword()` accessor; `encode()`/decode now use `this.password`).

**AI-named example** — `of` (no opcode of its own) became `CombatMessageHandler`
purely from its confirmed message references:

```java
// decompiled/core-src/deob/CombatMessageHandler.java  (was of_1)
public class CombatMessageHandler implements MessageHandler {   // atG -> MessageHandler
    public boolean a(NetworkMessage networkMessage) {
        switch (networkMessage.getId()) {
            case 8104: {
                FighterTurnBeginMessage fighterTurnBeginMessage = (FighterTurnBeginMessage)networkMessage;
                ...
```

Private field names are now handled too (`GS`/`GT` → `str`/`password`). Remaining
tokens (`kS` = an auto-named *class*, obfuscated *method* names) are the next layer.

---

## AI naming — done (class level), how to extend

The AI-naming loop (above) is implemented and applied: **321 classes** named
across domains (Combat 62, Chat 26, Guild 20, Coach 17, Team 15, Actor/Exchange
14, Tournament 13, Ladder/Matchmaking/Property/Calendar…). Names are **AI
guesses** (except embedded opcodes, which are facts) — flagged `source=ai` in
`class_names.csv`, with per-class `rationale` in `ai_names.csv`.

To push further:
- **Raise coverage:** widen the `DOMAINS` table or lower the confidence gate in
  `propose_names.py` (158 low-confidence `Screen`/misc proposals are ready but
  not applied), then `refresh_ai_names.ps1`.
- **Curate:** edit `ai_names.csv` by hand (fix a name, flip `apply` 0/1) and
  `run_pipeline.ps1` — it is the source of truth, so hand edits survive.
- **Members — fields: done.** `build_member_mapping.py` renamed 892 private
  fields (`FD:` lines in `deob_members.srg`); curate via `member_overrides.csv`.
  **Methods: next** — accessors/helpers via `MD:` lines; virtual methods need
  SpecialSource `--live` (inheritance-aware) to stay override-safe.

---

## Roadmap

- [x] Class-level remap → readable, collision-free `core.deobf.jar`
- [x] Re-decompile with names propagated (CFR)
- [x] AI-naming worklist (signals extracted)
- [x] AI-name the strong-signal classes; fold back into the map (321 applied)
- [x] Member **field** renaming (892 private fields, `FD:` via build_member_mapping.py)
- [ ] Member **method** renaming (`MD:` + SpecialSource `--live`) / raise AI-naming coverage
- [ ] Move classes into real 2007 packages + access-widener (tiny-remapper `--fixpackageaccess`)
- [ ] Full recompile (needs CFR-artifact cleanup; or Vineflower on Java 11+ for more recompilable source)

---

## Notes / caveats

- **Decompiler:** this box has **only Java 8**, so we use **CFR** (Java-8 OK).
  **Vineflower 1.12.0** (downloaded) produces more *recompilable* source but
  needs **Java 11+** — install a newer JRE to use it.
- **CFR artifacts:** decompiled source is for reading/editing; a handful of
  classes need manual fixups to recompile (a known CFR trait, e.g. the odd
  `a.error(...)` logger reference in `LoginMessage`). The **`.jar` is always
  valid** regardless — remapping is bytecode-level.
- **Copyright:** `build/`, `decompiled/`, and `tools/*.jar` are **git-ignored**
  (derived from the copyrighted, git-ignored `core.jar`, or third-party tools).
  Only scripts, mappings and docs are committed. Same stance as `../README.md`.
- **Provenance of names:** the 14 framework names and the 321 AI names are
  *descriptive guesses* for roles (marked `source=framework` / `source=ai` in
  `class_names.csv`; AI rows carry a `rationale` in `ai_names.csv`), **not**
  confirmed Ankama names. Only the 137 `source=opcode_map` names + embedded
  opcodes are fact-backed.

## Files

```
deobf-lab/
  scripts/
    build_mapping.py          (1) obf -> readable class map (+ CSV); folds ai_names.csv
    run_pipeline.ps1          one-command: map -> remap -> decompile
    extract_naming_signals.py AI-naming worklist builder (decompiled tree -> signals)
    propose_names.py          AI namer (worklist -> ai_names.csv, domain+role+opcode)
    refresh_ai_names.ps1      extract + propose + re-run pipeline
    build_member_mapping.py   field renamer (stable orig tree -> deob_members.srg)
  mappings/                   deob.srg, deob.tiny, class_names.csv, naming_worklist.csv,
                              ai_names.csv, deob_members.srg, member_names.csv             [committed]
  tools/                      SpecialSource-*.jar, vineflower-*.jar                        [git-ignored]
  build/                      core.deobf.jar                                               [git-ignored]
  decompiled/core-src/        deob/*.java                                                  [git-ignored]
  docs/README.md              this file
```
