package game

import (
	"strings"
	"sync"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// 2v2 team formation.
//
// This is the PRODUCTION path, and it is not the one item 33's opcodes describe.
// The team panel's "Creer une equipe 2VS2" (hu_2 case 16636) opens
// team2vs2NameDialog, the teammate is chosen from the coach's own FRIEND LIST
// (case 16635, built from mc_1.qM().qN()), and the named pair goes out as 6024.
// The 26313 "XvsXInvitation" request is a different thing entirely: its only
// builder is a binding in the client's `Test` Lua library, with no UI.
//
// The handshake:
//
//	6024 C2S  inviter -> server   [u8 teamName][i64 inviterId][i64 invitedId]
//	6025 S2C  server  -> invited  [u8 teamName][u8 inviterName][i64 inviterId][i64 invitedId]
//	6026 C2S  invited -> server   [i8 accept][u8 teamName][i64 inviterId][i64 invitedId][i16 reason]
//	6027 S2C  server  -> inviter  (empty) "Le coach a refuse la creation, ou est indisponible."
//	6028 S2C  server  -> both     (empty) both clients open the fighter picker
//
// The client auto-refuses on the invited side when it is already in a team or
// the inviter is on its ignore list (ug_1 case 6025 / lg_0 case 6025), sending
// 6026 with accept=0 and reason=2. The server enforces the same rules anyway:
// the client's checks protect the client's UX, not the server's state, and 6024
// is a bare pair of ids that anyone can craft.

// teamUpPair is a formed 2v2 duo, held until it is spent on a fight.
type teamUpPair struct {
	Name      string
	InviterID uint
	InvitedID uint
	// ready tracks which members have pressed Combattre. Guarded by teamUps.mu.
	ready map[uint]bool
}

// pendingTeamUp is an invitation awaiting the invited coach's answer.
type pendingTeamUp struct {
	Name      string
	InviterID uint
}

// teamUps holds pending 2v2 invitations and the duos that came out of them.
//
// Deliberately in memory rather than in the store: a duo is a lobby-lived
// arrangement (both clients sit on the fighter picker with it), and neither the
// client nor the protocol offers any way to resume one across a reconnect - the
// invitation dialog is modal and dies with the session.
type teamUps struct {
	mu      sync.Mutex
	pending map[uint]pendingTeamUp // invited coach id -> invitation
	pairs   map[uint]*teamUpPair   // EACH member's coach id -> the shared duo
}

func newTeamUps() *teamUps {
	return &teamUps{
		pending: map[uint]pendingTeamUp{},
		pairs:   map[uint]*teamUpPair{},
	}
}

func (t *teamUps) invite(invited uint, p pendingTeamUp) {
	t.mu.Lock()
	defer t.mu.Unlock()
	t.pending[invited] = p // one pending invitation per coach; a newer one wins
}

// takePending removes and returns the invitation held for invited, if the
// inviter matches. Matching on the inviter is what stops a crafted 6026 from
// consuming an invitation that came from somebody else.
func (t *teamUps) takePending(invited, inviter uint) (pendingTeamUp, bool) {
	t.mu.Lock()
	defer t.mu.Unlock()
	p, ok := t.pending[invited]
	if !ok || p.InviterID != inviter {
		return pendingTeamUp{}, false
	}
	delete(t.pending, invited)
	return p, true
}

func (t *teamUps) cancelPending(invited uint) {
	t.mu.Lock()
	defer t.mu.Unlock()
	delete(t.pending, invited)
}

func (t *teamUps) bind(p *teamUpPair) {
	t.mu.Lock()
	defer t.mu.Unlock()
	t.pairs[p.InviterID] = p
	t.pairs[p.InvitedID] = p
}

// PairOf returns the duo coachID belongs to, or nil.
func (t *teamUps) PairOf(coachID uint) *teamUpPair {
	t.mu.Lock()
	defer t.mu.Unlock()
	return t.pairs[coachID]
}

// release drops the duo both members share.
func (t *teamUps) release(coachID uint) {
	t.mu.Lock()
	defer t.mu.Unlock()
	p := t.pairs[coachID]
	if p == nil {
		return
	}
	delete(t.pairs, p.InviterID)
	delete(t.pairs, p.InvitedID)
}

// Partner returns the other member of coachID's duo (0 if it has none).
func (t *teamUps) Partner(coachID uint) uint {
	p := t.PairOf(coachID)
	if p == nil {
		return 0
	}
	if p.InviterID == coachID {
		return p.InvitedID
	}
	return p.InviterID
}

func registerTeamUpHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpTeamUpRequest, handleTeamUpRequest)
	r.Register(protocol.OpTeamUpAnswer, handleTeamUpAnswer)
}

// teamUpName trims a client-supplied team name to something safe to echo.
//
// The name goes straight back out in 6025, which the invited client renders
// into a dialog, and the chat renderer parses markup without escaping it (see
// B-104) - so the same stripping applies here.
func teamUpName(s string) string {
	s = strings.ReplaceAll(s, "<", "")
	s = strings.ReplaceAll(s, ">", "")
	s = strings.TrimSpace(s)
	if len(s) > 32 {
		s = s[:32]
	}
	if s == "" {
		s = "2vs2"
	}
	return s
}

// handleTeamUpRequest handles 6024 (ir_0): the inviter names a duo and picks a
// teammate from its friend list.
func handleTeamUpRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	name, err := r.StringU8()
	if err != nil {
		return nil
	}
	if _, err = r.I64(); err != nil { // the inviter id the client sends
		return nil
	}
	invitedID64, err := r.I64()
	if err != nil {
		return nil
	}
	// The inviter is the SENDER, never the id in the packet: trusting that field
	// would let anyone open invitations in another coach's name.
	inviter := s.Coach
	invited := uint(invitedID64)

	if !s.deps.teamUpAllowed(inviter.ID, invited) {
		return s.sendEmpty(protocol.OpTeamUpRefused)
	}
	target := s.deps.sessionForCoach(invited)
	if target == nil || target.Coach == nil {
		return s.sendEmpty(protocol.OpTeamUpRefused)
	}

	name = teamUpName(name)
	s.deps.TeamUps.invite(invited, pendingTeamUp{Name: name, InviterID: inviter.ID})

	w := protocol.NewWriter()
	w.StringU8(name)
	w.StringU8(inviter.Name)
	w.I64(int64(inviter.ID))
	w.I64(int64(invited))
	frame, err := protocol.EncodeS2C(protocol.OpTeamUpInvitation, w.Bytes())
	if err != nil {
		return err
	}
	s.log.Info("2v2 invitation", "from", inviter.Name, "to", target.Coach.Name, "team", name)
	return target.Send(frame)
}

// teamUpAllowed re-derives every rule the client applies before offering the
// invitation, plus the ones it cannot know about.
func (d *Deps) teamUpAllowed(inviter, invited uint) bool {
	if invited == 0 || invited == inviter {
		return false // "Impossible de faire un combat avec soi-meme !"
	}
	// Neither side may already be committed: to a fight, or to another duo.
	if d.Fights != nil && (d.Fights.ByCoach(inviter) != nil || d.Fights.ByCoach(invited) != nil) {
		return false
	}
	if d.TeamUps.PairOf(inviter) != nil || d.TeamUps.PairOf(invited) != nil {
		return false
	}
	// The invited coach must be online, and must not have ignored the inviter -
	// the same rule the client applies locally (ug_1 checks its ignore map before
	// even showing the dialog), enforced here because a crafted 6024 never asks.
	target := d.sessionForCoach(invited)
	if target == nil || target.Coach == nil {
		return false
	}
	return !ignoresCoach(target.Coach, inviter)
}

// handleTeamUpAnswer handles 6026 (abB): accept or refuse an invitation.
func handleTeamUpAnswer(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	accept, err := r.U8()
	if err != nil {
		return nil
	}
	name, err := r.StringU8()
	if err != nil {
		return nil
	}
	inviterID64, err := r.I64()
	if err != nil {
		return nil
	}
	// The remaining [i64 invitedId][i16 reason] are echoed back by the client and
	// carry nothing the server does not already know.

	inviter := uint(inviterID64)
	pending, ok := s.deps.TeamUps.takePending(s.Coach.ID, inviter)
	if !ok {
		// "Pas d'invitation trouvee" - answer nothing rather than inventing a duo.
		return nil
	}
	inviterSess := s.deps.sessionForCoach(inviter)

	if accept == 0 {
		s.log.Info("2v2 invitation refused", "by", s.Coach.Name, "inviter", inviter)
		if inviterSess != nil {
			return inviterSess.sendEmpty(protocol.OpTeamUpRefused)
		}
		return nil
	}
	// Re-check on ACCEPT as well: the invitation may have been sitting in a
	// dialog while the inviter started a fight or formed another duo.
	if inviterSess == nil || !s.deps.teamUpAllowed(inviter, s.Coach.ID) {
		if inviterSess != nil {
			_ = inviterSess.sendEmpty(protocol.OpTeamUpRefused)
		}
		return s.sendEmpty(protocol.OpTeamUpRefused)
	}

	if name == "" {
		name = pending.Name
	}
	pair := &teamUpPair{Name: teamUpName(name), InviterID: inviter, InvitedID: s.Coach.ID}
	s.deps.TeamUps.bind(pair)
	s.log.Info("2v2 team formed", "team", pair.Name, "inviter", inviter, "invited", s.Coach.ID)

	// Give BOTH coaches a real 2v2 team preset naming the other as its ally.
	// Without one the 2VS2 tab has nothing to show and its "Combattre" has
	// nothing to launch: the duo would exist server-side and be invisible.
	s.deps.createDuoPresets(pair)

	// Both clients open the fighter picker on 6028.
	if err := inviterSess.sendEmpty(protocol.OpTeamUpAccepted); err != nil {
		return err
	}
	if err := s.sendEmpty(protocol.OpTeamUpAccepted); err != nil {
		return err
	}
	// Push the refreshed preset lists so the new team appears in both panels.
	_ = inviterSess.pushTeamPresetList()
	return s.pushTeamPresetList()
}

// createDuoPresets gives each member of a duo a GameMode-2 team preset whose
// coach list names its partner.
//
// One preset per coach, not one shared row: each side owns its own fighters and
// its own slot in its own panel, and the client reads the preset out of the list
// it was sent. Existing presets for the same pair are reused so pressing accept
// twice does not litter the panel.
func (d *Deps) createDuoPresets(p *teamUpPair) {
	if d.Store == nil || p == nil {
		return
	}
	for _, side := range [2][2]uint{{p.InviterID, p.InvitedID}, {p.InvitedID, p.InviterID}} {
		owner, ally := side[0], side[1]
		existing, err := d.Store.Teams.ListByCoach(owner)
		if err != nil {
			continue
		}
		var found *domain.Team
		for i := range existing {
			if existing[i].AllyCoachID == ally && existing[i].GameMode == gameMode2v2 {
				found = &existing[i]
				break
			}
		}
		team := &domain.Team{
			CoachID: owner, Name: p.Name,
			GameMode: gameMode2v2, AllyCoachID: ally,
		}
		if found != nil {
			team.ID = found.ID
		}
		if err := d.Store.Teams.Upsert(team); err != nil {
			d.Log.Warn("2v2 preset save failed", "coach", owner, "err", err)
		}
	}
}

// gameMode2v2 is the client's zK.cB() value for a two-coach team. It is what
// makes the client show "waitingReplyForFight" when the team is launched.
const gameMode2v2 int16 = 2

// duoLaunchPartner returns the partner id when a 23103 "Combattre" is a DUO
// launch, or 0 when it is an ordinary solo one.
//
// The client's id is only a hint: it is accepted solely when the server's own
// duo registry agrees that these two are partners. A crafted 23103 naming a
// stranger therefore launches a normal solo fight rather than dragging that
// stranger in.
func (d *Deps) duoLaunchPartner(self uint, claimed int64) uint {
	if d.TeamUps == nil || claimed <= 0 || uint(claimed) == self {
		return 0 // afG == -1 (no ally) or the client's own fallback to self
	}
	partner := d.TeamUps.Partner(self)
	if partner == 0 || partner != uint(claimed) {
		return 0
	}
	return partner
}

// markLaunchReady records that a duo member pressed Combattre, returning how
// many of the pair are ready and whether that is both.
func (t *teamUps) markLaunchReady(self, partner uint) (int, bool) {
	t.mu.Lock()
	defer t.mu.Unlock()
	p := t.pairs[self]
	if p == nil {
		return 0, false
	}
	if p.ready == nil {
		p.ready = map[uint]bool{}
	}
	p.ready[self] = true
	n := len(p.ready)
	return n, p.ready[self] && p.ready[partner]
}

// joinDuoPartner adds the coach's 2v2 partner to a side that has already been
// built for the coach itself, so one fight carries both allies.
//
// The partner's fighters are placed on the start cells the first coach did not
// use. A side has 8 start cells on most arenas (34 of 47), so two six-fighter
// rosters cannot both fit whole - the partner takes what is left, which is the
// same clamp the single-coach path already applies.
//
// Returns true if a partner was actually added. It is deliberately quiet when
// there is no duo, no online partner, or no room: a 2v2 that cannot be seated
// degrades to the 1v1 that was already valid rather than failing the fight.
func (d *Deps) joinDuoPartner(team *FightTeam, ownerID uint, cells []Pos) bool {
	if team == nil || d.TeamUps == nil {
		return false
	}
	partnerID := d.TeamUps.Partner(ownerID)
	if partnerID == 0 {
		return false
	}
	ps := d.sessionForCoach(partnerID)
	if ps == nil || ps.Coach == nil {
		return false
	}
	free := len(cells) - len(team.Fighters)
	if free <= 0 {
		return false
	}
	spare := cells[len(team.Fighters):]
	partner, err := d.buildFightTeamFor(ps, team.ID, spare, nil)
	if err != nil || partner == nil || len(partner.Members) == 0 {
		return false
	}
	team.Members = append(team.Members, partner.Members...)
	team.Fighters = append(team.Fighters, partner.Fighters...)
	return true
}

// sendEmpty sends a payload-less S2C frame.
func (s *Session) sendEmpty(opcode uint16) error {
	frame, err := protocol.EncodeS2C(opcode, nil)
	if err != nil {
		return err
	}
	return s.Send(frame)
}
