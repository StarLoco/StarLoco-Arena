/*    */ package com.ankamagames.framework.kernel.impl;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.ServerInstance;
/*    */ import com.ankamagames.framework.kernel.impl.admin.AdminMessageCipher;
/*    */ import com.ankamagames.framework.kernel.impl.admin.AdminMessageDecoder;
/*    */ import com.ankamagames.framework.kernel.impl.admin.entity.AdminEntityPoolFactory;
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
/*    */ public class AdminServerInstance
/*    */   extends ServerInstance
/*    */ {
/* 20 */   private static final AdminServerInstance m_instance = new AdminServerInstance();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AdminServerInstance getInstance()
/*    */   {
/* 28 */     return m_instance;
/*    */   }
/*    */   
/*    */   public AdminServerInstance()
/*    */   {
/* 33 */     super("AdminServerInstance");
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
/*    */   public void initialize(String keyStoreFileName, String storeType, String alias, String password)
/*    */     throws Exception
/*    */   {
/* 50 */     AdminMessageCipher.getInstance().initialize(keyStoreFileName, storeType, alias, password);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean start(String bindAddress, int bindPort)
/*    */   {
/* 61 */     AdminMessageDecoder decoder = new AdminMessageDecoder();
/*    */     
/* 63 */     registerMessageDecoder(decoder);
/* 64 */     registerEntityFactory(new AdminEntityPoolFactory());
/*    */     try
/*    */     {
/* 67 */       initialize(bindAddress, bindPort);
/* 68 */       start();
/*    */     } catch (Exception ex) {
/* 70 */       m_logger.error(ExceptionFormatter.toString(ex));
/* 71 */       return false;
/*    */     }
/* 73 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\AdminServerInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */