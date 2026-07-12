/*     */ package org.fenggui;
/*     */ 
/*     */ import java.text.BreakIterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiLineLabel
/*     */   extends Label
/*     */ {
/*  34 */   private int maxCharactersPerLine = 50;
/*     */   
/*     */   private String[] text;
/*     */ 
/*     */   
/*     */   public MultiLineLabel() {
/*  40 */     initializeAppearance();
/*  41 */     setupTheme(MultiLineLabel.class);
/*  42 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeAppearance() {
/*  52 */     setAppearance(new MultiLineLabelAppearance(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiLineLabelAppearance getAppearance() {
/*  62 */     return (MultiLineLabelAppearance)super.getAppearance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAppearance(MultiLineLabelAppearance app) {
/*  67 */     setAppearance(app);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getTextArray() {
/*  72 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  78 */     if (text == null)
/*     */       return; 
/*  80 */     String line = wrapText(text, this.maxCharactersPerLine);
/*  81 */     this.text = line.split("\n");
/*     */     
/*  83 */     updateMinSize();
/*     */   }
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
/*     */   public static String wrapText(String toWrap, int maxLength) {
/*  97 */     if (maxLength == 0) throw new IllegalArgumentException("maxLength must not be 0");
/*     */     
/*  99 */     StringBuffer ret = new StringBuffer();
/* 100 */     BreakIterator boundary = BreakIterator.getLineInstance();
/* 101 */     boundary.setText(toWrap);
/* 102 */     int realEnd = -1;
/* 103 */     int start = boundary.first();
/* 104 */     int end = boundary.next();
/*     */     
/* 106 */     int lineLength = 0;
/*     */     
/* 108 */     while (end != -1) {
/*     */       
/* 110 */       int charCount = end - start - 1;
/*     */       
/* 112 */       if (charCount > maxLength) {
/*     */         
/* 114 */         realEnd = end;
/* 115 */         end = start + maxLength;
/*     */       } 
/* 117 */       String word = toWrap.substring(start, end);
/* 118 */       lineLength += word.length();
/* 119 */       if (lineLength >= maxLength) {
/*     */         
/* 121 */         ret.append("\n");
/* 122 */         lineLength = word.length();
/*     */       } 
/* 124 */       ret.append(word);
/* 125 */       if (realEnd == -1) {
/*     */         
/* 127 */         start = end;
/* 128 */         end = boundary.next();
/*     */         
/*     */         continue;
/*     */       } 
/* 132 */       start = end;
/* 133 */       end = realEnd;
/* 134 */       realEnd = -1;
/*     */     } 
/*     */     
/* 137 */     return ret.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxCharactersPerLine(int maxCharactersPerLine) {
/* 146 */     this.maxCharactersPerLine = maxCharactersPerLine;
/* 147 */     updateMinSize();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\MultiLineLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */