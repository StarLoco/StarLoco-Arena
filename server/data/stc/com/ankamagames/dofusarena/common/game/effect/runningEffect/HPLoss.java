/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.Part;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.PartLocalisator;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.effect.Elements;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HPLoss
/*     */   extends ArenaRunningEffect
/*     */ {
/*  27 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public HPLoss makeObject() {
/*  29 */       return new HPLoss();
/*     */     }
/*  27 */   });
/*     */   
/*     */ 
/*     */   protected Elements m_staticElement;
/*     */   
/*     */ 
/*     */ 
/*     */   protected HPLoss() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public HPLoss(Elements element)
/*     */   {
/*  40 */     this.m_staticElement = element;
/*  41 */     setTriggersToExecute();
/*     */   }
/*     */   
/*     */   public static HPLoss checkOut(EffectContext context, Elements element, int hpLost, EffectUser target) {
/*     */     HPLoss re;
/*     */     try {
/*  47 */       HPLoss re = (HPLoss)m_staticPool.borrowObject();
/*  48 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  51 */       re = new HPLoss(element);
/*  52 */       re.m_pool = null;
/*  53 */       m_logger.error("Erreur lors d'un checkOut sur un HPLoss : " + e.getMessage());
/*     */     }
/*     */     
/*  56 */     re.m_id = RunningEffectConstants.HP_LOSS.getId();
/*  57 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.HP_LOSS.getObject()).getRunningEffectStatus();
/*  58 */     re.m_staticElement = element;
/*  59 */     re.setTriggersToExecute();
/*  60 */     re.m_target = target;
/*  61 */     re.m_value = hpLost;
/*  62 */     re.m_maxExecutionCount = -1;
/*  63 */     re.m_context = context;
/*  64 */     return re;
/*     */   }
/*     */   
/*     */   public HPLoss newInstance() {
/*     */     HPLoss re;
/*     */     try {
/*  70 */       HPLoss re = (HPLoss)m_staticPool.borrowObject();
/*  71 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  74 */       re = new HPLoss();
/*  75 */       re.m_pool = null;
/*  76 */       m_logger.error("Erreur lors d'un newInstance sur un HPLoss : " + e.getMessage());
/*     */     }
/*     */     
/*  79 */     re.m_staticElement = this.m_staticElement;
/*     */     
/*     */ 
/*  82 */     re.cloneParameters(this);
/*  83 */     return re;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTriggersToExecute()
/*     */   {
/*  90 */     super.setTriggersToExecute();
/*  91 */     this.m_triggers.set(2);
/*  92 */     if (this.m_staticElement != null) {
/*  93 */       switch (this.m_staticElement) {
/*     */       case EARTH: 
/*     */         break;
/*     */       case WIND: 
/*  97 */         this.m_triggers.set(8);
/*  98 */         break;
/*     */       case FIRE: 
/* 100 */         this.m_triggers.set(5);
/* 101 */         break;
/*     */       case PHYSICAL: 
/* 103 */         this.m_triggers.set(6);
/* 104 */         break;
/*     */       case WATER: 
/* 106 */         this.m_triggers.set(7);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(int whatToUpdate, float howMuchToUpate, boolean set)
/*     */   {
/* 113 */     super.update(whatToUpdate, howMuchToUpate, set);
/* 114 */     switch (whatToUpdate)
/*     */     {
/*     */     case 0: 
/* 117 */       if (!set) {
/* 118 */         this.m_value += ValueRounder.randomRound(this.m_value * howMuchToUpate / 100.0F);
/*     */       }
/* 120 */       break;
/*     */     
/*     */     case 1: 
/* 123 */       if (!set) {
/* 124 */         this.m_value = ((int)(this.m_value + howMuchToUpate));
/*     */       } else {
/* 126 */         this.m_value = ValueRounder.randomRound(howMuchToUpate);
/*     */       }
/* 128 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 134 */     this.m_value = Math.max(0, this.m_value);
/*     */   }
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 138 */     if ((this.m_target != null) && (this.m_target.hasCharacteristic(FighterCharacteristicType.HP))) {
/* 139 */       this.m_target.getCharacteristic(FighterCharacteristicType.HP).substract(this.m_value);
/* 140 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/* 146 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/* 148 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/* 149 */       break;
/*     */     case 3: 
/* 151 */       this.m_value = DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1), (int)this.m_genericEffect.getParam(2));
/* 152 */       break;
/*     */     case 2: default: 
/* 154 */       m_logger.error("Nombre de paramètres incorrect dans un HPLoss : " + this.m_genericEffect.getParams().length);
/* 155 */       this.m_value = 0;
/*     */     }
/*     */     
/* 158 */     float value = this.m_value;
/*     */     
/*     */ 
/* 161 */     int modificator = 0;
/*     */     
/* 163 */     boolean mustBeLocalised = false;
/* 164 */     if ((this.m_genericEffect != null) && (this.m_genericEffect.isAffectedByLocalisation())) {
/* 165 */       mustBeLocalised = true;
/*     */     }
/* 167 */     if ((this.m_staticElement != Elements.PHYSICAL) && (mustBeLocalised)) {
/* 168 */       Part part = null;
/* 169 */       if (this.m_targetCell != null) {
/* 170 */         part = this.m_target.getPartLocalisator().getMainPartInSightFromVector(new Vector3(this.m_caster.getPosition(), this.m_targetCell));
/*     */       } else {
/* 172 */         part = this.m_target.getPartLocalisator().getMainPartInSightFromPosition(this.m_caster.getPosition());
/*     */       }
/* 174 */       if (part != null) {
/* 175 */         switch (part.getPartId()) {
/*     */         case 2: 
/* 177 */           modificator += 30;
/*     */           
/* 179 */           break;
/*     */         case 1: 
/*     */         case 3: 
/* 182 */           modificator += 15;
/* 183 */           break;
/*     */         
/*     */ 
/*     */         }
/*     */         
/*     */       } else {
/* 189 */         m_logger.error("Impossible de récupérer un partLocalisation...");
/*     */       }
/*     */     }
/*     */     
/* 193 */     switch (this.m_staticElement)
/*     */     {
/*     */     case EARTH: 
/*     */       break;
/*     */     
/*     */ 
/*     */     case FIRE: 
/* 200 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_IN_PERCENT)) {
/* 201 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_IN_PERCENT);
/*     */       }
/* 203 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_FIRE_PERCENT)) {
/* 204 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_FIRE_PERCENT);
/*     */       }
/* 206 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_IN_PERCENT)) {
/* 207 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_IN_PERCENT);
/*     */       }
/* 209 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_FIRE_PERCENT)) {
/* 210 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_FIRE_PERCENT);
/*     */       }
/* 212 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG)) {
/* 213 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG);
/*     */       }
/* 215 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_FIRE)) {
/* 216 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_FIRE);
/*     */       }
/* 218 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_FIRE)) {
/* 219 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_FIRE);
/*     */       }
/* 221 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES)) {
/* 222 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES);
/*     */       }
/* 224 */       break;
/*     */     case WIND: 
/* 226 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_IN_PERCENT)) {
/* 227 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_IN_PERCENT);
/*     */       }
/* 229 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_EARTH_PERCENT)) {
/* 230 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_EARTH_PERCENT);
/*     */       }
/* 232 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_IN_PERCENT)) {
/* 233 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_IN_PERCENT);
/*     */       }
/* 235 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_EARTH_PERCENT)) {
/* 236 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_EARTH_PERCENT);
/*     */       }
/* 238 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG)) {
/* 239 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG);
/*     */       }
/* 241 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_EARTH)) {
/* 242 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_EARTH);
/*     */       }
/* 244 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_EARTH)) {
/* 245 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_EARTH);
/*     */       }
/* 247 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES)) {
/* 248 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES);
/*     */       }
/*     */       
/* 251 */       break;
/*     */     case PHYSICAL: 
/* 253 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_IN_PERCENT)) {
/* 254 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_IN_PERCENT);
/*     */       }
/* 256 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_WATER_PERCENT)) {
/* 257 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_WATER_PERCENT);
/*     */       }
/* 259 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_IN_PERCENT)) {
/* 260 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_IN_PERCENT);
/*     */       }
/* 262 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_WATER_PERCENT)) {
/* 263 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_WATER_PERCENT);
/*     */       }
/* 265 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG)) {
/* 266 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG);
/*     */       }
/* 268 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_WATER)) {
/* 269 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_WATER);
/*     */       }
/* 271 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_WATER)) {
/* 272 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_WATER);
/*     */       }
/* 274 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES)) {
/* 275 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES);
/*     */       }
/*     */       
/* 278 */       break;
/*     */     case WATER: 
/* 280 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_IN_PERCENT)) {
/* 281 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_IN_PERCENT);
/*     */       }
/* 283 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_WIND_PERCENT)) {
/* 284 */         modificator += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_WIND_PERCENT);
/*     */       }
/* 286 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_IN_PERCENT)) {
/* 287 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_IN_PERCENT);
/*     */       }
/* 289 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_WIND_PERCENT)) {
/* 290 */         modificator -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_WIND_PERCENT);
/*     */       }
/* 292 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG)) {
/* 293 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG);
/*     */       }
/* 295 */       if (this.m_caster.hasCharacteristic(FighterCharacteristicType.DMG_WIND)) {
/* 296 */         value += this.m_caster.getCharacteristicValue(FighterCharacteristicType.DMG_WIND);
/*     */       }
/* 298 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES_WIND)) {
/* 299 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES_WIND);
/*     */       }
/* 301 */       if (this.m_target.hasCharacteristic(FighterCharacteristicType.RES)) {
/* 302 */         value -= this.m_target.getCharacteristicValue(FighterCharacteristicType.RES);
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/* 308 */     value = value * (100 + modificator) / 100.0F;
/* 309 */     this.m_value = Math.max(0, ValueRounder.randomRound(value));
/*     */     
/*     */ 
/* 312 */     if (this.m_target.hasCharacteristic(FighterCharacteristicType.DMG_REBOUND))
/*     */     {
/* 314 */       int reboundRES = this.m_target.getCharacteristicValue(FighterCharacteristicType.DMG_REBOUND);
/* 315 */       int rebound = ValueRounder.randomRound(this.m_value * reboundRES / 100.0F);
/*     */       
/* 317 */       if (rebound > 0) {
/* 318 */         if ((this.m_caster != null) && (this.m_caster != this.m_target)) {
/* 319 */           RunningEffect reboundRE = newInstance();
/* 320 */           reboundRE.forceValue(rebound);
/* 321 */           reboundRE.disableValueComputation();
/* 322 */           reboundRE.applyOnTargets(new EffectUser[] { this.m_caster });
/* 323 */           reboundRE.release();
/*     */         }
/* 325 */         this.m_value -= rebound;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean useCaster()
/*     */   {
/* 334 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 338 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 342 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\HPLoss.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */