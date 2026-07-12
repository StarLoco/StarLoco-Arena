package combat

import (
	"math/rand"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// EffectKind is the executor category an actionID resolves to, mirroring
// the concrete StaticRunningEffect subclasses referenced by
// RunningEffectConstants.java (see docs/05-combat-engine.md §5.4.1's
// priority-ordered catalog). Each RunningEffectDefinition table entry below
// pins one actionID to exactly one of these kinds plus (where relevant) a
// target characteristic/element, mirroring the Java constructor
// parameters exactly (e.g. id 21 RES_FIRE_GAIN = CharacGain(RES_FIRE)).
type EffectKind int

const (
	EffectHPLoss EffectKind = iota
	EffectHPGain
	EffectHPLeech
	EffectHPDebuff
	EffectCharacGain
	EffectCharacLoss
	EffectCharacBuff
	EffectCharacDebuff
	EffectCharacLeech
	EffectCharacPoison
	EffectAPUse
	EffectMPUse
	EffectPush
	EffectPull
	EffectTeleport
	EffectExchangePosition
	EffectRoot
	EffectStabilize
	EffectPetrified
	EffectSetInvisible
	EffectSetVisible
	EffectSummon
	EffectDeath
	EffectAutomaticEndTurn
	EffectCardEquipped
	// EffectSetArea implements actionID 66 (SET_EFFECT_AREA, Phase M --
	// docs/08-java-parity-roadmap.md): places a persistent ground-effect
	// area (trap/glyph) at the cast's target cell, see effectarea.go.
	EffectSetArea
	// Phase N (docs/08-java-parity-roadmap.md) -- the previously-deferred
	// effect kinds. These complete the RunningEffectConstants.java catalog
	// so that no spell whose effects reference these actionIDs silently
	// no-ops (the symptom that made "some spells don't launch"). See
	// effects.go's handlers for the per-kind semantics + documented
	// scope limits (CHANGE_LOOK/ADAPT_LOOK are cosmetic broadcast-only;
	// SPELL_REBOUND is single-bounce; STATE_APPLY expands a state bundle
	// via the state registry, empty by default -- see state.go).
	EffectCarry         // 58 CARRY
	EffectThrow         // 59 THROW
	EffectChangeLook    // 60 CHANGE_LOOK
	EffectDecurse       // 62 DECURSE
	EffectAttractSight  // 68 ATTRACT_SIGHT (turn sight toward cell)
	EffectSummonDouble  // 75 SUMMON_DOUBLE
	EffectSpellRebound  // 88 SPELL_REBOUND
	EffectStrikeBack    // 90 STRIKE_BACK
	EffectSummonMirror  // 97 SUMMON_MIRROR
	EffectAdaptLook     // 98 ADAPT_LOOK
	EffectStateApply    // 112 STATE_APPLY
	EffectRapprochement // 113 RAPPROCHEMENT (move caster toward target)
	EffectUnhandled     // known ID but not implemented (logged, no-op)
)

// runningEffectDef mirrors one RunningEffectConstants.java entry: which
// EffectKind runs, and (for characteristic-based kinds) which
// CharacteristicType it targets and which Element it's associated with (for
// HPLoss/HPLeech element variants).
type runningEffectDef struct {
	Kind  EffectKind
	Charc CharacteristicType
	Elem  Element
}

// runningEffectTable is the Go port of every RunningEffectConstants.java
// entry actually needed by the effect executor -- keyed by actionID (the
// same numeric ID that travels as EffectDef.ActionID from parsed
// spells.dat/cards.dat/events.dat records). IDs not present here fall back
// to EffectUnhandled (logged, no-op) rather than panicking. As of Phase N
// (docs/08-java-parity-roadmap.md) every effect ID in
// RunningEffectConstants.java has a mapping here -- the previously-deferred
// mechanics (carry/throw/decurse/rapprochement/attract-sight/summon-double/
// summon-mirror/change-look/adapt-look/spell-rebound/strike-back/
// state-apply) are now handled in effects.go (see the per-kind doc comments
// there for the documented scope of the cosmetic/data-limited ones). The
// only remaining catalog gap is ID 95, which has no constant in the
// reference (a genuine hole in the original enum).
var runningEffectTable = map[int32]runningEffectDef{
	1:   {Kind: EffectHPLoss, Elem: ElementPhysical},
	2:   {Kind: EffectHPLoss, Elem: ElementFire},
	3:   {Kind: EffectHPLoss, Elem: ElementEarth},
	4:   {Kind: EffectHPLoss, Elem: ElementWater},
	5:   {Kind: EffectHPLoss, Elem: ElementWind},
	6:   {Kind: EffectHPLeech, Elem: ElementPhysical},
	7:   {Kind: EffectHPLeech, Elem: ElementFire},
	8:   {Kind: EffectHPLeech, Elem: ElementEarth},
	9:   {Kind: EffectHPLeech, Elem: ElementWater},
	10:  {Kind: EffectHPLeech, Elem: ElementWind},
	11:  {Kind: EffectCharacBuff, Charc: HP},
	12:  {Kind: EffectHPDebuff},
	13:  {Kind: EffectCharacBuff, Charc: AP},
	14:  {Kind: EffectCharacDebuff, Charc: AP},
	15:  {Kind: EffectCharacGain, Charc: AP},
	16:  {Kind: EffectCharacLoss, Charc: AP}, // AP_LOSS: resistible via ResAPLoss, handled specially
	17:  {Kind: EffectCharacBuff, Charc: MP},
	18:  {Kind: EffectCharacDebuff, Charc: MP},
	19:  {Kind: EffectCharacGain, Charc: MP},
	20:  {Kind: EffectCharacLoss, Charc: MP}, // MP_LOSS: resistible via ResMPLoss
	21:  {Kind: EffectCharacGain, Charc: ResFire},
	22:  {Kind: EffectCharacLoss, Charc: ResFire},
	23:  {Kind: EffectCharacGain, Charc: ResEarth},
	24:  {Kind: EffectCharacLoss, Charc: ResEarth},
	25:  {Kind: EffectCharacGain, Charc: ResWater},
	26:  {Kind: EffectCharacLoss, Charc: ResWater},
	27:  {Kind: EffectCharacGain, Charc: ResWind},
	28:  {Kind: EffectCharacLoss, Charc: ResWind},
	29:  {Kind: EffectCharacGain, Charc: ResFirePercent},
	30:  {Kind: EffectCharacLoss, Charc: ResFirePercent},
	31:  {Kind: EffectCharacGain, Charc: ResEarthPercent},
	32:  {Kind: EffectCharacLoss, Charc: ResEarthPercent},
	33:  {Kind: EffectCharacGain, Charc: ResWaterPercent},
	34:  {Kind: EffectCharacLoss, Charc: ResWaterPercent},
	35:  {Kind: EffectCharacGain, Charc: ResWindPercent},
	36:  {Kind: EffectCharacLoss, Charc: ResWindPercent},
	37:  {Kind: EffectPush},
	38:  {Kind: EffectPull},
	39:  {Kind: EffectTeleport},
	40:  {Kind: EffectCharacGain, Charc: DmgFire},
	41:  {Kind: EffectCharacLoss, Charc: DmgFire},
	42:  {Kind: EffectCharacGain, Charc: DmgEarth},
	43:  {Kind: EffectCharacLoss, Charc: DmgEarth},
	44:  {Kind: EffectCharacGain, Charc: DmgWater},
	45:  {Kind: EffectCharacLoss, Charc: DmgWater},
	46:  {Kind: EffectCharacGain, Charc: DmgWind},
	47:  {Kind: EffectCharacLoss, Charc: DmgWind},
	48:  {Kind: EffectCharacGain, Charc: DmgFirePercent},
	49:  {Kind: EffectCharacLoss, Charc: DmgFirePercent},
	50:  {Kind: EffectCharacGain, Charc: DmgEarthPercent},
	51:  {Kind: EffectCharacLoss, Charc: DmgEarthPercent},
	52:  {Kind: EffectCharacGain, Charc: DmgWaterPercent},
	53:  {Kind: EffectCharacLoss, Charc: DmgWaterPercent},
	54:  {Kind: EffectCharacGain, Charc: DmgWindPercent},
	55:  {Kind: EffectCharacLoss, Charc: DmgWindPercent},
	56:  {Kind: EffectAutomaticEndTurn},
	57:  {Kind: EffectSetInvisible},
	58:  {Kind: EffectCarry},
	59:  {Kind: EffectThrow},
	60:  {Kind: EffectChangeLook},
	61:  {Kind: EffectCharacPoison, Elem: ElementPhysical},
	62:  {Kind: EffectDecurse},
	63:  {Kind: EffectDeath},
	64:  {Kind: EffectExchangePosition},
	65:  {Kind: EffectRoot},
	66:  {Kind: EffectSetArea},
	67:  {Kind: EffectSummon},
	68:  {Kind: EffectAttractSight},
	69:  {Kind: EffectHPGain},
	70:  {Kind: EffectCharacGain, Charc: CriticalRate},
	71:  {Kind: EffectCharacGain, Charc: FumbleRate},
	72:  {Kind: EffectCharacGain, Charc: Range},
	73:  {Kind: EffectCharacLoss, Charc: Range},
	74:  {Kind: EffectCharacGain, Charc: NbSummons},
	75:  {Kind: EffectSummonDouble},
	76:  {Kind: EffectCharacBuff, Charc: Init},
	77:  {Kind: EffectCharacDebuff, Charc: Init},
	78:  {Kind: EffectCharacGain, Charc: Heal},
	79:  {Kind: EffectCharacLoss, Charc: Heal},
	80:  {Kind: EffectCharacGain, Charc: ResInPercent},
	81:  {Kind: EffectCharacLoss, Charc: ResInPercent},
	82:  {Kind: EffectCharacGain, Charc: DmgInPercent},
	83:  {Kind: EffectCharacLoss, Charc: DmgInPercent},
	84:  {Kind: EffectSetVisible},
	85:  {Kind: EffectCharacLeech, Charc: AP},
	86:  {Kind: EffectCharacGain, Charc: ResAPLoss},
	87:  {Kind: EffectCharacGain, Charc: ResMPLoss},
	88:  {Kind: EffectSpellRebound},
	89:  {Kind: EffectCharacGain, Charc: DmgRebound},
	90:  {Kind: EffectStrikeBack},
	91:  {Kind: EffectAPUse},
	92:  {Kind: EffectMPUse},
	93:  {Kind: EffectCardEquipped},
	94:  {Kind: EffectStabilize},
	96:  {Kind: EffectPetrified},
	97:  {Kind: EffectSummonMirror},
	98:  {Kind: EffectAdaptLook},
	111: {Kind: EffectAutomaticEndTurn},
	112: {Kind: EffectStateApply},
	113: {Kind: EffectRapprochement},
}

// LookupRunningEffect resolves an actionID to its definition, or
// (zero, false) for an ID not (yet) modeled (see the doc-comment on
// runningEffectTable for what's intentionally deferred).
func LookupRunningEffect(actionID int32) (runningEffectDef, bool) {
	d, ok := runningEffectTable[actionID]
	return d, ok
}

// isDamageKind reports whether an EffectKind directly reduces a target's HP
// (used by the summon AI to tell an offensive spell from a pure debuff).
func isDamageKind(k EffectKind) bool {
	switch k {
	case EffectHPLoss, EffectHPLeech, EffectHPDebuff, EffectCharacPoison, EffectDeath:
		return true
	default:
		return false
	}
}

// isDebuffKind reports whether an EffectKind worsens a target's stats
// without dealing HP damage (AP/MP loss, characteristic loss/debuff, root,
// stabilize, petrify) -- the "hostile but non-damaging" effects that mark a
// harasser/kiter summon.
func isDebuffKind(k EffectKind) bool {
	switch k {
	case EffectCharacLoss, EffectCharacDebuff, EffectCharacLeech,
		EffectRoot, EffectStabilize, EffectPetrified, EffectAPUse, EffectMPUse:
		return true
	default:
		return false
	}
}

// isBuffKind reports whether an EffectKind improves the target's stats
// (used to recognize a self-buff summon like the Dial's SpellRebound).
func isBuffKind(k EffectKind) bool {
	switch k {
	case EffectHPGain, EffectCharacGain, EffectCharacBuff,
		EffectSpellRebound, EffectStrikeBack, EffectSetInvisible:
		return true
	default:
		return false
	}
}

// Target-condition bits from the client's FightTargetValidator (the values
// stored in EffectDef.Targets, a bitmask array). Only the two the summon AI
// needs are named here; see FightTargetValidator.java for the full set.
const (
	targetCondIsCaster int32 = 2 // CONDITION_IS_CASTER: target must be the caster (self)
	targetCondIsEnemy  int32 = 8 // CONDITION_IS_ENEMY
)

// effectTargetSideKind classifies an effect's intended recipient side for
// the summon AI, derived from its Targets bitmask + effect kind. Returns
// one of targetSelf / targetEnemy / targetNeutral.
type targetSide int

const (
	targetNeutral targetSide = iota
	targetSelf
	targetEnemy
)

// effectTargetSide reads an effect's FightTargetValidator condition bits
// (EffectDef.Targets) to decide whether it lands on the caster (self) or on
// an enemy. A CONDITION_IS_CASTER bit (and no enemy bit) means self; a
// CONDITION_IS_ENEMY bit means enemy. When the mask is ambiguous (e.g. the
// bare CONDITION_IN_AOE=1 that offensive area spells carry), the caller
// disambiguates via the effect KIND (damage/debuff -> enemy, buff -> self),
// which is what classifySummon does.
func effectTargetSide(e gamedata.EffectDef) targetSide {
	var caster, enemy bool
	for _, c := range e.Targets {
		if c&targetCondIsCaster != 0 {
			caster = true
		}
		if c&targetCondIsEnemy != 0 {
			enemy = true
		}
	}
	switch {
	case enemy:
		return targetEnemy
	case caster:
		return targetSelf
	default:
		return targetNeutral
	}
}

// rollBaseValue resolves an effect's magnitude from its Params, mirroring
// the "1 param = fixed value; 3 params = diceCount,diceFaces,modifier"
// convention documented in docs/05-combat-engine.md §5.6.
func rollBaseValue(params []float32, rng *rand.Rand) float64 {
	switch len(params) {
	case 0:
		return 0
	case 1:
		return float64(params[0])
	default:
		diceCount := int(params[0])
		diceFaces := int(params[1])
		modifier := float64(params[2])
		if diceCount <= 0 || diceFaces <= 0 {
			return modifier
		}
		total := 0
		for i := 0; i < diceCount; i++ {
			total += rng.Intn(diceFaces) + 1
		}
		return float64(total) + modifier
	}
}
