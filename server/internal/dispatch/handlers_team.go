package dispatch

import (
	"context"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterTeamHandlers wires team-preset CRUD opcodes, see
// docs/02-protocol.md TEAM_PRESET_SAVE/DELETE/LIST_REQUEST and
// TeamPresetSaveRequest.java/TeamPresetDeleteRequest.java/
// TeamPresetListRequest.java.
func RegisterTeamHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvTeamPresetSaveRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleTeamPresetSave(session, payload, deps)
	})
	r.Register(protocol.RecvTeamPresetDeleteRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleTeamPresetDelete(session, payload, deps)
	})
	r.Register(protocol.RecvTeamPresetListRequest, func(session *netio.Session, _ *protocol.Reader) {
		handleTeamPresetList(session, deps)
	})
}

func handleTeamPresetSave(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	slot := payload.Int16()
	name := payload.String()
	count := int(payload.Byte())
	fighterIDs := make([]uint, 0, count)
	for i := 0; i < count; i++ {
		fighterIDs = append(fighterIDs, uint(payload.Int64()))
	}
	if payload.Err() != nil {
		session.Send(buildTeamPresetSaveError())
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	deps.Logger.Debug().
		Int16("slot", slot).
		Str("name", name).
		Int("fighter_count", count).
		Interface("fighter_ids", fighterIDs).
		Uint("coach_id", coach.ID).
		Msg("dispatch: team save request parsed")

	ctx := context.Background()
	team, err := deps.Team.SaveTeam(ctx, coach.ID, slot, name, fighterIDs)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: team save failed")
		session.Send(buildTeamPresetSaveError())
		return
	}

	deps.Logger.Debug().
		Uint("team_id", team.ID).
		Int16("assigned_slot", team.Slot).
		Int("persisted_fighters", len(team.Fighters)).
		Msg("dispatch: team saved")

	// Echo back the REAL assigned slot, not the client-supplied one -- for
	// a new preset the client sends -1 and the server allocates the actual
	// slot, which the client needs to learn so it can reference the preset
	// afterward.
	session.Send(buildTeamPresetSaveResult(team.Slot, name, fighterIDs))
	handleTeamPresetList(session, deps)
}

func handleTeamPresetDelete(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	slot := payload.Int16()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	if err := deps.Team.DeleteTeam(context.Background(), coach.ID, slot); err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: team delete failed")
		return
	}
	session.Send(buildTeamPresetDeletionResult(slot))
}

func handleTeamPresetList(session *netio.Session, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	ctx := context.Background()
	teams, err := deps.Team.ListTeams(ctx, coach.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: team list failed")
		return
	}
	session.Send(buildTeamPresetList(teams))

	fighters, err := deps.Fighter.ListFighters(ctx, coach.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: fighter list (for team list) failed")
		return
	}
	withLoadouts, err := attachLoadouts(ctx, deps, fighters)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: load fighter loadouts (for team list) failed")
		return
	}
	session.Send(buildFighterInformationList(deps.Data, withLoadouts))
}
