package game

import (
	"bytes"
	"os"
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// envMapsRoot finds a directory containing maps/env, preferring the committed
// data-dist so this runs in CI, and skips when neither is present.
func envMapsRoot(t *testing.T) string {
	t.Helper()
	for _, root := range []string{
		filepath.Join("..", "..", "data-dist"),
		filepath.Join("..", "..", "data"),
	} {
		if _, err := os.Stat(filepath.Join(root, "maps", "env")); err == nil {
			return root
		}
	}
	t.Skip("no maps/env data")
	return ""
}

// envKindFor maps an env element type to the server's elementKind. Kept in the
// test as an independent transcription of the client's `asi` factory table, so a
// mistake in the production mapping cannot agree with itself.
func envKindFor(envType int16) (elementKind, bool) {
	switch envType {
	case gamedata.EnvTypeCardMaster:
		return kindCardMaster, true
	case gamedata.EnvTypeMailbox:
		return kindMailbox, true
	case gamedata.EnvTypeChallenge:
		return kindChallenge, true
	case gamedata.EnvTypeZaap:
		return kindZaap, true
	case gamedata.EnvTypeBreedMaster:
		return kindBreedMaster, true
	case gamedata.EnvTypeDemonIII:
		return kindDemonIII, true
	case gamedata.EnvTypeDemonChallenge:
		return kindDemonChallenge, true
	case gamedata.EnvTypeZoneTrigger:
		return kindZoneTrigger, true
	case gamedata.EnvTypeDemonI:
		return kindDemonI, true
	case gamedata.EnvTypeGraveyard:
		return kindGraveyard, true
	case gamedata.EnvTypeDemonTotem:
		return kindDemonTotem, true
	case gamedata.EnvTypeCardUsingSwitch:
		return kindFirework, true
	case gamedata.EnvTypeTournamentTotem:
		return kindTournamentTotem, true
	case gamedata.EnvTypeFusionLab:
		return kindFusionLab, true
	case gamedata.EnvTypeNPCTalker:
		return kindNPC, true
	}
	return 0, false
}

// TestCommittedTableIsStillWhatTheDataSays checks that elements_data.go has not
// gone stale: re-derive it from the env jars and require agreement.
//
// NOTE what this does and does not prove. Since elements_data.go is GENERATED from
// these jars, this is not an independent check — it cannot catch a generator bug,
// only a committed file that was never regenerated (or was hand-edited despite the
// DO NOT EDIT banner). The independent oracle is the hand transcription kept in
// elements_golden_test.go; see TestGeneratedTableMatchesTheHandTranscription.
//
// `alt` is not compared here: it is asserted against the golden fixture for Zaaps,
// and validated against tplg by TestTplgGroundMatchesTheHandTableZaapAlts.
func TestCommittedTableIsStillWhatTheDataSays(t *testing.T) {
	root := envMapsRoot(t)

	var checked int
	for worldID, want := range worldElements {
		got, err := gamedata.LoadEnvWorld(root, worldID)
		if err != nil {
			t.Fatalf("world %d: %v", worldID, err)
		}

		// Index the decoded elements, and check the world id the payload declares
		// really is this world (a drifted parse shows up here first).
		byID := make(map[int64]gamedata.EnvElement, len(got))
		for _, e := range got {
			if e.WorldID != worldID {
				t.Errorf("world %d: element %d claims worldId %d",
					worldID, e.InstanceID, e.WorldID)
			}
			byID[e.InstanceID] = e
		}

		if len(got) != len(want) {
			t.Errorf("world %d: env has %d elements, hand table has %d",
				worldID, len(got), len(want))
		}

		for _, w := range want {
			e, ok := byID[w.instanceID]
			if !ok {
				t.Errorf("world %d: hand-table element %d (%s) is absent from the env jar",
					worldID, w.instanceID, w.kind)
				continue
			}
			checked++

			k, known := envKindFor(e.Type)
			if !known {
				t.Errorf("world %d element %d: env type %d has no kind mapping",
					worldID, e.InstanceID, e.Type)
			} else if k != w.kind {
				t.Errorf("world %d element %d: env type %d -> kind %s, hand table says %s",
					worldID, e.InstanceID, e.Type, k, w.kind)
			}
			if e.CellX != w.cellX || e.CellY != w.cellY {
				t.Errorf("world %d element %d: env cell (%d,%d), hand table (%d,%d)",
					worldID, e.InstanceID, e.CellX, e.CellY, w.cellX, w.cellY)
			}
			if !bytes.Equal(e.Payload, w.payload) {
				t.Errorf("world %d element %d: payload differs\n env  %x\n hand %x",
					worldID, e.InstanceID, e.Payload, w.payload)
			}
		}
	}
	if checked != 139 {
		t.Errorf("compared %d elements, expected 139 — the hand table changed size", checked)
	}
}

// TestEnvDescriptorsMatchTheHandTableArgs: the Card Master catalogue id and mode,
// and the Fusion altar's lab id, were transcribed by hand out of the descriptor
// string. Re-derive them from data and check they agree — this is what makes the
// descriptor parsing trustworthy enough to drive the shop.
func TestEnvDescriptorsMatchTheHandTableArgs(t *testing.T) {
	root := envMapsRoot(t)

	var cardMasters, fusionLabs int
	for worldID, want := range worldElements {
		got, err := gamedata.LoadEnvWorld(root, worldID)
		if err != nil {
			t.Fatalf("world %d: %v", worldID, err)
		}
		byID := make(map[int64]gamedata.EnvElement, len(got))
		for _, e := range got {
			byID[e.InstanceID] = e
		}

		for _, w := range want {
			e, ok := byID[w.instanceID]
			if !ok {
				continue
			}
			switch w.kind {
			case kindCardMaster:
				mode, cardList, err := gamedata.ParseCardMasterDescriptor(&e)
				if err != nil {
					t.Errorf("world %d element %d: %v", worldID, e.InstanceID, err)
					continue
				}
				if cardList != w.arg || mode != w.mode {
					t.Errorf("world %d Card Master %d: env descriptor %q -> "+
						"mode %d cardList %d, hand table mode %d arg %d",
						worldID, e.InstanceID, e.Descriptor, mode, cardList, w.mode, w.arg)
				}
				cardMasters++
			case kindFusionLab:
				lab, err := gamedata.ParseSingleIntDescriptor(&e)
				if err != nil {
					t.Errorf("world %d element %d: %v", worldID, e.InstanceID, err)
					continue
				}
				if lab != w.arg {
					t.Errorf("world %d Fusion altar %d: env descriptor %q -> lab %d, "+
						"hand table arg %d", worldID, e.InstanceID, e.Descriptor, lab, w.arg)
				}
				fusionLabs++
			}
		}
	}
	// Canary: if the counts drop to zero the loops silently stopped proving
	// anything.
	if cardMasters != 21 {
		t.Errorf("checked %d Card Masters, expected 21", cardMasters)
	}
	if fusionLabs != 6 {
		t.Errorf("checked %d Fusion altars, expected 6", fusionLabs)
	}
}

// TestTplgGroundMatchesTheHandTableZaapAlts is the correctness gate for taking the
// ARRIVAL altitude from data.
//
// alt only matters where a cell is stood on, which is Zaaps (the login spawn and
// every teleport destination). The hand table's alt for those was read out of the
// tplg topology by hand; this re-derives it and requires agreement. A wrong value
// here does not error — it silently leaves the coach unable to move, because the
// client seeds its pathfinder with cell+altitude and needs a walkable layer at
// exactly that height.
//
// Elements that are never stood on carry 0 in the hand table by convention (the
// comment on worldElement.alt says so), so they are skipped rather than compared.
func TestTplgGroundMatchesTheHandTableZaapAlts(t *testing.T) {
	root := envMapsRoot(t)

	var checked int
	for worldID, want := range goldenWorldElements {
		topo, err := gamedata.LoadWorldTopology(root, worldID)
		if err != nil {
			t.Fatalf("world %d topology: %v", worldID, err)
		}
		env, err := gamedata.LoadEnvWorld(root, worldID)
		if err != nil {
			t.Fatalf("world %d env: %v", worldID, err)
		}
		byID := make(map[int64]gamedata.EnvElement, len(env))
		for _, e := range env {
			byID[e.InstanceID] = e
		}
		if topo.Cells() == 0 {
			t.Fatalf("world %d: topology decoded no cells", worldID)
		}
		for _, w := range want {
			if w.kind != kindZaap {
				continue
			}
			lo, ok := topo.LowestWalkableAlt(w.cellX, w.cellY)
			if !ok {
				t.Errorf("world %d Zaap %d at (%d,%d): no walkable tplg layer at all",
					worldID, w.instanceID, w.cellX, w.cellY)
				continue
			}
			if lo != w.alt {
				t.Errorf("world %d Zaap %d at (%d,%d): lowest walkable tplg layer %d, "+
					"hand transcription %d", worldID, w.instanceID, w.cellX, w.cellY, lo, w.alt)
			}
			// And the value really must be a floor, which is what the client needs.
			if !topo.HasWalkableLayerAt(w.cellX, w.cellY, w.alt) {
				t.Errorf("world %d Zaap %d at (%d,%d): arrival altitude %d is not a "+
					"walkable layer — the coach would be unable to move",
					worldID, w.instanceID, w.cellX, w.cellY, w.alt)
			}
			checked++
		}
	}
	if checked != 21 {
		t.Errorf("checked %d Zaaps, expected 21", checked)
	}
}

// TestTplgUniformAndNibbleChunksAreRead guards the two chunk kinds the topology
// reader used to drop: type 0 (exactly 8 bytes, skipped by an old size guard that
// assumed it carried no per-cell data) and type 1 (unhandled). On the overworld
// they are most of the ground, so without them altitude lookups read void nearly
// everywhere — which is how a data-driven arrival altitude would silently regress.
func TestTplgUniformAndNibbleChunksAreRead(t *testing.T) {
	root := envMapsRoot(t)

	// World 79 is the strongest case: 72 of its 81 chunks are type 0.
	topo, err := gamedata.LoadWorldTopology(root, 79)
	if err != nil {
		t.Fatalf("world 79 topology: %v", err)
	}
	// 81 chunks x 324 cells = 26 244 if every kind is read; dropping type 0 would
	// leave under a fifth of that.
	if n := topo.Cells(); n < 20000 {
		t.Errorf("world 79 decoded %d cells; expected ~26k — the uniform/nibble "+
			"chunks are being skipped again", n)
	}
}
