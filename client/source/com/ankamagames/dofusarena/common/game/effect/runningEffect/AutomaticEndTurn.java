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
/*    */ public class AutomaticEndTurn
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<AutomaticEndTurn>() {
/*    */         public AutomaticEndTurn makeObject() {
/* 19 */           return new AutomaticEndTurn();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   public AutomaticEndTurn newInstance() {
/*    */     AutomaticEndTurn re;
/*    */     try {
/* 28 */       re = (AutomaticEndTurn)m_staticPool.borrowObject();
/* 29 */       re.m_pool = m_staticPool;
/*    */     }
/* 31 */     catch (Exception e) {
/* 32 */       re = new AutomaticEndTurn();
/* 33 */       re.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un AutomaticEndTurn : " + e.getMessage());
/*    */     } 
/* 36 */     re.cloneParameters(this);
/* 37 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 42 */     if (this.m_target instanceof AbstractFighter) {
/* 43 */       ((AbstractFighter)this.m_target).getProperties().add(FighterPropertyType.PETRIFIED);
/*    */     }
/* 45 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unapply() {
/* 50 */     if (this.m_target instanceof AbstractFighter) {
/* 51 */       ((AbstractFighter)this.m_target).getProperties().substract(FighterPropertyType.PETRIFIED);
/*    */     }
/* 53 */     super.unapply();
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */ 
/*    */   
/*    */   public boolean useCaster() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\AutomaticEndTurn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */