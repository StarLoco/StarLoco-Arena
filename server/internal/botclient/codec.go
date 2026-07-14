package botclient

import (
	"github.com/dofusarena/go-server/internal/protocol"
)

// This file collects small, reusable payload builders/parsers for the
// opcodes bot behaviors send and interpret. They wrap protocol.Writer/
// Reader so higher layers (swarm behaviors, fight AI) express intent in
// terms of game concepts (walk to a cell, cast a spell) instead of raw
// byte layouts. Layouts are cited to the server handlers that decode them.

// Cell is an overworld or fight-map coordinate. Z is the altitude/level
// short that the movement/placement/cast payloads all carry.
type Cell struct {
	X int32
	Y int32
	Z int16
}

// --- Overworld ---

// Walk sends ACTOR_MOVEMENT_REQUEST (4501): a path of N (int32 x, int32 y,
// int16 z) waypoints with no count prefix -- the server infers the count
// from the frame length (handlers_fight.go handleActorMovementRequest). The
// last cell becomes the coach's new position and the move is broadcast to
// all coaches as ACTOR_MOVEMENT (4500).
func (c *Client) Walk(path ...Cell) error {
	w := protocol.NewWriter(len(path) * 10)
	for _, p := range path {
		w.PutInt32(p.X).PutInt32(p.Y).PutInt16(p.Z)
	}
	return c.Send(1, protocol.RecvActorMovementRequest, w.Bytes())
}

// SayVicinity sends VICINITY_MESSAGE (3153): a single 2-byte length-prefixed
// UTF-8 string (handlers_chat.go handleVicinityMessage). Broadcast to every
// other coach as VICINITY_MESSAGE (3152). Messages starting with '/' are
// treated as GM commands and swallowed, so callers should avoid a leading
// slash for ordinary chat.
func (c *Client) SayVicinity(msg string) error {
	w := protocol.NewWriter(len(msg) + 2)
	w.PutStringShort(msg)
	return c.Send(3, protocol.RecvVicinityMessage, w.Bytes())
}

// Whisper sends PRIVATE_MESSAGE (3155): pstring(targetName) + pstring(msg).
func (c *Client) Whisper(targetName, msg string) error {
	w := protocol.NewWriter(0)
	w.PutString(targetName)
	w.PutString(msg)
	return c.Send(3, protocol.RecvPrivateMessage, w.Bytes())
}

// --- Matchmaking ---

// SearchOpponent sends OPPONENT_SEARCH_REQUEST (2301): byte fightType +
// int32 bet. Two bots that send the SAME (fightType, bet) tuple are paired
// with each other (internal/world/matchmaking.go), so a swarm uses a unique
// bet per intended pairing to make matches deterministic at high
// concurrency.
func (c *Client) SearchOpponent(fightType byte, bet int32) error {
	w := protocol.NewWriter(5)
	w.PutByte(fightType)
	w.PutInt32(bet)
	return c.Send(2, protocol.RecvOpponentSearchRequest, w.Bytes())
}

// SetReadyForFight sends SET_READY_FOR_FIGHT (4303): int64 duelID + byte
// ready(=1) + int64 fighterID (the fighter this coach will field).
func (c *Client) SetReadyForFight(duelID int64, fighterID int64) error {
	w := protocol.NewWriter(17)
	w.PutInt64(duelID).PutByte(1).PutInt64(fighterID)
	return c.Send(2, protocol.RecvSetReadyForFight, w.Bytes())
}

// AcceptFightInvitation sends FIGHT_INVITATION_ACCEPT (4305): int64
// invitationID. Used by a bot to accept a right-click challenge from a real
// player; the server then creates the duel and the normal SET_READY_FOR_FIGHT
// -> CREATE_FIGHT flow follows.
func (c *Client) AcceptFightInvitation(invitationID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(invitationID)
	return c.Send(2, protocol.RecvFightInvitationAcceptMessage, w.Bytes())
}

// RejectFightInvitation sends FIGHT_INVITATION_REJECT (4307): int64
// invitationID.
func (c *Client) RejectFightInvitation(invitationID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(invitationID)
	return c.Send(2, protocol.RecvFightInvitationRejectMessage, w.Bytes())
}

// --- Fight phase ready-gates (all empty payloads) ---

func (c *Client) ReadyForPlacement() error {
	return c.Send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)
}
func (c *Client) ReadyForObservation() error {
	return c.Send(3, protocol.RecvTeamMateSetReadyForObservation, nil)
}
func (c *Client) ReadyForAction() error {
	return c.Send(3, protocol.RecvTeamMateSetReadyForAction, nil)
}

// --- Card exchange ---

// ExchangeInvite sends ITEM_EXCHANGE_INVITATION_REQUEST (5101): int64
// targetCoachId. The server replies with a confirmation to us and an
// invitation to the target.
func (c *Client) ExchangeInvite(targetCoachID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(targetCoachID)
	return c.Send(3, protocol.RecvItemExchangeInvitationRequest, w.Bytes())
}

// ExchangeAnswer sends ITEM_EXCHANGE_INVITATION_ANSWER (5103): int64
// exchangeId (ignored by the server, which looks up by coach) + byte accepted
// (0 = yes, 1 = no).
func (c *Client) ExchangeAnswer(exchangeID int64, accept bool) error {
	w := protocol.NewWriter(9)
	w.PutInt64(exchangeID)
	if accept {
		w.PutByte(0)
	} else {
		w.PutByte(1)
	}
	return c.Send(3, protocol.RecvItemExchangeInvitationAnswer, w.Bytes())
}

// ExchangeAddCard sends ITEM_EXCHANGE_ADD_CARD (5105): int64 exchangeId
// (ignored) + int64 cardUid + int16 quantity.
func (c *Client) ExchangeAddCard(exchangeID, cardUID int64, quantity int16) error {
	w := protocol.NewWriter(18)
	w.PutInt64(exchangeID).PutInt64(cardUID).PutInt16(quantity)
	return c.Send(3, protocol.RecvItemExchangeAddCard, w.Bytes())
}

// ExchangeSetReady sends ITEM_EXCHANGE_SET_READY (5107): int64 exchangeId
// (ignored). When both parties are ready the trade completes.
func (c *Client) ExchangeSetReady(exchangeID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(exchangeID)
	return c.Send(3, protocol.RecvItemExchangeSetReady, w.Bytes())
}

// ExchangeCancel sends ITEM_EXCHANGE_CANCEL (5108): int64 exchangeId
// (ignored).
func (c *Client) ExchangeCancel(exchangeID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(exchangeID)
	return c.Send(3, protocol.RecvItemExchangeCancel, w.Bytes())
}

// --- In-fight actions ---

// PlaceFighter sends MOVE_TO_FREE_PLACEMENT_REQUEST (8021): int64 fighterID
// + int32 x + int32 y + int16 z. Optional -- fighters already have valid
// start cells assigned server-side.
func (c *Client) PlaceFighter(fighterID int64, cell Cell) error {
	w := protocol.NewWriter(18)
	w.PutInt64(fighterID).PutInt32(cell.X).PutInt32(cell.Y).PutInt16(cell.Z)
	return c.Send(3, protocol.RecvMoveToFreePlacementRequest, w.Bytes())
}

// MoveFighter sends FIGHTER_ACTOR_MOVEMENT_REQUEST (4503): int64 fighterID
// followed by a path of (int32 x, int32 y, int16 z) cells EXCLUDING the
// fighter's current cell (the server prepends it for the echo).
func (c *Client) MoveFighter(fighterID int64, path ...Cell) error {
	w := protocol.NewWriter(8 + len(path)*10)
	w.PutInt64(fighterID)
	for _, p := range path {
		w.PutInt32(p.X).PutInt32(p.Y).PutInt16(p.Z)
	}
	return c.Send(3, protocol.RecvFighterActorMovementRequest, w.Bytes())
}

// CastSpell sends SPELL_CAST_REQUEST (8109): int64 fighterID + int32 spellId
// + int32 x + int32 y + int16 z.
func (c *Client) CastSpell(fighterID int64, spellID int32, target Cell) error {
	w := protocol.NewWriter(22)
	w.PutInt64(fighterID).PutInt32(spellID).PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	return c.Send(3, protocol.RecvSpellCastRequest, w.Bytes())
}

// CloseCombat sends CLOSE_COMBAT_REQUEST (8111): int64 fighterID + int32 x +
// int32 y + int16 z. Requires an adjacent (manhattan distance 1) enemy.
func (c *Client) CloseCombat(fighterID int64, target Cell) error {
	w := protocol.NewWriter(18)
	w.PutInt64(fighterID).PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	return c.Send(3, protocol.RecvCloseCombatRequest, w.Bytes())
}

// UseCard sends FIGHTER_CARD_USE_REQUEST (8107): int64 fighterID + int32
// cardId + int32 x + int32 y + int16 z. Used to trigger summon cards, etc.
func (c *Client) UseCard(fighterID int64, cardID int32, target Cell) error {
	w := protocol.NewWriter(22)
	w.PutInt64(fighterID).PutInt32(cardID).PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	return c.Send(3, protocol.RecvFighterCardUseRequest, w.Bytes())
}

// EndTurn sends FIGHTER_END_TURN_REQUEST (8105): int64 fighterID.
func (c *Client) EndTurn(fighterID int64) error {
	w := protocol.NewWriter(8)
	w.PutInt64(fighterID)
	return c.Send(3, protocol.RecvFighterEndTurnRequest, w.Bytes())
}

// GiveUp sends GIVE_UP_FIGHT_REQUEST (8151), empty payload.
func (c *Client) GiveUp() error {
	return c.Send(3, protocol.RecvGiveUpFightRequest, nil)
}

// EndFightDone sends END_FIGHT_DONE (4321), empty payload. Both coaches must
// ack before the fight actor releases them back to the overworld.
func (c *Client) EndFightDone() error {
	return c.Send(3, protocol.RecvEndFightDone, nil)
}
