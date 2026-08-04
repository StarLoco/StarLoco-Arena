package game

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// handleGMCommand processes a '/'-prefixed chat line as an admin command.
// Feedback is sent back as a private message from "Server". Commands are gated
// on the account's IsAdmin flag.
func handleGMCommand(s *Session, line string) error {
	if s.Account == nil || !s.Account.IsAdmin {
		return s.gmFeedback("You are not an administrator.")
	}
	fields := strings.Fields(strings.TrimPrefix(line, "/"))
	if len(fields) == 0 {
		return nil
	}
	verb := strings.ToUpper(fields[0])
	args := fields[1:]

	switch verb {
	case "HELP":
		return s.gmFeedback("Commands: /HELP /WHERE /TP x y [z] /WORLD id [x y] /WHO /STRENGTH n /FSTATE fighterId state /ENDFIGHT [win|lose] /EVOFIGHT /SUDDENDEATH /MAPDESTRUCT [x y [effectId]]")
	case "WHERE":
		return s.gmFeedback(fmt.Sprintf("pos=(%d,%d,%d)", s.Coach.PosX, s.Coach.PosY, s.Coach.PosZ))
	case "WHO":
		return s.gmFeedback(fmt.Sprintf("online=%d", len(s.deps.World.allSessions())))
	case "TP":
		return s.gmTeleport(args)
	case "WORLD":
		return s.gmWorld(args)
	case "FSTATE":
		return s.gmFighterState(args)
	case "ENDFIGHT":
		return s.gmEndFight(args)
	case "EVOFIGHT":
		return s.gmEvoFight()
	case "SUDDENDEATH":
		return s.gmSuddenDeath()
	case "MAPDESTRUCT":
		return s.gmMapDestruct(args)
	case "STRENGTH":
		if len(args) < 1 {
			return s.gmFeedback("usage: /STRENGTH <n>")
		}
		n, err := strconv.Atoi(args[0])
		if err != nil {
			return s.gmFeedback("bad number")
		}
		s.Coach.Strength = int32(n)
		_ = s.deps.Store.Coaches.Save(s.Coach)
		return s.gmFeedback(fmt.Sprintf("strength set to %d", n))
	default:
		return s.gmFeedback("unknown command: " + verb)
	}
}

func (s *Session) gmTeleport(args []string) error {
	if len(args) < 2 {
		return s.gmFeedback("usage: /TP x y [z]")
	}
	x, _ := strconv.Atoi(args[0])
	y, _ := strconv.Atoi(args[1])
	// Default to the coach's current altitude, not 0: the client's overworld
	// pathfinder needs the 4600 `alt` to match the destination cell's walkable
	// ground altitude, so resetting to 0 (when the ground isn't at 0) freezes the
	// coach. Override with "/TP x y z" to hop to a different altitude layer.
	z := int(s.Coach.PosZ)
	if len(args) >= 3 {
		z, _ = strconv.Atoi(args[2])
	}
	s.Coach.PosX, s.Coach.PosY, s.Coach.PosZ = int32(x), int32(y), int16(z)
	s.deps.World.UpdatePosition(s.Coach.ID, int32(x), int32(y), int16(z))
	_ = s.deps.Store.Coaches.Save(s.Coach)

	// Stay on the world the coach is currently in (use /WORLD to change island);
	// sending startWorldID here used to yank the coach back to the start island.
	world := s.currentWorld
	if world == 0 {
		world = startWorldID
	}
	return s.sendEnterOverworld(float32(x), float32(y), int16(z), world)
}

// gmWorld re-sends ENTER_WORLD_INSTANCE (4600) for a chosen world id, letting an
// admin hop overworlds live to find/verify islands. Usage: /WORLD <id> [x] [y].
// The world id is the i16 the retail client uses to stream
// contents/maps/{gfx,tplg,env}/<id>.jar (registered ids 5..113; see
// docs/OVERWORLD-MAP.md). Coordinates default to the current start cell.
func (s *Session) gmWorld(args []string) error {
	if len(args) < 1 {
		return s.gmFeedback("usage: /WORLD <id> [x y]")
	}
	world, err := strconv.Atoi(args[0])
	if err != nil {
		return s.gmFeedback("bad world id")
	}
	// Default to the destination world's primary Zaap: a known-walkable cell whose
	// ground altitude we know, so the coach can actually move on arrival (the 4600
	// `alt` must match the cell's ground wp — see zaapElement.alt).
	x, y, alt := float32(startCellX), float32(startCellY), s.Coach.PosZ
	if z, ok := primaryZaap(int16(world)); ok {
		x, y, alt = float32(z.cellX), float32(z.cellY), z.alt
	}
	if len(args) >= 3 {
		if xi, err := strconv.Atoi(args[1]); err == nil {
			x = float32(xi)
		}
		if yi, err := strconv.Atoi(args[2]); err == nil {
			y = float32(yi)
		}
	}
	// Keep the server-side coach position in sync with the rendered cell so
	// movement works after the hop (otherwise the client renders here while the
	// server still tracks the old cell, and moves desync).
	s.Coach.PosX, s.Coach.PosY, s.Coach.PosZ = int32(x), int32(y), alt
	s.deps.World.UpdatePosition(s.Coach.ID, int32(x), int32(y), alt)

	if err := s.sendEnterOverworld(x, y, alt, int16(world)); err != nil {
		return err
	}
	return s.gmFeedback(fmt.Sprintf("entered world %d at (%.0f,%.0f) alt=%d", world, x, y, alt))
}

// gmFighterState sets a fighter's evolution state directly:
// /FSTATE <fighterNameOrId> <state>, where state is 0 titular, 1 bench, 2 dead,
// 3 graveyard, 4 legendary titular, 5 legendary bench.
//
// Fighters normally reach state 2/3 by dying in an evolution-mode fight, which the
// fight engine does not model yet (fight deaths are not persistent), so this is
// the way to exercise the graveyard. Results are logged as well as sent back, so
// the outcome is visible even when the chat feedback is not.
func (s *Session) gmFighterState(args []string) error {
	if len(args) < 2 {
		return s.gmFeedback("usage: /FSTATE <fighterNameOrId> <state 0-5>")
	}
	state, err := strconv.Atoi(args[1])
	if err != nil || state < 0 || state > int(domain.FighterStateLegBench) {
		return s.gmFeedback("bad state (0=titular 1=bench 2=dead 3=graveyard 4/5=legendary)")
	}

	roster, err := s.deps.Store.Fighters.ListByCoach(s.Coach.ID)
	if err != nil {
		return s.gmFeedback("failed: " + err.Error())
	}
	var fighter *domain.Fighter
	if id, err := strconv.Atoi(args[0]); err == nil {
		for i := range roster {
			if roster[i].ID == uint(id) {
				fighter = &roster[i]
				break
			}
		}
	}
	if fighter == nil { // fall back to a case-insensitive name match
		for i := range roster {
			if strings.EqualFold(roster[i].Name, args[0]) {
				fighter = &roster[i]
				break
			}
		}
	}
	if fighter == nil {
		have := make([]string, 0, len(roster))
		for i := range roster {
			have = append(have, fmt.Sprintf("%d:%s(state %d)",
				roster[i].ID, roster[i].Name, roster[i].State))
		}
		s.log.Info("FSTATE: no such fighter", "coach", s.Coach.Name,
			"asked", args[0], "roster", have)
		return s.gmFeedback("no such fighter; you have: " + strings.Join(have, ", "))
	}

	from := fighter.State
	if err := s.setFighterState(fighter, uint8(state)); err != nil {
		return s.gmFeedback("failed: " + err.Error())
	}
	if err := s.pushFighterList(); err != nil {
		return err
	}
	s.log.Info("FSTATE", "coach", s.Coach.Name, "fighter", fighter.ID,
		"name", fighter.Name, "from", from, "to", state)
	return s.gmFeedback(fmt.Sprintf("fighter %d (%s) state %d -> %d",
		fighter.ID, fighter.Name, from, state))
}

// gmEndFight settles the caller's current fight immediately by zeroing one
// side's HP and running the normal end-of-fight path, so victory-only behaviour
// (challenge reward cards, stats, ladder movement) can be exercised without
// having to actually win. Usage: /ENDFIGHT [win|lose], default win.
//
// Like /FSTATE this exists because the alternative is unreachable by hand: the
// opponents in a PvE challenge are AI-driven and there is no other way to force
// a decisive result on demand.
//
// Runs the mutation on the fight actor goroutine, since fighter HP and the end
// sequence are actor-owned state.
func (s *Session) gmEndFight(args []string) error {
	f := s.deps.Fights.ByCoach(s.Coach.ID)
	if f == nil {
		return s.gmFeedback("not in a fight")
	}
	win := true
	if len(args) >= 1 && strings.EqualFold(args[0], "lose") {
		win = false
	}
	// Find the caller's side, then kill the other one (or its own, to lose).
	var mySide uint8
	for _, t := range f.Teams {
		if t != nil && t.Coach != nil && t.Coach.ID == s.Coach.ID {
			mySide = t.ID
		}
	}
	doomed := mySide
	if win {
		doomed = 1 - mySide
	}
	f.Post(func(f *Fight) {
		for _, ff := range f.allFighters() {
			if ff.TeamID == doomed {
				ff.HP = 0
			}
		}
		s.deps.checkFightEnd(f)
	})
	outcome := "win"
	if !win {
		outcome = "lose"
	}
	s.log.Info("GM end fight", "coach", s.Coach.Name, "fight", f.ID, "outcome", outcome)
	return s.gmFeedback(fmt.Sprintf("fight %d settled: %s (team %d wiped)", f.ID, outcome, doomed))
}

// gmEvoFight starts a solo EVOLUTION (lethal) practice fight — the caller's
// titular team against the sparring dummy, marked evolution so fighters that fall
// die for good. Combined with /ENDFIGHT lose it exercises the whole death →
// graveyard chain without needing a second coach (evolution fights are otherwise
// only reachable via a 2-coach direct challenge). Same rationale as /FSTATE and
// /ENDFIGHT: a real path is unreachable by hand, so a GM tool stands in.
// gmMapDestruct sends ONLY the client-side MapDestruction animation — RUNNING_EFFECT
// (8120) carrying mh_2 action 117 — and changes NOTHING on the server: no cells are
// removed, no fighter is harmed, no fight-end check runs. It answers one question:
// what does the client actually render?
//
//	/MAPDESTRUCT            destroy 35 cells (the outermost ring), arena centre
//	/MAPDESTRUCT r          destroy the outermost r cells
//	/MAPDESTRUCT r x y      … around a custom centre
//
// r is the client's destroy counter: it removes the first r cells of its spiral,
// ordered outermost → centre. 299 removes everything outside the 5×5 core.
//
// Caveat: the client kills entities standing on the cells it removes, so it may show
// fighters dying that the server still considers alive. That is a deliberate,
// temporary desync for this probe — end the fight afterwards rather than playing on.
func (s *Session) gmMapDestruct(args []string) error {
	f := s.deps.Fights.ByCoach(s.Coach.ID)
	if f == nil {
		return s.gmFeedback("not in a fight")
	}
	const usage = "usage: /MAPDESTRUCT [r [x y]]"
	r := int32(35) // the outermost ring by default
	if len(args) >= 1 {
		n, err := strconv.Atoi(args[0])
		if err != nil || n < 0 {
			return s.gmFeedback(usage)
		}
		r = int32(n)
	}
	centre := suddenDeathCentre(f.Arena())
	if len(args) >= 3 {
		x, errX := strconv.Atoi(args[1])
		y, errY := strconv.Atoi(args[2])
		if errX != nil || errY != nil {
			return s.gmFeedback(usage)
		}
		centre = Pos{X: int32(x), Y: int32(y)}
	}
	centre.Z = f.Arena().altitudeAt(centre.X, centre.Y)

	done := make(chan string, 1)
	f.Post(func(f *Fight) {
		target := f.anyLivingFighter()
		if target == nil {
			done <- "no living fighter to anchor the effect on"
			return
		}
		frame, err := buildRunningEffect(f.nextActionUID(), mapDestructionAction,
			f.scriptEffectID(), target.WireID, target.WireID, centre, r, 0, true)
		if err != nil {
			done <- "build failed"
			return
		}
		f.broadcast(frame)
		if seq, err := buildActionSequenceExecute(); err == nil {
			f.broadcast(seq)
		}
		if f.deps != nil && f.deps.Log != nil {
			f.deps.Log.Debug("map destruction probe sent (visual only)",
				"centre", centre, "r", r, "anchor", target.WireID)
		}
		done <- fmt.Sprintf("map destruction sent: r=%d centre=(%d,%d,%d) (visual only, server unchanged)",
			r, centre.X, centre.Y, centre.Z)
	})
	return s.gmFeedback(<-done)
}

// gmSuddenDeath removes ONE more ring of the arena immediately, instead of waiting
// for the next sudden-death table turn. Call it repeatedly to watch the arena
// shrink step by step. Test aid for the sudden-death mechanic (suddendeath.go).
func (s *Session) gmSuddenDeath() error {
	f := s.deps.Fights.ByCoach(s.Coach.ID)
	if f == nil {
		return s.gmFeedback("not in a fight")
	}
	f.Post(func(f *Fight) { f.advanceSuddenDeath() })
	return s.gmFeedback("sudden death: one shrink step applied")
}

func (s *Session) gmEvoFight() error {
	if s.deps.Fights.ByCoach(s.Coach.ID) != nil {
		return s.gmFeedback("already in a fight")
	}
	fightArena := pickArena()
	roster := s.deps.titularRoster(s.Coach.ID, len(fightArena.team0))
	if len(roster) == 0 {
		return s.gmFeedback("no titular fighters to field")
	}
	teamA, err := s.deps.buildFightTeamFor(s, 0, fightArena.team0, roster)
	if err != nil {
		return err
	}
	teamB := buildSparringTeam(1, fightArena.team1[0])
	s.log.Info("GM evolution fight", "coach", s.Coach.Name, "fighters", len(roster))
	// practice=true (unranked, vs sparring) + evolution=true (deaths persist).
	if err := s.deps.startFightWithTeams(fightArena, teamA, teamB, true, 0, true); err != nil {
		return err
	}
	return s.gmFeedback(fmt.Sprintf("evolution fight started with %d titular fighter(s); /ENDFIGHT lose to make them die", len(roster)))
}

// gmFeedback sends a private message from "Server" (coach id 0).
func (s *Session) gmFeedback(msg string) error {
	frame, err := buildPrivateMessage("Server", 0, msg)
	if err != nil {
		return err
	}
	return s.Send(frame)
}

var _ = protocol.OpPrivateContentMessage
