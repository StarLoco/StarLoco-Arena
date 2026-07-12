/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.theme.ThemeGradientBackground;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.GradientBackground;
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
/*     */ public class XGradientBackground
/*     */   extends XBackground
/*     */ {
/*     */   public static final String TAG = "GradientBackground";
/*  26 */   private GradientBackground m_gradientBackground = null;
/*     */   
/*  28 */   private Color m_topLeft = Color.LIGHT_GRAY;
/*  29 */   private Color m_topRight = Color.DARK_GRAY;
/*  30 */   private Color m_bottomLeft = Color.RED;
/*  31 */   private Color m_bottomRight = Color.GRAY;
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
/*     */   public void buildGUI()
/*     */   {
/*  46 */     if (!(this.m_parent instanceof XDecoratorAppearance)) {
/*  47 */       return;
/*     */     }
/*  49 */     if (this.m_gradientBackground == null) {
/*  50 */       this.m_gradientBackground = new GradientBackground(FengguiConstant.toFengguiColor(this.m_bottomLeft), 
/*  51 */         FengguiConstant.toFengguiColor(this.m_bottomRight), 
/*  52 */         FengguiConstant.toFengguiColor(this.m_topRight), 
/*  53 */         FengguiConstant.toFengguiColor(this.m_topLeft));
/*     */       
/*  55 */       ((XDecoratorAppearance)this.m_parent).addBackground(this);
/*     */     }
/*     */   }
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
/*     */   public Color getBottomLeft()
/*     */   {
/*  71 */     return this.m_bottomLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBottomLeft(Color bottomLeft)
/*     */   {
/*  78 */     this.m_bottomLeft = bottomLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getBottomRight()
/*     */   {
/*  85 */     return this.m_bottomRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBottomRight(Color bottomRight)
/*     */   {
/*  92 */     this.m_bottomRight = bottomRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTopLeft()
/*     */   {
/*  99 */     return this.m_topLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTopLeft(Color topLeft)
/*     */   {
/* 106 */     this.m_topLeft = topLeft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTopRight()
/*     */   {
/* 113 */     return this.m_topRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTopRight(Color topRight)
/*     */   {
/* 120 */     this.m_topRight = topRight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Background getBackground()
/*     */   {
/* 128 */     return this.m_gradientBackground;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 136 */     return "GradientBackground";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 144 */     XGradientBackground elem = (XGradientBackground)element;
/* 145 */     elem.m_bottomLeft = this.m_bottomLeft;
/* 146 */     elem.m_bottomRight = this.m_bottomRight;
/* 147 */     elem.m_topLeft = this.m_topLeft;
/* 148 */     elem.m_topRight = this.m_topRight;
/* 149 */     elem.setEnabled(this.m_enabled);
/* 150 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 157 */     XGradientBackground elem = new XGradientBackground();
/* 158 */     copyElementData(elem);
/* 159 */     return elem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static GradientBackground getGradientBackground(ThemeGradientBackground theme)
/*     */   {
/* 168 */     if (theme == null) {
/* 169 */       return null;
/*     */     }
/* 171 */     GradientBackground background = new GradientBackground(FengguiConstant.toFengguiColor(theme.getBottomLeft()), 
/* 172 */       FengguiConstant.toFengguiColor(theme.getBottomRight()), 
/* 173 */       FengguiConstant.toFengguiColor(theme.getTopRight()), 
/* 174 */       FengguiConstant.toFengguiColor(theme.getTopLeft()));
/* 175 */     background.setEnabled(theme.isEnabled());
/* 176 */     return background;
/*     */   }
/*     */   
/*     */   public ThemeBackground toThemeBackground() {
/* 180 */     ThemeGradientBackground bg = new ThemeGradientBackground();
/* 181 */     bg.setBottomLeft(this.m_bottomLeft);
/* 182 */     bg.setBottomRight(this.m_bottomRight);
/* 183 */     bg.setTopLeft(this.m_topLeft);
/* 184 */     bg.setTopRight(this.m_topRight);
/* 185 */     bg.setEnabled(this.m_enabled);
/* 186 */     return bg;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XGradientBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */