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
/*    */ public class ItemExchangeCancelMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   
/*    */   public byte[] encode()
/*    */   {
/* 28 */     ByteBuffer buffer = ByteBuffer.allocate(8);
/* 29 */     buffer.putLong(this.m_exchangeId);
/*    */     
/* 31 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 41 */     return 5108;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setExchangeId(long exchangeId)
/*    */   {
/* 48 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\exchange\ItemExchangeCancelMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */