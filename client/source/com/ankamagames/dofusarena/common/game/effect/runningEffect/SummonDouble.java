/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SummonDouble
/*    */   extends ArenaRunningEffect
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<SummonDouble>() { public SummonDouble makeObject() {
/* 20 */           return new SummonDouble();
/*    */         } }
/*    */     );
/*    */ 
/*    */   
/*    */   private long m_newTargetId;
/*    */ 
/*    */   
/*    */   public SummonDouble newInstance() {
/*    */     SummonDouble re;
/*    */     try {
/* 31 */       re = (SummonDouble)m_staticPool.borrowObject();
/* 32 */       re.m_pool = m_staticPool;
/*    */     }
/* 34 */     catch (Exception e) {
/*    */       
/* 36 */       re = new SummonDouble();
/* 37 */       re.m_pool = null;
/* 38 */       m_logger.error("Erreur lors d'un checkOut sur un SummonDouble : " + e.getMessage());
/*    */     } 
/* 40 */     re.cloneParameters(this);
/* 41 */     return re;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 47 */     if (this.m_caster instanceof AbstractFighter) {
/* 48 */       m_logger.info("Instanciation d'un nouveau double d'ID " + this.m_newTargetId);
/*    */       
/* 50 */       notifyExecution(linkedRE, trigger);
/* 51 */       this.m_target = (EffectUser)((AbstractFighter)this.m_caster).summonDouble(this.m_newTargetId, this.m_targetCell);
/*    */     } 
/* 53 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 57 */     this.m_newTargetId = this.m_context.getEffectUserInformationProvider().getNextFreeEffectUserId();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 69 */     return true;
/*    */   }
/*    */   
/*    */   protected void serializeTarget(ByteBuffer buff) {
/* 73 */     buff.putLong(this.m_newTargetId);
/*    */   }
/*    */   
/*    */   protected boolean unserializeTarget(long targetId, AbstractEffectManager manager) {
/* 77 */     this.m_newTargetId = targetId;
/* 78 */     this.m_target = null;
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SummonDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */