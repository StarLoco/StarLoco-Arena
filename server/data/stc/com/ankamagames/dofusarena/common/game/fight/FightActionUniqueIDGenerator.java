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
/*    */ public abstract class FightActionUniqueIDGenerator
/*    */ {
/* 15 */   private static int m_incrementalId = 0;
/*    */   
/*    */   public static int getNextID() {
/* 18 */     m_incrementalId += 1;
/* 19 */     if (m_incrementalId < 0)
/* 20 */       m_incrementalId = 0;
/* 21 */     return m_incrementalId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fight\FightActionUniqueIDGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */