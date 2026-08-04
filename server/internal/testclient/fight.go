package testclient

import "time"

// FighterTurnBegin decodes an 8104 payload: [i32 uid][i32 -1][i64 fighterWireId].
func ParseFighterTurnBegin(f *Frame) int64 {
	r := NewR(f.Payload)
	r.I32() // uid
	r.I32() // triggering
	return r.I64()
}

// RunningEffect decodes an 8120 payload for assertions:
// header(8) + mustExec(1) + triggered(1) + Nx(4) + effId(4) + blobLen(2) + blob.
// EffectID is the outer running-effect id (mh_2 id). The blob is the Ankama
// part-serialized "BinarSerial" (see buildRunningEffect / aJj.ad): its part 0
// carries [i64 caster][i64 target][i32 genericId][i32 x][i32 y][i16 z][i32 value].
type RunningEffect struct {
	EffectID int32
	CasterID int64
	TargetID int64
	Value    int32
}

// ParseRunningEffect extracts the running-effect id + caster/target/value from an
// 8120 frame, decoding the part-serialized blob envelope
// ([i8 numParts][numParts×{i8 idx,i32 off}]) then part 0's fixed payload.
func ParseRunningEffect(f *Frame) RunningEffect {
	r := NewR(f.Payload)
	r.I32() // uid
	r.I32() // triggering
	r.U8()  // mustExec
	r.U8()  // triggered
	r.I32() // Nx
	effID := r.I32()
	r.U16() // blobLen
	// blob envelope: numParts + directory (skipped; part 0 is always first).
	numParts := int(r.U8())
	for i := 0; i < numParts; i++ {
		r.U8()  // part idx
		r.I32() // part offset
	}
	// part 0 block: [i8 idx][i64 caster][i64 target][i32 genericId][x][y][z][value].
	r.U8() // part idx (0)
	caster := r.I64()
	target := r.I64()
	r.I32() // genericEffectId
	r.I32() // x
	r.I32() // y
	r.U16() // z
	value := r.I32()
	return RunningEffect{EffectID: effID, CasterID: caster, TargetID: target, Value: value}
}

// CastSpell sends a SPELL_CAST_REQUEST (8109 mc_2):
// [i64 fighterId][i32 spellId][i32 x][i32 y][i16 z].
//
// This used to send 8107, mirroring the server's own mistake — 8107 is the CARD-use
// request. Because the harness spoke the server's dialect instead of the retail
// client's, the whole e2e suite passed while spell casting was broken in the real
// client (B-047). Keep this aligned with the decompiled client, not with the server.
func (c *Client) CastSpell(casterWireID int64, spellID, x, y int32, z int16) error {
	p := NewW().I64(casterWireID).I32(spellID).I32(x).I32(y).U16(uint16(z)).Bytes()
	return c.Send(3, OpSpellCastReq, p)
}

// UseCard sends a FIGHTER_CARD_USE_REQUEST (8107 sg_2), the in-fight action-card
// play: [i64 fighterId][i32 cardId][i32 x][i32 y][i16 z].
func (c *Client) UseCard(userWireID int64, cardID, x, y int32, z int16) error {
	p := NewW().I64(userWireID).I32(cardID).I32(x).I32(y).U16(uint16(z)).Bytes()
	return c.Send(3, OpFighterCardUseReq, p)
}

// EndTurn sends FIGHTER_END_TURN_REQUEST (8105): [i64 fighterId].
func (c *Client) EndTurn(fighterWireID int64) error {
	return c.Send(3, OpEndTurnReq, NewW().I64(fighterWireID).Bytes())
}

// Opcodes for in-fight actions/broadcasts (exported for tests).
const (
	OpSpellCast      = 8110
	OpRunningEffect  = 8120
	OpFighterDies    = 4520
	OpFighterTurnEnd = 8106
)

// WaitForTurn waits for a FIGHTER_TURN_BEGIN (8104) and returns the fighter id
// whose turn it is.
func (c *Client) WaitForTurn(timeout time.Duration) (int64, error) {
	f, _, err := c.WaitFor(OpFighterTurnBegin, timeout)
	if err != nil {
		return 0, err
	}
	return ParseFighterTurnBegin(f), nil
}
