/*    */ package com.ankamagames.dofusarena.client.chat.console.command;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.clientToServer.UserChannelContentMessage;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
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
/*    */ public class ChannelContentCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 35 */     LocalCoach coach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*    */     
/* 37 */     if (coach != null)
/*    */     {
/* 39 */       ChatMessage message = new ChatMessage(coach.getName(), (String)args.get(3));
/* 40 */       message.setPipeDestination(3);
/*    */       
/* 42 */       ChatManager.getInstance().pushMessage(message, (String)args.get(2));
/*    */     }
/*    */     
/* 45 */     UserChannelContentMessage channelMessage = new UserChannelContentMessage();
/* 46 */     channelMessage.setChannelName((String)args.get(2));
/* 47 */     channelMessage.setMessageContent((String)args.get(3));
/* 48 */     DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(channelMessage);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\console\command\ChannelContentCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */