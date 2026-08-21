package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerTeamHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpTeamPresetSave, handleTeamPresetSave)
	r.Register(protocol.OpTeamPresetDelete, handleTeamPresetDelete)
	r.Register(protocol.OpFighterAssignTeam, handleFighterAssignTeam)
}

// handleFighterAssignTeam (6013 C2S: [i64 fighterId][i16 srcTeam][i16 dstTeam][i64 am]).
// The client's qp_1 packet (onFighterDropped / onFighterRemoved) sends this when a
// fighter is dragged between the pool and a team slot, or removed from a slot:
//   - dstTeam == -1  -> remove the fighter from srcTeam
//   - srcTeam == -1  -> the fighter came from the pool (nothing to unlink)
//   - otherwise      -> a move (unlink from srcTeam, link into dstTeam)
//
// am is the owning coach id (1v1) / teammate slot (2v2) and is not needed to
// persist 1v1 membership. Without this handler the packet was silently dropped, so
// team_fighters never persisted and a freshly built team reverted to the pool on
// reopen (the "fighters vanish" report).
func handleFighterAssignTeam(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	fid64, err := r.I64()
	if err != nil {
		return err
	}
	srcU, err := r.U16()
	if err != nil {
		return err
	}
	dstU, err := r.U16()
	if err != nil {
		return err
	}
	// am (i64) follows — owning coach / teammate slot; unused for 1v1 persistence.
	fid := uint(fid64)
	src := int16(srcU)
	dst := int16(dstU)

	// IDOR guard: the coach must own the fighter being assigned.
	roster, err := s.deps.Store.Fighters.ListByCoach(s.Coach.ID)
	if err != nil {
		return err
	}
	var moved *domain.Fighter
	for i := range roster {
		if roster[i].ID == fid {
			moved = &roster[i]
			break
		}
	}
	if moved == nil {
		return nil // not this coach's fighter — ignore
	}

	// Unlink from the source team when the fighter is leaving it.
	if src > 0 && src != dst {
		if t, err := s.deps.Store.Teams.Get(uint(src)); err == nil && t.CoachID == s.Coach.ID {
			if err := s.deps.Store.Teams.RemoveMember(uint(src), fid); err != nil {
				return err
			}
		}
	}

	// Link into the destination team (dst <= 0 is a pure removal to the pool).
	if dst > 0 {
		if t, err := s.deps.Store.Teams.Get(uint(dst)); err == nil && t.CoachID == s.Coach.ID && canPlaceFighter(t, moved, roster) {
			if err := s.deps.Store.Teams.AddMember(uint(dst), fid); err != nil {
				return err
			}
		}
	}

	s.log.Info("fighter team assignment", "fighter", fid, "src", src, "dst", dst)

	// Refresh the client's roster (pool) and team list so slots/pool reconcile.
	if err := s.pushFighterList(); err != nil {
		return err
	}
	return s.pushTeamPresetList()
}

// canPlaceFighter reports whether fighter may be added to team t under the client's
// team-building rules: no duplicate, at most 6 fighters, and at most 2 of the same
// breed. roster is the coach's fighters (for breed lookup of existing members).
func canPlaceFighter(t *domain.Team, fighter *domain.Fighter, roster []domain.Fighter) bool {
	if len(t.Members) >= 6 {
		return false
	}
	breedByID := make(map[uint]uint8, len(roster))
	for i := range roster {
		breedByID[roster[i].ID] = roster[i].BreedID
	}
	sameBreed := 0
	for _, m := range t.Members {
		if m.FighterID == fighter.ID {
			return false // already on the team
		}
		if breedByID[m.FighterID] == fighter.BreedID {
			sameBreed++
		}
	}
	return sameBreed < 2
}

// handleTeamPresetSave (6021 C2S: [sw_1 blob][u8 pad]) persists a team preset
// (members scoped to the coach's own fighters) and re-sends the team list.
func handleTeamPresetSave(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	tp, err := decodeTeamPreset(f.Payload)
	if err != nil {
		return err
	}

	team := &domain.Team{
		CoachID:  s.Coach.ID,
		Name:     sanitizeFighterName(tp.Name),
		Type:     tp.Type,
		GameMode: tp.GameMode,
		App1:     tp.App[0], App2: tp.App[1], App3: tp.App[2], App4: tp.App[3],
	}
	if tp.TeamID > 0 {
		team.ID = uint(tp.TeamID)
	}
	// Only include fighters the coach actually owns (IDOR guard).
	owned := s.ownedFighterSet()
	for _, fid := range tp.FighterIDs {
		if owned[uint(fid)] {
			team.Members = append(team.Members, domain.TeamFighter{FighterID: uint(fid)})
		}
	}
	if err := s.deps.Store.Teams.Upsert(team); err != nil {
		return err
	}
	s.log.Info("team preset saved", "name", team.Name, "members", len(team.Members))

	return s.pushTeamPresetList()
}

// handleTeamPresetDelete (6023 C2S: aad_1.encode = [i64 teamId][i16 Gm][i16 fA]).
// The team id is an i64 -- reading it as a u16 (the old bug) only saw the top 2
// zero bytes, so real ids decoded as 0 and the delete silently no-op'd.
func handleTeamPresetDelete(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	id, err := r.I64()
	if err != nil {
		return err
	}
	// r.U16() (Gm) and r.U16() (fA) follow but are unused.
	teamID := uint(id)
	if teamID != 0 {
		if _, err := s.deps.Store.Teams.Delete(teamID, s.Coach.ID); err != nil {
			return err
		}
		s.log.Info("team preset deleted", "id", teamID)
	}
	return s.pushTeamPresetList()
}

// ownedFighterSet returns the set of fighter ids owned by the coach.
func (s *Session) ownedFighterSet() map[uint]bool {
	set := make(map[uint]bool)
	fighters, err := s.deps.Store.Fighters.ListByCoach(s.Coach.ID)
	if err != nil {
		return set
	}
	for _, f := range fighters {
		set[f.ID] = true
	}
	return set
}

// pushTeamPresetList sends TeamPresetList(6030):
// [u8 presetCount]{sw_1 blob} then [u8 coachCount]{...}.
func (s *Session) pushTeamPresetList() error {
	if s.Coach == nil || s.deps == nil || s.deps.Store == nil {
		return nil // no persistence wired (unit harness); nothing to list
	}
	teams, err := s.deps.Store.Teams.ListByCoach(s.Coach.ID)
	if err != nil {
		return err
	}
	fighters, _ := s.deps.Store.Fighters.ListByCoach(s.Coach.ID)
	// Fighter -> owning coach. The second i64 of each preset fighter entry is the
	// OWNER (see encodeTeamPreset), and a 2v2 preset lists the ally's fighters
	// too, so their owner has to be resolved rather than assumed.
	ownerOf := make(map[uint]uint, len(fighters))
	for i := range fighters {
		ownerOf[fighters[i].ID] = fighters[i].CoachID
	}
	for i := range teams {
		if teams[i].AllyCoachID == 0 {
			continue
		}
		allies, err := s.deps.Store.Fighters.ListByCoach(teams[i].AllyCoachID)
		if err != nil {
			continue
		}
		for j := range allies {
			ownerOf[allies[j].ID] = allies[j].CoachID
		}
	}

	// Lead the team list with an EMPTY type=-4 team. The client's Evolution
	// first-open handler (ce_1 case 6030) does an unchecked arrayList.get(0), so
	// the list must be non-empty; but the -4 team must NOT list any fighters —
	// the client's fighter-pool filter (U/Z) hides any fighter that belongs to
	// any team in the 6030, so a populated -4 team makes the Elite roster render
	// empty. The coach's fighters reach the client via 6006 (type=1) only.
	_ = fighters // fighters flow via 6006, not the team list
	w := protocol.NewWriter().U8(uint8(len(teams) + 1))
	w.Raw(benchTeamPreset())
	for i := range teams {
		w.Raw(encodeTeamPreset(&teams[i], ownerOf))
	}
	w.U8(0) // coach section (empty)

	frame, err := protocol.EncodeS2C(protocol.OpTeamPresetList, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}
