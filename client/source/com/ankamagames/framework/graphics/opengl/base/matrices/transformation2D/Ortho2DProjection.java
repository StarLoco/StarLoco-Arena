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
/*    */ 
/*    */ 
/*    */ public class Ortho2DProjection
/*    */   implements GLMatrix
/*    */ {
/*    */   private float m_left;
/*    */   private float m_right;
/*    */   private float m_top;
/*    */   private float m_bottom;
/*    */   private static final float m_zMin = -200.0F;
/*    */   private static final float m_zMax = 100.0F;
/*    */   
/*    */   public Ortho2DProjection(float width, float height, boolean centered) {
/* 27 */     if (centered) {
/* 28 */       this.m_right = width * 0.5F;
/* 29 */       this.m_left = -this.m_right;
/* 30 */       this.m_top = height * 0.5F;
/* 31 */       this.m_bottom = -this.m_top;
/*    */     } else {
/*    */       
/* 34 */       this.m_right = width;
/* 35 */       this.m_left = 0.0F;
/* 36 */       this.m_top = height;
/* 37 */       this.m_bottom = 0.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(GL gl) {
/* 58 */     gl.glOrtho(this.m_left, this.m_right, this.m_bottom, this.m_top, -200.0D, 100.0D);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\Ortho2DProjection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */