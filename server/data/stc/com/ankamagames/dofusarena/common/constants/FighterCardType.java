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
/*    */   private String m_description;
/*    */   private short m_inventoryPosition;
/*    */   
/*    */   private FighterCardType(byte index, String description, short inventoryPosition)
/*    */   {
/* 25 */     this.m_index = index;
/* 26 */     this.m_description = description;
/* 27 */     this.m_inventoryPosition = inventoryPosition;
/*    */   }
/*    */   
/*    */   public short getInventoryPosition() {
/* 31 */     return this.m_inventoryPosition;
/*    */   }
/*    */   
/*    */ 
/* 35 */   public byte getIndex() { return this.m_index; }
/*    */   
/*    */   public static FighterCardType getTypeFromIndex(byte index) {
/*    */     FighterCardType[] arrayOfFighterCardType;
/* 39 */     int j = (arrayOfFighterCardType = values()).length; for (int i = 0; i < j; i++) { FighterCardType fct = arrayOfFighterCardType[i];
/* 40 */       if (fct.getIndex() == index)
/* 41 */         return fct; }
/* 42 */     return null;
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\constants\FighterCardType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */