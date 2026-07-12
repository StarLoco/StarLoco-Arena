/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractReferenceCoachCard;
/*    */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceCoachCard
/*    */   extends AbstractReferenceCoachCard
/*    */ {
/*    */   public ReferenceCoachCard(int id, CoachCardType type, int cardSetId, int goldValue)
/*    */   {
/* 19 */     super(id, type, cardSetId, goldValue);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 26 */     return DofusArenaTranslator.getInstance().getString(23, getId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 33 */     return DofusArenaTranslator.getInstance().getString(24, getId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getCardSetName()
/*    */   {
/* 40 */     if (getCardSetId() == 0) {
/* 41 */       return "";
/*    */     }
/* 43 */     return DofusArenaTranslator.getInstance().getString(25, getCardSetId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getCardSetDescription()
/*    */   {
/* 50 */     if (getCardSetId() == 0) {
/* 51 */       return "";
/*    */     }
/* 53 */     return DofusArenaTranslator.getInstance().getString(26, getCardSetId());
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\ReferenceCoachCard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */