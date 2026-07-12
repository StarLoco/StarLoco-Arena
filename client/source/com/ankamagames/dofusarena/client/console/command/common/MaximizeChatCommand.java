/*    */ package com.ankamagames.dofusarena.client.console.command.common;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import java.util.ArrayList;
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
/*    */ public class MaximizeChatCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 31 */     if (Xulor.getInstance().isLoaded("chatDialog")) {
/* 32 */       boolean isMaximized = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("chat.isMaximize").getBoolean();
/* 33 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.isMaximize", Boolean.valueOf(!isMaximized));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\common\MaximizeChatCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */