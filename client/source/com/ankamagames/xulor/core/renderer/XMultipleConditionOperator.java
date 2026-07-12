/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XMultipleConditionOperator
/*    */   extends XOperatorCondition
/*    */ {
/* 13 */   protected final ArrayList<XCondition> m_conditions = new ArrayList<XCondition>();
/*    */   
/*    */   public void addCondition(XCondition condition) {
/* 16 */     this.m_conditions.add(condition);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XMultipleConditionOperator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */