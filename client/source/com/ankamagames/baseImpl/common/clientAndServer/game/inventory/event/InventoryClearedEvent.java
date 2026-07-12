/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
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
/*    */ public class InventoryClearedEvent
/*    */   extends InventoryEvent
/*    */ {
/* 21 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<InventoryClearedEvent>() { public InventoryClearedEvent makeObject() {
/* 22 */           return new InventoryClearedEvent(null);
/*    */         } }
/*    */     );
/*    */   
/*    */   private InventoryClearedEvent() {}
/*    */   
/*    */   public static InventoryClearedEvent checkOut(Inventory inventory) {
/*    */     InventoryClearedEvent event;
/*    */     try {
/* 31 */       event = (InventoryClearedEvent)m_staticPool.borrowObject();
/* 32 */       event.m_pool = m_staticPool;
/* 33 */     } catch (Exception e) {
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un message de type InventoryClearedEvent : " + e.getMessage());
/* 35 */       event = new InventoryClearedEvent();
/*    */     } 
/* 37 */     event.init(inventory, InventoryEvent.Action.CLEARED);
/* 38 */     return event;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\InventoryClearedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */