/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.script.action.Action;
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
/*    */ public class FighterTurnEndAction
/*    */   extends Action
/*    */ {
/*    */   public FighterTurnEndAction(int uniqueId, int actionType, int actionId) {
/* 25 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 36 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 37 */       if (fight != null) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 42 */         Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */ 
/*    */ 
/*    */         
/* 46 */         if (fighter != null) {
/* 47 */           if (fighter.getId() == getInstigatorId()) {
/* 48 */             fight.askForFighterTurnEnd((BasicFighter)fighter);
/*    */           } else {
/* 50 */             m_logger.info("fin de tour prématurée du client");
/*    */           }
/*    */         
/*    */         }
/*    */       } 
/* 55 */     } catch (Exception e) {
/* 56 */       m_logger.error("Error : ", e);
/*    */     } 
/*    */     
/* 59 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FighterTurnEndAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */