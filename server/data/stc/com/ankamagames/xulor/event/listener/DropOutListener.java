/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.DropOutEvent;
/*    */ import com.ankamagames.xulor.event.IDropOutListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DropOutListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IDropOutListener
/*    */ {
/*    */   public void setDroppedOutFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, DropOutEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(DropOutEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\DropOutListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */