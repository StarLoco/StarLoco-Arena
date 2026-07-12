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
/*    */ public abstract class XUnaryConditionOperator
/*    */   extends XOperatorCondition
/*    */ {
/*    */   protected XCondition m_condition;
/*    */   
/*    */   public void addCondition(XCondition condition) {
/* 20 */     this.m_condition = condition;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XUnaryConditionOperator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */