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
/*    */ public class HPDebuff
/*    */   extends ArenaRunningEffect
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public HPDebuff makeObject() {
/* 21 */       return new HPDebuff();
/*    */     }
/* 19 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HPDebuff()
/*    */   {
/* 27 */     setTriggersToExecute();
/*    */   }
/*    */   
/*    */ 
/*    */   public HPDebuff newInstance()
/*    */   {
/*    */     HPDebuff re;
/*    */     try
/*    */     {
/* 36 */       HPDebuff re = (HPDebuff)m_staticPool.borrowObject();
/* 37 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 40 */       re = new HPDebuff();
/* 41 */       re.m_pool = null;
/* 42 */       m_logger.error("Erreur lors d'un checkOut sur un HPDebuff : " + e.getMessage());
/*    */     }
/* 44 */     re.cloneParameters(this);
/* 45 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute()
/*    */   {
/* 50 */     super.setTriggersToExecute();
/* 51 */     this.m_triggers.set(4);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger)
/*    */   {
/* 56 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/* 57 */       this.m_target.getCharacteristic(FighterCharacteristicType.HP).updateMaxValue(-this.m_value);
/* 58 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {
/* 63 */     switch (this.m_genericEffect.getParams().length) {
/*    */     case 1: 
/* 65 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 66 */       break;
/*    */     case 3: 
/* 68 */       this.m_value = (DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2));
/* 69 */       break;
/*    */     case 2: default: 
/* 71 */       m_logger.error("Nombre de paramètres incorrect dans un HPDebuff : " + this.m_genericEffect.getParams().length);
/* 72 */       this.m_value = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   public void unapply()
/*    */   {
/* 78 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/* 79 */       this.m_target.getCharacteristic(FighterCharacteristicType.HP).updateMaxValue(this.m_value);
/*    */     }
/* 81 */     super.unapply();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 85 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 89 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\HPDebuff.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */