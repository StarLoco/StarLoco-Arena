/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IWindowClosedListener;
/*    */ import com.ankamagames.xulor.event.WindowClosedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowClosedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IWindowClosedListener
/*    */ {
/*    */   public void setWindowClosedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, WindowClosedEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(WindowClosedEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\WindowClosedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */