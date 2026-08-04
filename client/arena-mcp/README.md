# arena-mcp — MCP server for autonomous live-client testing

An MCP server that lets the AI drive the **real DofusArena 2.70 retail client**
and the from-scratch Go server through clean tool calls, with **no human input**
and **without hijacking the desktop** (the client runs off-screen; input is
synthetic AWT, never the physical mouse/keyboard).

It's the orchestration layer on top of the Java control agent
(`../control-agent/`): opencode launches this server and keeps it alive, so the
client + Go server **persist across tool calls** (a plain shell can't do that —
it reaps background processes).

```
AI tool call  ->  arena-mcp (Node, this)  ->  control-agent HTTP (in client JVM)  ->  client
                        └─ owns: Go server process + client JVM process
```

## Tools

| Tool | What it does |
|---|---|
| `arena_up` | rebuild the Go server (optional) → start server + client(+agent) → wait until the login screen is interactive → move off-screen |
| `arena_login` | drive the login form (synthetic input), wait, report if it entered the world |
| `arena_screenshot` | return a **compressed JPEG** of what the client shows (off-screen capable). Defaults: `maxWidth 800, quality 72` → ~50–120 KB (vs ~2 MB PNG). `maxWidth` is the vision-token lever (tokens ≈ w·h/750); pass `maxWidth 1016 quality 85` for fine detail |
| `arena_click` / `arena_type` / `arena_key` | synthetic input (no physical mouse/keyboard) |
| `arena_eval` / `arena_roster` | read client-side model state via reflection |
| `arena_server_log` / `arena_client_log` | tail the server log / the client error-oracle log |
| `arena_status` / `arena_down` | lifecycle |

## Setup

```
cd client/arena-mcp
npm install
```

Registered in the project `opencode.json`:

```json
{
  "$schema": "https://opencode.ai/config.json",
  "mcp": {
    "arena": {
      "type": "local",
      "command": ["node", "E:\\Projets\\DofusArena2-06\\client\\arena-mcp\\server.mjs"],
      "enabled": true
    }
  }
}
```

**Restart opencode** after registering, then the `arena_*` tools are available.

## Typical dev loop

```
arena_up { rebuild: true }         # picks up your server code change
arena_login {}                     # -> "entered world coach=Loov"
arena_click { x, y }               # navigate a panel
arena_screenshot {}                # SEE what the client renders
arena_roster {}                    # ASSERT the client model matches the server
arena_client_log { filter: "ERROR|Exception|Erreur" }   # did anything break?
arena_down {}
```

## Notes

- Paths are hard-coded in `server.mjs` (`const P = {...}`) — stable on this
  machine; edit there if the install moves.
- The Go server is rebuilt to a temp exe on `arena_up { rebuild: true }`, so your
  latest server changes are always what the client talks to.
- `selftest.mjs` drives the full lifecycle through the real MCP client transport
  (`node selftest.mjs`) — a good smoke test after changing the agent or server.
- Test fixtures: account `locos975` / `azerty`, coach `Loov`. Login field is at
  canvas coords account (508,353), password (508,411).
- Coordinates are **canvas-relative** (the GLCanvas `pG` is 1016×741, inset below
  the title bar) — not window coordinates.
