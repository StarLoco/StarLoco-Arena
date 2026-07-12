/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public class Carry
/*    */   extends ArenaRunningEffect
/*    */ {
/* 18 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Carry>() {
/*    */         public Carry makeObject() {
/* 20 */           return new Carry();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/*    */   public Carry newInstance() {
/*    */     Carry re;
/*    */     try {
/* 29 */       re = (Carry)m_staticPool.borrowObject();
/* 30 */       re.m_pool = m_staticPool;
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       re = new Carry();
/* 34 */       re.m_pool = null;
/* 35 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 37 */     re.cloneParameters(this);
/* 38 */     return re;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 43 */     boolean executed = false;
/* 44 */     if (this.m_target instanceof AbstractFighter && this.m_caster instanceof AbstractFighter) {
/* 45 */       Point3 startPos = new Point3(this.m_target.getPosition());
/* 46 */       if (((AbstractFighter)this.m_caster).carry((AbstractFighter)this.m_target)) {
/* 47 */         executed = true;
/*    */ 
/*    */         
/* 50 */         notifyExecution(linkedRE, trigger);
/*    */ 
/*    */         
/* 53 */         if (this.m_context.getEffectAreaManager() != null) {
/* 54 */           this.m_context.getEffectAreaManager().checkInAndOut(startPos, this.m_target.getPosition(), this.m_target);
/*    */         }
/*    */       } 
/*    */     } 
/* 58 */     if (executed) {
/* 59 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster() {
/* 66 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 70 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Carry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */