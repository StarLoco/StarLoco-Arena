#!/usr/bin/env python3
r"""
extract_naming_signals.py -- build the AI-naming worklist for the classes that
are still auto-named (CFR names) after the mechanical remap.

The 2.70 obfuscation is name-only and strings are PLAINTEXT, so every class still
carries strong semantic signals. For each still-auto class we harvest the signals
a human/LLM uses to pick a good name:

  * super / interfaces      (e.g. extends OutputOnlyProxyMessage -> it's a C2S message)
  * opcode                  (int returned by getId()  -> cross-ref opcodes.md)
  * string literals         (plaintext French UI text, i18n keys, log messages)
  * refs to known classes   (already-named deob types it touches)
  * #methods / #fields

Output: ../mappings/naming_worklist.csv  (sorted: richest signals first)
Feed this to an LLM (or a human) to propose readable names, then add the
chosen names to build_mapping.py (opcode-less classes) and re-run run_pipeline.ps1.
"""
import csv
import os
import re

LAB     = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC     = os.path.join(LAB, "decompiled", "core-src", "deob")
NAMES   = os.path.join(LAB, "mappings", "class_names.csv")
OUT     = os.path.join(LAB, "mappings", "naming_worklist.csv")

RE_STR   = re.compile(r'"((?:[^"\\\n]|\\.){3,})"')  # single-line literals only
RE_EXT   = re.compile(r'\bclass\s+\w+\s+extends\s+([\w.]+)')
RE_IMPL  = re.compile(r'\bimplements\s+([\w.,\s]+?)\s*\{')
RE_GETID = re.compile(r'\bint\s+getId\s*\(\s*\)\s*\{\s*return\s+(-?\d+)\s*;')
RE_FIELD = re.compile(r'^\s*(?:private|protected|public)\s+(?:static\s+|final\s+|transient\s+|volatile\s+)*[\w.\[\]<>]+\s+\w+\s*[;=]', re.M)
RE_METH  = re.compile(r'^\s*(?:public|protected|private)\s+(?:static\s+|final\s+|abstract\s+|synchronized\s+|native\s+)*[\w.\[\]<>]+\s+\w+\s*\(', re.M)


def load_names():
    known = {}       # readable simple name -> (obf_true, source) for named classes
    rows  = []       # all rows
    with open(NAMES, encoding="utf-8") as f:
        for r in csv.DictReader(f):
            rows.append(r)
            if r["source"] in ("opcode_map", "framework"):
                known[r["readable"]] = (r["obf_true"], r["source"])
    return rows, set(known)


def main():
    rows, known = load_names()
    work = []
    for r in rows:
        if r["source"] != "auto":
            continue
        p = os.path.join(SRC, r["readable"] + ".java")
        if not os.path.exists(p):
            continue
        with open(p, encoding="utf-8", errors="replace") as f:
            txt = f.read()

        ext = RE_EXT.search(txt)
        ext = ext.group(1).split(".")[-1] if ext else ""
        impl_m = RE_IMPL.search(txt)
        impls = ""
        if impl_m:
            impls = ",".join(s.strip().split(".")[-1] for s in impl_m.group(1).split(",") if s.strip())
        gid = RE_GETID.search(txt)
        opcode = gid.group(1) if gid else ""

        strings = []
        for m in RE_STR.finditer(txt):
            s = m.group(1)
            if sum(c.isalpha() for c in s) < 3:
                continue  # skip punctuation/format fragments like "401|"
            if " + " in s or "()" in s:
                continue  # skip concatenation residue between adjacent literals
            if s not in strings and not s.startswith("Decompiled"):
                strings.append(s)
            if len(strings) >= 8:
                break

        refs = sorted({w for w in re.findall(r'\b([A-Z]\w+)\b', txt) if w in known})[:8]

        nf = len(RE_FIELD.findall(txt))
        nm = len(RE_METH.findall(txt))

        # informativeness score: opcode + known base/refs + plaintext strings
        score = (10 if opcode else 0) + (4 if ext in known else 0) + \
                (2 * len(refs)) + min(len(strings), 8) + (2 if impls else 0)

        work.append({
            "obf_true": r["obf_true"], "cfr": r["cfr"], "score": score,
            "opcode": opcode, "extends": ext, "implements": impls,
            "n_methods": nm, "n_fields": nf, "refs_known": " ".join(refs),
            "strings": " | ".join(strings),
        })

    work.sort(key=lambda w: (-w["score"], w["obf_true"]))
    cols = ["obf_true", "cfr", "score", "opcode", "extends", "implements",
            "n_methods", "n_fields", "refs_known", "strings"]
    with open(OUT, "w", encoding="utf-8", newline="") as f:
        w = csv.DictWriter(f, fieldnames=cols)
        w.writeheader()
        w.writerows(work)

    strong = sum(1 for w in work if w["score"] >= 8)
    withstr = sum(1 for w in work if w["strings"])
    withop  = sum(1 for w in work if w["opcode"])
    print(f"auto classes analysed:   {len(work)}")
    print(f"  with an opcode:        {withop}")
    print(f"  with plaintext strings:{withstr}")
    print(f"  strong signals (>=8):  {strong}")
    print(f"written: {OUT}")


if __name__ == "__main__":
    main()
