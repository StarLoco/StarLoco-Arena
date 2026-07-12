/*     */ package com.ankamagames.xulor.core.renderer;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
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
/*     */ 
/*     */ public class XConditionResult
/*     */   extends XElement
/*     */   implements ResultProvider
/*     */ {
/*     */   public static final String TAG = "Condition";
/*     */   private XCondition m_condition;
/*  23 */   private Object m_value = Boolean.valueOf(true);
/*     */   private boolean m_valueInit = false;
/*  25 */   private Object m_elseValue = Boolean.valueOf(false);
/*     */   private boolean m_elseValueInit = false;
/*     */   private boolean m_returnOriginalValue = false;
/*  28 */   private final ArrayList<XConditionResult> m_conditionResult = new ArrayList<XConditionResult>();
/*     */   protected ResultProviderParent m_parent;
/*     */   
/*     */   public void add(IElement element) {
/*  32 */     if (element instanceof XCondition) {
/*  33 */       setCondition((XCondition)element);
/*  34 */     } else if (element instanceof XConditionResult) {
/*  35 */       addConditionResult((XConditionResult)element);
/*     */     } 
/*  37 */     super.add(element);
/*     */   }
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
/*     */   public Object getEncapsulatedObject() {
/*  52 */     return null;
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
/*     */   public IElement cloneElementStructure() {
/*  71 */     return cloneConditionResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  78 */     return "Condition";
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getResult(Object object) {
/*  83 */     Object trueReturn = (this.m_valueInit || !this.m_returnOriginalValue) ? this.m_value : object;
/*  84 */     Object falseReturn = (this.m_elseValueInit || !this.m_returnOriginalValue) ? this.m_elseValue : object;
/*     */     
/*  86 */     if (this.m_conditionResult.size() == 0) { if (this.m_condition.isValid(object)) return trueReturn; 
/*  87 */       return falseReturn; }
/*     */     
/*  89 */     for (XConditionResult condition : this.m_conditionResult) {
/*  90 */       if (condition.getCondition().isValid(object))
/*  91 */         return condition.isComposite() ? condition.getResult(object) : condition.getValue(); 
/*     */     } 
/*  93 */     return falseReturn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XCondition getCondition() {
/* 100 */     return this.m_condition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCondition(XCondition condition) {
/* 107 */     this.m_condition = condition;
/* 108 */     if (this.m_condition != null) {
/* 109 */       this.m_condition.setConditionParent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getElseValue() {
/* 117 */     return this.m_elseValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElseValue(Object false1) {
/* 124 */     this.m_elseValue = false1;
/* 125 */     this.m_elseValueInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElseValue(String false1) {
/* 132 */     this.m_elseValue = false1;
/* 133 */     this.m_elseValueInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 140 */     return this.m_value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object true1) {
/* 147 */     this.m_value = true1;
/* 148 */     this.m_valueInit = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String true1) {
/* 155 */     this.m_value = true1;
/* 156 */     this.m_valueInit = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReturnOriginalValue() {
/* 161 */     return this.m_returnOriginalValue;
/*     */   }
/*     */   
/*     */   public void setReturnOriginalValue(boolean returnOriginalValue) {
/* 165 */     this.m_returnOriginalValue = returnOriginalValue;
/*     */   }
/*     */   
/*     */   public void fireConditionChanged() {
/* 169 */     if (this.m_parent != null) {
/* 170 */       this.m_parent.fireResultProviderChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResultProviderParent(ResultProviderParent parent) {
/* 176 */     this.m_parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addConditionResult(XConditionResult condition) {
/* 185 */     this.m_conditionResult.add(condition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComposite() {
/* 194 */     return (this.m_conditionResult.size() != 0);
/*     */   }
/*     */   
/*     */   public XConditionResult cloneConditionResult() {
/* 198 */     XConditionResult clone = new XConditionResult();
/* 199 */     if (this.m_condition != null) clone.setCondition(this.m_condition.cloneCondition()); 
/* 200 */     if (this.m_valueInit) clone.setValue(this.m_value); 
/* 201 */     if (this.m_elseValueInit) clone.setElseValue(this.m_elseValue); 
/* 202 */     clone.setReturnOriginalValue(this.m_returnOriginalValue);
/*     */ 
/*     */     
/* 205 */     copyElementData(clone);
/* 206 */     return clone;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XConditionResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */