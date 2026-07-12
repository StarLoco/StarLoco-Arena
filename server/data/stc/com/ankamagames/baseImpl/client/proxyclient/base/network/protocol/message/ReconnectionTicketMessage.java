/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReconnectionTicketMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte[] m_ticket;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 24 */     this.m_ticket = rawDatas;
/* 25 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 34 */     return 2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setId(int id) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getTicket()
/*    */   {
/* 49 */     return this.m_ticket;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ReconnectionTicketMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */