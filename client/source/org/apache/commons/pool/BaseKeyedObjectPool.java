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
/*    */ public abstract class BaseKeyedObjectPool
/*    */   implements KeyedObjectPool
/*    */ {
/*    */   public abstract Object borrowObject(Object paramObject) throws Exception;
/*    */   
/*    */   public abstract void returnObject(Object paramObject1, Object paramObject2) throws Exception;
/*    */   
/*    */   public abstract void invalidateObject(Object paramObject1, Object paramObject2) throws Exception;
/*    */   
/*    */   public void addObject(Object key) throws Exception, UnsupportedOperationException {
/* 36 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumIdle(Object key) throws UnsupportedOperationException {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumActive(Object key) throws UnsupportedOperationException {
/* 50 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumIdle() throws UnsupportedOperationException {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumActive() throws UnsupportedOperationException {
/* 64 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() throws Exception, UnsupportedOperationException {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear(Object key) throws Exception, UnsupportedOperationException {
/* 79 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws Exception {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
/* 94 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\BaseKeyedObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */