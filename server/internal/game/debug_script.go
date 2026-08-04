package game

import (
	"fmt"
	"net/http"
	"strconv"
	"strings"
	"time"
)

// debug_script.go adds the DEV-ONLY /script endpoint: a tiny fight-scenario
// runner that drives a live fight to completion IN-PROCESS, on the fight's own
// actor goroutine, in milliseconds.
//
// WHY: deep combat testing against the retail client was previously done by
// hand-driving raw /c2s opcode injections in a PowerShell poll-act loop. That
// fought two hard problems: (1) the 30s turn clock is far shorter than the
// slow inject+screenshot cadence, and (2) a short-duration (1-turn) buff/state
// applied in one tool call had already ticked away by the next. A scenario that
// must span several fighters' turns within ONE round (cast a state on fighter A,
// pass to B, have B act on the still-current state) could not be expressed
// reliably across separate slow calls.
//
// The /script endpoint solves both: it posts each step to the fight actor and
// waits for it synchronously, so an entire "A casts, advance to B, B attacks,
// dump" scenario runs atomically in a few milliseconds — no clock pressure, no
// inter-call gap for a state to expire, no brittle client-side parsing. The
// client still receives every real broadcast (cast/move/turn frames), so it is a
// faithful end-to-end test, just driven server-side.
//
// Commands (';' or newline separated, whitespace token args):
//
//	goto <wire> [ai]        advance turns until <wire> is current. Without "ai",
//	                        intermediate turns are force-ended (fast skip); with
//	                        "ai" a session-less/summon fighter actually plays its
//	                        AI turn first (to observe summon/sparring behaviour).
//	move <wire> <x> <y>     on <wire>'s turn: BFS a path and walk there.
//	cast <wire> <spell> <x> <y>   on <wire>'s turn: cast <spell> at cell (x,y).
//	castself <wire> <spell>       cast <spell> at the caster's own cell.
//	usecard <wire> <card> <x> <y> use an EQUIPPED card's active ability (the
//	                        weapon attack, 8107) at cell (x,y); reports AP before/after.
//	cc <wire> <x> <y>       close combat / "corps-à-corps" (8111, the unarmed
//	                        strike) on an adjacent cell; reports AP + target HP.
//	end                     end the current fighter's turn.
//	dump                    append a full fight snapshot to the log.
//	wait <ms>               sleep (lets pending AI/turn clocks fire; rarely needed).
//
// Example (immune blocks damage, in one round):
//
//	curl "http://127.0.0.1:5599/script?cmds=goto 177;castself 177 34;goto 192;\
//	      move 192 8 15;cast 192 4 9 15;dump"
//
// (wire ids may be given in full or as any unique suffix is NOT supported — pass
// the exact i64.)

// callSync posts fn to the fight actor and blocks until it has run, returning
// fn's result string. Returns ok=false if the actor could not accept the event
// (mailbox full or fight stopped) or did not run it within timeout. This is the
// synchronous "call an actor" pattern layered over the fire-and-forget Post: fn
// runs on the fight goroutine, so it may touch any fight state race-free.
func (f *Fight) callSync(timeout time.Duration, fn func(f *Fight) string) (string, bool) {
	ch := make(chan string, 1)
	if !f.Post(func(f *Fight) { ch <- fn(f) }) {
		return "", false
	}
	select {
	case s := <-ch:
		return s, true
	case <-time.After(timeout):
		return "", false
	}
}

// handleScript is the /script HTTP handler. It resolves the target fight (an
// explicit ?fight=<id>, else the single active fight), runs each command in
// order, and returns a per-step log followed by a final snapshot.
func (s *Server) handleScript(w http.ResponseWriter, r *http.Request) {
	var f *Fight
	if idStr := r.URL.Query().Get("fight"); idStr != "" {
		id, err := strconv.ParseInt(idStr, 10, 64)
		if err != nil {
			http.Error(w, "bad fight id: "+err.Error(), http.StatusBadRequest)
			return
		}
		f = s.deps.Fights.Get(id)
	} else {
		f = s.deps.Fights.Only()
	}
	if f == nil {
		http.Error(w, "no single active fight; pass ?fight=<id> (see /fight)", http.StatusNotFound)
		return
	}

	var out strings.Builder
	for i, cmd := range splitScriptCmds(r.URL.Query().Get("cmds")) {
		toks := strings.Fields(cmd)
		if len(toks) == 0 {
			continue
		}
		fmt.Fprintf(&out, "[%d] %-28s -> %s\n", i+1, cmd, f.runScriptCmd(toks))
		if f.Phase() == PhaseEnded {
			out.WriteString("     (fight ended)\n")
			break
		}
	}

	out.WriteString("--- snapshot ---\n")
	if snap, ok := f.callSync(2*time.Second, func(f *Fight) string {
		var b strings.Builder
		f.writeSnapshot(&b)
		return b.String()
	}); ok {
		out.WriteString(snap)
	} else {
		out.WriteString("(fight ended / actor unavailable)\n")
	}
	fmt.Fprint(w, out.String())
}

// splitScriptCmds splits the raw cmds string on ';' and newlines, trimming and
// dropping empties.
func splitScriptCmds(raw string) []string {
	raw = strings.ReplaceAll(raw, "\n", ";")
	var out []string
	for _, p := range strings.Split(raw, ";") {
		if p = strings.TrimSpace(p); p != "" {
			out = append(out, p)
		}
	}
	return out
}

// runScriptCmd executes one parsed command (tokens) against the fight and
// returns a human-readable result. Every state-mutating command runs its body
// on the fight actor via callSync, so the scenario is race-free and serialized
// against real client input.
func (f *Fight) runScriptCmd(toks []string) string {
	switch toks[0] {
	case "goto":
		if len(toks) < 2 {
			return "usage: goto <wire> [ai]"
		}
		target, err := strconv.ParseInt(toks[1], 10, 64)
		if err != nil {
			return "bad wire"
		}
		letAI := len(toks) >= 3 && toks[2] == "ai"
		return f.syncStr(3*time.Second, func(f *Fight) string {
			for i := 0; i < 40; i++ {
				if f.turnIndex < 0 || f.turnIndex >= len(f.Timeline) {
					return "no current fighter"
				}
				cur := f.Timeline[f.turnIndex]
				if cur.WireID == target {
					return fmt.Sprintf("cur=%d round=%d (advanced %d)", target, f.tableTurn, i)
				}
				if tf := f.fighterByWireID(target); tf == nil {
					return "no such fighter"
				} else if tf.HP <= 0 {
					return "target is dead"
				}
				if letAI && f.isAIControlled(cur) {
					f.runAITurn(cur) // plays its turn, then ends it
				} else {
					f.endTurn(cur.WireID)
				}
				if f.Phase() != PhaseAction {
					return "fight ended during goto"
				}
			}
			return "cap reached; target never became current"
		})

	case "move":
		if len(toks) < 4 {
			return "usage: move <wire> <x> <y>"
		}
		wire, x, y := mustI64(toks[1]), mustI32(toks[2]), mustI32(toks[3])
		return f.syncStr(2*time.Second, func(f *Fight) string {
			ff := f.fighterByWireID(wire)
			if ff == nil {
				return "no such fighter"
			}
			if !f.isCurrentTurn(wire) {
				return "not its turn"
			}
			if ff.Pos.X == x && ff.Pos.Y == y {
				return "already there"
			}
			path := f.reachableCells(ff, ff.Pos, ff.MP)[[2]int32{x, y}]
			if len(path) == 0 {
				return fmt.Sprintf("unreachable within mp=%d", ff.MP)
			}
			if !f.validateFightMove(ff, path) {
				return "invalid path (rooted?)"
			}
			f.applyFighterMove(ff, path)
			return fmt.Sprintf("moved to (%d,%d) mp=%d/%d", ff.Pos.X, ff.Pos.Y, ff.MP, ff.MaxMP)
		})

	case "cast":
		if len(toks) < 5 {
			return "usage: cast <wire> <spell> <x> <y>"
		}
		wire, spell := mustI64(toks[1]), mustI32(toks[2])
		x, y := mustI32(toks[3]), mustI32(toks[4])
		return f.castScript(wire, spell, Pos{X: x, Y: y})

	case "castself":
		if len(toks) < 3 {
			return "usage: castself <wire> <spell>"
		}
		wire, spell := mustI64(toks[1]), mustI32(toks[2])
		return f.castScript(wire, spell, Pos{X: -1})

	case "usecard":
		if len(toks) < 5 {
			return "usage: usecard <wire> <cardId> <x> <y>"
		}
		wire, card := mustI64(toks[1]), mustI32(toks[2])
		x, y := mustI32(toks[3]), mustI32(toks[4])
		return f.syncStr(2*time.Second, func(f *Fight) string {
			ff := f.fighterByWireID(wire)
			if ff == nil {
				return "no such fighter"
			}
			apBefore := ff.AP
			ok := f.useFighterCard(ff, card, Pos{X: x, Y: y, Z: f.Arena().altitudeAt(x, y)})
			return fmt.Sprintf("used=%v ap %d->%d/%d", ok, apBefore, ff.AP, ff.MaxAP)
		})

	case "cc":
		if len(toks) < 4 {
			return "usage: cc <wire> <x> <y>   (close combat / corps-à-corps)"
		}
		wire := mustI64(toks[1])
		x, y := mustI32(toks[2]), mustI32(toks[3])
		return f.syncStr(2*time.Second, func(f *Fight) string {
			ff := f.fighterByWireID(wire)
			if ff == nil {
				return "no such fighter"
			}
			if !f.isCurrentTurn(wire) {
				return "not its turn"
			}
			apBefore := ff.AP
			var hpBefore int32 = -1
			if v := f.fighterAtCell(Pos{X: x, Y: y}); v != nil {
				hpBefore = v.HP
			}
			f.closeCombat(ff, Pos{X: x, Y: y, Z: f.Arena().altitudeAt(x, y)})
			hpAfter := int32(-1)
			if v := f.fighterAtCell(Pos{X: x, Y: y}); v != nil {
				hpAfter = v.HP
			}
			return fmt.Sprintf("ap %d->%d/%d targetHP %d->%d", apBefore, ff.AP, ff.MaxAP, hpBefore, hpAfter)
		})

	case "end":
		return f.syncStr(2*time.Second, func(f *Fight) string {
			if f.turnIndex < 0 || f.turnIndex >= len(f.Timeline) {
				return "no current fighter"
			}
			cur := f.Timeline[f.turnIndex]
			f.endTurn(cur.WireID)
			nc := int64(0)
			if f.turnIndex >= 0 && f.turnIndex < len(f.Timeline) {
				nc = f.Timeline[f.turnIndex].WireID
			}
			return fmt.Sprintf("ended %d, cur=%d round=%d", cur.WireID, nc, f.tableTurn)
		})

	case "dump":
		return f.syncStr(2*time.Second, func(f *Fight) string {
			var b strings.Builder
			b.WriteByte('\n')
			f.writeSnapshot(&b)
			return b.String()
		})

	case "wait":
		if len(toks) < 2 {
			return "usage: wait <ms>"
		}
		ms := mustI32(toks[1])
		time.Sleep(time.Duration(ms) * time.Millisecond)
		return fmt.Sprintf("waited %dms", ms)

	default:
		return "unknown cmd (goto|move|cast|castself|end|dump|wait)"
	}
}

// castScript casts spell at target for the fighter wire on its turn. A target
// with X == -1 means "cast on the caster's own cell" (castself), resolved once
// the caster is known inside the actor.
func (f *Fight) castScript(wire int64, spell int32, target Pos) string {
	return f.syncStr(2*time.Second, func(f *Fight) string {
		caster := f.fighterByWireID(wire)
		if caster == nil {
			return "no such fighter"
		}
		if !f.isCurrentTurn(wire) {
			return "not its turn"
		}
		if target.X == -1 {
			target = caster.Pos
		}
		apBefore := caster.AP
		fired := f.castSpellByFighter(caster, spell, target)
		return fmt.Sprintf("fired=%v ap=%d->%d target=(%d,%d)", fired, apBefore, caster.AP, target.X, target.Y)
	})
}

// syncStr is callSync with a fixed "actor unavailable" fallback string, so a
// command body reads as a straight-line function.
func (f *Fight) syncStr(timeout time.Duration, fn func(f *Fight) string) string {
	if s, ok := f.callSync(timeout, fn); ok {
		return s
	}
	return "actor unavailable (fight ended?)"
}

func mustI64(s string) int64 { v, _ := strconv.ParseInt(s, 10, 64); return v }
func mustI32(s string) int32 { v, _ := strconv.ParseInt(s, 10, 32); return int32(v) }
