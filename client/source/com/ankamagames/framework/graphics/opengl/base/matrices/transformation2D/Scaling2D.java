/*    */ package com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Scaling2D
/*    */   implements GLMatrix
/*    */ {
/* 14 */   private float m_x = 1.0F;
/* 15 */   private float m_y = 1.0F;
/*    */ 
/*    */   
/*    */   public Scaling2D() {}
/*    */ 
/*    */   
/*    */   public Scaling2D(float x, float y) {
/* 22 */     set(x, y);
/*    */   }
/*    */   
/*    */   public float getX() {
/* 26 */     return this.m_x;
/*    */   }
/*    */   public void setX(float x) {
/* 29 */     this.m_x = x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 33 */     return this.m_y;
/*    */   }
/*    */   public void setY(float y) {
/* 36 */     this.m_y = y;
/*    */   }
/*    */   
/*    */   public void set(float x, float y) {
/* 40 */     this.m_x = x;
/* 41 */     this.m_y = y;
/*    */   }
/*    */   
/*    */   public void mult(float x, float y) {
/* 45 */     this.m_x *= x;
/* 46 */     this.m_y *= y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setup(GL gl) {
/* 51 */     if (this.m_x != 1.0F || this.m_y != 1.0F) {
/* 52 */       gl.glScalef(this.m_x, this.m_y, 1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 61 */     this.m_x = this.m_y = 1.0F;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     return String.format("Scaling : x=%f y=%f", new Object[] { Float.valueOf(this.m_x), Float.valueOf(this.m_y) });
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\Scaling2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */