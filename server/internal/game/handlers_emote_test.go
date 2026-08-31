package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestEmoteRelaysToNeighboursAndSelf: emotes were not implemented at all, so
// nothing happened when a player used one.
//
// The sender MUST be included. `avv_0.playEmote` only sets the actor's facing
// locally and then sends 4701; the animation is played exclusively by the 4700
// handler. Excluding the sender - the way vicinity chat correctly does - would
// leave the emoting player as the only person who sees nothing.
func TestEmoteRelaysToNeighboursAndSelf(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	self := tmSession(d, 1, "Emoter")
	near := tmSession(d, 2, "Neighbour")
	d.World.Add(&Online{Coach: self.Coach, Session: self})
	d.World.Add(&Online{Coach: near.Coach, Session: near})

	// Seed AoI both ways: the relay targets coaches whose client HAS the emoter
	// spawned, not merely coaches standing nearby.
	d.World.EnterAoI(self.Coach.ID)
	d.World.EnterAoI(near.Coach.ID)

	// Fixture check: if the neighbour does not actually have the emoter in its
	// known-set, the assertion below would pass for the wrong reason.
	if got := d.World.ViewersOf(self.Coach.ID); len(got) == 0 {
		t.Fatal("fixture: nobody has the emoter spawned, so this test could not " +
			"tell a working relay from a missing one")
	}

	if err := handleEmote(self, emoteFrame(t, "AnimEmote-Rire", 66)); err != nil {
		t.Fatalf("handleEmote: %v", err)
	}

	for _, tc := range []struct {
		name string
		sess *Session
	}{
		{"neighbour", near},
		{"sender", self},
	} {
		ops := drain(t, tc.sess)
		found := false
		for _, op := range ops {
			if op == protocol.OpEmotePlayed {
				found = true
			}
		}
		if !found {
			t.Errorf("%s received %v, want %d (4700) among them",
				tc.name, ops, protocol.OpEmotePlayed)
		}
	}
}

// TestEmoteUsesServerTableNotClientString: the client sends the animation name
// it resolved locally, but the table is hardcoded in the client enum `up_0`, so
// the server knows it too and must not relay an attacker-chosen string.
func TestEmoteUsesServerTableNotClientString(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Liar")
	d.World.Add(&Online{Coach: s.Coach, Session: s})

	if err := handleEmote(s, emoteFrame(t, "AnimEmote-EvilInjected", 66)); err != nil {
		t.Fatalf("handleEmote: %v", err)
	}
	payload := drainPayload(t, s, protocol.OpEmotePlayed)
	if payload == nil {
		t.Fatal("no 4700 emitted")
	}
	r := protocol.NewReader(payload)
	if _, err := r.I64(); err != nil {
		t.Fatalf("actor id: %v", err)
	}
	got, err := r.StringU8()
	if err != nil {
		t.Fatalf("name: %v", err)
	}
	if got != "AnimEmote-Rire" {
		t.Errorf("relayed %q, want the server's canonical name for id 66 "+
			"(%q) - the client's string must not be trusted", got, "AnimEmote-Rire")
	}
}

// TestEmoteUnknownIdDropped: ids are non-contiguous in `up_0`, and an unknown
// one must not be relayed.
func TestEmoteUnknownIdDropped(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Bogus")
	d.World.Add(&Online{Coach: s.Coach, Session: s})

	// 61 sits in a real gap between 60 (/declare) and 62 (/angry).
	if err := handleEmote(s, emoteFrame(t, "AnimEmote-Nope", 61)); err != nil {
		t.Fatalf("handleEmote: %v", err)
	}
	if p := drainPayload(t, s, protocol.OpEmotePlayed); p != nil {
		t.Error("an unknown emote id was relayed; the gaps in up_0 are real")
	}
}

func emoteFrame(t *testing.T, name string, id int32) *protocol.C2SFrame {
	t.Helper()
	return &protocol.C2SFrame{
		Opcode:  protocol.OpEmotePlay,
		Payload: protocol.NewWriter().StringU8(name).I32(id).Bytes(),
	}
}

// drainPayload returns the payload of the first frame with the given opcode, or
// nil. Frame layout matches drain(): opcode at bytes 2..3, payload from 4.
func drainPayload(t *testing.T, s *Session, opcode uint16) []byte {
	t.Helper()
	for {
		select {
		case frame := <-s.out:
			if len(frame) >= 4 && uint16(frame[2])<<8|uint16(frame[3]) == opcode {
				return frame[4:]
			}
		default:
			return nil
		}
	}
}

// TestEmoteNotSentToCoachesWhoCannotSeeIt is a live-client regression.
//
// Injecting 4700 with an actor the client has not spawned throws
// NullPointerException inside `no_2` - the retail handler dereferences
// `bd_1.Is().bb(id)` with no null check - and the client drops the message.
// Broadcasting emotes by raw proximity (SessionsNear) could therefore crash a
// neighbour's message handler, because AoI membership is seeded in EnterAoI and
// is NOT maintained as coaches move.
func TestEmoteNotSentToCoachesWhoCannotSeeIt(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	self := tmSession(d, 1, "Emoter")
	stranger := tmSession(d, 2, "Stranger")
	d.World.Add(&Online{Coach: self.Coach, Session: self})
	d.World.Add(&Online{Coach: stranger.Coach, Session: stranger})

	// The stranger is standing right next to the emoter but has never had it
	// spawned - exactly the divergence that raw proximity cannot see.
	if len(d.World.SessionsNear(self.Coach.PosX, self.Coach.PosY, self.Coach.ID)) == 0 {
		t.Fatal("fixture: the stranger is not within proximity, so this test would " +
			"pass even with the buggy SessionsNear broadcast")
	}
	if len(d.World.ViewersOf(self.Coach.ID)) != 0 {
		t.Fatal("fixture: the stranger already has the emoter spawned; AoI was " +
			"seeded when it should not have been")
	}

	if err := handleEmote(self, emoteFrame(t, "AnimEmote-Rire", 66)); err != nil {
		t.Fatalf("handleEmote: %v", err)
	}
	if p := drainPayload(t, stranger, protocol.OpEmotePlayed); p != nil {
		t.Error("4700 was sent to a coach that has not spawned the emoter; the " +
			"retail client NPEs on an unknown actor id")
	}
}
