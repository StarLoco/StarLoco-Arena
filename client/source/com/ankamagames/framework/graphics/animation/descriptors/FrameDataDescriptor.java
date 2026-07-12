/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Matrix2D;
/*     */ import com.ankamagames.framework.graphics.sba.records.ColorTransform;
/*     */ import com.ankamagames.framework.graphics.sba.records.Matrix;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.PlaceObject;
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
/*     */ public class FrameDataDescriptor
/*     */ {
/*     */   private int m_characterId;
/*     */   private int m_depth;
/*     */   private Matrix2D m_matrix;
/*     */   private Material m_material;
/*     */   
/*     */   public FrameDataDescriptor(PlaceObject tag, FrameDataDescriptor oldDatas) {
/*  32 */     Matrix sbaMatrix = tag.getMatrix();
/*     */     
/*  34 */     if (sbaMatrix == null) {
/*  35 */       if (oldDatas != null) {
/*  36 */         this.m_matrix = oldDatas.getMatrix();
/*     */       } else {
/*     */         
/*  39 */         this.m_matrix = new Matrix2D();
/*     */       } 
/*     */     } else {
/*     */       
/*  43 */       this.m_matrix = new Matrix2D();
/*  44 */       this.m_matrix.set(sbaMatrix.getScaleX(), sbaMatrix.getRotateSkew1(), 
/*  45 */           sbaMatrix.getRotateSkew0(), sbaMatrix.getScaleY(), 
/*  46 */           sbaMatrix.getTranslateX() * 0.1F, 
/*  47 */           sbaMatrix.getTranslateY() * 0.1F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  52 */     ColorTransform color = tag.getColorTransform();
/*     */     
/*  54 */     if (color == null) {
/*  55 */       if (oldDatas != null)
/*     */       {
/*  57 */         this.m_material = oldDatas.getMaterial();
/*     */       }
/*     */     } else {
/*  60 */       this.m_material = new Material();
/*     */ 
/*     */ 
/*     */       
/*  64 */       if (color.hasMultTerms()) {
/*     */         
/*  66 */         float r = color.getRedMultTerm() / 256.0F;
/*  67 */         float g = color.getGreenMultTerm() / 256.0F;
/*  68 */         float b = color.getBlueMultTerm() / 256.0F;
/*  69 */         float a = color.getAlphaMultTerm() / 256.0F;
/*     */         
/*  71 */         this.m_material.setDiffuse(r, g, b, a);
/*  72 */         this.m_material.setUseDiffuse(true);
/*     */       } 
/*     */       
/*  75 */       if (color.hasAddTerms()) {
/*     */         
/*  77 */         float r = color.getRedAddTerm() / 255.0F;
/*  78 */         float g = color.getGreenAddTerm() / 255.0F;
/*  79 */         float b = color.getBlueAddTerm() / 255.0F;
/*  80 */         float a = color.getAlphaAddTerm() / 255.0F;
/*     */         
/*  82 */         this.m_material.setSpecular(r, g, b, a);
/*  83 */         this.m_material.setUseSpecular(true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (tag.hasCharacterId()) {
/*  90 */       this.m_characterId = tag.getIdentifier();
/*     */     } else {
/*  92 */       this.m_characterId = 0;
/*     */     } 
/*     */     
/*  95 */     this.m_depth = tag.getDepth();
/*     */   }
/*     */ 
/*     */   
/*     */   protected FrameDataDescriptor(int depth) {
/* 100 */     this.m_characterId = 0;
/* 101 */     this.m_depth = depth;
/*     */   }
/*     */   
/*     */   public int getDepth() {
/* 105 */     return this.m_depth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCharacterId() {
/* 111 */     return (this.m_characterId != 0);
/*     */   }
/*     */   
/*     */   public int getCharacterId() {
/* 115 */     return this.m_characterId;
/*     */   }
/*     */   
/*     */   public void setCharacterId(int characterId) {
/* 119 */     this.m_characterId = characterId;
/*     */   }
/*     */   
/*     */   public Matrix2D getMatrix() {
/* 123 */     return this.m_matrix;
/*     */   }
/*     */   
/*     */   public void setMatrix(Matrix2D matrix) {
/* 127 */     this.m_matrix = matrix;
/*     */   }
/*     */   
/*     */   public Material getMaterial() {
/* 131 */     return this.m_material;
/*     */   }
/*     */   
/*     */   public void setMaterial(Material material) {
/* 135 */     this.m_material = material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FrameDataDescriptor duplicate() {
/* 144 */     FrameDataDescriptor clone = new FrameDataDescriptor(this.m_depth);
/*     */     
/* 146 */     clone.m_material = this.m_material;
/* 147 */     clone.m_matrix = this.m_matrix;
/*     */     
/* 149 */     return clone;
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 153 */     this.m_matrix = null;
/* 154 */     this.m_material = null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\FrameDataDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */