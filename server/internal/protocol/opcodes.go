// Package protocol implements the DofusArena binary wire protocol: frame
// parsing, opcode constants, and typed readers/writers for packet payloads.
//
// See go-server/docs/02-protocol.md for the full specification this file
// implements. Opcode values and byte layouts must stay in sync with that
// document and with the decompiled client under client/com/ankamagames/**.
package protocol

// RecvOpcode identifies a client -> server message type.
type RecvOpcode uint16

// SendOpcode identifies a server -> client message type.
type SendOpcode uint16

// Recv opcodes (client -> server), see docs/02-protocol.md §2.3.2.
const (
	RecvDisconnect                         RecvOpcode = 1
	RecvVersion                            RecvOpcode = 7
	RecvAuthentication                     RecvOpcode = 1025
	RecvCoachCreation                      RecvOpcode = 2049
	RecvOpponentSearchRequest              RecvOpcode = 2301
	RecvOpponentSearchCancel               RecvOpcode = 2303
	RecvAddFriendMessage                   RecvOpcode = 3129
	RecvAddIgnoreMessage                   RecvOpcode = 3131
	RecvRemoveFriendMessage                RecvOpcode = 3133
	RecvRemoveIgnoreMessage                RecvOpcode = 3135
	RecvVicinityMessage                    RecvOpcode = 3153
	RecvPrivateMessage                     RecvOpcode = 3155
	RecvFightInvitationRequestMessage      RecvOpcode = 4301
	RecvFightInvitationAcceptMessage       RecvOpcode = 4305
	RecvFightInvitationRejectMessage       RecvOpcode = 4307
	RecvSetReadyForFight                   RecvOpcode = 4303
	RecvFightCreationCancelMessage         RecvOpcode = 4311
	RecvActorMovementRequest               RecvOpcode = 4501
	RecvItemExchangeInvitationRequest      RecvOpcode = 5101
	RecvItemExchangeInvitationAnswer       RecvOpcode = 5103
	RecvItemExchangeAddCard                RecvOpcode = 5105
	RecvItemExchangeRemoveCard             RecvOpcode = 5106
	RecvItemExchangeSetReady               RecvOpcode = 5107
	RecvItemExchangeCancel                 RecvOpcode = 5108
	RecvCoachEquipmentUpdateRequest        RecvOpcode = 5201
	RecvCoachInventoryUpdateRequest        RecvOpcode = 5203
	RecvFighterCreateRequest               RecvOpcode = 6001
	RecvFighterDeleteRequest               RecvOpcode = 6003
	RecvFighterInformationListRequest      RecvOpcode = 6005
	RecvFighterUpdateInventoryRequest      RecvOpcode = 6011
	RecvTeamPresetSaveRequest              RecvOpcode = 6021
	RecvTeamPresetDeleteRequest            RecvOpcode = 6023
	RecvTeamPresetListRequest              RecvOpcode = 6031
	RecvTeamMateSetReadyForPlacement       RecvOpcode = 8011
	RecvMoveToFreePlacementRequest         RecvOpcode = 8021
	RecvTeamMateSetReadyForObservation     RecvOpcode = 8023
	RecvTeamMateSetReadyForAction          RecvOpcode = 8031
	RecvFighterEndTurnRequest              RecvOpcode = 8105
	RecvFighterCardUseRequest              RecvOpcode = 8107
	RecvSpellCastRequest                   RecvOpcode = 8109
	RecvCloseCombatRequest                 RecvOpcode = 8111
	RecvGiveUpFightRequest                 RecvOpcode = 8151
	RecvFighterActorMovementRequest        RecvOpcode = 4503
	RecvFighterActorDirectionChangeRequest RecvOpcode = 4521
	RecvEndFightDone                       RecvOpcode = 4321
	// RecvConsoleAdminCommand carries a free-text admin console command
	// string. Despite the legacy OpCode.java listing 8193 in the Send enum,
	// the client actually *sends* it over the regular game socket (see
	// client ConsoleAdminCommandMessage.java, an OutputOnlyProxyMessage),
	// so it is a client->server opcode here. See handlers_admin_console.go.
	RecvConsoleAdminCommand RecvOpcode = 8193
)

// Send opcodes (server -> client), see docs/02-protocol.md §2.3.1.
const (
	SendReconnectionTicket                    SendOpcode = 2
	SendReconnectionTicketRequestResult       SendOpcode = 4
	SendInvalidVersion                        SendOpcode = 8
	SendAuthenticationResult                  SendOpcode = 1024
	SendWorldServerUnavailable                SendOpcode = 1026
	SendCoachCreationRequest                  SendOpcode = 2048
	SendCoachCreationResult                   SendOpcode = 2050
	SendCoachInformation                      SendOpcode = 2052
	SendOpponentFound                         SendOpcode = 2300
	SendOpponentSearchError                   SendOpcode = 2302
	SendOpponentSearchInProgress              SendOpcode = 2304
	SendOpponentSearchCancelResult            SendOpcode = 2306
	SendPlayerStatisticsReport                SendOpcode = 2400
	SendFriendListMessage                     SendOpcode = 3144
	SendIgnoreListMessage                     SendOpcode = 3146
	SendVicinityMessage                       SendOpcode = 3152
	SendPrivateMessage                        SendOpcode = 3154
	SendFriendAddedMessage                    SendOpcode = 3156
	SendIgnoreAddedMessage                    SendOpcode = 3158
	SendFriendRemovedMessage                  SendOpcode = 3160
	SendIgnoreRemovedMessage                  SendOpcode = 3162
	SendUserNotFound                          SendOpcode = 3204
	SendMemberNotFound                        SendOpcode = 3208
	SendActorSpawn                            SendOpcode = 4096
	SendActorDespawn                          SendOpcode = 4098
	SendActorAppear                           SendOpcode = 4102
	SendActorDisapear                         SendOpcode = 4104
	SendActorReposition                       SendOpcode = 4106
	SendFightInvitation                       SendOpcode = 4300
	SendFightInvitationAccepted               SendOpcode = 4302
	SendFightInvitationRejected               SendOpcode = 4304
	SendFightInvitationError                  SendOpcode = 4309
	SendFightCreationCanceledMessage          SendOpcode = 4310
	SendReadyForFight                         SendOpcode = 4306
	SendActorMovement                         SendOpcode = 4500
	SendActorTeleport                         SendOpcode = 4510
	SendEnterWorldInstance                    SendOpcode = 4600
	SendNoInstanceServerAvailableMessage      SendOpcode = 5000
	SendItemExchangeInvitationRequest         SendOpcode = 5102
	SendItemExchangeInvitationConfirmation    SendOpcode = 5104
	SendItemExchangeCardAdded                 SendOpcode = 5109
	SendItemExchangeCardRemoved               SendOpcode = 5110
	SendItemExchangeEnd                       SendOpcode = 5111
	SendItemExchangeUserReady                 SendOpcode = 5112
	SendCoachInventoryUpdateMessage           SendOpcode = 5200
	SendCoachEquipmentUpdateMessage           SendOpcode = 5202
	SendFighterCreateResult                   SendOpcode = 6000
	SendFighterDeletionResult                 SendOpcode = 6002
	SendFighterInformationList                SendOpcode = 6006
	SendFighterUpdatedInformationInventory    SendOpcode = 6010
	SendTeamPresetSave                        SendOpcode = 6020
	SendTeamPresetDeletion                    SendOpcode = 6022
	SendTeamPresetList                        SendOpcode = 6030
	SendCreateFight                           SendOpcode = 8000
	SendStartPresentation                     SendOpcode = 8010
	SendTeamMateSetReadyForPlacementMessage   SendOpcode = 8012
	SendEndPresentation                       SendOpcode = 8018
	SendStartPlacement                        SendOpcode = 8020
	SendMoveToFreePlacement                   SendOpcode = 8022
	SendTeamMateSetReadyForObservationMessage SendOpcode = 8024
	SendEndPlacement                          SendOpcode = 8028
	SendStartObservation                      SendOpcode = 8030
	SendTeamMateSetReadyForActionMessage      SendOpcode = 8032
	SendEndObservation                        SendOpcode = 8038
	SendStartAction                           SendOpcode = 8040
	SendNewTableTurnBegin                     SendOpcode = 8100
	SendFighterTurnBegin                      SendOpcode = 8104
	SendFighterTurnEnd                        SendOpcode = 8106
	SendFighterCardUse                        SendOpcode = 8108
	SendSpellCast                             SendOpcode = 8110
	SendCloseCombat                           SendOpcode = 8112
	SendRunningEffectAction                   SendOpcode = 8120
	SendEffectAreaAction                      SendOpcode = 6200
	SendFightActionSequenceExecute            SendOpcode = 8200
	SendEndFight                              SendOpcode = 8300
	SendFighterTackled                        SendOpcode = 4506
	SendFighterDies                           SendOpcode = 4520
	SendFighterChangeDirection                SendOpcode = 4522
	SendFighterMove                           SendOpcode = 4524
	SendQueueNotification                     SendOpcode = 8192
	// SendConsoleAdminCommandResult (8194) delivers a TRACE/LOG/ERROR text
	// line back to the client's in-game console in response to a
	// CONSOLE_ADMIN_COMMAND. SendDefaultResult (8195) is a generic
	// completion ack carrying a single int result code. Opcode 8193 is
	// inbound only, see RecvConsoleAdminCommand above.
	SendConsoleAdminCommandResult SendOpcode = 8194
	SendDefaultResult             SendOpcode = 8195
)

// AuthResultCode values sent in AUTHENTICATION_RESULT payloads.
type AuthResultCode byte

const (
	AuthOK               AuthResultCode = 0
	AuthInvalidLogin     AuthResultCode = 2
	AuthAlreadyConnected AuthResultCode = 3
	AuthSaveInProgress   AuthResultCode = 4
	AuthClosedBeta       AuthResultCode = 127
)

// CoachCreationResultCode values sent in COACH_CREATION_RESULT payloads.
type CoachCreationResultCode byte

const (
	CoachCreationOK           CoachCreationResultCode = 0
	CoachCreationInvalidNameA CoachCreationResultCode = 11
	CoachCreationInvalidNameB CoachCreationResultCode = 12
	CoachCreationUndefinedA   CoachCreationResultCode = 10
	CoachCreationUndefinedB   CoachCreationResultCode = 13
)

// AdminResultType is the leading byte of a CONSOLE_ADMIN_COMMAND_RESULT
// payload. It tells the client's console how to render the message line,
// mirroring the client ConsoleAdminCommandResultMessage constants and its
// ConsoleManager.trace/log/err dispatch.
type AdminResultType byte

const (
	AdminResultTrace AdminResultType = 0
	AdminResultLog   AdminResultType = 1
	AdminResultError AdminResultType = 2
)

// OpponentSearchErrorCode is the single-byte payload of an
// OPPONENT_SEARCH_ERROR (2302) message. The legacy client
// (OpponentSearchErrorMessage.java:22-42) only reads rawDatas[0] into an
// unused field and never branches on the value, and neither the legacy Java
// server nor any decompiled source defines an error-code enum for it (the
// opcode was dead code on both sides). These values are therefore a Go-only
// convention, introduced to replace the previously-silent request-validation
// drops in handleOpponentSearchRequest with an explicit client signal. They
// exist for server-side clarity and diagnostics; the stock client treats any
// non-zero code identically. Value 0 is reserved as "no error" to match the
// client's neutral read.
type OpponentSearchErrorCode byte

const (
	// OpponentSearchErrBadRequest: the OPPONENT_SEARCH_REQUEST payload was
	// malformed / truncated and could not be decoded.
	OpponentSearchErrBadRequest OpponentSearchErrorCode = 1
	// OpponentSearchErrNoCoach: the session has no coach bound (not far
	// enough through the login/coach-selection flow to matchmake).
	OpponentSearchErrNoCoach OpponentSearchErrorCode = 2
	// OpponentSearchErrInvalidParams: the fightType or bet is out of range.
	// The only fightType the stock client ever sends over the 2301
	// matchmaking path is 1 (random/defy, see UIRandomFightCreationFrame.java
	// and UIChatFrame.java); type 4 belongs to the invitation path (4301),
	// not matchmaking. A negative bet is nonsensical (bet is a wager amount,
	// 0 = no bet).
	OpponentSearchErrInvalidParams OpponentSearchErrorCode = 3
)

// FightTypeMatchmakingDefy is the only fightType the stock client sends over
// the OPPONENT_SEARCH_REQUEST (2301) matchmaking path. fightType is otherwise
// an opaque byte server-side (no FightDefinition table exists in the Go
// gamedata store), so this allowlist is the pragmatic validation boundary.
const FightTypeMatchmakingDefy byte = 1
