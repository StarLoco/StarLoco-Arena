/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.DropEvent;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IDropListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DropListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IDropListener
/*    */ {
/*    */   public void setDroppedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, DropEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(DropEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\DropListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */