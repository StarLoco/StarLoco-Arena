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
/*    */ public class XNotNullCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isNotNull";
/*    */   
/*    */   public String getTag() {
/* 21 */     return "isNotNull";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 29 */     if (this.m_comparedValueInit)
/* 30 */       object = this.m_comparedValue; 
/* 31 */     return (object != null);
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 35 */     XNotNullCondition clone = new XNotNullCondition();
/* 36 */     copyConditionData(clone);
/* 37 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XNotNullCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */