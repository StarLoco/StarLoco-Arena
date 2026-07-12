package combat

import (
	"encoding/binary"
	"testing"
)

// Tests for the END_FIGHT (8300) card-wagering payload (writeCardBlob /
// buildEndFight), parsed back per the reference unserializeCards layout:
//   byte entryCount; repeat: long playerId, byte cardCount,
//   repeat: int refId, byte cursed.

// parseCardBlob decodes a card blob (as embedded in END_FIGHT after its
// int16 length prefix) into a map[playerID][]cardBlobCard, mirroring
// EndFightMessage.unserializeCards.
func parseCardBlob(t *testing.T, b []byte) map[int64][]cardBlobCard {
	t.Helper()
	out := map[int64][]cardBlobCard{}
	if len(b) == 0 {
		return out
	}
	pos := 0
	entryCount := int(b[pos])
	pos++
	for i := 0; i < entryCount; i++ {
		playerID := int64(binary.BigEndian.Uint64(b[pos : pos+8]))
		pos += 8
		cardCount := int(b[pos])
		pos++
		for j := 0; j < cardCount; j++ {
			refID := int32(binary.BigEndian.Uint32(b[pos : pos+4]))
			pos += 4
			cursed := b[pos] != 0
			pos++
			out[playerID] = append(out[playerID], cardBlobCard{TemplateID: refID, Cursed: cursed})
		}
	}
	return out
}

// endFightCardBlobs splits a NON-flee END_FIGHT payload into its lost/won
// card blobs. Layout: header(8) + flee(1) + winnerList + looserList +
// int16 lostLen + lostBlob + int16 wonLen + wonBlob. The player lists are
// variable-length, so we parse them to find the card blobs' offset.
func endFightCardBlobs(t *testing.T, payload []byte) (lost, won map[int64][]cardBlobCard) {
	t.Helper()
	pos := 8 // fight action header (uniqueID + triggeringID)
	if payload[pos] != 0 {
		t.Fatal("expected non-flee END_FIGHT")
	}
	pos++ // flee bool
	skipList := func() {
		count := int(payload[pos])
		pos++
		for i := 0; i < count; i++ {
			pos += 8 // playerId
			pos += 2 // strength
			reportLen := int(int16(binary.BigEndian.Uint16(payload[pos : pos+2])))
			pos += 2
			pos += reportLen
		}
	}
	skipList() // winners
	skipList() // losers

	lostLen := int(int16(binary.BigEndian.Uint16(payload[pos : pos+2])))
	pos += 2
	lost = parseCardBlob(t, payload[pos:pos+lostLen])
	pos += lostLen

	wonLen := int(int16(binary.BigEndian.Uint16(payload[pos : pos+2])))
	pos += 2
	won = parseCardBlob(t, payload[pos:pos+wonLen])
	return lost, won
}

func TestBuildEndFight_CardPayload(t *testing.T) {
	winners := []endFightPlayerResult{{PlayerID: 100, Strength: 1500}}
	losers := []endFightPlayerResult{{PlayerID: 200, Strength: 1400}}
	lostCards := []cardBlobEntry{{PlayerID: 200, Cards: []cardBlobCard{{TemplateID: 42, Cursed: false}}}}
	wonCards := []cardBlobEntry{{PlayerID: 100, Cards: []cardBlobCard{{TemplateID: 42, Cursed: false}}}}

	frame := buildEndFight(1, false, winners, losers, lostCards, wonCards)
	lost, won := endFightCardBlobs(t, frame.Payload)

	if got := lost[200]; len(got) != 1 || got[0].TemplateID != 42 {
		t.Errorf("lost cards for coach 200 = %v, want [{42 false}]", got)
	}
	if got := won[100]; len(got) != 1 || got[0].TemplateID != 42 {
		t.Errorf("won cards for coach 100 = %v, want [{42 false}]", got)
	}
}

func TestBuildEndFight_CursedFlagRoundTrips(t *testing.T) {
	winners := []endFightPlayerResult{{PlayerID: 100}}
	losers := []endFightPlayerResult{{PlayerID: 200}}
	lostCards := []cardBlobEntry{{PlayerID: 200, Cards: []cardBlobCard{{TemplateID: 7, Cursed: true}}}}
	wonCards := []cardBlobEntry{{PlayerID: 100, Cards: []cardBlobCard{{TemplateID: 7, Cursed: true}}}}

	frame := buildEndFight(1, false, winners, losers, lostCards, wonCards)
	lost, won := endFightCardBlobs(t, frame.Payload)

	if !lost[200][0].Cursed {
		t.Error("lost card cursed flag did not survive the wire")
	}
	if !won[100][0].Cursed {
		t.Error("won card cursed flag did not survive the wire")
	}
}

func TestBuildEndFight_NoCardsEmitsEmptyBlobs(t *testing.T) {
	winners := []endFightPlayerResult{{PlayerID: 100}}
	losers := []endFightPlayerResult{{PlayerID: 200}}
	frame := buildEndFight(1, false, winners, losers, nil, nil)
	lost, won := endFightCardBlobs(t, frame.Payload)
	if len(lost) != 0 || len(won) != 0 {
		t.Errorf("no-bet fight should have empty card blobs, got lost=%v won=%v", lost, won)
	}
}

func TestCardBlobEntryFor_OmitsEmpty(t *testing.T) {
	if _, ok := cardBlobEntryFor(100, nil); ok {
		t.Error("empty card list should produce no entry")
	}
	entry, ok := cardBlobEntryFor(100, []FightEndCard{{TemplateID: 5, Cursed: true}})
	if !ok {
		t.Fatal("non-empty card list should produce an entry")
	}
	if entry.PlayerID != 100 || len(entry.Cards) != 1 || entry.Cards[0].TemplateID != 5 || !entry.Cards[0].Cursed {
		t.Errorf("entry = %+v, want playerID 100 with one cursed template-5 card", entry)
	}
}
