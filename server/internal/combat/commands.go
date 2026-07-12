package combat

// Concrete Command implementations -- one per player-driven action that
// can affect fight state, dispatched serially inside Fight.Run's actor
// loop (see fight.go). Each corresponds to one Recv opcode handler in
// internal/dispatch/handlers_fight_combat.go, which decodes the wire
// payload and calls Fight.Send with one of these.

type cmdCoachReadyPresentation struct{ CoachID uint }

func (cmdCoachReadyPresentation) isCombatCommand() {}

type cmdCoachReadyObservation struct{ CoachID uint }

func (cmdCoachReadyObservation) isCombatCommand() {}

type cmdCoachReadyAction struct{ CoachID uint }

func (cmdCoachReadyAction) isCombatCommand() {}

// RequesterCoachID (on the player-driven fighter commands below) is the
// coach who actually sent the request, resolved from their authenticated
// session in dispatch. Each handler validates that this coach owns the
// FighterID being acted on before doing anything -- preventing a coach
// from moving/casting-with/ending-the-turn-of the OPPONENT's fighters (a
// reported bug: without this check, a coach could send
// MOVE_TO_FREE_PLACEMENT for the other team's fighter id and the server
// would happily apply it). A RequesterCoachID of 0 means "trusted
// internal caller / test" and bypasses the ownership check (see
// fighterOwnedByRequester).

type cmdMoveToFreePlacement struct {
	RequesterCoachID uint
	FighterID        int64
	Pos              Point3
}

func (cmdMoveToFreePlacement) isCombatCommand() {}

type cmdFighterMove struct {
	RequesterCoachID uint
	FighterID        int64
	Path             []Point3
}

func (cmdFighterMove) isCombatCommand() {}

type cmdFighterDirectionChange struct {
	RequesterCoachID uint
	FighterID        int64
	Direction        Direction8
}

func (cmdFighterDirectionChange) isCombatCommand() {}

type cmdFighterEndTurn struct {
	RequesterCoachID uint
	FighterID        int64
	// deferred is set only by the settle-guard timer re-send in
	// handleFighterEndTurn, so the second pass isn't deferred again (see
	// that method's doc comment). Always false for a real client request.
	deferred bool
}

func (cmdFighterEndTurn) isCombatCommand() {}

type cmdCloseCombat struct {
	RequesterCoachID uint
	FighterID        int64
	Target           Point3
}

func (cmdCloseCombat) isCombatCommand() {}

type cmdSpellCast struct {
	RequesterCoachID uint
	FighterID        int64
	SpellID          int32
	Target           Point3
}

func (cmdSpellCast) isCombatCommand() {}

type cmdCardUse struct {
	RequesterCoachID uint
	FighterID        int64
	CardID           int32
	Target           Point3
}

func (cmdCardUse) isCombatCommand() {}

type cmdGiveUp struct{ CoachID uint }

func (cmdGiveUp) isCombatCommand() {}

type cmdEndFightDone struct{ CoachID uint }

func (cmdEndFightDone) isCombatCommand() {}

// internal timer-driven pseudo-commands, injected by Fight's own clock
// goroutine rather than dispatch, so all mutation still funnels through
// the single actor loop.
type cmdClockFired struct {
	phase Phase
	// turnFighterID pins a turn-clock firing to the specific fighter/turn
	// it was armed for, so a stale clock (from an already-ended turn)
	// firing late is safely ignored -- mirrors AbstractFightTimeline's
	// m_lastTurnEndClock id-matching check (docs/opcodes/08-fight-combat-engine.md §1.4).
	turnFighterID int64
	turnSeq       uint64
}

func (cmdClockFired) isCombatCommand() {}

// Exported constructors -- dispatch (internal/dispatch) builds Commands
// via these rather than the unexported struct literals above, since
// dispatch decodes wire payloads but must never reach into Fight's
// internal state directly (see fight.go's package doc comment on the
// actor's serial-access guarantee).

// NewCoachReadyPresentation constructs the command for a
// TeamMateSetReadyForPlacementRequestMessage (Recv 8011) received while the
// fight is in PRESENTATION -- i.e. the "Prêt" button clicked during the
// presentation phase, which the client sends via the same opcode as the
// pre-fight teleport gate (see dispatch/handlers_fight.go's overload
// routing). Once both coaches send it, presentation ends immediately.
func NewCoachReadyPresentation(coachID uint) Command {
	return cmdCoachReadyPresentation{CoachID: coachID}
}

// NewCoachReadyObservation constructs the command for
// TeamMateSetReadyForObservationRequestMessage (Recv 8023).
func NewCoachReadyObservation(coachID uint) Command {
	return cmdCoachReadyObservation{CoachID: coachID}
}

// NewCoachReadyAction constructs the command for
// TeamMateSetReadyForActionRequestMessage (Recv 8031).
func NewCoachReadyAction(coachID uint) Command {
	return cmdCoachReadyAction{CoachID: coachID}
}

// NewMoveToFreePlacement constructs the command for
// MoveToFreePlacementRequestMessage (Recv 8021). requesterCoachID is the
// authenticated sender; the handler rejects the move unless that coach
// owns fighterID.
func NewMoveToFreePlacement(requesterCoachID uint, fighterID int64, x, y int32, z int16) Command {
	return cmdMoveToFreePlacement{RequesterCoachID: requesterCoachID, FighterID: fighterID, Pos: Point3{X: x, Y: y, Z: z}}
}

// NewFighterMove constructs the command for
// FighterActorMovementRequestMessage (Recv 4503).
func NewFighterMove(requesterCoachID uint, fighterID int64, path []Point3) Command {
	return cmdFighterMove{RequesterCoachID: requesterCoachID, FighterID: fighterID, Path: path}
}

// NewFighterDirectionChange constructs the command for
// FighterActorDirectionChangeRequestMessage (Recv 4521).
func NewFighterDirectionChange(requesterCoachID uint, fighterID int64, dir Direction8) Command {
	return cmdFighterDirectionChange{RequesterCoachID: requesterCoachID, FighterID: fighterID, Direction: dir}
}

// NewFighterEndTurn constructs the command for FighterEndTurnRequestMessage
// (Recv 8105).
func NewFighterEndTurn(requesterCoachID uint, fighterID int64) Command {
	return cmdFighterEndTurn{RequesterCoachID: requesterCoachID, FighterID: fighterID}
}

// NewCloseCombat constructs the command for CloseCombatRequestMessage
// (Recv 8111).
func NewCloseCombat(requesterCoachID uint, fighterID int64, x, y int32, z int16) Command {
	return cmdCloseCombat{RequesterCoachID: requesterCoachID, FighterID: fighterID, Target: Point3{X: x, Y: y, Z: z}}
}

// NewSpellCast constructs the command for SpellCastRequestMessage
// (Recv 8109).
func NewSpellCast(requesterCoachID uint, fighterID int64, spellID int32, x, y int32, z int16) Command {
	return cmdSpellCast{RequesterCoachID: requesterCoachID, FighterID: fighterID, SpellID: spellID, Target: Point3{X: x, Y: y, Z: z}}
}

// NewCardUse constructs the command for FighterCardUseRequestMessage
// (Recv 8107).
func NewCardUse(requesterCoachID uint, fighterID int64, cardID int32, x, y int32, z int16) Command {
	return cmdCardUse{RequesterCoachID: requesterCoachID, FighterID: fighterID, CardID: cardID, Target: Point3{X: x, Y: y, Z: z}}
}

// NewGiveUp constructs the command for GiveUpFightRequestMessage
// (Recv 8151, forfeit).
func NewGiveUp(coachID uint) Command {
	return cmdGiveUp{CoachID: coachID}
}

// NewEndFightDone constructs the command for EndFightDoneMessage
// (Recv 4321).
func NewEndFightDone(coachID uint) Command {
	return cmdEndFightDone{CoachID: coachID}
}
