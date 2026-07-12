/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemExchangerEndEvent
/*     */   extends ItemExchangerEvent
/*     */   implements Poolable
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(ItemExchangerEndEvent.class);
/*     */   private Reason m_reason;
/*     */   
/*     */   public enum Reason
/*     */   {
/*  27 */     INVITATION_IMPOSSIBLE_USER_BUSY,
/*     */     
/*  29 */     INVITATION_LOCALLY_CANCELED,
/*     */     
/*  31 */     INVITATION_REMOTELY_CANCELED,
/*     */     
/*  33 */     REMOTELY_CANCELED,
/*     */     
/*  35 */     LOCALLY_CANCELED,
/*     */     
/*  37 */     EXCHANGE_DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<ItemExchangerEndEvent>() { public ItemExchangerEndEvent makeObject() {
/*  48 */           return new ItemExchangerEndEvent();
/*     */         } }
/*     */     );
/*     */   public static ItemExchangerEndEvent checkOut(ItemExchanger itemExchanger, Reason reason) {
/*     */     ItemExchangerEndEvent event;
/*     */     try {
/*  54 */       event = (ItemExchangerEndEvent)m_staticPool.borrowObject();
/*  55 */       event.m_pool = m_staticPool;
/*  56 */     } catch (Exception e) {
/*  57 */       m_logger.error("Erreur lors d'un checkOut sur un message de type ItemExchangerEndEvent : " + e.getMessage());
/*  58 */       event = new ItemExchangerEndEvent();
/*     */     } 
/*  60 */     event.init(itemExchanger, reason);
/*  61 */     return event;
/*     */   }
/*     */   
/*     */   protected ItemExchangerEndEvent() {
/*  65 */     this.m_action = null;
/*  66 */     this.m_itemExchanger = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(ItemExchanger itemExchanger, Reason reason) {
/*  75 */     init(itemExchanger, ItemExchangerEvent.Action.EXCHANGE_END);
/*  76 */     this.m_reason = reason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() throws Exception {
/*  86 */     if (this.m_pool != null) {
/*  87 */       this.m_pool.returnObject(this);
/*  88 */       this.m_pool = null;
/*     */     } else {
/*  90 */       onCheckIn();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Reason getReason() {
/*  95 */     return this.m_reason;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/*  99 */     super.onCheckOut();
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 103 */     super.onCheckIn();
/* 104 */     this.m_reason = null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\event\ItemExchangerEndEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */