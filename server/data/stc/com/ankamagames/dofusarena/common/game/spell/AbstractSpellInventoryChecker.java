/*    */ package com.ankamagames.dofusarena.common.game.spell;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
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
/*    */ public class AbstractSpellInventoryChecker<C extends AbstractSpell>
/*    */   implements InventoryContentChecker<C>
/*    */ {
/*    */   public static final int OK = 0;
/*    */   public static final int INVALID_BREED = 1;
/*    */   private AbstractFighter m_fighter;
/*    */   
/*    */   protected AbstractSpellInventoryChecker(AbstractFighter fighter)
/*    */   {
/* 31 */     this.m_fighter = fighter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int canAddItem(Inventory inventory, C item)
/*    */   {
/* 41 */     Breed breed = this.m_fighter.getBreed();
/* 42 */     if ((breed == null) || (item.getBreedId() != breed.getId())) {
/* 43 */       return 1;
/*    */     }
/* 45 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int canAddItem(Inventory inventory, C item, short position)
/*    */   {
/* 56 */     Breed breed = this.m_fighter.getBreed();
/* 57 */     if ((breed == null) || (item.getBreedId() != breed.getId()))
/* 58 */       return 1;
/* 59 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int canReplaceItem(Inventory inventory, C oldItem, C newItem)
/*    */   {
/* 70 */     if (oldItem.getBreedId() != newItem.getBreedId())
/* 71 */       return 1;
/* 72 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int canRemoveItem(Inventory inventory, C item)
/*    */   {
/* 82 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\spell\AbstractSpellInventoryChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */