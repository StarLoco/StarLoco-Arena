/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*    */ public class InventoryItemModifiedEvent
/*    */   extends InventoryEvent
/*    */ {
/*    */   protected InventoryContent m_concernedItem;
/*    */   protected short m_position;
/*    */   
/* 28 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<InventoryItemModifiedEvent>() { public InventoryItemModifiedEvent makeObject() {
/* 29 */           return new InventoryItemModifiedEvent(null);
/*    */         } }
/*    */     );
/*    */   private InventoryItemModifiedEvent() {}
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutAddEvent(Inventory inventory, InventoryContent itemAdded) {
/* 35 */     return checkOut(inventory, InventoryEvent.Action.ITEM_ADDED, itemAdded, (short)0);
/*    */   }
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutAddEvent(Inventory inventory, InventoryContent itemAdded, short position) {
/* 39 */     return checkOut(inventory, InventoryEvent.Action.ITEM_ADDED, itemAdded, position);
/*    */   }
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutQuantityEvent(Inventory inventory, InventoryContent itemModified) {
/* 43 */     return checkOut(inventory, InventoryEvent.Action.ITEM_QUANTITY_MODIFIED, itemModified, (short)0);
/*    */   }
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutQuantityEvent(Inventory inventory, InventoryContent itemModified, short position) {
/* 47 */     return checkOut(inventory, InventoryEvent.Action.ITEM_QUANTITY_MODIFIED, itemModified, position);
/*    */   }
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutRemoveEvent(Inventory inventory, InventoryContent itemRemoved) {
/* 51 */     return checkOut(inventory, InventoryEvent.Action.ITEM_REMOVED, itemRemoved, (short)0);
/*    */   }
/*    */   
/*    */   public static InventoryItemModifiedEvent checkOutRemoveEvent(Inventory inventory, InventoryContent itemRemoved, short position) {
/* 55 */     return checkOut(inventory, InventoryEvent.Action.ITEM_REMOVED, itemRemoved, position);
/*    */   }
/*    */ 
/*    */   
/*    */   static InventoryItemModifiedEvent checkOut(Inventory inventory, InventoryEvent.Action action, InventoryContent itemModified, short position) {
/*    */     InventoryItemModifiedEvent event;
/*    */     try {
/* 62 */       event = (InventoryItemModifiedEvent)m_staticPool.borrowObject();
/* 63 */       event.m_pool = m_staticPool;
/* 64 */     } catch (Exception e) {
/* 65 */       m_logger.error("Erreur lors d'un checkOut sur un message de type InventoryItemModifiedEvent : " + e.getMessage());
/* 66 */       event = new InventoryItemModifiedEvent();
/*    */     } 
/* 68 */     event.init(inventory, action);
/* 69 */     event.m_concernedItem = itemModified;
/* 70 */     event.m_position = position;
/* 71 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryContent getConcernedItem() {
/* 79 */     return this.m_concernedItem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getPosition() {
/* 88 */     return this.m_position;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\InventoryItemModifiedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */