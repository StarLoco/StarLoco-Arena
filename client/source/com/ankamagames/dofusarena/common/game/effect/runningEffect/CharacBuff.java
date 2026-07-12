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
/*     */ public class CharacBuff
/*     */   extends ArenaRunningEffect
/*     */ {
/*  19 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CharacBuff>() {
/*     */         public CharacBuff makeObject() {
/*  21 */           return new CharacBuff(null);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private FighterCharacteristicType m_charac;
/*     */ 
/*     */   
/*     */   private CharacBuff() {}
/*     */   
/*     */   public CharacBuff(FighterCharacteristicType charac) {
/*  32 */     this.m_charac = charac;
/*  33 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharacBuff newInstance() {
/*     */     CharacBuff re;
/*     */     try {
/*  41 */       re = (CharacBuff)m_staticPool.borrowObject();
/*  42 */       re.m_pool = m_staticPool;
/*     */     }
/*  44 */     catch (Exception e) {
/*  45 */       re = new CharacBuff();
/*  46 */       re.m_pool = null;
/*  47 */       m_logger.error("Erreur lors d'un checkOut sur un CharacBuff : " + e.getMessage());
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
/*  62 */         this.m_triggers.set(3);
/*     */         break;
/*     */       
/*     */       case null:
/*  66 */         this.m_triggers.set(53);
/*     */         break;
/*     */       case MP:
/*  69 */         this.m_triggers.set(63);
/*     */         break;
/*     */       case INIT:
/*  72 */         this.m_triggers.set(171);
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
/* 134 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 135 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).updateMaxValue(this.m_value);
/* 136 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).add(this.m_value);
/* 137 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/* 143 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 1:
/* 145 */         this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*     */         return;
/*     */       case 3:
/* 148 */         this.m_value = DiceRoll.roll((int)this.m_genericEffect.getParam(0), (int)this.m_genericEffect.getParam(1)) + (int)this.m_genericEffect.getParam(2);
/*     */         return;
/*     */     } 
/* 151 */     m_logger.error("Nombre de paramètres incorrect dans un CharacBuff : " + (this.m_genericEffect.getParams()).length);
/* 152 */     this.m_value = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unapply() {
/* 158 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 159 */       this.m_target.getCharacteristic((CharacteristicType)this.m_charac).updateMaxValue(-this.m_value);
/*     */     }
/* 161 */     super.unapply();
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 173 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacBuff.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */