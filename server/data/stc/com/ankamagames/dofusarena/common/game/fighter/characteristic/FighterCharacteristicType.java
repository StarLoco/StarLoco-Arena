/*    */ package com.ankamagames.dofusarena.common.game.fighter.characteristic;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.characteristic.CharacteristicType;
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
/*    */ public enum FighterCharacteristicType
/*    */   implements CharacteristicType
/*    */ {
/* 18 */   HP(1, 0, Integer.MAX_VALUE, 0, 50, 50), 
/* 19 */   AP(2, 0, 12, 0, 6, 6), 
/* 20 */   MP(3, 0, 8, 0, 3, 3), 
/* 21 */   INIT(4, 0, Integer.MAX_VALUE, 0, 0, 0), 
/* 22 */   RES_FIRE_PERCENT(5, -100, 100, -100, 100, 0), 
/* 23 */   RES_WATER_PERCENT(6, -100, 100, -100, 100, 0), 
/* 24 */   RES_EARTH_PERCENT(7, -100, 100, -100, 100, 0), 
/* 25 */   RES_WIND_PERCENT(8, -100, 100, -100, 100, 0), 
/* 26 */   DMG_FIRE_PERCENT(9, 65036, 500, -100, 100, 0), 
/* 27 */   DMG_WATER_PERCENT(10, 65036, 500, -100, 100, 0), 
/* 28 */   DMG_EARTH_PERCENT(11, 65036, 500, -100, 100, 0), 
/* 29 */   DMG_WIND_PERCENT(12, 65036, 500, -100, 100, 0), 
/* 30 */   RES(13, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 31 */   RES_FIRE(14, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 32 */   RES_WATER(15, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 33 */   RES_EARTH(16, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 34 */   RES_WIND(17, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 35 */   DMG(18, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 36 */   DMG_FIRE(19, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 37 */   DMG_WATER(20, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 38 */   DMG_EARTH(21, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 39 */   DMG_WIND(22, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 40 */   RANGE(23, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0), 
/* 41 */   CRITICAL_RATE(24, 0, Integer.MAX_VALUE, 0, 100, 0), 
/* 42 */   FUMBLE_RATE(25, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 43 */   NB_SUMMONS(26, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0), 
/* 44 */   HEAL(29, 65036, 500, 65036, 500, 0), 
/* 45 */   RES_AP_LOSS(30, -100, 100, -100, 100, 0), 
/* 46 */   RES_MP_LOSS(31, -100, 100, -100, 100, 0), 
/* 47 */   RES_IN_PERCENT(32, -100, 100, -100, 100, 0), 
/* 48 */   DMG_IN_PERCENT(33, -100, 100, -100, 100, 0), 
/* 49 */   DMG_REBOUND(34, 0, 99, 0, 99, 0);
/*    */   
/*    */   private byte m_id;
/*    */   private int m_lowerBound;
/*    */   private int m_upperBound;
/*    */   private int m_defaultMin;
/*    */   private int m_defaultMax;
/*    */   private int m_defaultValue;
/*    */   
/*    */   private FighterCharacteristicType(int id, int lowerBound, int upperBound, int defaultMin, int defaultMax, int defaultValue)
/*    */   {
/* 60 */     this.m_id = ((byte)id);
/* 61 */     this.m_lowerBound = lowerBound;
/* 62 */     this.m_upperBound = upperBound;
/* 63 */     this.m_defaultMin = defaultMin;
/* 64 */     this.m_defaultMax = defaultMax;
/* 65 */     this.m_defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 69 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public int getLowerBound() {
/* 73 */     return this.m_lowerBound;
/*    */   }
/*    */   
/*    */   public int getUpperBound() {
/* 77 */     return this.m_upperBound;
/*    */   }
/*    */   
/*    */   public int getDefaultMin() {
/* 81 */     return this.m_defaultMin;
/*    */   }
/*    */   
/*    */   public int getDefaultMax() {
/* 85 */     return this.m_defaultMax;
/*    */   }
/*    */   
/*    */   public int getDefaultValue() {
/* 89 */     return this.m_defaultValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\fighter\characteristic\FighterCharacteristicType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */