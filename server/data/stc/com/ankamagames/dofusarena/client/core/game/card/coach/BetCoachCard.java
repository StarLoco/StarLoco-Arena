/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractReferenceCoachCardManager;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class BetCoachCard
/*    */   extends CoachCard
/*    */ {
/*    */   private long m_ownerId;
/*    */   
/*    */   public BetCoachCard(int referenceCardId)
/*    */   {
/* 23 */     this.m_referenceCard = ((ReferenceCoachCard)this.m_referenceCoachCardManager.get(referenceCardId));
/* 24 */     if (this.m_referenceCard == null) {
/* 25 */       m_logger.error("BetCoachCard : referenceCard not found : " + referenceCardId + " (" + this + ")");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BetCoachCard(int referenceCardId, boolean cursed)
/*    */   {
/* 36 */     this(referenceCardId);
/* 37 */     setCursed(cursed);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getOwnerId()
/*    */   {
/* 44 */     return this.m_ownerId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setOwnerId(long ownerId)
/*    */   {
/* 51 */     this.m_ownerId = ownerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\BetCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */