/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IMenuClosedListener;
/*    */ import com.ankamagames.xulor.event.MenuClosedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuClosedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMenuClosedListener
/*    */ {
/*    */   public void setMenuClosedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, MenuClosedEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(MenuClosedEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MenuClosedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */