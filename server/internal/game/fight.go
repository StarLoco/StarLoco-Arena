package game

import (
	"fmt"
	"math/rand"
	"strings"
	"sync"
	"sync/atomic"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Fight phases (server-authoritative state machine).
type FightPhase int32

const (
	PhasePresentation FightPhase = iota
	PhasePlacement
	PhaseObservation
	PhaseAction
	PhaseEnded
)

// FighterWireIDBase offsets DB fighter ids into the wire id space so they never
// collide with coach ids (which are also i64 on the wire).
const FighterWireIDBase int64 = 1 << 40

// Pos is a fight-map cell position.
type Pos struct {
	X int32
	Y int32
	Z int16
}

// FightFighter is one combatant instance in a fight.
type FightFighter struct {
	WireID  int64
	CoachID uint
	TeamID  uint8
	Fighter *domain.Fighter
	Pos     Pos
	// Orientation is the fighter's visual facing (a qc_0 direction index, 0-7 iso
	// + 8/9 top/bottom). Purely cosmetic in 2.70 — directional damage was dropped
	// (see BUGS.md B-037) — but the client animates it, so a change is broadcast
	// (4522). Default 0 (EAST); updated by the 4521 direction-change request.
	Orientation uint8
	// Combat stats, derived from breed base + equipped-card passive bonuses
	// (see computeFighterStats). Max* are the per-turn ceilings; HP/AP/MP are the
	// live values. Init orders the turn timeline.
	HP, MaxHP int32
	AP, MaxAP int32
	MP, MaxMP int32
	Init      int32
	Range     int32 // range-boost stat (from cards); extends boostable spell range
	// CritRate/FumbleRate are the critical-hit / fumble percentages rolled per cast
	// (breed base 5/1 + card & in-fight buffs, actions 70/71).
	CritRate, FumbleRate int32
	// Block/Dodge are the tackle percentages: Block holds an adjacent enemy in
	// place, Dodge slips away (see tackle.go). Client characs Lr.brd / Lr.bre.
	Block, Dodge int32
	// NbSummons is the NB_SUMMONS characteristic (client id 26): extra simultaneous
	// summons above the base of one. Base 0; raised by an action-74 buff ("+1
	// invocation", e.g. the Sadida/Osamodas/Rogue coach buff) and reverted on
	// expiry. Read by the canSummon cast criterion (see criteria.go).
	NbSummons int32
	// Stats is the fighter's elemental damage/resistance profile (from equipped
	// cards + in-fight buffs), consumed by the damage formula (combat_stats.go).
	Stats combatStats
	// Buffs are the active timed characteristic buffs/debuffs on this fighter.
	// Resource buffs (AP/MP/HP/Range) are applied to the fields above and reverted
	// when they expire; pure-stat buffs are tracked only so the count is visible.
	Buffs []*activeBuff
	// States are active status effects (rooted/petrified/stabilized/invisible/
	// immune) keyed to their remaining table-turn count (see states.go). The
	// server enforces them (block move/push/damage, skip turn); the client renders
	// them from the state running-effect.
	States map[fighterState]int32
	// stateSrc records the source effectId that set each active state, so action
	// 149 ("Retire un effet") can strip a specific state by its effectId (e.g. a
	// mask-switch spell removing the previously-worn mask). A stale entry (state
	// already gone) is harmless — 149 just finds nothing to remove.
	stateSrc map[fighterState]int32
	// Carry links (58 "Porter" / 59 "Jeter"): CarriedFighter is who this fighter
	// is carrying; CarriedByFighter is who is carrying this fighter. Bidirectional.
	CarriedFighter   *FightFighter
	CarriedByFighter *FightFighter
	// transfer is an active damage-transfer link (129): a share of the damage this
	// fighter takes is redirected to transfer.to (see spell_effects.go).
	transfer *damageTransfer
	// CastHistory enforces each spell's cast-frequency limits (min interval / max
	// per turn / max per target) for this fighter — see spell_cast_history.go.
	CastHistory spellCastHistory
	// Poisons are active recurring DoTs on this fighter, ticked each new table turn
	// (see tickPoisons).
	Poisons []*activePoison
	// Father is the fighter that summoned this one (nil for a real coach fighter).
	// A fighter with a Father is a server-AI-driven summon: no client sends its
	// input, so the built-in AI plays its turn (see ai.go).
	Father *FightFighter
	// SummonSpellID is the single spell a summoned creature casts (0 = a spell-less
	// blocker). Set from its Summoning template; drives the summon AI's behaviour.
	SummonSpellID int32
}

// isSummon reports whether this fighter is a summoned creature.
func (ff *FightFighter) isSummon() bool { return ff != nil && ff.Father != nil }

// damageTransfer is an active "Transfert de dommages" (129) link: `pct` percent
// of the damage the bearer takes is redirected to `to`, for `turns` table-turns.
type damageTransfer struct {
	to    *FightFighter
	pct   int32
	turns int32
}

// activePoison is a recurring poison DoT (action 61) on a fighter: it re-rolls
// its damage from `params` and re-applies it (bypassing resistance) at each new
// table turn until `turnsLeft` reaches zero. Ported from the v2.04b
// ActiveEffectPoisonTick model.
type activePoison struct {
	caster    *FightFighter
	actionID  int32
	effectID  int32
	params    []float32
	turnsLeft int32 // infiniteStateTurns for an infinite DoT
}

// FightTeam is one side of a fight.
type FightTeam struct {
	ID      uint8
	Coach   *domain.Coach
	Session *Session
	// Absent is set when the team's (real) coach dropped its connection mid-fight:
	// the fight is kept alive (reconnect-ready) but the team's fighters auto-pass
	// their turns and a grace timer forfeits them if the coach doesn't return.
	Absent   bool
	Fighters []*FightFighter
}

// Phase/turn clock durations (force-advance if a coach never signals). Kept as
// vars so tests can shorten them.
var (
	presentationClock = 20 * time.Second
	placementClock    = 30 * time.Second
	observationClock  = 10 * time.Second
	turnClock         = 30 * time.Second
	// aiTurnClock is how long a session-less (sparring/AI) fighter's turn lingers
	// before it auto-passes. Short enough to keep practice fights snappy, long
	// enough for the client to render the turn-begin. A human fighter always gets
	// the full turnClock. Kept as a var so tests can shorten it.
	aiTurnClock = 1200 * time.Millisecond
	// disconnectGraceClock is how long a fight is held open after a coach drops its
	// connection, so it can reconnect (the 2.70 client supports resume) before the
	// fight forfeits the absent coach. Kept as a var so tests can shorten it.
	disconnectGraceClock = 60 * time.Second
)

// Fight is an in-progress 1v1 fight between two coaches.
type Fight struct {
	ID        int64
	FightType int32
	// Practice marks an unranked fight (the "Tester" sparring bout and the PvE
	// challenges): no win/loss stats are persisted and no ladder movement occurs.
	Practice bool
	// ChallengeID is the type-400 challenge this fight realises (0 = not a
	// challenge). Set for the overworld DemonChallenge / BreedMaster fights; on
	// victory it selects the reward cards to grant. See challenge_fights.go.
	ChallengeID int32
	// Evolution marks the LETHAL fight mode (client aKl()==6): a fighter that
	// falls to 0 HP dies for good (→ evolution state "dead"), filling the
	// graveyard from real play. Only evolution fights persist deaths; ranked,
	// practice and PvE-challenge fights never do. See persistEvolutionDeaths.
	Evolution bool
	Bet       int64
	Teams     [2]*FightTeam
	Timeline  []*FightFighter // initiative-descending turn order

	phase atomic.Int32 // read from any goroutine; written only by the actor
	deps  *Deps

	// --- state below is owned SOLELY by the fight actor goroutine (run()) ---
	// No locks: all access happens inside events posted to the mailbox.
	readyPresent map[uint]bool
	readyObserve map[uint]bool
	readyAction  map[uint]bool
	turnIndex    int
	tableTurn    int32 // 1-indexed round number, incremented per table turn
	// tableEvent is the EVENT CARD drawn for the current round (events.go); 0 =
	// none. eventDeck/eventDeckPos are this fight's shuffled draw pile, dealt one
	// card per round and reshuffled once exhausted.
	tableEvent   int32
	eventDeck    []int32
	eventDeckPos int
	summonSeq    int32        // per-fight counter for allocating unique summon wire ids
	actionUID    atomic.Int32 // atomic: packet builders may read across goroutines

	// effectAreas are live traps/glyphs placed by action-66 spells (see
	// effectarea.go). effectAreaSeq allocates their unique ids; inAreaTrigger
	// guards trap re-entrancy while an area's inner effects are being replayed.
	effectAreas []*effectArea
	// cellBuffs are the turn-long stat bonuses granted by special battlefield
	// cells this turn, reverted when the granting fighter's turn ends
	// (specialcells.go).
	cellBuffs []cellBuff
	// destroyedCells are the arena cells removed by sudden death in THIS fight
	// (suddendeath.go). Per-fight, because the arena value itself is shared.
	// suddenDeathDone makes the collapse fire exactly once.
	// arena is the map this fight is played on. nil falls back to the hand-decoded
	// world 5 (see arena_registry.go), which is what unit tests build against.
	arena          *arena
	destroyedCells map[[2]int32]bool
	// suddenDeathStep is how many shrink steps have run (an index into
	// suddenDeathSchedule). 0 means the collapse has not started.
	suddenDeathStep int
	effectAreaSeq   int64
	inAreaTrigger   bool

	// rng is the fight's damage/dice source (lazily seeded via roll()). Tests may
	// set it directly for deterministic rolls.
	rng *rand.Rand

	// clock is the current phase/turn timer; clockGen guards a stale timer.
	clock    *time.Timer
	clockGen uint64

	// graceTimer forfeits a disconnected coach if it doesn't reconnect in time. It
	// is INDEPENDENT of the turn clock (its own generation guard) so arming a turn
	// clock never cancels the reconnect grace.
	graceTimer *time.Timer
	graceGen   uint64

	// spectators are read-only viewers watching the fight (see handlers_spectate.go).
	// Owned SOLELY by the fight actor: added on a spectate-join, removed on a
	// spectator disconnect. Every broadcast also reaches them.
	spectators []*Session

	// actor plumbing
	mailbox  chan fightEvent
	done     chan struct{}
	stopped  chan struct{} // closed when run() exits (test synchronization)
	stopOnce sync.Once
}

// Phase returns the current phase.
func (f *Fight) Phase() FightPhase { return FightPhase(f.phase.Load()) }

func (f *Fight) setPhase(p FightPhase) { f.phase.Store(int32(p)) }

// armClock schedules ev to run on the fight goroutine after d, unless the fight
// advances or ends first. MUST be called from inside the actor (an event), so
// no locking is needed. The generation guard (checked in postAfter) drops a
// stale timer after a manual advance.
func (f *Fight) armClock(d time.Duration, ev fightEvent) {
	f.stopClock()
	f.clockGen++
	f.clock = f.postAfter(d, f.clockGen, ev)
}

// stopClock cancels any pending timer. Called from inside the actor.
func (f *Fight) stopClock() {
	f.clockGen++
	if f.clock != nil {
		f.clock.Stop()
		f.clock = nil
	}
}

// nextActionUID returns a fresh action unique id for turn/action messages.
// Atomic so timer + handler goroutines can call it concurrently.
func (f *Fight) nextActionUID() int32 {
	return f.actionUID.Add(1)
}

// Arena returns the map this fight is played on, falling back to world 5 when the
// fight was built without one (unit tests, or a server with no map data).
func (f *Fight) Arena() *arena {
	if f != nil && f.arena != nil {
		return f.arena
	}
	return &practiceArena
}

// allFighters returns every fighter across both teams.
func (f *Fight) allFighters() []*FightFighter {
	var out []*FightFighter
	for _, t := range f.Teams {
		if t != nil {
			out = append(out, t.Fighters...)
		}
	}
	return out
}

// sessions returns both coaches' sessions.
func (f *Fight) sessions() []*Session {
	var out []*Session
	for _, t := range f.Teams {
		if t != nil && t.Session != nil {
			out = append(out, t.Session)
		}
	}
	return out
}

// broadcast sends a frame to both coaches AND every attached spectator.
func (f *Fight) broadcast(frame []byte) {
	for _, s := range f.sessions() {
		_ = s.Send(frame)
	}
	for _, s := range f.spectators {
		_ = s.Send(frame)
	}
}

// broadcastSpectators sends a frame to the attached viewers only. Used when the
// two coaches must each receive a personalised variant of a frame (END_FIGHT
// carries a per-coach reputation award) while spectators get a neutral one.
func (f *Fight) broadcastSpectators(frame []byte) {
	for _, s := range f.spectators {
		_ = s.Send(frame)
	}
}

// addSpectator attaches a viewer's session (idempotent). Actor-only.
func (f *Fight) addSpectator(s *Session) {
	for _, existing := range f.spectators {
		if existing == s {
			return
		}
	}
	f.spectators = append(f.spectators, s)
}

// removeSpectator detaches a viewer's session. Actor-only.
func (f *Fight) removeSpectator(s *Session) {
	kept := f.spectators[:0]
	for _, existing := range f.spectators {
		if existing != s {
			kept = append(kept, existing)
		}
	}
	f.spectators = kept
}

// fighterByWireID finds a fighter by wire id.
func (f *Fight) fighterByWireID(id int64) *FightFighter {
	for _, ff := range f.allFighters() {
		if ff.WireID == id {
			return ff
		}
	}
	return nil
}

// isCurrentTurn reports whether the given fighter wire id owns the current turn.
// Called from inside the actor (no lock needed).
func (f *Fight) isCurrentTurn(wireID int64) bool {
	if f.turnIndex < 0 || f.turnIndex >= len(f.Timeline) {
		return false
	}
	return f.Timeline[f.turnIndex].WireID == wireID
}

// currentFighter returns the fighter whose turn it is (nil if out of range).
func (f *Fight) currentFighter() *FightFighter {
	if f.turnIndex < 0 || f.turnIndex >= len(f.Timeline) {
		return nil
	}
	return f.Timeline[f.turnIndex]
}

// applyDirectionChange validates and applies a fighter's facing change, returning
// the fighter on success or nil when the change is not allowed. A coach may only
// turn its OWN, LIVING fighter, and only on that fighter's turn (facing is a free
// action of the acting fighter). The caller broadcasts 4522 on success. Cosmetic
// only — no AP/MP/position changes — so a rejected change is a silent no-op.
// Called from inside the actor (no lock needed).
func (f *Fight) applyDirectionChange(coachID uint, wireID int64, dir uint8) *FightFighter {
	ff := f.fighterByWireID(wireID)
	if ff == nil || ff.CoachID != coachID || ff.HP <= 0 || !f.isCurrentTurn(wireID) {
		return nil
	}
	ff.Orientation = dir
	return ff
}

// teamOfCoach returns the team owned by coachID (nil if none).
func (f *Fight) teamOfCoach(coachID uint) *FightTeam {
	for _, t := range f.Teams {
		if t != nil && t.Coach != nil && t.Coach.ID == coachID {
			return t
		}
	}
	return nil
}

// teamAbsent reports whether ff's team belongs to a coach that has disconnected.
func (f *Fight) teamAbsent(ff *FightFighter) bool {
	t := f.teamOf(ff)
	return t != nil && t.Absent
}

// allTeamsAbsent reports whether every real (coached) team has disconnected — the
// fight has no one left to play or win, so it should be torn down.
func (f *Fight) allTeamsAbsent() bool {
	real, absent := 0, 0
	for _, t := range f.Teams {
		if t == nil || t.Coach == nil {
			continue
		}
		real++
		if t.Absent {
			absent++
		}
	}
	return real > 0 && absent >= real
}

// teamOf returns the fight team a fighter belongs to (nil if not found).
func (f *Fight) teamOf(ff *FightFighter) *FightTeam {
	for _, t := range f.Teams {
		if t == nil {
			continue
		}
		for _, x := range t.Fighters {
			if x == ff {
				return t
			}
		}
	}
	return nil
}

// isAIControlled reports whether a fighter has no live coach session driving it
// and is therefore played by the built-in AI (ai.go): a synthetic sparring
// opponent (its team has no session) OR a summoned creature (has a Father — its
// team may belong to a real coach, but the client never sends a summon's input).
// Such fighters take their turn via runAITurn instead of waiting on player input.
func (f *Fight) isAIControlled(ff *FightFighter) bool {
	if ff != nil && ff.Father != nil {
		return true
	}
	t := f.teamOf(ff)
	return t == nil || t.Session == nil
}

// FightManager tracks active fights by id and by coach.
type FightManager struct {
	mu      sync.Mutex
	byID    map[int64]*Fight
	byCoach map[uint]*Fight
	nextID  int64
}

// NewFightManager creates an empty manager.
func NewFightManager() *FightManager {
	return &FightManager{byID: make(map[int64]*Fight), byCoach: make(map[uint]*Fight), nextID: 1}
}

// Create registers a new fight for two coaches.
func (m *FightManager) Create(f *Fight) {
	m.mu.Lock()
	defer m.mu.Unlock()
	f.ID = m.nextID
	m.nextID++
	m.byID[f.ID] = f
	for _, t := range f.Teams {
		if t != nil && t.Coach != nil {
			m.byCoach[t.Coach.ID] = f
		}
	}
}

// DebugDump returns a human-readable snapshot of active fights (dev inject only).
func (m *FightManager) DebugDump() string {
	m.mu.Lock()
	defer m.mu.Unlock()
	var b strings.Builder
	fmt.Fprintf(&b, "active fights: %d\n", len(m.byID))
	for _, f := range m.byID {
		f.writeSnapshot(&b)
	}
	return b.String()
}

// writeSnapshot formats one fight's live state (phase, current turn, every
// fighter's stats + states) into b. Reads actor-owned fields (turnIndex,
// Timeline), so callers that need a race-free read must run it on the fight
// goroutine (the /script endpoint does; the /fight dump tolerates the dev race).
func (f *Fight) writeSnapshot(b *strings.Builder) {
	cur := int64(0)
	if f.turnIndex >= 0 && f.turnIndex < len(f.Timeline) {
		cur = f.Timeline[f.turnIndex].WireID
	}
	fmt.Fprintf(b, "fight %d phase=%d practice=%v round=%d timeline=%d cur=%d\n",
		f.ID, f.Phase(), f.Practice, f.tableTurn, len(f.Timeline), cur)
	for _, t := range f.Teams {
		if t == nil {
			continue
		}
		for _, ff := range t.Fighters {
			name := ""
			nSpells, nObj := 0, 0
			var spellIDs, objIDs []int32
			if ff.Fighter != nil {
				name = ff.Fighter.Name
				nSpells = len(ff.Fighter.Spells)
				nObj = len(ff.Fighter.Objects)
				for _, sp := range ff.Fighter.Spells {
					spellIDs = append(spellIDs, sp.SpellID)
				}
				for _, obj := range ff.Fighter.Objects {
					objIDs = append(objIDs, obj.TemplateID)
				}
			}
			fmt.Fprintf(b, "  side=%d wire=%d name=%q breed=%d pos=(%d,%d,%d) hp=%d/%d ap=%d/%d mp=%d/%d init=%d crit=%d/%d spells=%d%v objects=%d%v %s %s\n",
				t.ID, ff.WireID, name, breedOf(ff), ff.Pos.X, ff.Pos.Y, ff.Pos.Z,
				ff.HP, ff.MaxHP, ff.AP, ff.MaxAP, ff.MP, ff.MaxMP, ff.Init, ff.CritRate, ff.FumbleRate, nSpells, spellIDs, nObj, objIDs, ff.stateSummary(), ff.Stats.summary())
		}
	}
	for _, a := range f.effectAreas {
		fmt.Fprintf(b, "  area id=%d template=%d type=%s cell=(%d,%d) shape=%d maxExec=%d effects=%d\n",
			a.id, a.templateID, a.tmpl.Type, a.center.X, a.center.Y, a.tmpl.AreaShape, a.maxExec, len(a.tmpl.Effects))
	}
}

// Only returns the single active fight if there is exactly one, else nil. The
// dev /script endpoint uses it so a scenario needn't name the fight id when a
// lone practice fight is running.
func (m *FightManager) Only() *Fight {
	m.mu.Lock()
	defer m.mu.Unlock()
	if len(m.byID) != 1 {
		return nil
	}
	for _, f := range m.byID {
		return f
	}
	return nil
}

// Get returns the fight with the given id, or nil.
func (m *FightManager) Get(id int64) *Fight {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.byID[id]
}

func breedOf(ff *FightFighter) uint8 {
	if ff.Fighter != nil {
		return ff.Fighter.BreedID
	}
	return 0
}

// ByCoach returns the fight a coach is in, or nil.
func (m *FightManager) ByCoach(coachID uint) *Fight {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.byCoach[coachID]
}

// Remove deregisters a fight.
func (m *FightManager) Remove(f *Fight) {
	if f == nil {
		return
	}
	m.mu.Lock()
	defer m.mu.Unlock()
	delete(m.byID, f.ID)
	for _, t := range f.Teams {
		if t != nil && t.Coach != nil {
			delete(m.byCoach, t.Coach.ID)
		}
	}
}
