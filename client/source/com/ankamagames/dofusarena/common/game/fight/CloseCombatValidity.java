/*    */ package com.ankamagames.dofusarena.common.game.fight;
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
/*    */ public enum CloseCombatValidity
/*    */ {
/* 15 */   OK,
/*    */   
/* 17 */   INVALID_TARGET_CELL,
/*    */   
/* 19 */   NOT_ENOUGH_PA,
/*    */   
/* 21 */   INVALID_RANGE,
/*    */   
/* 23 */   CRITERIONS_NOT_VALID;
/*    */   
/*    */   public boolean isValid() {
/* 26 */     return (this == OK);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\CloseCombatValidity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */