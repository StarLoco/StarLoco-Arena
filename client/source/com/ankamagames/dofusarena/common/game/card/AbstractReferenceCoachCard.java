/*    */ package com.ankamagames.dofusarena.common.game.card;
/*    */ 
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
/*    */ public abstract class AbstractReferenceCoachCard
/*    */ {
/*    */   protected final int m_id;
/*    */   protected final int m_value;
/*    */   protected final CoachCardType m_type;
/*    */   protected final int m_cardSetId;
/*    */   protected final CardSet m_cardSet;
/*    */   
/*    */   protected AbstractReferenceCoachCard(int id, CoachCardType type, int cardSetId, int goldValue) {
/* 28 */     this.m_id = id;
/* 29 */     this.m_type = type;
/* 30 */     this.m_value = goldValue;
/* 31 */     this.m_cardSetId = cardSetId;
/* 32 */     this.m_cardSet = null;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 36 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public void release() {}
/*    */   
/*    */   public long getUniqueId() {
/* 42 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public int getReferenceId() {
/* 46 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public byte[] serialize() {
/* 50 */     byte[] b = new byte[4];
/* 51 */     ByteBuffer.wrap(b).putInt(this.m_id);
/* 52 */     return b;
/*    */   }
/*    */   
/*    */   public boolean unserialize(ByteBuffer buf) {
/* 56 */     throw new UnsupportedOperationException("AbstractReferenceCoachCard can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
/*    */   }
/*    */   
/*    */   public CoachCardType getType() {
/* 60 */     return this.m_type;
/*    */   }
/*    */   
/*    */   public int getCardSetId() {
/* 64 */     return this.m_cardSetId;
/*    */   }
/*    */   
/*    */   public CardSet getCardSet() {
/* 68 */     return CardSetManager.getInstance().get(this.m_cardSetId);
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 72 */     return this.m_value;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractReferenceCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */