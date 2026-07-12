/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryClearedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryItemModifiedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ByteArray;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackInventory<ContentType extends InventoryContent>
/*     */   extends Inventory<ContentType>
/*     */ {
/*     */   protected final HashMap<Long, ContentType> m_contents;
/*     */   protected final InventoryContentProvider<ContentType> m_contentProvider;
/*     */   protected final InventoryContentChecker<ContentType> m_contentChecker;
/*     */   protected final boolean m_ordered;
/*     */   protected final boolean m_serializeQuantity;
/*     */   
/*     */   public StackInventory(short maximumSize, InventoryContentProvider<ContentType> contentProvider, InventoryContentChecker<ContentType> contentChecker, boolean ordered, boolean stackable, boolean serializeQuantity) {
/*  64 */     super(stackable);
/*  65 */     setMaximumSize(maximumSize);
/*  66 */     this.m_contents = ordered ? new LinkedHashMap<Long, ContentType>() : new HashMap<Long, ContentType>();
/*  67 */     this.m_contentProvider = contentProvider;
/*  68 */     this.m_contentChecker = contentChecker;
/*  69 */     this.m_ordered = ordered;
/*  70 */     this.m_serializeQuantity = serializeQuantity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] serialize() {
/*  81 */     int estimatedSize = 10 * size();
/*  82 */     ByteArray data = new ByteArray(estimatedSize);
/*     */     
/*  84 */     for (InventoryContent inventoryContent : this) {
/*  85 */       data.put(inventoryContent.serialize());
/*  86 */       if (this.m_serializeQuantity) {
/*  87 */         data.putShort(inventoryContent.getQuantity());
/*     */       }
/*     */     } 
/*  90 */     return data.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unserialize(byte[] data) {
/* 101 */     destroyAll();
/* 102 */     if (data == null) {
/* 103 */       return true;
/*     */     }
/* 105 */     boolean bError = false;
/*     */     
/*     */     try {
/* 108 */       ByteBuffer bf = ByteBuffer.wrap(data);
/*     */       
/* 110 */       while (bf.hasRemaining()) {
/* 111 */         ContentType item = this.m_contentProvider.unserializeContent(bf);
/* 112 */         if (item != null) {
/* 113 */           if (this.m_serializeQuantity)
/* 114 */             item.setQuantity(bf.getShort()); 
/* 115 */           if (!add(item))
/* 116 */             bError = true;  continue;
/*     */         } 
/* 118 */         if (this.m_serializeQuantity)
/* 119 */           bf.getShort(); 
/* 120 */         bError = true;
/*     */       }
/*     */     
/*     */     }
/* 124 */     catch (InventoryCapacityReachedException e) {
/* 125 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/* 126 */       bError = true;
/* 127 */     } catch (ContentAlreadyPresentException e) {
/* 128 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/* 129 */       bError = true;
/*     */     } 
/* 131 */     return !bError;
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
/*     */   public boolean add(ContentType item) throws InventoryCapacityReachedException, ContentAlreadyPresentException {
/* 145 */     if (item == null)
/* 146 */       return false; 
/* 147 */     if (item.getQuantity() <= 0) {
/* 148 */       m_logger.warn("Impossile d'ajouter un item avec un quantitée de " + item.getQuantity());
/* 149 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     if (!this.m_stackable) {
/* 154 */       if (isFull())
/* 155 */         throw new InventoryCapacityReachedException("Cannot add item : maximum size of inventory is reached (" + getMaximumSize() + ")"); 
/* 156 */       if (this.m_contents.containsKey(Long.valueOf(item.getUniqueId())))
/* 157 */         throw new ContentAlreadyPresentException("Item with uniqueID " + item.getUniqueId() + " is already present in the inventory"); 
/* 158 */       if (this.m_contentChecker != null && this.m_contentChecker.canAddItem(this, item) != 0)
/* 159 */         return false; 
/* 160 */       this.m_contents.put(Long.valueOf(item.getUniqueId()), item);
/* 161 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutAddEvent(this, (InventoryContent)item));
/* 162 */       return true;
/*     */     } 
/*     */     
/* 165 */     short remainingQuantity = item.getQuantity();
/* 166 */     int referenceId = item.getReferenceId();
/*     */ 
/*     */     
/* 169 */     for (InventoryContent inventoryContent : this.m_contents.values()) {
/* 170 */       if (inventoryContent.getReferenceId() == referenceId) {
/* 171 */         int emptyPlace = inventoryContent.getStackMaximumHeight() - inventoryContent.getQuantity();
/* 172 */         if (emptyPlace > 0) {
/* 173 */           short quantityToStack = (short)Math.min(remainingQuantity, emptyPlace);
/* 174 */           remainingQuantity = (short)(remainingQuantity - quantityToStack);
/* 175 */           inventoryContent.updateQuantity(quantityToStack);
/* 176 */           notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutQuantityEvent(this, inventoryContent));
/* 177 */           if (remainingQuantity <= 0) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     ContentType newContent = item;
/* 184 */     while (remainingQuantity > 0) {
/*     */       
/* 186 */       short qtyToPut = (short)Math.min(remainingQuantity, item.getStackMaximumHeight());
/* 187 */       newContent.setQuantity(qtyToPut);
/* 188 */       remainingQuantity = (short)(remainingQuantity - qtyToPut);
/* 189 */       if (this.m_contentChecker != null && this.m_contentChecker.canAddItem(this, newContent) != 0)
/* 190 */         return false; 
/* 191 */       this.m_contents.put(Long.valueOf(newContent.getUniqueId()), newContent);
/* 192 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutAddEvent(this, (InventoryContent)newContent));
/* 193 */       if (remainingQuantity > 0) {
/* 194 */         InventoryContent inventoryContent = newContent.getCopy();
/*     */       }
/*     */     } 
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public boolean updateQuantity(long uniqueId, short quantityUpdate) {
/* 201 */     ContentType item = getWithUniqueId(uniqueId);
/* 202 */     if (item == null)
/* 203 */       return false; 
/* 204 */     if (item.getQuantity() + quantityUpdate <= 0) {
/* 205 */       return destroy(item);
/*     */     }
/* 207 */     item.updateQuantity(quantityUpdate);
/* 208 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutQuantityEvent(this, (InventoryContent)item));
/*     */     
/* 210 */     return true;
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
/*     */   public boolean replace(ContentType oldItem, ContentType newItem) throws ContentAlreadyPresentException {
/* 222 */     if (oldItem == null || newItem == null)
/* 223 */       return false; 
/* 224 */     if (oldItem == newItem)
/* 225 */       return true; 
/* 226 */     if (oldItem.getUniqueId() != newItem.getUniqueId() && this.m_contents.containsKey(Long.valueOf(newItem.getUniqueId())))
/* 227 */       throw new ContentAlreadyPresentException("Item with uniqueID " + newItem.getUniqueId() + " is already present in the inventory"); 
/* 228 */     if (this.m_contentChecker != null && this.m_contentChecker.canReplaceItem(this, oldItem, newItem) != 0)
/* 229 */       return false; 
/* 230 */     if (!remove(oldItem))
/* 231 */       return false; 
/*     */     try {
/* 233 */       return add(newItem);
/* 234 */     } catch (InventoryCapacityReachedException e) {
/* 235 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/*     */       
/* 237 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(ContentType item) {
/* 248 */     if (item == null)
/* 249 */       return false; 
/* 250 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 251 */       return false; 
/* 252 */     if (this.m_contents.remove(Long.valueOf(item.getUniqueId())) == null)
/* 253 */       return false; 
/* 254 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item));
/* 255 */     return true;
/*     */   }
/*     */   
/*     */   public boolean destroy(ContentType item) {
/* 259 */     if (item == null)
/* 260 */       return false; 
/* 261 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 262 */       return false; 
/* 263 */     if (this.m_contents.remove(Long.valueOf(item.getUniqueId())) == null)
/* 264 */       return false; 
/* 265 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item));
/* 266 */     item.release();
/* 267 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ContentType removeWithUniqueId(long itemUniqueId) {
/* 272 */     InventoryContent inventoryContent = (InventoryContent)this.m_contents.get(Long.valueOf(itemUniqueId));
/* 273 */     if (inventoryContent == null)
/* 274 */       return null; 
/* 275 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, (ContentType)inventoryContent) != 0)
/* 276 */       return null; 
/* 277 */     this.m_contents.remove(Long.valueOf(itemUniqueId));
/* 278 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, inventoryContent));
/* 279 */     return (ContentType)inventoryContent;
/*     */   }
/*     */   
/*     */   public boolean destroyWithUniqueId(long itemUniqueId) {
/* 283 */     InventoryContent inventoryContent = (InventoryContent)this.m_contents.get(Long.valueOf(itemUniqueId));
/* 284 */     if (inventoryContent == null)
/* 285 */       return false; 
/* 286 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, (ContentType)inventoryContent) != 0)
/* 287 */       return false; 
/* 288 */     this.m_contents.remove(Long.valueOf(itemUniqueId));
/* 289 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, inventoryContent));
/* 290 */     inventoryContent.release();
/* 291 */     return true;
/*     */   }
/*     */   
/*     */   public Iterator<ContentType> iterator() {
/* 295 */     return this.m_contents.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(ContentType item) {
/* 305 */     return (item != null && this.m_contents.containsKey(Long.valueOf(item.getUniqueId())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsUniqueId(long uniqueId) {
/* 315 */     return this.m_contents.containsKey(Long.valueOf(uniqueId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsReferenceId(int referenceId) {
/* 325 */     for (InventoryContent inventoryContent : this.m_contents.values()) {
/* 326 */       if (inventoryContent.getReferenceId() == referenceId)
/* 327 */         return true; 
/* 328 */     }  return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ContentType getWithUniqueId(long uniqueId) {
/* 333 */     return this.m_contents.get(Long.valueOf(uniqueId));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ContentType getFirstWithReferenceId(int referenceId) {
/* 338 */     for (InventoryContent inventoryContent : this.m_contents.values()) {
/* 339 */       if (inventoryContent.getReferenceId() == referenceId)
/* 340 */         return (ContentType)inventoryContent; 
/* 341 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 350 */     return this.m_contents.size();
/*     */   }
/*     */   
/*     */   public int removeAll() {
/* 354 */     int itemsCount = size();
/* 355 */     this.m_contents.clear();
/* 356 */     if (itemsCount > 0)
/* 357 */       notifyObservers((InventoryEvent)InventoryClearedEvent.checkOut(this)); 
/* 358 */     return itemsCount;
/*     */   }
/*     */   
/*     */   public int destroyAll() {
/* 362 */     int itemsCount = size();
/* 363 */     for (InventoryContent inventoryContent : this.m_contents.values())
/* 364 */       inventoryContent.release(); 
/* 365 */     this.m_contents.clear();
/* 366 */     if (itemsCount > 0)
/* 367 */       notifyObservers((InventoryEvent)InventoryClearedEvent.checkOut(this)); 
/* 368 */     return itemsCount;
/*     */   }
/*     */   
/*     */   public InventoryContentChecker<ContentType> getContentChecker() {
/* 372 */     return this.m_contentChecker;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\StackInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */