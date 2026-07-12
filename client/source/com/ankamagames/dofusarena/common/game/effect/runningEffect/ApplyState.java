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
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ public class ApplyState extends ArenaRunningEffect {
/*  22 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ApplyState>() {
/*     */         public ApplyState makeObject() {
/*  24 */           return new ApplyState();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArrayList<RunningEffect> m_stateRunningEffects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BitSet m_endTriggers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ApplyState checkOut(EffectContext context, int look, EffectUser target, Effect genericEffect, EffectUser caster, EffectContainer effectContainer) {
/*     */     ApplyState re;
/*     */     try {
/*     */       re = (ApplyState)m_staticPool.borrowObject();
/*     */       re.m_pool = m_staticPool;
/*     */     } catch (Exception e) {
/*     */       re = new ApplyState();
/*     */       re.m_pool = null;
/*     */       m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage());
/*     */     } 
/*     */     re.m_id = RunningEffectConstants.STATE_APPLY.getId();
/*     */     re.m_status = ((StaticRunningEffect)RunningEffectConstants.STATE_APPLY.getObject()).getRunningEffectStatus();
/*     */     re.setTriggersToExecute();
/*     */     re.m_target = target;
/*     */     re.m_caster = caster;
/*     */     re.m_value = look;
/*     */     re.m_effectContainer = effectContainer;
/*     */     re.m_maxExecutionCount = -1;
/*     */     re.m_context = context;
/*     */     re.m_genericEffect = genericEffect;
/*     */     re.m_stateRunningEffects.clear();
/*     */     re.m_endTriggers.clear();
/*     */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplyState() {
/*  76 */     this.m_stateRunningEffects = new ArrayList<RunningEffect>();
/*  77 */     this.m_endTriggers = new BitSet();
/*     */   } public ApplyState newInstance() { ApplyState re; try { re = (ApplyState)m_staticPool.borrowObject(); re.m_pool = m_staticPool; }
/*     */     catch (Exception e)
/*     */     { re = new ApplyState(); re.m_pool = null; m_logger.error("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + e.getMessage()); }
/*  81 */      re.m_stateRunningEffects.clear(); this.m_endTriggers.clear(); re.cloneParameters(this); return re; } public void execute(RunningEffect linkedRE, boolean trigger) { if (this.m_value > 0) {
/*  82 */       if (this.m_target instanceof AbstractFighter) {
/*  83 */         State state = StateManager.getInstance().getState(this.m_value);
/*  84 */         if (state != null) {
/*  85 */           for (Effect effect : state) {
/*  86 */             StaticRunningEffect sre = (StaticRunningEffect)RunningEffectConstants.getInstance().getObjectFromId(effect.getActionId());
/*  87 */             if (sre != null) {
/*  88 */               RunningEffect re = sre.newParameterizedInstance(effect, (EffectContainer)state, this.m_context, this.m_caster, this.m_target, this.m_targetCell);
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
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unapply() {
/* 107 */     for (RunningEffect re : this.m_stateRunningEffects) {
/* 108 */       re.removeReference();
/* 109 */       if (!re.hasBeenUnnaplied()) {
/* 110 */         re.askForUnapplication(); continue;
/*     */       } 
/* 112 */       re.release();
/*     */     } 
/* 114 */     this.m_stateRunningEffects.clear();
/* 115 */     State state = StateManager.getInstance().getState(this.m_value);
/* 116 */     if (this.m_target instanceof AbstractFighter) {
/* 117 */       ((AbstractFighter)this.m_target).onStateUnapplied(state);
/*     */     }
/* 119 */     super.unapply();
/*     */   } public void computeValue(RunningEffect triggerRE) {
/*     */     short baseId;
/*     */     byte level;
/*     */     State state;
/* 124 */     switch ((this.m_genericEffect.getParams()).length) {
/*     */       case 2:
/* 126 */         baseId = (short)(int)this.m_genericEffect.getParams()[0];
/* 127 */         level = (byte)(int)this.m_genericEffect.getParams()[1];
/* 128 */         state = StateManager.getInstance().getState(baseId, level);
/* 129 */         if (state == null) {
/* 130 */           m_logger.error("aucun état associés aux paramètres, id:" + baseId + " level:" + level);
/* 131 */           this.m_value = -1;
/*     */         } else {
/* 133 */           this.m_value = state.getUniqueId();
/*     */           
/* 135 */           this.m_endTriggers.clear();
/* 136 */           this.m_endTriggers.or(super.getDeactivatedTriggersListening());
/* 137 */           this.m_endTriggers.or(state.getEndTriggers());
/*     */         } 
/*     */         return;
/*     */     } 
/*     */     
/* 142 */     m_logger.error("Nombre de paramètres incorrect dans un ApplyState : " + (this.m_genericEffect.getParams()).length);
/* 143 */     this.m_value = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitSet getDeactivatedTriggersListening() {
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\runningEffect\ApplyState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */