/*    */ package com.ankamagames.dofusarena.client.ui.progress;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.progress.ProgressMonitor;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.property.PropertiesProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DofusArenaProgressMonitor
/*    */   implements ProgressMonitor
/*    */ {
/*    */   private static final String PROGRESS_TASK_NAME = "progress.task.name";
/*    */   private static final String PROGRESS_SUBTASK_NAME = "progress.subtask.name";
/*    */   private static final String PROGRESS_VALUE = "progress.value";
/* 22 */   private int m_totalWork = 1;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void beginTask(String name, int totalWork)
/*    */   {
/* 31 */     this.m_totalWork = totalWork;
/* 32 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("progress.task.name", name);
/* 33 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("progress.value", Double.valueOf(0.0D));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void done() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTaskName(String name)
/*    */   {
/* 50 */     if (name == null) {
/* 51 */       name = DofusArenaTranslator.getInstance().getString("loading", new Object[0]);
/*    */     }
/* 53 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("progress.task.name", name);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void subTask(String name)
/*    */   {
/* 62 */     if (name == null) {
/* 63 */       name = "";
/*    */     }
/* 65 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("progress.subtask.name", name);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void worked(int work)
/*    */   {
/* 74 */     if (this.m_totalWork != 0) {
/* 75 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("progress.value", Double.valueOf(work / this.m_totalWork));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\progress\DofusArenaProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */