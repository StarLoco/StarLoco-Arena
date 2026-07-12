# Admin Console Commands

The in-game **admin console** lets an administrator type text commands that are sent to the
server, executed there, and answered with text lines printed back into the same console. It is
built on three opcodes:

| Opcode | Name | Direction | Purpose |
|---|---|---|---|
| 8193 | `CONSOLE_ADMIN_COMMAND` | client → server | carries the typed command string |
| 8194 | `CONSOLE_ADMIN_COMMAND_RESULT` | server → client | a `TRACE` / `LOG` / `ERROR` line rendered in the console |
| 8195 | `DEFAULT_RESULT` | server → client | a generic completion code (`0` = success) |

Server implementation: [`internal/dispatch/handlers_admin_console.go`](../internal/dispatch/handlers_admin_console.go)
and [`internal/dispatch/packets_admin_console.go`](../internal/dispatch/packets_admin_console.go).
Wire-format reference: [`docs/opcodes/00-implementation-status.md`](./opcodes/00-implementation-status.md).

> **Note on directions.** The legacy `OpCode.java` lists `8193` in its *Send* enum, but the
> decompiled client's `ConsoleAdminCommandMessage` is an `OutputOnlyProxyMessage` sent over the
> normal game socket, so `8193` is genuinely **client → server** (`RecvConsoleAdminCommand`).
> The separate SSL `AdminServerInstance` subsystem (its own `LoginMessage` / `PropertyQuery`
> protocol) is unrelated and out of scope.

---

## 1. Prerequisites

### The account must be an administrator

Admin console commands are gated by the account's `is_admin` flag (the same flag used by the GM
chat commands and the web portal). A non-admin gets an `ERROR` line and the command never runs.

The flag is **snapshotted at login**, so promote the account *before* connecting (or reconnect
after promoting).

Create an admin account with the seed CLI:

```
go run ./cmd/seedaccount -config configs/<your-config>.yaml -login myadmin -password secret -admin
```

(or create/flag one through the web admin portal — see [`docs/10-web-portal.md`](./10-web-portal.md)).

### No client-side configuration is required

The admin console works out of the box in the shipped client:

- The console command tree (including the `admin` command set) is compiled into `core.jar`
  and loaded unconditionally at startup.
- The `admin` command set is tagged `level="100"`, and the console's user level defaults to
  `127` (never lowered anywhere), so `127 >= 100` — the admin commands are always available.

---

## 2. Opening the console

Press **`Ctrl` + the console key** (the `` ` `` / `²` key just left of `1`, above `Tab`).

This runs the built-in `!/debug/console` shortcut (defined in the client's
`data/shortcuts.xml`), which toggles the `consoleDialog`. Press the same combination again to
hide it.

Inside the console prompt: **Enter** submits the current line, **↑ / ↓** cycle through history.

---

## 3. Command syntax (important)

The console navigates a **command tree using `/` as the path separator**, and each path segment
must be terminated with a `/`. Arguments come after the path, separated by spaces. Admin
commands live under the `admin/<subsystem>/` path.

**To run `STATUS`, type:**

```
/admin/game/STATUS
```

This is parsed as: navigate root → `admin` → `game`, then the remaining text `STATUS` is the
command string sent to the server (opcode 8193). Commands are **case-insensitive**, so
`/admin/game/status` works too.

### The subsystem prefix (`game`) is arbitrary here

The stock client exposes five admin subsystems: `proxy`, `worldmanager`, `connection`, `game`,
`chat`. Each only changes a `serverId` byte that **this server ignores** — the command string
that reaches the Go handler is identical. So all of these are equivalent:

```
/admin/game/STATUS
/admin/proxy/STATUS
/admin/worldmanager/STATUS
/admin/connection/STATUS
/admin/chat/STATUS
```

Use `/admin/game/...` by convention.

### Common mistakes

| What you type | Result | Why |
|---|---|---|
| `admin game ping` | `Commande 'admin game ping' invalide` | Spaces are **not** path separators — use `/`. |
| `/admin ping` | `Chemin admin invalide` | `/admin` is a single segment not ending in `/`, so it is treated as a flat command, not navigation. |
| `ping` | `Commande 'ping' invalide` | Missing the `/admin/<subsystem>/` path prefix. |
| `/admin/game/STATUS` | ✅ runs `STATUS` | Correct: `/`-separated path + command. |

### Prompt persistence and the `!` prefix

- After running `/admin/game/STATUS`, the console prompt **stays** at `/admin/game/>`. From
  there you can type just `STATUS`, `PING`, or `HELP` (no path needed) until you navigate away.
- Prefix a command with `!` (for example `!/admin/game/STATUS`) to run it from root **and reset**
  the prompt back afterward. This is the form the built-in keyboard shortcuts use.
- Type `..` to go back up one level.

---

## 4. Available commands

Each command replies with one or more lines in the console, then a `DEFAULT_RESULT` completion
code (`0` on success). Verbs are case-insensitive.

| Command | Example (from root) | Reply |
|---|---|---|
| `HELP` | `/admin/game/HELP` | Lists every available command with a one-line description (`LOG` lines). |
| `STATUS` | `/admin/game/STATUS` | `Server version <major>.<revision> build <build>` and `Online coaches: <n>` (`LOG` lines). |
| `PING` | `/admin/game/PING` | `pong` (a `TRACE` line) — confirms the console round-trip works. |

An unrecognized verb returns an `ERROR` line: `Unknown command: <verb>. Type HELP for the
command list.`

Adding new commands is a one-entry change to the `adminCommands` table in
[`handlers_admin_console.go`](../internal/dispatch/handlers_admin_console.go).

---

## 5. Wire format (reference)

- **`CONSOLE_ADMIN_COMMAND` (8193, in):** one 1-byte length-prefixed UTF-8 string — the command
  text. (The client also prepends a `serverId` byte as the frame's `architectureTarget`; the
  server ignores it.)
- **`CONSOLE_ADMIN_COMMAND_RESULT` (8194, out):** one type byte (`0 = TRACE`, `1 = LOG`,
  `2 = ERROR`) followed by a 2-byte length-prefixed UTF-8 message. The client routes it to
  `ConsoleManager.trace/log/err` accordingly.
- **`DEFAULT_RESULT` (8195, out):** a single big-endian `int32` result code (`0` = success).

---

## 6. Troubleshooting

- **`Commande '...' invalide`** — this is a *client-side* parse error; the command never left
  the client. Check the syntax in §3 (use `/`-separated paths, e.g. `/admin/game/STATUS`).
- **`Permission denied.`** — the account is not an administrator, or it was promoted *after*
  logging in. Set `is_admin` and reconnect (see §1).
- **Nothing happens at all** — you must be logged in to the world; the opcode is only handled on
  an authenticated session.
