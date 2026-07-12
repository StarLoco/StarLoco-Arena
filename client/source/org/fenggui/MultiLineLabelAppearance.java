/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.render.Graphics;
/*    */ import org.fenggui.render.IOpenGL;
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
/*    */ 
/*    */ public class MultiLineLabelAppearance
/*    */   extends LabelAppearance
/*    */ {
/* 29 */   private MultiLineLabel label = null;
/*    */ 
/*    */   
/*    */   public MultiLineLabelAppearance(MultiLineLabel w) {
/* 33 */     super(w);
/* 34 */     this.label = w;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension getContentMinSizeHint() {
/* 40 */     String[] text = this.label.getTextArray();
/*    */     
/* 42 */     if (text == null) {
/* 43 */       return new Dimension(0, 0);
/*    */     }
/* 45 */     int width = 0; byte b; int i; String[] arrayOfString1;
/* 46 */     for (arrayOfString1 = text, b = 0, i = arrayOfString1.length; b < i; ) { String s = arrayOfString1[b];
/* 47 */       int length = getFont().getWidth(s);
/* 48 */       if (width < length)
/* 49 */         width = length; 
/*    */       b++; }
/*    */     
/* 52 */     int height = text.length * getFont().getHeight();
/*    */     
/* 54 */     return new Dimension(width, height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintContent(Graphics g, IOpenGL gl) {
/* 60 */     String[] text = this.label.getTextArray();
/* 61 */     if (text != null && text.length > 0) {
/*    */       
/* 63 */       g.setFont(getFont());
/*    */       
/* 65 */       if (getTextColor() != null) g.setColor(getTextColor());
/*    */       
/* 67 */       int x = 0;
/* 68 */       int y = getAlignment().alignY(getContentHeight(), text.length * getFont().getHeight());
/*    */       
/* 70 */       for (int i = text.length - 1; i >= 0; i--) {
/*    */         
/* 72 */         String toDraw = text[i];
/* 73 */         if (toDraw.length() > 0) {
/*    */           
/* 75 */           x = getAlignment().alignX(getContentWidth(), getFont().getWidth(toDraw));
/* 76 */           g.drawString(toDraw, x, y);
/* 77 */           y += getFont().getHeight();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\MultiLineLabelAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */