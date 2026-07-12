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
/*    */ public abstract class ItemExchangeMoveCardMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   private long m_cardUniqueId;
/*    */   private short m_cardQuantity;
/*    */   
/*    */   public byte[] encode() {
/* 30 */     ByteBuffer buffer = ByteBuffer.allocate(18);
/* 31 */     buffer.putLong(this.m_exchangeId);
/* 32 */     buffer.putLong(this.m_cardUniqueId);
/* 33 */     buffer.putShort(this.m_cardQuantity);
/*    */     
/* 35 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExchangeId(long exchangeId) {
/* 42 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCardUniqueId(long uniqueId) {
/* 49 */     this.m_cardUniqueId = uniqueId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCardQuantity(short cardQuantity) {
/* 56 */     this.m_cardQuantity = cardQuantity;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\exchange\ItemExchangeMoveCardMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */