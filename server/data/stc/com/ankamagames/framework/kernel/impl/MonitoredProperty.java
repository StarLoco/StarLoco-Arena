package com.ankamagames.framework.kernel.impl;

public abstract interface MonitoredProperty
{
  public static final byte TYPE_BYTE = 1;
  public static final byte TYPE_SHORT = 2;
  public static final byte TYPE_INT = 3;
  public static final byte TYPE_LONG = 4;
  public static final byte TYPE_DOUBLE = 5;
  public static final byte TYPE_FLOAT = 6;
  public static final byte TYPE_STRING = 7;
  
  public abstract String getPropertyName();
  
  public abstract int getPropertyType();
  
  public abstract String[] getStringArrayEntries();
  
  public abstract void setStringArrayEntries(String[] paramArrayOfString);
  
  public abstract int[] getIntArrayEntries();
  
  public abstract void setIntArrayEntries(int[] paramArrayOfInt);
  
  public abstract Object getPropertyValue(String paramString, int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\MonitoredProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */