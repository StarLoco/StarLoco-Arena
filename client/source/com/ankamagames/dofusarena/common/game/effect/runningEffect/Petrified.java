/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterPropertyType;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*     */ public class Petrified
/*     */   extends ArenaRunningEffect
/*     */ {
/*  26 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Petrified>() {
/*     */         public Petrified makeObject() {
/*  28 */           return new Petrified();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_mustBeExecuted;
/*     */ 
/*     */ 
/*     */   
/*     */   public Petrified newInstance() {
/*     */     Petrified re;
/*     */     try {
/*  41 */       re = (Petrified)m_staticPool.borrowObject();
/*  42 */       re.m_pool = m_staticPool;
/*     */     }
/*  44 */     catch (Exception e) {
/*  45 */       re = new Petrified();
/*  46 */       re.m_pool = null;
/*  47 */       m_logger.error("Erreur lors d'un checkOut sur un Petrified : " + e.getMessage());
/*     */     } 
/*  49 */     re.cloneParameters(this);
/*  50 */     re.m_mustBeExecuted = true;
/*  51 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger) {
/*  56 */     if (this.m_mustBeExecuted) {
/*     */       
/*  58 */       if (this.m_target instanceof AbstractFighter) {
/*     */         
/*  60 */         if (this.m_valueComputationEnabled) {
/*  61 */           ArenaRunningEffect lookEffect; boolean bAdaptGfx = (this.m_genericEffect.getParam(2) == 1.0F);
/*     */           
/*  63 */           if (bAdaptGfx) {
/*  64 */             lookEffect = AdaptLook.checkOut(this.m_context, (int)this.m_genericEffect.getParam(1), this.m_target, this.m_genericEffect, this.m_caster, this.m_effectContainer);
/*     */           } else {
/*  66 */             lookEffect = ChangeLook.checkOut(this.m_context, (int)this.m_genericEffect.getParam(1), this.m_target, this.m_genericEffect, this.m_caster, this.m_effectContainer);
/*  67 */           }  lookEffect.disableValueComputation();
/*  68 */           lookEffect.applyOnTargets(new EffectUser[] { this.m_target });
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  73 */         notifyExecution(linkedRE, trigger);
/*  74 */         if (this.m_target instanceof AbstractFighter) {
/*  75 */           ((AbstractFighter)this.m_target).getProperties().add(FighterPropertyType.PETRIFIED);
/*     */         }
/*     */       } 
/*     */       
/*  79 */       super.execute(linkedRE, trigger);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/*  84 */     int chance = Math.min(100, (int)this.m_genericEffect.getParam(0));
/*  85 */     this.m_mustBeExecuted = (DiceRoll.roll(100) <= chance);
/*     */   }
/*     */   
/*     */   public void unapply() {
/*  89 */     if (this.m_target instanceof AbstractFighter) {
/*  90 */       ((AbstractFighter)this.m_target).getProperties().substract(FighterPropertyType.PETRIFIED);
/*     */     }
/*  92 */     super.unapply();
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\Petrified.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */