/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.exchange;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
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
/*    */ public class ItemExchangeCardAddedMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/*    */   private long m_exchangeId;
/*    */   private byte m_userIndex;
/*    */   private CoachCard m_card;
/*    */   
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 31 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 33 */     this.m_exchangeId = buffer.getLong();
/* 34 */     this.m_userIndex = buffer.get();
/* 35 */     this.m_card = new CoachCard();
/* 36 */     this.m_card.unserialize(buffer);
/* 37 */     this.m_card.setQuantity(buffer.getShort());
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 49 */     return 5109;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getExchangeId()
/*    */   {
/* 56 */     return this.m_exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CoachCard getCard()
/*    */   {
/* 63 */     return this.m_card;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getUserIndex()
/*    */   {
/* 70 */     return this.m_userIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\exchange\ItemExchangeCardAddedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */