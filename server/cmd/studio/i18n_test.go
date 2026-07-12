package main

import "testing"

func TestParseProperties_Escapes(t *testing.T) {
	raw := []byte(
		"# a comment\n" +
			"content.3.3=Homing Arrow\n" +
			"content.5.8=Iop's Heart\n" +
			"content.7.100=Damage \\u0028neutral\\u0029\n" +
			"content.4.1=line one\\\n" +
			"  continued\n" +
			"notcontent.9.9=ignored\n" +
			"content.bad=skip\n",
	)
	m := parseProperties(raw)
	if m["3.3"] != "Homing Arrow" {
		t.Errorf("3.3 = %q", m["3.3"])
	}
	if m["5.8"] != "Iop's Heart" {
		t.Errorf("5.8 = %q", m["5.8"])
	}
	if m["7.100"] != "Damage (neutral)" {
		t.Errorf("unicode decode failed: %q", m["7.100"])
	}
	if m["4.1"] != "line onecontinued" {
		t.Errorf("continuation failed: %q", m["4.1"])
	}
	if _, ok := m["bad"]; ok {
		t.Error("non cat.id key should be skipped")
	}
	if _, ok := m["9.9"]; ok {
		t.Error("non-content key should be skipped")
	}
}

func TestI18n_RealClient_Names(t *testing.T) {
	a := newAppWithClient(t)
	a.lang = "en"

	// Known values verified by inspecting the real texts_en.properties.
	if got := a.name(catSpellName, 141); got != "High-Energy Shot" {
		t.Errorf("spell 141 = %q, want High-Energy Shot", got)
	}
	if got := a.name(catBreedName, 8); got != "Iop's Heart" {
		t.Errorf("breed 8 = %q, want Iop's Heart", got)
	}
	if got := a.name(catCoachCardName, 1); got != "Hooded Cape" {
		t.Errorf("coach card 1 = %q, want Hooded Cape", got)
	}
	// nameWithID formats "Name (id)".
	if got := a.nameWithID(catSpellName, 141); got != "High-Energy Shot (141)" {
		t.Errorf("nameWithID = %q", got)
	}
	// Unknown id falls back to the bare id.
	if got := a.nameWithID(catSpellName, 999999); got != "999999" {
		t.Errorf("unknown fallback = %q", got)
	}
}

func TestI18n_RealClient_FrenchDiffers(t *testing.T) {
	a := newAppWithClient(t)
	a.lang = "en"
	en := a.name(catBreedName, 8)
	a.SetLanguage("fr")
	fr := a.name(catBreedName, 8)
	if en == "" || fr == "" {
		t.Skip("breed 8 missing in one language")
	}
	// The 12 breed names are translated, so EN and FR should differ.
	if en == fr {
		t.Errorf("expected EN/FR breed names to differ, both = %q", en)
	}
}

func TestGetNames_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	a.lang = "en"
	nb := a.GetNames()
	if !nb.Available {
		t.Fatal("expected names available with real client")
	}
	if len(nb.Spells) == 0 || len(nb.Breeds) != 12 {
		t.Errorf("spells=%d breeds=%d (want breeds=12)", len(nb.Spells), len(nb.Breeds))
	}
	if nb.Spells["141"] != "High-Energy Shot" {
		t.Errorf("names.Spells[141] = %q", nb.Spells["141"])
	}
}

func TestGetIcon_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	// Spell 141 has an icon (confirmed gui/spells/icons/141.png exists).
	res := a.GetIcon("spell", 141)
	if !res.Found || res.URL == "" {
		t.Fatalf("expected spell 141 icon, found=%v", res.Found)
	}
	if len(res.URL) < 30 || res.URL[:22] != "data:image/png;base64," {
		t.Errorf("bad icon data url prefix: %.30s", res.URL)
	}
	// A wildly out-of-range id has no icon -> Found=false, and the miss is cached.
	if a.GetIcon("spell", 88888).Found {
		t.Error("expected no icon for bogus id")
	}
	// Unknown kind -> no icon.
	if a.GetIcon("nope", 1).Found {
		t.Error("expected no icon for unknown kind")
	}
}

func TestGetLanguage_Default(t *testing.T) {
	a := &App{}
	ls := a.GetLanguage()
	if ls.Current != "en" {
		t.Errorf("default language = %q, want en", ls.Current)
	}
	if len(ls.Options) < 2 {
		t.Errorf("expected >=2 language options, got %d", len(ls.Options))
	}
}
