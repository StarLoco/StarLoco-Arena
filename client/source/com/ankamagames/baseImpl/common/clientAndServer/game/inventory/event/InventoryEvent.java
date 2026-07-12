/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InventoryEvent
/*    */   implements Poolable
/*    */ {
/*    */   protected Action m_action;
/*    */   protected Inventory m_inventory;
/* 20 */   protected static final Logger m_logger = Logger.getLogger(InventoryEvent.class);
/*    */   protected ObjectPool m_pool;
/*    */   
/*    */   public enum Action {
/* 24 */     ITEM_ADDED,
/* 25 */     ITEM_ADDED_AT,
/* 26 */     ITEM_REMOVED,
/* 27 */     ITEM_REMOVED_AT,
/* 28 */     ITEM_QUANTITY_MODIFIED,
/* 29 */     CLEARED;
/*    */   }
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
/*    */   protected InventoryEvent() {
/* 42 */     this.m_action = null;
/* 43 */     this.m_inventory = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void init(Inventory inventory, Action action) {
/* 52 */     this.m_inventory = inventory;
/* 53 */     this.m_action = action;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void release() throws Exception {
/* 63 */     if (this.m_pool != null) {
/* 64 */       this.m_pool.returnObject(this);
/* 65 */       this.m_pool = null;
/*    */     } else {
/* 67 */       onCheckIn();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 76 */     return this.m_action;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Inventory getInventory() {
/* 84 */     return this.m_inventory;
/*    */   }
/*    */   
/*    */   public void onCheckOut() {}
/*    */   
/*    */   public void onCheckIn() {
/* 90 */     this.m_inventory = null;
/* 91 */     this.m_action = null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\InventoryEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */