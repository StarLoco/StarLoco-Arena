package botclient

import (
	"fmt"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// Session holds the identity a caller learns while logging in, so higher
// layers can address the bot (e.g. its coach id) without re-parsing frames.
type Session struct {
	CoachID int64
	// CoachCreated reports whether this login had to create the coach
	// (the account had none yet) versus reusing an existing one.
	CoachCreated bool
	// InventoryCardUIDs are the unique ids of the coach's unequipped
	// (Pos 0) cards, parsed from COACH_INFORMATION. Higher layers use these
	// to offer a card in an exchange (which addresses cards by uid).
	InventoryCardUIDs []int64
}

// Login runs the full connect-to-visible handshake: AUTHENTICATION ->
// AUTHENTICATION_RESULT -> QUEUE_NOTIFICATION -> (COACH_CREATION if the
// account has no coach) -> COACH_INFORMATION -> ... -> ENTER_WORLD_INSTANCE.
// On success the bot is a visible coach in the overworld and may move/chat/
// matchmake. coachName/coachLook are only used if a coach must be created.
//
// This mirrors cmd/loadtest's rawLogin and handlers_connection.go's
// completeLogin/enterWorld sequence, but tolerates the extra post-login
// frames a live server sends (friend/ignore lists, statistics) by draining
// up to ENTER_WORLD_INSTANCE.
func (c *Client) Login(login, password, coachName string, look CoachLook) (*Session, error) {
	// AUTHENTICATION (1025): pstring(login) + pstring(password).
	w := protocol.NewWriter(0)
	w.PutString(login)
	w.PutString(password)
	if err := c.Send(1, protocol.RecvAuthentication, w.Bytes()); err != nil {
		return nil, err
	}

	result, err := c.Expect(protocol.SendAuthenticationResult, 0)
	if err != nil {
		return nil, err
	}
	if len(result) == 0 {
		return nil, fmt.Errorf("login(%s): empty AUTHENTICATION_RESULT", login)
	}
	if code := protocol.AuthResultCode(result[0]); code != protocol.AuthOK {
		return nil, fmt.Errorf("login(%s): auth rejected, code=%d", login, code)
	}

	// QUEUE_NOTIFICATION (8192): int32 position (=-1). Must be consumed.
	if _, err := c.Expect(protocol.SendQueueNotification, 0); err != nil {
		return nil, err
	}

	sess := &Session{}

	// Next frame is either COACH_CREATION_REQUEST (no coach yet) or the
	// start of completeLogin (COACH_INFORMATION).
	f, err := c.Recv(DefaultRecvTimeout)
	if err != nil {
		return nil, fmt.Errorf("login(%s): after queue: %w", login, err)
	}
	var coachInfo []byte
	switch f.Opcode {
	case protocol.SendCoachCreationRequest:
		sess.CoachCreated = true
		if err := c.createCoach(coachName, look); err != nil {
			return nil, fmt.Errorf("login(%s): %w", login, err)
		}
		if coachInfo, err = c.Expect(protocol.SendCoachInformation, 0); err != nil {
			return nil, err
		}
	case protocol.SendCoachInformation:
		coachInfo = f.Payload
	default:
		if !isBroadcastNoise(f.Opcode) {
			return nil, fmt.Errorf("login(%s): unexpected %s after queue", login, f.Opcode.Name())
		}
		// Rare: a broadcast slipped in first; wait for coach info.
		if coachInfo, err = c.Expect(protocol.SendCoachInformation, 0); err != nil {
			return nil, err
		}
	}
	// Parse COACH_INFORMATION for the coach id and unequipped card uids
	// (see packets_coach.go buildCoachInformation for the layout).
	parseCoachInformation(coachInfo, sess)

	// Drain the remaining completeLogin fan-out (friend/ignore lists,
	// statistics) up to ENTER_WORLD_INSTANCE, which marks world entry.
	if _, err := c.DrainUntil(protocol.SendEnterWorldInstance, 32, 0); err != nil {
		return nil, err
	}
	return sess, nil
}

// parseCoachInformation extracts the coach id and unequipped card uids from
// a COACH_INFORMATION (2052) payload. Layout (packets_coach.go):
//
//	int64 coachID + pstring name + byte skin + byte hair + byte sex +
//	uint16 equippedLen + [int16 slot, int32 tmpl, int64 uid, byte flag]*
//	uint16 unequippedLen + [int32 tmpl, int64 uid, byte flag, int16 qty]*
//
// Each card record is 15 bytes in both blobs. We only need the unequipped
// uids; anything malformed just yields fewer uids (best-effort).
func parseCoachInformation(payload []byte, sess *Session) {
	r := protocol.NewReader(payload)
	sess.CoachID = r.Int64()
	_ = r.String() // name
	_ = r.Byte()   // skin
	_ = r.Byte()   // hair
	_ = r.Byte()   // sex

	// Equipped blob: skip.
	equippedLen := int(r.Uint16())
	if equippedLen > 0 {
		_ = r.Bytes(equippedLen)
	}

	// Unequipped blob: 15 bytes/card, uid at offset 4 (after int32 tmpl).
	unequippedLen := int(r.Uint16())
	if unequippedLen <= 0 || unequippedLen%15 != 0 {
		return
	}
	blob := r.Bytes(unequippedLen)
	if blob == nil {
		return
	}
	br := protocol.NewReader(blob)
	for br.Remaining() >= 15 {
		_ = br.Int32()    // template id
		uid := br.Int64() // unique id
		_ = br.Byte()     // flag
		_ = br.Int16()    // quantity
		if br.Err() != nil {
			return
		}
		sess.InventoryCardUIDs = append(sess.InventoryCardUIDs, uid)
	}
}

// CoachLook is the cosmetic appearance sent at coach creation.
type CoachLook struct {
	Skin byte
	Hair byte
	Sex  byte
}

// createCoach replies to COACH_CREATION_REQUEST with COACH_CREATION (2049):
// pstring(name) + byte skin + byte hair + byte sex.
func (c *Client) createCoach(name string, look CoachLook) error {
	w := protocol.NewWriter(0)
	w.PutString(name)
	w.PutByte(look.Skin)
	w.PutByte(look.Hair)
	w.PutByte(look.Sex)
	if err := c.Send(2, protocol.RecvCoachCreation, w.Bytes()); err != nil {
		return err
	}
	result, err := c.Expect(protocol.SendCoachCreationResult, 0)
	if err != nil {
		return err
	}
	if len(result) == 0 {
		return fmt.Errorf("empty COACH_CREATION_RESULT")
	}
	if code := protocol.CoachCreationResultCode(result[0]); code != protocol.CoachCreationOK {
		return fmt.Errorf("coach creation rejected, code=%d", code)
	}
	return nil
}

// Disconnect sends a clean DISCONNECT (1) and closes the socket. Errors are
// ignored because the caller is tearing down regardless.
func (c *Client) Disconnect() {
	_ = c.Send(1, protocol.RecvDisconnect, nil)
	// Give the frame a moment to flush before the socket closes.
	time.Sleep(5 * time.Millisecond)
	_ = c.Close()
}
