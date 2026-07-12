/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Spacing;
/*     */ import java.util.ArrayList;
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
/*     */ public class ThemeElement
/*     */   implements IThemeElement
/*     */ {
/*     */   public static final String TAG = "ThemeElement";
/*  20 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList<ThemeAppearance>();
/*  21 */   private ThemeAttributes m_attributes = null;
/*  22 */   private String m_name = null;
/*     */   
/*     */   private ThemeSpacing m_padding;
/*     */   
/*     */   private ThemeSpacing m_margin;
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  30 */     if (elem instanceof ThemeAttributes) {
/*  31 */       this.m_attributes = (ThemeAttributes)elem;
/*  32 */     } else if (elem instanceof ThemeAppearance) {
/*  33 */       this.m_appearances.add((ThemeAppearance)elem);
/*  34 */     } else if (elem instanceof ThemeMargin) {
/*  35 */       this.m_margin = (ThemeMargin)elem;
/*  36 */     } else if (elem instanceof ThemePadding) {
/*  37 */       this.m_padding = (ThemePadding)elem;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPadding(ThemeSpacing spacing) {
/*  42 */     this.m_padding = spacing;
/*     */   }
/*     */   
/*     */   public void setPadding(Spacing spacing) {
/*  46 */     if (this.m_padding == null) {
/*  47 */       this.m_padding = new ThemePadding();
/*     */     }
/*  49 */     this.m_padding.setSpacing(spacing);
/*     */   }
/*     */ 
/*     */   
/*     */   public Spacing getPadding() {
/*  54 */     if (this.m_padding == null) {
/*  55 */       return null;
/*     */     }
/*  57 */     return this.m_padding.getSpacing();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMargin(ThemeSpacing spacing) {
/*  62 */     this.m_margin = spacing;
/*     */   }
/*     */   
/*     */   public void setMargin(Spacing spacing) {
/*  66 */     if (this.m_margin == null) {
/*  67 */       this.m_margin = new ThemeMargin();
/*     */     }
/*  69 */     this.m_margin.setSpacing(spacing);
/*     */   }
/*     */   
/*     */   public Spacing getMargin() {
/*  73 */     if (this.m_margin == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     return this.m_margin.getSpacing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ThemeAppearance> getAppearances() {
/*  84 */     return this.m_appearances;
/*     */   }
/*     */   
/*     */   public ThemeAppearance getThemeAppearance(String state) {
/*  88 */     for (ThemeAppearance app : this.m_appearances) {
/*  89 */       if (app.getState().equalsIgnoreCase(state)) {
/*  90 */         return app;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppearances(ArrayList<ThemeAppearance> appearances) {
/* 101 */     this.m_appearances = appearances;
/*     */   }
/*     */   
/*     */   public void addThemeAppearance(ThemeAppearance appearance) {
/* 105 */     if (appearance == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     if (this.m_appearances == null) {
/* 110 */       this.m_appearances = new ArrayList<ThemeAppearance>();
/*     */     }
/*     */     
/* 113 */     this.m_appearances.add(appearance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThemeAttributes getAttributes() {
/* 120 */     return this.m_attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributes(ThemeAttributes attributes) {
/* 127 */     this.m_attributes = attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 134 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 141 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeElement elem) {
/* 145 */     for (ThemeAppearance app : this.m_appearances) {
/* 146 */       if (app != null) elem.add(app.cloneAppearance()); 
/*     */     } 
/* 148 */     elem.setName(this.m_name);
/* 149 */     if (this.m_margin != null) elem.setMargin((ThemeMargin)this.m_margin.cloneAppearance()); 
/* 150 */     if (this.m_padding != null) elem.setPadding((ThemePadding)this.m_padding.cloneAppearance()); 
/* 151 */     if (this.m_attributes != null) elem.setAttributes((ThemeAttributes)this.m_attributes.cloneAppearance());
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 158 */     ThemeElement elem = new ThemeElement();
/*     */     
/* 160 */     copyAttributes(elem);
/*     */     
/* 162 */     return elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */