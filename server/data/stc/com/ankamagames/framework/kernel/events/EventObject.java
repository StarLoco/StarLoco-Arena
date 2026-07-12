/*    */ package com.ankamagames.framework.kernel.events;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventObject
/*    */ {
/*    */   protected Object m_source;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EventObject(Object source)
/*    */   {
/* 25 */     this.m_source = source;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 32 */     return this.m_source;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 42 */     return getClass().getName() + "[source=" + this.m_source + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\EventObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */