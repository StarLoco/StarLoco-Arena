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
/*    */ public class TimeEventRelatedToEffectUserFilter
/*    */   extends Filter<TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>>
/*    */ {
/*    */   private EffectUser m_effectUser;
/*    */   private boolean m_isPlaying;
/*    */   
/*    */   public TimeEventRelatedToEffectUserFilter(EffectUser fighter, boolean isPlaying)
/*    */   {
/* 26 */     this.m_effectUser = fighter;
/* 27 */     this.m_isPlaying = isPlaying;
/*    */   }
/*    */   
/*    */   public boolean isValid(TimeEvent timeEvent)
/*    */   {
/* 32 */     switch (timeEvent.getType()) {
/*    */     case 107: 
/* 34 */       FighterTurnTimeEvent wte = (FighterTurnTimeEvent)timeEvent;
/* 35 */       if ((wte.getTurnBasedFighter() == this.m_effectUser) && (!this.m_isPlaying)) {
/* 36 */         return true;
/*    */       }
/*    */       
/*    */       break;
/*    */     case 2: 
/* 41 */       StaticRunningEffectDelayedTimeEvent te = (StaticRunningEffectDelayedTimeEvent)timeEvent;
/* 42 */       if (te.getLauncher() == this.m_effectUser) {
/* 43 */         return true;
/*    */       }
/*    */       break;
/*    */     case 1: 
/* 47 */       RunningEffectDurationTimeEvent te = (RunningEffectDurationTimeEvent)timeEvent;
/* 48 */       RunningEffect re = te.getRunningEffect();
/* 49 */       if (re != null) {
/* 50 */         if (re.getTarget() == this.m_effectUser)
/* 51 */           return true;
/* 52 */         if (re.getCaster() == this.m_effectUser) {
/* 53 */           return true;
/*    */         }
/*    */       }
/*    */       break;
/*    */     default: 
/* 58 */       return false;
/*    */     }
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\filter\TimeEventRelatedToEffectUserFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */