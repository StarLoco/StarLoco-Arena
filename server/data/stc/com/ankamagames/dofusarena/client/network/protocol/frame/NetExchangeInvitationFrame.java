/*     */ package com.ankamagames.dofusarena.client.network.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
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
/*     */   public static NetExchangeInvitationFrame getInstance()
/*     */   {
/*  40 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  49 */     LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*     */     
/*  51 */     switch (message.getId())
/*     */     {
/*     */     case 5104: 
/*  54 */       ItemExchangeInvitationConfirmationMessage msg = (ItemExchangeInvitationConfirmationMessage)message;
/*     */       
/*  56 */       switch (msg.getInvitationResult())
/*     */       {
/*     */       case 0: 
/*  59 */         Coach requested = (Coach)MobileManager.getInstance().getMobile(msg.getRequestedId());
/*     */         
/*  61 */         ItemExchanger exchanger = new CardTrade(localCoach, requested, true);
/*  62 */         exchanger.start();
/*     */         
/*  64 */         break;
/*     */       
/*     */ 
/*     */       case 3: 
/*  68 */         ItemExchanger cardExchanger = localCoach.getCurrentExchanger();
/*     */         
/*  70 */         Coach requested = (Coach)MobileManager.getInstance().getMobile(msg.getRequestedId());
/*  71 */         cardExchanger.acceptInvitation(requested);
/*     */         
/*  73 */         break;
/*     */       
/*     */ 
/*     */       case 1: 
/*  77 */         ItemExchanger cardExchanger = localCoach.getCurrentExchanger();
/*     */         
/*  79 */         if (cardExchanger != null) {
/*  80 */           Coach requested = (Coach)MobileManager.getInstance().getMobile(msg.getRequestedId());
/*  81 */           cardExchanger.declineInvitation(requested, (byte)0);
/*     */         }
/*     */         
/*  84 */         break;
/*     */       
/*     */       case 2: 
/*  87 */         ItemExchanger cardExchanger = localCoach.getCurrentExchanger();
/*     */         
/*  89 */         Coach requested = (Coach)MobileManager.getInstance().getMobile(msg.getRequestedId());
/*  90 */         cardExchanger.declineInvitation(requested, (byte)0);
/*     */       }
/*     */       
/*     */       
/*     */ 
/*     */ 
/*  96 */       return false;
/*     */     
/*     */ 
/*     */     case 5102: 
/* 100 */       ItemExchangeInvitationMessage msg = (ItemExchangeInvitationMessage)message;
/*     */       
/* 102 */       Coach requester = (Coach)MobileManager.getInstance().getMobile(msg.getRequesterId());
/*     */       
/* 104 */       ItemExchanger<CoachCard> exchanger = new CardTrade(requester, localCoach, false);
/* 105 */       exchanger.start();
/*     */       
/* 107 */       localCoach.setCurrentItemExchanger(exchanger);
/*     */       
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 121 */     return 0L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\frame\NetExchangeInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */