/*    */ package com.ankamagames.dofusarena.common.game.card;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ public class CardSetManager
/*    */ {
/* 17 */   private static final CardSetManager m_uniqueInstance = new CardSetManager();
/*    */   
/*    */   public static CardSetManager getInstance() {
/* 20 */     return m_uniqueInstance;
/*    */   }
/*    */   private final HashMap<Integer, CardSet<AbstractReferenceCoachCard>> m_sets;
/*    */   
/*    */   private CardSetManager() {
/* 25 */     this.m_sets = new HashMap<Integer, CardSet<AbstractReferenceCoachCard>>();
/*    */   }
/*    */   public void add(CardSet<AbstractReferenceCoachCard> set) {
/* 28 */     this.m_sets.put(Integer.valueOf(set.getId()), set);
/*    */   }
/*    */   
/*    */   public CardSet<AbstractReferenceCoachCard> get(int id) {
/* 32 */     return this.m_sets.get(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public CardSet<AbstractReferenceCoachCard> remove(int id) {
/* 36 */     return this.m_sets.remove(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public void removeAll() {
/* 40 */     this.m_sets.clear();
/*    */   }
/*    */   
/*    */   public void getSets() {
/* 44 */     this.m_sets.values();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\CardSetManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */