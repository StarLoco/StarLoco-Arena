package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// fusionCatalog: cards 700/701/702 share CardSet 5; 900 is in a different set.
func fusionCatalog() *gamedata.Cards {
	return gamedata.NewCards(
		&gamedata.CoachCard{ID: 700, CardSet: 5, Price: map[uint8]int32{1: 10}},
		&gamedata.CoachCard{ID: 701, CardSet: 5, Price: map[uint8]int32{1: 10}},
		&gamedata.CoachCard{ID: 702, CardSet: 5, Price: map[uint8]int32{1: 10}},
		&gamedata.CoachCard{ID: 900, CardSet: 9, Price: map[uint8]int32{1: 10}},
	)
}

// parseFusion reads a FusionResult(5491): [i8 result][i32 obt][i32 notObt][i32 rec].
func parseFusion(payload []byte) (result uint8, obtained, notObtained, recovered int32) {
	r := testclient.NewR(payload)
	return r.U8(), r.I32(), r.I32(), r.I32()
}

// TestFusionSuccess: fusing two same-set cards succeeds (seeded RNG), consuming
// the inputs and granting an obtained card from that set.
func TestFusionSuccess(t *testing.T) {
	game.SeedFusionRand(3) // seed 3 -> first roll < 60 => success
	st, addr := testServerWithDeps(t, func(d *game.Deps) { d.Cards = fusionCatalog() })
	a, aID := dialLogin(t, addr, "fus_a", "FusA")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	// Seed inventory: one 700 and one 701 (same set).
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 700, Quantity: 1, Flag: domain.CardCursed})
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 701, Quantity: 1, Flag: domain.CardCursed})

	// Snapshot the pre-fusion set-5 total (starter grants may add copies).
	before := setTotal(t, st, uint(aID))

	// 5490: [i32 count]{i32 cardId}.
	req := testclient.NewW().I32(2).I32(700).I32(701).Bytes()
	_ = a.Send(3, testclient.OpFusionRequest, req)

	f, _, err := a.WaitFor(testclient.OpFusionResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FusionResult(5491): %v", err)
	}
	res, obtained, notObtained, recovered := parseFusion(f.Payload)
	if res != 0 {
		t.Fatalf("fusion result = %d, want 0", res)
	}
	if obtained == 0 {
		t.Fatalf("expected an obtained card on success, got obt=%d notObt=%d rec=%d",
			obtained, notObtained, recovered)
	}
	if obtained != 700 && obtained != 701 && obtained != 702 {
		t.Errorf("obtained %d not in set 5", obtained)
	}

	// Net change: two inputs consumed, one obtained granted => set total -1.
	time.Sleep(150 * time.Millisecond)
	after := setTotal(t, st, uint(aID))
	if after != before-1 {
		t.Errorf("set-5 total = %d after fusion, want %d (consumed 2, granted 1)", after, before-1)
	}
}

// setTotal returns the coach's total quantity of set-5 cards (700/701/702).
func setTotal(t *testing.T, st *store.Store, coachID uint) int16 {
	t.Helper()
	c, err := st.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	var total int16
	for _, card := range c.Inventory {
		if card.TemplateID == 700 || card.TemplateID == 701 || card.TemplateID == 702 {
			total += card.Quantity
		}
	}
	return total
}

// TestFusionFailureLeftovers: a failed roll (seeded) consumes the inputs and
// returns one as recovered leftovers.
func TestFusionFailureLeftovers(t *testing.T) {
	game.SeedFusionRand(1) // seed 1 -> first roll >= 60 => failure
	st, addr := testServerWithDeps(t, func(d *game.Deps) { d.Cards = fusionCatalog() })
	a, aID := dialLogin(t, addr, "fus_b", "FusB")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 700, Quantity: 1, Flag: domain.CardCursed})
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 701, Quantity: 1, Flag: domain.CardCursed})

	req := testclient.NewW().I32(2).I32(700).I32(701).Bytes()
	_ = a.Send(3, testclient.OpFusionRequest, req)

	f, _, err := a.WaitFor(testclient.OpFusionResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FusionResult(5491): %v", err)
	}
	res, obtained, _, recovered := parseFusion(f.Payload)
	if res != 0 {
		t.Fatalf("fusion result = %d, want 0", res)
	}
	if obtained != 0 {
		t.Errorf("failure should not obtain a card, got %d", obtained)
	}
	if recovered == 0 {
		t.Error("failure should recover leftovers")
	}
}

// TestFusionMixedSetsFails: cards from different sets can't fuse -> plain fail
// (all ids 0), inputs untouched.
func TestFusionMixedSetsFails(t *testing.T) {
	st, addr := testServerWithDeps(t, func(d *game.Deps) { d.Cards = fusionCatalog() })
	a, aID := dialLogin(t, addr, "fus_c", "FusC")
	reachWorld(t, a)
	a.DrainReceived(200 * time.Millisecond)

	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 700, Quantity: 1, Flag: domain.CardCursed})
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 900, Quantity: 1, Flag: domain.CardCursed})

	q700Before := ownedQty(t, st, uint(aID), 700)
	q900Before := ownedQty(t, st, uint(aID), 900)

	// 700 (set 5) + 900 (set 9) -> incompatible.
	req := testclient.NewW().I32(2).I32(700).I32(900).Bytes()
	_ = a.Send(3, testclient.OpFusionRequest, req)

	f, _, err := a.WaitFor(testclient.OpFusionResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no FusionResult(5491): %v", err)
	}
	res, obtained, notObtained, recovered := parseFusion(f.Payload)
	if res != 0 || obtained != 0 || notObtained != 0 || recovered != 0 {
		t.Fatalf("mixed-set fusion should be a plain fail (all 0), got res=%d o=%d n=%d r=%d",
			res, obtained, notObtained, recovered)
	}
	// Inputs untouched (no consume on an invalid recipe).
	time.Sleep(100 * time.Millisecond)
	if q := ownedQty(t, st, uint(aID), 700); q != q700Before {
		t.Errorf("card 700 qty changed on invalid fusion: %d -> %d", q700Before, q)
	}
	if q := ownedQty(t, st, uint(aID), 900); q != q900Before {
		t.Errorf("card 900 qty changed on invalid fusion: %d -> %d", q900Before, q)
	}
}

// ownedQty returns the coach's total quantity of one template.
func ownedQty(t *testing.T, st *store.Store, coachID uint, tmpl int32) int16 {
	t.Helper()
	c, err := st.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	var q int16
	for _, card := range c.Inventory {
		if card.TemplateID == tmpl {
			q += card.Quantity
		}
	}
	return q
}
