/*     */ package com.ankamagames.framework.graphics.particlesystem;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.graphics.particlesystem.states.ParticlePostRenderStates;
/*     */ import com.ankamagames.framework.graphics.particlesystem.states.ParticlePreRenderStates;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ParticleModel
/*     */ {
/*     */   private TextureMode m_textureMode;
/*     */   private BlendMode m_blendSource;
/*     */   private BlendMode m_blendDestination;
/*     */   private float m_hotX;
/*     */   private float m_hotY;
/*     */   
/*     */   public static enum TextureMode
/*     */   {
/*  26 */     ADD,  BLEND,  COMBINE,  DECAL; }
/*     */   
/*  28 */   public static enum BlendMode { ZERO, 
/*  29 */     ONE, 
/*  30 */     SRC_ALPHA, 
/*  31 */     SRC_ALPHA_SATURATE, 
/*  32 */     ONE_MINUS_SRC_ALPHA, 
/*  33 */     SRC_COLOR, 
/*  34 */     ONE_MINUS_SRC_COLOR, 
/*  35 */     DST_COLOR, 
/*  36 */     ONE_MINUS_DST_COLOR, 
/*  37 */     DST_ALPHA, 
/*  38 */     ONE_MINUS_DST_ALPHA;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   protected boolean m_scaleRandomKeepRatio = false;
/*     */   
/*  50 */   protected float m_scaleX = 1.0F;
/*  51 */   protected float m_scaleXRandom = 0.0F;
/*  52 */   protected float m_scaleY = 1.0F;
/*  53 */   protected float m_scaleYRandom = 0.0F;
/*  54 */   protected float m_rotation = 0.0F;
/*  55 */   protected float m_rotationRandom = 0.0F;
/*     */   
/*     */   private float m_redColor;
/*     */   
/*     */   private float m_greenColor;
/*     */   
/*     */   private float m_blueColor;
/*     */   private float m_alphaColor;
/*     */   protected ParticlePreRenderStates m_particlePreRenderStates;
/*     */   protected ParticlePostRenderStates m_particlePostRenderStates;
/*  65 */   protected Random m_random = new Random();
/*     */   
/*     */   public ParticleModel()
/*     */   {
/*  69 */     this.m_particlePreRenderStates = new ParticlePreRenderStates();
/*  70 */     this.m_particlePostRenderStates = new ParticlePostRenderStates();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Particle generateParticle(ParticleSystem paramParticleSystem);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void intializeParticleMesh(Mesh2D mesh)
/*     */   {
/*  86 */     mesh.setColor(getRedColor(), 
/*  87 */       getGreenColor(), 
/*  88 */       getBlueColor(), 
/*  89 */       getAlphaColor());
/*     */     
/*  91 */     float scaleX = this.m_scaleX;
/*  92 */     float scaleY = this.m_scaleY;
/*  93 */     float rotation = this.m_rotation;
/*     */     
/*  95 */     if (this.m_scaleRandomKeepRatio)
/*     */     {
/*  97 */       float randomScale = this.m_random.nextFloat() * (this.m_scaleXRandom > this.m_scaleYRandom ? this.m_scaleXRandom : this.m_scaleYRandom);
/*     */       
/*  99 */       scaleX += randomScale;
/* 100 */       scaleY += randomScale;
/*     */     }
/*     */     else
/*     */     {
/* 104 */       if (this.m_scaleXRandom != 0.0F) {
/* 105 */         scaleX += this.m_random.nextFloat() * this.m_scaleXRandom;
/*     */       }
/* 107 */       if (this.m_scaleYRandom != 0.0F) {
/* 108 */         scaleY += this.m_random.nextFloat() * this.m_scaleYRandom;
/*     */       }
/*     */     }
/* 111 */     if (this.m_rotationRandom != 0.0F) {
/* 112 */       rotation += this.m_random.nextFloat() * this.m_rotationRandom;
/*     */     }
/* 114 */     mesh.setScale(scaleX, scaleY);
/* 115 */     mesh.setRotation(rotation);
/*     */     
/* 117 */     mesh.setHotPoint(getHotX(), getHotY());
/*     */     
/* 119 */     mesh.setPreRenderStates(this.m_particlePreRenderStates);
/* 120 */     mesh.setPostRenderStates(this.m_particlePostRenderStates);
/*     */   }
/*     */   
/*     */   public float getHotX() {
/* 124 */     return this.m_hotX;
/*     */   }
/*     */   
/*     */   public void setHotX(float hotX) {
/* 128 */     this.m_hotX = hotX;
/*     */   }
/*     */   
/*     */   public float getHotY() {
/* 132 */     return this.m_hotY;
/*     */   }
/*     */   
/*     */   public void setHotY(float hotY) {
/* 136 */     this.m_hotY = hotY;
/*     */   }
/*     */   
/*     */   public float getRedColor() {
/* 140 */     return this.m_redColor;
/*     */   }
/*     */   
/*     */   public void setRedColor(float redColor) {
/* 144 */     this.m_redColor = redColor;
/*     */   }
/*     */   
/*     */   public float getGreenColor() {
/* 148 */     return this.m_greenColor;
/*     */   }
/*     */   
/*     */   public void setGreenColor(float greenColor) {
/* 152 */     this.m_greenColor = greenColor;
/*     */   }
/*     */   
/*     */   public float getBlueColor() {
/* 156 */     return this.m_blueColor;
/*     */   }
/*     */   
/*     */   public void setBlueColor(float blueColor) {
/* 160 */     this.m_blueColor = blueColor;
/*     */   }
/*     */   
/*     */   public float getAlphaColor() {
/* 164 */     return this.m_alphaColor;
/*     */   }
/*     */   
/*     */   public void setAlphaColor(float alphaColor) {
/* 168 */     this.m_alphaColor = alphaColor;
/*     */   }
/*     */   
/*     */   public float getScaleX() {
/* 172 */     return this.m_scaleX;
/*     */   }
/*     */   
/*     */   public void setScaleX(float scaleX) {
/* 176 */     this.m_scaleX = scaleX;
/*     */   }
/*     */   
/*     */   public float getScaleY() {
/* 180 */     return this.m_scaleY;
/*     */   }
/*     */   
/*     */   public void setScaleY(float scaleY) {
/* 184 */     this.m_scaleY = scaleY;
/*     */   }
/*     */   
/*     */   public float getRotation() {
/* 188 */     return this.m_rotation;
/*     */   }
/*     */   
/*     */   public void setRotation(float rotation) {
/* 192 */     this.m_rotation = rotation;
/*     */   }
/*     */   
/*     */   public boolean isScaleRandomKeepRatio() {
/* 196 */     return this.m_scaleRandomKeepRatio;
/*     */   }
/*     */   
/*     */   public void setScaleRandomKeepRatio(boolean scaleRandomKeepRatio) {
/* 200 */     this.m_scaleRandomKeepRatio = scaleRandomKeepRatio;
/*     */   }
/*     */   
/*     */   public float getScaleXRandom() {
/* 204 */     return this.m_scaleXRandom;
/*     */   }
/*     */   
/*     */   public void setScaleXRandom(float scaleXRandom) {
/* 208 */     this.m_scaleXRandom = scaleXRandom;
/*     */   }
/*     */   
/*     */   public float getScaleYRandom() {
/* 212 */     return this.m_scaleYRandom;
/*     */   }
/*     */   
/*     */   public void setScaleYRandom(float scaleYRandom) {
/* 216 */     this.m_scaleYRandom = scaleYRandom;
/*     */   }
/*     */   
/*     */   public float getRotationRandom() {
/* 220 */     return this.m_rotationRandom;
/*     */   }
/*     */   
/*     */   public void setRotationRandom(float rotationRandom) {
/* 224 */     this.m_rotationRandom = rotationRandom;
/*     */   }
/*     */   
/*     */   public TextureMode getTextureMode()
/*     */   {
/* 229 */     return this.m_textureMode;
/*     */   }
/*     */   
/*     */   public void setTextureMode(TextureMode textureMode) {
/* 233 */     this.m_textureMode = textureMode;
/*     */     
/* 235 */     switch (this.m_textureMode) {
/*     */     case ADD: 
/* 237 */       this.m_particlePreRenderStates.setTextureEnvMode(260);
/* 238 */       break;
/*     */     case DECAL: 
/* 240 */       this.m_particlePreRenderStates.setTextureEnvMode(8449);
/* 241 */       break;
/*     */     case COMBINE: 
/* 243 */       this.m_particlePreRenderStates.setTextureEnvMode(34160);
/* 244 */       break;
/*     */     case BLEND: 
/* 246 */       this.m_particlePreRenderStates.setTextureEnvMode(3042);
/*     */     }
/*     */   }
/*     */   
/*     */   public BlendMode getBlendDestination()
/*     */   {
/* 252 */     return this.m_blendDestination;
/*     */   }
/*     */   
/*     */   public void setBlendDestination(BlendMode blendDestination) {
/* 256 */     this.m_blendDestination = blendDestination;
/*     */     
/* 258 */     switch (this.m_blendDestination) {
/*     */     case DST_ALPHA: 
/* 260 */       this.m_particlePreRenderStates.setBlendDestination(0);
/* 261 */       break;
/*     */     case DST_COLOR: 
/* 263 */       this.m_particlePreRenderStates.setBlendDestination(1);
/* 264 */       break;
/*     */     case SRC_ALPHA: 
/* 266 */       this.m_particlePreRenderStates.setBlendDestination(774);
/* 267 */       break;
/*     */     case ONE_MINUS_SRC_ALPHA: 
/* 269 */       this.m_particlePreRenderStates.setBlendDestination(768);
/* 270 */       break;
/*     */     case SRC_ALPHA_SATURATE: 
/* 272 */       this.m_particlePreRenderStates.setBlendDestination(775);
/* 273 */       break;
/*     */     case ONE_MINUS_SRC_COLOR: 
/* 275 */       this.m_particlePreRenderStates.setBlendDestination(769);
/* 276 */       break;
/*     */     case ONE: 
/* 278 */       this.m_particlePreRenderStates.setBlendDestination(770);
/* 279 */       break;
/*     */     case ONE_MINUS_DST_COLOR: 
/* 281 */       this.m_particlePreRenderStates.setBlendDestination(771);
/* 282 */       break;
/*     */     case SRC_COLOR: 
/* 284 */       this.m_particlePreRenderStates.setBlendDestination(772);
/* 285 */       break;
/*     */     case ZERO: 
/* 287 */       this.m_particlePreRenderStates.setBlendDestination(773);
/* 288 */       break;
/*     */     case ONE_MINUS_DST_ALPHA: 
/* 290 */       this.m_particlePreRenderStates.setBlendDestination(776);
/*     */     }
/*     */   }
/*     */   
/*     */   public BlendMode getBlendSource()
/*     */   {
/* 296 */     return this.m_blendSource;
/*     */   }
/*     */   
/*     */   public void setBlendSource(BlendMode blendSource) {
/* 300 */     this.m_blendSource = blendSource;
/*     */     
/* 302 */     switch (this.m_blendSource) {
/*     */     case DST_ALPHA: 
/* 304 */       this.m_particlePreRenderStates.setBlendSource(0);
/* 305 */       break;
/*     */     case DST_COLOR: 
/* 307 */       this.m_particlePreRenderStates.setBlendSource(1);
/* 308 */       break;
/*     */     case SRC_ALPHA: 
/* 310 */       this.m_particlePreRenderStates.setBlendSource(774);
/* 311 */       break;
/*     */     case ONE_MINUS_SRC_ALPHA: 
/* 313 */       this.m_particlePreRenderStates.setBlendSource(768);
/* 314 */       break;
/*     */     case SRC_ALPHA_SATURATE: 
/* 316 */       this.m_particlePreRenderStates.setBlendSource(775);
/* 317 */       break;
/*     */     case ONE_MINUS_SRC_COLOR: 
/* 319 */       this.m_particlePreRenderStates.setBlendSource(769);
/* 320 */       break;
/*     */     case ONE: 
/* 322 */       this.m_particlePreRenderStates.setBlendSource(770);
/* 323 */       break;
/*     */     case ONE_MINUS_DST_COLOR: 
/* 325 */       this.m_particlePreRenderStates.setBlendSource(771);
/* 326 */       break;
/*     */     case SRC_COLOR: 
/* 328 */       this.m_particlePreRenderStates.setBlendSource(772);
/* 329 */       break;
/*     */     case ZERO: 
/* 331 */       this.m_particlePreRenderStates.setBlendSource(773);
/* 332 */       break;
/*     */     case ONE_MINUS_DST_ALPHA: 
/* 334 */       this.m_particlePreRenderStates.setBlendSource(776);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 343 */     this.m_particlePreRenderStates = null;
/* 344 */     this.m_particlePreRenderStates = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\ParticleModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */