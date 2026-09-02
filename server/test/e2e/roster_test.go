package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// buildFighterBlob builds a minimal et_2 type-1 blob for FighterCreate:
// [u8 type=1][i16 budget][u8 breed][u8 name][u8 sex][i8 ey=-1][3 colors]
// [i16 spellLen=0][i16 cardLen=0].
func buildFighterBlob(name string, breed uint8, spellIDs ...int32) []byte {
	w := testclient.NewW()
	w.U8(1)             // type = info
	w.U16(0)            // budget
	w.U8(breed)         // breed
	w.Str8(name)        // name
	w.U8(0)             // sex (zv)
	w.U8(0xFF)          // ey = -1 -> colors follow
	w.U8(1).U8(2).U8(3) // hair, skin, eye
	// Spell blob: [i16 lengthInBytes][i32 spellId...]. Fighters created with no
	// spells cannot cast anything — castSpellByFighter refuses a spell the
	// fighter does not know — so any test that casts must pass them here.
	w.U16(uint16(len(spellIDs) * 4))
	for _, id := range spellIDs {
		w.I32(id)
	}
	w.U16(0) // card blob len
	return w.Bytes()
}

// TestFighterCreateAndList: creating a fighter (6001) returns a result (6000)
// and the fighter then appears in the roster; deleting removes it.
func TestFighterCreateAndList(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "roster_a", "Ras")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// FighterCreate: [u8 flag][i16 slot][i16 blobLen][blob].
	blob := buildFighterBlob("Champ", 5)
	req := testclient.NewW().U8(0).U16(0).U16(uint16(len(blob))).Raw(blob).Bytes()
	_ = a.Send(3, testclient.OpFighterCreate, req)

	res, _, err := a.WaitFor(testclient.OpFighterCreateResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FighterCreateResult: %v", err)
	}
	if code := testclient.NewR(res.Payload).U8(); code != 0 {
		t.Fatalf("fighter create result = %d, want 0", code)
	}

	// After create, the server must re-push the roster (6006) so the grid shows
	// the new fighter — the client never re-requests the list, so without this
	// the fighter never appears and reopening the panel shows a stale roster.
	list, _, err := a.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FighterList(6006) push after create: %v", err)
	}
	if n := fighterListCount(list.Payload); n != 1 {
		t.Errorf("roster after create = %d fighters, want 1", n)
	}

	// Verify persisted.
	fighters, err := st.Fighters.ListByCoach(uint(aID))
	if err != nil || len(fighters) != 1 {
		t.Fatalf("expected 1 fighter, got %d (err %v)", len(fighters), err)
	}
	if fighters[0].Name != "Champ" || fighters[0].BreedID != 5 {
		t.Errorf("fighter = %+v, want name Champ breed 5", fighters[0])
	}
	fighterID := fighters[0].ID

	// Delete it: [i64 fighterId][i16 slot].
	del := testclient.NewW().I64(int64(fighterID)).U16(0).Bytes()
	_ = a.Send(3, testclient.OpFighterDelete, del)

	// Delete must also re-push the (now empty) roster.
	list2, _, err := a.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FighterList(6006) push after delete: %v", err)
	}
	if n := fighterListCount(list2.Payload); n != 0 {
		t.Errorf("roster after delete = %d fighters, want 0", n)
	}

	time.Sleep(100 * time.Millisecond)
	after, _ := st.Fighters.ListByCoach(uint(aID))
	if len(after) != 0 {
		t.Errorf("fighter not deleted, %d remain", len(after))
	}
}

// fighterListCount reads the fighter count from a FighterInformationList(6006)
// payload: [i64 leadId][u8 count]{...}.
func fighterListCount(payload []byte) int {
	r := testclient.NewR(payload)
	_ = r.I64() // lead/coach id
	return int(r.U8())
}

// TestTeamPresetListRequestPushesRoster: opening the team-management panel
// (6031) must return BOTH the team preset list (6030) AND the fighter roster
// (6006). The client repopulates its (cleared-on-close) fighter grid from this
// 6006, so without it the roster is empty after a close/reopen.
func TestTeamPresetListRequestPushesRoster(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "reopen_a", "ReA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// Create a fighter, then drain the create/roster push traffic.
	blob := buildFighterBlob("Champ", 5)
	req := testclient.NewW().U8(0).U16(0).U16(uint16(len(blob))).Raw(blob).Bytes()
	_ = a.Send(3, testclient.OpFighterCreate, req)
	if _, _, err := a.WaitFor(testclient.OpFighterCreateResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no create result: %v", err)
	}
	a.DrainReceived(200 * time.Millisecond)
	if f, _ := st.Fighters.ListByCoach(uint(aID)); len(f) != 1 {
		t.Fatalf("setup: expected 1 fighter, got %d", len(f))
	}

	// Reopen the panel: send TeamPresetListRequest (6031).
	_ = a.Send(3, testclient.OpTeamPresetListReq, nil)

	// Expect a team preset list (6030) AND a fighter roster (6006, count 1).
	sawTeams, sawRoster := false, false
	deadline := time.Now().Add(testclient.DefaultTimeout)
	for time.Now().Before(deadline) && !(sawTeams && sawRoster) {
		f, err := a.Recv(time.Until(deadline))
		if err != nil {
			break
		}
		switch f.Opcode {
		case testclient.OpTeamPresetList:
			sawTeams = true
		case testclient.OpFighterList:
			if fighterListCount(f.Payload) != 1 {
				t.Errorf("roster on reopen = %d fighters, want 1", fighterListCount(f.Payload))
			}
			sawRoster = true
		}
	}
	if !sawTeams {
		t.Error("6031 did not return a team preset list (6030)")
	}
	if !sawRoster {
		t.Error("6031 did not re-push the fighter roster (6006) -> grid empty on reopen")
	}
}

// TestTeamPresetSaveDelete: saving a team preset persists it and it shows in the
// preset list; deleting removes it (regression guard for the i64 team-id fix).
func TestTeamPresetSaveDelete(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "team_a", "Tas")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// sw_1 blob: [i16 type][i16 teamId=0][i16 gameMode][u8 name][u8 fCount=0][u8 cCount=0].
	blob := testclient.NewW().
		U16(0).U16(0).U16(1).Str8("Alpha").U8(0).U8(0).Bytes()
	// TeamPresetSave = [sw_1 blob][u8 pad].
	_ = a.Send(3, testclient.OpTeamPresetSave, testclient.NewW().Raw(blob).U8(0).Bytes())
	time.Sleep(200 * time.Millisecond)

	teams, err := st.Teams.ListByCoach(uint(aID))
	if err != nil || len(teams) != 1 {
		t.Fatalf("expected 1 team, got %d (err %v)", len(teams), err)
	}
	teamID := teams[0].ID

	// Delete via the i64 id (the fix): [i64 teamId][i16 Gm][i16 fA].
	del := testclient.NewW().I64(int64(teamID)).U16(0).U16(0).Bytes()
	_ = a.Send(3, testclient.OpTeamPresetDelete, del)
	time.Sleep(200 * time.Millisecond)

	after, _ := st.Teams.ListByCoach(uint(aID))
	if len(after) != 0 {
		t.Errorf("team preset not deleted (i64 id bug?), %d remain", len(after))
	}
}
