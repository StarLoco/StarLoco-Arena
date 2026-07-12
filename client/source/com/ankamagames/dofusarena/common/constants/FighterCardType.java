/*    */ package com.ankamagames.dofusarena.common.constants;
/*    */ 
/*    */ import com.ankamagames.framework.external.ExportableEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum FighterCardType
/*    */   implements ExportableEnum
/*    */ {
/* 13 */   WEAPON((byte)1, "Arme", (short)0),
/* 14 */   PET((byte)2, "Familier", (short)1),
/* 15 */   CLOAK((byte)3, "Cape", (short)2),
/* 16 */   HAT((byte)4, "Chapeau", (short)3),
/* 17 */   DOFUS((byte)5, "Dofus", (short)4);
/*    */   
/*    */   private byte m_index;
/*    */   
/*    */   private String m_description;
/*    */   private short m_inventoryPosition;
/*    */   
/*    */   FighterCardType(byte index, String description, short inventoryPosition) {
/* 25 */     this.m_index = index;
/* 26 */     this.m_description = description;
/* 27 */     this.m_inventoryPosition = inventoryPosition;
/*    */   }
/*    */   
/*    */   public short getInventoryPosition() {
/* 31 */     return this.m_inventoryPosition;
/*    */   }
/*    */   
/*    */   public byte getIndex() {
/* 35 */     return this.m_index; } public static FighterCardType getTypeFromIndex(byte index) {
/*    */     byte b;
/*    */     int i;
/*    */     FighterCardType[] arrayOfFighterCardType;
/* 39 */     for (i = (arrayOfFighterCardType = values()).length, b = 0; b < i; ) { FighterCardType fct = arrayOfFighterCardType[b];
/* 40 */       if (fct.getIndex() == index)
/* 41 */         return fct;  b++; }
/* 42 */      return null;
/*    */   }
/*    */   
/*    */   public String getEnumId() {
/* 46 */     return Byte.valueOf(getIndex()).toString();
/*    */   }
/*    */   
/*    */   public String getEnumLabel() {
/* 50 */     return this.m_description;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\constants\FighterCardType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */