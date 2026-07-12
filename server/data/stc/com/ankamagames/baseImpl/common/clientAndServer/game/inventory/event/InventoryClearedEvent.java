/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.Inventory;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
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
/*    */ 
/*    */ 
/*    */ public class InventoryClearedEvent
/*    */   extends InventoryEvent
/*    */ {
/* 21 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 22 */     public InventoryClearedEvent makeObject() { return new InventoryClearedEvent(null); }
/* 21 */   });
/*    */   
/*    */ 
/*    */ 
/*    */   public static InventoryClearedEvent checkOut(Inventory inventory)
/*    */   {
/*    */     InventoryClearedEvent event;
/*    */     
/*    */     try
/*    */     {
/* 31 */       InventoryClearedEvent event = (InventoryClearedEvent)m_staticPool.borrowObject();
/* 32 */       event.m_pool = m_staticPool;
/*    */     } catch (Exception e) {
/* 34 */       m_logger.error("Erreur lors d'un checkOut sur un message de type InventoryClearedEvent : " + e.getMessage());
/* 35 */       event = new InventoryClearedEvent();
/*    */     }
/* 37 */     event.init(inventory, InventoryEvent.Action.CLEARED);
/* 38 */     return event;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\InventoryClearedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */