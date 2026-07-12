/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import com.ankamagames.xulor.property.FieldProvider;
/*    */ import com.ankamagames.xulor.template.IItemRenderable;
/*    */ import com.ankamagames.xulor.util.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XItemCondition
/*    */   extends XUnaryConditionOperator
/*    */ {
/*    */   public static final String TAG = "ItemCondition";
/*    */   
/*    */   public String getTag()
/*    */   {
/* 24 */     return "ItemCondition";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(Object value)
/*    */   {
/* 32 */     if (this.m_comparedValueInit) {
/* 33 */       value = this.m_comparedValue;
/*    */     }
/* 35 */     if ((!(value instanceof IItemRenderable)) && (!(value instanceof FieldProvider)))
/*    */     {
/* 37 */       return false;
/*    */     }
/*    */     
/* 40 */     if ((value instanceof FieldProvider)) {
/* 41 */       return this.m_condition.isValid(((FieldProvider)value).getFieldValue(this.m_key));
/*    */     }
/*    */     
/* 44 */     IItemRenderable renderable = (IItemRenderable)value;
/* 45 */     if (renderable == null) {
/* 46 */       return false;
/*    */     }
/*    */     
/* 49 */     Item item = renderable.getItem();
/* 50 */     if ((item != null) && ((item.getValue() instanceof FieldProvider)) && (this.m_key != null)) {
/* 51 */       return this.m_condition.isValid(item.getFieldValue(this.m_key));
/*    */     }
/* 53 */     Object returnedValue = item == null ? null : item.getValue();
/* 54 */     return this.m_condition.isValid(returnedValue);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setField(String field)
/*    */   {
/* 64 */     setKey(field);
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 68 */     XItemCondition clone = new XItemCondition();
/* 69 */     copyConditionData(clone);
/* 70 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XItemCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */