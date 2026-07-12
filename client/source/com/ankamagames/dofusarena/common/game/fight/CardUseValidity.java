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
/*    */ public enum CardUseValidity
/*    */ {
/* 15 */   OK,
/*    */   
/* 17 */   INVALID_CARD,
/*    */   
/* 19 */   INVALID_LINE_OF_SIGHT,
/*    */   
/* 21 */   INVALID_TARGET_CELL,
/*    */   
/* 23 */   NOT_ENOUGH_PA,
/*    */   
/* 25 */   INVALID_RANGE,
/*    */   
/* 27 */   CARD_NOT_OWNED,
/*    */   
/* 29 */   CELL_NOT_FREE,
/*    */   
/* 31 */   CELLS_NOT_ALIGNED,
/*    */   
/* 33 */   CRITERIONS_NOT_VALID;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 38 */     return (this == OK);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\CardUseValidity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */