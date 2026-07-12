/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.DiceRoll;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class SpellRebound
/*     */   extends ArenaRunningEffect
/*     */ {
/*  24 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*     */     public SpellRebound makeObject() {
/*  26 */       return new SpellRebound();
/*     */     }
/*  24 */   });
/*     */   
/*     */ 
/*     */ 
/*     */   private float m_executionRate;
/*     */   
/*     */ 
/*     */ 
/*     */   public float getExecutionRate()
/*     */   {
/*  34 */     return this.m_executionRate;
/*     */   }
/*     */   
/*     */   public SpellRebound newInstance()
/*     */   {
/*     */     SpellRebound re;
/*     */     try
/*     */     {
/*  42 */       SpellRebound re = (SpellRebound)m_staticPool.borrowObject();
/*  43 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  46 */       re = new SpellRebound();
/*  47 */       re.m_pool = null;
/*  48 */       m_logger.error("Erreur lors d'un checkOut sur un SpellRebound : " + e.getMessage());
/*     */     }
/*  50 */     re.cloneParameters(this);
/*  51 */     if ((this.m_genericEffect.getParams() != null) && (this.m_genericEffect.getParams().length == 1)) {
/*  52 */       re.m_executionRate = Math.min(99.0F, this.m_genericEffect.getParam(0));
/*     */     } else {
/*  54 */       re.m_executionRate = 99.0F;
/*     */     }
/*  56 */     return re;
/*     */   }
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  61 */     if (linkedRE != null)
/*     */     {
/*  63 */       if ((linkedRE.getEffectContainer() != null) && (linkedRE.getEffectContainer().getContainerType() == 13) && 
/*  64 */         (this.m_value > 0) && (
/*  65 */         (!linkedRE.useTarget()) || (linkedRE.getCaster() != null))) {
/*  66 */         linkedRE.setTarget(linkedRE.getCaster());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  71 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE) {
/*  75 */     this.m_value = (DiceRoll.roll(100) <= this.m_executionRate ? 1 : 0);
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/*  79 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/*  83 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public boolean mustBeStacked() {
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canBeStackedWith(RunningEffect reToStack) {
/*  95 */     return (reToStack.getId() == getId()) && (reToStack != this);
/*     */   }
/*     */   
/*     */   public void stackWith(RunningEffect reToStack) {
/*  99 */     super.stackWith(reToStack);
/* 100 */     SpellRebound re = (SpellRebound)reToStack;
/* 101 */     this.m_executionRate = Math.min(99.0F, this.m_executionRate + re.getExecutionRate());
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\SpellRebound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */