/*    */ package com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.bubble;
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
/*    */ public class DefaultBubbleBackground
/*    */   extends AbstractTesselBackground
/*    */ {
/* 16 */   public static final double[][] VERTICES_ADJUSTMENT = {
/* 17 */     { 0.0D, 2.0D }, 
/* 18 */     { 0.0D, -2.0D }, 
/* 19 */     { 2.0D, 0.0D }, 
/* 20 */     { -2.0D, 0.0D }, 
/* 21 */     { 0.0D, -2.0D }, 
/* 22 */     { 0.0D, 2.0D }, 
/* 23 */     { -2.0D, 0.0D }, 
/* 24 */     { 36.0D, 0.0D }, 
/* 25 */     { 32.0D, -4.0D }, 
/* 26 */     { 36.0D, -8.0D }, 
/* 27 */     { 25.0D, -20.0D }, 
/* 28 */     { 32.0D, -8.0D }, 
/* 29 */     { 26.0D, -4.0D }, 
/* 30 */     { 28.0D, 0.0D }, 
/* 31 */     { 2.0D, 0.0D } };
/*    */   
/* 33 */   public static final double[][] VERTICES_WIDTH_AND_HEGHT = {
/* 34 */     { 0.0D, 0.0D }, 
/* 35 */     { 0.0D, 1.0D }, 
/* 36 */     { 0.0D, 1.0D }, 
/* 37 */     { 1.0D, 1.0D }, 
/* 38 */     { 1.0D, 1.0D }, 
/* 39 */     { 1.0D, 0.0D }, 
/* 40 */     { 1.0D, 0.0D }, 
/* 41 */     { 0.0D, 0.0D }, 
/* 42 */     { 0.0D, 0.0D }, 
/* 43 */     { 0.0D, 0.0D }, 
/* 44 */     { 0.0D, 0.0D }, 
/* 45 */     { 0.0D, 0.0D }, 
/* 46 */     { 0.0D, 0.0D }, 
/* 47 */     { 0.0D, 0.0D }, 
/* 48 */     { 0.0D, 0.0D } };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getBottomMargin()
/*    */   {
/* 56 */     return 9;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getLeftMargin()
/*    */   {
/* 65 */     return 5;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getRightMargin()
/*    */   {
/* 74 */     return 5;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getTopMargin()
/*    */   {
/* 83 */     return 1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void initializeVertices()
/*    */   {
/* 92 */     setVerticesAdjustment(VERTICES_ADJUSTMENT);
/* 93 */     setVerticesWidthAndHeight(VERTICES_WIDTH_AND_HEGHT);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\backgroundedText\bubble\DefaultBubbleBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */