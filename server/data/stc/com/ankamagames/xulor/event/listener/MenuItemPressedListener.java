/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
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
/*    */   public void setMenuItemPressedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, MenuItemPressedEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run(MenuItemPressedEvent event)
/*    */   {
/* 29 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MenuItemPressedListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */