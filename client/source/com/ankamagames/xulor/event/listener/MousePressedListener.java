/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IMousePressedListener;
/*    */ import com.ankamagames.xulor.event.MousePressedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MousePressedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMousePressedListener
/*    */ {
/*    */   public void setMousePressedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, MousePressedEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(MousePressedEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MousePressedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */