/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IMouseExitedListener;
/*    */ import com.ankamagames.xulor.event.MouseExitedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseExitedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMouseExitedListener
/*    */ {
/*    */   public void setMouseExitedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, MouseExitedEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(MouseExitedEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MouseExitedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */