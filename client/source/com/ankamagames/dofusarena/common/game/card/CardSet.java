/*    */ package com.ankamagames.dofusarena.common.game.card;
/*    */ 
/*    */ import java.lang.reflect.Array;
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
/*    */ 
/*    */ public abstract class CardSet<RC extends AbstractReferenceCoachCard>
/*    */ {
/* 19 */   private static Logger m_logger = Logger.getLogger(CardSet.class);
/*    */   
/*    */   private int m_id;
/*    */   
/*    */   private RC[] m_referenceCards;
/*    */   
/*    */   public CardSet(int id) {
/* 26 */     this.m_id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 30 */     return this.m_id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addReferenceCard(RC refCard) {
/* 35 */     if (this.m_referenceCards == null) {
/* 36 */       this.m_referenceCards = (RC[])Array.newInstance(AbstractReferenceCoachCard.class, 1);
/*    */     } else {
/* 38 */       AbstractReferenceCoachCard[] tmp = (AbstractReferenceCoachCard[])Array.newInstance(AbstractReferenceCoachCard.class, this.m_referenceCards.length + 1);
/* 39 */       System.arraycopy(this.m_referenceCards, 0, tmp, 0, this.m_referenceCards.length);
/* 40 */       this.m_referenceCards = (RC[])tmp;
/*    */     } 
/* 42 */     this.m_referenceCards[this.m_referenceCards.length - 1] = refCard;
/*    */   }
/*    */   
/*    */   public RC[] getReferenceCards() {
/* 46 */     return this.m_referenceCards;
/*    */   }
/*    */   
/*    */   public int size() {
/* 50 */     return (this.m_referenceCards != null) ? this.m_referenceCards.length : 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int indexOf(RC refCard) {
/*    */     try {
/* 60 */       if (this.m_referenceCards != null) {
/* 61 */         for (int i = 0; i < this.m_referenceCards.length; i++) {
/* 62 */           if (this.m_referenceCards[i] == refCard) {
/* 63 */             return i;
/*    */           }
/*    */         } 
/*    */       }
/* 67 */     } catch (Exception exception) {
/* 68 */       m_logger.error("Problème lors du indexOf(" + refCard + ")");
/* 69 */       return -1;
/*    */     } 
/* 71 */     return -1;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\CardSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */