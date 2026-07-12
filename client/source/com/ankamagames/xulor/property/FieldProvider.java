package com.ankamagames.xulor.property;

public interface FieldProvider {
  String[] getFields();
  
  Object getFieldValue(String paramString);
  
  void setFieldValue(String paramString, Object paramObject);
  
  void prependFieldValue(String paramString, Object paramObject);
  
  void appendFieldValue(String paramString, Object paramObject);
  
  boolean isFieldSynchronisable(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\property\FieldProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */