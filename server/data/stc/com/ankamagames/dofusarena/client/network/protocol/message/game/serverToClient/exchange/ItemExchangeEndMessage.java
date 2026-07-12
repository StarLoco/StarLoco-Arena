/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange;
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
/*    */ public class ItemExchangeEndMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private byte m_exchangeEndReason;
/*    */   private long m_exchangeId;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 29 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 31 */     this.m_exchangeEndReason = buffer.get();
/* 32 */     this.m_exchangeId = buffer.getLong();
/*    */     
/* 34 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 44 */     return 5111;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getExchangeId()
/*    */   {
/* 51 */     return this.m_exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getExchangeEndReason()
/*    */   {
/* 58 */     return this.m_exchangeEndReason;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\exchange\ItemExchangeEndMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */