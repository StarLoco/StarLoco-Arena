/*    */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.StandardConsoleView;
/*    */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.dofusarena.client.console.DofusArenaConsoleView;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
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
/*    */ public class ConsoleLoader
/*    */   implements ContentInitializer
/*    */ {
/* 23 */   private static ConsoleLoader m_instance = new ConsoleLoader();
/*    */   
/*    */ 
/*    */ 
/*    */   public static ConsoleLoader getInstance()
/*    */   {
/* 29 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 38 */     return DofusArenaTranslator.getInstance().getString("contentLoader.console", new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(AbstractGameClientInstance clientInstance)
/*    */     throws Exception
/*    */   {
/* 49 */     ConsoleManager.getInstance().addView(DofusArenaConsoleView.getInstance());
/* 50 */     ConsoleManager.getInstance().addView(new StandardConsoleView());
/* 51 */     if (DofusArenaClientConstants.DEBUG_CONSOLE_COMMANDS_PATH != null) {
/* 52 */       ConsoleManager.getInstance().addCommandListFromXmlFile(DofusArenaClientConstants.DEBUG_CONSOLE_COMMANDS_PATH);
/* 53 */       ConsoleManager.getInstance().addCommandListFromXmlFile(DofusArenaClientConstants.CONSOLE_COMMANDS_PATH);
/*    */     } else {
/* 55 */       throw new Exception("Impossible de trouver la définition des commandes de console.");
/*    */     }
/*    */     
/* 58 */     clientInstance.fireContentInitializerDone(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\ConsoleLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */