/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IMouseClickListener;
/*    */ import com.ankamagames.xulor.event.MouseClickEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseClickListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IMouseClickListener
/*    */ {
/*    */   public MouseClickListener() {}
/*    */   
/*    */   public MouseClickListener(Object userdata) {
/* 20 */     this.m_userdata = userdata;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMouseClickFunc(String func, ElementMap currentElementMap) {
/* 29 */     setCallBackFunc(func, MouseClickEvent.class, currentElementMap);
/*    */   }
/*    */ 
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
/*    */   public void run(MouseClickEvent event) {
/* 44 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\MouseClickListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */