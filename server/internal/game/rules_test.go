package game

import "testing"

// TestRulesFallBackToDefaults: every fixture in this package builds Deps without
// Rules, so the fallback is what almost all of the suite actually exercises. If
// it ever returned a zero Rules, XP would silently become 0 per fight and the
// social cap would refuse every add - both of which would look like unrelated
// bugs far from here.
func TestRulesFallBackToDefaults(t *testing.T) {
	var nilDeps *Deps
	if got := nilDeps.rules(); got != DefaultRules() {
		t.Errorf("nil Deps gave %+v, want the defaults %+v", got, DefaultRules())
	}
	if got := (&Deps{}).rules(); got != DefaultRules() {
		t.Errorf("zero Rules gave %+v, want the defaults %+v", got, DefaultRules())
	}
	d := DefaultRules()
	if d.BaseXPPerFight == 0 || d.StandingWin == 0 || d.MaxSocialListEntries == 0 {
		t.Fatalf("a default is zero (%+v) - zero means 'unset' in this design, so a "+
			"zero default can never be distinguished from an omitted config value", d)
	}
}

// TestRulesAreHonouredNotIgnored guards the wiring: a configured value must beat
// the default everywhere it is read. Without this, moving the constants into
// config would look done while every call site still used the old number.
func TestRulesAreHonouredNotIgnored(t *testing.T) {
	custom := Rules{
		BaseXPPerFight:       7,
		StandingWin:          77,
		StandingLoss:         5,
		MaxSocialListEntries: 3,
	}
	d := &Deps{Rules: custom}
	if got := d.rules().BaseXPPerFight; got != 7 {
		t.Errorf("BaseXPPerFight = %d, want the configured 7", got)
	}
	if got := d.standingForResult(true); got != 77 {
		t.Errorf("standingForResult(win) = %d, want the configured 77", got)
	}
	if got := d.standingForResult(false); got != 5 {
		t.Errorf("standingForResult(loss) = %d, want the configured 5", got)
	}
}
