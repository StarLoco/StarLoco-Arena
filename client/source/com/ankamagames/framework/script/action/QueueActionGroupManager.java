/*     */ package com.ankamagames.framework.script.action;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueueActionGroupManager
/*     */   implements ActionGroupEventListener
/*     */ {
/*  17 */   protected static Logger m_logger = Logger.getLogger(QueueActionGroupManager.class);
/*     */   
/*  19 */   private static QueueActionGroupManager m_instance = new QueueActionGroupManager();
/*     */   
/*     */   private ActionGroup m_pendingActionGroup;
/*     */ 
/*     */   
/*     */   public static QueueActionGroupManager getInstance() {
/*  25 */     return m_instance;
/*     */   }
/*     */ 
/*     */   
/*  29 */   private LinkedList<ActionGroup> m_executingActionGroups = new LinkedList<ActionGroup>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_actionInExecution = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionGroup addActionToPendingGroup(Action action) {
/*  39 */     if (this.m_pendingActionGroup == null) {
/*  40 */       this.m_pendingActionGroup = new ActionGroup();
/*     */     }
/*  42 */     this.m_pendingActionGroup.addAction(action);
/*     */     
/*  44 */     return this.m_pendingActionGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionGroup getPendingActionGroup() {
/*  51 */     return this.m_pendingActionGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedList<ActionGroup> getExecutingActionGroups() {
/*  58 */     return this.m_executingActionGroups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executePendingGroup() {
/*  66 */     if (this.m_pendingActionGroup == null) {
/*     */       return;
/*     */     }
/*  69 */     this.m_executingActionGroups.add(this.m_pendingActionGroup);
/*  70 */     this.m_pendingActionGroup = null;
/*     */     
/*  72 */     if (!this.m_actionInExecution) {
/*  73 */       executeNextGroup();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeNextGroup() {
/*  80 */     if (!this.m_actionInExecution && this.m_executingActionGroups.size() > 0) {
/*  81 */       this.m_actionInExecution = true;
/*     */       
/*  83 */       ActionGroup group = this.m_executingActionGroups.getFirst();
/*     */       
/*  85 */       group.addListener(this);
/*  86 */       group.runNextAction();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActionGroupFinished(ActionGroup group) {
/*  96 */     this.m_executingActionGroups.remove(group);
/*     */     
/*  98 */     this.m_actionInExecution = false;
/*     */ 
/*     */     
/* 101 */     executeNextGroup();
/*     */   }
/*     */   
/*     */   public void traceActionContent() {
/* 105 */     m_logger.info("Action In Execution : " + this.m_actionInExecution);
/*     */     
/* 107 */     if (this.m_executingActionGroups.size() > 0) {
/* 108 */       for (ActionGroup group : this.m_executingActionGroups) {
/* 109 */         m_logger.info("Executing Action Group (" + group.getActions().size() + " actions)");
/*     */         
/* 111 */         for (Action action : group.getActions()) {
/* 112 */           m_logger.info(" * " + action.getClass().getSimpleName());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 117 */     if (this.m_pendingActionGroup != null) {
/* 118 */       m_logger.info("Pending Action Group (" + this.m_pendingActionGroup.getActions().size() + " groupes)");
/* 119 */       for (Action action : this.m_pendingActionGroup.getActions()) {
/* 120 */         m_logger.info(" - " + action.getClass().getSimpleName());
/*     */       }
/*     */     } else {
/* 123 */       m_logger.info("Pending Action Group is null");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\action\QueueActionGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */