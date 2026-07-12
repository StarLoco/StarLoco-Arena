/*    */ package com.ankamagames.framework.kernel.utils;
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
/*    */ public abstract class StringUtils
/*    */ {
/*    */   public static byte[] toUTF8(String s)
/*    */   {
/*    */     try
/*    */     {
/* 24 */       return s.getBytes("UTF-8");
/*    */     } catch (UnsupportedEncodingException e) {}
/* 26 */     return s.getBytes();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String fromUTF8(byte[] b)
/*    */   {
/*    */     try
/*    */     {
/* 37 */       return new String(b, "UTF-8");
/*    */     } catch (UnsupportedEncodingException e) {}
/* 39 */     return new String(b);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\utils\StringUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */