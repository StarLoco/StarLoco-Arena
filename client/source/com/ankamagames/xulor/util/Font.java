/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Font
/*    */ {
/* 15 */   private java.awt.Font m_font = null;
/* 16 */   private Object m_renderedFont = null;
/*    */   private boolean m_antialiased = false;
/*    */   
/*    */   public Font() {
/* 20 */     this(null, false);
/*    */   }
/*    */   
/*    */   public Font(java.awt.Font font) {
/* 24 */     this(font, false);
/*    */   }
/*    */   
/*    */   public Font(java.awt.Font font, boolean antialiased) {
/* 28 */     if (font != null) {
/* 29 */       this.m_font = font;
/*    */     } else {
/* 31 */       this.m_font = java.awt.Font.decode("---");
/*    */     } 
/* 33 */     this.m_antialiased = antialiased;
/*    */   }
/*    */   
/*    */   public Object getRenderedFont() {
/* 37 */     return this.m_renderedFont;
/*    */   }
/*    */   
/*    */   public void setRenderedFont(Object renderedFont) {
/* 41 */     this.m_renderedFont = renderedFont;
/*    */   }
/*    */   
/*    */   public java.awt.Font getAWTFont() {
/* 45 */     return this.m_font;
/*    */   }
/*    */   
/*    */   public Font setSize(float size) {
/* 49 */     Font newFont = null;
/* 50 */     if (this.m_font != null) {
/* 51 */       java.awt.Font awtFont = this.m_font.deriveFont(size);
/* 52 */       newFont = FontManager.getInstance().getFont(awtFont, this.m_antialiased);
/*    */     } 
/* 54 */     return newFont;
/*    */   }
/*    */   
/*    */   public Font setStyle(int style) {
/* 58 */     Font newFont = null;
/* 59 */     if (this.m_font != null) {
/* 60 */       java.awt.Font awtFont = this.m_font.deriveFont(style);
/* 61 */       newFont = FontManager.getInstance().getFont(awtFont, this.m_antialiased);
/*    */     } 
/* 63 */     return newFont;
/*    */   }
/*    */   
/*    */   public Font setFontName(String name) {
/* 67 */     int style = this.m_font.getStyle();
/* 68 */     float size = this.m_font.getSize();
/* 69 */     java.awt.Font awtFont = java.awt.Font.decode(String.valueOf(name) + "--");
/* 70 */     awtFont = awtFont.deriveFont(style, size);
/* 71 */     return FontManager.getInstance().getFont(awtFont, this.m_antialiased);
/*    */   }
/*    */   
/*    */   public boolean isAntialiased() {
/* 75 */     return this.m_antialiased;
/*    */   }
/*    */   
/*    */   public Font setAntialiased(boolean antialiased) {
/* 79 */     return FontManager.getInstance().getFont(this.m_font, this.m_antialiased);
/*    */   }
/*    */   
/*    */   public Font clone() {
/* 83 */     return new Font(this.m_font, this.m_antialiased);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return this.m_font.toString();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Font.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */