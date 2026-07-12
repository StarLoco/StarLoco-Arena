# 10. Account Web Portal

A public-facing website for account operations the game wire-protocol never exposed:
visitors **register/login**, every logged-in user **sees all of their own stored data**,
and **admin** accounts get a full **account-management console** plus **impersonation**.

It's implemented in **`internal/webadmin`** with **Go stdlib + `html/template` only** — no
Node toolchain, no build step, no framework, no CDN. Templates, a hand-crafted
Dofus-themed stylesheet, and the favicon are `//go:embed`-ed, so the whole site ships
inside the Go binary.

## 10.1 Running it

The portal shares `internal/service` and the database with the game server. There are two
ways to run it, controlled by config:

- **Single process (local dev):** set `web.enabled: true` in the config and run the game
  server normally — `go run ./cmd/server --config configs/config.dev.yaml`. `internal/app`
  builds the portal handler in `App.New` and starts it in a goroutine from `App.Serve`,
  tied to the same shutdown context as the game listener.
- **Separate process (production):** leave `web.enabled: false` (so the game server stays
  lean) and run the standalone binary — `go run ./cmd/web --config configs/config.yaml`.
  `cmd/web` binds `web.listen_addr` **regardless** of the `enabled` flag. This lets the
  public site and the game TCP listener run as independent processes, on different hosts or
  behind different reverse-proxy routes.

Both entry points construct the handler via `webadmin.New` and serve via `webadmin.Serve`,
so there's no drift between them. The standalone process has no live `world.Registry` (that
lives in the game process), so online-status badges are simply omitted there.

## 10.2 Configuration (`web` block)

```yaml
web:
  enabled: false                 # start the portal inside cmd/server (dev convenience)
  listen_addr: ":8080"           # HTTP bind address
  session_secret: "..."          # REQUIRED in prod: HMAC key for signed session cookies
  secure_cookies: true           # set true when served over HTTPS
  base_url: "https://..."        # optional, for absolute links
```

`session_secret` **must** be a strong random value in production. If empty, a random
ephemeral key is generated at startup (logged with a warning): sessions then won't survive
a restart and can't be shared across multiple instances — fine for dev, not for prod.

## 10.3 Data model

Adds a single column: **`accounts.is_admin`** (migration
`000002_account_is_admin.{up,down}.sql` across sqlite/postgres/mysql), surfaced as
`domain.Account.IsAdmin`. It gates both this portal's admin console and (once built) the GM
chat-cheat-commands from roadmap §8.4.

Admin accounts are flagged either via `cmd/seedaccount --admin` (to bootstrap the first
one) or via the console's create/grant controls.

## 10.4 Service layer (`internal/service/account.go`)

`AccountService` is deliberately separate from `AuthService` (which keeps owning the
game-login connected-flag handshake). It provides:

| Method | Purpose |
|---|---|
| `Register(name, password, isAdmin)` | Policy-validated (name 3–64 chars `[A-Za-z0-9._-]`, password ≥6), case-insensitive-unique registration. Public web registration always passes `isAdmin=false`. |
| `VerifyPassword(name, password)` | bcrypt check **without** touching the connected flag (unlike `Authenticate`). |
| `ChangePassword(id, new)` | Policy-validated password change. |
| `SetAdmin(id, bool)` | Flip the admin flag. |
| `ListAccounts(params)` | Searchable (account or coach name, case-insensitive substring), paginated projection for the admin table + total count. |
| `GetAccountDetail(id)` | Deep aggregation: account → coach → cards, fighters (+ spell/object loadouts), teams, friends, ignored. Powers both the admin deep-view and each user's own "all my data" page. |
| `DeleteAccount(id)` | Transactional delete that cascades through the coach (cards/fighters/teams/social). **Refuses** to delete a currently-connected account (`ErrAccountConnected`). |

Sentinel errors (`ErrAccountNameTaken`, `ErrAccountNameInvalid`, `ErrPasswordTooShort`,
`ErrAccountNotFound`, `ErrWrongPassword`, `ErrAccountConnected`) let the web layer map
failures to friendly, field-specific messages without string matching.

## 10.5 Pages & routes

Public:
- `GET /` — landing (redirects logged-in users to `/account`)
- `GET /status` — public server-status page (online players, active fights, uptime); see
  §10.9
- `GET|POST /login`, `GET|POST /register`, `POST /logout`

Authenticated (any account):
- `GET /account` — the user's full data dashboard (account, coach, inventory split into
  equipped/bag, fighters with loadouts, teams, friends, ignored)
- `GET|POST /account/password` — change password

Admin only (real account must be `is_admin`):
- `GET /admin` — dashboard tiles (total accounts, connected, coaches online, active fights,
  server uptime) + recent list
- `GET /admin/monitoring` — live health/stats + a proxied pprof profiler grid; see §10.8
- `GET /admin/monitoring/pprof/{profile}` — reverse-proxies to `internal/adminhttp`'s
  `/debug/pprof/{profile}`
- `GET /admin/accounts` — searchable + paginated account table
- `GET|POST /admin/accounts/new` — create account (optionally admin)
- `GET /admin/accounts/{id}` — **deep view** of every stored datum for an account
- `POST /admin/accounts/{id}/delete` — delete + all related data (blocked while connected)
- `POST /admin/accounts/{id}/toggle-admin` — grant/revoke admin
- `POST /admin/accounts/{id}/impersonate` — start impersonating
- `POST /impersonate/stop` — stop impersonating (allowed for anyone currently impersonating)

## 10.6 Security model

- **Sessions** (`session.go`): HMAC-SHA256 **signed** (not encrypted — the cookie stores
  only `accountID.impersonatedID.issuedUnix`) HTTP-only, `SameSite=Lax` cookies with a TTL.
  Every request re-loads the referenced account from the DB rather than trusting cookie
  contents for authorization, so a deleted-but-still-cookied user is logged out cleanly.
- **CSRF** (`csrf.go`): a signed, per-account double-submit token (HMAC keyed by the same
  server secret, bucketed by day) is embedded in every state-changing form and re-derived +
  compared on POST. GET requests never mutate state and are exempt.
- **Passwords**: bcrypt via the existing `service.HashPassword`; never stored or logged in
  cleartext.
- **Flash messages** (`flash.go`): a short-lived, cleared-on-read cookie carries one-shot
  success/error notices across the POST→redirect→GET cycle.

### Impersonation

An admin's session carries both the **real** `AccountID` (the audit identity) and an
`ImpersonatedID` (the **effective** identity used for data access). While impersonating:

- a persistent banner ("You (*admin*) are viewing the portal as *target*") with a one-click
  **Stop impersonating** button is shown on every page;
- the session is **read-only** — changing a password is blocked while impersonating, so an
  admin can't accidentally alter the target's credentials;
- `requireAdmin` gates on the **real** account's `is_admin`, so impersonation can never
  elevate a non-admin;
- deleting or toggling the admin flag on one's **own** account through the console is
  blocked, and deleting a **connected** account is blocked with a clear warning.

## 10.7 Theming

The look is a hand-crafted dark "Dofus Arena" theme (worn brass/gold trim on deep slate,
warm candlelight accents, serif display headings) in `static/app.css`, plus an embedded SVG
favicon. It's fully responsive and dependency-free — everything is served from the embedded
`static/` directory at `/static/*`. The legacy game client assets are compiled Java/SWF and
aren't directly reusable as web assets, so the theme evokes the game's palette rather than
reusing its binaries.

## 10.8 Live server stats (admin dashboard + public `/status` + monitoring)

Three additions surface the same live counters/tools `internal/adminhttp` exposes over
loopback-only JSON/pprof (see `docs/06-config-and-ops.md` §6.5), as human-facing (or
authenticated-proxy) pages within the portal itself, since `adminhttp`'s own endpoints are
intentionally loopback-only and not meant to be browsed directly:

- **Admin dashboard** (`/admin`) gained two tiles: **Active fights**
  (`combat.Manager.Count()`) and **Server uptime** (tracked via `Handler.startedAt`, set at
  portal construction time). A new `Deps.Fights *combat.Manager` field (optional, nil-safe
  like `Data`/`World`) feeds the fight count; `internal/app` wires the same `combat.Manager`
  instance already shared with the game dispatch layer, while the standalone `cmd/web`
  process leaves it nil (no live game process to query) and the tile simply reads 0.
- **Public `/status` page** — no login required, deliberately shows only aggregate counts
  (players online, fights in progress, server uptime) with **no player/account-identifying
  information**, unlike the admin dashboard's recent-accounts table. Linked from the site
  footer on every page. Exists for players/visitors to answer "is the server up, and is
  anyone playing" without needing an account or hitting the ops-only `adminhttp` port.
- **Admin monitoring page** (`/admin/monitoring`, admin-only) — surfaces `adminhttp`'s
  `/healthz` and `/stats` JSON (fetched server-side by `handleAdminMonitoring` over HTTP,
  since `adminhttp` may run in a different process — e.g. the standalone `cmd/web`
  binary sharing a host with `cmd/server`) as a small stat grid, plus a grid of links into
  Go's built-in profiler (`allocs`, `block`, `cmdline`, `goroutine`, `heap`, `mutex`,
  `profile`, `threadcreate`, `trace`). Each link hits
  `GET /admin/monitoring/pprof/{profile}`, which `handleAdminPprofProxy` reverse-proxies
  (via `net/http/httputil.ReverseProxy`) to `adminhttp`'s `/debug/pprof/{profile}` on
  `Config.AdminHTTPAddr` (wired from `server.admin_addr`). This gives an authenticated admin
  a path to profiling data through the public-facing portal **without** needing direct
  network access to the loopback-bound `adminhttp` port — the proxy route is the only
  *authenticated* path to it, since `adminhttp` itself has no auth of its own (by design: it
  expects to be unreachable except from localhost). If `server.admin_addr` is unset (or
  unreachable from wherever the portal process runs), the page shows a clear "not
  configured" / fetch-error notice instead of erroring.

Overview/Accounts/Monitoring now share an `admin_tabs` sub-nav partial (`_partials.html`)
rendered under each page's `<h1>`. A `formatUptime` template helper (`templates.go`) renders
a raw second count as a compact duration (`"2d 4h"`, `"13m"`, `"42s"`) throughout.

## 10.9 Tests

- `internal/service/account_test.go` — registration/validation/uniqueness, verify/change
  password, `SetAdmin`, list search + pagination, deep `GetAccountDetail`, delete cascade +
  connected-account block.
- `internal/webadmin/webadmin_test.go` — session codec round-trip + tamper rejection,
  register→session flow, `/account` auth gating + render, admin-route 403 for non-admins,
  admin dashboard render (including the live-stats tiles, nil-safe when `Fights` is nil),
  public `/status` page render, impersonation CSRF + admin gating and effective-identity
  switch, connected-account delete block, `/admin/monitoring` admin-gating +
  not-configured/live-counter rendering (against a fake `adminhttp`-shaped test server), and
  the pprof reverse-proxy's admin-gating + response forwarding.
