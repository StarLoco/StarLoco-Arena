/*    */ package com.ankamagames.xulor.core.renderer;
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
/*    */ 
/*    */ 
/*    */ public class XNotCondition
/*    */   extends XUnaryConditionOperator
/*    */ {
/*    */   public static final String TAG = "Not";
/*    */   
/*    */   public String getTag() {
/* 21 */     return "Not";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 29 */     if (this.m_comparedValueInit)
/* 30 */       object = this.m_comparedValue; 
/* 31 */     if (this.m_condition != null) {
/* 32 */       return !this.m_condition.isValid(object);
/*    */     }
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 38 */     XNotCondition clone = new XNotCondition();
/* 39 */     copyConditionData(clone);
/* 40 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XNotCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */