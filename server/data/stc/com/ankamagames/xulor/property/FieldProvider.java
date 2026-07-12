package com.ankamagames.xulor.property;

public abstract interface FieldProvider
{
  public abstract String[] getFields();
  
  public abstract Object getFieldValue(String paramString);
  
  public abstract void setFieldValue(String paramString, Object paramObject);
  
  public abstract void prependFieldValue(String paramString, Object paramObject);
  
  public abstract void appendFieldValue(String paramString, Object paramObject);
  
  public abstract boolean isFieldSynchronisable(String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\property\FieldProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */