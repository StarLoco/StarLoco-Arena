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
/*    */ public enum SpellCastValidity
/*    */ {
/* 15 */   OK, 
/*    */   
/* 17 */   OK_BUT_NO_EFFECT_ON_TARGET, 
/*    */   
/* 19 */   INVALID_SPELL, 
/*    */   
/* 21 */   INVALID_LINE_OF_SIGHT, 
/*    */   
/* 23 */   INVALID_TARGET_CELL, 
/*    */   
/* 25 */   INVALID_RANGE, 
/*    */   
/* 27 */   NOT_ENOUGH_PA, 
/*    */   
/* 29 */   TOO_MUCH_CASTS_ON_THIS_TARGET, 
/*    */   
/* 31 */   TOO_MUCH_CASTS_THIS_TURN, 
/*    */   
/* 33 */   LAST_CAST_TOO_RECENT, 
/*    */   
/* 35 */   SPELL_UNKNOWN, 
/*    */   
/* 37 */   CELL_NOT_FREE, 
/*    */   
/* 39 */   CELLS_NOT_ALIGNED, 
/*    */   
/* 41 */   CAST_CRITERIONS_NOT_VALID;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid()
/*    */   {
/* 49 */     return (this == OK) || (this == OK_BUT_NO_EFFECT_ON_TARGET);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\SpellCastValidity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */