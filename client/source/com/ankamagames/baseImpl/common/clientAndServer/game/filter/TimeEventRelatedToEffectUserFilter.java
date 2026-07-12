/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.filter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.FighterTurnTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.RunningEffectDurationTimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.StaticRunningEffectDelayedTimeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeEventRelatedToEffectUserFilter
/*    */   extends Filter<TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>>
/*    */ {
/*    */   private EffectUser m_effectUser;
/*    */   private boolean m_isPlaying;
/*    */   
/*    */   public TimeEventRelatedToEffectUserFilter(EffectUser fighter, boolean isPlaying) {
/* 26 */     this.m_effectUser = fighter;
/* 27 */     this.m_isPlaying = isPlaying; } public boolean isValid(TimeEvent timeEvent) {
/*    */     FighterTurnTimeEvent wte;
/*    */     StaticRunningEffectDelayedTimeEvent staticRunningEffectDelayedTimeEvent;
/*    */     RunningEffectDurationTimeEvent te;
/*    */     RunningEffect re;
/* 32 */     switch (timeEvent.getType()) {
/*    */       case 107:
/* 34 */         wte = (FighterTurnTimeEvent)timeEvent;
/* 35 */         if (wte.getTurnBasedFighter() == this.m_effectUser && !this.m_isPlaying) {
/* 36 */           return true;
/*    */         }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 60 */         return false;case 2: staticRunningEffectDelayedTimeEvent = (StaticRunningEffectDelayedTimeEvent)timeEvent; if (staticRunningEffectDelayedTimeEvent.getLauncher() == this.m_effectUser) return true;  return false;case 1: te = (RunningEffectDurationTimeEvent)timeEvent; re = te.getRunningEffect(); if (re != null) { if (re.getTarget() == this.m_effectUser) return true;  if (re.getCaster() == this.m_effectUser) return true;  }  return false;
/*    */     } 
/*    */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\filter\TimeEventRelatedToEffectUserFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */