#!/usr/bin/env python3
"""
Build the CFR-name -> original-obfuscated-name rename map for core.jar.

CFR renames classes whose names collide case-insensitively on Windows
(e.g. original `fW` -> `fw_1`, `alO` -> `alo_2`). It records the original in a
header comment: `* Renamed from XXX`. We harvest those.

Also cross-checks against the real class list inside core.jar (via `jar -tf`)
so we know the authoritative set of original names.

Outputs analysis/rename_map.csv : cfr_name, original_name
"""
import os, re, csv, subprocess

DEC = r"E:\Projets\DofusArena2-06\client\decompiled\core"
JAR = r"E:\Projets\DofusArena2-06\client\compiled\game\core.jar"
JARBIN = r"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe"
OUT = r"E:\Projets\DofusArena2-06\client\analysis"

RENAMED = re.compile(r"Renamed from (\S+)")


def original_class_names():
    """Authoritative set of class names (default package only) from the jar."""
    res = subprocess.run([JARBIN, "-tf", JAR], capture_output=True, text=True)
    names = set()
    for line in res.stdout.splitlines():
        line = line.strip()
        if line.endswith(".class") and "/" not in line and "$" not in line:
            names.add(line[:-6])
    return names


def main():
    os.makedirs(OUT, exist_ok=True)
    originals = original_class_names()
    rows = []
    renamed_count = 0
    for name in os.listdir(DEC):
        if not name.endswith(".java"):
            continue
        cfr = name[:-5]
        path = os.path.join(DEC, name)
        try:
            with open(path, "r", encoding="utf-8", errors="replace") as f:
                head = f.read(600)
        except OSError:
            continue
        m = RENAMED.search(head)
        if m:
            orig = m.group(1)
            renamed_count += 1
        else:
            orig = cfr
            # CFR sometimes suffixes a colliding name without leaving a
            # `Renamed from` comment. If our name ends in _N and stripping it
            # yields a real jar class that isn't otherwise decompiled here,
            # adopt the stripped original.
            sm = re.match(r"^(.*)_\d+$", cfr)
            if sm:
                stripped = sm.group(1)
                if stripped in originals and stripped != cfr:
                    orig = stripped
                    renamed_count += 1
        rows.append((cfr, orig))

    rows.sort()
    with open(os.path.join(OUT, "rename_map.csv"), "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["cfr_name", "original_name"])
        w.writerows(rows)

    # sanity: how many originals did we account for?
    mapped_origs = set(o for _, o in rows)
    missing = originals - mapped_origs
    extra = mapped_origs - originals
    print(f"decompiled classes:        {len(rows)}")
    print(f"  explicitly renamed:      {renamed_count}")
    print(f"jar default-pkg classes:   {len(originals)}")
    print(f"originals covered by map:  {len(mapped_origs & originals)}")
    print(f"originals NOT in map:      {len(missing)}  (sample: {sorted(list(missing))[:10]})")
    print(f"map names not in jar:      {len(extra)}  (sample: {sorted(list(extra))[:10]})")
    print(f"written: {os.path.join(OUT, 'rename_map.csv')}")


if __name__ == "__main__":
    main()
