/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
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
/*    */   
/*    */   public void initializeCharacteristics(Fighter summon, int summonId) {
/* 26 */     SummoningDefinition summoningDefinition = (SummoningDefinition)getSummoningDefinition(summonId);
/*    */     
/* 28 */     if (summoningDefinition == null) {
/*    */       return;
/*    */     }
/* 31 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).setMax(summoningDefinition.getLifePoints());
/* 32 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).toMax();
/* 33 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).setMax(summoningDefinition.getMovementPoints());
/* 34 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).toMax();
/* 35 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).setMax(summoningDefinition.getActionPoints());
/* 36 */     summon.getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).toMax();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\SummoningManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */