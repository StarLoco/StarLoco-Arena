# 7. Phased Delivery Roadmap

## Phase 1 — Foundation

Goal: server boots in well under 1s, accepts raw TCP connections, speaks the correct
framing, has a working multi-DB layer with migrations.

- [ ] `go.mod` init, Go 1.26.4, base dependency set (koanf, zerolog, gorm + 3 drivers,
      golang-migrate, bcrypt)
- [ ] `internal/config` — YAML+env loading & validation
- [ ] `internal/log` — zerolog setup
- [ ] `internal/db` — driver selection, connection pool, async ping (§01 1.4.1)
- [ ] `migrations/{postgres,mysql,sqlite}/000001_init_schema` — full schema from
      `03-data-model.md`
- [ ] `internal/protocol/frame.go` — inbound/outbound framing per `02-protocol.md` §2.2
- [ ] `internal/netio` — TCP listener, per-connection goroutine, panic recovery, graceful
      shutdown skeleton
- [ ] Smoke test: connect with a raw TCP client, send a `VERSION`(7) packet, confirm
      correct framing round-trip
- [ ] Verify cold-start-to-accept timing empirically (target < 200ms), document actual
      numbers

## Phase 2 — Feature parity port

Goal: 1:1 port of everything the current Java server does.

- [ ] `internal/domain` — all GORM structs
- [x] `internal/gamedata` — lazy repositories + `.dat` parsers (cards/spells/events/
      summoning/staticEffects — staticEffects.dat format fully reverse-engineered and
      implemented, see `04-game-data-format.md` §4.5 and `08-java-parity-roadmap.md`
      Phase M)
- [ ] `internal/service/auth.go` — login, bcrypt (new account creation path needs a
      migration path for any existing cleartext passwords if real data is being carried
      over — flag with user before cutover)
- [ ] `internal/service/coach.go` — coach creation, `COACH_INFORMATION` broadcast
- [ ] `internal/service/social.go` — friends/ignore, private/vicinity chat
- [ ] `internal/service/fighter.go` — fighter CRUD
- [ ] `internal/service/team.go` — team preset CRUD
- [ ] `internal/service/exchange.go` — item exchange session
- [ ] `internal/service/matchmaking.go` — opponent search/cancel
- [ ] `internal/world/registry.go` — sync.Map online-coach registry, join/leave broadcast
- [ ] `internal/dispatch` — opcode routing table wired to all of the above
- [ ] Fight creation + presentation phase (parity with current `Fight.java`, including the
      `CREATE_FIGHT` packet — **requires decoding `Coach.unserialize`/`Fighter.unserialize`
      client-side formats first**, per `02-protocol.md` §2.4.7 note)
- [ ] Integration test suite exercising each opcode handler against an in-memory SQLite DB

## Phase 3 — Client protocol completeness pass

Goal: implement the remaining non-combat opcodes that exist in the client but were never
wired server-side in Java (see `02-protocol.md` §2.3 `[NOT IMPLEMENTED]` rows outside the
8100-8300 combat range): `ACTOR_DISAPEAR`, `ACTOR_REPOSITION`, fight-invitation
accept/reject flow, `ACTOR_MOVEMENT` (non-fight world movement), friend online/offline
notifications.

- [ ] Extract exact field layouts for each remaining `*RequestMessage.java` /
      `*Message.java` pair following the method in `02-protocol.md` §2.4
- [ ] Implement + test each

## Phase 4 — Combat engine (the core value-add)

Goal: real turn-based PvP combat, matching the reference implementation's rules.

- [ ] **Blocker research tasks** (do first):
  - [ ] Source breed base-stat table (HP/AP/MP/INIT maxes, close-combat AP cost) —
        `breeds.dat` is empty, needs sourcing from client decompile or hand-authoring
        (`05-combat-engine.md` §5.3.2)
  - [ ] Extract `ValueRounder.randomRound()` exact behavior (`05-combat-engine.md` §5.6.1)
  - [ ] Extract full trigger-ID table from `RunningEffectConstants.java`
        (`05-combat-engine.md` §5.4.2)
  - [x] Reconstruct `staticEffects.dat` framing (`04-game-data-format.md` §4.5) — DONE,
        see `08-java-parity-roadmap.md` Phase M
  - [ ] Confirm `WeaponFighterCardTemplate` additional fields (`04-game-data-format.md`
        §4.2 section 2)
- [ ] `combat/fighter.go` — fighter data model, characteristics, properties
- [ ] `combat/timeline.go` — turn order, table-turns, turn begin/end
- [ ] `combat/fight.go` — Fight actor goroutine, phase state machine (§5.1)
- [ ] `combat/pathfind` — A* port (§5.8)
- [ ] `combat/effect` — trigger bus + effect catalog, prioritized: damage/heal → AP/MP
      cost → characteristic mods → movement → property toggles → states → summons →
      special mechanics (§5.4.1 ordering)
- [ ] `combat/spell` — cast validation pipeline (§5.5) + execution
- [ ] Placement phase: free-placement cell selection, ready-up
- [ ] Observation phase
- [ ] Action phase: full turn loop, spell casting, close combat, card use, movement,
      fight-end conditions (§5.9)
- [ ] End-to-end fight test: two scripted fighters, full fight to completion, assert
      final state matches expected outcome for a fixed RNG seed

## Phase 5 — Hardening

- [ ] Full unit test coverage of `combat/effect` formulas (property-based tests for damage
      formula edge cases: 0 resist, negative resist, rebound interactions)
- [ ] Race detector (`go test -race`) clean across the whole suite, especially
      `world/registry.go` and `combat/fight.go` actor boundaries
- [ ] Load test: simulate N concurrent connections + M concurrent fights, profile with
      `pprof`, confirm memory/CPU stay within targets
- [ ] GitHub Actions CI: build, vet, lint (`golangci-lint`), test, race-test
- [ ] Wire protocol conformance doc kept in sync (`02-protocol.md` is a living document —
      update it as each opcode is implemented, don't let it drift from the code)
- [ ] Security pass: confirm no cleartext password storage anywhere, confirm bcrypt cost
      factor is appropriate, review input validation on every packet reader (length
      prefixes must be bounds-checked against remaining buffer size before allocation —
      the current Java `Parser.java` trusts client-supplied lengths somewhat naively)

## Explicit non-goals for this rewrite (v1)

- TLS/encryption on the wire (client can't speak it — see `02-protocol.md` §2.5)
- Multi-channel chat (`CHANNEL_*` opcodes)
- AI/PvE opponents
- Horizontal scaling / multi-instance world state (single-process monolith is the
  explicit target per the "resilient, low memory, fast" requirements — revisit only if
  player count ever demands it)
