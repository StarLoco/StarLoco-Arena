/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractSummoningDefinition;
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
/*    */ public class SummoningDefinition
/*    */   extends AbstractSummoningDefinition
/*    */ {
/*    */   private int m_spellId;
/*    */   
/*    */   public SummoningDefinition(int id, int lifePoints, int actionPoints, int movementPoints, int gfxId, int spellId)
/*    */   {
/* 29 */     super(id, lifePoints, actionPoints, movementPoints, gfxId);
/*    */     
/* 31 */     this.m_spellId = spellId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getSpellId()
/*    */   {
/* 38 */     return this.m_spellId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 45 */     return DofusArenaTranslator.getInstance().getString(10, getId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 52 */     return DofusArenaTranslator.getInstance().getString(11, getId());
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\SummoningDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */