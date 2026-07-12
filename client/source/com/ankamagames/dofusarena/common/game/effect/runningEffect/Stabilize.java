/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stabilize
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Stabilize>() {
/*    */         public Stabilize makeObject() {
/* 19 */           return new Stabilize();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int m_chance;
/*    */ 
/*    */ 
/*    */   
/*    */   public Stabilize newInstance() {
/*    */     Stabilize re;
/*    */     try {
/* 33 */       re = (Stabilize)m_staticPool.borrowObject();
/* 34 */       re.m_pool = m_staticPool;
/*    */     }
/* 36 */     catch (Exception e) {
/* 37 */       re = new Stabilize();
/* 38 */       re.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un Stabilize : " + e.getMessage());
/*    */     } 
/* 41 */     re.cloneParameters(this);
/* 42 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 47 */     if (this.m_target instanceof AbstractFighter) {
/* 48 */       ((AbstractFighter)this.m_target).getProperties().add(FighterPropertyType.STABILIZED);
/*    */     }
/* 50 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public void unapply() {
/* 57 */     if (this.m_target instanceof AbstractFighter) {
/* 58 */       ((AbstractFighter)this.m_target).getProperties().substract(FighterPropertyType.STABILIZED);
/*    */     }
/* 60 */     super.unapply();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Stabilize.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */