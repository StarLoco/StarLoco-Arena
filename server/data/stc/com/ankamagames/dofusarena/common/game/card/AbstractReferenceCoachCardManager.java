/*     */ package com.ankamagames.dofusarena.common.game.card;
/*     */ 
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReferenceCoachCardManager<RC extends AbstractReferenceCoachCard>
/*     */   implements Iterable<RC>
/*     */ {
/*  24 */   protected static final Logger m_logger = Logger.getLogger(AbstractReferenceCoachCardManager.class);
/*     */   
/*  26 */   private final TIntObjectHashMap<RC> m_cards = new TIntObjectHashMap();
/*     */   
/*  28 */   private final ArrayList<RC> m_randomCards = new ArrayList();
/*  29 */   private final Random m_randomizer = new Random(System.currentTimeMillis());
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(RC card)
/*     */   {
/*  35 */     this.m_cards.put(card.getId(), card);
/*     */   }
/*     */   
/*     */   public RC get(int id) {
/*  39 */     return (AbstractReferenceCoachCard)this.m_cards.get(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Nullable
/*     */   public RC getRandomFromValues(int minValue, int maxValue)
/*     */   {
/*  53 */     TIntObjectIterator<RC> it = this.m_cards.iterator();
/*  54 */     this.m_randomCards.clear();
/*     */     
/*  56 */     while (it.hasNext()) {
/*  57 */       it.advance();
/*  58 */       RC referenceCard = (AbstractReferenceCoachCard)it.value();
/*     */       
/*  60 */       if ((referenceCard.getValue() >= minValue) && (referenceCard.getValue() <= maxValue)) {
/*  61 */         this.m_randomCards.add(referenceCard);
/*     */       }
/*     */     }
/*  64 */     if (this.m_randomCards.isEmpty()) {
/*  65 */       return null;
/*     */     }
/*  67 */     return (AbstractReferenceCoachCard)this.m_randomCards.get(this.m_randomizer.nextInt(this.m_randomCards.size()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RC getHighestCardFromValues(int minValue, int maxValue)
/*     */   {
/*  79 */     TIntObjectIterator<RC> it = this.m_cards.iterator();
/*  80 */     this.m_randomCards.clear();
/*     */     
/*  82 */     RC bestCard = null;
/*  83 */     int highestValue = 0;
/*     */     
/*  85 */     while (it.hasNext()) {
/*  86 */       it.advance();
/*  87 */       RC referenceCard = (AbstractReferenceCoachCard)it.value();
/*     */       
/*  89 */       int cardValue = referenceCard.getValue();
/*     */       
/*  91 */       if ((cardValue >= highestValue) && (cardValue >= minValue) && (cardValue <= maxValue)) {
/*  92 */         bestCard = referenceCard;
/*  93 */         highestValue = referenceCard.getValue();
/*     */       }
/*     */     }
/*     */     
/*  97 */     return bestCard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<RC> iterator()
/*     */   {
/* 107 */     return this.m_randomCards.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractReferenceCoachCardManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */