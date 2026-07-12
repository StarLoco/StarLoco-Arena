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
/*     */ public class CharacGain
/*     */   extends ArenaRunningEffect
/*     */ {
/*  20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CharacGain>() {
/*     */         public CharacGain makeObject() {
/*  22 */           return new CharacGain(null);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private FighterCharacteristicType m_charac;
/*     */   
/*     */   private CharacGain() {}
/*     */   
/*     */   public CharacGain(FighterCharacteristicType charac) {
/*  32 */     this.m_charac = charac;
/*  33 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharacGain newInstance() {
/*     */     CharacGain re;
/*     */     try {
/*  41 */       re = (CharacGain)m_staticPool.borrowObject();
/*  42 */       re.m_pool = m_staticPool;
/*     */     }
/*  44 */     catch (Exception e) {
/*  45 */       re = new CharacGain();
/*  46 */       re.m_pool = null;
/*  47 */       m_logger.error("Erreur lors d'un checkOut sur un CharacGain : " + e.getMessage());
/*     */     } 
/*  49 */     re.m_charac = this.m_charac;
/*  50 */     re.cloneParameters(this);
/*  51 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTriggersToExecute() {
/*  58 */     super.setTriggersToExecute();
/*  59 */     if (this.m_charac == null)
/*  60 */       return;  switch (this.m_charac) {
/*     */       case HP:
/*  62 */         this.m_triggers.set(1);
/*     */         break;
/*     */       
/*     */       case null:
/*  66 */         this.m_triggers.set(51);
/*     */         break;
/*     */       case MP:
/*  69 */         this.m_triggers.set(61);
/*     */         break;
/*     */ 
/*     */       
/*     */       case RES_FIRE_PERCENT:
/*  74 */         this.m_triggers.set(111);
/*     */         break;
/*     */       case RES_WATER_PERCENT:
/*  77 */         this.m_triggers.set(81);
/*     */         break;
/*     */       case RES_EARTH_PERCENT:
/*  80 */         this.m_triggers.set(91);
/*     */         break;
/*     */       case RES_WIND_PERCENT:
/*  83 */         this.m_triggers.set(101);
/*     */         break;
/*     */       case DMG_FIRE_PERCENT:
/*  86 */         this.m_triggers.set(161);
/*     */         break;
/*     */       case DMG_WATER_PERCENT:
/*  89 */         this.m_triggers.set(131);
/*     */         break;
/*     */       case DMG_EARTH_PERCENT:
/*  92 */         this.m_triggers.set(141);
/*     */         break;
/*     */       case DMG_WIND_PERCENT:
/*  95 */         this.m_triggers.set(151);
/*     */         break;
/*     */       case RES:
/*  98 */         this.m_triggers.set(71);
/*     */         break;
/*     */       case RES_FIRE:
/* 101 */         this.m_triggers.set(111);
/*     */         break;
/*     */       case RES_WATER:
/* 104 */         this.m_triggers.set(81);
/*     */         break;
/*     */       case RES_EARTH:
/* 107 */         this.m_triggers.set(91);
/*     */         break;
/*     */       case RES_WIND:
/* 110 */         this.m_triggers.set(101);
/*     */         break;
/*     */       case DMG:
/* 113 */         this.m_triggers.set(121);
/*     */         break;
/*     */       case DMG_FIRE:
/* 116 */         this.m_triggers.set(161);
/*     */         break;
/*     */       case DMG_WATER:
/* 119 */         this.m_triggers.set(131);
/*     */         break;
/*     */       case DMG_EARTH:
/* 122 */         this.m_triggers.set(141);
/*     */         break;
/*     */       case DMG_WIND:
/* 125 */         this.m_triggers.set(151);
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
/* 136 */         this.m_triggers.set(181);
/*     */         break;
/*     */       case RES_AP_LOSS:
/* 139 */         this.m_triggers.set(57);
/*     */         break;
/*     */       case RES_MP_LOSS:
/* 142 */         this.m_triggers.set(66);
/*     */         break;
/*     */       case RES_IN_PERCENT:
/* 145 */         this.m_triggers.set(71);
/*     */         break;
/*     */       case DMG_IN_PERCENT:
/* 148 */         this.m_triggers.set(121);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 156 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 157 */       AbstractCharacteristic charac = this.m_target.getCharacteristic((CharacteristicType)this.m_charac);
/* 158 */       int initialValue = charac.value();
/* 159 */       this.m_value = charac.add(this.m_value) - initialValue;
/* 160 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 165 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 167 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         return;
/*     */       case 3:
/* 170 */         this.m_value = DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2);
/*     */         return;
/*     */     } 
/* 173 */     m_logger.error("Nombre de paramètres incorrect dans un CharacGain : " + (this.m_genericEffect.getParams()).length);
/* 174 */     this.m_value = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCaster() {
/* 179 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 183 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unapply() {
/* 194 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 195 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).substract(this.m_value);
/*     */     }
/* 197 */     super.unapply();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacGain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */