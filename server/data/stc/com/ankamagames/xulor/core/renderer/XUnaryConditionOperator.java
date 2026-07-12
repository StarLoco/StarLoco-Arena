/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XUnaryConditionOperator
/*    */   extends XOperatorCondition
/*    */ {
/*    */   protected XCondition m_condition;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addCondition(XCondition condition)
/*    */   {
/* 20 */     this.m_condition = condition;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XUnaryConditionOperator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */