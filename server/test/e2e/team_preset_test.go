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
