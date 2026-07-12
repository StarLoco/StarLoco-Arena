/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.core.CallBack;
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCallBackEventListener
/*    */   extends CallBack
/*    */ {
/* 20 */   protected Class<? extends Event> m_eventClass = null;
/* 21 */   private Event m_currentEvent = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Object m_userdata;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void setCallBackFunc(String func, Class<? extends Event> eventClass, ElementMap elementMap)
/*    */   {
/* 33 */     super.setCallBackFunc(func, elementMap);
/* 34 */     this.m_eventClass = eventClass;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object invokeCallBack(Event event)
/*    */   {
/* 43 */     this.m_currentEvent = event;
/* 44 */     IElement element = event.getElement();
/* 45 */     if (element != null) {
/* 46 */       super.setElementMap(element.getElementMap());
/*    */     }
/* 48 */     return super.invokeCallBack();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void fillParameters(String[] parameters, List<Class> parameterTypes, List<Object> args)
/*    */   {
/* 62 */     parameterTypes.add(this.m_eventClass);
/* 63 */     args.add(this.m_currentEvent);
/*    */     
/* 65 */     super.fillParameters(parameters, parameterTypes, args);
/*    */   }
/*    */   
/*    */   public void copyCallback(AbstractCallBackEventListener cb)
/*    */   {
/* 70 */     cb.setCallBackFunc(this.m_func, this.m_eventClass, this.m_elementMap);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\AbstractCallBackEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */