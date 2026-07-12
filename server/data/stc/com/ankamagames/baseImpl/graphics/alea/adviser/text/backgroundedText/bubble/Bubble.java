/*    */ package com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.bubble;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.TimeTargetedGLTextArea;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Bubble
/*    */   extends TimeTargetedGLTextArea
/*    */   implements Adviser
/*    */ {
/*    */   public Bubble(Font font, String text)
/*    */   {
/* 27 */     super(font, text);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Bubble(Font font, String text, int duration)
/*    */   {
/* 38 */     super(font, text, duration);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void init()
/*    */   {
/* 48 */     super.init();
/* 49 */     setBackground(new DefaultBubbleBackground());
/*    */   }
/*    */   
/*    */   public void process(AleaWorldScene scene, long realTime, int frameCount) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\backgroundedText\bubble\Bubble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */