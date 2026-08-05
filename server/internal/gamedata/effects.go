package gamedata

import (
	"encoding/binary"
	"math"
	"strings"
)

// cur is a bounds-checked big-endian cursor over a record payload. Every read
// advances the position; the first read that would run past the end trips err,
// after which every read yields a zero value. This lets a truncated or slightly
// mis-specified record degrade to empty data instead of panicking — important
// because these are copyrighted third-party blobs we parse defensively.
type cur struct {
	b   []byte
	pos int
	err bool
}

func (c *cur) ok() bool { return !c.err }

func (c *cur) need(n int) bool {
	if c.err || n < 0 || c.pos+n > len(c.b) {
		c.err = true
		return false
	}
	return true
}

func (c *cur) u8() uint8 {
	if !c.need(1) {
		return 0
	}
	v := c.b[c.pos]
	c.pos++
	return v
}

func (c *cur) i16() int16 {
	if !c.need(2) {
		return 0
	}
	v := int16(binary.BigEndian.Uint16(c.b[c.pos:]))
	c.pos += 2
	return v
}

func (c *cur) i32() int32 {
	if !c.need(4) {
		return 0
	}
	v := int32(binary.BigEndian.Uint32(c.b[c.pos:]))
	c.pos += 4
	return v
}

func (c *cur) i64() int64 {
	if !c.need(8) {
		return 0
	}
	v := int64(binary.BigEndian.Uint64(c.b[c.pos:]))
	c.pos += 8
	return v
}

func (c *cur) f32() float32 {
	if !c.need(4) {
		return 0
	}
	v := math.Float32frombits(binary.BigEndian.Uint32(c.b[c.pos:]))
	c.pos += 4
	return v
}

// str reads a record-payload string: [i32 len][UTF-8 bytes] (DATA-FORMAT §2).
// (This is NOT the Java writeUTF used by the index file, which is u16-prefixed.)
func (c *cur) str() string {
	n := int(c.i32())
	if n < 0 || !c.need(n) {
		return ""
	}
	s := string(c.b[c.pos : c.pos+n])
	c.pos += n
	return s
}

// f32Array reads [i32 count][f32×count].
func (c *cur) f32Array() []float32 {
	n := int(c.i32())
	if n <= 0 || !c.need(n*4) {
		return nil
	}
	out := make([]float32, n)
	for i := 0; i < n; i++ {
		out[i] = c.f32()
	}
	return out
}

// i32Array reads [i32 count][i32×count].
func (c *cur) i32Array() []int32 {
	n := int(c.i32())
	if n <= 0 || !c.need(n*4) {
		return nil
	}
	out := make([]int32, n)
	for i := 0; i < n; i++ {
		out[i] = c.i32()
	}
	return out
}

// i64Array reads [i32 count][i64×count].
func (c *cur) i64Array() []int64 {
	n := int(c.i32())
	if n <= 0 || !c.need(n*8) {
		return nil
	}
	out := make([]int64, n)
	for i := 0; i < n; i++ {
		out[i] = c.i64()
	}
	return out
}

// Effect is the subset of a decoded Ht effect record (type 200) the server
// needs. actionId maps to a RunningEffect (e.g. 11=HP boost); containerType is
// the parent tag ("FIGHTER_CARD_EQUIP" / "FIGHTER_CARD_USE" / …) that decides
// whether the effect is passive (applied while equipped) or active (on use);
// params are the effect's f32 magnitudes (params[0] is the bonus amount for a
// CharacBuff).
type Effect struct {
	EffectID      int32 // field 1: the generic-effect id (the client's part-0 genericEffectId)
	ActionID      int32
	IsCritical    bool // field 11: this effect runs on a CRITICAL hit (vs the normal subset)
	ContainerType string
	AreaShape     int32 // field 5: zone shape ordinal (1 point, 2 circle, 3 cross, 4 T, 9 T-inv, 32767 all)
	Params        []float32
	AreaSize      []int32 // 5th post-params array (Tg/effect_area_size): zone radius/size params
	Duration      []int32 // 6th post-params array: buff duration in turns (empty/[0] for instant effects)
	Targets       []int64 // field 19: target-condition bitmasks (empty = no restriction)
}

// decodeEffectList reads an embedded effect list (DATA-FORMAT §3):
//
//	[i32 count] then count × { i32 innerId, i16 innerVer, i32 blobLen, blob }
//
// Each wrapper mirrors the outer record framing ([id][ver][len][payload]); the
// wrapper's innerId IS the effectId, so the blob payload begins at the actionId
// field. We decode only the leading fields (through params) and skip the rest of
// each blob via its own length, so an over-/under-read in one effect can never
// corrupt the next.
func decodeEffectList(c *cur) []Effect {
	n := int(c.i32())
	if n <= 0 || n > 1<<16 {
		return nil
	}
	out := make([]Effect, 0, n)
	for i := 0; i < n && c.ok(); i++ {
		_ = c.i32() // innerId (effectId)
		_ = c.i16() // innerVer
		blobLen := int(c.i32())
		if blobLen < 0 || !c.need(blobLen) {
			break
		}
		blob := c.b[c.pos : c.pos+blobLen]
		c.pos += blobLen
		out = append(out, decodeEffectBlob(blob))
	}
	return out
}

// decodeEffectBlob parses one Ht effect payload up through the params array
// (DATA-FORMAT §3). Verified against the shipped bytes: the blob DOES lead with
// effectId (field 1) — the wrapper's innerId is redundant with it — so the
// layout is:
//
//	i32 effectId, i32 actionId, i32 parentId, str parentType, i32 areaShape,
//	i16 areaOrdering, 5×bool, f32[] params, …(unread tail)
func decodeEffectBlob(b []byte) Effect {
	return decodeEffectCursor(&cur{b: b})
}

// decodeEffectCursor decodes one Ht effect from a caller-owned cursor, consuming
// it EXACTLY — every field through to the two trailing flags.
//
// That exactness is the whole point. Most effects on this format arrive
// length-prefixed (`decodeEffectList` slices the blob first), so stopping early
// is harmless there. But an `np_1` parameter carries its effect INLINE with no
// length prefix (see parameters.go), so the only way past it is to parse it
// completely. Reading one byte too few or too many desynchronises the remainder
// of the enclosing record.
func decodeEffectCursor(c *cur) Effect {
	e := Effect{}
	e.EffectID = c.i32() // field 1 effectId (also carried as the wrapper innerId)
	e.ActionID = c.i32() // field 2 actionId
	_ = c.i32()          // field 3 parentId
	// field 4 parentType. Stored space-padded to a fixed width in the shipped
	// data ("FIGHTER_CARD_USE  "), so trim it — the client compares it trimmed
	// too (jb_2.a). Without this, matching "FIGHTER_CARD_USE" silently never
	// fires while the same-length "FIGHTER_CARD_EQUIP" happens to work.
	e.ContainerType = strings.TrimSpace(c.str())
	e.AreaShape = c.i32()      // field 5 areaShape
	_ = c.i16()                // field 6 areaOrdering
	_ = c.u8()                 // field 7 affectedByLocalisation — intentionally unmodeled: directional (back/side/front) damage is a Dofus-1/2.04b-era mechanic 2.70 dropped (the client has no directional-damage tooltips). Facing itself IS tracked (4521/4522 → FightFighter.Orientation) but is purely COSMETIC: it never feeds the damage formula. See BUGS.md B-037 / B-045.
	_ = c.u8()                 // field 8 targetTriggerIsSelf
	_ = c.u8()                 // field 9 isPersonal
	_ = c.u8()                 // field 10 hasSingleTarget
	e.IsCritical = c.u8() != 0 // field 11 isCritical (the crit-hit effect subset)
	e.Params = c.f32Array()    // field 12 params
	// After params come SIX consecutive i32[] arrays then one i64[] — the exact
	// order of the client's Ht deserializer (Ht.a, verified against its DB column
	// names): triggersBefore, triggersAfter, endTriggers, a VESTIGIAL array (Tf/
	// beH — never populated in the shipped data, so always empty), areaSize (Tg/
	// beI, DB column effect_area_size — the array the client feeds to zg_1.a as
	// the zone radius/size), duration (Th/beJ). We keep areaSize + duration; the
	// rest are skipped. The defensive cursor degrades a truncated tail to empty.
	//
	// NB: the areaSize is the FIFTH post-params array, not the fourth. Reading the
	// fourth (the empty vestigial Tf) made every circle/cross/T spell arrive with
	// no size and collapse to a single-cell point (the "AoE falls back to point"
	// bug); the radius is NOT baked into the shape ordinal.
	_ = c.i32Array()          // 13 triggersBefore (Tc/beE)
	_ = c.i32Array()          // 14 triggersAfter (Td/beF)
	_ = c.i32Array()          // 15 endTriggers (Te/beG)
	_ = c.i32Array()          // 16 vestigial (Tf/beH) — never populated; skip
	e.AreaSize = c.i32Array() // 17 areaSize (Tg/beI) — zone radius/size fed to zg_1.a
	e.Duration = c.i32Array() // 18 duration (Th/beJ)
	e.Targets = c.i64Array()  // 19 target-condition bitmasks (Ti/beK)
	// 20-21: two trailing flags (beL/beM, getters Tj/Tk). The client hands them
	// straight to the runtime effect constructor (abw_2 -> xj_0) and their meaning
	// is not established, but they MUST be consumed: they are the last two bytes
	// of the record, and an inline effect is only skippable if we read it whole.
	_ = c.u8() // 20 beL
	_ = c.u8() // 21 beM
	return e
}
