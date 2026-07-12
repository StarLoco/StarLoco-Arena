/*    */ package com.ankamagames.baseImpl.graphicalClient.ui.progress;
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
/*    */ 
/*    */ public abstract class AbstractProgressMonitorManager
/*    */ {
/*    */   public static final int DEFAULT_SHOW_DELAY = 200;
/* 18 */   private long m_showDelay = 200L;
/* 19 */   private long m_firstCallTimer = 0L;
/*    */ 
/*    */   
/*    */   private ProgressMonitor m_monitor;
/*    */ 
/*    */ 
/*    */   
/*    */   public ProgressMonitor getProgressMonitor() {
/* 27 */     return getProgressMonitor(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ProgressMonitor getProgressMonitor(boolean forceShow) {
/* 37 */     if (this.m_monitor == null) {
/* 38 */       this.m_monitor = createProgressMonitor();
/*    */     }
/*    */ 
/*    */     
/* 42 */     if (forceShow || (this.m_firstCallTimer != 0L && System.currentTimeMillis() - this.m_firstCallTimer > this.m_showDelay)) {
/* 43 */       showProgressMonitor(this.m_monitor);
/*    */     }
/*    */     
/* 46 */     if (this.m_firstCallTimer == 0L) {
/* 47 */       this.m_firstCallTimer = System.currentTimeMillis();
/*    */     }
/*    */     
/* 50 */     return this.m_monitor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void done() {
/* 57 */     this.m_firstCallTimer = 0L;
/* 58 */     if (this.m_monitor != null) {
/* 59 */       this.m_monitor.done();
/* 60 */       this.m_monitor.setTaskName("");
/* 61 */       this.m_monitor.subTask("");
/* 62 */       hideProgressMonitor(this.m_monitor);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setShowDelay(long showDelay) {
/* 70 */     this.m_showDelay = showDelay;
/*    */   }
/*    */   
/*    */   protected abstract ProgressMonitor createProgressMonitor();
/*    */   
/*    */   protected abstract void showProgressMonitor(ProgressMonitor paramProgressMonitor);
/*    */   
/*    */   protected abstract void hideProgressMonitor(ProgressMonitor paramProgressMonitor);
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClien\\ui\progress\AbstractProgressMonitorManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */