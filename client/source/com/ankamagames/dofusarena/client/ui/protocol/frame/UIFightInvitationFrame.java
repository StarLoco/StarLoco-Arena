/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FightInvitationAcceptMessage;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FightInvitationRejectMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightInvitationAcceptRequestMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightInvitationRejectRequestMessage;
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
/*    */ 
/*    */ 
/*    */ public class UIFightInvitationFrame
/*    */   implements MessageFrame
/*    */ {
/* 26 */   private static UIFightInvitationFrame m_instance = new UIFightInvitationFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIFightInvitationFrame getInstance() {
/* 32 */     return m_instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/*    */     UIFightInvitationAcceptRequestMessage uIFightInvitationAcceptRequestMessage;
/*    */     UIFightInvitationRejectRequestMessage msg;
/*    */     FightInvitationAcceptMessage fightInvitationAcceptMessage;
/*    */     FightInvitationRejectMessage netMessage;
/* 41 */     switch (message.getId()) {
/*    */       
/*    */       case 16500:
/* 44 */         uIFightInvitationAcceptRequestMessage = (UIFightInvitationAcceptRequestMessage)message;
/*    */ 
/*    */         
/* 47 */         fightInvitationAcceptMessage = new FightInvitationAcceptMessage();
/* 48 */         fightInvitationAcceptMessage.setInvitationId(uIFightInvitationAcceptRequestMessage.getInvitationId());
/* 49 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)fightInvitationAcceptMessage);
/*    */         
/* 51 */         return false;
/*    */ 
/*    */       
/*    */       case 16501:
/* 55 */         msg = (UIFightInvitationRejectRequestMessage)message;
/*    */ 
/*    */         
/* 58 */         netMessage = new FightInvitationRejectMessage();
/* 59 */         netMessage.setInvitationId(msg.getInvitationId());
/* 60 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*    */         
/* 62 */         return false;
/*    */     } 
/*    */ 
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 75 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIFightInvitationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */