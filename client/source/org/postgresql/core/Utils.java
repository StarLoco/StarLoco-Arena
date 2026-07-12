/*    */ package org.postgresql.core;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static String toHexString(byte[] data) {
/* 26 */     StringBuffer sb = new StringBuffer(data.length * 2);
/* 27 */     for (int i = 0; i < data.length; i++) {
/*    */       
/* 29 */       sb.append(Integer.toHexString(data[i] >> 4 & 0xF));
/* 30 */       sb.append(Integer.toHexString(data[i] & 0xF));
/*    */     } 
/* 32 */     return sb.toString();
/*    */   }
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
/*    */   public static byte[] encodeUTF8(String str) {
/*    */     try {
/* 48 */       return str.getBytes("UTF-8");
/*    */     
/*    */     }
/*    */     catch (UnsupportedEncodingException e) {
/*    */       
/* 53 */       throw new RuntimeException("Unexpected exception: UTF-8 charset not supported: " + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\Utils.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */