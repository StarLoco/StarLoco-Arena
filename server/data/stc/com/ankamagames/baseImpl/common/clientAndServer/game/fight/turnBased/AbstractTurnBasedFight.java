/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.fight.turnBased;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeline;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public abstract class AbstractTurnBasedFight<F extends BasicFighter>
/*    */   extends BasicFight<F>
/*    */ {
/*    */   public TurnBasedTimeline<F> getTimeline()
/*    */   {
/* 22 */     return (TurnBasedTimeline)this.m_timeline;
/*    */   }
/*    */   
/*    */   public void addFighterDuringFight(F fighter) {
/* 26 */     addFighter(fighter, true);
/*    */     
/* 28 */     if (getTimeline() == null) {
/* 29 */       m_logger.error("Impossible d'invoquer le summoning " + fighter + " : aucune timeline disponible");
/* 30 */       return;
/*    */     }
/*    */     
/*    */ 
/* 34 */     if (!fighter.isDead()) {
/* 35 */       getTimeline().addFighter(fighter, true, true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addFighterToTimeline(long fighterId, boolean withTimeEvent, boolean ordered)
/*    */   {
/* 41 */     F fighter = getFighterById(fighterId);
/* 42 */     if (fighter != null) {
/* 43 */       getTimeline().addFighter(fighter, withTimeEvent, ordered);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void askForFighterTurnBegin(BasicFighter fighter)
/*    */   {
/* 57 */     getTimeline().askForFighterStartTurn(fighter);
/*    */   }
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
/*    */   public void askForFighterTurnEnd(BasicFighter fighter)
/*    */   {
/* 72 */     if ((getTimeline() != null) && (getTimeline().isRunning())) {
/* 73 */       getTimeline().askForFighterEndTurn(fighter);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract void pushNewTableTurnEvent();
/*    */   
/*    */   public void onFighterStartTurn(F fighter)
/*    */   {
/* 82 */     fighter.onSpecialFighterEvent(101);
/*    */   }
/*    */   
/*    */   public void onFighterEndTurn(F fighter) {
/* 86 */     fighter.onSpecialFighterEvent(102);
/*    */   }
/*    */   
/*    */   public void initializeNewTableTurn() {
/* 90 */     getTimeline().initFighterTurnForOneTableTurn();
/*    */   }
/*    */   
/*    */   public void onTableTurnBegin() {}
/*    */   
/*    */   public void onTableTurnEnd()
/*    */   {
/* 97 */     if (getFightersCount() > 0) {
/* 98 */       pushNewTableTurnEvent();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\turnBased\AbstractTurnBasedFight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */