/*     */ package com.ankamagames.xulor.util;
/*     */ 
/*     */ import com.ankamagames.xulor.core.GenericParser;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
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
/*     */ 
/*     */ 
/*     */ public class Item
/*     */ {
/*  22 */   private static Logger m_logger = Logger.getLogger(GenericParser.class);
/*     */   
/*  24 */   private final HashMap<String, Object> m_associatedValue = new HashMap();
/*     */   private Object m_value;
/*     */   
/*     */   public Item() {}
/*     */   
/*     */   public Item(Object value)
/*     */   {
/*  31 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/*  39 */     this.m_value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getValue()
/*     */   {
/*  47 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/*  56 */     if ((this.m_value != null) && ((this.m_value instanceof FieldProvider)) && (fieldName != null)) {
/*  57 */       return ((FieldProvider)this.m_value).getFieldValue(fieldName);
/*     */     }
/*  59 */     m_logger.error("Tentative échouée de récupérer la valeur du champ " + fieldName);
/*  60 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldValue(String fieldName, Object value)
/*     */   {
/*  69 */     if ((this.m_value != null) && ((this.m_value instanceof FieldProvider)) && (fieldName != null)) {
/*  70 */       ((FieldProvider)this.m_value).setFieldValue(fieldName, value);
/*     */     } else {
/*  72 */       m_logger.error("Tentative échouée de modifier la valeur du champ " + fieldName + " par " + value);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getAssociatedValue()
/*     */   {
/*  78 */     return this.m_associatedValue.get(null);
/*     */   }
/*     */   
/*     */   public void setAssociatedValue(Object object) {
/*  82 */     this.m_associatedValue.put(null, object);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getAssociatedValue(String key)
/*     */   {
/*  89 */     return this.m_associatedValue.get(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAssociatedValue(String key, Object associatedValue)
/*     */   {
/*  96 */     this.m_associatedValue.put(key, associatedValue);
/*     */   }
/*     */   
/*     */   public void cleanAssociatedValue() {
/* 100 */     this.m_associatedValue.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\Item.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */