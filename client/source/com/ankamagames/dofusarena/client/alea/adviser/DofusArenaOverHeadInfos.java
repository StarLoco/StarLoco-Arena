/*    */ package com.ankamagames.dofusarena.client.alea.adviser;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.overHeadInfos.OverHeadInfos;
/*    */ import java.awt.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DofusArenaOverHeadInfos
/*    */   extends OverHeadInfos
/*    */ {
/* 15 */   private static Font DEFAULT_FONT = new Font("Arial Unicode MS", 0, 12);
/*    */   
/* 17 */   private static int DEFAULT_DURATION = 2500;
/* 18 */   private static int DEFAULT_MAX_WIDTH = 200;
/* 19 */   private static int DEFAULT_Y_OFFSET = 90;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DofusArenaOverHeadInfos(String text) {
/* 27 */     super(DEFAULT_FONT, text, DEFAULT_DURATION);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void init() {
/* 34 */     super.init();
/*    */     
/* 36 */     setMaxWidth(DEFAULT_MAX_WIDTH);
/* 37 */     setYOffset(DEFAULT_Y_OFFSET);
/*    */     
/* 39 */     setBackgroundColor(0.0F, 0.0F, 0.0F, 0.8F);
/* 40 */     setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\adviser\DofusArenaOverHeadInfos.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */