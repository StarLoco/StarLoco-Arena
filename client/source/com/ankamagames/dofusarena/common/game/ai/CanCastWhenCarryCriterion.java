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
/*    */ public class CanCastWhenCarryCriterion
/*    */   extends Criterion
/*    */ {
/*    */   private boolean m_canCastWhenCarry;
/*    */   
/*    */   public CanCastWhenCarryCriterion(boolean canCastWhenCarry) {
/* 22 */     this.m_canCastWhenCarry = canCastWhenCarry;
/*    */   }
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
/*    */   public int getValidity(Object criterionUser, Object criterionTarget, Object criterionContent, Object criterionContext) {
/* 36 */     if (criterionUser == null)
/* 37 */       return -1; 
/* 38 */     if (!(criterionUser instanceof AbstractFighter))
/* 39 */       return -1; 
/* 40 */     AbstractFighter f = (AbstractFighter)criterionUser;
/* 41 */     if (this.m_canCastWhenCarry == f.isCarrying())
/* 42 */       return 0; 
/* 43 */     return -2;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\ai\CanCastWhenCarryCriterion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */