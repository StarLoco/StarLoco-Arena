/*     */ package com.ankamagames.framework.graphics.opengl.base.material;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaterialColorMultAdd
/*     */   implements BaseMaterial
/*     */ {
/*     */   private boolean m_isDefault;
/*     */   
/*     */   private boolean m_useMultTerm;
/*     */   
/*     */   private boolean m_useAddTerm;
/*     */   
/*     */   private boolean m_multTermChanged;
/*     */   
/*     */   private boolean m_addTermChanged;
/*     */   
/*     */ 
/*     */   public MaterialColorMultAdd()
/*     */   {
/*  21 */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*  25 */     this.m_useMultTerm = false;
/*  26 */     this.m_useAddTerm = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  31 */     this.m_multTermChanged = true;
/*  32 */     this.m_addTermChanged = true;
/*     */     
/*  34 */     this.m_isDefault = true;
/*     */   }
/*     */   
/*     */   public boolean hasChanged() {
/*  38 */     return (this.m_multTermChanged) || (this.m_addTermChanged);
/*     */   }
/*     */   
/*     */   public MaterialColorMultAdd duplicate()
/*     */   {
/*  43 */     MaterialColorMultAdd material = new MaterialColorMultAdd();
/*     */     
/*  45 */     material.m_multTermChanged = true;
/*  46 */     material.m_addTermChanged = true;
/*     */     
/*  48 */     material.m_useAddTerm = this.m_useAddTerm;
/*  49 */     material.m_useMultTerm = this.m_useMultTerm;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */     return material;
/*     */   }
/*     */   
/*     */   public boolean hasDefaultValue() {
/*  59 */     return this.m_isDefault;
/*     */   }
/*     */   
/*     */   public static void composeMaterial(Material originMaterial, Material colorMaterial)
/*     */   {
/*  64 */     float[] diffuseOrigin = originMaterial.getDiffuse();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  69 */     if (colorMaterial.useSpecular()) {
/*  70 */       float[] specular = colorMaterial.getSpecular();
/*  71 */       float[] originSpecular = originMaterial.getSpecular();
/*     */       float r;
/*  73 */       float g; float b; float a; if (originMaterial.useSpecular()) {
/*  74 */         float r = originSpecular[0];
/*  75 */         float g = originSpecular[1];
/*  76 */         float b = originSpecular[2];
/*  77 */         float a = originSpecular[3];
/*     */         
/*     */ 
/*  80 */         if ((originMaterial.useDiffuse()) && (colorMaterial.useDiffuse())) {
/*  81 */           r *= diffuseOrigin[0];
/*  82 */           g *= diffuseOrigin[1];
/*  83 */           b *= diffuseOrigin[2];
/*  84 */           a *= diffuseOrigin[3];
/*     */         }
/*     */         
/*  87 */         r += specular[0];
/*  88 */         g += specular[1];
/*  89 */         b += specular[2];
/*  90 */         a += specular[3];
/*     */       } else {
/*  92 */         r = specular[0];
/*  93 */         g = specular[1];
/*  94 */         b = specular[2];
/*  95 */         a = specular[3];
/*     */       }
/*     */       
/*  98 */       originMaterial.setSpecular(r, g, b, a);
/*  99 */       originMaterial.setUseSpecular(true);
/*     */     }
/*     */     
/* 102 */     if (colorMaterial.useDiffuse())
/*     */     {
/* 104 */       float[] diffuse = colorMaterial.getDiffuse();
/* 105 */       float a; float r; float g; float b; float a; if (originMaterial.useDiffuse()) {
/* 106 */         float r = diffuseOrigin[0] * diffuse[0];
/* 107 */         float g = diffuseOrigin[1] * diffuse[1];
/* 108 */         float b = diffuseOrigin[2] * diffuse[2];
/* 109 */         a = diffuseOrigin[3] * diffuse[3];
/*     */       } else {
/* 111 */         r = diffuse[0];
/* 112 */         g = diffuse[1];
/* 113 */         b = diffuse[2];
/* 114 */         a = diffuse[3];
/*     */       }
/* 116 */       originMaterial.setDiffuse(r, g, b, a);
/* 117 */       originMaterial.setUseDiffuse(true);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\material\MaterialColorMultAdd.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */