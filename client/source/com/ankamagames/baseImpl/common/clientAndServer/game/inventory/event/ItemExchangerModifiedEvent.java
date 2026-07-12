/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemExchangerModifiedEvent
/*    */   extends ItemExchangerEvent
/*    */   implements Poolable
/*    */ {
/*    */   private Modification m_modification;
/*    */   private InventoryContent m_content;
/* 21 */   protected static final Logger m_logger = Logger.getLogger(ItemExchangerModifiedEvent.class);
/*    */   private short m_contentQuantity;
/*    */   
/*    */   public enum Modification {
/* 25 */     CONTENT_ADDED,
/* 26 */     CONTENT_REMOVED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ItemExchangerModifiedEvent>() { public ItemExchangerModifiedEvent makeObject() {
/* 35 */           return new ItemExchangerModifiedEvent();
/*    */         } }
/*    */     );
/*    */   public static ItemExchangerModifiedEvent checkOut(ItemExchanger itemExchanger, Modification modification, byte userIndex, InventoryContent content, short contentQuantity) {
/*    */     ItemExchangerModifiedEvent event;
/*    */     try {
/* 41 */       event = (ItemExchangerModifiedEvent)m_staticPool.borrowObject();
/* 42 */       event.m_pool = m_staticPool;
/* 43 */     } catch (Exception e) {
/* 44 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ItemExchangerEndEvent : " + e.getMessage());
/* 45 */       event = new ItemExchangerModifiedEvent();
/*    */     } 
/* 47 */     event.init(itemExchanger, modification, userIndex, content, contentQuantity);
/* 48 */     return event;
/*    */   }
/*    */   
/*    */   private void init(ItemExchanger itemExchanger, Modification modification, byte userIndex, InventoryContent content, short contentQuantity) {
/* 52 */     init(itemExchanger, ItemExchangerEvent.Action.EXCHANGE_CONTENT_MODIFIED);
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\ItemExchangerModifiedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */