/*    */ package com.ankamagames.dofusarena.common.game.effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectExecutionListener;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*    */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*    */ import com.ankamagames.framework.ai.dataProvider.LineOfSightObstacleInformationProvider;
/*    */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
/*    */ import com.ankamagames.framework.ai.dataProvider.TargetInformationProvider;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ArenaEffectContext extends EffectContext {
/* 17 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ArenaEffectContext>() { public ArenaEffectContext makeObject() {
/* 18 */           return new ArenaEffectContext(null);
/*    */         } }
/* 20 */     ); protected static final Logger m_logger = Logger.getLogger(ArenaEffectContext.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ArenaEffectContext() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ArenaEffectContext checkOut(AbstractFight fight) {
/*    */     ArenaEffectContext context;
/*    */     try {
/* 34 */       context = (ArenaEffectContext)m_staticPool.borrowObject();
/* 35 */       context.m_pool = m_staticPool;
/* 36 */     } catch (Exception e) {
/* 37 */       context = new ArenaEffectContext();
/* 38 */       context.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ArenaEffectContext : " + e.getMessage());
/*    */     } 
/*    */     
/* 42 */     context.m_timeline = (BasicTimeline)fight.getTimeline();
/* 43 */     context.m_cellInformationProvider = fight.getCellInformationProvider();
/* 44 */     context.m_effectUserInformationProvider = (EffectUserInformationProvider)fight;
/* 45 */     context.m_obstacleInformationProvider = (LineOfSightObstacleInformationProvider)fight;
/* 46 */     context.m_movementObstacleInformationProvider = (MovementObstacleInformationProvider)fight;
/* 47 */     context.m_effectExecutionListener = (EffectExecutionListener)fight;
/* 48 */     context.m_targetInformationProvider = (TargetInformationProvider)fight;
/* 49 */     context.m_effectAreaManager = fight.getEffectAreaManager();
/* 50 */     return context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 58 */     super.onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckIn() {
/* 66 */     super.onCheckIn();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getType() {
/* 71 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void release() {
/* 77 */     super.release();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\ArenaEffectContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */