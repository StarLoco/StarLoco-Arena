/*    */ package com.ankamagames.dofusarena.common.game.card;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
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
/*    */ 
/*    */ 
/*    */ public class AbstractFighterCardInventoryChecker<C extends AbstractFighterCard>
/*    */   implements InventoryContentChecker<C>
/*    */ {
/*    */   public static final int OK = 0;
/*    */   public static final int POSITION_NEEDED = 1;
/*    */   public static final int INVALID_POSITION = 2;
/*    */   private AbstractFighter m_fighter;
/*    */   
/*    */   protected AbstractFighterCardInventoryChecker(AbstractFighter fighter) {
/* 33 */     this.m_fighter = fighter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int canAddItem(Inventory inventory, C item) {
/* 43 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int canAddItem(Inventory inventory, C item, short position) {
/* 54 */     if (item.getType().getInventoryPosition() != position)
/* 55 */       return 2; 
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int canReplaceItem(Inventory inventory, C oldItem, C newItem) {
/* 67 */     if (oldItem.getType() != newItem.getType())
/* 68 */       return 2; 
/* 69 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int canRemoveItem(Inventory inventory, C item) {
/* 79 */     return 0;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\AbstractFighterCardInventoryChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */