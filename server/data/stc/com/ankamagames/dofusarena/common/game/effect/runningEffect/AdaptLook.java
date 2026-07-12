/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ValueRounder;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdaptLook
/*     */   extends ArenaRunningEffect
/*     */ {
/*  23 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*  24 */     public AdaptLook makeObject() { return new AdaptLook(); }
/*  23 */   });
/*     */   
/*     */   public static AdaptLook checkOut(EffectContext context, int look, EffectUser target, Effect genericEffect, EffectUser caster, EffectContainer effectContainer)
/*     */   {
/*     */     AdaptLook re;
/*     */     try
/*     */     {
/*  30 */       AdaptLook re = (AdaptLook)m_staticPool.borrowObject();
/*  31 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  34 */       re = new AdaptLook();
/*  35 */       re.m_pool = null;
/*  36 */       m_logger.error("Erreur lors d'un checkOut sur un AdaptLook : " + e.getMessage());
/*     */     }
/*     */     
/*  39 */     re.m_id = RunningEffectConstants.ADAPT_LOOK.getId();
/*  40 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.ADAPT_LOOK.getObject()).getRunningEffectStatus();
/*  41 */     re.setTriggersToExecute();
/*  42 */     re.m_caster = caster;
/*  43 */     re.m_target = target;
/*  44 */     re.m_value = look;
/*  45 */     re.m_maxExecutionCount = -1;
/*  46 */     re.m_effectContainer = effectContainer;
/*  47 */     re.m_context = context;
/*  48 */     re.m_genericEffect = genericEffect;
/*  49 */     return re;
/*     */   }
/*     */   
/*     */   public AdaptLook newInstance()
/*     */   {
/*     */     AdaptLook re;
/*     */     try
/*     */     {
/*  57 */       AdaptLook re = (AdaptLook)m_staticPool.borrowObject();
/*  58 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  62 */       re = new AdaptLook();
/*  63 */       re.m_pool = null;
/*  64 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*  66 */     re.cloneParameters(this);
/*  67 */     return re;
/*     */   }
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  72 */     if ((this.m_target instanceof AbstractFighter)) {
/*  73 */       ((AbstractFighter)this.m_target).adaptLook(this.m_value);
/*     */     }
/*     */     
/*  76 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */   
/*     */   public void unapply()
/*     */   {
/*  81 */     if ((this.m_target instanceof AbstractFighter)) {
/*  82 */       AbstractFighter fighter = (AbstractFighter)this.m_target;
/*  83 */       fighter.restoreLastLook();
/*     */     }
/*     */     
/*  86 */     super.unapply();
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/*  91 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/*  93 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*  94 */       break;
/*     */     default: 
/*  96 */       m_logger.error("Nombre de paramètres incorrect dans un AdaptLook : " + this.m_genericEffect.getParams().length);
/*  97 */       this.m_value = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 110 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\AdaptLook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */