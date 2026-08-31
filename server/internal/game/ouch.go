package game

import "github.com/StarLoco/arena-2.70/internal/protocol"

// The "ouch !" bubble (4902).
//
// `of_1` case 4902 resolves the fighter by id and pops a 25-second
// `interactiveBubbleDialog` reading "ouch !" over it. The frame carries a single
// fighter id, so it can only ever name ONE fighter - it cannot express
// "attacker hit target", which settles who it is for: the fighter that took the
// hit.
//
// Server policy (chosen deliberately, not derived - the client contains no rule
// about when a fighter says ouch): a fighter says ouch when it actually LOSES HP
// to a critical cast. Both halves matter. A crit that deals no damage - a
// debuff, a miss, a fully absorbed hit - produces no bubble, and neither does
// ordinary damage.
//
// The two leading i32s are the `ue_0.o` header. The 4902 handler never reads
// them, so they are sent as zero rather than invented.
func buildFighterOuch(fighterWireID int64) ([]byte, error) {
	w := protocol.NewWriter().I32(0).I32(0).I64(fighterWireID)
	return protocol.EncodeS2C(protocol.OpFighterOuch, w.Bytes())
}

// hpSnapshot records every fighter's HP so a cast can be compared before/after.
// Threading a "was crit" flag down through the effect system would touch every
// effect handler and still miss damage applied indirectly (rebound, transfer,
// collision); comparing HP catches all of it and cannot drift out of sync with
// the damage code.
func (f *Fight) hpSnapshot() map[int64]int32 {
	out := make(map[int64]int32, 8)
	for _, ff := range f.allFighters() {
		out[ff.WireID] = ff.HP
	}
	return out
}

// broadcastOuchForDamaged sends 4902 for every fighter whose HP dropped since
// the snapshot. Call it only for a critical cast.
func (f *Fight) broadcastOuchForDamaged(before map[int64]int32) {
	for _, ff := range f.allFighters() {
		was, ok := before[ff.WireID]
		if !ok || ff.HP >= was {
			continue // absent, unharmed, or healed
		}
		frame, err := buildFighterOuch(ff.WireID)
		if err != nil {
			continue
		}
		f.broadcast(frame)
	}
}
