/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.AbstractEffectManager;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUserInformationProvider;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SummonMirror
/*    */   extends ArenaRunningEffect
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public SummonMirror makeObject() { return new SummonMirror(); }
/* 20 */   });
/*    */   
/*    */ 
/*    */   private long m_newTargetId;
/*    */   
/*    */ 
/*    */   public SummonMirror newInstance()
/*    */   {
/*    */     SummonMirror re;
/*    */     
/*    */     try
/*    */     {
/* 32 */       SummonMirror re = (SummonMirror)m_staticPool.borrowObject();
/* 33 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 37 */       re = new SummonMirror();
/* 38 */       re.m_pool = null;
/* 39 */       m_logger.error("Erreur lors d'un checkOut sur un SummonDouble : " + e.getMessage());
/*    */     }
/* 41 */     re.cloneParameters(this);
/* 42 */     return re;
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 46 */     if ((this.m_caster instanceof AbstractFighter)) {
/* 47 */       m_logger.info("Instanciation d'une nouvelle invocations avec un id de " + this.m_newTargetId);
/*    */       
/*    */ 
/* 50 */       notifyExecution(linkedRE, trigger);
/* 51 */       this.m_target = ((AbstractFighter)this.m_caster).summonMirror(this.m_newTargetId, this.m_targetCell, this.m_value);
/*    */     }
/* 53 */     super.execute(linkedRE, trigger);
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE)
/*    */   {
/* 58 */     this.m_newTargetId = this.m_context.getEffectUserInformationProvider().getNextFreeEffectUserId();
/*    */     
/* 60 */     switch (this.m_genericEffect.getParams().length) {
/*    */     case 1: 
/* 62 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 63 */       break;
/*    */     default: 
/* 65 */       m_logger.error("Nombre de paramètres incorrect dans un Summon : " + this.m_genericEffect.getParams().length);
/* 66 */       this.m_value = 0;
/*    */     }
/*    */   }
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SummonMirror.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */