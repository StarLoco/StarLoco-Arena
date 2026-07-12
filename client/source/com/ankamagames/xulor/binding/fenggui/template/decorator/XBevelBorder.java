/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBevelBorder;
/*     */ import com.ankamagames.xulor.theme.ThemeBorder;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.border.BevelBorder;
/*     */ import org.fenggui.border.Border;
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
/*     */ public class XBevelBorder
/*     */   extends XBorder
/*     */ {
/*  25 */   private BevelBorder m_bevelBorder = null;
/*     */   
/*     */   public static final String TAG = "BevelBorder";
/*     */   
/*  29 */   private Color m_elevated = Color.LIGHT_GRAY;
/*  30 */   private Color m_lowered = Color.DARK_GRAY;
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
/*  48 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*     */       return;
/*     */     }
/*  51 */     if (this.m_bevelBorder == null) {
/*  52 */       this.m_bevelBorder = new BevelBorder(FengguiConstant.toFengguiColor(this.m_elevated), 
/*  53 */           FengguiConstant.toFengguiColor(this.m_lowered));
/*     */       
/*  55 */       ((XDecoratorAppearance)this.m_parent).addBorder(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  67 */     IElement[] components = getChildren(); byte b; int i;
/*     */     IElement[] arrayOfIElement1;
/*  69 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/*  70 */       c.buildXML();
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getElevated() {
/*  79 */     return this.m_elevated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElevated(Color elevated) {
/*  87 */     this.m_elevated = elevated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getLowered() {
/*  95 */     return this.m_lowered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLowered(Color lowered) {
/* 103 */     this.m_lowered = lowered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/* 112 */     return (Border)this.m_bevelBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 120 */     XBevelBorder elem = (XBevelBorder)element;
/* 121 */     elem.m_elevated = this.m_elevated;
/* 122 */     elem.m_lowered = this.m_lowered;
/* 123 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 130 */     XBevelBorder elem = new XBevelBorder();
/* 131 */     copyElementData((IElement)elem);
/* 132 */     return (IElement)elem;
/*     */   }
/*     */   
/*     */   public static BevelBorder getBevelBorder(ThemeBevelBorder theme) {
/* 136 */     if (theme == null) {
/* 137 */       return null;
/*     */     }
/*     */     
/* 140 */     BevelBorder border = new BevelBorder(FengguiConstant.toFengguiColor(theme.getElevated()), 
/* 141 */         FengguiConstant.toFengguiColor(theme.getLowered()));
/*     */     
/* 143 */     border.setEnabled(theme.isEnabled());
/* 144 */     return border;
/*     */   }
/*     */   
/*     */   public ThemeBorder toThemeBorder() {
/* 148 */     ThemeBevelBorder border = new ThemeBevelBorder();
/* 149 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 150 */     border.setElevated(this.m_elevated);
/* 151 */     border.setLowered(this.m_lowered);
/* 152 */     border.setSpacing(this.m_spacing);
/* 153 */     border.setEnabled(this.m_enabled);
/* 154 */     return (ThemeBorder)border;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 162 */     return "BevelBorder";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XBevelBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */