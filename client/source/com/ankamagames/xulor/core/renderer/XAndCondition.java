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
/*    */ public class XAndCondition
/*    */   extends XMultipleConditionOperator
/*    */ {
/*    */   public static final String TAG = "And";
/*    */   
/*    */   public String getTag() {
/* 19 */     return "And";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 27 */     if (this.m_comparedValueInit)
/* 28 */       object = this.m_comparedValue; 
/* 29 */     for (XCondition condition : this.m_conditions) {
/* 30 */       if (!condition.isValid(object)) {
/* 31 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 39 */     XAndCondition clone = new XAndCondition();
/* 40 */     copyConditionData(clone);
/* 41 */     return clone;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getEncapsulatedObject() {
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XAndCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */