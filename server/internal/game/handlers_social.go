package game

import (
	"errors"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
	"gorm.io/gorm"
)

func registerSocialHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpAddFriend, handleAddFriend)
	r.Register(protocol.OpRemoveFriend, handleRemoveFriend)
	r.Register(protocol.OpAddIgnore, handleAddIgnore)
	r.Register(protocol.OpRemoveIgnore, handleRemoveIgnore)
}

// readName reads a [u8 len][name] payload.
func readName(f *protocol.C2SFrame) (string, error) {
	return protocol.NewReader(f.Payload).StringU8()
}

func handleAddFriend(s *Session, f *protocol.C2SFrame) error {
	return s.socialEdit(f, socialFriend, true)
}
func handleRemoveFriend(s *Session, f *protocol.C2SFrame) error {
	return s.socialEdit(f, socialFriend, false)
}
func handleAddIgnore(s *Session, f *protocol.C2SFrame) error {
	return s.socialEdit(f, socialIgnore, true)
}
func handleRemoveIgnore(s *Session, f *protocol.C2SFrame) error {
	return s.socialEdit(f, socialIgnore, false)
}

type socialKind int

const (
	socialFriend socialKind = iota
	socialIgnore
)

// socialEdit resolves the target coach by name and adds/removes the edge, then
// replies with the appropriate S2C acknowledgement.
func (s *Session) socialEdit(f *protocol.C2SFrame, kind socialKind, add bool) error {
	if s.Coach == nil {
		return nil
	}
	name, err := readName(f)
	if err != nil {
		return err
	}
	target, err := s.deps.Store.Coaches.GetByName(name)
	if errors.Is(err, store.ErrNotFound) {
		return s.sendUserNotFound(name)
	}
	if err != nil {
		return err
	}
	if target.ID == s.Coach.ID {
		return nil // can't befriend/ignore self
	}

	db := s.deps.Store.DB()
	switch {
	case kind == socialFriend && add:
		edge := domain.CoachFriend{OwnerID: s.Coach.ID, FriendID: target.ID, Notify: true}
		db.Where("owner_id = ? AND friend_id = ?", s.Coach.ID, target.ID).
			FirstOrCreate(&edge)
		return s.sendSocialAck(protocol.OpFriendAdded, target)
	case kind == socialFriend && !add:
		db.Where("owner_id = ? AND friend_id = ?", s.Coach.ID, target.ID).
			Delete(&domain.CoachFriend{})
		return s.sendSocialAck(protocol.OpFriendRemoved, target)
	case kind == socialIgnore && add:
		edge := domain.CoachIgnored{OwnerID: s.Coach.ID, IgnoredID: target.ID}
		db.Where("owner_id = ? AND ignored_id = ?", s.Coach.ID, target.ID).
			FirstOrCreate(&edge)
		return s.sendSocialAck(protocol.OpIgnoreAdded, target)
	default: // ignore remove
		db.Where("owner_id = ? AND ignored_id = ?", s.Coach.ID, target.ID).
			Delete(&domain.CoachIgnored{})
		return s.sendSocialAck(protocol.OpIgnoreRemoved, target)
	}
}

// sendSocialAck replies with the S2C acknowledgement for a social edit. Each
// of the four acks has a DIFFERENT wire layout (verified against the 2.70
// client decoders kz_1/ft_0/adw_1/ahm_0):
//
//	3156 FriendAdded  (kz_1):  [u8 name][u8 note][i64 id][i16][i8 sex][i16]
//	3158 IgnoreAdded  (ft_0):  [u8 name][u8 note]
//	3160 FriendRemoved(adw_1): [u8 name]
//	3162 IgnoreRemoved(ahm_0): [u8 name]
//
// The second string ("note") is a secondary text field (guild/status alias);
// we send it empty. The trailing i16/i8/i16 on FriendAdded carry last-seen /
// flags / sex; we send zero + the coach sex.
func (s *Session) sendSocialAck(opcode uint16, target *domain.Coach) error {
	w := protocol.NewWriter().StringU8(target.Name)
	switch opcode {
	case protocol.OpFriendAdded:
		w.StringU8(""). // note
				I64(int64(target.ID)). // id
				U16(0).                // last-seen / flags
				U8(target.Sex).        // sex
				U16(0)                 // trailing
	case protocol.OpIgnoreAdded:
		w.StringU8("") // note
	case protocol.OpFriendRemoved, protocol.OpIgnoreRemoved:
		// name only
	}
	frame, err := protocol.EncodeS2C(opcode, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// sendUserNotFound replies with UserNotFound (3204): [u8 len][name].
func (s *Session) sendUserNotFound(name string) error {
	w := protocol.NewWriter().StringU8(name)
	frame, err := protocol.EncodeS2C(protocol.OpUserNotFound, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// ---------------------------------------------------------------------------
// Presence notifications (friend/ignore online/offline)
// ---------------------------------------------------------------------------

// buildFriendOnline builds NotificationFriendOnline (3148). Layout (verified vs
// client dh_0): [u8 name][u8 s2][u8 s3][i64 id][i16][i8 sex][i64]. The two extra
// strings are secondary/guild text (empty here); the i16/i64 are status/last-seen.
func buildFriendOnline(c *domain.Coach) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(c.Name).
		StringU8(""). // s2 (guild/alias)
		StringU8(""). // s3
		I64(int64(c.ID)).
		U16(0).    // status
		U8(c.Sex). // sex
		I64(0)     // last-seen
	return protocol.EncodeS2C(protocol.OpFriendOnline, w.Bytes())
}

// buildFriendOffline builds NotificationFriendOffline (3150): [u8 name][u8 s2]
// (verified vs client pv_0).
func buildFriendOffline(c *domain.Coach) ([]byte, error) {
	w := protocol.NewWriter().StringU8(c.Name).StringU8("")
	return protocol.EncodeS2C(protocol.OpFriendOffline, w.Bytes())
}

// buildIgnoreOnline builds NotificationIgnoreOnline (3164): [u8 name][i64 id]
// (verified vs client jH).
func buildIgnoreOnline(c *domain.Coach) ([]byte, error) {
	w := protocol.NewWriter().StringU8(c.Name).I64(int64(c.ID))
	return protocol.EncodeS2C(protocol.OpIgnoreOnline, w.Bytes())
}

// buildIgnoreOffline builds NotificationIgnoreOffline (3166): [u8 name]
// (verified vs client jf_0).
func buildIgnoreOffline(c *domain.Coach) ([]byte, error) {
	w := protocol.NewWriter().StringU8(c.Name)
	return protocol.EncodeS2C(protocol.OpIgnoreOffline, w.Bytes())
}

// notifyPresence pushes online/offline notifications to every coach who watches
// this coach as a friend (with notify) or as an ignore. Watchers who are not
// currently online are skipped. Errors are logged, not propagated — a presence
// push must never break login/logout.
func (s *Session) notifyPresence(coach *domain.Coach, online bool) {
	repo := s.deps.Store.Coaches

	friendBuild := buildFriendOnline
	ignoreBuild := buildIgnoreOnline
	if !online {
		friendBuild = buildFriendOffline
		ignoreBuild = buildIgnoreOffline
	}

	if friends, err := repo.WatchersAsFriend(coach.ID); err == nil {
		if frame, err := friendBuild(coach); err == nil {
			s.pushToWatchers(friends, frame)
		}
	} else {
		s.log.Warn("presence: friend watchers", "err", err)
	}

	if ignorers, err := repo.WatchersAsIgnore(coach.ID); err == nil {
		if frame, err := ignoreBuild(coach); err == nil {
			s.pushToWatchers(ignorers, frame)
		}
	} else {
		s.log.Warn("presence: ignore watchers", "err", err)
	}
}

// pushToWatchers sends a frame to each currently-online watcher id.
func (s *Session) pushToWatchers(watcherIDs []uint, frame []byte) {
	for _, id := range watcherIDs {
		if o := s.deps.World.Get(id); o != nil && o.Session != nil {
			_ = o.Session.Send(frame)
		}
	}
}

// silence unused import when gorm helpers change.
var _ = gorm.ErrRecordNotFound
