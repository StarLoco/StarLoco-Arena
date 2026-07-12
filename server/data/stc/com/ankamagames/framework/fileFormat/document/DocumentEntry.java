package com.ankamagames.framework.fileFormat.document;

import java.util.ArrayList;

public abstract interface DocumentEntry
{
  public abstract int getId();
  
  public abstract void setId(int paramInt);
  
  public abstract String getName();
  
  public abstract void setName(String paramString);
  
  public abstract String getStringValue();
  
  public abstract boolean getBooleanValue();
  
  public abstract byte getByteValue();
  
  public abstract int getIntValue();
  
  public abstract long getLongValue();
  
  public abstract float getFloatValue();
  
  public abstract double getDoubleValue();
  
  public abstract void setStringValue(String paramString);
  
  public abstract void setBooleanValue(boolean paramBoolean);
  
  public abstract void setByteValue(byte paramByte);
  
  public abstract void setIntValue(int paramInt);
  
  public abstract void setLongValue(long paramLong);
  
  public abstract void setFloatValue(float paramFloat);
  
  public abstract void setDoubleValue(double paramDouble);
  
  public abstract void addChild(DocumentEntry paramDocumentEntry);
  
  public abstract void removeChild(DocumentEntry paramDocumentEntry);
  
  public abstract ArrayList<? extends DocumentEntry> getChildren();
  
  public abstract DocumentEntry getChildByName(String paramString);
  
  public abstract ArrayList<DocumentEntry> getChildrenByName(String paramString);
  
  public abstract ArrayList<DocumentEntry> getDirectChildrenByName(String paramString);
  
  public abstract DocumentEntry getParameterByName(String paramString);
  
  public abstract void addParameter(DocumentEntry paramDocumentEntry);
  
  public abstract void removeParameter(DocumentEntry paramDocumentEntry);
  
  public abstract ArrayList<? extends DocumentEntry> getParameters();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */