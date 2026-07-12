/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.ISelectionChangedListener;
/*    */ import com.ankamagames.xulor.event.SelectionChangedEvent;
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
/*    */ 
/*    */ public class SelectionChangedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements ISelectionChangedListener
/*    */ {
/*    */   public void setSelectionChangedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 28 */     setCallBackFunc(func, SelectionChangedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(SelectionChangedEvent event)
/*    */   {
/* 35 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\SelectionChangedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */