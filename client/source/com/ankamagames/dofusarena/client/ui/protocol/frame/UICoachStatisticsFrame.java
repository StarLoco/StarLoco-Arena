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
/*    */ 
/*    */ 
/*    */ public class UICoachStatisticsFrame
/*    */   implements MessageFrame
/*    */ {
/* 22 */   private static UICoachStatisticsFrame m_instance = new UICoachStatisticsFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UICoachStatisticsFrame getInstance() {
/* 28 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 46 */     return 0L;
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
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/* 64 */     if (!isAboutToBeAdded) {
/*    */ 
/*    */       
/* 67 */       Xulor.getInstance().load("coachStatisticsDialog", Dialogs.getDialogPath("coachStatisticsDialog"), (short)10000);
/*    */       
/* 69 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.teamManagementButton", Boolean.valueOf(false));
/* 70 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(false));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 81 */     if (!isAboutToBeRemoved) {
/*    */ 
/*    */       
/* 84 */       Xulor.getInstance().unload("coachStatisticsDialog");
/*    */       
/* 86 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.teamManagementButton", Boolean.valueOf(true));
/* 87 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("menuBar.coachInventoryButton", Boolean.valueOf(true));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UICoachStatisticsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */