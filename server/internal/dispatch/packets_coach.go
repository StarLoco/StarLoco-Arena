package dispatch

import (
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// buildCoachInformation serializes the COACH_INFORMATION payload, see
// docs/02-protocol.md §2.4.3. coach.Inventory must be preloaded.
func buildCoachInformation(coach *domain.Coach) protocol.OutboundFrame {
	var equipped, unequipped []domain.CoachCard
	for _, card := range coach.Inventory {
		if card.Pos != 0 {
			equipped = append(equipped, card)
		} else {
			unequipped = append(unequipped, card)
		}
	}

	w := protocol.NewWriter(64)
	w.PutInt64(int64(coach.ID)).PutString(coach.Name)
	w.PutByte(coach.Skin).PutByte(coach.Hair).PutByte(coach.Sex)

	w.PutUint16(uint16(len(equipped) * 15))
	for _, card := range equipped {
		w.PutInt16(wireSlotForStoredPos(card.Pos)).PutInt32(card.TemplateID).PutInt64(int64(card.ID)).PutByte(card.Flag)
	}

	w.PutUint16(uint16(len(unequipped) * 15))
	for _, card := range unequipped {
		w.PutInt32(card.TemplateID).PutInt64(int64(card.ID)).PutByte(card.Flag).PutInt16(card.Quantity)
	}

	w.PutUint16(0) // locket set count, unused

	// LADDERS_STRENGTH block (COACH_INFORMATION uses options=14, whose
	// 0x8 bit makes the client call Coach.unserializeLaddersStrength --
	// see Coach.java:463-553). Format: byte count, then `count` times
	// {byte ladderId, short strength}. A ranked coach sends one entry for
	// ladder 1 (the 1v1 ladder that drives Level/Rank); an unranked coach
	// (strength 0) sends count=0 so the client leaves the ladder unset and
	// renders Level "-".
	//
	// NB: the previous code sent a bare byte 2 here (a mislabeled "legacy
	// constant"). The client read that 2 as the ladder *count* and then
	// underflowed reading two nonexistent entries -- silently aborting
	// unserialize, which is exactly why Level/Rank showed empty at login.
	writeLaddersStrength(w, coach.Strength)

	return protocol.OutboundFrame{Opcode: protocol.SendCoachInformation, Payload: w.Bytes()}
}

// writeLaddersStrength serializes the Coach.unserializeLaddersStrength block
// (byte count + count*{byte ladderId, short strength}) for the single 1v1
// ladder (id 1). A strength of 0 (unranked) is written as an empty block so
// the client leaves Level/Rank unset ("-").
func writeLaddersStrength(w *protocol.Writer, strength int32) {
	if strength <= 0 {
		w.PutByte(0)
		return
	}
	w.PutByte(1)                // one ladder entry
	w.PutByte(1)                // ladderId 1 = the 1v1 ladder
	w.PutInt16(int16(strength)) // ranked range 1000..3000 fits in int16
}

// buildFriendList serializes FRIEND_LIST_MESSAGE, see docs/02-protocol.md
// §2.4.3. coach.Friends must be preloaded with the Friend association, and
// registry is consulted to compute each friend's online flag.
func buildFriendList(coach *domain.Coach, registry *world.Registry) protocol.OutboundFrame {
	w := protocol.NewWriter(32)
	w.PutByte(byte(len(coach.Friends)))
	for _, f := range coach.Friends {
		if f.Friend == nil {
			continue
		}
		online := byte(0)
		if registry.IsOnline(f.Friend.ID) {
			online = 1
		}
		w.PutString(f.Friend.Name).PutByte(online).PutInt64(int64(f.Friend.ID))
	}
	return protocol.OutboundFrame{Opcode: protocol.SendFriendListMessage, Payload: w.Bytes()}
}

// buildIgnoreList serializes IGNORE_LIST_MESSAGE. Note: the legacy server's
// first byte is a byte SUM of name lengths rather than an entry count -- a
// quirk preserved here for wire compatibility (see docs/02-protocol.md
// §2.4.3).
func buildIgnoreList(coach *domain.Coach) protocol.OutboundFrame {
	var totalLen int
	for _, ig := range coach.Ignored {
		if ig.Ignored != nil {
			totalLen += len(ig.Ignored.Name)
		}
	}

	w := protocol.NewWriter(1 + totalLen + len(coach.Ignored))
	w.PutByte(byte(totalLen))
	for _, ig := range coach.Ignored {
		if ig.Ignored == nil {
			continue
		}
		w.PutString(ig.Ignored.Name)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendIgnoreListMessage, Payload: w.Bytes()}
}

// buildEnterWorldInstance serializes ENTER_WORLD_INSTANCE, see
// docs/02-protocol.md §2.4.4.
func buildEnterWorldInstance(x, y float32, z int16, mapID int16, dynamic bool) protocol.OutboundFrame {
	w := protocol.NewWriter(13)
	w.PutFloat32(x).PutFloat32(y).PutInt16(z).PutInt16(mapID).PutBool(dynamic)
	return protocol.OutboundFrame{Opcode: protocol.SendEnterWorldInstance, Payload: w.Bytes()}
}

// buildActorSpawn serializes ACTOR_SPAWN for the given coaches, see
// docs/02-protocol.md §2.4.5. It takes value-copy world.CoachView
// snapshots (NOT live *OnlineCoach pointers) precisely because those
// coaches belong to OTHER players: reading their live position/strength/
// equipment fields here -- on a broadcast or post-fight goroutine -- would
// race a concurrent fight-end or movement writing them. The registry hands
// out immutable views taken under its lock; this serializes off the copy.
func buildActorSpawn(coaches []world.CoachView) protocol.OutboundFrame {
	w := protocol.NewWriter(32 * len(coaches))
	w.PutInt32(int32(len(coaches)))
	for _, c := range coaches {
		w.PutByte(1) // actor type: 1 = coach
		w.PutInt64(int64(c.ID)).PutString(c.Name)
		w.PutInt32(c.PosX).PutInt32(c.PosY).PutInt16(c.PosZ)
		w.PutByte(1) // unknown flag, legacy constant
		w.PutByte(c.Skin).PutByte(c.Hair).PutByte(c.Sex)
		w.PutUint16(uint16(len(c.Equipped) * 15))
		for _, card := range c.Equipped {
			w.PutInt16(wireSlotForStoredPos(card.Pos)).PutInt32(card.TemplateID).PutInt64(int64(card.ID)).PutByte(card.Flag)
		}
		// ACTOR_SPAWN coach entries use options=11 (0x8 LADDERS_STRENGTH
		// set), so the trailing bytes are the same ladders-strength block
		// as COACH_INFORMATION -- previously a bare 0 (empty). Sending the
		// real strength lets other players see this coach's Level/Rank.
		writeLaddersStrength(w, c.Strength)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendActorSpawn, Payload: w.Bytes()}
}

// buildActorDespawn serializes ACTOR_DESPAWN for a single coach, see
// docs/02-protocol.md §2.4.6.
func buildActorDespawn(coachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutByte(1).PutInt64(int64(coachID))
	return protocol.OutboundFrame{Opcode: protocol.SendActorDespawn, Payload: w.Bytes()}
}

// actorAppearEntry is one row of ACTOR_APPEAR's entity list -- either a
// coach (the sideline "coach point" pawn each coach stands on inside the
// fight map, see FightStartCoachPointElement, docs/08-java-parity-
// roadmap.md Phase K) or a fighter. The wire format carries no
// discriminator byte at all (confirmed via the decompiled client's
// ActorAppearMessage.decode() -- see docs/opcodes/03-coach-world.md's
// "Discrepancy vs legacy comment" note); the client (NetFightActorsFrame,
// case 4102) disambiguates purely by ID: it first tries
// Fight.getFighterById(id), then falls back to each team's
// getTeamMateById(id) (coach IDs), so both entity kinds share one flat ID
// space and one wire shape.
type actorAppearEntry struct {
	ID        int64
	X, Y      int32
	Z         int16
	Direction combat.Direction8
}

// buildActorAppear serializes ACTOR_APPEAR (Send 4102), see
// docs/opcodes/03-coach-world.md's ACTOR_APPEAR section for the exact
// client-verified wire format. This is the packet that actually
// instantiates a visible mobile for each coach/fighter inside the
// fight-map scene (NetFightActorsFrame's case 4102 calls addMobile()) --
// ENTER_WORLD_INSTANCE alone only repositions the client's own camera/
// instance context, it never spawns anything (see that opcode's own doc
// section). Previously never built/sent anywhere in the Go server (opcode
// constant only) -- this was the root cause of teleported coaches/fighters
// never visually appearing on the fight map.
func buildActorAppear(entries []actorAppearEntry) protocol.OutboundFrame {
	w := protocol.NewWriter(1 + len(entries)*19)
	w.PutByte(byte(len(entries)))
	for _, e := range entries {
		w.PutInt64(e.ID).PutInt32(e.X).PutInt32(e.Y).PutInt16(e.Z).PutByte(byte(e.Direction))
	}
	return protocol.OutboundFrame{Opcode: protocol.SendActorAppear, Payload: w.Bytes()}
}
