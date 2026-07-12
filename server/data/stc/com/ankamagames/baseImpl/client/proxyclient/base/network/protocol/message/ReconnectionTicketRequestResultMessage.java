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
/*    */ public class ReconnectionTicketRequestResultMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private boolean m_success;
/*    */   
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
/* 26 */     if (!checkMessageSize(rawDatas.length, 1, false)) {
/* 27 */       return false;
/*    */     }
/* 29 */     this.m_success = (rawDatas[0] == 1);
/*    */     
/* 31 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 40 */     return 4;
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
/*    */   public boolean isSuccess()
/*    */   {
/* 55 */     return this.m_success;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ReconnectionTicketRequestResultMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */