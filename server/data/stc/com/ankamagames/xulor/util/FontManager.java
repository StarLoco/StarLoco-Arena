/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontManager
/*    */ {
/* 21 */   private static final FontManager m_fontManager = new FontManager();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 26 */   private static int MAX_UNUSED_FONT_LIFESPAN = 300;
/*    */   
/*    */ 
/* 29 */   private ArrayList<FontManagerTime> m_toDelete = new ArrayList();
/*    */   
/* 31 */   private ArrayList<FontManagerTime> m_fonts = new ArrayList();
/*    */   
/*    */   private class FontManagerTime {
/* 34 */     long m_time = 0L;
/* 35 */     Font m_font = null;
/*    */     
/*    */     public FontManagerTime(int time, Font font) {
/* 38 */       this.m_time = time;
/* 39 */       this.m_font = font;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static FontManager getInstance()
/*    */   {
/* 47 */     return m_fontManager;
/*    */   }
/*    */   
/*    */   public Font getFont(java.awt.Font awtFont) {
/* 51 */     return getFont(awtFont, false);
/*    */   }
/*    */   
/*    */   public Font getFont(java.awt.Font awtFont, boolean antialiased) {
/* 55 */     FontManagerTime ret = null;
/*    */     FontManagerTime fmt;
/* 57 */     for (Iterator localIterator = this.m_fonts.iterator(); localIterator.hasNext();) { fmt = (FontManagerTime)localIterator.next();
/* 58 */       Font font = fmt.m_font;
/* 59 */       if ((font.getAWTFont().equals(awtFont)) && (font.isAntialiased() == antialiased)) {
/* 60 */         ret = fmt;
/* 61 */         break;
/*    */       }
/*    */     }
/*    */     
/* 65 */     if (ret == null) {
/* 66 */       ret = new FontManagerTime((int)(System.nanoTime() / 1.0E9D), new Font(awtFont, antialiased));
/* 67 */       this.m_fonts.add(ret);
/* 68 */       fmt = ret.m_font;
/*    */     }
/*    */     
/* 71 */     ret.m_time = ((int)(System.nanoTime() / 1.0E9D));
/* 72 */     return ret.m_font;
/*    */   }
/*    */   
/*    */   public void addFont(Font font) {
/* 76 */     this.m_fonts.add(new FontManagerTime((int)(System.nanoTime() / 1.0E9D), font));
/* 77 */     cleanFonts();
/*    */   }
/*    */   
/*    */   public void removeFont(Font font) {
/* 81 */     this.m_fonts.remove(font);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private void cleanFonts()
/*    */   {
/* 89 */     int currentTime = (int)(System.nanoTime() / 1.0E9D);
/*    */     
/* 91 */     for (FontManagerTime fmt : this.m_fonts) {
/* 92 */       if (currentTime - fmt.m_time > MAX_UNUSED_FONT_LIFESPAN) {
/* 93 */         this.m_toDelete.add(fmt);
/*    */       }
/*    */     }
/*    */     
/* 97 */     this.m_fonts.removeAll(this.m_toDelete);
/*    */     
/* 99 */     this.m_toDelete.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\FontManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */