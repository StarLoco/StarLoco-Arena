/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
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
/*    */ 
/*    */ public class UIExchangeInvitationFrame
/*    */   implements MessageFrame
/*    */ {
/* 23 */   private static UIExchangeInvitationFrame m_instance = new UIExchangeInvitationFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIExchangeInvitationFrame getInstance() {
/* 29 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/*    */     UIExchangeInvitationAcceptRequestMessage uIExchangeInvitationAcceptRequestMessage;
/*    */     UIExchangeInvitationRejectRequestMessage msg;
/*    */     ItemExchangeInvitationAnswerMessage netMessage;
/* 38 */     switch (message.getId()) {
/*    */       
/*    */       case 16801:
/* 41 */         uIExchangeInvitationAcceptRequestMessage = (UIExchangeInvitationAcceptRequestMessage)message;
/*    */ 
/*    */         
/* 44 */         netMessage = new ItemExchangeInvitationAnswerMessage();
/* 45 */         netMessage.setExchangeId(uIExchangeInvitationAcceptRequestMessage.getInvitationId());
/* 46 */         netMessage.setInvitationResult((byte)0);
/* 47 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/* 48 */         return false;
/*    */ 
/*    */       
/*    */       case 16802:
/* 52 */         msg = (UIExchangeInvitationRejectRequestMessage)message;
/*    */ 
/*    */         
/* 55 */         netMessage = new ItemExchangeInvitationAnswerMessage();
/* 56 */         netMessage.setExchangeId(msg.getInvitationId());
/* 57 */         netMessage.setInvitationResult((byte)1);
/* 58 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*    */         
/* 60 */         return false;
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
/*    */   
/*    */   public long getId() {
/* 73 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIExchangeInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */