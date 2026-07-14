package botai

import (
	"math/rand"
	"testing"

	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// buildCreateFightPayload constructs a minimal 2-team CREATE_FIGHT (8000)
// payload for tests: one fighter per coach, matching packets_fight.go's
// buildCreateFight layout closely enough for FightState.ingestCreateFight.
func buildCreateFightPayload(myCoach, enemyCoach int64, myFighterWireID, enemyFighterWireID int64, mySpells []int32) []byte {
	w := protocol.NewWriter(128)
	w.PutByte(0)   // error code
	w.PutUint16(0) // coach cards blob (unused)
	w.PutInt32(1)  // fight type
	w.PutInt32(0)  // bet
	w.PutByte(2)   // team count

	writeTeam := func(teamID byte, coachID, fighterWireID int64, spells []int32) {
		w.PutByte(teamID)
		w.PutString("team")
		w.PutByte(1) // coach count
		w.PutInt64(coachID)
		w.PutString("Coach")
		w.PutByte(0).PutByte(0).PutByte(0) // skin/hair/sex
		w.PutUint16(0)                     // equipped coach-card blob
		w.PutByte(1)                       // fighter count
		w.PutInt64(fighterWireID)
		w.PutByte(1) // breed
		w.PutString("F")
		w.PutByte(0).PutByte(0) // sex/skin
		// spell blob (flat int32[])
		spellBlob := protocol.NewWriter(len(spells) * 4)
		for _, s := range spells {
			spellBlob.PutInt32(s)
		}
		w.PutUint16(uint16(spellBlob.Len()))
		w.PutBytes(spellBlob.Bytes())
		w.PutUint16(0) // object blob
		w.PutUint16(0) // statistics report
		w.PutByte(0)   // bet card count
	}
	writeTeam(1, myCoach, myFighterWireID, mySpells)
	writeTeam(2, enemyCoach, enemyFighterWireID, nil)
	return w.Bytes()
}

func actorAppearPayload(entries map[int64]Cell) []byte {
	w := protocol.NewWriter(1 + len(entries)*19)
	w.PutByte(byte(len(entries)))
	for id, c := range entries {
		w.PutInt64(id).PutInt32(c.X).PutInt32(c.Y).PutInt16(c.Z).PutByte(0)
	}
	return w.Bytes()
}

func turnBeginPayload(fighterID int64) []byte {
	w := protocol.NewWriter(16)
	w.PutInt32(0).PutInt32(-1).PutInt64(fighterID)
	return w.Bytes()
}

func fighterDiesPayload(fighterID int64) []byte {
	w := protocol.NewWriter(16)
	w.PutInt32(0).PutInt32(-1).PutInt64(fighterID)
	return w.Bytes()
}

func TestIngestCreateFight_IdentifiesOwnFightersAndSpells(t *testing.T) {
	const myCoach, enemyCoach = 100, 200
	const myFighter, enemyFighter = fighterWireIDBase + 1, fighterWireIDBase + 2
	spells := []int32{31, 32, 33}

	st := NewFightState(myCoach)
	st.Ingest(botclient.Frame{
		Opcode:  protocol.SendCreateFight,
		Payload: buildCreateFightPayload(myCoach, enemyCoach, myFighter, enemyFighter, spells),
	})

	me := st.Fighters[myFighter]
	if me == nil || !me.Mine {
		t.Fatalf("own fighter not identified as mine: %+v", me)
	}
	if len(me.SpellIDs) != 3 {
		t.Fatalf("own fighter spell ids = %v, want 3", me.SpellIDs)
	}
	enemy := st.Fighters[enemyFighter]
	if enemy == nil || enemy.Mine {
		t.Fatalf("enemy fighter mis-identified: %+v", enemy)
	}
	if len(enemy.SpellIDs) != 0 {
		t.Fatalf("enemy spell ids should be hidden, got %v", enemy.SpellIDs)
	}
}

func TestIngestTurnBegin_DetectsMyTurn(t *testing.T) {
	const myCoach, enemyCoach = 100, 200
	const myFighter, enemyFighter = fighterWireIDBase + 1, fighterWireIDBase + 2

	st := NewFightState(myCoach)
	st.Ingest(botclient.Frame{Opcode: protocol.SendCreateFight,
		Payload: buildCreateFightPayload(myCoach, enemyCoach, myFighter, enemyFighter, nil)})

	if myTurn := st.Ingest(botclient.Frame{Opcode: protocol.SendFighterTurnBegin, Payload: turnBeginPayload(enemyFighter)}); myTurn {
		t.Fatal("enemy turn should not report myTurn=true")
	}
	if myTurn := st.Ingest(botclient.Frame{Opcode: protocol.SendFighterTurnBegin, Payload: turnBeginPayload(myFighter)}); !myTurn {
		t.Fatal("own turn should report myTurn=true")
	}
	if st.CurrentTurn != myFighter {
		t.Fatalf("CurrentTurn = %d, want %d", st.CurrentTurn, myFighter)
	}
}

func TestIngestFighterDies_MarksDead(t *testing.T) {
	const myCoach, enemyCoach = 100, 200
	const myFighter, enemyFighter = fighterWireIDBase + 1, fighterWireIDBase + 2
	st := NewFightState(myCoach)
	st.Ingest(botclient.Frame{Opcode: protocol.SendCreateFight,
		Payload: buildCreateFightPayload(myCoach, enemyCoach, myFighter, enemyFighter, nil)})

	st.Ingest(botclient.Frame{Opcode: protocol.SendFighterDies, Payload: fighterDiesPayload(enemyFighter)})
	if st.Fighters[enemyFighter].Alive {
		t.Fatal("enemy should be dead after FIGHTER_DIES")
	}
	if len(st.EnemyLivingFighters()) != 0 {
		t.Fatalf("no living enemies expected, got %d", len(st.EnemyLivingFighters()))
	}
}

// setupPositionedFight builds a fight with my fighter and one enemy at given
// positions and my fighter's turn active.
func setupPositionedFight(t *testing.T, myPos, enemyPos Cell, mySpells []int32) *FightState {
	t.Helper()
	const myCoach, enemyCoach = 100, 200
	const myFighter, enemyFighter = fighterWireIDBase + 1, fighterWireIDBase + 2
	st := NewFightState(myCoach)
	st.Ingest(botclient.Frame{Opcode: protocol.SendCreateFight,
		Payload: buildCreateFightPayload(myCoach, enemyCoach, myFighter, enemyFighter, mySpells)})
	st.Ingest(botclient.Frame{Opcode: protocol.SendActorAppear,
		Payload: actorAppearPayload(map[int64]Cell{myFighter: myPos, enemyFighter: enemyPos})})
	st.Ingest(botclient.Frame{Opcode: protocol.SendFighterTurnBegin, Payload: turnBeginPayload(myFighter)})
	return st
}

func TestDumbAI_ClosesAndMeleesWhenAdjacent(t *testing.T) {
	st := setupPositionedFight(t, Cell{X: 5, Y: 5}, Cell{X: 6, Y: 5}, nil)
	intents := Dumb{}.PlanTurn(st, nil, rand.New(rand.NewSource(1)))
	if len(intents) != 1 || intents[0].Kind != IntentCloseCombat {
		t.Fatalf("adjacent enemy should yield a single close-combat intent, got %+v", intents)
	}
}

func TestDumbAI_StepsTowardDistantEnemy(t *testing.T) {
	st := setupPositionedFight(t, Cell{X: 0, Y: 0}, Cell{X: 5, Y: 0}, nil)
	intents := Dumb{}.PlanTurn(st, nil, rand.New(rand.NewSource(1)))
	if len(intents) == 0 || intents[0].Kind != IntentMove {
		t.Fatalf("distant enemy should yield a move intent first, got %+v", intents)
	}
	// The step must reduce Manhattan distance toward the enemy.
	if intents[0].Target.X <= 0 {
		t.Fatalf("step should move toward enemy (+X), got %+v", intents[0].Target)
	}
}

func TestSmartAI_CastsInRangeSpell(t *testing.T) {
	spells := []int32{500}
	st := setupPositionedFight(t, Cell{X: 0, Y: 0}, Cell{X: 3, Y: 0}, spells)
	book := SpellBook{500: {ID: 500, APCost: 4, RangeMin: 1, RangeMax: 6, Damage: 20}}
	// Force no summon branch by using a book without summon spells and a
	// deterministic rng.
	intents := Smart{}.PlanTurn(st, book, rand.New(rand.NewSource(1)))

	sawCast := false
	for _, in := range intents {
		if in.Kind == IntentCast && in.SpellID == 500 {
			sawCast = true
			if in.Target != (Cell{X: 3, Y: 0}) {
				t.Fatalf("cast target = %+v, want enemy cell", in.Target)
			}
		}
	}
	if !sawCast {
		t.Fatalf("smart AI should cast the in-range damage spell, got %+v", intents)
	}
}

func TestSmartAI_MovesInWhenSpellOutOfRange(t *testing.T) {
	spells := []int32{500}
	// Enemy at distance 10, spell max range 6 -> must step in first.
	st := setupPositionedFight(t, Cell{X: 0, Y: 0}, Cell{X: 10, Y: 0}, spells)
	book := SpellBook{500: {ID: 500, APCost: 4, RangeMin: 1, RangeMax: 6, Damage: 20}}
	intents := Smart{}.PlanTurn(st, book, rand.New(rand.NewSource(1)))
	if len(intents) == 0 || intents[0].Kind != IntentMove {
		t.Fatalf("out-of-range spell should yield a move first, got %+v", intents)
	}
}

func TestFightState_NoPanicOnTruncatedPayloads(t *testing.T) {
	st := NewFightState(1)
	// Feed deliberately short payloads for each parsed opcode; the Reader
	// is bounds-checked, so these must be no-ops, not panics.
	for _, op := range []protocol.SendOpcode{
		protocol.SendCreateFight, protocol.SendActorAppear, protocol.SendFighterMove,
		protocol.SendMoveToFreePlacement, protocol.SendFighterDies, protocol.SendFighterTurnBegin,
	} {
		st.Ingest(botclient.Frame{Opcode: op, Payload: []byte{0x01}})
	}
}
