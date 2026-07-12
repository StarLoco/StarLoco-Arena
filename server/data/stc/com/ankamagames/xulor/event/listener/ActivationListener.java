/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.ActivationEvent;
/*    */ import com.ankamagames.xulor.event.IActivationListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActivationListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IActivationListener
/*    */ {
/*    */   public ActivationListener() {}
/*    */   
/*    */   public ActivationListener(Object userdata)
/*    */   {
/* 20 */     this.m_userdata = userdata;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setActivatedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 29 */     setCallBackFunc(func, ActivationEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void build() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(ActivationEvent event)
/*    */   {
/* 44 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\ActivationListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */