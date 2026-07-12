/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction;
/*    */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ public class TurnSightOnCell
/*    */   extends ArenaRunningEffect
/*    */ {
/* 16 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<TurnSightOnCell>() {
/*    */         public TurnSightOnCell makeObject() {
/* 18 */           return new TurnSightOnCell();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   public TurnSightOnCell newInstance() {
/*    */     TurnSightOnCell re;
/*    */     try {
/* 27 */       re = (TurnSightOnCell)m_staticPool.borrowObject();
/* 28 */       re.m_pool = m_staticPool;
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       re = new TurnSightOnCell();
/* 32 */       re.m_pool = null;
/* 33 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 35 */     re.cloneParameters(this);
/* 36 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 41 */     Vector3i vDir = new Vector3i(this.m_target.getPosition(), this.m_targetCell);
/* 42 */     this.m_target.setDirection((Direction)vDir.toDirection4());
/* 43 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster() {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\TurnSightOnCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */