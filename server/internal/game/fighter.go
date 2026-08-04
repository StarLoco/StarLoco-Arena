package game

import (
	"strings"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Fighter creation limits (from the client rules).
const (
	maxFighterNameLen = 16
	maxFighterSpells  = 6
	// breedBaseValue is the value every breed contributes to a fighter's budget
	// before its cards. 600 in 2.70: it is the 8th argument of the client's breed
	// table (xq), the position v2.04b's unobfuscated Breed.java exposes as
	// getValue() and holds 400 in. Ours was the 2.04b figure, so every fighter's
	// budget was 200 short. See BUGS.md B-058.
	breedBaseValue = 600
	minBreedID     = 1
	maxBreedID     = 14

	// Fighter-inventory (6010/6011) loadout slot counts, from the client:
	// cards = ajv_2(6), spells = en_1(5).
	maxLoadoutCards  = 6
	maxLoadoutSpells = 5
)

// validBreed reports whether a breed id is a legal player breed.
func validBreed(id uint8) bool { return id >= minBreedID && id <= maxBreedID }

// sanitizeFighterName trims + caps the name, falling back to "Noob" (the
// client's own fallback) if empty.
func sanitizeFighterName(name string) string {
	name = strings.TrimSpace(name)
	if len(name) > maxFighterNameLen {
		name = name[:maxFighterNameLen]
	}
	if name == "" {
		return "Noob"
	}
	return name
}

// buildFighter validates a decoded blob into a persistable Fighter owned by
// coachID, computing its budget server-side. Invalid spells/cards are dropped.
func (s *Session) buildFighter(coachID uint, fb *FighterBlob) *domain.Fighter {
	breed := fb.BreedID
	if !validBreed(breed) {
		breed = minBreedID
	}
	f := &domain.Fighter{
		CoachID: coachID,
		BreedID: breed,
		Name:    sanitizeFighterName(fb.Name),
		Sex:     fb.Sex & 1,
		Hair:    fb.Hair,
		Skin:    fb.Skin,
		Eye:     fb.Eye,
		// The client tells us which roster it is recruiting for, via the et_2
		// type byte. This was decoded into FighterBlob.Type and then never read,
		// so a fighter created on the Évolution tab came straight back as a
		// CLASSIC one and the evolution roster could never be populated at all.
		Evolution: fb.Type == fighterBlobTypeEvolution,
	}

	// Spells: dedup, cap at 6. (Breed-legality check would need spell gamedata;
	// deferred — we keep client-provided ids but bounded.)
	seen := make(map[int32]bool)
	for _, id := range fb.SpellIDs {
		if seen[id] || len(f.Spells) >= maxFighterSpells {
			continue
		}
		seen[id] = true
		f.Spells = append(f.Spells, domain.FighterSpell{SpellID: id})
	}

	// Cards: one per slot (first wins).
	slotUsed := make(map[int16]bool)
	for _, c := range fb.Cards {
		if slotUsed[c.Slot] {
			continue
		}
		slotUsed[c.Slot] = true
		f.Objects = append(f.Objects, domain.FighterObject{TemplateID: c.ID, Slot: c.Slot})
	}

	f.Budget = s.computeFighterBudget(f)
	return f
}

// computeFighterBudget = breed base (400) + Σ card values (+ spell prices when
// spell gamedata is available). Clamped to int16. Values come from the FIGHTER-card
// table — see computeLoadoutBudget for why using the coach-card table was wrong.
func (s *Session) computeFighterBudget(f *domain.Fighter) int16 {
	value := breedBaseValue
	if s.deps.FighterCards != nil {
		for _, obj := range f.Objects {
			if card := s.deps.FighterCards.Get(obj.TemplateID); card != nil {
				value += int(card.Value)
			}
		}
	}
	// Spell prices require a spell gamedata table (not yet loaded); treated as 0.
	if value > 32767 {
		value = 32767
	}
	return int16(value)
}
