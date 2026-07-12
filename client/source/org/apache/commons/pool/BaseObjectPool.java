/*    */ package org.apache.commons.pool;
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
/*    */ public abstract class BaseObjectPool
/*    */   implements ObjectPool
/*    */ {
/*    */   public abstract Object borrowObject() throws Exception;
/*    */   
/*    */   public abstract void returnObject(Object paramObject) throws Exception;
/*    */   
/*    */   public abstract void invalidateObject(Object paramObject) throws Exception;
/*    */   
/*    */   public int getNumIdle() throws UnsupportedOperationException {
/* 36 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumActive() throws UnsupportedOperationException {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() throws Exception, UnsupportedOperationException {
/* 50 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addObject() throws Exception, UnsupportedOperationException {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void close() throws Exception {
/* 61 */     assertOpen();
/* 62 */     this.closed = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
/* 69 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   protected final boolean isClosed() {
/* 73 */     return this.closed;
/*    */   }
/*    */   
/*    */   protected final void assertOpen() throws IllegalStateException {
/* 77 */     if (isClosed())
/* 78 */       throw new IllegalStateException("Pool not open"); 
/*    */   }
/*    */   
/*    */   private volatile boolean closed = false;
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\BaseObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */