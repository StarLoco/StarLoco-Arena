/*    */ package com.ankamagames.framework.kernel.core.maths;
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
/*    */ public class Functions
/*    */ {
/*    */   public static int isqrt(int n) {
/* 16 */     for (int a = 0; n >= 2 * a + 1; n -= 2 * a++ + 1);
/* 17 */     return a;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\maths\Functions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */