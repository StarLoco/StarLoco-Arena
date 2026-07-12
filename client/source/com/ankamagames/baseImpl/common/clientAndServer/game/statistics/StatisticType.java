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
/*    */ public enum StatisticType
/*    */ {
/* 15 */   TYPE_INT((byte)1, (byte)4),
/* 16 */   TYPE_LONG((byte)2, (byte)8),
/* 17 */   TYPE_FLOAT((byte)3, (byte)4);
/*    */ 
/*    */ 
/*    */   
/*    */   private final byte m_typeId;
/*    */ 
/*    */ 
/*    */   
/*    */   private final byte m_typeSize;
/*    */ 
/*    */ 
/*    */   
/*    */   StatisticType(byte typeId, byte size) {
/* 30 */     this.m_typeId = typeId;
/* 31 */     this.m_typeSize = size;
/*    */   }
/*    */   
/*    */   byte getTypeSize() {
/* 35 */     return this.m_typeSize;
/*    */   }
/*    */   
/*    */   byte getTypeId() {
/* 39 */     return this.m_typeId;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\statistics\StatisticType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */