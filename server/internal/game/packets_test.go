package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestFriendListCountMatchesBlobsWithNilJoin verifies the friend-list count
// equals the number of blobs actually written even when a friend row has a nil
// joined coach (deleted account) — otherwise the client under-reads and login
// aborts.
func TestFriendListCountMatchesBlobsWithNilJoin(t *testing.T) {
	real := &domain.Coach{ID: 2, Name: "Alice"}
	c := &domain.Coach{
		ID: 1, Name: "Owner",
		Friends: []domain.CoachFriend{
			{OwnerID: 1, FriendID: 2, Friend: real}, // valid
			{OwnerID: 1, FriendID: 9, Friend: nil},  // nil join (deleted)
		},
	}
	frame, err := buildFriendList(c, NewRegistry(75))
	if err != nil {
		t.Fatalf("buildFriendList: %v", err)
	}
	// payload[0] = u8 count; must equal 1 (only the valid friend).
	if got := frame[4]; got != 1 {
		t.Errorf("friend count = %d, want 1 (nil join must be excluded)", got)
	}
}

// TestIgnoreListCountMatchesWithNilJoin: same guarantee for the ignore list.
func TestIgnoreListCountMatchesWithNilJoin(t *testing.T) {
	real := &domain.Coach{ID: 3, Name: "Bob"}
	c := &domain.Coach{
		ID: 1, Name: "Owner",
		Ignored: []domain.CoachIgnored{
			{OwnerID: 1, IgnoredID: 3, Ignored: real},
			{OwnerID: 1, IgnoredID: 9, Ignored: nil},
		},
	}
	frame, err := buildIgnoreList(c)
	if err != nil {
		t.Fatalf("buildIgnoreList: %v", err)
	}
	if got := frame[4]; got != 1 {
		t.Errorf("ignore count = %d, want 1 (nil join must be excluded)", got)
	}
}

// TestPlayerStatisticsReportFields verifies the 2400 report uses the field ids
// the client's PlayerStatisticsReport reads (3=fights, 4=won, 5=lost, 7=consec
// wins, 8=consec losses, 1=playTime, 2=fightTime) and does NOT emit the bogus
// field 6 (an internal model value, not the ladder strength).
func TestPlayerStatisticsReportFields(t *testing.T) {
	c := &domain.Coach{
		ID: 1, Name: "Statto",
		StatFights: 10, StatWins: 6, StatLosses: 4,
		ConsecutiveWins: 3, ConsecutiveLosses: 1,
		TimeInFightSecs: 120, TotalPlaySecs: 3600,
		Strength: 1500,
	}
	frame, err := buildPlayerStatisticsReport(c)
	if err != nil {
		t.Fatalf("buildPlayerStatisticsReport: %v", err)
	}
	// frame = [u16 len][u16 op][payload]; payload = [u16 blobLen][blob].
	payload := frame[4:]
	r := protocol.NewReader(payload[2:]) // skip the blobLen prefix
	if mid, _ := r.U16(); mid != 1 {
		t.Errorf("model id = %d, want 1", mid)
	}
	if rid, _ := r.I64(); rid != 1 {
		t.Errorf("report id = %d, want 1", rid)
	}
	n, _ := r.U16()
	ints := map[uint16]int32{}
	longs := map[uint16]int64{}
	for i := 0; i < int(n); i++ {
		fid, _ := r.U16()
		typ, _ := r.U8()
		switch typ {
		case 1:
			v, _ := r.I32()
			ints[fid] = v
		case 2:
			v, _ := r.I64()
			longs[fid] = v
		default:
			t.Fatalf("unexpected type code %d for field %d", typ, fid)
		}
	}
	if longs[1] != 3600 {
		t.Errorf("field 1 (playTime) = %d, want 3600", longs[1])
	}
	if longs[2] != 120 {
		t.Errorf("field 2 (fightTime) = %d, want 120", longs[2])
	}
	if ints[3] != 10 || ints[4] != 6 || ints[5] != 4 {
		t.Errorf("fights/won/lost = %d/%d/%d, want 10/6/4", ints[3], ints[4], ints[5])
	}
	if ints[7] != 3 {
		t.Errorf("field 7 (consec wins) = %d, want 3", ints[7])
	}
	if ints[8] != 1 {
		t.Errorf("field 8 (consec losses) = %d, want 1", ints[8])
	}
	if _, has := ints[6]; has {
		t.Error("field 6 must NOT be emitted (it is an internal model value, not strength)")
	}
}

// TestFighterListLeadIsTimestamp: the 6006 leading i64 must be a plausible
// server timestamp (seconds), NOT the coach id — the client uses it to compute
// each fighter's form as (now - lead)/3600 hours, so a small value (like a
// coach id) corrupts the roster and it fails to render on reopen.
func TestFighterListLeadIsTimestamp(t *testing.T) {
	frame, err := buildFighterList(2, []domain.Fighter{{ID: 1, Name: "F", BreedID: 3, Budget: 400}})
	if err != nil {
		t.Fatalf("buildFighterList: %v", err)
	}
	// payload = [i64 lead][u8 count]...
	payload := frame[4:]
	var lead int64
	for i := 0; i < 8; i++ {
		lead = lead<<8 | int64(payload[i])
	}
	// A real unix timestamp is ~1.7e9; the coach id (2) would be the bug.
	if lead < 1_000_000_000 {
		t.Errorf("6006 lead = %d, want a unix timestamp (>1e9), not the coach id", lead)
	}
}

// TestBenchTeamPresetIsEmpty: the 6030 team list leads with a type=-4 team that
// must be EMPTY. A -4 team listing fighters would hide them from the Elite
// available-fighters grid (the client's U/Z filter excludes any fighter that
// belongs to a team in the 6030). It exists only so the client's Evolution
// first-open handler (ce_1) can safely do arrayList.get(0).
func TestBenchTeamPresetIsEmpty(t *testing.T) {
	blob := benchTeamPreset()
	r := protocol.NewReader(blob)
	typ, _ := r.U16()
	if int16(typ) != -4 {
		t.Errorf("bench team type = %d, want -4", int16(typ))
	}
	_, _ = r.U16()      // teamId
	_, _ = r.U16()      // gameMode
	_, _ = r.StringU8() // name
	fCount, _ := r.U8()
	if fCount != 0 {
		t.Errorf("bench fighter count = %d, want 0 (must not reference fighters)", fCount)
	}
	coachCount, _ := r.U8()
	if coachCount != 0 {
		t.Errorf("bench coach count = %d, want 0", coachCount)
	}
	if r.Remaining() != 0 {
		t.Errorf("bench team has %d trailing bytes, want 0", r.Remaining())
	}
}

// TestWriteCoachActorLayout locks in the exact byte layout the client's
// aez_0.b(bb, 3179) reader expects for a Coach actor inside ActorSpawn (4096).
// A single missing/extra byte makes the client BufferUnderflow and drop the
// actor, so other coaches never spawn.
func TestWriteCoachActorLayout(t *testing.T) {
	w := protocol.NewWriter()
	v := CoachView{ID: 0x0102030405060708, Name: "Bob", Hair: 1, Skin: 2, Sex: 0,
		PosX: 10, PosY: 20, PosZ: 3}
	writeCoachActor(w, v)
	got := w.Bytes()

	// Expected size:
	//  V:   i64(8) + u8 len(1) + name(3)          = 12
	//  U:   i32(4)+i32(4)+i16(2)+u8(1)            = 11
	//  T:   u8+u8+u8(3) + i16(2)                  = 5
	//  bMU: i32(4)                                = 4
	//  dBg: u8(1)                                 = 1
	//  W:   i16(2)                                = 2
	//  S:   i16(2)                                = 2
	//  X:   u8(1)                                 = 1
	//  Y:   i32(4)                                = 4
	// total = 12+11+5+4+1+2+2+1+4 = 42
	const want = 42
	if len(got) != want {
		t.Fatalf("coach actor record = %d bytes, want %d", len(got), want)
	}

	// Spot-check the header: i64 id big-endian, then u8 nameLen=3, "Bob".
	if got[0] != 0x01 || got[7] != 0x08 {
		t.Errorf("id bytes wrong: % x", got[0:8])
	}
	if got[8] != 3 || string(got[9:12]) != "Bob" {
		t.Errorf("name field wrong: len=%d name=%q", got[8], got[9:12])
	}
}

// TestActorSpawnFraming verifies the 4096 outer framing: [i32 -bodyLen][body]
// (negative prefix = raw stored / uncompressed) with [i32 count] then actors.
func TestActorSpawnFraming(t *testing.T) {
	views := []CoachView{
		{ID: 1, Name: "A", Hair: 0, Skin: 0, Sex: 0, PosX: 1, PosY: 1},
		{ID: 2, Name: "B", Hair: 0, Skin: 0, Sex: 0, PosX: 2, PosY: 2},
	}
	frame, err := buildActorSpawn(views)
	if err != nil {
		t.Fatalf("buildActorSpawn: %v", err)
	}
	// frame = [u16 len][u16 op=4096][payload]
	op := uint16(frame[2])<<8 | uint16(frame[3])
	if op != protocol.OpActorSpawn {
		t.Fatalf("opcode = %d, want %d", op, protocol.OpActorSpawn)
	}
	payload := frame[4:]
	// payload[0:4] = i32 negative body length (stored form).
	prefix := int32(uint32(payload[0])<<24 | uint32(payload[1])<<16 |
		uint32(payload[2])<<8 | uint32(payload[3]))
	if prefix >= 0 {
		t.Errorf("stored-form prefix should be negative, got %d", prefix)
	}
	if int(-prefix) != len(payload)-4 {
		t.Errorf("prefix %d != body len %d", -prefix, len(payload)-4)
	}
	// body starts with i32 count = 2.
	body := payload[4:]
	count := int32(uint32(body[0])<<24 | uint32(body[1])<<16 |
		uint32(body[2])<<8 | uint32(body[3]))
	if count != 2 {
		t.Errorf("actor count = %d, want 2", count)
	}
}

// TestWriteCoachActorCarriesStanding: the bMU field in a Coach actor is the
// coach's evolution experience, from which the client renders its evolution
// level. It was hardcoded to 0, so every OTHER coach visible in the world showed
// as level 1 regardless of what they had earned.
//
// Offset: V (i64 id 8 + u8 len 1 + "Bob" 3) + U (4+4+2+1) + T (1+1+1+2) = 28.
func TestWriteCoachActorCarriesStanding(t *testing.T) {
	const want int32 = 0x0A0B0C0D
	w := protocol.NewWriter()
	writeCoachActor(w, CoachView{ID: 1, Name: "Bob", PosX: 10, PosY: 20, PosZ: 3, Standing: want})
	got := w.Bytes()

	const off = 12 + 11 + 5
	if len(got) < off+4 {
		t.Fatalf("actor record too short (%d bytes)", len(got))
	}
	v := int32(uint32(got[off])<<24 | uint32(got[off+1])<<16 | uint32(got[off+2])<<8 | uint32(got[off+3]))
	if v != want {
		t.Errorf("standing on the wire = %#x, want %#x", v, want)
	}
}
