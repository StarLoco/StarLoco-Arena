# drive.ps1 — autonomous DofusArena 2.70 client driver.
#
# Boots our Go server + the real retail client (with the control agent injected),
# auto-logs-in, and lets you script UI actions + screenshots + client-model reads
# with zero human input. Run pieces of this from the harness, or use the helpers.
#
#   . .\drive.ps1            # dot-source to load helpers
#   Start-Arena              # server + client + login
#   Shot login.png          # screenshot to a file
#   Ctl '/roster'           # read client-side fighter model
#   Stop-Arena              # tear everything down

$ErrorActionPreference = 'SilentlyContinue'

$Global:ARENA = @{
    Game     = 'E:\Projets\DofusArena2-06\client\compiled\game'
    Java     = 'E:\Projets\DofusArena2-06\client\compiled\jre\bin\java.exe'
    Natives  = 'E:\Projets\DofusArena2-06\client\compiled\natives\win32\x86'
    Agent    = 'E:\Projets\DofusArena2-06\client\control-agent\control-agent.jar'
    ServerEx = "$env:TEMP\arena-server.exe"
    ServerWd = 'E:\Projets\DofusArena2-06\server'
    Port     = 8099
    ShotDir  = 'C:\Users\flore\AppData\Local\Temp\opencode'
    Srv      = $null
    Cli      = $null
}

function Ctl([string]$path) {
    try { (Invoke-WebRequest -Uri "http://127.0.0.1:$($ARENA.Port)$path" -TimeoutSec 15 -UseBasicParsing).Content }
    catch { "ERR $path : $_" }
}

function Shot([string]$name) {
    $out = Join-Path $ARENA.ShotDir $name
    try { Invoke-WebRequest -Uri "http://127.0.0.1:$($ARENA.Port)/screenshot" -TimeoutSec 15 -UseBasicParsing -OutFile $out; $out }
    catch { "screenshot failed: $_" }
}

function Free-Port5555 {
    $c = Get-NetTCPConnection -LocalPort 5555 -ErrorAction SilentlyContinue
    if ($c) { $c.OwningProcess | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue } }
}

function Build-Server {
    Push-Location $ARENA.ServerWd
    go build -o $ARENA.ServerEx ./cmd/server 2>&1 | Out-Null
    Pop-Location
    Test-Path $ARENA.ServerEx
}

function Start-Arena([string]$user = 'locos975', [string]$pass = 'azerty', [int]$loginWait = 20) {
    Free-Port5555
    Get-Process java, arena-server -ErrorAction SilentlyContinue | Stop-Process -Force
    Start-Sleep -Seconds 1
    $ARENA.Srv = Start-Process -FilePath $ARENA.ServerEx -ArgumentList '--config', 'configs/config.sqlite.yaml' `
        -WorkingDirectory $ARENA.ServerWd -PassThru -RedirectStandardError "$env:TEMP\arena_srv.log" -WindowStyle Hidden
    Start-Sleep -Seconds 3
    $ARENA.Cli = Start-Process -FilePath $ARENA.Java -ArgumentList `
        "-javaagent:`"$($ARENA.Agent)`"=port=$($ARENA.Port)", '-Xmx768m', "-Djava.library.path=`"$($ARENA.Natives)`"", `
        '-cp', 'core.jar', 'com.ankamagames.dofusarena.client.DofusArenaClient' `
        -WorkingDirectory $ARENA.Game -PassThru -RedirectStandardOutput "$env:TEMP\arena_cli.log"
    Write-Host "server=$($ARENA.Srv.Id) client=$($ARENA.Cli.Id); waiting ${loginWait}s for login screen..."
    Start-Sleep -Seconds $loginWait
    Write-Host "health: $(Ctl '/health')"
    if ($user) {
        Ctl '/focus' | Out-Null
        Ctl '/click?x=512&y=380' | Out-Null
        Ctl "/type?text=$user" | Out-Null
        Ctl '/key?name=TAB' | Out-Null
        Ctl "/type?text=$pass" | Out-Null
        Ctl '/key?name=ENTER' | Out-Null
        Write-Host "login submitted for $user"
    }
}

function Stop-Arena {
    if ($ARENA.Cli) { Stop-Process -Id $ARENA.Cli.Id -Force -ErrorAction SilentlyContinue }
    if ($ARENA.Srv) { Stop-Process -Id $ARENA.Srv.Id -Force -ErrorAction SilentlyContinue }
    Get-Process java, arena-server -ErrorAction SilentlyContinue | Stop-Process -Force
    Free-Port5555
}

function Server-Log { Get-Content "$env:TEMP\arena_srv.log" -ErrorAction SilentlyContinue }

Write-Host "drive.ps1 loaded. Helpers: Build-Server, Start-Arena, Ctl <path>, Shot <name>, Server-Log, Stop-Arena"
