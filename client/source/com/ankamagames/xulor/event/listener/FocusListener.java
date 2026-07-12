/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.FocusEvent;
/*    */ import com.ankamagames.xulor.event.IFocusListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FocusListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IFocusListener
/*    */ {
/*    */   public void setFocusFunc(String func, ElementMap currentElementMap) {
/* 22 */     setCallBackFunc(func, FocusEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(FocusEvent event) {
/* 29 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\FocusListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */