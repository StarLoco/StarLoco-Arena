/*    */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*    */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*    */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import java.util.BitSet;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class MPUse
/*    */   extends DynamicallyDefinedArenaRunningEffect
/*    */ {
/* 20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 21 */     public MPUse makeObject() { return new MPUse(); }
/* 20 */   });
/*    */   
/*    */ 
/*    */   public MPUse()
/*    */   {
/* 25 */     setTriggersToExecute();
/*    */   }
/*    */   
/*    */   public MPUse newInstance()
/*    */   {
/*    */     MPUse re;
/*    */     try
/*    */     {
/* 33 */       MPUse re = (MPUse)m_staticPool.borrowObject();
/* 34 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 38 */       re = new MPUse();
/* 39 */       re.m_pool = null;
/* 40 */       m_logger.error("Erreur lors d'un newInstance sur un MPUse : " + e.getMessage());
/*    */     }
/* 42 */     re.cloneParameters(this);
/* 43 */     return re;
/*    */   }
/*    */   
/*    */   public static MPUse checkOut(EffectContext context, int mpUsed, EffectUser target)
/*    */   {
/* 48 */     if (target == null) { return null;
/*    */     }
/*    */     MPUse re;
/*    */     try
/*    */     {
/* 53 */       MPUse re = (MPUse)m_staticPool.borrowObject();
/* 54 */       re.m_pool = m_staticPool;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 58 */       re = new MPUse();
/* 59 */       re.m_pool = null;
/* 60 */       m_logger.error("Erreur lors d'un checkOut sur un MPUse : " + e.getMessage());
/*    */     }
/* 62 */     re.m_id = RunningEffectConstants.MP_USE.getId();
/* 63 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.MP_USE.getObject()).getRunningEffectStatus();
/* 64 */     re.setTriggersToExecute();
/* 65 */     re.m_target = target;
/* 66 */     re.m_value = mpUsed;
/* 67 */     re.m_maxExecutionCount = -1;
/* 68 */     re.m_context = context;
/* 69 */     return re;
/*    */   }
/*    */   
/*    */   public void setTriggersToExecute()
/*    */   {
/* 74 */     super.setTriggersToExecute();
/* 75 */     this.m_triggers.set(65);
/*    */   }
/*    */   
/*    */   public void execute(RunningEffect linkedRE, boolean trigger)
/*    */   {
/* 80 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.MP))) {
/* 81 */       this.m_target.getCharacteristic(FighterCharacteristicType.MP).substract(this.m_value);
/* 82 */       super.execute(linkedRE, trigger);
/*    */     }
/*    */   }
/*    */   
/*    */   public void computeValue(RunningEffect triggerRE) {}
/*    */   
/*    */   public boolean useCaster() {
/* 89 */     return false;
/*    */   }
/*    */   
/*    */   public boolean useTarget() {
/* 93 */     return true;
/*    */   }
/*    */   
/*    */   public boolean useTargetCell() {
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\MPUse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */