/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.statistics;
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
/*    */ class StatisticsEntry
/*    */ {
/*    */   private final StatisticType m_type;
/*    */   private float m_floatValue;
/*    */   private long m_longValue;
/*    */   
/*    */   public StatisticsEntry(int intValue) {
/* 20 */     this.m_type = StatisticType.TYPE_INT;
/* 21 */     this.m_longValue = intValue;
/*    */   }
/*    */   
/*    */   public StatisticsEntry(long longValue) {
/* 25 */     this.m_type = StatisticType.TYPE_LONG;
/* 26 */     this.m_longValue = longValue;
/*    */   }
/*    */   
/*    */   public StatisticsEntry(float floatValue) {
/* 30 */     this.m_type = StatisticType.TYPE_FLOAT;
/* 31 */     this.m_floatValue = floatValue;
/*    */   }
/*    */   
/*    */   public float getFloatValue() {
/* 35 */     return this.m_floatValue;
/*    */   }
/*    */   
/*    */   public void setFloatValue(float floatValue) {
/* 39 */     this.m_floatValue = floatValue;
/*    */   }
/*    */   
/*    */   public long getLongValue() {
/* 43 */     return this.m_longValue;
/*    */   }
/*    */   
/*    */   public void setLongValue(long longValue) {
/* 47 */     this.m_longValue = longValue;
/*    */   }
/*    */   
/*    */   public int getIntValue() {
/* 51 */     return (int)this.m_longValue;
/*    */   }
/*    */   
/*    */   public void setIntValue(int intValue) {
/* 55 */     this.m_longValue = intValue;
/*    */   }
/*    */   
/*    */   public StatisticType getType() {
/* 59 */     return this.m_type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     String ret = "";
/* 67 */     if (this.m_type == StatisticType.TYPE_INT) {
/* 68 */       ret = String.valueOf(ret) + this.m_longValue + " (as int)";
/* 69 */     } else if (this.m_type == StatisticType.TYPE_LONG) {
/* 70 */       ret = String.valueOf(ret) + this.m_longValue + " (as long)";
/* 71 */     } else if (this.m_type == StatisticType.TYPE_FLOAT) {
/* 72 */       ret = String.valueOf(ret) + this.m_floatValue + " (as float)";
/*    */     } 
/* 74 */     return ret;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticsEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */