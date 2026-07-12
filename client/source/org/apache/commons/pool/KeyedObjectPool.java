package org.apache.commons.pool;

public interface KeyedObjectPool {
  Object borrowObject(Object paramObject) throws Exception;
  
  void returnObject(Object paramObject1, Object paramObject2) throws Exception;
  
  void invalidateObject(Object paramObject1, Object paramObject2) throws Exception;
  
  void addObject(Object paramObject) throws Exception;
  
  int getNumIdle(Object paramObject) throws UnsupportedOperationException;
  
  int getNumActive(Object paramObject) throws UnsupportedOperationException;
  
  int getNumIdle() throws UnsupportedOperationException;
  
  int getNumActive() throws UnsupportedOperationException;
  
  void clear() throws Exception, UnsupportedOperationException;
  
  void clear(Object paramObject) throws Exception, UnsupportedOperationException;
  
  void close() throws Exception;
  
  void setFactory(KeyedPoolableObjectFactory paramKeyedPoolableObjectFactory) throws IllegalStateException, UnsupportedOperationException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\KeyedObjectPool.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */