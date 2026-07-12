/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import com.ankamagames.framework.script.action.TimedAction;
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
/*    */ public class MoveAction
/*    */   extends TimedAction
/*    */ {
/*    */   private PathFindResult m_path;
/*    */   
/*    */   public MoveAction(int uniqueId, int actionType, int actionId, long targetId, PathFindResult path) {
/* 36 */     super(uniqueId, actionType, actionId);
/*    */     
/* 38 */     setTargetId(targetId);
/* 39 */     this.m_path = path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long onRun() {
/* 48 */     if (this.m_path != null) {
/*    */ 
/*    */       
/* 51 */       Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getTargetId());
/* 52 */       int[] cell = this.m_path.getFirstStep();
/* 53 */       Point3 here = new Point3(cell[0], cell[1], (short)cell[2]);
/* 54 */       if (fighter.isCarried()) {
/* 55 */         fighter.getCarriedByFighter().uncarry(here);
/* 56 */         fighter.getActor().setMovementStyle(MovementStyleManager.WALK_STYLE);
/*    */       } 
/*    */       
/* 59 */       FighterActor fighterActor = fighter.getActor();
/* 60 */       if (fighterActor != null) {
/* 61 */         fighterActor.setPath(this.m_path, true);
/*    */       }
/*    */       
/* 64 */       return (this.m_path.getPathLength() * 300);
/*    */     } 
/* 66 */     return 0L;
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\MoveAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */