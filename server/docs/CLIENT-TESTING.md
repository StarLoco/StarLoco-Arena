# E2E fight-test flakiness — diagnosis and current state

The three full-fight e2e tests (`TestCombatSpellDamage`, `TestFighterMoveInFight`,
`TestPlacementMove`) used to fail *rotating* — a different one each run — on a busy
machine. Two real causes were found and fixed; a residual remains.

**Cause 1 — the turn clock was 5× the test's patience.** Production `turnClock` is 30s
while the tests wait 15s for a turn, so a missed window was a GUARANTEED timeout rather
than a slow pass: the next `FIGHTER_TURN_BEGIN` cannot arrive until the current fighter's
clock expires. `game.SetTurnClockForTest` now lets the e2e harness shrink it to 6s.

**Cause 2 — the collect loop swallowed the next turn.** `TestCombatSpellDamage`'s frame
collector documented itself as running "until the flush barrier **or the next turn
begins**", but its switch had no case for `FIGHTER_TURN_BEGIN` — so under load it consumed
that frame and the following `WaitForTurn` blocked for an entire extra turn. It now keeps
the frame (`pendingTurn`) for the next attempt.

**Result:** 3-of-3 runs failing → 1-of-4, on the same loaded machine (~53% background CPU
from unrelated apps).

**Cause 3 — the real one: the tests assumed "client A == side 0" (B-064).** Causes 1 and 2
were genuine improvements that did not touch the actual bug, which is why the failure kept
returning. Side 0 is whoever reaches the **matchmaker queue first** (`Matchmaker.Search`
pairs the arriving searcher with the one already queued; `buildFightTeam(pm.a, 0)`), and
`startFightForCombat` fires both searches with no happens-before — so under load B can win
and A becomes side 1. The tests then acted with the *opponent's* fighter, which the server
drops silently (`caster.CoachID != cid`), and skipped their own.

The tests are now **side-agnostic**: they act on every turn offered using that fighter's own
side-appropriate cells, prove ownership by the echo where one exists, and assert relative to
the caster's side (`fighterSide`). `isTeamAFighter` is gone.

**Two lessons worth keeping:**
1. *Make the flake deterministic before theorising.* Pinning 23 of 24 cores with busy loops
   turned "~1 in 4" into "4 in 4", which made the whole investigation cheap. Any timing bug
   in this suite should start here.
2. *Instrument the refusal, don't infer it.* Every guard in the cast path returns silently.
   One temporary `Log.Error` on each branch named the cause on the first run
   (`coachMismatch=true`, `notCurrentTurn=false`) and killed the timing hypothesis outright.

**Turn clock.** Now **12s** (was 6s). It must be long enough to act inside your own turn on
a loaded machine — the server silently refuses actions from a fighter whose turn expired —
and short enough that waiting through a turn nobody ends still fits a `WaitForTurn` budget.
A test needing fast rotation should end its *other* client's turns explicitly (as
`TestCombatSpellDamage` does) rather than shrink the clock.

**Rule of thumb when this suite goes red:** check whether the failing test's `Deps` field
is even wired before blaming a gameplay change. `testServerWithDeps` builds `game.Deps`
with **nil** `Cards`, `Spells`, `FighterCards`, `Summonings`, `CardSets`, `Events` and
`FightMaps`, so most data-layer work is provably inert in e2e. A *varying* failing test is
load; a *consistent* one is a regression.

---

# Autonomous live-client testing loop

How the server is validated against the **real retail 2.70 client** with no human
in the loop. This closes the gap that byte-audits and scripted wire tests can't:
*does the change actually work in the client the player runs?*

> **Button coordinates & UI flows:** see [`CLIENT-UI-MAP.md`](./CLIENT-UI-MAP.md)
> — verified canvas click targets so you don't re-find them by screenshot.

## The three oracles

A server change is validated against three independent signals, cheapest first:

1. **Go tests** (`go test ./...`, `go test -race ./...`) — unit + scripted-wire
   e2e. Fast, deterministic, catch regressions. Always run these first.
2. **Client error log** — the retail client writes `output.log` (log4j). It
   reports decode failures (`Erreur à la désérialisation`), UI crashes
   (`IndexOutOfBoundsException`), and protocol desyncs (the ping keepalive
   `reply number is low`). Truncate it, run the scenario, grep for `ERROR` /
   `Exception` / `Erreur`. Found B-014, would have flagged B-013's crash path.
3. **Screenshots + client model state** — the control agent (see
   `client/control-agent/README.md`) drives the client and returns PNGs +
   reflection reads of client-side models. This is how we confirm a panel
   actually renders what we sent (B-012, B-013).

## The loop — via the `arena` MCP tools (preferred)

The `arena-mcp` server (`client/arena-mcp/`, registered in
`opencode.json`) exposes the whole loop as tool calls. It runs the client
**off-screen** with **synthetic input** — it never touches the physical
mouse/keyboard or the visible desktop — and keeps the server+client alive across
calls.

```
arena_up { rebuild: true }     # rebuild server + start server/client, wait until interactive, go off-screen
arena_login {}                 # -> "entered world coach=Loov"
arena_click { x, y }           # navigate (canvas coords; GLCanvas is 1016x741)
arena_screenshot {}            # returns a PNG inline — SEE the panel
arena_roster {}                # ASSERT client model: adY.atu() size
arena_server_log { filter }    # what the server saw
arena_client_log { filter: "ERROR|Exception|Erreur" }   # did the client break?
arena_down {}                  # teardown + free port 5555
```

Rebuild the agent (`control-agent/`) after changing it; the MCP server picks up
the new jar on the next `arena_up`.

## Fallback — the PowerShell driver

If the MCP tools aren't available (e.g. before an opencode restart), the same
capabilities are in `client/control-agent/drive.ps1`:

```powershell
. .\drive.ps1
Build-Server; Start-Arena
Ctl '/offscreen?on=1'; Ctl '/login?user=locos975&pass=azerty'
Shot 'panel.png'; Ctl '/roster'
Stop-Arena
```

### Fallback that works from an agent shell — `control-agent/arena.ps1`

`drive.ps1` starts the server/client as **children of the calling shell**. An agent
tool-shell kills its whole process tree when a command returns, so both die instantly
(the symptom is a `ChildProcess.kill` error and nothing listening on 5555).

`arena.ps1` solves this by launching through **WMI**, which reparents the new process
away from the caller so it survives:

```powershell
([WMICLASS]"\\.\root\cimv2:Win32_Process").Create($cmdline, $workingDir)
```

```powershell
$a = "E:\Projets\DofusArena2-06\client\control-agent\arena.ps1"
& $a up -Rebuild          # server + client, agent on :8099, window off-screen
& $a login                # locos975 / azerty
& $a shot C:\tmp\s.jpg 1016 85
& $a c2s 3151 3 012A0C2F53...   # inject a C2S frame (GM chat, etc.)
& $a srvlog 30 "sudden|fight"
& $a down
```

Screenshots are written to a file, which the Read tool renders directly — so an agent
can *see* the game without any MCP layer.

### The agent's `/type` mangles non-ASCII — don't test encodings with it

`/type?text=…` URL-decodes with the JVM's platform charset, so `%C3%A9` arrives as
`Ã©` rather than `é`: the text lands **already mangled in the client's input field**,
before any protocol code runs. Chasing that as a server bug wastes a cycle.

To exercise accented text, inject the exact bytes a real client would send instead:

```powershell
$cp  = [System.Text.Encoding]::GetEncoding(1252)
$b   = $cp.GetBytes("Café")           # 43 61 66 E9 — what the client puts on the wire
$hex = "012A" + ("{0:X2}" -f $b.Length) + (($b | %{ "{0:X2}" -f $_ }) -join "")
& $arena c2s 3151 3 $hex
```

Reading the server log back has the same trap in reverse: it is UTF-8, so read it with
an explicit UTF-8 `StreamReader` — a bare `Get-Content` renders `é` as `Ã©` and looks
like a bug that isn't there.

### Proving "is it us or the client?" for a rendering bug

The pattern that settled the accented-name question, reusable for any "the client shows
the wrong thing" report:

1. **Dump the bytes we actually send** from a unit test on the builder
   (`writeFightCoachBlock`, `buildVicinityMessage`, …). If they are right, stop blaming
   the encoder.
2. **Change the input in the DB, not the code** — renaming a coach needs no rebuild and
   isolates one field.
3. **Feed it a pre-mangled value.** If the render gets *worse* rather than better, the
   client is applying its own transform and there is no server-side workaround. If it
   gets better, you have found a compensation (and a nasty decision to make).
4. Compare against a field that works — an accented chat *body* renders correctly,
   which is what proved the problem is specific to the name path.

### Two things this document used to claim that are FALSE

1. **"Clicks only reach AWT/Swing dialogs, not the GLCanvas."** Wrong. `ControlAgent`
   explicitly targets the GLCanvas (*"Input must target the GLCanvas, not the Frame"*)
   and invokes its registered listeners on the EDT. The full in-game UI is clickable;
   the team panel, the **Tester** button and the **Prêt** button were all driven this way.
2. **"Injected fights build no client-side match object, so in-fight visuals can't be
   self-verified."** Half-wrong, and the *reason* matters. Injecting `26330` through
   `/c2s` does produce a correct fight — the client loads the arena and logs **no**
   errors — but the client never arms its fight HUD, because that is local UI state
   entered when *the client itself* initiates. Drive the real UI instead and everything
   renders:

   > team emblem (490,652) → **TESTER** (608,344) → **PRÊT** (247,462)

   That is a solo practice fight against the Sparring dummy: **one account, no second
   player, full HUD.** For a fast end state use the GM commands (`/SUDDENDEATH`,
   `/ENDFIGHT`) rather than playing 15 rounds.

## Test account / fixtures

- Account **`locos975`** / password **`azerty`** (seed with
  `go run ./cmd/seedaccount --config configs/config.sqlite.yaml --login locos975 --password azerty`).
- Coach **`Loov`** (created interactively once; persists in `arena.dev.db`).
- Login field click target in the 1024×768 window: **`x=512 y=380`** (account),
  TAB to password.

## What each signal is good for

| Signal | Catches | Blind to |
|---|---|---|
| Go unit/e2e | wire layout, server logic, races | client-side rendering / state |
| `output.log` | decode fails, UI crashes, protocol desync | silent "wrong but valid" state |
| Screenshot | what the player actually sees | exact internal counts |
| `/eval` model read | client-side model counts/flags | pixels |

Use them together: `output.log` says "did anything break", the screenshot says
"does it look right", and `/eval` says "does the client's model match ours".

## Known-benign log noise (ignore these)

- `Probl?me lors de SoundManager.initialize` / `Erreur durant l'initialisation
  du soundManager` — audio device not available in the test env.
- `Impossible de retrouver la valeur sélectionnée dans la liste ... Minuit` —
  the server-select dropdown populating on the login screen.
- `game data not loaded ... indexes.bdat` (server side) — the harness runs
  without the client `bdata`; real-data-dependent features are exercised via the
  gamedata unit tests instead.

Only **new** ERROR lines (not on this list) indicate a real problem.

## Live session, 2026-08-11 — driving a fight from a cold database

Recorded because two steps here cost real time and are not obvious from the code.

**A 6006 roster push only lands while the team panel is the ACTIVE frame.** A
fighter created while the world was in front (here, by injecting `6001` through
`/c2s`) is persisted correctly and the server re-pushes the roster, but the
client drops it: `adY.atu()` stayed empty and the team grid stayed empty across
a full relog. Creating the fighter with the panel already open populated both
immediately. If the roster looks empty after a create, that is why.

**The client refuses to start a fight with an empty team, before any packet is
sent** — *"Il faut au moins un combattant dans ton équipe pour lancer le
combat !"*. So a fighter must be in a TEAM SLOT, not merely in the roster.
The reliable route is entirely in the GUI: team emblem → **Recruter** on a slot →
the *Nouveau combattant* dialog (name field, camp, one of the 14 class icons) →
**Valider**. That places the fighter in the slot and the budget counter moves
(600/6000 for a bare Iop).

Full working sequence from a database with no coach at all:

1. `arena_up`, `arena_login` → the coach-creation screen; click the name field,
   type, **Valider** (all canvas clicks — they work; see the retraction above).
2. Team emblem **(490, 652)**.
3. **Recruter** on the first slot **(141, 293)** → fill the dialog → **Valider**
   **(625, 660)**.
4. **TESTER (608, 344)** → the fight starts (it launches challenge 12, not a
   sparring dummy).
5. **PRÊT (483, 735)** → placement ends and the action phase begins.
6. End it fast with GM chat rather than playing it out — opcode `3151`, arch 3,
   payload `012A` + `[u8 len]` + the command:
   `curl "http://127.0.0.1:5599/c2s?opcode=3151&arch=3&hex=012A092F454E444649474854"`
   (that hex is `/ENDFIGHT`).

**What this run confirmed:** fighters render in the placement phase (which is the
real test of any change to the CREATE_FIGHT fighter blob — a wrong byte makes
`gn_0.b` underflow and the fighter silently disappears), round 1 draws event card
14, the end-of-fight panel renders with its won-cards row, and the client log
stays completely error-free.

**It also reproduced the known accent bug**: the opponent name rendered as
`DÃƒÂ¢fi` for *Défi*. That is the client-side mangling documented above — the
server's encoding is correct and must not be "fixed" to chase it.
