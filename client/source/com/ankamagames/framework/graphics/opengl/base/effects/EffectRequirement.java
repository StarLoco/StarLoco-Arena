/*     */ package com.ankamagames.framework.graphics.opengl.base.effects;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.resource.direct.DirectBufferManager;
/*     */ import com.sun.opengl.cg.CgGL;
/*     */ import java.nio.IntBuffer;
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
/*     */ public final class EffectRequirement
/*     */ {
/*     */   private int m_minVsProfile;
/*     */   private int m_minPsProfile;
/*     */   private int m_minTexUnits;
/*     */   private GL m_gl;
/*     */   
/*     */   public EffectRequirement() {}
/*     */   
/*     */   public EffectRequirement(int minVsProfile, int minPsProfile, int minTexUnits) {
/*  26 */     this.m_minVsProfile = minVsProfile;
/*  27 */     this.m_minPsProfile = minPsProfile;
/*  28 */     this.m_minTexUnits = minTexUnits;
/*  29 */     this.m_gl = GLU.getCurrentGL();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchCurrentConfiguration(GL gl) {
/*  34 */     boolean vsMatch = vsProfileMatchRequirements(CgGL.cgGLGetLatestProfile(8));
/*  35 */     boolean psMatch = psProfileMatchRequirements(CgGL.cgGLGetLatestProfile(9));
/*  36 */     boolean tuMatch = (getMaxTextureUnits() >= this.m_minTexUnits);
/*     */     
/*  38 */     return (vsMatch && psMatch && tuMatch);
/*     */   }
/*     */   
/*     */   public boolean vsProfileMatchRequirements(int vs) {
/*  42 */     return (vsVersion(vs) >= vsVersion(this.m_minVsProfile));
/*     */   }
/*     */   
/*     */   public boolean psProfileMatchRequirements(int ps) {
/*  46 */     return (psVersion(ps) >= psVersion(this.m_minPsProfile));
/*     */   }
/*     */   
/*     */   public int getMaxTextureUnits() {
/*  50 */     IntBuffer texUnits = DirectBufferManager.getInstance().getIntBuffer(1);
/*  51 */     this.m_gl.glGetIntegerv(34018, texUnits);
/*     */     
/*  53 */     int units = texUnits.get(0);
/*  54 */     DirectBufferManager.getInstance().releaseBuffer(texUnits);
/*  55 */     return units;
/*     */   }
/*     */   
/*     */   public boolean isTextureRectangleSupported() {
/*  59 */     return this.m_gl.isExtensionAvailable("GL_EXT_texture_rectangle");
/*     */   }
/*     */   
/*     */   public boolean isFBOSupported() {
/*  63 */     return this.m_gl.isExtensionAvailable("GL_EXT_framebuffer_object");
/*     */   }
/*     */ 
/*     */   
/*     */   private int vsVersion(int profile) {
/*  68 */     int version = 0;
/*     */     
/*  70 */     switch (profile) {
/*     */       case 6150:
/*  72 */         version = 11;
/*     */         break;
/*     */       case 7001:
/*  75 */         version = 30;
/*     */         break;
/*     */       case 6148:
/*  78 */         version = 20;
/*     */         break;
/*     */       case 6146:
/*  81 */         version = 11;
/*     */         break;
/*     */       case 6153:
/*  84 */         version = 11;
/*     */         break;
/*     */       case 6154:
/*  87 */         version = 20;
/*     */         break;
/*     */       case 6155:
/*  90 */         version = 29;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     return version;
/*     */   }
/*     */ 
/*     */   
/*     */   private int psVersion(int profile) {
/* 101 */     int version = 0;
/*     */     
/* 103 */     switch (profile) {
/*     */       case 7000:
/* 105 */         version = 20;
/*     */         break;
/*     */       case 6151:
/* 108 */         version = 30;
/*     */         break;
/*     */       case 6149:
/* 111 */         version = 20;
/*     */         break;
/*     */       case 6147:
/* 114 */         version = 13;
/*     */         break;
/*     */       case 6159:
/* 117 */         version = 11;
/*     */         break;
/*     */       case 6161:
/* 120 */         version = 13;
/*     */         break;
/*     */       case 6162:
/* 123 */         version = 20;
/*     */         break;
/*     */       case 6163:
/* 126 */         version = 29;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 132 */     return version;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\EffectRequirement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */