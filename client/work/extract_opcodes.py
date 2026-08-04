#!/usr/bin/env python3
"""
Extract network-message opcodes + serialization fingerprints from the
decompiled (obfuscated) DofusArena 2.70 client, and from the 2007 named
source, then join them on opcode to produce a Rosetta-Stone mapping.

Outputs (into analysis/):
  - opcodes_2.70.csv      : obf_class, opcode, direction, base, fingerprint
  - opcodes_2007.csv      : real_class, package, opcode, direction, fingerprint
  - opcode_map.csv        : opcode, obf_class, real_class, fp_match
  - opcodes.md            : human-readable summary table
"""
import os, re, csv, sys

DEC = r"E:\Projets\DofusArena2-06\client\decompiled\core"
# 2.04b (2007) client source, used as a Rosetta Stone. It lives on this repo's
# `v2.04` branch, not on v2.70 - check it out elsewhere and point SRC at it.
SRC = r"E:\Projets\StarLoco-Arena-main\client\source"
OUT = r"E:\Projets\DofusArena2-06\client\analysis"

os.makedirs(OUT, exist_ok=True)

# ---- regexes -----------------------------------------------------------
RENAMED = re.compile(r"Renamed from (\w+)")
# getId() { return <int>; }  (obfuscated) OR getProtocolId/getId in 2007
GETID = re.compile(
    r"public\s+int\s+getId\s*\(\s*\)\s*\{\s*return\s+(-?\d+)\s*;",
    re.S,
)
GETID_2007 = re.compile(
    r"public\s+int\s+get(?:Protocol)?Id\s*\(\s*\)\s*\{\s*return\s+(-?\d+)\s*;",
    re.S,
)
# JD-Core prefixes every line with `/* NN */` or `/*    */`; strip them so the
# body regexes match the same way they do on CFR output.
JDPREFIX = re.compile(r"/\*[\s\d]*\*/")
EXTENDS = re.compile(r"\bextends\s+([A-Za-z_]\w*)")

# serialization op fingerprint tokens (order preserved)
WRITE_OPS = re.compile(
    r"\.(putLong|putInt|putShort|putFloat|putDouble|put|"
    r"writeLong|writeInt|writeShort|writeByte|writeFloat|writeString|writeUTF)\b"
)
READ_OPS = re.compile(
    r"\.(getLong|getInt|getShort|getFloat|getDouble|get|"
    r"readLong|readInt|readShort|readByte|readFloat|readString|readUTF)\b"
)

# normalize op tokens so 2007 and 2.70 fingerprints are comparable
NORM = {
    "putLong": "L", "writeLong": "L", "getLong": "L", "readLong": "L",
    "putInt": "I", "writeInt": "I", "getInt": "I", "readInt": "I",
    "putShort": "S", "writeShort": "S", "getShort": "S", "readShort": "S",
    "putFloat": "F", "writeFloat": "F", "getFloat": "F", "readFloat": "F",
    "putDouble": "D", "writeDouble": "D", "getDouble": "D", "readDouble": "D",
    "put": "b", "writeByte": "b", "get": "b", "readByte": "b",
    "writeString": "T", "writeUTF": "T", "readString": "T", "readUTF": "T",
}


def fingerprint(text, encode=True):
    """Ordered token fingerprint of the (en/de)code method body."""
    rx = WRITE_OPS if encode else READ_OPS
    toks = [NORM.get(m, "?") for m in rx.findall(text)]
    return "".join(toks)


# first pass: build a superclass index (class -> its `extends X`) so we can
# resolve the full base chain of every class to classify network vs UI messages.
_ext_index = {}


def build_extends_index():
    for name in os.listdir(DEC):
        if not name.endswith(".java"):
            continue
        cls = name[:-5]
        try:
            with open(os.path.join(DEC, name), "r", encoding="utf-8", errors="replace") as f:
                head = f.read(4000)
        except OSError:
            continue
        m = re.search(r"\bclass\s+" + re.escape(cls) + r"\b[^\{]*?\bextends\s+([A-Za-z_]\w*)", head, re.S)
        _ext_index[cls] = m.group(1) if m else None


def base_chain(cls, limit=12):
    chain = []
    seen = set()
    cur = cls
    while cur and cur not in seen and len(chain) < limit:
        seen.add(cur)
        parent = _ext_index.get(cur)
        if parent is None:
            break
        chain.append(parent)
        cur = parent
    return chain


def classify(cls, base):
    """Return (kind, direction). kind in NET/UI/OTHER.

    Discriminator: only descendants of acb_2 carry the wire header
    (addClientHeader => [u16 len][u8 arch][u16 opcode][payload]) and are real
    NETWORK opcodes. Descendants of aed_2/sb_0 also extend pr_0 but are the
    client-side UI message bus (internal event IDs, NOT wire opcodes). axX is a
    separate secure/login network branch off pr_0 (no acb_2).
    """
    chain = [base] + base_chain(base)
    cset = set(c for c in chain if c)
    # UI bus takes precedence: aed_2/sb_0 subtree is never a wire opcode
    if "aed_2" in cset or "sb_0" in cset:
        return "UI", "UI"
    if "acb_2" in cset:
        if "so_0" in cset:
            return "NET", "C2S"
        if "ael_2" in cset:  # includes ue_0 fight msgs (ue_0 extends ael_2)
            return "NET", "S2C"
        return "NET", "?"
    if "axX" in cset:  # secure/login protocol (client->server), int opcode
        return "NET", "C2S"
    if "pr_0" in cset:
        return "OTHER", "?"
    return "OTHER", "?"


def scan_obf():
    if not _ext_index:
        build_extends_index()
    rows = []
    for name in os.listdir(DEC):
        if not name.endswith(".java"):
            continue
        path = os.path.join(DEC, name)
        try:
            with open(path, "r", encoding="utf-8", errors="replace") as f:
                text = f.read()
        except OSError:
            continue
        m = GETID.search(text)
        if not m:
            continue
        opcode = int(m.group(1))
        renamed = RENAMED.search(text)
        real_obf = renamed.group(1) if renamed else name[:-5]
        ext = EXTENDS.search(text)
        base = ext.group(1) if ext else ""
        kind, direction = classify(name[:-5], base)
        fp_w = fingerprint(text, encode=True)
        fp_r = fingerprint(text, encode=False)
        rows.append({
            "obf_file": name[:-5],
            "obf_orig": real_obf,
            "opcode": opcode,
            "base": base,
            "kind": kind,
            "direction": direction,
            "fp_write": fp_w,
            "fp_read": fp_r,
        })
    return rows


def scan_2007():
    rows = []
    for root, _dirs, files in os.walk(SRC):
        for name in files:
            if not name.endswith(".java"):
                continue
            path = os.path.join(root, name)
            try:
                with open(path, "r", encoding="utf-8", errors="replace") as f:
                    text = f.read()
            except OSError:
                continue
            text = JDPREFIX.sub("", text)  # strip JD-Core line-number comments
            m = GETID_2007.search(text)
            if not m:
                continue
            opcode = int(m.group(1))
            rel = os.path.relpath(root, SRC).replace(os.sep, ".")
            fp_w = fingerprint(text, encode=True)
            fp_r = fingerprint(text, encode=False)
            low = rel.lower()
            if "clienttoserver" in low or "OutputOnly" in text:
                direction = "C2S"
            elif "servertoclient" in low or "InputOnly" in text:
                direction = "S2C"
            else:
                direction = "?"
            rows.append({
                "real_class": name[:-5],
                "package": rel,
                "opcode": opcode,
                "direction": direction,
                "fp_write": fp_w,
                "fp_read": fp_r,
            })
    return rows


def main():
    obf = scan_obf()
    src = scan_2007()

    with open(os.path.join(OUT, "opcodes_2.70.csv"), "w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=["opcode", "obf_file", "obf_orig", "kind", "base", "direction", "fp_write", "fp_read"])
        w.writeheader()
        for r in sorted(obf, key=lambda x: (x["kind"], x["opcode"])):
            w.writerow({k: r[k] for k in w.fieldnames})

    with open(os.path.join(OUT, "opcodes_2007.csv"), "w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=["opcode", "real_class", "package", "direction", "fp_write", "fp_read"])
        w.writeheader()
        for r in sorted(src, key=lambda x: x["opcode"]):
            w.writerow({k: r[k] for k in w.fieldnames})

    # join on opcode
    src_by_op = {}
    for r in src:
        src_by_op.setdefault(r["opcode"], []).append(r)

    # only NETWORK messages belong in the wire-opcode map
    net = [r for r in obf if r["kind"] == "NET"]
    joined = []
    for r in sorted(net, key=lambda x: (x["opcode"], x["obf_file"])):
        cands = src_by_op.get(r["opcode"], [])
        real = ""
        fp_match = ""
        confidence = ""
        if cands:
            best = None
            for c in cands:
                if (c["fp_write"] and c["fp_write"] == r["fp_write"]) or \
                   (c["fp_read"] and c["fp_read"] == r["fp_read"]):
                    best = c
                    fp_match = "YES"
                    break
            if best is None:
                best = cands[0]
                fp_match = "NO"
            real = best["real_class"]
            confidence = "high" if fp_match == "YES" else "opcode-only"
        else:
            confidence = "new-in-2.70"
        joined.append({
            "opcode": r["opcode"],
            "obf_file": r["obf_file"],
            "real_class": real,
            "direction": r["direction"],
            "confidence": confidence,
            "base": r["base"],
        })

    with open(os.path.join(OUT, "opcode_map.csv"), "w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=["opcode", "obf_file", "real_class", "direction", "confidence", "base"])
        w.writeheader()
        for r in joined:
            w.writerow(r)

    # markdown report
    write_markdown(joined, obf, src)

    matched = sum(1 for r in joined if r["real_class"])
    fp_ok = sum(1 for r in joined if r["confidence"] == "high")
    ui = sum(1 for r in obf if r["kind"] == "UI")
    other = sum(1 for r in obf if r["kind"] == "OTHER")
    print(f"2.70 classes with getId():   {len(obf)}")
    print(f"  NETWORK messages:          {len(net)}")
    print(f"  UI-internal (sb_0):        {ui}")
    print(f"  other/non-message:         {other}")
    print(f"2007 named messages:         {len(src)}")
    print(f"NET joined by opcode:        {matched}/{len(net)}")
    print(f"  high-confidence (fp):      {fp_ok}")
    print(f"  new-in-2.70 (unnamed):     {len(net) - matched}")
    print(f"Distinct NET opcodes:        {len(set(r['opcode'] for r in net))}")
    print(f"Output written to:           {OUT}")


def write_markdown(joined, obf, src):
    lines = []
    lines.append("# DofusArena 2.70 — Network Opcode Map\n")
    lines.append("Auto-generated by `work/extract_opcodes.py`. Maps obfuscated 2.70 "
                 "message classes to opcodes and (where possible) to real class names "
                 "recovered from the 2007 named source.\n")
    lines.append("**Confidence:** `high` = opcode + serialization fingerprint match; "
                 "`opcode-only` = opcode matched but field layout differs (verify!); "
                 "`new-in-2.70` = opcode absent from 2007 source (needs bytecode analysis).\n")
    lines.append("Wire framing (main game protocol): "
                 "`[u16 length][u8 archTarget=3][u16 opcode][payload]`, little-endian. "
                 "Base classes: `so_0`=client→server, `ael_2`=server→client "
                 "(`ue_0`=fight S2C), `axX`=secure/login.\n")
    c2s = [r for r in joined if r["direction"] == "C2S"]
    s2c = [r for r in joined if r["direction"] != "C2S"]
    for title, group in (("Client → Server (C2S)", c2s), ("Server → Client (S2C)", s2c)):
        lines.append(f"\n## {title}\n")
        lines.append("| Opcode | Obf class | Real name (2007) | Confidence | Base |")
        lines.append("|-------:|-----------|------------------|------------|------|")
        for r in sorted(group, key=lambda x: x["opcode"]):
            lines.append(f"| {r['opcode']} | `{r['obf_file']}` | "
                         f"{r['real_class'] or '—'} | {r['confidence']} | `{r['base']}` |")
    with open(os.path.join(OUT, "opcodes.md"), "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")


if __name__ == "__main__":
    main()
