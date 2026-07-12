/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.script.action.Action;
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
/*    */ 
/*    */ public class FighterTurnEndAction
/*    */   extends Action
/*    */ {
/*    */   public FighterTurnEndAction(int uniqueId, int actionType, int actionId)
/*    */   {
/* 25 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 36 */       Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 37 */       if (fight != null)
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/* 42 */         Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */         
/*    */ 
/*    */ 
/* 46 */         if (fighter != null) {
/* 47 */           if (fighter.getId() == getInstigatorId()) {
/* 48 */             fight.askForFighterTurnEnd(fighter);
/*    */           } else {
/* 50 */             m_logger.info("fin de tour prématurée du client");
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 56 */       m_logger.error("Error : ", e);
/*    */     }
/*    */     
/* 59 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FighterTurnEndAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */