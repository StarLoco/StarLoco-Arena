/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import java.util.BitSet;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CharacPoison
/*    */   extends ArenaRunningEffect
/*    */ {
/* 18 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public CharacPoison makeObject() {
/* 20 */       return new CharacPoison();
/*    */     }
/* 18 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CharacPoison()
/*    */   {
/* 29 */     setTriggersToExecute();
/*    */   }
/*    */   
/*    */   public CharacPoison newInstance()
/*    */   {
/*    */     CharacPoison re;
/*    */     try
/*    */     {
/* 37 */       CharacPoison re = (CharacPoison)m_staticPool.borrowObject();
/* 38 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 41 */       re = new CharacPoison();
/* 42 */       re.m_pool = null;
/* 43 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*    */     }
/* 45 */     re.cloneParameters(this);
/* 46 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute()
/*    */   {
/* 51 */     super.setTriggersToExecute();
/* 52 */     this.m_triggers.set(2);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 56 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/* 57 */       this.m_target.getCharacteristic(FighterCharacteristicType.HP).substract(this.m_value);
/* 58 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE)
/*    */   {
/* 64 */     switch (this.m_genericEffect.getParams().length) {
/*    */     case 1: 
/* 66 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 67 */       break;
/*    */     default: 
/* 69 */       m_logger.error("Nombre de paramètres incorrect dans un CharacPoison : " + this.m_genericEffect.getParams().length);
/* 70 */       this.m_value = 0;
/*    */     }
/* 72 */     if (triggerRE != null)
/* 73 */       this.m_value *= triggerRE.getValue();
/*    */   }
/*    */   
/*    */   public boolean useCaster() {
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 81 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacPoison.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */