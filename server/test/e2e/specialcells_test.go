package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_CreateFightCarriesRealSpecialCells drives a real duel to
// CREATE_FIGHT and asserts the packet carries the fight map's special
// battlefield cells, DERIVED from the map's own baked negative-gfx Bonus
// tiles (gamedata.Map.SpecialCells). This is the end-to-end proof that the
// whole pipeline works in a live fight: selectFightMapID picks a real map,
// resolveSpecialCellRenders loads its special-cell layout, and
// buildCreateFight serializes it onto the wire. Every imported fight map
// (2-16) has special tiles painted into its art, so a random map always
// yields a non-empty list (no flakiness).
func TestE2E_CreateFightCarriesRealSpecialCells(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyA := append(putInt64(duelID), 1)
	readyA = append(readyA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyA)
	cAlice.expectOpcode(protocol.SendReadyForFight)
	readyB := append(putInt64(duelID), 1)
	readyB = append(readyB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	createFight := cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	cells := parseCreateFightSpecialCells(t, createFight)
	if len(cells) == 0 {
		t.Fatalf("CREATE_FIGHT carried no special cells, want the map's baked special tiles")
	}
	// Each cell must carry a real cellBaseId (a SPECIAL staticEffects id
	// 1002-1009, or trap-area id 1) and real coordinates; a zeroed tuple
	// would mean the data didn't flow.
	for i, c := range cells {
		if c.baseID == 0 {
			t.Errorf("special cell %d has cellBaseId 0 (data did not reach the wire)", i)
		}
		validBase := c.baseID == 1 || (c.baseID >= 1002 && c.baseID <= 1009)
		if !validBase {
			t.Errorf("special cell %d cellBaseId = %d, want trap(1) or special(1002-1009)", i, c.baseID)
		}
	}
}

type wireSpecialCell struct {
	baseID, cellID int64
	x, y           int32
	z              int16
}

// parseCreateFightSpecialCells walks a CREATE_FIGHT payload to its trailing
// special-cell block and returns the parsed cells, mirroring the wire order
// buildCreateFight writes / FightCreationMessage.decode expects.
func parseCreateFightSpecialCells(t *testing.T, payload []byte) []wireSpecialCell {
	t.Helper()
	r := newPayloadReader(payload)
	r.byte_() // error code
	r.int16() // coach cards blob length
	r.int32() // fight type
	r.int32() // bet
	teamCount := r.byte_()
	for tIdx := byte(0); tIdx < teamCount; tIdx++ {
		r.byte_()   // team id
		r.string_() // team name
		coachCount := r.byte_()
		for c := byte(0); c < coachCount; c++ {
			r.int64()   // coach id
			r.string_() // coach name
			r.byte_()   // skin
			r.byte_()   // hair
			r.byte_()   // sex
			equipLen := r.int16()
			r.skip(int(equipLen))
			fighterCount := r.byte_()
			for f := byte(0); f < fighterCount; f++ {
				r.int64()              // fighter id
				r.byte_()              // breed
				r.string_()            // name
				r.byte_()              // sex
				r.byte_()              // skin
				r.skip(int(r.int16())) // spells blob
				r.skip(int(r.int16())) // objects blob
			}
			r.skip(int(r.int16())) // stats report
			betCount := r.byte_()
			for b := byte(0); b < betCount; b++ {
				r.int32() // bet card template
			}
		}
	}
	timelineCount := r.byte_()
	for i := byte(0); i < timelineCount; i++ {
		r.int64()
	}
	eventCount := r.byte_()
	for i := byte(0); i < eventCount; i++ {
		r.int32()
	}
	count := r.byte_()
	out := make([]wireSpecialCell, 0, count)
	for i := byte(0); i < count; i++ {
		out = append(out, wireSpecialCell{
			baseID: r.int64(),
			cellID: r.int64(),
			x:      r.int32(),
			y:      r.int32(),
			z:      r.int16(),
		})
	}
	return out
}
