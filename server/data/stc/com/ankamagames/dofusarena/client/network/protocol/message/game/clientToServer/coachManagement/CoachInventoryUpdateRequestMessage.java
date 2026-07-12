/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.coachManagement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.OutputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ public class CoachInventoryUpdateRequestMessage
/*    */   extends OutputOnlyProxyMessage
/*    */ {
/* 22 */   private ArrayList<CoachCard> m_removedCardsArray = null;
/* 23 */   private ArrayList<CoachCard> m_lockedCardsArray = null;
/* 24 */   private ArrayList<CoachCard> m_unlockedCardsArray = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] encode()
/*    */   {
/* 33 */     ByteBuffer buffer = ByteBuffer.allocate(2 + 8 * this.m_removedCardsArray.size() + 8 * this.m_lockedCardsArray.size() + 8 * this.m_unlockedCardsArray.size() + 2 + 2);
/*    */     
/*    */ 
/* 36 */     if (this.m_removedCardsArray != null) {
/* 37 */       buffer.putShort((short)this.m_removedCardsArray.size());
/* 38 */       for (CoachCard removedCard : this.m_removedCardsArray) {
/* 39 */         buffer.putLong(removedCard.getUniqueId());
/*    */       }
/*    */     } else {
/* 42 */       buffer.putShort((short)0);
/*    */     }
/*    */     
/*    */ 
/* 46 */     if (this.m_lockedCardsArray != null) {
/* 47 */       buffer.putShort((short)this.m_lockedCardsArray.size());
/* 48 */       for (CoachCard lockedCard : this.m_lockedCardsArray) {
/* 49 */         buffer.putLong(lockedCard.getUniqueId());
/*    */       }
/*    */     } else {
/* 52 */       buffer.putShort((short)0);
/*    */     }
/*    */     
/*    */ 
/* 56 */     if (this.m_unlockedCardsArray != null) {
/* 57 */       buffer.putShort((short)this.m_unlockedCardsArray.size());
/* 58 */       for (CoachCard unlockedCard : this.m_unlockedCardsArray) {
/* 59 */         buffer.putLong(unlockedCard.getUniqueId());
/*    */       }
/*    */     } else {
/* 62 */       buffer.putShort((short)0);
/*    */     }
/*    */     
/* 65 */     return addClientHeader((byte)3, buffer.array());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 75 */     return 5203;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setRemovedCardsArray(ArrayList<CoachCard> removedCardsArray)
/*    */   {
/* 82 */     this.m_removedCardsArray = removedCardsArray;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setLockedCardsArray(ArrayList<CoachCard> lockedCardsArray)
/*    */   {
/* 89 */     this.m_lockedCardsArray = lockedCardsArray;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setUnlockedCardsArray(ArrayList<CoachCard> unlockedCardsArray)
/*    */   {
/* 96 */     this.m_unlockedCardsArray = unlockedCardsArray;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\clientToServer\coachManagement\CoachInventoryUpdateRequestMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */