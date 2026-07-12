package com.ankamagames.xulor.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Factory<T> {
  public static final String SETTER_ID = "set";
  
  public static final String GETTER_ID = "get";
  
  public static final String PREPENDER_ID = "prepend";
  
  public static final String APPENDER_ID = "append";
  
  T newInstance() throws Exception;
  
  T newInstance(Object paramObject) throws Exception;
  
  T newInstance(Object[] paramArrayOfObject) throws InstantiationException, IllegalAccessException, InvocationTargetException;
  
  Class getTemplate();
  
  Method getSetter(Class paramClass);
  
  Method getSetter(String paramString);
  
  Method guessSetter(String paramString);
  
  Method guessSetter(String paramString, Class paramClass);
  
  Method guessGetter(String paramString);
  
  Method guessGetter(String paramString, Class paramClass);
  
  Method guessPrepender(String paramString);
  
  Method guessPrepender(String paramString, Class paramClass);
  
  Method guessAppender(String paramString);
  
  Method guessAppender(String paramString, Class paramClass);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\Factory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */