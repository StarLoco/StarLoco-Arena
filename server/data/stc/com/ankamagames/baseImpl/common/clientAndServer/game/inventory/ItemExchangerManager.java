/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ public class ItemExchangerManager
/*    */ {
/* 19 */   protected static final Logger m_logger = Logger.getLogger(ItemExchangerManager.class);
/*    */   
/* 21 */   private static final ItemExchangerManager m_uniqueInstance = new ItemExchangerManager();
/*    */   
/*    */   public static ItemExchangerManager getInstance() {
/* 24 */     return m_uniqueInstance;
/*    */   }
/*    */   
/* 27 */   protected final HashMap<Long, ItemExchanger> m_exchangers = new HashMap();
/*    */   
/*    */ 
/*    */   public ItemExchanger getItemExchanger(long exchangeId)
/*    */   {
/* 32 */     return (ItemExchanger)this.m_exchangers.get(Long.valueOf(exchangeId));
/*    */   }
/*    */   
/*    */   public boolean addExchanger(ItemExchanger exchanger) {
/* 36 */     if (this.m_exchangers.containsKey(Long.valueOf(exchanger.getId()))) {
/* 37 */       m_logger.error("Impossible d'ajouter l'échange " + exchanger.getClass().getName() + " : un échange avec le même id (" + exchanger.getId() + ") existe déjà.");
/* 38 */       return false;
/*    */     }
/* 40 */     this.m_exchangers.put(Long.valueOf(exchanger.getId()), exchanger);
/* 41 */     return true;
/*    */   }
/*    */   
/*    */   public boolean removeExchanger(ItemExchanger exchanger) {
/* 45 */     return this.m_exchangers.remove(Long.valueOf(exchanger.getId())) != null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\ItemExchangerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */