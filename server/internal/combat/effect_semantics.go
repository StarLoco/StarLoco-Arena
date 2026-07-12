package combat

// This file exposes the effect registry as human-readable semantics for
// tooling (the studio's effect decoder). It is the single source of truth: the
// UI turns raw EffectDef.ActionID/Params/AreaShape/Targets into game-tooltip
// sentences using exactly the same actionID -> kind/characteristic/element
// mapping the combat engine executes with (runningEffectTable). A test asserts
// this stays exhaustive so a new registry entry can never silently render as
// "action N".

// EffectSemantic describes one actionID in plain terms.
type EffectSemantic struct {
	ActionID int32  `json:"actionId"`
	Kind     string `json:"kind"`     // machine kind, e.g. "hp_loss", "charac_gain"
	Verb     string `json:"verb"`     // human verb, e.g. "deals", "steals", "grants"
	Stat     string `json:"stat"`     // affected stat label, e.g. "AP", "Fire resistance" ("" if none)
	Element  string `json:"element"`  // element label for damage/leech ("" if none)
	Polarity string `json:"polarity"` // "good" | "bad" | "neutral" (for coloring)
	Note     string `json:"note"`     // optional extra clause, e.g. "then ends the turn"
}

// characLabel returns a human, game-facing name for a characteristic.
func characLabel(c CharacteristicType) string {
	switch c {
	case HP:
		return "HP"
	case AP:
		return "AP"
	case MP:
		return "MP"
	case Init:
		return "Initiative"
	case Range:
		return "Range"
	case CriticalRate:
		return "Critical hit"
	case FumbleRate:
		return "Fumble"
	case NbSummons:
		return "Summons"
	case Heal:
		return "Heal power"
	case ResAPLoss:
		return "AP-loss resist"
	case ResMPLoss:
		return "MP-loss resist"
	case DmgRebound:
		return "Damage rebound"
	case ResInPercent:
		return "Resistance %"
	case DmgInPercent:
		return "Damage %"
	case ResPercent:
		return "Resistance %"
	case DmgPercent:
		return "Damage %"
	case ResFire, ResFirePercent:
		return "Fire resistance"
	case ResWater, ResWaterPercent:
		return "Water resistance"
	case ResWind, ResWindPercent:
		return "Air resistance"
	case ResEarth, ResEarthPercent:
		return "Earth resistance"
	case Res:
		return "Resistance"
	case DmgFire, DmgFirePercent:
		return "Fire damage"
	case DmgWater, DmgWaterPercent:
		return "Water damage"
	case DmgWind, DmgWindPercent:
		return "Air damage"
	case DmgEarth, DmgEarthPercent:
		return "Earth damage"
	case Dmg:
		return "Damage"
	default:
		return "stat"
	}
}

// isPercentCharac reports whether the characteristic is a percentage stat, so
// the UI can render "+10%" instead of "+10".
func isPercentCharac(c CharacteristicType) bool {
	switch c {
	case ResPercent, ResFirePercent, ResWaterPercent, ResWindPercent, ResEarthPercent,
		DmgPercent, DmgFirePercent, DmgWaterPercent, DmgWindPercent, DmgEarthPercent,
		ResInPercent, DmgInPercent:
		return true
	default:
		return false
	}
}

// kindInfo maps an EffectKind to its machine name, human verb, and polarity.
func kindInfo(k EffectKind) (name, verb, polarity string) {
	switch k {
	case EffectHPLoss:
		return "hp_loss", "deals", "bad"
	case EffectHPGain:
		return "hp_gain", "restores", "good"
	case EffectHPLeech:
		return "hp_leech", "steals", "bad"
	case EffectHPDebuff:
		return "hp_debuff", "reduces max HP by", "bad"
	case EffectCharacGain:
		return "charac_gain", "grants", "good"
	case EffectCharacLoss:
		return "charac_loss", "removes", "bad"
	case EffectCharacBuff:
		return "charac_buff", "boosts", "good"
	case EffectCharacDebuff:
		return "charac_debuff", "weakens", "bad"
	case EffectCharacLeech:
		return "charac_leech", "steals", "bad"
	case EffectCharacPoison:
		return "charac_poison", "poisons for", "bad"
	case EffectAPUse:
		return "ap_use", "spends", "neutral"
	case EffectMPUse:
		return "mp_use", "spends", "neutral"
	case EffectPush:
		return "push", "pushes back", "bad"
	case EffectPull:
		return "pull", "pulls in", "bad"
	case EffectTeleport:
		return "teleport", "teleports", "neutral"
	case EffectExchangePosition:
		return "swap", "swaps positions with", "neutral"
	case EffectRoot:
		return "root", "roots", "bad"
	case EffectStabilize:
		return "stabilize", "stabilizes", "bad"
	case EffectPetrified:
		return "petrify", "petrifies", "bad"
	case EffectSetInvisible:
		return "invisible", "turns invisible", "good"
	case EffectSetVisible:
		return "visible", "reveals", "neutral"
	case EffectSummon:
		return "summon", "summons", "good"
	case EffectDeath:
		return "death", "instantly kills", "bad"
	case EffectAutomaticEndTurn:
		return "end_turn", "ends the turn", "bad"
	case EffectCardEquipped:
		return "card_equipped", "equips a card", "neutral"
	case EffectSetArea:
		return "set_area", "places a ground trap", "neutral"
	case EffectCarry:
		return "carry", "carries", "neutral"
	case EffectThrow:
		return "throw", "throws", "neutral"
	case EffectChangeLook:
		return "change_look", "changes appearance", "neutral"
	case EffectDecurse:
		return "decurse", "removes curses", "good"
	case EffectAttractSight:
		return "attract_sight", "draws sight", "neutral"
	case EffectSummonDouble:
		return "summon_double", "summons a double", "good"
	case EffectSpellRebound:
		return "spell_rebound", "reflects spells", "good"
	case EffectStrikeBack:
		return "strike_back", "strikes back for", "good"
	case EffectSummonMirror:
		return "summon_mirror", "summons a mirror", "good"
	case EffectAdaptLook:
		return "adapt_look", "adapts appearance", "neutral"
	case EffectStateApply:
		return "state_apply", "applies a state", "neutral"
	case EffectRapprochement:
		return "rapprochement", "closes in on", "neutral"
	case EffectUnhandled:
		return "unhandled", "triggers", "neutral"
	default:
		return "unknown", "triggers", "neutral"
	}
}

// EffectSemantics returns the full actionID -> semantic table, so the studio
// can render effects in plain language. Every actionID modeled by the combat
// engine (runningEffectTable) is included.
func EffectSemantics() []EffectSemantic {
	out := make([]EffectSemantic, 0, len(runningEffectTable))
	for id, def := range runningEffectTable {
		name, verb, polarity := kindInfo(def.Kind)
		s := EffectSemantic{ActionID: id, Kind: name, Verb: verb, Polarity: polarity}
		// Stat label: characteristic-based kinds carry a Charc.
		switch def.Kind {
		case EffectCharacGain, EffectCharacLoss, EffectCharacBuff, EffectCharacDebuff,
			EffectCharacLeech, EffectCharacPoison:
			s.Stat = characLabel(def.Charc)
			if isPercentCharac(def.Charc) {
				s.Note = "percent"
			}
		}
		// Element label for HP damage/leech/poison variants.
		switch def.Kind {
		case EffectHPLoss, EffectHPLeech, EffectCharacPoison:
			s.Element = def.Elem.String()
		}
		out = append(out, s)
	}
	return out
}
