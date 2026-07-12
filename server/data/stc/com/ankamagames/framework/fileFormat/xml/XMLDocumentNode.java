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
/*     */ public class XMLDocumentNode
/*     */   implements DocumentEntry
/*     */ {
/*     */   private String m_name;
/*     */   private String m_value;
/*     */   private ArrayList<XMLNodeAttribute> m_attributes;
/*     */   private ArrayList<XMLDocumentNode> m_children;
/*     */   
/*     */   public XMLDocumentNode(String name, String value)
/*     */   {
/*  25 */     this.m_attributes = new ArrayList();
/*  26 */     this.m_children = new ArrayList();
/*  27 */     this.m_name = name;
/*  28 */     this.m_value = value;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  32 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  36 */     this.m_name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  46 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStringValue()
/*     */   {
/*  63 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getBooleanValue()
/*     */   {
/*  72 */     return Boolean.parseBoolean(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getByteValue()
/*     */   {
/*  81 */     return Byte.parseByte(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIntValue()
/*     */   {
/*  90 */     return Integer.parseInt(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLongValue()
/*     */   {
/*  99 */     return Long.parseLong(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getFloatValue()
/*     */   {
/* 108 */     return Float.parseFloat(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDoubleValue()
/*     */   {
/* 117 */     return Double.parseDouble(this.m_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStringValue(String value)
/*     */   {
/* 126 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBooleanValue(boolean value)
/*     */   {
/* 135 */     this.m_value = (value ? "true" : "false");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setByteValue(byte value)
/*     */   {
/* 144 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIntValue(int value)
/*     */   {
/* 153 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLongValue(long value)
/*     */   {
/* 162 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFloatValue(float value)
/*     */   {
/* 171 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDoubleValue(double value)
/*     */   {
/* 180 */     this.m_value = value;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   public DocumentEntry getParameterByName(String name)
/*     */   {
/* 216 */     if (this.m_attributes != null) {
/* 217 */       for (XMLNodeAttribute attribute : this.m_attributes) {
/* 218 */         if (attribute.getName().equals(name))
/* 219 */           return attribute;
/*     */       }
/*     */     }
/* 222 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addParameter(DocumentEntry parameter)
/*     */   {
/* 231 */     if (!this.m_attributes.contains(parameter)) {
/* 232 */       this.m_attributes.add((XMLNodeAttribute)parameter);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeParameter(DocumentEntry parameter)
/*     */   {
/* 242 */     this.m_attributes.remove(parameter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<? extends DocumentEntry> getParameters()
/*     */   {
/* 251 */     return this.m_attributes;
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
/* 262 */     if (this.m_name.equals(name)) {
/* 263 */       return this;
/*     */     }
/* 265 */     for (DocumentEntry child : this.m_children) {
/* 266 */       DocumentEntry ret = child.getChildByName(name);
/* 267 */       if (ret != null) {
/* 268 */         return ret;
/*     */       }
/*     */     }
/* 271 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<DocumentEntry> getChildrenByName(String name)
/*     */   {
/* 281 */     ArrayList<DocumentEntry> list = new ArrayList();
/*     */     
/* 283 */     if (this.m_name.equals(name)) {
/* 284 */       list.add(this);
/*     */     } else {
/* 286 */       for (DocumentEntry child : this.m_children) {
/* 287 */         ArrayList<DocumentEntry> subList = child.getChildrenByName(name);
/* 288 */         if (subList != null) {
/* 289 */           list.addAll(subList);
/*     */         }
/*     */       }
/*     */     }
/* 293 */     if (list.isEmpty()) {
/* 294 */       list = null;
/*     */     }
/* 296 */     return list;
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
/* 307 */     ArrayList<DocumentEntry> list = new ArrayList();
/*     */     
/* 309 */     for (DocumentEntry child : this.m_children) {
/* 310 */       if (child.getName().equals(name))
/* 311 */         list.add(child);
/*     */     }
/* 313 */     if (list.isEmpty()) {
/* 314 */       list = null;
/*     */     }
/* 316 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addChild(DocumentEntry entry)
/*     */   {
/* 325 */     if (!this.m_children.contains(entry)) {
/* 326 */       this.m_children.add((XMLDocumentNode)entry);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeChild(DocumentEntry entry)
/*     */   {
/* 335 */     this.m_children.remove(entry);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<? extends DocumentEntry> getChildren()
/*     */   {
/* 344 */     return this.m_children;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\xml\XMLDocumentNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */