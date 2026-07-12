/*     */ package com.ankamagames.dofusarena.client.core.game.effectArea;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContext;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.FightingTeam;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StaticEffectAreaDisplayer;
/*     */ import com.ankamagames.dofusarena.common.game.effectArea.AbstractEffectArea;
/*     */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.ai.targetfinder.TargetValidator;
/*     */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectArea
/*     */   extends AbstractEffectArea
/*     */ {
/*     */   private int m_scriptId;
/*     */   
/*     */   private EffectArea() {}
/*     */   
/*     */   public EffectArea(int id, AreaOfEffect area, BitSet applicationtriggers, BitSet unapplicationtriggers, int maxExecutionCount, int scriptId, int targetsToShow, int[] deactivationDelay, int applicationCondition, TargetValidator applicationValidator, TargetValidator unapplicationValidator)
/*     */   {
/*  44 */     super(id, area, applicationtriggers, unapplicationtriggers, maxExecutionCount, targetsToShow, deactivationDelay, applicationCondition, applicationValidator, unapplicationValidator);
/*     */     
/*  46 */     this.m_scriptId = scriptId;
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
/*     */   public EffectArea instanceAnother(long id, int x, int y, short z, EffectContext context, EffectUser owner)
/*     */   {
/*  59 */     EffectArea area = new EffectArea();
/*     */     
/*  61 */     area.setArea(this.m_area);
/*  62 */     area.m_applicationTriggers = this.m_applicationTriggers;
/*  63 */     area.m_unapplicationTriggers = this.m_unapplicationTriggers;
/*  64 */     area.m_scriptId = this.m_scriptId;
/*  65 */     area.m_effects = this.m_effects;
/*  66 */     area.m_baseId = this.m_baseId;
/*  67 */     area.m_maxExecutionCount = this.m_maxExecutionCount;
/*  68 */     area.m_deactivationDelay = this.m_deactivationDelay;
/*  69 */     area.m_targetsToShow = this.m_targetsToShow;
/*  70 */     area.m_activateCondition = this.m_activateCondition;
/*     */     
/*  72 */     area.m_id = id;
/*  73 */     area.setPosition(x, y, z);
/*  74 */     area.m_context = context;
/*  75 */     area.m_owner = owner;
/*     */     
/*  77 */     return area;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getScriptId()
/*     */   {
/*  85 */     return this.m_scriptId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onDeactivation(Target applicant)
/*     */   {
/*  95 */     super.onDeactivation(applicant);
/*  96 */     if ((applicant != null) && ((applicant instanceof AbstractFighter))) {
/*  97 */       FightingTeam team = ((AbstractFighter)applicant).getTeam();
/*  98 */       StaticEffectAreaDisplayer.getInstance().markElementUse(team.getId(), getPosition(), DofusArenaClientInstance.getInstance().getWorldScene());
/*     */     } else {
/* 100 */       StaticEffectAreaDisplayer.getInstance().markElementUse(getPosition(), DofusArenaClientInstance.getInstance().getWorldScene());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onActivation(Target applicant)
/*     */   {
/* 111 */     super.onActivation(applicant);
/* 112 */     if ((applicant != null) && ((applicant instanceof AbstractFighter))) {
/* 113 */       FightingTeam team = ((AbstractFighter)applicant).getTeam();
/* 114 */       StaticEffectAreaDisplayer.getInstance().unmarkElement(team.getId(), getPosition(), DofusArenaClientInstance.getInstance().getWorldScene());
/*     */     } else {
/* 116 */       StaticEffectAreaDisplayer.getInstance().unmarkElement(getPosition(), DofusArenaClientInstance.getInstance().getWorldScene());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onDeath()
/*     */   {
/* 126 */     super.onDeath();
/* 127 */     StaticEffectAreaDisplayer.getInstance().unmarkElement(getPosition(), DofusArenaClientInstance.getInstance().getWorldScene());
/*     */   }
/*     */   
/*     */   public void execute(Target applicant) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\effectArea\EffectArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */