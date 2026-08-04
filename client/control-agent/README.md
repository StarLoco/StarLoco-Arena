# Control Agent — autonomous live-client testing

A tiny **Java agent injected into the real DofusArena 2.70 client** (`-javaagent`)
that exposes an HTTP control channel so the server developer (human or AI) can
drive the retail client and observe what it renders **with zero human input** —
and **without hijacking the desktop**.

> Most of the time you won't call this HTTP API directly — the **`arena-mcp`**
> server (`../arena-mcp/`) wraps it as MCP tools. This doc is the low-level
> reference.

**Non-intrusive by design:**
- **Input** is delivered by invoking the GLCanvas's own AWT listeners directly
  (not `java.awt.Robot`), so the physical mouse/keyboard are never touched and no
  window focus is stolen.
- **Screenshots** read the rendered component (GL back-buffer / `paint()`), so
  the window can be **off-screen** and capture still works.
- The window is parked **off the visible desktop**, so it never occupies your
  screen.

This is the loop that lets us verify a server change actually works on the real
client, instead of guessing from the wire.

## Why this exists

The 2.70 client is a JOGL/OpenGL Swing app with a bundled JRE 1.6. It has no
headless mode, no scripting API, and no built-in autologin. But every bug we hit
(fighter blob decode, `arrayList.get(0)` crashes, zeroed fatigue, the ping
keepalive desync) is observable either as a rendered pixel or as client-side
model state. The agent gives us both, programmatically.

## What it can do

`GET http://127.0.0.1:8099/<endpoint>`:

| Endpoint | Purpose |
|---|---|
| `/health` | `ok frames=1 canvas=true keyL=1 ready=true glHooked=false offscreen=true` — `ready=true` = login screen is interactive |
| `/screenshot` | PNG of the rendered client (GL/`paint()`; works off-screen) |
| `/offscreen?on=1` | Park the window off the visible desktop (or `on=0` to restore) |
| `/type?text=locos975` | Type a literal string (synthetic KEY_TYPED — layout-independent) |
| `/key?name=ENTER` | Press ENTER / TAB / ESCAPE / SPACE / BACKSPACE |
| `/click?x=508&y=353` | Click at **canvas-relative** coordinates |
| `/move?x=&y=` | Move the mouse (synthetic) |
| `/login?user=&pass=` | click account → type user → click password → type pass → ENTER |
| `/roster` | Read the client's fighter model: `adY.atu()` size + isEmpty |
| `/eval?class=adY&method=atu&chain=isEmpty` | Generic reflection: call a static no-arg method, then chain no-arg calls |
| `/tree` | Dump the AWT component hierarchy (class names + listener counts) |

The agent modifies **no** client code — it only starts a daemon HTTP thread in
`premain` and uses `java.awt.Robot` + reflection at runtime.

## Build

Requires a JDK (to compile 1.6-target bytecode). The client's bundled JRE is
1.6.0_07 and includes `com.sun.net.httpserver`.

```powershell
$jdk = "C:\Program Files\Java\jdk1.8.0_202\bin"
$rt  = "E:\Projets\DofusArena2-06\client\compiled\jre\lib\rt.jar"
$base = "E:\Projets\DofusArena2-06\client\control-agent"
& "$jdk\javac.exe" -source 1.6 -target 1.6 -bootclasspath $rt -d "$base\build" "$base\src\arena\agent\ControlAgent.java"
& "$jdk\jar.exe" cfm "$base\control-agent.jar" "$base\manifest.txt" -C "$base\build" .
```

## Launch the client with the agent

Three things are mandatory or the client won't reach the login screen:
- `-javaagent:control-agent.jar=port=8099` — inject the agent.
- `-Xmx768m` — the default heap OOMs on the 139 MB `gui.jar`.
- `-Djava.library.path=<natives>` — else `UnsatisfiedLinkError: no jogl`.

```powershell
$game    = "E:\Projets\DofusArena2-06\client\compiled\game"
$java    = "E:\Projets\DofusArena2-06\client\compiled\jre\bin\java.exe"
$natives = "E:\Projets\DofusArena2-06\client\compiled\natives\win32\x86"
$agent   = "E:\Projets\DofusArena2-06\client\control-agent\control-agent.jar"

& $java "-javaagent:`"$agent`"=port=8099" -Xmx768m "-Djava.library.path=`"$natives`"" `
    -cp core.jar com.ankamagames.dofusarena.client.DofusArenaClient
# (run with -WorkingDirectory $game)
```

Main class: `com.ankamagames.dofusarena.client.DofusArenaClient`.
Server is auto-selected (single proxy `127.0.0.1:5555` in `config.properties`).

## The easy way: `drive.ps1`

Dot-source the driver for one-liners:

```powershell
. .\drive.ps1
Build-Server                 # go build ./cmd/server -> temp exe
Start-Arena                  # kill stragglers, start server + client, auto-login as locos975
Shot 'evolution.png'         # screenshot to the temp dir (I read it back as a PNG)
Ctl '/roster'                # -> adY.atu()=true isEmpty=false size=3
Server-Log                   # tail the server log
Stop-Arena                   # tear everything down + free port 5555
```

## Gotchas (learned the hard way)

- **Wait for `ready=true`** before driving input. The agent HTTP server starts in
  `premain`, long before the UI is interactive; typing/clicking earlier is lost.
- **Canvas coordinates, not window**: the GLCanvas (`pG`, 1016×741) is inset
  below the title bar. Login account field is `(508,353)`, password `(508,411)`.
- **First keystroke after focus is swallowed** by the xulor2 field — settle
  ~500 ms after a click before typing, or the first char of the login drops.
- **Stale server on port 5555**: kill it between runs
  (`Get-NetTCPConnection -LocalPort 5555`); `drive.ps1`/`arena-mcp` handle it.
- **PowerShell non-interactive**: `Invoke-WebRequest` needs `-UseBasicParsing`.
- The login fields are custom xulor2/OpenGL widgets (not Swing); input is
  delivered straight to the canvas's AWT listeners, so focus/screen position and
  keyboard layout no longer matter (the char rides on `KEY_TYPED`).

## Extending

The `/eval` endpoint is the power tool: it resolves `Class.forName(class)`,
invokes a static no-arg `method`, then chains no-arg calls from `chain`
(dot-separated). Add convenience readers (like `/roster`) in
`ControlAgent.java` for any client model you want to assert on
(`adY.atu()` = fighters, `xz_0.amc()` = bench, `bs_0.IF()` = teams, etc.).
