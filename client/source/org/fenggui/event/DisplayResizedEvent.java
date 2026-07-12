/*    */ package org.fenggui.event;
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
/*    */ 
/*    */ public class DisplayResizedEvent
/*    */ {
/*    */   int width;
/*    */   int height;
/*    */   
/*    */   public DisplayResizedEvent(int width, int height) {
/* 36 */     this.width = width;
/* 37 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 42 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 46 */     return this.height;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\DisplayResizedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */