/*     */ package com.ankamagames.dofusarena.common.game.effectArea;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeline;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.EffectAreaActivationTimeEvent;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
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
/*     */ public abstract class AbstractEffectArea
/*     */   extends BasicEffectArea
/*     */ {
/*     */   public static final int SHOW_TO_EVERYONE = 0;
/*     */   public static final int SHOW_TO_TEAM_MEMBER = 1;
/*     */   public static final int ONE_TIME_FOR_EVERYONE = 1;
/*     */   public static final int ONE_TIME_FOR_TEAM = 2;
/*     */   public static final int ONE_TIME_FOR_TARGET = 3;
/*     */   
/*     */   protected AbstractEffectArea() {}
/*     */   
/*     */   public AbstractEffectArea(int baseId, AreaOfEffect area, BitSet applicationtriggers, BitSet unapplicationtriggers, int maxExecutionCount, int targetsToShow, int[] deactivationDelay, int applicationCondition, TargetValidator applicationValidator, TargetValidator unapplicationValidator)
/*     */   {
/*  46 */     super(baseId, area, applicationtriggers, unapplicationtriggers, maxExecutionCount, targetsToShow, deactivationDelay, applicationCondition, applicationValidator, unapplicationValidator);
/*     */   }
/*     */   
/*     */   public boolean hasNoExecutionCount()
/*     */   {
/*  51 */     return (this.m_maxExecutionCount >= 63) || (this.m_maxExecutionCount < 0);
/*     */   }
/*     */   
/*     */   public boolean canBeApply(Target applicant)
/*     */   {
/*  56 */     switch (this.m_activateCondition)
/*     */     {
/*     */     case 1: 
/*  59 */       if (this.m_unactiveForTargets.size() != 0)
/*  60 */         return false;
/*  61 */       return true;
/*     */     
/*     */ 
/*     */     case 2: 
/*  65 */       if ((applicant != null) && ((applicant instanceof AbstractFighter))) {
/*  66 */         FightingTeam team = ((AbstractFighter)applicant).getTeam();
/*     */         
/*  68 */         for (Target target : this.m_unactiveForTargets) {
/*  69 */           if (((target instanceof AbstractFighter)) && 
/*  70 */             (((AbstractFighter)target).getTeam() == team)) {
/*  71 */             return false;
/*     */           }
/*     */         }
/*  74 */         return true;
/*     */       }
/*  76 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 3: 
/*  81 */       if (this.m_unactiveForTargets.contains(applicant))
/*  82 */         return false;
/*  83 */       return true;
/*     */     }
/*     */     
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasActivationDelay() {
/*  91 */     if (this.m_deactivationDelay == null)
/*  92 */       return false;
/*  93 */     if (this.m_deactivationDelay.length != 2) {
/*  94 */       return false;
/*     */     }
/*  96 */     if ((this.m_deactivationDelay[0] > 0) || (this.m_deactivationDelay[1] > 0)) {
/*  97 */       return true;
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void pushActivationEventForTargetInTimeline(Target applicant)
/*     */   {
/* 105 */     if ((this.m_context != null) && (this.m_context.getTimeline() != null)) {
/* 106 */       TurnBasedTimeline<AbstractFighter> timeline = (TurnBasedTimeline)this.m_context.getTimeline();
/* 107 */       TurnBasedTimeUnit tu = (TurnBasedTimeUnit)this.m_context.getTimeline().now();
/* 108 */       int tableturn = tu.getTableTurn() + this.m_deactivationDelay[0];
/* 109 */       int turn = this.m_deactivationDelay[1];
/* 110 */       EffectAreaActivationTimeEvent te = EffectAreaActivationTimeEvent.checkOut(tableturn, turn, timeline, this, applicant);
/* 111 */       timeline.addTimeEvent(te);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effectArea\AbstractEffectArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */