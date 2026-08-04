package game

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Returning a coach to the overworld after a fight goes through
// Session.sendEnterOverworld (4600 + 4516 + interactive-element re-spawn), not a
// bare EnterInstance — see handleEndFightDone.

// writeActionHeader writes the ue_0 8-byte header [i32 uid][i32 triggeringId].
func writeActionHeader(w *protocol.Writer, uid int32) {
	w.I32(uid).I32(-1)
}

// buildFighterMoveInFight builds FIGHTER_MOVE (4524): header + fighter id +
// path steps {i32 x, i32 y, i16 z}. The path should be prefixed with the
// fighter's START cell so the client animates from the current position.
func buildFighterMoveInFight(uid int32, wireID int64, path []Pos) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(wireID)
	for _, p := range path {
		w.I32(p.X).I32(p.Y).U16(uint16(p.Z))
	}
	return protocol.EncodeS2C(protocol.OpFighterMoveInFight, w.Bytes())
}

// buildFighterDirectionChange builds FIGHTER_CHANGE_DIRECTION (4522): the ue_0
// header + fighter id + direction byte (a qc_0 index). Broadcast when a fighter
// turns to face a new direction (cosmetic; see B-037).
func buildFighterDirectionChange(uid int32, wireID int64, dir uint8) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(wireID).U8(dir)
	return protocol.EncodeS2C(protocol.OpFighterChangeDir, w.Bytes())
}

// buildFighterCardUse builds FIGHTER_CARD_USE (8108 arn_0): the ue_0 header +
// user + card id + the landed/target block. Same layout as SPELL_CAST (8110) — the
// client guards both at >= 21 bytes, and >= 32 once the "missed" flag is false, so
// the target block must be present for a landed play. The card id is resolved
// against the client's own card registry, so it must be a real template id.
func buildFighterCardUse(uid int32, userWireID int64, cardID int32, target Pos, missed, critical bool) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(userWireID).I32(cardID)
	w.U8(boolByte(missed))
	w.U8(boolByte(critical))
	w.I32(target.X).I32(target.Y).U16(uint16(target.Z))
	return protocol.EncodeS2C(protocol.OpFighterCardUse, w.Bytes())
}

// buildSpellCast builds SPELL_CAST (8110): header + caster + spell + crit + target.
func buildSpellCast(uid int32, casterWireID int64, spellID int32, target Pos, critMiss, critHit bool) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(casterWireID).I32(spellID)
	if critMiss {
		w.U8(1)
	} else {
		w.U8(0)
		w.U8(boolByte(critHit))
		w.I32(target.X).I32(target.Y).U16(uint16(target.Z))
	}
	return protocol.EncodeS2C(protocol.OpSpellCast, w.Bytes())
}

// buildRunningEffect builds RUNNING_EFFECT (8120) in the 2.70 amb_0 layout:
//
//	header + [i8 mustExecNow][i8 triggered][i32 Nx=0][i32 runningEffectId]
//	[i16 blobLen][blob]
//
// where blob is the Ankama part-serialized "BinarSerial" the client's aJj.ad()
// parses (NOT a flat struct). A running effect exposes up to six parts
// (xb_2.Kl); an HP-loss / AP-use / MP-use effect needs the first three:
//
//	part 0 (yi_1, 34B): [i64 caster][i64 target][i32 genericEffectId][i32 x][i32 y][i16 z][i32 value]
//	part 1 (Yk,    8B): [i64 caster]  -> sets ajQ() (caster fighter)
//	part 2 (yl_2,  8B): [i64 target]  -> sets ajR() (target fighter)
//
// The value is applied VERBATIM (HPLoss.execute does substract(m_value) without
// recomputing — mv_0.ax renders getValue() as-is), and ajR()/ajQ() must both
// resolve or the client drops the effect. The OLD blob was a flat struct whose
// first byte the client read as numParts=0, so it parsed nothing and the
// damage/HP-loss never showed (the reported "spell casts but no effect" bug).
//
// durationTurns is the packet's Nx field: for a timed CharacBuff the client sets
// the effect's remaining turns from it (mv_0.ax → RunningEffect.jt(Nx)); it is
// ignored for an instant effect (damage/heal/AP-MP), so pass 0 there.
func buildRunningEffect(uid, runningEffectID, genericEffectID int32, casterWireID, targetWireID int64, cell Pos, value, durationTurns int32, mustExecNow bool) ([]byte, error) {
	part0 := protocol.NewWriter().
		I64(casterWireID).
		I64(targetWireID).
		I32(genericEffectID).
		I32(cell.X).I32(cell.Y).U16(uint16(cell.Z)).
		I32(value).
		Bytes()
	part1 := protocol.NewWriter().I64(casterWireID).Bytes()
	part2 := protocol.NewWriter().I64(targetWireID).Bytes()
	blob := writeBinarSerial([]binarPart{{0, part0}, {1, part1}, {2, part2}})

	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.U8(boolByte(mustExecNow))
	w.U8(0)              // triggered
	w.I32(durationTurns) // Nx: buff duration in turns (0 = instant)
	w.I32(runningEffectID)
	w.U16(uint16(len(blob)))
	w.Raw(blob)
	return protocol.EncodeS2C(protocol.OpRunningEffect, w.Bytes())
}

// binarPart is one part of an Ankama BinarSerial blob: its type index (its
// position in the effect's Kl() part array) and its already-serialized payload.
type binarPart struct {
	idx  uint8
	data []byte
}

// writeBinarSerial encodes parts in the wire form the 2.70 client's aJj.ad()
// (and its ajl_2 serializer) expects:
//
//	[i8 numParts]
//	numParts × { [i8 partIdx][i32 absOffset] }   // directory
//	per part: [i8 partIdx][payload]              // absOffset points at the partIdx byte
//
// aJj.ad reads each part's payload from absOffset+1 and derives its length from
// the next part's offset (or the blob end for the last), so the directory
// offsets must be the absolute byte position of each part block.
func writeBinarSerial(parts []binarPart) []byte {
	w := protocol.NewWriter()
	w.U8(uint8(len(parts)))
	off := 1 + len(parts)*5 // count byte + directory (5 bytes/entry)
	for _, p := range parts {
		w.U8(p.idx)
		w.I32(int32(off))
		off += 1 + len(p.data) // partIdx byte + payload
	}
	for _, p := range parts {
		w.U8(p.idx)
		w.Raw(p.data)
	}
	return w.Bytes()
}

// buildActionSequenceExecute builds the empty 8200 flush barrier.
func buildActionSequenceExecute() ([]byte, error) {
	return protocol.EncodeS2C(protocol.OpActionSequenceExecute, nil)
}

// buildFighterDies builds FIGHTER_DIES (4520): header + fighter id.
func buildFighterDies(uid int32, wireID int64) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(wireID)
	return protocol.EncodeS2C(protocol.OpFighterDies, w.Bytes())
}

// buildCloseCombat builds CLOSE_COMBAT (8112, class aAD): header + attacker id +
// [i8 fumble]; on a non-fumble also [i8 crit][i32 x][i32 y][i16 z]. Mirrors
// buildSpellCast (17 bytes on a fumble, 28 otherwise).
func buildCloseCombat(uid int32, attackerWireID int64, target Pos, critMiss, critHit bool) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(attackerWireID)
	if critMiss {
		w.U8(1)
	} else {
		w.U8(0)
		w.U8(boolByte(critHit))
		w.I32(target.X).I32(target.Y).U16(uint16(target.Z))
	}
	return protocol.EncodeS2C(protocol.OpCloseCombat, w.Bytes())
}

// buildFighterTackled builds FIGHTER_TACKLED (4506, class acg): header + tackled
// fighter id + tackler id (24 bytes total). Cosmetic — plays the "held in place"
// animation when a fighter fails to evade an adjacent enemy's zone-of-control.
func buildFighterTackled(uid int32, tackledWireID, tacklerWireID int64) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.I64(tackledWireID).I64(tacklerWireID)
	return protocol.EncodeS2C(protocol.OpFighterTackled, w.Bytes())
}

// endFightCoach is one coach's result in END_FIGHT: its id and its NEW ladder
// strength (after the win/loss delta), sent in the 8300 strength map.
type endFightCoach struct {
	ID       uint
	Strength int32
}

// buildEndFight builds END_FIGHT (8300) in the 2.70 YP layout (non-flee). After
// the flee byte come the winners'/losers' STRENGTH MAPS (bA/bB) — the client reads
// these to update each coach's ladder Strength (→ Level/Rank on the result
// screen) — then the winner/loser report lists (bw/bx), then the card/object tail:
//
//	header + [i8 flee=0]
//	[i32 winCount]{i64 coachId, i32 strength}      // bA winners strength map
//	[i32 loseCount]{i64 coachId, i32 strength}     // bB losers strength map
//	[i8 winCount]{i64 coachId, i16 s2=0, i16 reportLen=0}  // bw
//	[i8 loseCount]{...}                            // bx
//	[i16 lostCards=0][i16 wonCards=0][i8 objStats=0][i8 cbI=0][i8 cbH=0][i32 cbG=0].
//
// endFightReport is one fighter's debrief blob, addressed by wire id.
type endFightReport struct {
	WireID int64
	Blob   []byte
}

func buildEndFight(uid int32, winners, losers []endFightCoach) ([]byte, error) {
	return buildEndFightFull(uid, winners, losers, nil, 0, 0, 0, nil)
}

// buildEndFightFull is buildEndFight plus the evolution payload: the per-fighter
// debrief reports, the coach reputation won, and the killed/injured counts.
func buildEndFightFull(uid int32, winners, losers []endFightCoach,
	reports []endFightReport, standingWon int32, killed, injured uint8,
	wonCards []int32) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.U8(0) // flee = false (normal end)

	// bA / bB: per-coach new ladder strength.
	w.I32(int32(len(winners)))
	for _, c := range winners {
		w.I64(int64(c.ID)).I32(c.Strength)
	}
	w.I32(int32(len(losers)))
	for _, c := range losers {
		w.I64(int64(c.ID)).I32(c.Strength)
	}
	// bw / bx: result lists (no per-coach report blob yet).
	w.U8(uint8(len(winners)))
	for _, c := range winners {
		w.I64(int64(c.ID)).U16(0).U16(0) // s2=0, reportLen=0
	}
	w.U8(uint8(len(losers)))
	for _, c := range losers {
		w.I64(int64(c.ID)).U16(0).U16(0)
	}
	// Card blobs, each [u16 blobLen] then [u8 groupCount]{[u8 n]{[i32 cardId]}}.
	//
	// ORDER: the FIRST blob is the cards WON and the second is the cards LOST —
	// the reverse of what this used to claim. Traced through the client:
	// `YP.c(blob, bl2)` files into `bl2 ? by : bz`, the first blob is read with
	// `bl2 = false` (so it lands in `bz`), and `ajo_1` publishes
	// `bz -> "fight.wonCards"` / `by -> "fight.lostCards"`. Getting this backwards
	// would show a player their winnings in the "Cartes perdues" column.
	writeCardBlob(w, wonCards)
	writeCardBlob(w, nil) // lost cards: staking is not implemented

	// PER-FIGHTER POST-FIGHT REPORTS — [u8 n]{[i64 fighterId][i16 len][OW blob]}.
	// (This byte was previously mislabelled "object stats count". YP.a() lines
	// 90-97 read it as the report list: `cbF.a(fighterId, new OW(bytes))`.)
	//
	// Each blob is the client's `OW`/`adl_0` 40-byte debrief record — see
	// postfight.go. `fightResultEvolutionDialog.xml` binds all 13 of its exposed
	// fields; with no reports the evolution debrief panel renders blank.
	w.U8(uint8(len(reports)))
	for _, rep := range reports {
		w.I64(rep.WireID).U16(uint16(len(rep.Blob))).Raw(rep.Blob)
	}

	// cbI / cbH: fighters killed / injured this fight, consumed by the client's
	// "Death" and "Injury" achievement counters (WE.java case 8300). Note the
	// wire order is killed FIRST (cbI), then injured (cbH).
	w.U8(killed)
	w.U8(injured)

	// cbG: standing (coach reputation) won. WE.java does `standing += amW()` and
	// pops coachLevelUpDialog when the evolution level changes.
	w.I32(standingWon)
	return protocol.EncodeS2C(protocol.OpEndFight, w.Bytes())
}

// writeCardBlob writes one [u16 len][u8 groupCount]{[u8 n]{[i32 cardId]}} blob.
// An empty list still writes a zero LENGTH (not an empty group), because the
// client only parses the blob when the length is > 0.
func writeCardBlob(w *protocol.Writer, cards []int32) {
	if len(cards) == 0 {
		w.U16(0)
		return
	}
	// One group holding every card. The grouping exists so a multi-coach fight
	// can report per-coach lists; a single coach needs exactly one group.
	inner := protocol.NewWriter().U8(1).U8(uint8(len(cards)))
	for _, id := range cards {
		inner.I32(id)
	}
	b := inner.Bytes()
	w.U16(uint16(len(b))).Raw(b)
}

func boolByte(b bool) uint8 {
	if b {
		return 1
	}
	return 0
}
