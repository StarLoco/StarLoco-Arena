/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.exchange;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
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
/*    */ public class UIExchangeMoveCardMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private short m_position;
/*    */   private CoachCard m_coachCard;
/*    */   private long m_exchangeId;
/*    */   
/*    */   public CoachCard getCoachCard()
/*    */   {
/* 25 */     return this.m_coachCard;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setCoachCard(CoachCard coachCard)
/*    */   {
/* 32 */     this.m_coachCard = coachCard;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getExchangeId()
/*    */   {
/* 39 */     return this.m_exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setExchangeId(long exchangeId)
/*    */   {
/* 46 */     this.m_exchangeId = exchangeId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getPosition()
/*    */   {
/* 53 */     return this.m_position;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPosition(short position)
/*    */   {
/* 60 */     this.m_position = position;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\exchange\UIExchangeMoveCardMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */