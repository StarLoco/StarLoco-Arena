package combat

import "testing"

func TestNewFighterFromBreed_MatchesBreedTable(t *testing.T) {
	for _, breed := range AllPlayableBreeds() {
		stats, ok := GetBreedStats(breed)
		if !ok {
			t.Fatalf("breed %d: no stats found", breed)
		}

		f := NewFighterFromBreed(1, 1, breed, "Test", 0, 0)

		if got := f.Characteristic(HP); got != stats.BaseHP {
			t.Errorf("breed %s: HP = %d, want %d", stats.Name, got, stats.BaseHP)
		}
		if got := f.Characteristics[HP].Max; got != stats.BaseHP {
			t.Errorf("breed %s: HP.Max = %d, want %d", stats.Name, got, stats.BaseHP)
		}
		if got := f.Characteristic(AP); got != stats.BaseAP {
			t.Errorf("breed %s: AP = %d, want %d", stats.Name, got, stats.BaseAP)
		}
		if got := f.Characteristic(MP); got != stats.BaseMP {
			t.Errorf("breed %s: MP = %d, want %d", stats.Name, got, stats.BaseMP)
		}
		if got := f.Characteristic(Init); got != stats.BaseInit {
			t.Errorf("breed %s: INIT = %d, want %d", stats.Name, got, stats.BaseInit)
		}
		if got := f.Characteristic(CriticalRate); got != stats.BaseCriticalRate {
			t.Errorf("breed %s: CriticalRate = %d, want %d", stats.Name, got, stats.BaseCriticalRate)
		}
		if got := f.Characteristic(FumbleRate); got != stats.BaseFumbleRate {
			t.Errorf("breed %s: FumbleRate = %d, want %d", stats.Name, got, stats.BaseFumbleRate)
		}
	}
}

func TestGetBreedStats_UnknownBreed(t *testing.T) {
	if _, ok := GetBreedStats(200); ok {
		t.Fatalf("expected unknown breed 200 to return ok=false")
	}
}

func TestNewFighterFromBreed_UnknownBreedDoesNotPanic(t *testing.T) {
	f := NewFighterFromBreed(1, 1, 200, "Ghost", 0, 0)
	if f.Characteristic(HP) != 0 {
		t.Errorf("unknown breed: HP = %d, want 0", f.Characteristic(HP))
	}
}

func TestCharacteristicBounds_Clamp(t *testing.T) {
	f := NewFighterFromBreed(1, 1, BreedIop, "Test", 0, 0)

	// AP.Max was set to the breed's base AP (6 for Iop) by
	// NewFighterFromBreed; Characteristic.Add clamps to Max when Max > 0,
	// so +100 clamps to the fighter's actual max, not the absolute bound.
	f.AddCharacteristic(AP, 100)
	if got := f.Characteristic(AP); got != f.Characteristics[AP].Max {
		t.Errorf("AP after +100 = %d, want clamped to Max=%d", got, f.Characteristics[AP].Max)
	}

	f.AddCharacteristic(MP, -100)
	if got := f.Characteristic(MP); got != 0 {
		t.Errorf("MP after -100 = %d, want clamped to 0", got)
	}

	f.AddCharacteristic(ResFirePercent, 500)
	if got := f.Characteristic(ResFirePercent); got != 100 {
		t.Errorf("ResFirePercent after +500 = %d, want clamped to 100", got)
	}
}
