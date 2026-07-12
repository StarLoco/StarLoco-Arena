/*     */ package com.ankamagames.framework.graphics.opengl.base;
/*     */ 
/*     */ import com.sun.opengl.util.texture.TextureCoords;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
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
/*     */ public class BaseTexture
/*     */ {
/*     */   private static final int DEFAULT_MIN_FILTERING = 9729;
/*     */   private static final int DEFAULT_MAG_FILTERING = 9729;
/*     */   private Texture m_texture;
/*     */   private int m_minFileringFlag;
/*     */   private int m_magFileringFlag;
/*     */   
/*     */   public BaseTexture()
/*     */   {
/*  30 */     this.m_minFileringFlag = 9729;
/*  31 */     this.m_magFileringFlag = 9729;
/*     */   }
/*     */   
/*     */   public Texture getTexture() {
/*  35 */     return this.m_texture;
/*     */   }
/*     */   
/*     */   public void setTexture(Texture texture) {
/*  39 */     this.m_texture = texture;
/*     */     
/*  41 */     if (texture != null)
/*     */     {
/*     */ 
/*     */ 
/*  45 */       GL gl = GLU.getCurrentGL();
/*     */       
/*  47 */       gl.glTexParameterf(texture.getTarget(), 10241, this.m_minFileringFlag);
/*  48 */       gl.glTexParameterf(texture.getTarget(), 10240, this.m_magFileringFlag);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMinFileringFlag()
/*     */   {
/*  55 */     return this.m_minFileringFlag;
/*     */   }
/*     */   
/*     */   public void setMinFileringFlag(int minFileringFlag) {
/*  59 */     this.m_minFileringFlag = minFileringFlag;
/*     */   }
/*     */   
/*     */   public int getMagFileringFlag() {
/*  63 */     return this.m_magFileringFlag;
/*     */   }
/*     */   
/*     */   public void setMagFileringFlag(int magFileringFlag) {
/*  67 */     this.m_magFileringFlag = magFileringFlag;
/*     */   }
/*     */   
/*     */   public void enable()
/*     */   {
/*  72 */     if (this.m_texture != null)
/*  73 */       this.m_texture.enable();
/*     */   }
/*     */   
/*     */   public void disable() {
/*  77 */     if (this.m_texture != null)
/*  78 */       this.m_texture.disable();
/*     */   }
/*     */   
/*     */   public void bind() {
/*  82 */     if (this.m_texture != null) {
/*  83 */       this.m_texture.bind();
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/*  88 */     if (this.m_texture != null) {
/*  89 */       this.m_texture.dispose();
/*  90 */       this.m_texture = null;
/*     */     }
/*     */     
/*  93 */     this.m_minFileringFlag = 9729;
/*  94 */     this.m_magFileringFlag = 9729;
/*     */   }
/*     */   
/*     */   public float getImageWidth()
/*     */   {
/*  99 */     if (this.m_texture != null) {
/* 100 */       return this.m_texture.getImageWidth();
/*     */     }
/* 102 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public float getImageHeight()
/*     */   {
/* 107 */     if (this.m_texture != null) {
/* 108 */       return this.m_texture.getImageHeight();
/*     */     }
/* 110 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public TextureCoords getImageTexCoords()
/*     */   {
/* 115 */     if (this.m_texture != null) {
/* 116 */       return this.m_texture.getImageTexCoords();
/*     */     }
/* 118 */     return null;
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
/*     */   public double getAlpha(int x, int y)
/*     */   {
/* 132 */     if (this.m_texture == null)
/* 133 */       return 0.0D;
/* 134 */     return (this.m_texture.getAlpha(x, y) & 0xFF) / 255.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\BaseTexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */