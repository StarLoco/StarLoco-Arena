/*    */ package com.ankamagames.framework.kernel.impl.admin;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.ServerCipher;
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
/*    */ public final class AdminMessageCipher
/*    */ {
/* 16 */   public static final Logger m_logger = Logger.getLogger(AdminMessageCipher.class);
/*    */   
/* 18 */   protected static final AdminMessageCipher m_instance = new AdminMessageCipher();
/*    */   
/*    */   protected ServerCipher m_serverCipher;
/*    */   
/*    */ 
/*    */   private AdminMessageCipher()
/*    */   {
/* 25 */     this.m_serverCipher = null;
/*    */   }
/*    */   
/*    */   public static AdminMessageCipher getInstance() {
/* 29 */     return m_instance;
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
/*    */   public void initialize(String keyStoreFileName, String storeType, String alias, String password)
/*    */     throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 47 */       this.m_serverCipher = new ServerCipher(keyStoreFileName, storeType, alias, password);
/*    */     }
/*    */     catch (Exception e) {
/* 50 */       this.m_serverCipher = null;
/* 51 */       m_logger.error("Impossible de créer le crypteur/decrypteur de données (raison : " + e.getMessage() + ")");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static byte[] crypt(byte[] data)
/*    */   {
/* 61 */     if (m_instance.m_serverCipher != null) {
/* 62 */       return m_instance.m_serverCipher.crypt(data);
/*    */     }
/* 64 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static byte[] decrypt(byte[] data)
/*    */   {
/* 73 */     if (m_instance.m_serverCipher != null) {
/* 74 */       return m_instance.m_serverCipher.decrypt(data);
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\AdminMessageCipher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */