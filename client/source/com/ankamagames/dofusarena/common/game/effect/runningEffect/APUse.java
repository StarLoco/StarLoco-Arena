/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ public class APUse
/*     */   extends DynamicallyDefinedArenaRunningEffect
/*     */ {
/*  20 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<APUse>() {
/*     */         public APUse makeObject() {
/*  22 */           return new APUse();
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   public APUse() {
/*  28 */     setTriggersToExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public APUse newInstance() {
/*     */     APUse re;
/*     */     try {
/*  40 */       re = (APUse)m_staticPool.borrowObject();
/*  41 */       re.m_pool = m_staticPool;
/*     */     }
/*  43 */     catch (Exception e) {
/*  44 */       re = new APUse();
/*  45 */       re.m_pool = null;
/*  46 */       m_logger.error("Erreur lors d'un newInstance sur un APUse : " + e.getMessage());
/*     */     } 
/*  48 */     re.cloneParameters(this);
/*  49 */     return re;
/*     */   }
/*     */   
/*     */   public static APUse checkOut(EffectContext context, int apUsed, EffectUser target) {
/*     */     APUse re;
/*     */     try {
/*  55 */       re = (APUse)m_staticPool.borrowObject();
/*  56 */       re.m_pool = m_staticPool;
/*     */     }
/*  58 */     catch (Exception e) {
/*  59 */       re = new APUse();
/*  60 */       re.m_pool = null;
/*  61 */       m_logger.error("Erreur lors d'un checkOut sur un APUse : " + e.getMessage());
/*     */     } 
/*  63 */     re.m_id = RunningEffectConstants.AP_USE.getId();
/*  64 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.AP_USE.getObject()).getRunningEffectStatus();
/*  65 */     re.setTriggersToExecute();
/*  66 */     re.m_target = target;
/*  67 */     re.m_value = apUsed;
/*  68 */     re.m_maxExecutionCount = -1;
/*  69 */     re.m_context = context;
/*  70 */     return re;
/*     */   }
/*     */   
/*     */   public void setTriggersToExecute() {
/*  74 */     super.setTriggersToExecute();
/*  75 */     this.m_triggers.set(55);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  81 */     if (this.m_target != null && this.m_target.hasCharacteristic((CharacteristicType)FighterCharacteristicType.AP)) {
/*  82 */       this.m_target.getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).substract(this.m_value);
/*  83 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {}
/*     */ 
/*     */   
/*     */   public boolean useCaster() {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 100 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\APUse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */