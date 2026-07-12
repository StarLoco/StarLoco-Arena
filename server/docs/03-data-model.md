# 3. Data Model & Persistence

## 3.1 Multi-DB strategy

GORM with driver selection at startup based on config (`database.driver: sqlite|postgres|mysql`):

| Driver | Package | Use case |
|---|---|---|
| SQLite | `github.com/glebarez/sqlite` (pure-Go GORM dialect, backed by `modernc.org/sqlite`, avoids requiring a C toolchain / CGO to build — confirmed working with `CGO_ENABLED=0`) | Local dev, tests, CI — zero setup, file or in-memory DB |
| PostgreSQL | `gorm.io/driver/postgres` | **Production default** |
| MySQL/MariaDB | `gorm.io/driver/mysql` | Optional, for operators wanting parity with the legacy DB / existing MySQL infra |

All domain structs use GORM tags only (no dialect-specific SQL in Go code); dialect
differences (e.g. `SERIAL` vs `AUTOINCREMENT`, `JSONB` vs `TEXT`) are handled by golang-migrate's
per-dialect migration files (see §3.4) rather than `AutoMigrate` in production. `AutoMigrate`
remains available as a **dev-only convenience** (`--dev-automigrate` flag) so local iteration
doesn't require writing a migration for every schema tweak.

## 3.2 Schema design

Direct successor of the OrmLite entities in `world/entity/**`, with the following fixes:

- **`accounts.password`**: stored as bcrypt hash (`varchar(60)`), not the raw password.
- **`coachs.position`**: was `DataType.SERIALIZABLE` (a Java-serialized blob — completely
  unportable and fragile). Replaced with three plain columns `pos_x int`, `pos_y int`,
  `pos_z smallint`. (Postgres could use a `POINT`/JSONB type but plain columns are simpler,
  queryable, and portable across all three supported drivers.)
- **`fighters.spells` / `fighters.objects`**: were CSV strings (`"12,45,90"`) — a classic
  anti-pattern (no referential integrity, manual parsing every read). Replaced with proper
  join tables (`fighter_spells`, `fighter_objects`) — GORM `many2many` associations.
- All foreign keys get explicit `ON DELETE CASCADE` where the Java version relied on
  implicit orphaning (e.g. deleting a `Coach` should cascade to their `CoachCard`s,
  `Fighter`s, `Team`s, friend/ignore rows).
- IDs: keep `Account`/`Coach`/`Team` as `uint`/int-based auto-increment (protocol uses
  `long`/`int` coach/account IDs already — no need to introduce UUIDs and break wire
  compatibility). `Fighter`/`CoachCard` IDs also stay integer-based since they're sent
  as `long` on the wire (§2.4).

## 3.3 GORM struct definitions

```go
// internal/domain/account.go
type Account struct {
    ID           uint      `gorm:"primaryKey"`
    Name         string    `gorm:"uniqueIndex;size:64;not null"`
    PasswordHash string    `gorm:"size:60;not null"` // bcrypt
    Connected    bool      `gorm:"not null;default:false"`
    CoachID      *uint     `gorm:"index"`
    Coach        *Coach    `gorm:"foreignKey:CoachID"`
    CreatedAt    time.Time
    UpdatedAt    time.Time
}

// internal/domain/coach.go
type Coach struct {
    ID        uint   `gorm:"primaryKey"`
    Name      string `gorm:"uniqueIndex;size:32;not null"`
    Skin      uint8  `gorm:"not null"`
    Hair      uint8  `gorm:"not null"`
    Sex       uint8  `gorm:"not null"`
    PosX      int32  `gorm:"not null;default:1"`
    PosY      int32  `gorm:"not null;default:1"`
    PosZ      int16  `gorm:"not null;default:0"`

    Inventory []CoachCard    `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
    Friends   []CoachFriend  `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
    Ignored   []CoachIgnored `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
    Fighters  []Fighter      `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
    Teams     []Team         `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`

    CreatedAt time.Time
    UpdatedAt time.Time
}

// internal/domain/coach_card.go
type CoachCard struct {
    ID         uint  `gorm:"primaryKey"`
    CoachID    uint  `gorm:"not null;index"`
    TemplateID int32 `gorm:"not null;index"` // FK into gamedata (not a DB table — see 04)
    Quantity   int16 `gorm:"not null;default:1"`
    Pos        int16 `gorm:"not null;default:0"` // 0 = inventory, >0 = equipped slot
    Flag       uint8 `gorm:"not null;default:2"` // bit flags: 1=LOCKED, 2=CURSED
}

// internal/domain/fighter.go
type Fighter struct {
    ID      uint   `gorm:"primaryKey"`
    CoachID uint   `gorm:"not null;index"`
    Name    string `gorm:"size:32;not null"`
    Breed   uint8  `gorm:"not null"`
    Sex     uint8  `gorm:"not null"`
    Skin    uint8  `gorm:"not null"`
    Budget  int16  `gorm:"not null"`

    SpellIDs  []int32 `gorm:"-"` // populated via FighterSpell join table, not a direct column
    ObjectIDs []int32 `gorm:"-"` // populated via FighterObject join table

    CreatedAt time.Time
}

type FighterSpell struct {
    FighterID uint  `gorm:"primaryKey"`
    SpellID   int32 `gorm:"primaryKey"` // references gamedata spell template id (not FK'd — external data)
}

type FighterObject struct {
    FighterID uint  `gorm:"primaryKey"`
    TemplateID int32 `gorm:"primaryKey"` // references gamedata fighter-card template id
}

// internal/domain/team.go
type Team struct {
    ID      uint   `gorm:"primaryKey"`
    CoachID uint   `gorm:"not null;index"`
    Slot    int16  `gorm:"not null"` // was "id" reused as a per-coach preset slot number in Java
    Name    string `gorm:"size:32;not null"`

    Fighters []Fighter `gorm:"many2many:team_fighters;constraint:OnDelete:CASCADE"`
}

// internal/domain/social.go
type CoachFriend struct {
    ID       uint `gorm:"primaryKey"`
    OwnerID  uint `gorm:"not null;index:idx_friend_owner_friend,unique"`
    FriendID uint `gorm:"not null;index:idx_friend_owner_friend,unique"`
    Notify   bool `gorm:"not null;default:true"`
}

type CoachIgnored struct {
    ID        uint `gorm:"primaryKey"`
    OwnerID   uint `gorm:"not null;index:idx_ignored_owner_ignored,unique"`
    IgnoredID uint `gorm:"not null;index:idx_ignored_owner_ignored,unique"`
}
```

Notes vs. the Java version:
- `TeamFighter` join entity is replaced by GORM's native `many2many` tag — no hand-rolled
  join-table DAO code or manual `PreparedQuery` (`Database.java:148-162`'s
  `makeFightersForTeamQuery` is no longer needed; GORM generates the equivalent join query).
- `Team.id` in Java was simultaneously the DB primary key AND a per-coach "preset slot
  number" (`Team.getNextId()`, `Team.java:71-77`) — conflating two concepts. Split into
  a real auto-increment `ID` (DB PK, used nowhere on the wire) and `Slot` (the `short id`
  that IS sent on the wire in `TEAM_PRESET_SAVE`/`TEAM_PRESET_LIST`, §2.4). This removes
  the awkward `getNextId()` linear scan.

## 3.4 Migrations

`golang-migrate/migrate` with per-dialect SQL files under `migrations/{postgres,mysql,sqlite}/`:

```
migrations/
├── postgres/
│   ├── 000001_init_schema.up.sql
│   └── 000001_init_schema.down.sql
├── mysql/
│   └── ... (same numbering)
└── sqlite/
    └── ... (same numbering)
```

Rationale for per-dialect files rather than one dialect-agnostic set: keeps SQL native and
reviewable (no lowest-common-denominator SQL abstraction), and the three dialects diverge
enough on auto-increment/JSON/index syntax that a shared file would need heavy templating
anyway. `internal/db/migrate.go` picks the right subdirectory based on the configured driver.

`cmd/server/main.go` runs migrations automatically on boot unless `--no-migrate` is passed
(useful for read-replica / multi-instance deployments where only one instance should run
migrations).

> **Implementation note**: `golang-migrate`'s own `database/sqlite` driver subpackage
> imports `modernc.org/sqlite` directly, which self-registers a `database/sql` driver named
> `"sqlite"` in its `init()`. Our GORM SQLite dialect (`glebarez/sqlite` → `glebarez/go-sqlite`,
> also backed by `modernc.org/sqlite` under the hood) registers a driver under the same name.
> Since `database/sql.Register` panics on a duplicate name, importing both packages in one
> binary is fatal at process start. The fix implemented in `internal/db/sqlite_migrate.go` is
> a small hand-rolled migration runner (apply `*.up.sql` files in filename order, tracked in a
> `schema_migrations` table) used only for the `sqlite` driver; Postgres and MySQL continue to
> use `golang-migrate` normally since they don't hit this conflict.



## 3.5 Connection pooling

GORM wraps `database/sql`, so standard pool tuning applies via config:

```yaml
database:
  driver: postgres
  dsn: "host=localhost user=arena password=... dbname=arena sslmode=disable"
  max_open_conns: 25
  max_idle_conns: 10
  conn_max_lifetime: 30m
  conn_max_idle_time: 5m
```

For SQLite (single-writer by design), `max_open_conns` is forced to 1 to avoid
`SQLITE_BUSY` errors, with `_journal_mode=WAL` set on the DSN for better read concurrency
during local dev.
