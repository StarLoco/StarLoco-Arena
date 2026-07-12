/*    */ package org.fenggui.util.fonttoolkit;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.RenderingHints;
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
/*    */ public class DrawCharacter
/*    */   extends RenderStage
/*    */ {
/* 34 */   private Color renderColor = Color.RED;
/*    */   private boolean antialiasing = false;
/* 36 */   int[] pixel = new int[4];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DrawCharacter(Color color, boolean antialiasing) {
/* 46 */     this.renderColor = color;
/* 47 */     this.pixel[0] = this.renderColor.getRed();
/* 48 */     this.pixel[1] = this.renderColor.getGreen();
/* 49 */     this.pixel[2] = this.renderColor.getBlue();
/* 50 */     this.antialiasing = antialiasing;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderChar(FontMetrics fontMetrics, BufferedImage image, char c, int safetyMargin) {
/*    */     Graphics2D g;
/* 59 */     BufferedImage tmpImage = null;
/* 60 */     if (this.antialiasing) {
/* 61 */       tmpImage = new BufferedImage(image.getWidth(), image.getHeight(), 10);
/*    */       
/* 63 */       g = (Graphics2D)tmpImage.getGraphics();
/*    */       
/* 65 */       g.setColor(Color.BLACK);
/* 66 */       g.fillRect(0, 0, tmpImage.getWidth(), tmpImage.getHeight());
/* 67 */       g.setColor(Color.WHITE);
/*    */       
/* 69 */       g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*    */     } else {
/*    */       
/* 72 */       g = (Graphics2D)image.getGraphics();
/* 73 */       g.setColor(this.renderColor);
/*    */     } 
/*    */     
/* 76 */     g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     g.setFont(fontMetrics.getFont());
/*    */ 
/*    */     
/* 85 */     g.drawString(Character.toString(c), 
/* 86 */         0, 
/* 87 */         fontMetrics.getMaxAscent());
/*    */     
/* 89 */     if (this.antialiasing)
/* 90 */       for (int x = 0; x < image.getWidth(); x++) {
/* 91 */         for (int y = 0; y < image.getHeight(); y++) {
/* 92 */           this.pixel[3] = tmpImage.getRaster().getSample(x, y, 0);
/* 93 */           if (this.pixel[3] != 0)
/* 94 */             image.getRaster().setPixel(x, y, this.pixel); 
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\fonttoolkit\DrawCharacter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */