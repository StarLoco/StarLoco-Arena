/*     */ package org.apache.commons.pool.impl;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Stack;
/*     */ import org.apache.commons.pool.BaseKeyedObjectPool;
/*     */ import org.apache.commons.pool.KeyedObjectPool;
/*     */ import org.apache.commons.pool.KeyedPoolableObjectFactory;
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
/*     */ public class StackKeyedObjectPool
/*     */   extends BaseKeyedObjectPool
/*     */   implements KeyedObjectPool
/*     */ {
/*     */   protected static final int DEFAULT_MAX_SLEEPING = 8;
/*     */   protected static final int DEFAULT_INIT_SLEEPING_CAPACITY = 4;
/*     */   protected HashMap _pools;
/*     */   protected KeyedPoolableObjectFactory _factory;
/*     */   protected int _maxSleeping;
/*     */   protected int _initSleepingCapacity;
/*     */   protected int _totActive;
/*     */   protected int _totIdle;
/*     */   protected HashMap _activeCount;
/*     */   
/*     */   public StackKeyedObjectPool() {
/*  51 */     this((KeyedPoolableObjectFactory)null, 8, 4);
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
/*     */   public StackKeyedObjectPool(int max) {
/*  63 */     this((KeyedPoolableObjectFactory)null, max, 4);
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
/*     */   public StackKeyedObjectPool(int max, int init) {
/*  77 */     this((KeyedPoolableObjectFactory)null, max, init);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory) {
/*  87 */     this(factory, 8);
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
/*     */   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory, int max) {
/*  99 */     this(factory, max, 4);
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
/*     */   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory, int max, int init) {
/* 323 */     this._pools = null;
/*     */ 
/*     */     
/* 326 */     this._factory = null;
/*     */ 
/*     */     
/* 329 */     this._maxSleeping = 8;
/*     */ 
/*     */     
/* 332 */     this._initSleepingCapacity = 4;
/*     */ 
/*     */     
/* 335 */     this._totActive = 0;
/*     */ 
/*     */     
/* 338 */     this._totIdle = 0;
/*     */ 
/*     */     
/* 341 */     this._activeCount = null;
/*     */     this._factory = factory;
/*     */     this._maxSleeping = (max < 0) ? 8 : max;
/*     */     this._initSleepingCapacity = (init < 1) ? 4 : init;
/*     */     this._pools = new HashMap();
/*     */     this._activeCount = new HashMap();
/*     */   }
/*     */   
/*     */   public synchronized Object borrowObject(Object key) throws Exception {
/*     */     Object obj = null;
/*     */     Stack stack = (Stack)this._pools.get(key);
/*     */     if (null == stack) {
/*     */       stack = new Stack();
/*     */       stack.ensureCapacity((this._initSleepingCapacity > this._maxSleeping) ? this._maxSleeping : this._initSleepingCapacity);
/*     */       this._pools.put(key, stack);
/*     */     } 
/*     */     try {
/*     */       obj = stack.pop();
/*     */       this._totIdle--;
/*     */     } catch (Exception e) {
/*     */       if (null == this._factory)
/*     */         throw new NoSuchElementException(); 
/*     */       obj = this._factory.makeObject(key);
/*     */     } 
/*     */     if (null != obj && null != this._factory)
/*     */       this._factory.activateObject(key, obj); 
/*     */     incrementActiveCount(key);
/*     */     return obj;
/*     */   }
/*     */   
/*     */   public synchronized void returnObject(Object key, Object obj) throws Exception {
/*     */     decrementActiveCount(key);
/*     */     if (null == this._factory || this._factory.validateObject(key, obj)) {
/*     */       Stack stack = (Stack)this._pools.get(key);
/*     */       if (null == stack) {
/*     */         stack = new Stack();
/*     */         stack.ensureCapacity((this._initSleepingCapacity > this._maxSleeping) ? this._maxSleeping : this._initSleepingCapacity);
/*     */         this._pools.put(key, stack);
/*     */       } 
/*     */       if (null != this._factory)
/*     */         try {
/*     */           this._factory.passivateObject(key, obj);
/*     */         } catch (Exception e) {
/*     */           this._factory.destroyObject(key, obj);
/*     */           return;
/*     */         }  
/*     */       if (stack.size() < this._maxSleeping) {
/*     */         stack.push(obj);
/*     */         this._totIdle++;
/*     */       } else if (null != this._factory) {
/*     */         this._factory.destroyObject(key, obj);
/*     */       } 
/*     */     } else if (null != this._factory) {
/*     */       this._factory.destroyObject(key, obj);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void invalidateObject(Object key, Object obj) throws Exception {
/*     */     decrementActiveCount(key);
/*     */     if (null != this._factory)
/*     */       this._factory.destroyObject(key, obj); 
/*     */     notifyAll();
/*     */   }
/*     */   
/*     */   public synchronized void addObject(Object key) throws Exception {
/*     */     Object obj = this._factory.makeObject(key);
/*     */     incrementActiveCount(key);
/*     */     returnObject(key, obj);
/*     */   }
/*     */   
/*     */   public int getNumIdle() {
/*     */     return this._totIdle;
/*     */   }
/*     */   
/*     */   public int getNumActive() {
/*     */     return this._totActive;
/*     */   }
/*     */   
/*     */   public synchronized int getNumActive(Object key) {
/*     */     return getActiveCount(key);
/*     */   }
/*     */   
/*     */   public synchronized int getNumIdle(Object key) {
/*     */     try {
/*     */       return ((Stack)this._pools.get(key)).size();
/*     */     } catch (Exception e) {
/*     */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/*     */     Iterator it = this._pools.keySet().iterator();
/*     */     while (it.hasNext()) {
/*     */       Object key = it.next();
/*     */       Stack stack = (Stack)this._pools.get(key);
/*     */       destroyStack(key, stack);
/*     */     } 
/*     */     this._totIdle = 0;
/*     */     this._pools.clear();
/*     */     this._activeCount.clear();
/*     */   }
/*     */   
/*     */   public synchronized void clear(Object key) {
/*     */     Stack stack = (Stack)this._pools.remove(key);
/*     */     destroyStack(key, stack);
/*     */   }
/*     */   
/*     */   private synchronized void destroyStack(Object key, Stack stack) {
/*     */     if (null == stack)
/*     */       return; 
/*     */     if (null != this._factory) {
/*     */       Iterator it = stack.iterator();
/*     */       while (it.hasNext()) {
/*     */         try {
/*     */           this._factory.destroyObject(key, it.next());
/*     */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */     this._totIdle -= stack.size();
/*     */     this._activeCount.remove(key);
/*     */     stack.clear();
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/*     */     StringBuffer buf = new StringBuffer();
/*     */     buf.append(getClass().getName());
/*     */     buf.append(" contains ").append(this._pools.size()).append(" distinct pools: ");
/*     */     Iterator it = this._pools.keySet().iterator();
/*     */     while (it.hasNext()) {
/*     */       Object key = it.next();
/*     */       buf.append(" |").append(key).append("|=");
/*     */       Stack s = (Stack)this._pools.get(key);
/*     */       buf.append(s.size());
/*     */     } 
/*     */     return buf.toString();
/*     */   }
/*     */   
/*     */   public synchronized void close() throws Exception {
/*     */     clear();
/*     */     this._pools = null;
/*     */     this._factory = null;
/*     */     this._activeCount = null;
/*     */   }
/*     */   
/*     */   public synchronized void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException {
/*     */     if (0 < getNumActive())
/*     */       throw new IllegalStateException("Objects are already active"); 
/*     */     clear();
/*     */     this._factory = factory;
/*     */   }
/*     */   
/*     */   private int getActiveCount(Object key) {
/*     */     try {
/*     */       return ((Integer)this._activeCount.get(key)).intValue();
/*     */     } catch (NoSuchElementException e) {
/*     */       return 0;
/*     */     } catch (NullPointerException e) {
/*     */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void incrementActiveCount(Object key) {
/*     */     this._totActive++;
/*     */     Integer old = (Integer)this._activeCount.get(key);
/*     */     if (null == old) {
/*     */       this._activeCount.put(key, new Integer(1));
/*     */     } else {
/*     */       this._activeCount.put(key, new Integer(old.intValue() + 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decrementActiveCount(Object key) {
/*     */     this._totActive--;
/*     */     Integer active = (Integer)this._activeCount.get(key);
/*     */     if (null != active)
/*     */       if (active.intValue() <= 1) {
/*     */         this._activeCount.remove(key);
/*     */       } else {
/*     */         this._activeCount.put(key, new Integer(active.intValue() - 1));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\impl\StackKeyedObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */