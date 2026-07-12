/*     */ package com.ankamagames.xulor.theme;
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
/*     */ public class ThemeAttributes
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "Attributes";
/*     */   private int m_width;
/*     */   private int m_height;
/*     */   private boolean m_shrinkable;
/*     */   private boolean m_expandable;
/*     */   private boolean m_widthInit = false;
/*     */   private boolean m_heightInit = false;
/*     */   private boolean m_shrinkableInit = false;
/*     */   private boolean m_expandableInit = false;
/*     */   
/*     */   public void add(IThemeElement elem) {}
/*     */   
/*     */   public boolean isExpandable() {
/*  35 */     return this.m_expandable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpandable(boolean expandable) {
/*  42 */     this.m_expandable = expandable;
/*  43 */     this.m_expandableInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  50 */     return this.m_height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/*  57 */     this.m_height = height;
/*  58 */     this.m_heightInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShrinkable() {
/*  65 */     return this.m_shrinkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShrinkable(boolean shrinkable) {
/*  72 */     this.m_shrinkable = shrinkable;
/*  73 */     this.m_shrinkableInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  80 */     return this.m_width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/*  87 */     this.m_width = width;
/*  88 */     this.m_widthInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExpandableInit() {
/*  95 */     return this.m_expandableInit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHeightInit() {
/* 102 */     return this.m_heightInit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShrinkableInit() {
/* 109 */     return this.m_shrinkableInit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWidthInit() {
/* 116 */     return this.m_widthInit;
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeAttributes attribute) {
/* 120 */     if (attribute == null) {
/*     */       return;
/*     */     }
/* 123 */     if (this.m_expandableInit) attribute.setExpandable(this.m_expandable); 
/* 124 */     if (this.m_shrinkableInit) attribute.setShrinkable(this.m_shrinkable); 
/* 125 */     if (this.m_heightInit) attribute.setHeight(this.m_height); 
/* 126 */     if (this.m_widthInit) attribute.setWidth(this.m_width);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 134 */     ThemeAttributes attribute = new ThemeAttributes();
/*     */     
/* 136 */     copyAttributes(attribute);
/*     */     
/* 138 */     return attribute;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeAttributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */