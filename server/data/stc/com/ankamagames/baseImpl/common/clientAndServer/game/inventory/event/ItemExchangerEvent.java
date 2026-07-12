/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemExchangerEvent
/*     */   implements Poolable
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(ItemExchangerEvent.class);
/*     */   protected Action m_action;
/*     */   protected ItemExchanger m_itemExchanger;
/*     */   
/*     */   public static enum Action {
/*  27 */     EXCHANGE_REQUESTED, 
/*     */     
/*  29 */     EXCHANGE_PROPOSED, 
/*     */     
/*  31 */     EXCHANGE_STARTED, 
/*     */     
/*  33 */     EXCHANGE_CONTENT_MODIFIED, 
/*     */     
/*  35 */     EXCHANGE_USER_READY, 
/*     */     
/*  37 */     EXCHANGE_END;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  47 */   protected byte m_userIndex = -1;
/*     */   
/*     */ 
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */ 
/*  53 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*  54 */     public ItemExchangerEvent makeObject() { return new ItemExchangerEvent(); }
/*  53 */   });
/*     */   
/*     */   public static ItemExchangerEvent checkOut(ItemExchanger itemExchanger, Action action)
/*     */   {
/*     */     ItemExchangerEvent event;
/*     */     try
/*     */     {
/*  60 */       ItemExchangerEvent event = (ItemExchangerEvent)m_staticPool.borrowObject();
/*  61 */       event.m_pool = m_staticPool;
/*     */     } catch (Exception e) {
/*  63 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ItemExchangerEvent : " + e.getMessage());
/*  64 */       event = new ItemExchangerEvent();
/*     */     }
/*  66 */     event.init(itemExchanger, action);
/*  67 */     return event;
/*     */   }
/*     */   
/*     */   protected ItemExchangerEvent() {
/*  71 */     this.m_action = null;
/*  72 */     this.m_itemExchanger = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void init(ItemExchanger itemExchanger, Action action)
/*     */   {
/*  81 */     this.m_itemExchanger = itemExchanger;
/*  82 */     this.m_action = action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */     throws Exception
/*     */   {
/*  92 */     if (this.m_pool != null) {
/*  93 */       this.m_pool.returnObject(this);
/*  94 */       this.m_pool = null;
/*     */     } else {
/*  96 */       onCheckIn();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getAction()
/*     */   {
/* 105 */     return this.m_action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemExchanger getItemExchanger()
/*     */   {
/* 113 */     return this.m_itemExchanger;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUserIndex(byte userIndex)
/*     */   {
/* 121 */     this.m_userIndex = userIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getUserIndex()
/*     */   {
/* 129 */     return this.m_userIndex;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */   
/*     */   public void onCheckIn() {
/* 135 */     this.m_itemExchanger = null;
/* 136 */     this.m_action = null;
/* 137 */     this.m_userIndex = -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\ItemExchangerEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */