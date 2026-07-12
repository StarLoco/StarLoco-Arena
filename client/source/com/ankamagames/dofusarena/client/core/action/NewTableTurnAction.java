/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
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
/*    */ 
/*    */ public class NewTableTurnAction
/*    */   extends Action
/*    */ {
/*    */   private AbstractEvent m_eventToAdd;
/*    */   
/*    */   public NewTableTurnAction(int uniqueId, int actionType, int actionId) {
/* 26 */     super(uniqueId, actionType, actionId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEventToAdd(AbstractEvent eventToAdd) {
/* 31 */     this.m_eventToAdd = eventToAdd;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 41 */       DofusArenaGameEntity.getInstance().getFight().addEvent(this.m_eventToAdd);
/* 42 */       DofusArenaGameEntity.getInstance().getFight().getTimeline().askForStartTurn();
/* 43 */     } catch (Exception e) {
/* 44 */       m_logger.error("Error : ", e);
/*    */     } 
/*    */     
/* 47 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\NewTableTurnAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */