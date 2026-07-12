/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharacLoss
/*     */   extends ArenaRunningEffect
/*     */ {
/*  20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CharacLoss>() {
/*     */         public CharacLoss makeObject() {
/*  22 */           return new CharacLoss();
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   protected FighterCharacteristicType m_charac;
/*     */   
/*     */   protected CharacLoss() {}
/*     */   
/*     */   public CharacLoss(FighterCharacteristicType charac) {
/*  32 */     this.m_charac = charac;
/*  33 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */   
/*     */   public CharacLoss newInstance() {
/*     */     CharacLoss re;
/*     */     try {
/*  40 */       re = (CharacLoss)m_staticPool.borrowObject();
/*  41 */       re.m_pool = m_staticPool;
/*     */     }
/*  43 */     catch (Exception e) {
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
/*     */   
/*     */   public void setTriggersToExecute() {
/*  57 */     super.setTriggersToExecute();
/*  58 */     if (this.m_charac == null)
/*  59 */       return;  switch (this.m_charac) {
/*     */       case HP:
/*  61 */         this.m_triggers.set(2);
/*     */         break;
/*     */       
/*     */       case null:
/*  65 */         this.m_triggers.set(52);
/*     */         break;
/*     */       case MP:
/*  68 */         this.m_triggers.set(62);
/*     */         break;
/*     */ 
/*     */       
/*     */       case RES_FIRE_PERCENT:
/*  73 */         this.m_triggers.set(112);
/*     */         break;
/*     */       case RES_WATER_PERCENT:
/*  76 */         this.m_triggers.set(82);
/*     */         break;
/*     */       case RES_EARTH_PERCENT:
/*  79 */         this.m_triggers.set(92);
/*     */         break;
/*     */       case RES_WIND_PERCENT:
/*  82 */         this.m_triggers.set(102);
/*     */         break;
/*     */       case DMG_FIRE_PERCENT:
/*  85 */         this.m_triggers.set(162);
/*     */         break;
/*     */       case DMG_WATER_PERCENT:
/*  88 */         this.m_triggers.set(132);
/*     */         break;
/*     */       case DMG_EARTH_PERCENT:
/*  91 */         this.m_triggers.set(142);
/*     */         break;
/*     */       case DMG_WIND_PERCENT:
/*  94 */         this.m_triggers.set(152);
/*     */         break;
/*     */       case RES:
/*  97 */         this.m_triggers.set(72);
/*     */         break;
/*     */       case RES_FIRE:
/* 100 */         this.m_triggers.set(112);
/*     */         break;
/*     */       case RES_WATER:
/* 103 */         this.m_triggers.set(82);
/*     */         break;
/*     */       case RES_EARTH:
/* 106 */         this.m_triggers.set(92);
/*     */         break;
/*     */       case RES_WIND:
/* 109 */         this.m_triggers.set(102);
/*     */         break;
/*     */       case DMG:
/* 112 */         this.m_triggers.set(122);
/*     */         break;
/*     */       case DMG_FIRE:
/* 115 */         this.m_triggers.set(162);
/*     */         break;
/*     */       case DMG_WATER:
/* 118 */         this.m_triggers.set(132);
/*     */         break;
/*     */       case DMG_EARTH:
/* 121 */         this.m_triggers.set(142);
/*     */         break;
/*     */       case DMG_WIND:
/* 124 */         this.m_triggers.set(152);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case HEAL:
/* 135 */         this.m_triggers.set(182);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case RES_IN_PERCENT:
/* 142 */         this.m_triggers.set(72);
/*     */         break;
/*     */       case DMG_IN_PERCENT:
/* 145 */         this.m_triggers.set(122);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 153 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 154 */       AbstractCharacteristic charac = this.m_target.getCharacteristic((CharacteristicType)this.m_charac);
/* 155 */       int currentValue = charac.value();
/* 156 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).substract(this.m_value);
/* 157 */       this.m_value = currentValue - charac.value();
/* 158 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 163 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 165 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         return;
/*     */       case 3:
/* 168 */         this.m_value = DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2);
/*     */         return;
/*     */     } 
/* 171 */     m_logger.error("Nombre de paramètres incorrect dans un CharacLoss : " + (this.m_genericEffect.getParams()).length);
/* 172 */     this.m_value = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unapply() {
/* 181 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 182 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).add(this.m_value);
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacLoss.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */