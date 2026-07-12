package world

import (
	"sync"
	"sync/atomic"
	"time"

	"github.com/dofusarena/go-server/internal/combat"
)

// DuelFighterSelection is one coach's chosen fighter roster for a duel,
// submitted via SET_READY_FOR_FIGHT, mirroring the legacy TempCoachFighter.
type DuelFighterSelection struct {
	FighterIDs []uint
}

// Duel represents an in-progress challenge between two coaches, from the
// moment matchmaking pairs them (OPPONENT_FOUND) through fighter selection
// (SET_READY_FOR_FIGHT), fight creation (CREATE_FIGHT), and presentation
// (START_PRESENTATION). This mirrors the legacy Fight + TempCoachFighter
// pair, kept separate from the future turn-based combat.Fight actor (see
// docs/05-combat-engine.md) since no real combat exists yet -- this is
// purely the challenge/setup flow the Java server implements today.
type Duel struct {
	ID       int64
	Type     byte
	Bet      int32
	CoachAID uint
	CoachBID uint

	mu         sync.Mutex
	selections map[uint]*DuelFighterSelection // coachID -> selection

	// placementReady mirrors the legacy TeamMateSetReadyForPlacement's
	// reuse of the WaitingOpponent.Opponent.ready field for a second,
	// separate readiness gate (teleport + START_PRESENTATION), distinct
	// from the fighter-selection readiness above. Kept as its own map for
	// clarity even though the legacy code conflated the two.
	placementReady map[uint]bool

	prepared            bool // CREATE_FIGHT already sent
	presentationStarted bool // START_PRESENTATION already sent / combat.Fight already instantiated

	// mapID is the fight map this duel takes place on, chosen once
	// (randomly, from the pool of usable fight maps) when the fight is
	// prepared and then reused for every downstream step (teleport,
	// coach/fighter placement, CREATE_FIGHT, special cells) so all coaches
	// see the SAME map. 0 means "not yet chosen" (callers fall back to the
	// default fight map). Set via SetMapID / read via MapID.
	mapID int32

	// fight is the real combat.Fight actor instantiated once both coaches
	// reach TEAM_MATE_SET_READY_FOR_PLACEMENT (see
	// docs/08-java-parity-roadmap.md Phase B). nil until then.
	fight *combat.Fight

	// readyTimer is the single forced-progress timer active at any given
	// moment for this duel: first covering the SET_READY_FOR_FIGHT
	// (fighter-selection) gate, then re-armed to cover the
	// TEAM_MATE_SET_READY_FOR_PLACEMENT gate once CREATE_FIGHT is sent.
	// Both gates were previously purely state-based with no timeout at
	// all -- a coach who never sent the expected readiness packet left
	// the duel stalled forever, matching the client's own 20s countdown
	// UI (Fight.onPresentationStart()'s Countdown.start(20)) having no
	// real server-side counterpart. See
	// dispatch/handlers_fight.go/handlers_matchmaking.go and
	// docs/08-java-parity-roadmap.md's write-up on this fix.
	readyTimer *time.Timer

	// stakeCards records the coach-card each coach put at stake in a bet
	// fight (coachID -> CoachCard.ID), chosen once at CREATE_FIGHT time
	// (see dispatch.prepareCreateFight's stake selection). Empty for a
	// no-bet fight. Carried on the Duel so the stake selected during
	// setup survives to the fight-end transfer (buildFightEndHook), which
	// reaches the duel via deps.Duels. Guarded by mu.
	stakeCards map[uint]uint
}

// SetMapID records the fight map chosen for this duel (idempotent: the
// first non-zero value wins, so a race between the placement handler and
// its forced-progress timer can't flip the map mid-setup). Returns the
// map ID now in effect (the previously-set one if this call was a no-op).
func (d *Duel) SetMapID(id int32) int32 {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.mapID == 0 {
		d.mapID = id
	}
	return d.mapID
}

// MapID returns the fight map chosen for this duel, or 0 if none has been
// chosen yet (callers should fall back to the default fight map).
func (d *Duel) MapID() int32 {
	d.mu.Lock()
	defer d.mu.Unlock()
	return d.mapID
}

// SetStakeCard records the coach-card coachID staked for this bet fight
// (the CoachCard.ID selected in prepareCreateFight). Idempotent per coach:
// the first non-zero stake wins, so a re-prepare can't reselect a different
// card mid-setup.
func (d *Duel) SetStakeCard(coachID, cardID uint) {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.stakeCards == nil {
		d.stakeCards = make(map[uint]uint, 2)
	}
	if _, exists := d.stakeCards[coachID]; !exists {
		d.stakeCards[coachID] = cardID
	}
}

// StakeCard returns the coach-card coachID staked for this fight, or
// (0, false) if none (a no-bet fight, or the coach hasn't been assigned a
// stake).
func (d *Duel) StakeCard(coachID uint) (uint, bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	id, ok := d.stakeCards[coachID]
	return id, ok
}

// SetFight attaches the instantiated combat.Fight to this duel, once it's
// created (see dispatch.handleTeamMateSetReadyForPlacement).
func (d *Duel) SetFight(f *combat.Fight) {
	d.mu.Lock()
	defer d.mu.Unlock()
	d.fight = f
}

// Fight returns the attached combat.Fight, if any.
func (d *Duel) Fight() (*combat.Fight, bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	return d.fight, d.fight != nil
}

func newDuel(id int64, coachA, coachB uint, fightType byte, bet int32) *Duel {
	return &Duel{
		ID:             id,
		Type:           fightType,
		Bet:            bet,
		CoachAID:       coachA,
		CoachBID:       coachB,
		selections:     make(map[uint]*DuelFighterSelection),
		placementReady: make(map[uint]bool),
	}
}

// OpponentOf returns the other coach's ID in this duel.
func (d *Duel) OpponentOf(coachID uint) uint {
	if coachID == d.CoachAID {
		return d.CoachBID
	}
	return d.CoachAID
}

// InvolvesCoach reports whether coachID is a participant in this duel.
func (d *Duel) InvolvesCoach(coachID uint) bool {
	return coachID == d.CoachAID || coachID == d.CoachBID
}

// SetSelection records a coach's chosen fighter roster. Returns true once
// both coaches have submitted a selection (i.e. the duel is ready to
// prepare / send CREATE_FIGHT), mirroring
// Fight.setTempCoachFighter + SetReadyForFight's "both ready" check.
func (d *Duel) SetSelection(coachID uint, fighterIDs []uint) (bothReady bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	d.selections[coachID] = &DuelFighterSelection{FighterIDs: fighterIDs}
	return len(d.selections) >= 2
}

// Selections returns both coaches' fighter selections, in (A, B) order.
// The second return value is false if either side hasn't selected yet.
func (d *Duel) Selections() (a, b *DuelFighterSelection, ok bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	a, aok := d.selections[d.CoachAID]
	b, bok := d.selections[d.CoachBID]
	return a, b, aok && bok
}

// MarkPrepared records that CREATE_FIGHT has been sent for this duel, and
// returns false if it was already marked (guards against double-send if
// both SET_READY_FOR_FIGHT packets race).
func (d *Duel) MarkPrepared() (firstTime bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.prepared {
		return false
	}
	d.prepared = true
	return true
}

// IsPrepared reports whether CREATE_FIGHT has already been sent for this
// duel, without mutating any state -- used by the SET_READY_FOR_FIGHT
// forced-progress timer to check whether the gate was already satisfied
// (by the other path) before it fires.
func (d *Duel) IsPrepared() bool {
	d.mu.Lock()
	defer d.mu.Unlock()
	return d.prepared
}

// MarkPresentationStarted records that this duel has begun (or is
// beginning) its teleport/instantiateFight/START_PRESENTATION transition,
// and returns false if it was already marked -- guards against the normal
// TEAM_MATE_SET_READY_FOR_PLACEMENT handler and the placement-ready
// forced-progress timer racing each other into a double
// teleport/instantiateFight/START_PRESENTATION sequence.
func (d *Duel) MarkPresentationStarted() (firstTime bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.presentationStarted {
		return false
	}
	d.presentationStarted = true
	return true
}

// SetPlacementReady records a coach's second-stage ("placement") readiness
// and returns true once both coaches are ready, mirroring
// TeamMateSetReadyForPlacement's separate readiness gate.
func (d *Duel) SetPlacementReady(coachID uint) (bothReady bool) {
	d.mu.Lock()
	defer d.mu.Unlock()
	d.placementReady[coachID] = true
	return len(d.placementReady) >= 2
}

// PlacementReadyCoaches returns the set of coach IDs that have already
// signaled placement-readiness, used by the forced-progress timer to
// determine which (if any) coach still needs to be force-readied when the
// timeout fires.
func (d *Duel) PlacementReadyCoaches() map[uint]bool {
	d.mu.Lock()
	defer d.mu.Unlock()
	out := make(map[uint]bool, len(d.placementReady))
	for k, v := range d.placementReady {
		out[k] = v
	}
	return out
}

// SelectedCoaches returns the set of coach IDs that have already submitted
// a fighter selection via SET_READY_FOR_FIGHT, used by the
// SET_READY_FOR_FIGHT forced-progress timer to determine which coach(es)
// never selected fighters at all when the timeout fires.
func (d *Duel) SelectedCoaches() map[uint]bool {
	d.mu.Lock()
	defer d.mu.Unlock()
	out := make(map[uint]bool, len(d.selections))
	for k := range d.selections {
		out[k] = true
	}
	return out
}

// ArmReadyTimer (re)arms this duel's single forced-progress timer,
// replacing any previously-armed one (e.g. the SET_READY_FOR_FIGHT timer
// being superseded by the TEAM_MATE_SET_READY_FOR_PLACEMENT timer once
// CREATE_FIGHT is sent). fire is invoked from a separate goroutine after d
// elapses unless CancelReadyTimer is called first.
func (d *Duel) ArmReadyTimer(delay time.Duration, fire func()) {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.readyTimer != nil {
		d.readyTimer.Stop()
	}
	d.readyTimer = time.AfterFunc(delay, fire)
}

// CancelReadyTimer stops this duel's forced-progress timer, if any is
// currently armed. Safe to call even if no timer is armed (no-op).
func (d *Duel) CancelReadyTimer() {
	d.mu.Lock()
	defer d.mu.Unlock()
	if d.readyTimer != nil {
		d.readyTimer.Stop()
		d.readyTimer = nil
	}
}

// DuelManager tracks all in-progress duels, replacing the legacy
// FightManager (ID allocation) combined with the surviving entries in
// WaitingOpponent's list after a match is found. Unlike the legacy
// version, matched-and-completed duels are actually removed here (the
// legacy WaitingOpponent list leaks matched entries forever).
type DuelManager struct {
	nextID atomic.Int64

	mu    sync.RWMutex
	duels map[int64]*Duel
}

// NewDuelManager creates an empty duel registry.
func NewDuelManager() *DuelManager {
	m := &DuelManager{duels: make(map[int64]*Duel)}
	m.nextID.Store(1)
	return m
}

// Create allocates and registers a new Duel between two matched coaches.
func (m *DuelManager) Create(coachA, coachB uint, fightType byte, bet int32) *Duel {
	id := m.nextID.Add(1) - 1
	d := newDuel(id, coachA, coachB, fightType, bet)

	m.mu.Lock()
	m.duels[id] = d
	m.mu.Unlock()

	return d
}

// Get looks up a duel by ID.
func (m *DuelManager) Get(id int64) (*Duel, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	d, ok := m.duels[id]
	return d, ok
}

// GetByCoach finds any active duel involving coachID.
//
// NOTE: this mirrors a quirk in the legacy
// World.getCoachExchangeById(coach, exchangeId) equivalent for fights
// (WaitingOpponent.getOpponentByCoach(coach)) which looks up purely by
// coach identity -- there's no notion of "which specific duel ID" being
// disambiguated, since a coach can only be in one duel at a time in
// practice. Preserved here for behavioral parity.
func (m *DuelManager) GetByCoach(coachID uint) (*Duel, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	for _, d := range m.duels {
		if d.InvolvesCoach(coachID) {
			return d, true
		}
	}
	return nil, false
}

// Remove unregisters a duel (fight created/canceled/completed), also
// canceling its forced-progress ready timer if one is still armed --
// necessary so a canceled/completed duel's timer never fires late and
// tries to act on a duel that's no longer registered.
func (m *DuelManager) Remove(id int64) {
	m.mu.Lock()
	d := m.duels[id]
	delete(m.duels, id)
	m.mu.Unlock()

	if d != nil {
		d.CancelReadyTimer()
	}
}
