<#
    refresh_ai_names.ps1 -- regenerate the AI-naming layer, then re-apply it.

    The AI-naming loop is two-pass because it reads the decompiled tree:

      pass 1 : run_pipeline.ps1            (map -> remap -> decompile)  [needs a decompile to mine]
      refresh: extract_naming_signals.py   (decompiled tree -> naming_worklist.csv)
               propose_names.py            (worklist        -> ai_names.csv)
      pass 2 : run_pipeline.ps1            (build_mapping now folds ai_names.csv back in)

    This script does the "refresh" middle step, then re-runs the pipeline so the
    new names land in build/core.deobf.jar + decompiled/core-src.
    Run run_pipeline.ps1 at least once first (so decompiled/core-src exists).
#>
$lab = Split-Path -Parent $PSScriptRoot
$src = "$lab\decompiled\core-src\deob"
if (-not (Test-Path $src)) {
    Write-Host "decompiled tree missing - run run_pipeline.ps1 first." -ForegroundColor Yellow
    exit 1
}
Write-Host "[refresh] extract_naming_signals.py ..." -ForegroundColor Cyan
python "$lab\scripts\extract_naming_signals.py"
Write-Host "[refresh] propose_names.py ..." -ForegroundColor Cyan
python "$lab\scripts\propose_names.py"
Write-Host "[refresh] re-running pipeline to apply names ..." -ForegroundColor Cyan
& "$PSScriptRoot\run_pipeline.ps1"
