/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import java.util.BitSet;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class APLoss
/*    */   extends ArenaRunningEffect
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public APLoss makeObject() {
/* 22 */       return new APLoss();
/*    */     }
/* 20 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public APLoss()
/*    */   {
/* 27 */     setTriggersToExecute();
/*    */   }
/*    */   
/*    */   public APLoss newInstance()
/*    */   {
/*    */     APLoss re;
/*    */     try {
/* 34 */       APLoss re = (APLoss)m_staticPool.borrowObject();
/* 35 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 38 */       re = new APLoss();
/* 39 */       re.m_pool = null;
/* 40 */       m_logger.error("Erreur lors d'un checkOut sur un APLoss : " + e.getMessage());
/*    */     }
/* 42 */     re.cloneParameters(this);
/* 43 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute() {
/* 47 */     super.setTriggersToExecute();
/* 48 */     this.m_triggers.set(52);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 52 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.AP))) {
/* 53 */       this.m_target.getCharacteristic(FighterCharacteristicType.AP).substract(this.m_value);
/* 54 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE)
/*    */   {
/* 60 */     switch (this.m_genericEffect.getParams().length) {
/*    */     case 1: 
/* 62 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 63 */       break;
/*    */     case 3: 
/* 65 */       this.m_value = (DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2));
/* 66 */       break;
/*    */     case 2: default: 
/* 68 */       m_logger.error("Nombre de paramètres incorrect dans un APLoss : " + this.m_genericEffect.getParams().length);
/*    */     }
/*    */     
/* 71 */     int value = this.m_value;
/*    */     
/*    */ 
/* 74 */     if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_AP_LOSS)) {
/* 75 */       int res = this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_AP_LOSS);
/* 76 */       if (res > 0) {
/* 77 */         value = 0;
/* 78 */         for (int i = 0; i < this.m_value; i++) {
/* 79 */           if (DiceRoll.roll(100) > res)
/* 80 */             value++;
/*    */         }
/*    */       }
/*    */     }
/* 84 */     this.m_value = value;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean useCaster()
/*    */   {
/* 90 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 94 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 98 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\APLoss.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */