/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
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
/*    */ public class Root
/*    */   extends ArenaRunningEffect
/*    */ {
/* 18 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Root>() {
/*    */         public Root makeObject() {
/* 20 */           return new Root();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean m_mustBeExecuted;
/*    */ 
/*    */ 
/*    */   
/*    */   public Root newInstance() {
/*    */     Root re;
/*    */     try {
/* 34 */       re = (Root)m_staticPool.borrowObject();
/* 35 */       re.m_pool = m_staticPool;
/*    */     }
/* 37 */     catch (Exception e) {
/* 38 */       re = new Root();
/* 39 */       re.m_pool = null;
/* 40 */       m_logger.error("Erreur lors d'un checkOut sur un Root : " + e.getMessage());
/*    */     } 
/* 42 */     re.cloneParameters(this);
/* 43 */     re.m_mustBeExecuted = true;
/* 44 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 49 */     if (this.m_mustBeExecuted && 
/* 50 */       this.m_target instanceof AbstractFighter) {
/* 51 */       ((AbstractFighter)this.m_target).getProperties().add(FighterPropertyType.ROOTED);
/*    */     }
/*    */     
/* 54 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 58 */     if (this.m_genericEffect.getParam(0) >= 0.0F) {
/* 59 */       int chance = Math.min(100, (int)this.m_genericEffect.getParam(0));
/* 60 */       this.m_mustBeExecuted = (DiceRoll.roll(100) <= chance);
/*    */     } else {
/* 62 */       this.m_mustBeExecuted = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unapply() {
/* 67 */     if (this.m_target instanceof AbstractFighter) {
/* 68 */       ((AbstractFighter)this.m_target).getProperties().substract(FighterPropertyType.ROOTED);
/*    */     }
/* 70 */     super.unapply();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 78 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Root.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */