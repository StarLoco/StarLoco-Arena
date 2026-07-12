/*     */ package com.ankamagames.xulor.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public abstract class StyledTextParser
/*     */ {
/*     */   private static final String PLAIN_STYLE = "plain";
/*     */   private static final String BOLD_STYLE = "bold";
/*     */   private static final String ITALIC_STYLE = "italic";
/*     */   private static final String BOLDITALIC_STYLE = "bolditalic";
/*  22 */   private static final Pattern textDataPattern = Pattern.compile("(<(([a-zA-Z0-9 =,]+)[|])?([^<>]+)>)|([^<>]+)");
/*     */   
/*  24 */   private static final Pattern fontPattern = Pattern.compile("font=([\\w\b]+)");
/*  25 */   private static final Pattern stylePattern = Pattern.compile("style=(plain|bold|italic|bolditalic)");
/*  26 */   private static final Pattern sizePattern = Pattern.compile("size=([0-9]+)");
/*  27 */   private static final Pattern colorPattern = Pattern.compile("color=([0-9A-Fa-f]{6})");
/*     */   
/*     */ 
/*     */   private Font m_defaultFont;
/*     */   
/*     */ 
/*     */ 
/*     */   public static void parse(String textExpression, StyledTextParserHandler handler)
/*     */   {
/*  36 */     Matcher textDatamatcher = textDataPattern.matcher(textExpression);
/*  37 */     textDatamatcher.reset();
/*  38 */     while (textDatamatcher.find())
/*     */     {
/*  40 */       String attributes = textDatamatcher.group(3);
/*  41 */       String text = textDatamatcher.group(4);
/*  42 */       if (text == null) {
/*  43 */         text = textDatamatcher.group(5);
/*     */       }
/*     */       
/*     */ 
/*  47 */       Font font = handler.getFont();
/*  48 */       Color color = null;
/*  49 */       if (attributes != null)
/*     */       {
/*     */ 
/*  52 */         Matcher fontMatcher = fontPattern.matcher(attributes);
/*  53 */         if (fontMatcher.find()) {
/*  54 */           String fontName = fontMatcher.group(1);
/*  55 */           font = font.setFontName(fontName);
/*     */         }
/*     */         
/*     */ 
/*  59 */         Matcher styleMatcher = stylePattern.matcher(attributes);
/*  60 */         if ((styleMatcher.find()) && 
/*  61 */           (font != null)) {
/*  62 */           String style = styleMatcher.group(1);
/*  63 */           if (style.equals("plain")) {
/*  64 */             font = font.setStyle(0);
/*  65 */           } else if (style.equals("bold")) {
/*  66 */             font = font.setStyle(1);
/*  67 */           } else if (style.equals("italic")) {
/*  68 */             font = font.setStyle(2);
/*  69 */           } else if (style.equals("bolditalic")) {
/*  70 */             font = font.setStyle(3);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*  76 */         Matcher sizeMatcher = sizePattern.matcher(attributes);
/*  77 */         if ((sizeMatcher.find()) && 
/*  78 */           (font != null)) {
/*  79 */           int fontSize = Integer.parseInt(sizeMatcher.group(1));
/*  80 */           font = font.setSize(fontSize);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*  85 */         Matcher colorMatcher = colorPattern.matcher(attributes);
/*  86 */         if (colorMatcher.find()) {
/*  87 */           int colorValue = Integer.parseInt(colorMatcher.group(1), 16);
/*  88 */           color = new Color(colorValue);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  94 */       handler.append(text, font, color);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Font getDefaultFont()
/*     */   {
/* 102 */     return this.m_defaultFont;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDefaultFont(Font defaultFont)
/*     */   {
/* 109 */     this.m_defaultFont = defaultFont;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\StyledTextParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */