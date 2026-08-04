package game

// conditions.go — the persistent fighter-condition layer (gamedata type 902):
// how a condition is APPLIED, what it DOES inside a fight, and how it EXPIRES.
// The post-fight wound roll that creates them lives in postfight_wounds.go.
//
// A condition is a status that survives the fight: a wound, a blessing or a
// curse. The client keeps them as a conditionId → duration map (`et_2.uk`) and
// resolves their effects at fighter-build time alongside breed and equipment
// (`gn_0.a`), which is exactly what applyConditionEffects does here.

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// condTypeStacking is the ONE mutual-exclusion class that does not exclude:
// type 21 ("ailments"), which the client lets stack freely. Everything else is
// one-per-type.
const condTypeStacking int16 = 21

// condTypePet is applied through a different client hook (`vm_2.d`) with none of
// the exclusion checks — 7 permanent stat grants reached via the Sphere Board.
// The normal apply path REFUSES it, so we refuse it too rather than invent a
// rule we cannot evidence.
const condTypePet int16 = 70

// canApplyCondition reports whether `cond` may be added to a fighter, mirroring
// the client's `vm_2.a`:
//
//	for each condition the fighter already holds:
//	    if (held.type == cond.type && cond.type != 21) || cond.type == 70 -> refuse
//
// Two consequences worth stating, because both are easy to get backwards:
//   - It is FIRST-WINS, not replace. An existing wound of the same body part
//     blocks a new one; the newcomer is silently dropped.
//   - Type 21 is exempt, so ailments accumulate without limit.
func canApplyCondition(defs *gamedata.Conditions, f *domain.Fighter, cond *gamedata.Condition) bool {
	if f == nil || cond == nil {
		return false
	}
	if cond.Type == condTypePet {
		return false // only reachable via the sphere-board hook
	}
	if cond.Type == condTypeStacking {
		return true // ailments always stack
	}
	for _, held := range f.Conditions {
		hd := defs.Get(held.ConditionID)
		if hd == nil {
			continue
		}
		if hd.Type == cond.Type {
			return false // one per mutual-exclusion class, first wins
		}
	}
	return true
}

// applyCondition adds a condition to a fighter if the rules allow, returning
// whether it landed. `duration` is the number of FIGHTS it lasts; pass the
// definition's own Duration unless a card overrides it.
func applyCondition(defs *gamedata.Conditions, f *domain.Fighter, id int16, duration int8) bool {
	cond := defs.Get(id)
	if cond == nil || !canApplyCondition(defs, f, cond) {
		return false
	}
	f.Conditions = append(f.Conditions, domain.FighterCondition{
		FighterID:   f.ID,
		ConditionID: id,
		Remaining:   duration,
	})
	return true
}

// removeCondition drops a condition by id, reporting whether it was held.
func removeCondition(f *domain.Fighter, id int16) bool {
	for i, c := range f.Conditions {
		if c.ConditionID == id {
			f.Conditions = append(f.Conditions[:i], f.Conditions[i+1:]...)
			return true
		}
	}
	return false
}

// heldWounds splits a fighter's conditions into the light and serious wounds it
// currently carries, and the set of body-part types already wounded at ANY
// severity. The roller needs all three.
func heldWounds(defs *gamedata.Conditions, f *domain.Fighter) (light, serious []*gamedata.Condition, woundedParts map[int16]bool) {
	woundedParts = map[int16]bool{}
	for _, held := range f.Conditions {
		cond := defs.Get(held.ConditionID)
		if cond == nil {
			continue
		}
		switch {
		case cond.IsLightWound():
			light = append(light, cond)
			woundedParts[cond.BodyPartOf()] = true
		case cond.IsSeriousWound():
			serious = append(serious, cond)
			woundedParts[cond.BodyPartOf()] = true
		}
	}
	return light, serious, woundedParts
}

// expireConditions decrements every non-permanent condition by one fight and
// drops those that reach zero. Returns the ids that expired.
//
// HONEST LIMIT: no client code decrements this byte — the countdown is
// server-side and therefore not recoverable. What IS evidence is `vm_2.a`
// adding +1 to a card-applied duration "so it survives this fight", which only
// makes sense if the decrement happens once per fight at the end. That is what
// this implements; the ordering against the wound roll is our choice (we expire
// AFTER rolling, so a wound inflicted this fight is not immediately aged).
func expireConditions(f *domain.Fighter) []int16 {
	if f == nil || len(f.Conditions) == 0 {
		return nil
	}
	var expired []int16
	kept := f.Conditions[:0]
	for _, c := range f.Conditions {
		if c.Remaining == domain.ConditionPermanent {
			kept = append(kept, c) // wounds never tick down
			continue
		}
		c.Remaining--
		if c.Remaining <= 0 {
			expired = append(expired, c.ConditionID)
			continue
		}
		kept = append(kept, c)
	}
	f.Conditions = kept
	return expired
}

// healWounds removes wounds of one severity with a per-wound chance, mirroring
// the consumable behaviour of AI 5 ("soigne toutes les blessures graves") and
// AI 11 ("soigne toutes les blessures légères"). Returns how many were healed.
func healWounds(defs *gamedata.Conditions, f *domain.Fighter, serious bool, chancePct int32, roll func(int) int) int {
	if f == nil || chancePct <= 0 {
		return 0
	}
	var healed int
	kept := f.Conditions[:0]
	for _, c := range f.Conditions {
		cond := defs.Get(c.ConditionID)
		match := cond != nil && (serious && cond.IsSeriousWound() || !serious && cond.IsLightWound())
		if match && int32(roll(100)) < chancePct {
			healed++
			continue
		}
		kept = append(kept, c)
	}
	f.Conditions = kept
	return healed
}

// conditionMetaBonus sums the magnitude of every META effect with the given AI
// action id across the conditions a fighter holds. This is how a head wound's
// "-10% XP" actually reaches the post-fight report.
func conditionMetaBonus(defs *gamedata.Conditions, f *domain.Fighter, action int32) int32 {
	if f == nil {
		return 0
	}
	var total int32
	for _, held := range f.Conditions {
		cond := defs.Get(held.ConditionID)
		if cond == nil {
			continue
		}
		for _, ef := range cond.MetaEffects {
			if ef.Action == action && len(ef.Params) > 0 {
				total += ef.Params[0]
			}
		}
	}
	return total
}

// conditionFightEffects returns every in-fight effect row carried by the
// conditions a fighter holds, to be folded into its stats at fight setup.
func conditionFightEffects(defs *gamedata.Conditions, f *domain.Fighter) []gamedata.Effect {
	if f == nil {
		return nil
	}
	var out []gamedata.Effect
	for _, held := range f.Conditions {
		cond := defs.Get(held.ConditionID)
		if cond == nil {
			continue
		}
		out = append(out, cond.FightEffects...)
	}
	return out
}
