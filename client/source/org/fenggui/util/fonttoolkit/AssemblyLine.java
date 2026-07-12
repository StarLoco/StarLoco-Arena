/*    */ package org.fenggui.util.fonttoolkit;
/*    */ 
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.ArrayList;
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
/*    */ public class AssemblyLine
/*    */ {
/* 31 */   private ArrayList<RenderStage> stages = new ArrayList<RenderStage>();
/*    */ 
/*    */   
/*    */   public void execute(FontMetrics fontMetrics, BufferedImage image, char c, int safetyMargin) {
/* 35 */     for (RenderStage stage : this.stages) {
/* 36 */       stage.renderChar(fontMetrics, image, c, safetyMargin);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addStage(RenderStage fr) {
/* 42 */     this.stages.add(fr);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\fonttoolkit\AssemblyLine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */