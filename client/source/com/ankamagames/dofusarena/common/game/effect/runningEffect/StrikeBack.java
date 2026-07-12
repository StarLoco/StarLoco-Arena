/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StrikeBack
/*    */   extends ArenaRunningEffect
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<StrikeBack>() {
/*    */         public StrikeBack makeObject() {
/* 21 */           return new StrikeBack();
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   public StrikeBack() {
/* 27 */     setTriggersToExecute();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StrikeBack newInstance() {
/*    */     StrikeBack re;
/*    */     try {
/* 35 */       re = (StrikeBack)m_staticPool.borrowObject();
/* 36 */       re.m_pool = m_staticPool;
/*    */     }
/* 38 */     catch (Exception e) {
/* 39 */       re = new StrikeBack();
/* 40 */       re.m_pool = null;
/* 41 */       m_logger.error("Erreur lors d'un checkOut sur un StrikeBack : " + e.getMessage());
/*    */     } 
/* 43 */     re.cloneParameters(this);
/* 44 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute() {
/* 48 */     super.setTriggersToExecute();
/* 49 */     this.m_triggers.set(2);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 53 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)FighterCharacteristicType.HP)) {
/* 54 */       if (this.m_value > 0) {
/* 55 */         this.m_target.getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).substract(this.m_value);
/*    */       }
/* 57 */       super.execute(linkedRE, trigger);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 62 */     this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0] * triggerRE.getValue() / 100.0F);
/*    */   }
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\StrikeBack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */