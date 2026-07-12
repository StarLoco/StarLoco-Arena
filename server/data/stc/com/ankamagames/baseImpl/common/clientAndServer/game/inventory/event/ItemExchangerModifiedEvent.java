/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
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
/*    */ public class ItemExchangerModifiedEvent
/*    */   extends ItemExchangerEvent
/*    */   implements Poolable
/*    */ {
/* 21 */   protected static final Logger m_logger = Logger.getLogger(ItemExchangerModifiedEvent.class);
/*    */   private Modification m_modification;
/*    */   
/*    */   public static enum Modification {
/* 25 */     CONTENT_ADDED, 
/* 26 */     CONTENT_REMOVED;
/*    */   }
/*    */   
/*    */ 
/*    */   private InventoryContent m_content;
/*    */   
/*    */   private short m_contentQuantity;
/*    */   
/* 34 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 35 */     public ItemExchangerModifiedEvent makeObject() { return new ItemExchangerModifiedEvent();
/*    */     }
/* 34 */   });
/*    */   
/*    */   public static ItemExchangerModifiedEvent checkOut(ItemExchanger itemExchanger, Modification modification, byte userIndex, InventoryContent content, short contentQuantity)
/*    */   {
/*    */     ItemExchangerModifiedEvent event;
/*    */     try
/*    */     {
/* 41 */       ItemExchangerModifiedEvent event = (ItemExchangerModifiedEvent)m_staticPool.borrowObject();
/* 42 */       event.m_pool = m_staticPool;
/*    */     } catch (Exception e) {
/* 44 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ItemExchangerEndEvent : " + e.getMessage());
/* 45 */       event = new ItemExchangerModifiedEvent();
/*    */     }
/* 47 */     event.init(itemExchanger, modification, userIndex, content, contentQuantity);
/* 48 */     return event;
/*    */   }
/*    */   
/*    */   private void init(ItemExchanger itemExchanger, Modification modification, byte userIndex, InventoryContent content, short contentQuantity) {
/* 52 */     super.init(itemExchanger, ItemExchangerEvent.Action.EXCHANGE_CONTENT_MODIFIED);
/* 53 */     this.m_modification = modification;
/* 54 */     this.m_userIndex = userIndex;
/* 55 */     this.m_content = content;
/* 56 */     this.m_contentQuantity = contentQuantity;
/*    */   }
/*    */   
/*    */   public Modification getModification() {
/* 60 */     return this.m_modification;
/*    */   }
/*    */   
/*    */   public InventoryContent getContent() {
/* 64 */     return this.m_content;
/*    */   }
/*    */   
/*    */   public short getContentQuantity() {
/* 68 */     return this.m_contentQuantity;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\ItemExchangerModifiedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */