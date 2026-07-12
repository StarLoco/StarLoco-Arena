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
/*    */ public enum FightActionType
/*    */ {
/* 15 */   SPELL_CAST((byte)0), 
/* 16 */   CARD_USE((byte)1), 
/* 17 */   CLOSE_COMBAT((byte)2), 
/* 18 */   EFFECT_EXECUTION((byte)3), 
/* 19 */   TACKLE((byte)4), 
/* 20 */   DIE((byte)5), 
/* 21 */   FIGHT_END((byte)6), 
/* 22 */   CHANGE_DIRECTION((byte)7), 
/* 23 */   MOVE((byte)8), 
/* 24 */   EFFEC_AREA_ACTION((byte)9), 
/* 25 */   TURN_END((byte)10), 
/* 26 */   TURN_START((byte)11), 
/* 27 */   NEW_TABLE_TURN((byte)12);
/*    */   
/*    */   private byte m_id;
/*    */   
/*    */   private FightActionType(byte id)
/*    */   {
/* 33 */     this.m_id = id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public byte getId()
/*    */   {
/* 40 */     return this.m_id;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightActionType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */