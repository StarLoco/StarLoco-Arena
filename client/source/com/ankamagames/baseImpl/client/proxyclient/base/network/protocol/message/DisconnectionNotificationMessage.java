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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisconnectionNotificationMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/* 21 */   private static byte[] m_emptyByteArray = new byte[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] encode() {
/* 29 */     return addClientHeader((byte)0, m_emptyByteArray);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 38 */     return 1;
/*    */   }
/*    */   
/*    */   public void setId(int id) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\DisconnectionNotificationMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */