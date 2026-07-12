/*    */ package com.ankamagames.dofusarena.common.game.filter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.filter.Filter;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TimeEvent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeInterval;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeUnit;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.turnBasedTimeEvent.FighterTurnTimeEvent;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterTurnTimeEventFilter
/*    */   extends Filter<TimeEvent<TurnBasedTimeUnit, TurnBasedTimeInterval>>
/*    */ {
/*    */   private AbstractFighter m_fighter;
/*    */   
/*    */   public FighterTurnTimeEventFilter(AbstractFighter fighter)
/*    */   {
/* 22 */     this.m_fighter = fighter;
/*    */   }
/*    */   
/*    */   public boolean isValid(TimeEvent timeEvent)
/*    */   {
/* 27 */     switch (timeEvent.getType()) {
/*    */     case 107: 
/* 29 */       FighterTurnTimeEvent wte = (FighterTurnTimeEvent)timeEvent;
/* 30 */       if (wte.getTurnBasedFighter() == this.m_fighter) {
/* 31 */         return true;
/*    */       }
/*    */       
/*    */ 
/*    */       break;
/*    */     default: 
/* 37 */       return false;
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\filter\FighterTurnTimeEventFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */