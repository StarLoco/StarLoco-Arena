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
/*    */ public class VerticalListAppearance<E>
/*    */   extends DecoratorAppearance
/*    */ {
/* 30 */   private Font font = Font.getDefaultFont();
/* 31 */   private VerticalList<E> vl = null;
/*    */ 
/*    */   
/*    */   public VerticalListAppearance(VerticalList<E> w) {
/* 35 */     super(w);
/* 36 */     this.vl = w;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Dimension getContentMinSizeHint() {
/* 42 */     return new Dimension(0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintContent(Graphics g, IOpenGL gl) {
/* 48 */     int x = 0;
/* 49 */     int y = getContentHeight() + this.font.getHeight();
/*    */     
/* 51 */     int columnCounter = 0;
/*    */     
/* 53 */     g.setFont(this.font);
/* 54 */     g.setColor(Color.BLACK);
/*    */     
/* 56 */     for (ListItem<E> item : this.vl.getItems()) {
/*    */       
/* 58 */       if (item.isSelected()) {
/*    */         
/* 60 */         g.setColor(Color.BLUE);
/* 61 */         g.drawFilledRectangle(x - 5, y, this.vl.getColumnWidth(columnCounter), this.font.getHeight());
/* 62 */         g.setColor(Color.WHITE);
/* 63 */         g.drawString(item.getText(), x, y);
/* 64 */         g.setColor(Color.BLACK);
/*    */       }
/*    */       else {
/*    */         
/* 68 */         g.drawString(item.getText(), x, y);
/*    */       } 
/*    */       
/* 71 */       y -= this.font.getHeight();
/*    */       
/* 73 */       if (y <= 0) {
/*    */         
/* 75 */         x += this.vl.getColumnWidth(columnCounter);
/* 76 */         columnCounter++;
/* 77 */         y = getContentHeight() - this.font.getHeight();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Font getFont() {
/* 84 */     return this.font;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFont(Font font) {
/* 89 */     this.font = font;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\VerticalListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */