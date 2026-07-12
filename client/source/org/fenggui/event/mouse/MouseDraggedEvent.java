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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseDraggedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   private int displayX;
/*    */   private int displayY;
/* 36 */   private MouseButton mouseButton = MouseButton.LEFT;
/*    */ 
/*    */   
/*    */   public MouseDraggedEvent(IWidget source, int x, int y, MouseButton mouseButton) {
/* 40 */     super(source);
/* 41 */     this.displayX = x;
/* 42 */     this.displayY = y;
/* 43 */     this.mouseButton = mouseButton;
/*    */   }
/*    */   
/*    */   public MouseButton getButton() {
/* 47 */     return this.mouseButton;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 51 */     return this.displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 55 */     return this.displayY;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseDraggedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */