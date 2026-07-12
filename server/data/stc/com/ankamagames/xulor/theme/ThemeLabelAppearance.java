/*     */ package com.ankamagames.xulor.theme;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import com.ankamagames.xulor.util.Font;
/*     */ import com.ankamagames.xulor.util.Pixmap;
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
/*     */ public class ThemeLabelAppearance
/*     */   extends ThemeAppearance
/*     */   implements IThemeElement, IFontable
/*     */ {
/*     */   public static final String TAG = "LabelAppearance";
/*  22 */   private int m_gap = 0;
/*  23 */   private Color m_textColor = Color.BLACK;
/*  24 */   private Font m_font = null;
/*  25 */   private Alignment m_alignment = Alignment.CENTER;
/*  26 */   private Pixmap m_pixmap = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getAlignment()
/*     */   {
/*  32 */     return this.m_alignment;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAlignment(Alignment alignment)
/*     */   {
/*  38 */     this.m_alignment = alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Font getFont()
/*     */   {
/*  46 */     return this.m_font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/*  54 */     this.m_font = font;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getGap()
/*     */   {
/*  60 */     return this.m_gap;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setGap(int gap)
/*     */   {
/*  66 */     this.m_gap = gap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Color getTextColor()
/*     */   {
/*  73 */     return this.m_textColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Pixmap getPixmap()
/*     */   {
/*  80 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(IThemeElement elem)
/*     */   {
/*  88 */     if ((elem instanceof ThemeColor)) {
/*  89 */       this.m_textColor = ((ThemeColor)elem).getColor();
/*  90 */     } else if ((elem instanceof ThemePixmap)) {
/*  91 */       this.m_pixmap = ((ThemePixmap)elem).getPixmap();
/*     */     } else {
/*  93 */       super.add(elem);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeLabelAppearance app) {
/*  98 */     super.copyAttributes(app);
/*  99 */     app.setAlignment(this.m_alignment);
/* 100 */     app.setFont(this.m_font);
/* 101 */     app.setGap(this.m_gap);
/* 102 */     if (this.m_pixmap != null) app.setPixmap(this.m_pixmap.clone());
/* 103 */     app.setTextColor(this.m_textColor);
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 107 */     ThemeLabelAppearance app = new ThemeLabelAppearance();
/*     */     
/* 109 */     copyAttributes(app);
/*     */     
/* 111 */     return app;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setPixmap(Pixmap pixmap)
/*     */   {
/* 117 */     this.m_pixmap = pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setTextColor(Color textColor)
/*     */   {
/* 123 */     this.m_textColor = textColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeLabelAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */