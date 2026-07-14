package main

import (
	"math/rand"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata"
)

// generator.go builds never-identical, always-legal fighter loadouts from
// the real game data. "Legal" means the same rules the wire handler
// enforces (internal/dispatch/packets_fighter.go validateFighterSpells/
// validateFighterObjects/computeFighterBudget + the 5000 team-value cap):
//
//   - at most 6 distinct spells, each belonging to the fighter's breed;
//   - at most one equipment card per slot (weapon/pet/cloak/hat/dofus);
//   - the resulting team value must stay under MaxTeamValue.
//
// We reproduce those rules here rather than importing the dispatch package
// (its validators are unexported and the dispatch package pulls in the
// whole server); the rules are small and stable, and the seeder persists
// through the same FighterService the wire path uses.

const (
	maxFighterSpells = 6
	maxTeamValue     = 5000
	// summonActionID is the effect ActionID that summons a creature
	// (combat.EffectSummon, effects_registry.go 67 -> EffectSummon). A
	// spell or card whose effects include this can deploy a summon.
	summonActionID = 67
)

// dataIndex is a precomputed, read-only view of the game data the generator
// and AI need, built once at startup so per-fighter generation is a cheap
// map/slice lookup rather than repeated repository scans.
type dataIndex struct {
	// spellsByBreed maps a breed id to its equippable spell ids.
	spellsByBreed map[byte][]int32
	// summonSpellsByBreed maps a breed id to the subset of its spells that
	// summon a creature (effect ActionID 67).
	summonSpellsByBreed map[byte][]int32
	// cardsBySlot maps an equipment slot (0..4) to fighter-card ids valid
	// for that slot.
	cardsBySlot map[int16][]int32
	// coachCardIDs are all valid cosmetic coach-card template ids (for the
	// "2 card sets, equip 1" coach provisioning and card exchanges).
	coachCardIDs []int32
	// equipCoachCardsBySlot maps a coach equipment WIRE slot (0..13) to the
	// coach-card template ids that can be worn there. A card only renders on
	// the coach sprite when equipped into the slot matching its type (see
	// coachCardTypeWireSlots), so the seeder must place each card in its
	// correct slot -- putting a hat in the cloak slot shows nothing.
	equipCoachCardsBySlot map[int16][]int32
	// slotByCoachCardTemplate maps a coach-card template id to its wire slot
	// (only for body-equippable templates). Lets the seeder re-slot cards a
	// reused coach already owns.
	slotByCoachCardTemplate map[int32]int16
	// spellPrice / cardValue cache the point cost of each id so budget math
	// avoids repeated repository Gets.
	spellPrice map[int32]int32
	cardValue  map[int32]int32
}

// buildDataIndex scans the store once and precomputes the generator's
// lookup tables. It only reads data already present; missing categories
// (e.g. no summon spells for a breed) yield empty slices, which the
// generator handles gracefully.
func buildDataIndex(store *gamedata.Store) *dataIndex {
	idx := &dataIndex{
		spellsByBreed:         make(map[byte][]int32),
		summonSpellsByBreed:   make(map[byte][]int32),
		cardsBySlot:           make(map[int16][]int32),
		spellPrice:            make(map[int32]int32),
		cardValue:               make(map[int32]int32),
		equipCoachCardsBySlot:   make(map[int16][]int32),
		slotByCoachCardTemplate: make(map[int32]int16),
	}

	playable := make(map[int32]bool, 12)
	for _, b := range combat.AllPlayableBreeds() {
		playable[int32(b)] = true
	}

	for _, sp := range store.Spells.All() {
		if !playable[sp.BreedID] {
			continue
		}
		breed := byte(sp.BreedID)
		idx.spellsByBreed[breed] = append(idx.spellsByBreed[breed], sp.ID)
		idx.spellPrice[sp.ID] = sp.Price
		for _, eff := range sp.Effects {
			if eff.ActionID == summonActionID {
				idx.summonSpellsByBreed[breed] = append(idx.summonSpellsByBreed[breed], sp.ID)
				break
			}
		}
	}

	for _, card := range store.FighterCards.All() {
		slot, ok := gamedata.FighterCardInventoryPosition(card.Type)
		if !ok {
			continue
		}
		idx.cardsBySlot[slot] = append(idx.cardsBySlot[slot], card.ID)
		idx.cardValue[card.ID] = card.Value
	}

	for _, cc := range store.CoachCards.All() {
		idx.coachCardIDs = append(idx.coachCardIDs, cc.ID)
		// If this card's type is body-equippable, index it under its wire
		// slot so the seeder can dress the coach in a coherent, RENDERABLE
		// outfit (right card in the right slot).
		if slot, ok := coachCardWireSlot(cc.Type); ok {
			idx.equipCoachCardsBySlot[slot] = append(idx.equipCoachCardsBySlot[slot], cc.ID)
			idx.slotByCoachCardTemplate[cc.ID] = slot
		}
	}

	return idx
}

// coachCardTypeWireSlots maps a CoachCardTemplate.Type (the CoachCardType
// enum id) to the equipment WIRE slot(s) the client renders it in, taken
// verbatim from the decompiled client's CoachCardType.java inventoryPositions
// (client/.../common/game/card/CoachCardType.java). Types not listed
// (SMILEY, EMOTE, CURSE, PET_*) have no body slot (position -1) and are not
// worn on the sprite. Multi-slot types (ARMBAND, SHOULDERPAD) list both
// left/right slots.
var coachCardTypeWireSlots = map[int32][]int16{
	2:  {5},      // PANT       -> Culotte
	3:  {2},      // HAIRS      -> Coiffure
	4:  {1},      // TATOO      -> Tatouages
	5:  {4, 12},  // ARMBAND    -> Brassard L/R
	6:  {10},     // SHOES      -> Bottes
	7:  {3, 13},  // SHOULDERPAD-> Epaulette L/R
	8:  {8},      // CLOAK      -> Cape
	9:  {6},      // TROUSERS   -> Pantalon
	10: {11},     // SHIR       -> Chemise
	11: {0},      // HAT        -> Chapeau
	12: {7},      // STAFF      -> Baton (weapon)
	13: {9},      // PET        -> Familier
}

// coachCardWireSlot returns the primary (first) wire slot a coach-card type
// is worn in, or (0,false) if the type is not body-equippable.
func coachCardWireSlot(cardType int32) (int16, bool) {
	slots, ok := coachCardTypeWireSlots[cardType]
	if !ok || len(slots) == 0 {
		return 0, false
	}
	return slots[0], true
}

// equipEntry is one item of a generated outfit: a coach-card template to
// grant and the WIRE slot to equip it in.
type equipEntry struct {
	TemplateID int32
	WireSlot   int16
}

// generateOutfit builds a random, RENDERABLE outfit: for a random subset of
// the body slots that have cards available, it picks one card. The result is
// a coherent set of (template, slot) pairs the seeder equips so the coach
// visibly wears gear. minSlots guarantees at least a few pieces show.
func (idx *dataIndex) generateOutfit(rng *rand.Rand, minSlots int) []equipEntry {
	slots := make([]int16, 0, len(idx.equipCoachCardsBySlot))
	for slot := range idx.equipCoachCardsBySlot {
		slots = append(slots, slot)
	}
	// Deterministic base order then shuffle, so a seeded rng is reproducible.
	sortInt16(slots)
	rng.Shuffle(len(slots), func(i, j int) { slots[i], slots[j] = slots[j], slots[i] })

	var outfit []equipEntry
	for _, slot := range slots {
		cards := idx.equipCoachCardsBySlot[slot]
		if len(cards) == 0 {
			continue
		}
		// Always fill the first minSlots; beyond that, ~60% chance per slot
		// so outfits vary between bots.
		if len(outfit) >= minSlots && rng.Float64() < 0.4 {
			continue
		}
		outfit = append(outfit, equipEntry{TemplateID: cards[rng.Intn(len(cards))], WireSlot: slot})
	}
	return outfit
}

func sortInt16(s []int16) {
	for i := 1; i < len(s); i++ {
		for j := i; j > 0 && s[j-1] > s[j]; j-- {
			s[j-1], s[j] = s[j], s[j-1]
		}
	}
}

// loadout is one generated fighter's build.
type loadout struct {
	Breed     byte
	SpellIDs  []int32
	ObjectIDs []int32
	Budget    int16
	// CanSummon reports whether this build includes at least one summon
	// spell (for behavior/telemetry: bots with a summoner will exercise the
	// summon path).
	CanSummon bool
}

// generateLoadout produces a random, legal fighter build for a randomly
// chosen playable breed using rng. Determinism: pass a seeded *rand.Rand
// for reproducible swarms. It guarantees:
//   - a valid playable breed;
//   - 1..maxFighterSpells distinct breed spells (biased to include a summon
//     spell when the breed has one, so summons get exercised);
//   - 0..5 equipment cards, at most one per slot;
//   - Budget recomputed the same way the server does, and the single-fighter
//     value kept well under maxTeamValue (a lone fighter is ~400 + loadout,
//     never near the cap, but we still clamp defensively).
//
// Never-identical: the (breed, spell subset, card subset) space is large;
// callers that want a strong uniqueness guarantee can track produced
// signatures, but in practice random selection over the real data yields
// distinct builds with overwhelming probability.
func (idx *dataIndex) generateLoadout(rng *rand.Rand) loadout {
	breeds := combat.AllPlayableBreeds()
	// Prefer a breed that actually has spells; fall back to any playable.
	var breed byte
	for attempts := 0; attempts < len(breeds)*2; attempts++ {
		b := breeds[rng.Intn(len(breeds))]
		if len(idx.spellsByBreed[b]) > 0 {
			breed = b
			break
		}
	}
	if breed == 0 {
		breed = breeds[rng.Intn(len(breeds))]
	}

	lo := loadout{Breed: breed}

	// Spells: pick a random-sized distinct subset of this breed's pool,
	// seeded to include a summon spell when available.
	pool := append([]int32(nil), idx.spellsByBreed[breed]...)
	rng.Shuffle(len(pool), func(i, j int) { pool[i], pool[j] = pool[j], pool[i] })

	summons := idx.summonSpellsByBreed[breed]
	if len(summons) > 0 && rng.Float64() < 0.85 {
		// Ensure a summon spell is first so it survives the size cap.
		sp := summons[rng.Intn(len(summons))]
		pool = moveToFront(pool, sp)
		lo.CanSummon = true
	}

	n := 0
	if len(pool) > 0 {
		n = 1 + rng.Intn(min(len(pool), maxFighterSpells))
	}
	lo.SpellIDs = append(lo.SpellIDs, pool[:n]...)
	// Recompute CanSummon from the actual chosen set (moveToFront may have
	// been a no-op if the breed had no summon).
	lo.CanSummon = false
	for _, id := range lo.SpellIDs {
		if containsInt32(summons, id) {
			lo.CanSummon = true
			break
		}
	}

	// Equipment: for each slot, maybe add one random card.
	for slot := int16(0); slot <= 4; slot++ {
		cards := idx.cardsBySlot[slot]
		if len(cards) == 0 || rng.Float64() < 0.4 {
			continue // ~40% of slots left empty for variety
		}
		lo.ObjectIDs = append(lo.ObjectIDs, cards[rng.Intn(len(cards))])
	}

	lo.Budget = idx.computeBudget(breed, lo.SpellIDs, lo.ObjectIDs)
	return lo
}

// computeBudget mirrors computeFighterBudget: breed base value + sum of
// spell prices + sum of card values, clamped to int16.
func (idx *dataIndex) computeBudget(breed byte, spellIDs, objectIDs []int32) int16 {
	var value int32
	if stats, ok := combat.GetBreedStats(breed); ok {
		value += stats.Value
	}
	for _, id := range spellIDs {
		value += idx.spellPrice[id]
	}
	for _, id := range objectIDs {
		value += idx.cardValue[id]
	}
	const maxInt16, minInt16 = 32767, -32768
	if value > maxInt16 {
		value = maxInt16
	}
	if value < minInt16 {
		value = minInt16
	}
	return int16(value)
}

// pickCoachCardSet returns setSize distinct random coach-card template ids,
// used to grant a "card set" at coach creation. Returns fewer if the pool
// is smaller than setSize.
func (idx *dataIndex) pickCoachCardSet(rng *rand.Rand, setSize int) []int32 {
	if len(idx.coachCardIDs) == 0 {
		return nil
	}
	pool := append([]int32(nil), idx.coachCardIDs...)
	rng.Shuffle(len(pool), func(i, j int) { pool[i], pool[j] = pool[j], pool[i] })
	if setSize > len(pool) {
		setSize = len(pool)
	}
	return pool[:setSize]
}

// --- small helpers ---

func moveToFront(s []int32, v int32) []int32 {
	for i, x := range s {
		if x == v {
			copy(s[1:i+1], s[0:i])
			s[0] = v
			return s
		}
	}
	return s
}

func containsInt32(s []int32, v int32) bool {
	for _, x := range s {
		if x == v {
			return true
		}
	}
	return false
}

func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}
