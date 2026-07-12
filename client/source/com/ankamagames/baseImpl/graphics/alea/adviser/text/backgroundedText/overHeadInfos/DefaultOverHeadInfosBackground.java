/*    */ package com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.overHeadInfos;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.text.AbstractTesselBackground;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultOverHeadInfosBackground
/*    */   extends AbstractTesselBackground
/*    */ {
/* 16 */   private static final double[][] VERTICES_ADJUSTMENT = new double[][] {
/* 17 */       { 0.0D, 2.0D
/* 18 */       }, { 0.0D, -2.0D
/* 19 */       }, { 2.0D, 0.0D
/* 20 */       }, { -2.0D, 0.0D
/* 21 */       }, { 0.0D, -2.0D
/* 22 */       }, { 0.0D, 2.0D
/* 23 */       }, { -2.0D, 0.0D
/* 24 */       }, { 2.0D, 0.0D }
/*    */     };
/* 26 */   private static final double[][] VERTICES_WIDTH_AND_HEGHT = new double[][] {
/* 27 */       { 0.0D, 0.0D
/* 28 */       }, { 0.0D, 1.0D
/* 29 */       }, { 0.0D, 1.0D
/* 30 */       }, { 1.0D, 1.0D
/* 31 */       }, { 1.0D, 1.0D
/* 32 */       }, { 1.0D, 0.0D
/* 33 */       }, { 1.0D, 0.0D
/* 34 */       }, { 0.0D, 0.0D }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBottomMargin() {
/* 42 */     return 9;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLeftMargin() {
/* 51 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRightMargin() {
/* 60 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTopMargin() {
/* 69 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initializeVertices() {
/* 78 */     setVerticesAdjustment(VERTICES_ADJUSTMENT);
/* 79 */     setVerticesWidthAndHeight(VERTICES_WIDTH_AND_HEGHT);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\backgroundedText\overHeadInfos\DefaultOverHeadInfosBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */