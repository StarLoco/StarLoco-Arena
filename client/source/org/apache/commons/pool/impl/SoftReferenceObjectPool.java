/*     */ package org.apache.commons.pool.impl;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.pool.BaseObjectPool;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoftReferenceObjectPool
/*     */   extends BaseObjectPool
/*     */   implements ObjectPool
/*     */ {
/*     */   private List _pool;
/*     */   private PoolableObjectFactory _factory;
/*     */   private int _numActive;
/*     */   
/*     */   public SoftReferenceObjectPool() {
/* 180 */     this._pool = null;
/*     */ 
/*     */     
/* 183 */     this._factory = null;
/*     */ 
/*     */     
/* 186 */     this._numActive = 0; this._pool = new ArrayList(); this._factory = null; } public SoftReferenceObjectPool(PoolableObjectFactory factory) { this._pool = null; this._factory = null; this._numActive = 0; this._pool = new ArrayList(); this._factory = factory; } public SoftReferenceObjectPool(PoolableObjectFactory factory, int initSize) throws Exception { this._pool = null; this._factory = null; this._numActive = 0;
/*     */     this._pool = new ArrayList();
/*     */     this._factory = factory;
/*     */     if (null != this._factory)
/*     */       for (int i = 0; i < initSize; i++) {
/*     */         Object obj = this._factory.makeObject();
/*     */         this._factory.passivateObject(obj);
/*     */         this._pool.add(new SoftReference(obj));
/*     */       }   }
/*     */ 
/*     */   
/*     */   public synchronized Object borrowObject() throws Exception {
/*     */     assertOpen();
/*     */     Object obj = null;
/*     */     while (null == obj) {
/*     */       if (this._pool.isEmpty()) {
/*     */         if (null == this._factory)
/*     */           throw new NoSuchElementException(); 
/*     */         obj = this._factory.makeObject();
/*     */       } else {
/*     */         SoftReference ref = this._pool.remove(this._pool.size() - 1);
/*     */         obj = ref.get();
/*     */       } 
/*     */       if (null != this._factory && null != obj)
/*     */         this._factory.activateObject(obj); 
/*     */       if (null != this._factory && null != obj && !this._factory.validateObject(obj)) {
/*     */         this._factory.destroyObject(obj);
/*     */         obj = null;
/*     */       } 
/*     */     } 
/*     */     this._numActive++;
/*     */     return obj;
/*     */   }
/*     */   
/*     */   public synchronized void returnObject(Object obj) throws Exception {
/*     */     assertOpen();
/*     */     boolean success = true;
/*     */     if (!this._factory.validateObject(obj)) {
/*     */       success = false;
/*     */     } else {
/*     */       try {
/*     */         this._factory.passivateObject(obj);
/*     */       } catch (Exception e) {
/*     */         success = false;
/*     */       } 
/*     */     } 
/*     */     boolean shouldDestroy = !success;
/*     */     this._numActive--;
/*     */     if (success)
/*     */       this._pool.add(new SoftReference(obj)); 
/*     */     notifyAll();
/*     */     if (shouldDestroy)
/*     */       try {
/*     */         this._factory.destroyObject(obj);
/*     */       } catch (Exception e) {} 
/*     */   }
/*     */   
/*     */   public synchronized void invalidateObject(Object obj) throws Exception {
/*     */     assertOpen();
/*     */     this._numActive--;
/*     */     this._factory.destroyObject(obj);
/*     */     notifyAll();
/*     */   }
/*     */   
/*     */   public synchronized void addObject() throws Exception {
/*     */     assertOpen();
/*     */     Object obj = this._factory.makeObject();
/*     */     this._numActive++;
/*     */     returnObject(obj);
/*     */   }
/*     */   
/*     */   public synchronized int getNumIdle() {
/*     */     assertOpen();
/*     */     return this._pool.size();
/*     */   }
/*     */   
/*     */   public synchronized int getNumActive() {
/*     */     assertOpen();
/*     */     return this._numActive;
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/*     */     assertOpen();
/*     */     if (null != this._factory) {
/*     */       Iterator iter = this._pool.iterator();
/*     */       while (iter.hasNext()) {
/*     */         try {
/*     */           Object obj = ((SoftReference)iter.next()).get();
/*     */           if (null != obj)
/*     */             this._factory.destroyObject(obj); 
/*     */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */     this._pool.clear();
/*     */   }
/*     */   
/*     */   public synchronized void close() throws Exception {
/*     */     clear();
/*     */     this._pool = null;
/*     */     this._factory = null;
/*     */     super.close();
/*     */   }
/*     */   
/*     */   public synchronized void setFactory(PoolableObjectFactory factory) throws IllegalStateException {
/*     */     assertOpen();
/*     */     if (0 < getNumActive())
/*     */       throw new IllegalStateException("Objects are already active"); 
/*     */     clear();
/*     */     this._factory = factory;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\impl\SoftReferenceObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */