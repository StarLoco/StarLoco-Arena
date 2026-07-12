/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
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
/*    */ 
/*    */ public class Summon
/*    */   extends ArenaRunningEffect
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Summon>() { public Summon makeObject() {
/* 21 */           return new Summon();
/*    */         } }
/*    */     );
/*    */ 
/*    */   
/*    */   private long m_newTargetId;
/*    */   
/*    */   public Summon newInstance() {
/*    */     Summon re;
/*    */     try {
/* 31 */       re = (Summon)m_staticPool.borrowObject();
/* 32 */       re.m_pool = m_staticPool;
/*    */     }
/* 34 */     catch (Exception e) {
/*    */       
/* 36 */       re = new Summon();
/* 37 */       re.m_pool = null;
/* 38 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     } 
/* 40 */     re.cloneParameters(this);
/* 41 */     return re;
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 45 */     if (this.m_caster instanceof AbstractFighter) {
/* 46 */       m_logger.info("Instanciation d'une nouvelle invocations avec un id de " + this.m_newTargetId);
/*    */ 
/*    */       
/* 49 */       notifyExecution(linkedRE, trigger);
/*    */       
/* 51 */       ((AbstractFighter)this.m_caster).summonCreature(this.m_newTargetId, this.m_targetCell, this.m_value);
/*    */     } 
/* 53 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 58 */     this.m_newTargetId = this.m_context.getEffectUserInformationProvider().getNextFreeEffectUserId();
/*    */     
/* 60 */     switch ((this.m_genericEffect.getParams()).length) {
/*    */       case 1:
/* 62 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*    */         return;
/*    */     } 
/* 65 */     m_logger.error("Nombre de paramètres incorrect dans un Summon : " + (this.m_genericEffect.getParams()).length);
/* 66 */     this.m_value = 0;
/*    */   }
/*    */ 
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
/*    */   
/*    */   protected void serializeTarget(ByteBuffer buff) {
/* 83 */     buff.putLong(this.m_newTargetId);
/*    */   }
/*    */   
/*    */   protected boolean unserializeTarget(long targetId, AbstractEffectManager manager) {
/* 87 */     this.m_newTargetId = targetId;
/* 88 */     this.m_target = null;
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Summon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */