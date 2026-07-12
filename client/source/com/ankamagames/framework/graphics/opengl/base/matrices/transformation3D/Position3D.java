/*    */ package com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Position3D
/*    */   implements GLMatrix
/*    */ {
/*    */   protected float m_x;
/*    */   protected float m_y;
/*    */   protected float m_z;
/*    */   
/*    */   public Position3D() {}
/*    */   
/*    */   public Position3D(float x, float y, float z) {
/* 22 */     set(x, y, z);
/*    */   }
/*    */   
/*    */   public float getX() {
/* 26 */     return this.m_x;
/*    */   }
/*    */   
/*    */   public void setX(float x) {
/* 30 */     this.m_x = x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 34 */     return this.m_y;
/*    */   }
/*    */   
/*    */   public void setY(float y) {
/* 38 */     this.m_y = y;
/*    */   }
/*    */   
/*    */   public float getZ() {
/* 42 */     return this.m_z;
/*    */   }
/*    */   
/*    */   public void setZ(float z) {
/* 46 */     this.m_z = z;
/*    */   }
/*    */   
/*    */   public void set(float x, float y, float z) {
/* 50 */     this.m_x = x;
/* 51 */     this.m_y = y;
/* 52 */     this.m_z = z;
/*    */   }
/*    */   
/*    */   public void add(float x, float y, float z) {
/* 56 */     this.m_x += x;
/* 57 */     this.m_y += y;
/* 58 */     this.m_z += z;
/*    */   }
/*    */   
/*    */   public void setup(GL gl) {
/* 62 */     if (this.m_x != 0.0F || this.m_y != 0.0F || this.m_z != 0.0F) {
/* 63 */       gl.glTranslatef(this.m_x, this.m_y, this.m_z);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 72 */     this.m_x = this.m_y = this.m_z = 0.0F;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     return String.format("Position x=%f y=%f z=%f", new Object[] { Float.valueOf(this.m_x), Float.valueOf(this.m_y), Float.valueOf(this.m_z) });
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation3D\Position3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */