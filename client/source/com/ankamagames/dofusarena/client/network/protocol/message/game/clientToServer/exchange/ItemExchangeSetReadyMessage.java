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
/*    */ public class ItemExchangeSetReadyMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   
/*    */   public byte[] encode() {
/* 29 */     ByteBuffer buffer = ByteBuffer.allocate(8);
/* 30 */     buffer.putLong(this.m_exchangeId);
/*    */     
/* 32 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 42 */     return 5107;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExchangeId(long exchangeId) {
/* 49 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\exchange\ItemExchangeSetReadyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */