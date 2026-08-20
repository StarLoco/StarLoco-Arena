package gamedata

import "math/rand"

// EffectKind classifies a decoded spell effect by the combat mechanic the 2.70
// client will render for it. The classification is keyed by the effect's mh_2
// ActionID (RunningEffectConstants). It lets the game-package resolver dispatch
// every effect a practice/real spell can carry without duplicating the id map,
// and lets it recognise (and safely skip) the exotic state effects it does not
// yet model. Ids and French labels are transcribed verbatim from the client's
// mh_2.java; the mechanics are cross-checked against the v2.04b server's proven
// effect resolver (same data lineage; the 2.70 id set is a superset).
type EffectKind int

const (
	// KindUnsupported is every effect the resolver does not model yet — the
	// remaining exotic family (carry/throw, aura, look-change, dispel, drunk,
	// damage-transfer, …). These are skipped: the cast still animates and its
	// other effects still resolve, but this one is a no-op (documented, not
	// silently wrong). They need bespoke client-state RE + live verification.
	KindUnsupported EffectKind = iota

	KindDamage       // flat elemental HP loss (direct 1-5, "par sort" 130-134)
	KindLeech        // HP leech 6-10: damage the target, heal the caster the same
	KindHeal         // 69 "Soin"
	KindPercentHP    // 125 HP loss as a % of the target's MAX HP
	KindPoison       // 61 poison: a real per-round DoT (immediate first tick, then a fresh roll each table turn)
	KindScaledAP     // 151/156/158/160/162 neutral+elem damage scaled by caster AP
	KindScaledMP     // 152/157/159/161/163 neutral+elem damage scaled by caster MP
	KindInstantDeath // 63 "Mort instantanée"

	KindAPLoss  // 16 flat AP removal
	KindMPLoss  // 20 flat MP removal
	KindAPSteal // 85 AP steal (target loses, caster gains)
	KindMPSteal // 103 MP steal
	KindAPGain  // 15 direct AP gain (current)
	KindMPGain  // 19 direct MP gain (current)

	KindTeleport // 39 caster teleports to the targeted cell
	KindSwap     // 64 swap caster & target positions
	KindPush     // 37 push the target away from the caster
	KindPull     // 38 pull the target toward the caster

	// KindSummon spawns a creature the caster controls: 67 "Invoque une créature"
	// (from a type-300 template, params[0]=template id), 75 "Invoque un double"
	// (a clone of the caster, no template) and 97 "Invoque un mirroir" (a template
	// mirror). The client creates + renders the fighter itself from the effect;
	// the server allocates its id, models it, and plays its turns via the AI.
	KindSummon

	// KindState inflicts a status effect for a duration: root (65/127),
	// stabilise (94/128), invisibility (57), petrify (96) and immunity (95/124).
	// It renders like a buff (standard blob, Nx=duration) but the server enforces
	// the rule (block move/push/damage, skip turn) — see game/states.go.
	KindState

	// KindBuff is a timed characteristic buff/debuff/gain/loss (CharacBuff /
	// CharacGain / CharacDebuff / CharacLoss family). The resolver renders every
	// one (so the client shows the buff icon + timer for its Duration turns) and
	// fully models the RESOURCE buffs (AP/MP/HP/Range — see BuffResource).
	//
	// The pure-stat buffs are consumed too, and have been since the elemental
	// damage model landed: resistances (flat and %), damage (flat and %),
	// all-element res/damage %, AP/MP-loss resistance, damage rebound, heal
	// power, crit/fumble rate, block and dodge all feed the combat formulas.
	// What stays render-only is the genuinely inert set — initiative above all
	// (no shipped spell grants it, and nothing re-sorts the timeline mid-fight;
	// see ROADMAP §8.2) — plus a few exotic ids. This comment used to say the
	// whole stat family was unconsumed.
	KindBuff

	// KindTrap places a persistent ground-effect area (trap/glyph): action 66
	// "Pose un piège". params[0] is a type-210 StaticEffect template id whose
	// footprint + inner effects the trap replays when a fighter triggers it
	// (walks onto it, or starts a turn on it). The server owns the area, its
	// trigger detection and the inner-effect replay — see game/effectarea.go.
	KindTrap

	// KindDispel strips a fighter's enchantments: 62 "Désenvoûtement" removes its
	// active buffs (reverting their stat changes) and timed states.
	KindDispel

	// KindVisual is a client-side-only effect with no server mechanic — it is
	// broadcast so the client renders/animates it, but changes no fight state:
	// 60/98 look change, 139 "Redirection des dégâts (purement visuel)". (Drunk
	// 126 is now a tracked KindState so the canCastWhenDrunk criterion can read it.)
	KindVisual

	// KindSelfPush (153 "Est repoussé de sa cible") shoves the CASTER away from
	// its target - the client class `azw_0` is `na_2` (push 37) with `bWl`/`bWm`
	// swapped, down to the same stability guard, altitude rule and collision
	// damage. Like push it needs blob part 3 (the destination) or it NPEs.
	KindSelfPush

	// KindRevealInvisible (84 "Révéler l'invisible") strips invisibility from
	// every fighter in the area: the client walks the target's running effects
	// and expires each `co_0` (action 57), reverting its dodge/block swing and
	// re-showing the sprite (`aum`). The server must clear its own state too or
	// targeting/AI keep treating the fighter as hidden.
	KindRevealInvisible

	// KindSpellCooldown (140 "Diminution du cooldown d'un sort") makes the SOURCE
	// spell recastable in params[0] turns instead of its normal wait. The client
	// applies this to the spell named in blob part 4 (`aus_0` -> `gn_0.a(fv,…)`
	// -> `sH.a`), and that is the only per-spell cooldown mutator it has, so 8120
	// with this action IS the cooldown-update message.
	KindSpellCooldown

	// KindCurseBonusCells (150 "Inverse les effets des cases bonus") curses the
	// target cell so a bonus tile there harms instead of helps. Entirely
	// server-owned: the client's counterpart sets a flag (`yl_1.aX`) whose getter
	// `FI()` has ZERO callers, and its EffectArea execute body is empty.
	KindCurseBonusCells

	// KindSpellReturn (88 "Renvoi de sort") sends the next damaging spell aimed
	// at the fighter back at its caster. The client half is a TRIGGER buff
	// (`amv_1`) that re-points the incoming effect with `h(ajQ())`; the server
	// must redirect too, since it is the one that resolves damage.
	KindSpellReturn

	// KindCarry (58 "Porter quelqu'un") / KindThrow (59 "Jeter quelqu'un"): the
	// caster picks up a target fighter (stacked on its cell) and later throws it
	// to a target cell. Bidirectional carry links, ported from the v2.04b resolver.
	KindCarry
	KindThrow

	// KindAura (176 "Pose une aura"): a persistent ground-effect area like a trap,
	// but CENTERED ON THE CASTER (it follows the caster) and firing on turn-start
	// for fighters in its radius — see game/effectarea.go.
	KindAura

	// KindZoneMPLoss (177 "Perte de PM triggerée en zone"): a direct area effect
	// that drains params[0] MP from every fighter in the spell's zone centered on
	// the CASTER (the caster excluded).
	KindZoneMPLoss

	// KindZoneAPLoss (169 "Perte de points d'action triggerée en zone"): the AP
	// twin of KindZoneMPLoss, same shape, same mh_2 family.
	KindZoneAPLoss

	// KindZoneDamage (165 fire / 166 water / 167 air / 168 earth, "Perte de
	// points de vie <élément> triggerée en zone"): elemental HP loss to every
	// fighter in the spell's zone centred on the CASTER (caster excluded). One
	// mh_2 aez_1 class per element; the element comes from damageElement.
	KindZoneDamage

	// KindLineDamage (178-181 "Perte de PV <élément> en ligne entre deux
	// combattants"): elemental damage to every fighter in the axis-aligned
	// bounding box spanned by the caster and the target (both excluded).
	KindLineDamage

	// KindDamageTransfer (129 "Transfert de dommages"): links the target so a
	// percentage of the damage it later takes is redirected to the caster.
	KindDamageTransfer

	// KindRemoveEffect (149 "Retire un effet"): strips from the target every
	// running-effect whose source effectId equals params[0] (params[1] caps the
	// count, default all), reverting each exactly like an early expiry — the state
	// bit cleared, the buff's stat reverted, the aura area destroyed. The client's
	// dw_0 does the same on its side from the broadcast. Used by the Masqueraider
	// mask-switch spells (each bundles ~15 to strip the other masks' components).
	KindRemoveEffect
)

// effectKind maps an mh_2 ActionID to its EffectKind. Anything absent is
// KindUnsupported. Grouped to mirror mh_2.java.
var effectKind = func() map[int32]EffectKind {
	m := map[int32]EffectKind{}
	// Flat elemental damage: direct (1-5) + "par sort" (130-134).
	for _, id := range []int32{1, 2, 3, 4, 5, 130, 131, 132, 133, 134} {
		m[id] = KindDamage
	}
	// HP leech (6-10).
	for _, id := range []int32{6, 7, 8, 9, 10} {
		m[id] = KindLeech
	}
	// Damage scaled by remaining AP (151 neutral,156 fire,158 air,160 water,162 earth).
	for _, id := range []int32{151, 156, 158, 160, 162} {
		m[id] = KindScaledAP
	}
	// Damage scaled by remaining MP (152 neutral,157 fire,159 air,161 water,163 earth).
	for _, id := range []int32{152, 157, 159, 161, 163} {
		m[id] = KindScaledMP
	}
	m[69] = KindHeal
	m[125] = KindPercentHP
	m[61] = KindPoison
	m[63] = KindInstantDeath
	m[16] = KindAPLoss
	m[20] = KindMPLoss
	m[85] = KindAPSteal
	m[103] = KindMPSteal
	m[15] = KindAPGain
	m[19] = KindMPGain
	m[39] = KindTeleport
	m[64] = KindSwap
	m[37] = KindPush
	m[38] = KindPull
	m[67] = KindSummon // Invoque une créature
	m[75] = KindSummon // Invoque un double
	m[97] = KindSummon // Invoque un mirroir
	m[66] = KindTrap   // Pose un piège (persistent ground-effect area)
	m[62] = KindDispel // Désenvoûtement
	// Client-visual-only effects (rendered, no server mechanic).
	//
	// 68 "Tourne le regard vers la cellule ciblée" turns the target's sprite to
	// face the effect cell (`ez_0`: build a direction, `bWm.b(dir)`) and nothing
	// else. 170 "Aucun effet" is the null effect - `eo_1` does not even override
	// the execute hook, so its whole purpose is to make the client play the
	// authored animation. 171 "Devenir évanescent" sets state `deG`, whose ONLY
	// reader in the entire client swaps the fighter's material to a blue tint
	// (`ee_2` -> `vD.java:172-178`); the real Evanescence numbers are separate
	// rows (actions 80 + 164), both already resolved.
	for _, id := range []int32{60, 98, 139, 68, 170, 171} {
		m[id] = KindVisual
	}
	m[58] = KindCarry       // Porter quelqu'un
	m[59] = KindThrow       // Jeter quelqu'un
	m[176] = KindAura       // Pose une aura
	m[177] = KindZoneMPLoss // Perte de PM triggerée en zone
	m[169] = KindZoneAPLoss // Perte de points d'action triggeree en zone
	m[153] = KindSelfPush   // Est repoussé de sa cible
	m[84] = KindRevealInvisible
	m[140] = KindSpellCooldown
	m[150] = KindCurseBonusCells
	m[88] = KindSpellReturn
	// 172 "Bouger vers la cible adverse la plus proche" stays UNSUPPORTED on
	// purpose: its client implementation (`gM`) is `if (bWm instanceof gn_0)
	// ((gn_0)bWm).Qf();`, and `gn_0.Qf()` is an EMPTY method body declared once
	// with no override anywhere in the client - the same shape as the dead
	// `do_1.a(...)` found in B-109. Sending the effect provably does nothing. The
	// behaviour it names (a summon charging the nearest enemy) already exists
	// server-side in ai.go's aggressive AI, which is what actually drives the
	// three shipped summons that carry this row.
	// Zone-triggered ELEMENTAL HP loss: one mh_2 aez_1 class per element
	// (165 fire, 166 water, 167 air, 168 earth - see damageElement). Same shape
	// as 177/169: the spell's own zone, centred on the caster, caster excluded.
	for _, id := range []int32{165, 166, 167, 168} {
		m[id] = KindZoneDamage
	}
	m[129] = KindDamageTransfer // Transfert de dommages
	m[149] = KindRemoveEffect   // Retire un effet (targeted removal by effectId)
	// Line/box elemental damage between two fighters (178 fire, 179 water, 180
	// air, 181 earth — element from the mh_2 label, see damageElement).
	for _, id := range []int32{178, 179, 180, 181} {
		m[id] = KindLineDamage
	}
	// Status states: root, stabilise, invisibility, petrify, immunity, skip-turn,
	// drunk (126, tracked for the canCastWhenDrunk criterion) and the three
	// mutually-exclusive Masqueraider masks (173 class / 174 coward / 175 berzerk,
	// tracked for the canCastWhenMask* criteria — see states.go).
	for _, id := range []int32{65, 127, 94, 128, 57, 96, 95, 124, 56, 111, 126, 173, 174, 175} {
		m[id] = KindState
	}
	// Characteristic buff/debuff/gain/loss family (CharacBuff/Gain/Debuff/Loss).
	// Resource ones (AP/MP/HP/Range) are additionally modelled server-side; the
	// rest render as buff icons only. See BuffResource for the resource subset.
	buffIDs := []int32{
		11, 12, 13, 14, 17, 18, // HP/AP/MP boost & debuff
		21, 22, 23, 24, 25, 26, 27, 28, // elemental resistance +/-
		29, 30, 31, 32, 33, 34, 35, 36, // elemental resistance % +/-
		40, 41, 42, 43, 44, 45, 46, 47, // elemental damage +/-
		48, 49, 50, 51, 52, 53, 54, 55, // elemental damage % +/-
		70, 71, // crit / fumble rate
		72, 73, // range +/-
		74,     // summon count (NB_SUMMONS) — also mapped in resourceBuff, applied mechanically
		76, 77, // initiative +/-
		78, 79, // heal power +/-
		80, 81, 82, 83, // all-element res% / damage% +/-
		86, 87, // resistance to AP/MP loss
		89,                // damage rebound %
		99, 100, 101, 102, // AP/MP boost+gain & debuff+loss
		120, 121, 122, 123, // block % / dodge % +/-
		135, 136, 137, 138, // damage-on-successful-hit boosts
		141,      // damage % steal
		147, 148, // crit/fumble down
		154, 164, // zone-spell resistance +/-
		155, // damage/resistance bluff
	}
	for _, id := range buffIDs {
		m[id] = KindBuff
	}
	return m
}()

// Kind returns the effect's mechanic classification (KindUnsupported if the
// resolver does not model this action id).
func (e Effect) Kind() EffectKind { return effectKind[e.ActionID] }

// BuffResource names the fight resource a resource-affecting characteristic buff
// mutates. Only these are modelled server-side (they change turn resources or
// death math and so must stay in sync with the client); every other KindBuff is
// render-only in the current flat-damage model.
type BuffResource int

const (
	BuffNone       BuffResource = iota
	BuffHP                      // max (and current) HP
	BuffAP                      // AP ceiling
	BuffMP                      // MP ceiling
	BuffRange                   // range characteristic
	BuffSummons                 // NB_SUMMONS characteristic (client id 26): +1 = one extra simultaneous summon
	BuffCritRate                // critical-hit rate (%)
	BuffFumbleRate              // fumble (critical-miss) rate (%)
	BuffBlock                   // block/tackle % (client charac Lr.brd)
	BuffDodge                   // dodge % (client charac Lr.bre)
)

// buffMeta describes a resource buff: which resource, its sign (+1 gain / -1
// loss), and whether it moves the MAX (CharacBuff/Debuff raise/lower the ceiling)
// or only the current value (CharacGain/Loss).
type buffMeta struct {
	res        BuffResource
	sign       int32
	affectsMax bool
}

var resourceBuff = map[int32]buffMeta{
	11:  {BuffHP, +1, true},          // Boost de HP (raise max)
	12:  {BuffHP, -1, true},          // Deboost de HP
	13:  {BuffAP, +1, true},          // Boost de AP (raise max)
	14:  {BuffAP, -1, true},          // Deboost de AP
	17:  {BuffMP, +1, true},          // Boost de MP
	18:  {BuffMP, -1, true},          // Deboost de MP
	72:  {BuffRange, +1, false},      // Augmente la portée
	73:  {BuffRange, -1, false},      // Diminue la portée
	74:  {BuffSummons, +1, false},    // Augmente le nombre d'invocs (NB_SUMMONS, client charac 26)
	70:  {BuffCritRate, +1, false},   // Augmente les chances de coup critique (CH)
	71:  {BuffFumbleRate, +1, false}, // Augmente les chances d'échec critique (CM)
	147: {BuffCritRate, -1, false},   // Diminue le taux de coup critique
	148: {BuffFumbleRate, -1, false}, // Diminue le taux d'échec critique
	120: {BuffBlock, +1, false},      // Gain de pourcentage de blocage   (Lr.brd)
	121: {BuffBlock, -1, false},      // Perte de pourcentage de blocage
	122: {BuffDodge, +1, false},      // Gain de pourcentage d'esquive    (Lr.bre)
	123: {BuffDodge, -1, false},      // Perte de pourcentage d'esquive
	99:  {BuffAP, +1, true},          // Boost et gain de PA
	100: {BuffAP, -1, true},          // Deboost et perte de PA
	101: {BuffMP, +1, true},          // Boost et gain de PM
	102: {BuffMP, -1, true},          // Deboost et perte de PM
}

// BuffResource reports the resource a KindBuff mutates server-side, its signed
// direction, and whether it moves the max or the current value. ok is false for
// a pure-stat buff (rendered only).
func (e Effect) BuffResource() (res BuffResource, sign int32, affectsMax, ok bool) {
	m, found := resourceBuff[e.ActionID]
	if !found {
		return BuffNone, 0, false, false
	}
	return m.res, m.sign, m.affectsMax, true
}

// Roll returns the effect's magnitude from its params, using the Ankama
// convention proven in the v2.04b resolver and the 2.70 client's computeValue:
//   - 1 param  → a fixed value (params[0]);
//   - 3 params → a dice roll [diceCount, diceFaces, modifier];
//   - 0 params → 0 (a param-less effect, e.g. teleport);
//   - 2 / >3   → degrade to params[0] (never observed in real data).
//
// The value is authoritative: the client renders whatever integer the server
// sends (RunningEffect.getValue() verbatim — it never re-rolls on receive), so
// the exact roll distribution need not match Ankama's, only its range. rng may
// be nil for the deterministic (fixed/dice-of-1-face) paths.
func (e Effect) Roll(rng *rand.Rand) int32 {
	p := e.Params
	switch len(p) {
	case 0:
		return 0
	case 3:
		count, faces, mod := int32(p[0]), int32(p[1]), int32(p[2])
		if count <= 0 || faces <= 1 || rng == nil {
			// Degenerate dice → deterministic min (count·1 + mod), never negative.
			v := count + mod
			if faces <= 1 {
				v = count*faces + mod
			}
			if v < 0 {
				return 0
			}
			return v
		}
		total := int32(0)
		for i := int32(0); i < count; i++ {
			total += int32(rng.Intn(int(faces))) + 1
		}
		total += mod
		if total < 0 {
			return 0
		}
		return total
	default: // 1, 2, or >3 params
		v := int32(p[0])
		if v < 0 {
			return -v // magnitudes are positive on the wire
		}
		return v
	}
}

// DurationTurns returns the buff duration in table-turns and whether it is
// infinite. The effect record's Duration is [tableTurns, turns]; a slot ≥ 63 is
// the client's "infinite" sentinel (TurnBasedTimeInterval.isInfinite). An absent
// duration is a 0-turn (instant) effect.
func (e Effect) DurationTurns() (turns int32, infinite bool) {
	if len(e.Duration) == 0 {
		return 0, false
	}
	for _, d := range e.Duration {
		if d >= 63 {
			return d, true
		}
	}
	return e.Duration[0], false
}
