/*    */ package com.ankamagames.dofusarena.client.ui.progress;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.AbstractProgressMonitorManager;
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*    */ import com.ankamagames.dofusarena.client.ui.Dialogs;
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
/*    */ public class DofusArenaProgressMonitorManager
/*    */   extends AbstractProgressMonitorManager
/*    */ {
/* 19 */   private static DofusArenaProgressMonitorManager m_instance = new DofusArenaProgressMonitorManager();
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
/*    */   public static DofusArenaProgressMonitorManager getInstance() {
/* 31 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ProgressMonitor createProgressMonitor() {
/* 41 */     return new DofusArenaProgressMonitor();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void showProgressMonitor(ProgressMonitor monitor) {
/* 51 */     Xulor.getInstance().load("progressDialog", Dialogs.getDialogPath("progressDialog"), 66L, (short)19500);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void hideProgressMonitor(ProgressMonitor monitor) {
/* 61 */     Xulor.getInstance().unload("progressDialog");
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\progress\DofusArenaProgressMonitorManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */