package dispatch

import (
	"context"
	"errors"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterSocialHandlers wires the friend/ignore list opcodes, see
// docs/02-protocol.md ADD_FRIEND_MESSAGE/ADD_IGNORE_MESSAGE/REMOVE_*.
func RegisterSocialHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvAddFriendMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleSocialEdit(session, payload, deps, true, true)
	})
	r.Register(protocol.RecvRemoveFriendMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleSocialEdit(session, payload, deps, true, false)
	})
	r.Register(protocol.RecvAddIgnoreMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleSocialEdit(session, payload, deps, false, true)
	})
	r.Register(protocol.RecvRemoveIgnoreMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleSocialEdit(session, payload, deps, false, false)
	})
}

// handleSocialEdit handles all four friend/ignore add/remove opcodes, which
// share an identical wire shape (1-byte-length-prefixed target name) and
// response shape (name + target coach ID), differing only in which
// SocialService method and response opcode apply.
func handleSocialEdit(session *netio.Session, payload *protocol.Reader, deps *Deps, isFriend, isAdd bool) {
	targetName := payload.String()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	ctx := context.Background()
	var target domain.Coach
	err := deps.Coach.DB().WithContext(ctx).Where("name = ?", targetName).First(&target).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		sendUserNotFound(session, targetName)
		return
	}
	if err != nil {
		deps.Logger.Error().Err(err).Str("name", targetName).Msg("dispatch: social lookup error")
		return
	}

	var changed bool
	var opErr error
	switch {
	case isFriend && isAdd:
		changed, opErr = deps.Social.AddFriend(ctx, coach.ID, target.ID)
	case isFriend && !isAdd:
		opErr = deps.Social.RemoveFriend(ctx, coach.ID, target.ID)
		changed = opErr == nil
	case !isFriend && isAdd:
		changed, opErr = deps.Social.AddIgnored(ctx, coach.ID, target.ID)
	default:
		opErr = deps.Social.RemoveIgnored(ctx, coach.ID, target.ID)
		changed = opErr == nil
	}
	if opErr != nil {
		deps.Logger.Error().Err(opErr).Msg("dispatch: social edit error")
		return
	}
	if !changed {
		return
	}

	opcode := resultOpcode(isFriend, isAdd)
	w := protocol.NewWriter(9 + len(target.Name))
	w.PutString(target.Name).PutInt64(int64(target.ID))
	session.Send(protocol.OutboundFrame{Opcode: opcode, Payload: w.Bytes()})
}

func resultOpcode(isFriend, isAdd bool) protocol.SendOpcode {
	switch {
	case isFriend && isAdd:
		return protocol.SendFriendAddedMessage
	case isFriend && !isAdd:
		return protocol.SendFriendRemovedMessage
	case !isFriend && isAdd:
		return protocol.SendIgnoreAddedMessage
	default:
		return protocol.SendIgnoreRemovedMessage
	}
}

func sendUserNotFound(session *netio.Session, name string) {
	w := protocol.NewWriter(1 + len(name))
	w.PutString(name)
	session.Send(protocol.OutboundFrame{Opcode: protocol.SendUserNotFound, Payload: w.Bytes()})
}
