/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
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
/*    */ public class UIFightMenuBarFrame
/*    */   extends UIAbstractMenuBarFrame
/*    */ {
/* 18 */   private static UIFightMenuBarFrame m_instance = new UIFightMenuBarFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIFightMenuBarFrame getInstance() {
/* 24 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 33 */     return 0L;
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
/* 51 */     if (!isAboutToBeAdded)
/*    */     {
/*    */       
/* 54 */       Xulor.getInstance().load("fightMenuBarDialog", Dialogs.getDialogPath("fightMenuBarDialog"), 1L, (short)10000);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 67 */     if (!isAboutToBeRemoved)
/*    */     {
/*    */       
/* 70 */       Xulor.getInstance().unload("fightMenuBarDialog");
/*    */     }
/*    */     
/* 73 */     super.onFrameRemove(frameHandler, isAboutToBeRemoved);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIFightMenuBarFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */