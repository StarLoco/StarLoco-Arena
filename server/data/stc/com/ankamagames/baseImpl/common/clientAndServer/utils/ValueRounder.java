/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueRounder
/*    */ {
/*    */   public static int randomRound(float value)
/*    */   {
/* 11 */     double integerPart = Math.floor(value);
/* 12 */     double decimalPart = value - integerPart;
/* 13 */     if (MersenneTwister.getInstance().nextBoolean(decimalPart))
/* 14 */       integerPart += 1.0D;
/* 15 */     return (int)integerPart;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\utils\ValueRounder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */