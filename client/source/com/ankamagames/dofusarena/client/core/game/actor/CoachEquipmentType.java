/*    */ package com.ankamagames.dofusarena.client.core.game.actor;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.card.CoachCardType;
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
/* 16 */   PANT(CoachCardType.PANT.getInventoryPosition()[0], new String[] { "Jupe" }),
/* 17 */   HAIRS(CoachCardType.HAIRS.getInventoryPosition()[0], new String[] { "Cheveux", "Natte" }),
/* 18 */   TATOO(CoachCardType.TATOO.getInventoryPosition()[0], new String[] { "CorpsTatoo" }),
/* 19 */   ARMBAND_LEFT(CoachCardType.ARMBAND.getInventoryPosition()[0], new String[] { "Brassard-G" }),
/* 20 */   ARMBAND_RIGHT(CoachCardType.ARMBAND.getInventoryPosition()[1], new String[] { "Brassard-D" }),
/* 21 */   SHOES(CoachCardType.SHOES.getInventoryPosition()[0], new String[] { "Botte-G", "Botte-D", "PiedHabit-G", "PiedHabit-D" }),
/* 22 */   SHOULDERPAD_LEFT(CoachCardType.SHOULDERPAD.getInventoryPosition()[0], new String[] { "Epaulette-G" }),
/* 23 */   SHOULDERPAD_RIGHT(CoachCardType.SHOULDERPAD.getInventoryPosition()[1], new String[] { "Epaulette-D" }),
/* 24 */   CLOAK(CoachCardType.CLOAK.getInventoryPosition()[0], new String[] { "Cape" }),
/* 25 */   TROUSERS(CoachCardType.TROUSERS.getInventoryPosition()[0], new String[] { "BassinHabit", "CuisseHabit-G", "CuisseHabit-D", "JambeHabit-G", "JambeHabit-D" }),
/* 26 */   SHIR(CoachCardType.SHIR.getInventoryPosition()[0], new String[] { "TroncHabit", "CorpsMaleHabit", "CorpsFemeleHabit", "EpauleHabit-G", "EpauleHabit-D", "BrasHabit-G", "BrasHabit-D" }),
/* 27 */   HAT(CoachCardType.HAT.getInventoryPosition()[0], new String[] { "Chapeau" }),
/* 28 */   STAFF(CoachCardType.STAFF.getInventoryPosition()[0], new String[] { "Arme" });
/*    */ 
/*    */ 
/*    */   
/*    */   private short m_position;
/*    */ 
/*    */   
/*    */   private String[] m_linkageNames;
/*    */ 
/*    */ 
/*    */   
/*    */   CoachEquipmentType(short position, String[] linkageNames) {
/* 40 */     this.m_position = position;
/* 41 */     this.m_linkageNames = linkageNames;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getPosition() {
/* 48 */     return this.m_position;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLinkageNames() {
/* 55 */     return this.m_linkageNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CoachEquipmentType getActorEquipmentTypeFromPosition(short position) {
/*    */     byte b;
/*    */     int i;
/*    */     CoachEquipmentType[] arrayOfCoachEquipmentType;
/* 63 */     for (i = (arrayOfCoachEquipmentType = values()).length, b = 0; b < i; ) { CoachEquipmentType actorEquipmentType = arrayOfCoachEquipmentType[b];
/* 64 */       if (actorEquipmentType.getPosition() == position)
/* 65 */         return actorEquipmentType; 
/*    */       b++; }
/*    */     
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\CoachEquipmentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */