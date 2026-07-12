package parser

// CoachCardRaw mirrors CoachCardTemplate.java, see
// docs/04-game-data-format.md §4.2 section 1.
type CoachCardRaw struct {
	ID    int32
	Type  int32
	Value int32
	Set   int32
}

// FighterCardRaw mirrors FighterCardTemplate.java / WeaponFighterCardTemplate.java,
// see docs/04-game-data-format.md §4.2 section 2.
//
// The reference loader reads a number of extra fields between Type and Value
// (and between Value and ScriptID) that the combat engine never uses --
// weapon-only leftovers and flags. Those are preserved verbatim in Reserved*
// fields ONLY so byte-exact re-encoders (cmd/studio/encode) can reproduce
// them; the server ignores them entirely.
type FighterCardRaw struct {
	ID       int32
	Type     byte
	Value    int32
	ScriptID int32
	SubType  int32

	// Preserved raw bytes between Type and Value (read order:
	// byte, bool, int32, int32, bool, bool).
	ReservedByte1 byte
	ReservedBool1 bool
	ReservedI32A  int32
	ReservedI32B  int32
	ReservedBool2 bool
	ReservedBool3 bool
	// Preserved raw bools between Value and ScriptID.
	ReservedBool4 bool
	ReservedBool5 bool
}

// CardsFile is the fully-parsed content of cards.dat: coach cards, fighter
// cards, and the effects attached to fighter cards by ParentID.
//
// Unlike the legacy Java loaders (CoachCardLoader / FighterCardLoader /
// EffectLoader), which each re-open and re-scan the file from the start to
// reach their section, ParseCardsFile reads the whole file once,
// sequentially, and returns all three sections together.
type CardsFile struct {
	CoachCards   []CoachCardRaw
	FighterCards []FighterCardRaw
	Effects      []EffectRaw
}

// ParseCardsFile parses the full contents of cards.dat.
func ParseCardsFile(data []byte) (CardsFile, error) {
	r := NewReader(data)
	var out CardsFile

	coachCardCount := int(r.Int32())
	out.CoachCards = make([]CoachCardRaw, 0, coachCardCount)
	for i := 0; i < coachCardCount; i++ {
		out.CoachCards = append(out.CoachCards, CoachCardRaw{
			ID:    r.Int32(),
			Type:  r.Int32(),
			Value: r.Int32(),
			Set:   r.Int32(),
		})
	}

	fighterCardCount := int(r.Int32())
	out.FighterCards = make([]FighterCardRaw, 0, fighterCardCount)
	for i := 0; i < fighterCardCount; i++ {
		id := r.Int32()
		cardType := r.Byte()
		resByte1 := r.Byte() // unused by engine, preserved for re-encode
		resBool1 := r.Bool() // unused
		resI32A := r.Int32() // unused (weapon leftover)
		resI32B := r.Int32() // unused (weapon leftover)
		resBool2 := r.Bool() // unused
		resBool3 := r.Bool() // unused
		value := r.Int32()
		resBool4 := r.Bool() // unused
		resBool5 := r.Bool() // unused
		scriptID := r.Int32()
		subType := r.Int32()
		out.FighterCards = append(out.FighterCards, FighterCardRaw{
			ID:            id,
			Type:          cardType,
			Value:         value,
			ScriptID:      scriptID,
			SubType:       subType,
			ReservedByte1: resByte1,
			ReservedBool1: resBool1,
			ReservedI32A:  resI32A,
			ReservedI32B:  resI32B,
			ReservedBool2: resBool2,
			ReservedBool3: resBool3,
			ReservedBool4: resBool4,
			ReservedBool5: resBool5,
		})
	}

	effectCount := int(r.Int32())
	out.Effects = make([]EffectRaw, 0, effectCount)
	for i := 0; i < effectCount; i++ {
		out.Effects = append(out.Effects, ReadEffect(r))
	}

	if err := r.Err(); err != nil {
		return CardsFile{}, err
	}
	return out, nil
}
