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
// Genuinely NOT spawned, because clicking them would start a flow that cannot
// complete: env type 12 cardUsingSwitch (the use-card-on-element flow) and type
// 15 NPCTalker (dialog trees, record type 1500 undecoded).
var worldElements = map[int16][]worldElement{
	// --- Maknala ---------------------------------------------------------------
	23: {
		mustElement(kindZaap, 35, -57, 0, 2, 0, "02000000000B010000000D0000010017FFFFFFC70000000000020001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 111, -2, -164, 3, 0, "02000000000B010000000D0000010017FFFFFFFEFFFFFF5C00030001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 112, -68, 80, 0, 0, "02000000000B010000000D0000010017FFFFFFBC0000005000000001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 120, 57, -42, 2, 0, "02000000000B010000000D000001001700000039FFFFFFD600020001010101FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 5, -7, -50, -11, 5, "02000000000B01000000130001000000000000010017FFFFFFF9FFFFFFCEFFF50001010103000000000003303B3500"),
		mustElement(kindCardMaster, 6, 23, 31, -15, 6, "02000000000B010000000D0000010017000000170000001FFFF10001010101000000000003303B3600"),
		mustElement(kindFusionLab, 174, 24, 20, -11, 2, "02000000000B010000000D00000100170000001800000014FFF50001010103FFFF000000013200"),
		mustElement(kindMailbox, 17, -40, -5, 0, 0, "02000000000B010000000D0000010017FFFFFFD8FFFFFFFB00000001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 121, 87, -58, 1, 0, "02000000000B010000000D000001001700000057FFFFFFC600010001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 75, 29, -135, 0, 0, "02000000000B010000000D00000100170000001DFFFFFF79FFF50001010103FFFF000000046E756C6C00"),
		// Challenges (descriptors "144;117;13;16;14" and "145;146;3").
		mustElement(kindChallenge, 29, -45, -80, 7, 0, "02000000000B010000000D0000010017FFFFFFD3FFFFFFB000070001010101FFFF000000103134343B3131373B31333B31363B313400"),
		mustElement(kindChallenge, 30, 59, -136, 0, 0, "02000000000B010000000D00000100170000003BFFFFFF78000000010101010000000000093134353B3134363B3300"),
		// Demon challenge (descriptor "281;117;45;280").
		mustElement(kindDemonChallenge, 122, -70, -58, -4, 0, "02000000000B010000000D0000010017FFFFFFBAFFFFFFC6FFFC0001010103FFFF0000000E3238313B3131373B34353B32383000"),
		// Firework launchers (descriptor "272").
		mustElement(kindFirework, 115, 22, -27, -11, 0, "02000000000B010000000D000001001700000016FFFFFFE5FFF50001010103FFFF0000000332373200"),
		mustElement(kindFirework, 116, 22, -24, -11, 0, "02000000000B010000000D000001001700000016FFFFFFE8FFF50001010103FFFF0000000332373200"),
		mustElement(kindFirework, 117, 26, -94, -11, 0, "02000000000B010000000D00000100170000001AFFFFFFA2FFF50001010103FFFF0000000332373200"),
		mustElement(kindFirework, 118, 26, -96, -11, 0, "02000000000B010000000D00000100170000001AFFFFFFA0FFF50001010103FFFF0000000332373200"),
	},
	// --- Sturbia ---------------------------------------------------------------
	24: {
		mustElement(kindZaap, 36, 57, -48, -11, 0, "02000000000B010000000D000001001800000039FFFFFFD0FFF50001010103FFFF000000046E756C6C00"),
		mustElement(kindZaap, 113, 36, 80, 1, 0, "02000000000B010000000D0000010018000000240000005000010001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 114, 99, 78, 7, 0, "02000000000B010000000D0000010018000000630000004E00070001010101FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 7, -62, 29, 0, 7, "02000000000B010000000D0000010018FFFFFFC20000001D00000001010101000000000003303B3700"),
		mustElement(kindCardMaster, 8, -30, -82, 0, 8, "02000000000B010000000D0000010018FFFFFFE2FFFFFFAE00000001010103000000000003303B3800"),
		mustElement(kindFusionLab, 175, 28, 19, 0, 6, "02000000000B010000000D00000100180000001C0000001300000001010103FFFF000000013600"),
		mustElement(kindMailbox, 18, 0, -18, 0, 0, "02000000000B010000000D000001001800000000FFFFFFEE00000001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 19, 66, 20, 0, 0, "02000000000B010000000D0000010018000000420000001400000001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 20, 58, -83, 0, 0, "02000000000B010000000D00000100180000003AFFFFFFAD00000001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 102, -58, -83, 0, 0, "02000000000B010000000D0000010018FFFFFFC6FFFFFFAD00000001010101FFFF000000046E756C6C00"),
	},
	// --- Venivici (the login spawn island) -------------------------------------
	25: {
		mustElement(kindZaap, 37, 40, -20, 8, 0, "02000000000B010000000D000001001900000028FFFFFFEC001E0001010103FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 9, 94, 6, 2, 13, "02000000000B010000000D00000100190000005E0000000600020001010101000000000004303B313300"),
		mustElement(kindCardMaster, 10, -58, 33, 2, 14, "02000000000B010000000D0000010019FFFFFFC60000002100020001010101000000000004303B313400"),
		mustElement(kindFusionLab, 176, -45, -25, 2, 4, "02000000000B010000000D0000010019FFFFFFD3FFFFFFE700020001010103FFFF000000013400"),
		mustElement(kindMailbox, 21, 33, 36, 10, 0, "02000000000B010000000D0000010019000000210000002400060001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 103, 4, 45, 0, 0, "02000000000B010000000D0000010019000000040000002D00060001010101FFFF000000046E756C6C00"),
	},
	// --- Île du Passage --------------------------------------------------------
	26: {
		mustElement(kindZaap, 38, 47, 61, 17, 0, "02000000000B010000000D000001001A0000002F0000003D00110001010101FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 11, 73, -51, 16, 10, "02000000000B010000000D000001001A00000049FFFFFFCD00100001010103000000000004303B313000"),
		mustElement(kindCardMaster, 12, -23, 41, 13, 9, "02000000000B010000000D000001001AFFFFFFE900000029000D0001010101000000000003303B3900"),
		mustElement(kindFusionLab, 177, 1, -10, 16, 3, "02000000000B010000000D000001001A00000001FFFFFFF600100001010103FFFF000000013300"),
		mustElement(kindMailbox, 22, 44, 21, 13, 0, "02000000000B010000000D000001001A0000002C00000015000D0001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 23, 3, 62, 13, 0, "02000000000B010000000D000001001A000000030000003E000D0001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 104, 48, -25, 0, 0, "02000000000B010000000D000001001A00000030FFFFFFE700100001010103FFFF000000046E756C6C00"),
		mustElement(kindFirework, 108, 39, 2, 6, 0, "02000000000B010000000D000001001A000000270000000200060001010103FFFF0000000332373200"),
	},
	// --- Île du Quadraimant (Fourmagnet) ---------------------------------------
	27: {
		mustElement(kindZaap, 39, 54, 76, 15, 0, "02000000000B010000000D000001001B000000360000004C000F0001010103FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 13, 92, 82, 8, 11, "02000000000B010000000D000001001B0000005C0000005200000001010101000000000004303B313100"),
		mustElement(kindCardMaster, 14, 34, -6, 23, 12, "02000000000B010000000D000001001B00000022FFFFFFFA001B0001010103000000000004303B313200"),
		mustElement(kindFusionLab, 178, 114, 31, 9, 7, "02000000000B010000000D000001001B000000720000001F00090001010103FFFF000000013700"),
		mustElement(kindMailbox, 24, 43, -27, 23, 0, "02000000000B010000000D000001001B0000002BFFFFFFE500170001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 25, 84, 0, 16, 0, "02000000000B010000000D000001001B000000540000000000100001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 105, 38, 46, 0, 0, "02000000000B010000000D000001001B000000260000002E000F0001010101FFFF000000046E756C6C00"),
	},
	// --- Magmara ---------------------------------------------------------------
	28: {
		mustElement(kindZaap, 40, -22, -36, 50, 0, "02000000000B010000000D000001001CFFFFFFEAFFFFFFDC00320001010101FFFF000000046E756C6C00"),
		mustElement(kindCardMaster, 15, 2, 28, 35, 16, "02000000000B010000000D000001001C000000020000001C00230001010101000000000004303B313600"),
		mustElement(kindCardMaster, 16, 65, 0, 30, 15, "02000000000B010000000D000001001C0000004100000000001A0001010103000000000004303B313500"),
		mustElement(kindFusionLab, 179, 9, -46, 45, 5, "02000000000B010000000D000001001C00000009FFFFFFD2002D0001010103FFFF000000013500"),
		mustElement(kindMailbox, 26, 21, -20, 15, 0, "02000000000B010000000D000001001C00000015FFFFFFEC000F0001010103FFFF000000046E756C6C00"),
		mustElement(kindMailbox, 27, 35, 47, 35, 0, "02000000000B010000000D000001001C000000230000002F001F0001010103FFFF000000046E756C6C00"),
		mustElement(kindGraveyard, 106, -20, 20, 0, 0, "02000000000B010000000D000001001CFFFFFFEC0000001400280001010101FFFF000000046E756C6C00"),
	},
	// --- Totem Arena -----------------------------------------------------------
	// Destination of the five "first islands" Zaap cards, and the richest island in
	// the game: the complete 24-totem demon ladder, Demon I, the tournament totem
	// and the five minute-demon challenges. NOTE the client ships no
	// content.61.37, so this island's map panel shows "!content.61.37!" (the
	// picture still renders) — a client data gap, not server-fixable.
	37: {
		mustElement(kindZaap, 70, 132, 126, 24, 0, "02000000000B010000000D0000010025000000840000007E00180001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 74, 161, 143, -30, 0, "02000000000B010000000D0000010025000000A10000008FFFE20001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 100, 96, 173, -40, 0, "02000000000B010000000D000001002500000060000000ADFFD80001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 109, 44, 163, 0, 0, "02000000000B010000000D00000100250000002C000000A300000001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 110, 18, -24, 1, 0, "02000000000B010000000D000001002500000012FFFFFFE800010001010101FFFF000000046E756C6C00"),
		mustElement(kindZaap, 138, 134, 45, -4, 0, "02000000000B010000000D0000010025000000860000002DFFFC0001010101FFFF000000046E756C6C00"),
		// Card Masters: inst 101 is a "démone II" exchanger (flag 1), inst 210 a
		// normal shop.
		mustCardMaster(101, 111, 157, -40, 17, 1, "02000000000B010000000D00000100250000006F0000009DFFD80001010103FFFF00000004313B313700"),
		mustCardMaster(210, 66, 75, -3, 18, 0, "02000000000B010000000D0000010025000000420000004BFFFD0001010103FFFF00000004303B313800"),
		// Demon I and the five minute-demon challenges.
		mustElement(kindDemonI, 73, 136, 124, 0, 0, "02000000000B010000000D0000010025000000880000007C00190001010101FFFF000000173236313B3235313B3235323B3235333B3235343B32353500"),
		mustElement(kindDemonChallenge, 55, 137, 137, 1, 0, "02000000000B010000000D0000010025000000890000008900010001010101FFFF0000000E3236343B3131333B33363B32363900"),
		mustElement(kindDemonChallenge, 56, 144, 140, 1, 0, "02000000000B010000000D0000010025000000900000008C00010001010101FFFF0000000E3236353B3131343B33323B32373000"),
		mustElement(kindDemonChallenge, 57, 148, 106, 1, 0, "02000000000B010000000D0000010025000000940000006A00010001010101FFFF0000000E3236323B3131313B33343B32363700"),
		mustElement(kindDemonChallenge, 58, 136, 108, 1, 0, "02000000000B010000000D0000010025000000880000006C00010001010101FFFF0000000E3236333B3131323B33353B32363800"),
		mustElement(kindDemonChallenge, 72, 151, 111, 5, 0, "02000000000B010000000D0000010025000000970000006F00050001010101FFFF000000103236363B303B33333B3237313B32373400"),
		mustElement(kindTournamentTotem, 119, 121, 190, -32, 0, "02000000000B010000000D000001002500000079000000BEFFE00001010101FFFF0000000332373300"),
		mustElement(kindZoneTrigger, 71, 134, 122, 24, 0, "02000000000B010000000D0000010025000000860000007A001900010101010000000F000000840000007E0019000000840000007D0019000000840000007C0019000000840000007B0019000000840000007A0019000000850000007E0019000000850000007D0019000000850000007C0019000000850000007B0019000000850000007A0019000000860000007E0019000000860000007D0019000000860000007C0019000000860000007B0019000000860000007A001900093130393B303B32373800"),
		// The complete demon ladder: 24 totems, demon ids 1..24 (arg = demonId,
		// which our 27511 reply echoes back).
		mustElement(kindDemonTotem, 76, 108, 64, 0, 16, "02000000000B010000000D00000100250000006C0000004000000001010103FFFF00000002313600"),
		mustElement(kindDemonTotem, 77, 108, 52, 0, 4, "02000000000B010000000D00000100250000006C0000003400000001010103FFFF000000013400"),
		mustElement(kindDemonTotem, 78, 106, 90, 0, 5, "02000000000B010000000D00000100250000006A0000005A00000001010103FFFF000000013500"),
		mustElement(kindDemonTotem, 79, 106, 102, 0, 17, "02000000000B010000000D00000100250000006A0000006600000001010103FFFF00000002313700"),
		mustElement(kindDemonTotem, 80, 84, 123, 0, 7, "02000000000B010000000D0000010025000000540000007B00000001010103FFFF000000013700"),
		mustElement(kindDemonTotem, 81, 72, 123, 0, 19, "02000000000B010000000D0000010025000000480000007B00000001010103FFFF00000002313900"),
		mustElement(kindDemonTotem, 82, 48, 123, 0, 8, "02000000000B010000000D0000010025000000300000007B00000001010103FFFF000000013800"),
		mustElement(kindDemonTotem, 83, 13, 98, 0, 10, "02000000000B010000000D00000100250000000D0000006200000001010103FFFF00000002313000"),
		mustElement(kindDemonTotem, 84, 13, 86, 0, 22, "02000000000B010000000D00000100250000000D0000005600000001010103FFFF00000002323200"),
		mustElement(kindDemonTotem, 85, 11, 63, 0, 11, "02000000000B010000000D00000100250000000B0000003F00000001010103FFFF00000002313100"),
		mustElement(kindDemonTotem, 86, 11, 51, 0, 23, "02000000000B010000000D00000100250000000B0000003300000001010103FFFF00000002323300"),
		mustElement(kindDemonTotem, 87, 34, 28, 0, 1, "02000000000B010000000D0000010025000000220000001C00000001010103FFFF000000013100"),
		mustElement(kindDemonTotem, 88, 40, 22, 0, 13, "02000000000B010000000D0000010025000000280000001600000001010103FFFF00000002313300"),
		mustElement(kindDemonTotem, 89, 71, 26, 0, 2, "02000000000B010000000D0000010025000000470000001A00000001010103FFFF000000013200"),
		mustElement(kindDemonTotem, 90, 83, 26, 0, 14, "02000000000B010000000D0000010025000000530000001A00000001010103FFFF00000002313400"),
		mustElement(kindDemonTotem, 91, 89, 40, 0, 3, "02000000000B010000000D0000010025000000590000002800000001010103FFFF000000013300"),
		mustElement(kindDemonTotem, 92, 92, 47, 0, 15, "02000000000B010000000D00000100250000005C0000002F00000001010103FFFF00000002313500"),
		mustElement(kindDemonTotem, 93, 24, 45, 0, 12, "02000000000B010000000D0000010025000000180000002D00000001010103FFFF00000002313200"),
		mustElement(kindDemonTotem, 94, 29, 41, 0, 24, "02000000000B010000000D00000100250000001D0000002900000001010103FFFF00000002323400"),
		mustElement(kindDemonTotem, 95, 29, 110, 0, 9, "02000000000B010000000D00000100250000001D0000006E00000001010103FFFF000000013900"),
		mustElement(kindDemonTotem, 96, 25, 104, 0, 21, "02000000000B010000000D0000010025000000190000006800000001010103FFFF00000002323100"),
		mustElement(kindDemonTotem, 97, 89, 110, 0, 18, "02000000000B010000000D0000010025000000590000006E00000001010103FFFF00000002313800"),
		mustElement(kindDemonTotem, 98, 94, 105, 0, 6, "02000000000B010000000D00000100250000005E0000006900000001010103FFFF000000013600"),
		mustElement(kindDemonTotem, 99, 36, 123, 0, 20, "02000000000B010000000D0000010025000000240000007B00000001010103FFFF00000002323000"),
	},
	// --- Recruitment island ----------------------------------------------------
	// The twelve breed masters (recruit a fighter of each breed), Demon III, and
	// the tutorial zone triggers. Reachable with /WORLD 35; no Zaap card routes
	// here yet.
	35: {
		mustElement(kindZaap, 41, 100, 90, 0, 0, "02000000000B010000000D0000010023000000640000005A00000001010103FFFF000000046E756C6C00"),
		mustElement(kindBreedMaster, 42, 74, 147, 1, 0, "02000000000B010000000D00000100230000004A0000009300010001010101FFFF000000103137373B3135383B3135343B313B313800"),
		mustElement(kindBreedMaster, 43, 76, 105, 13, 0, "02000000000B010000000D00000100230000004C00000069000D0001010101FFFF000000103137353B3135393B3135343B323B313900"),
		mustElement(kindBreedMaster, 44, 79, 101, 12, 0, "02000000000B010000000D00000100230000004F00000065000C0001010103FFFF000000113137323B3136343B3135343B31303B323800"),
		mustElement(kindBreedMaster, 45, 60, 111, 0, 0, "02000000000B010000000D00000100230000003C0000006F00070001010103FFFF000000103138303B3136323B3135343B363B323700"),
		mustElement(kindBreedMaster, 46, 56, 115, 1, 0, "02000000000B010000000D0000010023000000380000007300010001010103FFFF000000103138313B3135363B3135343B393B323600"),
		mustElement(kindBreedMaster, 47, 63, 124, 1, 0, "02000000000B010000000D00000100230000003F0000007C00010001010101FFFF000000103137303B3136353B3135343B353B323500"),
		mustElement(kindBreedMaster, 48, 66, 144, 1, 0, "02000000000B010000000D0000010023000000420000009000010001010102FFFF000000103137393B3136333B3135343B373B323400"),
		mustElement(kindBreedMaster, 49, 61, 150, 1, 0, "02000000000B010000000D00000100230000003D0000009600010001010101FFFF000000113137343B3136363B3135343B31323B323300"),
		mustElement(kindBreedMaster, 50, 69, 155, 1, 0, "02000000000B010000000D0000010023000000450000009B00010001010107FFFF000000103137383B3135373B3135343B333B323200"),
		mustElement(kindBreedMaster, 51, 90, 165, -2, 0, "02000000000B010000000D00000100230000005A000000A500010001010101FFFF000000113137333B3136313B3135343B31313B323100"),
		mustElement(kindBreedMaster, 52, 111, 173, -2, 0, "02000000000B010000000D00000100230000006F000000AD00010001010101FFFF000000103137363B3135353B3135343B383B313700"),
		mustElement(kindBreedMaster, 53, 111, 161, 1, 0, "02000000000B010000000D00000100230000006F000000A100010001010103FFFF000000103137313B3136303B3135343B343B323000"),
		mustElement(kindDemonIII, 54, 116, 133, 1, 0, "02000000000B010000000D0000010023000000740000008500010001010101FFFF000000243134343B3135323B3135333B3136373B3136393B3136383B3237333B32393B33303B333100"),
		mustElement(kindZoneTrigger, 60, 179, 194, 0, 0, "02000000000B010000000D0000010023000000B3000000C20000000101010100000004000000B3000000C10000000000B3000000C20000000000B3000000C30000000000B3000000C4000000093130303B303B32373500"),
		mustElement(kindZoneTrigger, 62, 145, 171, 0, 0, "02000000000B010000000D000001002300000091000000AB000000010101010000000400000091000000AB000000000092000000AB000000000094000000AB000000000093000000AB000000093130323B303B32373500"),
		mustElement(kindZoneTrigger, 63, 115, 136, 1, 0, "02000000000B010000000D00000100230000007300000088000100010101010000000300000073000000880001000000740000008800000000007500000088FFFF00093130333B303B32373500"),
		mustElement(kindZoneTrigger, 64, 101, 154, 1, 0, "02000000000B010000000D0000010023000000650000009A0001000101010100000001000000650000009A000100093130343B303B33363000"),
		mustElement(kindZoneTrigger, 66, 79, 140, 1, 0, "02000000000B010000000D00000100230000004F0000008C00010001010101000000030000004F0000008C00010000004F0000008D00010000004F0000008E000100093130343B303B33363000"),
		mustElement(kindZoneTrigger, 67, 71, 127, 1, 0, "02000000000B010000000D0000010023000000470000007F0001000101010100000004000000470000007F000100000047000000800001000000470000008100010000004700000082000100093130343B303B33363000"),
		mustElement(kindZoneTrigger, 68, 81, 114, 8, 0, "02000000000B010000000D000001002300000051000000720008000101010100000002000000510000007300080000005100000072000800093130343B303B33363000"),
		mustElement(kindZoneTrigger, 69, 96, 98, 3, 0, "02000000000B010000000D000001002300000060000000620003000101010100000002000000600000006200030000006100000062000300093130383B3238343B3000"),
	},
	// --- Demon I's den ---------------------------------------------------------
	// Reachable with /WORLD 79. Demon I himself is an NPCTalker here.
	79: {
		mustElement(kindZaap, 130, 11, 28, 24, 0, "02000000000B010000000D000001004F0000000B0000001C00180001010101FFFF000000046E756C6C00"),
		mustElement(kindNPC, 181, 15, 22, 20, 0, "02000000000B010000000D000001004F0000000F0000001600190001010103FFFF000000133236313B2D313B36353B36353B64656D6F6E3100"),
		mustElement(kindDemonChallenge, 133, 15, 10, 1, 0, "02000000000B010000000D000001004F0000000F0000000A00010001010101FFFF0000000E3236333B3131323B33353B32363800"),
		mustElement(kindDemonChallenge, 134, 27, 8, 1, 0, "02000000000B010000000D000001004F0000001B0000000800010001010101FFFF0000000E3236323B3131313B33343B32363700"),
		mustElement(kindDemonChallenge, 135, 30, 13, 5, 0, "02000000000B010000000D000001004F0000001E0000000D00050001010101FFFF000000103236363B303B33333B3237313B32373400"),
		mustElement(kindDemonChallenge, 136, 23, 42, 1, 0, "02000000000B010000000D000001004F000000170000002A00010001010101FFFF0000000E3236353B3131343B33323B32373000"),
		mustElement(kindDemonChallenge, 137, 16, 39, 1, 0, "02000000000B010000000D000001004F000000100000002700010001010101FFFF0000000E3236343B3131333B33363B32363900"),
	},
	// --- Card Master hub -------------------------------------------------------
	// Seven "démone II" card exchangers (flag 1) plus a tournament totem.
	// Reachable with /WORLD 80.
	80: {
		mustElement(kindZaap, 131, -29, 9, -40, 0, "02000000000B010000000D0000010050FFFFFFE300000009FFD80001010101FFFF000000046E756C6C00"),
		mustCardMaster(141, -17, -6, -39, 17, 1, "02000000000B010000000D0000010050FFFFFFEFFFFFFFFAFFD80001010103FFFF00000008313B31373B33303900"),
		mustCardMaster(211, -19, -2, -40, 24, 1, "02000000000B010000000D0000010050FFFFFFEDFFFFFFFEFFD80001010103FFFF00000008313B32343B33313400"),
		mustCardMaster(212, -14, -2, -40, 22, 1, "02000000000B010000000D0000010050FFFFFFF2FFFFFFFEFFD80001010103FFFF00000008313B32323B33313500"),
		mustCardMaster(213, -11, 0, -40, 23, 1, "02000000000B010000000D0000010050FFFFFFF500000000FFD80001010103FFFF00000008313B32333B33313200"),
		mustCardMaster(214, -21, 0, -40, 20, 1, "02000000000B010000000D0000010050FFFFFFEB00000000FFD80001010103FFFF00000008313B32303B33313300"),
		mustCardMaster(215, -16, 3, -40, 21, 1, "02000000000B010000000D0000010050FFFFFFF000000003FFD80001010103FFFF00000008313B32313B33313100"),
		mustCardMaster(216, -16, 0, -40, 19, 1, "02000000000B010000000D0000010050FFFFFFF000000000FFD80001010103FFFF00000008313B31393B33313000"),
		mustElement(kindTournamentTotem, 139, -4, 26, -32, 0, "02000000000B010000000D0000010050FFFFFFFC0000001AFFE00001010101FFFF0000000332373300"),
	},
	// --- Gostof / Baan island --------------------------------------------------
	// Five talking NPCs (Baan and the four Gostof breeds). Reachable with /WORLD 85.
	85: {
		mustElement(kindZaap, 180, -2, 20, 0, 0, "02000000000B010000000D0000010055FFFFFFFE0000001400000001010101FFFF000000046E756C6C00"),
		mustElement(kindNPC, 143, 7, 9, 0, 0, "02000000000B010000000D0000010055000000070000000900060001010103FFFF0000000F3239353B3232383B373B373B626F6200"),
		mustElement(kindNPC, 170, 19, 9, 3, 0, "02000000000B010000000D0000010055000000130000000900030001010105FFFF000000193239393B2D313B35363B35363B676F73745372616D6574746500"),
		mustElement(kindNPC, 171, 9, 0, -3, 0, "02000000000B010000000D00000100550000000900000000FFFE0001010103FFFF000000143239373B2D313B34353B34353B676F7374496F7000"),
		mustElement(kindNPC, 172, -4, 7, 0, 0, "02000000000B010000000D0000010055FFFFFFFC0000000700000001010101FFFF000000163239363B2D313B35303B35303B676F7374536163726900"),
		mustElement(kindNPC, 173, 8, 18, -4, 0, "02000000000B010000000D00000100550000000800000012FFFC0001010107FFFF000000143239383B2D313B36303B36303B676F7374456E7500"),
	},
}

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
