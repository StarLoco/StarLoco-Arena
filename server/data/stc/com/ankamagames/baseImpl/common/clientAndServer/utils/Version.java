/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Version
/*    */ {
/*    */   private static Version m_uniqueVersionChecker;
/*    */   
/*    */   protected Version()
/*    */   {
/* 18 */     m_uniqueVersionChecker = this;
/*    */   }
/*    */   
/*    */   public static boolean checkVersion(byte[] datas) {
/* 22 */     if (m_uniqueVersionChecker != null) {
/* 23 */       return m_uniqueVersionChecker.implCheckVersion(datas);
/*    */     }
/* 25 */     System.err.println("Le vérificateur de version n'a pas été définit");
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public static byte[] getNeededVersion()
/*    */   {
/* 31 */     if (m_uniqueVersionChecker != null) {
/* 32 */       return m_uniqueVersionChecker.implGetNeededVersion();
/*    */     }
/* 34 */     System.err.println("Le vérificateur de version n'a pas été définit");
/* 35 */     return new byte[0];
/*    */   }
/*    */   
/*    */   protected abstract boolean implCheckVersion(byte[] paramArrayOfByte);
/*    */   
/*    */   protected abstract byte[] implGetNeededVersion();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\utils\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */