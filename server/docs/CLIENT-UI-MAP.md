# Client UI map — coordinates & flows for the live-testing loop

Companion to [`CLIENT-TESTING.md`](./CLIENT-TESTING.md). Purpose: **stop
re-discovering the same button positions by screenshot every session.** Read this
first, click the verified targets blind, and spend one screenshot only to confirm
an *unverified* (`~`) target before a precision click.

Legend: **✓** = verified by a successful click (safe to click blind) ·
**~** = approximate from XML/screenshot (confirm with one screenshot first).

---

## Coordinate system — READ THIS FIRST

- `arena_click`/`arena_drag` take **CANVAS coordinates**. The GLCanvas is
  **1016 × 741**. All coordinates in this file are canvas coordinates.
  - The control agent **re-normalizes the window to 1024×768 (canvas 1016×741)
    before every action** (`ensureNormalized()`, called by click/type/key/drag/
    move/login/screenshot, plus on boot via `setOffscreen`) — so even a later
    resize/maximize can't desync coordinates. Without it the client opens
    maximized (e.g. 2560×1440 → a 2552×1413 canvas) and every hardcoded
    coordinate misses. Sanity-check with the agent's `/tree` — `pG [WxH]` must
    read `1016x741`.
- `arena_screenshot { maxWidth }` returns a downscaled image:
  - **`maxWidth: 1016`** → native, **screenshot px == canvas coords (1:1)**.
    Use this whenever you need to read a coordinate to click.
  - **`maxWidth: 800`** (default) → cheap overview; **multiply screenshot px ×
    1.27** to get canvas coords. Good for "does it look right", bad for aiming.
- **Vision tokens scale with resolution**, so 800 is cheaper. Rule of thumb:
  overview at 800, one 1016 shot only when you must aim at a `~` target.

### Gotchas that have burned us
- **Typing swallows the first keystroke.** After clicking a text field, the
  first char is often lost. Prefix a throwaway char or re-check with a shot.
- **Export disk ≠ server save.** Every visible disk (below the slots *and* under
  each preset icon) is *Exporter équipe* — it writes a **local file** and pops
  "Fichier sauvegardé". There is **no** "save team to server" button; membership
  persists via drag (opcode 6013). See the Team-persistence model below.
- **Session dies → `fetch failed`.** If a tool returns `fetch failed`, run
  `arena_status`; if `down`, `arena_up` + `arena_login` again.

---

## Boot & login

```
arena_up { rebuild: true }        # rebuild server, start server+client off-screen
arena_login { user:"locos975", pass:"azerty" }   # -> "entered world coach=Loov id=3"
```

`arena_login` drives the form for you. Manual field targets (canvas), only if
needed: account **(508, 353)** ✓, password **(508, 411)** ✓, **SE CONNECTER** **(508, 489)** ✓ (verified — `arena_login` uses these + ENTER). Test fixture: coach
**Loov** (id=3), DB `server/arena.db`.

---

## World HUD (after login)

| Target | Canvas (x,y) | Notes |
|---|---|---|
| Team crest → opens Team panel | **(508, 640)** ✓ | orange crest, bottom-center |

---

## Team panel — Elite tab

Source XML: `contents/gui.jar!/gui/dialogs/teamManagementElite.xml`. This IS the
active 5-tab panel (Evolution / Elite / 2vs2 / Tournois / Légendes). Opcodes:
`6031` open, `6030` preset-list (S→C), `6006` fighter-list (S→C), `6021` save
team (C→S).

Take a **1016** shot to aim; these are read off the native frame.

| Target | Canvas (x,y) | Verified | Notes |
|---|---|---|---|
| Tab: Evolution | ~(120, 56) | ~ | |
| Tab: Elite | ~(245, 56) | ~ | |
| Tab: 2vs2 | ~(365, 56) | ~ | |
| Tab: Tournois | ~(490, 56) | ~ | |
| Tab: Légendes | ~(600, 56) | ~ | |
| Slot 1..6 (portrait) | x ≈ 140 / 288 / 434 / 578 / 724 / 868, y ≈ 165 | ~ | name label at y ≈ 225 |
| "Recruter" under empty slot | same x as slot, y ≈ 293 | ✓ (slot 3 @ 432,292) | opens *Nouveau combattant* |
| **Import** (red ↑, below slots left) | (110, 343) | ~ | presumed import-from-file |
| **Export** disk (below slots left) | (145, 343) | ✓ | *Exporter équipe* → local file, **NOT server save** |
| COMBATTRE (fight) | ~(408, 343) | ~ | `setClassicReadyForFight` |
| TESTER | ~(605, 343) | ~ | `launchTeamTest` |
| Budget readout "N / 6000" | ~(810, 343) | ~ | display only |
| "Masquer les équipes préconstruites" | (60, 395) | ~ | checkbox |
| Working-team name label | ~(508, 395) | ~ | display only |
| Preset icon 1 / 2 (bottom list) | ~(122, 460) / ~(230, 460) | ~ | click selects an editable preset |
| "Créer équipe" slots (bottom row) | x ≈ 322/415/508/601/693/786/880, y ≈ 535 | ~ | `openCloseTeamNameDialog` (names a new editable preset) |

### Team persistence model — there is NO "save to server" button (key trap)
The disks that look like "save" are **file** export/import (client-only):
- `saveTeam`/`loadTeam` (the `style="save"`/`style="load"` disks, and the disk
  under each preset icon) map to client-internal **20127/20126** and write/read a
  **local file** — `%saveTeam%` renders "Exporter équipe" → "Fichier sauvegardé".
- `selectTeamPreset` (clicking a preset icon) is client-internal **16617**: it
  loads the preset into the working slots locally; the server is never told which
  team is "active". `editableTeamPreset` is set by clicking the Elite **tab**
  (`changeTeamTab`→`d(firstPreset)`), not by selecting an icon, and only gates the
  export/import disks + per-preset delete-X visibility.

Team state reaches the server via three real network opcodes:

| Action | Opcode | Persists |
|---|---|---|
| CRÉER (name a new team) | 6021 | the team row (members=0 for a new team) |
| Recruter (create fighter into a slot) | 6001 `[u8 flag][i16 slot][blob]` | the **fighter** only — carries **no team id**, so membership is NOT saved |
| **drag pool↔slot / remove from slot** | **6013** `qp_1 [i64 fighter][i16 src][i16 dst][i64 am]` | **the team membership** (dst=-1 removes) |

**Consequence:** the only way to persist a fighter *into* a team is the **drag
pool→slot** (6013). Recruter-into-slot only shows the fighter in the slot
client-side; it reverts to the pool on reopen unless you then drag it in. (6013
was unhandled server-side until **B-016** — that was the real "fighters vanish"
bug.)

**Synthetic-drag caveat:** `arena_drag` does not currently trigger the client's
GL fighter-card drag (no 6013 is emitted), so the pool→slot flow can't be driven
from the harness yet — verify 6013 via the unit test / DB (`team_fighters`).

---

## Dialog: Nouveau combattant (create fighter)

Opened by "Recruter". Server logs `fighter created ... id=N breed=B`.

| Target | Canvas (x,y) | Verified |
|---|---|---|
| Name field | (241, 241) | ✓ (focus+type) |
| Camp Wakfu | ~(451, 288) | ~ |
| Camp Dofus | ~(490, 288) | ~ |
| Class grid row 1 (7 icons) | x ≈ 197/292/387/483/578/673/762, y ≈ 476 | ✓ (Féca @ 197,476) |
| Class grid row 2 (7 icons) | same x, y ≈ 560 | ~ |
| FERMER | ~(394, 658) | ~ |
| **VALIDER** | (622, 658) | ✓ → advances to equip screen |

Class row-1 icon 1 = **Féca** (shield, tooltip "Le bouclier Féca"), breed=1.

## Screen: fighter loadout (SORTS / EQUIPEMENT)

Shown right after VALIDER on creation. Budget 600, stats panel on the right.

| Target | Canvas (x,y) | Verified |
|---|---|---|
| **VALIDER** (finalize → places fighter in slot) | (486, 330) | ✓ |

After this VALIDER the fighter lands in the team slot and budget increases (e.g.
+600 → 1800/6000). Opcodes: `6011`→`6010` loadout. **This does NOT auto-save the
team** (no `6021`); an explicit save is still required.

---

## Flow: create a fighter into a team (verified)

1. Crest **(508,640)** → Team panel (Elite).
2. "Recruter" under an empty slot (e.g. slot 3 **(432,292)**).
3. Name field **(241,241)** → `arena_type` (guard the swallowed first key).
4. Pick class row-1 icon (Féca **(197,476)**).
5. **VALIDER (622,658)** → loadout screen.
6. **VALIDER (486,330)** → fighter drops into slot, budget += cost.

Result observed: `Tanko + Savecheck + Poolcheck`, budget `1800/6000`,
`fighter created id=27 breed=1`.

---

## Source of truth: dialog XMLs

Dialog layouts + action bindings live in `gui.jar`. Bindings look like
`onClick="dofusarena.teamManagement:saveTeam(selectedTeam)"`. Extract one with:

```powershell
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip=[IO.Compression.ZipFile]::OpenRead("E:\Projets\DofusArena2-06\client\compiled\game\contents\gui.jar")
$e=$zip.Entries | ? { $_.FullName -match 'teamManagementElite\.xml$' }
$sr=[IO.StreamReader]::new($e.Open()); $sr.ReadToEnd() | Out-File "$env:TEMP\teamElite.xml" -Encoding utf8
$sr.Close(); $zip.Dispose()
```

Known panels:
- `teamManagementElite.xml` — the active 5-tab team panel (this file's map).
- `newTeamManagementDialog.xml` — `rightArrow`→`openCloseFighterList`,
  `closeButton`→`openCloseTeamManagementDialog`.
- `teamManagementFighterListDialog.xml` — the fighter **pool** list (opened by
  `openCloseFighterList`); drag pool→slot happens here, not on Elite.

`<sld>` positions are relative to their parent container's `align`/`size`/
`xOffset`/`yOffset`; trace the container chain to turn them into canvas coords, or
just take a 1016 shot and read the pixel.
