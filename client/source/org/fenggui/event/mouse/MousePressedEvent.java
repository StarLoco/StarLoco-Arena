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
/*    */ public class MousePressedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   private int displayX;
/*    */   private int displayY;
/*    */   private int clickCount;
/* 34 */   private MouseButton mouseButton = MouseButton.LEFT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MousePressedEvent(IWidget source, int mouseX, int mouseY, MouseButton mouseButton, int clickCount) {
/* 45 */     super(source);
/* 46 */     this.displayX = mouseX;
/* 47 */     this.displayY = mouseY;
/* 48 */     this.mouseButton = mouseButton;
/* 49 */     this.clickCount = clickCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MouseButton getButton() {
/* 57 */     return this.mouseButton;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayX() {
/* 65 */     return this.displayX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayY() {
/* 73 */     return this.displayY;
/*    */   }
/*    */   
/*    */   public int getClickCount() {
/* 77 */     return this.clickCount;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MousePressedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */