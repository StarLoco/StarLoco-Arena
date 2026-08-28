#!/usr/bin/env node
// arena-mcp — an MCP server that drives the DofusArena 2.70 retail client + the
// from-scratch Go server for autonomous, non-intrusive live testing.
//
// It owns the process lifecycle (Go server + Java client with the control agent
// injected), so the running session survives across tool calls — unlike shelling
// out, where the caller's shell would reap background processes. Input is
// delivered to the client via direct AWT-listener invocation (no physical
// mouse/keyboard) and the window runs off-screen, so nothing hijacks the desktop.
//
// Layers:  MCP tools (this) -> control-agent HTTP (in the client JVM) -> client.

import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";
import { spawn, spawnSync } from "node:child_process";
import { readFileSync, writeFileSync, existsSync, mkdtempSync, openSync } from "node:fs";
import { tmpdir } from "node:os";
import { join } from "node:path";

// ---- fixed environment paths (stable on this machine) --------------------
const P = {
  game: "E:\\Projets\\DofusArena2-06\\client\\compiled\\game",
  java: "E:\\Projets\\DofusArena2-06\\client\\compiled\\jre\\bin\\java.exe",
  natives: "E:\\Projets\\DofusArena2-06\\client\\compiled\\natives\\win32\\x86",
  agentJar: "E:\\Projets\\DofusArena2-06\\client\\control-agent\\control-agent.jar",
  serverDir: "E:\\Projets\\DofusArena2-06\\server",
  clientLog: "E:\\Projets\\DofusArena2-06\\client\\compiled\\game\\output.log",
  mainClass: "com.ankamagames.dofusarena.client.DofusArenaClient",
  agentPort: 8099,
  userPrefs: "E:\\Projets\\DofusArena2-06\\client\\compiled\\game\\userPreferences.properties",
  clientConfig: "E:\\Projets\\DofusArena2-06\\client\\compiled\\game\\config.properties",
};

// ---- server targets -------------------------------------------------------
// game/config.properties declares the servers the login dialog offers:
//
//   proxyGroup_1=Localhost      proxyAddresses_1=127.0.0.1:5555
//   proxyGroup_2=ArenaReborn    proxyAddresses_2=game.arenareborn.com:443
//
// The client preselects one from the "lastServer" USER PREFERENCE, which is
// ZERO-based (yy_0 writes `NM.getIndex() - 1`, and DofusArenaClientInstance
// does `list.get(n)`). Do not confuse it with `lastProxyGroupIndex` in
// config.properties, which apN writes ONE-based - they are different keys and
// setting the wrong one silently connects you to the wrong server.
// bugReportURL is where the client's "Rapport de bug" dialog POSTs. The client
// appends "<language>/bug-report" itself (aOG.a), so this is a bare origin with
// a trailing slash. It is switched with the target so a report always lands on
// the server the bug happened on.
const TARGETS = {
  localhost: {
    prefIndex: 0, group: "Localhost", local: true,
    bugReportURL: "http://localhost/",
  },
  arenareborn: {
    prefIndex: 1, group: "ArenaReborn", local: false,
    bugReportURL: "https://arenareborn.com/",
  },
};

// selectServer points the client at a target before it launches. It rewrites
// only the one line, preserving the file's CRLF endings and every other
// preference (the client rewrites this file itself on exit, so clobbering it
// would lose the user's graphics and audio settings).
function selectServer(name) {
  const t = TARGETS[name];
  if (!t) throw new Error("unknown server target: " + name);

  const eol = /\r\n/.test(readFileSync(P.userPrefs, "latin1")) ? "\r\n" : "\n";
  let prefs = readFileSync(P.userPrefs, "latin1").split(/\r?\n/);
  let found = false;
  prefs = prefs.map((l) => {
    if (/^lastServer=/.test(l)) { found = true; return "lastServer=" + t.prefIndex; }
    return l;
  });
  if (!found) prefs.push("lastServer=" + t.prefIndex);
  writeFileSync(P.userPrefs, prefs.join(eol), "latin1");

  // Keep config.properties' 1-based cousin consistent, so a human opening the
  // file sees the same server the client will actually preselect, and point the
  // bug reporter at the same server.
  try {
    const raw = readFileSync(P.clientConfig, "latin1");
    const cfgEol = /\r\n/.test(raw) ? "\r\n" : "\n";
    const cfg = raw.split(/\r?\n/).map((l) => {
      if (/^lastProxyGroupIndex=/.test(l)) return "lastProxyGroupIndex=" + (t.prefIndex + 1);
      if (/^bugReportURL=/.test(l)) return "bugReportURL=" + t.bugReportURL;
      return l;
    });
    writeFileSync(P.clientConfig, cfg.join(cfgEol), "latin1");
  } catch {}

  return t;
}
const SERVER_EXE = join(tmpdir(), "arena-server.exe");
const SERVER_LOG = join(mkdtempSync(join(tmpdir(), "arena-mcp-")), "server.log");
const AGENT = `http://127.0.0.1:${P.agentPort}`;

// ---- process state --------------------------------------------------------
let srvProc = null;
let cliProc = null;

const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

function log(...a) {
  // MCP uses stdout for the protocol; logs must go to stderr.
  process.stderr.write("[arena-mcp] " + a.join(" ") + "\n");
}

async function agent(path, { timeout = 15000 } = {}) {
  const ctrl = new AbortController();
  const t = setTimeout(() => ctrl.abort(), timeout);
  try {
    const res = await fetch(AGENT + path, { signal: ctrl.signal });
    return res;
  } finally {
    clearTimeout(t);
  }
}

async function agentText(path, opts) {
  const res = await agent(path, opts);
  return await res.text();
}

async function agentReachable() {
  try {
    const r = await agent("/health", { timeout: 2000 });
    return r.ok;
  } catch {
    return false;
  }
}

function killPort5555() {
  // Kill any process holding the game port (stale server from a prior run).
  spawnSync("powershell", [
    "-NoProfile", "-Command",
    "$c=Get-NetTCPConnection -LocalPort 5555 -ErrorAction SilentlyContinue; if($c){$c.OwningProcess|ForEach-Object{Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue}}",
  ], { windowsHide: true });
}

function killChildren() {
  for (const p of [cliProc, srvProc]) {
    if (p && !p.killed) {
      try { spawnSync("taskkill", ["/PID", String(p.pid), "/T", "/F"], { windowsHide: true }); } catch {}
    }
  }
  cliProc = null;
  srvProc = null;
}

function buildServer() {
  const r = spawnSync("go", ["build", "-o", SERVER_EXE, "./cmd/server"], {
    cwd: P.serverDir, windowsHide: true, encoding: "utf8", timeout: 240000,
  });
  if (r.status !== 0) {
    return { ok: false, out: (r.stdout || "") + (r.stderr || "") };
  }
  return { ok: true };
}

function tail(file, filter, lines = 40) {
  if (!existsSync(file)) return "(no log yet)";
  let text;
  try { text = readFileSync(file, "utf8"); } catch { return "(unreadable)"; }
  let arr = text.split(/\r?\n/).filter(Boolean);
  if (filter) {
    const re = new RegExp(filter, "i");
    arr = arr.filter((l) => re.test(l));
  }
  return arr.slice(-lines).join("\n") || "(no matching lines)";
}

// ---- MCP server -----------------------------------------------------------
const server = new McpServer({ name: "arena", version: "0.1.0" });

server.tool(
  "arena_up",
  "Boot the autonomous test session: (optionally rebuild and) start the Go server + the retail client with the control agent injected, wait for the login screen, and move the client window off-screen. Non-intrusive: no physical mouse/keyboard, window off the visible desktop.",
  {
    rebuild: z.boolean().default(true).describe("Rebuild the Go server binary from source first (picks up your code changes). Ignored when server='arenareborn'."),
    waitSeconds: z.number().default(22).describe("Seconds to wait for the client to reach the login screen."),
    server: z.enum(["localhost", "arenareborn"]).default("localhost").describe("Which server the client connects to. 'localhost' builds and starts the local Go server (127.0.0.1:5555). 'arenareborn' starts NO local server and points the client at the live production server (game.arenareborn.com:443)."),
  },
  async ({ rebuild, waitSeconds, server: target }) => {
    killChildren();
    await sleep(800);

    const t = selectServer(target);

    // Only the local target owns a Go server. Pointing the client at production
    // must NOT build, start or kill anything locally - and must not free port
    // 5555, which would kill a dev server the user is deliberately running.
    if (t.local) {
      killPort5555();
      await sleep(400);

      if (rebuild || !existsSync(SERVER_EXE)) {
        const b = buildServer();
        if (!b.ok) return { content: [{ type: "text", text: "SERVER BUILD FAILED:\n" + b.out }] };
      }

      // Start the Go server (log to a file we can tail).
      writeFileSync(SERVER_LOG, "");
      const srvFd = openSync(SERVER_LOG, "a");
      srvProc = spawn(SERVER_EXE, ["--config", "configs/config.sqlite.yaml"], {
        cwd: P.serverDir, windowsHide: true, stdio: ["ignore", srvFd, srvFd],
      });
      await sleep(2500);
    } else {
      // No local server: make that explicit in the log the tools tail, so an
      // empty server log reads as "remote target" rather than "server crashed".
      writeFileSync(SERVER_LOG, "(no local server: client targets " + t.group + ")\n");
    }

    // Truncate the client error log so this session is isolated.
    try { writeFileSync(P.clientLog, ""); } catch {}

    // Start the client with the agent, JOGL natives and a real heap.
    //
    // stdout+stderr are captured into clientLog. The client's log4j has no file
    // appender configured, so everything it reports -- decode failures, and the
    // stack traces from any exception thrown inside a UI action -- goes to the
    // console. This used to be `stdio: "ignore"`, which discarded all of it and
    // left arena_client_log permanently empty, so a client-side exception was
    // invisible and looked exactly like "the server sent nothing".
    const cliFd = openSync(P.clientLog, "a");
    cliProc = spawn(P.java, [
      `-javaagent:${P.agentJar}=port=${P.agentPort}`,
      "-Xmx768m",
      `-Djava.library.path=${P.natives}`,
      "-cp", "core.jar", P.mainClass,
    ], { cwd: P.game, windowsHide: true, stdio: ["ignore", cliFd, cliFd] });

    // Wait for the login screen to be INTERACTIVE (health ready=true), not just
    // for the agent HTTP server to answer — the agent starts early in premain,
    // long before the UI attaches its input handler to the GLCanvas.
    const deadline = Date.now() + waitSeconds * 1000;
    let ready = false;
    while (Date.now() < deadline) {
      try {
        const h = await agentText("/health", { timeout: 2000 });
        if (/ready=true/.test(h)) { ready = true; break; }
      } catch {}
      await sleep(1000);
    }
    if (!ready) {
      return { content: [{ type: "text", text: "Client UI not interactive within " + waitSeconds + "s. Server log:\n" + tail(SERVER_LOG, null, 15) }] };
    }
    await sleep(1500); // small settle after the UI reports ready
    // Move off-screen so it doesn't occupy the user's desktop.
    try { await agentText("/offscreen?on=1"); } catch {}
    const health = await agentText("/health");
    const where = t.local
      ? "target: Localhost (127.0.0.1:5555, local Go server started)"
      : "target: ArenaReborn (game.arenareborn.com:443, LIVE - no local server started)";
    return { content: [{ type: "text", text: "Session up (off-screen, non-intrusive).\n" + where + "\nagent: " + health + "\nserver: " + tail(SERVER_LOG, "listening|starting|ERROR|no local server", 5) }] };
  }
);

server.tool(
  "arena_login",
  "Log the client in and wait for it to enter the world. Drives the login form via synthetic AWT events (no physical input).",
  {
    user: z.string().default("locos975"),
    pass: z.string().default("azerty"),
  },
  async ({ user, pass }) => {
    await agentText(`/login?user=${encodeURIComponent(user)}&pass=${encodeURIComponent(pass)}`, { timeout: 20000 });
    await sleep(8000);
    // With server='arenareborn' there is no local Go server, so this tail is a
    // placeholder rather than evidence. Say so instead of returning a blank
    // "server:" line that reads like the login silently failed - the real log
    // is on the production host (docker compose logs arena).
    const remote = /no local server/.test(tail(SERVER_LOG, null, 3));
    const srv = remote
      ? "(remote target - server-side log is on the host: docker compose logs arena)"
      : tail(SERVER_LOG, "auth attempt|entered world|no coach|prompting", 8);
    const cli = tail(P.clientLog, "ERROR|Exception|pathfind|refus|Invalid", 6);
    return { content: [{ type: "text", text: "login submitted.\nserver: " + srv + "\nclient errors: " + cli }] };
  }
);

server.tool(
  "arena_screenshot",
  "Capture what the client is showing (off-screen window). Returns a compressed image. Vision-token cost is resolution-based, so `maxWidth` is the real token lever; JPEG keeps the payload tiny. Defaults (maxWidth 800, JPEG q72) are readable and cheap; pass maxWidth 1016 + quality 85 for fine detail, or a smaller maxWidth for a cheap overview.",
  {
    maxWidth: z.number().default(800).describe("Downscale so width <= this (0 = native 1016). Lower = fewer tokens."),
    quality: z.number().default(72).describe("JPEG quality 1..100."),
    format: z.enum(["jpg", "png"]).default("jpg"),
  },
  async ({ maxWidth, quality, format }) => {
    const res = await agent(`/screenshot?fmt=${format}&q=${quality}&maxw=${maxWidth}`, { timeout: 15000 });
    const buf = Buffer.from(await res.arrayBuffer());
    const mimeType = format === "png" ? "image/png" : "image/jpeg";
    return { content: [{ type: "image", data: buf.toString("base64"), mimeType }] };
  }
);

server.tool(
  "arena_click",
  "Click at canvas-relative coordinates (the GLCanvas is 1016x741). Synthetic — no physical mouse.",
  { x: z.number(), y: z.number() },
  async ({ x, y }) => ({ content: [{ type: "text", text: await agentText(`/click?x=${x}&y=${y}`) }] })
);

server.tool(
  "arena_doubleclick",
  "Double-click at canvas-relative coordinates (the GLCanvas is 1016x741). Needed to ACTIVATE things a single click only highlights - interactive world elements (NPCs, Zaaps), list rows, inventory cards - because the client tests MouseEvent.getClickCount()==2. Two arena_click calls do NOT substitute. Synthetic - no physical mouse.",
  { x: z.number(), y: z.number() },
  async ({ x, y }) => ({ content: [{ type: "text", text: await agentText(`/doubleclick?x=${x}&y=${y}`) }] })
);

server.tool(
  "arena_type",
  "Type a literal string into the focused client field (synthetic key events).",
  { text: z.string() },
  async ({ text }) => ({ content: [{ type: "text", text: await agentText(`/type?text=${encodeURIComponent(text)}`) }] })
);

server.tool(
  "arena_drag",
  "Drag from (x1,y1) to (x2,y2) in canvas coords (1016x741) — press, dragged steps, release. Use to drag a fighter card from the pool onto a team slot. Synthetic, no physical mouse.",
  { x1: z.number(), y1: z.number(), x2: z.number(), y2: z.number() },
  async ({ x1, y1, x2, y2 }) => ({ content: [{ type: "text", text: await agentText(`/drag?x1=${x1}&y1=${y1}&x2=${x2}&y2=${y2}`) }] })
);

server.tool(
  "arena_key",
  "Press a named key: ENTER, TAB, ESCAPE, SPACE, BACKSPACE.",
  { name: z.string() },
  async ({ name }) => ({ content: [{ type: "text", text: await agentText(`/key?name=${encodeURIComponent(name)}`) }] })
);

server.tool(
  "arena_eval",
  "Read client-side model state via reflection: call a static no-arg method, then chain no-arg calls. E.g. class=adY method=atu chain=isEmpty.",
  { class: z.string(), method: z.string(), chain: z.string().optional() },
  async ({ class: cls, method, chain }) => {
    const q = `/eval?class=${encodeURIComponent(cls)}&method=${encodeURIComponent(method)}` + (chain ? `&chain=${encodeURIComponent(chain)}` : "");
    return { content: [{ type: "text", text: await agentText(q) }] };
  }
);

server.tool(
  "arena_roster",
  "Read the client's fighter-roster model (adY.atu()): size + isEmpty. Use to assert the client actually holds the fighters the server sent.",
  {},
  async () => ({ content: [{ type: "text", text: await agentText("/roster") }] })
);

server.tool(
  "arena_server_log",
  "Tail the Go server log (what the server saw). Optional case-insensitive regex filter.",
  { filter: z.string().optional(), lines: z.number().default(40) },
  async ({ filter, lines }) => ({ content: [{ type: "text", text: tail(SERVER_LOG, filter, lines) }] })
);

server.tool(
  "arena_client_log",
  "Tail the client's error log (output.log) — the oracle for decode failures, UI crashes, and protocol desyncs. Optional regex filter.",
  { filter: z.string().optional(), lines: z.number().default(40) },
  async ({ filter, lines }) => ({ content: [{ type: "text", text: tail(P.clientLog, filter, lines) }] })
);

server.tool(
  "arena_status",
  "Report whether the server/client processes are alive and the agent is reachable.",
  {},
  async () => {
    const reachable = await agentReachable();
    const health = reachable ? await agentText("/health").catch(() => "?") : "(unreachable)";
    return { content: [{ type: "text", text: `server=${srvProc && !srvProc.killed ? "up" : "down"} client=${cliProc && !cliProc.killed ? "up" : "down"} agent=${health}` }] };
  }
);

server.tool(
  "arena_down",
  "Tear down the session: kill the client + server and free the game port.",
  {},
  async () => {
    killChildren();
    killPort5555();
    return { content: [{ type: "text", text: "session torn down." }] };
  }
);

process.on("exit", () => { killChildren(); });
process.on("SIGINT", () => { killChildren(); process.exit(0); });
process.on("SIGTERM", () => { killChildren(); process.exit(0); });

const transport = new StdioServerTransport();
await server.connect(transport);
log("arena-mcp ready");
