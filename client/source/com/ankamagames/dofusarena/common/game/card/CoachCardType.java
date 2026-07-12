/*    */ package com.ankamagames.dofusarena.common.game.card;
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
/*    */ public enum CoachCardType
/*    */ {
/* 15 */   SMILEY(0, "Smiley", new short[] { -1 }),
/* 16 */   EMOTE(1, "Emote", new short[] { -1 }),
/* 17 */   PANT(2, "Culotte", new short[] { 5 }),
/* 18 */   HAIRS(3, "Coiffure", new short[] { 2 }),
/* 19 */   TATOO(4, "Tatouages", new short[] { 1 }),
/* 20 */   ARMBAND(5, "Brassard", new short[] { 4, 12 }),
/* 21 */   SHOES(6, "Bottes", new short[] { 10 }),
/* 22 */   SHOULDERPAD(7, "Epaulette", new short[] { 3, 13 }),
/* 23 */   CLOAK(8, "Cape", new short[] { 8 }),
/* 24 */   TROUSERS(9, "Pantalon", new short[] { 6 }),
/* 25 */   SHIR(10, "Chemise", new short[] { 11 }),
/* 26 */   HAT(11, "Chapeau", new short[1]),
/* 27 */   STAFF(12, "Bâton", new short[] { 7 }),
/* 28 */   PET(13, "Familier", new short[] { 9 }),
/* 29 */   CURSE(14, "Malediction", new short[] { -1 }),
/* 30 */   PET_HEART(15, "Familier - Coeur", new short[] { -1 }),
/* 31 */   PET_MEMBER(16, "Familier - Membre", new short[] { -1 }),
/* 32 */   PET_HEAD(17, "Familier - Tête", new short[] { -1 }),
/* 33 */   PET_BODY(18, "Familier - Tronc", new short[] { -1 }),
/* 34 */   PET_ACCESSORY(19, "Familier - Accessoire", new short[] { -1 });
/*    */   
/*    */   private final int m_id;
/*    */   
/*    */   private final String m_name;
/*    */   private final short[] m_inventoryPositions;
/*    */   
/*    */   CoachCardType(int typeId, String humanReadableName, short[] inventoryPositions) {
/* 42 */     this.m_id = typeId;
/* 43 */     this.m_name = humanReadableName;
/* 44 */     this.m_inventoryPositions = inventoryPositions;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 48 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 52 */     return this.m_name;
/*    */   }
/*    */   
/*    */   public short[] getInventoryPosition() {
/* 56 */     return this.m_inventoryPositions; } public static CoachCardType getFromId(int typeId) {
/*    */     byte b;
/*    */     int i;
/*    */     CoachCardType[] arrayOfCoachCardType;
/* 60 */     for (i = (arrayOfCoachCardType = values()).length, b = 0; b < i; ) { CoachCardType type = arrayOfCoachCardType[b];
/* 61 */       if (type.getId() == typeId)
/* 62 */         return type;  b++; }
/* 63 */      return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\card\CoachCardType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */