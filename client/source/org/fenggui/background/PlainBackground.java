/*    */ package org.fenggui.background;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.io.DefaultElementName;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.IOStreamSaveable;
/*    */ import org.fenggui.io.InputOnlyStream;
/*    */ import org.fenggui.io.InputOutputStream;
/*    */ import org.fenggui.render.Graphics;
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
/*    */ @DefaultElementName("PlainBackground")
/*    */ public class PlainBackground
/*    */   extends Background
/*    */ {
/* 41 */   private Color color = Color.GRAY;
/*    */ 
/*    */   
/*    */   public PlainBackground() {
/* 45 */     this(Color.BLACK_HALF_OPAQUE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PlainBackground(Color g) {
/* 51 */     this.color = g;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PlainBackground(InputOnlyStream stream) throws IOException, IOStreamException {
/* 57 */     process((InputOutputStream)stream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 63 */     return this.color;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(Color background) {
/* 69 */     this.color = background;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 77 */     super.process(stream);
/*    */     
/* 79 */     this.color = (Color)stream.processChild("Color", (IOStreamSaveable)this.color, Color.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 86 */     g.setColor(this.color);
/*    */     
/* 88 */     g.drawFilledRectangle(localX, localY, width, height);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\background\PlainBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */