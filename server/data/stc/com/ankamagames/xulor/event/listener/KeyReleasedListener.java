/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.IKeyReleasedListener;
/*    */ import com.ankamagames.xulor.event.KeyReleasedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyReleasedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IKeyReleasedListener
/*    */ {
/*    */   public void setKeyReleasedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, KeyReleasedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(KeyReleasedEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\KeyReleasedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */