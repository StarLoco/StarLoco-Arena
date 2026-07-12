/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
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
/*     */ public class CharacDebuff
/*     */   extends ArenaRunningEffect
/*     */ {
/*  19 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CharacDebuff>() {
/*     */         public CharacDebuff makeObject() {
/*  21 */           return new CharacDebuff();
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   protected FighterCharacteristicType m_charac;
/*     */   
/*     */   protected boolean m_execute;
/*     */ 
/*     */   
/*     */   protected CharacDebuff() {}
/*     */   
/*     */   public CharacDebuff(FighterCharacteristicType charac) {
/*  34 */     this.m_execute = true;
/*  35 */     this.m_charac = charac;
/*  36 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharacDebuff newInstance() {
/*     */     CharacDebuff wre;
/*     */     try {
/*  44 */       wre = (CharacDebuff)m_staticPool.borrowObject();
/*  45 */       wre.m_pool = m_staticPool;
/*     */     }
/*  47 */     catch (Exception e) {
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
/*     */   
/*     */   public void setTriggersToExecute() {
/*  62 */     super.setTriggersToExecute();
/*  63 */     if (this.m_charac == null)
/*  64 */       return;  switch (this.m_charac) {
/*     */       case HP:
/*  66 */         this.m_triggers.set(4);
/*     */         break;
/*     */       
/*     */       case null:
/*  70 */         this.m_triggers.set(54);
/*     */         break;
/*     */       case MP:
/*  73 */         this.m_triggers.set(64);
/*     */         break;
/*     */       case INIT:
/*  76 */         this.m_triggers.set(172);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 138 */     if (this.m_execute && 
/* 139 */       this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 140 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).updateMaxValue(-this.m_value);
/* 141 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 148 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 150 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         break;
/*     */       case 3:
/* 153 */         this.m_value = DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2);
/*     */         break;
/*     */       default:
/* 156 */         m_logger.error("Nombre de paramètres incorrect dans un CharacDebuff : " + (this.m_genericEffect.getParams()).length);
/* 157 */         this.m_value = 0;
/*     */         break;
/*     */     } 
/* 160 */     this.m_execute = true;
/*     */     
/* 162 */     int res = 0;
/* 163 */     switch (this.m_charac) {
/*     */       case MP:
/* 165 */         if (this.m_target.hasCharacteristic((CharacteristicType)FighterCharacteristicType.RES_MP_LOSS)) {
/* 166 */           res = this.m_target.getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_MP_LOSS);
/*     */         }
/*     */         break;
/*     */       case null:
/* 170 */         if (this.m_target.hasCharacteristic((CharacteristicType)FighterCharacteristicType.RES_AP_LOSS)) {
/* 171 */           res = this.m_target.getCharacteristicValue((CharacteristicType)FighterCharacteristicType.RES_AP_LOSS);
/*     */         }
/*     */         break;
/*     */       default:
/* 175 */         res = 0;
/*     */         break;
/*     */     } 
/* 178 */     if (DiceRoll.roll(100) <= res) {
/* 179 */       this.m_execute = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unapply() {
/* 185 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 186 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).updateMaxValue(this.m_value);
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacDebuff.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */