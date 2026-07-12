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
/*     */ public class CharacLoss
/*     */   extends ArenaRunningEffect
/*     */ {
/*  20 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public CharacLoss makeObject() {
/*  22 */       return new CharacLoss();
/*     */     }
/*  20 */   });
/*     */   
/*     */ 
/*     */   protected FighterCharacteristicType m_charac;
/*     */   
/*     */ 
/*     */ 
/*     */   protected CharacLoss() {}
/*     */   
/*     */ 
/*     */   public CharacLoss(FighterCharacteristicType charac)
/*     */   {
/*  32 */     this.m_charac = charac;
/*  33 */     setTriggersToExecute();
/*     */   }
/*     */   
/*     */   public CharacLoss newInstance()
/*     */   {
/*     */     CharacLoss re;
/*     */     try {
/*  40 */       CharacLoss re = (CharacLoss)m_staticPool.borrowObject();
/*  41 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  44 */       re = new CharacLoss();
/*  45 */       re.m_pool = null;
/*  46 */       m_logger.error("Erreur lors d'un checkOut sur un CharacLoss : " + e.getMessage());
/*     */     }
/*  48 */     re.m_charac = this.m_charac;
/*  49 */     re.cloneParameters(this);
/*  50 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTriggersToExecute()
/*     */   {
/*  57 */     super.setTriggersToExecute();
/*  58 */     if (this.m_charac == null) return;
/*  59 */     switch (this.m_charac) {
/*     */     case AP: 
/*  61 */       this.m_triggers.set(2);
/*  62 */       break;
/*     */     
/*     */     case CRITICAL_RATE: 
/*  65 */       this.m_triggers.set(52);
/*  66 */       break;
/*     */     case DMG: 
/*  68 */       this.m_triggers.set(62);
/*  69 */       break;
/*     */     case DMG_EARTH: 
/*     */       break;
/*     */     case DMG_EARTH_PERCENT: 
/*  73 */       this.m_triggers.set(112);
/*  74 */       break;
/*     */     case DMG_FIRE: 
/*  76 */       this.m_triggers.set(82);
/*  77 */       break;
/*     */     case DMG_FIRE_PERCENT: 
/*  79 */       this.m_triggers.set(92);
/*  80 */       break;
/*     */     case DMG_IN_PERCENT: 
/*  82 */       this.m_triggers.set(102);
/*  83 */       break;
/*     */     case DMG_REBOUND: 
/*  85 */       this.m_triggers.set(162);
/*  86 */       break;
/*     */     case DMG_WATER: 
/*  88 */       this.m_triggers.set(132);
/*  89 */       break;
/*     */     case DMG_WATER_PERCENT: 
/*  91 */       this.m_triggers.set(142);
/*  92 */       break;
/*     */     case DMG_WIND: 
/*  94 */       this.m_triggers.set(152);
/*  95 */       break;
/*     */     case DMG_WIND_PERCENT: 
/*  97 */       this.m_triggers.set(72);
/*  98 */       break;
/*     */     case FUMBLE_RATE: 
/* 100 */       this.m_triggers.set(112);
/* 101 */       break;
/*     */     case HEAL: 
/* 103 */       this.m_triggers.set(82);
/* 104 */       break;
/*     */     case HP: 
/* 106 */       this.m_triggers.set(92);
/* 107 */       break;
/*     */     case INIT: 
/* 109 */       this.m_triggers.set(102);
/* 110 */       break;
/*     */     case MP: 
/* 112 */       this.m_triggers.set(122);
/* 113 */       break;
/*     */     case NB_SUMMONS: 
/* 115 */       this.m_triggers.set(162);
/* 116 */       break;
/*     */     case RANGE: 
/* 118 */       this.m_triggers.set(132);
/* 119 */       break;
/*     */     case RES: 
/* 121 */       this.m_triggers.set(142);
/* 122 */       break;
/*     */     case RES_AP_LOSS: 
/* 124 */       this.m_triggers.set(152);
/* 125 */       break;
/*     */     case RES_EARTH: 
/*     */       break;
/*     */     case RES_EARTH_PERCENT: 
/*     */       break;
/*     */     case RES_FIRE: 
/*     */       break;
/*     */     case RES_FIRE_PERCENT: 
/*     */       break;
/*     */     case RES_IN_PERCENT: 
/* 135 */       this.m_triggers.set(182);
/* 136 */       break;
/*     */     case RES_MP_LOSS: 
/*     */       break;
/*     */     case RES_WATER: 
/*     */       break;
/*     */     case RES_WATER_PERCENT: 
/* 142 */       this.m_triggers.set(72);
/* 143 */       break;
/*     */     case RES_WIND: 
/* 145 */       this.m_triggers.set(122);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/* 153 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(this.m_charac))) {
/* 154 */       AbstractCharacteristic charac = this.m_target.getCharacteristic(this.m_charac);
/* 155 */       int currentValue = charac.value();
/* 156 */       this.m_target.getCharacteristic(this.m_charac).substract(this.m_value);
/* 157 */       this.m_value = (currentValue - charac.value());
/* 158 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 163 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/* 165 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 166 */       break;
/*     */     case 3: 
/* 168 */       this.m_value = (DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2));
/* 169 */       break;
/*     */     case 2: default: 
/* 171 */       m_logger.error("Nombre de paramètres incorrect dans un CharacLoss : " + this.m_genericEffect.getParams().length);
/* 172 */       this.m_value = 0;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void unapply()
/*     */   {
/* 181 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(this.m_charac))) {
/* 182 */       this.m_target.getCharacteristic(this.m_charac).add(this.m_value);
/*     */     }
/* 184 */     super.unapply();
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 196 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacLoss.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */