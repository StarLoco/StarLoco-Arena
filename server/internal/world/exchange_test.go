package world

import "testing"

func TestExchangeManagerStartAndGet(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	got, ok := m.Get(e.ID)
	if !ok || got != e {
		t.Fatalf("Get(%d) = %+v, %v", e.ID, got, ok)
	}
	if e.FromID != 1 || e.ToID != 2 {
		t.Errorf("exchange = %+v", e)
	}
}

func TestExchangeIsInitiator(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	if !e.IsInitiator(1) {
		t.Error("IsInitiator(1) should be true (the 'from' side)")
	}
	if e.IsInitiator(2) {
		t.Error("IsInitiator(2) should be false (the 'to' side)")
	}
}

func TestExchangeAddCardNewOffer(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	entry := e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 3}, 10)
	if entry.Quantity != 3 {
		t.Errorf("Quantity = %d, want 3", entry.Quantity)
	}

	from, to := e.Offers()
	if len(from) != 1 || len(to) != 0 {
		t.Fatalf("Offers = from:%v to:%v", from, to)
	}
}

func TestExchangeAddCardStacksExistingOffer(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 3}, 10)
	entry := e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 4}, 10)

	if entry.Quantity != 7 {
		t.Errorf("stacked Quantity = %d, want 7 (3+4)", entry.Quantity)
	}
}

func TestExchangeAddCardClampsToOwnedQuantity(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	entry := e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 999}, 10)
	if entry.Quantity != 10 {
		t.Errorf("Quantity = %d, want clamped to 10", entry.Quantity)
	}
}

func TestExchangeAddCardClampsNonPositiveToOne(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	entry := e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 0}, 10)
	if entry.Quantity != 1 {
		t.Errorf("Quantity = %d, want clamped to 1", entry.Quantity)
	}

	entry = e.AddCard(2, ExchangeCardEntry{CoachCardID: 200, TemplateID: 6, Quantity: -5}, 10)
	if entry.Quantity != 1 {
		t.Errorf("negative Quantity = %d, want clamped to 1", entry.Quantity)
	}
}

func TestExchangeRemoveCardReducesQuantity(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)
	e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 5}, 10)

	ok := e.RemoveCard(1, 5, 2)
	if !ok {
		t.Fatal("RemoveCard should succeed for an offered template")
	}

	from, _ := e.Offers()
	if len(from) != 1 || from[0].Quantity != 3 {
		t.Errorf("after removing 2 of 5, offers = %v, want quantity 3", from)
	}
}

func TestExchangeRemoveCardFullyRemovesWhenQuantityHitsZero(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)
	e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 3}, 10)

	e.RemoveCard(1, 5, 3)

	from, _ := e.Offers()
	if len(from) != 0 {
		t.Errorf("offer should be fully removed, got %v", from)
	}
}

func TestExchangeRemoveCardUnknownTemplateFails(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	if e.RemoveCard(1, 999, 1) {
		t.Error("RemoveCard should fail for a template never offered")
	}
}

func TestExchangeSetReadyTogglesAndRequiresBothSides(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	isFromSide, bothReady := e.SetReady(1)
	if !isFromSide {
		t.Error("SetReady(1) should report isFromSide=true")
	}
	if bothReady {
		t.Error("should not be ready with only one side confirmed")
	}

	isFromSide, bothReady = e.SetReady(2)
	if isFromSide {
		t.Error("SetReady(2) should report isFromSide=false")
	}
	if !bothReady {
		t.Error("should be ready once both sides confirm")
	}
}

func TestExchangeSetReadyIsAToggle(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	_, bothReady := e.SetReady(1)
	if bothReady {
		t.Fatal("unexpected both-ready with only one side set")
	}
	// Toggling the same side back off, then on again.
	e.SetReady(1)
	_, bothReady = e.SetReady(1)
	if bothReady {
		t.Error("toggling should require an explicit second SetReady call from the other side")
	}
}

func TestExchangeAddCardResetsReadyState(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)
	e.SetReady(1)
	e.SetReady(2)

	e.AddCard(1, ExchangeCardEntry{CoachCardID: 100, TemplateID: 5, Quantity: 1}, 10)

	// After modifying the offer, both-ready must be reset, requiring
	// fresh confirmation.
	_, bothReady := e.SetReady(1)
	if bothReady {
		t.Error("ready state should have been reset by AddCard")
	}
}

func TestExchangeManagerGetByCoach(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)

	got, ok := m.GetByCoach(1)
	if !ok || got.ID != e.ID {
		t.Errorf("GetByCoach(1) = %+v, %v", got, ok)
	}
	got, ok = m.GetByCoach(2)
	if !ok || got.ID != e.ID {
		t.Errorf("GetByCoach(2) = %+v, %v", got, ok)
	}
}

func TestExchangeManagerRemove(t *testing.T) {
	m := NewExchangeManager()
	e := m.Start(1, 2)
	m.Remove(e.ID)

	if _, ok := m.Get(e.ID); ok {
		t.Error("Get should fail after Remove")
	}
}
