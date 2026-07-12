/*     */ package com.ankamagames.framework.fileFormat.xml;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLNodeAttribute
/*     */   implements DocumentEntry
/*     */ {
/*     */   private String m_name;
/*     */   private String m_value;
/*     */   
/*     */   public XMLNodeAttribute(String name, String value)
/*     */   {
/*  23 */     this.m_name = name;
/*  24 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  34 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  46 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  50 */     this.m_name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStringValue()
/*     */   {
/*  59 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getBooleanValue()
/*     */   {
/*  68 */     return Boolean.parseBoolean(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getByteValue()
/*     */   {
/*  77 */     return Byte.parseByte(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIntValue()
/*     */   {
/*  86 */     return Integer.parseInt(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLongValue()
/*     */   {
/*  95 */     return Long.parseLong(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getFloatValue()
/*     */   {
/* 104 */     return Float.parseFloat(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDoubleValue()
/*     */   {
/* 113 */     return Double.parseDouble(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStringValue(String value)
/*     */   {
/* 122 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBooleanValue(boolean value)
/*     */   {
/* 131 */     this.m_value = (value ? "true" : "false");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setByteValue(byte value)
/*     */   {
/* 140 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIntValue(int value)
/*     */   {
/* 149 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLongValue(long value)
/*     */   {
/* 158 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFloatValue(float value)
/*     */   {
/* 167 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDoubleValue(double value)
/*     */   {
/* 176 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addChild(DocumentEntry entry) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeChild(DocumentEntry entry) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<? extends DocumentEntry> getChildren()
/*     */   {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DocumentEntry getChildByName(String name)
/*     */   {
/* 212 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<DocumentEntry> getChildrenByName(String name)
/*     */   {
/* 222 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<DocumentEntry> getDirectChildrenByName(String name)
/*     */   {
/* 233 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DocumentEntry getParameterByName(String name)
/*     */   {
/* 243 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addParameter(DocumentEntry parameter) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeParameter(DocumentEntry parameter) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<? extends DocumentEntry> getParameters()
/*     */   {
/* 268 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\xml\XMLNodeAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */