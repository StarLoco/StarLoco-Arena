/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.render.Font;
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
/*    */ import org.fenggui.util.Color;
/*    */ import org.fenggui.util.Dimension;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressBarAppearance
/*    */   extends DecoratorAppearance
/*    */ {
/* 30 */   private ProgressBar bar = null;
/*    */   
/* 32 */   private Font font = Font.getDefaultFont();
/* 33 */   private Color progressBarColor = Color.BLUE;
/* 34 */   private Color textColor = Color.BLACK;
/*    */ 
/*    */   
/*    */   public ProgressBarAppearance(ProgressBar w) {
/* 38 */     super(w);
/* 39 */     this.bar = w;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension getContentMinSizeHint() {
/* 45 */     return new Dimension(0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintContent(Graphics g, IOpenGL gl) {
/* 52 */     g.setColor(this.progressBarColor);
/* 53 */     g.drawFilledRectangle(0, 0, 
/* 54 */         (int)(getContentWidth() * this.bar.getValue()), 
/* 55 */         getContentHeight());
/*    */     
/* 57 */     String s = this.bar.getText();
/* 58 */     if (s != null && s.length() > 0) {
/* 59 */       g.setColor(this.textColor);
/* 60 */       g.setFont(getFont());
/* 61 */       g.drawString(s, (
/* 62 */           getContentWidth() - this.font.getWidth(s)) / 2, 
/* 63 */           getContentHeight() / 2 - this.font.getHeight() / 2);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Color getProgressBarColor() {
/* 68 */     return this.progressBarColor;
/*    */   }
/*    */   
/*    */   public void setProgressBarColor(Color progressBarColor) {
/* 72 */     this.progressBarColor = progressBarColor;
/*    */   }
/*    */   
/*    */   public Color getTextColor() {
/* 76 */     return this.textColor;
/*    */   }
/*    */   
/*    */   public void setTextColor(Color textColor) {
/* 80 */     this.textColor = textColor;
/*    */   }
/*    */   
/*    */   public Font getFont() {
/* 84 */     return this.font;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFont(Font font) {
/* 89 */     this.font = font;
/* 90 */     this.bar.updateMinSize();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ProgressBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */