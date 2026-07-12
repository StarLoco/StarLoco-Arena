/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.connection.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import java.nio.ByteBuffer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientAuthenticationResultsMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_newClientId;
/*    */   private byte m_resultCode;
/*    */   private String m_nickName;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 37 */     if (!checkMessageSize(rawDatas.length, 1, true)) {
/* 38 */       return false;
/*    */     }
/* 40 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 41 */     this.m_resultCode = bb.get();
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 52 */     return 1024;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getClientId() {
/* 61 */     return this.m_clientId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getNewClientId() {
/* 68 */     return this.m_newClientId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSuccessfull() {
/* 75 */     return (this.m_resultCode == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getErrorCode() {
/* 83 */     return this.m_resultCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNickName() {
/* 90 */     return this.m_nickName;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\connection\serverToClient\ClientAuthenticationResultsMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */