/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.AbstractCharacteristic;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.dofusarena.client.core.game.spell.SpellManager;
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
/*    */ public class SummonedFighter
/*    */   extends Fighter
/*    */ {
/*    */   private String m_baseGfx;
/*    */   
/*    */   public SummonedFighter(Fighter father, SummoningDefinition definition) {
/* 30 */     this.m_father = father;
/* 31 */     this.m_breed = Breed.MONSTER;
/* 32 */     setTeamMate(father.getTeamMate());
/* 33 */     setName(String.format(definition.getName(), new Object[] { father.getName() }));
/* 34 */     setBaseGfx(String.valueOf(definition.getBaseGfxId()));
/*    */     
/* 36 */     for (TIntObjectIterator<AbstractCharacteristic> it = this.m_characteristics.iterator(); it.hasNext(); ) {
/* 37 */       it.advance();
/* 38 */       AbstractCharacteristic charac = (AbstractCharacteristic)it.value();
/* 39 */       charac.makeDefault();
/*    */     } 
/*    */     
/* 42 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).setMax(definition.getLifePoints());
/* 43 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).setMax(definition.getMovementPoints());
/* 44 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).setMax(definition.getActionPoints());
/* 45 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.AP).toMax();
/* 46 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.HP).toMax();
/* 47 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.MP).toMax();
/* 48 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.INIT).setMax(this.m_father.getCharacteristicValue((CharacteristicType)FighterCharacteristicType.INIT));
/* 49 */     getCharacteristic((CharacteristicType)FighterCharacteristicType.INIT).toMax();
/*    */ 
/*    */     
/* 52 */     if (definition.getSpellId() > 0) {
/*    */       try {
/* 54 */         getSpellInventory().add((InventoryContent)SpellManager.getInstance().getSpell(definition.getSpellId()));
/* 55 */       } catch (Exception e) {
/* 56 */         m_logger.error("Erreur lors de l'ajout d'un sort à un SummonedFighter :", e);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBaseGfx(String baseGfx) {
/* 65 */     this.m_baseGfx = baseGfx;
/* 66 */     updateActorGfx();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getBaseGfx() {
/* 76 */     return this.m_baseGfx;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSummoned() {
/* 86 */     return true;
/*    */   }
/*    */   
/*    */   public void setSex(byte sex) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\SummonedFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */