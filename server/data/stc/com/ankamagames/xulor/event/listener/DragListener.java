/*    */ package com.ankamagames.xulor.event.listener;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.event.AbstractCallBackEventListener;
/*    */ import com.ankamagames.xulor.event.DragEvent;
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
/*    */   public DragListener()
/*    */   {
/* 19 */     this(null);
/*    */   }
/*    */   
/*    */   public DragListener(Object userdata)
/*    */   {
/* 24 */     this.m_userdata = userdata;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDraggedFunc(String func, ElementMap currentElementMap)
/*    */   {
/* 33 */     setCallBackFunc(func, DragEvent.class, currentElementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(DragEvent event)
/*    */   {
/* 41 */     invokeCallBack(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\listener\DragListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */