/*     */ package com.ankamagames.xulor.property;
/*     */ 
/*     */ import com.ankamagames.xulor.core.Factory;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProvider;
/*     */ import com.ankamagames.xulor.template.IElement;
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
/*     */ public class PropertyClient
/*     */ {
/*     */   private IElement m_element;
/*     */   private Factory m_factory;
/*     */   private String m_attribute;
/*  24 */   private String m_fieldName = null;
/*  25 */   private ResultProvider m_resultProvider = null;
/*  26 */   private boolean m_layoutOnChange = false;
/*     */   
/*     */   public PropertyClient(IElement element, Factory factory, String attribute, String propertyField, ResultProvider result, boolean layoutOnChange) {
/*  29 */     this.m_element = element;
/*  30 */     this.m_factory = factory;
/*  31 */     this.m_attribute = attribute;
/*  32 */     this.m_fieldName = propertyField;
/*  33 */     this.m_resultProvider = result;
/*  34 */     this.m_layoutOnChange = layoutOnChange;
/*     */   }
/*     */   
/*     */   public PropertyClient(IElement element, Factory factory, String attribute, ResultProvider result, boolean layoutOnChange) {
/*  38 */     this.m_element = element;
/*  39 */     this.m_factory = factory;
/*  40 */     this.m_attribute = attribute;
/*  41 */     this.m_resultProvider = result;
/*  42 */     this.m_layoutOnChange = layoutOnChange;
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
/*     */   public String getAttribute()
/*     */   {
/*  66 */     return this.m_attribute;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAttribute(String attribute)
/*     */   {
/*  76 */     this.m_attribute = attribute;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getElement()
/*     */   {
/*  85 */     return this.m_element;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setElement(IElement element)
/*     */   {
/*  95 */     this.m_element = element;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Factory getFactory()
/*     */   {
/* 104 */     return this.m_factory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFactory(Factory factory)
/*     */   {
/* 114 */     this.m_factory = factory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFieldName()
/*     */   {
/* 123 */     return this.m_fieldName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldName(String propertyField)
/*     */   {
/* 132 */     this.m_fieldName = propertyField;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ResultProvider getResultProvider()
/*     */   {
/* 139 */     return this.m_resultProvider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setResultProvider(ResultProvider result)
/*     */   {
/* 146 */     this.m_resultProvider = result;
/*     */   }
/*     */   
/*     */   public boolean hasLayoutOnChange() {
/* 150 */     return this.m_layoutOnChange;
/*     */   }
/*     */   
/*     */   public void setLayoutOnChange(boolean layoutOnChange) {
/* 154 */     this.m_layoutOnChange = layoutOnChange;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 158 */     return "(PropertyClient Element:" + this.m_element + " attribute=" + this.m_attribute + " field=" + this.m_fieldName + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\property\PropertyClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */