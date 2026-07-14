package dispatch

import (
	"context"
	"time"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/protocol"
)

// buildFightEndHook returns the combat.FightEndHook wired to persist coach
// statistics when a fight ends. For each participant it:
//
//   - persists the win/loss result, updated consecutive-win streak, fight
//     time, and new ladder strength (CoachService.ApplyFightResult);
//   - refreshes the coach cached on the still-online session so a later
//     COACH_INFORMATION / stats packet reflects the new values without a
//     re-login;
//   - pushes a fresh PLAYER_STATISTICS_REPORT (2400) to the coach so an
//     open "Statistiques du Coach" window updates live;
//   - returns the new Strength (drives the results-screen Level/Rank via
//     Coach.setLadderStrength) and a serialized PlayerStatisticsReport blob
//     for END_FIGHT.
//
// It runs inside the fight actor goroutine, so it does bounded work only: a
// short DB transaction per participant plus a couple of buffered sends.
func buildFightEndHook(deps *Deps) combat.FightEndHook {
	return func(participants []combat.FightEndParticipant, duration time.Duration) map[uint]combat.FightEndOutcome {
		ctx := context.Background()
		secs := int64(duration / time.Second)
		outcomes := make(map[uint]combat.FightEndOutcome, len(participants))

		for _, p := range participants {
			coach, err := deps.Coach.ApplyFightResult(ctx, p.CoachID, p.Won, secs)
			if err != nil {
				deps.Logger.Error().Err(err).
					Uint("coach_id", p.CoachID).
					Bool("won", p.Won).
					Msg("dispatch: failed to persist fight statistics")
				continue
			}

			// Refresh the session-cached coach and push a live stats
			// update if the coach is still online. The live coach is the
			// same pointer stashed as the session's cached coach (see
			// enterWorld/setSessionCoach), so updating its stat fields
			// keeps a later COACH_INFORMATION / stats packet consistent
			// without a re-login. The write goes through the registry lock
			// (UpdateStats) because this runs on the fight actor goroutine
			// and would otherwise race a concurrent broadcast reading those
			// same fields via a view (e.g. another fight returning a coach
			// to the world). buildPlayerStatisticsReport reads the FRESH DB
			// `coach` copy, not the shared pointer, so it stays race-free.
			deps.World.UpdateStats(p.CoachID, coach)
			if oc, online := deps.World.Get(p.CoachID); online {
				oc.Session.Send(buildPlayerStatisticsReport(coach))
			}

			// END_FIGHT carries strength as an int16; the ranked range
			// (1000..3000) fits comfortably.
			report := protocol.NewWriter(64)
			serializePlayerStatisticsReport(report, coach)
			outcomes[p.CoachID] = combat.FightEndOutcome{
				Strength: int16(coach.Strength),
				Report:   report.Bytes(),
			}
		}

		// Card wagering: transfer each loser's staked card to the winner
		// (the authoritative grant the client's END_FIGHT payload only
		// displays). Only runs for a bet fight (the duel carries stake
		// cards); a no-bet fight leaves every outcome's card lists empty.
		settleWageredCards(ctx, deps, participants, outcomes)

		return outcomes
	}
}

// settleWageredCards performs the end-of-fight card transfer for a bet
// fight and fills each participant's FightEndOutcome LostCards/WonCards for
// the END_FIGHT display blobs. It looks the duel up via any participant
// (they all share one), reads each coach's staked card off the duel, and
// for every LOSER transfers that stake to the winner (there is exactly one
// winner in a 1v1; in a hypothetical draw with no winner nothing is
// transferred). A missing duel or no stakes (no-bet fight) is a silent
// no-op. Each transfer is a single bounded DB transaction
// (CoachService.TransferCard) that re-verifies ownership, so a card that was
// locked/unequipped/gone mid-fight is safely skipped.
func settleWageredCards(ctx context.Context, deps *Deps, participants []combat.FightEndParticipant, outcomes map[uint]combat.FightEndOutcome) {
	if len(participants) == 0 {
		return
	}
	duel, ok := deps.Duels.GetByCoach(participants[0].CoachID)
	if !ok {
		return
	}

	// Identify the (single) winner. If there's no winner (draw), no cards
	// change hands -- a bet is settled only on a decisive result.
	var winnerID uint
	var haveWinner bool
	for _, p := range participants {
		if p.Won {
			winnerID = p.CoachID
			haveWinner = true
			break
		}
	}
	if !haveWinner {
		return
	}

	for _, p := range participants {
		if p.Won {
			continue // losers stake to the winner; the winner stakes to nobody
		}
		stakeCardID, staked := duel.StakeCard(p.CoachID)
		if !staked {
			continue // this coach had no stake (no-bet fight, or none eligible)
		}
		transferred, err := deps.Coach.TransferCard(ctx, p.CoachID, winnerID, stakeCardID)
		if err != nil {
			deps.Logger.Error().Err(err).
				Uint("loser", p.CoachID).Uint("winner", winnerID).Uint("card", stakeCardID).
				Msg("dispatch: staked-card transfer failed")
			continue
		}
		if transferred == nil {
			continue // card was gone/locked/equipped at settle time -- nothing moved
		}
		card := combat.FightEndCard{TemplateID: transferred.TemplateID, Cursed: transferred.Cursed}
		// Record on both sides' outcomes: the loser LOST it, the winner WON it.
		loserOutcome := outcomes[p.CoachID]
		loserOutcome.LostCards = append(loserOutcome.LostCards, card)
		outcomes[p.CoachID] = loserOutcome

		winnerOutcome := outcomes[winnerID]
		winnerOutcome.WonCards = append(winnerOutcome.WonCards, card)
		outcomes[winnerID] = winnerOutcome

		deps.Logger.Info().
			Uint("loser", p.CoachID).Uint("winner", winnerID).
			Int32("card_template", transferred.TemplateID).Bool("cursed", transferred.Cursed).
			Msg("dispatch: staked card transferred to winner")

		// The transfer changed both coaches' persisted inventories; refresh
		// the in-memory registry copies so a later ACTOR_SPAWN reflects the
		// new ownership rather than the stale login-time snapshot.
		refreshOnlineInventory(deps, p.CoachID)
		refreshOnlineInventory(deps, winnerID)
	}
}


