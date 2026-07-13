# DofusArena 2 — Server Rewrite & Client Preservation

Run your own **DofusArena 2** (the card version of Ankama's tactical arena game)
on your own computer. This project bundles the original game client, its
decompiled source used as reference, the raw game data, and a **from-scratch Go
server** that speaks the client's exact language over the network.

New to programming? **You don't need to be a developer to use this.** The
[Getting Started](#-getting-started-step-by-step) guide below walks you through
every step — installing the tools, downloading the code, and starting the
server, website, editor, and game — with copy-paste commands for both **Windows**
and **macOS**.

> 💬 **Stuck or have a question? [Join us on Discord](https://discord.com/invite/k3Yk9DuhgY)** — see [Community & Support](#-community--support).

---

## What you can run

| Piece | What it is | Do you need it to play? |
|---|---|---|
| 🎮 **Game client** | The actual DofusArena game window you log in and play in. | Yes (Windows only) |
| 🖥️ **Server** | The program the client connects to. Handles accounts, chat, matchmaking, duels. | Yes |
| 🌐 **Website** | A local account portal (register/log in, see your data, admin console). | No — optional, but the easiest way to create an account |
| 🛠️ **Editor** | "Studio", a desktop app for browsing/editing game data and art. | No — for advanced/curious users |

The **server** is the actively developed part. Everything else is either
reference material or the runnable retail client used to test the server.

---

## 🚀 Getting Started (step by step)

Follow these in order. Windows and macOS instructions are given side by side.
When a command is the **same on both**, it's shown once.

> The **game client is Windows-only**. On macOS you can run and develop the
> server, website, and editor natively — but to actually *play*, you'll use the
> client on a Windows PC (or a Windows virtual machine). See
> [Step 4](#step-4--play-the-game).

### Step 0 — Install the tools you need

You only need the first two rows to run the **server + website + game**. The
last two rows are **only for the editor (Studio)**.

| Tool | Why | Get it |
|---|---|---|
| **Go** 1.26.4+ | Runs the server and website | <https://go.dev/dl/> — Windows: the `.msi`; macOS: the `.pkg` (or `brew install go`) |
| **GitHub Desktop** *(easiest)* **or** Git + Git LFS | Downloads the code (including large game files) | GitHub Desktop: <https://desktop.github.com/> · Git: <https://git-scm.com/downloads> · Git LFS: <https://git-lfs.com/> |
| **Node.js** (LTS) — *editor only* | Builds the editor's interface | <https://nodejs.org/> (or `brew install node`) |
| **Wails** CLI — *editor only* | Builds/runs the editor desktop app | See [Step 6](#step-6-optional--open-the-editor-studio) |

**After installing Go, check it works.** Open a terminal (see below) and run:

```
go version
```

You should see something like `go version go1.26.4 …`. If instead you get "command
not found" or "not recognized", close and reopen the terminal; if it still
fails, reinstall Go and make sure you finished the installer.

<details>
<summary><b>How to open a terminal</b></summary>

- **Windows:** Press `Win`, type **PowerShell**, press Enter. To open it *inside a
  folder*, open that folder in File Explorer, click the address bar, type
  `powershell`, and press Enter.
- **macOS:** Press `Cmd + Space`, type **Terminal**, press Enter. To move into a
  folder, type `cd ` (with a space) and then drag the folder onto the Terminal
  window and press Enter.
</details>

### Step 1 — Get the code

The project includes some **large files** (the game client and its data). These
are stored with **Git LFS**, so please use one of the two methods below — the
green **"Download ZIP"** button on GitHub does **not** download those large
files, and the game won't start if you use it.

**Method A — GitHub Desktop (recommended for beginners).** It handles the large
files automatically.

1. Install and open **GitHub Desktop**.
2. Menu **File → Clone repository… → URL** tab.
3. Paste this URL and pick where to save it, then click **Clone**:
   ```
   https://github.com/StarLoco/StarLoco-Arena.git
   ```
4. Wait for it to finish (the large files take a while the first time).

**Method B — command line.** Run these in a terminal, once:

```
git lfs install
git clone https://github.com/StarLoco/StarLoco-Arena.git
```

This creates a `StarLoco-Arena` folder. **Everything below is run from inside the
`server` folder** of that project (`StarLoco-Arena/server`).

### Step 2 — Start the server (this also starts the website)

Open a terminal **in the `server` folder** and run:

```
go run ./cmd/server --config configs/config.dev.yaml
```

The very first time, Go downloads some dependencies and the command may take a
minute. When it's ready you'll see log lines and the program **keeps running** —
that's normal. It is now:

- listening for the game client on **port 5555**, and
- serving the **website at <http://localhost:8080>** (already turned on in the
  dev config).

It also creates a small database file (`arena.dev.db`) in the `server` folder on
first run.

> **Leave this terminal window open** the whole time you want to play. To stop
> the server later, click the window and press `Ctrl + C`.

### Step 3 — Create an account

Accounts aren't created from inside the game, so make one first. Pick either way:

**The easy way (website):** open <http://localhost:8080> in your browser and use
the **Register** page.

**Or the command line:** open a **second** terminal in the `server` folder and
run:

```
go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123
```

That creates an account with login `test` and password `test123`. Add `--admin`
at the end if you want it to unlock the website's admin console:

```
go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123 --admin
```

### Step 4 — Play the game

**On Windows:** open the game by running

```
client\compiled\game\DofusArena.exe
```

(You can also double-click `DofusArena.exe` in that folder.) It is **already set
to connect to your local server** (`127.0.0.1:5555`), so just log in with the
account you created in Step 3. It brings its own Java runtime — nothing else to
install.

In-game, open the console with `Ctrl + ²`; the available commands are listed in
[`docs/client-console-commands.md`](./docs/client-console-commands.md).

**On macOS:** the bundled client is **Windows-only**, so it can't run directly on
a Mac. Two common options:

- Run the client on a **Windows PC on the same network**, and point it at your
  Mac: edit `client\compiled\game\config.properties` on that PC and change
  `proxyAddresses_1` to your Mac's local IP, e.g. `192.168.1.20:5555,192.168.1.20:5555`.
- Or run it inside a **Windows virtual machine** (Parallels, VMware, UTM, …) on
  your Mac. If the server runs on the same Mac, the default `127.0.0.1:5555` may
  need to be the VM's host address instead.

### Step 5 (optional) — Explore the website

With the server running (Step 2), open <http://localhost:8080>. From there you
can register/log in, see **all of your own account data**, and — if your account
is an **admin** (see Step 3) — manage accounts and use the admin console.

> Advanced: in production you'd run the website as its own process with
> `go run ./cmd/web --config configs/config.dev.yaml` instead of bundling it into
> the game server. For local use, Step 2 already does everything.

### Step 6 (optional) — Open the editor (Studio)

Studio is a desktop app for browsing and editing the game's data files and art.
It's the most advanced part to set up, and needs **Node.js** (Step 0) plus the
**Wails** tool.

1. **Install the Wails command** (once):
   ```
   go install github.com/wailsapp/wails/v2/cmd/wails@latest
   ```
   Make sure Go's tools folder is on your `PATH` so the `wails` command is found:
   - **Windows:** it's usually added automatically (`%USERPROFILE%\go\bin`). If
     `wails` isn't recognized, reopen the terminal.
   - **macOS:** add this line to `~/.zshrc`, then reopen Terminal:
     ```
     export PATH="$HOME/go/bin:$PATH"
     ```
2. **Check your system is ready:**
   ```
   wails doctor
   ```
   Follow anything it flags as missing (on macOS it may ask you to run
   `xcode-select --install`; on Windows it may need the WebView2 runtime, which
   is preinstalled on Windows 11).
3. **Run it** from the `server/cmd/studio` folder:
   ```
   wails dev
   ```
   This opens the Studio window (the first run automatically installs and builds
   its interface, so it may take a bit).

To create a **standalone app** instead, run `wails build` from the same folder.
The result lands in `server/cmd/studio/build/bin` (a `.exe` on Windows, a `.app`
on macOS).

> If the interface fails to build, build it manually first: from
> `server/cmd/studio/frontend` run `npm install` then `npm run build`, then retry
> `wails dev`.

---

## 🩹 Troubleshooting

| Problem | Fix |
|---|---|
| `go` / `git` / `wails` **is not recognized** (Windows) or **command not found** (macOS) | The tool isn't installed or isn't on your `PATH`. Reopen the terminal after installing. For `wails`, see [Step 6](#step-6-optional--open-the-editor-studio). |
| The **game won't start**, or files look tiny/broken | You likely downloaded without Git LFS (or used "Download ZIP"). Fix it: run `git lfs install` then `git lfs pull` inside the project folder, or re-clone with [Method A](#step-1--get-the-code). |
| **"port already in use"** / address `5555` or `8080` in use | Another copy of the server is still running. Close the other terminal window (`Ctrl + C`), or change `listen_addr` / `web.listen_addr` in `server/configs/config.dev.yaml`. |
| The client **can't connect** | Make sure the Step 2 terminal is still running. On first launch your firewall may ask for permission — **allow** it. On macOS, double-check the client's server address (see [Step 4](#step-4--play-the-game)). |
| `go test -race` **fails to build** | The race detector needs a C compiler. It's optional — plain `go test ./...` works without one. See [Testing](#testing). |
| Something else | Ask on **[Discord](https://discord.com/invite/k3Yk9DuhgY)** — we're happy to help. |

---

## Repository layout

```
StarLoco-Arena/
├── client/
│   ├── source/       Decompiled Java/Lua client SOURCE (reference only, not built here)
│   └── compiled/     The runnable retail client (launcher + core.jar + assets + bundled JRE)
├── server/           The Go 1.26 server rewrite — the active project
│   ├── cmd/          Entry points: server, seedaccount, studio (editor), web, loadtest…
│   ├── internal/     Server implementation (protocol, gamedata, combat, world, service…)
│   ├── data/         Game data consumed at runtime (.dat/.ade/.amw + maps/) and the
│   │                 decompiled game-logic reference under data/stc/
│   ├── migrations/   Versioned SQL migrations (sqlite/postgres/mysql), embedded
│   ├── configs/      Example YAML configs
│   └── docs/         Full server design specification + FEATURES-STATUS.md
├── tools/            Reverse-engineering references (i18n/sba/zorder) + cfr.jar (decompiler)
└── docs/             Top-level docs (e.g. in-client console commands)
```

> **Why `data/` lives inside `server/`:** it is the server's runtime input. The
> server config (`server/configs/*.yaml`) and its tests resolve it relative to
> the `server/` working directory. Do **not** move it back to the repo root — see
> [`AGENTS.md`](./AGENTS.md).

---

## Prerequisites (quick reference)

| To… | You need |
|---|---|
| Build/run the **server** or **website** | [Go 1.26.4+](https://go.dev/dl/) (no C toolchain required) |
| Run `go test -race` | A C toolchain (e.g. Windows: `winget install BrechtSanders.WinLibs.POSIX.UCRT`; macOS: `xcode-select --install`) |
| Build the **editor** (Studio) | [Node.js](https://nodejs.org/) + the [Wails v2 CLI](https://wails.io/) |
| Run the **game client** | Windows — nothing extra (it ships its own bundled JRE) |
| Clone with the large binaries | [Git LFS](https://git-lfs.com/) (`git lfs install`) |

For full server details, CLI flags, and the design docs, see
[`server/README.md`](./server/README.md) and [`server/docs/`](./server/docs).

---

## Testing

From the `server` folder:

```
go vet ./...
go build ./...
go test ./...            # full suite, no C toolchain required
go test -race ./...      # requires a C toolchain (optional)
```

---

## Git & Git LFS

Large binaries (the compiled client, its bundled JRE, and binary game data) are
tracked with **Git LFS** (see [`.gitattributes`](./.gitattributes)). Before
cloning or working with the repo:

```
git lfs install
```

Regenerable outputs (build artifacts, `node_modules/`, dev databases, logs) are
excluded via [`.gitignore`](./.gitignore).

---

## 💬 Community & Support

Questions, ideas, or just want to hang out? Join the community on Discord:

[![Join our Discord](https://discordapp.com/api/guilds/856945561421086730/widget.png?style=banner2)](https://discord.com/invite/k3Yk9DuhgY)

### Support the project by buying us a coffee

If this project is useful to you, a coffee is hugely appreciated and helps keep
the work going. ☕

[![Buy Me A Coffee](https://cdn.buymeacoffee.com/buttons/default-orange.png)](https://www.buymeacoffee.com/starloco)

---

## License & legal

- The **original work** in this repository (the Go server under `server/`
  excluding `server/data/`, the utilities under `tools/`, and this project's
  docs) is released under the [MIT License](./LICENSE).
- This repository is an **independent, non-commercial** preservation and
  interoperability project and is **not affiliated with Ankama or Oracle**.
  **DofusArena**, **Dofus**, and all related assets are the property of
  **Ankama**; the bundled Java runtime is the property of **Oracle/Sun**. The
  MIT license does **not** cover that third-party material.
- See [`DISCLAIMER.md`](./DISCLAIMER.md) for the full non-affiliation and
  copyright notice, [`NOTICE`](./NOTICE) for third-party attributions, and
  [`SECURITY.md`](./SECURITY.md) for takedown/vulnerability reporting.

## Contributing

See [`CONTRIBUTING.md`](./CONTRIBUTING.md) and [`AGENTS.md`](./AGENTS.md). By
participating you agree to the [Code of Conduct](./CODE_OF_CONDUCT.md).
