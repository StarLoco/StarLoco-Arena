package combat

import "github.com/dofusarena/go-server/internal/protocol"

// This file implements the Phase D fight-phase transition state machine
// (opcodes 8018-8040), fully spec'd in docs/opcodes/07-fight-lifecycle.md.
// Every transition funnels through one of the askForXEnd methods below,
// called either from the corresponding phase clock firing
// (handleClockFired in fight.go) or from every coach signaling ready
// (handleCoachReadyObservation/handleCoachReadyAction) -- mirroring the
// reference engine's single askForXEnd() entry point regardless of
// trigger source, see docs/opcodes/08-fight-combat-engine.md §1.4.

// askForPresentationEnd is the validate-and-drain entry point for leaving
// PRESENTATION, called identically whether triggered by the presentation
// clock firing OR by both coaches signaling ready during presentation
// (handleCoachReadyPresentation, opcode 8011 -- see §8.19 of
// docs/08-java-parity-roadmap.md), mirroring the reference engine's single
// askForXEnd() entry point regardless of trigger source.
func (f *Fight) askForPresentationEnd() {
	if f.CurrentPhase() != PhasePresentation {
		f.logger.Warn().Str("phase", f.CurrentPhase().String()).Msg("combat: askForPresentationEnd called out of order")
		return
	}
	f.cancelPhaseClock()
	f.endPresentation()
}

func (f *Fight) endPresentation() {
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendEndPresentation})
	f.startPlacement()
}

func (f *Fight) startPlacement() {
	f.setPhase(PhasePlacement)
	f.placementReadyCoaches = make(map[uint]bool)
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendStartPlacement})
	f.armPhaseClock(f.Clocks.Placement)
}

// askForPlacementEnd mirrors the reference's askForPlacementEnd(): called
// identically whether triggered by the placement clock firing or by every
// coach signaling ready-for-observation (8023) -- see
// docs/opcodes/08-fight-combat-engine.md §1.4.
func (f *Fight) askForPlacementEnd() {
	if f.CurrentPhase() != PhasePlacement {
		f.logger.Warn().Str("phase", f.CurrentPhase().String()).Msg("combat: askForPlacementEnd called out of order")
		return
	}
	f.cancelPhaseClock()
	f.endPlacement()
}

func (f *Fight) endPlacement() {
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendEndPlacement})
	f.startObservation()
}

// startObservation is guarded: only valid if the previous phase was
// PLACEMENT, mirroring AbstractFight.startObservation()'s defensive check
// (docs/opcodes/08-fight-combat-engine.md §1.1) -- logs and aborts on an
// out-of-order call rather than silently advancing.
func (f *Fight) startObservation() {
	if f.CurrentPhase() != PhasePlacement {
		f.logger.Error().Str("phase", f.CurrentPhase().String()).Msg("combat: startObservation called with wrong previous phase, aborting")
		return
	}
	f.setPhase(PhaseObservation)
	f.observationReadyCoaches = make(map[uint]bool)
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendStartObservation})
	f.armPhaseClock(f.Clocks.Observation)
}

func (f *Fight) askForObservationEnd() {
	if f.CurrentPhase() != PhaseObservation {
		f.logger.Warn().Str("phase", f.CurrentPhase().String()).Msg("combat: askForObservationEnd called out of order")
		return
	}
	f.cancelPhaseClock()
	f.endObservation()
}

func (f *Fight) endObservation() {
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendEndObservation})
	f.startAction()
}

// startAction is guarded: only valid if the previous phase was
// OBSERVATION, mirroring AbstractFight.startAction()'s defensive check.
// Cancels the (now-irrelevant) phase clock and kicks off the first table
// turn via Timeline.
func (f *Fight) startAction() {
	if f.CurrentPhase() != PhaseObservation {
		f.logger.Error().Str("phase", f.CurrentPhase().String()).Msg("combat: startAction called with wrong previous phase, aborting")
		return
	}
	f.setPhase(PhaseAction)
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendStartAction})
	f.startNextTurn()
}

// handleCoachReadyPresentation processes a
// TeamMateSetReadyForPlacementRequestMessage (Recv 8011) that arrived while
// the fight is in PRESENTATION -- the "Prêt" button clicked during the
// presentation phase (the client reuses opcode 8011 for this, distinct from
// its pre-fight teleport-gate use; dispatch routes it here only when a
// fight actor already exists and is presenting). Broadcasts the same 8012
// ack the client's NetFightPresentationFrame expects (which hides the
// "Prêt" dialog and shows a "waiting for opponent" spinner), then, once
// both coaches have voted, ends presentation immediately instead of waiting
// out the full presentation clock. See §8.19 of
// docs/08-java-parity-roadmap.md.
func (f *Fight) handleCoachReadyPresentation(coachID uint) {
	if f.CurrentPhase() != PhasePresentation {
		return
	}
	f.presentationReadyCoaches[coachID] = true
	f.broadcastAll(buildTeamMateSetReadyForPlacement(coachID))
	f.logger.Info().
		Str("event", "ready").Str("phase", "presentation").
		Uint("coach_id", coachID).
		Int("ready", len(f.presentationReadyCoaches)).Int("need", len(f.CoachIDs())).
		Msg("fight: coach ready")
	if len(f.presentationReadyCoaches) >= len(f.CoachIDs()) {
		f.askForPresentationEnd()
	}
}

// handleCoachReadyObservation processes
// TeamMateSetReadyForObservationRequestMessage (Recv 8023): per-coach
// readiness gate for leaving PLACEMENT, same shape as the existing
// placement-ready implementation (docs/opcodes/07-fight-lifecycle.md).
func (f *Fight) handleCoachReadyObservation(coachID uint) {
	if f.CurrentPhase() != PhasePlacement {
		return
	}
	f.placementReadyCoaches[coachID] = true
	f.broadcastAll(buildTeamMateSetReadyForObservation(coachID))
	f.logger.Info().
		Str("event", "ready").Str("phase", "placement").
		Uint("coach_id", coachID).
		Int("ready", len(f.placementReadyCoaches)).Int("need", len(f.CoachIDs())).
		Msg("fight: coach ready")
	if len(f.placementReadyCoaches) >= len(f.CoachIDs()) {
		f.askForPlacementEnd()
	}
}

// handleCoachReadyAction processes
// TeamMateSetReadyForActionRequestMessage (Recv 8031): per-coach readiness
// gate for leaving OBSERVATION.
func (f *Fight) handleCoachReadyAction(coachID uint) {
	if f.CurrentPhase() != PhaseObservation {
		return
	}
	f.observationReadyCoaches[coachID] = true
	f.broadcastAll(buildTeamMateSetReadyForAction(coachID))
	f.logger.Info().
		Str("event", "ready").Str("phase", "observation").
		Uint("coach_id", coachID).
		Int("ready", len(f.observationReadyCoaches)).Int("need", len(f.CoachIDs())).
		Msg("fight: coach ready")
	if len(f.observationReadyCoaches) >= len(f.CoachIDs()) {
		f.askForObservationEnd()
	}
}

// handleMoveToFreePlacement processes MoveToFreePlacementRequestMessage
// (Recv 8021): moves a fighter to a free cell during PLACEMENT.
//
// Validation rejects a move if another living fighter already occupies the
// target X/Y, plus -- when real map data is attached to this fight (see
// SetMapData, docs/08-java-parity-roadmap.md Phase K) -- if the target
// cell has no walkable surface at all, OR if the target cell is not in the
// fighter's own team's free-placement ZONE.
//
// The legal placement zone is exactly that team's FightStartPointElement
// (map element kind 1000) cells, keyed by team side -- mirroring the
// reference client, which only sends a MoveToFreePlacementRequest (8021)
// when StartPointManager.containsTarget(teamId, target) is true, and builds
// that set solely from type-1000 elements split by team id (see
// StartPointManager.java / UIFightPlacementFrame.java). Map.FightStartCells()
// already exposes exactly this per-team set, keyed by the raw team-side byte
// (teamSideByte = TeamID-1, matching buildCombatTeam/resolveFighterPlacementCells).
//
// Falls back to occupancy-only checking if no map data is attached (dev/test
// setups without the game's data files present), and to walkable-only if the
// map has no start cells for that team side (never lock a player out of
// repositioning over a data quirk -- resolveFighterPlacementCells degrades
// the same way). A rejected placement produces no 8022 frame, mirroring the
// existing occupancy/ownership silent-reject behavior.
func (f *Fight) handleMoveToFreePlacement(c cmdMoveToFreePlacement) {
	if f.CurrentPhase() != PhasePlacement {
		return
	}
	fighter, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok {
		return
	}
	if f.IsOccupied(c.Pos, fighter) {
		return
	}
	if f.mapData != nil && !f.IsWalkable(c.Pos) {
		return
	}
	if !f.isInPlacementZone(fighter, c.Pos) {
		return
	}
	fighter.Position = c.Pos
	f.broadcastAll(buildMoveToFreePlacement(c.FighterID, c.Pos))
}

// isInPlacementZone reports whether pos (by X/Y) is a legal free-placement
// cell for fighter -- i.e. one of its own team's FightStartPointElement
// cells (see handleMoveToFreePlacement). Returns true (permissive) when no
// map data is attached, or when the map defines no start cells for this
// fighter's team side, so those setups keep the pre-zone occupancy/walkable
// behavior rather than rejecting every move.
func (f *Fight) isInPlacementZone(fighter *Fighter, pos Point3) bool {
	if f.mapData == nil {
		return true
	}
	// teamSideByte 0/1 <- TeamID 1/2 (buildCombatTeam convention).
	if fighter.TeamID == 0 {
		return true
	}
	zone := f.mapData.FightStartCells()[fighter.TeamID-1]
	if len(zone) == 0 {
		return true
	}
	for _, cell := range zone {
		if cell[0] == pos.X && cell[1] == pos.Y {
			return true
		}
	}
	return false
}
