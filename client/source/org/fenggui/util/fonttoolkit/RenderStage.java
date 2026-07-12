/*    */ package org.fenggui.util.fonttoolkit;
/*    */ 
/*    */ import java.awt.FontMetrics;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderStage
/*    */ {
/*    */   public void renderChar(FontMetrics fontMetrics, BufferedImage image, char c, int safetyMargin) {
/* 36 */     renderChar(fontMetrics, image, c);
/*    */   }
/*    */   
/*    */   public void renderChar(FontMetrics fontMetrics, BufferedImage image, char c) {
/* 40 */     renderChar(image);
/*    */   }
/*    */   
/*    */   public void renderChar(BufferedImage image) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\fonttoolkit\RenderStage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */