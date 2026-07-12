/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.IListener;
/*    */ import com.ankamagames.xulor.event.ItemClickEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemClickListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IListener
/*    */ {
/*    */   public void setItemClickFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 22 */     setCallBackFunc(func, ItemClickEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(ItemClickEvent event)
/*    */   {
/* 30 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\ItemClickListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */