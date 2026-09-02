package game

// Coach-card equipment rules, ported from the retail client.
//
// These are rules the client enforces LOCALLY, which means a modified client
// simply omits them - and unlike the roster rules there is no server->client
// refusal frame for them, so the server's only options are "accept" or "silently
// drop". Dropping is right: the retail client can never produce these requests,
// so anything that reaches here is forged.

// coachCardSlotForType maps a coach-card type (client enum aMK) to the ONE
// equipment slot it may occupy, or -1 for "not equippable at all".
//
// Ported verbatim from aMK.java:6-36, where every wearable type declares exactly
// one legal position and everything else declares {-1}. Slot values here are the
// client's 0-based positions; applyEquipment stores Pos = slot+1.
//
// SECURITY: applyEquipment previously mapped slot index -> Pos with an ownership
// check and nothing else, so ANY owned template could occupy ANY of the 14 slots -
// including types the client can never equip (Zaap, firework, title, emote...).
// That matters because equippedCountsPerSet counts equipped cards per card SET,
// and set thresholds feed real outcomes: resurrection chance, XP, morale, fatigue,
// reputation, wound and death chance. Fourteen cards of one set is every threshold
// unlocked at once.
var coachCardSlotForType = map[int32]int16{
	2:  5,  // Culotte
	3:  2,  // Coiffure
	4:  1,  // Tatouages
	5:  4,  // Brassard
	6:  10, // Bottes
	7:  3,  // Epaulette
	8:  8,  // Cape
	9:  6,  // Pantalon
	10: 11, // Chemise
	11: 0,  // Chapeau
	12: 7,  // Baton
	13: 9,  // Familier
}

// coachCardFitsSlot reports whether a card template may occupy slot (0-based).
func coachCardFitsSlot(cardType int32, slot int16) bool {
	want, ok := coachCardSlotForType[cardType]
	return ok && want == slot
}

// coachCardIsEquippable reports whether the type is wearable at all.
func coachCardIsEquippable(cardType int32) bool {
	_, ok := coachCardSlotForType[cardType]
	return ok
}
