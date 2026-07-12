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
/*    */ public class XNullCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isNull";
/*    */   
/*    */   public String getTag() {
/* 20 */     return "isNull";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(Object object) {
/* 28 */     if (this.m_comparedValueInit)
/* 29 */       object = this.m_comparedValue; 
/* 30 */     return (object == null);
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 34 */     XNullCondition clone = new XNullCondition();
/* 35 */     copyConditionData(clone);
/* 36 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XNullCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */