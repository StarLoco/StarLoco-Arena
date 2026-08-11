package game

// postfight.go — the per-fighter post-fight report, the client's `adl_0` (read
// back as `OW`). It is the carrier for the whole coach META layer: XP gained and
// where it came from, the new morale and fatigue, and the wound/death flags.
//
// It rides inside END_FIGHT (8300) as [u8 n]{[i64 fighterId][i16 len][40 bytes]}
// and is bound field-by-field by the client's `fightResultEvolutionDialog.xml`.
// Sending no reports (which this server did until now) leaves that debrief panel
// blank and means no fighter ever gains XP.
//
// EVERY formula here is transcribed from the shipped client — `adl_0`, `et_2` and
// the constants in `nr_0` — because `core.jar` contains the *shared* client/server
// code, so the real server's arithmetic is literally in our hands. Each one is
// quoted above its Go translation. Do not "improve" them: the client recomputes
// and displays several of these values, so drifting from them shows up as visibly
// inconsistent numbers on the results screen.
//
// Scope: XP, morale, fatigue and coach reputation, PLUS the wound roll
// (`bf_1.b`) and the death roll (`adl_0.atd`) — both added in B-066 and run
// from applyWoundAndDeathRolls in postfight_apply.go. (This comment described
// the slice-1 state, where they were deliberately skipped and `injuryRolled`
// stayed false; that stopped being true two slices ago.) See
// docs/DATA-COVERAGE.md.

import (
	"math"
	"math/rand"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Client constants from `nr_0`. Named for what they mean; the obfuscated name is
// kept so the provenance stays checkable.
const (
	maxTiredness    = 100    // nr_0.Pq
	maxMorale       = 100    // nr_0.Pt
	deathXPScale    = 100000 // nr_0.Pv — totalXp/1000 = injury chance %
	goodRestHours   = 12     // nr_0.Pw — idle longer than this earns the rest bonus
	goodRestPercent = 50     // nr_0.Px — and that bonus is +50%
	standingXPScale = 60     // nr_0.Po — reputation → XP conversion (AI 14)
	maxSpendableXP  = 50000  // nr_0.PI — et_2.ft refuses gains at/above this
	postFightBytes  = 40     // adl_0.nj()
)

// fighterLevelSteps is nr_0.PP: the totalXp thresholds between fighter levels
// 1..6 (Larve, Tofu, Prespic, Bouftou, Craqueleur, Démon).
var fighterLevelSteps = [5]int32{860, 4000, 10000, 20000, 40000}

// FighterLevel converts lifetime XP into the fighter level 1..6 the client shows
// as a medal and that reward criteria gate on.
//
//	nr_0.cs: for j in PP { if n > PP[j] continue; return j+1 }; return 6
func FighterLevel(totalXP int32) int16 {
	for i, step := range fighterLevelSteps {
		if totalXP <= step {
			return int16(i + 1)
		}
	}
	return 6
}

// StandingToLevel converts a coach's standing (evolution XP) into its evolution
// level, capped at 50.
//
//	aet_0.nJ: max(1, min((int)sqrt(standing/10), 50))
func StandingToLevel(standing int32) int32 {
	if standing < 0 {
		standing = 0
	}
	lvl := int32(math.Sqrt(float64(standing / 10)))
	if lvl < 1 {
		return 1
	}
	if lvl > 50 {
		return 50
	}
	return lvl
}

// StandingForLevel is the standing required to reach an evolution level.
//
//	nr_0.ct: n == 1 ? 0 : n*n*10
func StandingForLevel(level int32) int32 {
	if level <= 1 {
		return 0
	}
	return level * level * 10
}

// recoverTiredness applies elapsed-time fatigue recovery.
//
//	et_2.a(byte fatigue, long hours):
//	  if hours == 0 return fatigue
//	  d = max(0, sqrt(fatigue*100/Pq) - sqrt(max(hours-1, 0)))
//	  return d*d*Pq/100
//
// With Pq == 100 this is simply (sqrt(fatigue) - sqrt(hours-1))², i.e. recovery
// accelerates as it goes: 100 fatigue needs 101 h to clear, but 25 needs only 26.
// NOTE the integer division `fatigue*100/Pq` happens BEFORE the sqrt in the
// client, so it must here too, or values differ by a rounding step.
func recoverTiredness(tiredness uint8, hours int64) uint8 {
	if hours <= 0 {
		return tiredness
	}
	inner := float64(int32(tiredness) * 100 / maxTiredness) // integer division, as the client does
	h := hours - 1
	if h < 0 {
		h = 0
	}
	d := math.Sqrt(inner) - math.Sqrt(float64(h))
	if d < 0 {
		d = 0
	}
	return uint8(d * d * float64(maxTiredness) / 100.0)
}

// postFightReport is one fighter's debrief — the server-side twin of `adl_0`.
// Field names are the client's `OW` accessor names; the obfuscated field is noted
// so the 40-byte layout stays auditable against `adl_0.cd()`.
type postFightReport struct {
	injuryRolled bool  // cng — the wound/death roll ran (slice 2)
	won          bool  // cnh
	injuryChance int32 // cni
	injuryCancel bool  // cnj
	exhausted    bool  // cnk — fatigue hit the cap
	woundID      int32 // cnl — id of the wound inflicted (0 = none)
	deathChance  int32 // cnm
	resurrected  bool  // cnn
	dead         bool  // baT

	tiredness      int8 // aRx — new value
	tirednessDelta int8 // cno
	morale         int8 // aRy — new value
	moraleDelta    int8 // cnp

	totalXP      int32 // cnq
	baseXP       int32 // cnr — XP before any bonus
	moraleBonus  int8  // cns — the bonus %, which IS the morale value
	goodRest     bool  // cnt — rested more than 12 h
	xpBeforeGear int32 // cnu — after morale + rest, before item/set bonuses
	xpFinal      int32 // cnv — what the fighter actually banks
}

// encode writes the 40-byte blob exactly as `adl_0.cd()` does. The order is not
// negotiable — `OW.b()` reads it positionally with no length markers.
func (r *postFightReport) encode() []byte {
	w := protocol.NewWriter()
	w.U8(boolByte(r.injuryRolled))
	w.U8(boolByte(r.won))
	w.I32(r.injuryChance)
	w.U8(boolByte(r.injuryCancel))
	w.U8(boolByte(r.exhausted))
	w.I32(r.woundID)
	w.I32(r.deathChance)
	w.U8(boolByte(r.resurrected))
	w.U8(boolByte(r.dead))
	w.U8(uint8(r.tiredness))
	w.U8(uint8(r.tirednessDelta))
	w.U8(uint8(r.morale))
	w.U8(uint8(r.moraleDelta))
	w.I32(r.totalXP)
	w.I32(r.baseXP)
	w.U8(uint8(r.moraleBonus))
	w.U8(boolByte(r.goodRest))
	w.I32(r.xpBeforeGear)
	w.I32(r.xpFinal)
	return w.Bytes()
}

// applyXP runs the XP formula.
//
//	adl_0.a(int baseXp, long hoursSinceLastFight, byte morale):
//	  cnr = baseXp; cns = morale
//	  n = baseXp * (100 + morale) / 100
//	  if hours > Pw { n = n * (100 + Px) / 100; cnt = true }
//	  cnu = cnv = n
//
// The morale value is used DIRECTLY as a bonus percentage — that is what proves
// higher morale is better, and it is why morale and the bonus % are the same
// number on the results screen.
func (r *postFightReport) applyXP(baseXP int32, hoursSinceLastFight int64, morale int8) {
	r.baseXP = baseXP
	r.moraleBonus = morale
	n := baseXP * (100 + int32(morale)) / 100
	if hoursSinceLastFight > goodRestHours {
		n = n * (100 + goodRestPercent) / 100
		r.goodRest = true
	}
	r.xpBeforeGear = n
	r.xpFinal = n
}

// addXP is `adl_0.ft`: gear/set bonuses stack onto the final XP only. The client
// guards on `cnv != 0`, so a fight that yielded nothing stays at nothing — a
// bonus cannot conjure XP out of a zero.
func (r *postFightReport) addXP(n int32) {
	if r.xpFinal != 0 {
		r.xpFinal += n
	}
}

// addMorale is `adl_0.au`: shift both the new value and the delta, so the client
// shows the modifier's contribution inside the delta it displays.
func (r *postFightReport) addMorale(n int8) {
	r.morale += n
	r.moraleDelta += n
}

// addTiredness is `adl_0.av` (note the client updates the delta FIRST here).
func (r *postFightReport) addTiredness(n int8) {
	r.tirednessDelta += n
	r.tiredness += n
}

// applyTiredness recovers elapsed fatigue, then adds this fight's cost.
//
//	adl_0.a(byte tiredness, long hours, boolean addFightCost):
//	  rested = et_2.a(tiredness, hours)
//	  cno = addFightCost ? rand(25) : 0
//	  aRx = min(rested + cno, Pq)
//	  ate()
func (r *postFightReport) applyTiredness(tiredness uint8, hours int64, addFightCost bool, rng *rand.Rand) {
	rested := recoverTiredness(tiredness, hours)
	var cost int32
	if addFightCost {
		cost = int32(rng.Intn(25))
	}
	r.tirednessDelta = int8(cost)
	total := int32(rested) + cost
	if total > maxTiredness {
		total = maxTiredness
	}
	r.tiredness = int8(total)
	r.finaliseTiredness()
}

// finaliseTiredness is `adl_0.ate()`: at the cap the fighter is EXHAUSTED (which
// makes the client report a 100% injury chance); otherwise fatigue amplifies the
// injury chance proportionally.
func (r *postFightReport) finaliseTiredness() {
	if int32(r.tiredness) >= maxTiredness {
		r.exhausted = true
	} else if r.injuryChance != 0 {
		r.injuryChance += int32(r.tiredness) * r.injuryChance / 100
	}
}

// applyMoraleDrift is `adl_0.dg(boolean bl2)` — morale reacts to the result, and
// the swing is DAMPED as it approaches the end it is heading for: a win moves by
// (100-morale)/50, a loss by morale/50. So morale converges rather than pinning.
// `bl2` is the "clean" result flag (a decisive win / a heavy loss), which doubles
// the random term in the fighter's favour or against it.
func (r *postFightReport) applyMoraleDrift(morale int8, decisive bool, rng *rand.Rand) {
	r.morale = morale
	var delta int32
	if r.won {
		delta = int32(rng.Intn(5))
		if decisive {
			delta += int32(rng.Intn(5))
		}
		delta = delta * (maxMorale - int32(morale)) / (maxMorale / 2)
	} else {
		d := int32(rng.Intn(5))
		if !decisive {
			d -= int32(rng.Intn(5))
		}
		delta = -d
		delta = delta * int32(morale) / (maxMorale / 2)
	}
	r.moraleDelta = int8(delta)
	r.morale = int8(clampInt32(int32(morale)+delta, 0, maxMorale))
}

// bank writes the report back onto the persistent fighter, applying the client's
// own guards: `et_2.ft` refuses XP once spendable XP reaches 50 000, and morale
// and fatigue are clamped to 0..100.
func (r *postFightReport) bank(f *domain.Fighter, now int64) {
	if r.xpFinal > 0 && f.XP < maxSpendableXP {
		f.XP += r.xpFinal
		f.TotalXP += r.xpFinal
	}
	r.totalXP = f.TotalXP
	f.Morale = uint8(clampInt32(int32(r.morale), 0, maxMorale))
	f.Tiredness = uint8(clampInt32(int32(r.tiredness), 0, maxTiredness))
	f.LastFightAt = now
}

func clampInt32(v, lo, hi int32) int32 {
	if v < lo {
		return lo
	}
	if v > hi {
		return hi
	}
	return v
}

// hoursSince returns whole hours between a stored unix timestamp and now. A zero
// timestamp (never fought) reports a long rest, which is the friendly reading and
// matches a fresh fighter starting fully rested.
func hoursSince(last, now int64) int64 {
	if last <= 0 {
		return goodRestHours + 1
	}
	if now <= last {
		return 0
	}
	return (now - last) / 3600
}
