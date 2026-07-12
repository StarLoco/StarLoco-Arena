package parser

// SummoningRaw mirrors Summoning.java, see docs/04-game-data-format.md §4.6.
type SummoningRaw struct {
	ID      int32
	HP      int32
	AP      int32
	MP      int32
	Gfx     int32
	SpellID int32
}

// ParseSummoningFile parses the full contents of summoning.dat.
func ParseSummoningFile(data []byte) ([]SummoningRaw, error) {
	r := NewReader(data)

	count := int(r.Int32())
	out := make([]SummoningRaw, 0, count)
	for i := 0; i < count; i++ {
		out = append(out, SummoningRaw{
			ID:      r.Int32(),
			HP:      r.Int32(),
			AP:      r.Int32(),
			MP:      r.Int32(),
			Gfx:     r.Int32(),
			SpellID: r.Int32(),
		})
	}

	if err := r.Err(); err != nil {
		return nil, err
	}
	return out, nil
}
