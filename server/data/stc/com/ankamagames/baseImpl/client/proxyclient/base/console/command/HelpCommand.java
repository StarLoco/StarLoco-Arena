/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.console.command;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandDescriptorSet;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
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
/*    */ public class HelpCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 29 */     ArrayList<CommandPattern> commandSets = new ArrayList();
/* 30 */     ArrayList<CommandPattern> commands = new ArrayList();
/*    */     
/*    */ 
/* 33 */     ArrayList<CommandPattern> customCommands = manager.getCommandDescriptorSet().getChildren();
/* 34 */     for (CommandPattern commandPattern : customCommands) {
/* 35 */       if (commandPattern.getLevel() <= manager.getUserLevel()) {
/* 36 */         if ((commandPattern instanceof CommandDescriptorSet)) {
/* 37 */           commandSets.add(commandPattern);
/*    */         } else {
/* 39 */           commands.add(commandPattern);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 45 */     ArrayList<CommandPattern> globalCommands = manager.getNativeCommandDescriptorSet().getChildren();
/* 46 */     for (CommandPattern commandPattern : globalCommands) {
/* 47 */       if (commandPattern.getLevel() <= manager.getUserLevel()) {
/* 48 */         if ((commandPattern instanceof CommandDescriptorSet)) {
/* 49 */           commandSets.add(commandPattern);
/*    */         } else {
/* 51 */           commands.add(commandPattern);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 57 */     StringBuilder builder = new StringBuilder("# Liste des commandes #\n");
/* 58 */     for (CommandPattern commandPattern : commandSets) {
/* 59 */       builder.append("[").append(commandPattern.getName()).append("] ");
/*    */     }
/* 61 */     for (CommandPattern commandPattern : commands) {
/* 62 */       builder.append(commandPattern.getName()).append(" ");
/*    */     }
/*    */     
/* 65 */     manager.trace(builder.toString());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\HelpCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */