/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.TeamMateSetReadyForActionRequestMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*    */ import com.ankamagames.xulor.Xulor;
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
/*    */ public class UIFightObservationFrame
/*    */   implements MessageFrame
/*    */ {
/* 25 */   private static UIFightObservationFrame m_instance = new UIFightObservationFrame();
/*    */   
/*    */ 
/*    */ 
/*    */   public static UIFightObservationFrame getInstance()
/*    */   {
/* 31 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 40 */     switch (message.getId())
/*    */     {
/*    */ 
/*    */ 
/*    */     case 18011: 
/* 45 */       TeamMateSetReadyForActionRequestMessage netMessage = new TeamMateSetReadyForActionRequestMessage();
/* 46 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*    */       
/* 48 */       return false;
/*    */     }
/*    */     
/*    */     
/* 52 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 61 */     return 0L;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setId(long id) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*    */   {
/* 79 */     if (!isAboutToBeAdded)
/*    */     {
/*    */ 
/* 82 */       Xulor.getInstance().load("fightObservationDialog", Dialogs.getDialogPath("fightObservationDialog"), 1L, (short)10000);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*    */   {
/* 94 */     if (!isAboutToBeRemoved)
/*    */     {
/*    */ 
/* 97 */       Xulor.getInstance().unload("fightObservationDialog");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightObservationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */