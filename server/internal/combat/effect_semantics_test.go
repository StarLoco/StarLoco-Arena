package combat

import "testing"

// TestEffectSemantics_Exhaustive asserts every actionID the engine models has a
// semantic entry, so the studio's effect decoder can never regress to showing a
// bare "action N" for a known effect.
func TestEffectSemantics_Exhaustive(t *testing.T) {
	sem := EffectSemantics()
	if len(sem) != len(runningEffectTable) {
		t.Fatalf("EffectSemantics len = %d, want %d (one per registry entry)", len(sem), len(runningEffectTable))
	}
	seen := map[int32]bool{}
	for _, s := range sem {
		if _, ok := runningEffectTable[s.ActionID]; !ok {
			t.Errorf("semantic for actionID %d has no registry entry", s.ActionID)
		}
		if s.Kind == "" || s.Verb == "" {
			t.Errorf("actionID %d: empty kind/verb", s.ActionID)
		}
		if s.Polarity != "good" && s.Polarity != "bad" && s.Polarity != "neutral" {
			t.Errorf("actionID %d: bad polarity %q", s.ActionID, s.Polarity)
		}
		seen[s.ActionID] = true
	}
	for id := range runningEffectTable {
		if !seen[id] {
			t.Errorf("registry actionID %d missing from EffectSemantics", id)
		}
	}
}

// TestEffectSemantics_KnownSamples pins a few high-signal effects to their
// expected human decode, so a wrong verb/stat/element is caught.
func TestEffectSemantics_KnownSamples(t *testing.T) {
	byID := map[int32]EffectSemantic{}
	for _, s := range EffectSemantics() {
		byID[s.ActionID] = s
	}
	cases := []struct {
		id                        int32
		kind, verb, stat, element string
		polarity                  string
	}{
		{2, "hp_loss", "deals", "", "fire", "bad"},
		{63, "death", "instantly kills", "", "", "bad"},
		{13, "charac_buff", "boosts", "AP", "", "good"},
		{20, "charac_loss", "removes", "MP", "", "bad"},
		{69, "hp_gain", "restores", "", "", "good"},
		{10, "hp_leech", "steals", "", "wind", "bad"},
		{67, "summon", "summons", "", "", "good"},
	}
	for _, c := range cases {
		s, ok := byID[c.id]
		if !ok {
			t.Errorf("actionID %d not present", c.id)
			continue
		}
		if s.Kind != c.kind || s.Verb != c.verb || s.Stat != c.stat || s.Element != c.element || s.Polarity != c.polarity {
			t.Errorf("actionID %d = %+v, want kind=%q verb=%q stat=%q element=%q polarity=%q",
				c.id, s, c.kind, c.verb, c.stat, c.element, c.polarity)
		}
	}
}
