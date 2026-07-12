/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XFalseCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isFalse";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 20 */     return "isFalse";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(Object object)
/*    */   {
/* 28 */     if (this.m_comparedValueInit)
/* 29 */       object = this.m_comparedValue;
/* 30 */     if ((object instanceof Boolean)) {
/* 31 */       return !((Boolean)object).booleanValue();
/*    */     }
/*    */     
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 38 */     XFalseCondition clone = new XFalseCondition();
/* 39 */     copyConditionData(clone);
/* 40 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XFalseCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */