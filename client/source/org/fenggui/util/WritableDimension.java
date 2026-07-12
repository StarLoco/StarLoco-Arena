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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WritableDimension
/*    */   extends Dimension
/*    */ {
/*    */   public WritableDimension(int w, int h) {
/* 38 */     super(w, h);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WritableDimension(Dimension d) {
/* 46 */     super(d);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setWidth(int width) {
/* 51 */     this.width = width;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSize(int width, int height) {
/* 56 */     this.width = width;
/* 57 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSize(Dimension d) {
/* 62 */     this.width = d.getWidth();
/* 63 */     this.height = d.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHeight(int height) {
/* 68 */     this.height = height;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\WritableDimension.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */