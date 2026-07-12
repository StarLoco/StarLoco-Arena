/*     */ package com.ankamagames.dofusarena.common.game.effect.runningEffect;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.State;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.StateManager;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ApplyState extends ArenaRunningEffect
/*     */ {
/*  22 */   private static final ObjectPool m_staticPool = new com.ankamagames.framework.kernel.core.common.MonitoredPool(new ObjectFactory() {
/*     */     public ApplyState makeObject() {
/*  24 */       return new ApplyState();
/*     */     }
/*  22 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ApplyState checkOut(EffectContext context, int look, EffectUser target, Effect genericEffect, EffectUser caster, EffectContainer effectContainer)
/*     */   {
/*     */     ApplyState re;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  34 */       ApplyState re = (ApplyState)m_staticPool.borrowObject();
/*  35 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  38 */       re = new ApplyState();
/*  39 */       re.m_pool = null;
/*  40 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*     */     
/*  43 */     re.m_id = RunningEffectConstants.STATE_APPLY.getId();
/*  44 */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.STATE_APPLY.getObject()).getRunningEffectStatus();
/*  45 */     re.setTriggersToExecute();
/*  46 */     re.m_target = target;
/*  47 */     re.m_caster = caster;
/*  48 */     re.m_value = look;
/*  49 */     re.m_effectContainer = effectContainer;
/*  50 */     re.m_maxExecutionCount = -1;
/*  51 */     re.m_context = context;
/*  52 */     re.m_genericEffect = genericEffect;
/*  53 */     re.m_stateRunningEffects.clear();
/*  54 */     re.m_endTriggers.clear();
/*  55 */     return re;
/*     */   }
/*     */   
/*     */   public ApplyState newInstance()
/*     */   {
/*     */     ApplyState re;
/*     */     try {
/*  62 */       ApplyState re = (ApplyState)m_staticPool.borrowObject();
/*  63 */       re.m_pool = m_staticPool;
/*     */     }
/*     */     catch (Exception e) {
/*  66 */       re = new ApplyState();
/*  67 */       re.m_pool = null;
/*  68 */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     }
/*  70 */     re.m_stateRunningEffects.clear();
/*  71 */     this.m_endTriggers.clear();
/*  72 */     re.cloneParameters(this);
/*  73 */     return re;
/*     */   }
/*     */   
/*  76 */   private final ArrayList<RunningEffect> m_stateRunningEffects = new ArrayList();
/*  77 */   private final BitSet m_endTriggers = new BitSet();
/*     */   
/*     */   public void execute(RunningEffect linkedRE, boolean trigger)
/*     */   {
/*  81 */     if (this.m_value > 0) {
/*  82 */       if ((this.m_target instanceof AbstractFighter)) {
/*  83 */         State state = StateManager.getInstance().getState(this.m_value);
/*  84 */         if (state != null) {
/*  85 */           for (Effect effect : state) {
/*  86 */             StaticRunningEffect sre = (StaticRunningEffect)RunningEffectConstants.getInstance().getObjectFromId(effect.getActionId());
/*  87 */             if (sre != null) {
/*  88 */               RunningEffect re = sre.newParameterizedInstance(effect, state, this.m_context, this.m_caster, this.m_target, this.m_targetCell);
/*  89 */               this.m_stateRunningEffects.add(re);
/*  90 */               re.addReference();
/*  91 */               re.applyOnTargets(new EffectUser[] { this.m_target });
/*     */             }
/*     */           }
/*  94 */           ((AbstractFighter)this.m_target).onStateApplied(state);
/*     */         } else {
/*  96 */           m_logger.error("State inconnu puor le client, mais vraisemblablement pas pour le serveur");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 101 */       super.execute(linkedRE, trigger);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unapply()
/*     */   {
/* 107 */     for (RunningEffect re : this.m_stateRunningEffects) {
/* 108 */       re.removeReference();
/* 109 */       if (!re.hasBeenUnnaplied()) {
/* 110 */         re.askForUnapplication();
/*     */       } else
/* 112 */         re.release();
/*     */     }
/* 114 */     this.m_stateRunningEffects.clear();
/* 115 */     State state = StateManager.getInstance().getState(this.m_value);
/* 116 */     if ((this.m_target instanceof AbstractFighter)) {
/* 117 */       ((AbstractFighter)this.m_target).onStateUnapplied(state);
/*     */     }
/* 119 */     super.unapply();
/*     */   }
/*     */   
/*     */   public void computeValue(RunningEffect triggerRE)
/*     */   {
/* 124 */     switch (this.m_genericEffect.getParams().length) {
/*     */     case 2: 
/* 126 */       short baseId = (short)(int)this.m_genericEffect.getParams()[0];
/* 127 */       byte level = (byte)(int)this.m_genericEffect.getParams()[1];
/* 128 */       State state = StateManager.getInstance().getState(baseId, level);
/* 129 */       if (state == null) {
/* 130 */         m_logger.error("aucun état associés aux paramètres, id:" + baseId + " level:" + level);
/* 131 */         this.m_value = -1;
/*     */       } else {
/* 133 */         this.m_value = state.getUniqueId();
/*     */         
/* 135 */         this.m_endTriggers.clear();
/* 136 */         this.m_endTriggers.or(super.getDeactivatedTriggersListening());
/* 137 */         this.m_endTriggers.or(state.getEndTriggers());
/*     */       }
/*     */       
/* 140 */       break;
/*     */     default: 
/* 142 */       m_logger.error("Nombre de paramètres incorrect dans un ApplyState : " + this.m_genericEffect.getParams().length);
/* 143 */       this.m_value = -1;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public BitSet getDeactivatedTriggersListening()
/*     */   {
/* 151 */     return this.m_endTriggers;
/*     */   }
/*     */   
/*     */   public boolean useCaster() {
/* 155 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTarget() {
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   public boolean useTargetCell() {
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\ApplyState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */