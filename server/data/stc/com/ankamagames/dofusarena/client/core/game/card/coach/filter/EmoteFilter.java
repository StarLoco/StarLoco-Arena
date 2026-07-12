/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach.filter;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*    */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
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
/*    */ public class EmoteFilter
/*    */   implements CoachCardFilter
/*    */ {
/*    */   public boolean accept(CoachCard coachCard)
/*    */   {
/* 23 */     return coachCard.getFieldValue("cardType").equals(CoachCardType.EMOTE);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\filter\EmoteFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */