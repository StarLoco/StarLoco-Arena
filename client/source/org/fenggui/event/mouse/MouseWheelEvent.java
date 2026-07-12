/*    */ package org.fenggui.event.mouse;
/*    */ 
/*    */ import org.fenggui.IWidget;
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
/*    */ public class MouseWheelEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   private boolean up;
/*    */   private int rotation;
/*    */   private int displayX;
/*    */   private int displayY;
/*    */   
/*    */   public MouseWheelEvent(IWidget source, int displayX, int displayY, boolean up, int rotation) {
/* 33 */     super(source);
/* 34 */     this.up = up;
/* 35 */     this.rotation = rotation;
/* 36 */     this.displayX = displayX;
/* 37 */     this.displayY = displayY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean wheeledUp() {
/* 42 */     return this.up;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRotations() {
/* 47 */     return this.rotation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayX() {
/* 56 */     return this.displayX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayY() {
/* 65 */     return this.displayY;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseWheelEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */