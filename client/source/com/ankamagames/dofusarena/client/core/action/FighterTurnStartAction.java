/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.fight.BasicFighter;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
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
/*    */ public class FighterTurnStartAction
/*    */   extends Action
/*    */ {
/*    */   public FighterTurnStartAction(int uniqueId, int actionType, int actionId) {
/* 24 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 34 */       Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId());
/* 35 */       if (fighter != null) {
/* 36 */         DofusArenaGameEntity.getInstance().getFight().askForFighterTurnBegin((BasicFighter)fighter);
/*    */       } else {
/* 38 */         m_logger.error("Début de tour demandé pour un fighter inexistant ??");
/*    */       } 
/* 40 */     } catch (Exception e) {
/* 41 */       m_logger.error("Error : ", e);
/*    */     } 
/*    */     
/* 44 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\FighterTurnStartAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */