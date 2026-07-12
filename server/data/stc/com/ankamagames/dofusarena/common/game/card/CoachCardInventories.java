/*     */ package com.ankamagames.dofusarena.common.game.card;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentChecker;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryItemModifiedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.PositionAlreadyUsedException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoachCardInventories<CoachCard extends AbstractCoachCard>
/*     */   implements InventoryObserver, InventoryContentChecker<CoachCard>
/*     */ {
/*  29 */   protected static final Logger m_logger = Logger.getLogger(CoachCardInventories.class);
/*     */   
/*     */   public static final int OK = 0;
/*     */   
/*     */   public static final int ERROR_INVALID_POSITION = 1;
/*     */   
/*     */   protected StackInventory<CoachCard> m_inventory;
/*     */   
/*     */   protected ArrayInventory<CoachCard> m_equipment;
/*  38 */   private static final Set<Long> EMPTY_SET = new HashSet();
/*     */   
/*     */ 
/*  41 */   private final Set<Long> m_lockedItems = new HashSet();
/*     */   
/*     */ 
/*  44 */   private final Map<Integer, Set<Long>> m_itemsPerSet = new HashMap();
/*     */   
/*     */   protected CoachCardInventories() {}
/*     */   
/*     */   public CoachCardInventories(InventoryContentProvider<CoachCard> inventoryProvider, InventoryContentProvider<CoachCard> equipmentProvider) {
/*  49 */     this.m_inventory = new StackInventory((short)100, inventoryProvider, this, false, true, true);
/*  50 */     this.m_equipment = new ArrayInventory(equipmentProvider, this, (short)14, false);
/*  51 */     this.m_inventory.addObserver(this);
/*     */   }
/*     */   
/*     */   public CoachCardInventories(InventoryContentProvider<CoachCard> provider) {
/*  55 */     this(provider, provider);
/*     */   }
/*     */   
/*     */   public byte[] serializeEquipment() {
/*  59 */     return this.m_equipment.serialize();
/*     */   }
/*     */   
/*     */   public byte[] serializeInventory() {
/*  63 */     return this.m_inventory.serialize();
/*     */   }
/*     */   
/*     */   public boolean unserializeEquipment(byte[] data) {
/*  67 */     return this.m_equipment.unserialize(data);
/*     */   }
/*     */   
/*     */   public boolean unserializeInventory(byte[] data) {
/*  71 */     return this.m_inventory.unserialize(data);
/*     */   }
/*     */   
/*     */   public void addEquipmentObserver(InventoryObserver observer) {
/*  75 */     this.m_equipment.addObserver(observer);
/*     */   }
/*     */   
/*     */   public void addInventoryObserver(InventoryObserver observer) {
/*  79 */     this.m_inventory.addObserver(observer);
/*     */   }
/*     */   
/*     */   public void removeEquipmentObserver(InventoryObserver observer) {
/*  83 */     this.m_equipment.removeObserver(observer);
/*     */   }
/*     */   
/*     */   public void removeInventoryObserver(InventoryObserver observer) {
/*  87 */     this.m_inventory.removeObserver(observer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeAll()
/*     */   {
/*  94 */     this.m_itemsPerSet.clear();
/*  95 */     this.m_lockedItems.clear();
/*  96 */     this.m_inventory.removeAll();
/*  97 */     this.m_equipment.removeAll();
/*     */   }
/*     */   
/*     */   public void destroyAll() {
/* 101 */     this.m_itemsPerSet.clear();
/* 102 */     this.m_lockedItems.clear();
/* 103 */     this.m_inventory.destroyAll();
/* 104 */     this.m_equipment.destroyAll();
/*     */   }
/*     */   
/*     */   public boolean containsInEquipment(CoachCard card)
/*     */   {
/* 109 */     return this.m_equipment.contains(card);
/*     */   }
/*     */   
/*     */   public CoachCard getEquipmentAt(short pos) {
/* 113 */     return (AbstractCoachCard)this.m_equipment.getFromPosition(pos);
/*     */   }
/*     */   
/*     */   public boolean removeEquipment(CoachCard card) {
/* 117 */     return this.m_equipment.remove(card);
/*     */   }
/*     */   
/*     */   public boolean removeEquipmentAt(short pos) {
/* 121 */     return this.m_equipment.removeAt(pos);
/*     */   }
/*     */   
/*     */   public boolean addEquipmentAt(CoachCard card, short pos) throws ContentAlreadyPresentException, PositionAlreadyUsedException, InventoryCapacityReachedException {
/* 125 */     return this.m_equipment.addAt(card, pos);
/*     */   }
/*     */   
/*     */   public int canAddEquipment(CoachCard card, short pos) {
/* 129 */     if (this.m_equipment.getContentChecker() == null)
/* 130 */       return 0;
/* 131 */     return this.m_equipment.getContentChecker().canAddItem(this.m_equipment, card, pos);
/*     */   }
/*     */   
/*     */   public Iterable<CoachCard> getEquipmentCards() {
/* 135 */     return this.m_equipment;
/*     */   }
/*     */   
/*     */   public CoachCard getFromEquipment(long uniqueId) {
/* 139 */     return (AbstractCoachCard)this.m_equipment.getWithUniqueId(uniqueId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEquipmentPositionFree(short position)
/*     */   {
/* 148 */     return this.m_equipment.isPositionFree(position);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean containsInInventory(CoachCard card)
/*     */   {
/* 154 */     return this.m_inventory.contains(card);
/*     */   }
/*     */   
/*     */   public boolean removeFromInventory(CoachCard card) {
/* 158 */     return this.m_inventory.remove(card);
/*     */   }
/*     */   
/*     */   public boolean destroyFromInventory(CoachCard card) {
/* 162 */     return this.m_inventory.destroy(card);
/*     */   }
/*     */   
/*     */   public CoachCard removeFromInventory(long uniqueId) {
/* 166 */     return (AbstractCoachCard)this.m_inventory.removeWithUniqueId(uniqueId);
/*     */   }
/*     */   
/*     */   public boolean destroyFromInventory(long uniqueId) {
/* 170 */     return this.m_inventory.destroyWithUniqueId(uniqueId);
/*     */   }
/*     */   
/*     */   public boolean addToInventory(CoachCard card) throws ContentAlreadyPresentException, InventoryCapacityReachedException {
/* 174 */     if (card == null) {
/* 175 */       m_logger.error("Impossible d'ajotuer une carte nulle à un inventaire.");
/* 176 */       return false;
/*     */     }
/*     */     
/* 179 */     if (!card.isCursed())
/*     */     {
/* 181 */       CoachCard oldCard = (AbstractCoachCard)this.m_inventory.getFirstWithReferenceId(card.getReferenceId());
/* 182 */       if ((oldCard != null) && 
/* 183 */         (oldCard.isCursed()))
/* 184 */         this.m_inventory.destroy(oldCard);
/* 185 */       return this.m_inventory.add(card);
/*     */     }
/*     */     
/* 188 */     CoachCard oldCard = (AbstractCoachCard)this.m_inventory.getFirstWithReferenceId(card.getReferenceId());
/* 189 */     if ((oldCard != null) && 
/* 190 */       (!oldCard.isCursed()))
/* 191 */       return false;
/* 192 */     return this.m_inventory.add(card);
/*     */   }
/*     */   
/*     */   public Iterable<CoachCard> getInventoryCards()
/*     */   {
/* 197 */     return this.m_inventory;
/*     */   }
/*     */   
/*     */   public CoachCard getFromInventory(long uniqueId) {
/* 201 */     return (AbstractCoachCard)this.m_inventory.getWithUniqueId(uniqueId);
/*     */   }
/*     */   
/*     */   public CoachCard getFirstWithReferenceIdFromInventory(int referenceId) {
/* 205 */     return (AbstractCoachCard)this.m_inventory.getFirstWithReferenceId(referenceId);
/*     */   }
/*     */   
/*     */   public boolean updateInventoryQuantity(long uniqueId, short quantityUpdate) {
/* 209 */     return this.m_inventory.updateQuantity(uniqueId, quantityUpdate);
/*     */   }
/*     */   
/*     */   public boolean lockCard(CoachCard item) {
/* 213 */     if (!canLockCard(item))
/* 214 */       return false;
/* 215 */     this.m_lockedItems.add(Long.valueOf(item.getUniqueId()));
/* 216 */     item.setLocked(true);
/* 217 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unlockCard(CoachCard item) {
/* 221 */     if (!canUnlockCard(item))
/* 222 */       return false;
/* 223 */     this.m_lockedItems.remove(Long.valueOf(item.getUniqueId()));
/* 224 */     item.setLocked(false);
/* 225 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onInventoryEvent(InventoryEvent event)
/*     */   {
/* 231 */     switch (event.getAction()) {
/*     */     case ITEM_REMOVED_AT: 
/* 233 */       this.m_lockedItems.clear();
/* 234 */       this.m_itemsPerSet.clear();
/* 235 */       break;
/*     */     case CLEARED: 
/*     */     case ITEM_ADDED: 
/* 238 */       CoachCard c = (AbstractCoachCard)((InventoryItemModifiedEvent)event).getConcernedItem();
/* 239 */       if (c.isLocked())
/* 240 */         this.m_lockedItems.add(Long.valueOf(c.getUniqueId()));
/* 241 */       CardSet set = c.getReferenceCard().getCardSet();
/* 242 */       if (set != null) {
/* 243 */         Set<Long> cards = (Set)this.m_itemsPerSet.get(Integer.valueOf(set.getId()));
/* 244 */         if (cards == null) {
/* 245 */           cards = new HashSet();
/* 246 */           this.m_itemsPerSet.put(Integer.valueOf(set.getId()), cards);
/*     */         }
/* 248 */         cards.add(Long.valueOf(c.getUniqueId()));
/*     */       }
/* 250 */       break;
/*     */     
/*     */     case ITEM_ADDED_AT: 
/*     */     case ITEM_QUANTITY_MODIFIED: 
/* 254 */       CoachCard c = (AbstractCoachCard)((InventoryItemModifiedEvent)event).getConcernedItem();
/* 255 */       if (c.isLocked())
/* 256 */         this.m_lockedItems.remove(Long.valueOf(c.getUniqueId()));
/* 257 */       CardSet set = c.getReferenceCard().getCardSet();
/* 258 */       if (set != null) {
/* 259 */         Set<Long> cards = (Set)this.m_itemsPerSet.get(Integer.valueOf(set.getId()));
/* 260 */         if (cards != null) {
/* 261 */           cards.remove(Long.valueOf(c.getUniqueId()));
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getLockedItemsCount()
/*     */   {
/* 270 */     return this.m_lockedItems.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int canAddItem(Inventory inventory, CoachCard item)
/*     */   {
/* 281 */     return 0;
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
/*     */   public int canAddItem(Inventory inventory, CoachCard item, short position)
/*     */   {
/* 294 */     if (inventory == this.m_equipment) {
/* 295 */       CoachCardType type = item.getReferenceCard().getType();
/* 296 */       if (type == null) {
/* 297 */         m_logger.error("Type de carte inconnu pour la carte " + item.getReferenceId());
/* 298 */         return 0;
/*     */       }
/* 300 */       short[] possiblePositions = type.getInventoryPosition();
/* 301 */       boolean bFound = false;
/* 302 */       if (possiblePositions != null)
/* 303 */         for (int i = possiblePositions.length - 1; i >= 0; i--)
/* 304 */           if (possiblePositions[i] == position) {
/* 305 */             bFound = true;
/* 306 */             break;
/*     */           }
/* 308 */       if (!bFound)
/* 309 */         return 1;
/*     */     }
/* 311 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int canReplaceItem(Inventory inventory, CoachCard oldItem, CoachCard newItem)
/*     */   {
/* 323 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int canRemoveItem(Inventory inventory, CoachCard item)
/*     */   {
/* 334 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean canUnlockCard(CoachCard card) {
/* 338 */     return card.isLocked();
/*     */   }
/*     */   
/*     */   protected boolean canLockCard(CoachCard card) {
/* 342 */     return (!card.isLocked()) && (getLockedItemsCount() < 10);
/*     */   }
/*     */   
/*     */   public Set<Long> getSetOwnedCards(int setId) {
/* 346 */     Set<Long> cards = (Set)this.m_itemsPerSet.get(Integer.valueOf(setId));
/* 347 */     return cards != null ? cards : EMPTY_SET;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\CoachCardInventories.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */