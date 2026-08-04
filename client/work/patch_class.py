#!/usr/bin/env python3
r"""
Surgical patch pipeline for the obfuscated DofusArena 2.70 core.jar.

Because CFR renamed the case-colliding obfuscated classes (e.g. original `fW`
-> `fw_1`, `So` -> `so_0`), a CFR-decompiled source file cannot be compiled
directly against the original jar: its symbols don't exist there. This tool
"re-obfuscates" the source names back to the ORIGINAL names using
analysis/rename_map.csv, compiles the single class against the original jars,
and injects the resulting .class back into a patched copy of core.jar.

Usage:
  python patch_class.py <cfr_class>            # dry-run: reobfuscate + compile only
  python patch_class.py <cfr_class> --inject   # also inject into patched jar
  python patch_class.py <cfr_class> --src EDITED.java --inject

Notes:
- Edit the CFR .java (in decompiled/core or a copy) using CFR names; this tool
  translates names for you. So you edit `so_0`, it compiles as `So`.
- The patched jar is written to work/patched/core.jar (original is never touched).
- Multiple case-variant .class files (fW/fw/Fw/FW) cannot coexist on Windows,
  so each class is compiled into an isolated temp dir and injected by exact
  internal jar path.
"""
import os, re, csv, sys, shutil, subprocess, argparse, tempfile, zipfile

BASE = r"E:\Projets\DofusArena2-06\client"
DEC = os.path.join(BASE, "decompiled", "core")
MAP = os.path.join(BASE, "analysis", "rename_map.csv")
JAR = os.path.join(BASE, "compiled", "game", "core.jar")
I18N = os.path.join(BASE, "compiled", "game", "contents", "i18n.jar")
LIBDIR = os.path.join(BASE, "compiled", "lib")
WORK = os.path.join(BASE, "work")
PATCHED_DIR = os.path.join(WORK, "patched")
JDK = r"C:\Program Files\Java\jdk1.8.0_202\bin"
JAVAC = os.path.join(JDK, "javac.exe")
JARBIN = os.path.join(JDK, "jar.exe")


def load_map():
    """cfr_name -> original_name (only entries that actually differ)."""
    m = {}
    with open(MAP, newline="", encoding="utf-8") as f:
        for row in csv.DictReader(f):
            if row["cfr_name"] != row["original_name"]:
                m[row["cfr_name"]] = row["original_name"]
    return m


def reobfuscate(src_text, name_map):
    """Replace CFR names with original names as whole-word tokens.

    Longest CFR names first so e.g. `aa_12` is handled before `aa`.
    Word boundaries prevent touching substrings inside longer identifiers.
    """
    for cfr in sorted(name_map, key=len, reverse=True):
        orig = name_map[cfr]
        src_text = re.sub(r"(?<![\w$])" + re.escape(cfr) + r"(?![\w$])",
                          orig, src_text)
    return src_text


def classpath():
    cp = [JAR, I18N]
    if os.path.isdir(LIBDIR):
        cp += [os.path.join(LIBDIR, j) for j in os.listdir(LIBDIR) if j.endswith(".jar")]
    return ";".join(p for p in cp if os.path.exists(p))


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("cfr_class", help="CFR class name, e.g. fw_1")
    ap.add_argument("--src", help="path to edited .java (defaults to decompiled/core/<cfr>.java)")
    ap.add_argument("--inject", action="store_true", help="inject compiled class into patched jar")
    args = ap.parse_args()

    name_map = load_map()
    cfr = args.cfr_class
    original = name_map.get(cfr, cfr)
    src_path = args.src or os.path.join(DEC, cfr + ".java")
    if not os.path.exists(src_path):
        sys.exit(f"source not found: {src_path}")

    with open(src_path, "r", encoding="utf-8", errors="replace") as f:
        text = f.read()

    reob = reobfuscate(text, name_map)

    # compile in an isolated temp dir (avoids case-collision on Windows FS)
    tmp = tempfile.mkdtemp(prefix="dapatch_")
    try:
        jsrc = os.path.join(tmp, original + ".java")
        with open(jsrc, "w", encoding="utf-8") as f:
            f.write(reob)
        outdir = os.path.join(tmp, "out")
        os.makedirs(outdir, exist_ok=True)
        cmd = [JAVAC, "-encoding", "UTF-8", "-cp", classpath(), "-d", outdir, jsrc]
        res = subprocess.run(cmd, capture_output=True, text=True)
        if res.returncode != 0:
            print("COMPILE FAILED for", original)
            print(res.stdout)
            print(res.stderr)
            sys.exit(1)
        classfile = os.path.join(outdir, original + ".class")
        if not os.path.exists(classfile):
            # class may have landed in a subdir if it declared a package; list
            produced = [p for p in os.listdir(outdir) if p.endswith(".class")]
            print("compiled OK but expected", original + ".class", "not found; produced:", produced)
            sys.exit(1)
        print(f"OK: {cfr}  ->  original '{original}'  compiled ({os.path.getsize(classfile)} bytes)")

        if args.inject:
            os.makedirs(PATCHED_DIR, exist_ok=True)
            patched_jar = os.path.join(PATCHED_DIR, "core.jar")
            if not os.path.exists(patched_jar):
                shutil.copy2(JAR, patched_jar)
                print("created patched jar copy:", patched_jar)
            inject(patched_jar, original + ".class", classfile)
            print(f"injected {original}.class into {patched_jar}")
    finally:
        shutil.rmtree(tmp, ignore_errors=True)


def inject(jar_path, entry_name, classfile):
    """Replace/add a single entry in the zip, preserving everything else."""
    tmpjar = jar_path + ".tmp"
    with zipfile.ZipFile(jar_path, "r") as zin, \
         zipfile.ZipFile(tmpjar, "w", zipfile.ZIP_DEFLATED) as zout:
        for item in zin.infolist():
            if item.filename == entry_name:
                continue  # drop old
            zout.writestr(item, zin.read(item.filename))
        with open(classfile, "rb") as f:
            zout.writestr(entry_name, f.read())
    os.replace(tmpjar, jar_path)


if __name__ == "__main__":
    main()
