package game

// events.go implements the per-round EVENT CARD: at the start of every table
// turn a card is drawn that affects the WHOLE arena for that round.
//
// The mechanic is server-authoritative. NEW_TABLE_TURN_BEGIN (8100) carries a
// single `int eventId`; the client resolves it (cw_1.eO().w(id) → tO
// "ClientEvent") purely to DISPLAY the card — jg_1 stores it and nothing in the
// client applies its effects. So the server draws the card, tells the client
// which one it drew, and then applies the effects itself.
//
// # Which cards are in the deck
//
// The 2.70 data ships 51 event records (type 230), but they are not one pool:
// the 2006 client's events.dat holds EXACTLY ids 1..27, so 1..27 is the base
// per-round deck and everything from 43 up was added later for PvE content —
// the creature cards (43..47, 67) and the "Démon des Minutes 3" boosts/debuffs
// (61..66) carry creature-scoped target masks and are scripted by challenges,
// and 48..59 are a second, far stronger god set (+3 AP, +100% resist, arena-wide
// invisibility) that would dominate a fight if it came up every few rounds.
// Drawing only 1..27 keeps the rotation to the cards designed for it: the 12
// breed-god cards (each buffs only its own breed, via the effect's breed target
// condition) and the 15 neutral arena cards.
//
// # The opening round
//
// Round 1 always draws event 14 ("Cloué au lit" / "Tied to the bed"), whose
// three effects are actions 94 + 127 + 128 — stabilised, anchored and
// intransposable. It does NOT stop anyone walking: it makes the whole arena
// immune to being pushed, pulled, carried or swapped for the opening round. (It
// used to freeze everyone solid here, because 127/128 were mis-mapped onto
// root/stabilise — see states.go and BUGS.md B-053.)

// roundEventDeckMaxID bounds the per-round deck to the base card set (see above).
const roundEventDeckMaxID int32 = 27

// firstRoundEventID is the card the opening round always draws.
const firstRoundEventID int32 = 14

// noRoundEvent is the eventId meaning "no card" — what the client reads as a nil
// event (jg_1 keeps null for 0) and what we send when no event data is loaded.
const noRoundEvent int32 = 0

// drawRoundEvent deals the next card for a new table turn WITHOUT applying it.
// Selection is split from application so the caller can broadcast
// NEW_TABLE_TURN_BEGIN with the drawn id FIRST — the client has to instantiate
// the card before the effect actions that belong to the round arrive — and only
// then apply the effects.
func (f *Fight) drawRoundEvent() int32 {
	if f.deps == nil || f.deps.Events.Len() == 0 {
		return noRoundEvent // no data: the mechanic is inert, exactly as before
	}
	if f.eventDeck == nil {
		f.buildEventDeck()
	}
	if len(f.eventDeck) == 0 {
		return noRoundEvent
	}
	if f.eventDeckPos >= len(f.eventDeck) {
		// Deck exhausted: reshuffle so every card is seen once per cycle. The
		// opening-round card is not forced again — that rule is fight-start only.
		f.shuffleEventDeck()
		f.eventDeckPos = 0
	}
	id := f.eventDeck[f.eventDeckPos]
	f.eventDeckPos++
	return id
}

// buildEventDeck fills the fight's deck with every drawable card, shuffled with
// the fight's own RNG. The ids are collected in ascending order first so the
// pre-shuffle order is stable and a seeded fight is reproducible.
func (f *Fight) buildEventDeck() {
	all := f.deps.Events.IDs()
	deck := make([]int32, 0, len(all))
	for _, id := range all {
		if id >= 1 && id <= roundEventDeckMaxID {
			deck = append(deck, id)
		}
	}
	f.eventDeck = deck
	f.eventDeckPos = 0
	f.shuffleEventDeck()
	// The opening round is fixed: put the first-round card at the top of the
	// deck rather than special-casing the draw, so it is still dealt exactly
	// once this cycle and cannot come up again on round 2.
	for i, id := range f.eventDeck {
		if id == firstRoundEventID {
			f.eventDeck[0], f.eventDeck[i] = f.eventDeck[i], f.eventDeck[0]
			break
		}
	}
}

// shuffleEventDeck Fisher-Yates-shuffles the deck in place with the fight RNG.
func (f *Fight) shuffleEventDeck() {
	rng := f.rngSource()
	for i := len(f.eventDeck) - 1; i > 0; i-- {
		j := rng.Intn(i + 1)
		f.eventDeck[i], f.eventDeck[j] = f.eventDeck[j], f.eventDeck[i]
	}
}

// applyRoundEvent applies the drawn card's effects to the whole arena. Called
// AFTER NEW_TABLE_TURN_BEGIN has been broadcast. A zero/unknown id is a no-op.
//
// Every effect is applied ONCE PER LIVING FIGHTER with that fighter as both
// caster and target. Self-casting is what makes the card work: an event has no
// caster, and the per-fighter handlers read the caster's own characteristics (a
// "+30% damage" card has to scale off each fighter's own stats), while
// self==target keeps the rebound/opponent guards out of the way.
//
// Each effect is still gated by its own target conditions, which is how the
// breed-god cards restrict themselves — "Dieu Iop" carries the Iop breed bit, so
// only Iops are buffed. Summons are NOT excluded: the shipped data targets them
// explicitly where intended (the Osamodas card buffs summons via the IS_SUMMONED
// condition), so the conditions decide, not a blanket rule.
func (f *Fight) applyRoundEvent(id int32) {
	if id == noRoundEvent || f.deps == nil {
		return
	}
	ev := f.deps.Events.Get(id)
	if ev == nil {
		return
	}
	for _, ef := range ev.Effects {
		// Snapshot per effect: an effect can kill (an event damages everyone),
		// and dying mutates the timeline.
		for _, ff := range f.livingFighters() {
			if !effectTargetAllowed(ff, ff, ef.Targets) {
				continue
			}
			f.applyPerTargetEffect(ff, ef, ff.Pos)
		}
	}
}

// beginTableTurn draws the round's card, broadcasts NEW_TABLE_TURN_BEGIN with
// its real id and applies it. Shared by the first table turn and every later
// one so both paths behave identically.
func (f *Fight) beginTableTurn() {
	f.tableEvent = f.drawRoundEvent()
	table, _ := buildNewTableTurn(f.nextActionUID(), uint8(f.tableTurn), f.tableEvent)
	f.broadcast(table)
	if f.deps != nil && f.deps.Log != nil {
		f.deps.Log.Info("table turn", "fight", f.ID, "turn", f.tableTurn, "eventCard", f.tableEvent)
	}
}

// applyTableTurnEffects resolves the round card's effects. It is DELIBERATELY
// separate from beginTableTurn and must run only after FIGHTER_TURN_BEGIN.
//
// A timed effect makes the client call `ZT.jt`, which anchors the expiry on
// "whose turn is it" (`cn_0.JG()` -> `aGT.dh()`). The client clears that anchor
// on every NEW_TABLE_TURN and only re-establishes it on the next
// FIGHTER_TURN_BEGIN, so anything sent in between throws
// `IllegalStateException: currentFighter() sans hasCurrentFighter()`. The action
// is caught and dropped, which leaves the buff registered on the fighter but
// never executed and never able to expire - a permanent inert entry in its list.
// Sending it after the turn begins costs one frame of latency and nothing else.
func (f *Fight) applyTableTurnEffects() {
	f.applyRoundEvent(f.tableEvent)
}
