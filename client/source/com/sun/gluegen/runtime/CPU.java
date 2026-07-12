/*    */ package com.sun.gluegen.runtime;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPU
/*    */ {
/*    */   private static boolean is32Bit;
/*    */   
/*    */   static {
/* 54 */     String str1 = System.getProperty("os.name").toLowerCase();
/* 55 */     String str2 = System.getProperty("os.arch").toLowerCase();
/* 56 */     if ((str1.startsWith("windows") && str2.equals("x86")) || (str1.startsWith("linux") && str2.equals("i386")) || (str1.startsWith("mac os") && str2.equals("ppc")) || (str1.startsWith("mac os") && str2.equals("i386")) || (str1.startsWith("sunos") && str2.equals("sparc")) || (str1.startsWith("sunos") && str2.equals("x86")) || (str1.startsWith("freebsd") && str2.equals("i386")) || (str1.startsWith("hp-ux") && str2.equals("pa_risc2.0"))) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 64 */       is32Bit = true;
/* 65 */     } else if ((!str1.startsWith("windows") || !str2.equals("amd64")) && (!str1.startsWith("linux") || !str2.equals("amd64")) && (!str1.startsWith("linux") || !str2.equals("x86_64")) && (!str1.startsWith("linux") || !str2.equals("ia64")) && (!str1.startsWith("sunos") || !str2.equals("sparcv9")) && (!str1.startsWith("sunos") || !str2.equals("amd64"))) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 72 */       throw new RuntimeException("Please port CPU detection (32/64 bit) to your platform (" + str1 + "/" + str2 + ")");
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean is32Bit() {
/* 77 */     return is32Bit;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\CPU.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */