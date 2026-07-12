/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class ChangeDirectionAction
/*    */   extends Action
/*    */ {
/*    */   private final Direction8 m_direction;
/*    */   
/*    */   public ChangeDirectionAction(int uniqueId, int actionType, int actionId, Direction8 direction) {
/* 27 */     super(uniqueId, actionType, actionId);
/* 28 */     this.m_direction = direction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 39 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId());
/*    */     
/* 41 */     if (fighter != null) {
/* 42 */       fighter.setDirection((Direction)this.m_direction);
/*    */     }
/*    */     
/* 45 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\ChangeDirectionAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */