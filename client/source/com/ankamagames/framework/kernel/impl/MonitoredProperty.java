package com.ankamagames.framework.kernel.impl;

public interface MonitoredProperty {
  public static final byte TYPE_BYTE = 1;
  
  public static final byte TYPE_SHORT = 2;
  
  public static final byte TYPE_INT = 3;
  
  public static final byte TYPE_LONG = 4;
  
  public static final byte TYPE_DOUBLE = 5;
  
  public static final byte TYPE_FLOAT = 6;
  
  public static final byte TYPE_STRING = 7;
  
  String getPropertyName();
  
  int getPropertyType();
  
  String[] getStringArrayEntries();
  
  void setStringArrayEntries(String[] paramArrayOfString);
  
  int[] getIntArrayEntries();
  
  void setIntArrayEntries(int[] paramArrayOfint);
  
  Object getPropertyValue(String paramString, int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\MonitoredProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */