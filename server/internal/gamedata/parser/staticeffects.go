package parser

// This file parses staticEffects.dat, using the standard big-endian .dat
// convention (Reader, not AleaReader -- see reader.go's doc comment).
//
// STATUS UPDATE (docs/08-java-parity-roadmap.md Phase M, July 2026):
// docs/04-game-data-format.md §4.5 previously stated this format was
// unrecoverable because "StaticEffectLoader was found to have its body
// entirely commented out". That was WRONG -- re-examining the decompiled
// client/com/ankamagames/dofusarena/client/core/contentInitializer/
// StaticEffectLoader.java (obtained via a fresh decompile of the real
// game's core.jar, this time with a working body) shows a complete,
// substantial read() method. Cross-verified against the real bytecode
// (javap -c -p on the actual compiled .class) to confirm the decompiled
// source's method body is genuinely present and not a decompiler
// artifact, AND empirically verified: parsing the real 1868-byte
// data/staticEffects.dat with this exact layout consumes precisely
// 1868 bytes with zero leftover -- the strongest possible confirmation
// available without an official reference decoder.
//
// Format (StaticEffectLoader.read(), confirmed field-for-field):
//
//	int32   areaCount
//	repeated areaCount times:
//	  int32    id
//	  int32    scriptId
//	  int16    areaShapeId          // AreaOfEffectEnum id, see area.go's AreaShape
//	  int32[]  areaParams           // shape-specific size params (e.g. circle radius)
//	  int32[]  applicationTriggers  // trigger-bit IDs, set into a BitSet
//	  int32[]  unapplicationTriggers
//	  int16    maxExecutionCount    // >=63 means unlimited (mirrors
//	                                //   AbstractEffectArea.hasNoExecutionCount()'s
//	                                //   `!(m_maxExecutionCount < 63 && >= 0)` check)
//	  int32[]  applicationTargets   // FightTargetValidator selector ids
//	  int32[]  unapplicationTargets
//	  int32    targetsToShow
//	  string   effectAreaType       // "TRAP" or "SPECIAL" -- the ONLY two values ever
//	                                //   written in this project's real data (confirmed:
//	                                //   all 10 real areas are one or the other); any
//	                                //   other string is simply not registered anywhere
//	                                //   by the reference loader (neither addEffectArea
//	                                //   nor addSpecialCell is called)
//	  int32[]  deactivationDelay    // [tableTurnDelay, turnDelay], mirrors
//	                                //   AbstractEffectArea.hasActivationDelay()
//	  int32    applicationCondition // 0=always, 1=ONE_TIME_FOR_EVERYONE,
//	                                //   2=ONE_TIME_FOR_TEAM, 3=ONE_TIME_FOR_TARGET
//	                                //   (AbstractEffectArea.SHOW_TO_*/ONE_TIME_FOR_*
//	                                //   constants)
//
//	int32   effectCount
//	repeated effectCount times:
//	  (same Effect layout as cards.dat/spells.dat/events.dat, see effect.go)
//	  -- parentType is always "AREA" in the real data, parentId matches one
//	     of the area records' own `id` field above.
type StaticEffectAreaRaw struct {
	ID                    int32
	ScriptID              int32
	AreaShapeID           int16
	AreaParams            []int32
	ApplicationTriggers   []int32
	UnapplicationTriggers []int32
	MaxExecutionCount     int16
	ApplicationTargets    []int32
	UnapplicationTargets  []int32
	TargetsToShow         int32
	EffectAreaType        string // "TRAP" or "SPECIAL"
	DeactivationDelay     []int32
	ApplicationCondition  int32
}

// StaticEffectsFile is the fully-parsed content of staticEffects.dat.
type StaticEffectsFile struct {
	Areas   []StaticEffectAreaRaw
	Effects []EffectRaw
}

// ParseStaticEffectsFile parses the full contents of staticEffects.dat.
func ParseStaticEffectsFile(data []byte) (StaticEffectsFile, error) {
	r := NewReader(data)
	var out StaticEffectsFile

	areaCount := int(r.Int32())
	out.Areas = make([]StaticEffectAreaRaw, 0, areaCount)
	for i := 0; i < areaCount; i++ {
		out.Areas = append(out.Areas, StaticEffectAreaRaw{
			ID:                    r.Int32(),
			ScriptID:              r.Int32(),
			AreaShapeID:           r.Int16(),
			AreaParams:            r.Int32Slice(),
			ApplicationTriggers:   r.Int32Slice(),
			UnapplicationTriggers: r.Int32Slice(),
			MaxExecutionCount:     r.Int16(),
			ApplicationTargets:    r.Int32Slice(),
			UnapplicationTargets:  r.Int32Slice(),
			TargetsToShow:         r.Int32(),
			EffectAreaType:        r.String(),
			DeactivationDelay:     r.Int32Slice(),
			ApplicationCondition:  r.Int32(),
		})
	}

	effectCount := int(r.Int32())
	out.Effects = make([]EffectRaw, 0, effectCount)
	for i := 0; i < effectCount; i++ {
		out.Effects = append(out.Effects, ReadEffect(r))
	}

	if err := r.Err(); err != nil {
		return StaticEffectsFile{}, err
	}
	return out, nil
}
