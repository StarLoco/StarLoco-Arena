/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.characteristic.FighterCharacteristicType;
/*    */ import gnu.trove.TIntObjectIterator;
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
/*    */ public class DoubleFighter
/*    */   extends Fighter
/*    */ {
/*    */   public DoubleFighter(Fighter father) {
/* 26 */     this.m_father = father;
/* 27 */     setTeamMate(father.getTeamMate());
/* 28 */     setName(father.getName());
/* 29 */     Breed b = father.getBreed();
/* 30 */     setBreedAndSex(b.getId(), father.getSex());
/* 31 */     setSkinIndex(father.getSkinIndex());
/*    */     
/* 33 */     for (TIntObjectIterator<AbstractCharacteristic> it = this.m_characteristics.iterator(); it.hasNext(); ) {
/* 34 */       it.advance();
/* 35 */       AbstractCharacteristic charac = (AbstractCharacteristic)it.value();
/* 36 */       charac.makeDefault();
/*    */     } 
/*    */     
/* 39 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).setMax(b.getBaseHp());
/* 40 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).setMax(b.getBaseMp());
/* 41 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).setMax(b.getBaseAp());
/* 42 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).toMax();
/* 43 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).toMax();
/* 44 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).toMax();
/* 45 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.INIT).setMax(this.m_father.getCharacteristicValue((CharacteristicType)FighterCharacteristicType.INIT));
/* 46 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.INIT).toMax();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSummoned() {
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\DoubleFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */