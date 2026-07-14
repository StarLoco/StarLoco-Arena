package botai

import (
	"fmt"
	"math/rand"
	"strings"
	"time"

	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// Driver runs a fight to completion over a botclient.Client using an AI. It
// assumes the caller has already reached the point where CREATE_FIGHT (8000)
// is the next meaningful frame the client will receive (i.e. matchmaking +
// SET_READY_FOR_FIGHT are done). Driver then walks the phase ready-gates,
// plays every turn belonging to the bot's own fighters via the AI, and acks
// END_FIGHT.
//
// It is intentionally resilient: any action the server silently drops
// (an illegal move/cast) simply yields no echo, and the turn falls through
// to END_TURN, so a fight always makes progress toward a KO or a timeout
// rather than deadlocking.
type Driver struct {
	Client  *botclient.Client
	CoachID int64
	Book    SpellBook
	AI      AI
	RNG     *rand.Rand

	// FrameTimeout bounds waiting for the next fight frame. 0 uses a
	// sensible default. A fight with two idle-ish AIs still emits turn
	// frames well within this.
	FrameTimeout time.Duration

	// ActionPause, if > 0, is slept between the bot's own actions so a
	// human watching via a real client sees the fight play out at a
	// visible pace instead of instantly. 0 = as fast as possible.
	ActionPause time.Duration
}

// FightOutcome summarizes how a driven fight concluded.
type FightOutcome struct {
	Turns   int
	Won     bool // best-effort: true if any of our fighters outlived all enemies
	Ended   bool // END_FIGHT observed and acked
	Elapsed time.Duration
}

// RunFight drives the fight from CREATE_FIGHT through END_FIGHT_DONE. The
// caller passes the CREATE_FIGHT payload it already consumed (so Driver can
// seed state without racing the read pump); pass nil to have Driver wait
// for it.
func (d *Driver) RunFight(createFightPayload []byte) (FightOutcome, error) {
	start := time.Now()
	if d.FrameTimeout <= 0 {
		d.FrameTimeout = 40 * time.Second
	}
	if d.RNG == nil {
		d.RNG = rand.New(rand.NewSource(time.Now().UnixNano()))
	}
	state := NewFightState(d.CoachID)

	if createFightPayload != nil {
		state.Ingest(botclient.Frame{Opcode: protocol.SendCreateFight, Payload: createFightPayload})
	}

	// Advance through the three ready-gates. The server also advances on
	// its per-phase clocks, so even if a gate frame is missed the fight
	// proceeds; we send the votes eagerly and let frame ingestion continue.
	_ = d.Client.ReadyForPlacement()
	_ = d.Client.ReadyForObservation()
	_ = d.Client.ReadyForAction()

	out := FightOutcome{}
	deadline := time.Now().Add(3 * time.Minute) // absolute safety cap
	forfeited := false

	// idleTimeout bounds how long we wait for the NEXT meaningful fight
	// frame before assuming the fight has stalled (e.g. the opponent bot
	// desynced or moved on). On the first stall we forfeit -- which ends
	// the fight cleanly for BOTH bots (END_FIGHT is then broadcast) rather
	// than both sides hanging until their sockets time out. A stall is
	// normal at swarm scale (the paired bot may itself be stuck), so a
	// forfeit-to-terminate is the correct, non-fatal outcome.
	idleTimeout := d.FrameTimeout
	if idleTimeout <= 0 || idleTimeout > 25*time.Second {
		idleTimeout = 25 * time.Second
	}

	for {
		if time.Now().After(deadline) {
			return out, fmt.Errorf("botai: fight exceeded safety deadline (turns=%d)", out.Turns)
		}
		f, err := d.Client.Recv(idleTimeout)
		if err != nil {
			if isTimeout(err) && !forfeited {
				// Stalled: forfeit to force the fight to end, then keep
				// reading for the resulting END_FIGHT.
				forfeited = true
				_ = d.Client.GiveUp()
				continue
			}
			return out, fmt.Errorf("botai: recv during fight: %w", err)
		}
		myTurn := state.Ingest(f)

		if state.Ended {
			out.Ended = true
			out.Won = len(state.MyLivingFighters()) > 0 && len(state.EnemyLivingFighters()) == 0
			// Ack END_FIGHT so the fight actor releases us.
			_ = d.Client.EndFightDone()
			// Drain the fight's trailing teardown frames (ENTER_WORLD_
			// INSTANCE returning us to the overworld, ACTOR_SPAWN refresh)
			// so they don't leak into whatever behavior the bot does next.
			d.drainSettle(1500 * time.Millisecond)
			out.Elapsed = time.Since(start)
			return out, nil
		}

		if myTurn && !forfeited {
			out.Turns++
			d.playTurn(state)
			// Safety valve: an extremely long fight (two evenly-matched
			// dumb bots that keep missing) forfeits after a turn cap so
			// the swarm keeps churning.
			if out.Turns >= maxDrivenTurns {
				forfeited = true
				_ = d.Client.GiveUp()
			}
		}
	}
}

// maxDrivenTurns caps how many of our own turns a single driven fight plays
// before forfeiting, so a pathological stalemate can't tie up two bots for
// the whole run. Generous enough that a normal melee KO (a handful of turns)
// always finishes first.
const maxDrivenTurns = 40

// isTimeout reports whether err is a botclient recv timeout (as opposed to a
// closed connection or a protocol error).
func isTimeout(err error) bool {
	return err != nil && strings.Contains(err.Error(), "timeout")
}

// drainSettle reads and discards frames for up to d, returning early once a
// short quiet gap (no frame within a small poll window) is observed. This
// swallows the fight's post-END teardown broadcasts so they don't leak into
// the bot's next behavior.
func (d *Driver) drainSettle(total time.Duration) {
	deadline := time.Now().Add(total)
	for time.Now().Before(deadline) {
		if _, err := d.Client.Recv(150 * time.Millisecond); err != nil {
			return // timeout (quiet) or closed -- done settling
		}
	}
}

// playTurn asks the AI for intents, executes them in order (pausing for
// visibility if configured), then always ends the turn. Between intents it
// does NOT re-block on frames (that would risk consuming the opponent's
// turn frames); the AI plans a full sequence up front and the server
// validates each action, so a best-effort in-order execution is correct.
func (d *Driver) playTurn(state *FightState) {
	me := state.Fighters[state.CurrentTurn]
	if me == nil {
		return
	}
	intents := d.AI.PlanTurn(state, d.Book, d.RNG)
	for _, in := range intents {
		d.execIntent(in)
		if d.ActionPause > 0 {
			time.Sleep(d.ActionPause)
		}
	}
	// End our turn so the fight progresses.
	if d.ActionPause > 0 {
		time.Sleep(d.ActionPause)
	}
	_ = d.Client.EndTurn(me.WireID)
}

func (d *Driver) execIntent(in Intent) {
	switch in.Kind {
	case IntentMove:
		_ = d.Client.MoveFighter(in.FighterID, in.Target.toClient())
	case IntentCast:
		_ = d.Client.CastSpell(in.FighterID, in.SpellID, in.Target.toClient())
	case IntentCloseCombat:
		_ = d.Client.CloseCombat(in.FighterID, in.Target.toClient())
	case IntentEndTurn:
		_ = d.Client.EndTurn(in.FighterID)
	}
}
