/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XStringCondition
/*    */   extends XUnaryConditionOperator
/*    */ {
/*    */   public static final String TAG = "StringCondition";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 20 */     return "StringCondition";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(Object value)
/*    */   {
/* 28 */     if (this.m_comparedValueInit)
/* 29 */       value = this.m_comparedValue;
/* 30 */     if ((!(this.m_value instanceof String)) || (!(value instanceof String)) || (this.m_condition == null) || (this.m_key == null)) {
/* 31 */       return false;
/*    */     }
/* 33 */     String string = (String)value;
/*    */     
/* 35 */     if (this.m_key.equalsIgnoreCase("length"))
/* 36 */       return this.m_condition.isValid(Integer.valueOf(string.length()));
/* 37 */     if (this.m_key.equalsIgnoreCase("startsWith")) {
/* 38 */       return this.m_condition.isValid(Boolean.valueOf(string.startsWith((String)this.m_value)));
/*    */     }
/*    */     
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 45 */     XStringCondition clone = new XStringCondition();
/* 46 */     copyConditionData(clone);
/* 47 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XStringCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */