/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemePlainBorder;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.PlainBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPlainBorder
/*     */   extends XBorder
/*     */ {
/*  20 */   private PlainBorder m_plainBorder = null;
/*     */   
/*     */   public static final String TAG = "PlainBorder";
/*     */   
/*  24 */   private Color m_color = Color.RED;
/*     */ 
/*     */ 
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
/*     */   
/*     */   public void buildGUI() {
/*  42 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*     */       return;
/*     */     }
/*  45 */     if (this.m_plainBorder == null) {
/*  46 */       this.m_plainBorder = new PlainBorder(FengguiConstant.toFengguiSpacing(this.m_spacing), 
/*  47 */           FengguiConstant.toFengguiColor(this.m_color));
/*     */       
/*  49 */       ((XDecoratorAppearance)this.m_parent).addBorder(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  60 */     IElement[] components = getChildren(); byte b; int i;
/*     */     IElement[] arrayOfIElement1;
/*  62 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/*  63 */       c.buildXML();
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  72 */     return this.m_color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/*  80 */     this.m_color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/*  89 */     return (Border)this.m_plainBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/*  97 */     XPlainBorder elem = (XPlainBorder)element;
/*  98 */     elem.m_color = this.m_color;
/*  99 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 106 */     XPlainBorder elem = new XPlainBorder();
/* 107 */     copyElementData((IElement)elem);
/* 108 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 116 */     return "PlainBorder";
/*     */   }
/*     */   
/*     */   public static PlainBorder getPlainBorder(ThemePlainBorder theme) {
/* 120 */     if (theme == null) {
/* 121 */       return null;
/*     */     }
/*     */     
/* 124 */     PlainBorder border = new PlainBorder(FengguiConstant.toFengguiSpacing(theme.getSpacing()), 
/* 125 */         FengguiConstant.toFengguiColor(theme.getColor()));
/* 126 */     border.setEnabled(theme.isEnabled());
/* 127 */     return border;
/*     */   }
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 131 */     ThemePlainBorder border = new ThemePlainBorder();
/* 132 */     border.setColor(this.m_color);
/* 133 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 134 */     border.setEnabled(this.m_enabled);
/* 135 */     border.setSpacing(this.m_spacing);
/* 136 */     return (ThemeBorder)border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XPlainBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */