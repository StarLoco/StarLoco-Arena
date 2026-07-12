/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchangerUser;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.core.game.exchange.CardTrade;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeInvitationConfirmationMessage;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange.ItemExchangeInvitationMessage;
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
/*     */ public class NetExchangeInvitationFrame
/*     */   implements MessageFrame
/*     */ {
/*  32 */   protected static final Logger m_logger = Logger.getLogger(NetExchangeInvitationFrame.class);
/*     */   
/*  34 */   private static NetExchangeInvitationFrame m_instance = new NetExchangeInvitationFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetExchangeInvitationFrame getInstance() {
/*  40 */     return m_instance; } public boolean onMessage(Message message) {
/*     */     ItemExchangeInvitationConfirmationMessage itemExchangeInvitationConfirmationMessage;
/*     */     ItemExchangeInvitationMessage msg;
/*     */     Coach requested;
/*     */     ItemExchanger cardExchanger;
/*     */     Coach requester;
/*     */     CardTrade cardTrade2;
/*     */     Coach coach1;
/*     */     CardTrade cardTrade1;
/*  49 */     LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*     */     
/*  51 */     switch (message.getId()) {
/*     */       
/*     */       case 5104:
/*  54 */         itemExchangeInvitationConfirmationMessage = (ItemExchangeInvitationConfirmationMessage)message;
/*     */         
/*  56 */         switch (itemExchangeInvitationConfirmationMessage.getInvitationResult()) {
/*     */           
/*     */           case 0:
/*  59 */             requested = (Coach)MobileManager.getInstance().getMobile(itemExchangeInvitationConfirmationMessage.getRequestedId());
/*     */             
/*  61 */             cardTrade2 = new CardTrade((ItemExchangerUser)localCoach, (ItemExchangerUser)requested, true);
/*  62 */             cardTrade2.start();
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/*  68 */             cardExchanger = localCoach.getCurrentExchanger();
/*     */             
/*  70 */             coach1 = (Coach)MobileManager.getInstance().getMobile(itemExchangeInvitationConfirmationMessage.getRequestedId());
/*  71 */             cardExchanger.acceptInvitation((ItemExchangerUser)coach1);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/*  77 */             cardExchanger = localCoach.getCurrentExchanger();
/*     */             
/*  79 */             if (cardExchanger != null) {
/*  80 */               coach1 = (Coach)MobileManager.getInstance().getMobile(itemExchangeInvitationConfirmationMessage.getRequestedId());
/*  81 */               cardExchanger.declineInvitation((ItemExchangerUser)coach1, (byte)0);
/*     */             } 
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/*  87 */             cardExchanger = localCoach.getCurrentExchanger();
/*     */             
/*  89 */             coach1 = (Coach)MobileManager.getInstance().getMobile(itemExchangeInvitationConfirmationMessage.getRequestedId());
/*  90 */             cardExchanger.declineInvitation((ItemExchangerUser)coach1, (byte)0);
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  96 */         return false;
/*     */ 
/*     */       
/*     */       case 5102:
/* 100 */         msg = (ItemExchangeInvitationMessage)message;
/*     */         
/* 102 */         requester = (Coach)MobileManager.getInstance().getMobile(msg.getRequesterId());
/*     */         
/* 104 */         cardTrade1 = new CardTrade((ItemExchangerUser)requester, (ItemExchangerUser)localCoach, false);
/* 105 */         cardTrade1.start();
/*     */         
/* 107 */         localCoach.setCurrentItemExchanger((ItemExchanger)cardTrade1);
/*     */         
/* 109 */         return false;
/*     */     } 
/*     */     
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 121 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetExchangeInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */