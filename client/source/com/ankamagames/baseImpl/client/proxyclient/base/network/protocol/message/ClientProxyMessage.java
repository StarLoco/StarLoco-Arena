/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*    */ public abstract class ClientProxyMessage
/*    */   extends Message
/*    */ {
/*    */   protected long m_clientId;
/*    */   
/*    */   public void onCheckOut() {}
/*    */   
/*    */   public void onCheckIn() {}
/*    */   
/*    */   public byte[] addClientHeader(byte architectureTarget, byte[] datas) {
/* 30 */     int msgSize = 5 + datas.length;
/* 31 */     ByteBuffer bb = ByteBuffer.allocate(msgSize);
/*    */ 
/*    */     
/* 34 */     bb.putShort((short)msgSize);
/* 35 */     bb.put(architectureTarget);
/* 36 */     bb.putShort((short)getId());
/*    */ 
/*    */     
/* 39 */     bb.put(datas);
/*    */     
/* 41 */     return bb.array();
/*    */   }
/*    */ 
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
/*    */   public long getClientId() {
/* 56 */     return this.m_clientId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setClientId(long clientId) {
/* 63 */     this.m_clientId = clientId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\ClientProxyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */