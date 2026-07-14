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

	// Vote ready-for-placement now: the fight is in PRESENTATION and the
	// server accepts this vote (8011) only in that phase. The remaining two
	// ready votes are phase-gated, so we send each ONLY when we see its
	// phase begin (below) -- sending them early gets them silently dropped,
	// which is exactly what left fights waiting the full phase clocks (and,
	// under the swarm's broadcast flood, sometimes never reaching a turn).
	_ = d.Client.ReadyForPlacement()

	out := FightOutcome{}
	// Absolute safety cap. Kept modest so a genuinely wedged fight frees the
	// bot quickly instead of tying it up for minutes.
	deadline := time.Now().Add(90 * time.Second)
	forfeited := false

	// stallWindow bounds how long we tolerate NO meaningful fight progress
	// (a phase/turn/move/cast/death frame -- NOT the constant overworld
	// broadcast noise from other bots) before forfeiting to force the fight
	// to end. Tracking progress by MEANINGFUL frames (not raw Recv timeouts)
	// is essential: at swarm scale a fighting bot's socket is flooded with
	// other bots' ACTOR_MOVEMENT/VICINITY broadcasts, so Recv almost never
	// times out even when the fight itself is completely stuck -- the old
	// timeout-based stall detection never fired, and fights hung for minutes.
	stallWindow := d.FrameTimeout
	if stallWindow <= 0 || stallWindow > 20*time.Second {
		stallWindow = 20 * time.Second
	}
	lastProgress := time.Now()

	for {
		now := time.Now()
		if now.After(deadline) {
			// Last-resort: forfeit and give END_FIGHT a moment, else bail.
			if !forfeited {
				forfeited = true
				_ = d.Client.GiveUp()
				deadline = now.Add(8 * time.Second)
				continue
			}
			return out, fmt.Errorf("botai: fight exceeded safety deadline (turns=%d)", out.Turns)
		}
		if !forfeited && now.Sub(lastProgress) > stallWindow {
			forfeited = true
			_ = d.Client.GiveUp()
			lastProgress = now // give the forfeit->END_FIGHT a fresh window
			continue
		}

		// Short Recv timeout so the stall check runs promptly even amid a
		// heavy noise stream (each noisy frame returns fast; a genuine quiet
		// gap returns via timeout and we re-check the stall clock).
		f, err := d.Client.Recv(2 * time.Second)
		if err != nil {
			if isTimeout(err) {
				continue // re-evaluate stall/deadline, keep waiting
			}
			return out, fmt.Errorf("botai: recv during fight: %w", err)
		}

		if isFightProgressFrame(f.Opcode) {
			lastProgress = time.Now()
		}
		// React to phase starts with the phase-correct ready vote so both
		// bots skip the phase clocks and reach the action phase quickly.
		if !forfeited {
			switch f.Opcode {
			case protocol.SendStartPlacement:
				_ = d.Client.ReadyForObservation()
			case protocol.SendStartObservation:
				_ = d.Client.ReadyForAction()
			}
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

// isFightProgressFrame reports whether an opcode represents real fight
// progress (a phase transition, a turn, or an in-fight action/outcome) as
// opposed to the overworld broadcast noise that floods a fighting bot's
// socket at swarm scale. Used to detect a genuinely stalled fight.
func isFightProgressFrame(op protocol.SendOpcode) bool {
	switch op {
	case protocol.SendStartPresentation, protocol.SendEndPresentation,
		protocol.SendStartPlacement, protocol.SendEndPlacement,
		protocol.SendStartObservation, protocol.SendEndObservation,
		protocol.SendStartAction, protocol.SendNewTableTurnBegin,
		protocol.SendFighterTurnBegin, protocol.SendActorAppear,
		protocol.SendFighterMove, protocol.SendMoveToFreePlacement,
		protocol.SendFighterDies, protocol.SendEndFight:
		return true
	default:
		return false
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
