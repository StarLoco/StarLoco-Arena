/*     */ package com.ankamagames.xulor.core.renderer;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
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
/*     */ public abstract class XCondition
/*     */   extends XElement
/*     */ {
/*  17 */   protected String m_key = null;
/*  18 */   protected Object m_value = null;
/*  19 */   protected Object m_comparedValue = null;
/*  20 */   protected boolean m_comparedValueInit = false;
/*     */   
/*     */ 
/*     */ 
/*     */   protected XConditionResult m_conditionParent;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/*  35 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object getValue()
/*     */   {
/*  42 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/*  49 */     this.m_value = value;
/*  50 */     fireConditionChanged();
/*     */   }
/*     */   
/*     */   public Object getComparedValue() {
/*  54 */     return this.m_comparedValue;
/*     */   }
/*     */   
/*     */   public void setComparedValue(Object value) {
/*  58 */     this.m_comparedValue = value;
/*  59 */     this.m_comparedValueInit = true;
/*  60 */     fireConditionChanged();
/*     */   }
/*     */   
/*     */   public void fireConditionChanged() {
/*  64 */     if ((this.m_parent instanceof XCondition)) {
/*  65 */       ((XCondition)this.m_parent).fireConditionChanged();
/*  66 */     } else if ((this.m_parent instanceof XConditionResult)) {
/*  67 */       ((XConditionResult)this.m_parent).fireConditionChanged();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  75 */     return this.m_key;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/*  82 */     this.m_key = key;
/*     */   }
/*     */   
/*     */   public XConditionResult getConditionParent()
/*     */   {
/*  87 */     return this.m_conditionParent;
/*     */   }
/*     */   
/*     */   public void setConditionParent(XConditionResult parent) {
/*  91 */     this.m_conditionParent = parent;
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
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 112 */     return cloneCondition();
/*     */   }
/*     */   
/*     */   public void copyConditionData(XCondition condition) {
/* 116 */     condition.setKey(this.m_key);
/* 117 */     condition.setValue(this.m_value);
/* 118 */     if (this.m_comparedValueInit) {
/* 119 */       condition.setComparedValue(this.m_comparedValue);
/*     */     }
/* 121 */     super.copyElementData(condition);
/*     */   }
/*     */   
/*     */   public abstract XCondition cloneCondition();
/*     */   
/*     */   public abstract boolean isValid(Object paramObject);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */