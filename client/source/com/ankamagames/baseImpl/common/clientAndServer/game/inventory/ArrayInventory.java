/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryClearedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.InventoryItemModifiedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.PositionAlreadyUsedException;
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.ByteArray;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.ArrayIterator;
/*     */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*     */ import gnu.trove.TLongShortHashMap;
/*     */ import gnu.trove.TLongShortIterator;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
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
/*     */ public class ArrayInventory<ContentType extends InventoryContent>
/*     */   extends Inventory<ContentType>
/*     */ {
/*  38 */   protected ContentType[] m_contents = null;
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected final TLongShortHashMap m_idxByUniqueId = new TLongShortHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final InventoryContentProvider<ContentType> m_contentProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final InventoryContentChecker<ContentType> m_contentChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInventory(InventoryContentProvider<ContentType> contentProvider, InventoryContentChecker<ContentType> contentChecker, short maximumSize, boolean stackable) {
/*  64 */     super(stackable);
/*  65 */     setMaximumSize(maximumSize);
/*  66 */     this.m_contentProvider = contentProvider;
/*  67 */     this.m_contentChecker = contentChecker;
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
/*  78 */     int estimatedSize = 10 * size();
/*  79 */     ByteArray data = new ByteArray(estimatedSize);
/*     */     
/*  81 */     for (TLongShortIterator it = this.m_idxByUniqueId.iterator(); it.hasNext(); ) {
/*  82 */       it.advance();
/*  83 */       short pos = it.value();
/*  84 */       ContentType item = this.m_contents[pos];
/*  85 */       data.putShort(pos);
/*  86 */       data.put(item.serialize());
/*     */     } 
/*     */     
/*  89 */     return data.toArray();
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
/*     */   public boolean unserialize(byte[] data) {
/* 102 */     destroyAll();
/*     */     
/* 104 */     if (data == null) {
/* 105 */       return true;
/*     */     }
/*     */     
/* 108 */     boolean bError = false;
/*     */     
/*     */     try {
/* 111 */       ByteBuffer bf = ByteBuffer.wrap(data);
/*     */       
/* 113 */       while (bf.hasRemaining()) {
/* 114 */         short pos = bf.getShort();
/* 115 */         ContentType item = this.m_contentProvider.unserializeContent(bf);
/* 116 */         if (item == null || !addAt(item, pos)) {
/* 117 */           m_logger.error("Erreur lors de la désérialisation d'un ArrayInventory : impossible d'ajouter l'item");
/* 118 */           bError = true;
/*     */         }
/*     */       
/*     */       } 
/* 122 */     } catch (InventoryCapacityReachedException e) {
/* 123 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/* 124 */       bError = true;
/* 125 */     } catch (ContentAlreadyPresentException e) {
/* 126 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/* 127 */       bError = true;
/* 128 */     } catch (PositionAlreadyUsedException e) {
/* 129 */       m_logger.error(ExceptionFormatter.toString((Throwable)e));
/* 130 */       bError = true;
/*     */     } 
/*     */     
/* 133 */     return !bError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setMaximumSize(short maxSize) {
/* 144 */     if (maxSize < getMaximumSize()) {
/* 145 */       m_logger.error("Can't decrease the size of an ArrayInventory");
/* 146 */       return false;
/*     */     } 
/*     */     
/* 149 */     if (this.m_contents != null && maxSize == getMaximumSize()) {
/* 150 */       return true;
/*     */     }
/* 152 */     super.setMaximumSize(maxSize);
/*     */     
/* 154 */     if (this.m_contents == null) {
/* 155 */       this.m_contents = (ContentType[])new InventoryContent[maxSize];
/*     */     } else {
/* 157 */       InventoryContent[] tmp = new InventoryContent[maxSize];
/* 158 */       System.arraycopy(this.m_contents, 0, tmp, 0, this.m_contents.length);
/* 159 */       this.m_contents = (ContentType[])tmp;
/*     */     } 
/*     */     
/* 162 */     this.m_idxByUniqueId.ensureCapacity(maxSize);
/* 163 */     return true;
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
/*     */ 
/*     */   
/*     */   public boolean add(ContentType item) throws InventoryCapacityReachedException, ContentAlreadyPresentException {
/* 180 */     if (item == null)
/* 181 */       return false; 
/* 182 */     if (isFull())
/* 183 */       throw new InventoryCapacityReachedException("Cannot add item : maximum size of inventory is reached (" + getMaximumSize() + ")"); 
/* 184 */     if (this.m_idxByUniqueId.containsKey(item.getUniqueId()))
/* 185 */       throw new ContentAlreadyPresentException("Item with uniqueID " + item.getUniqueId() + " is already present in the inventory"); 
/* 186 */     if (this.m_contentChecker != null && this.m_contentChecker.canAddItem(this, item) != 0) {
/* 187 */       return false;
/*     */     }
/*     */     
/* 190 */     short idx = -1;
/* 191 */     for (short i = 0; i < this.m_contents.length; i = (short)(i + 1)) {
/* 192 */       if (this.m_contents[i] == null) {
/* 193 */         idx = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 197 */     if (idx == -1) {
/* 198 */       throw new InventoryCapacityReachedException("Cannot add item : no left space for it (strange, should have say Inventory is full before. Size : " + size() + " MaxSize : " + getMaximumSize());
/*     */     }
/*     */     
/* 201 */     this.m_contents[idx] = item;
/* 202 */     this.m_idxByUniqueId.put(item.getUniqueId(), idx);
/* 203 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutAddEvent(this, (InventoryContent)item, idx));
/*     */     
/* 205 */     return true;
/*     */   }
/*     */   
/*     */   public boolean updateQuantity(long uniqueId, short quantityUpdate) {
/* 209 */     throw new UnsupportedOperationException("updateQuantity non géré pour les ArrayInventory");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAt(ContentType item, short position) throws InventoryCapacityReachedException, ContentAlreadyPresentException, PositionAlreadyUsedException {
/* 227 */     if (item == null) {
/* 228 */       m_logger.info("Impossible d'ajouter un item null");
/* 229 */       return false;
/*     */     } 
/* 231 */     if (position < 0 || position >= this.m_maximumSize) {
/* 232 */       m_logger.info("Impossible d'ajouter un item : position en dehors des limites : " + position);
/* 233 */       return false;
/*     */     } 
/* 235 */     if (this.m_contentChecker != null && this.m_contentChecker.canAddItem(this, item, position) != 0) {
/* 236 */       m_logger.info("Position refusée par le checker");
/* 237 */       return false;
/*     */     } 
/* 239 */     if (isFull())
/* 240 */       throw new InventoryCapacityReachedException("Cannot add item : maximum size of inventory is reached (" + getMaximumSize() + ")"); 
/* 241 */     if (this.m_idxByUniqueId.containsKey(item.getUniqueId()))
/* 242 */       throw new ContentAlreadyPresentException("Item with uniqueID " + item.getUniqueId() + " is already present in the inventory"); 
/* 243 */     if (this.m_contents[position] != null) {
/* 244 */       throw new PositionAlreadyUsedException("Cannot add item " + item + " at position " + position + " item " + this.m_contents[position] + "already present");
/*     */     }
/* 246 */     this.m_contents[position] = item;
/* 247 */     this.m_idxByUniqueId.put(item.getUniqueId(), position);
/* 248 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutAddEvent(this, (InventoryContent)item, position));
/* 249 */     return true;
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
/* 261 */     if (oldItem == null || newItem == null)
/* 262 */       return false; 
/* 263 */     if (oldItem == newItem)
/* 264 */       return true; 
/* 265 */     if (oldItem.getUniqueId() != newItem.getUniqueId() && this.m_idxByUniqueId.containsKey(newItem.getUniqueId()))
/* 266 */       throw new ContentAlreadyPresentException("Item with uniqueID " + newItem.getUniqueId() + " is already present in the inventory"); 
/* 267 */     if (!contains(oldItem))
/* 268 */       return false; 
/* 269 */     if (this.m_contentChecker != null && this.m_contentChecker.canReplaceItem(this, oldItem, newItem) != 0)
/* 270 */       return false; 
/* 271 */     short idx = this.m_idxByUniqueId.remove(oldItem.getUniqueId());
/* 272 */     this.m_idxByUniqueId.put(newItem.getUniqueId(), idx);
/* 273 */     this.m_contents[idx] = newItem;
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(ContentType item) {
/* 284 */     if (item == null)
/* 285 */       return false; 
/* 286 */     if (this.m_idxByUniqueId.contains(item.getUniqueId())) {
/* 287 */       short idx = this.m_idxByUniqueId.get(item.getUniqueId());
/* 288 */       if (this.m_contents[idx] != item) {
/* 289 */         m_logger.error("Problème de logique : table d'index et tableau incohérents. Item attendu à la position " + idx + " : " + item + " item trouvé : " + this.m_contents[idx]);
/* 290 */         return false;
/*     */       } 
/* 292 */       if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 293 */         return false; 
/* 294 */       this.m_contents[idx] = null;
/* 295 */       this.m_idxByUniqueId.remove(item.getUniqueId());
/* 296 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item, idx));
/* 297 */       return true;
/*     */     } 
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean destroy(ContentType item) {
/* 309 */     if (item == null)
/* 310 */       return false; 
/* 311 */     if (this.m_idxByUniqueId.contains(item.getUniqueId())) {
/* 312 */       short idx = this.m_idxByUniqueId.get(item.getUniqueId());
/* 313 */       if (this.m_contents[idx] != item) {
/* 314 */         m_logger.error("Problème de logique : table d'index et tableau incohérents. Item attendu à la position " + idx + " : " + item + " item trouvé : " + this.m_contents[idx]);
/* 315 */         return false;
/*     */       } 
/* 317 */       if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 318 */         return false; 
/* 319 */       this.m_contents[idx] = null;
/* 320 */       this.m_idxByUniqueId.remove(item.getUniqueId());
/* 321 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item, idx));
/* 322 */       item.release();
/* 323 */       return true;
/*     */     } 
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAt(short position) {
/* 336 */     ContentType item = this.m_contents[position];
/* 337 */     if (item == null)
/* 338 */       return false; 
/* 339 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 340 */       return false; 
/* 341 */     this.m_contents[position] = null;
/* 342 */     this.m_idxByUniqueId.remove(item.getUniqueId());
/* 343 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item, position));
/* 344 */     return true;
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
/*     */   public boolean destroyAt(short position) {
/* 356 */     if (position < 0 || position >= this.m_maximumSize)
/* 357 */       return false; 
/* 358 */     ContentType item = this.m_contents[position];
/* 359 */     if (item == null)
/* 360 */       return false; 
/* 361 */     if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, item) != 0)
/* 362 */       return false; 
/* 363 */     this.m_contents[position] = null;
/* 364 */     this.m_idxByUniqueId.remove(item.getUniqueId());
/* 365 */     notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)item, position));
/* 366 */     item.release();
/* 367 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getPosition(long uniqueId) {
/* 377 */     if (!this.m_idxByUniqueId.containsKey(uniqueId))
/* 378 */       return -1; 
/* 379 */     return this.m_idxByUniqueId.get(uniqueId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getPosition(ContentType item) {
/* 389 */     if (item == null)
/* 390 */       return -1; 
/* 391 */     return getPosition(item.getUniqueId());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ContentType removeWithUniqueId(long itemUniqueId) {
/* 396 */     if (this.m_idxByUniqueId.contains(itemUniqueId)) {
/* 397 */       short idx = this.m_idxByUniqueId.remove(itemUniqueId);
/* 398 */       if (this.m_contents[idx] == null || this.m_contents[idx].getUniqueId() != itemUniqueId) {
/* 399 */         m_logger.error("Problème de logique : table d'index et tableau incohérents. Item attendu à la position " + idx + " : id " + itemUniqueId + 
/* 400 */             ". item trouvé : " + this.m_contents[idx] + (
/* 401 */             (this.m_contents[idx] == null) ? "" : ("(id : " + this.m_contents[idx].getUniqueId() + ")")));
/* 402 */         this.m_contents[idx] = null;
/* 403 */         return null;
/*     */       } 
/* 405 */       ContentType contentToReturn = this.m_contents[idx];
/* 406 */       if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, contentToReturn) != 0)
/* 407 */         return null; 
/* 408 */       this.m_contents[idx] = null;
/* 409 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)contentToReturn, idx));
/* 410 */       return contentToReturn;
/*     */     } 
/* 412 */     return null;
/*     */   }
/*     */   
/*     */   public boolean destroyWithUniqueId(long itemUniqueId) {
/* 416 */     if (this.m_idxByUniqueId.contains(itemUniqueId)) {
/* 417 */       short idx = this.m_idxByUniqueId.remove(itemUniqueId);
/* 418 */       if (this.m_contents[idx] == null || this.m_contents[idx].getUniqueId() != itemUniqueId) {
/* 419 */         m_logger.error("Problème de logique : table d'index et tableau incohérents. Item attendu à la position " + idx + " : id " + itemUniqueId + 
/* 420 */             ". item trouvé : " + this.m_contents[idx] + (
/* 421 */             (this.m_contents[idx] == null) ? "" : ("(id : " + this.m_contents[idx].getUniqueId() + ")")));
/* 422 */         this.m_contents[idx] = null;
/* 423 */         return false;
/*     */       } 
/* 425 */       if (this.m_contentChecker != null && this.m_contentChecker.canRemoveItem(this, this.m_contents[idx]) != 0)
/* 426 */         return false; 
/* 427 */       notifyObservers((InventoryEvent)InventoryItemModifiedEvent.checkOutRemoveEvent(this, (InventoryContent)this.m_contents[idx], idx));
/* 428 */       this.m_contents[idx].release();
/* 429 */       this.m_contents[idx] = null;
/* 430 */       return true;
/*     */     } 
/* 432 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ContentType> iterator() {
/* 440 */     return (Iterator<ContentType>)new ArrayIterator((Object[])this.m_contents, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(ContentType item) {
/* 450 */     return (item != null && this.m_idxByUniqueId.containsKey(item.getUniqueId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsUniqueId(long uniqueId) {
/* 460 */     return this.m_idxByUniqueId.containsKey(uniqueId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsReferenceId(int referenceId) {
/*     */     byte b;
/*     */     int i;
/*     */     ContentType[] arrayOfContentType;
/* 470 */     for (i = (arrayOfContentType = this.m_contents).length, b = 0; b < i; ) { ContentType item = arrayOfContentType[b];
/* 471 */       if (item != null && item.getReferenceId() == referenceId)
/* 472 */         return true;  b++; }
/* 473 */      return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPositionFree(short position) {
/* 482 */     return (position >= 0 && position < this.m_maximumSize && this.m_contents[position] == null);
/*     */   }
/*     */   
/*     */   public ContentType getFromPosition(short position) {
/* 486 */     if (position < 0 || position >= this.m_maximumSize)
/* 487 */       return null; 
/* 488 */     return this.m_contents[position];
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ContentType getWithUniqueId(long uniqueId) {
/* 493 */     if (!this.m_idxByUniqueId.contains(uniqueId))
/* 494 */       return null; 
/* 495 */     short idx = this.m_idxByUniqueId.get(uniqueId);
/* 496 */     return this.m_contents[idx]; } @Nullable
/*     */   public ContentType getFirstWithReferenceId(int referenceId) {
/*     */     byte b;
/*     */     int i;
/*     */     ContentType[] arrayOfContentType;
/* 501 */     for (i = (arrayOfContentType = this.m_contents).length, b = 0; b < i; ) { ContentType item = arrayOfContentType[b];
/* 502 */       if (item != null && item.getReferenceId() == referenceId)
/* 503 */         return item;  b++; }
/* 504 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 513 */     return this.m_idxByUniqueId.size();
/*     */   }
/*     */   
/*     */   public int removeAll() {
/* 517 */     int itemsCount = size();
/* 518 */     for (int i = this.m_contents.length - 1; i >= 0; i--)
/* 519 */       this.m_contents[i] = null; 
/* 520 */     this.m_idxByUniqueId.clear();
/* 521 */     if (itemsCount > 0)
/* 522 */       notifyObservers((InventoryEvent)InventoryClearedEvent.checkOut(this)); 
/* 523 */     return itemsCount;
/*     */   }
/*     */   
/*     */   public int destroyAll() {
/* 527 */     int itemsCount = size();
/* 528 */     for (int i = this.m_contents.length - 1; i >= 0; i--) {
/* 529 */       if (this.m_contents[i] != null)
/* 530 */         this.m_contents[i].release(); 
/* 531 */       this.m_contents[i] = null;
/*     */     } 
/* 533 */     this.m_idxByUniqueId.clear();
/* 534 */     if (itemsCount > 0)
/* 535 */       notifyObservers((InventoryEvent)InventoryClearedEvent.checkOut(this)); 
/* 536 */     return itemsCount;
/*     */   }
/*     */   
/*     */   public InventoryContentChecker<ContentType> getContentChecker() {
/* 540 */     return this.m_contentChecker;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\ArrayInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */