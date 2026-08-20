// Command genelements regenerates internal/game/elements_data.go — the overworld
// interactive-element table — from the client's own data.
//
// The table used to be transcribed by hand out of maps/env/*.jar. That worked, but
// it was 139 entries of hex typed by a human, and one of them was wrong: a
// direction byte written 03 where the jar says 01, which went onto the wire on
// every spawn of that Card Master. Generation removes the whole class of mistake.
//
// (A second suspected error turned out to be the generator's fault, not the
// transcription's — see the arrival-altitude comment below. The hand value was
// right and the live client proved it.)
//
// It stays GENERATED rather than read at runtime on purpose: the server then has no
// startup dependency on map data for something as basic as "what can the player
// click", the table is reviewable in git, and every consumer keeps taking stable
// pointers into it. internal/game's elements_fromdata_test.go is the CI gate that
// the committed table still matches the data.
//
// Usage (from server/):
//
//	go run ./cmd/genelements -data data-dist -out internal/game/elements_data.go
package main

import (
	"bytes"
	"flag"
	"fmt"
	"go/format"
	"os"
	"sort"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// policyWorlds is the authored half of this table, and the reason the generator
// takes a list rather than globbing the jars: the client ships 114 env layers and
// the server deliberately serves 11. The others are either empty (110/113 have an
// env layer with no elements — a player sent there would be stranded), staff-only
// (111/112 are the moderator and prison worlds), or unreachable content.
//
// Order is the order players meet them, and it is load-bearing in one place: the
// FIRST Zaap of a world is its primary one (primaryZaap), which is where a coach
// lands on login and on /WORLD.
var policyWorlds = []struct {
	id      int16
	comment string
}{
	{23, "Maknala"},
	{24, "Sturbia"},
	{25, "Venivici (the login spawn island)"},
	{26, "Île du Passage"},
	{27, "Île du Quadraimant (Fourmagnet)"},
	{28, "Magmara"},
	{37, "Totem Arena"},
	{35, "Recruitment island"},
	{79, "Demon I's den"},
	{80, "Card Master hub"},
	{85, "Gostof / Baan island"},

	// The 24 clan islands, one per Demon des Heures, held by that demon's leading
	// active clan and reached with Zaap card 859 (see zaap.go, guild_repo.go).
	//
	// These were left out as "unreachable content" while there was no clan system,
	// which was correct at the time and stopped being correct when clans landed: a
	// live teleport to world 88 was refused with "destination zaap missing" because
	// nothing here registered the island's Zaap - the one element that is both the
	// arrival point and the only way back off.
	{86, "clan island (demon 1)"},
	{87, "clan island (demon 2)"},
	{88, "clan island (demon 3)"},
	{89, "clan island (demon 4)"},
	{90, "clan island (demon 5)"},
	{91, "clan island (demon 6)"},
	{92, "clan island (demon 7)"},
	{93, "clan island (demon 8)"},
	{94, "clan island (demon 9)"},
	{95, "clan island (demon 10)"},
	{96, "clan island (demon 11)"},
	{97, "clan island (demon 12)"},
	{98, "clan island (demon 13)"},
	{99, "clan island (demon 14)"},
	{100, "clan island (demon 15)"},
	{101, "clan island (demon 16)"},
	{102, "clan island (demon 17)"},
	{103, "clan island (demon 18)"},
	{104, "clan island (demon 19)"},
	{105, "clan island (demon 20)"},
	{106, "clan island (demon 21)"},
	{107, "clan island (demon 22)"},
	{108, "clan island (demon 23)"},
	{109, "clan island (demon 24)"},
}

// kindFor maps an env element type to the game package's elementKind constant NAME.
// Transcribed from the client's `asi` factory table.
var kindFor = map[int16]string{
	gamedata.EnvTypeCardMaster:      "kindCardMaster",
	gamedata.EnvTypeMailbox:         "kindMailbox",
	gamedata.EnvTypeChallenge:       "kindChallenge",
	gamedata.EnvTypeZaap:            "kindZaap",
	gamedata.EnvTypeBreedMaster:     "kindBreedMaster",
	gamedata.EnvTypeDemonIII:        "kindDemonIII",
	gamedata.EnvTypeDemonChallenge:  "kindDemonChallenge",
	gamedata.EnvTypeZoneTrigger:     "kindZoneTrigger",
	gamedata.EnvTypeDemonI:          "kindDemonI",
	gamedata.EnvTypeGraveyard:       "kindGraveyard",
	gamedata.EnvTypeDemonTotem:      "kindDemonTotem",
	gamedata.EnvTypeCardUsingSwitch: "kindFirework",
	gamedata.EnvTypeTournamentTotem: "kindTournamentTotem",
	gamedata.EnvTypeFusionLab:       "kindFusionLab",
	gamedata.EnvTypeNPCTalker:       "kindNPC",
}

func main() {
	dataDir := flag.String("data", "data-dist", "directory containing maps/")
	outPath := flag.String("out", "internal/game/elements_data.go", "file to write")
	flag.Parse()

	var buf bytes.Buffer
	writeHeader(&buf)

	total := 0
	for _, w := range policyWorlds {
		elems, err := gamedata.LoadEnvWorld(*dataDir, w.id)
		if err != nil {
			fatal("world %d: %v", w.id, err)
		}
		topo, err := gamedata.LoadWorldTopology(*dataDir, w.id)
		if err != nil {
			fatal("world %d topology: %v", w.id, err)
		}
		// Deterministic output: instance-id order. primaryZaap() scans for the first
		// ZAAP rather than the first element, so only the relative order of the Zaaps
		// matters, and instance-id order happens to reproduce the hand transcription's
		// choice in all 11 worlds - which TestGeneratedTablePreservesThePrimaryZaap
		// asserts, because that is where the coach lands on login and on /WORLD.
		sort.SliceStable(elems, func(i, j int) bool {
			return elems[i].InstanceID < elems[j].InstanceID
		})

		fmt.Fprintf(&buf, "\t// --- %s %s\n", w.comment,
			dashes(len("\t// --- "+w.comment)))
		fmt.Fprintf(&buf, "\t%d: {\n", w.id)
		for i := range elems {
			e := &elems[i]
			kind, ok := kindFor[e.Type]
			if !ok {
				fatal("world %d element %d: env type %d has no kind mapping",
					w.id, e.InstanceID, e.Type)
			}
			// ARRIVAL ALTITUDE. A Zaap is stood on, and the 4600 alt must be a
			// walkable layer or the client's pathfinder seeds on nothing and the
			// coach cannot move at all.
			//
			// It is the LOWEST walkable layer of the cell, not the element's own
			// authored z and not the highest layer. Only one Zaap in retail data
			// distinguishes them - world 25's, whose cell has floors at 8 and 30 while
			// the Zaap platform itself sits at 30 - and it settles it: spawning the
			// coach at 30 renders no coach at all, while 8 is where it has always
			// correctly appeared. Physically it reads as arriving on the ground rather
			// than on the platform above it.
			alt := e.DecorAlt
			if e.Type == gamedata.EnvTypeZaap {
				lo, ok := topo.LowestWalkableAlt(e.CellX, e.CellY)
				if !ok {
					fatal("world %d Zaap %d at (%d,%d): no walkable tplg layer at all",
						w.id, e.InstanceID, e.CellX, e.CellY)
				}
				alt = lo
			}
			arg, mode := argFor(e)
			if e.Type == gamedata.EnvTypeCardMaster {
				fmt.Fprintf(&buf, "\t\tmustCardMaster(%d, %d, %d, %d, %d, %d, %q),\n",
					e.InstanceID, e.CellX, e.CellY, alt, arg, mode, hexUpper(e.Payload))
			} else {
				fmt.Fprintf(&buf, "\t\tmustElement(%s, %d, %d, %d, %d, %d, %q),\n",
					kind, e.InstanceID, e.CellX, e.CellY, alt, arg, hexUpper(e.Payload))
			}
			total++
		}
		fmt.Fprintf(&buf, "\t},\n")
	}
	fmt.Fprintf(&buf, "}\n")

	src, err := format.Source(buf.Bytes())
	if err != nil {
		fatal("gofmt: %v", err)
	}
	if err := os.WriteFile(*outPath, src, 0o644); err != nil {
		fatal("write %s: %v", *outPath, err)
	}
	fmt.Printf("wrote %s: %d elements across %d worlds\n", *outPath, total, len(policyWorlds))
}

// argFor derives the descriptor argument the server acts on. Only three kinds have
// one; everything else takes 0, which matches what the hand table carried.
func argFor(e *gamedata.EnvElement) (arg int32, mode uint8) {
	switch e.Type {
	case gamedata.EnvTypeCardMaster:
		m, cardList, err := gamedata.ParseCardMasterDescriptor(e)
		if err != nil {
			fatal("%v", err)
		}
		return cardList, m
	case gamedata.EnvTypeFusionLab, gamedata.EnvTypeDemonTotem:
		v, err := gamedata.ParseSingleIntDescriptor(e)
		if err != nil {
			fatal("%v", err)
		}
		return v, 0
	}
	return 0, 0
}

func writeHeader(buf *bytes.Buffer) {
	buf.WriteString(`// Code generated by cmd/genelements. DO NOT EDIT.
//
// Regenerate with, from server/:
//
//	go run ./cmd/genelements -data data-dist -out internal/game/elements_data.go
//
// Source: the client's own overworld layers, maps/env/<world>.jar (element ids,
// types, positions, descriptors and the opcode-200 payload verbatim) plus
// maps/tplg/<world>.jar, which is used to VERIFY that every Zaap's authored
// arrival altitude is a genuinely walkable layer.
//
// Which worlds appear here is a policy decision, not data — see cmd/genelements.
// elements_fromdata_test.go re-derives this from the same data and fails if the
// two ever diverge.

package game

// worldElements maps a worldId to every interactive element the server spawns
// there. The first Zaap listed for a world is its "primary" one (see primaryZaap).
var worldElements = map[int16][]worldElement{
`)
}

func dashes(n int) string {
	const width = 80
	if n >= width {
		return ""
	}
	b := make([]byte, width-n)
	for i := range b {
		b[i] = '-'
	}
	return string(b)
}

func hexUpper(b []byte) string {
	const digits = "0123456789ABCDEF"
	out := make([]byte, 0, len(b)*2)
	for _, c := range b {
		out = append(out, digits[c>>4], digits[c&0x0F])
	}
	return string(out)
}

func fatal(format string, a ...any) {
	fmt.Fprintf(os.Stderr, "genelements: "+format+"\n", a...)
	os.Exit(1)
}
