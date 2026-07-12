package e2e

import (
	"math"
	"strings"
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// sendVicinityText builds and sends a VICINITY_MESSAGE with the 2-byte
// length prefix the wire format uses for this opcode (see
// docs/02-protocol.md and social_chat_test.go).
func (c *testClient) sendVicinityText(msg string) {
	payload := putInt16(int16(len(msg)))
	payload = append(payload, msg...)
	c.send(4, protocol.RecvVicinityMessage, payload)
}

// expectGMFeedback reads the next frame, asserting it's the synthetic
// "Server" PRIVATE_MESSAGE reply GM commands use (see
// handlers_gm_commands.go's sendGMFeedback) and returns its message text.
func (c *testClient) expectGMFeedback() string {
	c.t.Helper()
	payload := c.expectOpcode(protocol.SendPrivateMessage)
	r := newPayloadReader(payload)
	sender := r.string_()
	r.int64()
	message := r.string_()
	if sender != "Server" {
		c.t.Fatalf("GM feedback sender = %q, want %q", sender, "Server")
	}
	return message
}

// inventoryUpdateAddedTemplates parses a COACH_INVENTORY_UPDATE_MESSAGE
// payload (see packets_inventory.go's inventoryDelta.build) and returns the
// template IDs present in its AddInventory section.
func inventoryUpdateAddedTemplates(payload []byte) []int32 {
	r := newPayloadReader(payload)

	skipEntries := func(n int, entrySize int) {
		r.skip(n * entrySize)
	}

	addEquipCount := int(uint16(r.int16()))
	skipEntries(addEquipCount, 15)
	removeEquipCount := int(uint16(r.int16()))
	skipEntries(removeEquipCount, 2)

	addInvCount := int(uint16(r.int16()))
	ids := make([]int32, 0, addInvCount)
	for i := 0; i < addInvCount; i++ {
		ids = append(ids, r.int32()) // template id
		r.int64()                    // card instance id
		r.byte_()                    // flag
		r.int16()                    // quantity
	}
	return ids
}

// realCoachCardID is a coach-card template id known to exist in the real
// data/cards.dat file (verified directly against the parsed store; see
// TestRealDataFilesParse's log output).
const realCoachCardID = int32(2)

func TestE2E_GMCommandsRejectedForNonAdmin(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw") // not admin

	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	c.sendVicinityText("/STATS")

	// No GM command should have executed and nothing should have been
	// broadcast either (a '/'-prefixed message is never vicinity chat). A
	// follow-up private message to an unknown user gives a deterministic
	// "next frame" to assert against: if /STATS had executed for a
	// non-admin, PLAYER_STATISTICS_REPORT would arrive first and this
	// expectOpcode would fail on the mismatch.
	c.send(4, protocol.RecvPrivateMessage, append(pstring("Nobody"), pstring("hi")...))
	result := c.expectOpcode(protocol.SendUserNotFound)
	if newPayloadReader(result).string_() != "Nobody" {
		t.Error("expected the USER_NOT_FOUND probe to arrive undisturbed")
	}
}

func TestE2E_GMStatsCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/STATS")
	c.expectOpcode(protocol.SendPlayerStatisticsReport)
}

func TestE2E_GMCellIDCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/CELLID")
	msg := c.expectGMFeedback()
	// A freshly created coach starts at (1,1,0), see
	// service.CoachService.CreateCoach.
	if msg != "1,1,0" {
		t.Errorf("/CELLID reply = %q, want %q", msg, "1,1,0")
	}
}

func TestE2E_GMTeleportCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/TP 10 20 3 7 1")
	payload := c.expectOpcode(protocol.SendEnterWorldInstance)
	r := newPayloadReader(payload)
	x := math.Float32frombits(uint32(r.int32()))
	y := math.Float32frombits(uint32(r.int32()))
	z := r.int16()
	mapID := r.int16()
	dynamic := r.byte_()
	if x != 10 || y != 20 || z != 3 || mapID != 7 || dynamic != 1 {
		t.Errorf("ENTER_WORLD_INSTANCE = x=%v y=%v z=%d map=%d dynamic=%d, want 10,20,3,7,1", x, y, z, mapID, dynamic)
	}

	// Position must persist -- a subsequent /CELLID reflects it.
	c.sendVicinityText("/CELLID")
	msg := c.expectGMFeedback()
	if msg != "10,20,3" {
		t.Errorf("/CELLID after /TP = %q, want %q", msg, "10,20,3")
	}
}

func TestE2E_GMTeleportInvalidArgs(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/TP not enough args")
	msg := c.expectGMFeedback()
	if !strings.HasPrefix(msg, "Usage: /TP") {
		t.Errorf("/TP with bad args reply = %q, want a usage message", msg)
	}
}

func TestE2E_GMCardCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/CARD " + itoa32(realCoachCardID))
	update := c.expectOpcode(protocol.SendCoachInventoryUpdateMessage)
	got := inventoryUpdateAddedTemplates(update)
	if len(got) != 1 || got[0] != realCoachCardID {
		t.Fatalf("inventory update added templates = %v, want [%d]", got, realCoachCardID)
	}
	msg := c.expectGMFeedback()
	if !strings.Contains(msg, itoa32(realCoachCardID)) {
		t.Errorf("/CARD feedback = %q, want it to mention template %d", msg, realCoachCardID)
	}
}

func TestE2E_GMCardUnknownTemplateRejected(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/CARD 99999999")
	msg := c.expectGMFeedback()
	if !strings.Contains(msg, "Unknown coach card template") {
		t.Errorf("/CARD with unknown template reply = %q, want an 'unknown template' message", msg)
	}
}

func TestE2E_GMAllCardsCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/ALLCARDS")
	update := c.expectOpcode(protocol.SendCoachInventoryUpdateMessage)
	got := inventoryUpdateAddedTemplates(update)
	if len(got) == 0 {
		t.Fatal("/ALLCARDS should have granted at least one card template")
	}
	msg := c.expectGMFeedback()
	if !strings.HasPrefix(msg, "Granted") {
		t.Errorf("/ALLCARDS feedback = %q, want it to start with 'Granted'", msg)
	}
}

func TestE2E_GMPresCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/PRES")
	payload := c.expectOpcode(protocol.SendStartPresentation)
	if len(payload) != 0 {
		t.Errorf("/PRES payload = %v, want empty", payload)
	}
}

func TestE2E_GMCancelCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/CANCEL 5")
	payload := c.expectOpcode(protocol.SendFightCreationCanceledMessage)
	r := newPayloadReader(payload)
	fightID := r.int64()
	reason := r.byte_()
	if fightID != 0 || reason != 5 {
		t.Errorf("FIGHT_CREATION_CANCELED_MESSAGE = fightID=%d reason=%d, want 0,5", fightID, reason)
	}
}

func TestE2E_GMUnknownCommand(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("boss", "pw", "Boss")

	c.sendVicinityText("/NOPE")
	msg := c.expectGMFeedback()
	if !strings.Contains(msg, "Unknown command") {
		t.Errorf("unknown command reply = %q, want it to mention 'Unknown command'", msg)
	}
}

// TestE2E_AdminNormalChatStillBroadcasts confirms admin privilege doesn't
// change ordinary (non-'/') vicinity chat behavior.
func TestE2E_AdminNormalChatStillBroadcasts(t *testing.T) {
	a, addr := startTestServer(t)
	seedAdminAccount(t, a, "boss", "pw")
	seedAccount(t, a, "alice", "pw")

	cBoss := dialTestClient(t, addr)
	cBoss.mustLogin("boss", "pw", "Boss")
	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBoss.drainUntil(protocol.SendActorSpawn, 5)

	cBoss.sendVicinityText("hello from the admin")
	received := cAlice.expectOpcode(protocol.SendVicinityMessage)
	r := newPayloadReader(received)
	sender := r.string_()
	r.int64()
	msg := r.string_()
	if sender != "Boss" || msg != "hello from the admin" {
		t.Errorf("VICINITY_MESSAGE sender=%q message=%q", sender, msg)
	}
}

func itoa32(v int32) string {
	if v == 0 {
		return "0"
	}
	neg := v < 0
	if neg {
		v = -v
	}
	var buf [16]byte
	i := len(buf)
	for v > 0 {
		i--
		buf[i] = byte('0' + v%10)
		v /= 10
	}
	if neg {
		i--
		buf[i] = '-'
	}
	return string(buf[i:])
}
