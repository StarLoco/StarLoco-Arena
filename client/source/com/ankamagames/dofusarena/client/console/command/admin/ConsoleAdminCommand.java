/*    */ package com.ankamagames.dofusarena.client.console.command.admin;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.ConsoleAdminCommandMessage;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*    */ public abstract class ConsoleAdminCommand
/*    */   implements Command
/*    */ {
/*    */   private byte m_serverId;
/*    */   
/*    */   protected ConsoleAdminCommand(byte serverId) {
/* 31 */     this.m_serverId = serverId;
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
/* 42 */     ConsoleAdminCommandMessage netMessage = new ConsoleAdminCommandMessage();
/* 43 */     netMessage.setCommand(args.get(0));
/* 44 */     netMessage.setServerId(this.m_serverId);
/* 45 */     NetworkEntity networkEntity = DofusArenaGameEntity.getInstance().getNetworkEntity();
/* 46 */     if (networkEntity != null) {
/* 47 */       networkEntity.sendMessage((Message)netMessage);
/*    */     } else {
/* 49 */       manager.err("Pour accéder à ces commandes il faut être connecté !");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\admin\ConsoleAdminCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */