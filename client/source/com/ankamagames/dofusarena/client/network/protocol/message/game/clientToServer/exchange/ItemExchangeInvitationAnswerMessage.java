/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.exchange;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
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
/*    */ public class ItemExchangeInvitationAnswerMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   private byte m_exchangeInvitationResult;
/*    */   
/*    */   public byte[] encode() {
/* 32 */     ByteBuffer buffer = ByteBuffer.allocate(9);
/* 33 */     buffer.putLong(this.m_exchangeId);
/* 34 */     buffer.put(this.m_exchangeInvitationResult);
/*    */     
/* 36 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return 5103;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInvitationResult(byte exchangeInvitationResult) {
/* 53 */     this.m_exchangeInvitationResult = exchangeInvitationResult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExchangeId(long exchangeId) {
/* 60 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\exchange\ItemExchangeInvitationAnswerMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */