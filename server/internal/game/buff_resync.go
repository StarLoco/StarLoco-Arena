package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Restoring buff icons for a client that was not there when the buffs were cast
// (a reconnect, or a spectator joining mid-fight) - item 11.
//
// The channel is 8121 (rq_2) and NOT 8120. `of_1` case 8120 builds an `mv_0` and
// either runs it immediately or queues it on the effect scheduler, so replaying
// buffs through it would RE-APPLY every one of them and double their effects
// client-side. Case 8121 instead attaches the effect to the target's buff
// container (`zT.ajR().PJ().o(zT)`) and raises "hasBuff" - it attaches without
// executing, which is exactly what a resync needs.

// buildAttachBuff encodes 8121: [i32 mh_2 actionId][i16 blobLen][blob][akv_0].
//
// The trailing akv_0 is [i64 fighterId][i16 expiry][i8 flag], and `expiry` is the
// subtle part: it is an ABSOLUTE mark against that fighter's own turn counter,
// not a remaining count. `aGT` builds it as `alh_1.aAy() + duration` and reads
// what is left back as `expiry - aAy()`, so sending a remaining count would make
// every restored buff look like it expires at the start of the fight.
//
// A negative expiry means infinite (`akv_0.isInfinite()` is `NC < 0`), which maps
// straight onto activeBuff.infinite.
func buildAttachBuff(actionID int32, blob []byte, fighterWireID int64, expiry int16) ([]byte, error) {
	w := protocol.NewWriter().
		I32(actionID).
		U16(uint16(len(blob))).
		Raw(blob).
		I64(fighterWireID).
		U16(uint16(expiry)).
		U8(0) // akv_0.ND: the client's own +/-1 turn-ordering nudge; we do not model it
	return protocol.EncodeS2C(protocol.OpRunScriptedEffect, w.Bytes())
}

// buffExpiryMark converts one active buff into the wire's absolute expiry.
func buffExpiryMark(ff *FightFighter, b *activeBuff) int16 {
	if b.infinite {
		return -1
	}
	return int16(ff.turnsTaken + b.turnsLeft)
}

// resyncBuffs re-attaches every fighter's active buffs for one viewer.
//
// Sent per viewer rather than broadcast: this repairs one client's missing state,
// and everyone else already has these buffs attached - re-sending would stack a
// second copy in their container, since `PJ().o()` appends.
func (d *Deps) resyncBuffs(sess *Session, f *Fight) {
	if sess == nil || f == nil {
		return
	}
	for _, ff := range f.allFighters() {
		if ff.HP <= 0 {
			continue // a dead fighter's icons are moot, and it is greyed out anyway
		}
		for _, b := range ff.Buffs {
			// The blob is the same six-part encoding 8120 uses, and the TARGET is
			// carried inside it: `zT.ajR()` must resolve or the attach is skipped
			// in silence.
			blob := buildEffectBlob(b.effectID, ff.WireID, ff.WireID, ff.Pos, b.delta)
			frame, err := buildAttachBuff(b.actionID, blob, ff.WireID, buffExpiryMark(ff, b))
			if err != nil {
				continue
			}
			_ = sess.Send(frame)
		}
	}
}
