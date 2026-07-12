/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
/*    */ import org.apache.log4j.Logger;
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
/*    */ 
/*    */ public final class DiceRoll
/*    */ {
/* 20 */   protected static final Logger m_logger = Logger.getLogger(DiceRoll.class);
/*    */ 
/*    */   
/*    */   public static int roll(int diceValue) {
/* 24 */     if (diceValue <= 0) {
/* 25 */       m_logger.error("DiceRoll.roll appelé avec une valeur de dé de " + diceValue + "\n" + ExceptionFormatter.toString(new RuntimeException("StackTrace de DiceRoll")));
/* 26 */       return 1;
/*    */     } 
/* 28 */     return MersenneTwister.getInstance().nextInt(diceValue) + 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public static long roll(long diceValue) {
/* 33 */     if (diceValue <= 0L) {
/* 34 */       m_logger.error("DiceRoll.roll appelé avec une valeur de dé de " + diceValue + "\n" + ExceptionFormatter.toString(new RuntimeException("StackTrace de DiceRoll")));
/* 35 */       return 1L;
/*    */     } 
/* 37 */     return MersenneTwister.getInstance().nextLong(diceValue) + 1L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int roll(int diceCount, int diceValue, int moderator) {
/* 43 */     if (diceValue <= 0) {
/* 44 */       m_logger.error("DiceRoll.roll appelé avec une valeur de dé de " + diceValue + "\n" + ExceptionFormatter.toString(new RuntimeException("StackTrace de DiceRoll")));
/* 45 */       return 1;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     int total = moderator + diceCount;
/* 55 */     if (diceValue > 0 && diceCount > 0)
/*    */     {
/* 57 */       for (int i = diceCount; i > 0; i--)
/*    */       {
/* 59 */         total += MersenneTwister.getInstance().nextInt(diceValue);
/*    */       }
/*    */     }
/* 62 */     return total;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int roll(int min, int max) {
/* 68 */     int total = min;
/* 69 */     if (min > 0 && max > 0 && max - min > 0)
/*    */     {
/* 71 */       total = min + MersenneTwister.getInstance().nextInt(max - min + 1);
/*    */     }
/* 73 */     return total;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServe\\utils\DiceRoll.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */