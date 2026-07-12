package dispatch

import (
	"context"
	"fmt"
	"math/rand"
	"time"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// RegisterFightHandlers wires the duel setup flow (fighter-ready
// selection, fight-creation cancel, placement readiness), world-map actor
// movement, and (Phase D/E/F onward) the full in-fight opcode set: phase
// readiness gates, movement, close combat, spell/card casting, turn end,
// and forfeit. See docs/opcodes/07-fight-lifecycle.md and
// docs/opcodes/08-fight-combat-engine.md for the wire specs.
func RegisterFightHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvSetReadyForFight, func(session *netio.Session, payload *protocol.Reader) {
		handleSetReadyForFight(session, payload, deps)
	})
	r.Register(protocol.RecvFightCreationCancelMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleFightCreationCancel(session, payload, deps)
	})
	r.Register(protocol.RecvTeamMateSetReadyForPlacement, func(session *netio.Session, _ *protocol.Reader) {
		handleTeamMateSetReadyForPlacement(session, deps)
	})
	r.Register(protocol.RecvActorMovementRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleActorMovementRequest(session, payload, deps)
	})
	r.Register(protocol.RecvFightInvitationRequestMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleFightInvitationRequest(session, payload, deps)
	})
	r.Register(protocol.RecvFightInvitationAcceptMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleFightInvitationAccept(session, payload, deps)
	})
	r.Register(protocol.RecvFightInvitationRejectMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleFightInvitationReject(session, payload, deps)
	})

	// Phase D: placement/observation/action readiness gates.
	r.Register(protocol.RecvMoveToFreePlacementRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleMoveToFreePlacementRequest(session, payload, deps)
	})
	r.Register(protocol.RecvTeamMateSetReadyForObservation, func(session *netio.Session, _ *protocol.Reader) {
		handleCoachReadyForObservation(session, deps)
	})
	r.Register(protocol.RecvTeamMateSetReadyForAction, func(session *netio.Session, _ *protocol.Reader) {
		handleCoachReadyForAction(session, deps)
	})

	// Phase E/F: turn cycle + combat actions.
	r.Register(protocol.RecvFighterEndTurnRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterEndTurnRequest(session, payload, deps)
	})
	r.Register(protocol.RecvCloseCombatRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleCloseCombatRequest(session, payload, deps)
	})
	r.Register(protocol.RecvSpellCastRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleSpellCastRequest(session, payload, deps)
	})
	r.Register(protocol.RecvFighterCardUseRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterCardUseRequest(session, payload, deps)
	})
	r.Register(protocol.RecvFighterActorMovementRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterActorMovementRequest(session, payload, deps)
	})
	r.Register(protocol.RecvFighterActorDirectionChangeRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleFighterActorDirectionChangeRequest(session, payload, deps)
	})
	r.Register(protocol.RecvGiveUpFightRequest, func(session *netio.Session, _ *protocol.Reader) {
		handleGiveUpFightRequest(session, deps)
	})
	r.Register(protocol.RecvEndFightDone, func(session *netio.Session, _ *protocol.Reader) {
		handleEndFightDoneRequest(session, deps)
	})
}

func handleSetReadyForFight(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	duelID := payload.Int64()
	count := int(payload.Byte())
	fighterIDs := make([]uint, 0, count)
	for i := 0; i < count; i++ {
		fighterIDs = append(fighterIDs, uint(payload.Int64()))
	}
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	duel, ok := deps.Duels.Get(duelID)
	if !ok || !duel.InvolvesCoach(coach.ID) {
		deps.Logger.Debug().Int64("duel_id", duelID).Msg("dispatch: set-ready-for-fight for unknown/foreign duel")
		return
	}

	session.Send(buildReadyForFight(0, coach.ID))

	bothReady := duel.SetSelection(coach.ID, fighterIDs)
	if !bothReady {
		return
	}
	tryPrepareCreateFight(duel, deps)
}

// tryPrepareCreateFight marks duel prepared (idempotent guard against a
// race between both coaches' concurrent SET_READY_FOR_FIGHT packets, or
// against the forced-progress timer racing a very-late real packet) and,
// if this call won the race, sends CREATE_FIGHT and then IMMEDIATELY runs
// the teleport + fight-instantiation + ACTOR_APPEAR + START_PRESENTATION
// sequence (startPresentationForDuel).
//
// Flow correction (§8.22): CREATE_FIGHT is what makes the client show the
// presentation "VS" panel (client-side Fight.start()); the only ready gate
// after that is TEAM_MATE_SET_READY_FOR_PLACEMENT (opcode 8011), sent by
// that panel's own "Prêt" button -- there is NO separate pre-fight
// "teleport gate". So the teleport/presentation setup must happen right
// after CREATE_FIGHT (so the map + fighters exist as the panel shows),
// NOT gated behind an 8011. The 8011s that follow are the presentation-
// ready votes (handleCoachReadyPresentation), each acked with 8012, which
// end presentation once both arrive.
func tryPrepareCreateFight(duel *world.Duel, deps *Deps) {
	if !duel.MarkPrepared() {
		return // already prepared by a concurrent path
	}
	duel.CancelReadyTimer()
	prepareCreateFight(duel, deps)
	startPresentationForDuel(duel, deps)
	armPresentationReadyTimeout(duel, deps)
}

// armMatchReadyTimeout arms the forced-progress timer for the
// SET_READY_FOR_FIGHT (fighter-selection) gate: previously a coach who
// never sent SET_READY_FOR_FIGHT left the duel stalled forever, with no
// server-side counterpart to the client's own cosmetic ready-countdown UI.
// Per project decision, if the timeout fires and at least one coach never
// submitted a fighter selection at all, there is no roster to fall back on
// (the server never sees anything but the final atomic selection) -- so
// the duel is canceled for both sides rather than guessed at.
func armMatchReadyTimeout(duel *world.Duel, deps *Deps) {
	duel.ArmReadyTimer(deps.Combat.MatchReadyClock, func() {
		selected := duel.SelectedCoaches()
		if len(selected) >= 2 {
			// Both sides already selected via a race with the clock;
			// tryPrepareCreateFight is idempotent (MarkPrepared guards
			// it), so it's safe to just invoke it directly here too.
			tryPrepareCreateFight(duel, deps)
			return
		}
		deps.Logger.Info().Int64("duel_id", duel.ID).Msg("dispatch: SET_READY_FOR_FIGHT timed out with an incomplete fighter selection, canceling duel")
		cancelDuel(duel, deps, CancelReasonNoSelectedFighter)
	})
}

// prepareCreateFight loads both coaches + their selected fighters and
// broadcasts CREATE_FIGHT to both, mirroring Fight.startPreparation.
func prepareCreateFight(duel *world.Duel, deps *Deps) {
	ctx := context.Background()
	selA, selB, ok := duel.Selections()
	if !ok {
		return
	}

	teamA, errA := buildDuelTeam(ctx, deps, 1, "team1", duel.CoachAID, selA.FighterIDs)
	teamB, errB := buildDuelTeam(ctx, deps, 2, "team2", duel.CoachBID, selB.FighterIDs)
	if errA != nil || errB != nil {
		deps.Logger.Error().Err(errA).Err(errB).Msg("dispatch: failed to build duel teams for CREATE_FIGHT")
		cancelDuel(duel, deps, CancelReasonInternalErrorDuringCreate)
		return
	}

	// Enforce the client's 5000 team-value cap server-side (the client greys
	// out "ready" past it, but a MITM bypasses that). Each fighter's value
	// is its server-recomputed Budget; computeTeamValue adds the per-breed
	// duplicate surcharge, mirroring EditableTeamPreset.getValue(). A team
	// over the cap (or a bogus/oversized roster) is rejected with the
	// client's own "invalid team budget" cancel reason (46).
	if overA, overB := teamOverBudget(ctx, deps, duel.CoachAID, selA.FighterIDs), teamOverBudget(ctx, deps, duel.CoachBID, selB.FighterIDs); overA || overB {
		deps.Logger.Info().
			Int64("duel", duel.ID).Bool("coachA_over_budget", overA).Bool("coachB_over_budget", overB).
			Msg("dispatch: fight rejected -- a team exceeds the 5000 value cap")
		cancelDuel(duel, deps, CancelReasonInvalidTeamBudget)
		return
	}

	// Card wagering: in a bet fight (Bet != 0) each coach stakes one random
	// unlocked, unequipped coach card. If either coach has no eligible card,
	// the fight can't be held for a bet -- reject with the client's own
	// "cantHoldTheBet" cancel reason (47). The chosen stake is recorded on
	// the Duel (SetStakeCard) so it survives to the fight-end transfer, and
	// attached to each team so buildCreateFight announces it in the stake
	// list. A no-bet fight skips all of this (BetCard stays nil).
	if duel.Bet != 0 {
		stakeA, okA := selectDuelStake(ctx, deps, duel, duel.CoachAID)
		stakeB, okB := selectDuelStake(ctx, deps, duel, duel.CoachBID)
		if !okA || !okB {
			deps.Logger.Info().
				Int64("duel", duel.ID).Bool("coachA_can_stake", okA).Bool("coachB_can_stake", okB).
				Msg("dispatch: bet fight rejected -- a coach has no unlocked card to stake")
			cancelDuel(duel, deps, CancelReasonCantHoldTheBet)
			return
		}
		teamA.BetCard = stakeA
		teamB.BetCard = stakeB
	}

	// Choose this duel's fight map NOW (CREATE_FIGHT is the earliest point
	// the client needs it) and reuse it for the whole downstream flow via
	// Duel.SetMapID. The special-cell render tuples for that map are sent
	// in CREATE_FIGHT so the client draws the tiles.
	mapID := int(duel.SetMapID(selectFightMapID(deps)))
	specialCells := resolveSpecialCellRenders(deps, mapID)

	frame := buildCreateFight(duel.Type, duel.Bet, teamA, teamB, deps.Data, specialCells)
	sendToCoachID(deps, duel.CoachAID, frame)
	sendToCoachID(deps, duel.CoachBID, frame)
}

// teamOverBudget reports whether coachID's selected roster exceeds the
// MaxTeamValue (5000) cap. It loads the coach's OWN fighters matching the
// selection (GetFightersByIDs is coach-scoped, so unowned ids are dropped)
// and computes the team value via computeTeamValue. A DB error is treated
// as "over budget" (fail closed) so a fight never starts on an unverifiable
// roster. An empty roster is under budget (value 0).
func teamOverBudget(ctx context.Context, deps *Deps, coachID uint, fighterIDs []uint) bool {
	fighters, err := deps.Fighter.GetFightersByIDs(ctx, coachID, fighterIDs)
	if err != nil {
		deps.Logger.Error().Err(err).Uint("coach", coachID).Msg("dispatch: team-budget check load failed")
		return true
	}
	return computeTeamValue(fighters) > MaxTeamValue
}

// selectDuelStake picks one random unlocked coach card for coachID to put at
// stake, records it on the duel (so the fight-end transfer can find it), and
// returns it. ok=false means the coach has no eligible card (the caller must
// reject the bet fight). A DB error is treated as "can't stake" (ok=false)
// and logged -- a bet fight must fail closed rather than silently proceed
// without a real stake.
func selectDuelStake(ctx context.Context, deps *Deps, duel *world.Duel, coachID uint) (*domain.CoachCard, bool) {
	card, err := deps.Coach.SelectStakeCard(ctx, coachID)
	if err != nil {
		deps.Logger.Error().Err(err).Uint("coach", coachID).Msg("dispatch: stake card selection failed")
		return nil, false
	}
	if card == nil {
		return nil, false
	}
	duel.SetStakeCard(coachID, card.ID)
	return card, true
}

// resolveSpecialCellRenders builds the client-facing CREATE_FIGHT special-
// cell render tuples for mapID's authored layout (see specialcells.go). Each
// tuple's Z is the cell's standing altitude. Returns nil on any error or if
// the map has no special cells -- a fight must never fail to start over this
// optional data.
func resolveSpecialCellRenders(deps *Deps, mapID int) []combat.SpecialCellRender {
	if deps.Data == nil || deps.Data.SpecialCells == nil || deps.Data.Maps == nil {
		return nil
	}
	layout, err := deps.Data.SpecialCells.Get(mapID)
	if err != nil || len(layout.Cells) == 0 {
		if err != nil {
			deps.Logger.Warn().Err(err).Int("map_id", mapID).Msg("dispatch: failed to load special-cell layout for CREATE_FIGHT")
		}
		return nil
	}
	m, err := deps.Data.Maps.Get(mapID)
	if err != nil {
		return nil
	}
	out := make([]combat.SpecialCellRender, 0, len(layout.Cells))
	for i, c := range layout.Cells {
		z, found := m.StandingAltitudeAt(c.X, c.Y)
		if !found {
			z = 0
		}
		out = append(out, combat.SpecialCellRender{
			CellBaseID: c.CellBaseID,
			CellID:     int64(i + 1),
			X:          c.X,
			Y:          c.Y,
			Z:          z,
		})
	}
	return out
}

// buildDuelTeam loads coachID's selected fighters plus their real spell/
// equipment loadout (via FighterService.LoadoutMaps -- the same source
// buildCombatTeam already uses for the real combat.Fight's Fighter
// objects) for CREATE_FIGHT's per-fighter spells/equipment blobs, fixing a
// reported bug where fighters showed up in-fight with no spells/
// equipment despite having been equipped -- see
// docs/08-java-parity-roadmap.md's write-up on this fix.
func buildDuelTeam(ctx context.Context, deps *Deps, teamID byte, teamName string, coachID uint, fighterIDs []uint) (duelTeamInfo, error) {
	coach, err := deps.Coach.GetByID(ctx, coachID)
	if err != nil {
		return duelTeamInfo{}, err
	}
	fighters, err := deps.Fighter.GetFightersByIDs(ctx, coachID, fighterIDs)
	if err != nil {
		return duelTeamInfo{}, err
	}

	// Load the coach's equipped cards so CREATE_FIGHT can carry them in the
	// coach look blob (making equipment visible on the coach sprite -- see
	// buildCreateFight). Non-fatal: a coach with no gear (or a load error)
	// just serializes an empty blob rather than failing the whole fight.
	coachEquipment, err := deps.Coach.GetEquippedCards(ctx, coachID)
	if err != nil {
		deps.Logger.Warn().Err(err).Uint("coach_id", coachID).Msg("dispatch: failed to load coach equipment for CREATE_FIGHT, coach will appear without gear")
		coachEquipment = nil
	}

	ids := make([]uint, len(fighters))
	for i, fr := range fighters {
		ids[i] = fr.ID
	}
	spellsByFighter, objectsByFighter, err := deps.Fighter.LoadoutMaps(ctx, ids)
	if err != nil {
		return duelTeamInfo{}, err
	}

	return duelTeamInfo{
		TeamID:           teamID,
		TeamName:         teamName,
		Coach:            coach,
		CoachEquipment:   coachEquipment,
		Fighters:         fighters,
		SpellsByFighter:  spellsByFighter,
		ObjectsByFighter: objectsByFighter,
	}, nil
}

func handleFightCreationCancel(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	duelID := payload.Int64()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	duel, ok := deps.Duels.Get(duelID)
	if !ok || !duel.InvolvesCoach(coach.ID) {
		session.Send(buildFightCreationCanceled(duelID, CancelReasonInternalErrorDuringCreate))
		return
	}

	session.Send(buildFightCreationCanceled(duelID, CancelReasonNoSelectedFighter))
	sendToCoachID(deps, duel.OpponentOf(coach.ID), buildFightCreationCanceled(duelID, CancelReasonCanceledByOpponent))
	deps.Duels.Remove(duelID)
}

func cancelDuel(duel *world.Duel, deps *Deps, reason byte) {
	sendToCoachID(deps, duel.CoachAID, buildFightCreationCanceled(duel.ID, reason))
	sendToCoachID(deps, duel.CoachBID, buildFightCreationCanceled(duel.ID, reason))
	deps.Duels.Remove(duel.ID)
}

// handleTeamMateSetReadyForPlacement processes TEAM_MATE_SET_READY_FOR_
// PLACEMENT (opcode 8011) -- the presentation "VS" panel's "Prêt" button
// (UIFightPresentationFrame's UI event 18009). This is the SINGLE ready
// gate after CREATE_FIGHT (there is NO separate pre-fight teleport gate --
// §8.22): the teleport + fight instantiation + START_PRESENTATION already
// happened right after CREATE_FIGHT (tryPrepareCreateFight ->
// startPresentationForDuel). Each 8011 is a presentation-ready vote routed
// into the fight actor, which acks it with an 8012 to the sender (so their
// VS panel shows "waiting for opponent") and, once BOTH coaches vote, ends
// presentation -> START_PLACEMENT.
func handleTeamMateSetReadyForPlacement(session *netio.Session, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	duel, ok := deps.Duels.GetByCoach(coach.ID)
	if !ok {
		return
	}

	fight, ok := duel.Fight()
	if !ok {
		// No fight actor yet -- an 8011 shouldn't arrive before
		// CREATE_FIGHT/presentation setup, but if it races ahead, ignore
		// it (the client re-sends on Prêt, and presentation has its own
		// fallback clock).
		return
	}
	// Route the presentation-ready vote to the fight actor (safe from any
	// goroutine). handleCoachReadyPresentation sends the 8012 ack and, once
	// both coaches have voted, ends presentation. A stray 8011 arriving
	// after presentation is a no-op there.
	fight.Send(combat.NewCoachReadyPresentation(coach.ID))
}

// startPresentationForDuel runs the CREATE_FIGHT follow-up: teleports both
// coaches into the fight map, instantiates the real combat.Fight actor,
// sends ACTOR_APPEAR so fighters/coaches actually render, and broadcasts
// START_PRESENTATION. Called from tryPrepareCreateFight right AFTER
// CREATE_FIGHT (§8.22) -- NOT gated behind an 8011, since CREATE_FIGHT is
// what makes the client show the presentation "VS" panel and the map/
// fighters must exist by then. Idempotent via MarkPresentationStarted.
func startPresentationForDuel(duel *world.Duel, deps *Deps) {
	if !duel.MarkPresentationStarted() {
		return // already started via a concurrent path
	}

	// coachASpot/coachBSpot are resolved from real .amw/elements.ade map
	// data when available (docs/08-java-parity-roadmap.md Phase K),
	// falling back to the historical hardcoded placeholder cells if map
	// data for fightMapID isn't loadable -- confirmed via
	// internal/gamedata's real-data tests that these specific cells DO
	// carry a real FightStartCoachPointElement in the actual game data,
	// with team-side param byte 0 for the (16,11) cell and 1 for (1,7).
	// Reuse the fight map already chosen for this duel at CREATE_FIGHT time
	// (prepareCreateFight -> Duel.SetMapID) so every step here lands on the
	// same arena the client was told about. If somehow unset (e.g. a code
	// path that skipped prepareCreateFight), SetMapID picks one now.
	mapID := int(duel.MapID())
	if mapID == 0 {
		mapID = int(duel.SetMapID(selectFightMapID(deps)))
	}

	coachASpot, coachBSpot := resolveCoachStartSpots(deps, mapID)
	// The ENTER_WORLD_INSTANCE worldX/worldY doubles as the client's
	// initial fight-camera focus (NetInstanceFrame case 4600 ->
	// setCameraTarget(DefaultIsoWorldTarget(worldX, worldY))). Focusing it
	// on each coach's own spot made the camera sit off to one side of the
	// arena at fight start; instead we center it on the battlefield center
	// so both coaches see the whole arena. The coaches' actual on-map
	// placement (ACTOR_APPEAR, resolveCoachAppearSpots below) is unaffected
	// -- only the camera-focus coords change here.
	cameraCenter := resolveFightCameraCenter(deps, mapID, coachASpot, coachBSpot)
	teleport := buildEnterWorldInstance(float32(cameraCenter.X), float32(cameraCenter.Y), cameraCenter.Z, int16(mapID), true)
	sendToCoachID(deps, duel.CoachAID, teleport)
	teleportB := buildEnterWorldInstance(float32(cameraCenter.X), float32(cameraCenter.Y), cameraCenter.Z, int16(mapID), true)
	sendToCoachID(deps, duel.CoachBID, teleportB)

	// Hand off to a real turn-based combat.Fight actor (starts in
	// PhasePresentation), before broadcasting START_PRESENTATION.
	fight, err := instantiateFight(duel, deps, mapID)
	if err != nil {
		deps.Logger.Error().Err(err).Int64("duel_id", duel.ID).Msg("dispatch: failed to instantiate combat.Fight")
		cancelDuel(duel, deps, CancelReasonInternalErrorDuringCreate)
		return
	}

	// Send ACTOR_APPEAR (4102) for every fighter AND both coaches now that
	// the fight actor exists and everyone has a real position -- this is
	// the message that actually instantiates a visible mobile inside the
	// fight-map scene (NetFightActorsFrame's case 4102 -> addMobile()).
	// See docs/opcodes/03-coach-world.md's ACTOR_APPEAR section and §8.18.
	coachAAppear, coachBAppear := resolveCoachAppearSpots(deps, mapID)
	// Face each coach toward the arena centroid (whatever map this duel
	// landed on) rather than a hardcoded diagonal, so they always look at
	// the battlefield -- see buildActorAppearForFight / coachFacingToward.
	fightCenter := resolveFightCameraCenter(deps, mapID, coachAAppear, coachBAppear)
	appear := buildActorAppearForFight(fight, duel.CoachAID, coachAAppear, duel.CoachBID, coachBAppear, fightCenter)
	sendToCoachID(deps, duel.CoachAID, appear)
	sendToCoachID(deps, duel.CoachBID, appear)

	start := buildStartPresentation()
	sendToCoachID(deps, duel.CoachAID, start)
	sendToCoachID(deps, duel.CoachBID, start)
}

// armPresentationReadyTimeout arms a forced-progress fallback: if the
// presentation-ready votes (8011) never both arrive (a coach never clicks
// Prêt / disconnects), the presentation clock inside combat.Fight already
// ends presentation on its own, so this timer is a redundant safety net
// that force-ends presentation via the fight actor. It force-votes both
// coaches so handleCoachReadyPresentation reaches its both-ready branch.
func armPresentationReadyTimeout(duel *world.Duel, deps *Deps) {
	duel.ArmReadyTimer(deps.Combat.PlacementReadyClock, func() {
		fight, ok := duel.Fight()
		if !ok || fight.CurrentPhase() != combat.PhasePresentation {
			return // fight gone or already past presentation
		}
		deps.Logger.Info().Int64("duel_id", duel.ID).Msg("dispatch: presentation-ready timed out, force-ending presentation")
		fight.Send(combat.NewCoachReadyPresentation(duel.CoachAID))
		fight.Send(combat.NewCoachReadyPresentation(duel.CoachBID))
	})
}

// buildActorAppearForFight assembles the ACTOR_APPEAR entity list for a
// freshly-instantiated fight: one entry per fighter (at its real combat
// position, facing its own Direction) PLUS one entry per coach (at its
// pedestal spot). ACTOR_APPEAR is the only message that instantiates a
// visible mobile inside the fight scene (NetFightActorsFrame case 4102 ->
// addMobile()); the client resolves each entry by fighter-id FIRST, then
// falls back to a coach lookup, so both must appear here for both to
// render.
//
// ID scheme (fixes coach/fighter ID collision -- see
// combat.FighterWireIDBase and §8.18 of docs/08-java-parity-roadmap.md):
// fighters already carry their offset wire id (FighterWireIDBase+realId,
// applied at combat.Fighter construction and matched in CREATE_FIGHT), and
// coaches carry their REAL id (which the client compares against the
// login-supplied local coach id in setFight()/FightEndAction, so it must
// NOT be offset). The offset guarantees the client's fighter-first
// resolution never mistakes a coach entry for a fighter (or vice versa).
//
// Orientation note: only the four DIAGONAL Direction8 values render
// correctly for fighter sprites in the isometric client; the four CARDINAL
// ones render wrong (confirmed empirically via the /APPEAR GM command).
// Each combat.Fighter is created with a valid diagonal Direction
// (NewFighterFromBreed -> defaultTeamFacing), used verbatim here. The two
// coaches are now oriented DYNAMICALLY toward the fight-map centroid
// (coachFacingToward), snapping the coach->center vector to the nearest of
// the four render-legal diagonals -- so each coach always looks at the
// battlefield on ANY map, fixing the "coach turns its back to the match on
// some maps" bug. (Previously hardcoded NW for A / SE for B, which only
// happened to face center on maps whose pedestals sit top-left/bottom-right.)
func buildActorAppearForFight(fight *combat.Fight, coachAID uint, coachASpot combat.Point3, coachBID uint, coachBSpot combat.Point3, fightCenter combat.Point3) protocol.OutboundFrame {
	entries := []actorAppearEntry{
		{ID: int64(coachAID), X: coachASpot.X, Y: coachASpot.Y, Z: coachASpot.Z, Direction: coachFacingToward(coachASpot, fightCenter)},
		{ID: int64(coachBID), X: coachBSpot.X, Y: coachBSpot.Y, Z: coachBSpot.Z, Direction: coachFacingToward(coachBSpot, fightCenter)},
	}
	for _, fr := range fight.AllFighters() {
		entries = append(entries, actorAppearEntry{
			ID: fr.ID, X: fr.Position.X, Y: fr.Position.Y, Z: fr.Position.Z, Direction: fr.Direction,
		})
	}
	return buildActorAppear(entries)
}

// coachFacingToward returns the render-legal DIAGONAL Direction8 that points
// the coach standing at `from` toward `center` (the arena centroid). Only
// the four diagonal directions (SE=(+1,0), SW=(0,+1), NW=(-1,0), NE=(0,-1)
// in grid space -- the single-axis moves that LOOK diagonal on the 2:1 iso
// projection) render correctly for a coach sprite, so the from->center
// vector is snapped to whichever of them best matches: the axis with the
// larger absolute delta wins, and its sign picks the direction. Ties (and a
// degenerate from==center) fall back to SOUTH_EAST -- a valid diagonal that
// faces "into" the map from a typical corner pedestal.
func coachFacingToward(from, center combat.Point3) combat.Direction8 {
	dx := center.X - from.X
	dy := center.Y - from.Y
	absDX, absDY := dx, dy
	if absDX < 0 {
		absDX = -absDX
	}
	if absDY < 0 {
		absDY = -absDY
	}
	if absDX >= absDY {
		if dx > 0 {
			return combat.DirSouthEast
		}
		if dx < 0 {
			return combat.DirNorthWest
		}
	} else {
		if dy > 0 {
			return combat.DirSouthWest
		}
		if dy < 0 {
			return combat.DirNorthEast
		}
	}
	return combat.DirSouthEast
}

// defaultFightMapID is the fallback map used when the usable-fight-map pool
// can't be discovered (missing/unreadable map data) -- historically the
// single hardcoded map every duel took place on. Real fights now pick a
// RANDOM map per duel from MapStore.FightMapIDs (see selectFightMapID); this
// constant only survives as the "always safe" fallback.
const defaultFightMapID = 2

// selectFightMapID picks a random usable fight map for a new duel from the
// discovered pool (MapStore.FightMapIDs). Falls back to defaultFightMapID
// when the pool is empty or can't be computed (missing data files, read
// error) -- a fight must never fail to start just because map discovery is
// unavailable.
func selectFightMapID(deps *Deps) int32 {
	if deps.Data == nil || deps.Data.Maps == nil {
		return defaultFightMapID
	}
	ids, err := deps.Data.Maps.FightMapIDs()
	if err != nil || len(ids) == 0 {
		if err != nil {
			deps.Logger.Warn().Err(err).Msg("dispatch: fight-map discovery failed, using default map")
		}
		return defaultFightMapID
	}
	return int32(ids[rand.Intn(len(ids))])
}

// fallbackCoachSpotA/B are the historical hardcoded placeholder
// coach-placement cells used before real .amw/elements.ade map data was
// parsed (docs/08-java-parity-roadmap.md Phase K). Real map data (see
// resolveCoachStartSpots) is now preferred when loadable; these remain as
// a fallback for dev/test setups without the game's data files present.
var (
	fallbackCoachSpotA = combat.Point3{X: 16, Y: 11, Z: -3}
	fallbackCoachSpotB = combat.Point3{X: 1, Y: 7, Z: -3}
)

// resolveCoachStartSpots looks up fightMapID's real
// FightStartCoachPointElement cells (docs/08-java-parity-roadmap.md Phase
// K) to place each coach at fight start, falling back to the historical
// hardcoded cells if map data isn't loadable (missing data files, parse
// error) -- a fight must never fail to start just because optional real
// map data is unavailable. The two real cells found in this project's
// actual game data are keyed by the element's raw team-side param byte:
// 0 -> (16,11), 1 -> (1,7) -- confirmed via
// internal/gamedata/map_realdata_test.go against the real files. Byte 0
// is treated as "team A/coach A's spot", byte 1 as "team B/coach B's
// spot", an arbitrary-but-consistent assignment since only one map's data
// has been cross-checked so far.
func resolveCoachStartSpots(deps *Deps, mapID int) (spotA, spotB combat.Point3) {
	spotA, spotB = fallbackCoachSpotA, fallbackCoachSpotB
	if deps.Data == nil || deps.Data.Maps == nil {
		return spotA, spotB
	}
	m, err := deps.Data.Maps.Get(mapID)
	if err != nil {
		deps.Logger.Warn().Err(err).Int("map_id", mapID).Msg("dispatch: real map data unavailable, using fallback coach-placement cells")
		return spotA, spotB
	}
	cellsA, okA := m.CoachStartCells()[0]
	cellsB, okB := m.CoachStartCells()[1]
	if okA && len(cellsA) > 0 {
		spotA = cellSpotAtAltitude(m, cellsA[0], fallbackCoachSpotA.Z)
	}
	if okB && len(cellsB) > 0 {
		spotB = cellSpotAtAltitude(m, cellsB[0], fallbackCoachSpotB.Z)
	}
	return spotA, spotB
}

// resolveFightCameraCenter computes the cell the client's initial fight
// camera should focus on -- the CENTER of the battlefield rather than
// either coach's off-to-one-side spot (see startPresentationForDuel's
// camera-focus note). It averages every FightStartPointElement cell of
// BOTH team sides (the real spawn-area centroid, i.e. the middle of the
// arena) when real map data is available; if the map has no fight-start
// cells (or map data isn't loadable at all), it falls back to the midpoint
// between the two coach spots, which are the arena's two ends and whose
// midpoint is therefore still a sensible center. The Z (altitude) is taken
// from the center cell's own walkable surface (nearest to the coaches'
// altitude) so the camera focus sits at the battlefield's height.
func resolveFightCameraCenter(deps *Deps, mapID int, coachASpot, coachBSpot combat.Point3) combat.Point3 {
	midZ := coachASpot.Z
	// Default: geometric midpoint of the two coach spots (always valid,
	// used when real fight-start-cell data is unavailable).
	center := combat.Point3{
		X: (coachASpot.X + coachBSpot.X) / 2,
		Y: (coachASpot.Y + coachBSpot.Y) / 2,
		Z: midZ,
	}

	if deps.Data == nil || deps.Data.Maps == nil {
		return center
	}
	m, err := deps.Data.Maps.Get(mapID)
	if err != nil {
		return center
	}

	// Average all fight-start (spawn-area) cells across both team sides to
	// get the true arena centroid.
	var sumX, sumY, n int64
	for _, cells := range m.FightStartCells() {
		for _, c := range cells {
			sumX += int64(c[0])
			sumY += int64(c[1])
			n++
		}
	}
	if n > 0 {
		center.X = int32(sumX / n)
		center.Y = int32(sumY / n)
	}

	// Anchor the camera's altitude to a real walkable surface at the center
	// cell (nearest to the coaches' Z), falling back to the coach altitude
	// if the center cell has no walkable surface.
	center.Z = cellSpotAtAltitude(m, [2]int32{center.X, center.Y}, midZ).Z
	return center
}

// resolveCoachAppearSpots resolves the coach STANDING altitude (base+height,
// via topmostWalkableSpot -- the same rule fighters use, confirmed correct
// in-game) for each coach's ACTOR_APPEAR entry. Distinct from
// resolveCoachStartSpots (used for the ENTER_WORLD_INSTANCE camera, which
// works at its own altitude): the rendered coach SPRITE needs the standing
// altitude to sit on its pedestal, whereas the camera/instance altitude is
// a separate concern. Fixes a reported "coach altitude is wrong" bug. See
// §8.20 of docs/08-java-parity-roadmap.md.
func resolveCoachAppearSpots(deps *Deps, mapID int) (spotA, spotB combat.Point3) {
	spotA, spotB = fallbackCoachSpotA, fallbackCoachSpotB
	if deps.Data == nil || deps.Data.Maps == nil {
		return spotA, spotB
	}
	m, err := deps.Data.Maps.Get(mapID)
	if err != nil {
		return spotA, spotB
	}
	if cells, ok := m.CoachStartCells()[0]; ok && len(cells) > 0 {
		spotA = topmostWalkableSpot(m, cells[0], fallbackCoachSpotA.Z)
	}
	if cells, ok := m.CoachStartCells()[1]; ok && len(cells) > 0 {
		spotB = topmostWalkableSpot(m, cells[0], fallbackCoachSpotB.Z)
	}
	return spotA, spotB
}

// cellSpotAtAltitude picks the walkable surface at cell closest to
// preferredZ (mirroring Fight.ArrivalAltitude's own "nearest walkable
// surface" resolution), falling back to preferredZ unchanged if the cell
// has no walkable surface at all (shouldn't happen for a real
// FightStartCoachPointElement cell, but must never crash fight creation).
// Used only for the coach's own anchor cell (resolveCoachStartSpots) --
// see topmostWalkableSpot for fighter placement cells, which uses a
// different (and more correct) resolution rule.
func cellSpotAtAltitude(m *gamedata.Map, cell [2]int32, preferredZ int16) combat.Point3 {
	best, found := preferredZ, false
	bestDist := 0
	for _, s := range m.SurfacesAt(cell[0], cell[1]) {
		if !s.Walkable {
			continue
		}
		dist := int(s.Altitude) - int(preferredZ)
		if dist < 0 {
			dist = -dist
		}
		if !found || dist < bestDist {
			best, bestDist, found = s.Altitude, dist, true
		}
	}
	return combat.Point3{X: cell[0], Y: cell[1], Z: best}
}

// topmostWalkableSpot picks the highest walkable "standing altitude" at
// cell -- the surface a mobile visibly stands ON, viewed from above --
// and returns it as the cell's placement Z. Falls back to fallbackZ
// unchanged if the cell has no walkable surface at all (shouldn't happen
// for a real FightStartPointElement cell, but must never crash fight
// creation).
//
// CRITICAL altitude convention (fixes a reported "fighters render sunk
// under the map, only pop up after a client redraw" bug): the client
// renders a Mobile at `mobile.getAltitude() * elevationUnit` screen
// pixels, and expects that altitude to be the TOP-OF-BLOCK surface, i.e.
// a surface's `Altitude + Height`, NOT its raw base `Altitude`. This is
// exactly what the client itself computes everywhere it picks a cell to
// stand/move/cast on (WorldSceneInteractionUtils uses
// `getAltitude() + getHeight()`; UIFightPlacementFrame sends
// `coordinates.getZ()` which is base+height) and what makes coaches
// render correctly. Sending the raw base altitude instead drops the
// fighter `Height` altitude-units (~Height*elevationUnit px) into the
// terrain. See docs/opcodes/03-coach-world.md's ACTOR_APPEAR section /
// docs/08-java-parity-roadmap.md §8.17 for the full derivation.
//
// So the standing altitude of a surface is `Altitude + round(Height)`,
// and we pick the highest such standing altitude among walkable surfaces
// (the natural "ground you'd stand on" for a stacked/multi-level cell,
// with no dependency on any other cell's altitude).
func topmostWalkableSpot(m *gamedata.Map, cell [2]int32, fallbackZ int16) combat.Point3 {
	z, found := m.StandingAltitudeAt(cell[0], cell[1])
	if !found {
		z = fallbackZ
	}
	return combat.Point3{X: cell[0], Y: cell[1], Z: z}
}

// instantiateFight builds two combat.Team from the duel's already-recorded
// fighter selections (loading breed/spell/object data fresh from the DB and
// gamedata), then registers a real combat.Fight with deps.Fights and
// attaches it to the duel -- see docs/08-java-parity-roadmap.md Phase B.
func instantiateFight(duel *world.Duel, deps *Deps, mapID int) (*combat.Fight, error) {
	ctx := context.Background()
	selA, selB, ok := duel.Selections()
	if !ok {
		return nil, fmt.Errorf("dispatch: instantiateFight: duel %d missing fighter selections", duel.ID)
	}

	spotA, spotB := resolveCoachStartSpots(deps, mapID)
	// teamSideByte 0/1 matches resolveCoachStartSpots's own convention
	// (CoachStartCells()[0] -> spotA/team A, [1] -> spotB/team B) --
	// FightStartCells() is keyed by the exact same raw team-side param
	// byte, since both element kinds share the same per-map authoring
	// convention (docs/04-game-data-format.md §4.9.5).
	teamA, err := buildCombatTeam(ctx, deps, mapID, 1, "team1", duel.CoachAID, selA.FighterIDs, spotA, 0)
	if err != nil {
		return nil, err
	}
	teamB, err := buildCombatTeam(ctx, deps, mapID, 2, "team2", duel.CoachBID, selB.FighterIDs, spotB, 1)
	if err != nil {
		return nil, err
	}

	broadcaster := world.RegistryBroadcaster{Registry: deps.World}
	fight := deps.Fights.Create(duel.Type, []*combat.Team{teamA, teamB}, broadcaster)

	// Persist coach statistics (fights/wins/losses, ladder strength, fight
	// time) when this fight ends, and push a live PLAYER_STATISTICS_REPORT
	// to each participant -- see buildFightEndHook.
	fight.SetEndHook(buildFightEndHook(deps))

	// Wire real map data (walkability/altitude) into the fight if this
	// duel's chosen map's data is loadable -- see
	// docs/08-java-parity-roadmap.md Phase K. A failure here is
	// non-fatal: the fight simply falls back to the occupancy-only
	// stub behavior (IsWalkable/ArrivalAltitude in turns.go).
	if deps.Data != nil && deps.Data.Maps != nil {
		if m, err := deps.Data.Maps.Get(mapID); err == nil {
			fight.SetMapData(m)
			// Populate this map's special battlefield cells (buff/damage
			// tiles) so they trigger at turn start -- see Part B.
			applySpecialCells(fight, m, deps)
		} else {
			deps.Logger.Warn().Err(err).Int("map_id", mapID).Msg("dispatch: real map data unavailable for new fight, using occupancy-only fallback")
		}
	}

	duel.SetFight(fight)
	return fight, nil
}

// specialCellTypeByName maps an authored gamedata special-cell type name to
// the combat engine's SpecialCellType enum. Unknown names map to
// SpecialCellNone (skipped by the caller) so a typo in a data file can't
// crash fight creation.
func specialCellTypeByName(name gamedata.SpecialCellTypeName) combat.SpecialCellType {
	switch name {
	case gamedata.SpecialCellTrap:
		return combat.SpecialCellTrap
	case gamedata.SpecialCellEnthusiasm:
		return combat.SpecialCellEnthusiasm
	case gamedata.SpecialCellShield:
		return combat.SpecialCellShield
	case gamedata.SpecialCellEagleEye:
		return combat.SpecialCellEagleEye
	case gamedata.SpecialCellPanacea:
		return combat.SpecialCellPanacea
	case gamedata.SpecialCellMotivation:
		return combat.SpecialCellMotivation
	case gamedata.SpecialCellHealingHeart:
		return combat.SpecialCellHealingHeart
	case gamedata.SpecialCellKiller:
		return combat.SpecialCellKiller
	default:
		return combat.SpecialCellNone
	}
}

// applySpecialCells registers this map's authored special battlefield cells
// (buff/damage tiles) onto the fight so they trigger at turn start (see
// internal/combat/specialcells.go). A missing/empty layout is a no-op; a
// malformed layout file is logged and skipped (a fight must never fail to
// start over an optional cosmetic/gameplay-flavor data file). Also records
// the client-facing (cellBaseId, x, y, z) tuples on the fight so
// CREATE_FIGHT can render the tiles.
func applySpecialCells(fight *combat.Fight, m *gamedata.Map, deps *Deps) {
	if deps.Data == nil || deps.Data.SpecialCells == nil {
		return
	}
	layout, err := deps.Data.SpecialCells.Get(m.ID)
	if err != nil {
		deps.Logger.Warn().Err(err).Int("map_id", m.ID).Msg("dispatch: failed to load special-cell layout, fight starts with no special cells")
		return
	}
	for _, c := range layout.Cells {
		cellType := specialCellTypeByName(c.Type)
		if cellType == combat.SpecialCellNone {
			deps.Logger.Warn().Str("type", string(c.Type)).Int("map_id", m.ID).Msg("dispatch: unknown special-cell type in layout, skipping")
			continue
		}
		fight.SetSpecialCell(c.X, c.Y, cellType)

		// Z for the client render tuple: the standing altitude of the cell
		// (base+height), matching how mobiles are placed on it.
		z, found := m.StandingAltitudeAt(c.X, c.Y)
		if !found {
			z = 0
		}
		fight.AddSpecialCellRender(c.CellBaseID, c.X, c.Y, z)
	}
}

// buildCombatTeam loads coachID's selected fighters and assigns each a
// real, distinct starting placement cell -- fixing a reported bug where
// every fighter was placed at (anchor.X, anchor.Y+i, anchor.Z), i.e.
// directly on/adjacent to the COACH's own pedestal cell (anchor), which
// visually stacked every fighter on the coach's spot and, worse, could
// place a fighter on a cell with no walkable path to the rest of the
// battlefield at all (an isolated/off-map pocket), permanently blocking
// that fighter's movement once combat started. See
// docs/08-java-parity-roadmap.md's write-up on this fix.
//
// Placement cell resolution, in priority order:
//  1. Real FightStartPointElement cells for this team side (see
//     docs/04-game-data-format.md §4.9.5) -- confirmed via real map data
//     to be genuinely distinct from, and not merely adjacent to, the
//     coach's own FightStartCoachPointElement anchor cell, and to already
//     all resolve to a walkable surface. One is picked at random (via
//     fight-independent rng, since no combat.Fight exists yet at this
//     point in the flow) for each fighter, without replacement, per
//     project decision -- a fresh, unpredictable arrangement each fight
//     rather than a fixed order.
//  2. If real map data has no such cells for this side, or fewer than
//     needed for the roster size, fall back to a random-walkable-cell
//     search radiating outward from anchor (Map.NearbyWalkableCells) --
//     per project decision ("if you don't know where, use random
//     walkable cell").
//  3. If no map data is attached at all (dev/test setups without the
//     game's data files present), fall back to the historical
//     anchor+Y-offset placement unchanged, so a fight never fails to
//     start just because optional real map data is unavailable.
func buildCombatTeam(ctx context.Context, deps *Deps, mapID int, teamID uint8, teamName string, coachID uint, fighterIDs []uint, anchor combat.Point3, teamSideByte byte) (*combat.Team, error) {
	fighters, err := deps.Fighter.GetFightersByIDs(ctx, coachID, fighterIDs)
	if err != nil {
		return nil, err
	}

	ids := make([]uint, len(fighters))
	for i, fr := range fighters {
		ids[i] = fr.ID
	}
	spellsByFighter, objectsByFighter, err := deps.Fighter.LoadoutMaps(ctx, ids)
	if err != nil {
		return nil, err
	}

	positions := resolveFighterPlacementCells(deps, mapID, anchor, teamSideByte, len(fighters))

	mate := &combat.TeamMate{CoachID: coachID}
	for i, fr := range fighters {
		// Fighter wire/engine id = real DB id + FighterWireIDBase, so
		// fighter ids never collide with coach ids (which keep their real
		// ids) -- required for the client to resolve coaches in
		// ACTOR_APPEAR (see FighterWireIDBase's doc comment). CREATE_FIGHT
		// applies the SAME offset (buildCreateFight) so the client's
		// getFighterById map is keyed consistently.
		cf := combat.NewFighterFromBreed(combat.FighterWireIDBase+int64(fr.ID), teamID, fr.Breed, fr.Name, fr.Sex, fr.Skin)
		cf.CoachID = coachID
		cf.Position = positions[i]
		cf.SpellIDs = spellsByFighter[fr.ID]
		cf.ObjectIDs = objectsByFighter[fr.ID]
		// Apply the passive equip-time characteristic bonuses of every
		// equipped fight-card (e.g. +Initiative/+HP/+AP) BEFORE the fighter
		// is handed to the fight -- combat.NewFight builds the init-sorted
		// turn-order Timeline from each fighter's Init characteristic, so
		// equipment must have adjusted Init by now for it to affect turn
		// order, exactly as the reference client applies card effects on
		// ITEM_ADDED before combat starts (see combat.ApplyEquipmentBonuses).
		combat.ApplyEquipmentBonuses(cf, equippedCardTemplates(deps, cf.ObjectIDs))
		mate.Fighters = append(mate.Fighters, cf)
	}

	return &combat.Team{ID: teamID, Name: teamName, Mates: []*combat.TeamMate{mate}}, nil
}

// equippedCardTemplates resolves a fighter's equipped-card object IDs to
// their gamedata.FighterCardTemplate records (each of which carries the
// EquipEffects passive-bonus subset), skipping any ID with no template.
// Returns nil (so ApplyEquipmentBonuses is a no-op) when the gamedata store
// is unavailable -- e.g. dev/test setups without the game data files -- so a
// fight never fails to start just because optional card data is missing.
func equippedCardTemplates(deps *Deps, objectIDs []int32) []gamedata.FighterCardTemplate {
	if deps == nil || deps.Data == nil || deps.Data.FighterCards == nil || len(objectIDs) == 0 {
		return nil
	}
	out := make([]gamedata.FighterCardTemplate, 0, len(objectIDs))
	for _, id := range objectIDs {
		if card, ok := deps.Data.FighterCards.Get(id); ok {
			out = append(out, card)
		}
	}
	return out
}

// resolveFighterPlacementCells returns count distinct starting positions
// for one team's fighters, per buildCombatTeam's doc comment above.
func resolveFighterPlacementCells(deps *Deps, mapID int, anchor combat.Point3, teamSideByte byte, count int) []combat.Point3 {
	fallback := func() []combat.Point3 {
		out := make([]combat.Point3, count)
		for i := range out {
			out[i] = combat.Point3{X: anchor.X, Y: anchor.Y + int32(i), Z: anchor.Z}
		}
		return out
	}
	if count == 0 {
		return nil
	}
	if deps.Data == nil || deps.Data.Maps == nil {
		return fallback()
	}
	m, err := deps.Data.Maps.Get(mapID)
	if err != nil {
		return fallback()
	}

	rng := rand.New(rand.NewSource(time.Now().UnixNano()))

	cells := append([][2]int32{}, m.FightStartCells()[teamSideByte]...)
	rng.Shuffle(len(cells), func(i, j int) { cells[i], cells[j] = cells[j], cells[i] })

	if len(cells) < count {
		// Not enough real placement cells for this roster size (or none
		// at all) -- fill the remainder with a random-walkable-cell
		// search radiating outward from the coach's own anchor cell.
		need := count - len(cells)
		extra := m.NearbyWalkableCells([2]int32{anchor.X, anchor.Y}, need+len(cells), rng)
		// Exclude cells already chosen above so the two pools don't
		// collide on the same cell.
		chosen := make(map[[2]int32]bool, len(cells))
		for _, c := range cells {
			chosen[c] = true
		}
		for _, c := range extra {
			if len(cells) >= count {
				break
			}
			if chosen[c] {
				continue
			}
			chosen[c] = true
			cells = append(cells, c)
		}
	}

	out := make([]combat.Point3, count)
	for i := 0; i < count; i++ {
		if i >= len(cells) {
			// Still short (an extremely small/degenerate map with almost
			// no walkable cells at all near the anchor) -- fall back to
			// the historical offset for any remaining slots rather than
			// leaving a zero-value position.
			out[i] = combat.Point3{X: anchor.X, Y: anchor.Y + int32(i), Z: anchor.Z}
			continue
		}
		out[i] = topmostWalkableSpot(m, cells[i], anchor.Z)
	}
	return out
}

// sessionFight looks up the combat.Fight the session's coach is currently
// participating in, via their duel. Returns (nil, false) if the coach has
// no active duel/fight (foreign/stale packet), which every in-fight
// handler treats as a silent no-op rather than an error, consistent with
// the router's general tolerance for partially-implemented/out-of-order
// traffic (see router.go's doc comment).
func sessionFight(session *netio.Session, deps *Deps) (*combat.Fight, *domain.Coach, bool) {
	coach, ok := sessionCoach(session)
	if !ok {
		return nil, nil, false
	}
	duel, ok := deps.Duels.GetByCoach(coach.ID)
	if !ok {
		return nil, nil, false
	}
	fight, ok := duel.Fight()
	if !ok {
		return nil, nil, false
	}
	return fight, coach, true
}

func handleActorMovementRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	var cells []MovementCell
	var lastX, lastY int32
	var lastZ int16
	for payload.Remaining() >= 10 {
		x := payload.Int32()
		y := payload.Int32()
		z := payload.Int16()
		cells = append(cells, MovementCell{X: x, Y: y, Z: z})
		lastX, lastY, lastZ = x, y, z
	}
	if payload.Err() != nil || len(cells) == 0 {
		return
	}

	frame := buildActorMovement(coach.ID, coach.PosX, coach.PosY, coach.PosZ, cells)
	for _, oc := range deps.World.Snapshot() {
		oc.Session.Send(frame)
	}

	coach.PosX, coach.PosY, coach.PosZ = lastX, lastY, lastZ
	if err := deps.Coach.UpdatePosition(context.Background(), coach.ID, lastX, lastY, lastZ); err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: failed to persist coach position")
	}
}

// handleFightInvitationRequest handles FIGHT_INVITATION_REQUEST_MESSAGE
// (4301), the "challenge by right-click on a player" flow. The legacy Java
// server (FightInvitationRequest.java) only parsed this packet and did
// nothing -- its handler ended with a "TODO: Finir d'implementer le defi
// par clic droit sur un joueur" comment, so opcodes 4300/4302/4304/4309
// were never sent by either server. This is a Go-only completion of that
// flow (see docs/opcodes/04-matchmaking-invitation.md): it validates the
// target, allocates a pending Invitation, and sends FIGHT_INVITATION
// (4300) to both parties. The client shows the target an accept/reject
// message box (FightInvitationManager.addInvitation), whose result comes
// back as FightInvitationAccept(4305) / FightInvitationReject(4307),
// handled below.
//
// Reuse note: on acceptance we create a real world.Duel via the SAME
// DuelManager.Create path matchmaking uses after OPPONENT_FOUND, so the
// entire downstream SET_READY_FOR_FIGHT -> CREATE_FIGHT -> presentation ->
// combat pipeline is shared and unchanged.
func handleFightInvitationRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	targetID := uint(payload.Int64())
	fightType := payload.Byte()
	bet := payload.Int32()
	if payload.Err() != nil {
		return
	}

	inviter, ok := sessionCoach(session)
	if !ok {
		return
	}

	// Can't challenge yourself.
	if targetID == inviter.ID {
		session.Send(buildFightInvitationError(InvitationErrTargetIsYourself))
		return
	}

	// Target must be online.
	target, ok := deps.World.Get(targetID)
	if !ok {
		session.Send(buildFightInvitationError(InvitationErrTargetNotFound))
		return
	}

	// Neither party may already be tied up in a pending invitation or an
	// in-progress duel/fight -- the inviter is "you're busy" (32), the
	// target is "target busy" (31).
	if _, busy := deps.Invitations.GetByCoach(inviter.ID); busy {
		session.Send(buildFightInvitationError(InvitationErrYoureBusy))
		return
	}
	if _, busy := deps.Duels.GetByCoach(inviter.ID); busy {
		session.Send(buildFightInvitationError(InvitationErrYoureBusy))
		return
	}
	if _, busy := deps.Invitations.GetByCoach(targetID); busy {
		session.Send(buildFightInvitationError(InvitationErrTargetBusy))
		return
	}
	if _, busy := deps.Duels.GetByCoach(targetID); busy {
		session.Send(buildFightInvitationError(InvitationErrTargetBusy))
		return
	}

	inv := deps.Invitations.Create(inviter.ID, targetID, fightType, bet)

	// Each side's FIGHT_INVITATION carries the OPPOSING team (the client
	// renders it as "the opponent's team"): the inviter sees the target's
	// team, the target sees the inviter's team. `inviter` flags whose copy
	// is the outgoing one (message-box styling only).
	toInviter := buildFightInvitation(inv.ID, true, fightType, bet, 1, targetID,
		[]invitationTeamMate{{CoachID: targetID, Name: target.Name()}})
	toTarget := buildFightInvitation(inv.ID, false, fightType, bet, 1, inviter.ID,
		[]invitationTeamMate{{CoachID: inviter.ID, Name: inviter.Name}})

	session.Send(toInviter)
	target.Session.Send(toTarget)

	deps.Logger.Debug().
		Int64("invitation_id", inv.ID).
		Uint("inviter_id", inviter.ID).
		Uint("target_id", targetID).
		Uint8("fight_type", fightType).
		Int32("bet", bet).
		Msg("dispatch: fight invitation sent")
}

// handleFightInvitationAccept handles FightInvitationAccept (4305): the
// target accepted a pending invitation. Validates the invitation still
// exists and this coach is its target, re-checks both parties are still
// online and un-dueled, then creates a real Duel (reusing the matchmaking
// DuelManager path), sends FIGHT_INVITATION_ACCEPTED (4302) to both, and
// arms the SET_READY_FOR_FIGHT forced-progress timer -- exactly as the
// OPPONENT_FOUND path does -- so the shared fight-setup flow takes over.
func handleFightInvitationAccept(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	invitationID := payload.Int64()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	inv, ok := deps.Invitations.Get(invitationID)
	if !ok || inv.TargetID != coach.ID {
		// Unknown invitation, or this coach isn't its target (stale /
		// spoofed accept) -- ignore silently.
		return
	}

	// Consume the invitation up-front so a duplicate/racing accept can't
	// create two duels.
	deps.Invitations.Remove(inv.ID)

	// Both parties must still be online and free of any other duel; if the
	// inviter dropped or got pulled into another fight in the meantime,
	// tell the accepter it fell through.
	inviter, inviterOnline := deps.World.Get(inv.InviterID)
	if !inviterOnline {
		session.Send(buildFightInvitationError(InvitationErrTargetNotFound))
		return
	}
	if _, busy := deps.Duels.GetByCoach(inv.InviterID); busy {
		session.Send(buildFightInvitationError(InvitationErrTargetBusy))
		return
	}
	if _, busy := deps.Duels.GetByCoach(inv.TargetID); busy {
		session.Send(buildFightInvitationError(InvitationErrYoureBusy))
		return
	}

	duel := deps.Duels.Create(inv.InviterID, inv.TargetID, inv.Type, inv.Bet)

	accepted := buildFightInvitationAccepted(inv.ID, duel.ID)
	inviter.Session.Send(accepted)
	session.Send(accepted)

	// Arm the SET_READY_FOR_FIGHT forced-progress timer, identical to the
	// matchmaking path (handlers_matchmaking.go): a coach who never selects
	// fighters after accepting must not stall the duel forever.
	armMatchReadyTimeout(duel, deps)

	deps.Logger.Info().
		Int64("invitation_id", inv.ID).
		Int64("duel_id", duel.ID).
		Uint("inviter_id", inv.InviterID).
		Uint("target_id", inv.TargetID).
		Msg("dispatch: fight invitation accepted, duel created")
}

// handleFightInvitationReject handles FightInvitationReject (4307): the
// target declined a pending invitation. Notifies the inviter with
// FIGHT_INVITATION_REJECTED (4304) and discards the invitation.
func handleFightInvitationReject(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	invitationID := payload.Int64()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	inv, ok := deps.Invitations.Get(invitationID)
	if !ok || inv.TargetID != coach.ID {
		return
	}

	deps.Invitations.Remove(inv.ID)

	if inviter, online := deps.World.Get(inv.InviterID); online {
		inviter.Session.Send(buildFightInvitationRejected(inv.ID))
	}

	deps.Logger.Debug().
		Int64("invitation_id", inv.ID).
		Uint("inviter_id", inv.InviterID).
		Uint("target_id", inv.TargetID).
		Msg("dispatch: fight invitation rejected")
}

// sendToCoachID looks up an online coach by ID and sends them frame,
// no-op if they're not currently online.
func sendToCoachID(deps *Deps, coachID uint, frame protocol.OutboundFrame) {
	if oc, ok := deps.World.Get(coachID); ok {
		oc.Session.Send(frame)
	}
}
