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
/*     */ public class CharacDebuff
/*     */   extends ArenaRunningEffect
/*     */ {
/*  19 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public CharacDebuff makeObject() {
/*  21 */       return new CharacDebuff();
/*     */     }
/*  19 */   });
/*     */   
/*     */ 
/*     */   protected FighterCharacteristicType m_charac;
/*     */   
/*     */ 
/*     */   protected boolean m_execute;
/*     */   
/*     */ 
/*     */ 
/*     */   protected CharacDebuff() {}
/*     */   
/*     */ 
/*     */   public CharacDebuff(FighterCharacteristicType charac)
/*     */   {
/*  34 */     this.m_execute = true;
/*  35 */     this.m_charac = charac;
/*  36 */     setTriggersToExecute();
/*     */   }
/*     */   
/*     */   public CharacDebuff newInstance()
/*     */   {
/*     */     CharacDebuff wre;
/*     */     try
/*     */     {
/*  44 */       CharacDebuff wre = (CharacDebuff)m_staticPool.borrowObject();
/*  45 */       wre.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  48 */       wre = new CharacDebuff();
/*  49 */       wre.m_pool = null;
/*  50 */       m_logger.error("Erreur lors d'un checkOut sur un CharacDebuff : " + e.getMessage());
/*     */     }
/*  52 */     wre.m_charac = this.m_charac;
/*  53 */     wre.m_execute = this.m_execute;
/*  54 */     wre.cloneParameters(this);
/*  55 */     return wre;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTriggersToExecute()
/*     */   {
/*  62 */     super.setTriggersToExecute();
/*  63 */     if (this.m_charac == null) return;
/*  64 */     switch (this.m_charac) {
/*     */     case AP: 
/*  66 */       this.m_triggers.set(4);
/*  67 */       break;
/*     */     
/*     */     case CRITICAL_RATE: 
/*  70 */       this.m_triggers.set(54);
/*  71 */       break;
/*     */     case DMG: 
/*  73 */       this.m_triggers.set(64);
/*  74 */       break;
/*     */     case DMG_EARTH: 
/*  76 */       this.m_triggers.set(172);
/*  77 */       break;
/*     */     case DMG_EARTH_PERCENT: 
/*     */       break;
/*     */     case DMG_FIRE: 
/*     */       break;
/*     */     case DMG_FIRE_PERCENT: 
/*     */       break;
/*     */     case DMG_IN_PERCENT: 
/*     */       break;
/*     */     case DMG_REBOUND: 
/*     */       break;
/*     */     case DMG_WATER: 
/*     */       break;
/*     */     case DMG_WATER_PERCENT: 
/*     */       break;
/*     */     case DMG_WIND: 
/*     */       break;
/*     */     case DMG_WIND_PERCENT: 
/*     */       break;
/*     */     case FUMBLE_RATE: 
/*     */       break;
/*     */     case HEAL: 
/*     */       break;
/*     */     case HP: 
/*     */       break;
/*     */     case INIT: 
/*     */       break;
/*     */     case MP: 
/*     */       break;
/*     */     case NB_SUMMONS: 
/*     */       break;
/*     */     case RANGE: 
/*     */       break;
/*     */     case RES: 
/*     */       break;
/*     */     case RES_AP_LOSS: 
/*     */       break;
/*     */     case RES_EARTH: 
/*     */       break;
/*     */     case RES_EARTH_PERCENT: 
/*     */       break;
/*     */     case RES_FIRE: 
/*     */       break;
/*     */     case RES_FIRE_PERCENT: 
/*     */       break;
/*     */     case RES_IN_PERCENT: 
/*     */       break;
/*     */     case RES_MP_LOSS: 
/*     */       break;
/*     */     case RES_WATER: 
/*     */       break;
/*     */     case RES_WATER_PERCENT: 
/*     */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/* 138 */     if ((this.m_execute) && 
/* 139 */       (this.m_target != null) && (this.m_target.hasCharacteristic(this.m_charac))) {
/* 140 */       this.m_target.getCharacteristic(this.m_charac).updateMaxValue(-this.m_value);
/* 141 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/* 148 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/* 150 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 151 */       break;
/*     */     case 3: 
/* 153 */       this.m_value = (DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2));
/* 154 */       break;
/*     */     case 2: default: 
/* 156 */       m_logger.error("Nombre de paramètres incorrect dans un CharacDebuff : " + this.m_genericEffect.getParams().length);
/* 157 */       this.m_value = 0;
/*     */     }
/*     */     
/* 160 */     this.m_execute = true;
/*     */     
/* 162 */     int res = 0;
/* 163 */     switch (this.m_charac) {
/*     */     case DMG: 
/* 165 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_MP_LOSS)) {
/* 166 */         res = this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_MP_LOSS);
/*     */       }
/* 168 */       break;
/*     */     case CRITICAL_RATE: 
/* 170 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_AP_LOSS)) {
/* 171 */         res = this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_AP_LOSS);
/*     */       }
/* 173 */       break;
/*     */     default: 
/* 175 */       res = 0;
/*     */     }
/*     */     
/* 178 */     if (DiceRoll.roll(100) <= res) {
/* 179 */       this.m_execute = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void unapply()
/*     */   {
/* 185 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(this.m_charac))) {
/* 186 */       this.m_target.getCharacteristic(this.m_charac).updateMaxValue(this.m_value);
/*     */     }
/* 188 */     super.unapply();
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 196 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 200 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacDebuff.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */