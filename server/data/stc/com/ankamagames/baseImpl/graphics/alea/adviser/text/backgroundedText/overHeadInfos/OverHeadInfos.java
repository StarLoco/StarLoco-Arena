/*    */ package com.ankamagames.baseImpl.graphics.alea.adviser.text.backgroundedText.overHeadInfos;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.TimeTargetedGLTextArea;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import com.ankamagames.graphics.isometric.text.AbstractTesselBackground;
/*    */ import com.ankamagames.graphics.isometric.text.BackgroundedText.BackgroundedTextHotPointPosition;
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
/*    */ public class OverHeadInfos
/*    */   extends TimeTargetedGLTextArea
/*    */   implements Adviser
/*    */ {
/*    */   public OverHeadInfos(Font font, String text)
/*    */   {
/* 28 */     super(font, text);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public OverHeadInfos(Font font, String text, int duration)
/*    */   {
/* 39 */     super(font, text, duration);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setBackgroundColor(float r, float g, float b, float a)
/*    */   {
/* 51 */     ((AbstractTesselBackground)getBackground()).setBackgoundColor(r, g, b, a);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setBorderColor(float r, float g, float b, float a)
/*    */   {
/* 63 */     ((AbstractTesselBackground)getBackground()).setBorderColor(r, g, b, a);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void init()
/*    */   {
/* 73 */     super.init();
/* 74 */     setBackground(new DefaultOverHeadInfosBackground());
/* 75 */     setHotPointPosition(BackgroundedText.BackgroundedTextHotPointPosition.SOUTH);
/*    */   }
/*    */   
/*    */   public void process(AleaWorldScene scene, long realTime, int frameCount) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\text\backgroundedText\overHeadInfos\OverHeadInfos.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */