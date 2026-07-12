/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharacLeech
/*     */   extends CharacDebuff
/*     */ {
/*  18 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public CharacLeech makeObject() {
/*  20 */       return new CharacLeech(null);
/*     */     }
/*  18 */   });
/*     */   
/*     */ 
/*     */ 
/*     */   private CharacLeech() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public CharacLeech(FighterCharacteristicType charac)
/*     */   {
/*  28 */     super(charac);
/*  29 */     setTriggersToExecute();
/*     */   }
/*     */   
/*     */   public CharacLeech newInstance()
/*     */   {
/*     */     CharacLeech re;
/*     */     try {
/*  36 */       CharacLeech re = (CharacLeech)m_staticPool.borrowObject();
/*  37 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  40 */       re = new CharacLeech();
/*  41 */       re.m_pool = null;
/*  42 */       m_logger.error("Erreur lors d'un checkOut sur un CharacLeech : " + e.getMessage());
/*     */     }
/*  44 */     re.m_charac = this.m_charac;
/*  45 */     re.cloneParameters(this);
/*  46 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTriggersToExecute()
/*     */   {
/*  53 */     super.setTriggersToExecute();
/*  54 */     if (this.m_charac == null) return;
/*  55 */     switch (this.m_charac) {
/*     */     case AP: 
/*     */       break;
/*     */     case CRITICAL_RATE: 
/*  59 */       this.m_triggers.set(56);
/*  60 */       break;
/*     */     case DMG: 
/*     */       break;
/*     */     case DMG_EARTH: 
/*     */       break;
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
/* 125 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(this.m_charac)) && (this.m_caster != null) && (this.m_caster.hasCharacteristic(this.m_charac))) {
/* 126 */       int characLeech = Math.min(this.m_value, this.m_target.getCharacteristic(this.m_charac).value());
/* 127 */       this.m_caster.getCharacteristic(this.m_charac).add(characLeech);
/* 128 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean useCaster()
/*     */   {
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacLeech.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */