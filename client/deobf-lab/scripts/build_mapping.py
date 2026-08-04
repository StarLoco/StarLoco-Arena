#!/usr/bin/env python3
r"""
build_mapping.py  --  Build a deobfuscation *remap* for DofusArena 2.70 core.jar.

Unlike work/patch_class.py (which REOBFUSCATES a single edited class back to its
cryptic name and injects it), this produces a mapping that turns the WHOLE jar
into readable, unique, non-colliding names -- at the *bytecode* level -- so it can
be remapped once (SpecialSource / tiny-remapper) and then decompiled into a clean,
human/AI-readable, recompilable tree.

Naming policy (target = a single flat package, default "deob"):
  1. real 2007 name   -- from analysis/opcode_map.csv (obf_file -> real_class),
                         resolved through rename_map.csv (CFR name -> true jar name).
                         Highest confidence wins.
  2. framework name   -- a small hand dictionary for high-value infra base classes
                         documented in ../README.md (readers/writers/bases/dispatchers).
  3. auto name        -- else the CFR filename (already collision-free & FS-safe,
                         courtesy of work/build_rename_map.py).

Every final simple name is then made:
  * globally unique (case-insensitively -> safe to extract on Windows),
  * a legal Java identifier (Java keywords -> prefixed),
  * not a Windows reserved device name (AUX/CON/NUL/COM1.. -> prefixed).

A single flat package is deliberate: all ~4900 obfuscated classes live in the
default package today and freely use package-private members of one another.
Moving them TOGETHER into one named package preserves that access (so the result
still verifies / recompiles).  Splitting into the real 2007 packages is a later
pass that also needs an access-widener -- see docs/README.md.

Classes that already kept a real com.ankamagames.* name (have '/' in their jar
name) are LEFT UNTOUCHED (no mapping emitted).

Outputs (into ../mappings/):
  deob.srg          SpecialSource searge mapping (CL: lines, incl. nested)
  deob.tiny         tiny v1 mapping (CLASS lines, incl. nested)
  class_names.csv   obf_true, cfr, readable, source, opcode, direction, base, pkg2007
"""
import csv
import os
import re
import subprocess
import sys

# --- paths (verified 2026-07) ------------------------------------------------
JAR      = r"E:\Projets\DofusArena2-06\client\compiled\game\core.jar"
JARBIN   = r"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe"
ANALYSIS = r"E:\Projets\DofusArena2-06\client\analysis"
OUTDIR   = r"E:\Projets\DofusArena2-06\client\deobf-lab\mappings"
PKG      = "deob"

# --- hand dictionary: high-value framework classes (keyed by CFR name) -------
# Names are descriptive (goal is readability, not byte-exact original names).
# Source provenance for each is ../README.md "Key classes" + analysis/PROTOCOL.md.
FRAMEWORK = {
    "acf":   "AssetBinaryReader",        # little-endian asset/map reader
    "aij_1": "AssetBinaryWriter",        # binary writer
    "pr_0":  "NetworkMessage",           # abstract base: encode()/a(byte[])/getId()
    "acb_2": "ClientProxyMessage",       # main-game framing base
    "so_0":  "OutputOnlyProxyMessage",   # client -> server base
    "ael_2": "InputOnlyProxyMessage",    # server -> client base
    "ue_0":  "FightServerToClientMessage",  # fight S2C base (+8-byte header)
    "axX":   "AdminMessageBase",         # login/secure framing base
    "qc_2":  "LoginProtocolDispatcher",  # login/secure switch
    "fp_0":  "GameMessageDispatcher",    # main-game dispatcher (~250-case)
    "gz_1":  "GameMessageFactory",       # opcode -> message registry
    "aed_2": "UiEventMessage",           # UI message bus (NOT wire)
    "sb_0":  "UiEventBus",               # UI message bus (NOT wire)
    "atG":   "MessageHandler",           # interface implemented by msg dispatch handlers
}

JAVA_KEYWORDS = {
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while", "true", "false", "null",
    "var", "record", "yield", "sealed", "permits",
}
WIN_RESERVED = {"con", "prn", "aux", "nul"} | {f"com{i}" for i in range(1, 10)} | {f"lpt{i}" for i in range(1, 10)}


def jar_classes():
    """All .class entries in the jar (internal names, no .class, keep '$' and '/')."""
    res = subprocess.run([JARBIN, "-tf", JAR], capture_output=True, text=True)
    if res.returncode != 0:
        sys.exit(f"jar -tf failed: {res.stderr}")
    out = []
    for line in res.stdout.splitlines():
        line = line.strip()
        if line.endswith(".class"):
            out.append(line[:-6])
    return out


def load_rename_map():
    cfr2orig, orig2cfr = {}, {}
    with open(os.path.join(ANALYSIS, "rename_map.csv"), encoding="utf-8") as f:
        for row in csv.DictReader(f):
            cfr, orig = row["cfr_name"], row["original_name"]
            cfr2orig[cfr] = orig
            orig2cfr.setdefault(orig, cfr)
    return cfr2orig, orig2cfr


def load_opcode_semantics(cfr2orig, true_set):
    """true_jar_name -> (real_class, opcode, direction, base, confidence)."""
    conf_rank = {"high": 3, "opcode-only": 2, "new-in-2.70": 1, "": 0}
    best = {}
    with open(os.path.join(ANALYSIS, "opcode_map.csv"), encoding="utf-8") as f:
        for row in csv.DictReader(f):
            real = (row.get("real_class") or "").strip()
            if not real:
                continue  # unnamed / new-in-2.70 -> leave for auto naming
            obf = (row.get("obf_file") or "").strip()
            true = cfr2orig.get(obf, obf)  # resolve CFR name -> true jar name
            if true not in true_set:
                continue
            conf = (row.get("confidence") or "").strip()
            cand = (real, row.get("opcode", ""), row.get("direction", ""),
                    row.get("base", ""), conf)
            if true not in best or conf_rank.get(conf, 0) > conf_rank.get(best[true][4], 0):
                best[true] = cand
    return best


def load_pkg2007():
    m = {}
    p = os.path.join(ANALYSIS, "opcodes_2007.csv")
    if os.path.exists(p):
        with open(p, encoding="utf-8") as f:
            for row in csv.DictReader(f):
                rc = (row.get("real_class") or "").strip()
                if rc:
                    m.setdefault(rc, (row.get("package") or "").strip())
    return m


def load_ai_names():
    """obf_true -> ai_name for rows propose_names.py marked apply==1."""
    p = os.path.join(OUTDIR, "ai_names.csv")
    m = {}
    if os.path.exists(p):
        with open(p, encoding="utf-8") as fh:
            for r in csv.DictReader(fh):
                if str(r.get("apply", "")).strip() in ("1", "True", "true"):
                    m[r["obf_true"]] = r["ai_name"]
    return m


def sanitize(name):
    if name.lower() in JAVA_KEYWORDS or name.lower() in WIN_RESERVED:
        return "C_" + name
    if not re.match(r"^[A-Za-z_$][A-Za-z0-9_$]*$", name):
        return "C_" + re.sub(r"[^A-Za-z0-9_$]", "_", name)
    return name


def main():
    os.makedirs(OUTDIR, exist_ok=True)
    cfr2orig, orig2cfr = load_rename_map()
    all_entries = jar_classes()

    # default-package top-level classes only (no '/', no '$') are the remap targets
    default_top = [c for c in all_entries if "/" not in c and "$" not in c]
    true_set = set(default_top)
    real_pkg_count = sum(1 for c in all_entries if "/" in c)

    opsem = load_opcode_semantics(cfr2orig, true_set)
    fw_true = {}  # true jar name -> framework readable name
    fw_miss = []
    for cfr, nice in FRAMEWORK.items():
        true = cfr2orig.get(cfr, cfr)
        if true in true_set:
            fw_true[true] = nice
        else:
            fw_miss.append((cfr, true))
    pkg2007 = load_pkg2007()
    ai_names = load_ai_names()

    # assign simple target names (pre-uniqueness)
    rows = []  # (true, cfr, base_simple, source, opcode, direction, base, pkg2007)
    n_real = n_fw = n_ai = n_auto = 0
    for true in sorted(default_top):
        cfr = orig2cfr.get(true, true)
        if true in opsem:
            real, opcode, direction, base, _conf = opsem[true]
            simple, source = real, "opcode_map"
            n_real += 1
            pkg = pkg2007.get(real, "")
        elif true in fw_true:
            simple, source = fw_true[true], "framework"
            opcode = direction = base = pkg = ""
            n_fw += 1
        elif true in ai_names:
            simple, source = ai_names[true], "ai"
            opcode = direction = base = pkg = ""
            n_ai += 1
        else:
            simple, source = cfr, "auto"
            opcode = direction = base = pkg = ""
            n_auto += 1
        rows.append([true, cfr, sanitize(simple), source, opcode, direction, base, pkg])

    # global case-insensitive uniqueness (safe for Windows extraction)
    seen = {}
    n_dedup = 0
    for r in rows:
        base_simple = r[2]
        key = base_simple.lower()
        if key not in seen:
            seen[key] = 0
            final = base_simple
        else:
            seen[key] += 1
            final = f"{base_simple}_{seen[key]}"
            while final.lower() in seen:
                seen[key] += 1
                final = f"{base_simple}_{seen[key]}"
            seen[final.lower()] = 0
            n_dedup += 1
        r[2] = final

    true2target = {r[0]: f"{PKG}/{r[2]}" for r in rows}  # internal name w/ '/'

    # expand to every jar entry (nested classes inherit their outer's target)
    def target_for(entry):
        if "$" in entry:
            outer, inner = entry.split("$", 1)
            if outer in true2target:
                return f"{true2target[outer]}${inner}"
            return None  # nested of an untouched real-package class
        return true2target.get(entry)

    mapped_entries = []
    for e in all_entries:
        t = target_for(e)
        if t and t != e:
            mapped_entries.append((e, t))

    # --- write SRG (SpecialSource) -------------------------------------------
    srg = os.path.join(OUTDIR, "deob.srg")
    with open(srg, "w", encoding="utf-8", newline="\n") as f:
        for old, new in mapped_entries:
            f.write(f"CL: {old} {new}\n")

    # --- write tiny v1 (tiny-remapper) ---------------------------------------
    tiny = os.path.join(OUTDIR, "deob.tiny")
    with open(tiny, "w", encoding="utf-8", newline="\n") as f:
        f.write("v1\tobf\tdeob\n")
        for old, new in mapped_entries:
            f.write(f"CLASS\t{old}\t{new}\n")

    # --- write human/AI CSV --------------------------------------------------
    csvp = os.path.join(OUTDIR, "class_names.csv")
    with open(csvp, "w", encoding="utf-8", newline="") as f:
        w = csv.writer(f)
        w.writerow(["obf_true", "cfr", "readable", "source", "opcode",
                    "direction", "base", "pkg2007"])
        for r in rows:
            r2 = list(r)
            r2[2] = true2target[r[0]].split("/", 1)[1]
            w.writerow(r2)

    print(f"jar .class entries:        {len(all_entries)}")
    print(f"  real-package (untouched):{real_pkg_count}")
    print(f"  default-package classes: {len(default_top)}")
    print(f"named from opcode_map:     {n_real}")
    print(f"named from framework dict: {n_fw}   (missing: {fw_miss})")
    print(f"named from ai_names.csv:   {n_ai}")
    print(f"auto (CFR name):           {n_auto}")
    print(f"case-insensitive dedups:   {n_dedup}")
    print(f"total mapping lines:       {len(mapped_entries)}  (incl. nested)")
    print(f"written: {srg}")
    print(f"written: {tiny}")
    print(f"written: {csvp}")


if __name__ == "__main__":
    main()
