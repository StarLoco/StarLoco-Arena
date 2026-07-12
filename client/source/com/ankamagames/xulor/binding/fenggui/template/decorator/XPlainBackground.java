/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.theme.ThemePlainBackground;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.PlainBackground;
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
/*     */ public class XPlainBackground
/*     */   extends XBackground
/*     */ {
/*  24 */   private PlainBackground m_plainBackground = null;
/*     */   
/*     */   public static final String TAG = "PlainBackground";
/*     */   
/*  28 */   private Color m_color = Color.LIGHT_GRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  43 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*     */       return;
/*     */     }
/*  46 */     if (this.m_plainBackground == null) {
/*     */       
/*  48 */       this.m_plainBackground = new PlainBackground(FengguiConstant.toFengguiColor(this.m_color));
/*     */       
/*  50 */       ((XDecoratorAppearance)this.m_parent).addBackground(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  66 */     return this.m_color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/*  73 */     this.m_color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Background getBackground() {
/*  81 */     return (Background)this.m_plainBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/*  89 */     XPlainBackground elem = (XPlainBackground)element;
/*  90 */     elem.m_color = this.m_color;
/*  91 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/*  98 */     XPlainBackground elem = new XPlainBackground();
/*  99 */     copyElementData((IElement)elem);
/* 100 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 108 */     return "PlainBackground";
/*     */   }
/*     */   
/*     */   public static PlainBackground getPlainBackground(ThemePlainBackground theme) {
/* 112 */     if (theme == null) {
/* 113 */       return null;
/*     */     }
/*     */     
/* 116 */     PlainBackground background = new PlainBackground(FengguiConstant.toFengguiColor(theme.getColor()));
/* 117 */     background.setEnabled(theme.isEnabled());
/* 118 */     return background;
/*     */   }
/*     */   
/*     */   public ThemeBackground toThemeBackground() {
/* 122 */     ThemePlainBackground bg = new ThemePlainBackground();
/* 123 */     bg.setColor(this.m_color);
/* 124 */     bg.setEnabled(this.m_enabled);
/*     */     
/* 126 */     return (ThemeBackground)bg;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPlainBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */