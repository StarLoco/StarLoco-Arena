/*    */ package org.fenggui.text;
/*    */ 
/*    */ import org.fenggui.render.Font;
/*    */ import org.fenggui.util.Color;
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
/*    */ public class TextStyle
/*    */ {
/*    */   private Font font;
/*    */   private Color color;
/*    */   
/*    */   public TextStyle(Font font, Color color) {
/* 47 */     this.font = font;
/* 48 */     this.color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 57 */     return this.color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(Color color) {
/* 66 */     this.color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Font getFont() {
/* 75 */     return this.font;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFont(Font font) {
/* 84 */     this.font = font;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\text\TextStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */