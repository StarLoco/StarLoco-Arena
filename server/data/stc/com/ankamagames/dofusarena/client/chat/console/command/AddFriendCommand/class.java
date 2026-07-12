/*    */ package com.ankamagames.dofusarena.client.chat.console.command;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.AddFriendMessage;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
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
/*    */ public class AddFriendCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 31 */     String userName = ((String)args.get(2)).replaceAll("\"", "");
/*    */     
/*    */ 
/* 34 */     AddFriendMessage privateMessage = new AddFriendMessage();
/* 35 */     privateMessage.setFriendName(userName);
/* 36 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(privateMessage);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\console\command\AddFriendCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */