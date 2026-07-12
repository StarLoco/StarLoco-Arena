/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeCardAddedMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeCardRemovedMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeEndMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeUserReadyMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetExchangeFrame
/*     */   implements MessageFrame
/*     */ {
/*  31 */   protected static final Logger m_logger = Logger.getLogger(NetExchangeFrame.class);
/*     */   
/*  33 */   private static NetExchangeFrame m_instance = new NetExchangeFrame();
/*     */   
/*     */ 
/*     */ 
/*     */   public static NetExchangeFrame getInstance()
/*     */   {
/*  39 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  48 */     LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*     */     
/*  50 */     switch (message.getId())
/*     */     {
/*     */     case 5109: 
/*  53 */       ItemExchangeCardAddedMessage exchangeCardAddedMessage = (ItemExchangeCardAddedMessage)message;
/*  54 */       ItemExchanger<CoachCard> cardExchanger = localCoach.getCurrentExchanger();
/*     */       
/*  56 */       CoachCard card = exchangeCardAddedMessage.getCard();
/*  57 */       cardExchanger.addItemToExchange(exchangeCardAddedMessage.getUserIndex(), card, card.getQuantity());
/*     */       
/*  59 */       return false;
/*     */     
/*     */ 
/*     */     case 5110: 
/*  63 */       ItemExchangeCardRemovedMessage cardRemovedMessage = (ItemExchangeCardRemovedMessage)message;
/*  64 */       ItemExchanger<CoachCard> cardExchanger = localCoach.getCurrentExchanger();
/*     */       
/*  66 */       CoachCard card = cardRemovedMessage.getCard();
/*  67 */       cardExchanger.removeItemToExchange(cardRemovedMessage.getUserIndex(), card, card.getQuantity());
/*     */       
/*  69 */       return false;
/*     */     
/*     */ 
/*     */     case 5112: 
/*  73 */       ItemExchangeUserReadyMessage exchangeUserReadyMessage = (ItemExchangeUserReadyMessage)message;
/*  74 */       ItemExchanger<CoachCard> cardExchanger = localCoach.getCurrentExchanger();
/*     */       
/*  76 */       cardExchanger.setUserReady(cardExchanger.getUser(exchangeUserReadyMessage.getUserIndex()));
/*     */       
/*  78 */       return false;
/*     */     
/*     */ 
/*     */     case 5111: 
/*  82 */       ItemExchangeEndMessage endMessage = (ItemExchangeEndMessage)message;
/*  83 */       ItemExchanger cardExchanger = localCoach.getCurrentExchanger();
/*     */       
/*  85 */       switch (endMessage.getExchangeEndReason())
/*     */       {
/*     */       case 0: 
/*  88 */         cardExchanger.acceptExchange(localCoach);
/*  89 */         break;
/*     */       case 1: 
/*  91 */         cardExchanger.cancelExchange(localCoach);
/*     */       }
/*     */       
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 106 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 125 */     if (!isAboutToBeAdded)
/*     */     {
/* 127 */       DofusArenaGameEntity.getInstance().removeFrame(NetExchangeInvitationFrame.getInstance());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 138 */     if (!isAboutToBeRemoved)
/*     */     {
/* 140 */       DofusArenaGameEntity.getInstance().pushFrame(NetExchangeInvitationFrame.getInstance());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetExchangeFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */