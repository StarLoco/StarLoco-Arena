#!/usr/bin/env python3
r"""
build_member_mapping.py -- rename obfuscated FIELDS of the named classes.

Member layer on top of the class remap: emits SRG `FD:` lines that SpecialSource
applies alongside deob.srg, turning e.g. `private String GT` into
`private String password`.

IDEMPOTENCY: we read field/accessor info from the **original** CFR tree
(../decompiled/core), where field names are always the true obfuscated names and
never change -- NOT from our own output tree (whose fields may already be
renamed). Types there are obfuscated too, so we resolve a field's type name
through rename_map (CFR->true) + class_names.csv (true->readable) to decide if it
is a class we already named.

SAFETY: private fields only. Private fields are not inherited/polymorphic, so a
rename is class-local (no override/vtable concerns).

Field-name source, in priority order:
  0. member_overrides.csv (hand curation: owner_readable, field_obf, field_new)
  1. surviving accessor   -- getX()/isX()/setX() whose name survived -> field = x
  2. named reference type -- field typed as a class we named -> camelCase(type)
  3. typed fallback       -- primitive / String / collection -> typed name
  4. else                 -- leave obfuscated (auto/lib ref types)

Output:
  ../mappings/deob_members.srg   FD lines (fed to SpecialSource with deob.srg)
  ../mappings/member_names.csv   audit: owner_obf, owner_readable, field_obf, field_new, why
"""
import csv
import os
import re

LAB      = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ORIG     = os.path.join(LAB, "..", "decompiled", "core")        # STABLE obf tree
ANALYSIS = os.path.join(LAB, "..", "analysis")
NAMES    = os.path.join(LAB, "mappings", "class_names.csv")
OUT_SRG  = os.path.join(LAB, "mappings", "deob_members.srg")
OUT_CSV  = os.path.join(LAB, "mappings", "member_names.csv")
PKG = "deob"

PRIM = {"int": "num", "long": "lng", "short": "sht", "byte": "bt",
        "boolean": "flag", "float": "flt", "double": "dbl", "char": "chr",
        "String": "str"}
COLL = ("List", "ArrayList", "Map", "HashMap", "Set", "HashSet", "Vector", "Collection")
KEYWORDS = {"do", "if", "for", "int", "new", "try", "case", "char", "byte", "long",
            "this", "else", "enum", "goto", "null", "true", "void", "class", "final"}

RE_FIELD  = re.compile(
    r'^\s*private\s+((?:static\s+|final\s+|transient\s+|volatile\s+)*)([\w.$\[\]<>]+)\s+(\w+)\s*[;=]', re.M)
RE_GETTER = re.compile(
    r'(?:public|protected)\s+[\w.$\[\]<>]+\s+(\w+)\s*\(\s*\)\s*\{\s*return\s+this\.(\w+)\s*;\s*\}')
RE_SETTER = re.compile(
    r'(?:public|protected)\s+void\s+(\w+)\s*\(\s*[\w.$\[\]<>]+\s+(\w+)\s*\)\s*\{\s*this\.(\w+)\s*=\s*\2\s*;\s*\}')


def is_obf(name):
    return len(name) <= 3


def lower_first(s):
    return s[:1].lower() + s[1:] if s else s


def main():
    cfr2orig, orig2cfr = {}, {}
    with open(os.path.join(ANALYSIS, "rename_map.csv"), encoding="utf-8") as f:
        for r in csv.DictReader(f):
            cfr2orig[r["cfr_name"]] = r["original_name"]
            orig2cfr.setdefault(r["original_name"], r["cfr_name"])

    true2readable, true2source = {}, {}
    with open(NAMES, encoding="utf-8") as f:
        for r in csv.DictReader(f):
            true2readable[r["obf_true"]] = r["readable"]
            true2source[r["obf_true"]] = r["source"]
    named = {rd: t for t, rd in true2readable.items() if true2source.get(t) != "auto"}

    overrides = {}
    ovp = os.path.join(LAB, "mappings", "member_overrides.csv")
    if os.path.exists(ovp):
        with open(ovp, encoding="utf-8") as fh:
            for r in csv.DictReader(fh):
                overrides[(r["owner_readable"], r["field_obf"])] = r["field_new"]

    def readable_type(simple):
        """obf/CFR type token -> readable name iff it's a class we named."""
        true_t = cfr2orig.get(simple, simple)
        rd = true2readable.get(true_t)
        return rd if rd and true2source.get(true_t) != "auto" else None

    fd_lines, audit = [], []
    n_acc = n_ref = n_prim = n_ovr = 0
    for readable, owner_obf in sorted(named.items()):
        cfr = orig2cfr.get(owner_obf, owner_obf)
        p = os.path.join(ORIG, cfr + ".java")
        if not os.path.exists(p):
            continue
        with open(p, encoding="utf-8", errors="replace") as fh:
            txt = fh.read()

        acc = {}
        for m in RE_GETTER.finditer(txt):
            mn, fld = m.group(1), m.group(2)
            if mn.startswith(("get", "is")) and not is_obf(mn):
                base = lower_first(mn[3 if mn.startswith("get") else 2:])
                if base:
                    acc.setdefault(fld, base)
        for m in RE_SETTER.finditer(txt):
            mn, fld = m.group(1), m.group(3)
            if mn.startswith("set") and not is_obf(mn):
                base = lower_first(mn[3:])
                if base:
                    acc.setdefault(fld, base)

        fields = RE_FIELD.findall(txt)
        used = {nm for _, _, nm in fields if not is_obf(nm)}

        for _mods, typ, nm in fields:
            ov = overrides.get((readable, nm))
            if not ov and not is_obf(nm):
                continue
            simple = typ.split("<")[0].replace("[]", "").split(".")[-1]
            if ov:
                base, why = ov, "override"; n_ovr += 1
            elif nm in acc:
                base, why = acc[nm], "accessor"; n_acc += 1
            elif readable_type(simple):
                base, why = lower_first(readable_type(simple)), "ref-type"; n_ref += 1
            elif typ in PRIM:
                base, why = PRIM[typ], "prim"; n_prim += 1
            elif typ.endswith("[]") and typ[:-2] in PRIM:
                base, why = PRIM[typ[:-2]] + "Arr", "prim"; n_prim += 1
            elif simple in COLL:
                base, why = lower_first(simple), "prim"; n_prim += 1
            else:
                continue

            if base in KEYWORDS:
                base += "_"
            final, k = base, 1
            while final in used:
                k += 1
                final = f"{base}{k}"
            used.add(final)

            fd_lines.append(f"FD: {owner_obf}/{nm} {PKG}/{readable}/{final}")
            audit.append([owner_obf, readable, nm, final, why])

    with open(OUT_SRG, "w", encoding="utf-8", newline="\n") as f:
        f.write("\n".join(fd_lines) + ("\n" if fd_lines else ""))
    with open(OUT_CSV, "w", encoding="utf-8", newline="") as f:
        w = csv.writer(f)
        w.writerow(["owner_obf", "owner_readable", "field_obf", "field_new", "why"])
        w.writerows(audit)

    print(f"named classes scanned:   {len(named)}")
    print(f"fields renamed:          {len(fd_lines)}")
    print(f"  override / accessor:   {n_ovr} / {n_acc}")
    print(f"  ref-type / fallback:   {n_ref} / {n_prim}")
    print(f"written: {OUT_SRG}")
    print(f"written: {OUT_CSV}")


if __name__ == "__main__":
    main()
