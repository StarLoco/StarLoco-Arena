/*    */ package com.ankamagames.dofusarena.client.core.game.card.fighter;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.constants.FighterCardType;
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractFighterCardManager;
/*    */ import gnu.trove.TLongObjectHashMap;
/*    */ import gnu.trove.TLongObjectIterator;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FighterCardManager
/*    */   extends AbstractFighterCardManager<FighterCard>
/*    */ {
/* 22 */   private static final FighterCardManager m_instance = new FighterCardManager();
/*    */   
/*    */ 
/*    */ 
/*    */   public static FighterCardManager getInstance()
/*    */   {
/* 28 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArrayList<FighterCard> getFighterCardsByType(FighterCardType type)
/*    */   {
/* 39 */     ArrayList<FighterCard> cards = new ArrayList();
/* 40 */     for (TLongObjectIterator<FighterCard> it = getFighterCards().iterator(); it.hasNext();) {
/* 41 */       it.advance();
/* 42 */       if (((FighterCard)it.value()).getType() == type) {
/* 43 */         cards.add((FighterCard)it.value());
/*    */       }
/*    */     }
/* 46 */     Collections.sort(cards);
/* 47 */     return cards;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public FighterCard unserializeContent(ByteBuffer buf)
/*    */   {
/* 58 */     FighterCard refFighterCard = (FighterCard)super.unserializeContent(buf);
/* 59 */     if ((refFighterCard != null) && (refFighterCard.isUsable())) {
/* 60 */       return new UsableFighterCard(refFighterCard);
/*    */     }
/* 62 */     return refFighterCard;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\fighter\FighterCardManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */