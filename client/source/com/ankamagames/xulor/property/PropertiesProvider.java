/*     */ package com.ankamagames.xulor.property;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class PropertiesProvider
/*     */ {
/*  19 */   private static Logger m_logger = Logger.getLogger(PropertiesProvider.class);
/*     */   
/*  21 */   private final HashMap<String, Property> m_properties = new HashMap<String, Property>();
/*  22 */   private final ArrayList<Property> m_needProcess = new ArrayList<Property>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(Property property) {
/*  30 */     this.m_properties.put(property.getName(), property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<Property> getProperties() {
/*  38 */     return this.m_properties.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onProcess() {
/*  45 */     boolean aPropertyChanged = !this.m_needProcess.isEmpty();
/*  46 */     synchronized (this.m_needProcess) {
/*  47 */       for (int i = 0; i < this.m_needProcess.size(); i++) {
/*  48 */         ((Property)this.m_needProcess.get(i)).onProcess();
/*     */       }
/*  50 */       this.m_needProcess.clear();
/*     */     } 
/*  52 */     return aPropertyChanged;
/*     */   }
/*     */   
/*     */   public void addToProcessList(Property property) {
/*  56 */     synchronized (this.m_needProcess) {
/*  57 */       if (property != null && !this.m_needProcess.contains(property)) {
/*  58 */         this.m_needProcess.add(property);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeProperty(String name) {
/*  69 */     this.m_properties.remove(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyValue(String name, Object value) {
/*  79 */     Property property = this.m_properties.get(name);
/*  80 */     if (property == null) {
/*  81 */       property = new Property(name);
/*  82 */       addProperty(property);
/*     */     } 
/*  84 */     property.setValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependPropertyValue(String name, Object value) {
/*  95 */     Property property = this.m_properties.get(name);
/*  96 */     if (property == null) {
/*  97 */       setPropertyValue(name, value);
/*     */     } else {
/*  99 */       property.prependValue(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendPropertyValue(String name, Object value) {
/* 110 */     Property property = this.m_properties.get(name);
/* 111 */     if (property == null) {
/* 112 */       setPropertyValue(name, value);
/*     */     } else {
/* 114 */       property.appendValue(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyValue(String name, String field, Object value) {
/* 126 */     Property property = this.m_properties.get(name);
/* 127 */     if (property != null) {
/* 128 */       property.setFieldValue(field, value);
/*     */     } else {
/* 130 */       m_logger.error("La définition d'une valeur de champ est impossible sur la propriété " + name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependPropertyValue(String name, String field, Object value) {
/* 142 */     Property property = this.m_properties.get(name);
/* 143 */     if (property == null) {
/* 144 */       setPropertyValue(name, value);
/*     */     } else {
/* 146 */       property.prependValue(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendPropertyValue(String name, String field, Object value) {
/* 158 */     Property property = this.m_properties.get(name);
/* 159 */     if (property != null) {
/* 160 */       property.appendFieldValue(field, value);
/*     */     } else {
/* 162 */       m_logger.error("La définition d'une valeur de champ est impossible sur la propriété " + name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void firePropertyValueChanged(FieldProvider fieldProvider, String[] fields) {
/*     */     byte b;
/*     */     int i;
/*     */     String[] arrayOfString;
/* 176 */     for (i = (arrayOfString = fields).length, b = 0; b < i; ) { String field = arrayOfString[b];
/* 177 */       firePropertyValueChanged(fieldProvider, field);
/*     */       b++; }
/*     */   
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
/*     */   public void firePropertyValueChanged(FieldProvider fieldProvider, String field) {
/* 191 */     synchronized (this.m_properties) {
/* 192 */       for (Property property : this.m_properties.values()) {
/* 193 */         Object value = property.getValue();
/* 194 */         if (value != null && value.equals(fieldProvider)) {
/* 195 */           property.fireFieldValueChanged(field);
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */   public void firePropertyValueChanged(String name, String field) {
/* 211 */     Property property = this.m_properties.get(name);
/* 212 */     if (property != null) {
/* 213 */       property.fireFieldValueChanged(field);
/*     */     } else {
/* 215 */       m_logger.error("La définition d'une valeur de champ est impossible sur la propriété " + name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringProperty(String name) {
/* 226 */     Property prop = this.m_properties.get(name);
/*     */     
/* 228 */     if (prop != null) {
/* 229 */       return prop.getString();
/*     */     }
/*     */     
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBooleanProperty(String name) {
/* 242 */     Property prop = this.m_properties.get(name);
/*     */     
/* 244 */     if (prop != null) {
/* 245 */       return prop.getBoolean();
/*     */     }
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntProperty(String name) {
/* 257 */     Property prop = this.m_properties.get(name);
/*     */     
/* 259 */     if (prop != null) {
/* 260 */       return prop.getInt();
/*     */     }
/* 262 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloatProperty(String name) {
/* 272 */     Property prop = this.m_properties.get(name);
/*     */     
/* 274 */     if (prop != null) {
/* 275 */       return prop.getFloat();
/*     */     }
/*     */     
/* 278 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDoubleProperty(String name) {
/* 288 */     Property prop = this.m_properties.get(name);
/*     */     
/* 290 */     if (prop != null) {
/* 291 */       return prop.getDouble();
/*     */     }
/* 293 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLongProperty(String name) {
/* 303 */     Property prop = this.m_properties.get(name);
/*     */     
/* 305 */     if (prop != null) {
/* 306 */       return prop.getLong();
/*     */     }
/*     */     
/* 309 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObjectProperty(String name) {
/* 319 */     Property prop = this.m_properties.get(name);
/*     */     
/* 321 */     if (prop != null) {
/* 322 */       return prop.getValue();
/*     */     }
/*     */     
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property getProperty(String name) {
/* 335 */     return this.m_properties.get(name);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\property\PropertiesProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */