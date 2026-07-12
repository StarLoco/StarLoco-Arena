package combat

import (
	"sort"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements the per-round EVENT CARD system (the wiki's "Round"
// mechanic: "At the start of each round a random event card which affects
// the whole round is drawn"). It is server-authoritative, matching the
// decompiled client exactly:
//
//   - The server owns event selection. NEW_TABLE_TURN_BEGIN (8100) carries
//     a single `int eventId` (buildNewTableTurnBegin); the client resolves
//     it via AbstractEventManager.getAbstractEventFromId() PURELY to display
//     the card art/description (NewTableTurnAction.run -> Fight.addEvent ->
//     UI property "fight.eventCards"). The client does NOT apply the event's
//     effects -- it only shows the card.
//   - The server draws the event and APPLIES its effects to the fighters,
//     then emits the concrete effect FightActions the client renders.
//
// # Draw model
//
// The reference deck primitive is AbstractEventManager.newShufflizedEvents()
// -- a Collections.shuffle() of ALL loaded events, with no weight/probability
// field anywhere in the data. So this draws from a shuffled deck of every
// event id, dealing them out one per table-turn and reshuffling once the
// deck is exhausted (so every event appears once before any repeats, and the
// order is fight-specific via the fight's own RNG -- deterministic under
// SetRNGSeed for tests).
//
// # Effect targeting
//
// Every event effect in the real events.dat carries areaShape=32767
// (AreaEmpty) -- the reference's "no cell-area; the target set is the whole
// round" sentinel (an event like "+5 damage to all" / "poison everyone"
// affects every fighter, per the wiki). AreaEmpty resolves to ZERO cells in
// the normal cast path (area.go's IsPointInside), so events must NOT go
// through cell-area target resolution: applyEventEffects instead applies each
// event effect directly to EVERY living fighter. Effects with a real
// Duration (all events are duration=[1,0] = one table-turn) are duration-
// tracked exactly like a spell buff/debuff, so the event's effect lasts the
// round and auto-reverts at the next table-turn (duration.go), matching the
// wiki's "affects the whole round".

// selectEventID deals the next event id from the shuffled deck for a new
// table-turn (reshuffling once exhausted), WITHOUT applying its effects.
// Returns 0 (no event) when no event data is loaded -- the mechanic is then
// inert, exactly reproducing the pre-event behavior. Selection is split from
// application (applyDrawnEvent) so the caller can broadcast
// NEW_TABLE_TURN_BEGIN with the drawn id FIRST (the client must instantiate
// the event card before the effect actions that reference the round arrive),
// then apply the effects.
func (f *Fight) selectEventID() int32 {
	if f.data == nil || f.data.Events == nil {
		return 0
	}
	if f.eventDeck == nil {
		f.eventDeck = f.buildEventDeck()
	}
	if len(f.eventDeck) == 0 {
		return 0
	}
	// Deal the next card; reshuffle when the deck is exhausted so every
	// event is seen once per cycle (mirrors re-calling newShufflizedEvents).
	if f.eventDeckPos >= len(f.eventDeck) {
		f.shuffleEventDeck()
		f.eventDeckPos = 0
	}
	eventID := f.eventDeck[f.eventDeckPos]
	f.eventDeckPos++
	return eventID
}

// applyDrawnEvent applies the effects of the event with the given id (from
// selectEventID) to all living fighters. No-op for id 0 or an unknown id.
// Called AFTER NEW_TABLE_TURN_BEGIN has been broadcast.
func (f *Fight) applyDrawnEvent(eventID int32, triggeringActionID int32) {
	if eventID == 0 || f.data == nil || f.data.Events == nil {
		return
	}
	event, ok := f.data.Events.Get(eventID)
	if !ok {
		return
	}
	f.applyEventEffects(event, triggeringActionID)
}

// buildEventDeck collects every event id from gamedata.Events into a
// fight-local slice and shuffles it with the fight's RNG (so the draw order
// is deterministic under SetRNGSeed). Repository.All()'s iteration order
// isn't guaranteed, so the ids are sorted first to make the pre-shuffle
// order stable, keeping seeded tests reproducible.
func (f *Fight) buildEventDeck() []int32 {
	all := f.data.Events.All()
	deck := make([]int32, 0, len(all))
	for _, e := range all {
		deck = append(deck, e.ID)
	}
	sort.Slice(deck, func(i, j int) bool { return deck[i] < deck[j] })
	f.eventDeck = deck
	f.shuffleEventDeck()
	return f.eventDeck
}

// shuffleEventDeck Fisher-Yates-shuffles the event deck in place using the
// fight RNG (mirrors Collections.shuffle in newShufflizedEvents).
func (f *Fight) shuffleEventDeck() {
	for i := len(f.eventDeck) - 1; i > 0; i-- {
		j := f.rng.Intn(i + 1)
		f.eventDeck[i], f.eventDeck[j] = f.eventDeck[j], f.eventDeck[i]
	}
}

// applyEventEffects runs each of an event's effects against EVERY living
// fighter (the event "affects the whole round"). Each effect is applied with
// the fighter itself as BOTH caster and target -- an event has no fighter
// source, and the effect is effectively self-applied to each fighter (a
// "+5 damage" event buffs each fighter using its own characteristics; a
// "10 damage to all" event hits each fighter). Using the fighter as caster
// (rather than nil) means the stat handlers that read caster characteristics
// -- ComputeHeal/ComputeHPLoss's Dmg/Res, etc. -- work without special
// nil-caster casing, and self==target correctly skips the caster!=target
// rebound/opponent checks (an event damage isn't a "hit" that rebounds).
// A triggered event effect (with a before/after listen-set) is deferred onto
// the fighter like any reactive effect. Broadcasts each application.
func (f *Fight) applyEventEffects(event gamedata.EventTemplate, triggeringActionID int32) {
	for _, eff := range event.Effects {
		def, ok := LookupRunningEffect(eff.ActionID)
		if !ok {
			f.logger.Debug().
				Int32("event_id", event.ID).Int32("action_id", eff.ActionID).
				Msg("combat: event effect has unresolved actionID, skipping")
			continue
		}
		// Snapshot the living fighters first: an effect could kill a fighter
		// (Timeline.RemoveFighter mutates the order) mid-iteration.
		living := f.livingFighters()
		for _, fighter := range living {
			if fighter.IsDead {
				continue
			}
			// Event/bonus cards do not affect summons (wiki: "Cards do not
			// work on summons"). A summon (Father != nil) is a fighter in
			// the timeline, so it's in the living set -- skip it explicitly
			// so the round card only touches real coach fighters.
			if fighter.Father != nil {
				continue
			}
			if effectMustBeDeferred(eff) {
				f.deferReactiveEffect(fighter, fighter, def, eff)
				continue
			}
			f.applyRunningEffect(fighter, fighter, def, eff, triggeringActionID)
		}
	}
}

// livingFighters returns a snapshot slice of every fighter currently alive,
// safe to iterate while effect application mutates the timeline.
func (f *Fight) livingFighters() []*Fighter {
	order := f.Timeline.Order()
	out := make([]*Fighter, 0, len(order))
	for _, fr := range order {
		if !fr.IsDead {
			out = append(out, fr)
		}
	}
	return out
}
