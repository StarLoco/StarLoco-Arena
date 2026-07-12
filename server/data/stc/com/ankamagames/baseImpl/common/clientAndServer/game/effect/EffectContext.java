/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.LineOfSightObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.TargetInformationProvider;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
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
/*     */ 
/*     */ public abstract class EffectContext
/*     */   implements Poolable
/*     */ {
/*  27 */   protected static final Logger m_logger = Logger.getLogger(EffectContext.class);
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   protected BasicTimeline m_timeline;
/*     */   
/*     */   protected CellInformationProvider m_cellInformationProvider;
/*     */   
/*     */   protected LineOfSightObstacleInformationProvider m_obstacleInformationProvider;
/*     */   protected MovementObstacleInformationProvider m_movementObstacleInformationProvider;
/*     */   protected EffectUserInformationProvider m_effectUserInformationProvider;
/*     */   protected EffectExecutionListener m_effectExecutionListener;
/*     */   protected EffectAreaManager m_effectAreaManager;
/*     */   protected TargetInformationProvider<? extends EffectUser> m_targetInformationProvider;
/*     */   
/*     */   public abstract byte getType();
/*     */   
/*     */   public EffectExecutionListener getEffectExecutionListener()
/*     */   {
/*  46 */     return this.m_effectExecutionListener;
/*     */   }
/*     */   
/*     */   public TargetInformationProvider<? extends EffectUser> getTargetInformationProvider() {
/*  50 */     return this.m_targetInformationProvider;
/*     */   }
/*     */   
/*     */   public BasicTimeline getTimeline() {
/*  54 */     return this.m_timeline;
/*     */   }
/*     */   
/*     */   public void setTimeline(BasicTimeline timeline) {
/*  58 */     this.m_timeline = timeline;
/*     */   }
/*     */   
/*     */   public CellInformationProvider getCellInformationProvider()
/*     */   {
/*  63 */     return this.m_cellInformationProvider;
/*     */   }
/*     */   
/*     */   public LineOfSightObstacleInformationProvider getObstacleInformationProvider() {
/*  67 */     return this.m_obstacleInformationProvider;
/*     */   }
/*     */   
/*     */   public MovementObstacleInformationProvider getMovementObstacleInformationProvider()
/*     */   {
/*  72 */     return this.m_movementObstacleInformationProvider;
/*     */   }
/*     */   
/*     */   public EffectUserInformationProvider getEffectUserInformationProvider() {
/*  76 */     return this.m_effectUserInformationProvider;
/*     */   }
/*     */   
/*     */   public EffectAreaManager getEffectAreaManager()
/*     */   {
/*  81 */     return this.m_effectAreaManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/*  88 */     if (this.m_pool != null) {
/*     */       try {
/*  90 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/*  92 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       }
/*  94 */       this.m_pool = null;
/*     */     } else {
/*  96 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/* 101 */     this.m_timeline = null;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 105 */     this.m_timeline = null;
/* 106 */     this.m_cellInformationProvider = null;
/* 107 */     this.m_effectUserInformationProvider = null;
/* 108 */     this.m_obstacleInformationProvider = null;
/* 109 */     this.m_effectAreaManager = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\EffectContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */