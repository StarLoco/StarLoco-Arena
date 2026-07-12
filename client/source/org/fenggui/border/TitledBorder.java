/*     */ package org.fenggui.border;
/*     */ 
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.util.Color;
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
/*     */ public class TitledBorder
/*     */   extends Border
/*     */ {
/*  33 */   private String title = "";
/*  34 */   private Color color = Color.GRAY; private Color textColor = Color.BLACK;
/*  35 */   private Font font = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TitledBorder() {
/*  43 */     this(Font.getDefaultFont(), "");
/*     */   }
/*     */ 
/*     */   
/*     */   public TitledBorder(String title) {
/*  48 */     this(Font.getDefaultFont(), title, Color.BLACK);
/*     */   }
/*     */ 
/*     */   
/*     */   public TitledBorder(Font font, String title) {
/*  53 */     this(font, title, Color.BLACK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TitledBorder(Font font, String title, Color textColor) {
/*  59 */     super(font.getHeight(), 1, 1, 1);
/*  60 */     this.font = font;
/*  61 */     setTitle(title);
/*  62 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  68 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/*  74 */     this.color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  80 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/*  86 */     this.title = title;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/*  91 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/*  96 */     return this.textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 102 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 109 */     g.setColor(this.textColor);
/* 110 */     g.setFont(this.font);
/* 111 */     g.drawString(this.title, localX + 10, localY + height - this.font.getHeight());
/*     */     
/* 113 */     g.setColor(this.color);
/*     */     
/* 115 */     byte b = 2;
/*     */ 
/*     */     
/* 118 */     g.setLineWidth(1.0F);
/* 119 */     g.drawLine(
/* 120 */         localX, localY, 
/* 121 */         localX, localY + height - this.font.getHeight() / 2 - 2);
/*     */ 
/*     */     
/* 124 */     g.drawLine(
/* 125 */         localX + width - getRight(), localY, 
/* 126 */         localX + width - getRight(), localY + height - this.font.getHeight() / 2 - 2 + 1);
/*     */ 
/*     */     
/* 129 */     g.drawLine(
/* 130 */         localX, localY + height - this.font.getHeight() / 2 - 2, 
/* 131 */         localX + 5, localY + height - this.font.getHeight() / 2 - 2);
/*     */     
/* 133 */     g.drawLine(
/* 134 */         localX + this.font.getWidth(this.title) + 15, localY + height - this.font.getHeight() / 2 - 2, 
/* 135 */         localX + getLeft() + width, localY + height - this.font.getHeight() / 2 - 2);
/*     */ 
/*     */     
/* 138 */     g.drawLine(
/* 139 */         localX, localY, 
/* 140 */         getLeft() + width + getRight(), localY);
/*     */ 
/*     */     
/* 143 */     g.setLineWidth(1.0F);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\TitledBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */