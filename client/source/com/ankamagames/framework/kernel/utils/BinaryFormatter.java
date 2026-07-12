/*    */ package com.ankamagames.framework.kernel.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BinaryFormatter
/*    */ {
/*    */   public static String toString(byte[] b) {
/* 14 */     if (b == null)
/* 15 */       return ""; 
/* 16 */     StringBuilder sb = new StringBuilder();
/* 17 */     for (int i = 0; i < b.length; i++) {
/* 18 */       if (i != 0) sb.append(' '); 
/* 19 */       int intVal = b[i] & 0xFF;
/* 20 */       sb.append(Integer.toHexString(intVal));
/*    */     } 
/* 22 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kerne\\utils\BinaryFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */