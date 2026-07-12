package service

import (
	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/world"
)

// MatchmakingService bridges the world.Matchmaker queue to duel creation
// via world.DuelManager, replacing the legacy WaitingOpponent/FightManager
// coupling that lived directly in packet-handler code.
type MatchmakingService struct {
	matchmaker *world.Matchmaker
	duels      *world.DuelManager
	logger     zerolog.Logger
}

// NewMatchmakingService constructs a MatchmakingService.
func NewMatchmakingService(matchmaker *world.Matchmaker, duels *world.DuelManager, logger zerolog.Logger) *MatchmakingService {
	return &MatchmakingService{matchmaker: matchmaker, duels: duels, logger: logger}
}

// SearchOpponent enqueues coachID's search and, if a compatible waiting
// opponent is found, creates a Duel and returns it (nil otherwise, meaning
// the search is now queued and waiting).
func (s *MatchmakingService) SearchOpponent(coachID uint, fightType byte, bet int32) *world.Duel {
	searcher := &world.WaitingOpponent{CoachID: coachID, Type: fightType, Bet: bet}
	opponent, found := s.matchmaker.FindMatch(searcher)
	if !found {
		return nil
	}

	duel := s.duels.Create(coachID, opponent.CoachID, fightType, bet)
	s.logger.Info().
		Int64("duel_id", duel.ID).
		Uint("coach_a", coachID).
		Uint("coach_b", opponent.CoachID).
		Msg("service: matchmaking found opponent, duel created")
	return duel
}

// CancelSearch removes coachID from the waiting queue.
func (s *MatchmakingService) CancelSearch(coachID uint) bool {
	return s.matchmaker.Cancel(coachID)
}
