/*    */ package com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.StyleMobile;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class RunMovementStyle
/*    */   implements PathMovementStyle
/*    */ {
/* 18 */   private static int WALK_CELL_SPEED = 300;
/* 19 */   private static int JUMP_IMPULSION_FACTOR = 3;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private static String ANIMATION_START_JUMP = "AnimSaut";
/* 25 */   private static String ANIMATION_END_JUMP = "AnimSautFin";
/* 26 */   private static String ANIMATION_MOVE = "AnimCourse";
/*    */   
/* 28 */   private static double JUMP_DESCENDING_PHASE_CELL_POSITION = 0.8D;
/*    */   
/*    */   private StyleMobile m_movementActor;
/*    */   
/*    */   public void setMobile(StyleMobile actor) {
/* 33 */     this.m_movementActor = actor;
/*    */   }
/*    */   
/*    */   public int getCellSpeed() {
/* 37 */     return WALK_CELL_SPEED;
/*    */   }
/*    */   
/*    */   public int getAirImpulsion() {
/* 41 */     return JUMP_IMPULSION_FACTOR;
/*    */   }
/*    */   
/*    */   public void onStandingOnLastCell() {
/* 45 */     if (!this.m_movementActor.getAnimation().equals(ANIMATION_END_JUMP)) {
/* 46 */       this.m_movementActor.setAnimation(this.m_movementActor.getStaticAnimationKey());
/*    */     }
/*    */   }
/*    */   
/*    */   public void onMovingOnAir(double cellPositionPercent) {
/* 51 */     if (cellPositionPercent > JUMP_DESCENDING_PHASE_CELL_POSITION) {
/* 52 */       this.m_movementActor.setAnimation(ANIMATION_END_JUMP);
/*    */     } else {
/* 54 */       this.m_movementActor.setAnimation(ANIMATION_START_JUMP);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onMovingOnGround(int remainPathLength) {
/* 59 */     this.m_movementActor.setAnimation(ANIMATION_MOVE);
/*    */   }
/*    */   
/*    */   public void onWaiting() {
/* 63 */     this.m_movementActor.setMovementStyle(MovementStyleManager.WALK_STYLE);
/*    */   }
/*    */   
/*    */   public void onDirectionChanged(Direction8 newDirection) {
/* 67 */     this.m_movementActor.setDirection(newDirection);
/*    */   }
/*    */   
/*    */   public boolean createPathOnSetPosition() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isAirImpulsionNeeded(int z) {
/* 75 */     return (Math.abs(z) > 1);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\RunMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */