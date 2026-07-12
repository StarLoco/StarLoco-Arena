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
/*    */ public class SlideMovementStyle
/*    */   implements PathMovementStyle
/*    */ {
/* 17 */   private static int SLIDE_CELL_SPEED = 150;
/*    */   private StyleMobile m_movementActor;
/*    */   
/*    */   public void setMobile(StyleMobile actor)
/*    */   {
/* 22 */     this.m_movementActor = actor;
/*    */   }
/*    */   
/*    */   public int getCellSpeed() {
/* 26 */     return SLIDE_CELL_SPEED;
/*    */   }
/*    */   
/*    */   public int getAirImpulsion() {
/* 30 */     return 0;
/*    */   }
/*    */   
/*    */   public void onStandingOnLastCell() {
/* 34 */     this.m_movementActor.setMovementStyle(MovementStyleManager.WALK_STYLE);
/*    */   }
/*    */   
/*    */ 
/*    */   public void onMovingOnAir(double cellPositionPercent) {}
/*    */   
/*    */ 
/*    */   public void onMovingOnGround(int remainPathLength) {}
/*    */   
/*    */ 
/*    */   public void onWaiting() {}
/*    */   
/*    */   public void onDirectionChanged(Direction8 newDirection) {}
/*    */   
/*    */   public boolean createPathOnSetPosition()
/*    */   {
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isAirImpulsionNeeded(int dz) {
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\SlideMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */