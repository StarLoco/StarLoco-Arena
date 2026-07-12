/*    */ package org.fenggui.table;
/*    */ 
/*    */ import org.fenggui.layout.Alignment;
/*    */ import org.fenggui.render.Font;
/*    */ import org.fenggui.render.Graphics;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextCellRenderer
/*    */   implements ICellRenderer
/*    */ {
/* 35 */   private Font font = Font.getDefaultFont();
/* 36 */   private Color textColor = Color.WHITE;
/* 37 */   private Alignment alignment = Alignment.LEFT;
/*    */ 
/*    */   
/*    */   public void paint(Graphics g, Object value, int x, int y, int width, int height) {
/* 41 */     String s = value.toString();
/*    */     
/* 43 */     s = this.font.confineLength(s, width - 4);
/*    */     
/* 45 */     int entryOffset = 4 + this.alignment.alignX(width - 4, this.font.getWidth(s));
/*    */     
/* 47 */     g.setFont(this.font);
/* 48 */     g.setColor(this.textColor);
/* 49 */     g.drawString(s, x + entryOffset, y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFont(Font font) {
/* 57 */     this.font = font;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTextColor(Color textColor) {
/* 65 */     this.textColor = textColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension getCellContentSize(Object value) {
/* 70 */     if (value == null) {
/* 71 */       return null;
/*    */     }
/* 73 */     String s = value.toString();
/*    */     
/* 75 */     return new Dimension(this.font.getWidth(s), this.font.getHeight());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\TextCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */