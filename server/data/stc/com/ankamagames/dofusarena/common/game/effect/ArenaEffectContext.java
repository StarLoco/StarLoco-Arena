/*    */ package com.ankamagames.dofusarena.common.game.effect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArenaEffectContext
/*    */   extends EffectContext
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 18 */     public ArenaEffectContext makeObject() { return new ArenaEffectContext(null); }
/* 17 */   });
/*    */   
/*    */ 
/* 20 */   protected static final Logger m_logger = Logger.getLogger(ArenaEffectContext.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ArenaEffectContext checkOut(AbstractFight fight)
/*    */   {
/*    */     ArenaEffectContext context;
/*    */     
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 34 */       ArenaEffectContext context = (ArenaEffectContext)m_staticPool.borrowObject();
/* 35 */       context.m_pool = m_staticPool;
/*    */     } catch (Exception e) {
/* 37 */       context = new ArenaEffectContext();
/* 38 */       context.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ArenaEffectContext : " + e.getMessage());
/*    */     }
/*    */     
/* 42 */     context.m_timeline = fight.getTimeline();
/* 43 */     context.m_cellInformationProvider = fight.getCellInformationProvider();
/* 44 */     context.m_effectUserInformationProvider = fight;
/* 45 */     context.m_obstacleInformationProvider = fight;
/* 46 */     context.m_movementObstacleInformationProvider = fight;
/* 47 */     context.m_effectExecutionListener = fight;
/* 48 */     context.m_targetInformationProvider = fight;
/* 49 */     context.m_effectAreaManager = fight.getEffectAreaManager();
/* 50 */     return context;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onCheckOut()
/*    */   {
/* 58 */     super.onCheckOut();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onCheckIn()
/*    */   {
/* 66 */     super.onCheckIn();
/*    */   }
/*    */   
/*    */   public byte getType()
/*    */   {
/* 71 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void release()
/*    */   {
/* 77 */     super.release();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\ArenaEffectContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */