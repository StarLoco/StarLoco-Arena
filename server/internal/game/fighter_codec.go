package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// FighterBlob is the decoded et_2 fighter serialization (the info fields the
// server needs; the optional type-2 combat block is decoded-and-ignored).
type FighterBlob struct {
	Type     uint8 // 1 = info, 2 = combat
	Budget   int16 // aRo (client-advertised; server recomputes)
	BreedID  uint8
	Name     string
	Sex      uint8
	Hair     uint8
	Skin     uint8
	Eye      uint8
	SpellIDs []int32
	Cards    []FighterCardRef
}

// FighterCardRef is one equipped card: slot position + template id.
type FighterCardRef struct {
	Slot int16
	ID   int32
}

// decodeFighterBlob parses an et_2 blob (big-endian). Layout:
//
//	[u8 type][i16 aRo][u8 breed][i32 aRk if breed==0][u8 nameLen][name]
//	[u8 sex][i8 ey][u8 hair][u8 skin][u8 eye  -- colors only if ey<0]
//	[i16 spellLen][spells: i32*][i16 cardLen][cards: {i16 pos, i32 id}*]
//	[type-2 combat block if type==2 -- ignored]
//
// et_2 blob type byte (client field aIm): 1 = classic fighter, 2 = evolution
// fighter (which carries the trailing evolution block).
const (
	fighterBlobTypeClassic   uint8 = 1
	fighterBlobTypeEvolution uint8 = 2
)

func decodeFighterBlob(data []byte) (*FighterBlob, error) {
	r := protocol.NewReader(data)
	fb := &FighterBlob{}

	typ, err := r.U8()
	if err != nil {
		return nil, err
	}
	fb.Type = typ

	budget, err := r.U16()
	if err != nil {
		return nil, err
	}
	fb.Budget = int16(budget)

	breed, err := r.U8()
	if err != nil {
		return nil, err
	}
	fb.BreedID = breed
	if breed == 0 {
		if _, err := r.I32(); err != nil { // aRk custom-breed id (skip)
			return nil, err
		}
	}

	name, err := r.StringU8()
	if err != nil {
		return nil, err
	}
	fb.Name = name

	sex, err := r.U8()
	if err != nil {
		return nil, err
	}
	fb.Sex = sex

	ey, err := r.U8()
	if err != nil {
		return nil, err
	}
	if int8(ey) < 0 { // colors present
		if fb.Hair, err = r.U8(); err != nil {
			return nil, err
		}
		if fb.Skin, err = r.U8(); err != nil {
			return nil, err
		}
		if fb.Eye, err = r.U8(); err != nil {
			return nil, err
		}
	} else { // absent -> client defaults
		fb.Hair, fb.Skin, fb.Eye = 1, 2, 1
	}

	// spell blob: flat i32 list
	spellLen, err := r.U16()
	if err != nil {
		return nil, err
	}
	for i := 0; i < int(spellLen)/4; i++ {
		id, err := r.I32()
		if err != nil {
			return nil, err
		}
		fb.SpellIDs = append(fb.SpellIDs, id)
	}

	// card blob: {i16 pos, i32 id} pairs
	cardLen, err := r.U16()
	if err != nil {
		return nil, err
	}
	for i := 0; i < int(cardLen)/6; i++ {
		pos, err := r.U16()
		if err != nil {
			return nil, err
		}
		id, err := r.I32()
		if err != nil {
			return nil, err
		}
		fb.Cards = append(fb.Cards, FighterCardRef{Slot: int16(pos), ID: id})
	}

	// type-2 combat block intentionally ignored (not needed for CRUD).
	return fb, nil
}

// encodeFighterBlob serializes a persisted fighter as an et_2 blob, always
// emitting the ey=-1 + 3-color form.
//
// A classic fighter is type 1 (no trailing block). A fighter in EVOLUTION mode
// (domain.Fighter.IsEvolution) is type 2 and carries the evolution tail written by
// writeEvolutionTail — that type byte is what makes the client file it into the
// evolution roster, and hence into the graveyard when its state says so.
func encodeFighterBlob(f *domain.Fighter) []byte {
	w := protocol.NewWriter()
	blobType := fighterBlobTypeClassic
	if f.IsEvolution() {
		blobType = fighterBlobTypeEvolution
	}
	w.U8(blobType)
	w.U16(uint16(f.Budget)) // aRo
	w.U8(f.BreedID)
	// breed != 0 here (0 is the special custom breed we don't create), so no aRk.
	w.StringU8(f.Name)
	w.U8(f.Sex)
	w.U8(0xFF) // ey = -1 => colors follow
	w.U8(f.Hair)
	w.U8(f.Skin)
	w.U8(f.Eye)

	// spell blob
	spellBlob := protocol.NewWriter()
	for _, sp := range f.Spells {
		spellBlob.I32(sp.SpellID)
	}
	sb := spellBlob.Bytes()
	w.U16(uint16(len(sb)))
	w.Raw(sb)

	// card blob — same 5-slot filter as the fight blob (see equipForWire); the
	// roster inventory is the same `en_1` on the client side.
	cardBlob := protocol.NewWriter()
	for _, obj := range equipForWire(f.Objects) {
		cardBlob.U16(uint16(obj.Slot)).I32(obj.TemplateID)
	}
	cb := cardBlob.Bytes()
	w.U16(uint16(len(cb)))
	w.Raw(cb)

	if blobType == 2 {
		writeEvolutionTail(w, f)
	}
	return w.Bytes()
}

// writeEvolutionTail appends the type-2 ("evolution") block of an et_2 blob:
//
//	[i32 sphereBoardId][i32 xp][i32 totalXp][u8 tiredness][u8 morale][u8 state]
//	[i16 sphereX][i16 sphereY]
//	[i16 sphereCount]{[i32 sphereId]}
//	[u8  conditionCount]{[i16 conditionId][u8 level]}
//	[i16 passiveCount]{[i32 passiveId]}
//	[i16 passiveSetCount]{[i32 passiveSetId]}
//
// The STATE byte is the one that matters here: the client's graveyard list is
// exactly the evolution fighters whose state is 3.
//
// The client parses this tail inside a try/catch that silently DOWNGRADES the
// fighter to type 1 on any error — so a short or malformed tail doesn't error,
// it just makes the fighter vanish from the evolution roster. Keep every count
// present even when zero.
//
// Sphere boards and passives are not modelled yet; they are emitted as empty
// lists. CONDITIONS are real (gamedata type 902) — the client keys them into
// `et_2.uk` (a conditionId → duration map) and shows wounds on the fighter's
// portrait, so this list is what makes an injury visible to the player.
func writeEvolutionTail(w *protocol.Writer, f *domain.Fighter) {
	w.I32(0) // sphereBoardId
	w.I32(f.XP)
	w.I32(f.TotalXP)
	w.U8(f.Tiredness)
	w.U8(f.Morale)
	w.U8(f.State)
	w.U16(0) // sphereX
	w.U16(0) // sphereY
	w.U16(0) // sphere count

	// The count is a single BYTE, so cap it. A fighter can never legitimately
	// hold this many (one per mutual-exclusion type, plus stacking type 21), but
	// a corrupt row must not wrap the length and desync the whole blob.
	conds := f.Conditions
	if len(conds) > 255 {
		conds = conds[:255]
	}
	w.U8(uint8(len(conds)))
	for _, c := range conds {
		w.U16(uint16(c.ConditionID))
		w.U8(uint8(c.Remaining))
	}

	w.U16(0) // passive count
	w.U16(0) // passive-set count
}
