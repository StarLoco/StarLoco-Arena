/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.ISliderMovedListener;
/*    */ import com.ankamagames.xulor.event.SliderMovedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SliderMovedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements ISliderMovedListener
/*    */ {
/*    */   public void setSliderMovedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, SliderMovedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(SliderMovedEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\SliderMovedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */