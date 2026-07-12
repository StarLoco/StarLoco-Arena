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
/*    */ public class MouseReleasedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   private int displayX;
/*    */   private int displayY;
/*    */   private int clickCount;
/*    */   private MouseButton button;
/*    */   
/*    */   public MouseReleasedEvent(IWidget source, int x, int y, MouseButton mouseButton, int clickCount) {
/* 38 */     super(source);
/* 39 */     this.button = mouseButton;
/* 40 */     this.displayX = x;
/* 41 */     this.displayY = y;
/* 42 */     this.clickCount = clickCount;
/*    */   }
/*    */   
/*    */   public MouseButton getButton() {
/* 46 */     return this.button;
/*    */   }
/*    */   
/*    */   public int getDisplayX() {
/* 50 */     return this.displayX;
/*    */   }
/*    */   
/*    */   public int getDisplayY() {
/* 54 */     return this.displayY;
/*    */   }
/*    */   
/*    */   public int getClickCount() {
/* 58 */     return this.clickCount;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseReleasedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */