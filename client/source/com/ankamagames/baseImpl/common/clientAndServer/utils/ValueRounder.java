/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueRounder
/*    */ {
/*    */   public static int randomRound(float value) {
/* 11 */     double integerPart = Math.floor(value);
/* 12 */     double decimalPart = value - integerPart;
/* 13 */     if (MersenneTwister.getInstance().nextBoolean(decimalPart))
/* 14 */       integerPart++; 
/* 15 */     return (int)integerPart;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServe\\utils\ValueRounder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */