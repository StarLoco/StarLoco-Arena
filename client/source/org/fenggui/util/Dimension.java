/*    */ package org.fenggui.util;
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
/*    */ 
/*    */ public class Dimension
/*    */ {
/* 33 */   int width = -1, height = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension(int w, int h) {
/* 42 */     this.width = w;
/* 43 */     this.height = h;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension(Dimension d) {
/* 52 */     this.width = d.getWidth();
/* 53 */     this.height = d.getHeight();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(int x, int y) {
/* 63 */     return (x >= 0 && x < this.width && y >= 0 && y < this.height);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 71 */     return this.height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 79 */     return this.width;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "(" + this.width + ", " + this.height + ")";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Dimension.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */