/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange.ItemExchangeInvitationAnswerMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeInvitationAcceptRequestMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.exchange.UIExchangeInvitationRejectRequestMessage;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIExchangeInvitationFrame
/*    */   implements MessageFrame
/*    */ {
/* 23 */   private static UIExchangeInvitationFrame m_instance = new UIExchangeInvitationFrame();
/*    */   
/*    */ 
/*    */ 
/*    */   public static UIExchangeInvitationFrame getInstance()
/*    */   {
/* 29 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 38 */     switch (message.getId())
/*    */     {
/*    */     case 16801: 
/* 41 */       UIExchangeInvitationAcceptRequestMessage msg = (UIExchangeInvitationAcceptRequestMessage)message;
/*    */       
/*    */ 
/* 44 */       ItemExchangeInvitationAnswerMessage netMessage = new ItemExchangeInvitationAnswerMessage();
/* 45 */       netMessage.setExchangeId(msg.getInvitationId());
/* 46 */       netMessage.setInvitationResult((byte)0);
/* 47 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/* 48 */       return false;
/*    */     
/*    */ 
/*    */     case 16802: 
/* 52 */       UIExchangeInvitationRejectRequestMessage msg = (UIExchangeInvitationRejectRequestMessage)message;
/*    */       
/*    */ 
/* 55 */       ItemExchangeInvitationAnswerMessage netMessage = new ItemExchangeInvitationAnswerMessage();
/* 56 */       netMessage.setExchangeId(msg.getInvitationId());
/* 57 */       netMessage.setInvitationResult((byte)1);
/* 58 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*    */       
/* 60 */       return false;
/*    */     }
/*    */     
/*    */     
/* 64 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 73 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIExchangeInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */