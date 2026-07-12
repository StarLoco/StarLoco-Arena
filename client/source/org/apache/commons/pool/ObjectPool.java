package org.apache.commons.pool;

public interface ObjectPool {
  Object borrowObject() throws Exception;
  
  void returnObject(Object paramObject) throws Exception;
  
  void invalidateObject(Object paramObject) throws Exception;
  
  void addObject() throws Exception;
  
  int getNumIdle() throws UnsupportedOperationException;
  
  int getNumActive() throws UnsupportedOperationException;
  
  void clear() throws Exception, UnsupportedOperationException;
  
  void close() throws Exception;
  
  void setFactory(PoolableObjectFactory paramPoolableObjectFactory) throws IllegalStateException, UnsupportedOperationException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\ObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */