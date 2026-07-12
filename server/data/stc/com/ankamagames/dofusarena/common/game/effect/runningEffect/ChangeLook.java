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
/*     */ public class ChangeLook
/*     */   extends ArenaRunningEffect
/*     */ {
/*  22 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*  23 */     public ChangeLook makeObject() { return new ChangeLook(); }
/*  22 */   });
/*     */   
/*     */   public static ChangeLook checkOut(EffectContext context, int look, EffectUser target, Effect genericEffect, EffectUser caster, EffectContainer effectContainer)
/*     */   {
/*     */     ChangeLook re;
/*     */     try
/*     */     {
/*  29 */       ChangeLook re = (ChangeLook)m_staticPool.borrowObject();
/*  30 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  33 */       re = new ChangeLook();
/*  34 */       re.m_pool = null;
/*  35 */       m_logger.error("Erreur lors d'un checkOut sur un ChangeLook : " + e.getMessage());
/*     */     }
/*     */     
/*  38 */     re.m_id = RunningEffectConstants.CHANGE_LOOK.getId();
/*  39 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.CHANGE_LOOK.getObject()).getRunningEffectStatus();
/*  40 */     re.setTriggersToExecute();
/*  41 */     re.m_target = target;
/*  42 */     re.m_caster = caster;
/*  43 */     re.m_value = look;
/*  44 */     re.m_effectContainer = effectContainer;
/*  45 */     re.m_maxExecutionCount = -1;
/*  46 */     re.m_context = context;
/*  47 */     re.m_genericEffect = genericEffect;
/*  48 */     return re;
/*     */   }
/*     */   
/*     */   public ChangeLook newInstance()
/*     */   {
/*     */     ChangeLook re;
/*     */     try
/*     */     {
/*  56 */       ChangeLook re = (ChangeLook)m_staticPool.borrowObject();
/*  57 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  61 */       re = new ChangeLook();
/*  62 */       re.m_pool = null;
/*  63 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*  65 */     re.cloneParameters(this);
/*  66 */     return re;
/*     */   }
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  71 */     if ((this.m_target instanceof AbstractFighter)) {
/*  72 */       ((AbstractFighter)this.m_target).changeLook(this.m_value);
/*     */     }
/*     */     
/*  75 */     super.execute(linkedRE, trigger);
/*     */   }
/*     */   
/*     */   public void unapply()
/*     */   {
/*  80 */     if ((this.m_target instanceof AbstractFighter)) {
/*  81 */       AbstractFighter fighter = (AbstractFighter)this.m_target;
/*  82 */       fighter.restoreLastLook();
/*     */     }
/*     */     
/*  85 */     super.unapply();
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/*  90 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 1: 
/*  92 */       this.m_value = ValueRounder.randomRound(this.m_genericEffect.getParams()[0]);
/*  93 */       break;
/*     */     default: 
/*  95 */       m_logger.error("Nombre de paramètres incorrect dans un ChangeLook : " + this.m_genericEffect.getParams().length);
/*  96 */       this.m_value = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\ChangeLook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */