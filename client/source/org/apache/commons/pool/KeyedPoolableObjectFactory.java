package org.apache.commons.pool;

public interface KeyedPoolableObjectFactory {
  Object makeObject(Object paramObject) throws Exception;
  
  void destroyObject(Object paramObject1, Object paramObject2) throws Exception;
  
  boolean validateObject(Object paramObject1, Object paramObject2);
  
  void activateObject(Object paramObject1, Object paramObject2) throws Exception;
  
  void passivateObject(Object paramObject1, Object paramObject2) throws Exception;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\apache\commons\pool\KeyedPoolableObjectFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */