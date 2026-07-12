/*    */ package com.ankamagames.framework.kernel.impl;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.ServerInstance;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageDecoder;
/*    */ import com.ankamagames.framework.kernel.impl.admin.AdminMessageCipher;
/*    */ import com.ankamagames.framework.kernel.impl.admin.AdminMessageDecoder;
/*    */ import com.ankamagames.framework.kernel.impl.admin.entity.AdminEntityPoolFactory;
/*    */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
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
/*    */   
/*    */   public static AdminServerInstance getInstance() {
/* 28 */     return m_instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public AdminServerInstance() {
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
/*    */ 
/*    */   
/*    */   public void initialize(String keyStoreFileName, String storeType, String alias, String password) throws Exception {
/* 50 */     AdminMessageCipher.getInstance().initialize(keyStoreFileName, storeType, alias, password);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean start(String bindAddress, int bindPort) {
/* 61 */     AdminMessageDecoder decoder = new AdminMessageDecoder();
/*    */     
/* 63 */     registerMessageDecoder((MessageDecoder)decoder);
/* 64 */     registerEntityFactory((ObjectFactory)new AdminEntityPoolFactory());
/*    */     
/*    */     try {
/* 67 */       initialize(bindAddress, bindPort);
/* 68 */       start();
/* 69 */     } catch (Exception ex) {
/* 70 */       m_logger.error(ExceptionFormatter.toString(ex));
/* 71 */       return false;
/*    */     } 
/* 73 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\AdminServerInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */