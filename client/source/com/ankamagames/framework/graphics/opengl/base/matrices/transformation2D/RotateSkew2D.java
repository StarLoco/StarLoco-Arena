/*     */ package com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotateSkew2D
/*     */   implements GLMatrix
/*     */ {
/*  33 */   private float[] m_buffer = new float[16];
/*     */ 
/*     */   
/*     */   public RotateSkew2D() {
/*  37 */     reset();
/*     */   }
/*     */   
/*     */   public RotateSkew2D(float x, float y) {
/*  41 */     this();
/*  42 */     set(x, y);
/*     */   }
/*     */   
/*     */   public float getX() {
/*  46 */     return -this.m_buffer[1];
/*     */   }
/*     */   public void setX(float x) {
/*  49 */     this.m_buffer[1] = -x;
/*     */   }
/*     */   
/*     */   public float getY() {
/*  53 */     return -this.m_buffer[4];
/*     */   }
/*     */   public void setY(float y) {
/*  56 */     this.m_buffer[4] = -y;
/*     */   }
/*     */   
/*     */   public void set(float x, float y) {
/*  60 */     this.m_buffer[1] = -x;
/*  61 */     this.m_buffer[4] = -y;
/*     */   }
/*     */   
/*     */   public void add(float x, float y) {
/*  65 */     this.m_buffer[1] = this.m_buffer[1] - x;
/*  66 */     this.m_buffer[4] = this.m_buffer[4] - y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  73 */     this.m_buffer[0] = 1.0F;
/*  74 */     this.m_buffer[1] = 0.0F;
/*  75 */     this.m_buffer[2] = 0.0F;
/*  76 */     this.m_buffer[3] = 0.0F;
/*     */     
/*  78 */     this.m_buffer[4] = 0.0F;
/*  79 */     this.m_buffer[5] = 1.0F;
/*  80 */     this.m_buffer[6] = 0.0F;
/*  81 */     this.m_buffer[7] = 0.0F;
/*     */     
/*  83 */     this.m_buffer[8] = 0.0F;
/*  84 */     this.m_buffer[9] = 0.0F;
/*  85 */     this.m_buffer[10] = 1.0F;
/*  86 */     this.m_buffer[11] = 0.0F;
/*     */     
/*  88 */     this.m_buffer[12] = 0.0F;
/*  89 */     this.m_buffer[13] = 0.0F;
/*  90 */     this.m_buffer[14] = 0.0F;
/*  91 */     this.m_buffer[15] = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(GL gl) {
/* 100 */     if (this.m_buffer[1] != 0.0F || this.m_buffer[4] != 0.0F) {
/* 101 */       gl.glMultMatrixf(this.m_buffer, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return String.format("RotateSkew x=%f y=%f", new Object[] { Float.valueOf(getX()), Float.valueOf(getY()) });
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\RotateSkew2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */