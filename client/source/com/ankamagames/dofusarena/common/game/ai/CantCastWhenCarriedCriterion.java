/*    */ package com.ankamagames.dofusarena.common.game.ai;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.framework.ai.criteria.Criterion;
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
/*    */ public class CantCastWhenCarriedCriterion
/*    */   extends Criterion
/*    */ {
/*    */   public int getValidity(Object criterionUser, Object criterionTarget, Object criterionContent, Object criterionContext) {
/* 28 */     if (criterionUser == null)
/* 29 */       return -1; 
/* 30 */     if (!(criterionUser instanceof AbstractFighter))
/* 31 */       return -1; 
/* 32 */     AbstractFighter f = (AbstractFighter)criterionUser;
/* 33 */     if (!f.isCarried())
/* 34 */       return 0; 
/* 35 */     return -2;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\ai\CantCastWhenCarriedCriterion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */