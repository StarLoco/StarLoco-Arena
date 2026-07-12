/*    */ package com.ankamagames.dofusarena.client.alea.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.StyleMobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.PathMovementStyle;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThrowMovementStyle
/*    */   implements PathMovementStyle
/*    */ {
/*    */   public static final String NAME = "Throw";
/* 16 */   private static int THROW_CELL_SPEED = 60;
/*    */   
/* 18 */   private int m_distance = 0;
/*    */   
/*    */   private StyleMobile m_movementActor;
/*    */   
/*    */   public void setMobile(StyleMobile actor) {
/* 23 */     this.m_movementActor = actor;
/*    */   }
/*    */   
/*    */   public int getCellSpeed() {
/* 27 */     return (this.m_distance > 1) ? THROW_CELL_SPEED : 300;
/*    */   }
/*    */   
/*    */   public int getAirImpulsion() {
/* 31 */     return this.m_distance + 1;
/*    */   }
/*    */   
/*    */   public void onStandingOnLastCell() {
/* 35 */     this.m_movementActor.setMovementStyle(MovementStyleManager.WALK_STYLE);
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
/*    */   
/*    */   public void onDirectionChanged(Direction8 newDirection) {}
/*    */   
/*    */   public boolean createPathOnSetPosition() {
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isAirImpulsionNeeded(int dz) {
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   public void setDistance(int distance) {
/* 59 */     this.m_distance = distance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\mobile\ThrowMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */