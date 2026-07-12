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
/*    */ public class NavigateToCommandSetCommand
/*    */   implements Command
/*    */ {
/*    */   private CommandDescriptorSet m_commandDescriptorSet;
/*    */   
/*    */   public NavigateToCommandSetCommand(CommandDescriptorSet commandDescriptorSet) {
/* 26 */     this.m_commandDescriptorSet = commandDescriptorSet;
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
/* 37 */     manager.setCommandDescriptorSet(this.m_commandDescriptorSet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\NavigateToCommandSetCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */