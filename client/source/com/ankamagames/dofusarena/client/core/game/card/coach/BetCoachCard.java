/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
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
/*    */ public class BetCoachCard
/*    */   extends CoachCard
/*    */ {
/*    */   private long m_ownerId;
/*    */   
/*    */   public BetCoachCard(int referenceCardId) {
/* 23 */     this.m_referenceCard = this.m_referenceCoachCardManager.get(referenceCardId);
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
/*    */   
/*    */   public BetCoachCard(int referenceCardId, boolean cursed) {
/* 36 */     this(referenceCardId);
/* 37 */     setCursed(cursed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getOwnerId() {
/* 44 */     return this.m_ownerId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOwnerId(long ownerId) {
/* 51 */     this.m_ownerId = ownerId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\BetCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */