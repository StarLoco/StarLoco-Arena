/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryObserver;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public abstract class Inventory<ContentType extends InventoryContent>
/*     */   implements Iterable<ContentType>
/*     */ {
/*     */   public static final short SIZE_INFINITE = -1;
/*  29 */   protected static final Logger m_logger = Logger.getLogger(Inventory.class);
/*     */ 
/*     */   
/*     */   protected short m_maximumSize;
/*     */ 
/*     */   
/*  35 */   protected final List<InventoryObserver> m_eventsObservers = new ArrayList<InventoryObserver>(1);
/*     */ 
/*     */   
/*     */   protected final boolean m_stackable;
/*     */ 
/*     */   
/*     */   protected Inventory(boolean stackable) {
/*  42 */     this.m_stackable = stackable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObserver(InventoryObserver observer) {
/*  51 */     if (observer == null)
/*     */       return; 
/*  53 */     if (!this.m_eventsObservers.contains(observer)) {
/*  54 */       this.m_eventsObservers.add(observer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObserver(InventoryObserver observer) {
/*  62 */     if (observer == null)
/*     */       return; 
/*  64 */     this.m_eventsObservers.remove(observer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyObservers(InventoryEvent event) {
/*  73 */     notifyObservers(event, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void notifyObservers(InventoryEvent event, boolean releaseEvent) {
/*  82 */     for (InventoryObserver observer : this.m_eventsObservers)
/*  83 */       observer.onInventoryEvent(event); 
/*  84 */     if (releaseEvent) {
/*     */       try {
/*  86 */         event.release();
/*  87 */       } catch (Exception e) {
/*  88 */         m_logger.error(ExceptionFormatter.toString(e));
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setMaximumSize(short maxSize) {
/* 105 */     if (this.m_maximumSize > 0 && maxSize < size()) {
/* 106 */       m_logger.error("Can't change the size of the inventory to " + maxSize + " : current size is " + size());
/* 107 */       return false;
/*     */     } 
/* 109 */     if (maxSize < 0) {
/* 110 */       this.m_maximumSize = -1;
/*     */     } else {
/* 112 */       this.m_maximumSize = maxSize;
/* 113 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getMaximumSize() {
/* 121 */     return this.m_maximumSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 129 */     return (this.m_maximumSize != -1 && size() >= this.m_maximumSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 137 */     return (size() == 0);
/*     */   }
/*     */   
/*     */   public abstract byte[] serialize();
/*     */   
/*     */   public abstract boolean unserialize(byte[] paramArrayOfbyte);
/*     */   
/*     */   public abstract boolean add(ContentType paramContentType) throws InventoryCapacityReachedException, ContentAlreadyPresentException;
/*     */   
/*     */   public abstract boolean updateQuantity(long paramLong, short paramShort);
/*     */   
/*     */   public abstract boolean replace(ContentType paramContentType1, ContentType paramContentType2) throws ContentAlreadyPresentException;
/*     */   
/*     */   public abstract boolean remove(ContentType paramContentType);
/*     */   
/*     */   public abstract boolean destroy(ContentType paramContentType);
/*     */   
/*     */   @Nullable
/*     */   public abstract ContentType removeWithUniqueId(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   public abstract boolean destroyWithUniqueId(long paramLong);
/*     */   
/*     */   public abstract boolean contains(ContentType paramContentType);
/*     */   
/*     */   public abstract boolean containsUniqueId(long paramLong);
/*     */   
/*     */   public abstract boolean containsReferenceId(int paramInt);
/*     */   
/*     */   @Nullable
/*     */   public abstract ContentType getWithUniqueId(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   public abstract ContentType getFirstWithReferenceId(int paramInt);
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public abstract int removeAll();
/*     */   
/*     */   public abstract int destroyAll();
/*     */   
/*     */   public abstract Iterator<ContentType> iterator();
/*     */   
/*     */   public abstract InventoryContentChecker<ContentType> getContentChecker();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\Inventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */