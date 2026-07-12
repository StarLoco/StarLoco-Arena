/*    */ package com.ankamagames.framework.ai.criteria;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Criterion
/*    */ {
/*    */   public abstract int getValidity(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4);
/*    */   
/*    */   public boolean isValid(Object criterionUser, Object criterionTarget, Object criterionContent, Object criterionContext) {
/* 38 */     return (getValidity(criterionUser, criterionTarget, criterionContent, criterionContext) == 0);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\criteria\Criterion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */