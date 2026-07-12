/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReconnectionTicketRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private final byte[] m_ticket;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ReconnectionTicketRequestMessage(byte[] ticket)
/*    */   {
/* 25 */     this.m_ticket = ticket;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] encode()
/*    */   {
/* 35 */     byte[] ticket = this.m_ticket;
/* 36 */     if (this.m_ticket == null) {
/* 37 */       ticket = new byte[0];
/*    */     }
/* 39 */     return addClientHeader((byte)0, ticket);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 3;
/*    */   }
/*    */   
/*    */   public void setId(int id) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ReconnectionTicketRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */