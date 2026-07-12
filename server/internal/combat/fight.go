// Package combat implements the turn-based fight engine: the Fight actor,
// turn timeline, fighter state, spell/card validation, running effects,
// and pathfinding. See go-server/docs/05-combat-engine.md and
// go-server/docs/opcodes/08-fight-combat-engine.md for the full design and
// the game rules this package implements.
package combat

import (
	"math/rand"
	"sync/atomic"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// Phase is one state in the fight lifecycle state machine, see
// docs/05-combat-engine.md §5.1.
type Phase int

const (
	PhasePresentation Phase = iota
	PhasePlacement
	PhaseObservation
	PhaseAction
	PhaseEnded
)

func (p Phase) String() string {
	switch p {
	case PhasePresentation:
		return "presentation"
	case PhasePlacement:
		return "placement"
	case PhaseObservation:
		return "observation"
	case PhaseAction:
		return "action"
	case PhaseEnded:
		return "ended"
	default:
		return "unknown"
	}
}

// Clocks configures the per-phase timers (see docs/05-combat-engine.md
// §5.1 table and docs/opcodes/08-fight-combat-engine.md §1.4's concrete
// clock parameters: presentation 20s, placement 30s, observation 10s,
// per-fighter-turn 30s).
type Clocks struct {
	Presentation time.Duration
	Placement    time.Duration
	Observation  time.Duration
	Turn         time.Duration
	// EndFightAck bounds how long the fight actor waits, after END_FIGHT is
	// broadcast, for EVERY coach to send its EndFightDoneMessage(4321) ack
	// before force-completing anyway. Without it, a coach that never acks
	// (e.g. a MITM silently dropping its own 4321 while keeping the TCP
	// connection alive) would leave the fight actor's Run loop blocked
	// forever, so its duel is never removed and both participants' card
	// inventory/exchange stay frozen indefinitely (they're gated on "not in
	// a duel"). If zero, defaultEndFightAckClock is used.
	EndFightAck time.Duration

	// ActionSettle is the minimum wall-clock gap enforced between a
	// fighter's last animated action (spell/card/close-combat cast) and the
	// processing of their FIGHTER_TURN_END. If a player ends their turn
	// while the client is still playing that action's queued group (e.g.
	// ending the turn the instant a Feca teleport is cast), the client
	// stacks the turn-end + next-turn groups BEHIND the still-running cast
	// group; if that cast group stalls, everything after it freezes and the
	// fighter appears stuck (can't move/cast). Deferring the turn-end by a
	// short settle lets the cast's action sequence finish first. If zero,
	// the guard is disabled (end-turn is processed immediately) -- the
	// default for tests that drive turns synchronously; real fights set
	// defaultActionSettle via NewFight when unset.
	ActionSettle time.Duration
}

// defaultEndFightAckClock is the fallback force-complete timeout applied
// when Clocks.EndFightAck is zero (e.g. tests using Clocks{}). Generous
// enough that a legitimately-slow client always acks first, short enough
// that a stuck fight self-heals promptly.
const defaultEndFightAckClock = 30 * time.Second

// Command is a message sent into a Fight actor's inbox by connection
// goroutines (via dispatch) or by the fight's own clock goroutines,
// wanting to affect fight state. Concrete command types are in
// commands.go. Command is the common envelope.
type Command interface {
	isCombatCommand()
}

// Fight is one active combat instance, run as its own actor goroutine with
// a buffered command inbox. All mutation of fight state happens serially
// inside Run, so no locks are needed on Fight's own fields -- see
// docs/01-architecture.md §1.3. The one exception is Phase: external
// goroutines (tests, dispatch, admin/observability endpoints) legitimately
// need to read the current phase without synchronizing through the actor's
// command channel, so it's backed by an atomic and exposed via
// CurrentPhase()/setPhase() rather than being a plain field.
type Fight struct {
	ID     int64
	Type   byte
	phase  atomic.Int32
	Clocks Clocks

	Teams    []*Team
	Timeline *Timeline

	inbox  chan Command
	done   chan struct{}
	logger zerolog.Logger

	broadcaster Broadcaster
	data        *gamedata.Store
	mapData     *gamedata.Map // nil if no real map data could be loaded for this fight's map (falls back to occupancy-only checks, see IsWalkable/ArrivalAltitude in turns.go)
	// coachCells is the set of FightStartCoachPointElement cells (the raised
	// pedestals the two coaches stand on), keyed by "x,y". Built once from
	// mapData in SetMapData. IsWalkable treats these as NON-walkable so no
	// fighter or summon can path onto/through a coach's platform mid-fight
	// (the coaches are spectators, not on the battlefield). Empty when no
	// map data is attached.
	coachCells map[[2]int32]bool
	rng        *rand.Rand

	fightersByID map[int64]*Fighter

	// Per-phase readiness tracking (Phase D: TEAM_MATE_SET_READY_FOR_*
	// opcodes). Reset each time the corresponding phase is entered.
	//   - presentationReadyCoaches: opcode 8011 (TeamMateSetReadyForPlacement),
	//     re-sent by the client during PRESENTATION when "Prêt" is clicked --
	//     the same opcode as the pre-fight teleport gate, overloaded (see
	//     dispatch/handlers_fight.go). When both coaches vote, presentation
	//     ends immediately instead of waiting the full presentation clock.
	//   - placementReadyCoaches: opcode 8023 (placement->observation).
	//   - observationReadyCoaches: opcode 8031 (observation->action).
	presentationReadyCoaches map[uint]bool
	placementReadyCoaches    map[uint]bool
	observationReadyCoaches  map[uint]bool

	// phaseClockGen/turnClockGen guard against a stale timer firing after
	// the phase/turn it was armed for has already ended (either by the
	// other path -- all-ready vs. clock -- or by the fight ending),
	// mirroring the m_lastStepClock/m_lastTurnEndClock id-matching
	// tolerance in docs/opcodes/08-fight-combat-engine.md §1.4.
	phaseClockGen uint64
	turnClockGen  uint64
	phaseTimer    *time.Timer
	turnTimer     *time.Timer

	// actionIDCounter is the monotonically increasing uniqueId used in the
	// 8-byte fight-action header (uniqueId/triggeringActionUniqueId) on
	// every FightActionMessage-derived opcode -- see
	// docs/opcodes/08-fight-combat-engine.md Part 2's wire-format
	// preliminaries.
	actionIDCounter int32

	// endFightDoneCoaches tracks EndFightDoneMessage(4321) acks after
	// END_FIGHT is sent, so Manager knows when it's safe to consider this
	// fight fully torn down (see fightend.go).
	endFightDoneCoaches map[uint]bool

	// startedAt is the wall-clock time this fight was created, used to
	// compute each participant's "temps passé en combat" contribution at
	// end (see endFight / FightEndHook). endHook, if set, is invoked once
	// when the fight ends to persist statistics and supply END_FIGHT's
	// per-coach Strength/Report; nil in tests that don't exercise stats.
	startedAt time.Time
	endHook   FightEndHook

	// nextFighterID allocates IDs for mid-fight summons, starting well
	// above any real domain fighter ID range used by tests/dev data to
	// avoid collisions (see effects.go's Summon handling).
	nextFighterID int64

	// currentFighterID mirrors Timeline.CurrentFighter().ID (0 if none),
	// updated every time the actor changes whose turn it is. Exists so
	// external goroutines (tests, admin/observability) can safely observe
	// "whose turn is it" without racing on Timeline's own internal,
	// actor-only-mutated state -- see CurrentFighterID().
	currentFighterID atomic.Int64

	// advancingTurn is set (actor-goroutine only) while startNextTurn is
	// selecting/starting a fighter's turn. It suppresses killFighter's
	// own current-fighter-died auto-advance recovery so the two death
	// paths that already happen INSIDE startNextTurn (a new-table-turn
	// poison tick, a turn-start killer/trap cell) aren't advanced twice --
	// those sites do their own explicit startNextTurn. Outside startNextTurn
	// (e.g. the current fighter walking onto a trap mid-move), it is false,
	// so killFighter drives the recovery. See killFighter/startNextTurn.
	advancingTurn bool

	// lastActionAt is the wall-clock time the current fighter last broadcast
	// an animated action sequence (a spell/card/close-combat cast). Used by
	// the FIGHTER_TURN_END settle guard (handleFighterEndTurn) to avoid
	// ending a turn while the client is still playing that action's queued
	// group, which would freeze the fighter. Zero when no action has been
	// taken this turn (cleared at each turn start).
	lastActionAt time.Time

	// specialCells/activeCellBuffs implement the game-manual-described
	// special battlefield cells mechanic (Trap/Enthusiasm/Shield/Eagle
	// eye/Panacea/Motivation/Healing heart/Killer) -- see specialcells.go.
	// Nil map means no special cells are registered for this fight (the
	// mechanic is then simply inert), since no real per-map cell layout
	// data is sourced in this port yet.
	specialCells    map[specialCellKey]SpecialCellType
	activeCellBuffs []activeCellBuff

	// specialCellRenders holds the client-facing render tuples for this
	// fight's special cells (the CREATE_FIGHT specialCell list:
	// cellBaseId/x/y/z), populated alongside SetSpecialCell so the packet
	// builder can serialize them for the client to draw the tile art. See
	// AddSpecialCellRender / SpecialCellRenders.
	specialCellRenders []SpecialCellRender

	// effectAreas/nextEffectAreaIDCounter implement persistent ground-
	// effect areas (traps/glyphs) -- see effectarea.go, Phase M
	// (docs/08-java-parity-roadmap.md). nil until the first
	// SET_EFFECT_AREA (actionID 66) effect actually places one, matching
	// this project's general "inert until real data populates it"
	// pattern (same as specialCells above).
	effectAreas             *EffectAreaManager
	nextEffectAreaIDCounter int64

	// states is the per-fight registry of STATE_APPLY status-effect bundles
	// (see state.go). Nil until the first RegisterState -- inert by default,
	// matching this project's reality (no state data is sourced, and no real
	// spell/card/event uses STATE_APPLY), the same "inert until real data
	// populates it" pattern as specialCells/effectAreas above.
	states *stateManager

	// eventDeck/eventDeckPos implement the per-round EVENT CARD system (see
	// events.go): a shuffled deck of every event id in gamedata.Events,
	// drawn one per table-turn (NEW_TABLE_TURN_BEGIN carries the drawn id,
	// and the event's effects are applied to all fighters). nil until the
	// first draw builds it; empty if no event data is loaded (the mechanic
	// is then inert -- eventId 0 is sent, matching the pre-event behavior).
	eventDeck    []int32
	eventDeckPos int
}

// NewFight constructs a Fight in the PRESENTATION phase, not yet running.
// Call Run (in its own goroutine) to start processing. teams must already
// contain fully-populated Fighters (breed stats applied) -- see
// NewFighterFromBreed. broadcaster and data may be nil in tests that don't
// need outbound packets / gamedata lookups (e.g. pure timeline tests), but
// must be non-nil for any fight driven through dispatch.
func NewFight(id int64, fightType byte, clocks Clocks, teams []*Team, broadcaster Broadcaster, data *gamedata.Store, logger zerolog.Logger) *Fight {
	var allFighters []*Fighter
	fightersByID := make(map[int64]*Fighter)
	var maxID int64
	for _, team := range teams {
		for _, mate := range team.Mates {
			for _, fr := range mate.Fighters {
				allFighters = append(allFighters, fr)
				fightersByID[fr.ID] = fr
				if fr.ID > maxID {
					maxID = fr.ID
				}
			}
		}
	}

	f := &Fight{
		ID:                       id,
		Type:                     fightType,
		Clocks:                   clocks,
		Teams:                    teams,
		Timeline:                 NewTimeline(allFighters),
		inbox:                    make(chan Command, 64),
		done:                     make(chan struct{}),
		logger:                   logger.With().Int64("fight_id", id).Logger(),
		broadcaster:              broadcaster,
		data:                     data,
		rng:                      rand.New(rand.NewSource(time.Now().UnixNano())),
		fightersByID:             fightersByID,
		presentationReadyCoaches: make(map[uint]bool),
		placementReadyCoaches:    make(map[uint]bool),
		observationReadyCoaches:  make(map[uint]bool),
		endFightDoneCoaches:      make(map[uint]bool),
		nextFighterID:            maxID + 1_000_000,
		startedAt:                time.Now(),
	}
	return f
}

// SetEndHook registers the callback invoked once when this fight ends, used
// to persist coach statistics and populate END_FIGHT's per-coach
// Strength/Report (see FightEndHook). Must be called before Run.
func (f *Fight) SetEndHook(hook FightEndHook) {
	f.endHook = hook
}

// SetMapData attaches real .amw/elements.ade-derived map data (see
// internal/gamedata's Map type, docs/08-java-parity-roadmap.md Phase K) to
// this fight, used by IsWalkable/ArrivalAltitude in turns.go. Must be
// called before Run (or before any movement command is processed) to take
// effect; passing nil reverts to the occupancy-only fallback behavior.
func (f *Fight) SetMapData(m *gamedata.Map) {
	f.mapData = m
	f.coachCells = nil
	if m == nil {
		return
	}
	// Precompute the coach-pedestal cell set so IsWalkable can reject
	// movement onto/through the coaches' platforms (see coachCells).
	cells := make(map[[2]int32]bool)
	for _, side := range m.CoachStartCells() {
		for _, c := range side {
			cells[c] = true
		}
	}
	if len(cells) > 0 {
		f.coachCells = cells
	}
}

// CurrentPhase returns the fight's current lifecycle phase. Safe to call
// from any goroutine (see the Fight struct doc-comment on Phase's atomic
// backing).
func (f *Fight) CurrentPhase() Phase {
	return Phase(f.phase.Load())
}

func (f *Fight) setPhase(p Phase) {
	prev := Phase(f.phase.Load())
	f.phase.Store(int32(p))
	f.logger.Info().
		Str("event", "phase").
		Str("from", prev.String()).
		Str("to", p.String()).
		Dur("elapsed", time.Since(f.startedAt)).
		Msg("fight: phase transition")
}

// SetRNGSeed reinitializes the fight's RNG with a fixed seed, for
// deterministic tests (docs/08-java-parity-roadmap.md Phase H's
// "fixed RNG seed" end-to-end test). Must be called before Run.
func (f *Fight) SetRNGSeed(seed int64) {
	f.rng = rand.New(rand.NewSource(seed))
}

// FighterByID looks up a fighter participating in this fight.
func (f *Fight) FighterByID(id int64) (*Fighter, bool) {
	fighter, ok := f.fightersByID[id]
	return fighter, ok
}

// AllFighters returns every fighter participating in this fight (both
// teams, all team mates), in no particular order. Used by dispatch to
// build the ACTOR_APPEAR entity list once placement-ready (see
// docs/opcodes/03-coach-world.md's ACTOR_APPEAR section).
func (f *Fight) AllFighters() []*Fighter {
	var out []*Fighter
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			out = append(out, mate.Fighters...)
		}
	}
	return out
}

// Send enqueues a command for this fight's actor loop to process. Never
// blocks the caller for long: the inbox is generously buffered, and a full
// inbox indicates a stuck fight actor, which should be investigated rather
// than silently blocked on.
func (f *Fight) Send(cmd Command) {
	select {
	case f.inbox <- cmd:
	case <-f.done:
	}
}

// Done returns a channel closed once the fight has ended and its actor
// loop has exited.
func (f *Fight) Done() <-chan struct{} {
	return f.done
}

// Run is the actor loop. It must be started in its own goroutine
// (go fight.Run()). A panic anywhere in command handling is recovered so a
// bug in combat logic can only end this one fight, never crash the server
// -- see docs/01-architecture.md §1.5. Run returns once the fight has
// ended AND every participating coach has acked EndFightDoneMessage(4321)
// (or, as a fallback, been disconnected -- see disconnect handling in
// dispatch), so Manager can safely forget this fight.
func (f *Fight) Run() {
	defer close(f.done)
	defer f.stopTimers()
	defer func() {
		if r := recover(); r != nil {
			f.logger.Error().Interface("panic", r).Msg("combat: recovered panic in fight actor, ending fight")
			f.setPhase(PhaseEnded)
		}
	}()

	f.armPhaseClock(f.Clocks.Presentation)

	for cmd := range f.inbox {
		f.handleCommand(cmd)
		if f.CurrentPhase() == PhaseEnded && f.allEndFightDoneAcked() {
			return
		}
	}
}

func (f *Fight) stopTimers() {
	if f.phaseTimer != nil {
		f.phaseTimer.Stop()
	}
	if f.turnTimer != nil {
		f.turnTimer.Stop()
	}
}

// armPhaseClock (re)arms the shared phase-transition clock, bumping the
// generation so a previously-armed phase clock that still fires late is
// recognized as stale and ignored (docs/opcodes/08-fight-combat-engine.md
// §1.4).
func (f *Fight) armPhaseClock(d time.Duration) {
	if f.phaseTimer != nil {
		f.phaseTimer.Stop()
	}
	f.phaseClockGen++
	gen := f.phaseClockGen
	phase := f.CurrentPhase()
	f.phaseTimer = time.AfterFunc(d, func() {
		f.Send(cmdClockFired{phase: phase, turnSeq: gen})
	})
}

func (f *Fight) cancelPhaseClock() {
	if f.phaseTimer != nil {
		f.phaseTimer.Stop()
	}
	f.phaseClockGen++ // invalidate any in-flight fire
}

func (f *Fight) armTurnClock() {
	if f.turnTimer != nil {
		f.turnTimer.Stop()
	}
	f.turnClockGen++
	gen := f.turnClockGen
	var fighterID int64
	if cur := f.Timeline.CurrentFighter(); cur != nil {
		fighterID = cur.ID
	}
	f.turnTimer = time.AfterFunc(f.Clocks.Turn, func() {
		f.Send(cmdClockFired{phase: PhaseAction, turnFighterID: fighterID, turnSeq: gen})
	})
}

func (f *Fight) cancelTurnClock() {
	if f.turnTimer != nil {
		f.turnTimer.Stop()
	}
	f.turnClockGen++
}

func (f *Fight) handleCommand(cmd Command) {
	if f.CurrentPhase() == PhaseEnded {
		// Once the fight has ended, the only commands still meaningful are
		// the EndFightDoneMessage ack and the force-complete clock fire
		// (which force-acks everyone if a coach never sends its 4321);
		// everything else is ignored.
		switch c := cmd.(type) {
		case cmdEndFightDone:
			f.handleEndFightDone(c)
		case cmdClockFired:
			f.handleEndFightAckClock(c)
		}
		return
	}
	switch c := cmd.(type) {
	case cmdClockFired:
		f.handleClockFired(c)
	case cmdCoachReadyPresentation:
		f.handleCoachReadyPresentation(c.CoachID)
	case cmdCoachReadyObservation:
		f.handleCoachReadyObservation(c.CoachID)
	case cmdCoachReadyAction:
		f.handleCoachReadyAction(c.CoachID)
	case cmdMoveToFreePlacement:
		f.handleMoveToFreePlacement(c)
	case cmdFighterMove:
		f.handleFighterMove(c)
	case cmdFighterDirectionChange:
		f.handleFighterDirectionChange(c)
	case cmdFighterEndTurn:
		f.handleFighterEndTurn(c)
	case cmdCloseCombat:
		f.handleCloseCombat(c)
	case cmdSpellCast:
		f.handleSpellCast(c)
	case cmdCardUse:
		f.handleCardUse(c)
	case cmdGiveUp:
		f.handleGiveUp(c)
	case cmdEndFightDone:
		f.handleEndFightDone(c)
	default:
		f.logger.Warn().Type("cmd", cmd).Msg("combat: unhandled command type")
	}
}

func (f *Fight) handleClockFired(c cmdClockFired) {
	switch c.phase {
	case PhasePresentation:
		if c.turnSeq != f.phaseClockGen || f.CurrentPhase() != PhasePresentation {
			return // stale
		}
		// A phase timing out (rather than both coaches voting ready) is the
		// usual cause of a "slow first turn": one client never sent its
		// ready signal, so we waited out the full clock. Log it so the
		// stalling phase is obvious in the server log.
		f.logger.Info().Str("event", "clock").Str("phase", "presentation").
			Int("ready", len(f.presentationReadyCoaches)).Int("need", len(f.CoachIDs())).
			Msg("fight: phase clock expired (not all coaches ready)")
		f.askForPresentationEnd()
	case PhasePlacement:
		if c.turnSeq != f.phaseClockGen || f.CurrentPhase() != PhasePlacement {
			return
		}
		f.logger.Info().Str("event", "clock").Str("phase", "placement").
			Int("ready", len(f.placementReadyCoaches)).Int("need", len(f.CoachIDs())).
			Msg("fight: phase clock expired (not all coaches ready)")
		f.askForPlacementEnd()
	case PhaseObservation:
		if c.turnSeq != f.phaseClockGen || f.CurrentPhase() != PhaseObservation {
			return
		}
		f.logger.Info().Str("event", "clock").Str("phase", "observation").
			Int("ready", len(f.observationReadyCoaches)).Int("need", len(f.CoachIDs())).
			Msg("fight: phase clock expired (not all coaches ready)")
		f.askForObservationEnd()
	case PhaseAction:
		if c.turnSeq != f.turnClockGen || f.CurrentPhase() != PhaseAction {
			return
		}
		cur := f.Timeline.CurrentFighter()
		if cur == nil || cur.ID != c.turnFighterID {
			return
		}
		f.logger.Info().Str("event", "clock").Str("phase", "action").
			Int64("fighter_id", cur.ID).
			Msg("fight: turn clock expired, auto-ending turn")
		f.askForFighterEndTurn(cur.ID)
	}
}

// handleEndFightAckClock processes the force-complete clock armed at
// endFight time. If it's the current (non-stale) PhaseEnded clock, it
// force-marks every coach as having acked EndFightDoneMessage so Run()'s
// loop exits on the next iteration -- releasing the duel even if a coach
// (malicious or crashed-but-connected) never sent its own 4321 ack. A stale
// fire (superseded by a real ack completing the fight, or a leftover clock
// from an earlier phase) is ignored via the generation check.
func (f *Fight) handleEndFightAckClock(c cmdClockFired) {
	if c.phase != PhaseEnded || c.turnSeq != f.phaseClockGen {
		return // stale
	}
	if f.allEndFightDoneAcked() {
		return // already complete; the Run loop will exit on its own
	}
	f.logger.Warn().Str("event", "clock").Str("phase", "ended").
		Msg("fight: end-fight ack clock expired, force-completing (a coach never acked)")
	for _, coachID := range f.CoachIDs() {
		f.endFightDoneCoaches[coachID] = true
	}
}

// nextActionID returns the next monotonically increasing uniqueId for the
// fight-action header (docs/opcodes/08-fight-combat-engine.md Part 2).
func (f *Fight) nextActionID() int32 {
	f.actionIDCounter++
	return f.actionIDCounter
}

// nextSummonID allocates a fresh fighter ID for a mid-fight summon.
func (f *Fight) nextSummonID() int64 {
	f.nextFighterID++
	return f.nextFighterID
}

// registerFighter adds a newly-created fighter (summon) into the fight's
// lookup table and its TeamMate's fighter list.
func (f *Fight) registerFighter(fighter *Fighter, coachID uint) {
	f.fightersByID[fighter.ID] = fighter
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			if mate.CoachID == coachID {
				mate.Fighters = append(mate.Fighters, fighter)
				return
			}
		}
	}
}

// TeamOf returns the Team a fighter belongs to, or nil if not found.
func (f *Fight) TeamOf(fighter *Fighter) *Team {
	for _, team := range f.Teams {
		if team.ID == fighter.TeamID {
			return team
		}
	}
	return nil
}

// AreOpponents reports whether a and b belong to different teams,
// mirroring BasicFight.areOpponent -- used throughout target validation. A
// nil fighter (e.g. an environmental/reactive effect with no fighter
// caster) is treated as "not an opponent of anyone" so callers that gate a
// hostile effect on AreOpponents simply skip it rather than panicking.
func (f *Fight) AreOpponents(a, b *Fighter) bool {
	if a == nil || b == nil {
		return false
	}
	return a.TeamID != b.TeamID
}

// resolveOwnedFighter looks up fighterID and verifies requesterCoachID owns
// it, returning (fighter, true) only if both hold. This is the shared
// authorization gate for every player-driven fighter command (move,
// placement, direction-change, close-combat, spell/card cast, end-turn):
// it prevents a coach from acting on the OPPONENT's fighters (a reported
// bug where a coach could send MOVE_TO_FREE_PLACEMENT for the other team's
// fighter id and the server applied it).
//
// A requesterCoachID of 0 bypasses the ownership check entirely -- this is
// the "trusted internal caller" sentinel used by tests and any future
// server-driven (non-player-request) command path, since no real coach
// ever has id 0. Real dispatch always passes the authenticated sender's
// non-zero coach id (see sessionFight in dispatch), so player requests are
// always checked.
func (f *Fight) resolveOwnedFighter(requesterCoachID uint, fighterID int64) (*Fighter, bool) {
	fighter, ok := f.fightersByID[fighterID]
	if !ok {
		return nil, false
	}
	if requesterCoachID != 0 && fighter.CoachID != requesterCoachID {
		f.logger.Debug().
			Uint("requester_coach_id", requesterCoachID).
			Int64("fighter_id", fighterID).
			Uint("owner_coach_id", fighter.CoachID).
			Msg("combat: rejecting action on a fighter the requester does not own")
		return nil, false
	}
	return fighter, true
}
