/*     */ package com.ankamagames.dofusarena.client.core.action;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectManager;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.ParticleSystemFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.SoundFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.VisualEffectFunctionsLibrary;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*     */ import com.ankamagames.dofusarena.client.core.script.SpellEffectActionFunctionsLibrary;
/*     */ import com.ankamagames.dofusarena.common.game.effect.ArenaEffectContext;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectConstants;
/*     */ import com.ankamagames.dofusarena.common.game.effect.RunningEffectDefinition;
/*     */ import com.ankamagames.framework.script.action.ScriptedAction;
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
/*     */ public class SpellEffectAction
/*     */   extends ScriptedAction
/*     */ {
/*     */   private RunningEffect m_effect;
/*     */   private int m_effectValue;
/*     */   private boolean m_isTriggered;
/*     */   
/*     */   public SpellEffectAction(int uniqueId, int actionType, int actionId, RunningEffect effect, boolean isTriggered)
/*     */   {
/*  41 */     super(uniqueId, actionType, actionId);
/*     */     
/*  43 */     this.m_effect = effect;
/*  44 */     this.m_isTriggered = isTriggered;
/*     */     
/*  46 */     if (this.m_effect != null) {
/*  47 */       this.m_effectValue = effect.getValue();
/*     */     }
/*     */     
/*  50 */     addJavaFunctionsLibrary(MobileFunctionsLibrary.getInstance());
/*  51 */     addJavaFunctionsLibrary(ParticleSystemFunctionsLibrary.getInstance());
/*  52 */     addJavaFunctionsLibrary(SoundFunctionsLibrary.getInstance());
/*  53 */     addJavaFunctionsLibrary(VisualEffectFunctionsLibrary.getInstance());
/*  54 */     addJavaFunctionsLibrary(new SpellEffectActionFunctionsLibrary(this));
/*     */     
/*  56 */     setScriptFileId(getScriptIdFromEffectId(actionId));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onActionFinished()
/*     */   {
/*  68 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/*  69 */     if ((fight != null) && (this.m_effect != null)) {
/*  70 */       this.m_effect.setContext(ArenaEffectContext.checkOut(fight));
/*  71 */       if (this.m_isTriggered) {
/*  72 */         this.m_effect.execute(null, true);
/*     */       } else {
/*  74 */         if ((this.m_effect.hasDuration()) && 
/*  75 */           (this.m_effect.getTarget() != null) && (this.m_effect.getTarget().getRunningEffectManager() != null)) {
/*  76 */           if (this.m_effect.mustBeStacked()) {
/*  77 */             this.m_effect.getTarget().getRunningEffectManager().stackEffect(this.m_effect);
/*     */           } else {
/*  79 */             this.m_effect.getTarget().getRunningEffectManager().storeEffect(this.m_effect);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  86 */         if ((this.m_effect.hasDuration()) && (!this.m_effect.isInfinite())) {
/*  87 */           this.m_effect.pushRunningEffectDurationTimeEventInTimeline();
/*     */         }
/*  89 */         if (!this.m_effect.mustBeTriggered()) {
/*  90 */           this.m_effect.execute(null, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int getScriptIdFromEffectId(int effectId)
/*     */   {
/* 103 */     RunningEffectDefinition definition = (RunningEffectDefinition)RunningEffectConstants.getInstance().getConstantDefinition(effectId);
/*     */     
/* 105 */     if (definition == null) {
/* 106 */       return -1;
/*     */     }
/* 108 */     return definition.getScriptId();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public RunningEffect getEffect()
/*     */   {
/* 115 */     return this.m_effect;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getEffectValue()
/*     */   {
/* 122 */     return this.m_effectValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setEffectValue(int effectValue)
/*     */   {
/* 129 */     this.m_effectValue = effectValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\SpellEffectAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */