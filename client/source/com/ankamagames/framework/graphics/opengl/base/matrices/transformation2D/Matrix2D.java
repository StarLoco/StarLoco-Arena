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
/*     */ public class Matrix2D
/*     */   implements GLMatrix
/*     */ {
/*  14 */   private float[] m_buffer = new float[16];
/*     */   
/*     */   public Matrix2D() {
/*  17 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  24 */     this.m_buffer[0] = 1.0F;
/*  25 */     this.m_buffer[1] = 0.0F;
/*  26 */     this.m_buffer[2] = 0.0F;
/*  27 */     this.m_buffer[3] = 0.0F;
/*     */     
/*  29 */     this.m_buffer[4] = 0.0F;
/*  30 */     this.m_buffer[5] = 1.0F;
/*  31 */     this.m_buffer[6] = 0.0F;
/*  32 */     this.m_buffer[7] = 0.0F;
/*     */     
/*  34 */     this.m_buffer[8] = 0.0F;
/*  35 */     this.m_buffer[9] = 0.0F;
/*  36 */     this.m_buffer[10] = 1.0F;
/*  37 */     this.m_buffer[11] = 0.0F;
/*     */     
/*  39 */     this.m_buffer[12] = 0.0F;
/*  40 */     this.m_buffer[13] = 0.0F;
/*  41 */     this.m_buffer[14] = 0.0F;
/*  42 */     this.m_buffer[15] = 1.0F;
/*     */   }
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
/*     */   public void set(float a, float b, float c, float d, float tx, float ty) {
/*  62 */     this.m_buffer[0] = a;
/*  63 */     this.m_buffer[1] = -c;
/*  64 */     this.m_buffer[2] = 0.0F;
/*  65 */     this.m_buffer[3] = 0.0F;
/*     */     
/*  67 */     this.m_buffer[4] = -b;
/*  68 */     this.m_buffer[5] = d;
/*  69 */     this.m_buffer[6] = 0.0F;
/*  70 */     this.m_buffer[7] = 0.0F;
/*     */     
/*  72 */     this.m_buffer[8] = 0.0F;
/*  73 */     this.m_buffer[9] = 0.0F;
/*  74 */     this.m_buffer[10] = 1.0F;
/*  75 */     this.m_buffer[11] = 0.0F;
/*     */     
/*  77 */     this.m_buffer[12] = tx;
/*  78 */     this.m_buffer[13] = ty;
/*  79 */     this.m_buffer[14] = 0.0F;
/*  80 */     this.m_buffer[15] = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateScale2D(float a, float x, float y) {
/*  85 */     this.m_buffer[0] = (float)Math.cos(a) * x;
/*  86 */     this.m_buffer[5] = (float)Math.cos(a) * y;
/*     */     
/*  88 */     this.m_buffer[1] = (float)Math.sin(a) * x;
/*  89 */     this.m_buffer[4] = -((float)Math.sin(a)) * y;
/*     */   }
/*     */   
/*     */   public void rotate2D(float a) {
/*  93 */     this.m_buffer[0] = (float)Math.cos(a);
/*  94 */     this.m_buffer[4] = -((float)Math.sin(a));
/*  95 */     this.m_buffer[1] = (float)Math.sin(a);
/*  96 */     this.m_buffer[5] = (float)Math.cos(a);
/*     */   }
/*     */   
/*     */   public void scale2D(float x, float y) {
/* 100 */     this.m_buffer[0] = this.m_buffer[0] * x;
/* 101 */     this.m_buffer[5] = this.m_buffer[5] * y;
/*     */   }
/*     */   
/*     */   public void translate(float x, float y, float z) {
/* 105 */     this.m_buffer[12] = x;
/* 106 */     this.m_buffer[13] = y;
/* 107 */     this.m_buffer[14] = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(GL gl) {
/* 115 */     gl.glMultMatrixf(this.m_buffer, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return String.format("Matrix2D", new Object[0]);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\matrices\transformation2D\Matrix2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */