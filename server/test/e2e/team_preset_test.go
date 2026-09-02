package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const (
	opTeamPresetDelete  = 6023 // C2S: [i64 teamId][u16][u16]
	opTeamPresetDeleted = 6022 // S2C agH: [i8 status](+[i16 teamId] on success)
	opTeamPresetSave    = 6021 // C2S
	opTeamPresetSaved   = 6020 // S2C aic_0: [i8 status](+preset on success)
	opTeamPresetList    = 6030 // S2C
)

// TestDeletedTeamPresetIsAcknowledged: re-sending the preset list does NOT remove
// a deleted preset from the client.
//
// `dx_2` case 6030 calls `bs_0.IF().IG()`, which purges only the presets where
// `afK()` is false (`bMK.size() > 1` - the DUO ones) and KEEPS the normal ones,
// then merges the payload into a map keyed by preset id. A deleted preset is
// simply absent from that payload, so nothing ever removes it and it stays on
// screen until the player relogs. 6022 is what calls `bs_0.IF().as(id)`.
func TestDeletedTeamPresetIsAcknowledged(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "delp", "DelP")
	reachWorld(t, c)

	team := &domain.Team{CoachID: uint(coachID), Name: "Doomed", Type: -1, GameMode: 1}
	if err := st.Teams.Upsert(team); err != nil {
		t.Fatalf("create preset: %v", err)
	}
	c.DrainReceived(300 * time.Millisecond)

	_ = c.Send(2, opTeamPresetDelete,
		testclient.NewW().I64(int64(team.ID)).U16(0).U16(0).Bytes())

	f, _, err := c.WaitFor(opTeamPresetDeleted, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 6022 after deleting a preset: the client keeps showing it: %v", err)
	}
	r := testclient.NewR(f.Payload)
	if status := r.U8(); status != 0 {
		t.Fatalf("6022 status = %d, want 0", status)
	}
	if got := r.U16(); got != uint16(team.ID) {
		t.Errorf("6022 preset id = %d, want %d - the client removes BY this id, so a "+
			"wrong one deletes the wrong row", got, team.ID)
	}
}

// TestDuplicateTeamNameIsRefused: retail refuses a preset name already in use -
// the client carries a dedicated status (25) and the string
// "error.teamManagement.teamNameExist" for exactly this. Without it the save
// silently succeeded and the team panel listed two identical names.
//
// The error frame is ONE byte: aic_0 reads the preset only inside
// `if (aV == 0)`, so anything appended would be left unread on a message the
// client considers complete.
func TestDuplicateTeamNameIsRefused(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "dupn", "DupN")
	reachWorld(t, c)

	existing := &domain.Team{CoachID: uint(coachID), Name: "Alpha", Type: -1, GameMode: 1}
	if err := st.Teams.Upsert(existing); err != nil {
		t.Fatalf("seed preset: %v", err)
	}
	c.DrainReceived(300 * time.Millisecond)

	_ = c.Send(2, opTeamPresetSave, teamPresetSaveBlob("Alpha"))

	f, _, err := c.WaitFor(opTeamPresetSaved, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 6020 for a duplicate preset name: the save looks like it "+
			"worked and the panel shows two identical names: %v", err)
	}
	if n := len(f.Payload); n != 1 {
		t.Errorf("error frame = %d bytes, want 1 (aic_0 reads nothing after a "+
			"non-zero status)", n)
	}
	if got := testclient.NewR(f.Payload).U8(); got != 25 {
		t.Errorf("status = %d, want 25 (teamNameExist)", got)
	}

	// And it must NOT have been written.
	teams, err := st.Teams.ListByCoach(uint(coachID))
	if err != nil {
		t.Fatalf("list: %v", err)
	}
	if len(teams) != 1 {
		t.Errorf("coach has %d presets after a refused save, want 1", len(teams))
	}
}

// TestDistinctTeamNameStillSaves guards the over-correction: only a DIFFERENT
// preset with the same name is a clash.
func TestDistinctTeamNameStillSaves(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "okn", "OkN")
	reachWorld(t, c)
	existing := &domain.Team{CoachID: uint(coachID), Name: "Alpha", Type: -1, GameMode: 1}
	if err := st.Teams.Upsert(existing); err != nil {
		t.Fatalf("seed preset: %v", err)
	}
	c.DrainReceived(300 * time.Millisecond)

	_ = c.Send(2, opTeamPresetSave, teamPresetSaveBlob("Beta"))
	if _, _, err := c.WaitFor(opTeamPresetList, testclient.DefaultTimeout); err != nil {
		t.Fatalf("a distinct name was not saved: %v", err)
	}
	teams, _ := st.Teams.ListByCoach(uint(coachID))
	if len(teams) != 2 {
		t.Errorf("coach has %d presets, want 2", len(teams))
	}
}

// teamPresetSaveBlob builds a minimal valid 6021 payload: a normal (non-special)
// preset with no fighters, which is all the duplicate-name check needs.
//
// Layout from decodeTeamPreset: [u16 type][u16 teamId][u16 gameMode][u8 len name]
// then, for special types only, 4 appearance bytes - type -1 is not special, so
// they are omitted - then [u8 fighterCount] and the fighter entries.
func teamPresetSaveBlob(name string) []byte {
	return testclient.NewW().
		U16(uint16(0xFFFF)). // type -1
		U16(0).              // teamId 0 = new
		U16(1).              // gameMode
		Str8(name).
		U8(0). // no fighters
		U8(0). // no coach list (the 2v2 tail; decodeTeamPreset requires the count)
		Bytes()
}
