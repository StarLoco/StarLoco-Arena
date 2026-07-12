/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
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
/*    */ public class UIMenuFrame
/*    */   implements MessageFrame
/*    */ {
/* 20 */   private static UIMenuFrame m_instance = new UIMenuFrame();
/*    */   
/*    */ 
/*    */ 
/*    */   public static UIMenuFrame getInstance()
/*    */   {
/* 26 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 35 */     message.getId();
/*    */     
/*    */ 
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 47 */     return 0L;
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
/* 65 */     if (!isAboutToBeAdded)
/*    */     {
/*    */ 
/* 68 */       Xulor.getInstance().load("menuDialog", Dialogs.getDialogPath("menuDialog"), 129L, (short)19500);
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
/* 80 */     if (!isAboutToBeRemoved)
/*    */     {
/*    */ 
/* 83 */       Xulor.getInstance().unload("menuDialog");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIMenuFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */