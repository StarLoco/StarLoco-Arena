/*    */ package com.ankamagames.dofusarena.common.game.ai;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
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
/*    */ public class CanSummonCriterion
/*    */   extends Criterion
/*    */ {
/*    */   public int getValidity(Object criterionUser, Object criterionTarget, Object criterionContent, Object criterionContext)
/*    */   {
/* 25 */     if (criterionUser == null)
/* 26 */       return -1;
/* 27 */     if (!(criterionUser instanceof AbstractFighter))
/* 28 */       return -1;
/* 29 */     AbstractFighter f = (AbstractFighter)criterionUser;
/* 30 */     if (f.getSummoningsCount() < 1 + f.getCharacteristicValue(FighterCharacteristicType.NB_SUMMONS))
/* 31 */       return 0;
/* 32 */     return -2;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\ai\CanSummonCriterion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */