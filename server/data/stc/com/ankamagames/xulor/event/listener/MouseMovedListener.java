/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.IMouseMovedListener;
/*    */ import com.ankamagames.xulor.event.MouseMovedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseMovedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMouseMovedListener
/*    */ {
/*    */   public void setMouseMovedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, MouseMovedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(MouseMovedEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MouseMovedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */