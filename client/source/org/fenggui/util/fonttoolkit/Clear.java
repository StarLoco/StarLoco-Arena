/*    */ package org.fenggui.util.fonttoolkit;
/*    */ 
/*    */ import java.awt.AlphaComposite;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
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
/*    */ public class Clear
/*    */   extends RenderStage
/*    */ {
/*    */   public void renderChar(FontMetrics fontMetrics, BufferedImage image, char c, int safetyMargin) {
/* 35 */     Graphics2D g = image.createGraphics();
/*    */     
/* 37 */     clear(g, image.getWidth(), image.getHeight());
/*    */   }
/*    */   
/*    */   public static void clear(Graphics2D g, int width, int height) {
/* 41 */     g.setComposite(AlphaComposite.getInstance(1));
/* 42 */     g.fillRect(0, 0, width, height);
/* 43 */     g.setComposite(AlphaComposite.getInstance(3));
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\fonttoolkit\Clear.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */