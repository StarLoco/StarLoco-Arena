/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HPGain
/*     */   extends ArenaRunningEffect
/*     */ {
/*  21 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public HPGain makeObject() {
/*  23 */       return new HPGain();
/*     */     }
/*  21 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HPGain()
/*     */   {
/*  29 */     setTriggersToExecute();
/*     */   }
/*     */   
/*     */   public HPGain newInstance() {
/*     */     HPGain re;
/*     */     try {
/*  35 */       HPGain re = (HPGain)m_staticPool.borrowObject();
/*  36 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  39 */       re = new HPGain();
/*  40 */       re.m_pool = null;
/*  41 */       m_logger.error("Erreur lors d'un checkOut sur un HPGain : " + e.getMessage());
/*     */     }
/*  43 */     re.cloneParameters(this);
/*  44 */     return re;
/*     */   }
/*     */   
/*     */   public void setTriggersToExecute()
/*     */   {
/*  49 */     super.setTriggersToExecute();
/*  50 */     this.m_triggers.set(1);
/*     */   }
/*     */   
/*     */   public void update(int whatToUpdate, float howMuchToUpate, boolean set) {
/*  54 */     super.update(whatToUpdate, howMuchToUpate, set);
/*  55 */     switch (whatToUpdate)
/*     */     {
/*     */     case 0: 
/*  58 */       if (!set) {
/*  59 */         this.m_value = ((int)(this.m_value + this.m_value * howMuchToUpate / 100.0F));
/*     */       }
/*  61 */       break;
/*     */     
/*     */     case 1: 
/*  64 */       if (!set) {
/*  65 */         this.m_value = ((int)(this.m_value + howMuchToUpate));
/*     */       } else {
/*  67 */         this.m_value = ValueRounder.randomRound(howMuchToUpate);
/*     */       }
/*  69 */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  78 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/*  79 */       AbstractCharacteristic charac = this.m_target.getCharacteristic(FighterCharacteristicType.HP);
/*     */       
/*     */ 
/*  82 */       int initialValue = charac.value();
/*  83 */       this.m_value = (charac.add(this.m_value) - initialValue);
/*     */       
/*  85 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/*  91 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/*  93 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*  94 */       break;
/*     */     case 3: 
/*  96 */       this.m_value = (DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2));
/*  97 */       break;
/*     */     case 2: default: 
/*  99 */       m_logger.error("Nombre de paramètres incorrect dans un HPGain : " + this.m_genericEffect.getParams().length);
/* 100 */       this.m_value = 0;
/*     */     }
/* 102 */     float value = this.m_value;
/* 103 */     if ((this.m_caster != null) && (this.m_caster.hasCharacteristic(FighterCharacteristicType.HEAL))) {
/* 104 */       value *= (100 + this.m_caster.getCharacteristicValue(FighterCharacteristicType.HEAL)) / 100.0F;
/*     */     }
/* 106 */     this.m_value = Math.max(0, ValueRounder.randomRound(value));
/*     */   }
/*     */   
/*     */   public boolean useCaster()
/*     */   {
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 119 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\HPGain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */