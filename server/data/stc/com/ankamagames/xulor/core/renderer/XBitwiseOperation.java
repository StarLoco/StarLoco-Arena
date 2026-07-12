/*     */ package com.ankamagames.xulor.core.renderer;
/*     */ 
/*     */ import com.ankamagames.xulor.util.PrimitiveConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XBitwiseOperation
/*     */   extends XUnaryConditionOperator
/*     */ {
/*     */   public static final String TAG = "BitwiseOperation";
/*     */   public static final String AND_KEY = "and";
/*     */   public static final String OR_KEY = "or";
/*     */   public static final String NOT_KEY = "not";
/*  18 */   private String m_key = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  33 */     return this.m_key;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/*  41 */     this.m_key = key;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/*  49 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  68 */     return "BitwiseOperation";
/*     */   }
/*     */   
/*     */   public void copyElementData(XValueReplacer replacer)
/*     */   {
/*  73 */     replacer.setKey(this.m_key);
/*  74 */     super.copyElementData(replacer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public XValueReplacer cloneElementStructure()
/*     */   {
/*  82 */     XValueReplacer clone = new XValueReplacer();
/*  83 */     copyElementData(clone);
/*  84 */     return clone;
/*     */   }
/*     */   
/*     */   public XCondition cloneCondition() {
/*  88 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValid(Object object)
/*     */   {
/*  96 */     if (this.m_comparedValueInit) {
/*  97 */       object = this.m_comparedValue;
/*     */     }
/*  99 */     Object value = null;
/* 100 */     if (this.m_key != null) {
/* 101 */       if (this.m_key.equalsIgnoreCase("and")) {
/* 102 */         if ((object instanceof Integer)) {
/* 103 */           value = Integer.valueOf(PrimitiveConverter.getInteger(object) & PrimitiveConverter.getInteger(this.m_value));
/* 104 */         } else if ((object instanceof Short)) {
/* 105 */           value = Integer.valueOf(PrimitiveConverter.getShort(object) & PrimitiveConverter.getShort(this.m_value));
/* 106 */         } else if ((object instanceof Long)) {
/* 107 */           value = Long.valueOf(PrimitiveConverter.getLong(object) & PrimitiveConverter.getLong(this.m_value));
/*     */         }
/*     */       }
/* 110 */       if (this.m_key.equalsIgnoreCase("or")) {
/* 111 */         if ((object instanceof Integer)) {
/* 112 */           value = Integer.valueOf(PrimitiveConverter.getInteger(object) | PrimitiveConverter.getInteger(this.m_value));
/* 113 */         } else if ((object instanceof Short)) {
/* 114 */           value = Integer.valueOf(PrimitiveConverter.getShort(object) | PrimitiveConverter.getShort(this.m_value));
/* 115 */         } else if ((object instanceof Long)) {
/* 116 */           value = Long.valueOf(PrimitiveConverter.getLong(object) | PrimitiveConverter.getLong(this.m_value));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 122 */     return this.m_condition.isValid(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XBitwiseOperation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */