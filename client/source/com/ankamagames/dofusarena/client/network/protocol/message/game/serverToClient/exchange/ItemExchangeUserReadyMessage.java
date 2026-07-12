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
/*    */ public class ItemExchangeUserReadyMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   private byte m_userIndex;
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 28 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 30 */     this.m_exchangeId = buffer.getLong();
/* 31 */     this.m_userIndex = buffer.get();
/*    */     
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 43 */     return 5112;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getExchangeId() {
/* 50 */     return this.m_exchangeId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getUserIndex() {
/* 57 */     return this.m_userIndex;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\exchange\ItemExchangeUserReadyMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */