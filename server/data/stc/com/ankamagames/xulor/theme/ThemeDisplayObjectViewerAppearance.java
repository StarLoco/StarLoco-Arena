/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThemeDisplayObjectViewerAppearance
/*     */   extends ThemeAppearance
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "DisplayObjectViewerAppearance";
/*     */   
/*     */ 
/*     */   private int m_xOffset;
/*     */   
/*     */ 
/*     */   private int m_yOffset;
/*     */   
/*     */ 
/*     */   private float m_scale;
/*     */   
/*     */ 
/*  22 */   private String m_linkage = null;
/*  23 */   private String m_descriptorLibrary = null;
/*     */   
/*  25 */   private boolean m_xOffsetInit = false; private boolean m_yOffsetInit = false;
/*  26 */   private boolean m_scaleInit = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDescriptorLibrary()
/*     */   {
/*  32 */     return this.m_descriptorLibrary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDescriptorLibrary(String descriptorLibrary)
/*     */   {
/*  39 */     String themeDirectory = ThemeParser.getInstance().getThemeDirectory();
/*     */     
/*  41 */     this.m_descriptorLibrary = ((themeDirectory != null ? themeDirectory : "") + descriptorLibrary);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLinkage()
/*     */   {
/*  49 */     return this.m_linkage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLinkage(String linkage)
/*     */   {
/*  56 */     this.m_linkage = linkage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getScale()
/*     */   {
/*  63 */     return this.m_scale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setScale(float scale)
/*     */   {
/*  70 */     this.m_scale = scale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getXOffset()
/*     */   {
/*  77 */     return this.m_xOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setXOffset(int offset)
/*     */   {
/*  84 */     this.m_xOffset = offset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getYOffset()
/*     */   {
/*  91 */     return this.m_yOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setYOffset(int offset)
/*     */   {
/*  98 */     this.m_yOffset = offset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isScaleInit()
/*     */   {
/* 105 */     return this.m_scaleInit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isXOffsetInit()
/*     */   {
/* 112 */     return this.m_xOffsetInit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isYOffsetInit()
/*     */   {
/* 119 */     return this.m_yOffsetInit;
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeDisplayObjectViewerAppearance app) {
/* 123 */     super.copyAttributes(app);
/* 124 */     if (this.m_linkage != null) app.setLinkage(this.m_linkage);
/* 125 */     if (this.m_descriptorLibrary != null) app.m_descriptorLibrary = this.m_descriptorLibrary;
/* 126 */     if (this.m_scaleInit) app.setScale(this.m_scale);
/* 127 */     if (this.m_xOffsetInit) app.setXOffset(this.m_xOffset);
/* 128 */     if (this.m_yOffsetInit) app.setYOffset(this.m_yOffset);
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 132 */     ThemeDisplayObjectViewerAppearance app = new ThemeDisplayObjectViewerAppearance();
/*     */     
/* 134 */     copyAttributes(app);
/*     */     
/* 136 */     return app;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeDisplayObjectViewerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */