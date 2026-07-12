/*     */ package com.ankamagames.dofusarena.client.network.protocol;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.AbstractClientMessageDecoder;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChatUserFlagsMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendListMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendRemovedMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.PrivateContentMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.MemberNotFoundMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.TargetIsYourselfMessage;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.UserNotFoundMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.CloseCombatMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FightActionSequenceExecute;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterDiesMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.RunningEffectActionMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.SpellCastMessage;
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
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchInProgressMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.ReadyForFightMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DofusArenaMessageDecoder extends AbstractClientMessageDecoder
/*     */ {
/*  38 */   protected static Logger m_logger = Logger.getLogger(DofusArenaMessageDecoder.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Message createMessageFromType(int msgType)
/*     */   {
/*  46 */     Message msg = null;
/*  47 */     switch (msgType)
/*     */     {
/*     */ 
/*     */ 
/*     */     case 1024: 
/*  52 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.connection.serverToClient.ClientAuthenticationResultsMessage();
/*  53 */       break;
/*     */     
/*     */     case 1026: 
/*  56 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.connection.serverToClient.WorldServerUnavailableMessage();
/*  57 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */     case 2048: 
/*  62 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.CoachCreationRequestMessage();
/*  63 */       break;
/*     */     
/*     */     case 2050: 
/*  66 */       msg = new CoachCreationResultMessage();
/*  67 */       break;
/*     */     
/*     */     case 2052: 
/*  70 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.CoachInformationsMessage();
/*  71 */       break;
/*     */     
/*     */     case 2302: 
/*  74 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchErrorMessage();
/*  75 */       break;
/*     */     
/*     */     case 2300: 
/*  78 */       msg = new OpponentFoundMessage();
/*  79 */       break;
/*     */     
/*     */     case 2304: 
/*  82 */       msg = new OpponentSearchInProgressMessage();
/*  83 */       break;
/*     */     
/*     */     case 2306: 
/*  86 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.OpponentSearchCancelResultMessage();
/*  87 */       break;
/*     */     
/*     */     case 4300: 
/*  90 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationMessage();
/*  91 */       break;
/*     */     
/*     */     case 4302: 
/*  94 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationAcceptedMessage();
/*  95 */       break;
/*     */     
/*     */     case 4304: 
/*  98 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationRejectedMessage();
/*  99 */       break;
/*     */     
/*     */     case 4309: 
/* 102 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.FightInvitationErrorMessage();
/* 103 */       break;
/*     */     
/*     */     case 4310: 
/* 106 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.FightCreationCanceledMessage();
/* 107 */       break;
/*     */     
/*     */     case 2400: 
/* 110 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.world.serverToClient.PlayerStatisticsReportMessage();
/* 111 */       break;
/*     */     
/*     */     case 4306: 
/* 114 */       msg = new ReadyForFightMessage();
/* 115 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */     case 5000: 
/* 120 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.serverStatus.NoInstanceServerAvailableMessage();
/* 121 */       break;
/*     */     
/*     */     case 4600: 
/* 124 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.EnterInstanceMessage();
/* 125 */       break;
/*     */     
/*     */     case 5200: 
/* 128 */       msg = new CoachInventoryUpdateMessage();
/* 129 */       break;
/*     */     
/*     */     case 5202: 
/* 132 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.coachManagement.CoachEquipmentUpdateMessage();
/* 133 */       break;
/*     */     
/*     */     case 4096: 
/* 136 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorSpawnMessage();
/* 137 */       break;
/*     */     
/*     */     case 4098: 
/* 140 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorDespawnMessage();
/* 141 */       break;
/*     */     
/*     */     case 4102: 
/* 144 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorAppearMessage();
/* 145 */       break;
/*     */     
/*     */     case 4104: 
/* 148 */       msg = new ActorDisapearMessage();
/* 149 */       break;
/*     */     
/*     */     case 4106: 
/* 152 */       msg = new ActorRepositionMessage();
/* 153 */       break;
/*     */     
/*     */     case 4500: 
/* 156 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorMovementMessage();
/* 157 */       break;
/*     */     
/*     */     case 4510: 
/* 160 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor.ActorTeleportsMessage();
/* 161 */       break;
/*     */     
/*     */     case 6020: 
/* 164 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.SaveTeamPresetMessage();
/* 165 */       break;
/*     */     
/*     */     case 6022: 
/* 168 */       msg = new DeletionTeamPresetMessage();
/* 169 */       break;
/*     */     
/*     */     case 6030: 
/* 172 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.TeamPresetListMessage();
/* 173 */       break;
/*     */     
/*     */     case 6006: 
/* 176 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.FighterInformationListMessage();
/* 177 */       break;
/*     */     
/*     */     case 6000: 
/* 180 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.CreationFighterInformationMessage();
/* 181 */       break;
/*     */     
/*     */     case 6002: 
/* 184 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.DeletionFighterInformationMessage();
/* 185 */       break;
/*     */     
/*     */     case 6010: 
/* 188 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.teamManagement.UpdatedFighterInformationInventoryMessage();
/* 189 */       break;
/*     */     
/*     */     case 8000: 
/* 192 */       msg = new FightCreationMessage();
/* 193 */       break;
/*     */     
/*     */     case 8022: 
/* 196 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.MoveToFreePlacementMessage();
/* 197 */       break;
/*     */     
/*     */     case 8010: 
/* 200 */       msg = new StartPresentationMessage();
/* 201 */       break;
/*     */     
/*     */     case 8018: 
/* 204 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EndPresentationMessage();
/* 205 */       break;
/*     */     
/*     */     case 8012: 
/* 208 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.TeamMateSetReadyForPlacementMessage();
/* 209 */       break;
/*     */     
/*     */     case 8020: 
/* 212 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.StartPlacementMessage();
/* 213 */       break;
/*     */     
/*     */     case 8028: 
/* 216 */       msg = new EndPlacementMessage();
/* 217 */       break;
/*     */     
/*     */     case 8024: 
/* 220 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.TeamMateSetReadyForObservationMessage();
/* 221 */       break;
/*     */     
/*     */     case 8030: 
/* 224 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.StartObservationMessage();
/* 225 */       break;
/*     */     
/*     */     case 8038: 
/* 228 */       msg = new EndObservationMessage();
/* 229 */       break;
/*     */     
/*     */     case 8032: 
/* 232 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.TeamMateSetReadyForActionMessage();
/* 233 */       break;
/*     */     
/*     */     case 6200: 
/* 236 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.EffectAreaActionMessage();
/* 237 */       break;
/*     */     
/*     */     case 8040: 
/* 240 */       msg = new StartActionMessage();
/* 241 */       break;
/*     */     
/*     */     case 8104: 
/* 244 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterTurnBeginMessage();
/* 245 */       break;
/*     */     
/*     */     case 8106: 
/* 248 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterTurnEndMessage();
/* 249 */       break;
/*     */     
/*     */     case 4520: 
/* 252 */       msg = new FighterDiesMessage();
/* 253 */       break;
/*     */     
/*     */     case 4524: 
/* 256 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterMoveMessage();
/* 257 */       break;
/*     */     
/*     */     case 8100: 
/* 260 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.NewTableTurnBeginMessage();
/* 261 */       break;
/*     */     
/*     */     case 4522: 
/* 264 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.fight.FighterChangeDirectionMessage();
/* 265 */       break;
/*     */     
/*     */     case 8300: 
/* 268 */       msg = new EndFightMessage();
/* 269 */       break;
/*     */     
/*     */     case 5102: 
/* 272 */       msg = new ItemExchangeInvitationMessage();
/* 273 */       break;
/*     */     
/*     */     case 5104: 
/* 276 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeInvitationConfirmationMessage();
/* 277 */       break;
/*     */     
/*     */     case 5109: 
/* 280 */       msg = new ItemExchangeCardAddedMessage();
/* 281 */       break;
/*     */     
/*     */     case 5110: 
/* 284 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeCardRemovedMessage();
/* 285 */       break;
/*     */     
/*     */     case 5111: 
/* 288 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeEndMessage();
/* 289 */       break;
/*     */     
/*     */     case 5112: 
/* 292 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeUserReadyMessage();
/* 293 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 8200: 
/* 299 */       msg = new FightActionSequenceExecute();
/* 300 */       break;
/*     */     
/*     */     case 8120: 
/* 303 */       msg = new RunningEffectActionMessage();
/* 304 */       break;
/*     */     
/*     */     case 8110: 
/* 307 */       msg = new SpellCastMessage();
/* 308 */       break;
/*     */     
/*     */     case 4506: 
/* 311 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterTackledMessage();
/* 312 */       break;
/*     */     
/*     */     case 8108: 
/* 315 */       msg = new com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.action.FighterCardUseMessage();
/* 316 */       break;
/*     */     
/*     */     case 8112: 
/* 319 */       msg = new CloseCombatMessage();
/* 320 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 3128: 
/* 326 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelFlagsMessage();
/* 327 */       break;
/*     */     
/*     */     case 3130: 
/* 330 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelJoinMessage();
/* 331 */       break;
/*     */     
/*     */     case 3132: 
/* 334 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelLeaveMessage();
/* 335 */       break;
/*     */     
/*     */     case 3134: 
/* 338 */       msg = new ChannelMemberFlagsMessage();
/* 339 */       break;
/*     */     
/*     */     case 3136: 
/* 342 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMemberKickMessage();
/* 343 */       break;
/*     */     
/*     */     case 3138: 
/* 346 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelMembersMessage();
/* 347 */       break;
/*     */     
/*     */     case 3140: 
/* 350 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.ChannelContentMessage();
/* 351 */       break;
/*     */     
/*     */     case 3142: 
/* 354 */       msg = new ChatUserFlagsMessage();
/* 355 */       break;
/*     */     
/*     */     case 3144: 
/* 358 */       msg = new FriendListMessage();
/* 359 */       break;
/*     */     
/*     */     case 3146: 
/* 362 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreListMessage();
/* 363 */       break;
/*     */     
/*     */     case 3148: 
/* 366 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationFriendOnlineMessage();
/* 367 */       break;
/*     */     
/*     */     case 3150: 
/* 370 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.NotificationFriendOfflineMessage();
/* 371 */       break;
/*     */     
/*     */     case 3152: 
/* 374 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.VicinityContentMessage();
/* 375 */       break;
/*     */     
/*     */     case 3154: 
/* 378 */       msg = new PrivateContentMessage();
/* 379 */       break;
/*     */     
/*     */     case 3156: 
/* 382 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.FriendAddedMessage();
/* 383 */       break;
/*     */     
/*     */     case 3160: 
/* 386 */       msg = new FriendRemovedMessage();
/* 387 */       break;
/*     */     
/*     */     case 3158: 
/* 390 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreAddedMessage();
/* 391 */       break;
/*     */     
/*     */     case 3162: 
/* 394 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.IgnoreRemovedMessage();
/* 395 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */     case 3202: 
/* 400 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.ChannelNotFoundMessage();
/* 401 */       break;
/*     */     
/*     */     case 3204: 
/* 404 */       msg = new UserNotFoundMessage();
/* 405 */       break;
/*     */     
/*     */     case 3206: 
/* 408 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.MalformedCommandMessage();
/* 409 */       break;
/*     */     
/*     */     case 3208: 
/* 412 */       msg = new MemberNotFoundMessage();
/* 413 */       break;
/*     */     
/*     */     case 3210: 
/* 416 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.NotEnoughPrivilegesMessage();
/* 417 */       break;
/*     */     
/*     */     case 3212: 
/* 420 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.NotYetImplementedMessage();
/* 421 */       break;
/*     */     
/*     */     case 3214: 
/* 424 */       msg = new TargetIsYourselfMessage();
/* 425 */       break;
/*     */     
/*     */     case 3216: 
/* 428 */       msg = new com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient.errorMessage.OperationNotPermitedMessage();
/* 429 */       break;
/*     */     
/*     */     default: 
/* 432 */       m_logger.warn("Type de message inconnu du décodeur : " + msgType); }
/*     */     
/* 434 */     return msg;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\DofusArenaMessageDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */