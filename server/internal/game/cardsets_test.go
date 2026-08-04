package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestSetBonusFor pins the card-set aggregation: a bonus counts only once the
// coach has the effect's threshold worth of that set's cards EQUIPPED, and
// several sources of the same action sum.
func TestSetBonusFor(t *testing.T) {
	deps := &Deps{
		Cards: gamedata.NewCards(
			&gamedata.CoachCard{ID: 10, CardSet: 1},
			&gamedata.CoachCard{ID: 11, CardSet: 1},
			&gamedata.CoachCard{ID: 12, CardSet: 1},
			&gamedata.CoachCard{ID: 20, CardSet: 2},
			&gamedata.CoachCard{ID: 30}, // no set
		),
		CardSets: gamedata.NewCardSets(
			&gamedata.CardSet{ID: 1, Effects: []gamedata.CardSetEffect{
				{Action: aiActionResurrect, Params: []int32{5}, Threshold: 2},
				{Action: aiActionResurrect, Params: []int32{20}, Threshold: 3},
				{Action: 1, Params: []int32{99}, Threshold: 2}, // a different action
			}},
			&gamedata.CardSet{ID: 2, Effects: []gamedata.CardSetEffect{
				{Action: aiActionResurrect, Params: []int32{7}, Threshold: 1},
			}},
		),
	}
	sess := func(equipped ...int32) *Session {
		coach := &domain.Coach{ID: 1, Name: "T"}
		for i, id := range equipped {
			coach.Inventory = append(coach.Inventory,
				domain.CoachCard{TemplateID: id, Pos: int16(i + 1)})
		}
		return &Session{Coach: coach, deps: deps}
	}

	// One card of set 1: below every threshold.
	if got := sess(10).setBonusFor(aiActionResurrect); got != 0 {
		t.Errorf("1 card = %d, want 0", got)
	}
	// Two cards: unlocks the threshold-2 effect only.
	if got := sess(10, 11).setBonusFor(aiActionResurrect); got != 5 {
		t.Errorf("2 cards = %d, want 5", got)
	}
	// Three: both set-1 effects, summed.
	if got := sess(10, 11, 12).setBonusFor(aiActionResurrect); got != 25 {
		t.Errorf("3 cards = %d, want 25 (5+20)", got)
	}
	// Adding a card of another set adds that set's bonus too.
	if got := sess(10, 11, 12, 20).setBonusFor(aiActionResurrect); got != 32 {
		t.Errorf("3 of set 1 + 1 of set 2 = %d, want 32 (5+20+7)", got)
	}
	// A different action id is not mixed in.
	if got := sess(10, 11).setBonusFor(1); got != 99 {
		t.Errorf("action 1 bonus = %d, want 99", got)
	}
	// UNEQUIPPED cards must not count (Pos 0).
	unequipped := &Session{deps: deps, Coach: &domain.Coach{ID: 1, Inventory: []domain.CoachCard{
		{TemplateID: 10, Pos: 0}, {TemplateID: 11, Pos: 0}, {TemplateID: 12, Pos: 0},
	}}}
	if got := unequipped.setBonusFor(aiActionResurrect); got != 0 {
		t.Errorf("unequipped cards granted %d, want 0", got)
	}
	// No catalogue (server without data files) must be inert, not a panic.
	if got := (&Session{Coach: &domain.Coach{}, deps: &Deps{}}).setBonusFor(aiActionResurrect); got != 0 {
		t.Errorf("no catalogue = %d, want 0", got)
	}
}
