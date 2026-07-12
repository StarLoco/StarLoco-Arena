/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.DragEvent;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.IDragListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DragListener
/*    */   extends AbstractCallBackEventListener
/*    */   implements IDragListener
/*    */ {
/*    */   public DragListener() {
/* 19 */     this((Object)null);
/*    */   }
/*    */ 
/*    */   
/*    */   public DragListener(Object userdata) {
/* 24 */     this.m_userdata = userdata;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDraggedFunc(String func, ElementMap currentElementMap) {
/* 33 */     setCallBackFunc(func, DragEvent.class, currentElementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(DragEvent event) {
/* 41 */     invokeCallBack((Event)event);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\DragListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */