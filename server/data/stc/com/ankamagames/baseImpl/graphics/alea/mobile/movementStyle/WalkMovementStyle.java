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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WalkMovementStyle
/*    */   implements PathMovementStyle
/*    */ {
/*    */   private static final int WALK_CELL_SPEED = 450;
/*    */   private static final int JUMP_IMPULSION_FACTOR = 3;
/*    */   private static final String ANIMATION_START_JUMP = "AnimSaut";
/*    */   private static final String ANIMATION_END_JUMP = "AnimSautFin";
/*    */   protected static final String ANIMATION_MOVE = "AnimMarche";
/* 29 */   private static double JUMP_DESCENDING_PHASE_CELL_POSITION = 0.8D;
/*    */   protected StyleMobile m_movementActor;
/*    */   
/*    */   public void setMobile(StyleMobile mobile)
/*    */   {
/* 34 */     this.m_movementActor = mobile;
/*    */   }
/*    */   
/*    */   public int getCellSpeed() {
/* 38 */     return 450;
/*    */   }
/*    */   
/*    */   public int getAirImpulsion() {
/* 42 */     return 3;
/*    */   }
/*    */   
/*    */   public void onStandingOnLastCell() {
/* 46 */     if (!this.m_movementActor.getAnimation().equals("AnimSautFin"))
/* 47 */       this.m_movementActor.setAnimation(this.m_movementActor.getStaticAnimationKey());
/*    */   }
/*    */   
/*    */   public void onMovingOnAir(double cellPositionPercent) {
/* 51 */     if (cellPositionPercent > JUMP_DESCENDING_PHASE_CELL_POSITION) {
/* 52 */       this.m_movementActor.setAnimation("AnimSautFin");
/*    */     } else {
/* 54 */       this.m_movementActor.setAnimation("AnimSaut");
/*    */     }
/*    */   }
/*    */   
/*    */   public void onMovingOnGround(int remainPathLength) {
/* 59 */     this.m_movementActor.setAnimation("AnimMarche");
/*    */     
/* 61 */     if (remainPathLength > 3) {
/* 62 */       this.m_movementActor.setMovementStyle(MovementStyleManager.RUN_STYLE);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onWaiting() {}
/*    */   
/*    */   public void onDirectionChanged(Direction8 newDirection) {
/* 69 */     this.m_movementActor.setDirection(newDirection);
/*    */   }
/*    */   
/*    */   public boolean createPathOnSetPosition() {
/* 73 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isAirImpulsionNeeded(int z) {
/* 77 */     return Math.abs(z) > 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\WalkMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */