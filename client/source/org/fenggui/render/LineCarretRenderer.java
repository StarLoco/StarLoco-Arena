/*    */ package org.fenggui.render;
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
/*    */ public class LineCarretRenderer
/*    */   implements ICarretRenderer
/*    */ {
/* 24 */   private int height = 10;
/*    */ 
/*    */   
/*    */   public LineCarretRenderer(int height) {
/* 28 */     this.height = height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(int x, int y, Graphics g) {
/* 36 */     g.drawLine(x, y, x, y + this.height);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\LineCarretRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */