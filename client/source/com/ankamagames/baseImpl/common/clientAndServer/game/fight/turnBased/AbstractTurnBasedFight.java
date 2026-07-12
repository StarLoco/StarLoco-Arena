/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.fight.turnBased;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFight;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.BasicTimeline;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.time.TurnBased.TurnBasedTimeline;
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
/*    */ public abstract class AbstractTurnBasedFight<F extends BasicFighter>
/*    */   extends BasicFight<F>
/*    */ {
/*    */   public TurnBasedTimeline<F> getTimeline() {
/* 22 */     return (TurnBasedTimeline<F>)this.m_timeline;
/*    */   }
/*    */   
/*    */   public void addFighterDuringFight(F fighter) {
/* 26 */     addFighter((BasicFighter)fighter, true);
/*    */     
/* 28 */     if (getTimeline() == null) {
/* 29 */       m_logger.error("Impossible d'invoquer le summoning " + fighter + " : aucune timeline disponible");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 34 */     if (!fighter.isDead()) {
/* 35 */       getTimeline().addFighter((BasicFighter)fighter, true, true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addFighterToTimeline(long fighterId, boolean withTimeEvent, boolean ordered) {
/* 41 */     BasicFighter basicFighter = getFighterById(fighterId);
/* 42 */     if (basicFighter != null) {
/* 43 */       getTimeline().addFighter(basicFighter, withTimeEvent, ordered);
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
/*    */   
/*    */   public void askForFighterTurnBegin(BasicFighter fighter) {
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
/*    */   
/*    */   public void askForFighterTurnEnd(BasicFighter fighter) {
/* 72 */     if (getTimeline() != null && getTimeline().isRunning()) {
/* 73 */       getTimeline().askForFighterEndTurn(fighter);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFighterStartTurn(F fighter) {
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
/*    */   
/*    */   public void onTableTurnBegin() {}
/*    */   
/*    */   public void onTableTurnEnd() {
/* 97 */     if (getFightersCount() > 0)
/* 98 */       pushNewTableTurnEvent(); 
/*    */   }
/*    */   
/*    */   public abstract void pushNewTableTurnEvent();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\turnBased\AbstractTurnBasedFight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */