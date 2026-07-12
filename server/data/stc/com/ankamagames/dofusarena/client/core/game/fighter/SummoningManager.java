/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractSummoningManager;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SummoningManager
/*    */   extends AbstractSummoningManager<SummoningDefinition>
/*    */ {
/* 16 */   private static final SummoningManager m_uniqueInstance = new SummoningManager();
/*    */   
/*    */   public static SummoningManager getInstance() {
/* 19 */     return m_uniqueInstance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void initializeCharacteristics(Fighter summon, int summonId)
/*    */   {
/* 26 */     SummoningDefinition summoningDefinition = (SummoningDefinition)getSummoningDefinition(summonId);
/*    */     
/* 28 */     if (summoningDefinition == null) {
/* 29 */       return;
/*    */     }
/* 31 */     summon.getCharacteristic(FighterCharacteristicType.HP).setMax(summoningDefinition.getLifePoints());
/* 32 */     summon.getCharacteristic(FighterCharacteristicType.HP).toMax();
/* 33 */     summon.getCharacteristic(FighterCharacteristicType.MP).setMax(summoningDefinition.getMovementPoints());
/* 34 */     summon.getCharacteristic(FighterCharacteristicType.MP).toMax();
/* 35 */     summon.getCharacteristic(FighterCharacteristicType.AP).setMax(summoningDefinition.getActionPoints());
/* 36 */     summon.getCharacteristic(FighterCharacteristicType.AP).toMax();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\SummoningManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */