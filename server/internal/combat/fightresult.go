package combat

import "time"

// FightEndParticipant is one coach's outcome for a finished fight, handed to
// the FightEndHook so the dispatch/service layer can persist ladder &
// lifetime statistics (see docs/opcodes/03-coach-world.md and
// internal/service/coach.go's ApplyFightResult).
type FightEndParticipant struct {
	CoachID uint
	Won     bool
}

// FightEndOutcome is the per-coach data the FightEndHook returns to the
// Fight actor so it can populate END_FIGHT's per-player Strength (drives the
// client's Level/Rank via Coach.setLadderStrength) and the opaque
// PlayerStatisticsReport blob (drives the "Statistiques du Coach" window on
// the results screen). Empty/zero values are safe: Strength 0 and a nil
// Report reproduce the legacy "no stats" wire shape.
type FightEndOutcome struct {
	Strength int16
	Report   []byte

	// LostCards / WonCards are the card-wagering results for this coach in a
	// bet fight: the cards this coach lost (its stake, transferred to the
	// opponent) and won (the opponent's stake, received). Both drive the
	// END_FIGHT lost/won display blobs (the actual inventory transfer is
	// done authoritatively by the hook -- CoachService.TransferCard -- before
	// it returns these). Empty for a no-bet fight. Exported (unlike the
	// internal cardBlobEntry) so the dispatch-package hook can populate them.
	LostCards []FightEndCard
	WonCards  []FightEndCard
}

// FightEndCard is one wagered card outcome: the template id + cursed flag
// the END_FIGHT wire blob carries (see writeCardBlob / cardBlobCard). Kept
// exported and package-independent so the dispatch-layer FightEndHook can
// build it; endFight maps it into the internal cardBlobEntry for the wire.
type FightEndCard struct {
	TemplateID int32
	Cursed     bool
}

// FightEndHook is invoked exactly once, from the Fight actor goroutine, the
// moment a fight ends (both the normal all-but-one-team-eliminated path and
// the flee/give-up path). It receives every participant's win/loss result
// plus the fight's duration, persists the statistics, and returns the
// updated per-coach strength + report blob to embed in END_FIGHT. Returning
// nil (or a nil map) leaves END_FIGHT with the legacy empty-stats shape.
//
// The hook must not block on network I/O for long -- it runs inside the
// fight actor loop. The dispatch implementation does a single bounded DB
// transaction (CoachService.ApplyFightResult) per participant.
type FightEndHook func(participants []FightEndParticipant, duration time.Duration) map[uint]FightEndOutcome
