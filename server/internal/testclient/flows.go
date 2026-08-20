package testclient

import (
	"fmt"
	"time"
)

// Opcodes the test client uses (subset, mirroring the real client).
const (
	OpClientVersion        = 7
	OpInvalidClientVersion = 8 // S2C [u8 major][u16 minor] = expected version
	OpPing                 = 107
	OpAuth                 = 1025
	OpAuthResult           = 1024
	OpCoachCreateReq       = 2048
	OpCoachCreate          = 2049
	OpCoachInfo            = 2052
	OpEnterInstance        = 4600

	OpSearch             = 2301
	OpSearchCancel       = 2303 // C2S: empty
	OpSearchInProgress   = 2304 // S2C: empty
	OpSearchCancelResult = 2306 // S2C: [i8 result]
	OpMatchFound         = 23110
	OpMatchAccept        = 23114
	OpCreateFight        = 8000

	OpReadyForPlacement   = 8011
	OpStartPlacement      = 8020
	OpReadyForObservation = 8023
	OpStartObservation    = 8030
	OpReadyForAction      = 8031
	OpStartAction         = 8040
	OpFighterTurnBegin    = 8104
	OpSpellCastReq        = 8109 // mc_2 — the REAL spell cast (8107 is card use, B-047)
	OpFighterCardUseReq   = 8107 // sg_2 — in-fight action-card play
	OpFighterCardUse      = 8108 // arn_0 — its broadcast
	OpEndTurnReq          = 8105
	OpEndFight            = 8300
	OpEndFightDone        = 26321
	OpGiveUp              = 8151

	// OpTeamTest doubles as the "Tester" practice launch ([i32 fightType][i16
	// teamId]) and the overworld CHALLENGE launch ([i32 challengeId][i16 99]).
	// Both are sent with arch 2. OpFightCreationError is the refusal.
	OpTeamTest           = 26330
	OpFightCreationError = 26310

	OpFighterCreate           = 6001
	OpFighterCreateResult     = 6000
	OpFighterList             = 6006
	OpUpdateFighterInventory  = 6011 // C2S
	OpUpdatedFighterInventory = 6010 // S2C

	// Guilds ("clans"). Strings on this family are UTF-8, not the cp1252 the
	// coach/fighter names use.
	OpGuildCreate       = 509 // C2S (arch 3)
	OpGuildInvite       = 501
	OpGuildInviteAnswer = 503
	OpGuildGet          = 517
	OpGuildMembersGet   = 519
	OpGuildInvitation   = 502
	OpGuildResult       = 504
	OpGuildRecord       = 510
	OpGuildMembers      = 512
	OpGuildMembership   = 552
	OpGuildCreatedFeed  = 558
	OpGuildMemberFeed   = 560
	OpUserClanContent   = 3199 // C2S
	OpClanContent       = 3198 // S2C

	// Exchange
	// These match the retail client's own numbering, which is NOT contiguous:
	// see internal/protocol/opcodes.go. The test client previously used the
	// 2006 layout, which made the exchange e2e tests agree with a server that
	// the real client could not talk to.
	OpExchangeInvite      = 5101
	OpExchangeInvitationS = 5102
	OpExchangeAnswer      = 5103
	OpExchangeConfirm     = 5104
	OpExchangeAddCard     = 5105
	OpExchangeRemoveCard  = 5107
	OpExchangeSetReady    = 5109
	OpExchangeCardAdded   = 5110
	OpExchangeCancel      = 5111
	OpExchangeCardRemoved = 5112
	OpExchangeError       = 5113
	OpExchangeEnd         = 5114
	OpExchangeUserReady   = 5116

	// Social & chat
	OpUserPrivateContent = 3155 // C2S whisper
	OpPrivateContent     = 3154 // S2C private msg
	OpUserChannelContent = 3151 // C2S channel send
	OpChannelContent     = 3140 // S2C channel recv
	OpUserNotFound       = 3204
	OpAddFriend          = 3129
	OpRemoveFriend       = 3133
	OpAddIgnore          = 3131
	OpRemoveIgnore       = 3135
	OpFriendAdded        = 3156
	OpFriendRemoved      = 3160
	OpIgnoreAdded        = 3158
	OpIgnoreRemoved      = 3162
	OpFriendOnline       = 3148
	OpFriendOffline      = 3150
	OpIgnoreOnline       = 3164
	OpIgnoreOffline      = 3166

	// Inventory & equipment
	OpCoachInventoryUpdate        = 5200 // S2C
	OpCoachEquipmentUpdateRequest = 5201 // C2S 14×i32 slot refs
	OpCoachInventoryUpdateRequest = 5203 // C2S

	// Overworld interactive elements (Zaaps, Card Masters, Fusion altars, ...)
	OpInteractiveElementSpawn  = 200 // S2C [i16 count]{[i64 id][i16 len][blob]}
	OpInteractiveElementAction = 201 // C2S [i64 instanceId][i16 actionOrdinal] — element click

	// Mailbox
	OpMailListRequest = 15000 // C2S empty
	OpMailList        = 15001 // S2C [i16 count]{mail record}
	OpMailSendResult  = 15003 // S2C [i64 result][mail record]
	OpMailDelete      = 15004 // C2S [u8 n]{i64 mailId}
	OpMailTakeCards   = 15006 // C2S [i64 mailId](+pad)
	OpMailCardsTaken  = 15007 // S2C [i64 mailId][i64 coachId][u8 n]{i32 cardId}
	OpMailSend        = 539   // C2S mail record
	OpMailCheckName   = 15506 // C2S [u8 len][name]
	OpMailNameResult  = 15507 // S2C [i64 coachId]

	// Shop / economy
	OpWalletUpdate = 4001 // S2C full wallet
	// OpShopOpen (5300) is the client's DEBUG-CONSOLE opener, not a shop request;
	// the in-world shop is opened by clicking a Card Master (201 -> 5401).
	OpShopOpen      = 5300
	OpShopCatalog   = 5401 // S2C catalog
	OpShopBuy       = 5450 // C2S
	OpShopResult    = 5403 // S2C
	OpShopBarter    = 5400 // C2S card-for-card
	OpFusionRequest = 5490 // C2S fuse cards
	OpFusionResult  = 5491 // S2C fusion outcome

	// Ladder — four boards, one opcode pair each (all C2S arch 2)
	OpLadderRequest          = 27500 // C2S [i32 start] 1v1
	OpLadderResponse         = 27501 // S2C 1v1 window
	OpGuildLadderRequest     = 27502 // C2S [i16 board][i32 start] guild
	OpGuildLadder            = 27503 // S2C guild window
	OpLadder2v2Request       = 27504 // C2S [i32 start] 2v2
	OpLadder2v2Response      = 27505 // S2C 2v2 window
	OpTournamentLadderReq    = 27506 // C2S "Tournoi" tab: [i32 mStart][i32 tStart][i32 yStart][i8 m][i8 t][i16 y]
	OpTournamentLadder       = 27507 // S2C tournament-points board (3 windows)
	OpProLeagueLadderReq     = 27514 // C2S "Ligue Pro" tab: [i32 start][i32 leagueId][i32 pageSize]
	OpProLeagueLadder        = 27515 // S2C pro-league window
	OpCoachReputationRequest = 27508 // C2S [i32 start] coach reputation
	OpCoachReputation        = 27509 // S2C coach-reputation window
	OpDemonListRequest       = 27512 // C2S [i16 flag][i32 start] demon list
	OpDemonList              = 27513 // S2C demon list (24 demons)

	// Tournaments (totem / calendar). Calendar + register are arch 3; list + tree
	// are arch 2.
	OpTournamentCalReq        = 17002 // C2S empty
	OpTournamentCalendar      = 17003 // S2C calendar events
	OpTournamentListReq       = 28601 // C2S empty
	OpTournamentList          = 28602 // S2C registerable tournaments
	OpTournamentRegister      = 4607  // C2S [i64 tid][i64 coachId][i16 preset][i32 card]
	OpTournamentRegisterReply = 28608 // S2C [i64 tid][i8 err]
	OpTournamentTreeReq       = 28649 // C2S [i64 tid][i32 round][i32 nameLen][name]
	OpTournamentTree          = 28650 // S2C empty tree

	// In-fight movement & placement
	OpFighterMoveInFightReq = 4503 // C2S
	OpFighterMoveInFight    = 4524 // S2C
	OpMoveToPlacementReq    = 8021 // C2S
	OpMoveToFreePlacement   = 8022 // S2C

	// Fighters / teams
	OpFighterDelete     = 6003
	OpTeamPresetSave    = 6021
	OpTeamPresetDelete  = 6023
	OpTeamPresetListReq = 6031
	OpTeamPresetList    = 6030
)

const defaultTimeout = 3 * time.Second

// DefaultTimeout is the standard wait used by test flows.
const DefaultTimeout = defaultTimeout

// Login performs the version + auth handshake and returns once AuthResult(0) is
// received. Does NOT consume the coach-creation prompt.
func (c *Client) Login(login, password string) error {
	// ClientVersion: [u8 0x02][u16 70][u8 len][build]
	ver := NewW().U8(0x02).U16(70).Str8("72909").Bytes()
	if err := c.Send(0, OpClientVersion, ver); err != nil {
		return err
	}
	// Auth: [u8 loginLen][login][u8 passLen][pass]
	auth := NewW().Str8(login).Str8(password).Bytes()
	if err := c.Send(1, OpAuth, auth); err != nil {
		return err
	}
	f, _, err := c.WaitFor(OpAuthResult, defaultTimeout)
	if err != nil {
		return err
	}
	if code := NewR(f.Payload).U8(); code != 0 {
		return fmt.Errorf("auth failed, result code %d", code)
	}
	return nil
}

// CreateCoach handles either the creation prompt (2048) or an existing-coach
// login (2052), submitting a coach if prompted. Returns the coach id read from
// CoachInformations(2052).
func (c *Client) CreateCoach(name string) (int64, error) {
	f, seen, err := c.WaitFor2(OpCoachCreateReq, OpCoachInfo, defaultTimeout)
	if err != nil {
		return 0, err
	}
	if f.Opcode == OpCoachCreateReq {
		// Submit a coach: [u8 nameLen][name][u8 hair][u8 skin][u8 sex]
		payload := NewW().Str8(name).U8(1).U8(1).U8(0).Bytes()
		if err := c.Send(2, OpCoachCreate, payload); err != nil {
			return 0, err
		}
		f, _, err = c.WaitFor(OpCoachInfo, defaultTimeout)
		if err != nil {
			return 0, err
		}
	}
	_ = seen
	// CoachInformations starts with [i64 coachId].
	return NewR(f.Payload).I64(), nil
}

// OpPingReply (108, abj_0) is the server->client ping acknowledgement. As the
// client, the testclient SENDS 107 and RECEIVES 108; a 108 needs no response
// (it just credits the keepalive counter), so we consume and ignore it.
const OpPingReply = 108

// AnswerPing handles ping frames during longer waits. Historically the server
// echoed 107; it now correctly replies to the client's 107 with a 108, so a
// 108 arriving here is just consumed. (Kept tolerant of a stray 107 too.)
func (c *Client) AnswerPing(f *Frame) {
	if f.Opcode == OpPing {
		r := NewR(f.Payload)
		flag := r.U8()
		key := r.I32()
		reply := NewW().U8(flag).I32(key).I64(0).I64(0).I64(0).Bytes()
		_ = c.Send(byte(flag), OpPing, reply)
	}
	// OpPingReply (108): keepalive ack, nothing to do.
}

// WaitFor2 waits until one of two opcodes arrives.
func (c *Client) WaitFor2(opA, opB uint16, timeout time.Duration) (*Frame, []*Frame, error) {
	deadline := time.Now().Add(timeout)
	var seen []*Frame
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return nil, seen, err
		}
		if f.Opcode == opA || f.Opcode == opB {
			return f, seen, nil
		}
		c.AnswerPing(f)
		seen = append(seen, f)
	}
	return nil, seen, fmt.Errorf("testclient: timeout waiting for %d/%d", opA, opB)
}
