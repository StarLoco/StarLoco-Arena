/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharacLeech
/*     */   extends CharacDebuff
/*     */ {
/*  18 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<CharacLeech>() {
/*     */         public CharacLeech makeObject() {
/*  20 */           return new CharacLeech(null);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private CharacLeech() {}
/*     */   
/*     */   public CharacLeech(FighterCharacteristicType charac) {
/*  28 */     super(charac);
/*  29 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */   
/*     */   public CharacLeech newInstance() {
/*     */     CharacLeech re;
/*     */     try {
/*  36 */       re = (CharacLeech)m_staticPool.borrowObject();
/*  37 */       re.m_pool = m_staticPool;
/*     */     }
/*  39 */     catch (Exception e) {
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
/*     */   
/*     */   public void setTriggersToExecute() {
/*  53 */     super.setTriggersToExecute();
/*  54 */     if (this.m_charac == null)
/*  55 */       return;  switch (this.m_charac) {
/*     */ 
/*     */       
/*     */       case null:
/*  59 */         this.m_triggers.set(56);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/* 125 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)this.m_charac) && this.m_caster != null && this.m_caster.hasCharacteristic((CharacteristicType)this.m_charac)) {
/* 126 */       int characLeech = Math.min(this.m_value, this.m_target.getCharacteristic((CharacteristicType)this.m_charac).value());
/* 127 */       this.m_caster.getCharacteristic((CharacteristicType)this.m_charac).add(characLeech);
/* 128 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCaster() {
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\CharacLeech.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */