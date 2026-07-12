/*    */ package org.fenggui.background;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.io.DefaultElementName;
/*    */ import org.fenggui.io.IOStreamException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @DefaultElementName("FunnyBackground")
/*    */ public class FunnyBackground
/*    */   extends Background
/*    */ {
/*    */   public FunnyBackground() {}
/*    */   
/*    */   public FunnyBackground(InputOnlyStream stream) throws IOException, IOStreamException {
/* 49 */     process((InputOutputStream)stream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 55 */     g.setColor(Color.RED);
/* 56 */     g.drawWireRectangle(localX, localY, width, height);
/* 57 */     g.setColor(Color.BLUE);
/* 58 */     g.drawWireRectangle(localX + 1, localY + 1, width - 2, height - 2);
/* 59 */     g.setColor(Color.YELLOW);
/* 60 */     g.drawWireRectangle(localX + 2, localY + 2, width - 4, height - 4);
/*    */   }
/*    */   
/*    */   public void process(InputOutputStream stream) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\background\FunnyBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */