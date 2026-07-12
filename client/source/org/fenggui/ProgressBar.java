/*    */ package org.fenggui;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressBar
/*    */   extends StandardWidget
/*    */ {
/* 30 */   private double value = 0.5D;
/* 31 */   private String text = "Working...";
/*    */   
/* 33 */   private ProgressBarAppearance appearance = null;
/*    */ 
/*    */   
/*    */   public ProgressBar(String text) {
/* 37 */     setText(text);
/* 38 */     this.appearance = new ProgressBarAppearance(this);
/* 39 */     setupTheme(ProgressBar.class);
/* 40 */     updateMinSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public ProgressBar() {
/* 45 */     this((String)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue() {
/* 50 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(double value) {
/* 56 */     if (value > 1.0D) value = 1.0D; 
/* 57 */     if (value < 0.0D) value = 0.0D; 
/* 58 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHorizontal() {
/* 63 */     return true;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 67 */     return this.text;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setText(String text) {
/* 72 */     this.text = text;
/* 73 */     updateMinSize();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ProgressBarAppearance getAppearance() {
/* 79 */     return this.appearance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ProgressBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */