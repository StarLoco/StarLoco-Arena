/*    */ package com.ankamagames.dofusarena.client.console.command.common.OnlineHelpCommand;
/*    */ 
/*    */ import com.Ostermiller.util.Browser;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnlineHelpCommand
/*    */   implements Command
/*    */ {
/* 25 */   private static Logger m_logger = Logger.getLogger(com.ankamagames.dofusarena.client.console.command.common.OnlineHelpCommand.class);
/*    */   
/*    */   static {
/* 28 */     Browser.init();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/*    */     try {
/* 40 */       Browser.displayURL("http://www.dofus-arena.com");
/* 41 */     } catch (IOException e) {
/* 42 */       m_logger.error("Problème lors du chargement de la page d'aide en ligne");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\common\OnlineHelpCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */