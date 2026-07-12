package dispatch

import (
	"math"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// serializeFighter mirrors the client's FighterInformation.serialize()
// layout (client common/game/fighter/FighterInformation.java:177-203):
//
//	byte  version marker (1)
//	short budget
//	byte  breed
//	byte  nameLen; name
//	byte  sex
//	byte  skin
//	short spellsInvLen; spellsInv[]   // flat int32[] blob, see inventory_codec.go
//	short cardsInvLen;  cardsInv[]    // [short pos][int32 id] format, pos = equipment slot
func serializeFighter(store *gamedata.Store, f domain.Fighter, spellIDs, objectIDs []int32) []byte {
	spellBlob := buildSpellBlob(spellIDs)
	objectBlob := buildInventoryBlob(store, objectIDs)

	w := protocol.NewWriter(11 + len(f.Name) + len(spellBlob) + len(objectBlob))
	w.PutByte(1) // version marker
	w.PutInt16(f.Budget).PutByte(f.Breed)
	w.PutString(f.Name)
	w.PutByte(f.Sex).PutByte(f.Skin)

	w.PutUint16(uint16(len(spellBlob)))
	w.PutBytes(spellBlob)
	w.PutUint16(uint16(len(objectBlob)))
	w.PutBytes(objectBlob)
	return w.Bytes()
}

// buildFighterCreateResult serializes FIGHTER_CREATE_RESULT, see
// docs/02-protocol.md §2.4.8 / FighterCreateRequest.java:63-66.
func buildFighterCreateResult(fighterID uint, serialized []byte) protocol.OutboundFrame {
	w := protocol.NewWriter(11 + len(serialized))
	w.PutByte(0) // error code
	w.PutInt64(int64(fighterID))
	w.PutUint16(uint16(len(serialized)))
	w.PutBytes(serialized)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterCreateResult, Payload: w.Bytes()}
}

// buildFighterCreateError serializes an error-code FIGHTER_CREATE_RESULT.
func buildFighterCreateError() protocol.OutboundFrame {
	w := protocol.NewWriter(1)
	w.PutByte(1)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterCreateResult, Payload: w.Bytes()}
}

// buildFighterDeletionResult serializes FIGHTER_DELETION_RESULT, see
// FighterDeleteRequest.java:23,25.
func buildFighterDeletionResult(ok bool, fighterID uint) protocol.OutboundFrame {
	if !ok {
		w := protocol.NewWriter(1)
		w.PutByte(1)
		return protocol.OutboundFrame{Opcode: protocol.SendFighterDeletionResult, Payload: w.Bytes()}
	}
	w := protocol.NewWriter(9)
	w.PutByte(0).PutInt64(int64(fighterID))
	return protocol.OutboundFrame{Opcode: protocol.SendFighterDeletionResult, Payload: w.Bytes()}
}

// fighterWithLoadout pairs a persisted Fighter with its equipped spell and
// object template IDs, needed to serialize the full FIGHTER_INFORMATION_LIST
// entry.
type fighterWithLoadout struct {
	Fighter   domain.Fighter
	SpellIDs  []int32
	ObjectIDs []int32
}

// buildFighterInformationList serializes FIGHTER_INFORMATION_LIST.
//
// NOTE: the legacy server has two different senders for this same opcode
// that disagree on payload detail -- FightInformationListRequest.java
// always zeroes the spell/object arrays (a real bug: the "fighter list"
// screen shows fighters with no equipped loadout), while
// TeamPresetListRequest.java sends the fighter's true `serialize()` output
// (spells/objects included). This port fixes the inconsistency by always
// including the real loadout, since that's strictly more correct and the
// client only ever reads what it needs.
func buildFighterInformationList(store *gamedata.Store, fighters []fighterWithLoadout) protocol.OutboundFrame {
	size := 1
	for _, fw := range fighters {
		size += 8 + 2 + 11 + len(fw.Fighter.Name) + len(fw.SpellIDs)*4 + len(fw.ObjectIDs)*4
	}

	w := protocol.NewWriter(size)
	w.PutByte(byte(len(fighters)))
	for _, fw := range fighters {
		serialized := serializeFighter(store, fw.Fighter, fw.SpellIDs, fw.ObjectIDs)
		w.PutInt64(int64(fw.Fighter.ID))
		w.PutUint16(uint16(len(serialized)))
		w.PutBytes(serialized)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendFighterInformationList, Payload: w.Bytes()}
}

// buildFighterUpdatedInventory serializes
// FIGHTER_UPDATED_INFORMATION_INVENTORY. The client's
// UpdatedFighterInformationInventoryMessage.decode reads:
//
//	long  fighterId
//	byte  errorCode (0 = ok)
//	short spellInvLen; spellInv[]   // flat int32[] blob (see inventory_codec.go)
//	short cardInvLen;  cardInv[]    // [short pos][int32 id] pairs, pos = equipment slot
func buildFighterUpdatedInventory(store *gamedata.Store, fighterID uint, spellIDs, objectIDs []int32) protocol.OutboundFrame {
	spellBlob := buildSpellBlob(spellIDs)
	objectBlob := buildInventoryBlob(store, objectIDs)

	w := protocol.NewWriter(13 + len(spellBlob) + len(objectBlob))
	w.PutInt64(int64(fighterID)).PutByte(0)
	w.PutUint16(uint16(len(spellBlob)))
	w.PutBytes(spellBlob)
	w.PutUint16(uint16(len(objectBlob)))
	w.PutBytes(objectBlob)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterUpdatedInformationInventory, Payload: w.Bytes()}
}

func buildFighterUpdateError(fighterID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutInt64(int64(fighterID)).PutByte(1)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterUpdatedInformationInventory, Payload: w.Bytes()}
}

// maxFighterSpells is the fighter spell-inventory capacity, mirroring the
// client's StackInventory((short)6, ...) in Fighter.java:256 -- a fighter
// may equip at most 6 distinct spells.
const maxFighterSpells = 6

// validateFighterSpells filters a client-submitted spell list to the set a
// fighter of the given breed may legally equip, enforcing the same rules
// the client's SpellInventoryChecker/StackInventory enforce (which a MITM
// client bypasses):
//
//   - the spell must exist in game data (unknown ids silently dropped,
//     matching the legacy skip-on-unknown behavior);
//   - the spell's BreedID must equal the fighter's breed
//     (AbstractSpellInventoryChecker.canAddItem -> INVALID_BREED);
//   - no duplicate spell ids (StackInventory non-stackable, unique
//     uniqueId == spell id);
//   - at most maxFighterSpells (6) spells (StackInventory capacity).
//
// Extra/invalid entries are dropped rather than rejecting the whole request,
// preserving the legacy silent-skip semantics while making the persisted
// loadout server-authoritative.
func validateFighterSpells(store *gamedata.Store, breed byte, ids []int32) []int32 {
	out := make([]int32, 0, len(ids))
	seen := make(map[int32]bool, len(ids))
	for _, id := range ids {
		if len(out) >= maxFighterSpells {
			break
		}
		if seen[id] {
			continue
		}
		spell, ok := store.Spells.Get(id)
		if !ok {
			continue
		}
		if spell.BreedID != int32(breed) {
			continue // spell belongs to another breed -- not equippable
		}
		seen[id] = true
		out = append(out, id)
	}
	return out
}

// validateFighterObjects filters a client-submitted equipment-card list to
// the set a fighter may legally equip, enforcing the client's
// AbstractFighterCardInventoryChecker rules (which a MITM client bypasses):
//
//   - the card must exist in game data (unknown ids silently dropped);
//   - the card's Type must be a real equipment category (weapon/pet/cloak/
//     hat/dofus); anything else has no valid inventory slot and is dropped;
//   - at most one card per equipment slot/type (the ArrayInventory keys by
//     the type's fixed position, so a second card of the same type would
//     overwrite the slot) -- the first card of each type wins.
func validateFighterObjects(store *gamedata.Store, ids []int32) []int32 {
	out := make([]int32, 0, len(ids))
	usedSlot := make(map[int16]bool, len(ids))
	for _, id := range ids {
		card, ok := store.FighterCards.Get(id)
		if !ok {
			continue
		}
		slot, ok := gamedata.FighterCardInventoryPosition(card.Type)
		if !ok {
			continue // not a real equipment type -- no valid slot
		}
		if usedSlot[slot] {
			continue // already have a card in this slot -- keep the first
		}
		usedSlot[slot] = true
		out = append(out, id)
	}
	return out
}

// computeFighterBudget recomputes a fighter's point "value" server-side
// from its breed plus its equipped spells and cards, mirroring
// AbstractFighter.computeValue() (client common/game/fighter/
// AbstractFighter.java:347-361): value = breed.getValue()
// + Sum(spell.goldValue) + Sum(card.goldValue). The result is stored
// instead of trusting the client-supplied budget field (which a MITM
// client can forge to claim a cheaper-than-real, over-cap team). The
// value is a short in the wire format, so it is clamped to the int16 range.
func computeFighterBudget(store *gamedata.Store, breed byte, spellIDs, objectIDs []int32) int16 {
	var value int32
	if stats, ok := combat.GetBreedStats(breed); ok {
		value += stats.Value
	}
	for _, id := range spellIDs {
		if spell, ok := store.Spells.Get(id); ok {
			value += spell.Price
		}
	}
	for _, id := range objectIDs {
		if card, ok := store.FighterCards.Get(id); ok {
			value += card.Value
		}
	}
	if value > math.MaxInt16 {
		value = math.MaxInt16
	}
	if value < math.MinInt16 {
		value = math.MinInt16
	}
	return int16(value)
}

// MaxTeamValue is the maximum total point value a fighter team may have,
// mirroring the client's hardcoded cap (UIRandomFightTeamManagementFrame.
// java:74 `if (teamValue > 5000)`). A team over this is rejected at
// fight-creation time -- the client greys the "ready" button past it, but a
// MITM client can bypass that, so the server must enforce it too.
const MaxTeamValue = 5000

// computeTeamValue is a byte-for-byte port of the client's
// EditableTeamPreset.getValue() (client core/game/team/
// EditableTeamPreset.java:40-77): the sum of every fighter's own value plus
// a per-breed "duplicate" surcharge. Each fighter's own value is its stored
// Budget (== AbstractFighter.computeValue() == breed base + spell/card
// values), which the server recomputes and persists in computeFighterBudget
// (so a forged client budget never reaches here).
//
// The per-breed surcharge loop is deliberately preserved verbatim, including
// its self-referential `previousValue += previousValue + b1*100` growth
// (which is a quirk of the original code, NOT a bug to "fix"): for a breed
// appearing `count` times, iterate b1 from 1..count-1 accumulating
// previousValue, then add previousValue to the total.
func computeTeamValue(fighters []domain.Fighter) int {
	value := 0
	breedCount := make(map[byte]int)
	for _, f := range fighters {
		value += int(f.Budget)
		breedCount[f.Breed]++
	}
	for _, count := range breedCount {
		previousValue := 0
		for b1 := 1; b1 <= count-1; b1++ {
			previousValue += previousValue + b1*100
		}
		value += previousValue
	}
	return value
}
