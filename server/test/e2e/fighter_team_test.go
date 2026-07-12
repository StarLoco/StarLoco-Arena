package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

func TestE2E_FighterCreateListDelete(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	// FIGHTER_CREATE_REQUEST payload: short(unused) byte(version)
	// short(budget) byte(breed) pstring(name) byte(sex) byte(skin)
	// short(spellsLen) bytes short(objectsLen) bytes
	payload := append([]byte{0, 0}, 1) // unused short + version byte
	payload = append(payload, putInt16(100)...)
	payload = append(payload, 1) // breed
	payload = append(payload, pstring("Bob")...)
	payload = append(payload, 0, 0)           // sex, skin
	payload = append(payload, putInt16(0)...) // spells len
	payload = append(payload, putInt16(0)...) // objects len

	c.send(3, protocol.RecvFighterCreateRequest, payload)
	result := c.expectOpcode(protocol.SendFighterCreateResult)
	if result[0] != 0 {
		t.Fatalf("fighter create error code = %d, want 0", result[0])
	}
	r := newPayloadReader(result)
	errorCode := r.byte_()
	fighterID := r.int64()
	if errorCode != 0 || fighterID == 0 {
		t.Fatalf("errorCode=%d fighterID=%d", errorCode, fighterID)
	}

	c.send(3, protocol.RecvFighterInformationListRequest, nil)
	listPayload := c.expectOpcode(protocol.SendFighterInformationList)
	lr := newPayloadReader(listPayload)
	count := lr.byte_()
	if count != 1 {
		t.Fatalf("fighter list count = %d, want 1", count)
	}

	c.send(3, protocol.RecvFighterDeleteRequest, putInt64(fighterID))
	delResult := c.expectOpcode(protocol.SendFighterDeletionResult)
	dr := newPayloadReader(delResult)
	if dr.byte_() != 0 {
		t.Error("fighter delete error code should be 0")
	}

	c.send(3, protocol.RecvFighterInformationListRequest, nil)
	listPayload2 := c.expectOpcode(protocol.SendFighterInformationList)
	if listPayload2[0] != 0 {
		t.Errorf("fighter count after delete = %d, want 0", listPayload2[0])
	}
}

func TestE2E_TeamPresetSaveAndList(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	fighterID := createFighter(t, c, "Bob")

	// TEAM_PRESET_SAVE_REQUEST: short(slot) pstring(name) byte(count) [long fighterId]*
	// A brand-new preset is sent with slot == -1 (the client's TeamPreset
	// constructor initializes id = -1); the server allocates and echoes
	// back the real slot.
	payload := putInt16(-1)
	payload = append(payload, pstring("MyTeam")...)
	payload = append(payload, 1)
	payload = append(payload, putInt64(fighterID)...)

	c.send(3, protocol.RecvTeamPresetSaveRequest, payload)
	saveResult := c.expectOpcode(protocol.SendTeamPresetSave)
	if saveResult[0] != 0 {
		t.Fatalf("team save error code = %d, want 0", saveResult[0])
	}
	sr := newPayloadReader(saveResult)
	sr.byte_() // error code
	assignedSlot := sr.int16()
	if assignedSlot < 0 {
		t.Errorf("server should echo back a non-negative allocated slot, got %d", assignedSlot)
	}

	listResult := c.expectOpcode(protocol.SendTeamPresetList)
	lr := newPayloadReader(listResult)
	teamCount := lr.byte_()
	if teamCount != 1 {
		t.Fatalf("team count = %d, want 1", teamCount)
	}
	listedSlot := lr.int16()
	if listedSlot != assignedSlot {
		t.Errorf("listed slot %d != save-result slot %d", listedSlot, assignedSlot)
	}
	lr.string_() // team name
	listedFighterCount := lr.byte_()
	if listedFighterCount != 1 {
		t.Fatalf("listed fighter count = %d, want 1", listedFighterCount)
	}
	// The fighter IDs MUST follow the count -- this is what makes team
	// rosters survive a reconnect (the client reads `count` longs).
	listedFighterID := lr.int64()
	if listedFighterID != fighterID {
		t.Errorf("listed fighter id = %d, want %d", listedFighterID, fighterID)
	}

	// Auto-triggered fighter re-list follows.
	c.expectOpcode(protocol.SendFighterInformationList)

	// TEAM_PRESET_DELETE_REQUEST: short(slot) -- using the real slot.
	c.send(3, protocol.RecvTeamPresetDeleteRequest, putInt16(assignedSlot))
	delResult := c.expectOpcode(protocol.SendTeamPresetDeletion)
	dr := newPayloadReader(delResult)
	if dr.byte_() != 0 {
		t.Error("team delete error code should be 0")
	}
}

// TestE2E_TeamFightersSurviveReconnect reproduces the reported bug: create
// fighters, save them into a team, disconnect, reconnect, and confirm the
// team still lists its fighters. This is a regression guard for the missing
// fighter-IDs bug in TEAM_PRESET_LIST.
func TestE2E_TeamFightersSurviveReconnect(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")

	// --- Session 1: create fighters + save a team ---
	c1 := dialTestClient(t, addr)
	c1.mustLogin("alice", "pw", "Alice")
	f1 := createFighter(t, c1, "Iop")
	f2 := createFighter(t, c1, "Cra")

	payload := putInt16(-1)
	payload = append(payload, pstring("Squad")...)
	payload = append(payload, 2)
	payload = append(payload, putInt64(f1)...)
	payload = append(payload, putInt64(f2)...)
	c1.send(3, protocol.RecvTeamPresetSaveRequest, payload)
	c1.expectOpcode(protocol.SendTeamPresetSave)
	c1.expectOpcode(protocol.SendTeamPresetList)
	c1.expectOpcode(protocol.SendFighterInformationList)

	// Disconnect.
	c1.conn.Close()

	// --- Session 2: reconnect and request the team list ---
	c2 := dialTestClient(t, addr)
	c2.mustLogin("alice", "pw", "Alice")

	c2.send(3, protocol.RecvTeamPresetListRequest, nil)
	listResult := c2.expectOpcode(protocol.SendTeamPresetList)
	lr := newPayloadReader(listResult)

	teamCount := lr.byte_()
	if teamCount != 1 {
		t.Fatalf("after reconnect: team count = %d, want 1", teamCount)
	}
	lr.int16() // slot
	name := lr.string_()
	if name != "Squad" {
		t.Errorf("team name = %q, want %q", name, "Squad")
	}
	fighterCount := lr.byte_()
	if fighterCount != 2 {
		t.Fatalf("after reconnect: team fighter count = %d, want 2", fighterCount)
	}
	gotF1 := lr.int64()
	gotF2 := lr.int64()
	if gotF1 != f1 || gotF2 != f2 {
		t.Errorf("team fighter IDs after reconnect = [%d, %d], want [%d, %d]", gotF1, gotF2, f1, f2)
	}
}

// createFighter is a small e2e helper: creates one fighter via the wire
// protocol and returns its ID.
func createFighter(t *testing.T, c *testClient, name string) int64 {
	t.Helper()
	payload := append([]byte{0, 0}, 1)
	payload = append(payload, putInt16(100)...)
	payload = append(payload, 1)
	payload = append(payload, pstring(name)...)
	payload = append(payload, 0, 0)
	payload = append(payload, putInt16(0)...)
	payload = append(payload, putInt16(0)...)

	c.send(3, protocol.RecvFighterCreateRequest, payload)
	result := c.expectOpcode(protocol.SendFighterCreateResult)
	r := newPayloadReader(result)
	r.byte_() // error code
	return r.int64()
}
