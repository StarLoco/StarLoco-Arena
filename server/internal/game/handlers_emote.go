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
	r.Register(protocol.OpResetPosition, handleResetPosition)
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
	// ViewersOf, NOT SessionsNear: 4700 makes the client look the actor up by id
	// and `no_2` dereferences the result with no null check, so sending it to
	// someone who never received an ActorSpawn for this coach throws
	// NullPointerException in their client. Proximity and AoI membership are not
	// the same set - AoI is seeded on EnterAoI and not maintained on movement.
	n := 0
	for _, other := range s.deps.World.ViewersOf(s.Coach.ID) {
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

// handleResetPosition implements the `/resetPosition` console command (4514,
// `aII`, empty payload): the player's own unstick.
//
// It deliberately reuses the Zaap/enter path rather than the cheaper
// ActorTeleports (4510). 4510 is the correct frame for moving an actor without a
// walk animation, but our overworld AoI membership is only ever computed on
// EnterAoI (instance enter / leaving a fight) - Registry.UpdatePosition just
// records coordinates. Teleporting with 4510 alone would move the coach visually
// while leaving every AoI known-set stale, so a long hop would leave the coach
// visible to people it is nowhere near and invisible to those it landed among.
// Going through sendEnterOverworld re-seeds AoI correctly. See the 4510 row in
// OPCODE-INVENTORY.md for what would have to exist first.
//
// Destination is the primary Zaap of the coach's CURRENT world, matching the
// Zaap arrival rule: a teleporter is always somewhere the coach can walk out of,
// which is precisely what "unstick me" needs. Falling back to the start world
// would silently turn an unstick into an eviction from the island the player
// is on.
func handleResetPosition(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	if s.deps.Fights.ByCoach(s.Coach.ID) != nil {
		return nil // not an escape hatch out of a fight
	}
	world := s.currentWorld
	if world == 0 {
		world = startWorldID
	}
	z, ok := primaryZaap(world)
	if !ok {
		s.log.Debug("resetPosition: no zaap on this world", "coach", s.Coach.Name, "world", world)
		return nil
	}
	s.Coach.PosX, s.Coach.PosY, s.Coach.PosZ = z.cellX, z.cellY, z.alt
	s.deps.World.UpdatePosition(s.Coach.ID, z.cellX, z.cellY, z.alt)
	_ = s.deps.Store.Coaches.Save(s.Coach)

	if err := s.sendEnterOverworld(float32(z.cellX), float32(z.cellY), z.alt, world); err != nil {
		return err
	}
	s.log.Info("reset position", "coach", s.Coach.Name, "world", world,
		"cell", []int32{z.cellX, z.cellY}, "alt", z.alt)
	return nil
}
