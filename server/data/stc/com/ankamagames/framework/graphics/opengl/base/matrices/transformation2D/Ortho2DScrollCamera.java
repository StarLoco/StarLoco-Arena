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
/*    */ 
/*    */ public class Ortho2DScrollCamera
/*    */   implements GLMatrix
/*    */ {
/*    */   private double m_scrollX;
/*    */   private double m_scrollY;
/*    */   private double m_scrollZ;
/*    */   private double m_posX;
/*    */   private double m_posY;
/*    */   private double m_posZ;
/*    */   private boolean m_initialized;
/*    */   
/*    */   public Ortho2DScrollCamera()
/*    */   {
/* 30 */     this.m_initialized = false;
/*    */   }
/*    */   
/*    */   public void setup(GL gl) {
/* 34 */     gl.glLoadIdentity();
/* 35 */     gl.glTranslated(this.m_scrollX, this.m_scrollY, this.m_scrollZ);
/*    */   }
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
/*    */   public void scrollX(double dx)
/*    */   {
/* 49 */     this.m_scrollX = dx;
/*    */   }
/*    */   
/*    */   public void scrollY(double dy) {
/* 53 */     this.m_scrollY = dy;
/*    */   }
/*    */   
/*    */   public void scrollZ(double dz) {
/* 57 */     this.m_scrollZ = dz;
/*    */   }
/*    */   
/*    */   public double getPosX() {
/* 61 */     return this.m_posX;
/*    */   }
/*    */   
/*    */   public void setPosX(double posX) {
/* 65 */     this.m_initialized = true;
/* 66 */     this.m_posX = posX;
/*    */   }
/*    */   
/*    */   public double getPosY() {
/* 70 */     return this.m_posY;
/*    */   }
/*    */   
/*    */   public void setPosY(double posY) {
/* 74 */     this.m_initialized = true;
/* 75 */     this.m_posY = posY;
/*    */   }
/*    */   
/*    */   public double getPosZ() {
/* 79 */     return this.m_posZ;
/*    */   }
/*    */   
/*    */   public void setPosZ(double posZ) {
/* 83 */     this.m_initialized = true;
/* 84 */     this.m_posZ = posZ;
/*    */   }
/*    */   
/*    */   public boolean isInitialized() {
/* 88 */     return this.m_initialized;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\Ortho2DScrollCamera.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */