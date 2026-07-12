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
/*     */ 
/*     */ public class ThemeTextEditorAppearance
/*     */   extends ThemeAppearance
/*     */   implements IThemeElement, IFontable
/*     */ {
/*     */   public static final String TAG = "TextEditorAppearance";
/*     */   public static final String TEXT_COLOR = "text";
/*     */   public static final String SELECTION_COLOR = "selection";
/*     */   public static final String CURSOR_COLOR = "cursor";
/*  24 */   private Color m_textColor = null;
/*  25 */   private Color m_selectionColor = null;
/*  26 */   private Color m_cursorColor = null;
/*  27 */   private Font m_font = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IThemeElement elem) {
/*  33 */     if (elem instanceof ThemeNamedColor) {
/*  34 */       ThemeNamedColor namedColor = (ThemeNamedColor)elem;
/*  35 */       if ("text".equalsIgnoreCase(namedColor.getName())) {
/*  36 */         this.m_textColor = namedColor.getColor();
/*  37 */       } else if ("selection".equalsIgnoreCase(namedColor.getName())) {
/*  38 */         this.m_selectionColor = namedColor.getColor();
/*  39 */       } else if ("cursor".equalsIgnoreCase(namedColor.getName())) {
/*  40 */         this.m_cursorColor = namedColor.getColor();
/*     */       } 
/*     */     } 
/*  43 */     super.add(elem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/*  50 */     return this.m_font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/*  57 */     this.m_font = font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSelectionColor() {
/*  64 */     return this.m_selectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionColor(Color selectionColor) {
/*  71 */     this.m_selectionColor = selectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getCursorColor() {
/*  78 */     return this.m_cursorColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorColor(Color cursorColor) {
/*  85 */     this.m_cursorColor = cursorColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/*  92 */     return this.m_textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/*  99 */     this.m_textColor = textColor;
/*     */   }
/*     */   
/*     */   protected void copyAttributes(ThemeTextEditorAppearance app) {
/* 103 */     copyAttributes(app);
/* 104 */     app.setFont(this.m_font);
/* 105 */     app.setSelectionColor(this.m_selectionColor);
/* 106 */     app.setTextColor(this.m_textColor);
/* 107 */     app.setCursorColor(this.m_cursorColor);
/*     */   }
/*     */   
/*     */   public IThemeElement cloneAppearance() {
/* 111 */     ThemeTextEditorAppearance app = new ThemeTextEditorAppearance();
/*     */     
/* 113 */     copyAttributes(app);
/*     */     
/* 115 */     return app;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeTextEditorAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */