<#
    run_pipeline.ps1 -- one-command DofusArena 2.70 deobfuscation remap.

    Stages:
      1. build_mapping.py   obf core.jar  ->  mappings/{deob.srg,deob.tiny,class_names.csv}
      2. SpecialSource      core.jar  + deob.srg  ->  build/core.deobf.jar   (readable bytecode)
      3. CFR                core.deobf.jar         ->  decompiled/core-src/   (readable source)

    The output jar is a drop-in replacement for the original core.jar: same 5059
    entries, valid bytecode, but every default-package class now has a unique,
    readable, collision-free name in package `deob`.

    Prereqreqs (all verified 2026-07):
      - JDK 1.8 at $JdkBin
      - Python 3 on PATH
      - tools/SpecialSource-*-shaded.jar   (Maven Central net.md-5:SpecialSource)
      - ../../tools/cfr.jar                 (CFR 0.152, already in repo)
    Vineflower (tools/vineflower-*.jar) gives more recompilable source but needs
    Java 11+; this box only has Java 8, so we decompile with CFR.
#>
param(
    [string]$JdkBin   = "C:\Program Files\Java\jdk1.8.0_202\bin",
    [string]$CoreJar  = "E:\Projets\DofusArena2-06\client\compiled\game\core.jar",
    [switch]$SkipDecompile
)

$ErrorActionPreference = "Stop"
$lab   = Split-Path -Parent $PSScriptRoot
$java  = Join-Path $JdkBin "java.exe"
$ss    = Get-ChildItem "$lab\tools\SpecialSource-*-shaded.jar" | Select-Object -First 1
$cfr   = "E:\Projets\DofusArena2-06\tools\cfr.jar"
$srg   = "$lab\mappings\deob.srg"
$obf   = "$lab\build\core.deobf.jar"
$src   = "$lab\decompiled\core-src"

Write-Host "[1/3] build_mapping.py ..." -ForegroundColor Cyan
python "$lab\scripts\build_mapping.py"
if (-not (Test-Path $srg)) { throw "mapping not produced: $srg" }

Write-Host "[2/3] SpecialSource remap ..." -ForegroundColor Cyan
New-Item -ItemType Directory -Path "$lab\build" -Force | Out-Null
$srgArgs = @("-m", $srg)
$mem = "$lab\mappings\deob_members.srg"
if (Test-Path $mem) { $srgArgs += @("-m", $mem); Write-Host "      + member map: deob_members.srg" }
& $java -jar $ss.FullName -i "$CoreJar" -o "$obf" @srgArgs --stable
if (-not (Test-Path $obf)) { throw "remap failed: $obf" }
$n = (& (Join-Path $JdkBin "jar.exe") -tf "$obf" | Measure-Object).Count
Write-Host ("      core.deobf.jar entries: {0}  ({1:N2} MB)" -f $n, ((Get-Item $obf).Length/1MB))

if ($SkipDecompile) { Write-Host "skip decompile"; return }

Write-Host "[3/3] CFR decompile ..." -ForegroundColor Cyan
Remove-Item "$src\*" -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path $src -Force | Out-Null
& $java -jar $cfr "$obf" --outputdir "$src" --silent true
$j = (Get-ChildItem "$src\deob" -Filter *.java -ErrorAction SilentlyContinue | Measure-Object).Count
Write-Host "      decompiled deob/*.java: $j" -ForegroundColor Green
Write-Host "done. readable source at: $src\deob" -ForegroundColor Green
