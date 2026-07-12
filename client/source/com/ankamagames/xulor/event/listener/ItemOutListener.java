/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IListener;
/*    */ import com.ankamagames.xulor.event.ItemOutEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemOutListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IListener
/*    */ {
/*    */   public void setItemOutFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, ItemOutEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(ItemOutEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\ItemOutListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */