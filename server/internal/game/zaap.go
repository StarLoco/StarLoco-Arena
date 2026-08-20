package game

// Zaap (teleporter) card network.
//
// The Zaaps themselves are interactive elements spawned per world — see
// elements.go (kindZaap) and Session.sendEnterOverworld. Clicking one opens the
// grimoire's Zaap-cards page locally (gated by client achievement 448 / criterion
// 219, which is seeded in the coach descriptor — see
// handshake.EncodeCoachInformations / criterionZaapUnlock; delivering it via
// opcode 22002 instead would pop the client's tutorial-guide dialog).
// Double-clicking a destination card sends ZAAP_USE (opcode 4512, client Gs) =
// [i32 cardTemplateId]; this file maps that card to a destination Zaap.
//
// See docs/OVERWORLD-MAP.md ("Zaap network") for the full reverse-engineering.

// zaapDest is a Zaap card's destination: the world plus the exact Zaap on it the
// coach arrives at (an island can have several).
type zaapDest struct {
	world      int16
	instanceID int64
}

// zaapCardDest maps a Zaap CARD template id (the [i32 cardId] the client sends in
// ZAAP_USE / 4512) to its destination. Zaap cards are ordinary cards whose
// card-type is 20; names live at content.23.<id>, destination text at
// content.24.<id>, and worlds are named by content.61.<worldId>.
//
// Set 30 "Zaaps des premières îles" (7 cards): 202, 553 + the five Totem-Arena
// cards. Set 86 "Zaaps des îles avancées" (6 cards): 203, 204, 206, 207, 208 (+
// 859, see below). The exact Zaap for the low-confidence Totem-Arena rows was
// inferred from co-located env elements (254 is solid: Demon I stands 4 cells from
// inst 70); a wrong guess only lands the coach elsewhere on the RIGHT island.
var zaapCardDest = map[int32]zaapDest{
	202: {23, 35},  // Maknala — place du marché
	553: {23, 112}, // Maknala — village de la plage
	203: {24, 36},  // Strubia
	204: {25, 37},  // Veniviki
	208: {26, 38},  // Île du Passage
	206: {27, 39},  // Île du Quadraimant
	207: {28, 40},  // Île de Magmara
	254: {37, 70},  // Totem Arena — repaire du démon I
	255: {37, 138}, // Totem Arena — route totémique
	256: {37, 100}, // Totem Arena — îlot des tournois
	558: {37, 109}, // Totem Arena — le platotémique
	870: {37, 74},  // Totem Arena — île aux Rigines
}

// starterZaapCards are the Zaap cards granted to every coach so the network is
// usable out of the box. The retail game handed these out as level-up rewards
// (content.49.430-434); granting them up front is a dev/preservation convenience.
// Ascending id order. Every card here MUST have a zaapCardDest entry.
var starterZaapCards = []int32{202, 203, 204, 206, 207, 208, 254, 255, 256, 553, 558, 870}

// --- Deliberately NOT granted / NOT routed -----------------------------------
//
// Card 859 "Île de clan" (set 86) teleports to the coach's OWN clan island. Those
// are worlds 86..109 — one per clan — so it can only be routed once a clan system
// exists and a coach can be resolved to a clan. DONE: see clanIslandDest below -
// the destination is now a dynamic per-clan lookup, allotted from the 24 islands
// the map data ships. The instanceId/cell/alt table is in docs/OVERWORLD-MAP.md
// under "Clan island Zaaps", and a test parses that document to check the
// transcription rather than trusting it.
//
// Cards 547..552 (set 80, "Zaapis") are UNRELEASED placeholder content in this
// build and are intentionally left unowned: content.26.80 says they "will SOON
// give access to new islands", their content.24 destination text never names an
// island (unlike every working card), no content.61 world carries their names
// (Ledrob/Onskaï/Ripaï/Siska/Trubwak/Krokoboo), and every Zaap-bearing world in
// the client is already accounted for. Routing them would be inventing content.

// clanIslandZaapCard is card 859 "Île de clan": unlike every other Zaap card its
// destination is not fixed, it is the coach's OWN clan island.
const clanIslandZaapCard int32 = 859

// clanIslandZaap maps a clan-island world (86-109) to the Zaap instance a coach
// arrives at. Transcribed from docs/OVERWORLD-MAP.md "Clan island Zaaps"; world
// 102 ships two Zaaps and the first is used.
var clanIslandZaap = map[int16]int64{
	86: 144, 87: 145, 88: 146, 89: 147, 90: 148, 91: 149,
	92: 150, 93: 151, 94: 152, 95: 153, 96: 155, 97: 156,
	98: 157, 99: 158, 100: 159, 101: 160, 102: 161, 103: 163,
	104: 164, 105: 165, 106: 166, 107: 167, 108: 168, 109: 169,
}

// clanIslandDest resolves card 859 for a coach: its clan's island, allotting one
// on first use. Reports ok=false when the coach is in no clan, or when all 24
// islands are already taken - the case the client has its own
// `error.guild.noIsland` message for.
func (d *Deps) clanIslandDest(coachID uint) (zaapDest, bool) {
	if d == nil || d.Store == nil || d.Store.Guilds == nil {
		return zaapDest{}, false
	}
	m, err := d.Store.Guilds.MembershipOf(coachID)
	if err != nil || m == nil {
		return zaapDest{}, false
	}
	world, err := d.Store.Guilds.AssignIsland(m.GuildID)
	if err != nil || world == 0 {
		return zaapDest{}, false
	}
	inst, ok := clanIslandZaap[world]
	if !ok {
		return zaapDest{}, false
	}
	return zaapDest{world, inst}, true
}
