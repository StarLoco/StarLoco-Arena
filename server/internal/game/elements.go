package game

import (
	"encoding/hex"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Overworld interactive elements.
//
// Every island's Zaaps, Card Masters, Fusion altars, mailboxes and graveyards are
// baked into its env layer (contents/maps/env/<world>.jar) as "interactive
// elements", each with a unique env instanceId. The client CLEARS its live element
// manager on every ENTER_INSTANCE (4600), so the server must (re)push the world's
// elements with INTERACTIVE_ELEMENT_SPAWN (opcode 200) on each entry — see
// Session.sendEnterOverworld. Clicking one sends INTERACTIVE_ELEMENT_ACTION
// (opcode 201) carrying the instanceId; what happens next depends on the kind
// (see handleInteractiveElementAction).
//
// The payloads below are the exact part-table blobs extracted from the env jars
// (the outer env framing is little-endian, but the blob itself is opaque and is
// copied verbatim onto the wire). Cells and ground altitudes come from the
// matching tplg (topology) data. See docs/OVERWORLD-MAP.md.

// elementKind is what an interactive element does when clicked.
type elementKind uint8

const (
	// kindZaap (env type 4) opens the grimoire's Zaap-card page locally; picking a
	// destination card sends ZAAP_USE (4512). See zaap.go.
	kindZaap elementKind = iota
	// kindCardMaster (env type 1) opens NO UI by itself: it arms its handler and
	// waits for the server to push the catalogue (5401), which is what opens and
	// populates the shop.
	kindCardMaster
	// kindFusionLab (env type 14) opens the fusion altar entirely from client-local
	// lab definitions; only the fuse itself is networked (5490 -> 5491).
	kindFusionLab
	// kindMailbox (env type 2) opens the mailbox dialog locally. Its contents are
	// server-driven and NOT implemented yet, so it opens empty.
	kindMailbox
	// kindGraveyard (env type 10) opens the graveyard dialog locally; its roster is
	// server-driven (see handlers_evolution.go / the 6031 -> 6006+6030 reply).
	kindGraveyard
	// kindChallenge (env type 3) opens an accept/refuse bubble locally. Accepting
	// sends 26330 [i32 challengeId][i16 99] (see challengeAcceptBreed), which
	// starts a real PvE fight — see challenge_fights.go.
	kindChallenge
	// kindDemonChallenge (env type 7) behaves like kindChallenge, or runs a local
	// Lua scenario when its descriptor names one. Its descriptor is a ';'-separated
	// list `nameId;scenarioId;challengeId;promptTextId[;refusalTextId]`; field 2 is
	// the challenge id sent in 26330.
	kindDemonChallenge
	// kindFirework (env type 12, "cardUsingSwitch") opens the firework dialog
	// locally, filled from the coach's own card inventory. Launching one sends
	// 22095, which we echo back as 22094 so the effect is visible.
	kindFirework
	// kindNPC (env type 15, NPCTalker) opens a dialog tree entirely from client
	// data — the server owes nothing at all beyond spawning it.
	kindNPC
	// kindBreedMaster (env type 5) offers recruit / test-fight / cancel. Recruit is
	// local and already works through our fighter creation; the test fight is the
	// shared 26330 challenge path (refused politely).
	kindBreedMaster
	// kindDemonI / kindDemonIII (env types 9 and 6) are the named demon NPCs.
	kindDemonI
	kindDemonIII
	// kindDemonTotem (env type 11) sends 27510 asking for a demon's ladder page;
	// its dialog opens ONLY on our 27511 reply.
	kindDemonTotem
	// kindTournamentTotem (env type 13) sends 17002 (+28601); its dialog opens ONLY
	// on our 17003 reply.
	kindTournamentTotem
	// kindZoneTrigger (env type 8) fires on WALK-ON and runs a local Lua scenario.
	// It never even sends 201 — purely client-side.
	kindZoneTrigger
)

func (k elementKind) String() string {
	switch k {
	case kindZaap:
		return "zaap"
	case kindCardMaster:
		return "cardmaster"
	case kindFusionLab:
		return "fusionlab"
	case kindMailbox:
		return "mailbox"
	case kindGraveyard:
		return "graveyard"
	case kindChallenge:
		return "challenge"
	case kindDemonChallenge:
		return "demonchallenge"
	case kindFirework:
		return "firework"
	case kindNPC:
		return "npc"
	case kindBreedMaster:
		return "breedmaster"
	case kindDemonI:
		return "demon1"
	case kindDemonIII:
		return "demon3"
	case kindDemonTotem:
		return "demontotem"
	case kindTournamentTotem:
		return "tournamenttotem"
	case kindZoneTrigger:
		return "zonetrigger"
	}
	return "unknown"
}

// worldElement is one interactive element of a world.
type worldElement struct {
	kind       elementKind
	instanceID int64
	payload    []byte
	cellX      int32
	cellY      int32
	// alt is the element cell's walkable GROUND altitude (the tplg layer "wp").
	// It matters only where the cell is used as an ARRIVAL cell (Zaaps): the
	// ENTER_INSTANCE (4600) `alt` must equal it, because the client seeds its
	// overworld pathfinder with the coach's cell + altitude and requires a walkable
	// layer at EXACTLY that altitude — a mismatch leaves the coach unable to move.
	// NOTE the env sprite's RU z is decoration height, NOT the ground wp. Elements
	// that are never stood on (mailboxes, graveyards — often on non-walkable cells)
	// carry 0 here; it is unused for them.
	alt int16
	// arg is the element's descriptor argument: for a Card Master its catalogue id
	// ("cardListId", descriptor "flag;cardListId[;nameContentId]"), for a Fusion
	// altar its lab-definition id, for a Demon Totem its demon id. 0 when the kind
	// has no argument.
	arg int32
	// mode is a Card Master's descriptor flag (field 0): 0 = the ordinary
	// "kardmaster" shop, 1 = the "démone II" exchanger variant. It selects which
	// UI the client opens when our catalogue (5401) arrives.
	mode uint8
}

func mustElement(kind elementKind, instanceID int64, x, y int32, alt int16, arg int32, payloadHex string) worldElement {
	b, err := hex.DecodeString(payloadHex)
	if err != nil {
		panic("game: bad element payload hex: " + err.Error())
	}
	return worldElement{
		kind: kind, instanceID: instanceID, payload: b,
		cellX: x, cellY: y, alt: alt, arg: arg,
	}
}

// mustCardMaster builds a Card Master element, which additionally carries the
// descriptor's mode flag (0 = kardmaster shop, 1 = "démone II" exchanger).
func mustCardMaster(instanceID int64, x, y int32, alt int16, cardListID int32, mode uint8, payloadHex string) worldElement {
	e := mustElement(kindCardMaster, instanceID, x, y, alt, cardListID, payloadHex)
	e.mode = mode
	return e
}

// worldElements maps a worldId to every interactive element the server spawns
// there. The first Zaap listed for a world is its "primary" one (see primaryZaap):
// it is the default arrival for /WORLD and the login spawn.
//
// STALE UNTIL NOW: this list used to name Challenge (3), DemonChallenge (7),
// ZoneTrigger (8), BreedMaster (5), DemonTotem (11) and TournamentTotem (13) as
// "deliberately NOT spawned yet". All six ARE spawned — the table below places
// 2 Challenges, 11 DemonChallenges, 9 ZoneTriggers, 12 BreedMasters, 24
// DemonTotems and 2 TournamentTotems — and their flows exist (PvE challenges,
// breed-master test fights, the tournament calendar).
//
// CORRECTION: that "genuinely not spawned" list was itself stale. It named env
// type 12 (cardUsingSwitch → kindFirework) and type 15 (NPCTalker → kindNPC),
// but the table below places 5 Fireworks and 6 NPCs. **All 15 env types are
// spawned.** Both are safe to spawn because both are answered entirely
// client-side: the firework dialog is filled from the coach's own inventory (we
// only echo 22095 back as 22094), and NPCTalker runs a dialog tree from client
// data, so an undecoded record type 1500 costs the player nothing here.
// worldElements now lives in elements_data.go, GENERATED from the client's own
// maps/env layers by cmd/genelements. It used to be transcribed by hand here;
// see that command's doc comment for the two errors that cost us.

// elementAt returns a world's element by env instanceId.
func elementAt(world int16, instanceID int64) (worldElement, bool) {
	for _, e := range worldElements[world] {
		if e.instanceID == instanceID {
			return e, true
		}
	}
	return worldElement{}, false
}

// zaapAt returns a specific Zaap on a world by env instanceId.
func zaapAt(world int16, instanceID int64) (worldElement, bool) {
	e, ok := elementAt(world, instanceID)
	if !ok || e.kind != kindZaap {
		return worldElement{}, false
	}
	return e, true
}

// primaryZaap returns a world's main (first-listed) Zaap.
func primaryZaap(world int16) (worldElement, bool) {
	for _, e := range worldElements[world] {
		if e.kind == kindZaap {
			return e, true
		}
	}
	return worldElement{}, false
}

// buildInteractiveElementSpawn builds INTERACTIVE_ELEMENT_SPAWN (opcode 200,
// client rz_2): [i16 count]{[i64 instanceId][i16 payloadLen][payload]}. Outer
// framing is big-endian (network order); each element payload is appended verbatim
// (it is an opaque part-table blob).
func buildInteractiveElementSpawn(elems ...worldElement) ([]byte, error) {
	w := protocol.NewWriter()
	w.U16(uint16(len(elems)))
	for _, e := range elems {
		w.I64(e.instanceID)
		w.U16(uint16(len(e.payload)))
		w.Raw(e.payload)
	}
	return protocol.EncodeS2C(protocol.OpInteractiveElementSpawn, w.Bytes())
}

// sendWorldElements pushes the INTERACTIVE_ELEMENT_SPAWN for every element of
// worldID. Call it right after the ENTER_INSTANCE (4600) for that world — the
// client clears its element manager on each 4600, so they must be re-sent per
// entry (see sendEnterOverworld).
func (s *Session) sendWorldElements(worldID int16) {
	elems := worldElements[worldID]
	if len(elems) == 0 {
		return
	}
	if frame, err := buildInteractiveElementSpawn(elems...); err == nil {
		_ = s.Send(frame)
	}
}
