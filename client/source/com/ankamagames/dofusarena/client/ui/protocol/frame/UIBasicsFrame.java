/*    */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*    */ public class UIBasicsFrame
/*    */   extends UIAbstractMenuBarFrame
/*    */ {
/* 21 */   private static UIBasicsFrame m_instance = new UIBasicsFrame();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static UIBasicsFrame getInstance() {
/* 27 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onMessage(Message message) {
/* 36 */     switch (message.getId()) {
/*    */ 
/*    */       
/*    */       case 16386:
/* 40 */         DofusArenaGameEntity.getInstance().logoff();
/*    */         
/* 42 */         return false;
/*    */ 
/*    */ 
/*    */       
/*    */       case 16387:
/* 47 */         DofusArenaGameEntity.getInstance().quit();
/*    */         
/* 49 */         return false;
/*    */     } 
/*    */ 
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 62 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIBasicsFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */