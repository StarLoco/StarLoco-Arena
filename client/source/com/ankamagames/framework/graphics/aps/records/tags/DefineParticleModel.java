/*     */ package com.ankamagames.framework.graphics.aps.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleModel;
/*     */ import java.io.IOException;
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
/*     */ public abstract class DefineParticleModel
/*     */   extends Tag
/*     */ {
/*     */   private float m_hotX;
/*     */   private float m_hotY;
/*     */   private float m_scaleX;
/*     */   private float m_scaleRandomX;
/*     */   private float m_scaleY;
/*     */   private float m_scaleRandomY;
/*     */   private boolean m_scaleRandomKeepRatio;
/*     */   private float m_rotation;
/*     */   private float m_rotationRandom;
/*     */   private float m_redColor;
/*     */   private float m_greenColor;
/*     */   private float m_blueColor;
/*     */   private float m_alphaColor;
/*     */   private int m_textureMode;
/*     */   
/*     */   protected DefineParticleModel() {}
/*     */   
/*     */   public DefineParticleModel(ParticleModel model) {
/*  39 */     this.m_hotX = model.getHotX();
/*  40 */     this.m_hotY = model.getHotY();
/*  41 */     this.m_scaleX = model.getScaleX();
/*  42 */     this.m_scaleRandomX = model.getScaleXRandom();
/*  43 */     this.m_scaleY = model.getScaleY();
/*  44 */     this.m_scaleRandomY = model.getScaleYRandom();
/*  45 */     this.m_scaleRandomKeepRatio = model.isScaleRandomKeepRatio();
/*  46 */     this.m_rotation = model.getRotation();
/*  47 */     this.m_rotationRandom = model.getRotationRandom();
/*  48 */     this.m_alphaColor = model.getAlphaColor();
/*  49 */     this.m_redColor = model.getRedColor();
/*  50 */     this.m_greenColor = model.getGreenColor();
/*  51 */     this.m_blueColor = model.getBlueColor();
/*  52 */     this.m_textureMode = model.getTextureMode().ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeParticle(ParticleModel model) {
/*  57 */     model.setHotX(this.m_hotX);
/*  58 */     model.setHotY(this.m_hotY);
/*  59 */     model.setScaleX(this.m_scaleX);
/*  60 */     model.setScaleXRandom(this.m_scaleRandomX);
/*  61 */     model.setScaleY(this.m_scaleY);
/*  62 */     model.setScaleYRandom(this.m_scaleRandomY);
/*  63 */     model.setScaleRandomKeepRatio(this.m_scaleRandomKeepRatio);
/*  64 */     model.setRotation(this.m_rotation);
/*  65 */     model.setRotationRandom(this.m_rotationRandom);
/*  66 */     model.setRedColor(this.m_redColor);
/*  67 */     model.setGreenColor(this.m_greenColor);
/*  68 */     model.setBlueColor(this.m_blueColor);
/*  69 */     model.setAlphaColor(this.m_alphaColor);
/*  70 */     model.setTextureMode(ParticleModel.TextureMode.values()[this.m_textureMode]);
/*     */     
/*  72 */     model.setBlendSource(ParticleModel.BlendMode.ONE);
/*  73 */     model.setBlendDestination(ParticleModel.BlendMode.ONE_MINUS_SRC_ALPHA);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/*  78 */     outStream.writeFloat(this.m_hotX);
/*  79 */     outStream.writeFloat(this.m_hotY);
/*  80 */     outStream.writeFloat(this.m_scaleX);
/*  81 */     outStream.writeFloat(this.m_scaleRandomX);
/*  82 */     outStream.writeFloat(this.m_scaleY);
/*  83 */     outStream.writeFloat(this.m_scaleRandomY);
/*  84 */     outStream.writeBooleanBit(this.m_scaleRandomKeepRatio);
/*  85 */     outStream.writeFloat(this.m_rotation);
/*  86 */     outStream.writeFloat(this.m_rotationRandom);
/*  87 */     outStream.writeFloat(this.m_redColor);
/*  88 */     outStream.writeFloat(this.m_greenColor);
/*  89 */     outStream.writeFloat(this.m_blueColor);
/*  90 */     outStream.writeFloat(this.m_alphaColor);
/*  91 */     outStream.writeUI16(this.m_textureMode);
/*     */     
/*  93 */     outStream.align();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(InputBitStream inStream) throws IOException {
/*  98 */     this.m_hotX = inStream.readFloat();
/*  99 */     this.m_hotY = inStream.readFloat();
/* 100 */     this.m_scaleX = inStream.readFloat();
/* 101 */     this.m_scaleRandomX = inStream.readFloat();
/* 102 */     this.m_scaleY = inStream.readFloat();
/* 103 */     this.m_scaleRandomY = inStream.readFloat();
/* 104 */     this.m_scaleRandomKeepRatio = inStream.readBooleanBit();
/* 105 */     this.m_rotation = inStream.readFloat();
/* 106 */     this.m_rotationRandom = inStream.readFloat();
/* 107 */     this.m_redColor = inStream.readFloat();
/* 108 */     this.m_greenColor = inStream.readFloat();
/* 109 */     this.m_blueColor = inStream.readFloat();
/* 110 */     this.m_alphaColor = inStream.readFloat();
/* 111 */     this.m_textureMode = inStream.readUI16();
/*     */     
/* 113 */     inStream.align();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineParticleModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */