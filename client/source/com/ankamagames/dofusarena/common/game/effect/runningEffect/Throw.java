/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ public class Throw
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Throw>() {
/*    */         public Throw makeObject() {
/* 19 */           return new Throw();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   public Throw newInstance() {
/*    */     Throw re;
/*    */     try {
/* 28 */       re = (Throw)m_staticPool.borrowObject();
/* 29 */       re.m_pool = m_staticPool;
/*    */     }
/* 31 */     catch (Exception e) {
/* 32 */       re = new Throw();
/* 33 */       re.m_pool = null;
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 36 */     re.cloneParameters(this);
/* 37 */     return re;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 43 */     boolean executed = false;
/* 44 */     if (this.m_caster instanceof AbstractFighter)
/*    */     {
/* 46 */       if (((AbstractFighter)this.m_caster).getCarriedFighter() != null) {
/* 47 */         AbstractFighter carrier = (AbstractFighter)this.m_caster;
/* 48 */         AbstractFighter carried = carrier.getCarriedFighter();
/* 49 */         Point3 startPos = new Point3(carrier.getCarriedFighter().getPosition());
/* 50 */         if (carrier.uncarry(this.m_targetCell)) {
/* 51 */           executed = true;
/*    */ 
/*    */           
/* 54 */           notifyExecution(linkedRE, trigger);
/*    */ 
/*    */           
/* 57 */           if (this.m_context.getEffectAreaManager() != null) {
/* 58 */             this.m_context.getEffectAreaManager().checkInAndOut(startPos, carried.getPosition(), (EffectUser)carried);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/* 63 */     if (executed) {
/* 64 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster() {
/* 71 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Throw.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */