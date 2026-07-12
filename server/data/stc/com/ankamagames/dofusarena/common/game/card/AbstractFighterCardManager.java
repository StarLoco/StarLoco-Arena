/*    */ package com.ankamagames.dofusarena.common.game.card;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*    */ import gnu.trove.TLongObjectHashMap;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public abstract class AbstractFighterCardManager<FC extends AbstractFighterCard>
/*    */   implements InventoryContentProvider<FC>
/*    */ {
/* 21 */   protected static final Logger m_logger = Logger.getLogger(AbstractFighterCardManager.class);
/*    */   
/* 23 */   private final TLongObjectHashMap<FC> m_cards = new TLongObjectHashMap();
/*    */   
/*    */ 
/*    */   public void add(FC card)
/*    */   {
/* 28 */     this.m_cards.put(card.getId(), card);
/*    */   }
/*    */   
/*    */   public TLongObjectHashMap<FC> getFighterCards() {
/* 32 */     return this.m_cards;
/*    */   }
/*    */   
/*    */   public FC get(long id) {
/* 36 */     return (AbstractFighterCard)this.m_cards.get(id);
/*    */   }
/*    */   
/*    */   public FC unserializeContent(ByteBuffer buf) {
/* 40 */     return (AbstractFighterCard)this.m_cards.get(buf.getInt());
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractFighterCardManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */