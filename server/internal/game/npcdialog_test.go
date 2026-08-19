package game

import (
	"strconv"
	"strings"
	"testing"
)

// NPC dialog trees (ROADMAP item 27, record type 1500) are run ENTIRELY by the
// client. The server's whole obligation is to spawn the NPCTalker element; from
// there the client opens npcTalkDialog itself and walks the tree out of its own
// data. Evidence, all from the decompiled client:
//
//   - ao_2.a(fh_2,false) — registering the dialog frame OPENS "npcTalkDialog".
//     There is no request and no reply.
//   - The 17001/17002 it handles are NOT wire opcodes: they arrive as wm_0, which
//     extends sb_0 -> aed_2, whose encode() returns null, so they can never be
//     serialized. They are internal UI events (the same pattern as 22050/22051).
//   - A reply can do exactly two things, per the alj enum:
//     cEY(1,"Lancer un défi")  -> th_0 sends 26330 (challenge start), and
//     cEZ(2,"Donne un exploit") -> po_2 sends 22003 (a criterion).
//     Both are implemented and covered elsewhere.
//
// So there is no protocol to add. What CAN silently break the feature is the
// server no longer spawning the NPC, which is what this test guards.

// TestNPCTalkersAreSpawned: the NPCTalker elements must be present in their
// worlds' element lists, or their dialog trees become unreachable.
func TestNPCTalkersAreSpawned(t *testing.T) {
	found := map[int64]int16{} // instanceID -> world
	for world, elems := range worldElements {
		for _, e := range elems {
			if e.kind == kindNPC {
				found[e.instanceID] = world
			}
		}
	}
	if len(found) == 0 {
		t.Fatal("no NPCTalker elements are spawned: every NPC dialog tree in the " +
			"game is unreachable")
	}
	// The full retail placement. Pinned so a regeneration that drops one is loud.
	// Five sit on the Gostof / Baan island (85); the sixth is on 79.
	want := map[int64]int16{143: 85, 170: 85, 171: 85, 172: 85, 173: 85, 181: 79}
	for id, world := range want {
		got, ok := found[id]
		if !ok {
			t.Errorf("NPC %d is not spawned at all", id)
			continue
		}
		if got != world {
			t.Errorf("NPC %d is in world %d, want %d", id, got, world)
		}
	}
	if len(found) != len(want) {
		t.Errorf("spawned %d NPCs, want %d (%v)", len(found), len(want), found)
	}
}

// TestExactlyOneNPCHasADialogTree pins the fact that makes item 27 small: of the
// six NPCTalkers, only "bob" carries a dialog id. The other five are decorative
// ghosts whose descriptor names dialog -1, so no amount of server work would give
// them anything to say.
//
// The descriptor is ';'-separated: nameTextId;dialogId;?;?;spriteName.
func TestExactlyOneNPCHasADialogTree(t *testing.T) {
	withDialog := map[int64]int{}
	for _, elems := range worldElements {
		for _, e := range elems {
			if e.kind != kindNPC {
				continue
			}
			desc := trailingDescriptor(e.payload)
			parts := strings.Split(desc, ";")
			if len(parts) < 2 {
				t.Errorf("NPC %d: descriptor %q has no dialog field", e.instanceID, desc)
				continue
			}
			id, err := strconv.Atoi(parts[1])
			if err != nil {
				t.Errorf("NPC %d: dialog field %q is not a number (descriptor %q)",
					e.instanceID, parts[1], desc)
				continue
			}
			if id >= 0 {
				withDialog[e.instanceID] = id
			}
		}
	}
	if len(withDialog) != 1 {
		t.Fatalf("expected exactly one NPC with a dialog tree, got %v", withDialog)
	}
	if got, ok := withDialog[143]; !ok || got != 228 {
		t.Errorf("the talking NPC is %v, want {143: 228} (bob)", withDialog)
	}
}

// trailingDescriptor extracts the ';'-separated ASCII descriptor at the end of an
// element payload (NUL-terminated in the env record).
func trailingDescriptor(payload []byte) string {
	end := len(payload)
	for end > 0 && payload[end-1] == 0 {
		end--
	}
	start := end
	for start > 0 {
		c := payload[start-1]
		printable := c >= 0x20 && c < 0x7f
		if !printable {
			break
		}
		start--
	}
	return string(payload[start:end])
}
