/*    */ package com.ankamagames.dofusarena.client.core.game.actor;
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
/*    */ public enum CoachEquipmentType
/*    */ {
/* 16 */   PANT(com.ankamagames.dofusarena.common.game.card.CoachCardType.PANT.getInventoryPosition()[0], new String[] { "Jupe" }), 
/* 17 */   HAIRS(com.ankamagames.dofusarena.common.game.card.CoachCardType.HAIRS.getInventoryPosition()[0], new String[] { "Cheveux", "Natte" }), 
/* 18 */   TATOO(com.ankamagames.dofusarena.common.game.card.CoachCardType.TATOO.getInventoryPosition()[0], new String[] { "CorpsTatoo" }), 
/* 19 */   ARMBAND_LEFT(com.ankamagames.dofusarena.common.game.card.CoachCardType.ARMBAND.getInventoryPosition()[0], new String[] { "Brassard-G" }), 
/* 20 */   ARMBAND_RIGHT(com.ankamagames.dofusarena.common.game.card.CoachCardType.ARMBAND.getInventoryPosition()[1], new String[] { "Brassard-D" }), 
/* 21 */   SHOES(com.ankamagames.dofusarena.common.game.card.CoachCardType.SHOES.getInventoryPosition()[0], new String[] { "Botte-G", "Botte-D", "PiedHabit-G", "PiedHabit-D" }), 
/* 22 */   SHOULDERPAD_LEFT(com.ankamagames.dofusarena.common.game.card.CoachCardType.SHOULDERPAD.getInventoryPosition()[0], new String[] { "Epaulette-G" }), 
/* 23 */   SHOULDERPAD_RIGHT(com.ankamagames.dofusarena.common.game.card.CoachCardType.SHOULDERPAD.getInventoryPosition()[1], new String[] { "Epaulette-D" }), 
/* 24 */   CLOAK(com.ankamagames.dofusarena.common.game.card.CoachCardType.CLOAK.getInventoryPosition()[0], new String[] { "Cape" }), 
/* 25 */   TROUSERS(com.ankamagames.dofusarena.common.game.card.CoachCardType.TROUSERS.getInventoryPosition()[0], new String[] { "BassinHabit", "CuisseHabit-G", "CuisseHabit-D", "JambeHabit-G", "JambeHabit-D" }), 
/* 26 */   SHIR(com.ankamagames.dofusarena.common.game.card.CoachCardType.SHIR.getInventoryPosition()[0], new String[] { "TroncHabit", "CorpsMaleHabit", "CorpsFemeleHabit", "EpauleHabit-G", "EpauleHabit-D", "BrasHabit-G", "BrasHabit-D" }), 
/* 27 */   HAT(com.ankamagames.dofusarena.common.game.card.CoachCardType.HAT.getInventoryPosition()[0], new String[] { "Chapeau" }), 
/* 28 */   STAFF(com.ankamagames.dofusarena.common.game.card.CoachCardType.STAFF.getInventoryPosition()[0], new String[] { "Arme" });
/*    */   
/*    */ 
/*    */ 
/*    */   private short m_position;
/*    */   
/*    */ 
/*    */   private String[] m_linkageNames;
/*    */   
/*    */ 
/*    */   private CoachEquipmentType(short position, String[] linkageNames)
/*    */   {
/* 40 */     this.m_position = position;
/* 41 */     this.m_linkageNames = linkageNames;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public short getPosition()
/*    */   {
/* 48 */     return this.m_position;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String[] getLinkageNames()
/*    */   {
/* 55 */     return this.m_linkageNames;
/*    */   }
/*    */   
/*    */ 
/*    */   public static CoachEquipmentType getActorEquipmentTypeFromPosition(short position)
/*    */   {
/*    */     CoachEquipmentType[] arrayOfCoachEquipmentType;
/*    */     
/* 63 */     int j = (arrayOfCoachEquipmentType = values()).length; for (int i = 0; i < j; i++) { CoachEquipmentType actorEquipmentType = arrayOfCoachEquipmentType[i];
/* 64 */       if (actorEquipmentType.getPosition() == position) {
/* 65 */         return actorEquipmentType;
/*    */       }
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\CoachEquipmentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */