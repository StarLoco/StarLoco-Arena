/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IMenuItemPressedListener;
/*    */ import com.ankamagames.xulor.event.MenuItemPressedEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MenuItemPressedListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMenuItemPressedListener
/*    */ {
/*    */   public void setMenuItemPressedFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, MenuItemPressedEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(MenuItemPressedEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MenuItemPressedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */