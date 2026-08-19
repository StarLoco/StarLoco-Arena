package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// captureFight attaches a spectator whose send queue we can drain, so a test can
// assert on the FRAMES a fight actually put on the wire rather than on its
// internal state. The two differ exactly where these bugs live.
func captureFight(t *testing.T, f *Fight) func() [][]byte {
	t.Helper()
	s := &Session{out: make(chan []byte, 64), quit: make(chan struct{})}
	f.spectators = append(f.spectators, s)
	return func() [][]byte {
		var out [][]byte
		for {
			select {
			case fr := <-s.out:
				out = append(out, fr)
			default:
				return out
			}
		}
	}
}

// runningEffectFrames filters captured frames down to 8120 with the given action
// id, returning each one's decoded blob parts.
func runningEffectFrames(t *testing.T, frames [][]byte, actionID int32) []map[uint8][]byte {
	t.Helper()
	var out []map[uint8][]byte
	for _, fr := range frames {
		if len(fr) < 24 {
			continue
		}
		if op := uint16(fr[2])<<8 | uint16(fr[3]); op != uint16(protocol.OpRunningEffect) {
			continue
		}
		payload := fr[4:]
		if be32(payload[14:18]) != actionID {
			continue
		}
		out = append(out, parseBinarSerial(t, blobOf(t, fr)))
	}
	return out
}

// TestPushBroadcastCarriesItsDestination is the call-site half of the part-3
// work: buildRunningEffect can serialise a destination perfectly and the shove
// still land nowhere if applyPushPull forgets to attach it, or attaches the cell
// the victim came FROM.
//
// The client applies part 3 verbatim (`na_2.java:57` m(bzW)) and never recomputes
// it on the wire path, so this frame is the single source of truth for where the
// fighter ends up on screen. If it disagrees with the server's own victim.Pos the
// two silently desync — and no state-only assertion can see it.
func TestPushBroadcastCarriesItsDestination(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)

	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{3}}, victim.Pos, true)

	if victim.Pos.X != 9 {
		t.Fatalf("victim.x = %d, want 9 (pushed 3 east)", victim.Pos.X)
	}
	got := runningEffectFrames(t, drain(), 37)
	if len(got) != 1 {
		t.Fatalf("got %d push broadcasts, want 1", len(got))
	}
	p3, ok := got[0][3]
	if !ok {
		t.Fatal("push broadcast has no part 3; the client would move the fighter to " +
			"a null/stale cell (na_2.java:54 NPE or :57 m(null))")
	}
	if x, y := be32(p3[0:4]), be32(p3[4:8]); x != victim.Pos.X || y != victim.Pos.Y {
		t.Errorf("part 3 destination = (%d,%d) but the server moved the victim to (%d,%d)",
			x, y, victim.Pos.X, victim.Pos.Y)
	}
}

// TestPushBroadcastNamesTheBlocker pins the second half of part 3: when the shove
// is stopped by another fighter, that fighter's id rides along so the client can
// play the impact against it (`hk_0.f` resolves it via gW().cL(id)).
func TestPushBroadcastNamesTheBlocker(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	blocker := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim, blocker}},
	}}
	drain := captureFight(t, f)

	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{4}}, victim.Pos, true)

	if victim.Pos.X != 7 {
		t.Fatalf("victim.x = %d, want 7 (blocked at 8)", victim.Pos.X)
	}
	got := runningEffectFrames(t, drain(), 37)
	if len(got) == 0 {
		t.Fatal("no push broadcast")
	}
	p3 := got[0][3]
	if p3 == nil {
		t.Fatal("push broadcast has no part 3")
	}
	if id := be64(p3[10:18]); id != blocker.WireID {
		t.Errorf("part 3 collided fighter = %d, want %d (the blocker)", id, blocker.WireID)
	}
}

// TestUnblockedPushReportsNoCollider is the negative: an unobstructed shove must
// send 0, not a stale id. hk_0.f only resolves a fighter when the field is
// non-zero, so a leftover would make the client animate an impact that never
// happened.
func TestUnblockedPushReportsNoCollider(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)
	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{2}}, victim.Pos, true)
	got := runningEffectFrames(t, drain(), 37)
	if len(got) == 0 {
		t.Fatal("no push broadcast")
	}
	if id := be64(got[0][3][10:18]); id != 0 {
		t.Errorf("collided fighter = %d, want 0 (nothing blocked the shove)", id)
	}
}
