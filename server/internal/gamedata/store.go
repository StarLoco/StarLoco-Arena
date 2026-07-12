package gamedata

import (
	"fmt"
	"os"
	"path/filepath"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// Store bundles every lazily-loaded game-data repository the server needs.
// Construct one with NewStore at composition-root time (cmd/server/main.go)
// and inject it into whichever services/combat packages need lookups.
type Store struct {
	CoachCards        *Repository[CoachCardTemplate]
	FighterCards      *Repository[FighterCardTemplate]
	Spells            *Repository[SpellTemplate]
	Events            *Repository[EventTemplate]
	Summonings        *Repository[SummoningTemplate]
	StaticEffectAreas *Repository[StaticEffectAreaTemplate]

	// Maps lazily loads/caches per-mapID real map data (.amw chunk files +
	// the shared elements.ade catalog), see map.go and
	// docs/08-java-parity-roadmap.md Phase K. Distinct from the
	// Repository[T]-based fields above since a map's on-disk shape (a
	// per-mapID directory of multiple chunk files, not one flat .dat
	// file) doesn't fit the generic Repository pattern.
	Maps *MapStore

	// SpecialCells lazily loads/caches per-mapID special-battlefield-cell
	// layouts (the authored buff/damage tiles) from
	// data/maps/<id>/specialcells.json -- see specialcells.go.
	SpecialCells *SpecialCellStore
}

// NewStore builds a Store rooted at dataDir (the directory containing
// cards.dat, spells.dat, events.dat, summoning.dat, staticEffects.dat).
// No I/O happens until the first Get/All/Len call on one of the returned
// repositories (see docs/01-architecture.md §1.4.2).
func NewStore(dataDir string) *Store {
	// cards.dat backs both CoachCards and FighterCards; parseCardsFileOnce
	// ensures the file is only read/parsed once even though two
	// repositories depend on it.
	cardsOnce := newSharedLoader(func() (parser.CardsFile, error) {
		return loadCardsFile(dataDir)
	})

	coachCards := NewRepository(func() (map[int32]CoachCardTemplate, error) {
		cf, err := cardsOnce()
		if err != nil {
			return nil, err
		}
		out := make(map[int32]CoachCardTemplate, len(cf.CoachCards))
		for _, c := range cf.CoachCards {
			out[c.ID] = CoachCardTemplate{ID: c.ID, Type: c.Type, Value: c.Value, Set: c.Set}
		}
		return out, nil
	})

	fighterCards := NewRepository(func() (map[int32]FighterCardTemplate, error) {
		cf, err := cardsOnce()
		if err != nil {
			return nil, err
		}
		effectsByParent := groupEffectsByParent(cf.Effects, "FIGHTER_CARD")
		out := make(map[int32]FighterCardTemplate, len(cf.FighterCards))
		for _, c := range cf.FighterCards {
			all := effectsToDefs(effectsByParent[c.ID])
			use, equip := splitFighterCardEffects(all)
			out[c.ID] = FighterCardTemplate{
				ID:           c.ID,
				Type:         c.Type,
				Value:        c.Value,
				ScriptID:     c.ScriptID,
				SubType:      c.SubType,
				Effects:      all,
				UseEffects:   use,
				EquipEffects: equip,
			}
		}
		return out, nil
	})

	spells := NewRepository(func() (map[int32]SpellTemplate, error) {
		raw, err := readFile(dataDir, "spells.dat")
		if err != nil {
			return nil, err
		}
		sf, err := parser.ParseSpellsFile(raw)
		if err != nil {
			return nil, fmt.Errorf("gamedata: parse spells.dat: %w", err)
		}
		effectsByParent := groupEffectsByParent(sf.Effects, "SPELL")
		out := make(map[int32]SpellTemplate, len(sf.Spells))
		for _, s := range sf.Spells {
			// Normalize the raw range exactly like the reference client's
			// AbstractSpell constructor (AbstractSpell.java:54-55):
			//   rangeMin = max(0, min(rawMin, rawMax))
			//   rangeMax = max(0, max(rawMin, rawMax))
			// The real data sometimes stores these swapped/inverted -- e.g.
			// the Feca teleport (spell 140) has raw rangeMin=6, rangeMax=0,
			// which the client normalizes to rangeMin=0, rangeMax=6. Without
			// this, the server's range check (rangeMax stays 0 while
			// rangeMin is 6) makes the effective range empty and rejects
			// EVERY cast -- the reported "Feca teleport doesn't work" bug.
			rangeMin, rangeMax := normalizeSpellRange(s.RangeMin, s.RangeMax)
			out[s.ID] = SpellTemplate{
				ID:                        s.ID,
				ActionPointsCost:          s.ActionPointsCost,
				CastFrequencyMaxPerPlayer: s.CastFrequencyMaxPerPlayer,
				CastFrequencyMaxPerTurn:   s.CastFrequencyMaxPerTurn,
				CastFrequencyMinInterval:  s.CastFrequencyMinInterval,
				CastTestLineOfSight:       s.CastTestLineOfSight,
				CastOnlyLine:              s.CastOnlyLine,
				RangeMin:                  rangeMin,
				RangeMax:                  rangeMax,
				Price:                     s.Price,
				AiTargetID:                s.AiTargetID,
				NeedFreeCell:              s.NeedFreeCell,
				ScriptID:                  s.ScriptID,
				BreedID:                   s.BreedID,
				Criterion:                 s.Criterion,
				UseAutoDescription:        s.UseAutoDescription,
				Effects:                   effectsToDefs(effectsByParent[s.ID]),
			}
		}
		return out, nil
	})

	events := NewRepository(func() (map[int32]EventTemplate, error) {
		raw, err := readFile(dataDir, "events.dat")
		if err != nil {
			return nil, err
		}
		ef, err := parser.ParseEventsFile(raw)
		if err != nil {
			return nil, fmt.Errorf("gamedata: parse events.dat: %w", err)
		}
		effectsByParent := groupEffectsByParent(ef.Effects, "")
		out := make(map[int32]EventTemplate, len(ef.Events))
		for _, e := range ef.Events {
			out[e.ID] = EventTemplate{
				ID:                 e.ID,
				UseAutoDescription: e.UseAutoDescription,
				Effects:            effectsToDefs(effectsByParent[e.ID]),
			}
		}
		return out, nil
	})

	summonings := NewRepository(func() (map[int32]SummoningTemplate, error) {
		raw, err := readFile(dataDir, "summoning.dat")
		if err != nil {
			return nil, err
		}
		sr, err := parser.ParseSummoningFile(raw)
		if err != nil {
			return nil, fmt.Errorf("gamedata: parse summoning.dat: %w", err)
		}
		out := make(map[int32]SummoningTemplate, len(sr))
		for _, s := range sr {
			out[s.ID] = SummoningTemplate{ID: s.ID, HP: s.HP, AP: s.AP, MP: s.MP, Gfx: s.Gfx, SpellID: s.SpellID}
		}
		return out, nil
	})

	staticEffectAreas := NewRepository(func() (map[int32]StaticEffectAreaTemplate, error) {
		raw, err := readFile(dataDir, "staticEffects.dat")
		if err != nil {
			return nil, err
		}
		sf, err := parser.ParseStaticEffectsFile(raw)
		if err != nil {
			return nil, fmt.Errorf("gamedata: parse staticEffects.dat: %w", err)
		}
		// parentType is padded with trailing spaces in the real data
		// (confirmed: "AREA              ", an authoring quirk) -- use
		// the same prefix-tolerant grouping as spells/cards/events.
		effectsByParent := groupEffectsByParent(sf.Effects, "AREA")
		out := make(map[int32]StaticEffectAreaTemplate, len(sf.Areas))
		for _, a := range sf.Areas {
			out[a.ID] = StaticEffectAreaTemplate{
				ID:                    a.ID,
				ScriptID:              a.ScriptID,
				AreaShapeID:           a.AreaShapeID,
				AreaParams:            a.AreaParams,
				ApplicationTriggers:   a.ApplicationTriggers,
				UnapplicationTriggers: a.UnapplicationTriggers,
				MaxExecutionCount:     a.MaxExecutionCount,
				ApplicationTargets:    a.ApplicationTargets,
				UnapplicationTargets:  a.UnapplicationTargets,
				TargetsToShow:         a.TargetsToShow,
				EffectAreaType:        a.EffectAreaType,
				DeactivationDelay:     a.DeactivationDelay,
				ApplicationCondition:  a.ApplicationCondition,
				Effects:               effectsToDefs(effectsByParent[a.ID]),
			}
		}
		return out, nil
	})

	// The special-cell store DERIVES per-map layouts from map art (via the
	// MapStore), so both share one MapStore instance.
	maps := NewMapStore(dataDir)

	return &Store{
		CoachCards:        coachCards,
		FighterCards:      fighterCards,
		Spells:            spells,
		Events:            events,
		Summonings:        summonings,
		StaticEffectAreas: staticEffectAreas,
		Maps:              maps,
		SpecialCells:      NewSpecialCellStore(dataDir, maps),
	}
}

// WarmUp forces every repository to load immediately. Used by the optional
// --warm-cache startup flag.
func (s *Store) WarmUp() error {
	for _, err := range []error{
		s.CoachCards.WarmUp(),
		s.FighterCards.WarmUp(),
		s.Spells.WarmUp(),
		s.Events.WarmUp(),
		s.Summonings.WarmUp(),
		s.StaticEffectAreas.WarmUp(),
	} {
		if err != nil {
			return err
		}
	}
	return nil
}

func readFile(dataDir, name string) ([]byte, error) {
	path := filepath.Join(dataDir, name)
	raw, err := os.ReadFile(path)
	if err != nil {
		return nil, fmt.Errorf("gamedata: read %s: %w", path, err)
	}
	if len(raw) == 0 {
		// Some legacy files (breeds.dat, items.dat) are empty/vestigial;
		// treat as "no records" rather than an error so a missing/empty
		// file doesn't take down repositories that don't need it.
		return raw, nil
	}
	return raw, nil
}

func loadCardsFile(dataDir string) (parser.CardsFile, error) {
	raw, err := readFile(dataDir, "cards.dat")
	if err != nil {
		return parser.CardsFile{}, err
	}
	if len(raw) == 0 {
		return parser.CardsFile{}, nil
	}
	cf, err := parser.ParseCardsFile(raw)
	if err != nil {
		return parser.CardsFile{}, fmt.Errorf("gamedata: parse cards.dat: %w", err)
	}
	return cf, nil
}

// newSharedLoader wraps a loader function so it is invoked at most once,
// with the result (or error) cached and replayed to every caller. Used to
// share a single cards.dat parse between the CoachCards and FighterCards
// repositories.
func newSharedLoader[T any](loadFn func() (T, error)) func() (T, error) {
	var (
		once   = new(bool)
		result T
		err    error
	)
	return func() (T, error) {
		if !*once {
			result, err = loadFn()
			*once = true
		}
		return result, err
	}
}

func groupEffectsByParent(effects []parser.EffectRaw, parentTypePrefix string) map[int32][]parser.EffectRaw {
	out := make(map[int32][]parser.EffectRaw)
	for _, e := range effects {
		if parentTypePrefix != "" && !hasPrefix(e.ParentType, parentTypePrefix) {
			continue
		}
		out[e.ParentID] = append(out[e.ParentID], e)
	}
	return out
}

// splitFighterCardEffects partitions a fighter card's effect list into its
// USE-time (actively castable) and EQUIP-time (passive stat) subsets by the
// effect's container type, mirroring AbstractFighterCard.addEffect
// (client/.../game/card/AbstractFighterCard.java:124-134): effects whose
// trimmed ParentType is "FIGHTER_CARD_USE" go to the use list; those whose
// trimmed ParentType is "FIGHTER_CARD_EQUIP" go to the equip list. The real
// cards.dat pads ParentType with trailing spaces (the same authoring quirk
// as staticEffects.dat's "AREA              "), so the compare trims first --
// exactly like the client's getContainerType().trim().equals(...).
//
// A container type that is neither (e.g. a bare "FIGHTER_CARD" in
// synthetic/test data, which the reference never emits) is treated as
// USE-time: it preserves the historical behavior (all effects castable) for
// data that doesn't carry the use/equip split, rather than silently
// dropping such effects from both lists.
func splitFighterCardEffects(effects []EffectDef) (use, equip []EffectDef) {
	for _, e := range effects {
		switch trimSpace(e.ParentType) {
		case "FIGHTER_CARD_EQUIP":
			equip = append(equip, e)
		default: // "FIGHTER_CARD_USE" and any un-suffixed/legacy value
			use = append(use, e)
		}
	}
	return use, equip
}

// trimSpace strips leading/trailing ASCII spaces (the only padding the real
// data uses); kept local to avoid pulling in the whole strings package for
// one call and to document the exact padding contract.
func trimSpace(s string) string {
	start := 0
	for start < len(s) && s[start] == ' ' {
		start++
	}
	end := len(s)
	for end > start && s[end-1] == ' ' {
		end--
	}
	return s[start:end]
}

func effectsToDefs(raws []parser.EffectRaw) []EffectDef {
	if len(raws) == 0 {
		return nil
	}
	out := make([]EffectDef, 0, len(raws))
	for _, e := range raws {
		out = append(out, EffectDef{
			ID:                     e.ID,
			ParentType:             e.ParentType,
			ParentID:               e.ParentID,
			Duration:               e.Duration,
			ActionID:               e.ActionID,
			IsCritical:             e.IsCritical,
			Params:                 e.Params,
			AreaShape:              e.AreaShape,
			AreaSize:               e.AreaSize,
			Targets:                e.Targets,
			TriggersAfter:          e.TriggersAfter,
			TriggersBefore:         e.TriggersBefore,
			AffectedByLocalisation: e.AffectedByLocalisation,
		})
	}
	return out
}

func hasPrefix(s, prefix string) bool {
	return len(s) >= len(prefix) && s[:len(prefix)] == prefix
}

// normalizeSpellRange ports the reference client's AbstractSpell
// constructor range normalization (AbstractSpell.java:54-55):
//
//	rangeMin = max(0, min(rawMin, rawMax))
//	rangeMax = max(0, max(rawMin, rawMax))
//
// i.e. it clamps both to >= 0 and guarantees rangeMin <= rangeMax by
// swapping them when the raw data stores them inverted (as some spells do,
// e.g. the Feca teleport spell 140's raw rangeMin=6/rangeMax=0). Applying
// this at load time keeps the server's range validation in lock-step with
// what the client considers castable.
func normalizeSpellRange(rawMin, rawMax byte) (rangeMin, rangeMax byte) {
	if rawMin <= rawMax {
		return rawMin, rawMax
	}
	return rawMax, rawMin
}
