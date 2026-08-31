package game

// Rules holds the numbers this server INVENTED. The 2.70 client receives every
// one of them pre-computed, so it cannot arbitrate any of them - there is no
// retail-correct value to recover, only a value we chose. They are gathered here,
// and surfaced in config.yaml, so an operator can tune them without patching Go
// and so nobody mistakes them for protocol facts.
//
// Anything derived from the client (wire layouts, card stats, tournament defs)
// does NOT belong here - that is data, and data wins over preference.
type Rules struct {
	// BaseXPPerFight is the XP a fight awards before morale and set bonuses.
	// Retail's value is unknowable: the client is handed the final figure.
	BaseXPPerFight int32
	// StandingWin / StandingLoss are the evolution-standing deltas applied after
	// a ranked fight. The client renders a level derived from standing, never the
	// delta, so again only the result is observable.
	StandingWin  int32
	StandingLoss int32
	// MaxSocialListEntries caps the friend list and the ignore list. The client
	// carries the refusal (3216) but no limit, so retail enforced this
	// server-side with a number we cannot recover.
	MaxSocialListEntries int
}

// DefaultRules returns the values the server used before any of this was
// configurable, so an operator who sets nothing sees no behaviour change.
func DefaultRules() Rules {
	return Rules{
		BaseXPPerFight:       100,
		StandingWin:          10,
		StandingLoss:         3,
		MaxSocialListEntries: 100,
	}
}

// rules returns the configured rules, falling back to the defaults when Deps was
// built without them (every test fixture that does not care about tuning).
//
// Nil-safe on purpose: some fight fixtures run with a nil Deps, and a rules
// lookup is not a reason for those to start panicking. A nil receiver is a
// legitimate Go method call, so this costs nothing.
func (d *Deps) rules() Rules {
	if d == nil || d.Rules == (Rules{}) {
		return DefaultRules()
	}
	return d.Rules
}
