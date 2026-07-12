package e2e

import (
	"bufio"
	"encoding/binary"
	"io"
	"net"
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// testClient is a minimal raw-socket protocol client for e2e testing,
// mirroring go-server/scripts/smoke_test.py's framing logic but as a
// reusable Go test helper.
type testClient struct {
	t    *testing.T
	conn net.Conn
	r    *bufio.Reader
}

func dialTestClient(t *testing.T, addr string) *testClient {
	t.Helper()
	conn, err := net.DialTimeout("tcp", addr, 5*time.Second)
	if err != nil {
		t.Fatalf("dial %s: %v", addr, err)
	}
	t.Cleanup(func() { _ = conn.Close() })
	_ = conn.SetDeadline(time.Now().Add(10 * time.Second))
	return &testClient{t: t, conn: conn, r: bufio.NewReader(conn)}
}

// send writes a framed client->server packet: [size][archTarget][opcode][payload].
func (c *testClient) send(archTarget byte, opcode protocol.RecvOpcode, payload []byte) {
	c.t.Helper()
	totalSize := uint16(protocol.InboundHeaderSize + len(payload))
	header := make([]byte, protocol.InboundHeaderSize)
	binary.BigEndian.PutUint16(header[0:2], totalSize)
	header[2] = archTarget
	binary.BigEndian.PutUint16(header[3:5], uint16(opcode))

	if _, err := c.conn.Write(header); err != nil {
		c.t.Fatalf("send header: %v", err)
	}
	if len(payload) > 0 {
		if _, err := c.conn.Write(payload); err != nil {
			c.t.Fatalf("send payload: %v", err)
		}
	}
}

// recvFrame reads exactly one server->client frame: [size][opcode][payload].
func (c *testClient) recvFrame() (protocol.SendOpcode, []byte, error) {
	header := make([]byte, protocol.OutboundHeaderSize)
	if _, err := io.ReadFull(c.r, header); err != nil {
		return 0, nil, err
	}
	totalSize := binary.BigEndian.Uint16(header[0:2])
	opcode := protocol.SendOpcode(binary.BigEndian.Uint16(header[2:4]))
	payloadLen := int(totalSize) - protocol.OutboundHeaderSize
	payload := make([]byte, payloadLen)
	if payloadLen > 0 {
		if _, err := io.ReadFull(c.r, payload); err != nil {
			return 0, nil, err
		}
	}
	return opcode, payload, nil
}

// expectOpcode reads the next frame and fails the test if its opcode
// doesn't match want. Returns the payload.
func (c *testClient) expectOpcode(want protocol.SendOpcode) []byte {
	c.t.Helper()
	opcode, payload, err := c.recvFrame()
	if err != nil {
		c.t.Fatalf("recvFrame: %v (expected opcode %d)", err, want)
	}
	if opcode != want {
		c.t.Fatalf("got opcode %d, want %d (payload=%v)", opcode, want, payload)
	}
	return payload
}

// drainUntil reads frames (up to max) until one with the wanted opcode is
// found, returning its payload. Useful for skipping over broadcast
// messages whose exact ordering isn't the point of a given test.
func (c *testClient) drainUntil(want protocol.SendOpcode, max int) []byte {
	c.t.Helper()
	for i := 0; i < max; i++ {
		opcode, payload, err := c.recvFrame()
		if err != nil {
			c.t.Fatalf("drainUntil(%d): %v", want, err)
		}
		if opcode == want {
			return payload
		}
	}
	c.t.Fatalf("drainUntil(%d): opcode not seen within %d frames", want, max)
	return nil
}

// --- payload builders (1-byte length-prefixed strings, per wire protocol) ---

func pstring(s string) []byte {
	if len(s) > 255 {
		panic("pstring: too long")
	}
	return append([]byte{byte(len(s))}, s...)
}

func putInt64(v int64) []byte {
	b := make([]byte, 8)
	binary.BigEndian.PutUint64(b, uint64(v))
	return b
}

func putInt32(v int32) []byte {
	b := make([]byte, 4)
	binary.BigEndian.PutUint32(b, uint32(v))
	return b
}

func putInt16(v int16) []byte {
	b := make([]byte, 2)
	binary.BigEndian.PutUint16(b, uint16(v))
	return b
}

// inventoryBlob builds the client's ArrayInventory wire format used for the
// equipment/card inventory: repeated [short pos][int32 id] pairs (see
// internal/dispatch/inventory_codec.go). Do NOT use for spells -- see
// spellBlob.
func inventoryBlob(ids ...int32) []byte {
	out := make([]byte, 0, len(ids)*6)
	for i, id := range ids {
		out = append(out, putInt16(int16(i))...)
		out = append(out, putInt32(id)...)
	}
	return out
}

// parseInventoryBlob reads an ArrayInventory (equipment/card) blob back
// into its IDs, ignoring the position shorts. Do NOT use for spells -- see
// parseSpellBlob.
func parseInventoryBlob(blob []byte) []int32 {
	var ids []int32
	for len(blob) >= 6 {
		ids = append(ids, int32(binary.BigEndian.Uint32(blob[2:6])))
		blob = blob[6:]
	}
	return ids
}

// parseInventoryBlobPositions reads an ArrayInventory (equipment/card)
// blob back into a map of id -> wire position. Used to assert the server
// assigns each item's real, client-required equipment-category slot
// (weapon=0, pet=1, cloak=2, hat=3, dofus=4) rather than a sequential
// array index -- see internal/gamedata.FighterCardInventoryPosition.
func parseInventoryBlobPositions(blob []byte) map[int32]int16 {
	out := make(map[int32]int16)
	for len(blob) >= 6 {
		pos := int16(binary.BigEndian.Uint16(blob[0:2]))
		id := int32(binary.BigEndian.Uint32(blob[2:6]))
		out[id] = pos
		blob = blob[6:]
	}
	return out
}

// spellBlob builds the client's StackInventory<Spell>(serializeQuantity=false)
// wire format for a fighter's spell ids: a flat concatenation of int32 ids,
// no leading pos, no trailing quantity (see internal/dispatch/inventory_codec.go).
func spellBlob(ids ...int32) []byte {
	out := make([]byte, 0, len(ids)*4)
	for _, id := range ids {
		out = append(out, putInt32(id)...)
	}
	return out
}

// parseSpellBlob reads a flat spell-id blob back into its IDs.
func parseSpellBlob(blob []byte) []int32 {
	var ids []int32
	for len(blob) >= 4 {
		ids = append(ids, int32(binary.BigEndian.Uint32(blob[0:4])))
		blob = blob[4:]
	}
	return ids
}

// --- payload readers ---

type payloadReader struct {
	buf []byte
	pos int
}

func newPayloadReader(buf []byte) *payloadReader {
	return &payloadReader{buf: buf}
}

func (r *payloadReader) byte_() byte {
	v := r.buf[r.pos]
	r.pos++
	return v
}

func (r *payloadReader) int16() int16 {
	v := int16(binary.BigEndian.Uint16(r.buf[r.pos:]))
	r.pos += 2
	return v
}

func (r *payloadReader) int32() int32 {
	v := int32(binary.BigEndian.Uint32(r.buf[r.pos:]))
	r.pos += 4
	return v
}

func (r *payloadReader) int64() int64 {
	v := int64(binary.BigEndian.Uint64(r.buf[r.pos:]))
	r.pos += 8
	return v
}

func (r *payloadReader) string_() string {
	n := int(r.byte_())
	s := string(r.buf[r.pos : r.pos+n])
	r.pos += n
	return s
}

func (r *payloadReader) skip(n int) {
	r.pos += n
}

// --- high-level flows ---

// authenticate performs the AUTHENTICATION handshake and returns the
// result code.
func (c *testClient) authenticate(login, password string) byte {
	c.t.Helper()
	payload := append(pstring(login), pstring(password)...)
	c.send(1, protocol.RecvAuthentication, payload)
	result := c.expectOpcode(protocol.SendAuthenticationResult)
	return result[0]
}

// mustLogin authenticates and, if needed, creates a coach, draining all
// post-login packets. Returns the coach ID (parsed from COACH_INFORMATION).
func (c *testClient) mustLogin(login, password, coachName string) (coachID int64) {
	c.t.Helper()
	if code := c.authenticate(login, password); code != 0 {
		c.t.Fatalf("authenticate(%s) result code = %d, want 0", login, code)
	}
	c.expectOpcode(protocol.SendQueueNotification)

	opcode, payload, err := c.recvFrame()
	if err != nil {
		c.t.Fatalf("recvFrame after auth: %v", err)
	}

	if opcode == protocol.SendCoachCreationRequest {
		creation := append(pstring(coachName), []byte{0, 0, 0}...)
		c.send(2, protocol.RecvCoachCreation, creation)
		result := c.expectOpcode(protocol.SendCoachCreationResult)
		if result[0] != 0 {
			c.t.Fatalf("coach creation result code = %d, want 0", result[0])
		}
		payload = c.expectOpcode(protocol.SendCoachInformation)
	} else if opcode != protocol.SendCoachInformation {
		c.t.Fatalf("unexpected opcode after auth: %d", opcode)
	}

	r := newPayloadReader(payload)
	coachID = r.int64()

	// Drain the rest of the post-login sequence: FRIEND_LIST, IGNORE_LIST,
	// PLAYER_STATISTICS_REPORT, ENTER_WORLD_INSTANCE.
	c.drainUntil(protocol.SendEnterWorldInstance, 10)

	// If any other coach is already online, joining triggers an
	// ACTOR_SPAWN broadcast to EVERY online coach, including this new
	// connection itself (see dispatch.enterWorld's fan-out loop) --
	// arriving immediately after ENTER_WORLD_INSTANCE on this very
	// connection. Opportunistically drain it here (best-effort, short
	// deadline) so callers don't have to know about this side effect.
	// This is not sent at all for the very first player to log in (no
	// other online coaches yet), hence the short deadline + tolerance for
	// a timeout rather than a hard requirement.
	c.drainOptionalActorSpawn()

	return coachID
}

// tryExpectOpcode attempts a single read within timeout and reports
// whether a frame with the wanted opcode arrived. Unlike expectOpcode,
// this does NOT fail the test if a different opcode (or no frame at all)
// arrives within timeout -- it simply returns false, leaving the read
// deadline restored to the normal long value either way. Useful for
// probing "does sending X produce Y" without knowing in advance whether Y
// (or some other/no response) is the actual outcome, e.g. testing that a
// rejected action produces silence rather than asserting an exact
// response opcode.
func (c *testClient) tryExpectOpcode(want protocol.SendOpcode, timeout time.Duration) bool {
	c.t.Helper()
	_ = c.conn.SetReadDeadline(time.Now().Add(timeout))
	opcode, _, err := c.recvFrame()
	_ = c.conn.SetReadDeadline(time.Now().Add(10 * time.Second))
	return err == nil && opcode == want
}

// tryExpectFrame is like tryExpectOpcode but also returns the frame payload
// on success, for tests that need to inspect the matched frame's bytes.
func (c *testClient) tryExpectFrame(want protocol.SendOpcode, timeout time.Duration) ([]byte, bool) {
	c.t.Helper()
	_ = c.conn.SetReadDeadline(time.Now().Add(timeout))
	opcode, payload, err := c.recvFrame()
	_ = c.conn.SetReadDeadline(time.Now().Add(10 * time.Second))
	if err != nil || opcode != want {
		return nil, false
	}
	return payload, true
}

// drainOptionalActorSpawn attempts a single non-blocking-ish read for an
// ACTOR_SPAWN frame that may or may not be pending (see mustLogin's doc
// comment). Uses a short deadline so it doesn't stall tests where no such
// frame is coming.
func (c *testClient) drainOptionalActorSpawn() {
	c.t.Helper()
	_ = c.conn.SetReadDeadline(time.Now().Add(200 * time.Millisecond))
	opcode, _, err := c.recvFrame()
	// Restore the long deadline used for the rest of the test regardless
	// of outcome.
	_ = c.conn.SetReadDeadline(time.Now().Add(10 * time.Second))
	if err != nil {
		return // no frame arrived in time -- fine, nothing to drain
	}
	if opcode != protocol.SendActorSpawn {
		c.t.Fatalf("drainOptionalActorSpawn: unexpected opcode %d arrived right after login", opcode)
	}
}
