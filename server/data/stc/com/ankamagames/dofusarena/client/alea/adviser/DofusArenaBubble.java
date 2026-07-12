/*    */ package com.ankamagames.dofusarena.client.alea.adviser;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.bubble.Bubble;
/*    */ import java.awt.Font;
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
/*    */ public class DofusArenaBubble
/*    */   extends Bubble
/*    */ {
/* 18 */   private static Font DEFAULT_FONT = new Font("Arial Unicode MS", 0, 12);
/*    */   
/* 20 */   private static int DEFAULT_DURATION = 2500;
/* 21 */   private static int DEFAULT_MAX_WIDTH = 200;
/* 22 */   private static int DEFAULT_MIN_WIDTH = 45;
/* 23 */   private static int DEFAULT_X_OFFSET = -5;
/* 24 */   private static int DEFAULT_Y_OFFSET = 80;
/*    */   
/*    */   public DofusArenaBubble(String text) {
/* 27 */     super(DEFAULT_FONT, text);
/* 28 */     int duration = 0;
/* 29 */     if (text != null) {
/* 30 */       duration = text.length() * 50;
/*    */     }
/* 32 */     setDuration(DEFAULT_DURATION + duration);
/* 33 */     setMaxWidth(DEFAULT_MAX_WIDTH);
/* 34 */     setMinWidth(DEFAULT_MIN_WIDTH);
/* 35 */     setXOffset(DEFAULT_X_OFFSET);
/* 36 */     setYOffset(DEFAULT_Y_OFFSET);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\adviser\DofusArenaBubble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */