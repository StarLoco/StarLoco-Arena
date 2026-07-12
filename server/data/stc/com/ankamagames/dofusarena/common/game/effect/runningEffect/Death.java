/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import java.util.BitSet;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class Death
/*    */   extends ArenaRunningEffect
/*    */ {
/* 17 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*    */     public Death makeObject() {
/* 19 */       return new Death();
/*    */     }
/* 17 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Death()
/*    */   {
/* 25 */     setTriggersToExecute();
/*    */   }
/*    */   
/*    */   public Death newInstance() {
/*    */     Death re;
/*    */     try {
/* 31 */       Death re = (Death)m_staticPool.borrowObject();
/* 32 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e) {
/* 35 */       re = new Death();
/* 36 */       re.m_pool = null;
/* 37 */       m_logger.error("Erreur lors d'un checkOut sur un RE:Death : " + e.getMessage());
/*    */     }
/* 39 */     this.m_maxExecutionCount = 1;
/* 40 */     re.cloneParameters(this);
/* 41 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute() {
/* 45 */     super.setTriggersToExecute();
/* 46 */     this.m_triggers.set(2);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger)
/*    */   {
/* 51 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/* 52 */       this.m_target.getCharacteristic(FighterCharacteristicType.HP).toMin();
/* 53 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster()
/*    */   {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Death.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */