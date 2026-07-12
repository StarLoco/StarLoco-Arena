/*    */ package com.ankamagames.framework.kernel.core.net;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import com.ankamagames.framework.kernel.core.common.Validator;
/*    */ import gnu.trove.TLinkable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionValidator
/*    */   extends Validator
/*    */   implements TLinkable, Poolable
/*    */ {
/*    */   protected Connection m_connection;
/*    */   protected int m_connectionId;
/*    */   protected TLinkable m_previous;
/*    */   protected TLinkable m_next;
/*    */   
/*    */   public void onCheckOut()
/*    */   {
/* 33 */     reset();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onCheckIn()
/*    */   {
/* 40 */     reset();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TLinkable getNext()
/*    */   {
/* 47 */     return this.m_next;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setNext(TLinkable linkable)
/*    */   {
/* 54 */     this.m_next = linkable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TLinkable getPrevious()
/*    */   {
/* 61 */     return this.m_previous;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPrevious(TLinkable linkable)
/*    */   {
/* 68 */     this.m_previous = linkable;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */