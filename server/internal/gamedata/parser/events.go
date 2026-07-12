package parser

// EventRaw mirrors Event.java, see docs/04-game-data-format.md §4.4.
type EventRaw struct {
	ID                 int32
	UseAutoDescription bool
}

// EventsFile is the fully-parsed content of events.dat.
//
// NOTE: the legacy Java EventLoader parses each effect's fields but never
// calls Event.addEffect(), silently discarding them (a confirmed dead-code
// bug, see docs/04-game-data-format.md §4.4). ParseEventsFile fixes this by
// actually returning the effects so callers can attach them to their
// parent event by ParentID.
type EventsFile struct {
	Events  []EventRaw
	Effects []EffectRaw
}

// ParseEventsFile parses the full contents of events.dat.
func ParseEventsFile(data []byte) (EventsFile, error) {
	r := NewReader(data)
	var out EventsFile

	eventCount := int(r.Int32())
	out.Events = make([]EventRaw, 0, eventCount)
	for i := 0; i < eventCount; i++ {
		out.Events = append(out.Events, EventRaw{
			ID:                 r.Int32(),
			UseAutoDescription: r.Bool(),
		})
	}

	effectCount := int(r.Int32())
	out.Effects = make([]EffectRaw, 0, effectCount)
	for i := 0; i < effectCount; i++ {
		out.Effects = append(out.Effects, ReadEffect(r))
	}

	if err := r.Err(); err != nil {
		return EventsFile{}, err
	}
	return out, nil
}
