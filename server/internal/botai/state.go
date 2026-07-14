// Package botai provides a fight-observation model and pluggable turn AIs
// for automated coaches. It is deliberately transport-agnostic: FightState
// is fed raw server frames (via Ingest) and the AI implementations emit
// high-level Intents, so the exact same brain can drive a network bot
// (cmd/botswarm, via Driver) today and a server-side PvE "AI coach" against
// real players later.
//
// The observation is best-effort from the wire alone: the bot learns its
// own fighters and their spell ids from CREATE_FIGHT, every fighter's
// position from ACTOR_APPEAR and subsequent FIGHTER_MOVE frames, deaths
// from FIGHTER_DIES, and whose turn it is from FIGHTER_TURN_BEGIN. It does
// NOT have the server's authoritative HP/AP/MP or map walkability, so the
// AIs act conservatively (attempt an action; if the server drops it, fall
// back) rather than assuming a move/cast will always be accepted.
package botai

import (
	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// fighterWireIDBase mirrors combat.FighterWireIDBase: a fighter's on-wire id
// is its DB id plus this offset. Duplicated here to avoid importing the
// whole combat package for one constant.
const fighterWireIDBase int64 = 1_000_000_000

// Cell is a fight-map coordinate. Mirrors botclient.Cell; kept separate so
// botai has no forced dependency direction beyond the client used by Driver.
type Cell struct {
	X int32
	Y int32
	Z int16
}

func (c Cell) toClient() botclient.Cell { return botclient.Cell{X: c.X, Y: c.Y, Z: c.Z} }

// manhattan returns the grid distance between two cells (ignoring Z, which
// is altitude, not planar distance).
func manhattan(a, b Cell) int32 {
	dx := a.X - b.X
	if dx < 0 {
		dx = -dx
	}
	dy := a.Y - b.Y
	if dy < 0 {
		dy = -dy
	}
	return dx + dy
}

// ObservedFighter is what the bot knows about one fighter on the field.
type ObservedFighter struct {
	WireID  int64
	CoachID int64 // owning coach (0 if unknown, e.g. summons)
	Breed   byte
	Name    string
	Mine    bool // belongs to this bot's coach
	Alive   bool
	Pos     Cell
	HasPos  bool
	// SpellIDs is populated only for the bot's own fighters (from
	// CREATE_FIGHT); opponent spell lists are not sent to us.
	SpellIDs []int32
}

// FightState is the bot's evolving picture of a fight. All mutation happens
// through Ingest on the bot's own goroutine (one FightState per fight), so
// it needs no locking.
type FightState struct {
	MyCoachID int64

	Fighters map[int64]*ObservedFighter // keyed by wire id

	// CurrentTurn is the wire id of the fighter whose turn it is, or 0 if
	// no turn is active yet.
	CurrentTurn int64

	// Ended is set when END_FIGHT arrives.
	Ended bool
}

// NewFightState creates an empty state for the given owning coach.
func NewFightState(myCoachID int64) *FightState {
	return &FightState{
		MyCoachID: myCoachID,
		Fighters:  make(map[int64]*ObservedFighter),
	}
}

// MyLivingFighters returns the bot's own fighters that are still alive.
func (s *FightState) MyLivingFighters() []*ObservedFighter {
	var out []*ObservedFighter
	for _, f := range s.Fighters {
		if f.Mine && f.Alive {
			out = append(out, f)
		}
	}
	return out
}

// EnemyLivingFighters returns living fighters not owned by this bot.
func (s *FightState) EnemyLivingFighters() []*ObservedFighter {
	var out []*ObservedFighter
	for _, f := range s.Fighters {
		if !f.Mine && f.Alive {
			out = append(out, f)
		}
	}
	return out
}

// Ingest updates the state from one server frame. It returns true if the
// frame was a turn-begin for one of the bot's own fighters (a cue for the
// Driver to act). Unrecognized frames are ignored.
func (s *FightState) Ingest(f botclient.Frame) (myTurn bool) {
	switch f.Opcode {
	case protocol.SendCreateFight:
		s.ingestCreateFight(f.Payload)
	case protocol.SendActorAppear:
		s.ingestActorAppear(f.Payload)
	case protocol.SendFighterMove:
		s.ingestFighterMove(f.Payload)
	case protocol.SendMoveToFreePlacement:
		s.ingestPlacement(f.Payload)
	case protocol.SendFighterDies:
		s.ingestFighterDies(f.Payload)
	case protocol.SendFighterTurnBegin:
		return s.ingestTurnBegin(f.Payload)
	case protocol.SendEndFight:
		s.Ended = true
	}
	return false
}

func (s *FightState) fighter(wireID int64) *ObservedFighter {
	fr, ok := s.Fighters[wireID]
	if !ok {
		fr = &ObservedFighter{WireID: wireID, Alive: true}
		s.Fighters[wireID] = fr
	}
	return fr
}

// ingestCreateFight parses CREATE_FIGHT (8000) to learn each team's coach
// and fighters (and, for the bot's own team, spell lists). See
// packets_fight.go buildCreateFight for the layout.
func (s *FightState) ingestCreateFight(payload []byte) {
	r := protocol.NewReader(payload)
	_ = r.Byte()          // error code
	skipBlob16(r)         // coach cards blob (unused)
	_ = r.Int32()         // fight type
	_ = r.Int32()         // bet
	teamCount := r.Byte() // team count (2)

	for t := 0; t < int(teamCount); t++ {
		_ = r.Byte()   // team id
		_ = r.String() // team name
		coachCount := r.Byte()
		for c := 0; c < int(coachCount); c++ {
			coachID := r.Int64()
			_ = r.String() // coach name
			_ = r.Byte()   // skin
			_ = r.Byte()   // hair
			_ = r.Byte()   // sex
			skipBlob16(r)  // equipped coach-card blob

			mine := coachID == s.MyCoachID
			fighterCount := r.Byte()
			for fi := 0; fi < int(fighterCount); fi++ {
				wireID := r.Int64()
				breed := r.Byte()
				name := r.String()
				_ = r.Byte() // sex
				_ = r.Byte() // skin

				spellBlobLen := int(r.Uint16())
				spellBlob := r.Bytes(spellBlobLen)
				objBlobLen := int(r.Uint16())
				_ = r.Bytes(objBlobLen)

				fr := s.fighter(wireID)
				fr.CoachID = coachID
				fr.Breed = breed
				fr.Name = name
				fr.Mine = mine
				fr.Alive = true
				if mine {
					fr.SpellIDs = parseInt32Slice(spellBlob)
				}
			}
			// per-coach statistics report length (unused)
			skipBlob16(r)
			// bet card list: byte count, then count × int32
			betCount := r.Byte()
			for b := 0; b < int(betCount); b++ {
				_ = r.Int32()
			}
		}
	}
	// The remaining timeline / event / special-cell tails are not needed
	// for AI decisions and are ignored.
}

// ingestActorAppear parses ACTOR_APPEAR (4102): byte count + per entry
// [int64 id, int32 x, int32 y, int16 z, byte dir]. This seeds fighter
// positions at fight start (and any teleport re-appear).
func (s *FightState) ingestActorAppear(payload []byte) {
	r := protocol.NewReader(payload)
	count := r.Byte()
	for i := 0; i < int(count); i++ {
		id := r.Int64()
		x := r.Int32()
		y := r.Int32()
		z := r.Int16()
		_ = r.Byte() // direction
		if r.Err() != nil {
			return
		}
		// Only track ids in the fighter wire-id range (coaches also appear
		// on the overworld but not in a fight scene; in a fight every
		// entry is a fighter).
		fr := s.fighter(id)
		fr.Pos = Cell{X: x, Y: y, Z: z}
		fr.HasPos = true
	}
}

// ingestFighterMove parses FIGHTER_MOVE (4524): 8-byte action header +
// int64 fighterID + path of [int32 x, int32 y, int16 z]; the LAST cell is
// the fighter's new position.
func (s *FightState) ingestFighterMove(payload []byte) {
	r := protocol.NewReader(payload)
	_ = r.Int32() // uniqueId
	_ = r.Int32() // triggeringId
	id := r.Int64()
	var last Cell
	have := false
	for r.Remaining() >= 10 {
		last = Cell{X: r.Int32(), Y: r.Int32(), Z: r.Int16()}
		have = true
	}
	if r.Err() != nil || !have {
		return
	}
	fr := s.fighter(id)
	fr.Pos = last
	fr.HasPos = true
}

// ingestPlacement parses MOVE_TO_FREE_PLACEMENT (8022): int64 fighterID +
// int32 x + int32 y + int16 z.
func (s *FightState) ingestPlacement(payload []byte) {
	r := protocol.NewReader(payload)
	id := r.Int64()
	x := r.Int32()
	y := r.Int32()
	z := r.Int16()
	if r.Err() != nil {
		return
	}
	fr := s.fighter(id)
	fr.Pos = Cell{X: x, Y: y, Z: z}
	fr.HasPos = true
}

// ingestFighterDies parses FIGHTER_DIES (4520): 8-byte header + int64
// fighterID.
func (s *FightState) ingestFighterDies(payload []byte) {
	r := protocol.NewReader(payload)
	_ = r.Int32()
	_ = r.Int32()
	id := r.Int64()
	if r.Err() != nil {
		return
	}
	s.fighter(id).Alive = false
}

// ingestTurnBegin parses FIGHTER_TURN_BEGIN (8104): 8-byte header + int64
// fighterID. Returns true if the active fighter is one of the bot's own.
func (s *FightState) ingestTurnBegin(payload []byte) bool {
	r := protocol.NewReader(payload)
	_ = r.Int32()
	_ = r.Int32()
	id := r.Int64()
	if r.Err() != nil {
		return false
	}
	s.CurrentTurn = id
	fr, ok := s.Fighters[id]
	return ok && fr.Mine && fr.Alive
}

// --- payload helpers ---

// skipBlob16 skips a uint16-length-prefixed byte blob.
func skipBlob16(r *protocol.Reader) {
	n := int(r.Uint16())
	if n > 0 {
		_ = r.Bytes(n)
	}
}

// parseInt32Slice reads a flat int32[] (the spell-blob format).
func parseInt32Slice(b []byte) []int32 {
	r := protocol.NewReader(b)
	var out []int32
	for r.Remaining() >= 4 {
		out = append(out, r.Int32())
	}
	return out
}
