package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// fighterBlobState walks a type-2 et_2 blob and returns its state byte, or -1 when
// the blob is a classic (type-1) fighter.
func fighterBlobState(t *testing.T, blob []byte) int {
	t.Helper()
	if len(blob) == 0 {
		t.Fatal("empty fighter blob")
	}
	if blob[0] != 2 {
		return -1 // classic fighter: no evolution tail
	}
	// [u8 type][u16 budget][u8 breed][u8 nameLen][name][u8 sex][u8 ey][3 colours]
	// [u16 spellLen][spells][u16 cardLen][cards] then the evolution tail.
	p := 1 + 2 + 1
	p += 1 + int(blob[p]) // name
	p += 1 + 1 + 3        // sex, ey, colours
	p += 2 + (int(blob[p])<<8 | int(blob[p+1]))
	p += 2 + (int(blob[p])<<8 | int(blob[p+1]))
	// tail: [i32 sphereBoardId][i32 xp][i32 totalXp][u8 tiredness][u8 morale][u8 state]
	if p+15 > len(blob) {
		t.Fatalf("type-2 blob has no evolution tail (len %d, tail at %d)", len(blob), p)
	}
	return int(blob[p+14])
}

// readFighterList decodes FIGHTER_LIST (6006) into fighterId -> state (-1 when the
// fighter is a classic type-1 blob).
func readFighterList(t *testing.T, payload []byte) map[int64]int {
	t.Helper()
	r := testclient.NewR(payload)
	_ = r.I64() // server time (seconds)
	n := int(r.U8())
	out := make(map[int64]int, n)
	for i := 0; i < n; i++ {
		id := r.I64()
		blobLen := int(r.U16())
		if blobLen > r.Remaining() {
			t.Fatalf("fighter %d: blob length %d exceeds payload", id, blobLen)
		}
		out[id] = fighterBlobState(t, r.RawN(blobLen))
	}
	return out
}

// TestGraveyardRosterAndResurrection drives the whole evolution/graveyard
// subsystem over the wire:
//
//  1. opening the graveyard (6031) must be answered, or the client soft-locks
//     behind an unclosable loading veil;
//  2. an interred fighter must arrive as an evolution (type-2) blob whose state
//     byte is 3 — that is exactly what the client's graveyard list filters on;
//  3. using a resurrection card on it (22099) returns it to the bench and
//     consumes the card.
func TestGraveyardRosterAndResurrection(t *testing.T) {
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "grave_a", "GraveA")
	reachWorld(t, c)

	// A living fighter and one that died and was interred.
	alive := &domain.Fighter{
		CoachID: uint(coachID), BreedID: 1, Name: "Alive",
		State: domain.FighterStateTitular,
	}
	buried := &domain.Fighter{
		CoachID: uint(coachID), BreedID: 1, Name: "Buried",
		State: domain.FighterStateGraveyard,
	}
	if err := st.Fighters.Create(alive); err != nil {
		t.Fatalf("create alive fighter: %v", err)
	}
	if err := st.Fighters.Create(buried); err != nil {
		t.Fatalf("create buried fighter: %v", err)
	}
	c.DrainReceived(200 * time.Millisecond)

	// 1. Opening the graveyard sends 6031; the server must answer with the roster
	//    (6006) AND the preset list (6030) — the latter is what dismisses the
	//    client's loading veil.
	_ = c.Send(2, testclient.OpTeamPresetListReq, nil)
	f, _, err := c.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FighterList(6006) after 6031 — the graveyard would open empty: %v", err)
	}
	states := readFighterList(t, f.Payload)

	if got, ok := states[int64(alive.ID)]; !ok || got != -1 {
		t.Errorf("living fighter state = %d (ok=%v), want a classic type-1 blob", got, ok)
	}
	if got, ok := states[int64(buried.ID)]; !ok {
		t.Error("interred fighter missing from the roster")
	} else if got != int(domain.FighterStateGraveyard) {
		t.Errorf("interred fighter state = %d, want %d (graveyard)",
			got, domain.FighterStateGraveyard)
	}

	if _, _, err := c.WaitFor(testclient.OpTeamPresetList, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no TeamPresetList(6030) — the client's loading veil would never lift: %v", err)
	}

	// 2. Resurrect: drop a consumable onto the interred fighter. Zaap card 202 is
	//    granted at login, so it is guaranteed to be in the inventory.
	const consumable int32 = 202
	req := testclient.NewW().I64(int64(buried.ID)).I32(consumable).Bytes()
	_ = c.Send(3, 22099, req)

	// The refreshed roster is pushed; the fighter is now on the bench.
	f, _, err = c.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no refreshed FighterList after resurrection: %v", err)
	}
	if got := readFighterList(t, f.Payload)[int64(buried.ID)]; got != int(domain.FighterStateBench) {
		t.Errorf("resurrected fighter state = %d, want %d (bench)",
			got, domain.FighterStateBench)
	}

	// It really moved in the database, not just on the wire.
	after, err := st.Fighters.Get(buried.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	if after.State != domain.FighterStateBench {
		t.Errorf("persisted state = %d, want %d", after.State, domain.FighterStateBench)
	}

	// And the resurrection card was consumed.
	var left int64
	st.DB().Model(&domain.CoachCard{}).
		Where("coach_id = ? AND template_id = ?", coachID, consumable).
		Count(&left)
	if left != 0 {
		t.Errorf("resurrection card still owned (%d rows), want it consumed", left)
	}
}

// TestGraveyardStateTransitionPersists: the client moves a dead fighter to the
// graveyard optimistically via 23000 and consumes no reply, so the server must
// reproduce the transition and persist it.
func TestGraveyardStateTransitionPersists(t *testing.T) {
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "grave_b", "GraveB")
	reachWorld(t, c)

	dead := &domain.Fighter{
		CoachID: uint(coachID), BreedID: 1, Name: "Doomed",
		State: domain.FighterStateDead,
	}
	if err := st.Fighters.Create(dead); err != nil {
		t.Fatalf("create fighter: %v", err)
	}
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, 23000, testclient.NewW().I64(int64(dead.ID)).U8(0).Bytes())
	if _, _, err := c.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no refreshed FighterList after state change: %v", err)
	}

	after, err := st.Fighters.Get(dead.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	if after.State != domain.FighterStateGraveyard {
		t.Errorf("state = %d, want %d (graveyard)", after.State, domain.FighterStateGraveyard)
	}
}
