/*    */ package com.ankamagames.dofusarena.common.game.fighter;
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
/*    */ public abstract class AbstractSummoningDefinition
/*    */ {
/*    */   private int m_id;
/*    */   private int m_lifePoints;
/*    */   private int m_actionPoints;
/*    */   private int m_movementPoints;
/*    */   private int m_baseGfxId;
/*    */   
/*    */   public AbstractSummoningDefinition(int id, int lifePoints, int actionPoints, int movementPoints, int baseGfxId) {
/* 22 */     this.m_id = id;
/* 23 */     this.m_lifePoints = lifePoints;
/* 24 */     this.m_actionPoints = actionPoints;
/* 25 */     this.m_movementPoints = movementPoints;
/* 26 */     this.m_baseGfxId = baseGfxId;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 30 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public int getLifePoints() {
/* 34 */     return this.m_lifePoints;
/*    */   }
/*    */   
/*    */   public int getActionPoints() {
/* 38 */     return this.m_actionPoints;
/*    */   }
/*    */   
/*    */   public int getMovementPoints() {
/* 42 */     return this.m_movementPoints;
/*    */   }
/*    */   
/*    */   public int getBaseGfxId() {
/* 46 */     return this.m_baseGfxId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\AbstractSummoningDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */