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
/*    */ public class XOrCondition
/*    */   extends XMultipleConditionOperator
/*    */ {
/*    */   public static final String TAG = "Or";
/*    */   
/*    */   public String getTag() {
/* 21 */     return "Or";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 29 */     if (this.m_comparedValueInit)
/* 30 */       object = this.m_comparedValue; 
/* 31 */     for (XCondition condition : this.m_conditions) {
/* 32 */       if (condition.isValid(object)) {
/* 33 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 41 */     XOrCondition clone = new XOrCondition();
/* 42 */     copyConditionData(clone);
/* 43 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XOrCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */