/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.DragOutEvent;
/*    */ import com.ankamagames.xulor.event.IListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DragOutListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IListener
/*    */ {
/*    */   public void setDraggedOutFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, DragOutEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(DragOutEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\DragOutListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */