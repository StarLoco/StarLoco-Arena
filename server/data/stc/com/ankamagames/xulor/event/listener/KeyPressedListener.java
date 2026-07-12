/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.IKeyPressedListener;
/*    */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyPressedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IKeyPressedListener
/*    */ {
/*    */   public void setKeyPressedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, KeyPressedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(KeyPressedEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\KeyPressedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */