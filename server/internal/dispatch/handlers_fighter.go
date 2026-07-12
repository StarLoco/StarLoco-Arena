package dispatch

import (
	"context"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterFighterHandlers wires fighter (deck-built combatant) CRUD
// opcodes, see docs/02-protocol.md FIGHTER_CREATE/DELETE/
// INFORMATION_LIST/UPDATE_INVENTORY_REQUEST and
// FighterCreateRequest.java/FighterDeleteRequest.java/
// FightInformationListRequest.java/FighterUpdateInventoryRequest.java.
func RegisterFighterHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvFighterCreateRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterCreate(session, payload, deps)
	})
	r.Register(protocol.RecvFighterDeleteRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterDelete(session, payload, deps)
	})
	r.Register(protocol.RecvFighterInformationListRequest, func(session *netio.Session, _ *protocol.Reader) {
		handleFighterInformationList(session, deps)
	})
	r.Register(protocol.RecvFighterUpdateInventoryRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterUpdateInventory(session, payload, deps)
	})
}

func handleFighterCreate(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	_ = payload.Uint16() // legacy leading short, purpose unclear, preserved for framing parity
	_ = payload.Byte()   // client-version byte, unused
	_ = payload.Int16()  // client-supplied budget: IGNORED, recomputed server-side
	breed := payload.Byte()
	name := payload.String()
	sex := payload.Byte()
	skin := payload.Byte()

	// Spell inventory is a flat int32[] blob (no pos, no quantity); the
	// equipment/card inventory is a length-prefixed ArrayInventory blob of
	// [short pos][int32 id] pairs. See inventory_codec.go.
	spellBlob := payload.Bytes(int(payload.Uint16()))
	objectBlob := payload.Bytes(int(payload.Uint16()))
	if payload.Err() != nil {
		session.Send(buildFighterCreateError())
		return
	}

	// Validate the loadout server-side: spells must belong to the fighter's
	// breed (max 6 distinct), cards must be real equipment types (max one
	// per slot). A MITM client can submit unowned/illegal/over-budget
	// loadouts, so the server must not trust the raw blobs.
	spellIDs := validateFighterSpells(deps.Data, breed, parseSpellIDs(spellBlob))
	objectIDs := validateFighterObjects(deps.Data, parseInventoryIDs(objectBlob))
	// Recompute the point value from the validated loadout instead of
	// trusting the client's budget field (which it can forge to claim a
	// cheaper, over-cap team).
	budget := computeFighterBudget(deps.Data, breed, spellIDs, objectIDs)

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	fighter, err := deps.Fighter.CreateFighter(context.Background(), coach.ID, name, breed, sex, skin, budget, spellIDs, objectIDs)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: fighter creation failed")
		session.Send(buildFighterCreateError())
		return
	}

	serialized := serializeFighter(deps.Data, *fighter, spellIDs, objectIDs)
	session.Send(buildFighterCreateResult(fighter.ID, serialized))
}

func handleFighterDelete(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	id := payload.Int64()
	if payload.Err() != nil {
		return
	}

	// The fighter id is client-supplied; scope the delete to the
	// authenticated coach so a crafted packet can't delete another coach's
	// fighter (IDOR). A non-owned/absent fighter yields deleted=false.
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	deleted, err := deps.Fighter.DeleteFighter(context.Background(), coach.ID, uint(id))
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: fighter deletion failed")
	}
	session.Send(buildFighterDeletionResult(err == nil && deleted, uint(id)))
}

func handleFighterInformationList(session *netio.Session, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	ctx := context.Background()
	fighters, err := deps.Fighter.ListFighters(ctx, coach.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: list fighters failed")
		return
	}
	withLoadouts, err := attachLoadouts(ctx, deps, fighters)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: load fighter loadouts failed")
		return
	}
	session.Send(buildFighterInformationList(deps.Data, withLoadouts))
}

func handleFighterUpdateInventory(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()

	// Spell inventory is a flat int32[] blob (no pos, no quantity); the
	// equipment/card inventory is a length-prefixed ArrayInventory blob of
	// [short pos][int32 id] pairs. See inventory_codec.go.
	spellBlob := payload.Bytes(int(payload.Uint16()))
	objectBlob := payload.Bytes(int(payload.Uint16()))
	if payload.Err() != nil {
		session.Send(buildFighterUpdateError(uint(fighterID)))
		return
	}

	// The fighter id is client-supplied; the update must be scoped to the
	// authenticated coach so a crafted packet can't rewrite another coach's
	// loadout (IDOR).
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	ctx := context.Background()

	// Load the owned fighter to (a) confirm ownership and (b) get its breed
	// for spell-breed validation. A fighter not owned by the caller yields
	// an empty result -> reject.
	owned, err := deps.Fighter.GetFightersByIDs(ctx, coach.ID, []uint{uint(fighterID)})
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: load fighter for inventory update failed")
		session.Send(buildFighterUpdateError(uint(fighterID)))
		return
	}
	if len(owned) == 0 {
		// Not the caller's fighter -- reject rather than silently ack.
		session.Send(buildFighterUpdateError(uint(fighterID)))
		return
	}
	breed := owned[0].Breed

	spellIDs := parseSpellIDs(spellBlob)
	objectIDs := parseInventoryIDs(objectBlob)

	// Validate server-side (breed-match + max spells + one card per slot),
	// same as fighter creation -- a MITM client bypasses these client-side
	// inventory checkers.
	filteredSpellIDs := validateFighterSpells(deps.Data, breed, spellIDs)
	filteredObjectIDs := validateFighterObjects(deps.Data, objectIDs)
	budget := computeFighterBudget(deps.Data, breed, filteredSpellIDs, filteredObjectIDs)

	deps.Logger.Debug().
		Int64("fighter_id", fighterID).
		Interface("raw_spell_ids", spellIDs).
		Interface("filtered_spell_ids", filteredSpellIDs).
		Interface("raw_object_ids", objectIDs).
		Interface("filtered_object_ids", filteredObjectIDs).
		Msg("dispatch: fighter inventory update")

	updated, err := deps.Fighter.UpdateInventory(ctx, coach.ID, uint(fighterID), budget, filteredSpellIDs, filteredObjectIDs)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: fighter inventory update failed")
		session.Send(buildFighterUpdateError(uint(fighterID)))
		return
	}
	if !updated {
		session.Send(buildFighterUpdateError(uint(fighterID)))
		return
	}

	session.Send(buildFighterUpdatedInventory(deps.Data, uint(fighterID), filteredSpellIDs, filteredObjectIDs))
}

// attachLoadouts batch-loads spell/object IDs for a list of fighters and
// pairs them up, used to build FIGHTER_INFORMATION_LIST payloads.
func attachLoadouts(ctx context.Context, deps *Deps, fighters []domain.Fighter) ([]fighterWithLoadout, error) {
	ids := make([]uint, len(fighters))
	for i, f := range fighters {
		ids[i] = f.ID
	}
	spells, objects, err := deps.Fighter.LoadoutMaps(ctx, ids)
	if err != nil {
		return nil, err
	}
	out := make([]fighterWithLoadout, len(fighters))
	for i, f := range fighters {
		out[i] = fighterWithLoadout{Fighter: f, SpellIDs: spells[f.ID], ObjectIDs: objects[f.ID]}
	}
	return out, nil
}
