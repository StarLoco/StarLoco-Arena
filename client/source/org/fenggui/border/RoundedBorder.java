/*    */ package org.fenggui.border;
/*    */ 
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.util.Color;
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
/*    */ public class RoundedBorder
/*    */   extends Border
/*    */ {
/*    */   private int weight;
/*    */   private int radius;
/*    */   private Color color;
/*    */   
/*    */   public RoundedBorder(Color color, int weight, int radius) {
/* 62 */     this.color = color;
/* 63 */     this.weight = weight;
/* 64 */     this.radius = radius;
/* 65 */     setSpacing(radius, radius, radius, radius);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBottomBorderWidth() {
/* 70 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLeftBorderWidth() {
/* 75 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRightBorderWidth() {
/* 80 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTopBorderWidth() {
/* 85 */     return this.weight;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 91 */     g.setColor(this.color);
/* 92 */     g.setLineWidth(this.weight);
/*    */ 
/*    */     
/* 95 */     g.drawRoundedRectangle(localX, localY, width, height, this.radius);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\RoundedBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */