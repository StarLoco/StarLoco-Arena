package combat

// Team is one side of a fight, holding one or more TeamMates (participants,
// each possibly controlling several Fighters via summons). See
// docs/05-combat-engine.md §5.1 (FightingTeam/TeamMate).
type Team struct {
	ID   uint8
	Name string

	Mates []*TeamMate
}

// TeamMate is a single real participant (a Coach) within a Team, along with
// the Fighters they brought into this fight.
type TeamMate struct {
	CoachID  uint
	Fighters []*Fighter
}

// AliveFighterCount returns how many of this team's fighters (across all
// team mates) are still alive.
func (t *Team) AliveFighterCount() int {
	count := 0
	for _, mate := range t.Mates {
		for _, f := range mate.Fighters {
			if !f.IsDead {
				count++
			}
		}
	}
	return count
}
