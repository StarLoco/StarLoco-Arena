package com.ankamagames.xulor.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract interface Factory<T>
{
  public static final String SETTER_ID = "set";
  public static final String GETTER_ID = "get";
  public static final String PREPENDER_ID = "prepend";
  public static final String APPENDER_ID = "append";
  
  public abstract T newInstance()
    throws Exception;
  
  public abstract T newInstance(Object paramObject)
    throws Exception;
  
  public abstract T newInstance(Object[] paramArrayOfObject)
    throws InstantiationException, IllegalAccessException, InvocationTargetException;
  
  public abstract Class getTemplate();
  
  public abstract Method getSetter(Class paramClass);
  
  public abstract Method getSetter(String paramString);
  
  public abstract Method guessSetter(String paramString);
  
  public abstract Method guessSetter(String paramString, Class paramClass);
  
  public abstract Method guessGetter(String paramString);
  
  public abstract Method guessGetter(String paramString, Class paramClass);
  
  public abstract Method guessPrepender(String paramString);
  
  public abstract Method guessPrepender(String paramString, Class paramClass);
  
  public abstract Method guessAppender(String paramString);
  
  public abstract Method guessAppender(String paramString, Class paramClass);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\Factory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */