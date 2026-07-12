/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.console;
/*    */ 
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
/*    */ public final class StandardConsoleView
/*    */   implements ConsoleView
/*    */ {
/* 16 */   protected static final Logger m_logger = Logger.getLogger(ConsoleView.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void log(String text)
/*    */   {
/* 24 */     m_logger.info(text);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void trace(String text)
/*    */   {
/* 33 */     m_logger.info(text);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void err(String text)
/*    */   {
/* 42 */     m_logger.error(text);
/*    */   }
/*    */   
/*    */   public void setPrompt(String prompt) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\StandardConsoleView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */