/*    */ package com.ankamagames.dofusarena.common.game.fighter;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractSummoningManager<S extends AbstractSummoningDefinition>
/*    */ {
/* 15 */   private final TIntObjectHashMap<S> m_summoningDefinitions = new TIntObjectHashMap();
/*    */   
/*    */   public S getSummoningDefinition(int summoningId) {
/* 18 */     return (S)this.m_summoningDefinitions.get(summoningId);
/*    */   }
/*    */   
/*    */   public void addSummoningDefinition(S summoning) {
/* 22 */     this.m_summoningDefinitions.put(summoning.getId(), summoning);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\AbstractSummoningManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */