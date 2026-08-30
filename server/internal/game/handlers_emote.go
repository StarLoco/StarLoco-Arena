package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Emotes.
//
// The emote table is NOT data-driven: the 2.70 client hardcodes it as the enum
// `up_0`, ten entries of (id, animation name, chat command). Because the whole
// table is knowable, the server does not have to trust the name the client sends
// - it looks the id up and relays its OWN canonical name. A modified client
// therefore cannot make other players' clients try to play an arbitrary
// animation string.
//
// ids are non-contiguous (57, 59, 60, 62, ...) exactly as in `up_0`; the gaps
// are real and are not ours to fill.
var emoteAnimations = map[int32]string{
	57: "AnimEmote-Applaudir",     // /clap
	59: "AnimEmote-Lire-Debut",    // /read
	60: "AnimEmote-Declaration",   // /declare
	62: "AnimEmote-Colere",        // /angry
	63: "AnimEmote-Guitare-Debut", // /music
	65: "AnimEmote-Pointer",       // /show
	66: "AnimEmote-Rire",          // /laugh
	67: "AnimEmote-Effraye",       // /fear
	68: "AnimEmote-Defaite",       // /cry
	69: "AnimEmote-Non",           // /no
}

func registerEmoteHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpEmotePlay, handleEmote)
}

// handleEmote receives 4701 (`JY`) and relays the emote to everyone in the
// sender's area of interest.
//
// C2S: [u8 nameLen][name utf8][i32 emoteId]. The client sends both the id and
// the animation name it resolved locally; we read the name to consume the frame
// but deliberately IGNORE it in favour of our own table (see emoteAnimations).
//
// The sender is included in the broadcast: `avv_0.playEmote` only updates the
// actor's facing direction locally and then sends 4701 - the animation is played
// solely by the 4700 handler. Excluding the sender the way vicinity chat does
// would mean the emoting player is the one person who never sees it.
func handleEmote(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	if _, err := r.StringU8(); err != nil { // client-resolved name, not trusted
		return err
	}
	emoteID, err := r.I32()
	if err != nil {
		return err
	}
	// The client sends abs(id) already (`up_0.dP(Math.abs(...))`), but a modified
	// one need not, and an unknown id must not be relayed.
	anim, ok := emoteAnimations[emoteID]
	if !ok {
		s.log.Debug("unknown emote id dropped", "coach", s.Coach.Name, "id", emoteID)
		return nil
	}
	frame, err := buildEmotePlayed(s.Coach.ID, anim)
	if err != nil {
		return err
	}
	n := 0
	for _, other := range s.deps.World.SessionsNear(s.Coach.PosX, s.Coach.PosY, s.Coach.ID) {
		if other.Send(frame) == nil {
			n++
		}
	}
	if s.Send(frame) == nil { // the emoting player has to see it too
		n++
	}
	s.log.Debug("emote", "coach", s.Coach.Name, "id", emoteID, "anim", anim, "to", n)
	return nil
}

// buildEmotePlayed builds EmotePlayed (4700): [i64 actorId][u8 nameLen][name].
// The actor id is the coach id, the same identity used by ActorSpawn/ActorDespawn.
func buildEmotePlayed(coachID uint, anim string) ([]byte, error) {
	w := protocol.NewWriter().I64(int64(coachID)).StringU8(anim)
	return protocol.EncodeS2C(protocol.OpEmotePlayed, w.Bytes())
}
