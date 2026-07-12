package dispatch

import (
	"testing"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// createFightReader wraps a CREATE_FIGHT payload for step-by-step field
// extraction, mirroring the exact field order in buildCreateFight.
type createFightReader struct {
	r *protocol.Reader
}

// readTeam reads one team's fields (up to and including each fighter's
// spells/equipment blobs), returning the per-fighter spell/equipment ids
// found (in fighter order) for assertions.
// betCardsFromTeam is filled by readTeam with the team's staked card
// template ids (the CREATE_FIGHT per-team bet-card list).
func (cr *createFightReader) readTeamWithBet(t *testing.T) (spellsByFighter, objectsByFighter [][]int32, betCards []int32) {
	spellsByFighter, objectsByFighter, betCards = cr.readTeamInternal(t)
	return
}

func (cr *createFightReader) readTeam(t *testing.T) (spellsByFighter, objectsByFighter [][]int32) {
	spellsByFighter, objectsByFighter, _ = cr.readTeamInternal(t)
	return
}

func (cr *createFightReader) readTeamInternal(t *testing.T) (spellsByFighter, objectsByFighter [][]int32, betCards []int32) {
	t.Helper()
	r := cr.r
	r.Byte()       // teamId
	_ = r.String() // teamName
	coachCount := r.Byte()
	for c := byte(0); c < coachCount; c++ {
		r.Int64()      // coach id
		_ = r.String() // coach name
		r.Byte()       // skin
		r.Byte()       // hair
		r.Byte()       // sex
		equipLen := r.Uint16()
		r.Bytes(int(equipLen)) // coach equipment blob (unused, always 0 here)

		fighterCount := r.Byte()
		for f := byte(0); f < fighterCount; f++ {
			r.Int64()      // fighter id
			r.Byte()       // breed
			_ = r.String() // name
			r.Byte()       // sex
			r.Byte()       // skin

			spellsLen := r.Uint16()
			spellsBlob := r.Bytes(int(spellsLen))
			spellsByFighter = append(spellsByFighter, parseSpellIDs(spellsBlob))

			objLen := r.Uint16()
			objBlob := r.Bytes(int(objLen))
			objectsByFighter = append(objectsByFighter, parseInventoryIDs(objBlob))
		}
	}
	statsLen := r.Uint16()
	r.Bytes(int(statsLen))
	betCardCount := r.Byte()
	for i := byte(0); i < betCardCount; i++ {
		betCards = append(betCards, r.Int32())
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	return spellsByFighter, objectsByFighter, betCards
}

// TestBuildCreateFightIncludesRealSpellsAndEquipment verifies the fix for
// a reported bug: fighters previously showed up in-fight with no
// spells/equipment at all, even though the player had equipped them,
// because buildCreateFight always hardcoded both blob lengths to 0. This
// confirms real spell/equipment ids (threaded through duelTeamInfo's new
// SpellsByFighter/ObjectsByFighter maps, sourced the same way
// buildCombatTeam already loads them via FighterService.LoadoutMaps) now
// actually appear on the wire, in the exact format documented in
// docs/opcodes/07-fight-lifecycle.md's "Fighter.unserialize(buffer)"
// section (spells: flat int32[]; equipment: [short pos][int32 id] pairs).
func TestBuildCreateFightIncludesRealSpellsAndEquipment(t *testing.T) {
	store := newTestFighterCardStore(map[int32]gamedata.FighterCardTemplate{
		9001: {ID: 9001, Type: gamedata.FighterCardTypeWeapon},
		9002: {ID: 9002, Type: gamedata.FighterCardTypeHat},
	})

	fighterA := domain.Fighter{ID: 100, Breed: 1, Name: "Alice1", Sex: 0, Skin: 0}
	teamA := duelTeamInfo{
		TeamID:   1,
		TeamName: "team1",
		Coach:    &domain.Coach{ID: 1, Name: "Alice"},
		Fighters: []domain.Fighter{fighterA},
		SpellsByFighter: map[uint][]int32{
			100: {501, 502, 503},
		},
		ObjectsByFighter: map[uint][]int32{
			100: {9001, 9002},
		},
	}
	fighterB := domain.Fighter{ID: 200, Breed: 2, Name: "Bob1", Sex: 1, Skin: 0}
	teamB := duelTeamInfo{
		TeamID:   2,
		TeamName: "team2",
		Coach:    &domain.Coach{ID: 2, Name: "Bob"},
		Fighters: []domain.Fighter{fighterB},
		// Bob's fighter has no spells/equipment at all -- must serialize
		// as empty blobs, not crash on a nil map lookup.
	}

	frame := buildCreateFight(1, 0, teamA, teamB, store, nil)
	if frame.Opcode != protocol.SendCreateFight {
		t.Fatalf("opcode = %v, want SendCreateFight", frame.Opcode)
	}

	r := protocol.NewReader(frame.Payload)
	r.Byte()   // error code
	r.Uint16() // coach cards blob length
	r.Int32()  // fight type
	r.Int32()  // bet
	teamCount := r.Byte()
	if teamCount != 2 {
		t.Fatalf("teamCount = %d, want 2", teamCount)
	}

	cr := &createFightReader{r: r}
	spellsA, objectsA := cr.readTeam(t)
	spellsB, objectsB := cr.readTeam(t)

	if len(spellsA) != 1 || len(spellsA[0]) != 3 {
		t.Fatalf("team A fighter 0 spells = %v, want 3 spell ids", spellsA)
	}
	wantSpells := []int32{501, 502, 503}
	for i, want := range wantSpells {
		if spellsA[0][i] != want {
			t.Errorf("team A spell[%d] = %d, want %d", i, spellsA[0][i], want)
		}
	}

	if len(objectsA) != 1 || len(objectsA[0]) != 2 {
		t.Fatalf("team A fighter 0 objects = %v, want 2 object ids", objectsA)
	}
	wantObjects := map[int32]bool{9001: true, 9002: true}
	for _, id := range objectsA[0] {
		if !wantObjects[id] {
			t.Errorf("unexpected object id %d in team A fighter 0's blob", id)
		}
	}

	if len(spellsB) != 1 || len(spellsB[0]) != 0 {
		t.Errorf("team B fighter 0 spells = %v, want empty (no loadout provided)", spellsB)
	}
	if len(objectsB) != 1 || len(objectsB[0]) != 0 {
		t.Errorf("team B fighter 0 objects = %v, want empty (no loadout provided)", objectsB)
	}
}

// TestBuildCreateFightBetCardPayload verifies the card-wagering stake list:
// a team's BetCard is announced in CREATE_FIGHT's per-team bet-card list
// (byte count + count × int32 templateId), and a nil BetCard yields count 0.
func TestBuildCreateFightBetCardPayload(t *testing.T) {
	fighterA := domain.Fighter{ID: 100, Breed: 1, Name: "Alice1"}
	teamA := duelTeamInfo{
		TeamID: 1, TeamName: "team1",
		Coach:    &domain.Coach{ID: 1, Name: "Alice"},
		Fighters: []domain.Fighter{fighterA},
		BetCard:  &domain.CoachCard{ID: 555, TemplateID: 4242}, // staked
	}
	fighterB := domain.Fighter{ID: 200, Breed: 2, Name: "Bob1"}
	teamB := duelTeamInfo{
		TeamID: 2, TeamName: "team2",
		Coach:    &domain.Coach{ID: 2, Name: "Bob"},
		Fighters: []domain.Fighter{fighterB},
		// no BetCard -> count 0
	}

	frame := buildCreateFight(1, 1, teamA, teamB, nil, nil)
	r := protocol.NewReader(frame.Payload)
	r.Byte()   // error code
	r.Uint16() // coach cards blob length
	r.Int32()  // fight type
	if bet := r.Int32(); bet != 1 {
		t.Fatalf("bet flag = %d, want 1", bet)
	}
	if teamCount := r.Byte(); teamCount != 2 {
		t.Fatalf("teamCount = %d, want 2", teamCount)
	}
	cr := &createFightReader{r: r}
	_, _, betA := cr.readTeamWithBet(t)
	_, _, betB := cr.readTeamWithBet(t)

	if len(betA) != 1 || betA[0] != 4242 {
		t.Errorf("team A bet cards = %v, want [4242]", betA)
	}
	if len(betB) != 0 {
		t.Errorf("team B bet cards = %v, want [] (no stake)", betB)
	}
}

// TestBuildCreateFightNilStoreStillIncludesSpells verifies that a nil
// gamedata.Store (e.g. gamedata unavailable) still serializes spells
// correctly (buildSpellBlob needs no store lookup) while gracefully
// degrading equipment to empty rather than panicking -- a fight must
// never crash CREATE_FIGHT just because gamedata lookups are unavailable.
func TestBuildCreateFightNilStoreStillIncludesSpells(t *testing.T) {
	fighterA := domain.Fighter{ID: 100, Breed: 1, Name: "Alice1", Sex: 0, Skin: 0}
	teamA := duelTeamInfo{
		TeamID:   1,
		TeamName: "team1",
		Coach:    &domain.Coach{ID: 1, Name: "Alice"},
		Fighters: []domain.Fighter{fighterA},
		SpellsByFighter: map[uint][]int32{
			100: {501},
		},
		ObjectsByFighter: map[uint][]int32{
			100: {9001},
		},
	}
	teamB := duelTeamInfo{
		TeamID:   2,
		TeamName: "team2",
		Coach:    &domain.Coach{ID: 2, Name: "Bob"},
		Fighters: []domain.Fighter{{ID: 200, Breed: 2, Name: "Bob1", Sex: 1, Skin: 0}},
	}

	frame := buildCreateFight(1, 0, teamA, teamB, nil, nil)

	r := protocol.NewReader(frame.Payload)
	r.Byte()
	r.Uint16()
	r.Int32()
	r.Int32()
	r.Byte()
	cr := &createFightReader{r: r}
	spellsA, objectsA := cr.readTeam(t)
	_, _ = cr.readTeam(t)

	if len(spellsA) != 1 || len(spellsA[0]) != 1 || spellsA[0][0] != 501 {
		t.Errorf("team A spells = %v, want [[501]] (spells unaffected by nil store)", spellsA)
	}
	if len(objectsA) != 1 || len(objectsA[0]) != 0 {
		t.Errorf("team A objects = %v, want empty (nil store can't resolve equipment slot)", objectsA)
	}
}

// TestBuildCreateFightIncludesCoachEquipment verifies the fix for a
// reported "coach equipment doesn't show" bug: CREATE_FIGHT previously
// always wrote an empty coach-equipment blob (length 0). It now serializes
// each equipped coach card (Pos != 0) in the client-expected 15-byte format
// ([int16 slot][int32 templateId][int64 uniqueId][int8 flags]), which
// composes the coach's on-map sprite (hat/cloak/weapon via applyEquipment).
func TestBuildCreateFightIncludesCoachEquipment(t *testing.T) {
	teamA := duelTeamInfo{
		TeamID:   1,
		TeamName: "team1",
		Coach:    &domain.Coach{ID: 1, Name: "Alice"},
		CoachEquipment: []domain.CoachCard{
			{ID: 700, TemplateID: 133, Pos: 4, Flag: 2}, // hat in stored slot 4
			{ID: 701, TemplateID: 9, Pos: 1, Flag: 2},   // weapon in stored slot 1
		},
		Fighters: []domain.Fighter{{ID: 100, Breed: 1, Name: "F", Sex: 0, Skin: 0}},
	}
	teamB := duelTeamInfo{
		TeamID:   2,
		TeamName: "team2",
		Coach:    &domain.Coach{ID: 2, Name: "Bob"}, // no equipment -> empty blob
		Fighters: []domain.Fighter{{ID: 200, Breed: 2, Name: "G", Sex: 1, Skin: 0}},
	}

	frame := buildCreateFight(1, 0, teamA, teamB, nil, nil)
	r := protocol.NewReader(frame.Payload)
	r.Byte()   // error code
	r.Uint16() // coach cards blob length
	r.Int32()  // fight type
	r.Int32()  // bet
	r.Byte()   // team count

	// Team A: read up to and including the coach equipment blob.
	r.Byte()       // team id
	_ = r.String() // team name
	r.Byte()       // coach count
	r.Int64()      // coach id
	_ = r.String() // coach name
	r.Byte()       // skin
	r.Byte()       // hair
	r.Byte()       // sex
	equipLen := r.Uint16()
	if equipLen != 2*15 {
		t.Fatalf("coach A equipment blob length = %d, want %d (2 cards * 15 bytes)", equipLen, 2*15)
	}
	type equip struct {
		slot int16
		tmpl int32
		uid  int64
		flag byte
	}
	var got []equip
	for i := 0; i < 2; i++ {
		got = append(got, equip{r.Int16(), r.Int32(), r.Int64(), r.Byte()})
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	// Stored Pos 4 -> wire slot 3; Pos 1 -> wire slot 0.
	want := []equip{
		{3, 133, 700, 2},
		{0, 9, 701, 2},
	}
	for i := range want {
		if got[i] != want[i] {
			t.Errorf("coach A equipment[%d] = %+v, want %+v", i, got[i], want[i])
		}
	}
}

// TestBuildCreateFightSerializesSpecialCells verifies the CREATE_FIGHT
// special-cell block is written after the two teams + timeline + event
// count, in the exact wire order the client's FightCreationMessage.decode
// expects: [byte count] then per cell [long baseId][long id][int x][int y]
// [short z].
func TestBuildCreateFightSerializesSpecialCells(t *testing.T) {
	fighterA := domain.Fighter{ID: 100, Breed: 1, Name: "Alice1", Sex: 0, Skin: 0}
	fighterB := domain.Fighter{ID: 200, Breed: 2, Name: "Bob1", Sex: 0, Skin: 0}
	teamA := duelTeamInfo{TeamID: 1, TeamName: "team1", Coach: &domain.Coach{ID: 1, Name: "Alice"}, Fighters: []domain.Fighter{fighterA}}
	teamB := duelTeamInfo{TeamID: 2, TeamName: "team2", Coach: &domain.Coach{ID: 2, Name: "Bob"}, Fighters: []domain.Fighter{fighterB}}

	specialCells := []combat.SpecialCellRender{
		{CellBaseID: 1002, CellID: 1, X: 8, Y: 9, Z: -4},
		{CellBaseID: 1, CellID: 2, X: 11, Y: 3, Z: -3},
	}

	frame := buildCreateFight(1, 0, teamA, teamB, nil, specialCells)
	r := protocol.NewReader(frame.Payload)
	r.Byte()   // error code
	r.Uint16() // coach cards blob length
	r.Int32()  // fight type
	r.Int32()  // bet
	if tc := r.Byte(); tc != 2 {
		t.Fatalf("teamCount = %d, want 2", tc)
	}

	cr := &createFightReader{r: r}
	cr.readTeam(t)
	cr.readTeam(t)

	// Timeline fighter list.
	timelineCount := r.Byte()
	for i := byte(0); i < timelineCount; i++ {
		r.Int64()
	}
	// Event list.
	eventCount := r.Byte()
	for i := byte(0); i < eventCount; i++ {
		r.Int32()
	}

	// Special-cell block.
	count := r.Byte()
	if int(count) != len(specialCells) {
		t.Fatalf("special cell count = %d, want %d", count, len(specialCells))
	}
	for i, want := range specialCells {
		got := combat.SpecialCellRender{
			CellBaseID: r.Int64(),
			CellID:     r.Int64(),
			X:          r.Int32(),
			Y:          r.Int32(),
			Z:          r.Int16(),
		}
		if got != want {
			t.Errorf("special cell[%d] = %+v, want %+v", i, got, want)
		}
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
}

// TestBuildCreateFightTimelineIsInitiativeSorted is the regression for the
// "I can only act on ONE of my two fighters" bug. The client adds the
// CREATE_FIGHT timeline fighters to its own timeline in wire order
// (addFighterToTimeline(id, false, false) -- NOT re-sorted by INIT) and then
// only accepts a FIGHTER_TURN_BEGIN whose fighter is at the FRONT of that
// queue (TurnBasedTimeline.askForFighterStartTurn silently no-ops otherwise).
// So the CREATE_FIGHT timeline order MUST equal the combat engine's
// initiative-descending turn order (combat.BuildTurnOrder), or out-of-
// position turns get dropped client-side.
func TestBuildCreateFightTimelineIsInitiativeSorted(t *testing.T) {
	// Deliberately list fighters in NON-initiative order across both teams.
	// Breeds & base INIT (combat.breedTable): Iop=40, Xelor=70, Sadida=60,
	// Sacrier=30. Expected turn order by INIT desc = Xelor(70) > Sadida(60)
	// > Iop(40) > Sacrier(30).
	teamA := duelTeamInfo{
		TeamID: 1, TeamName: "team1", Coach: &domain.Coach{ID: 1, Name: "Alice"},
		Fighters: []domain.Fighter{
			{ID: 100, Breed: combat.BreedIop, Name: "IopA"},     // INIT 40
			{ID: 101, Breed: combat.BreedXelor, Name: "XelorA"}, // INIT 70
		},
	}
	teamB := duelTeamInfo{
		TeamID: 2, TeamName: "team2", Coach: &domain.Coach{ID: 2, Name: "Bob"},
		Fighters: []domain.Fighter{
			{ID: 200, Breed: combat.BreedSacrier, Name: "SacrierB"}, // INIT 30
			{ID: 201, Breed: combat.BreedSadida, Name: "SadidaB"},   // INIT 60
		},
	}

	frame := buildCreateFight(1, 0, teamA, teamB, nil, nil)
	r := protocol.NewReader(frame.Payload)
	r.Byte()   // error code
	r.Uint16() // coach cards blob length
	r.Int32()  // fight type
	r.Int32()  // bet
	r.Byte()   // team count
	cr := &createFightReader{r: r}
	cr.readTeam(t)
	cr.readTeam(t)

	// Timeline block: must be INIT-descending.
	timelineCount := r.Byte()
	got := make([]int64, 0, timelineCount)
	for i := byte(0); i < timelineCount; i++ {
		got = append(got, r.Int64())
	}
	want := []int64{
		combat.FighterWireIDBase + 101, // Xelor 70
		combat.FighterWireIDBase + 201, // Sadida 60
		combat.FighterWireIDBase + 100, // Iop 40
		combat.FighterWireIDBase + 200, // Sacrier 30
	}
	if len(got) != len(want) {
		t.Fatalf("timeline count = %d, want %d", len(got), len(want))
	}
	for i := range want {
		if got[i] != want[i] {
			t.Errorf("timeline[%d] = %d, want %d (order must be INIT-descending to match FIGHTER_TURN_BEGIN)", i, got[i], want[i])
		}
	}

	// And it must exactly match combat.BuildTurnOrder on the equivalent
	// combat.Fighters (same input order teamA-then-teamB), so the wire order
	// and the engine's turn order can never drift apart.
	var cf []*combat.Fighter
	for _, f := range append(append([]domain.Fighter{}, teamA.Fighters...), teamB.Fighters...) {
		cf = append(cf, combat.NewFighterFromBreed(combat.FighterWireIDBase+int64(f.ID), 0, f.Breed, f.Name, 0, 0))
	}
	engineOrder := combat.BuildTurnOrder(cf)
	for i := range engineOrder {
		if engineOrder[i].ID != want[i] {
			t.Errorf("combat.BuildTurnOrder[%d].ID = %d, want %d -- wire timeline and engine turn order disagree", i, engineOrder[i].ID, want[i])
		}
	}
}

// TestSpecialCellTypeByNameMapsAllManualTypes verifies every authored
// special-cell type name maps to a non-None combat.SpecialCellType, and an
// unknown name maps to None (skipped rather than crashing).
func TestSpecialCellTypeByNameMapsAllManualTypes(t *testing.T) {
	cases := map[gamedata.SpecialCellTypeName]combat.SpecialCellType{
		gamedata.SpecialCellTrap:         combat.SpecialCellTrap,
		gamedata.SpecialCellEnthusiasm:   combat.SpecialCellEnthusiasm,
		gamedata.SpecialCellShield:       combat.SpecialCellShield,
		gamedata.SpecialCellEagleEye:     combat.SpecialCellEagleEye,
		gamedata.SpecialCellPanacea:      combat.SpecialCellPanacea,
		gamedata.SpecialCellMotivation:   combat.SpecialCellMotivation,
		gamedata.SpecialCellHealingHeart: combat.SpecialCellHealingHeart,
		gamedata.SpecialCellKiller:       combat.SpecialCellKiller,
	}
	for name, want := range cases {
		if got := specialCellTypeByName(name); got != want {
			t.Errorf("specialCellTypeByName(%q) = %v, want %v", name, got, want)
		}
	}
	if got := specialCellTypeByName("nonsense"); got != combat.SpecialCellNone {
		t.Errorf("specialCellTypeByName(unknown) = %v, want SpecialCellNone", got)
	}
}
