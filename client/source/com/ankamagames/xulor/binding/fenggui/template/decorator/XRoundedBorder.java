/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeRoundedBorder;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.RoundedBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRoundedBorder
/*     */   extends XBorder
/*     */ {
/*  20 */   private RoundedBorder m_roundedBorder = null;
/*     */   
/*     */   public static final String TAG = "RoundedBorder";
/*     */   
/*  24 */   private Color m_color = Color.RED;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_width;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int m_radius;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  45 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*     */       return;
/*     */     }
/*  48 */     if (this.m_roundedBorder == null) {
/*  49 */       this.m_roundedBorder = new RoundedBorder(FengguiConstant.toFengguiColor(this.m_color), this.m_width, this.m_radius);
/*     */       
/*  51 */       ((XDecoratorAppearance)this.m_parent).addBorder(this);
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
/*  62 */     IElement[] components = getChildren(); byte b; int i;
/*     */     IElement[] arrayOfIElement1;
/*  64 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/*  65 */       c.buildXML();
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRadius() {
/*  74 */     return this.m_radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadius(int radius) {
/*  82 */     this.m_radius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  90 */     return this.m_width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/*  98 */     this.m_width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 105 */     return this.m_color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 113 */     this.m_color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/* 122 */     return (Border)this.m_roundedBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 130 */     return "RoundedBorder";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 138 */     XRoundedBorder elem = (XRoundedBorder)element;
/* 139 */     elem.m_color = this.m_color;
/* 140 */     elem.m_radius = this.m_radius;
/* 141 */     elem.m_width = this.m_width;
/* 142 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 149 */     XRoundedBorder elem = new XRoundedBorder();
/* 150 */     copyElementData((IElement)elem);
/* 151 */     return (IElement)elem;
/*     */   }
/*     */   
/*     */   public static RoundedBorder getRoundedBorder(ThemeRoundedBorder theme) {
/* 155 */     if (theme == null) {
/* 156 */       return null;
/*     */     }
/*     */     
/* 159 */     RoundedBorder border = new RoundedBorder(FengguiConstant.toFengguiColor(theme.getColor()), theme.getWidth(), theme.getRadius());
/* 160 */     border.setEnabled(theme.isEnabled());
/* 161 */     return border;
/*     */   }
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 165 */     ThemeRoundedBorder border = new ThemeRoundedBorder();
/*     */     
/* 167 */     border.setColor(this.m_color);
/* 168 */     border.setRadius(this.m_radius);
/* 169 */     border.setWidth(this.m_width);
/* 170 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 171 */     border.setEnabled(this.m_enabled);
/* 172 */     border.setSpacing(this.m_spacing);
/*     */     
/* 174 */     return (ThemeBorder)border;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XRoundedBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */