package game

import (
	"encoding/binary"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The card-exchange block was implemented against the 2006 opcode layout, where
// 5105..5112 run in order. 2.70 renumbered it, and nothing caught that: the
// audit recorded the block as correct, and the end-to-end tests passed because
// the test client had the same wrong numbers hard-coded, so the server was only
// ever checked against itself.
//
// These tests pin the numbering and the payload sizes to the retail client's own
// decoders, which is the only authority that matters.

// clientExchangeOpcodes is what the 2.70 client actually implements. Direction
// is taken from the base class each message extends: so_0 messages have
// encode() and are sent BY the client; ael_2 messages have a(byte[]) and are
// decoded by it. gz_1, the client's decode factory, contains cases for exactly
// the S2C entries below — a server sending any of the C2S ones is sending
// something the client cannot even instantiate.
var clientExchangeOpcodes = []struct {
	opcode uint16
	class  string
	s2c    bool
	what   string
}{
	{5101, "fw_1", false, "invite"},
	{5102, "uo_1", true, "invitation"},
	{5103, "tw_0", false, "answer"},
	{5104, "Ul", true, "confirmation"},
	{5105, "ua_2", false, "add card"},
	{5107, "wd_0", false, "remove card"},
	{5109, "ahJ", false, "set ready"},
	{5110, "asH", true, "card added"},
	{5111, "any", false, "cancel"},
	{5112, "aaz_1", true, "card removed"},
	{5113, "Or", true, "error"},
	{5114, "aqX", true, "end"},
	{5116, "dl_0", true, "user ready"},
}

func TestExchangeOpcodesMatchTheClient(t *testing.T) {
	ours := map[string]uint16{
		"invite":       protocol.OpExchangeInvite,
		"invitation":   protocol.OpExchangeInvitationRequest,
		"answer":       protocol.OpExchangeAnswer,
		"confirmation": protocol.OpExchangeConfirmation,
		"add card":     protocol.OpExchangeAddCard,
		"remove card":  protocol.OpExchangeRemoveCard,
		"set ready":    protocol.OpExchangeSetReady,
		"card added":   protocol.OpExchangeCardAdded,
		"cancel":       protocol.OpExchangeCancel,
		"card removed": protocol.OpExchangeCardRemoved,
		"error":        protocol.OpExchangeError,
		"end":          protocol.OpExchangeEnd,
		"user ready":   protocol.OpExchangeUserReady,
	}

	for _, want := range clientExchangeOpcodes {
		got, ok := ours[want.what]
		if !ok {
			t.Errorf("no server opcode for %q (client %s = %d)", want.what, want.class, want.opcode)
			continue
		}
		if got != want.opcode {
			t.Errorf("%s: server uses %d, client class %s answers to %d",
				want.what, got, want.class, want.opcode)
		}
	}

	// The reverse direction: nothing we send may land on an opcode the client
	// only ever sends. That was the original fault — 5109 and 5111 are C2S in
	// 2.70, so the old server was broadcasting into messages the client's decode
	// factory has no case for.
	c2s := map[uint16]string{}
	for _, e := range clientExchangeOpcodes {
		if !e.s2c {
			c2s[e.opcode] = e.class
		}
	}
	for _, sent := range []struct {
		op   uint16
		name string
	}{
		{protocol.OpExchangeInvitationRequest, "OpExchangeInvitationRequest"},
		{protocol.OpExchangeConfirmation, "OpExchangeConfirmation"},
		{protocol.OpExchangeCardAdded, "OpExchangeCardAdded"},
		{protocol.OpExchangeCardRemoved, "OpExchangeCardRemoved"},
		{protocol.OpExchangeError, "OpExchangeError"},
		{protocol.OpExchangeEnd, "OpExchangeEnd"},
		{protocol.OpExchangeUserReady, "OpExchangeUserReady"},
	} {
		if class, bad := c2s[sent.op]; bad {
			t.Errorf("%s is %d, which the client implements as the CLIENT-SENT message %s — "+
				"it has no decoder for it", sent.name, sent.op, class)
		}
	}
}

// exchangePayload strips the 4-byte S2C header (length + opcode) added by
// EncodeS2C and checks the opcode is the expected one.
func exchangePayload(t *testing.T, frame []byte, wantOpcode uint16) []byte {
	t.Helper()
	if len(frame) < 4 {
		t.Fatalf("frame too short: %d bytes", len(frame))
	}
	if got := binary.BigEndian.Uint16(frame[2:4]); got != wantOpcode {
		t.Fatalf("opcode = %d, want %d", got, wantOpcode)
	}
	return frame[4:]
}

// TestExchangeCardMoveWireShape pins 5110/5112 to what asH and aaz_1 read:
//
//	getLong() ; get() ; wy_2.b(bb) -> getInt() ; getShort()
//
// = 15 bytes. The old builder wrote 24 (it added an i64 uid and an i8 flags
// byte from the 2006 layout), so the client would have read the uid's high half
// as its card id and then run off the end.
func TestExchangeCardMoveWireShape(t *testing.T) {
	for _, tc := range []struct {
		name   string
		opcode uint16
	}{
		{"added", protocol.OpExchangeCardAdded},
		{"removed", protocol.OpExchangeCardRemoved},
	} {
		t.Run(tc.name, func(t *testing.T) {
			frame, err := buildExchangeCardMove(tc.opcode, 77, 1, 4242, 3)
			if err != nil {
				t.Fatalf("build: %v", err)
			}
			body := exchangePayload(t, frame, tc.opcode)
			if len(body) != 15 {
				t.Fatalf("payload is %d bytes, want 15 "+
					"(i64 exId + i8 userIdx + i32 refCardId + i16 qty)", len(body))
			}

			r := protocol.NewReader(body)
			exID, _ := r.I64()
			userIdx, _ := r.U8()
			refCard, _ := r.I32()
			qty, _ := r.U16()

			if exID != 77 {
				t.Errorf("exchangeId = %d, want 77", exID)
			}
			if userIdx != 1 {
				t.Errorf("userIndex = %d, want 1", userIdx)
			}
			if refCard != 4242 {
				t.Errorf("referenceCardId = %d, want 4242 — the client looks the "+
					"card up by this id, so a row id here resolves to nothing", refCard)
			}
			if qty != 3 {
				t.Errorf("quantity = %d, want 3", qty)
			}
			if r.Remaining() != 0 {
				t.Errorf("%d trailing bytes; the client stops reading after the quantity",
					r.Remaining())
			}
		})
	}
}

// The three short S2C messages: Or, aqX and dl_0 each read exactly 9 bytes, and
// two of them put the byte FIRST.
func TestExchangeShortMessagesWireShape(t *testing.T) {
	t.Run("error", func(t *testing.T) {
		frame, err := buildExchangeError(9, exchangeErrUniqueExists)
		if err != nil {
			t.Fatalf("build: %v", err)
		}
		body := exchangePayload(t, frame, protocol.OpExchangeError)
		r := protocol.NewReader(body)
		code, _ := r.U8() // Or reads the byte BEFORE the id
		exID, _ := r.I64()
		if code != exchangeErrUniqueExists || exID != 9 {
			t.Errorf("got code=%d exId=%d, want 1 and 9", code, exID)
		}
		if r.Remaining() != 0 {
			t.Errorf("%d trailing bytes, want 0", r.Remaining())
		}
	})

	t.Run("end", func(t *testing.T) {
		frame, err := buildExchangeEnd(9, protocol.ExchangeEndCancel)
		if err != nil {
			t.Fatalf("build: %v", err)
		}
		body := exchangePayload(t, frame, protocol.OpExchangeEnd)
		r := protocol.NewReader(body)
		reason, _ := r.U8() // aqX reads the byte BEFORE the id
		exID, _ := r.I64()
		if reason != protocol.ExchangeEndCancel || exID != 9 {
			t.Errorf("got reason=%d exId=%d", reason, exID)
		}
		if r.Remaining() != 0 {
			t.Errorf("%d trailing bytes, want 0", r.Remaining())
		}
	})

	t.Run("user ready", func(t *testing.T) {
		frame, err := buildExchangeUserReady(9, 1)
		if err != nil {
			t.Fatalf("build: %v", err)
		}
		body := exchangePayload(t, frame, protocol.OpExchangeUserReady)
		r := protocol.NewReader(body)
		exID, _ := r.I64() // dl_0 reads the id FIRST
		idx, _ := r.U8()
		if exID != 9 || idx != 1 {
			t.Errorf("got exId=%d userIdx=%d", exID, idx)
		}
		if r.Remaining() != 0 {
			t.Errorf("%d trailing bytes, want 0", r.Remaining())
		}
	})
}
