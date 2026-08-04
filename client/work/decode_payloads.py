#!/usr/bin/env python3
r"""
Extract per-opcode PAYLOAD field layouts from the decompiled 2.70 messages.

For each network message class we parse the body of `a(byte[])` (decode, S->C /
inbound) or `encode()` (C->S / outbound) and emit the ordered list of wire
fields with java type + byte size. All network reads are BIG-ENDIAN
(ByteBuffer.wrap default). Shared inherited headers are expanded:
  - ue_0.o(bb)  -> apt:int32, apu:int32           (fight/combat S->C 8-byte hdr)

Recognized primitives (ByteBuffer):
  getLong  -> i64(8)   getInt   -> i32(4)   getShort -> i16(2)
  get()    -> i8(1)    getFloat -> f32(4)   getDouble-> f64(8)
  getShort()&0xFFFF -> u16       get()&0xFF -> u8
String/blob helpers:
  this.m(bb)      -> blob   : [u8 len][len bytes]
  <len-prefixed UTF>        : detected as get()->len then loop (best-effort)

Output: analysis/payloads.md  and  analysis/payloads.csv
This is a STATIC, best-effort structural extractor: complex loops/nested
objects are flagged '(complex: see source)' rather than guessed.
"""
import os, re, csv

BASE = r"E:\Projets\DofusArena2-06\client"
DEC = os.path.join(BASE, "decompiled", "core")
OUT = os.path.join(BASE, "analysis")
MAPCSV = os.path.join(OUT, "opcode_map.csv")

# --- helper header expansions (inherited decoders read before own fields) ----
HEADER_EXPANSIONS = {
    "o": [("apt", "i32", 4), ("apu", "i32", 4)],   # ue_0.o(bb) fight header
}

# ByteBuffer getter -> (typename, size)
GETTERS = {
    "getLong": ("i64", 8), "getInt": ("i32", 4), "getShort": ("i16", 2),
    "getFloat": ("f32", 4), "getDouble": ("f64", 8),
    "getChar": ("u16", 2),
}

# regex helpers
GET_CALL = re.compile(r"\.(getLong|getInt|getShort|getFloat|getDouble|getChar|get)\s*\(\s*\)")
MASK16 = re.compile(r"&\s*0xFFFF")
MASK8 = re.compile(r"&\s*0xFF\b")
DECODE_SIG = re.compile(r"public\s+boolean\s+a\s*\(\s*byte\[\]\s*\w+\s*\)\s*\{")
ENCODE_SIG = re.compile(r"public\s+byte\[\]\s+encode\s*\(\s*\)\s*\{")
LEN_CHECK = re.compile(r"\.a\s*\(\s*\w+\.length\s*,\s*(\d+)\s*,\s*(true|false)\s*\)")
HELPER_CALL = re.compile(r"this\.(\w+)\s*\(\s*\w+\s*\)")  # this.o(byteBuffer)
ALLOC = re.compile(r"ByteBuffer\.allocate\s*\(([^)]*)\)")
PUT_CALL = re.compile(r"\.(putLong|putInt|putShort|putFloat|putDouble|put)\s*\(")


def extract_method_body(text, sig_re):
    m = sig_re.search(text)
    if not m:
        return None
    i = m.end() - 1  # at the '{'
    depth = 0
    start = i
    for j in range(i, len(text)):
        c = text[j]
        if c == "{":
            depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0:
                return text[start + 1:j]
    return text[start + 1:]


def parse_decode(body):
    """Return (fields, exact_len, complexity_notes)."""
    fields = []
    notes = []
    exact_len = None
    lm = LEN_CHECK.search(body)
    if lm:
        exact_len = (int(lm.group(1)), lm.group(2) == "true")

    # detect loops / collections -> mark complex
    has_loop = bool(re.search(r"\b(for|while)\b", body))

    # walk line by line to preserve order and catch inherited header calls
    for raw in body.splitlines():
        line = raw.strip()
        # inherited header helper e.g. this.o(byteBuffer)
        hc = HELPER_CALL.search(line)
        if hc and hc.group(1) in HEADER_EXPANSIONS and ".get" not in line:
            for nm, ty, sz in HEADER_EXPANSIONS[hc.group(1)]:
                fields.append((nm, ty, sz, "header:" + hc.group(1)))
            continue
        if "this.m(" in line:
            fields.append(("(blob)", "blob", 0, "u8 len + len bytes"))
            continue
        for gm in GET_CALL.finditer(line):
            g = gm.group(1)
            if g == "get":
                if MASK8.search(line):
                    fields.append(("", "u8", 1, ""))
                else:
                    fields.append(("", "i8", 1, ""))
            elif g == "getShort" and MASK16.search(line):
                fields.append(("", "u16", 2, ""))
            else:
                ty, sz = GETTERS[g]
                fields.append(("", ty, sz, ""))
    if has_loop:
        notes.append("contains loop/collection - variable-length, verify source")
    return fields, exact_len, notes


def parse_encode(body):
    fields = []
    notes = []
    alloc = None
    am = ALLOC.search(body)
    if am:
        alloc = am.group(1).strip()
    has_loop = bool(re.search(r"\b(for|while)\b", body))
    for raw in body.splitlines():
        line = raw.strip()
        for pm in PUT_CALL.finditer(line):
            p = pm.group(1)
            mapping = {"putLong": ("i64", 8), "putInt": ("i32", 4),
                       "putShort": ("i16", 2), "putFloat": ("f32", 4),
                       "putDouble": ("f64", 8), "put": ("i8/bytes", 0)}
            ty, sz = mapping[p]
            fields.append(("", ty, sz, ""))
    if has_loop:
        notes.append("contains loop/collection - variable-length, verify source")
    return fields, alloc, notes


def load_opcode_map():
    rows = {}
    with open(MAPCSV, newline="", encoding="utf-8") as f:
        for r in csv.DictReader(f):
            rows[r["obf_file"]] = r
    return rows


def main():
    omap = load_opcode_map()
    results = []
    for obf, meta in omap.items():
        path = os.path.join(DEC, obf + ".java")
        if not os.path.exists(path):
            continue
        with open(path, "r", encoding="utf-8", errors="replace") as f:
            text = f.read()
        direction = meta["direction"]
        if direction == "C2S":
            body = extract_method_body(text, ENCODE_SIG)
            if body is None:
                continue
            fields, alloc, notes = parse_encode(body)
            fixed_len = alloc
        else:  # S2C (and ?)
            body = extract_method_body(text, DECODE_SIG)
            if body is None:
                continue
            fields, exact, notes = parse_decode(body)
            fixed_len = None
            if exact:
                fixed_len = f"{exact[0]} ({'exact' if exact[1] else 'min'})"
        results.append({
            "opcode": int(meta["opcode"]),
            "obf": obf,
            "real": meta["real_class"],
            "dir": direction,
            "len": fixed_len or "",
            "nfields": len(fields),
            "fields": fields,
            "notes": "; ".join(notes),
        })

    results.sort(key=lambda r: (r["opcode"], r["obf"]))

    # CSV: one row per field
    with open(os.path.join(OUT, "payloads.csv"), "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["opcode", "obf", "real", "dir", "declared_len", "field_idx", "field_name", "type", "bytes", "note"])
        for r in results:
            if not r["fields"]:
                w.writerow([r["opcode"], r["obf"], r["real"], r["dir"], r["len"], "", "(no fields / empty)", "", "", r["notes"]])
            for idx, (nm, ty, sz, note) in enumerate(r["fields"]):
                w.writerow([r["opcode"], r["obf"], r["real"], r["dir"], r["len"], idx, nm, ty, sz, note or r["notes"]])

    # Markdown
    lines = ["# DofusArena 2.70 — Message Payload Layouts\n",
             "Auto-extracted from message `a(byte[])` (S2C decode) / `encode()` "
             "(C2S encode) bodies. **All fields are BIG-ENDIAN** (network byte "
             "order). Header already stripped; S2C fight messages (base `ue_0`) "
             "begin with an 8-byte header `apt:i32, apu:i32`.\n",
             "Types: i8/i16/i32/i64 signed, u8/u16 unsigned, f32/f64 float, "
             "blob = `[u8 len][len bytes]`. Rows with a loop note are "
             "variable-length — read the source for the element layout.\n",
             "> Best-effort static extraction. `declared_len` for S2C is the "
             "class's own length assertion; for C2S it's the ByteBuffer.allocate "
             "expression.\n"]
    for r in results:
        title = r["real"] or "(unnamed)"
        lines.append(f"\n### {r['opcode']} — `{r['obf']}` — {title}  [{r['dir']}]")
        if r["len"]:
            lines.append(f"declared length: `{r['len']}`")
        if r["notes"]:
            lines.append(f"note: _{r['notes']}_")
        if not r["fields"]:
            lines.append("_(no simple fields detected — empty body or complex; see source)_")
            continue
        lines.append("| # | field | type | bytes | note |")
        lines.append("|--:|-------|------|------:|------|")
        for idx, (nm, ty, sz, note) in enumerate(r["fields"]):
            lines.append(f"| {idx} | {nm or '—'} | {ty} | {sz or '?'} | {note} |")
    with open(os.path.join(OUT, "payloads.md"), "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")

    total = len(results)
    withfields = sum(1 for r in results if r["fields"])
    looped = sum(1 for r in results if r["notes"])
    print(f"messages processed:    {total}")
    print(f"  with decoded fields: {withfields}")
    print(f"  variable-length:     {looped}")
    print(f"written: analysis/payloads.md, analysis/payloads.csv")


if __name__ == "__main__":
    main()
