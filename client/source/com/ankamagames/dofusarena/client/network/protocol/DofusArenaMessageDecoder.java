/*     */ package com.ankamagames.dofusarena.client.network.protocol;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChatUserFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendListMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendRemovedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.PrivateContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.MemberNotFoundMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.OperationNotPermitedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.TargetIsYourselfMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.UserNotFoundMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.CloseCombatMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionSequenceExecute;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterDiesMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.RunningEffectActionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.SpellCastMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorAppearMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorDisapearMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorRepositionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement.CoachInventoryUpdateMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeCardAddedMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeInvitationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndFightMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndObservationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndPlacementMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FightCreationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.StartActionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.StartPresentationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.DeletionTeamPresetMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.CoachCreationResultMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentFoundMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchCancelResultMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchInProgressMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.PlayerStatisticsReportMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.ReadyForFightMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ 
/*     */ public class DofusArenaMessageDecoder extends AbstractClientMessageDecoder {
/*  38 */   protected static Logger m_logger = Logger.getLogger(DofusArenaMessageDecoder.class);
protected Message createMessageFromType(int msgType) { ClientAuthenticationResultsMessage clientAuthenticationResultsMessage; WorldServerUnavailableMessage worldServerUnavailableMessage; CoachCreationRequestMessage coachCreationRequestMessage; CoachCreationResultMessage coachCreationResultMessage; CoachInformationsMessage coachInformationsMessage; OpponentSearchErrorMessage opponentSearchErrorMessage; OpponentFoundMessage opponentFoundMessage; OpponentSearchInProgressMessage opponentSearchInProgressMessage; OpponentSearchCancelResultMessage opponentSearchCancelResultMessage; FightInvitationMessage fightInvitationMessage; FightInvitationAcceptedMessage fightInvitationAcceptedMessage; FightInvitationRejectedMessage fightInvitationRejectedMessage; FightInvitationErrorMessage fightInvitationErrorMessage; FightCreationCanceledMessage fightCreationCanceledMessage; PlayerStatisticsReportMessage playerStatisticsReportMessage; ReadyForFightMessage readyForFightMessage; NoInstanceServerAvailableMessage noInstanceServerAvailableMessage; EnterInstanceMessage enterInstanceMessage; CoachInventoryUpdateMessage coachInventoryUpdateMessage; CoachEquipmentUpdateMessage coachEquipmentUpdateMessage; ActorSpawnMessage actorSpawnMessage; ActorDespawnMessage actorDespawnMessage; ActorAppearMessage actorAppearMessage; ActorDisapearMessage actorDisapearMessage; ActorRepositionMessage actorRepositionMessage; ActorMovementMessage actorMovementMessage; ActorTeleportsMessage actorTeleportsMessage; SaveTeamPresetMessage saveTeamPresetMessage; DeletionTeamPresetMessage deletionTeamPresetMessage; TeamPresetListMessage teamPresetListMessage; FighterInformationListMessage fighterInformationListMessage; CreationFighterInformationMessage creationFighterInformationMessage; DeletionFighterInformationMessage deletionFighterInformationMessage; UpdatedFighterInformationInventoryMessage updatedFighterInformationInventoryMessage; FightCreationMessage fightCreationMessage; MoveToFreePlacementMessage moveToFreePlacementMessage; StartPresentationMessage startPresentationMessage; EndPresentationMessage endPresentationMessage; TeamMateSetReadyForPlacementMessage teamMateSetReadyForPlacementMessage; StartPlacementMessage startPlacementMessage; EndPlacementMessage endPlacementMessage; TeamMateSetReadyForObservationMessage teamMateSetReadyForObservationMessage; StartObservationMessage startObservationMessage; EndObservationMessage endObservationMessage; TeamMateSetReadyForActionMessage teamMateSetReadyForActionMessage; EffectAreaActionMessage effectAreaActionMessage; StartActionMessage startActionMessage; FighterTurnBeginMessage fighterTurnBeginMessage; FighterTurnEndMessage fighterTurnEndMessage; FighterDiesMessage fighterDiesMessage; FighterMoveMessage fighterMoveMessage; NewTableTurnBeginMessage newTableTurnBeginMessage; FighterChangeDirectionMessage fighterChangeDirectionMessage; EndFightMessage endFightMessage; ItemExchangeInvitationMessage itemExchangeInvitationMessage; ItemExchangeInvitationConfirmationMessage itemExchangeInvitationConfirmationMessage; ItemExchangeCardAddedMessage itemExchangeCardAddedMessage; ItemExchangeCardRemovedMessage itemExchangeCardRemovedMessage; ItemExchangeEndMessage itemExchangeEndMessage; ItemExchangeUserReadyMessage itemExchangeUserReadyMessage; FightActionSequenceExecute fightActionSequenceExecute; RunningEffectActionMessage runningEffectActionMessage; SpellCastMessage spellCastMessage; FighterTackledMessage fighterTackledMessage; FighterCardUseMessage fighterCardUseMessage; CloseCombatMessage closeCombatMessage; ChannelFlagsMessage channelFlagsMessage; ChannelJoinMessage channelJoinMessage; ChannelLeaveMessage channelLeaveMessage; ChannelMemberFlagsMessage channelMemberFlagsMessage; ChannelMemberKickMessage channelMemberKickMessage; ChannelMembersMessage channelMembersMessage; ChannelContentMessage channelContentMessage; ChatUserFlagsMessage chatUserFlagsMessage; FriendListMessage friendListMessage; IgnoreListMessage ignoreListMessage; NotificationFriendOnlineMessage notificationFriendOnlineMessage; NotificationFriendOfflineMessage notificationFriendOfflineMessage; VicinityContentMessage vicinityContentMessage; PrivateContentMessage privateContentMessage; FriendAddedMessage friendAddedMessage; FriendRemovedMessage friendRemovedMessage; IgnoreAddedMessage ignoreAddedMessage; IgnoreRemovedMessage ignoreRemovedMessage;
    ChannelNotFoundMessage channelNotFoundMessage;
/*     */     UserNotFoundMessage userNotFoundMessage;
/*     */     MalformedCommandMessage malformedCommandMessage;
/*     */     MemberNotFoundMessage memberNotFoundMessage;
/*     */     NotEnoughPrivilegesMessage notEnoughPrivilegesMessage;
/*     */     NotYetImplementedMessage notYetImplementedMessage;
/*     */     TargetIsYourselfMessage targetIsYourselfMessage;
/*     */     OperationNotPermitedMessage operationNotPermitedMessage;
/*  46 */     Message msg = null;
/*  47 */     switch (msgType)
/*     */     
/*     */     { 
/*     */       
/*     */       case 1024://OK
/*  52 */         return (Message)new ClientAuthenticationResultsMessage();
/*     */ 
/*     */       
/*     */       case 1026://OK
/*  56 */         return (Message)new WorldServerUnavailableMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2048://OK
/*  62 */         return (Message)new CoachCreationRequestMessage();
/*     */ 
/*     */       
/*     */       case 2050://OK
/*  66 */         return (Message)new CoachCreationResultMessage();
/*     */ 
/*     */       
/*     */       case 2052://OK
/*  70 */         return (Message)new CoachInformationsMessage();
/*     */ 
/*     */       
/*     */       case 2302://OK
/*  74 */         return (Message)new OpponentSearchErrorMessage();
/*     */ 
/*     */       
/*     */       case 2300://OK
/*  78 */         return (Message)new OpponentFoundMessage();
/*     */ 
/*     */       
/*     */       case 2304://OK
/*  82 */         return (Message)new OpponentSearchInProgressMessage();
/*     */ 
/*     */       
/*     */       case 2306://OK
/*  86 */         return (Message)new OpponentSearchCancelResultMessage();
/*     */ 
/*     */       
/*     */       case 4300:
/*  90 */         return (Message)new FightInvitationMessage();
/*     */ 
/*     */       
/*     */       case 4302:
/*  94 */         return (Message)new FightInvitationAcceptedMessage();
/*     */ 
/*     */       
/*     */       case 4304:
/*  98 */         return (Message)new FightInvitationRejectedMessage();
/*     */ 
/*     */       
/*     */       case 4309:
/* 102 */         return (Message)new FightInvitationErrorMessage();
/*     */ 
/*     */       
/*     */       case 4310:
/* 106 */         return (Message)new FightCreationCanceledMessage();
/*     */ 
/*     */       
/*     */       case 2400://OK
/* 110 */         return (Message)new PlayerStatisticsReportMessage();
/*     */ 
/*     */       
/*     */       case 4306://OK
/* 114 */         return (Message)new ReadyForFightMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5000://OK
/* 120 */         return (Message)new NoInstanceServerAvailableMessage();
/*     */ 
/*     */       
/*     */       case 4600://OK
/* 124 */         return (Message)new EnterInstanceMessage();
/*     */ 
/*     */       
/*     */       case 5200://OK
/* 128 */         return (Message)new CoachInventoryUpdateMessage();
/*     */ 
/*     */       
/*     */       case 5202://OK
/* 132 */         return (Message)new CoachEquipmentUpdateMessage();
/*     */ 
/*     */       
/*     */       case 4096://OK
/* 136 */         return (Message)new ActorSpawnMessage();
/*     */ 
/*     */       
/*     */       case 4098://OK
/* 140 */         return (Message)new ActorDespawnMessage();
/*     */ 
/*     */       
/*     */       case 4102://OK
/* 144 */         return (Message)new ActorAppearMessage();
/*     */ 
/*     */       
/*     */       case 4104://OK
/* 148 */         return (Message)new ActorDisapearMessage();
/*     */ 
/*     */       
/*     */       case 4106://OK
/* 152 */         return (Message)new ActorRepositionMessage();
/*     */ 
/*     */       
/*     */       case 4500://OK
/* 156 */         return (Message)new ActorMovementMessage();
/*     */ 
/*     */       
/*     */       case 4510://OK
/* 160 */         return (Message)new ActorTeleportsMessage();
/*     */ 
/*     */       
/*     */       case 6020://OK
/* 164 */         return (Message)new SaveTeamPresetMessage();
/*     */ 
/*     */       
/*     */       case 6022://OK
/* 168 */         return (Message)new DeletionTeamPresetMessage();
/*     */ 
/*     */       
/*     */       case 6030://OK
/* 172 */         return (Message)new TeamPresetListMessage();
/*     */ 
/*     */       
/*     */       case 6006://OK
/* 176 */         return (Message)new FighterInformationListMessage();
/*     */ 
/*     */       
/*     */       case 6000://OK
/* 180 */         return (Message)new CreationFighterInformationMessage();
/*     */ 
/*     */       
/*     */       case 6002://OK
/* 184 */         return (Message)new DeletionFighterInformationMessage();
/*     */ 
/*     */       
/*     */       case 6010://OK
/* 188 */         return (Message)new UpdatedFighterInformationInventoryMessage();
/*     */ 
/*     */       
/*     */       case 8000:// OK
/* 192 */         return (Message)new FightCreationMessage();

/*     */       case 8010:
/* 200 */         return (Message)new StartPresentationMessage();
/*     */
/*     */       case 8012:// OK
/* 208 */         return (Message)new TeamMateSetReadyForPlacementMessage();
/*     */       
/*     */       case 8018:
/* 204 */         return (Message)new EndPresentationMessage();
/*     */
/*     */       case 8020:
/* 212 */         return (Message)new StartPlacementMessage();
/*     */
/*     */       case 8022:
/* 196 */         return (Message)new MoveToFreePlacementMessage();
/*     */       
/*     */       case 8028:
/* 216 */         return (Message)new EndPlacementMessage();
/*     */ 
/*     */       
/*     */       case 8024:
/* 220 */         return (Message)new TeamMateSetReadyForObservationMessage();
/*     */ 
/*     */       
/*     */       case 8030:
/* 224 */         return (Message)new StartObservationMessage();
/*     */ 
/*     */       
/*     */       case 8038:
/* 228 */         return (Message)new EndObservationMessage();
/*     */ 
/*     */       
/*     */       case 8032:
/* 232 */         return (Message)new TeamMateSetReadyForActionMessage();
/*     */ 
/*     */       
/*     */       case 6200:
/* 236 */         return (Message)new EffectAreaActionMessage();
/*     */ 
/*     */       
/*     */       case 8040:
/* 240 */         return (Message)new StartActionMessage();
/*     */ 
/*     */       
/*     */       case 8104:
/* 244 */         return (Message)new FighterTurnBeginMessage();
/*     */ 
/*     */       
/*     */       case 8106:
/* 248 */         return (Message)new FighterTurnEndMessage();
/*     */ 
/*     */       
/*     */       case 4520:
/* 252 */         return (Message)new FighterDiesMessage();
/*     */ 
/*     */       
/*     */       case 4524:
/* 256 */         return (Message)new FighterMoveMessage();
/*     */ 
/*     */       
/*     */       case 8100:
/* 260 */         return (Message)new NewTableTurnBeginMessage();
/*     */ 
/*     */       
/*     */       case 4522:
/* 264 */         return (Message)new FighterChangeDirectionMessage();
/*     */ 
/*     */       
/*     */       case 8300:
/* 268 */         return (Message)new EndFightMessage();
/*     */ 
/*     */       
/*     */       case 5102:// OK
/* 272 */         return (Message)new ItemExchangeInvitationMessage();
/*     */ 
/*     */       
/*     */       case 5104:// OK
/* 276 */         return (Message)new ItemExchangeInvitationConfirmationMessage();
/*     */ 
/*     */       
/*     */       case 5109:// OK
/* 280 */         return (Message)new ItemExchangeCardAddedMessage();
/*     */ 
/*     */       
/*     */       case 5110:// OK
/* 284 */         return (Message)new ItemExchangeCardRemovedMessage();
/*     */ 
/*     */       
/*     */       case 5111:// OK
/* 288 */         return (Message)new ItemExchangeEndMessage();
/*     */ 
/*     */       
/*     */       case 5112:// OK
/* 292 */         return (Message)new ItemExchangeUserReadyMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 8200:
/* 299 */         return (Message)new FightActionSequenceExecute();
/*     */ 
/*     */       
/*     */       case 8120:
/* 303 */         return (Message)new RunningEffectActionMessage();
/*     */ 
/*     */       
/*     */       case 8110:
/* 307 */         return (Message)new SpellCastMessage();
/*     */ 
/*     */       
/*     */       case 4506:
/* 311 */         return (Message)new FighterTackledMessage();
/*     */ 
/*     */       
/*     */       case 8108:
/* 315 */         return (Message)new FighterCardUseMessage();
/*     */ 
/*     */       
/*     */       case 8112:
/* 319 */         return (Message)new CloseCombatMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3128:
/* 326 */         return (Message)new ChannelFlagsMessage();
/*     */ 
/*     */       
/*     */       case 3130:
/* 330 */         return (Message)new ChannelJoinMessage();
/*     */ 
/*     */       
/*     */       case 3132:
/* 334 */         return (Message)new ChannelLeaveMessage();
/*     */ 
/*     */       
/*     */       case 3134:
/* 338 */         return (Message)new ChannelMemberFlagsMessage();
/*     */ 
/*     */       
/*     */       case 3136:
/* 342 */         return (Message)new ChannelMemberKickMessage();
/*     */ 
/*     */       
/*     */       case 3138:
/* 346 */         return (Message)new ChannelMembersMessage();
/*     */ 
/*     */       
/*     */       case 3140:
/* 350 */         return (Message)new ChannelContentMessage();
/*     */ 
/*     */       
/*     */       case 3142:
/* 354 */         return (Message)new ChatUserFlagsMessage();
/*     */ 
/*     */       
/*     */       case 3144://OK
/* 358 */         return (Message)new FriendListMessage();
/*     */ 
/*     */       
/*     */       case 3146://OK
/* 362 */         return (Message)new IgnoreListMessage();
/*     */ 
/*     */       
/*     */       case 3148:
/* 366 */         return (Message)new NotificationFriendOnlineMessage();
/*     */ 
/*     */       
/*     */       case 3150:
/* 370 */         return (Message)new NotificationFriendOfflineMessage();
/*     */ 
/*     */       
/*     */       case 3152://OK
/* 374 */         return (Message)new VicinityContentMessage();
/*     */ 
/*     */       
/*     */       case 3154://OK
/* 378 */         return (Message)new PrivateContentMessage();
/*     */ 
/*     */       
/*     */       case 3156://OK
/* 382 */         return (Message)new FriendAddedMessage();
/*     */ 
/*     */       
/*     */       case 3160://OK
/* 386 */         return (Message)new FriendRemovedMessage();
/*     */ 
/*     */       
/*     */       case 3158://OK
/* 390 */         return (Message)new IgnoreAddedMessage();
/*     */ 
/*     */       
/*     */       case 3162://OK
/* 394 */         return (Message)new IgnoreRemovedMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3202:
/* 400 */         return (Message)new ChannelNotFoundMessage();
/*     */ 
/*     */       
/*     */       case 3204://OK
/* 404 */         return (Message)new UserNotFoundMessage();
/*     */ 
/*     */       
/*     */       case 3206:
/* 408 */         return (Message)new MalformedCommandMessage();
/*     */ 
/*     */       
/*     */       case 3208://OK
/* 412 */         return (Message)new MemberNotFoundMessage();
/*     */ 
/*     */       
/*     */       case 3210:
/* 416 */         return (Message)new NotEnoughPrivilegesMessage();
/*     */ 
/*     */       
/*     */       case 3212:
/* 420 */         return (Message)new NotYetImplementedMessage();
/*     */ 
/*     */       
/*     */       case 3214:
/* 424 */         return (Message)new TargetIsYourselfMessage();
/*     */ 
/*     */       
/*     */       case 3216:
/* 428 */         operationNotPermitedMessage = new OperationNotPermitedMessage();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 434 */         return (Message)operationNotPermitedMessage; }  m_logger.warn("Type de message inconnu du décodeur : " + msgType); return (Message)operationNotPermitedMessage; }
/*     */ 
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\DofusArenaMessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */