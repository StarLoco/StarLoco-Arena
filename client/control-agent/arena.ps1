# Standalone driver for the retail DofusArena 2.70 client + Go server.
# Replicates what client/arena-mcp/server.mjs does, but callable from the
# shell so it does not depend on the MCP layer being available.
#
#   .\arena.ps1 up [-Rebuild]   boot server + client (client window off-screen)
#   .\arena.ps1 login
#   .\arena.ps1 shot <file> [maxw] [q]
#   .\arena.ps1 agent <path>    raw GET on the control agent
#   .\arena.ps1 c2s <opcode> <arch> <hex>
#   .\arena.ps1 srvlog [n] [filter]
#   .\arena.ps1 clilog [n] [filter]
#   .\arena.ps1 down

param(
  [Parameter(Position = 0)][string]$Cmd = "status",
  [Parameter(Position = 1)][string]$A1,
  [Parameter(Position = 2)][string]$A2,
  [Parameter(Position = 3)][string]$A3,
  [switch]$Rebuild
)

$ErrorActionPreference = "Continue"

$P = @{
  Game      = "E:\Projets\DofusArena2-06\client\compiled\game"
  Java      = "E:\Projets\DofusArena2-06\client\compiled\jre\bin\java.exe"
  Natives   = "E:\Projets\DofusArena2-06\client\compiled\natives\win32\x86"
  AgentJar  = "E:\Projets\DofusArena2-06\client\control-agent\control-agent.jar"
  ServerDir = "E:\Projets\DofusArena2-06\server"
  ClientLog = "E:\Projets\DofusArena2-06\client\compiled\game\output.log"
  MainClass = "com.ankamagames.dofusarena.client.DofusArenaClient"
  AgentPort = 8099
}
$ServerExe = Join-Path $env:TEMP "arena-server.exe"
$ServerLog = Join-Path $env:TEMP "arena-server.log"
$Agent     = "http://127.0.0.1:$($P.AgentPort)"
$Dev       = "http://127.0.0.1:5599"

function Get-Agent([string]$Path, [int]$TimeoutSec = 15) {
  try { (Invoke-WebRequest -Uri ($Agent + $Path) -UseBasicParsing -TimeoutSec $TimeoutSec).Content }
  catch { "AGENT-ERR $($_.Exception.Message)" }
}

function Stop-Port([int]$Port) {
  $c = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue
  if ($c) { $c.OwningProcess | Sort-Object -Unique | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue } }
}

function Get-Tail([string]$File, [int]$N, [string]$Filter) {
  if (-not (Test-Path -LiteralPath $File)) { return "(no file $File)" }
  $lines = Get-Content -LiteralPath $File -Tail 4000 -ErrorAction SilentlyContinue
  if ($Filter) { $lines = $lines | Where-Object { $_ -match $Filter } }
  ($lines | Select-Object -Last $N) -join "`n"
}

switch ($Cmd) {

  "up" {
    Stop-Port 5555; Stop-Port 8099
    Get-Process -Name "arena-server" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
    if ($Rebuild -or -not (Test-Path -LiteralPath $ServerExe)) {
      Write-Output "building server..."
      Push-Location $P.ServerDir
      $b = & go build -o $ServerExe ./cmd/server 2>&1 | Out-String
      Pop-Location
      if ($LASTEXITCODE -ne 0) { Write-Output "SERVER BUILD FAILED:`n$b"; exit 1 }
    }
    Set-Content -LiteralPath $ServerLog -Value "" -Encoding utf8
    Start-Process -FilePath $ServerExe -ArgumentList "--config", "configs/config.sqlite.yaml" `
      -WorkingDirectory $P.ServerDir -WindowStyle Hidden `
      -RedirectStandardOutput $ServerLog -RedirectStandardError "$ServerLog.err"
    Start-Sleep -Seconds 3
    try { Set-Content -LiteralPath $P.ClientLog -Value "" -Encoding utf8 } catch {}
    Start-Process -FilePath $P.Java -WorkingDirectory $P.Game -WindowStyle Minimized -ArgumentList @(
      "-javaagent:$($P.AgentJar)=port=$($P.AgentPort)",
      "-Xmx768m",
      "-Djava.library.path=$($P.Natives)",
      "-cp", "core.jar", $P.MainClass
    )
    $deadline = (Get-Date).AddSeconds(90)
    $ready = $false
    while ((Get-Date) -lt $deadline) {
      $h = Get-Agent "/health" 3
      if ($h -match "ready=true") { $ready = $true; break }
      Start-Sleep -Seconds 2
    }
    if (-not $ready) { Write-Output "client not interactive in 90s`n$(Get-Tail $ServerLog 15 $null)"; exit 1 }
    Start-Sleep -Seconds 2
    Get-Agent "/offscreen?on=1" | Out-Null
    Write-Output "UP. agent: $(Get-Agent '/health')"
    Write-Output "server: $(Get-Tail $ServerLog 6 'listening|starting|DEV')"
  }

  "login" {
    $u = if ($A1) { $A1 } else { "locos975" }
    $p = if ($A2) { $A2 } else { "azerty" }
    Get-Agent "/login?user=$u&pass=$p" 25 | Out-Null
    Start-Sleep -Seconds 9
    Write-Output (Get-Tail $ServerLog 10 "auth attempt|entered world|no coach|prompting")
  }

  "shot" {
    $file = if ($A1) { $A1 } else { Join-Path $env:TEMP "arena-shot.jpg" }
    $maxw = if ($A2) { $A2 } else { "900" }
    $q    = if ($A3) { $A3 } else { "75" }
    try {
      Invoke-WebRequest -Uri "$Agent/screenshot?fmt=jpg&q=$q&maxw=$maxw" -UseBasicParsing -TimeoutSec 25 -OutFile $file
      Write-Output "saved $file ($((Get-Item $file).Length) bytes)"
    } catch { Write-Output "SHOT-ERR $($_.Exception.Message)" }
  }

  "agent" { Write-Output (Get-Agent $A1 25) }

  "c2s" {
    try {
      $u = "$Dev/c2s?opcode=$A1&arch=$A2&hex=$A3"
      Write-Output (Invoke-WebRequest -Uri $u -UseBasicParsing -TimeoutSec 15).Content
    } catch { Write-Output "C2S-ERR $($_.Exception.Message)" }
  }

  "dev" {
    try { Write-Output (Invoke-WebRequest -Uri "$Dev$A1" -UseBasicParsing -TimeoutSec 20).Content }
    catch { Write-Output "DEV-ERR $($_.Exception.Message)" }
  }

  "srvlog" { Write-Output (Get-Tail $ServerLog $(if ($A1) { [int]$A1 } else { 40 }) $A2) }
  "clilog" { Write-Output (Get-Tail $P.ClientLog $(if ($A1) { [int]$A1 } else { 40 }) $A2) }

  "down" {
    Get-Process -Name "arena-server" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
    Get-Process -Name "java" -ErrorAction SilentlyContinue |
      Where-Object { $_.Path -like "*DofusArena2-06*" } | Stop-Process -Force -ErrorAction SilentlyContinue
    Stop-Port 5555; Stop-Port 8099
    Write-Output "torn down."
  }

  default {
    $h = Get-Agent "/health" 3
    Write-Output "agent: $h"
  }
}
