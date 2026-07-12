/*    */ package com.ankamagames.framework.kernel.events;
/*    */ 
/*    */ import gnu.trove.TLinkable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameworkEvent
/*    */   implements TLinkable
/*    */ {
/*    */   private FrameworkEventsHandler m_handler;
/*    */   private TLinkable m_previous;
/*    */   private TLinkable m_next;
/*    */   
/*    */   public FrameworkEventsHandler getHandler()
/*    */   {
/* 21 */     return this.m_handler;
/*    */   }
/*    */   
/*    */   public void setHandler(FrameworkEventsHandler handler) {
/* 25 */     this.m_handler = handler;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TLinkable getNext()
/*    */   {
/* 32 */     return this.m_next;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setNext(TLinkable linkable)
/*    */   {
/* 39 */     this.m_next = linkable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TLinkable getPrevious()
/*    */   {
/* 46 */     return this.m_previous;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPrevious(TLinkable linkable)
/*    */   {
/* 53 */     this.m_previous = linkable;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\events\FrameworkEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */