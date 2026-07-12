/*     */ package com.ankamagames.framework.script.action;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public abstract class Action
/*     */ {
/*  18 */   protected static Logger m_logger = Logger.getLogger(Action.class);
/*     */   
/*     */   public static final long NO_TARGET_ID = -9223372036854775808L;
/*     */   
/*     */   public static final int NO_TRIGGER_ACTION_ID = -1;
/*  23 */   private ArrayList<ActionEventListener> m_listeners = new ArrayList<ActionEventListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_uniqueId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_actionType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_actionId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private long m_instigatorId = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private long m_targetId = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private int m_triggerActionUniqueId = -1;
/*     */   
/*     */   public Action(int uniqueId, int actionType, int actionId) {
/*  56 */     this.m_uniqueId = uniqueId;
/*  57 */     this.m_actionType = actionType;
/*  58 */     this.m_actionId = actionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void run();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(ActionEventListener listener) {
/*  72 */     this.m_listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(ActionEventListener listener) {
/*  81 */     this.m_listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueId() {
/*  88 */     return this.m_uniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUniqueId(int uniqueId) {
/*  95 */     this.m_uniqueId = uniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActionType() {
/* 102 */     return this.m_actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionType(int actionType) {
/* 109 */     this.m_actionType = actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActionId() {
/* 116 */     return this.m_actionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionId(int actionId) {
/* 123 */     this.m_actionId = actionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getInstigatorId() {
/* 130 */     return this.m_instigatorId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstigatorId(long instigatorId) {
/* 137 */     this.m_instigatorId = instigatorId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTriggerActionUniqueId() {
/* 144 */     return this.m_triggerActionUniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTriggerActionUniqueId(int triggerActionUniqueId) {
/* 151 */     this.m_triggerActionUniqueId = triggerActionUniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTargetId() {
/* 158 */     return this.m_targetId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetId(long targetId) {
/* 165 */     this.m_targetId = targetId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireActionFinishedEvent() {
/* 172 */     onActionFinished(); byte b; int i;
/*     */     ActionEventListener[] arrayOfActionEventListener;
/* 174 */     for (i = (arrayOfActionEventListener = this.m_listeners.<ActionEventListener>toArray(new ActionEventListener[0])).length, b = 0; b < i; ) { ActionEventListener listener = arrayOfActionEventListener[b];
/* 175 */       listener.onActionFinished(this);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   protected abstract void onActionFinished();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\action\Action.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */