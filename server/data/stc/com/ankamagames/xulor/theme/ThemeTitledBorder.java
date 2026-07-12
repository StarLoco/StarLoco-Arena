/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import com.ankamagames.xulor.util.Font;
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
/*     */ public class ThemeTitledBorder
/*     */   extends ThemeBorder
/*     */   implements IThemeElement, IFontable
/*     */ {
/*     */   private Font m_font;
/*  20 */   private Color m_textColor = Color.BLACK; private Color m_color = Color.GRAY;
/*     */   
/*     */   private String m_title;
/*     */   public static final String TAG = "TitledBorder";
/*     */   
/*     */   public void setColor(Color color)
/*     */   {
/*  27 */     this.m_color = color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTextColor(Color textColor)
/*     */   {
/*  34 */     this.m_textColor = textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/*  41 */     this.m_title = title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(IThemeElement elem)
/*     */   {
/*  48 */     if ((elem instanceof ThemeNamedColor)) {
/*  49 */       ThemeNamedColor tnc = (ThemeNamedColor)elem;
/*  50 */       if (tnc.getName().equalsIgnoreCase("borderColor")) {
/*  51 */         this.m_color = tnc.getColor();
/*  52 */       } else if (tnc.getName().equalsIgnoreCase("textColor")) {
/*  53 */         this.m_color = tnc.getColor();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/*  62 */     return this.m_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTextColor()
/*     */   {
/*  69 */     return this.m_textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  76 */     return this.m_title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Font getFont()
/*     */   {
/*  83 */     return this.m_font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/*  90 */     this.m_font = font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IThemeElement cloneAppearance()
/*     */   {
/*  97 */     ThemeTitledBorder border = new ThemeTitledBorder();
/*     */     
/*  99 */     border.setAsBorderSpacing(this.m_asBorderSpacing);
/* 100 */     border.setColor(this.m_color);
/* 101 */     border.setFont(this.m_font);
/* 102 */     border.setTextColor(this.m_textColor);
/* 103 */     border.setTitle(this.m_title);
/* 104 */     border.setSpacing(this.m_spacing);
/* 105 */     border.setEnabled(this.m_enabled);
/*     */     
/* 107 */     return border;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeTitledBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */