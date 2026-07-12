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
/*    */ public class MouseMovedEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   int displayX;
/*    */   int displayY;
/*    */   
/*    */   public MouseMovedEvent(IWidget source, int displayX, int displayY) {
/* 30 */     super(source);
/* 31 */     this.displayX = displayX;
/* 32 */     this.displayY = displayY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayX() {
/* 38 */     return this.displayX;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDisplayY() {
/* 44 */     return this.displayY;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\mouse\MouseMovedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */