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
/*    */ public class XTrueCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isTrue";
/*    */   
/*    */   public String getTag() {
/* 20 */     return "isTrue";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 28 */     if (this.m_comparedValueInit)
/* 29 */       object = this.m_comparedValue; 
/* 30 */     if (object instanceof Boolean) {
/* 31 */       return ((Boolean)object).booleanValue();
/*    */     }
/* 33 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 37 */     XTrueCondition clone = new XTrueCondition();
/* 38 */     copyConditionData(clone);
/* 39 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XTrueCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */