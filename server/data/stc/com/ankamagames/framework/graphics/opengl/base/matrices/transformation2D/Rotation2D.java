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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Rotation2D
/*    */   implements GLMatrix
/*    */ {
/*    */   protected float m_angleDeg;
/*    */   
/*    */   public Rotation2D() {}
/*    */   
/*    */   public Rotation2D(float angleDeg)
/*    */   {
/* 23 */     this.m_angleDeg = angleDeg;
/*    */   }
/*    */   
/*    */   public float getAngleDeg()
/*    */   {
/* 28 */     return this.m_angleDeg;
/*    */   }
/*    */   
/*    */   public void setAngleDeg(float angleDeg) {
/* 32 */     this.m_angleDeg = angleDeg;
/*    */   }
/*    */   
/*    */   public void add(float angleDeg) {
/* 36 */     this.m_angleDeg += angleDeg;
/*    */   }
/*    */   
/*    */   public void setup(GL gl) {
/* 40 */     if (this.m_angleDeg != 0.0F) {
/* 41 */       gl.glRotatef(this.m_angleDeg, 0.0F, 0.0F, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void reset()
/*    */   {
/* 50 */     this.m_angleDeg = 0.0F;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return String.format("Rotation angle=%f", new Object[] { Float.valueOf(this.m_angleDeg) });
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\Rotation2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */