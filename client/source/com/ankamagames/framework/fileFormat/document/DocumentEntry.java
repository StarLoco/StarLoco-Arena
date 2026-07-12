package com.ankamagames.framework.fileFormat.document;

import java.util.ArrayList;

public interface DocumentEntry {
  int getId();
  
  void setId(int paramInt);
  
  String getName();
  
  void setName(String paramString);
  
  String getStringValue();
  
  boolean getBooleanValue();
  
  byte getByteValue();
  
  int getIntValue();
  
  long getLongValue();
  
  float getFloatValue();
  
  double getDoubleValue();
  
  void setStringValue(String paramString);
  
  void setBooleanValue(boolean paramBoolean);
  
  void setByteValue(byte paramByte);
  
  void setIntValue(int paramInt);
  
  void setLongValue(long paramLong);
  
  void setFloatValue(float paramFloat);
  
  void setDoubleValue(double paramDouble);
  
  void addChild(DocumentEntry paramDocumentEntry);
  
  void removeChild(DocumentEntry paramDocumentEntry);
  
  ArrayList<? extends DocumentEntry> getChildren();
  
  DocumentEntry getChildByName(String paramString);
  
  ArrayList<DocumentEntry> getChildrenByName(String paramString);
  
  ArrayList<DocumentEntry> getDirectChildrenByName(String paramString);
  
  DocumentEntry getParameterByName(String paramString);
  
  void addParameter(DocumentEntry paramDocumentEntry);
  
  void removeParameter(DocumentEntry paramDocumentEntry);
  
  ArrayList<? extends DocumentEntry> getParameters();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */