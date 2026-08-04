package game

import "github.com/StarLoco/arena-2.70/internal/protocol"

// handlers_spectate.go implements watching an ongoing fight as a read-only
// spectator. The client first asks whether a coach is in a spectatable fight
// (C2S 2260 → S2C 2261); if so it offers "enterSpectatorMode", which sends the
// join (C2S 26331). The server then attaches the viewer's session to the fight's
// spectator list and replays the fight snapshot (the same sendFightResync path as
// a reconnect resume, with the spectator flag set). A spectator receives every
// fight broadcast, cannot act (it owns no fighter and is not in Fights.ByCoach),
// and is returned to the overworld when the fight ends (via END_FIGHT + the
// existing END_FIGHT_DONE handler) or when it disconnects.

func registerSpectateHandlers(r *Router, _ *Deps) {
	r.Register(protocol.OpSpectateQuery, handleSpectateQuery)
	r.Register(protocol.OpSpectateJoin, handleSpectateJoin)
}

// handleSpectateQuery answers "is coach <id> in a spectatable fight?" (2260) with
// SPECTATE_REPLY (2261) [i8 1/0]. A fight is spectatable while it exists and has
// not ended.
func handleSpectateQuery(s *Session, frame *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	targetID, err := protocol.NewReader(frame.Payload).I64()
	if err != nil {
		return err
	}
	spectatable := byte(0)
	if f := s.deps.Fights.ByCoach(uint(targetID)); f != nil && f.Phase() != PhaseEnded {
		spectatable = 1
	}
	reply, err := protocol.EncodeS2C(protocol.OpSpectateReply,
		protocol.NewWriter().U8(spectatable).Bytes())
	if err != nil {
		return err
	}
	return s.Send(reply)
}

// handleSpectateJoin attaches this session as a spectator of the fight coach <id>
// is in (26331) and replays the fight to it. A coach that is itself in a fight, or
// already spectating, is ignored; so is a target that is not in a live fight.
func handleSpectateJoin(s *Session, frame *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	// Can't spectate while in your own fight or while already spectating one.
	if s.deps.Fights.ByCoach(s.Coach.ID) != nil || s.spectating != nil {
		return nil
	}
	targetID, err := protocol.NewReader(frame.Payload).I64()
	if err != nil {
		return err
	}
	f := s.deps.Fights.ByCoach(uint(targetID))
	if f == nil || f.Phase() == PhaseEnded {
		return nil
	}
	deps := s.deps
	sess := s
	cid := s.Coach.ID
	// Bind the spectator link (this session's own goroutine owns the field), then
	// attach + remove-from-overworld + replay on the fight actor.
	s.spectating = f
	posted := f.Post(func(f *Fight) {
		if f.Phase() == PhaseEnded {
			return
		}
		f.addSpectator(sess)
		// Leave the overworld (like a fighter) so others stop seeing the spectator
		// wandering, then replay the fight as a read-only viewer.
		if viewers := deps.World.SetInFight(cid, true); len(viewers) > 0 {
			if dsp, err := buildActorDespawn([]uint{cid}); err == nil {
				for _, v := range viewers {
					_ = v.Send(dsp)
				}
			}
		}
		deps.sendFightResync(sess, f, true)
		deps.Log.Info("spectator joined fight", "id", f.ID, "coach", cid, "target", targetID)
	})
	if !posted {
		s.spectating = nil // fight actor already stopped
	}
	return nil
}
